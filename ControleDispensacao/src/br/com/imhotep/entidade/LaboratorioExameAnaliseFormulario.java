package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "tb_laboratorio_exame_analise_formulario", schema="laboratorio")
public class LaboratorioExameAnaliseFormulario implements Serializable {
	private static final long serialVersionUID = 6581105142029715998L;
	
	private int idLaboratorioExameAnaliseFormulario;
	private String descricao;
	private String observacao;
	private Set<LaboratorioFormularioItem> itens;
	
	@SequenceGenerator(name = "generator", sequenceName = "laboratorio.tb_laboratorio_exame_analise__id_laboratorio_exame_analise__seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_laboratorio_exame_analise_formulario", unique = true, nullable = false)
	public int getIdLaboratorioExameAnaliseFormulario() {
		return idLaboratorioExameAnaliseFormulario;
	}

	public void setIdLaboratorioExameAnaliseFormulario(
			int idLaboratorioExameAnaliseFormulario) {
		this.idLaboratorioExameAnaliseFormulario = idLaboratorioExameAnaliseFormulario;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "cv_observacao")
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "formulario")
	@Fetch(FetchMode.SUBSELECT)
	public Set<LaboratorioFormularioItem> getItens() {
		return itens;
	}
	
	public void setItens(Set<LaboratorioFormularioItem> itens) {
		this.itens = itens;
	}
	
	@Transient
	public List<LaboratorioFormularioItem> getItensLista(){
		if(getItens() != null)
			return new ArrayList<LaboratorioFormularioItem>(getItens());
		return new ArrayList<LaboratorioFormularioItem>();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idLaboratorioExameAnaliseFormulario;
		result = prime * result
				+ ((observacao == null) ? 0 : observacao.hashCode());
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
		LaboratorioExameAnaliseFormulario other = (LaboratorioExameAnaliseFormulario) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idLaboratorioExameAnaliseFormulario != other.idLaboratorioExameAnaliseFormulario)
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		return true;
	}
	
}
