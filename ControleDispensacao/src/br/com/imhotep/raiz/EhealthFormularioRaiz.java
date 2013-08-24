package br.com.imhotep.raiz;

import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EhealthFormulario;
import br.com.imhotep.entidade.EhealthFormularioRedeSocial;
import br.com.imhotep.entidade.EhealthFormularioTecnologia;
import br.com.imhotep.enums.TipoEhealthRedeSocialEnum;
import br.com.imhotep.enums.TipoEhealthTipoTecnologiaEnum;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EhealthFormularioRaiz extends PadraoHome<EhealthFormulario>{
	
	private List<EhealthFormularioRedeSocial> redesSociais;
	private List<EhealthFormularioTecnologia> tecnologias;
	private TipoEhealthRedeSocialEnum tipoRedeSocial;
	private TipoEhealthTipoTecnologiaEnum tipoTecnologia;
	private boolean exibeFormulario;
	
	public static EhealthFormularioRaiz getInstanciaAtual(){
		try {
			return (EhealthFormularioRaiz) Utilitarios.procuraInstancia(EhealthFormularioRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setExibeFormulario(false);
	}
	
	public void exibeFormulario(){
		setExibeFormulario(true);
	}
	
	@Override
	protected void preEnvio() {
		super.getInstancia().setEhealthEstabelecimento(EhealthEstabelecimentoRaiz.getInstanciaAtual().getInstancia());
		super.preEnvio();
	}
	
	public void iniciarPreenchimento(){
		Integer idEhealthEstabelecimento = EhealthEstabelecimentoRaiz.getInstanciaAtual().getInstancia().getIdEhealthEstabelecimento();
		List<EhealthFormulario> busca = super.getBusca("select o from EhealthFormulario o where o.ehealthEstabelecimento.idEhealthEstabelecimento="+idEhealthEstabelecimento);
		if(busca != null && !busca.isEmpty())
			setInstancia(busca.get(0));
		setExibeFormulario(true);
	}
	
	public void addRedeSocial(){
		EhealthFormularioRedeSocial efrs = new EhealthFormularioRedeSocial();
		efrs.setEhealthFormulario(getInstancia());
		efrs.setTipoRedeSocial(getTipoRedeSocial());
		if(new EhealthFormularioRedeSocialRaiz(efrs).enviar()){
			if(getInstancia().getRedesSociais() == null)
				getInstancia().setRedesSociais(new HashSet<EhealthFormularioRedeSocial>());
			
			getInstancia().getRedesSociais().add(efrs);
			setTipoRedeSocial(null);
		}
	}
	
	public void addTecnologia(){
		EhealthFormularioTecnologia eft = new EhealthFormularioTecnologia();
		eft.setEhealthFormulario(getInstancia());
		eft.setTipoTecnologia(getTipoTecnologia());
		if(new EhealthFormularioTecnologiaRaiz(eft).enviar()){
			if(getInstancia().getTecnologias() == null)
				getInstancia().setTecnologias(new HashSet<EhealthFormularioTecnologia>());

			getInstancia().getTecnologias().add(eft);
			setTipoTecnologia(null);
		}
	}
	
	public void remRedeSocial(EhealthFormularioRedeSocial efrs){
		if(new EhealthFormularioRedeSocialRaiz(efrs).apagar()){
			getInstancia().getRedesSociais().remove(efrs);
			setTipoRedeSocial(null);
		}
	}
	
	public void remTecnologia(EhealthFormularioTecnologia eft){
		if(new EhealthFormularioTecnologiaRaiz(eft).apagar()){
			getInstancia().getTecnologias().remove(eft);
			setTipoTecnologia(null);
		}
	}
	
	public List<EhealthFormularioRedeSocial> getRedesSociais() {
		return redesSociais;
	}
	public void setRedesSociais(List<EhealthFormularioRedeSocial> redesSociais) {
		this.redesSociais = redesSociais;
	}
	public List<EhealthFormularioTecnologia> getTecnologias() {
		return tecnologias;
	}
	public void setTecnologias(List<EhealthFormularioTecnologia> tecnologias) {
		this.tecnologias = tecnologias;
	}
	public TipoEhealthRedeSocialEnum getTipoRedeSocial() {
		return tipoRedeSocial;
	}
	public void setTipoRedeSocial(TipoEhealthRedeSocialEnum tipoRedeSocial) {
		this.tipoRedeSocial = tipoRedeSocial;
	}
	public TipoEhealthTipoTecnologiaEnum getTipoTecnologia() {
		return tipoTecnologia;
	}
	public void setTipoTecnologia(TipoEhealthTipoTecnologiaEnum tipoTecnologia) {
		this.tipoTecnologia = tipoTecnologia;
	}

	public boolean getExibeFormulario() {
		return exibeFormulario;
	}

	public void setExibeFormulario(boolean exibeFormulario) {
		this.exibeFormulario = exibeFormulario;
	}
	
}
