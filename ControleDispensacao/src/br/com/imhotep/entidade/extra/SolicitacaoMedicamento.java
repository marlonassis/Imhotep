package br.com.imhotep.entidade.extra;

import java.util.Date;

import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;

public class SolicitacaoMedicamento {
	private int idSolicitacaoMedicamentoUnidade;
	private String descricao;
	private int quantidadeItens;
	private Date dataInsercao;
	private Profissional profissionalInsercao;
	private TipoStatusDispensacaoEnum statusDispensacao;
	
	public SolicitacaoMedicamento(){
		
	}
	
	public SolicitacaoMedicamento(int idSolicitacaoMedicamentoUnidade, String descricao, int quantidadeItens, 
			Date dataInsercao, Profissional profissionalInsercao, TipoStatusDispensacaoEnum statusDispensacao){
		this.idSolicitacaoMedicamentoUnidade = idSolicitacaoMedicamentoUnidade;
		this.descricao = descricao;
		this.quantidadeItens = quantidadeItens;
		this.dataInsercao = dataInsercao;
		this.profissionalInsercao = profissionalInsercao;
		this.statusDispensacao = statusDispensacao;
	}
	
	public Date getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}

	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}
	
	public int getIdSolicitacaoMedicamentoUnidade() {
		return idSolicitacaoMedicamentoUnidade;
	}
	public void setIdSolicitacaoMedicamentoUnidade(
			int idSolicitacaoMedicamentoUnidade) {
		this.idSolicitacaoMedicamentoUnidade = idSolicitacaoMedicamentoUnidade;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricaoUnidade) {
		this.descricao = descricaoUnidade;
	}
	public int getQuantidadeItens() {
		return quantidadeItens;
	}
	public void setQuantidadeItens(int quantidadeItens) {
		this.quantidadeItens = quantidadeItens;
	}

	public TipoStatusDispensacaoEnum getStatusDispensacao() {
		return statusDispensacao;
	}

	public void setStatusDispensacao(TipoStatusDispensacaoEnum statusDispensacao) {
		this.statusDispensacao = statusDispensacao;
	}

}
