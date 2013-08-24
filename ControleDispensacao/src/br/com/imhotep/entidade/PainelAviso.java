package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Transient;

@Entity
@Table(name = "tb_painel_aviso")
public class PainelAviso implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int idPainelAviso;
	private String descricao;
	private Date dataInsercao;
	private Profissional profissionalInsercao;
	private Date dataInicio;
	private Date dataFim;

	private Set<PainelAvisoEspecialidade> especialidades;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_painel_aviso_id_painel_aviso_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_painel_aviso", unique = true, nullable = false)
	public int getIdPainelAviso() {
		return idPainelAviso;
	}
	public void setIdPainelAviso(int idPainelAviso) {
		this.idPainelAviso = idPainelAviso;
	}
	
	@Column(name="cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_data_insercao")
	public Date getDataInsercao() {
		return dataInsercao;
	}
	public void setDataInsercao(Date dataInsercao) {
		this.dataInsercao = dataInsercao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_insercao")
	public Profissional getProfissionalInsercao() {
		return profissionalInsercao;
	}
	public void setProfissionalInsercao(Profissional profissionalInsercao) {
		this.profissionalInsercao = profissionalInsercao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_data_inicio")
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_data_fim")
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "painelAviso")
	public Set<PainelAvisoEspecialidade> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(Set<PainelAvisoEspecialidade> especialidades) {
		this.especialidades = especialidades;
	}
	
	@Transient
	public List<PainelAvisoEspecialidade> getEspecialidadesList(){
		if(getEspecialidades() != null){
			return new ArrayList<PainelAvisoEspecialidade>(getEspecialidades());
		}
		return new ArrayList<PainelAvisoEspecialidade>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof PainelAviso))
			return false;
		
		return ((PainelAviso)obj).getIdPainelAviso() == this.idPainelAviso;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + dataInsercao.hashCode() + profissionalInsercao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
