package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.imhotep.comparador.MateriaisAlmoxarifadoGrupoComparador;
import br.com.imhotep.comparador.MateriaisAlmoxarifadoGrupoSubGrupoComparador;
import br.com.imhotep.comparador.MaterialAlmoxarifadoComparador;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupo;
import br.com.imhotep.entidade.extra.MateriaisAlmoxarifadoGrupoSubGrupo;
import br.com.remendo.ConsultaGeral;

public class MateriaisAlmoxarifadoGrupoConsultaRaiz extends ConsultaGeral<MateriaisAlmoxarifadoGrupo> {
	public List<MateriaisAlmoxarifadoGrupo> consultaGeralMateriais(){
		String sql = "select o from MaterialAlmoxarifado o order by o.grupoAlmoxarifado.descricao";
		Collection<MaterialAlmoxarifado> materiais = new ConsultaGeral<MaterialAlmoxarifado>().consulta(new StringBuilder(sql), null);
		List<MateriaisAlmoxarifadoGrupo> lista = construirLista(materiais);
		return lista;
	}
	
	private List<MateriaisAlmoxarifadoGrupo> construirLista(Collection<MaterialAlmoxarifado> materiais) {
		List<MaterialAlmoxarifado> lista = new ArrayList<MaterialAlmoxarifado>(materiais);
		MateriaisAlmoxarifadoGrupo mag = new MateriaisAlmoxarifadoGrupo();
		List<MateriaisAlmoxarifadoGrupo> ret = new ArrayList<MateriaisAlmoxarifadoGrupo>();
		GrupoAlmoxarifado grupoAlmoxarifadoTemp = materiais.iterator().next().getGrupoAlmoxarifado();
		Map<GrupoAlmoxarifado, List<MaterialAlmoxarifado>> mapGrupos = new HashMap<GrupoAlmoxarifado, List<MaterialAlmoxarifado>>();
		Map<SubGrupoAlmoxarifado, List<MaterialAlmoxarifado>> mapSubGrupos = new HashMap<SubGrupoAlmoxarifado, List<MaterialAlmoxarifado>>();
		
		for(MaterialAlmoxarifado ma : lista){
			
			if(grupoAlmoxarifadoTemp.getIdGrupoAlmoxarifado() != ma.getGrupoAlmoxarifado().getIdGrupoAlmoxarifado()){
				mag.setGrupoAlmoxarifado(grupoAlmoxarifadoTemp);
				mag.setMateriais(mapGrupos.get(grupoAlmoxarifadoTemp));
				addSubGrupos(mag, mapSubGrupos);
				ret.add(mag);
				mag = new MateriaisAlmoxarifadoGrupo();
				mapGrupos = new HashMap<GrupoAlmoxarifado, List<MaterialAlmoxarifado>>();
				mapSubGrupos = new HashMap<SubGrupoAlmoxarifado, List<MaterialAlmoxarifado>>();
				grupoAlmoxarifadoTemp = ma.getGrupoAlmoxarifado();
			}
			
			if(!mapGrupos.containsKey(ma.getGrupoAlmoxarifado())){
				mapGrupos.put(ma.getGrupoAlmoxarifado(), new ArrayList<MaterialAlmoxarifado>());
			}
				
			SubGrupoAlmoxarifado subGrupoAlmoxarifado = ma.getSubGrupoAlmoxarifado();
			if(subGrupoAlmoxarifado != null){
				if(mapSubGrupos.containsKey(subGrupoAlmoxarifado))
					mapSubGrupos.get(subGrupoAlmoxarifado).add(ma);
				else{
					mapSubGrupos.put(subGrupoAlmoxarifado, new ArrayList<MaterialAlmoxarifado>());
					mapSubGrupos.get(subGrupoAlmoxarifado).add(ma);
				}
			}else{
				mapGrupos.get(ma.getGrupoAlmoxarifado()).add(ma);
			}
			
		}
		
		mag.setGrupoAlmoxarifado(grupoAlmoxarifadoTemp);
		mag.setMateriais(mapGrupos.get(grupoAlmoxarifadoTemp));
		addSubGrupos(mag, mapSubGrupos);
		ret.add(mag);
		ordenarResultado(ret);
		return ret;
	}

	private void addSubGrupos(MateriaisAlmoxarifadoGrupo mag,
			Map<SubGrupoAlmoxarifado, List<MaterialAlmoxarifado>> mapSubGrupos) {
		List<MateriaisAlmoxarifadoGrupoSubGrupo> subGrupos = new ArrayList<MateriaisAlmoxarifadoGrupoSubGrupo>();
		Set<SubGrupoAlmoxarifado> keysSubGrupo = mapSubGrupos.keySet();
		for(SubGrupoAlmoxarifado sa : keysSubGrupo){
			MateriaisAlmoxarifadoGrupoSubGrupo tempSB = new MateriaisAlmoxarifadoGrupoSubGrupo();
			tempSB.setSubGrupoAlmoxarifado(sa);
			tempSB.setMateriais(mapSubGrupos.get(sa));
			subGrupos.add(tempSB);
		}
		
		mag.setSubGrupos(subGrupos);
	}

	private void ordenarResultado(List<MateriaisAlmoxarifadoGrupo> ret) {
		Collections.sort(ret, new MateriaisAlmoxarifadoGrupoComparador());
		for(MateriaisAlmoxarifadoGrupo obj : ret){
			if(obj.getMateriais() != null)
				Collections.sort(obj.getMateriais(), new MaterialAlmoxarifadoComparador());
			if(obj.getSubGrupos() != null){
				Collections.sort(obj.getSubGrupos(), new MateriaisAlmoxarifadoGrupoSubGrupoComparador());
				for(MateriaisAlmoxarifadoGrupoSubGrupo obj2 : obj.getSubGrupos()){
					if(obj2.getMateriais() != null)
						Collections.sort(obj2.getMateriais(), new MaterialAlmoxarifadoComparador());
				}
			}
		}
	}

}
