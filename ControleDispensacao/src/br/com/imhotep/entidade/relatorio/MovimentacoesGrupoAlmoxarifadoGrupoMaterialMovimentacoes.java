package br.com.imhotep.entidade.relatorio;

import java.util.Date;

public class MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes {
	private String lote;
	private Integer qtdAtual;
	private Integer qtdMovimentada;
	private String justificativa;
	private String tipoMovimento;
	private String tipoOperacao;
	private Date dataMovimento;
	private String usuario;
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes(){
		super();
	}
	
	public MovimentacoesGrupoAlmoxarifadoGrupoMaterialMovimentacoes(
			String lote, Integer qtdAtual, Integer qtdMovimentada,
			String justificativa, String tipoMovimento, String tipoOperacao,
			Date dataMovimento) {
		super();
		this.lote = lote;
		this.qtdAtual = qtdAtual;
		this.qtdMovimentada = qtdMovimentada;
		this.justificativa = justificativa;
		this.tipoMovimento = tipoMovimento;
		this.tipoOperacao = tipoOperacao;
		this.dataMovimento = dataMovimento;
	}

	

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Integer getQtdAtual() {
		return qtdAtual;
	}

	public void setQtdAtual(Integer qtdAtual) {
		this.qtdAtual = qtdAtual;
	}

	public Integer getQtdMovimentada() {
		return qtdMovimentada;
	}

	public void setQtdMovimentada(Integer qtdMovimentada) {
		this.qtdMovimentada = qtdMovimentada;
	}

	public String getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}

	public String getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
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
