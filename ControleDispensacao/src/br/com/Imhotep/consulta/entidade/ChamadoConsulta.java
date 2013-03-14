package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Chamado;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class ChamadoConsulta extends PadraoConsulta<Chamado> {
	
	public ChamadoConsulta(){
		getCamposConsulta().put("o.assunto", INCLUINDO_TUDO);
		setOrderBy("o.dataSolicitacao desc");
	}
	
	@Override
	public List<Chamado> getList() {
		setConsultaGeral(new ConsultaGeral<Chamado>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Chamado o where o.profissionalSolicitante.idProfissional = "+getIdProfissionalAtual()));
		return super.getList();
	}
	
	private int getIdProfissionalAtual(){
		int id=0;
		try {
			id = Autenticador.getInstancia().getProfissionalAtual().getIdProfissional();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return id;
	}
}
