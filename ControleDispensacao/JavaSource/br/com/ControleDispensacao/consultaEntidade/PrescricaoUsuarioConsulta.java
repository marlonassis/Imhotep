package br.com.ControleDispensacao.consultaEntidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.seguranca.Autenticador;
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
			super.mensagem("Erro ao pesquisar a prescrição por usuário.", "Erro ao pegar o usuário", FacesMessage.SEVERITY_ERROR);
		}
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
