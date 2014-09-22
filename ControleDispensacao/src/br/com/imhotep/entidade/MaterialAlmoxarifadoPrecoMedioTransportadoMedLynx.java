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
@Table(name = "tb_material_almoxarifado_preco_medio_transportado_medlynx")
public class MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx implements Serializable {
	private static final long serialVersionUID = 7665683307664414523L;
	
	private int idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx;
	private MaterialAlmoxarifado materialAlmoxarifado;
	private Integer saldo;
	private Double precoMedio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_material_almoxarifado_prec_id_material_almoxarifado_prec_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_material_almoxarifado_preco_medio_transportado_medlynx", unique = true, nullable = false)
	public int getIdMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx() {
		return idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx;
	}
	public void setIdMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx(int idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx) {
		this.idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx = idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx;
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
	@JoinColumn(name = "id_material_almoxarifado")
	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}
	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx;
		result = prime * result
				+ ((materialAlmoxarifado == null) ? 0 : materialAlmoxarifado.hashCode());
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
		MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx other = (MaterialAlmoxarifadoPrecoMedioTransportadoMedLynx) obj;
		if (idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx != other.idMaterialAlmoxarifadoPrecoMedioTransportadoMedLynx)
			return false;
		if (materialAlmoxarifado == null) {
			if (other.materialAlmoxarifado != null)
				return false;
		} else if (!materialAlmoxarifado.equals(other.materialAlmoxarifado))
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
		return materialAlmoxarifado.toString();
	}
}
