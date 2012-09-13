package br.com.ControleDispensacao.consultaEntidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="consultaPrescricaoConsulta")
@SessionScoped
public class ConsultaPrescricaoConsulta extends PadraoConsulta<Prescricao> {
	public ConsultaPrescricaoConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.leito", IGUAL);
		getCamposConsulta().put("o.dataConclusao", IGUAL);
		setOrderBy("o.dataConclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		carregaValoresConsulta();
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.unidade.idUnidade = :idUnidade and o.dataConclusao is not null"));
		return super.getList();
	}
	
	private void carregaValoresConsulta() {
		HashMap<Object, Object> restricaoConsulta = new HashMap<Object, Object>();
		restricaoConsulta.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
