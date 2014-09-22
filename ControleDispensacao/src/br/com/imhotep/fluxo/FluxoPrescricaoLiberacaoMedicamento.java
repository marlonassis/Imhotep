package br.com.imhotep.fluxo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.controle.ControleMedicacaoRestrito;
import br.com.imhotep.entidade.ControleMedicacaoRestritoSCHI;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.Paciente;
import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.raiz.PrescricaoItemRaiz;
import br.com.imhotep.raiz.PrescricaoRaiz;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

public class FluxoPrescricaoLiberacaoMedicamento extends PadraoFluxo{

	private Prescricao prescricaoAtual = PrescricaoRaiz.getInstanciaHome().getPrescricaoAtual();
	
	public List<ControleMedicacaoRestritoSCHI> controlesAtivos(Paciente paciente){
		return getControlesValidos(paciente);
	}
	
	private List<ControleMedicacaoRestritoSCHI> getControlesValidos(Paciente paciente){
		if(paciente != null && paciente.getIdPaciente() != 0){
			ConsultaGeral<ControleMedicacaoRestritoSCHI> cg = new ConsultaGeral<ControleMedicacaoRestritoSCHI>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("dataLimite", new Date());
			hm.put("idPaciente", paciente.getIdPaciente());
			String string = "select o from ControleMedicacaoRestritoSCHI o where o.dataLimite >= :dataLimite and " +
					"o.tipoPrescricaoInadequada is null " +
					"and o.profissionalInfectologista != null " +
					"and o.idControleMedicacaoRestritoSCHI in (select a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI from PrescricaoItem a where a.prescricao.paciente.idPaciente = :idPaciente)";
			return new ArrayList<ControleMedicacaoRestritoSCHI>(cg.consulta(new StringBuilder(string), hm));
		}
		return null;
	}
	// TODO criar exception personalizado
	public boolean analisarLiberacao(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI, String usuario, String senha){
		Profissional profissionalAutorizador = null;
		try {
			profissionalAutorizador = Autenticador.getInstancia().profissionalPeloNomeUsuario(usuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usu�rio atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em ControleMedicamentoRestrito");
		}
		
		if(profissionalAutorizador != null){
			if(Parametro.profissionalEnfermeiroMedico(profissionalAutorizador)){
				analiseIndividualItensPrescritos(controleMedicacaoRestritoSCHI, profissionalAutorizador);
			}else{
				super.mensagem("Informe algum profissional que seja m�dico ou enfermeiro.", null, FacesMessage.SEVERITY_WARN);
				return false;
			}
		}else{
			super.mensagem("Profissional n�o encontrado.", "Verifique se o usu�rio e senha est�o corretos.", FacesMessage.SEVERITY_WARN);
			return false;
		}
		return true;
	}
	
	private List<Material> itensLiberacaoAtual(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI){
		ConsultaGeral<Material> cg = new ConsultaGeral<Material>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idControleMedicacaoRestritoSCHI", controleMedicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI());
		return (List<Material>) cg.consulta(new StringBuilder("select a.material from PrescricaoItem a where a.controleMedicacaoRestritoSCHI.idControleMedicacaoRestritoSCHI = :idControleMedicacaoRestritoSCHI"), hm);
	}
	
	private void analiseIndividualItensPrescritos(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI, Profissional profissionalAutorizador) {
		List<PrescricaoItem> medicamentosPendentesLiberacao = getMedicamentosPendentesLiberacao();
		List<Material> medicamentosAutorizados = null;
		boolean controleCadastrado = controleMedicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI() != 0;
		if(controleCadastrado)
			medicamentosAutorizados = itensLiberacaoAtual(controleMedicacaoRestritoSCHI);
		
		for(PrescricaoItem item : medicamentosPendentesLiberacao){
			if(controleCadastrado)
				if(!medicamentosAutorizados.contains(item.getMaterial()))
					continue;
			analiseTipoMaterial(controleMedicacaoRestritoSCHI, profissionalAutorizador, item);
		}
	}
	
	private void analiseTipoMaterial(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI, Profissional profissionalAutorizador, PrescricaoItem item) {
		if(isMaterialAntibiotico(item.getMaterial())){
			anexaAutorizacaoAtualizaItem(controleMedicacaoRestritoSCHI, profissionalAutorizador, item);
		}else{
			adicionaAutorizacaoProfissionalAutorizado(profissionalAutorizador, item);
		}
	}
	
	private void adicionaAutorizacaoProfissionalAutorizado(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		if(verificaEspecialidadeValida(item.getMaterial(), profissionalPeloUsuario)){
			item.setProfissionalLiberacao(profissionalPeloUsuario);
			new PrescricaoItemRaiz(item, false).atualizar();
		}else{
			super.mensagem("O usu�rio informado n�o possue autoriza��o para liberar este material.", "", FacesMessage.SEVERITY_WARN);
		}
	}
	
	private boolean verificaEspecialidadeValida(Material material, Profissional profissional){
		ConsultaGeral<Integer> cg = new ConsultaGeral<Integer>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
//		hm.put("idEspecialidade", profissional.getEspecialidade().getIdEspecialidade());
		Integer idEspecialidade = (Integer) cg.consultaUnica(new StringBuilder("select a.idLiberaMaterialEspecialidade from LiberaMaterialEspecialidade a where a.material.idMaterial = :idMaterial and a.especialidade.idEspecialidade = :idEspecialidade"), hm);
//		return idEspecialidade != null && idEspecialidade != 0;
		return false;
	}
	
	private void anexaAutorizacaoAtualizaItem(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI, Profissional profissionalPeloUsuario, PrescricaoItem item) {
		anexaFormularioAntibioticoItem(controleMedicacaoRestritoSCHI, profissionalPeloUsuario, item);
		new PrescricaoItemRaiz(item, false).atualizar();
	}
	
	private void anexaFormularioAntibioticoItem(ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI, Profissional profissionalPeloUsuario, PrescricaoItem item) {
		controleMedicacaoRestritoSCHI.setProfissionalAssistente(profissionalPeloUsuario);
		controleMedicacaoRestritoSCHI.setDataCriacaoAssistente(new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, controleMedicacaoRestritoSCHI.getTempoUso());
		controleMedicacaoRestritoSCHI.setDataLimite(calendar.getTime());
		if(controleMedicacaoRestritoSCHI.getIdControleMedicacaoRestritoSCHI() == 0){
			new ControleMedicacaoRestrito().gravaRestricao(controleMedicacaoRestritoSCHI);
		}
		item.setControleMedicacaoRestritoSCHI(controleMedicacaoRestritoSCHI);
	}
	
	public boolean isMaterialAntibiotico(Material material){
		if(material != null){
			String grupoMaterial = material.getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
	}
	
	public List<PrescricaoItem> getMedicamentosPendentesLiberacao(){
			return prescricaoItemPendente(prescricaoAtual);
	}
	
	private List<PrescricaoItem> prescricaoItemPendente(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			String selectAutorizacaoEspecialidade = "exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial) ";
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao and (( "+selectAutorizacaoEspecialidade+" and o.profissionalLiberacao is null) or (lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBI�TICO') and o.controleMedicacaoRestritoSCHI is null)) "), hm);
		}
		return null;
	}
	
	public String especialidadesLiberamMaterial(Material material){
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
		List<String> especialidades = (List<String>) cg.consulta(new StringBuilder("select o.especialidade.descricao from LiberaMaterialEspecialidade o where o.material.idMaterial = :idMaterial"), hm);
		
		if(especialidades.isEmpty()){
			return "Todas as especialidades liberam.";
		}
		
		String ret = "";
		int cont = 0;
		for(String especialidade : especialidades){
			ret = ret.concat(especialidade);
			cont++;
			if(especialidades.size() > cont)
				ret = ret.concat(",");
		}
		return ret;
	}

}
