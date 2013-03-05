package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Doacao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="doacaoConsulta")
@SessionScoped
public class DoacaoConsulta extends PadraoConsulta<Doacao> {
	public DoacaoConsulta(){
		getCamposConsulta().put("o.hospital", IGUAL);
		setOrderBy("o.dataDoacao desc");
	}
	
	@Override
	public List<Doacao> getList() {
		setConsultaGeral(new ConsultaGeral<Doacao>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Doacao o where 1=1"));
		return super.getList();
	}
}
