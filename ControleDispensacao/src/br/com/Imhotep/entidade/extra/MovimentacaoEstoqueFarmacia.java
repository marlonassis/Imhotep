package br.com.Imhotep.entidade.extra;

import java.util.Date;

import br.com.Imhotep.entidade.TipoMovimento;

public class MovimentacaoEstoqueFarmacia {
	private String mensagemNaoEncontrado;
	private TipoMovimento tipoMovimento;
	private String lote;
	private Integer quantidade;
	private Date dataMovimento;
	private String usuario;
	private String nomeUnidade;
	private String nomeMaterial;

	public MovimentacaoEstoqueFarmacia(){
		
	}
	
	public MovimentacaoEstoqueFarmacia(String mensagemNaoEncontrado){
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

	public String getNomeUnidade() {
		return nomeUnidade;
	}

	public void setNomeUnidade(String nomeUnidade) {
		this.nomeUnidade = nomeUnidade;
	}

	public String getNomeMaterial() {
		return nomeMaterial;
	}

	public void setNomeMaterial(String nomeMaterial) {
		this.nomeMaterial = nomeMaterial;
	}
	
}
