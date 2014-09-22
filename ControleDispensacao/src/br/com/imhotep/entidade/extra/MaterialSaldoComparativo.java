package br.com.imhotep.entidade.extra;

import java.util.ArrayList;
import java.util.List;

public class MaterialSaldoComparativo {
	private String material;
	private Integer saldoTransportado;
	private Integer saldoDomingo;
	private Integer qtdInventario;
	private List<EstoqueApoio> listaEstoqueInventariado = new ArrayList<EstoqueApoio>();
	private List<EstoqueApoio> listaEstoqueSistema = new ArrayList<EstoqueApoio>();
	
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public Integer getSaldoTransportado() {
		return saldoTransportado;
	}
	public void setSaldoTransportado(Integer saldoTransportado) {
		this.saldoTransportado = saldoTransportado;
	}
	public Integer getSaldoDomingo() {
		return saldoDomingo;
	}
	public void setSaldoDomingo(Integer saldoDomingo) {
		this.saldoDomingo = saldoDomingo;
	}
	public Integer getQtdInventario() {
		return qtdInventario;
	}
	public void setQtdInventario(Integer qtdInventario) {
		this.qtdInventario = qtdInventario;
	}
	public List<EstoqueApoio> getListaEstoqueInventariado() {
		return listaEstoqueInventariado;
	}
	public void setListaEstoqueInventariado(List<EstoqueApoio> listaEstoqueInventariado) {
		this.listaEstoqueInventariado = listaEstoqueInventariado;
	}
	public List<EstoqueApoio> getListaEstoqueSistema() {
		return listaEstoqueSistema;
	}
	public void setListaEstoqueSistema(List<EstoqueApoio> listaEstoqueSistema) {
		this.listaEstoqueSistema = listaEstoqueSistema;
	}
	
}
