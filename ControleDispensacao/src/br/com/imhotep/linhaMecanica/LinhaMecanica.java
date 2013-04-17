package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Menu;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.excecoes.ExcecaoProfissionalNaoEncontrado;
import br.com.imhotep.seguranca.Autenticador;

public class LinhaMecanica extends GerenciadorMecanico {
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public boolean executarCUD(String sql, String banco){
		setNomeBanco(banco);
		return super.executarQuery(sql);
	}
	
	private Profissional getProfissionalAtual() throws ExcecaoProfissionalNaoEncontrado{
		try {
			return Autenticador.getInstancia().getProfissionalAtual();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		};
		throw new ExcecaoProfissionalNaoEncontrado();
	}
	
	public void removerAutorizacaoMenu(Profissional profissional, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		removerFilhos(profissional, menu.getMenusFilho());
		String sql = "delete from tb_autoriza_menu_profissional where id_profissional = "+profissional.getIdProfissional()+" and " +
				"id_menu = "+menu.getIdMenu();
		executarQuery(sql);
	}
	
	public void removerAutorizacaoMenu(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		removerFilhos(especialidade, menu.getMenusFilho());
		String sql = "delete from tb_autoriza_menu where id_especialidade = "+especialidade.getIdEspecialidade()+" and " +
				"id_menu = "+menu.getIdMenu();
		executarQuery(sql);
	}
	
	private void removerFilhos(Profissional profissional, List<Menu> filhos) {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				removerFilhos(profissional, filho.getMenusFilho());
			String sql = "delete from tb_autoriza_menu_profissional where id_profissional = "+profissional.getIdProfissional()+" and " +
					"id_menu = "+filho.getIdMenu();
			executarQuery(sql);
		}
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
	
	public void inserirAutorizacaoMenu(Profissional profissional, Menu menu) throws ExcecaoProfissionalNaoEncontrado{
		setNomeBanco(DB_BANCO_IMHOTEP);
		liberarPaisMenu(profissional, menu);
		liberarFilhosMenu(profissional, menu.getMenusFilho());
	}
	
	public void inserirAutorizacaoMenu(Especialidade especialidade, Menu menu){
		setNomeBanco(DB_BANCO_IMHOTEP);
		liberarPaisMenu(especialidade, menu);
		liberarFilhosMenu(especialidade, menu.getMenusFilho());
	}
	
	private void liberarFilhosMenu(Profissional profissional, List<Menu> filhos) throws ExcecaoProfissionalNaoEncontrado {
		for(Menu filho : filhos){
			if(filho.getMenusFilho() != null && !filho.getMenusFilho().isEmpty())
				liberarFilhosMenu(profissional, filho.getMenusFilho());
			String sql = "insert into tb_autoriza_menu_profissional (id_profissional, id_menu, id_profissional_insercao, dt_data_criacao) " +
					"values ("+profissional.getIdProfissional()+", "+filho.getIdMenu()+", "+getProfissionalAtual().getIdProfissional() + ", '"+new SimpleDateFormat().format(new Date())+"')";
			executarQuery(sql);
		}
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
	
	private void liberarPaisMenu(Profissional profissional, Menu menu) throws ExcecaoProfissionalNaoEncontrado {
		if(menu.getMenuPai() != null)
			liberarPaisMenu(profissional, menu.getMenuPai());
		String sql = "insert into tb_autoriza_menu_profissional (id_profissional, id_menu, id_profissional_insercao, dt_data_criacao) " +
				"values ("+profissional.getIdProfissional()+", "+menu.getIdMenu()+", "+getProfissionalAtual().getIdProfissional() + ", '"+new SimpleDateFormat().format(new Date())+"')";
		executarQuery(sql);
		
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
		lm.setNomeBanco(DB_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(lm.utf8_to_latin1("select id_menu, cv_url, cv_descricao from tb_menu where cv_url is not null"));
		while (rs.next()) { 
			int idMenu = rs.getInt(1);
			String url = rs.getString(2);
//			String descricao = rs.getString(3);
			String sql = "update tb_menu set cv_url = '"+url.replaceAll("jsf", "hu")+"' where id_menu = "+idMenu;
			lm.executarQuery(sql);
			System.out.println("idM: "+idMenu+" - urlAntiga: "+url+" - urlNova: "+url.replaceAll("jsf", "hu"));
//			if(url.indexOf(".") < 0)
//				System.out.println("idM: "+idMenu+" - nome: "+descricao+" - url: "+url);
		}
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
