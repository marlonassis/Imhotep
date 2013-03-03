package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;
import br.com.Imhotep.entidade.NotaFiscal;

@ManagedBean(name="notaFiscalConsulta")
@SessionScoped
public class NotaFiscalConsulta extends PadraoConsulta<NotaFiscal> {
	public NotaFiscalConsulta(){
		getCamposConsulta().put("o.fornecedor", IGUAL);
		getCamposConsulta().put("o.identificacaoNotaFiscal", CONTENDO);
		setOrderBy("o.dataInsercao desc");
	}
	
	@Override
	public List<NotaFiscal> getList() {
		setConsultaGeral(new ConsultaGeral<NotaFiscal>());
		String sql = "select o from NotaFiscal o where 1=1";
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		return super.getList();
	}
}
