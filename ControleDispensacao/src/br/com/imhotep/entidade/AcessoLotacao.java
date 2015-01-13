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
@Table(name = "tb_acesso_lotacao", schema="controle")
public class AcessoLotacao implements Serializable {
	private static final long serialVersionUID = 190209614333181693L;
	
	private int idAcessoLotacao;
	private EstruturaOrganizacionalMenu estruturaOrganizacionalMenu;
	private LotacaoProfissional lotacaoProfissional;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_lotacao_funcao_id_acesso_lotacao_funcao_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_lotacao", unique = true, nullable = false)
	public int getIdAcessoLotacao() {
		return idAcessoLotacao;
	}
	public void setIdAcessoLotacao(int idAcessoLotacao) {
		this.idAcessoLotacao = idAcessoLotacao;
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
	@JoinColumn(name = "id_lotacao_profissional")
	public LotacaoProfissional getLotacaoProfissional() {
		return lotacaoProfissional;
	}

	public void setLotacaoProfissional(LotacaoProfissional lotacaoProfissional) {
		this.lotacaoProfissional = lotacaoProfissional;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacionalMenu == null) ? 0
						: estruturaOrganizacionalMenu.hashCode());
		result = prime * result + idAcessoLotacao;
		result = prime
				* result
				+ ((lotacaoProfissional == null) ? 0 : lotacaoProfissional
						.hashCode());
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
		AcessoLotacao other = (AcessoLotacao) obj;
		if (estruturaOrganizacionalMenu == null) {
			if (other.estruturaOrganizacionalMenu != null)
				return false;
		} else if (!estruturaOrganizacionalMenu
				.equals(other.estruturaOrganizacionalMenu))
			return false;
		if (idAcessoLotacao != other.idAcessoLotacao)
			return false;
		if (lotacaoProfissional == null) {
			if (other.lotacaoProfissional != null)
				return false;
		} else if (!lotacaoProfissional.equals(other.lotacaoProfissional))
			return false;
		return true;
	}
	
}
