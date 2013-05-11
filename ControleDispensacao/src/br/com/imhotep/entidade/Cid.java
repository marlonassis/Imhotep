package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_cid_10")
public class Cid {
	
	private int idCid;
	private String nome;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_cid_10_id_cid_10_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_cid_10", unique = true, nullable = false)
	public int getIdCid() {
		return idCid;
	}
	public void setIdCid(int idCid) {
		this.idCid = idCid;
	}
	
	@Column(name = "cv_nome")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Cid))
			return false;
		
		return ((Cid)obj).getIdCid() == this.idCid;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
