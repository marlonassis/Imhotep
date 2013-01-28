package br.com.Imhotep.entidade;

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
@Table(name = "tb_libera_material_especialidade")
public class LiberaMaterialEspecialidade {
	private int idLiberaMaterialEspecialidade;
	private Especialidade especialidade;
	private Material material;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_libera_material_especialid_id_libera_material_especialid_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_libera_material_especialidade", unique = true, nullable = false)
	public int getIdLiberaMaterialEspecialidade() {
		return this.idLiberaMaterialEspecialidade;
	}
	
	public void setIdLiberaMaterialEspecialidade(int idLiberaMaterialEspecialidade){
		this.idLiberaMaterialEspecialidade = idLiberaMaterialEspecialidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_especialidade")
	public Especialidade getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof LiberaMaterialEspecialidade))
			return false;
		
		return ((LiberaMaterialEspecialidade)obj).getIdLiberaMaterialEspecialidade() == this.idLiberaMaterialEspecialidade;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + material.getDescricao().hashCode();
	}

	@Override
	public String toString() {
		return especialidade.getDescricao().concat(" - ").concat(material.getDescricao());
	}
}
