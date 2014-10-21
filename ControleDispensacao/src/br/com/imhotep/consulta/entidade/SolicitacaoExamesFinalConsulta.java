package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioSolicitacao;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class SolicitacaoExamesFinalConsulta extends PadraoConsulta<LaboratorioSolicitacao> {
	
	private boolean exibirExamePendente;
	
	public SolicitacaoExamesFinalConsulta(){
		getCamposConsulta().put("o.paciente", IGUAL);
		getCamposConsulta().put("o.statusSolicitacao", IGUAL);
		getCamposConsulta().put("o.profissionalSolicitacao", IGUAL);
		getCamposConsulta().put("o.idLaboratorioSolicitacao", IGUAL);
	}
	
	@Override
	public void carregarResultado() {
		setConsultaGeral(new ConsultaGeral<LaboratorioSolicitacao>());
		
		String hql = "";
		if(isExibirExamePendente()){
			setPesquisaGuiada(false);
			hql = "select o from LaboratorioSolicitacao o where o.statusSolicitacao in ('AB', 'AC', 'AD', 'AE') ";
		}
		else{
			setPesquisaGuiada(true);
			hql = "select o from LaboratorioSolicitacao o where o.statusSolicitacao != 'AA'";
		}
		
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(hql));
		super.carregarResultado();
	}

	public boolean isExibirExamePendente() {
		return exibirExamePendente;
	}

	public void setExibirExamePendente(boolean exibirExamePendente) {
		this.exibirExamePendente = exibirExamePendente;
	}
}
