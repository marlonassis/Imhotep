package br.com.ControleDispensacao.entidade;

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
@Table(name = "tb_especialidade")
public class Especialidade {
	private int idEspecialidade;
	private TipoProfissional tipoProfissional;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_especialidade_id_especialidade_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_especialidade", unique = true, nullable = false)
	public int getIdEspecialidade() {
		return idEspecialidade;
	}
	public void setIdEspecialidade(int idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_profissional")
	public TipoProfissional getTipoProfissional() {
		return tipoProfissional;
	}
	public void setTipoProfissional(TipoProfissional tipoProfissional) {
		this.tipoProfissional = tipoProfissional;
	}
	
	@Column(name = "ds_descricao", length = 50)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Especialidade))
			return false;
		
		return ((Especialidade)obj).getIdEspecialidade() == this.idEspecialidade;
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