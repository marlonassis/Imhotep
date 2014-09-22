package br.com.imhotep.entidade;

import java.io.Serializable;
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

import br.com.imhotep.enums.TipoEstoqueAlmoxarifadoLog;

@Entity
@Table(name = "tb_estoque_almoxarifado_log", schema="almoxarifado")
public class EstoqueAlmoxarifadoLog implements Serializable {
	private static final long serialVersionUID = 3805047824707532787L;
	
	private int idEstoqueAlmoxarifadoLog;
	private String lote;
	private String material;
	private String codigoBarras;
	private Profissional profissionalAlteracao;
	private Date dataLog;
	private TipoEstoqueAlmoxarifadoLog tipoAlteracao;
	private String dataValidade;
	  
	@SequenceGenerator(name = "generator", sequenceName = "almoxarifado.tb_estoque_almoxarifado_log_id_estoque_almoxarifado_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_almoxarifado_log", unique = true, nullable = false)
	public int getIdEstoqueAlmoxarifadoLog() {
		return this.idEstoqueAlmoxarifadoLog;
	}
	
	public void setIdEstoqueAlmoxarifadoLog(int idEstoqueAlmoxarifadoLog){
		this.idEstoqueAlmoxarifadoLog = idEstoqueAlmoxarifadoLog;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_alteracao")
	public Profissional getProfissionalAlteracao(){
		return profissionalAlteracao;
	}
	
	public void setProfissionalAlteracao(Profissional profissionalAlteracao){
		this.profissionalAlteracao = profissionalAlteracao;
	}
	
	@Column(name = "cv_lote")
	public String getLote(){
		return lote;
	}
	
	public void setLote(String lote){
		this.lote = lote;
	}
	
	@Column(name = "cv_material")
	public String getMaterial(){
		return material;
	}
	
	public void setMaterial(String material){
		this.material = material;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_log")
	public Date getDataLog() {
		return this.dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}
	
	@Column(name = "tp_tipo_alteracao")
	@Enumerated(EnumType.STRING)
	public TipoEstoqueAlmoxarifadoLog getTipoAlteracao() {
		return tipoAlteracao;
	}
	public void setTipoAlteracao(TipoEstoqueAlmoxarifadoLog tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
	}
	
	@Column(name = "cv_data_validade")
	public String getDataValidade(){
		return dataValidade;
	}
	
	public void setDataValidade(String dataValidade){
		this.dataValidade = dataValidade;
	}
	
	@Column(name="cv_codigo_barras")
	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoBarras == null) ? 0 : codigoBarras.hashCode());
		result = prime * result + ((dataLog == null) ? 0 : dataLog.hashCode());
		result = prime * result
				+ ((dataValidade == null) ? 0 : dataValidade.hashCode());
		result = prime * result + idEstoqueAlmoxarifadoLog;
		result = prime * result + ((lote == null) ? 0 : lote.hashCode());
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime
				* result
				+ ((profissionalAlteracao == null) ? 0 : profissionalAlteracao
						.hashCode());
		result = prime * result
				+ ((tipoAlteracao == null) ? 0 : tipoAlteracao.hashCode());
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
		EstoqueAlmoxarifadoLog other = (EstoqueAlmoxarifadoLog) obj;
		if (codigoBarras == null) {
			if (other.codigoBarras != null)
				return false;
		} else if (!codigoBarras.equals(other.codigoBarras))
			return false;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (dataValidade == null) {
			if (other.dataValidade != null)
				return false;
		} else if (!dataValidade.equals(other.dataValidade))
			return false;
		if (idEstoqueAlmoxarifadoLog != other.idEstoqueAlmoxarifadoLog)
			return false;
		if (lote == null) {
			if (other.lote != null)
				return false;
		} else if (!lote.equals(other.lote))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (profissionalAlteracao == null) {
			if (other.profissionalAlteracao != null)
				return false;
		} else if (!profissionalAlteracao.equals(other.profissionalAlteracao))
			return false;
		if (tipoAlteracao != other.tipoAlteracao)
			return false;
		return true;
	}

}
