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
@Table(name = "tb_funcao_profissional", schema="administrativo")
public class FuncaoProfissional implements Serializable {
	private static final long serialVersionUID = -1047225630657413452L;
	
	private int idFuncaoProfissional;
	private Funcao funcao;
	private Profissional profissional;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_funcao_profissional_id_funcao_profissional_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_funcao_profissional", unique = true, nullable = false)
	public int getIdFuncaoProfissional() {
		return this.idFuncaoProfissional;
	}
	
	public void setIdFuncaoProfissional(int idFuncaoProfissional){
		this.idFuncaoProfissional = idFuncaoProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcao")
	public Funcao getFuncao(){
		return funcao;
	}
	
	public void setFuncao(Funcao funcao){
		this.funcao = funcao;
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
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + idFuncaoProfissional;
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
		FuncaoProfissional other = (FuncaoProfissional) obj;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (idFuncaoProfissional != other.idFuncaoProfissional)
			return false;
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
			return false;
		return true;
	}
	
}
