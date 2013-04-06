package br.com.imhotep.entidade.relatorio;

import java.util.Date;

import br.com.imhotep.entidade.TipoMovimento;

public class MovimentacaoEstoqueUnidade {
	private String mensagemNaoEncontrado;
	private TipoMovimento tipoMovimento;
	private String nomeMaterial;
	private String lote;
	private Integer quantidade;
	private Date dataMovimento;
	private String usuario;

	public MovimentacaoEstoqueUnidade(){
		
	}
	
	public MovimentacaoEstoqueUnidade(TipoMovimento tipoMovimento, String nomeMaterial, String lote, Integer quantidade, 
			Date dataMovimento, String usuario){
		this.tipoMovimento=tipoMovimento;
		this.nomeMaterial=nomeMaterial;
		this.lote=lote;
		this.quantidade=quantidade;
		this.dataMovimento=dataMovimento;
		this.usuario=usuario;
	}
	
	public MovimentacaoEstoqueUnidade(String mensagemNaoEncontrado){
		setMensagemNaoEncontrado(mensagemNaoEncontrado);
	}
	
	public String getMensagemNaoEncontrado() {
		return mensagemNaoEncontrado;
	}

	public void setMensagemNaoEncontrado(String mensagemNaoEncontrado) {
		this.mensagemNaoEncontrado = mensagemNaoEncontrado;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
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

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
