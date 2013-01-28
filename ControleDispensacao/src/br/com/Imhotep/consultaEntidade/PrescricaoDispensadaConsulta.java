package br.com.Imhotep.consultaEntidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="prescricaoDispensadaConsulta")
@SessionScoped
public class PrescricaoDispensadaConsulta extends PadraoConsulta<Prescricao> {
	public PrescricaoDispensadaConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.numero", IGUAL);
		getCamposConsulta().put("o.leito", IGUAL);
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		carregaValoresConsulta();
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.profissionalInclusao.idProfissional = :profissionalInclusao and o.dispensado = 'S'"));
		return super.getList();
	}
	
	private void carregaValoresConsulta() {
		HashMap<Object, Object> restricaoConsulta = new HashMap<Object, Object>();
		try {
			restricaoConsulta.put("profissionalInclusao", Autenticador.getInstancia().getUsuarioAtual().getIdUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pesquisar um prescrição dispensada.", "Erro ao pegar o usuário atual", FacesMessage.SEVERITY_ERROR);
		}
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
