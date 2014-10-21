package br.com.imhotep.entidade.extra;

import java.util.List;

public class ConsumoAnualAlmoxarifado {
	private Integer idMaterialAlmoxarfiado;
	private String descricao;
	private String sigla;
	private List<String> totalEntradaConsumoList;
	
	public ConsumoAnualAlmoxarifado(Integer idMaterialAlmoxarfiado,
			List<String> totalEntradaConsumoList, String descricao, String sigla) {
		super();
		this.idMaterialAlmoxarfiado = idMaterialAlmoxarfiado;
		this.descricao = descricao;
		this.sigla = sigla;
		this.setTotalEntradaConsumoList(totalEntradaConsumoList);
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
	
	public List<String> getTotalEntradaConsumoList() {
		return totalEntradaConsumoList;
	}
	
	public void setTotalEntradaConsumoList(List<String> totalEntradaConsumoList) {
		this.totalEntradaConsumoList = totalEntradaConsumoList;
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
		ConsumoAnualAlmoxarifado other = (ConsumoAnualAlmoxarifado) obj;
		if (idMaterialAlmoxarfiado == null) {
			if (other.idMaterialAlmoxarfiado != null)
				return false;
		} else if (!idMaterialAlmoxarfiado.equals(other.idMaterialAlmoxarfiado))
			return false;
		return true;
	}

}
