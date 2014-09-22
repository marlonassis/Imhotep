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
@Table(name = "tb_funcao", schema="administrativo")
public class Funcao implements Serializable {
	private static final long serialVersionUID = -5214294578054650936L;
	
	private int idFuncao;
	private Funcao funcaoPai;
	private String nome;

	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_funcao_id_funcao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_funcao", unique = true, nullable = false)
	public int getIdFuncao() {
		return idFuncao;
	}
	
	public void setIdFuncao(int idFuncao) {
		this.idFuncao = idFuncao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcao_pai")
	public Funcao getFuncaoPai() {
		return funcaoPai;
	}

	public void setFuncaoPai(Funcao funcaoPai) {
		this.funcaoPai = funcaoPai;
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
		if(getFuncaoPai() != null)
			return "->" + nome;
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((funcaoPai == null) ? 0 : funcaoPai.hashCode());
		result = prime * result + idFuncao;
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
		Funcao other = (Funcao) obj;
		if (funcaoPai == null) {
			if (other.funcaoPai != null)
				return false;
		} else if (!funcaoPai.equals(other.funcaoPai))
			return false;
		if (idFuncao != other.idFuncao)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
