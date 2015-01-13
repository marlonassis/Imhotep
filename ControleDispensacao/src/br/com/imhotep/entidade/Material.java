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
import javax.persistence.Transient;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.enums.TipoEstadoFisicoMedicamentoEnum;

@Entity
@Table(name = "tb_material")
public class Material implements Serializable {
	private static final long serialVersionUID = -702478247737632275L;
	
	private int idMaterial;
	private UnidadeMaterial unidadeMaterial;
	private TipoMaterial tipoMaterial;
	private TipoEstadoFisicoMedicamentoEnum tipoEstadoFisico;
	private Familia familia;
	private ListaEspecial listaEspecial;
	private String codigoMaterial;
	private String descricao;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Integer quantidadeMinima;
	private Boolean bloqueado;
	private Boolean padronizado;
	private Double precoMedio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_material_id_material_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_material", unique = true, nullable = false)
	public int getIdMaterial() {
		return this.idMaterial;
	}
	
	public void setIdMaterial(int idMaterial){
		this.idMaterial = idMaterial;
	}

	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_material")
	public UnidadeMaterial getUnidadeMaterial() {
		return this.unidadeMaterial;
	}

	public void setUnidadeMaterial(UnidadeMaterial unidadeMaterial) {
		this.unidadeMaterial = unidadeMaterial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_tipo_material")
	public TipoMaterial getTipoMaterial() {
		return this.tipoMaterial;
	}
	
	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	@Column(name = "tp_estado_fisico")
	@Enumerated(EnumType.STRING)
	public TipoEstadoFisicoMedicamentoEnum getTipoEstadoFisico() {
		return tipoEstadoFisico;
	}
	public void setTipoEstadoFisico(TipoEstadoFisicoMedicamentoEnum tipoEstadoFisico) {
		this.tipoEstadoFisico = tipoEstadoFisico;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_familia")
	public Familia getFamilia() {
		return this.familia;
	}
	
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_lista_especial")
	public ListaEspecial getListaEspecial() {
		return this.listaEspecial;
	}
	
	public void setListaEspecial(ListaEspecial listaEspecial) {
		this.listaEspecial = listaEspecial;
	}
	
	@Column(name = "cv_codigo_material")
	public String getCodigoMaterial() {
		return this.codigoMaterial;
	}

	public void setCodigoMaterial(String codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_inclusao")
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_usuario_inclusao")
	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}

	@Column(name = "in_quantidade_minima")
	public Integer getQuantidadeMinima() {
		return this.quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	
	@Transient
	public boolean isMaterialAntibiotico(){
		String grupoMaterial = getFamilia().getSubGrupo().getGrupo().getDescricao();
		return grupoMaterial.equalsIgnoreCase(Constantes.MATERIAL_ANTIBIOTICO);
	}
	
	@Transient
	public String getDescricaoUnidadeMaterial(){
		if(getDescricao() != null && getUnidadeMaterial() != null)
			return getDescricao().concat(" - ").concat(getUnidadeMaterial().getSigla());
		return "";
	}
	
	@Column(name="bl_bloqueado")
	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	@Column(name="bl_padronizado")
	public Boolean getPadronizado() {
		return padronizado;
	}

	public void setPadronizado(Boolean padronizado) {
		this.padronizado = padronizado;
	}
	
	@Column(name = "db_preco_medio")
	public Double getPrecoMedio() {
		return this.precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bloqueado == null) ? 0 : bloqueado.hashCode());
		result = prime * result
				+ ((codigoMaterial == null) ? 0 : codigoMaterial.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((familia == null) ? 0 : familia.hashCode());
		result = prime * result + idMaterial;
		result = prime * result
				+ ((listaEspecial == null) ? 0 : listaEspecial.hashCode());
		result = prime * result
				+ ((padronizado == null) ? 0 : padronizado.hashCode());
		result = prime * result
				+ ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime
				* result
				+ ((quantidadeMinima == null) ? 0 : quantidadeMinima.hashCode());
		result = prime
				* result
				+ ((tipoEstadoFisico == null) ? 0 : tipoEstadoFisico.hashCode());
		result = prime * result
				+ ((tipoMaterial == null) ? 0 : tipoMaterial.hashCode());
		result = prime * result
				+ ((unidadeMaterial == null) ? 0 : unidadeMaterial.hashCode());
		result = prime * result
				+ ((usuarioInclusao == null) ? 0 : usuarioInclusao.hashCode());
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
		Material other = (Material) obj;
		if (bloqueado == null) {
			if (other.bloqueado != null)
				return false;
		} else if (!bloqueado.equals(other.bloqueado))
			return false;
		if (codigoMaterial == null) {
			if (other.codigoMaterial != null)
				return false;
		} else if (!codigoMaterial.equals(other.codigoMaterial))
			return false;
		if (dataInclusao == null) {
			if (other.dataInclusao != null)
				return false;
		} else if (!dataInclusao.equals(other.dataInclusao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (familia == null) {
			if (other.familia != null)
				return false;
		} else if (!familia.equals(other.familia))
			return false;
		if (idMaterial != other.idMaterial)
			return false;
		if (listaEspecial == null) {
			if (other.listaEspecial != null)
				return false;
		} else if (!listaEspecial.equals(other.listaEspecial))
			return false;
		if (padronizado == null) {
			if (other.padronizado != null)
				return false;
		} else if (!padronizado.equals(other.padronizado))
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (quantidadeMinima == null) {
			if (other.quantidadeMinima != null)
				return false;
		} else if (!quantidadeMinima.equals(other.quantidadeMinima))
			return false;
		if (tipoEstadoFisico != other.tipoEstadoFisico)
			return false;
		if (tipoMaterial == null) {
			if (other.tipoMaterial != null)
				return false;
		} else if (!tipoMaterial.equals(other.tipoMaterial))
			return false;
		if (unidadeMaterial == null) {
			if (other.unidadeMaterial != null)
				return false;
		} else if (!unidadeMaterial.equals(other.unidadeMaterial))
			return false;
		if (usuarioInclusao == null) {
			if (other.usuarioInclusao != null)
				return false;
		} else if (!usuarioInclusao.equals(other.usuarioInclusao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getDescricao();
	}

}
