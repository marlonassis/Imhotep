package br.com.imhotep.entidade.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.imhotep.comparador.MovimentacoesMaterialAlmoxarifadoGrupoMaterialMovimentosComparador;

public class MovimentacoesGrupoAlmoxarifadoGrupoMaterial {
	private Integer idMaterial;
	private String material;
	private Integer consumo;
	private Integer saldoEstoque;
	private List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> movimentacoes = new ArrayList<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes>();
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterial(){
		super();
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterial(String material){
		super();
		this.material = material;
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterial(Integer idMaterial, String material, Integer consumo, Integer saldoEstoque, List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> movimentacoes){
		super();
		this.idMaterial = idMaterial;
		this.material = material;
		this.consumo = consumo;
		this.saldoEstoque = saldoEstoque;
		this.movimentacoes = movimentacoes;
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterial(MovimentacoesGrupoAlmoxarifadoGrupoMaterial obj, List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> movimentacoes){
		super();
		this.idMaterial = obj.getIdMaterial();
		this.material = obj.getMaterial();
		this.consumo = obj.getConsumo();
		this.saldoEstoque = obj.getSaldoEstoque();
		this.movimentacoes = obj.getMovimentacoes();
	}
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}

	public List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> getMovimentacoes() {
		if(movimentacoes != null){
			Collections.sort(movimentacoes, new MovimentacoesMaterialAlmoxarifadoGrupoMaterialMovimentosComparador());
		}
		return movimentacoes;
	}

	public void setMovimentacoes(List<MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes> movimentacoes) {
		this.movimentacoes = movimentacoes;
	}

	public Integer getConsumo() {
		return consumo;
	}

	public void setConsumo(Integer consumo) {
		this.consumo = consumo;
	}

	public Integer getSaldoEstoque() {
		return saldoEstoque;
	}

	public void setSaldoEstoque(Integer saldoEstoque) {
		this.saldoEstoque = saldoEstoque;
	}

	public Integer getIdMaterial() {
		return idMaterial;
	}

	public void setIdMaterial(Integer idMaterial) {
		this.idMaterial = idMaterial;
	}

	public String getMaterialId(){
		if(getIdMaterial() != null && getMaterial() != null){
			return getIdMaterial() + " - " + getMaterial();
		}
		return "";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idMaterial == null) ? 0 : idMaterial.hashCode());
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
		MovimentacoesGrupoAlmoxarifadoGrupoMaterial other = (MovimentacoesGrupoAlmoxarifadoGrupoMaterial) obj;
		if (idMaterial == null) {
			if (other.idMaterial != null)
				return false;
		} else if (!idMaterial.equals(other.idMaterial))
			return false;
		return true;
	}
	
	
}
