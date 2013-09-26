package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_fabricante_almoxarifado")
public class FabricanteAlmoxarifado implements Serializable{
	private static final long serialVersionUID = -1634025937505079027L;
	
	private int idFabricanteAlmoxarifado;
	private String descricao;

	@SequenceGenerator(name = "generator", sequenceName = "public.tb_fabricante_almoxarifado_id_fabricante_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_fabricante_almoxarifado", unique = true, nullable = false)
	public int getIdFabricanteAlmoxarifado() {
		return this.idFabricanteAlmoxarifado;
	}
	
	public void setIdFabricanteAlmoxarifado(int idFabricanteAlmoxarifado){
		this.idFabricanteAlmoxarifado = idFabricanteAlmoxarifado;
	}

	@Column(name = "cv_descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof FabricanteAlmoxarifado))
			return false;
		
		return ((FabricanteAlmoxarifado)obj).getIdFabricanteAlmoxarifado() == this.idFabricanteAlmoxarifado;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
	
}
