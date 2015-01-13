package br.com.imhotep.entidade.relatorio;

import java.util.Date;

import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.enums.TipoOperacaoEnum;

public class MovimentacaoEstoqueMaterialAlmoxarifado {
	private String mensagemNaoEncontrado;
	private TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado;
	private Integer idLote;
	private String lote;
	private Double quantidade;
	private Double quantidadeAtual;
	private Date dataMovimento;
	private String usuario;
	private String nomeUnidade;
	private String nomeMaterial;
	private String justificativa;

	public MovimentacaoEstoqueMaterialAlmoxarifado(){
		
	}
	
	public MovimentacaoEstoqueMaterialAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, String nomeUnidade, 
			String lote, Integer idLote, Double quantidade, Double quantidadeAtual, Date dataMovimento, 
			Profissional profissinal, String nomeMaterial, String justificativa){
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
		this.nomeUnidade = nomeUnidade;
		this.lote = lote;
		this.quantidade = quantidade;
		this.quantidadeAtual = quantidadeAtual;
		this.dataMovimento = dataMovimento;
		this.usuario = profissinal.getNomeResumido();
		this.nomeMaterial = nomeMaterial;
		this.justificativa = justificativa;
		this.idLote = idLote;
	}
	
	public MovimentacaoEstoqueMaterialAlmoxarifado(String mensagemNaoEncontrado){
		setMensagemNaoEncontrado(mensagemNaoEncontrado);
	}
	
	public String getMensagemNaoEncontrado() {
		return mensagemNaoEncontrado;
	}

	public void setMensagemNaoEncontrado(String mensagemNaoEncontrado) {
		this.mensagemNaoEncontrado = mensagemNaoEncontrado;
	}

	public TipoMovimentoAlmoxarifado getTipoMovimentoAlmoxarifado() {
		return tipoMovimentoAlmoxarifado;
	}

	public void setTipoMovimentoAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado) {
		this.tipoMovimentoAlmoxarifado = tipoMovimentoAlmoxarifado;
	}

	public String getLote() {
		if(lote == null || lote.isEmpty())
			return "("+getIdLote()+")";
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
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
	
	public Double getSaldoRestante(){
		if(getTipoMovimentoAlmoxarifado() == null){
			return 0d;
		}
		if(getTipoMovimentoAlmoxarifado().getTipoOperacao().equals(TipoOperacaoEnum.E))
			return getQuantidadeAtual().doubleValue() + getQuantidade().doubleValue();
		else
			return getQuantidadeAtual().doubleValue() - getQuantidade().doubleValue();
	}
	
	@Override
	public String toString() {
		return this.tipoMovimentoAlmoxarifado+" - "+
		this.nomeUnidade +" - "+
		this.lote+" - "+
		this.quantidade +" - "+
		this.dataMovimento +" - "+
		this.usuario +" - "+
		this.nomeMaterial;
	}

	public Double getQuantidadeAtual() {
		return quantidadeAtual;
	}

	public void setQuantidadeAtual(Double quantidadeAtual) {
		this.quantidadeAtual = quantidadeAtual;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
}
