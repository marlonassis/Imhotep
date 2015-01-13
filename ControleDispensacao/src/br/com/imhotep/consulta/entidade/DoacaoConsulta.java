package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Doacao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class DoacaoConsulta extends PadraoConsulta<Doacao> {
	public DoacaoConsulta(){
		getCamposConsulta().put("o.hospital", IGUAL);
		getCamposConsulta().put("date(o.dataDoacao)", IGUAL);
		getCamposConsulta().put("o.tipoMovimento", IGUAL);
		setOrderBy("o.dataDoacao desc");
	}
	
	@Override
	public List<Doacao> getList() {
		setConsultaGeral(new ConsultaGeral<Doacao>());
		if(super.isTodosCamposVazios())
			getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Doacao o where o.liberado is false"));
		else
			getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Doacao o where 1=1"));
		return super.getList();
	}
}
