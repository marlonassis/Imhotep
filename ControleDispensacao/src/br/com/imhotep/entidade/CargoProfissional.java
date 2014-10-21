package br.com.imhotep.entidade;

import java.io.Serializable;

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
@Table(name = "tb_cargo_profissional", schema="administrativo")
public class CargoProfissional implements Serializable {
	private static final long serialVersionUID = -1047225630657413452L;
	
	private int idCargoProfissional;
	private Cargo cargo;
	private Profissional profissional;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_cargo_profissional_id_cargo_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cargo_profissional", unique = true, nullable = false)
	public int getIdCargoProfissional() {
		return this.idCargoProfissional;
	}
	
	public void setIdCargoProfissional(int idCargoProfissional){
		this.idCargoProfissional = idCargoProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo")
	public Cargo getCargo(){
		return cargo;
	}
	
	public void setCargo(Cargo cargo){
		this.cargo = cargo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional(){
		return profissional;
	}
	
	public void setProfissional(Profissional profissional){
		this.profissional = profissional;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + idCargoProfissional;
		result = prime * result
				+ ((profissional == null) ? 0 : profissional.hashCode());
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
		CargoProfissional other = (CargoProfissional) obj;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (idCargoProfissional != other.idCargoProfissional)
			return false;
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
			return false;
		return true;
	}
	
}
