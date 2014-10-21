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
	private Integer quantidade;
	private Integer quantidadeAtual;
	private Date dataMovimento;
	private String usuario;
	private String nomeUnidade;
	private String nomeMaterial;
	private String justificativa;

	public MovimentacaoEstoqueMaterialAlmoxarifado(){
		
	}
	
	public MovimentacaoEstoqueMaterialAlmoxarifado(TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado, String nomeUnidade, 
			String lote, Integer idLote, Integer quantidade, Integer quantidadeAtual, Date dataMovimento, 
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
		if(getTipoMovimentoAlmoxarifado() == null){
			return 0;
		}
		if(getTipoMovimentoAlmoxarifado().getTipoOperacao().equals(TipoOperacaoEnum.E))
			return getQuantidadeAtual() + getQuantidade();
		else
			return getQuantidadeAtual() - getQuantidade();
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

	public Integer getIdLote() {
		return idLote;
	}

	public void setIdLote(Integer idLote) {
		this.idLote = idLote;
	}
}
