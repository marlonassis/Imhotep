package br.com.ControleDispensacao.consultaEntidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

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
		restricaoConsulta.put("profissionalInclusao", Autenticador.getInstancia().getUsuarioAtual().getIdUsuario());
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
