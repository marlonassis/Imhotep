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
@Table(name = "tb_laboratorio_formulario_item", schema="laboratorio")
public class LaboratorioFormularioItem implements Serializable {
	private static final long serialVersionUID = -279932329067144623L;
	
	private int idLaboratorioFormularioItem;
	private LaboratorioExameAnaliseFormulario formulario;
	private LaboratorioExameAnaliseItem item;
	private LaboratorioExameAnaliseGrupo grupo;

	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_formulario_ite_id_laboratorio_formulario_ite_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_formulario_item", unique = true, nullable = false)
	public int getIdLaboratorioFormularioItem() {
		return idLaboratorioFormularioItem;
	}
	public void setIdLaboratorioFormularioItem(int idLaboratorioFormularioItem) {
		this.idLaboratorioFormularioItem = idLaboratorioFormularioItem;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame_analise_formulario")
	public LaboratorioExameAnaliseFormulario getFormulario() {
		return formulario;
	}
	public void setFormulario(LaboratorioExameAnaliseFormulario formulario) {
		this.formulario = formulario;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame_analise_item")
	public LaboratorioExameAnaliseItem getItem() {
		return item;
	}
	public void setItem(LaboratorioExameAnaliseItem item) {
		this.item = item;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_laboratorio_exame_analise_grupo")
	public LaboratorioExameAnaliseGrupo getGrupo() {
		return grupo;
	}
	public void setGrupo(LaboratorioExameAnaliseGrupo grupo) {
		this.grupo = grupo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((formulario == null) ? 0 : formulario.hashCode());
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + idLaboratorioFormularioItem;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
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
		LaboratorioFormularioItem other = (LaboratorioFormularioItem) obj;
		if (formulario == null) {
			if (other.formulario != null)
				return false;
		} else if (!formulario.equals(other.formulario))
			return false;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (idLaboratorioFormularioItem != other.idLaboratorioFormularioItem)
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		return true;
	}
	
}
