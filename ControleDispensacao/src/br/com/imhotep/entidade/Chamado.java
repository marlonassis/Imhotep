package br.com.imhotep.entidade;

import java.util.Date;

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

import br.com.imhotep.enums.TipoChamadoStatusEnum;

@Entity
@Table(name = "tb_chamado")
public class Chamado {
	
	private int idChamado;
	
	private String assunto;
	private String descricao;
	private Unidade unidadeSolicitante;
	private Profissional profissionalSolicitante;
	private Date dataSolicitacao;
	private Recursos recurso;
	
	private Unidade unidadeReceptora;
	private Profissional profissionalAtendimento;
	private Date dataAtendimento;
	private String laudoTecnico;
	
	private TipoChamadoStatusEnum tipoChamadoStatus;
	private String observacao;
	private Date dataUsuarioFechamento;
	
	private Chamado chamadoPai;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_chamado_id_chamado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_chamado", unique = true, nullable = false)
	public int getIdChamado() {
		return idChamado;
	}
	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}
	
	@Column(name = "cv_assunto")
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_solicitante")
	public Unidade getUnidadeSolicitante() {
		return unidadeSolicitante;
	}
	public void setUnidadeSolicitante(Unidade unidadeSolicitante) {
		this.unidadeSolicitante = unidadeSolicitante;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_solicitante")
	public Profissional getProfissionalSolicitante() {
		return profissionalSolicitante;
	}
	public void setProfissionalSolicitante(Profissional profissionalSolicitante) {
		this.profissionalSolicitante = profissionalSolicitante;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_solicitacao")
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_receptora")
	public Unidade getUnidadeReceptora() {
		return unidadeReceptora;
	}
	public void setUnidadeReceptora(Unidade unidadeReceptora) {
		this.unidadeReceptora = unidadeReceptora;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_atendimento")
	public Profissional getProfissionalAtendimento() {
		return profissionalAtendimento;
	}
	public void setProfissionalAtendimento(Profissional profissionalAtendimento) {
		this.profissionalAtendimento = profissionalAtendimento;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_atendimento")
	public Date getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(Date dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	
	@Column(name = "cv_laudo_tecnico")
	public String getLaudoTecnico() {
		return laudoTecnico;
	}
	public void setLaudoTecnico(String laudoTecnico) {
		this.laudoTecnico = laudoTecnico;
	}
	
	@Column(name = "tp_tipo_chamado_status")
	@Enumerated(EnumType.STRING)
	public TipoChamadoStatusEnum getTipoChamadoStatus() {
		return tipoChamadoStatus;
	}
	public void setTipoChamadoStatus(TipoChamadoStatusEnum tipoChamadoStatus) {
		this.tipoChamadoStatus = tipoChamadoStatus;
	}
	
	@Column(name = "cv_observacao")
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_usuario_fechamento")
	public Date getDataUsuarioFechamento() {
		return dataUsuarioFechamento;
	}
	public void setDataUsuarioFechamento(Date dataUsuarioFechamento) {
		this.dataUsuarioFechamento = dataUsuarioFechamento;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_chamado_pai")
	public Chamado getChamadoPai() {
		return chamadoPai;
	}
	public void setChamadoPai(Chamado chamadoPai) {
		this.chamadoPai = chamadoPai;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_recursos")
	public Recursos getRecurso() {
		return recurso;
	}
	public void setRecurso(Recursos recurso) {
		this.recurso = recurso;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Chamado))
			return false;
		
		return ((Chamado)obj).getIdChamado() == this.idChamado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataSolicitacao.hashCode()+profissionalSolicitante.hashCode();
	}

	@Override
	public String toString() {
		return "Assunto: ".concat(assunto);
	}
}
