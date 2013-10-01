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
@Table(name = "tb_preco_medio_transportado_medlynx")
public class PrecoMedioTransportadoMedLynx implements Serializable {
	private static final long serialVersionUID = -5067117240548658758L;
	
	private int idPrecoMedioTransportadoMedlynx;
	private Material material;
	private Integer saldo;
	private Double precoMedio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_preco_medio_transportado_m_id_preco_medio_transportado_m_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_preco_medio_transportado_medlynx", unique = true, nullable = false)
	public int getIdPrecoMedioTransportadoMedLynx() {
		return idPrecoMedioTransportadoMedlynx;
	}
	public void setIdPrecoMedioTransportadoMedLynx(int idPrecoMedioTransportadoMedlynx) {
		this.idPrecoMedioTransportadoMedlynx = idPrecoMedioTransportadoMedlynx;
	}
	
	@Column(name="in_saldo_transportado")
	public Integer getSaldo() {
		return saldo;
	}
	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}
	
	@Column(name="db_preco_medio_transportado")
	public Double getPrecoMedio() {
		return precoMedio;
	}
	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_material")
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPrecoMedioTransportadoMedlynx;
		result = prime * result
				+ ((material == null) ? 0 : material.hashCode());
		result = prime * result
				+ ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
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
		PrecoMedioTransportadoMedLynx other = (PrecoMedioTransportadoMedLynx) obj;
		if (idPrecoMedioTransportadoMedlynx != other.idPrecoMedioTransportadoMedlynx)
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return material.toString();
	}
}
