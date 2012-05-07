package br.com.ControleDispensacao.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "tb_prescricao")
public class Prescricao {
	private int idPrescricao;
	private Unidade unidade;
	private Profissional profissional;
	private Paciente paciente;
	private Integer ano;
	private String numero;
	private Usuario usuarioInclusao;
	private Date dataInclusao;
	private String leito;
	private Float massa;
	private MotivoFimReceita motivoFimReceita;
	private TipoStatusEnum dispensavel;
	private TipoStatusEnum dispensado;
	private List<PrescricaoItem> prescricaoItens;
	private Date dataPrescricao;
	private Date dataDipensacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_prescricao_id_prescricao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_prescricao", unique = true, nullable = false)
	public int getIdPrescricao() {
		return idPrescricao;
	}
	public void setIdPrescricao(int idPrescricao) {
		this.idPrescricao = idPrescricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade")
	public Unidade getUnidade() {
		return unidade;
	}
	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
	@Column(name = "in_ano")
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	@Column(name = "ds_numero")
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_inclusao")
	public Usuario getUsuarioInclusao() {
		return usuarioInclusao;
	}
	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@Column(name = "ds_leito")
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_motivo_fim_receita")
	public MotivoFimReceita getMotivoFimReceita() {
		return motivoFimReceita;
	}
	public void setMotivoFimReceita(MotivoFimReceita motivoFimReceita) {
		this.motivoFimReceita = motivoFimReceita;
	}

	@Column(name = "tp_dispensavel")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getDispensavel() {
		return dispensavel;
	}
	public void setDispensavel(TipoStatusEnum dispensavel) {
		this.dispensavel = dispensavel;
	}
	
	@Column(name = "tp_dispensado")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getDispensado() {
		return dispensado;
	}
	public void setDispensado(TipoStatusEnum dispensado) {
		this.dispensado = dispensado;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "prescricao")
	public List<PrescricaoItem> getPrescricaoItens() {
		return prescricaoItens;
	}
	public void setPrescricaoItens(List<PrescricaoItem> prescricaoItens) {
		this.prescricaoItens = prescricaoItens;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_prescricao")
	public Date getDataPrescricao() {
		return dataPrescricao;
	}
	
	public void setDataPrescricao(Date dataPrescricao) {
		this.dataPrescricao = dataPrescricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_dispensacao")
	public Date getDataDipensacao() {
		return dataDipensacao;
	}
	
	public void setDataDipensacao(Date dataDipensacao) {
		this.dataDipensacao = dataDipensacao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Prescricao))
			return false;
		
		return ((Prescricao)obj).getIdPrescricao() == this.idPrescricao;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + leito.hashCode();
	}

	@Override
	public String toString() {
		return "Leito: ".concat(leito).concat(" - Paciente: ").concat(paciente.getNome());
	}
}
