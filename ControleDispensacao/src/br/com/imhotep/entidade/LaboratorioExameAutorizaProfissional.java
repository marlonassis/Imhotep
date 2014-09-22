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

import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoItemExameEnum;

@Entity
@Table(name = "tb_laboratorio_exame_autoriza_profissional", schema="laboratorio")
public class LaboratorioExameAutorizaProfissional {
	private int idLaboratorioExameAutorizaProfissional;
	private Profissional profissional;
	private TipoAutorizacaoSolicitacaoExameEnum faseSolicitacao;
	private TipoAutorizacaoSolicitacaoItemExameEnum faseSolicitacaoItem;
	private Profissional profissionalCadastro;
	private Date dataCadastro;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_exame_autoriza_id_laboratorio_exame_autoriza_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_exame_autoriza_profissional", unique = true, nullable = false)
	public int getIdLaboratorioExameAutorizaProfissional() {
		return this.idLaboratorioExameAutorizaProfissional;
	}
	
	public void setIdLaboratorioExameAutorizaProfissional(int idLaboratorioExameAutorizaProfissional){
		this.idLaboratorioExameAutorizaProfissional = idLaboratorioExameAutorizaProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Column(name = "tp_fase_solicitacao")
	@Enumerated(EnumType.STRING)
	public TipoAutorizacaoSolicitacaoExameEnum getFaseSolicitacao() {
		return faseSolicitacao;
	}
	public void setFaseSolicitacao(TipoAutorizacaoSolicitacaoExameEnum faseSolicitacao) {
		this.faseSolicitacao = faseSolicitacao;
	}
	
	@Column(name = "tp_fase_solicitacao_item")
	@Enumerated(EnumType.STRING)
	public TipoAutorizacaoSolicitacaoItemExameEnum getFaseSolicitacaoItem() {
		return faseSolicitacaoItem;
	}
	public void setFaseSolicitacaoItem(TipoAutorizacaoSolicitacaoItemExameEnum faseSolicitacaoItem) {
		this.faseSolicitacaoItem = faseSolicitacaoItem;
	}

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	
}
