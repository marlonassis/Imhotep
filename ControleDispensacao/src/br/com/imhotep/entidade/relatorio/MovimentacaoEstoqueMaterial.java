package br.com.imhotep.entidade.relatorio;

import java.util.Date;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.enums.TipoOperacaoEnum;

public class MovimentacaoEstoqueMaterial {
	private String mensagemNaoEncontrado;
	private TipoMovimento tipoMovimento;
	private String lote;
	private Integer quantidade;
	private Integer quantidadeAtual;
	private Date dataMovimento;
	private String usuario;
	private String nomeUnidade;
	private String nomeMaterial;
	private String justificativa;

	public MovimentacaoEstoqueMaterial(){
		
	}
	
	public MovimentacaoEstoqueMaterial(TipoMovimento tipoMovimento, String nomeUnidade, 
			String lote, Integer quantidade, Integer quantidadeAtual, Date dataMovimento, 
			String usuario, String nomeMaterial, String justificativa){
		this.tipoMovimento = tipoMovimento;
		this.nomeUnidade = nomeUnidade;
		this.lote = lote;
		this.quantidade = quantidade;
		this.quantidadeAtual = quantidadeAtual;
		this.dataMovimento = dataMovimento;
		this.usuario = usuario;
		this.nomeMaterial = nomeMaterial;
		this.justificativa = justificativa;
	}
	
	public MovimentacaoEstoqueMaterial(String mensagemNaoEncontrado){
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
	
	public Integer getSaldoRestante(){
		if(getTipoMovimento() == null){
			return 0;
		}
		if(getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.E))
			return getQuantidadeAtual() + getQuantidade();
		else
			return getQuantidadeAtual() - getQuantidade();
	}
	
	@Override
	public String toString() {
		return this.tipoMovimento+" - "+
		this.nomeUnidade +" - "+
		this.lote+" - "+
		this.quantidade +" - "+
		this.dataMovimento +" - "+
		this.usuario +" - "+
		this.nomeMaterial;
	}

	public Integer getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(Integer quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
}
