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
@Table(name = "tb_acesso_funcao_variavel", schema="controle")
public class AcessoFuncaoVariavel implements Serializable {
	private static final long serialVersionUID = -248920151766886850L;
	
	private int idAcessoFuncaoVariavel;
	private AcessoFuncao acessoFuncao;
	private MenuVariavel menuVariavel;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_funcao_variavel_id_acesso_funcao_variavel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_funcao_variavel", unique = true, nullable = false)
	public int getIdAcessoFuncaoVariavel() {
		return idAcessoFuncaoVariavel;
	}
	
	public void setIdAcessoFuncaoVariavel(int idAcessoFuncaoVariavel) {
		this.idAcessoFuncaoVariavel = idAcessoFuncaoVariavel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_acesso_funcao")
	public AcessoFuncao getAcessoFuncao() {
		return acessoFuncao;
	}
	
	public void setAcessoFuncao(AcessoFuncao acessoFuncao) {
		this.acessoFuncao = acessoFuncao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_menu_variavel")
	public MenuVariavel getMenuVariavel() {
		return menuVariavel;
	}
	
	public void setMenuVariavel(MenuVariavel menuVariavel) {
		this.menuVariavel = menuVariavel;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acessoFuncao == null) ? 0 : acessoFuncao.hashCode());
		result = prime * result + idAcessoFuncaoVariavel;
		result = prime * result
				+ ((menuVariavel == null) ? 0 : menuVariavel.hashCode());
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
		AcessoFuncaoVariavel other = (AcessoFuncaoVariavel) obj;
		if (acessoFuncao == null) {
			if (other.acessoFuncao != null)
				return false;
		} else if (!acessoFuncao.equals(other.acessoFuncao))
			return false;
		if (idAcessoFuncaoVariavel != other.idAcessoFuncaoVariavel)
			return false;
		if (menuVariavel == null) {
			if (other.menuVariavel != null)
				return false;
		} else if (!menuVariavel.equals(other.menuVariavel))
			return false;
		return true;
	}
	
}
