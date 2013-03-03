package br.com.imhotep.entidade.relatorio;

import java.util.Date;

public class EstoqueVencimento {
	private Integer codigoMaterial;
	private String nomeMaterial;
	private String lote;
	private Date dataValidade;
	private Integer quantidade;
	private String mensagemNaoEncontrado;
	private String usuario;
	
	public EstoqueVencimento(){
		
	}
	
	public EstoqueVencimento(String mensagemNaoEncontrado){
		setMensagemNaoEncontrado(mensagemNaoEncontrado);
	}
	
	public String getMensagemNaoEncontrado() {
		return mensagemNaoEncontrado;
	}

	public void setMensagemNaoEncontrado(String mensagemNaoEncontrado) {
		this.mensagemNaoEncontrado = mensagemNaoEncontrado;
	}

	public String getNomeMaterial() {
		return nomeMaterial;
	}

	public void setNomeMaterial(String nomeMaterial) {
		this.nomeMaterial = nomeMaterial;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Integer getCodigoMaterial() {
		return codigoMaterial;
	}

	public void setCodigoMaterial(Integer codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}
	
}
