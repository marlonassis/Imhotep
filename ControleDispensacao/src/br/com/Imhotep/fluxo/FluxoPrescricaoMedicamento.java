package br.com.Imhotep.fluxo;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.com.Imhotep.comparador.DoseDataComparador;
import br.com.Imhotep.controle.ControleEstoque;
import br.com.Imhotep.controle.ControlePrescricaoItem;
import br.com.Imhotep.controle.ControlePrescricaoItemDose;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidade.extra.Dose;
import br.com.Imhotep.enums.TipoViaAdministracaoMedicamentoEnum;
import br.com.Imhotep.raiz.PrescricaoRaiz;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItem;
import br.com.imhotep.excecoes.ExcecaoControlePrescricaoItemDose;
import br.com.imhotep.excecoes.ExcecaoEstoqueVazio;
import br.com.imhotep.excecoes.ExcecaoFormularioNaoPreenchido;
import br.com.imhotep.excecoes.ExcecaoSaldoInsuficienteEstoque;
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
	
	public void inserirItem(Dose dose, Prescricao prescricao) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoControlePrescricaoItem, ExcecaoControlePrescricaoItemDose{
		formularioDoseVazio(dose);
		liberaDose(dose.getPrescricaoItem().getMaterial(), dose);
		dose.getPrescricaoItem().setPrescricao(prescricao);
		gravaPrescricaoItem(dose.getPrescricaoItem());
		gravaDose(dose);
	}
	
	public void inserirDose(Dose dose) throws ExcecaoFormularioNaoPreenchido, ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque, ExcecaoControlePrescricaoItemDose{
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
	
	private void liberaDose(Material material, Dose dose) throws ExcecaoEstoqueVazio, ExcecaoSaldoInsuficienteEstoque{
		int quantidadeDose = dose.getQuantidadeDoses() * dose.getQuantidadePorDose();
		ControleEstoque ce = new ControleEstoque();
		ce.liberarDose(quantidadeDose, material);
	}
	
	private void formularioDoseVazio(Dose dose) throws ExcecaoFormularioNaoPreenchido {
		if(dose.getDataInicio().before(Calendar.getInstance().getTime()))
			throw new ExcecaoFormularioNaoPreenchido("A data de início da dose deve ser maior que a data atual.");
		
		if(dose.getPrescricaoItem().getMaterial() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe o material.");
		
		if(dose.getQuantidadeDoses() == null || dose.getQuantidadeDoses() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe a quantidade de doses diárias.");
		
		if(dose.getQuantidadePorDose() == null || dose.getQuantidadePorDose() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe a quantidade do medicamento por dose.");
		
		if(dose.getIntervaloEntreDoses() == null || dose.getIntervaloEntreDoses() == 0)
			throw new ExcecaoFormularioNaoPreenchido("Informe o intervalo de tempo entre as doses.");
		
		if(dose.getDataInicio() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe a hora de início da dosagem.");
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe o tipo da via que o medicamento deve ser adminsitrado.");
		
		if(dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento() != null && dose.getPrescricaoItem().getTipoViaAdministracaoMedicamento().equals(TipoViaAdministracaoMedicamentoEnum.OT) && dose.getPrescricaoItem().getOutraVia() == null)
			throw new ExcecaoFormularioNaoPreenchido("Informe a outra via de administração do medicamento.");
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
