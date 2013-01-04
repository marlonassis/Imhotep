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
@Table(name = "tb_medicamento_nao_padronizado")
public class MedicamentoNaoPadronizado {
	private int idMedicamentoNaoPadronizado;
	private String nomeGenerico;
	private String nomeComercial;
	private String formaFarmaceutica;
	private String dosagem;
	private String acaoFarmacologica;
	private String indicacao;
	private String posologia;
	private Integer duracaoTratamento;
	private Paciente paciente;
	private String justificativa;
	private Date dataSolicitacao;
	private Profissional profissionalSolicitacao;
	private String parecerComissaoFarmacia;
	private Date dataLiberacao;
	private Profissional profissionalLiberacao;
	private Prescricao prescricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_medicamento_nao_padronizad_id_medicamento_nao_padronizad_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_medicamento_nao_padronizado", unique = true, nullable = false)
	public int getIdMedicamentoNaoPadronizado() {
		return this.idMedicamentoNaoPadronizado;
	}
	
	public void setIdMedicamentoNaoPadronizado(int idMedicamentoNaoPadronizado){
		this.idMedicamentoNaoPadronizado = idMedicamentoNaoPadronizado;
	}

	@Column(name = "cv_nome_generico")
	public String getNomeGenerico() {
		return this.nomeGenerico;
	}

	public void setNomeGenerico(String nomeGenerico) {
		this.nomeGenerico = nomeGenerico;
	}
	
	@Column(name = "cv_nome_comercial")
	public String getNomeComercial() {
		return this.nomeComercial;
	}

	public void setNomeComercial(String nomeComercial) {
		this.nomeComercial = nomeComercial;
	}
	
	@Column(name = "cv_forma_farmaceutica")
	public String getFormaFarmaceutica() {
		return this.formaFarmaceutica;
	}

	public void setFormaFarmaceutica(String formaFarmaceutica) {
		this.formaFarmaceutica = formaFarmaceutica;
	}

	@Column(name = "cv_posologia")
	public String getPosologia() {
		return this.posologia;
	}

	public void setPosologia(String posologia) {
		this.posologia = posologia;
	}
	
	@Column(name = "cv_acao_farmacologica")
	public String getAcaoFarmacologica() {
		return this.acaoFarmacologica;
	}

	public void setAcaoFarmacologica(String acaoFarmacologica) {
		this.acaoFarmacologica = acaoFarmacologica;
	}
	
	@Column(name = "cv_indicacao")
	public String getIndicacao() {
		return this.indicacao;
	}

	public void setIndicacao(String indicacao) {
		this.indicacao = indicacao;
	}
	
	@Column(name = "cv_dosagem")
	public String getDosagem() {
		return this.dosagem;
	}

	public void setDosagem(String dosagem) {
		this.dosagem = dosagem;
	}
	
	@Column(name = "in_duracao_tratamento")
	public Integer getDuracaoTratamento() {
		return this.duracaoTratamento;
	}

	public void setDuracaoTratamento(Integer duracaoTratamento) {
		this.duracaoTratamento = duracaoTratamento;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	public Paciente getPaciente() {
		return this.paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Column(name = "cv_justificativa")
	public String getJustificativa() {
		return this.justificativa;
	}

	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_solicitacao")
	public Date getDataSolicitacao() {
		return this.dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_solicitacao")
	public Profissional getProfissionalSolicitacao() {
		return this.profissionalSolicitacao;
	}
	
	public void setProfissionalSolicitacao(Profissional profissionalSolicitacao) {
		this.profissionalSolicitacao = profissionalSolicitacao;
	}
	
	@Column(name = "cv_parecer_comissao_farmacia")
	public String getParecerComissaoFarmacia() {
		return this.parecerComissaoFarmacia;
	}

	public void setParecerComissaoFarmacia(String parecerComissaoFarmacia) {
		this.parecerComissaoFarmacia = parecerComissaoFarmacia;
	}	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_liberacao")
	public Date getDataLiberacao() {
		return this.dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_liberacao")
	public Profissional getProfissionalLiberacao() {
		return this.profissionalLiberacao;
	}
	
	public void setProfissionalLiberacao(Profissional profissionalLiberacao) {
		this.profissionalLiberacao = profissionalLiberacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_prescricao")
	public Prescricao getPrescricao() {
		return this.prescricao;
	}
	
	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof MedicamentoNaoPadronizado))
			return false;
		
		return ((MedicamentoNaoPadronizado)obj).getIdMedicamentoNaoPadronizado() == this.idMedicamentoNaoPadronizado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + getIdMedicamentoNaoPadronizado();
	}

	@Override
	public String toString() {
		return nomeGenerico;
	}
}
