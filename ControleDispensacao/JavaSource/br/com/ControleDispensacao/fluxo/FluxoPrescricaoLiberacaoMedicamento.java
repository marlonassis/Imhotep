package br.com.ControleDispensacao.fluxo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.auxiliar.Constantes;
import br.com.ControleDispensacao.auxiliar.ControleMedicacaoRestrito;
import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.entidade.ControleMedicacaoRestritoSCHI;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.negocio.PrescricaoHome;
import br.com.ControleDispensacao.negocio.PrescricaoItemHome;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

@ManagedBean(name="fluxoPrescricaoLiberacaoMedicamento")
@RequestScoped
public class FluxoPrescricaoLiberacaoMedicamento extends PadraoFluxo{

	private String fluxoAtualPrescricao = PrescricaoHome.getInstanciaHome().getFluxoAtualPrescricao();
	private Prescricao prescricaoAtual = PrescricaoHome.getInstanciaHome().getPrescricaoAtual();
	
	private ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI = new ControleMedicacaoRestritoSCHI();
	private String usuario;
	private String senha;
	
	public boolean isExisteAntibiotico(){
		for(PrescricaoItem item : prescricaoItemPendente(prescricaoAtual)){
			if(isMaterialAntibiotico(item.getMaterial())){
				return true;
			}
		}
		return false;
	}
	
	public void analiseLiberacao(){
		Profissional profissionalAutorizador = Autenticador.getInstancia().profissionalPeloNomeUsuario(getUsuario(), getSenha());
		if(profissionalAutorizador != null){
			analiseIndividualItensPrescritos(profissionalAutorizador);
		}
	}
	
	private void analiseIndividualItensPrescritos(Profissional profissionalAutorizador) {
		for(PrescricaoItem item : medicamentosPendentesLiberacao()){
			analiseTipoMaterial(profissionalAutorizador, item);
		}
	}
	
	private void analiseTipoMaterial(Profissional profissionalAutorizador, PrescricaoItem item) {
		if(isMaterialAntibiotico(item.getMaterial()) && Parametro.profissionalEnfermeiroMedico(profissionalAutorizador)){
			anexaAutorizacaoAtualizaItem(profissionalAutorizador, item);
		}else{
			AdicionaAutorizacaoProfissionalAutorizado(profissionalAutorizador, item);
		}
	}
	
	private void AdicionaAutorizacaoProfissionalAutorizado(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		if(verificaEspecialidadeValida(item.getMaterial(), profissionalPeloUsuario)){
			item.setProfissionalLiberacao(profissionalPeloUsuario);
			new PrescricaoItemHome(item, false).atualizar();
		}
	}
	
	private boolean verificaEspecialidadeValida(Material material, Profissional profissional){
		ConsultaGeral<Integer> cg = new ConsultaGeral<Integer>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
		hm.put("idEspecialidade", profissional.getEspecialidade().getIdEspecialidade());
		Integer idEspecialidade = (Integer) cg.consultaUnica(new StringBuilder("select a.idLiberaMaterialEspecialidade from LiberaMaterialEspecialidade a where a.material.idMaterial = :idMaterial and a.especialidade.idEspecialidade = :idEspecialidade"), hm);
		return idEspecialidade != null && idEspecialidade != 0;
	}
	
	private void anexaAutorizacaoAtualizaItem(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		anexaFormularioAntibioticoItem(profissionalPeloUsuario, item);
		new PrescricaoItemHome(item, false).atualizar();
	}
	
	private void anexaFormularioAntibioticoItem(Profissional profissionalPeloUsuario, PrescricaoItem item) {
		getControleMedicacaoRestritoSCHI().setProfissionalAssistente(profissionalPeloUsuario);
		getControleMedicacaoRestritoSCHI().setDataCriacaoAssistente(new Date());
		if(getControleMedicacaoRestritoSCHI().getIdControleMedicacaoRestritoSCHI() == 0){
			new ControleMedicacaoRestrito().gravaRestricao(getControleMedicacaoRestritoSCHI());
		}
		item.setControleMedicacaoRestritoSCHI(getControleMedicacaoRestritoSCHI());
	}
	
	public boolean isMaterialAntibiotico(Material material){
		if(material != null){
			String grupoMaterial = material.getFamilia().getSubGrupo().getGrupo().getDescricao();
			return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
		}
		return false;
	}
	
	public List<PrescricaoItem> medicamentosPendentesLiberacao(){
			return prescricaoItemPendente(prescricaoAtual);
	}
	
	private List<PrescricaoItem> prescricaoItemPendente(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			String selectAutorizacaoEspecialidade = "exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial) ";
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder("select o from PrescricaoItem o where o.prescricao.idPrescricao = :idPrescricao and (( "+selectAutorizacaoEspecialidade+" and profissionalLiberacao is null) or (lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO') and controleMedicacaoRestritoSCHI is null)) "), hm);
		}
		return null;
	}
	
	public String especialidadesLiberamMaterial(Material material){
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hm = new HashMap<Object, Object>();
		hm.put("idMaterial", material.getIdMaterial());
		List<String> especialidades = (List<String>) cg.consulta(new StringBuilder("select o.especialidade.descricao from LiberaMaterialEspecialidade o where o.material.idMaterial = :idMaterial"), hm);
		
		if(especialidades.isEmpty()){
			return "Todos";
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ControleMedicacaoRestritoSCHI getControleMedicacaoRestritoSCHI() {
		return controleMedicacaoRestritoSCHI;
	}

	public void setControleMedicacaoRestritoSCHI(
			ControleMedicacaoRestritoSCHI controleMedicacaoRestritoSCHI) {
		this.controleMedicacaoRestritoSCHI = controleMedicacaoRestritoSCHI;
	}
}
