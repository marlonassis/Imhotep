package br.com.imhotep.entidade.extra;

/**
 * Data: 05/09/2014
 * Funcionalidade: Relatório de média de consumo e previsão de estoque
 * JASPER: RelatorioMediaConsumoAlmoxarifado.jasper 
 */

import java.util.List;

//TODO Rel 2

public class MediaConsumoAlmoxarifado {
	private static final int MESES_PERIODO_CONSUMO = 6;
	private Integer idMaterialAlmoxarfiado;
	private Integer saldoAtual;
	private String descricao;
	private String sigla;
	private List<Integer> totalConsumoList;
	
	//MUDANCA #
	private List<Long> prevEstoque;
	
	public MediaConsumoAlmoxarifado(Integer idMaterialAlmoxarfiado,
			List<Integer> totalConsumoList, Integer saldoAtual, String descricao,
			String sigla, List<Long> prevEstoque) {
		super();
		this.idMaterialAlmoxarfiado = idMaterialAlmoxarfiado;
		this.saldoAtual = saldoAtual;
		this.descricao = descricao;
		this.sigla = sigla;
		this.setTotalConsumoList(totalConsumoList);
		this.setPrevEstoque(prevEstoque);
	}
	
	public String getDescricaoCompleta(){
		return getIdMaterialAlmoxarfiado() + " - ".concat(getDescricao()).concat(" - ").concat(getSigla());
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
	
	public Integer getIdMaterialAlmoxarfiado() {
		return idMaterialAlmoxarfiado;
	}
	public void setIdMaterialAlmoxarfiado(Integer idMaterialAlmoxarfiado) {
		this.idMaterialAlmoxarfiado = idMaterialAlmoxarfiado;
	}
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	//TODO MUDANCA 2: Calculo da media de consumo
	public Integer getMediaConsumo(){
		Integer total = 0;
		if(getTotalConsumoList() != null)
			for(Integer item : getTotalConsumoList()){
				total += item;
			}
		return (int) Math.round(total.doubleValue()/MESES_PERIODO_CONSUMO);
		
	}
	
	//TODO MUDANCA 2: Calculo da media de consumo
	public Integer getPrevisaoTermino(){
		Integer mediaConsumo = getMediaConsumo();
		Integer media = 0;
		if(mediaConsumo != 0)
			media = (int) Math.round( getSaldoAtual().doubleValue()/mediaConsumo );
		return media;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idMaterialAlmoxarfiado == null) ? 0
						: idMaterialAlmoxarfiado.hashCode());
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
		MediaConsumoAlmoxarifado other = (MediaConsumoAlmoxarifado) obj;
		if (idMaterialAlmoxarfiado == null) {
			if (other.idMaterialAlmoxarfiado != null)
				return false;
		} else if (!idMaterialAlmoxarfiado.equals(other.idMaterialAlmoxarfiado))
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
