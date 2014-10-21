package br.com.imhotep.entidade.extra;

/**
 * Criado por Asclepíades Neto
 * Data: 05/09/2014
 * Funcionalidade: Relatório de materiais/medicamentos por unidade 
 * JASPER: RelatorioAlmoxarifadoUnidade.jasper   
 */

import java.util.ArrayList;
import java.util.List;
//TODO Rel 1
public class RelMaterialPorUnidade {
	private String unidade;
	private List<Itens> itens;
	
	
	public List<Itens> getItens() {
		return itens;
	}

	public RelMaterialPorUnidade(String unidade) {
		this.unidade = unidade;
		this.itens = new ArrayList<Itens>();
	}
	
	public void addItem(String material, String qtd_solicitada, String qtd_consumida,String grupo, String subgrupo, String codigo, String familia){
		Itens i = new Itens(material, qtd_solicitada, qtd_consumida, grupo, subgrupo, codigo, familia);
		itens.add(i);
	}
	
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	public class Itens{
		private String material;
		private String qtd_solicitada;
		private String qtd_consumida;
		private String grupo;
		private String subgrupo;
		private String codigo;
		private String familia;
		
		public Itens( String material, String qtd_solicitada, String qtd_consumida,String grupo, String subgrupo, String codigo, String familia ){
			this.material = material;
			this.qtd_solicitada = qtd_solicitada;
			this.qtd_consumida = qtd_consumida;
			this.grupo = grupo;
			this.subgrupo = subgrupo;
			this.codigo = codigo;
			this.familia = familia;
		}
		
		public String getQtd_solicitada() {
			return qtd_solicitada;
		}

		public void setQtd_solicitada(String qtd_solicitada) {
			this.qtd_solicitada = qtd_solicitada;
		}

		public String getQtd_consumida() {
			return qtd_consumida;
		}

		public void setQtd_consumida(String qtd_consumida) {
			this.qtd_consumida = qtd_consumida;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getMaterial() {
			return material;
		}
		public void setMaterial(String material) {
			this.material = material;
		}
		public String getGrupo() {
			return grupo;
		}
		public void setGrupo(String grupo) {
			this.grupo = grupo;
		}
		public String getSubgrupo() {
			return subgrupo;
		}
		public void setSubgrupo(String subgrupo) {
			this.subgrupo = subgrupo;
		}

		public String getFamilia() {
			return familia;
		}

		public void setFamilia(String familia) {
			this.familia = familia;
		}
	}
	
}
