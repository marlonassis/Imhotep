package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EhealthEstabelecimento;
import br.com.imhotep.entidade.EhealthEstado;
import br.com.imhotep.entidade.EhealthFormulario;
import br.com.imhotep.entidade.EhealthFormularioRedeSocial;
import br.com.imhotep.entidade.EhealthFormularioTecnologia;
import br.com.imhotep.entidade.EhealthMunicipio;
import br.com.imhotep.entidade.EhealthPais;
import br.com.imhotep.enums.TipoEhealthRedeSocialEnum;
import br.com.imhotep.enums.TipoEhealthTipoTecnologiaEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EhealthEstabelecimentoRaiz extends PadraoRaiz<EhealthEstabelecimento>{
	
	private List<EhealthFormularioRedeSocial> redesSociais;
	private List<EhealthFormularioTecnologia> tecnologias;
	private TipoEhealthRedeSocialEnum tipoRedeSocial;
	private TipoEhealthTipoTecnologiaEnum tipoTecnologia;
	private EhealthPais pais;
	private EhealthEstado estado;
	private boolean exibeFormulario;
	private EhealthFormulario formulario = new EhealthFormulario();
	
	public EhealthEstabelecimentoRaiz(){
		super();
		setFormulario(new EhealthFormulario());
	}
	
	public static EhealthEstabelecimentoRaiz getInstanciaAtual(){
		try {
			return (EhealthEstabelecimentoRaiz) Utilitarios.procuraInstancia(EhealthEstabelecimentoRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void cadastrarFormulario(){
		try {
			getFormulario().setPesquisador(Autenticador.getProfissionalLogado());
			getFormulario().setDataCadastro(new Date());
			getFormulario().setEhealthEstabelecimento(getInstancia());
			getInstancia().setFormulario(getFormulario());
			super.enviarGenerico(getInstancia().getFormulario());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void atualizarFormulario(){
		getInstancia().setFormulario(getFormulario());
		super.atualizarGenerico(getInstancia().getFormulario());
	}
	
	@Override
	protected void preEnvio() {
		getInstancia().setDataCadastro(new Date());
		super.preEnvio();
	}
	
	@Override
	public void setInstancia(EhealthEstabelecimento instancia) {
		super.setInstancia(instancia);
		if(getFormulario() == null){
			setFormulario(new EhealthFormulario());
		}
	}
	
	public void pegarEstabelecimento(){
		try {
			getInstancia().setPesquisador(Autenticador.getProfissionalLogado());
			super.atualizar();
			super.novaInstancia();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public void rejeitarEstabelecimento(){
		if(getInstancia().getFormulario() == null || super.apagarGenerico(getInstancia().getFormulario())){
			getInstancia().setPesquisador(null);
			getInstancia().setFormulario(null);
			super.atualizar();
			super.novaInstancia();
		}
	}
	
	
	public void exibeFormulario(){
		setExibeFormulario(true);
		String sql = "select o from EhealthFormulario o where o.ehealthEstabelecimento.idEhealthEstabelecimento = " + getInstancia().getIdEhealthEstabelecimento();
		EhealthFormulario form = new ConsultaGeral<EhealthFormulario>().consultaUnica(new StringBuilder(sql));
		if(form != null && form.getIdEhealthFormulario() != 0){
			getInstancia().setFormulario(form);
			setFormulario(form);
		}
	}
	
	public void ocultarFormulario(){
		super.novaInstancia();
		setExibeFormulario(false);
		redesSociais = null;
		tecnologias = null;
		tipoRedeSocial = null;
		tipoTecnologia = null;
		setFormulario(new EhealthFormulario());
	}
	
	
	public List<EhealthMunicipio> getMunicipiosEstado(){
		if(getEstado() != null){
			String hql = "select o from EhealthMunicipio o where o.ehealthEstado.idEhealthEstado = " + getEstado().getIdEhealthEstado() + " order by o.nome";
			Collection<EhealthMunicipio> coll = new ConsultaGeral<EhealthMunicipio>(new StringBuilder(hql)).consulta();
			return new ArrayList<EhealthMunicipio>(coll);
		}
		return new ArrayList<EhealthMunicipio>();
	}
	
	public List<EhealthEstado> getEstadosPais(){
		if(getPais() != null){
			String hql = "select o from EhealthEstado o where o.pais.idEhealthPais = " + getPais().getIdEhealthPais() + " order by o.nome";
			Collection<EhealthEstado> coll = new ConsultaGeral<EhealthEstado>(new StringBuilder(hql)).consulta();
			return new ArrayList<EhealthEstado>(coll);
		}
		return new ArrayList<EhealthEstado>();
	}
	
	public void addRedeSocial(){
		EhealthFormularioRedeSocial efrs = new EhealthFormularioRedeSocial();
		efrs.setEhealthFormulario(getInstancia().getFormulario());
		efrs.setTipoRedeSocial(getTipoRedeSocial());
		if(new EhealthFormularioRedeSocialRaiz(efrs).enviar()){
			if(getFormulario().getRedesSociais() == null)
				getFormulario().setRedesSociais(new HashSet<EhealthFormularioRedeSocial>());
			
			getFormulario().getRedesSociais().add(efrs);
			setTipoRedeSocial(null);
		}
	}
	
	public void addTecnologia(){
		EhealthFormularioTecnologia eft = new EhealthFormularioTecnologia();
		eft.setEhealthFormulario(getInstancia().getFormulario());
		eft.setTipoTecnologia(getTipoTecnologia());
		if(new EhealthFormularioTecnologiaRaiz(eft).enviar()){
			if(getFormulario().getTecnologias() == null)
				getFormulario().setTecnologias(new HashSet<EhealthFormularioTecnologia>());

			getFormulario().getTecnologias().add(eft);
			setTipoTecnologia(null);
		}
	}
	
	public void remRedeSocial(EhealthFormularioRedeSocial efrs){
		if(new EhealthFormularioRedeSocialRaiz(efrs).apagar()){
			getFormulario().getRedesSociais().remove(efrs);
			setTipoRedeSocial(null);
		}
	}
	
	public void remTecnologia(EhealthFormularioTecnologia eft){
		if(new EhealthFormularioTecnologiaRaiz(eft).apagar()){
			getFormulario().getTecnologias().remove(eft);
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

	public EhealthPais getPais() {
		return pais;
	}

	public void setPais(EhealthPais pais) {
		this.pais = pais;
	}

	public EhealthEstado getEstado() {
		return estado;
	}

	public void setEstado(EhealthEstado estado) {
		this.estado = estado;
	}

	public EhealthFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(EhealthFormulario formulario) {
		this.formulario = formulario;
	}
	
	
}
