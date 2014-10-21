package br.com.imhotep.entidade.extra;

import java.util.Date;

public class EstoqueAlmoxarifadoMaterialLote {
	private int idLote;
	private String lote;
	private String fabricante;
	private Date dataValidade;
	private int quantidadeAtual;
	private String responsavel;
	
	public EstoqueAlmoxarifadoMaterialLote(){
		super();
	}
	
	public EstoqueAlmoxarifadoMaterialLote(int idLote, String lote, String fabricante, Date dataValidade, int quantidadeAtual, String responsavel){
		super();
		this.lote = lote;
		this.idLote = idLote;
		this.fabricante = fabricante;
		this.dataValidade = dataValidade;
		this.quantidadeAtual = quantidadeAtual;
		this.responsavel = responsavel;
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

	public int getIdLote() {
		return idLote;
	}

	public void setIdLote(int idLote) {
		this.idLote = idLote;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
}
