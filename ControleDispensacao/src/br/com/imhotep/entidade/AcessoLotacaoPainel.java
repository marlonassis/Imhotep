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
@Table(name = "tb_acesso_lotacao_painel", schema="controle")
public class AcessoLotacaoPainel implements Serializable {
	private static final long serialVersionUID = 8776615878550305977L;
	
	private int idAcessoLotacaoPainel;
	private EstruturaOrganizacionalPainel estruturaOrganizacionalPainel;
	private LotacaoProfissional lotacaoProfissional;
	
	@SequenceGenerator(name = "generator", sequenceName = "controle.tb_acesso_lotacao_painel_id_acesso_lotacao_painel_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_acesso_lotacao_painel", unique = true, nullable = false)
	public int getIdAcessoLotacaoPainel() {
		return idAcessoLotacaoPainel;
	}
	public void setIdAcessoLotacaoPainel(int idAcessoLotacaoPainel) {
		this.idAcessoLotacaoPainel = idAcessoLotacaoPainel;
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
				+ ((estruturaOrganizacionalPainel == null) ? 0
						: estruturaOrganizacionalPainel.hashCode());
		result = prime * result + idAcessoLotacaoPainel;
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
		AcessoLotacaoPainel other = (AcessoLotacaoPainel) obj;
		if (estruturaOrganizacionalPainel == null) {
			if (other.estruturaOrganizacionalPainel != null)
				return false;
		} else if (!estruturaOrganizacionalPainel
				.equals(other.estruturaOrganizacionalPainel))
			return false;
		if (idAcessoLotacaoPainel != other.idAcessoLotacaoPainel)
			return false;
		if (lotacaoProfissional == null) {
			if (other.lotacaoProfissional != null)
				return false;
		} else if (!lotacaoProfissional.equals(other.lotacaoProfissional))
			return false;
		return true;
	}
	
}
