package br.com.imhotep.entidade;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.imhotep.enums.TipoComplexidadeEnum;
import br.com.imhotep.enums.TipoSexoEnum;

@Entity
@Table(name = "tb_procedimento_saude")
public class ProcedimentoSaude {
	
	private int idProcedimentoSaude;
	private String nome;
	private String codigoProcedimento;
	private TipoComplexidadeEnum complexidade;
	private TipoSexoEnum sexo;
	private Integer quantidadeMaximaExecucoes;
	private Integer diasPermanencia;
	private Integer pontos;
	private Integer idadeMinima;
	private Integer idadeMaxima;
	private Double valorSh;
	private Double valorSa;
	private Double valorSp;
	private String financiamento;
	private String rubrica;
	private Integer quantidadeTempoPermanencia;
	private Date dataCompetencia;
	private Convenio convenio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_procedimento_saude_id_procedimento_saude_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_procedimento_saude", unique = true, nullable = false)
	public int getIdProcedimentoSaude() {
		return idProcedimentoSaude;
	}
	public void setIdProcedimentoSaude(int idProcedimentoSaude) {
		this.idProcedimentoSaude = idProcedimentoSaude;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "cv_codigo_procedimento")
	public String getCodigoProcedimento() {
		return codigoProcedimento;
	}
	public void setCodigoProcedimento(String codigoProcedimento) {
		this.codigoProcedimento = codigoProcedimento;
	}
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cv_complexidade")
	public TipoComplexidadeEnum getComplexidade() {
		return complexidade;
	}
	public void setComplexidade(TipoComplexidadeEnum complexidade) {
		this.complexidade = complexidade;
	}
	
	@Column(name = "cv_sexo")
	@Enumerated(EnumType.STRING)
	public TipoSexoEnum getSexo() {
		return sexo;
	}
	public void setSexo(TipoSexoEnum sexo) {
		this.sexo = sexo;
	}
	
	@Column(name = "in_quantidade_maxima_execucoes")
	public Integer getQuantidadeMaximaExecucoes() {
		return quantidadeMaximaExecucoes;
	}
	public void setQuantidadeMaximaExecucoes(Integer quantidadeMaximaExecucoes) {
		this.quantidadeMaximaExecucoes = quantidadeMaximaExecucoes;
	}
	
	@Column(name = "in_dias_permanencia")
	public Integer getDiasPermanencia() {
		return diasPermanencia;
	}
	public void setDiasPermanencia(Integer diasPermanencia) {
		this.diasPermanencia = diasPermanencia;
	}
	
	@Column(name = "in_pontos")
	public Integer getPontos() {
		return pontos;
	}
	public void setPontos(Integer pontos) {
		this.pontos = pontos;
	}
	
	@Column(name = "in_idade_minima")
	public Integer getIdadeMinima() {
		return idadeMinima;
	}
	public void setIdadeMinima(Integer idadeMinima) {
		this.idadeMinima = idadeMinima;
	}
	
	@Column(name = "in_idade_maxima")
	public Integer getIdadeMaxima() {
		return idadeMaxima;
	}
	public void setIdadeMaxima(Integer idadeMaxima) {
		this.idadeMaxima = idadeMaxima;
	}
	
	@Column(name = "db_valor_sh")
	public Double getValorSh() {
		return valorSh;
	}
	public void setValorSh(Double valorSh) {
		this.valorSh = valorSh;
	}
	
	@Column(name = "db_valor_sa")
	public Double getValorSa() {
		return valorSa;
	}
	public void setValorSa(Double valorSa) {
		this.valorSa = valorSa;
	}
	
	@Column(name = "db_valor_sp")
	public Double getValorSp() {
		return valorSp;
	}
	public void setValorSp(Double valorSp) {
		this.valorSp = valorSp;
	}
	
	@Column(name = "cv_financiamento")
	public String getFinanciamento() {
		return financiamento;
	}
	public void setFinanciamento(String financiamento) {
		this.financiamento = financiamento;
	}
	
	@Column(name = "cv_rubrica")
	public String getRubrica() {
		return rubrica;
	}
	public void setRubrica(String rubrica) {
		this.rubrica = rubrica;
	}
	
	@Column(name = "in_quantidade_tempo_permanencia")
	public Integer getQuantidadeTempoPermanencia() {
		return quantidadeTempoPermanencia;
	}
	public void setQuantidadeTempoPermanencia(Integer quantidadeTempoPermanencia) {
		this.quantidadeTempoPermanencia = quantidadeTempoPermanencia;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_competencia")
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name = "id_convenio")
	public Convenio getConvenio() {
		return convenio;
	}
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ProcedimentoSaude))
			return false;
		
		return ((ProcedimentoSaude)obj).getIdProcedimentoSaude() == this.idProcedimentoSaude;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
