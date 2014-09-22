package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.controle.ControleInstancia;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialEstoqueRaiz extends PadraoRaiz<EstoqueAlmoxarifado>{

	private List<EstoqueAlmoxarifado> estoquesEncontrados = new ArrayList<EstoqueAlmoxarifado>();
	private MaterialAlmoxarifado materialAlmoxarifado;
	
	public static MaterialEstoqueRaiz getInstanciaRaiz() {
		try {
			return (MaterialEstoqueRaiz) new ControleInstancia().procuraInstancia(MaterialEstoqueRaiz.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void buscarEstoques(){
		if(getMaterialAlmoxarifado() != null){
			String hql = "select o from EstoqueAlmoxarifado o where o.materialAlmoxarifado.idMaterialAlmoxarifado = " + getMaterialAlmoxarifado().getIdMaterialAlmoxarifado();
			Collection<EstoqueAlmoxarifado> consulta = new ConsultaGeral<EstoqueAlmoxarifado>(new StringBuilder(hql)).consulta();
			setEstoquesEncontrados(new ArrayList<EstoqueAlmoxarifado>(consulta));
		}
	}

	public void novaBusca(){
		estoquesEncontrados = new ArrayList<EstoqueAlmoxarifado>();
		materialAlmoxarifado = new MaterialAlmoxarifado();
	}
	
	public List<EstoqueAlmoxarifado> getEstoquesEncontrados() {
		return estoquesEncontrados;
	}

	public void setEstoquesEncontrados(List<EstoqueAlmoxarifado> estoquesEncontrados) {
		this.estoquesEncontrados = estoquesEncontrados;
	}

	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}

	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
	

}
