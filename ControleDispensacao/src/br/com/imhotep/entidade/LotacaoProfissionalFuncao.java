package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_lotacao_profissional_funcao", schema="administrativo")
public class LotacaoProfissionalFuncao  implements Serializable {
	private static final long serialVersionUID = 6526870141310104184L;
	
	private int idLotacaoProfissionalFuncao;
	private LotacaoProfissional lotacaoProfissional;
	private EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao;
	private Date dataPosse;
	private String portaria;
	
	@Id
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_lotacao_profissional_funca_id_lotacao_profissional_funca_seq")
	@GeneratedValue(generator = "generator")
	@Column(name = "id_lotacao_profissional_funcao", unique = true, nullable = false)
	public int getIdLotacaoProfissionalFuncao() {
		return idLotacaoProfissionalFuncao;
	}
	public void setIdLotacaoProfissionalFuncao(int idLotacaoProfissionalFuncao) {
		this.idLotacaoProfissionalFuncao = idLotacaoProfissionalFuncao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lotacao_profissional")
	public LotacaoProfissional getLotacaoProfissional() {
		return lotacaoProfissional;
	}
	public void setLotacaoProfissional(LotacaoProfissional lotacaoProfissional) {
		this.lotacaoProfissional = lotacaoProfissional;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_estrutura_organizacional_funcao")
	public EstruturaOrganizacionalFuncao getEstruturaOrganizacionalFuncao() {
		return estruturaOrganizacionalFuncao;
	}
	public void setEstruturaOrganizacionalFuncao(EstruturaOrganizacionalFuncao estruturaOrganizacionalFuncao) {
		this.estruturaOrganizacionalFuncao = estruturaOrganizacionalFuncao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_posse")
	public Date getDataPosse() {
		return this.dataPosse;
	}

	public void setDataPosse(Date dataPosse) {
		this.dataPosse = dataPosse;
	}
	
	@Column(name = "cv_portaria")
	public String getPortaria() {
		return this.portaria;
	}

	public void setPortaria(String portaria) {
		this.portaria = portaria;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataPosse == null) ? 0 : dataPosse.hashCode());
		result = prime * result + ((estruturaOrganizacionalFuncao == null) ? 0 : estruturaOrganizacionalFuncao.hashCode());
		result = prime * result + idLotacaoProfissionalFuncao;
		result = prime
				* result
				+ ((lotacaoProfissional == null) ? 0 : lotacaoProfissional
						.hashCode());
		result = prime * result
				+ ((portaria == null) ? 0 : portaria.hashCode());
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
		LotacaoProfissionalFuncao other = (LotacaoProfissionalFuncao) obj;
		if (dataPosse == null) {
			if (other.dataPosse != null)
				return false;
		} else if (!dataPosse.equals(other.dataPosse))
			return false;
		if (estruturaOrganizacionalFuncao == null) {
			if (other.estruturaOrganizacionalFuncao != null)
				return false;
		} else if (!estruturaOrganizacionalFuncao.equals(other.estruturaOrganizacionalFuncao))
			return false;
		if (idLotacaoProfissionalFuncao != other.idLotacaoProfissionalFuncao)
			return false;
		if (lotacaoProfissional == null) {
			if (other.lotacaoProfissional != null)
				return false;
		} else if (!lotacaoProfissional.equals(other.lotacaoProfissional))
			return false;
		if (portaria == null) {
			if (other.portaria != null)
				return false;
		} else if (!portaria.equals(other.portaria))
			return false;
		return true;
	}
	
}
