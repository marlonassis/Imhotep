package br.com.imhotep.entidade.extra;

import java.util.Date;

public class MedicamentoControladoListaMedicamentoEstoque {
	private String lote;
	private String fabricante;
	private Date dataValidade;
	private int quantidadeAtual;
	
	public MedicamentoControladoListaMedicamentoEstoque(){
		super();
	}
	
	public MedicamentoControladoListaMedicamentoEstoque(String lote, String fabricante, Date dataValidade, int quantidadeAtual){
		super();
		this.lote = lote;
		this.fabricante = fabricante;
		this.dataValidade = dataValidade;
		this.quantidadeAtual = quantidadeAtual;
	}
	
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	public Date getDataValidade() {
		return dataValidade;
	}
	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	public int getQuantidadeAtual() {
		return quantidadeAtual;
	}
	public void setQuantidadeAtual(int quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

}
