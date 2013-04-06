package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.NotaFiscalEstoque;
import br.com.imhotep.raiz.NotaFiscalRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="notaFiscalEstoqueItensConsulta")
@SessionScoped
public class NotaFiscalEstoqueItensConsulta extends PadraoConsulta<NotaFiscalEstoque> {
	public NotaFiscalEstoqueItensConsulta(){
		setOrderBy("o.estoque.lote, o.estoque.dataInclusao");
	}
	
	@Override
	public List<NotaFiscalEstoque> getList() {
		setConsultaGeral(new ConsultaGeral<NotaFiscalEstoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from NotaFiscalEstoque o where o.notaFiscal.identificacaoNotaFiscal = :identificacaoNotaFiscal"));
		carregarValorConsulta();
		return super.getList();
	}

	private void carregarValorConsulta() {
		try {
			getConsultaGeral().getAddValorConsulta().put("identificacaoNotaFiscal", ((NotaFiscalRaiz) new ControleInstancia().procuraInstancia(NotaFiscalRaiz.class)).getInstancia().getIdentificacaoNotaFiscal() );
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
