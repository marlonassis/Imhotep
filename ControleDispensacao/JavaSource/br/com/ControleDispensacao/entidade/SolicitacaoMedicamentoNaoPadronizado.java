package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_solicitacao_medicamento_nao_padronizado")
public class SolicitacaoMedicamentoNaoPadronizado {
	private int idSolicitacaoMedicamentoNaoPadronizado;
	private String nomeGenerico;
	private String nomeComercial;
	private String formaFarmaceutica;
	private String dosagem;
	private String acaoFarmacologica;
	private String indicacao;
	private String posologia;
	private Integer duracaoTratamento;
	private Prescricao prescricao;
	private String justificativa;
	private Date dataSolicitacao;
	private Profissional profissionalSolicitacao;
	private String parecerFarmacia;
	private Date dataParecer;
	private Profissional profissionalAutorizacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_solicitacao_medicamento_na_tb_solicitacao_medicamento_na_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_solicitacao_medicamento_nao_padronizado", unique = true, nullable = false)
	public int getIdSolicitacaoMedicamentoNaoPadronizado() {
		return idSolicitacaoMedicamentoNaoPadronizado;
	}
	public void setIdSolicitacaoMedicamentoNaoPadronizado(
			int idSolicitacaoMedicamentoNaoPadronizado) {
		this.idSolicitacaoMedicamentoNaoPadronizado = idSolicitacaoMedicamentoNaoPadronizado;
	}
	
	@Column(name = "cv_nome_generico")
	public String getNomeGenerico() {
		return nomeGenerico;
	}
	public void setNomeGenerico(String nomeGenerico) {
		this.nomeGenerico = nomeGenerico;
	}
	
	@Column(name = "cv_nome_comercial")
	public String getNomeComercial() {
		return nomeComercial;
	}	
	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}
	
	@Column(name = "cv_forma_farmaceutica")
	public String getFormaFarmaceutica() {
		return formaFarmaceutica;
	}
	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}
	
	@Column(name = "cv_dosagem")
	public String getDosagem() {
		return dosagem;
	}
	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}
	
	@Column(name = "cv_acao_farmacologica")
	public String getAcaoFarmacologica() {
		return acaoFarmacologica;
	}
	public void setAcaoFarmacologica(String acaoFarmacologica) {
		this.acaoFarmacologica = acaoFarmacologica;
	}
	
	@Column(name = "cv_indicacao")
	public String getIndicacao() {
		return indicacao;
	}
	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}
	
	@Column(name = "cv_posologia")
	public String getPosologia() {
		return posologia;
	}
	public void setPosologia(String posologia) {
		this.posologia = posologia;
	}
	
	@Column(name = "in_duracao_tratamento")
	public Integer getDuracaoTratamento() {
		return duracaoTratamento;
	}
	public void setDuracaoTratamento(Integer duracaoTratamento) {
		this.duracaoTratamento = duracaoTratamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return prescricao;
	}
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@Column(name = "cv_justificativa")
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_solicitacao")
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_solicitacao")
	public Profissional getProfissionalSolicitacao() {
		return profissionalSolicitacao;
	}
	public void setProfissionalSolicitacao(Profissional profissionalSolicitacao) {
		this.profissionalSolicitacao = profissionalSolicitacao;
	}
	
	@Column(name = "cv_parecer_farmacia")
	public String getParecerFarmacia() {
		return parecerFarmacia;
	}
	public void setParecerFarmacia(String parecerFarmacia) {
		this.parecerFarmacia = parecerFarmacia;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_parecer")
	public Date getDataParecer() {
		return dataParecer;
	}
	public void setDataParecer(Date dataParecer) {
		this.dataParecer = dataParecer;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_autorizacao")
	public Profissional getProfissionalAutorizacao() {
		return profissionalAutorizacao;
	}
	public void setProfissionalAutorizacao(Profissional profissionalAutorizacao) {
		this.profissionalAutorizacao = profissionalAutorizacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SolicitacaoMedicamentoNaoPadronizado))
			return false;
		
		return ((SolicitacaoMedicamentoNaoPadronizado)obj).getIdSolicitacaoMedicamentoNaoPadronizado() == this.idSolicitacaoMedicamentoNaoPadronizado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + Integer.valueOf(idSolicitacaoMedicamentoNaoPadronizado);
	}
	
	@Override
	public String toString() {
		return nomeGenerico;
	}
	
}
