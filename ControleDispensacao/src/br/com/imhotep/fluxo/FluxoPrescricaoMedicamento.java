package br.com.imhotep.fluxo;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.imhotep.comparador.DoseDataComparador;
import br.com.imhotep.controle.ControleEstoque;
import br.com.imhotep.controle.ControlePrescricaoItem;
import br.com.imhotep.controle.ControlePrescricaoItemDose;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.entidade.PrescricaoItemDose;
import br.com.imhotep.entidade.extra.Dose;
import br.com.imhotep.enums.TipoViaAdministracaoMedicamentoEnum;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItemDose;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoFormularioNaoPreenchido;
import br.com.imhotep.excecoes.ExcecaoReservaVazia;
import br.com.imhotep.raiz.PrescricaoRaiz;
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
	
	public void inserirItem(Dose dose, Prescricao prescricao) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItem, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia{
		formularioDoseVazio(dose);
		liberaDose(dose.getPrescricaoItem().getMaterial(), dose);
		dose.getPrescricaoItem().setPrescricao(prescricao);
		gravaPrescricaoItem(dose.getPrescricaoItem());
		gravaDose(dose);
	}
	
	public void inserirDose(Dose dose) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoControlePrescricaoItemDose, ExcecaoReservaVazia{
		formularioDoseVazio(dose);
		liberaDose(dose.getPrescricaoItem().getMaterial(), dose);
		gravaDose(dose);
	}
	
	private void gravaDose(Dose dose) throws ExcecaoControlePrescricaoItemDose{
		new ControlePrescricaoItemDose().gravaPrescricaoItemDose(dose);
	}
	
	private void gravaPrescricaoItem(PrescricaoItem pi) throws ExcecaoControlePrescricaoItem{
		new ControlePrescricaoItem().gravaPrescricaoItem(pi);
	}
	
	private void liberaDose(Material material, Dose dose) throws ExcecaoEstoqueVazio, ExcecaoEstoqueSaldoInsuficiente, ExcecaoReservaVazia{
		int quantidadeDose = dose.getQuantidadeDoses() * dose.getQuantidadePorDose();
		ControleEstoque ce = new ControleEstoque();
		ce.liberarReserva(quantidadeDose, material);
	}
	
	private void formularioDoseVazio(Dose dose) throws ExcecaoFormularioNaoPreenchido {
		if(dose.getDataInicio().before(Calendar.getInstance().getTime()))
			throw new ExcecaoFormularioNaoPreenchido("A data de in�cio da dose deve ser maior que a data atual.");
		
		if(dose.getPrescricaoItem().getMaterial() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe o material.");
		
		if(dose.getQuantidadeDoses() == null || dose.getQuantidadeDoses() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe a quantidade de doses di�rias.");
		
		if(dose.getQuantidadePorDose() == null || dose.getQuantidadePorDose() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe a quantidade do medicamento por dose.");
		
		if(dose.getIntervaloEntreDoses() == null || dose.getIntervaloEntreDoses() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe o intervalo de tempo entre as doses.");
		
		if(dose.getDataInicio() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe a hora de in�cio da dosagem.");
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe o tipo da via que o medicamento deve ser adminsitrado.");
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() != null && dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento().equals(TipoViaAdministracaoMedicamentoEnum.OT) && dose.getPrescricaoItem().getOutraVia() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe a outra via de administra��o do medicamento.");
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
