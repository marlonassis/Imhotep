package br.com.imhotep.consulta.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.DevolucaoMaterial;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoEstornoFalha;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EstornoMovimentacaoAlmoxarifadoConsultaRaiz  extends ConsultaGeral<DevolucaoMaterial>{

	public boolean isDiferecaNegativa(MovimentoLivroAlmoxarifado item){
		int idEA = item.getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
		int idMLA = item.getIdMovimentoLivroAlmoxarifado();
		String sql = "select coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a "+
						"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
						"where b.tp_operacao = 'E' and a.id_estoque_almoxarifado = "+idEA+" and a.id_movimento_livro_almoxarifado != "+idMLA+"), 0) "+
						"- "+
						"coalesce((select sum(a.in_quantidade_movimentacao) from tb_movimento_livro_almoxarifado a "+ 
						"inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
						"where b.tp_operacao != 'E' and a.id_estoque_almoxarifado = "+idEA+"), 0) as qtd";
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp(Constantes.IP_LOCAL);
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				int qtd = rs.getInt("qtd");
				if(qtd < 0){
					return true;
				}else{
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean isPrimeiroMovimento(MovimentoLivroAlmoxarifado item) throws ExcecaoEstornoFalha{
		int idEstoque = item.getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
		String sql = "select a.id_movimento_livro_almoxarifado = "+item.getIdMovimentoLivroAlmoxarifado()+
					 " primeiroMovimento from tb_movimento_livro_almoxarifado a where "+
					 "a.dt_data_movimento =  "+
					 "(select min(b.dt_data_movimento) from tb_movimento_livro_almoxarifado b where b.id_estoque_almoxarifado = "+idEstoque+") "+
					 "and a.id_estoque_almoxarifado = "+idEstoque+"";
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp(Constantes.IP_LOCAL);
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				boolean primeiroMovimento = rs.getBoolean("primeiroMovimento");
				return primeiroMovimento;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ExcecaoEstornoFalha();
	}
	
	public boolean isTemMovimentoPosteriorSaida(MovimentoLivroAlmoxarifado item) throws ExcecaoEstornoFalha{
		int idEstoque = item.getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
		String dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getDataMovimento());
		String sql = "select count(a.id_movimento_livro_almoxarifado) qtdMovimento from tb_movimento_livro_almoxarifado a " +
					 "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
					 "where cast(to_char(a.dt_data_movimento, 'YYYY-MM-DD HH24:MI:SS') as timestamp) > '"+dataFormat+"' "+
					 "and a.id_estoque_almoxarifado = "+idEstoque+" and b.tp_operacao != 'E'"; 
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp(Constantes.IP_LOCAL);
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(sql);
		try {
			while(rs.next()){
				int qtdMovimento = rs.getInt("qtdMovimento");
				return qtdMovimento > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ExcecaoEstornoFalha();
	}
	
	
	public boolean isSegundoMovimentoSaida(MovimentoLivroAlmoxarifado item) throws ExcecaoEstornoFalha{
		int idEstoque = item.getEstoqueAlmoxarifado().getIdEstoqueAlmoxarifado();
		String dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getDataMovimento());
		String sql = "select b.tp_operacao != 'E' saida from tb_movimento_livro_almoxarifado a "+ 
					 "inner join tb_tipo_movimento_almoxarifado b on a.id_tipo_movimento_almoxarifado = b.id_tipo_movimento_almoxarifado "+
					 "where "+
					 "cast(to_char(a.dt_data_movimento, 'YYYY-MM-DD HH24:MI:SS') as timestamp) > '"+dataFormat+"' "+
					 "and a.id_estoque_almoxarifado = "+idEstoque+"  "+
					 "order by a.dt_data_movimento  "+
					 "limit 1"; 
		LinhaMecanica lm = new LinhaMecanica();
		lm.setIp(Constantes.IP_LOCAL);
		lm.setNomeBanco(Constantes.NOME_BANCO_IMHOTEP);
		ResultSet rs = lm.consultar(sql);
		try {
			boolean saida = false;
			while(rs.next()){
				saida = rs.getBoolean("saida");
			}
			return saida;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new ExcecaoEstornoFalha();
	}
	
}