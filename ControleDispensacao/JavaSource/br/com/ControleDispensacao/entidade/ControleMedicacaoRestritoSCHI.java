package br.com.ControleDispensacao.entidade;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.enums.TipoIndicacaoEnum;
import br.com.ControleDispensacao.enums.TipoPrescricaoInadequadaEnum;
import br.com.ControleDispensacao.enums.TipoSubIndicacaoProfilaxiaEnum;
import br.com.ControleDispensacao.enums.TipoSubIndicacaoTerapeuticaEnum;

	@Entity
	@Table(name = "tb_controle_medicacao_restrito_schi")
	public class ControleMedicacaoRestritoSCHI {
		private int idControleMedicacaoRestritoSCHI;
		
		private String via;
		private Integer frequencia;
		private Integer dose;
		private Integer tempoUso;
		private Date dataLimite;
		
		private Profissional profissionalAssistente;
		private Profissional profissionalInfectologista;
		private Date dataLiberacaoInfectologista;
		private Date dataCriacaoAssistente;
		
		private TipoBooleanEnum culturaSolicitada;
		private Date dataSolicitacaoCultura;
		private String germeIsolado;
		private String sensibilidade;
		private String materialCultura;
		
		private TipoIndicacaoEnum tipoIndicacao;
		private TipoSubIndicacaoProfilaxiaEnum subIndicacaoProfilaxia;
		private TipoSubIndicacaoTerapeuticaEnum subIndicacaoTerapeutica;
		private String observacaoIndicacao;
		
		private TipoPrescricaoInadequadaEnum tipoPrescricaoInadequada;
		private String prescricaoInadequadaDescricao;
		
		private Paciente paciente;
		private String leito;
		private Float massa;
		
		private PrescricaoItem prescricaoItem;
		
		@SequenceGenerator(name = "generator", sequenceName = "public.tb_controle_medicacao_restrit_id_controle_medicacao_restrit_seq")
		@Id
		@GeneratedValue(generator = "generator")
		@Column(name = "id_controle_medicacao_restrito_schi", unique = true, nullable = false)
		public int getIdControleMedicacaoRestritoSCHI() {
			return idControleMedicacaoRestritoSCHI;
		}
		
		public void setIdControleMedicacaoRestritoSCHI(int idControleMedicacaoRestritoSCHI) {
			this.idControleMedicacaoRestritoSCHI = idControleMedicacaoRestritoSCHI;
		}

		@Column(name = "in_tempo_uso")
		public Integer getTempoUso() {
			return tempoUso;
		}

		public void setTempoUso(Integer tempoUso) {
			this.tempoUso = tempoUso;
		}
		
		@Column(name = "tp_indicacao")
		@Enumerated(EnumType.STRING)
		public TipoIndicacaoEnum getTipoIndicacao() {
			return tipoIndicacao;
		}

		public void setTipoIndicacao(TipoIndicacaoEnum tipoIndicacao) {
			this.tipoIndicacao = tipoIndicacao;
		}

		@Column(name = "tp_cultura_solicitada")
		@Enumerated(EnumType.STRING)
		public TipoBooleanEnum getCulturaSolicitada() {
			return culturaSolicitada;
		}

		public void setCulturaSolicitada(TipoBooleanEnum culturaSolicitada) {
			this.culturaSolicitada = culturaSolicitada;
		}

		@Column(name = "ds_germe_isolado")
		public String getGermeIsolado() {
			return germeIsolado;
		}

		public void setGermeIsolado(String germeIsolado) {
			this.germeIsolado = germeIsolado;
		}

		@Column(name = "ds_sensibilidade")
		public String getSensibilidade() {
			return sensibilidade;
		}

		public void setSensibilidade(String sensibilidade) {
			this.sensibilidade = sensibilidade;
		}

		@Column(name = "tp_prescricao_inadequada")
		@Enumerated(EnumType.STRING)
		public TipoPrescricaoInadequadaEnum getTipoPrescricaoInadequada() {
			return tipoPrescricaoInadequada;
		}

		public void setTipoPrescricaoInadequada(TipoPrescricaoInadequadaEnum tipoPrescricaoInadequada) {
			this.tipoPrescricaoInadequada = tipoPrescricaoInadequada;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "id_profissional_assistente")
		public Profissional getProfissionalAssistente() {
			return profissionalAssistente;
		}

		public void setProfissionalAssistente(Profissional profissionalAssistente) {
			this.profissionalAssistente = profissionalAssistente;
		}

		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "id_profissional_infectologista")
		public Profissional getProfissionalInfectologista() {
			return profissionalInfectologista;
		}

		public void setProfissionalInfectologista(
				Profissional profissionalInfectologista) {
			this.profissionalInfectologista = profissionalInfectologista;
		}
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "dt_criacao_assistente")
		public Date getDataCriacaoAssistente(){
			return dataCriacaoAssistente;
		}
		
		public void setDataCriacaoAssistente(Date dataCriacaoAssistente){
			this.dataCriacaoAssistente = dataCriacaoAssistente;
		}
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "dt_solicitacao_cultura")
		public Date getDataSolicitacaoCultura(){
			return dataSolicitacaoCultura;
		}
		
		public void setDataSolicitacaoCultura(Date dataSolicitacaoCultura){
			this.dataSolicitacaoCultura = dataSolicitacaoCultura;
		}
		
		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "dt_liberacao_infectologista")
		public Date getDataLiberacaoInfectologista(){
			return dataLiberacaoInfectologista;
		}
		
		public void setDataLiberacaoInfectologista(Date dataLiberacaoInfectologista){
			this.dataLiberacaoInfectologista = dataLiberacaoInfectologista;
		}

		@Temporal(TemporalType.TIMESTAMP)
		@Column(name = "dt_limite")
		public Date getDataLimite(){
			return dataLimite;
		}
		
		public void setDataLimite(Date dataLimite){
			this.dataLimite = dataLimite;
		}
		
		@Column(name = "tp_indicacao_terapeutica")
		@Enumerated(EnumType.STRING)
		public TipoSubIndicacaoTerapeuticaEnum getSubIndicacaoTerapeutica() {
			return subIndicacaoTerapeutica;
		}

		public void setSubIndicacaoTerapeutica(TipoSubIndicacaoTerapeuticaEnum subIndicacaoTerapeutica) {
			this.subIndicacaoTerapeutica = subIndicacaoTerapeutica;
		}
		
		@Column(name = "tp_indicacao_profilaxia")
		@Enumerated(EnumType.STRING)
		public TipoSubIndicacaoProfilaxiaEnum getSubIndicacaoProfilaxia() {
			return subIndicacaoProfilaxia;
		}

		public void setSubIndicacaoProfilaxia(TipoSubIndicacaoProfilaxiaEnum subIndicacaoProfilaxia) {
			this.subIndicacaoProfilaxia = subIndicacaoProfilaxia;
		}
		
		@Column(name = "cv_prescricao_inadequada_descricao")
		public String getPrescricaoInadequadaDescricao() {
			return prescricaoInadequadaDescricao;
		}

		public void setPrescricaoInadequadaDescricao(String prescricaoInadequadaDescricao) {
			this.prescricaoInadequadaDescricao = prescricaoInadequadaDescricao;
		}
		
		@Column(name = "cv_observacao_indicacao")
		public String getObservacaoIndicacao() {
			return observacaoIndicacao;
		}

		public void setObservacaoIndicacao(String observacaoIndicacao) {
			this.observacaoIndicacao = observacaoIndicacao;
		}
		
		@Column(name = "cv_material_cultura")
		public String getMaterialCultura() {
			return materialCultura;
		}

		public void setMaterialCultura(String materialCultura) {
			this.materialCultura = materialCultura;
		}
		
		@Column(name = "in_dose")
		public Integer getDose() {
			return dose;
		}

		public void setDose(Integer dose) {
			this.dose = dose;
		}

		@Column(name = "cv_via")
		public String getVia() {
			return via;
		}

		public void setVia(String via) {
			this.via = via;
		}
		
		@Column(name = "in_frequencia")
		public Integer getFrequencia() {
			return frequencia;
		}

		public void setFrequencia(Integer frequencia) {
			this.frequencia = frequencia;
		}
		
		@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
		@JoinColumn(name = "id_paciente")
		public Paciente getPaciente() {
			return paciente;
		}
		public void setPaciente(Paciente paciente) {
			this.paciente = paciente;
		}
		
		@Column(name = "cv_leito")
		public String getLeito() {
			return leito;
		}
		public void setLeito(String leito) {
			this.leito = leito;
		}
		
		@Column(name = "db_massa")
		public Float getMassa() {
			return massa;
		}
		public void setMassa(Float peso) {
			this.massa = peso;
		}
		
		@OneToOne(mappedBy = "controleMedicacaoRestritoSCHI", targetEntity = PrescricaoItem.class, fetch = FetchType.EAGER)
		public PrescricaoItem getPrescricaoItem() {
			return prescricaoItem;
		}
		public void setPrescricaoItem(PrescricaoItem prescricaoItem) {
			this.prescricaoItem = prescricaoItem;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == null)
				return false;
			if(!(obj instanceof ControleMedicacaoRestritoSCHI))
				return false;
			
			return ((ControleMedicacaoRestritoSCHI)obj).getIdControleMedicacaoRestritoSCHI() == this.idControleMedicacaoRestritoSCHI;
		}

		@Override
		public int hashCode() {
		    int hash = 1;
		    return hash * 31 + ((Integer) idControleMedicacaoRestritoSCHI).hashCode();
		}

		@Override
		public String toString() {
			return germeIsolado;
		}

}
