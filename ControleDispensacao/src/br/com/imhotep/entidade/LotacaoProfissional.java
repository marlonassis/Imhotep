package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.imhotep.enums.TipoLotacaoProfissionalEnum;

@Entity
@Table(name = "tb_lotacao_profissional", schema="administrativo")
public class LotacaoProfissional  implements Serializable {
	private static final long serialVersionUID = -1182891254213741916L;
	
	private int idLotacaoProfissional;
	private EstruturaOrganizacional estruturaOrganizacional;
	private Profissional profissional;
	private TipoLotacaoProfissionalEnum tipoLotacao;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_lotacao_profissional_id_lotacao_profissional_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_lotacao_profissional", unique = true, nullable = false)
	public int getIdLotacaoProfissional() {
		return idLotacaoProfissional;
	}
	public void setIdLotacaoProfissional(int idLotacaoProfissional) {
		this.idLotacaoProfissional = idLotacaoProfissional;
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
	@JoinColumn(name = "id_profissional")
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
	@Column(name = "tp_tipo_lotacao")
	@Enumerated(EnumType.STRING)
	public TipoLotacaoProfissionalEnum getTipoLotacao() {
		return this.tipoLotacao;
	}

	public void setTipoLotacao(TipoLotacaoProfissionalEnum tipoLotacao) {
		this.tipoLotacao = tipoLotacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idLotacaoProfissional;
		result = prime * result
				+ ((profissional == null) ? 0 : profissional.hashCode());
		result = prime * result
				+ ((tipoLotacao == null) ? 0 : tipoLotacao.hashCode());
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
		LotacaoProfissional other = (LotacaoProfissional) obj;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idLotacaoProfissional != other.idLotacaoProfissional)
			return false;
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
			return false;
		if (tipoLotacao != other.tipoLotacao)
			return false;
		return true;
	}
	
}
