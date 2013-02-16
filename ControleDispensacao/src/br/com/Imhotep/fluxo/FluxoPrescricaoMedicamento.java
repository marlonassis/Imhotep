package br.com.Imhotep.fluxo;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.Imhotep.comparador.DoseDataComparador;
import br.com.Imhotep.controle.ControlePrescricaoItem;
import br.com.Imhotep.controle.ControlePrescricaoItemDose;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidadeExtra.Dose;
import br.com.Imhotep.enums.TipoViaAdministracaoMedicamentoEnum;
import br.com.Imhotep.raiz.EstoqueRaiz;
import br.com.Imhotep.raiz.PrescricaoRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

public class FluxoPrescricaoMedicamento extends PadraoFluxo{
	
	private Prescricao prescricaoAtual = PrescricaoRaiz.getInstanciaHome().getPrescricaoAtual();
	
	public List<PrescricaoItem> getItensPrescricao(){
		return itensPrescricao(prescricaoAtual);
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseEscolhidoList() {
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(prescricaoAtual);
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}else{
			return null;
		}
	}
	
	private List<PrescricaoItem> itensPrescricao(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	public boolean inserirItem(Dose dose, Prescricao prescricao){
			if(!formularioDoseVazio(dose) && liberaDose(dose.getPrescricaoItem().getMaterial(), dose)){
				dose.getPrescricaoItem().setPrescricao(prescricao);
				if(gravaPrescricaoItem(dose.getPrescricaoItem())){
					return gravaDose(dose);
				}
			}
			return false;
	}
	
	public boolean inserirDose(Dose dose){
		if(!formularioDoseVazio(dose) && liberaDose(dose.getPrescricaoItem().getMaterial(), dose)){
			return gravaDose(dose);
		}
		return false;
	}
	
	private boolean gravaDose(Dose dose){
		if(!new ControlePrescricaoItemDose().gravaPrescricaoItemDose(dose)){
			super.mensagem("Ocorrreu erro ao gravar a dose.", "", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private boolean gravaPrescricaoItem(PrescricaoItem pi){
		if(!new ControlePrescricaoItem().gravaPrescricaoItem(pi)){
			super.mensagem("Ocorrreu erro ao gravar a prescrição item.", "", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private boolean liberaDose(Material material, Dose dose) {
		EstoqueRaiz eh = new EstoqueRaiz();
		Object[] totais = eh.consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !eh.estoqueVazio(estoqueAtual) && !eh.estoqueInsuficiente(estoqueAtual, dose.getQuantidadeDoses(), dose.getQuantidadePorDose());
	}
	
	private boolean formularioDoseVazio(Dose dose){
		if(dose.getDataInicio().before(Calendar.getInstance().getTime())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "A data de início da dose deve ser maior que a data atual.", ""));
			return true;
		}
		
		if(dose.getPrescricaoItem().getMaterial() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o material.", ""));
			return true;
		}
		if(dose.getQuantidadeDoses() == null || dose.getQuantidadeDoses() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a quantidade de doses diárias.", ""));
			return true;
		}
		if(dose.getQuantidadePorDose() == null || dose.getQuantidadePorDose() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a quantidade do medicamento por dose.", ""));
			return true;
		}
		if(dose.getIntervaloEntreDoses() == null || dose.getIntervaloEntreDoses() == 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o intervalo de tempo entre as doses.", ""));
			return true;
		}
		if(dose.getDataInicio() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a hora de início da dosagem.", ""));
			return true;
		}
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe o tipo da via que o medicamento deve ser adminsitrado.", ""));
			return true;
		}
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() != null && dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento().equals(TipoViaAdministracaoMedicamentoEnum.OT) && dose.getPrescricaoItem().getOutraVia() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informe a outra via de administração do medicamento.", ""));
			return true;
		}
		
		return false;
	}
	
	public List<PrescricaoItemDose> getPrescricaoItemDoseList(){
			return buscaDoses(prescricaoAtual);
	}

	public List<PrescricaoItemDose> getPrescricaoItemDoseSCHIList(PrescricaoItem prescricaoItem){
		return buscaDosesPrescricaoItem(prescricaoItem);
	}
	
	private List<PrescricaoItemDose> buscaDosesPrescricaoItem(PrescricaoItem prescricaoItem) {
		List<PrescricaoItemDose> pidList = getListaDosePrescricaoItem(prescricaoItem);
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}
		return null;
	}
	
	private List<PrescricaoItemDose> buscaDoses(Prescricao prescricao) {
		List<PrescricaoItemDose> pidList = getListaPrescricaoItemDose(prescricao);
		if(pidList != null){
			Collections.sort(pidList, new DoseDataComparador());
			return pidList;
		}
		return null;
	}
	
	private List<PrescricaoItemDose> getListaPrescricaoItemDose(Prescricao prescricao) {
		if(prescricao != null){
			ConsultaGeral<PrescricaoItemDose> cg = new ConsultaGeral<PrescricaoItemDose>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			return (List<PrescricaoItemDose>) cg.consulta(new StringBuilder("select o from PrescricaoItemDose o where o.prescricaoItem.prescricao.idPrescricao = :idPrescricao"), hm);
		}
		return null;
	}
	
	private List<PrescricaoItemDose> getListaDosePrescricaoItem(PrescricaoItem prescricaoItem) {
		if(prescricaoItem != null){
			ConsultaGeral<PrescricaoItemDose> cg = new ConsultaGeral<PrescricaoItemDose>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricaoItem", prescricaoItem.getIdPrescricaoItem());
			return (List<PrescricaoItemDose>) cg.consulta(new StringBuilder("select o from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrescricaoItem"), hm);
		}
		return null;
	}
	
	public Long quantidadeDosesPrescricaoItem(PrescricaoItem prescricaoItem){
		return getQuantidadeDosePrescricaoItem(prescricaoItem);
	}
	
	private Long getQuantidadeDosePrescricaoItem(PrescricaoItem prescricaoItem) {
		if(prescricaoItem != null){
			ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricaoItem", prescricaoItem.getIdPrescricaoItem());
			return cg.consultaUnica(new StringBuilder("select count(o.idPrescricaoItemDose) from PrescricaoItemDose o where o.prescricaoItem.idPrescricaoItem = :idPrescricaoItem"), hm);
		}
		return null;
	}
}
