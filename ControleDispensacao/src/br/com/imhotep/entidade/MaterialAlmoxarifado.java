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
import javax.persistence.Transient;

@Entity
@Table(name = "tb_material_almoxarifado")
public class MaterialAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 6153098254771445434L;
	
	private int idMaterialAlmoxarifado;
	private String descricao;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private UnidadeMaterialAlmoxarifado unidadeMaterialAlmoxarifado;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private Integer quantidadeMinima;
	private Boolean bloqueado;
	private Double precoMedio;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_material_almoxarifado_id_material_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_material_almoxarifado", unique = true, nullable = false)
	public int getIdMaterialAlmoxarifado() {
		return this.idMaterialAlmoxarifado;
	}
	
	public void setIdMaterialAlmoxarifado(int idMaterialAlmoxarifado){
		this.idMaterialAlmoxarifado = idMaterialAlmoxarifado;
	}

	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_almoxarifado")
	public GrupoAlmoxarifado getGrupoAlmoxarifado() {
		return this.grupoAlmoxarifado;
	}

	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado) {
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sub_grupo_almoxarifado")
	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return this.subGrupoAlmoxarifado;
	}

	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
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
	@JoinColumn(name = "id_profissional_inclusao")
	public Profissional getProfissionalInclusao() {
		return this.profissionalInclusao;
	}

	public void setProfissionalInclusao(Profissional profissionalInclusao) {
		this.profissionalInclusao = profissionalInclusao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_unidade_material_almoxarifado")
	public UnidadeMaterialAlmoxarifado getUnidadeMaterialAlmoxarifado() {
		return this.unidadeMaterialAlmoxarifado;
	}

	public void setUnidadeMaterialAlmoxarifado(UnidadeMaterialAlmoxarifado unidadeMaterialAlmoxarifado) {
		this.unidadeMaterialAlmoxarifado = unidadeMaterialAlmoxarifado;
	}
	
	@Column(name = "in_quantidade_minima")
	public Integer getQuantidadeMinima() {
		return this.quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
	
	@Column(name="bl_bloqueado")
	public Boolean getBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(Boolean bloqueado) {
		this.bloqueado = bloqueado;
	}
	
	@Column(name = "db_preco_medio")
	public Double getPrecoMedio() {
		return this.precoMedio;
	}

	public void setPrecoMedio(Double precoMedio) {
		this.precoMedio = precoMedio;
	}
	
	@Transient
	public String getDescricaoReduzida(){
		if(getDescricao() != null){
			int length = getDescricao().length();
			int tamanho = length > 160 ? 160 : length;
			return getIdMaterialAlmoxarifado() + " - " + String.valueOf(getDescricao().subSequence(0, tamanho))+( length > 160 ? "..." : "");
		}
		return getDescricao();
	}
	
	@Transient
	public String getDescricaoId(){
		return getIdMaterialAlmoxarifado() + " - " + getDescricao();
	}
	
	@Transient
	public String getDescricaoUnidadeHtml(){
		String descricaoUnidadeMaterial = getDescricao();
		if(descricaoUnidadeMaterial != null){
			String ret = "";
			int cont = 1;
			for(String s : descricaoUnidadeMaterial.split(" ")){
				ret += s + " ";
				if(cont == 9){
					ret += "<br/>";
					cont = 0;
				}
				cont++;
			}
			
			return getIdMaterialAlmoxarifado() + " - " + ret.concat(" - ").concat(getUnidadeMaterialAlmoxarifado().getSigla());
		}
		return "";
	}
	
	@Transient
	public String getDescricaoUnidadeMaterial(){
		if(getDescricao() != null && getUnidadeMaterialAlmoxarifado() != null)
			return getIdMaterialAlmoxarifado() + " - " + getDescricao().concat(" - ").concat(getUnidadeMaterialAlmoxarifado().getSigla());
		return "";
	}
	
	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bloqueado == null) ? 0 : bloqueado.hashCode());
		result = prime * result
				+ ((dataInclusao == null) ? 0 : dataInclusao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime
				* result
				+ ((grupoAlmoxarifado == null) ? 0 : grupoAlmoxarifado
						.hashCode());
		result = prime * result + idMaterialAlmoxarifado;
		result = prime * result
				+ ((precoMedio == null) ? 0 : precoMedio.hashCode());
		result = prime
				* result
				+ ((profissionalInclusao == null) ? 0 : profissionalInclusao
						.hashCode());
		result = prime
				* result
				+ ((quantidadeMinima == null) ? 0 : quantidadeMinima.hashCode());
		result = prime
				* result
				+ ((subGrupoAlmoxarifado == null) ? 0 : subGrupoAlmoxarifado
						.hashCode());
		result = prime
				* result
				+ ((unidadeMaterialAlmoxarifado == null) ? 0
						: unidadeMaterialAlmoxarifado.hashCode());
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
		MaterialAlmoxarifado other = (MaterialAlmoxarifado) obj;
		if (bloqueado == null) {
			if (other.bloqueado != null)
				return false;
		} else if (!bloqueado.equals(other.bloqueado))
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
		if (grupoAlmoxarifado == null) {
			if (other.grupoAlmoxarifado != null)
				return false;
		} else if (!grupoAlmoxarifado.equals(other.grupoAlmoxarifado))
			return false;
		if (idMaterialAlmoxarifado != other.idMaterialAlmoxarifado)
			return false;
		if (precoMedio == null) {
			if (other.precoMedio != null)
				return false;
		} else if (!precoMedio.equals(other.precoMedio))
			return false;
		if (profissionalInclusao == null) {
			if (other.profissionalInclusao != null)
				return false;
		} else if (!profissionalInclusao.equals(other.profissionalInclusao))
			return false;
		if (quantidadeMinima == null) {
			if (other.quantidadeMinima != null)
				return false;
		} else if (!quantidadeMinima.equals(other.quantidadeMinima))
			return false;
		if (subGrupoAlmoxarifado == null) {
			if (other.subGrupoAlmoxarifado != null)
				return false;
		} else if (!subGrupoAlmoxarifado.equals(other.subGrupoAlmoxarifado))
			return false;
		if (unidadeMaterialAlmoxarifado == null) {
			if (other.unidadeMaterialAlmoxarifado != null)
				return false;
		} else if (!unidadeMaterialAlmoxarifado
				.equals(other.unidadeMaterialAlmoxarifado))
			return false;
		return true;
	}
	
}
