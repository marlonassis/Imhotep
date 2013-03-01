package br.com.Imhotep.raiz;

import java.util.ArrayList;
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
	
	public void camelCase(){
		List<Especialidade> list = new ArrayList<Especialidade>(getBusca("select o from Especialidade o where o.idEspecialidade > 54"));
		int idEspecialidade = 55, cont = 0;
		int size = list.size();
		while(cont < size){
			Especialidade obj = list.get(cont);
			String esp = obj.getDescricao();
//			esp = esp.toLowerCase();
			
			char[] charArray = esp.toCharArray();
			int i = 1;
			charArray[0] = String.valueOf(charArray[0]).toUpperCase().charAt(0);
			for(char c : charArray){
				if((int)c==32 || c==94 || c==45){
					charArray[i] = String.valueOf(charArray[i]).toUpperCase().charAt(0);
				}else{
					if(i < charArray.length)
						charArray[i] = String.valueOf(charArray[i]).toLowerCase().charAt(0);
				}
				i++;
			}
			
			int idAntigo = obj.getIdEspecialidade();
//			obj.setIdEspecialidade(idEspecialidade);
			obj.setDescricao(String.valueOf(charArray));
			
			
			super.setInstancia(obj);
			super.atualizar();
			
//			String update = "update Especialidade set descricao = '"+String.valueOf(charArray)+"' where idEspecialidade ="+idAntigo;
//			super.executa(update);

			idEspecialidade++;
			cont++;
		}
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
