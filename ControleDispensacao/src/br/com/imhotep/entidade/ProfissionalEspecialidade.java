package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_profissional_especialidade")
public class ProfissionalEspecialidade {
	private int idProfissionalEspecialidade;
	private Profissional profissional; 
	private Especialidade especialidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_profissional_especialidade_id_profissional_especialidade_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_profissional_especialidade", unique = true, nullable = false)
	public int getIdProfissionalEspecialidade() {
		return idProfissionalEspecialidade;
	}
	
	public void setIdProfissionalEspecialidade(int idProfissionalEspecialidade) {
		this.idProfissionalEspecialidade = idProfissionalEspecialidade;
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
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof ProfissionalEspecialidade))
			return false;
		
		return ((ProfissionalEspecialidade)obj).getIdProfissionalEspecialidade() == this.idProfissionalEspecialidade;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + especialidade.hashCode() + profissional.hashCode();
	}

	@Override
	public String toString() {
		return profissional.getNome();
	}
}
