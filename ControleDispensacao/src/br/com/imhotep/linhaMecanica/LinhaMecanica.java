package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.Imhotep.entidade.Especialidade;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Menu;

public class LinhaMecanica extends GerenciadorMecanico {
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public void removerAutorizacaoMenuEspecialidade(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		removerFilhos(especialidade, menu.getMenusFilho());
		String sql = "delete from tb_autoriza_menu where id_especialidade = "+especialidade.getIdEspecialidade()+" and " +
				"id_menu = "+menu.getIdMenu();
		executarQuery(sql);
	}
	
	private void removerFilhos(Especialidade especialidade, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				removerFilhos(especialidade, filho.getMenusFilho());
			String sql = "delete from tb_autoriza_menu where id_especialidade = "+especialidade.getIdEspecialidade()+" and " +
					"id_menu = "+filho.getIdMenu();
			executarQuery(sql);
		}
	}
	
	public void inserirAutorizacaoMenuEspecialidade(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		liberarPaisMenu(especialidade, menu);
		liberarFilhosMenu(especialidade, menu.getMenusFilho());
	}

	private void liberarFilhosMenu(Especialidade especialidade, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				liberarFilhosMenu(especialidade, filho.getMenusFilho());
			String sql = "insert into tb_autoriza_menu (id_especialidade, id_menu) " +
					"values ("+especialidade.getIdEspecialidade()+", "+filho.getIdMenu()+")";
			executarQuery(sql);
		}
	}
	
	private void liberarPaisMenu(Especialidade especialidade, Menu menu) {
		if(menu.getMenuPai() != null)
			liberarPaisMenu(especialidade, menu.getMenuPai());
		String sql = "insert into tb_autoriza_menu (id_especialidade, id_menu) " +
				"values ("+especialidade.getIdEspecialidade()+", "+menu.getIdMenu()+")";
		executarQuery(sql);
	}
	
	public boolean apagarMovimentoLivroEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_movimento_livro where id_estoque = "+idEstoque;
		return executarQuery(sql);
	}
	
	public boolean fluxoFusaoEstoque(int idEstoqueOrigem, int idEstoqueDestino, int idMaterial){
		setNomeBanco(DB_BANCO_IMHOTEP);
		
		String sql = "update tb_estoque set bl_lock = true where id_estoque = "+idEstoqueOrigem+" or id_estoque = "+idEstoqueDestino;
		if(!executarQuery(sql))
			return false;
		
		sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("transfereMovimentacaoEstoque", sql);
		
		sql = "delete from tb_estoque where id_estoque = "+idEstoqueOrigem;
		super.getFluxo().put("apagarEstoque", sql);
		
		if(!executarQueryFluxo())
			return false;
		
		sql = "update tb_estoque set bl_lock = false where id_estoque = "+idEstoqueDestino;
		if(!executarQuery(sql))
			return false;
		
		if(!reordenacaoMovimento(idMaterial))
			return false;
		return true;
	}
	
	public boolean apagarEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "delete from tb_estoque where id_estoque = "+idEstoque;
		return executarQuery(sql);
	}
	
	public void transfereMovimentacaoEstoque(int idEstoqueOrigem, int idEstoqueDestino){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_movimento_livro set id_estoque = "+idEstoqueDestino+" where id_estoque = "+idEstoqueOrigem;
		executarQuery(sql);
	}
	
	public void lockEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque set bl_lock = true where id_estoque = "+idEstoque;
		executarQuery(sql);
	}
	
	public void unLockEstoque(int idEstoque){
		setNomeBanco(DB_BANCO_IMHOTEP);
		String sql = "update tb_estoque set bl_lock = false where id_estoque = "+idEstoque;
		executarQuery(sql);
	}
	
	public void salvarLogSessaoDestruida(Date dataLog, String idSessao, Date dataUltimoMovimentoSessao, int tempoSessao){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select cv_sessao from tb_usuario_acesso_log where cv_sessao = '"+idSessao+"'"));
			while (rs.next()) { 
				String sql = "insert into tb_usuario_acesso_log (dt_data_log, tp_tipo_log, cv_sessao, dt_data_ultimo_movimento_sessao, in_tempo_sessao) " +
						"values ('"+new SimpleDateFormat().format(dataLog)+"','A', '"+idSessao+"', '"+new SimpleDateFormat().format(dataUltimoMovimentoSessao)+"', " +
								tempoSessao+")";
				executarQuery(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		LinhaMecanica lm = new LinhaMecanica();

//		lm.reordenacaoMovimento(517);
		
		Calendar ini = Calendar.getInstance();
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_material from tb_material order by id_material"));
		while (rs.next()) { 
			lm.reordenacaoMovimento(rs.getInt(1));
		}
		Calendar fim = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(ini.getTime()));
		System.out.println(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fim.getTime()));
		System.out.println( ((fim.getTimeInMillis() - ini.getTimeInMillis()) / 1000 / 60) + "min e " + ((((fim.getTimeInMillis() - ini.getTimeInMillis()) / 1000)%60)*60) + "s" );
	}

	public List<Estoque> estoquePorMaterial(int idMaterial){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select id_estoque, cv_lote from tb_estoque " +
					"where id_material = "+idMaterial));
			List<Estoque> list = new ArrayList<Estoque>();
			while (rs.next()) { 
				int idEstoque = rs.getInt(1);
				String lote = rs.getString(2);
				Estoque estoque = new Estoque();
				estoque.setIdEstoque(idEstoque);
				estoque.setLote(lote);
				list.add(estoque);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean reordenacaoMovimento(int idMaterial){
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			ResultSet rs = consultar(utf8_to_latin1("select a.id_movimento_livro, a.in_saldo_anterior, a.in_quantidade_movimentacao, b.tp_operacao from tb_movimento_livro a " +
					"inner join tb_tipo_movimento b on b.id_tipo_movimento = a.id_tipo_movimento " +
					"inner join tb_estoque c on c.id_estoque = a.id_estoque " +
					"inner join tb_material d on d.id_material = c.id_material " +
					"where d.id_material = "+idMaterial+" order by a.dt_data_movimento asc"));
			int saldoAnterior = 0;
			int quantidadeMovimentacao = 0;
			String tipoOperacao = null;
			while (rs.next()) { 
				int idMovimentoLivro = rs.getInt(1);
				quantidadeMovimentacao = rs.getInt(3);
				String sql = "update tb_movimento_livro set in_saldo_anterior = "+saldoAnterior+" where id_movimento_livro = "+idMovimentoLivro;
				executarQuery(sql);
				tipoOperacao = rs.getString(4);
				System.out.println("idML: "+idMovimentoLivro+" - idMaterial: "+idMaterial+" - SA: "+saldoAnterior+" - QM: "+quantidadeMovimentacao);
				if(tipoOperacao.equals("E"))
					saldoAnterior += quantidadeMovimentacao;
				else
					saldoAnterior -= quantidadeMovimentacao;
			}
			
			String sql = "update tb_estoque set in_quantidade_atual = "+saldoAnterior+" where id_estoque = "+idMaterial;
			executarQuery(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
