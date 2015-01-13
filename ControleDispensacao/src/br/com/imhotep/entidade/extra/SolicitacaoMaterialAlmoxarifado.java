package br.com.imhotep.entidade.extra;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;

public class SolicitacaoMaterialAlmoxarifado {
	private SolicitacaoMaterialAlmoxarifadoUnidadeItem item;
	private Integer cota;
	private Double quantidadeRecebidaMes;
	private Double totalEstoque;
	private Map<EstoqueAlmoxarifado, Double> estoqueReservado = new HashMap<EstoqueAlmoxarifado, Double>(); 
	
	public Double quantidadeLiberada(){
		Set<EstoqueAlmoxarifado> keys = getEstoqueReservado().keySet();
		Double total = 0d;
		for(EstoqueAlmoxarifado key : keys){
			Double qtd = getEstoqueReservado().get(key);
			total += qtd == null ? 0d : qtd;
		}
		return total;
	}

	public boolean dispensadoEmParte(){
		int quantidadeSolicitada = getItem().getQuantidadeSolicitada() == null ? 0 : getItem().getQuantidadeSolicitada().intValue();
		return (quantidadeSolicitada > quantidadeLiberada());
	}
	
	public SolicitacaoMaterialAlmoxarifadoUnidadeItem getItem() {
		return item;
	}

	public void setItem(SolicitacaoMaterialAlmoxarifadoUnidadeItem item) {
		this.item = item;
	}

	public Map<EstoqueAlmoxarifado, Double> getEstoqueReservado() {
		return estoqueReservado;
	}

	public void setEstoqueReservado(Map<EstoqueAlmoxarifado, Double> estoqueReservado) {
		this.estoqueReservado = estoqueReservado;
	}

	public Integer getCota() {
		return cota;
	}

	public void setCota(Integer cota) {
		this.cota = cota;
	}

	public Double getTotalEstoque() {
		return totalEstoque;
	}

	public void setTotalEstoque(Double totalEstoque) {
		this.totalEstoque = totalEstoque;
	}

	public Double getQuantidadeRecebidaMes() {
		return quantidadeRecebidaMes;
	}

	public void setQuantidadeRecebidaMes(Double quantidadeRecebidaMes) {
		this.quantidadeRecebidaMes = quantidadeRecebidaMes;
	}
	
	public String getLotes(){
		String res = "";
		Set<EstoqueAlmoxarifado> keySet = getEstoqueReservado().keySet();
		for(EstoqueAlmoxarifado key : keySet){
			Double qtd = getEstoqueReservado().get(key);
			res += ((key.getLote() == null || key.getLote().isEmpty()) ? "<b>[Sem Lote]</b>" : "<b>"+key.getLote()+"</b>") + " - " + qtd + " <br/>";
		}
		return res;
	}
	
}
