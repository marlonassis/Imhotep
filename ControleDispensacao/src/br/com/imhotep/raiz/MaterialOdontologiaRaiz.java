package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.SubGrupoOdontologiaConsultaRaiz;
import br.com.imhotep.entidade.MaterialOdontologia;
import br.com.imhotep.entidade.SubGrupoOdontologia;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialOdontologiaRaiz extends PadraoRaiz<MaterialOdontologia>{
	private List<SubGrupoOdontologia> subGrupoOdontologiaList;

	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInclusao(Autenticador.getProfissionalLogado());
			getInstancia().setDataInclusao(new Date());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setSubGrupoOdontologiaList(new ArrayList<SubGrupoOdontologia>());
	}
	
	@Override
	public boolean atualizar() {
		if(getSubGrupoOdontologiaList() == null || getSubGrupoOdontologiaList().isEmpty()){
			getInstancia().setSubGrupoOdontologia(null);
		}
		return super.atualizar();
	}
	
	@Override
	public void setInstancia(MaterialOdontologia instancia) {
		super.setInstancia(instancia);
		atualizaSubGrupoAmoxarifado();
	}
	
	public void atualizaSubGrupoAmoxarifado(){
		if(getInstancia().getGrupoOdontologia() != null){
			setSubGrupoOdontologiaList(new SubGrupoOdontologiaConsultaRaiz().consultarSubGrupoGrupo(getInstancia().getGrupoOdontologia().getIdGrupoOdontologia()));
			if(getSubGrupoOdontologiaList() == null || getSubGrupoOdontologiaList().isEmpty()){
				getInstancia().setSubGrupoOdontologia(null);
			}
		}
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoOdontologiaList() == null || getSubGrupoOdontologiaList().isEmpty())
			return false;
		return true;
	}
	
	public List<SubGrupoOdontologia> getSubGrupoOdontologiaList() {
		return subGrupoOdontologiaList;
	}

	public void setSubGrupoOdontologiaList(List<SubGrupoOdontologia> subGrupoOdontologiaList) {
		this.subGrupoOdontologiaList = subGrupoOdontologiaList;
	}

	
}
