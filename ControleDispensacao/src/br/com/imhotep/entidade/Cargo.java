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
import javax.persistence.Transient;

@Entity
@Table(name = "tb_cargo", schema="administrativo")
public class Cargo implements Serializable {
	private static final long serialVersionUID = -5214294578054650936L;
	
	private int idCargo;
	private Cargo cargoPai;
	private String nome;

	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_cargo_id_cargo_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cargo", unique = true, nullable = false)
	public int getIdCargo() {
		return idCargo;
	}
	
	public void setIdCargo(int idCargo) {
		this.idCargo = idCargo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cargo_pai")
	public Cargo getCargoPai() {
		return cargoPai;
	}

	public void setCargoPai(Cargo cargoPai) {
		this.cargoPai = cargoPai;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Transient
	public String getNomeFilhoSeta(){
		if(getCargoPai() != null)
			return "->" + nome;
		return nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cargoPai == null) ? 0 : cargoPai.hashCode());
		result = prime * result + idCargo;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cargo other = (Cargo) obj;
		if (cargoPai == null) {
			if (other.cargoPai != null)
				return false;
		} else if (!cargoPai.equals(other.cargoPai))
			return false;
		if (idCargo != other.idCargo)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
}
