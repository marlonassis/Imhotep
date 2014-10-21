package br.com.imhotep.consulta.entidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="prescricaoUsuarioConsulta")
@SessionScoped
public class PrescricaoUsuarioConsulta extends PadraoConsulta<Prescricao> {
	public PrescricaoUsuarioConsulta(){
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		carregaValoresConsulta();
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.profissionalInclusao.idProfissional = :profissionalInclusao "));
		return super.getList();
	}
 
	private void carregaValoresConsulta() {
		HashMap<Object, Object> restricaoConsulta = new HashMap<Object, Object>();
		try {
			restricaoConsulta.put("profissionalInclusao", Autenticador.getInstancia().getUsuarioAtual().getIdUsuario());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pesquisar a prescri��o por usu�rio.", "Erro ao pegar o usu�rio", FacesMessage.SEVERITY_ERROR);
		}
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
