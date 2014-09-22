package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.Id;

import br.com.imhotep.entidade.Profissional;

import javax.persistence.SequenceGenerator;

import br.com.imhotep.entidade.LaboratorioSolicitacaoItem;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;


@Entity
@Table(name = "tb_laboratorio_solicitacao_item_resultado", schema="laboratorio")
public class LaboratorioSolicitacaoItemResultado implements Serializable {
	private static final long serialVersionUID = 1806004006692682914L;
	
	private int idLaboratorioSolicitacaoItemResultado;
	private LaboratorioSolicitacaoItem laboratorioSolicitacaoItem;
	private String resultado;
	private String observacao;
	private Date dataCadastro;
	private Profissional profissionalCadastro;
	private Boolean resultadoRejeitado;

	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_solicitacao_it_id_laboratorio_solicitacao_i_seq1")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_solicitacao_item_resultado", unique = true, nullable = false)	
	public int getIdLaboratorioSolicitacaoItemResultado() {
		return idLaboratorioSolicitacaoItemResultado;
	}

	public void setIdLaboratorioSolicitacaoItemResultado(int idLaboratorioSolicitacaoItemResultado) {
		this.idLaboratorioSolicitacaoItemResultado = idLaboratorioSolicitacaoItemResultado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_solicitacao_item")	
	public LaboratorioSolicitacaoItem getLaboratorioSolicitacaoItem() {
		return laboratorioSolicitacaoItem;
	}

	public void setLaboratorioSolicitacaoItem(LaboratorioSolicitacaoItem laboratorioSolicitacaoItem) {
		this.laboratorioSolicitacaoItem = laboratorioSolicitacaoItem;
	}

	@Column(name = "cv_resultado")	
	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	@Column(name = "cv_observacao")	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")	
	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")	
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}

	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}

	@Column(name="bl_resultado_rejeitado", nullable=false) 	
	public Boolean getResultadoRejeitado() {
		return resultadoRejeitado;
	}

	public void setResultadoRejeitado(Boolean resultadoRejeitado) {
		this.resultadoRejeitado = resultadoRejeitado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result + idLaboratorioSolicitacaoItemResultado;
		result = prime
				* result
				+ ((laboratorioSolicitacaoItem == null) ? 0
						: laboratorioSolicitacaoItem.hashCode());
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime * result
				+ ((resultado == null) ? 0 : resultado.hashCode());
		result = prime
				* result
				+ ((resultadoRejeitado == null) ? 0 : resultadoRejeitado
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LaboratorioSolicitacaoItemResultado other = (LaboratorioSolicitacaoItemResultado) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (idLaboratorioSolicitacaoItemResultado != other.idLaboratorioSolicitacaoItemResultado)
			return false;
		if (laboratorioSolicitacaoItem == null) {
			if (other.laboratorioSolicitacaoItem != null)
				return false;
		} else if (!laboratorioSolicitacaoItem
				.equals(other.laboratorioSolicitacaoItem))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (resultado == null) {
			if (other.resultado != null)
				return false;
		} else if (!resultado.equals(other.resultado))
			return false;
		if (resultadoRejeitado == null) {
			if (other.resultadoRejeitado != null)
				return false;
		} else if (!resultadoRejeitado.equals(other.resultadoRejeitado))
			return false;
		return true;
	}

}
