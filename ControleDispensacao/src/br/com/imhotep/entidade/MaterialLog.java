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

import br.com.imhotep.enums.TipoEstoqueLog;

@Entity
@Table(name = "tb_material_log")
public class MaterialLog {
	
	private int idMaterialLog;
	private String nome;
	private String codigo;
	private Date dataLog;
	private String unidade;
	private Profissional profissionalAlteracao;
	private TipoEstoqueLog tipoLog;
	private boolean bloqueado;
	private Material material;
	  
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_material_log_id_material_log_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_material_log", unique = true, nullable = false)
	public int getIdMaterialLog() {
		return this.idMaterialLog;
	}
	
	public void setIdMaterialLog(int idMaterialLog){
		this.idMaterialLog = idMaterialLog;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial(){
		return material;
	}
	
	public void setMaterial(Material material){
		this.material = material;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_alteracao")
	public Profissional getProfissionalAlteracao(){
		return profissionalAlteracao;
	}
	
	public void setProfissionalAlteracao(Profissional profissionalAlteracao){
		this.profissionalAlteracao = profissionalAlteracao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_log")
	public Date getDataLog() {
		return this.dataLog;
	}

	public void setDataLog(Date dataLog) {
		this.dataLog = dataLog;
	}
	
	@Column(name = "tp_tipo_log")
	@Enumerated(EnumType.STRING)
	public TipoEstoqueLog getTipoLog() {
		return tipoLog;
	}
	public void setTipoLog(TipoEstoqueLog tipoLog) {
		this.tipoLog = tipoLog;
	}
	
	@Column(name="cv_codigo")
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="cv_nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name="cv_unidade")
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	
	@Column(name="bl_bloqueado")
	public boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bloqueado ? 1231 : 1237);
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataLog == null) ? 0 : dataLog.hashCode());
		result = prime * result + idMaterialLog;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime
				* result
				+ ((profissionalAlteracao == null) ? 0 : profissionalAlteracao
						.hashCode());
		result = prime * result + ((tipoLog == null) ? 0 : tipoLog.hashCode());
		result = prime * result + ((unidade == null) ? 0 : unidade.hashCode());
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
		MaterialLog other = (MaterialLog) obj;
		if (bloqueado != other.bloqueado)
			return false;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataLog == null) {
			if (other.dataLog != null)
				return false;
		} else if (!dataLog.equals(other.dataLog))
			return false;
		if (idMaterialLog != other.idMaterialLog)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (profissionalAlteracao == null) {
			if (other.profissionalAlteracao != null)
				return false;
		} else if (!profissionalAlteracao.equals(other.profissionalAlteracao))
			return false;
		if (tipoLog != other.tipoLog)
			return false;
		if (unidade == null) {
			if (other.unidade != null)
				return false;
		} else if (!unidade.equals(other.unidade))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return tipoLog.getLabel();
	}
	
}
