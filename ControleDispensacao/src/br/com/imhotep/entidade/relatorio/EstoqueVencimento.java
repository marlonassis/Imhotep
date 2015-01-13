package br.com.imhotep.entidade.relatorio;

import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;

public class EstoqueVencimento {
	private String codigoMaterial;
	private String nomeMaterial;
	private String lote;
	private Date dataValidade;
	private Integer quantidade;
	private String mensagemNaoEncontrado;
	private String usuario;
	private boolean bloqueado;
	
	public EstoqueVencimento(){
		
	}
	
	public EstoqueVencimento(String codigoMaterial, String nomeMaterial, String lote, Date dataValidade, Integer quantidade, String usuario, boolean bloqueado){
		this.codigoMaterial = codigoMaterial;
		this.nomeMaterial = nomeMaterial;
		this.lote = lote;
		this.dataValidade = dataValidade;
		this.quantidade = quantidade;
		this.usuario = usuario;
		this.bloqueado = bloqueado;
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
		return new Utilitarios().nomeResumido(usuario);
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCodigoMaterial() {
		return codigoMaterial;
	}

	public void setCodigoMaterial(String codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
}
