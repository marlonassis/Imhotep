package br.com.imhotep.entidade.extra;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;

public class SolicitacaoMaterialAlmoxarifado {
	private SolicitacaoMaterialAlmoxarifadoUnidadeItem item;
	private Integer cota;
	private Integer quantidadeRecebidaMes;
	private Integer totalEstoque;
	private Map<EstoqueAlmoxarifado, Integer> estoqueReservado = new HashMap<EstoqueAlmoxarifado, Integer>(); 
	
	public Integer quantidadeLiberada(){
		Set<EstoqueAlmoxarifado> keys = getEstoqueReservado().keySet();
		Integer total = 0;
		for(EstoqueAlmoxarifado key : keys){
			Integer qtd = getEstoqueReservado().get(key);
			total += qtd == null ? 0 : qtd;
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

	public Map<EstoqueAlmoxarifado, Integer> getEstoqueReservado() {
		return estoqueReservado;
	}

	public void setEstoqueReservado(Map<EstoqueAlmoxarifado, Integer> estoqueReservado) {
		this.estoqueReservado = estoqueReservado;
	}

	public Integer getCota() {
		return cota;
	}

	public void setCota(Integer cota) {
		this.cota = cota;
	}

	public Integer getTotalEstoque() {
		return totalEstoque;
	}

	public void setTotalEstoque(Integer totalEstoque) {
		this.totalEstoque = totalEstoque;
	}

	public Integer getQuantidadeRecebidaMes() {
		return quantidadeRecebidaMes;
	}

	public void setQuantidadeRecebidaMes(Integer quantidadeRecebidaMes) {
		this.quantidadeRecebidaMes = quantidadeRecebidaMes;
	}
	
	public String getLotes(){
		String res = "";
		Set<EstoqueAlmoxarifado> keySet = getEstoqueReservado().keySet();
		for(EstoqueAlmoxarifado key : keySet){
			Integer qtd = getEstoqueReservado().get(key);
			res += ((key.getLote() == null || key.getLote().isEmpty()) ? "<b>[Sem Lote]</b>" : "<b>"+key.getLote()+"</b>") + " - " + qtd + " <br/>";
		}
		return res;
	}
	
}
