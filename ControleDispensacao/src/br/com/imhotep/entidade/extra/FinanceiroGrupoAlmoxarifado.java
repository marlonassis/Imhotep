package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.List;

public class FinanceiroGrupoAlmoxarifado {
	private String descricao;
	private List<FinanceiroGrupoNotaFiscalAlmoxarifado> itens = new ArrayList<FinanceiroGrupoNotaFiscalAlmoxarifado>();
	
	public FinanceiroGrupoAlmoxarifado() {
		super();
	}
	public FinanceiroGrupoAlmoxarifado(String descricao,
			List<FinanceiroGrupoNotaFiscalAlmoxarifado> itens) {
		super();
		this.descricao = descricao;
		this.itens = itens;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<FinanceiroGrupoNotaFiscalAlmoxarifado> getItens() {
		return itens;
	}
	public void setItens(List<FinanceiroGrupoNotaFiscalAlmoxarifado> itens) {
		this.itens = itens;
	}
	
	public double getTotal(){
		double total = 0d;
		for(FinanceiroGrupoNotaFiscalAlmoxarifado obj : getItens()){
			total += obj.getValorDescontado();
		}
		return total;
	}
	
}
