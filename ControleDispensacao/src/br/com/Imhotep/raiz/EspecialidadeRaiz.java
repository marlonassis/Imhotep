package br.com.Imhotep.raiz;

import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Especialidade;
import br.com.Imhotep.entidade.Material;
import br.com.remendo.PadraoHome;

@ManagedBean(name="especialidadeRaiz")
@SessionScoped
public class EspecialidadeRaiz extends PadraoHome<Especialidade>{

	public Collection<Especialidade> getListaEspecialidadeMaterial(Material material){
		return getBusca("select o.especialidade from LiberaMaterialEspecialidade o where o.material.idMaterial = "+material.getIdMaterial());
	}
	
	/**
	 * Método que retorna uma lista de Especialidade
	 * @param String sql
	 * @return Collection Especialidade
	 */
	public Collection<Especialidade> getListaEspecialidadeAutoComplete(String sql){
		return super.getBusca("select o from Especialidade as o where lower(to_ascii(o.descricao)) like lower(to_ascii('%"+sql+"%')) ");
	}

	public List<Especialidade> listaEspecialidadePorTipoConselho(Integer idTipoConselho){
		if(idTipoConselho != null){
			return super.getBusca("select o from Especialidade as o where o.tipoConselho.idTipoConselho = " + idTipoConselho + " order by o.descricao");
		}else{
			return super.getBusca("select o from Especialidade as o where o.tipoConselho is null order by o.descricao");
		}
	}
	
}
