package br.com.imhotep.entidade.extra;

/**
 * Data: 05/09/2014
 * Funcionalidade: Relatório de média de consumo e previsão de estoque
 * JASPER: RelatorioMediaConsumoFarmacia.jasper 
 */

import java.util.List;

public class MediaConsumoFarmacia {
	private static final int MESES_PERIODO_CONSUMO = 6;
	private String idMaterial;
	private Integer saldoAtual;
	private String descricao;
	private String sigla;
	private List<Integer> totalConsumoList;
	
	//MUDANCA #
	private List<Long> prevEstoque;
	
	public MediaConsumoFarmacia(String codMaterial,
			List<Integer> totalConsumoList, Integer saldoAtual, String descricao,
			String sigla, List<Long> prevEstoque) {
		super();
		this.idMaterial = codMaterial;
		this.saldoAtual = saldoAtual;
		this.descricao = descricao;
		this.sigla = sigla;
		this.setTotalConsumoList(totalConsumoList);
		this.setPrevEstoque(prevEstoque);
	}
	
	public String getDescricaoCompleta(){
		return getIdMaterial() + " - ".concat(getDescricao()).concat(" - ").concat(getSigla());
	}
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public Integer getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(Integer saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	
	public String getIdMaterial() {
		return idMaterial;
	}
	public void setIdMaterial(String idMaterial) {
		this.idMaterial = idMaterial;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getMediaConsumo(){
		Integer total = 0;
		if(getTotalConsumoList() != null)
			for(Integer item : getTotalConsumoList()){
				total += item;
			}
		return total/MESES_PERIODO_CONSUMO;
	}
	
	public Integer getPrevisaoTermino(){
		Integer mediaConsumo = getMediaConsumo();
		Integer media = 0;
		if(mediaConsumo != 0)
			media = getSaldoAtual() / mediaConsumo;
		return media;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idMaterial == null) ? 0
						: idMaterial.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaConsumoFarmacia other = (MediaConsumoFarmacia) obj;
		if (idMaterial == null) {
			if (other.idMaterial != null)
				return false;
		} else if (!idMaterial.equals(other.idMaterial))
			return false;
		return true;
	}

	public List<Integer> getTotalConsumoList() {
		return totalConsumoList;
	}

	public void setTotalConsumoList(List<Integer> totalConsumoList) {
		this.totalConsumoList = totalConsumoList;
	}
	
	public List<Long> getPrevEstoque() {
		return prevEstoque;
	}

	public void setPrevEstoque(List<Long> prevEstoque) {
		this.prevEstoque = prevEstoque;
	}
	
}
