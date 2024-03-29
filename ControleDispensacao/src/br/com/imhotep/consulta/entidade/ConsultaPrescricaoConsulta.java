package br.com.imhotep.consulta.entidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Prescricao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="consultaPrescricaoConsulta")
@SessionScoped
public class ConsultaPrescricaoConsulta extends PadraoConsulta<Prescricao> {
	public ConsultaPrescricaoConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.unidade", IGUAL);
		getCamposConsulta().put("o.dataConclusao", MAIOR_IGUAL);
		setOrderBy("o.dataConclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		carregaValoresConsulta();
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.dataConclusao is not null and o.dataBloqueio is null"));
		return super.getList();
	}
	
	private void carregaValoresConsulta() {
		HashMap<Object, Object> restricaoConsulta = new HashMap<Object, Object>();
		try {
//			restricaoConsulta.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pesquisar prescri��o", "Erro ao pegar a unidade atual.", FacesMessage.SEVERITY_ERROR);
		}
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
