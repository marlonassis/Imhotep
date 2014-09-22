package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_laboratorio_exame_analise_grupo", schema="laboratorio")
public class LaboratorioExameAnaliseGrupo implements Serializable {
	private static final long serialVersionUID = -546772428024066061L;
	
	private int idLaboratorioExameAnaliseGrupo;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_exame_analise__id_laboratorio_exame_analise_seq1")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_exame_analise_grupo", unique = true, nullable = false)
	public int getIdLaboratorioExameAnaliseGrupo() {
		return idLaboratorioExameAnaliseGrupo;
	}

	public void setIdLaboratorioExameAnaliseGrupo(
			int idLaboratorioExameAnaliseGrupo) {
		this.idLaboratorioExameAnaliseGrupo = idLaboratorioExameAnaliseGrupo;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idLaboratorioExameAnaliseGrupo;
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
		LaboratorioExameAnaliseGrupo other = (LaboratorioExameAnaliseGrupo) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idLaboratorioExameAnaliseGrupo != other.idLaboratorioExameAnaliseGrupo)
			return false;
		return true;
	}

}
