package br.com.Imhotep.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.controle.ControleInstancia;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.raiz.NotaFiscalRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estoquePorNotaFiscalConsulta")
@SessionScoped
public class EstoquePorNotaFiscalConsulta extends PadraoConsulta<Estoque> {
	public EstoquePorNotaFiscalConsulta(){
		setOrderBy("o.estoque.lote, o.estoque.dataInclusao");
	}
	
	@Override
	public List<Estoque> getList() {
		setConsultaGeral(new ConsultaGeral<Estoque>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o.estoque from NotaFiscalEstoque o where o.notaFiscal.identificacaoNotaFiscal = :identificacaoNotaFiscal"));
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
