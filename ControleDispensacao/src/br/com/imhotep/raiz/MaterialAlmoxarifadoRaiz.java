package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.SubGrupoAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialAlmoxarifadoRaiz extends PadraoRaiz<MaterialAlmoxarifado>{
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;

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
		setSubGrupoAlmoxarifadoList(new ArrayList<SubGrupoAlmoxarifado>());
	}
	
	@Override
	public boolean atualizar() {
		if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty()){
			getInstancia().setSubGrupoAlmoxarifado(null);
		}
		return super.atualizar();
	}
	
	@Override
	public void setInstancia(MaterialAlmoxarifado instancia) {
		super.setInstancia(instancia);
		atualizaSubGrupoAmoxarifado();
	}
	
	public void atualizaSubGrupoAmoxarifado(){
		if(getInstancia().getGrupoAlmoxarifado() != null){
			setSubGrupoAlmoxarifadoList(new SubGrupoAlmoxarifadoConsultaRaiz().consultarSubGrupoGrupo(getInstancia().getGrupoAlmoxarifado().getIdGrupoAlmoxarifado()));
			if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty()){
				getInstancia().setSubGrupoAlmoxarifado(null);
			}
		}
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty())
			return false;
		return true;
	}
	
	public List<SubGrupoAlmoxarifado> getSubGrupoAlmoxarifadoList() {
		return subGrupoAlmoxarifadoList;
	}

	public void setSubGrupoAlmoxarifadoList(List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList) {
		this.subGrupoAlmoxarifadoList = subGrupoAlmoxarifadoList;
	}

	
}
