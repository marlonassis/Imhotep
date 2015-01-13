package br.com.imhotep.entidade;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.imhotep.enums.TipoHistoricoChefiaEnum;

@Entity
@Table(name = "tb_historico_chefia", schema="administrativo")
public class HistoricoChefia implements Serializable {
	private static final long serialVersionUID = 7260738620282457600L;
	
	private int idHistoricoChefia;
	private Date dataPortaria;
	private String portaria;
	private TipoHistoricoChefiaEnum tipo;
	private Date dataCadastro;
	private Profissional profissionalCadastro;
	private EstruturaOrganizacional estruturaOrganizacional; 
	private Profissional profissionalChefe;
	
	@SequenceGenerator(name = "generator", sequenceName = "administrativo.tb_historico_chefia_id_historico_chefia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_historico_chefia", unique = true, nullable = false)
	public int getIdHistoricoChefia() {
		return idHistoricoChefia;
	}
	
	public void setIdHistoricoChefia(int idHistoricoChefia) {
		this.idHistoricoChefia = idHistoricoChefia;
	}
	
	@Column(name = "tp_tipo")
	@Enumerated(EnumType.STRING)
	public TipoHistoricoChefiaEnum getTipo() {
		return tipo;
	}
	public void setTipo(TipoHistoricoChefiaEnum tipo) {
		this.tipo = tipo;
	}
	
	@Column(name = "cv_portaria")
	public String getPortaria() {
		return portaria;
	}
	
	public void setPortaria(String portaria) {
		this.portaria = portaria;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_portaria")
	public Date getDataPortaria() {
		return dataPortaria;
	}
	
	public void setDataPortaria(Date dataPortaria) {
		this.dataPortaria = dataPortaria;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return dataCadastro;
	}
	
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_chefe")
	public Profissional getProfissionalChefe() {
		return profissionalChefe;
	}
	
	public void setProfissionalChefe(Profissional profissionalChefe) {
		this.profissionalChefe = profissionalChefe;
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
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime * result
				+ ((dataPortaria == null) ? 0 : dataPortaria.hashCode());
		result = prime
				* result
				+ ((estruturaOrganizacional == null) ? 0
						: estruturaOrganizacional.hashCode());
		result = prime * result + idHistoricoChefia;
		result = prime * result
				+ ((portaria == null) ? 0 : portaria.hashCode());
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime
				* result
				+ ((profissionalChefe == null) ? 0 : profissionalChefe
						.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		HistoricoChefia other = (HistoricoChefia) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (dataPortaria == null) {
			if (other.dataPortaria != null)
				return false;
		} else if (!dataPortaria.equals(other.dataPortaria))
			return false;
		if (estruturaOrganizacional == null) {
			if (other.estruturaOrganizacional != null)
				return false;
		} else if (!estruturaOrganizacional
				.equals(other.estruturaOrganizacional))
			return false;
		if (idHistoricoChefia != other.idHistoricoChefia)
			return false;
		if (portaria == null) {
			if (other.portaria != null)
				return false;
		} else if (!portaria.equals(other.portaria))
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (profissionalChefe == null) {
			if (other.profissionalChefe != null)
				return false;
		} else if (!profissionalChefe.equals(other.profissionalChefe))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
