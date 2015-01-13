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
@Table(name = "tb_acesso_funcao_painel", schema="controle")
public class AcessoFuncaoPainel implements Serializable {
	private static final long serialVersionUID = 2199580781881032049L;
	
	private int idAcessoFuncaoPainel;
	private EstruturaOrganizacionalPainel estruturaOrganizacionalPainel;
	private EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_funcao_painel_id_acesso_funcao_painel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_funcao_painel", unique = true, nullable = false)
	public int getIdAcessoFuncaoPainel() {
		return idAcessoFuncaoPainel;
	}
	
	public void setIdAcessoFuncaoPainel(int idAcessoFuncaoPainel) {
		this.idAcessoFuncaoPainel = idAcessoFuncaoPainel;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional_painel")
	public EstruturaOrganizacionalPainel getEstruturaOrganizacionalPainel() {
		return estruturaOrganizacionalPainel;
	}
	
	public void setEstruturaOrganizacionalPainel(EstruturaOrganizacionalPainel estruturaOrganizacionalPainel) {
		this.estruturaOrganizacionalPainel = estruturaOrganizacionalPainel;
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
				+ ((estruturaOrganizacionalPainel == null) ? 0
						: estruturaOrganizacionalPainel.hashCode());
		result = prime * result + idAcessoFuncaoPainel;
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
		AcessoFuncaoPainel other = (AcessoFuncaoPainel) obj;
		if (estruturaOrganizacionalFuncao == null) {
			if (other.estruturaOrganizacionalFuncao != null)
				return false;
		} else if (!estruturaOrganizacionalFuncao
				.equals(other.estruturaOrganizacionalFuncao))
			return false;
		if (estruturaOrganizacionalPainel == null) {
			if (other.estruturaOrganizacionalPainel != null)
				return false;
		} else if (!estruturaOrganizacionalPainel
				.equals(other.estruturaOrganizacionalPainel))
			return false;
		if (idAcessoFuncaoPainel != other.idAcessoFuncaoPainel)
			return false;
		return true;
	}
	
}
