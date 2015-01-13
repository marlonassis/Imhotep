package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MedicamentoPrecoMedioLog;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MedicamentoPrecoMedioLogRaiz extends PadraoRaiz<MedicamentoPrecoMedioLog>{
	private Material medicamento;
	private boolean exibirDialogPrecoMedio;
	
	public List<MedicamentoPrecoMedioLog> getListaLog(){
		if(getMedicamento() != null){
			StringBuilder sb = new StringBuilder("select o from MedicamentoPrecoMedioLog o where o.medicamento.idMaterial = ");
			sb.append(getMedicamento().getIdMaterial());
			sb.append(" order by o.dataCadastro desc");
			Collection<MedicamentoPrecoMedioLog> consulta = new ConsultaGeral<MedicamentoPrecoMedioLog>(sb).consulta();
			return new ArrayList<MedicamentoPrecoMedioLog>(consulta);
		}
		return new ArrayList<MedicamentoPrecoMedioLog>();
	}
	
	public void exibirDialogPrecoMedio(){
		setExibirDialogPrecoMedio(true);
	}
	
	public void ocultarDialogPrecoMedio(){
		setExibirDialogPrecoMedio(false);
	}
	
	public void atualizarPrecoMedio(){
		try {
			getInstancia().setDataCadastro(new Date());
			getInstancia().setMedicamento(getMedicamento());
			getInstancia().setProfissionalResponsavel(Autenticador.getProfissionalLogado());
			getInstancia().setPrecoMedio(getMedicamento().getPrecoMedio());
			PadraoFluxoTemp.limparFluxo();
			PadraoFluxoTemp.getObjetoAtualizar().put("Medicamento - " + getMedicamento().hashCode(), getMedicamento());
			PadraoFluxoTemp.getObjetoSalvar().put("Log - " + getInstancia().hashCode(), getInstancia());
			PadraoFluxoTemp.finalizarFluxo();
			PadraoFluxoTemp.limparFluxo();
			super.mensagem("Alterado com sucesso", null, Constantes.INFO);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		super.novaInstancia();
	}
	
	public Material getMedicamento() {
		return medicamento;
	}

	public void setMedicamento(Material medicamento) {
		this.medicamento = medicamento;
	}

	public boolean isExibirDialogPrecoMedio() {
		return exibirDialogPrecoMedio;
	}

	public void setExibirDialogPrecoMedio(boolean exibirDialogPrecoMedio) {
		this.exibirDialogPrecoMedio = exibirDialogPrecoMedio;
	}
	
}
