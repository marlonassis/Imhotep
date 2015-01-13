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
@Table(name = "tb_acesso_funcao", schema="controle")
public class AcessoFuncao implements Serializable {
	private static final long serialVersionUID = -2628852328484952903L;
	
	private int idAcessoFuncao;
	private EstruturaOrganizacionalMenu estruturaOrganizacionalMenu;
	private EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_funcao_id_acesso_funcao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_funcao", unique = true, nullable = false)
	public int getIdAcessoFuncao() {
		return idAcessoFuncao;
	}
	
	public void setIdAcessoFuncao(int idAcessoFuncao) {
		this.idAcessoFuncao = idAcessoFuncao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional_menu")
	public EstruturaOrganizacionalMenu getEstruturaOrganizacionalMenu() {
		return estruturaOrganizacionalMenu;
	}
	
	public void setEstruturaOrganizacionalMenu(EstruturaOrganizacionalMenu estruturaOrganizacionalMenu) {
		this.estruturaOrganizacionalMenu = estruturaOrganizacionalMenu;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional_funcao")
	public EstruturaOrganizacionalFuncao getEstruturaOrganizacionalFuncao() {
		return estruturaOrganizacionalFuncao;
	}
	
	public void setEstruturaOrganizacionalFuncao(EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao) {
		this.estruturaOrganizacionalFuncao = estruturaOrganizacionalFuncao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacionalFuncao == null) ? 0
						: estruturaOrganizacionalFuncao.hashCode());
		result = prime
				* result
				+ ((estruturaOrganizacionalMenu == null) ? 0
						: estruturaOrganizacionalMenu.hashCode());
		result = prime * result + idAcessoFuncao;
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
		AcessoFuncao other = (AcessoFuncao) obj;
		if (estruturaOrganizacionalFuncao == null) {
			if (other.estruturaOrganizacionalFuncao != null)
				return false;
		} else if (!estruturaOrganizacionalFuncao
				.equals(other.estruturaOrganizacionalFuncao))
			return false;
		if (estruturaOrganizacionalMenu == null) {
			if (other.estruturaOrganizacionalMenu != null)
				return false;
		} else if (!estruturaOrganizacionalMenu
				.equals(other.estruturaOrganizacionalMenu))
			return false;
		if (idAcessoFuncao != other.idAcessoFuncao)
			return false;
		return true;
	}
	
}
