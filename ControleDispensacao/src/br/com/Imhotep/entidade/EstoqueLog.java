package br.com.Imhotep.entidade;

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

import br.com.Imhotep.enums.TipoEstoqueLog;

@Entity
@Table(name = "tb_estoque_log")
public class EstoqueLog {
	
	private int idEstoqueLog;
	private String lote;
	private String material;
	private Profissional profissionalAlteracao;
	private Date dataLog;
	private TipoEstoqueLog tipoAlteracao;
	  
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_estoque_log_id_estoque_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estoque_log", unique = true, nullable = false)
	public int getIdEstoqueLog() {
		return this.idEstoqueLog;
	}
	
	public void setIdEstoqueLog(int idEstoqueLog){
		this.idEstoqueLog = idEstoqueLog;
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
	public TipoEstoqueLog getTipoAlteracao() {
		return tipoAlteracao;
	}
	public void setTipoAlteracao(TipoEstoqueLog tipoAlteracao) {
		this.tipoAlteracao = tipoAlteracao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EstoqueLog))
			return false;
		
		return ((EstoqueLog)obj).getIdEstoqueLog() == this.idEstoqueLog;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + lote.hashCode() + dataLog.hashCode() + tipoAlteracao.hashCode();
	}

	@Override
	public String toString() {
		return lote;
	}
	
}
