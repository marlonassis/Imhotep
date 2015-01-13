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

import br.com.imhotep.enums.TipoStatusInventarioEnum;

@Entity
@Table(name = "tb_inventario", schema="farmacia")
public class InventarioFarmacia implements Serializable {
	private static final long serialVersionUID = 5158271860208212510L;
	
	private int idInventarioFarmacia;
	private TipoStatusInventarioEnum tipoStatus;
	private String descricao;
	private Profissional profissionalFinalizacao;
	private Profissional profissionalCadastro;
	private Date dataInicio;
	private Date dataFinalizacao;
	
	@SequenceGenerator(name = "generator", sequenceName = "farmacia.tb_inventario_id_inventario_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_inventario", unique = true, nullable = false)
	public int getIdInventarioFarmacia() {
		return idInventarioFarmacia;
	}
	public void setIdInventarioFarmacia(int idInventarioFarmacia) {
		this.idInventarioFarmacia = idInventarioFarmacia;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inicio")
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_finalizacao")
	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	
	@Column(name = "tp_status")
	@Enumerated(EnumType.STRING)
	public TipoStatusInventarioEnum getTipoStatus() {
		return tipoStatus;
	}
	
	public void setTipoStatus(TipoStatusInventarioEnum tipoStatus) {
		this.tipoStatus = tipoStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_cadastro")
	public Profissional getProfissionalCadastro() {
		return profissionalCadastro;
	}
	public void setProfissionalCadastro(Profissional profissionalCadastro) {
		this.profissionalCadastro = profissionalCadastro;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_profissional_finalizacao")
	public Profissional getProfissionalFinalizacao() {
		return profissionalFinalizacao;
	}
	public void setProfissionalFinalizacao(Profissional profissionalFinalizacao) {
		this.profissionalFinalizacao = profissionalFinalizacao;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataFinalizacao == null) ? 0 : dataFinalizacao.hashCode());
		result = prime * result
				+ ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + idInventarioFarmacia;
		result = prime
				* result
				+ ((profissionalCadastro == null) ? 0 : profissionalCadastro
						.hashCode());
		result = prime
				* result
				+ ((profissionalFinalizacao == null) ? 0
						: profissionalFinalizacao.hashCode());
		result = prime * result
				+ ((tipoStatus == null) ? 0 : tipoStatus.hashCode());
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
		InventarioFarmacia other = (InventarioFarmacia) obj;
		if (dataFinalizacao == null) {
			if (other.dataFinalizacao != null)
				return false;
		} else if (!dataFinalizacao.equals(other.dataFinalizacao))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idInventarioFarmacia != other.idInventarioFarmacia)
			return false;
		if (profissionalCadastro == null) {
			if (other.profissionalCadastro != null)
				return false;
		} else if (!profissionalCadastro.equals(other.profissionalCadastro))
			return false;
		if (profissionalFinalizacao == null) {
			if (other.profissionalFinalizacao != null)
				return false;
		} else if (!profissionalFinalizacao
				.equals(other.profissionalFinalizacao))
			return false;
		if (tipoStatus != other.tipoStatus)
			return false;
		return true;
	}
	
}
