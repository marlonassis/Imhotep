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
@Table(name = "tb_estrutura_organizacional_funcao", schema="administrativo")
public class EstruturaOrganizacionalFuncao  implements Serializable {
	private static final long serialVersionUID = 8407298428377467384L;
	
	private int idEstruturaOrganizacionalFuncao;
	private EstruturaOrganizacional estruturaOrganizacional;
	private Funcao funcao;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_estrutura_organizacional_f_id_estrutura_organizacional_f_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_estrutura_organizacional_funcao", unique = true, nullable = false)
	public int getIdEstruturaOrganizacionalFuncao() {
		return idEstruturaOrganizacionalFuncao;
	}
	public void setIdEstruturaOrganizacionalFuncao(int idEstruturaOrganizacionalFuncao) {
		this.idEstruturaOrganizacionalFuncao = idEstruturaOrganizacionalFuncao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional")
	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}
	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_funcao")
	public Funcao getFuncao() {
		return funcao;
	}
	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + ((funcao == null) ? 0 : funcao.hashCode());
		result = prime * result + idEstruturaOrganizacionalFuncao;
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
		EstruturaOrganizacionalFuncao other = (EstruturaOrganizacionalFuncao) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (funcao == null) {
			if (other.funcao != null)
				return false;
		} else if (!funcao.equals(other.funcao))
			return false;
		if (idEstruturaOrganizacionalFuncao != other.idEstruturaOrganizacionalFuncao)
			return false;
		return true;
	}
	
}
