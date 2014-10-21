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
@Table(name = "tb_material_odontologia")
public class MaterialOdontologia implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6752428898032765808L;
	private int idMaterialOdontologia;
	private String descricao;
	private GrupoOdontologia grupoOdontologia;
	private SubGrupoOdontologia subGrupoOdontologia;
	private UnidadeMaterialOdontologia unidadeMaterialOdontologia;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private Integer quantidadeMinima;
	private Boolean bloqueado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_material_odontologia_id_material_odontologia_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_material_odontologia", unique = true, nullable = false)
	public int getIdMaterialOdontologia() {
		return this.idMaterialOdontologia;
	}
	
	public void setIdMaterialOdontologia(int idMaterialOdontologia){
		this.idMaterialOdontologia = idMaterialOdontologia;
	}

	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_odontologia")
	public GrupoOdontologia getGrupoOdontologia() {
		return this.grupoOdontologia;
	}

	public void setGrupoOdontologia(GrupoOdontologia grupoOdontologia) {
		this.grupoOdontologia = grupoOdontologia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_sub_grupo_odontologia")
	public SubGrupoOdontologia getSubGrupoOdontologia() {
		return this.subGrupoOdontologia;
	}

	public void setSubGrupoOdontologia(SubGrupoOdontologia subGrupoOdontologia) {
		this.subGrupoOdontologia = subGrupoOdontologia;
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
	@JoinColumn(name = "id_unidade_material_odontologia")
	public UnidadeMaterialOdontologia getUnidadeMaterialOdontologia() {
		return this.unidadeMaterialOdontologia;
	}

	public void setUnidadeMaterialOdontologia(UnidadeMaterialOdontologia unidadeMaterialOdontologia) {
		this.unidadeMaterialOdontologia = unidadeMaterialOdontologia;
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
	
	@Transient
	public String getDescricaoReduzida(){
		if(getDescricao() != null){
			int length = getDescricao().length();
			int tamanho = length > 160 ? 160 : length;
			return getIdMaterialOdontologia() + " - " + String.valueOf(getDescricao().subSequence(0, tamanho))+( length > 160 ? "..." : "");
		}
		return getDescricao();
	}
	
	@Transient
	public String getDescricaoId(){
		return getIdMaterialOdontologia() + " - " + getDescricao();
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
			
			return getIdMaterialOdontologia() + " - " + ret.concat(" - ").concat(getUnidadeMaterialOdontologia().getSigla());
		}
		return "";
	}
	
	@Transient
	public String getDescricaoUnidadeMaterial(){
		if(getDescricao() != null && getUnidadeMaterialOdontologia() != null)
			return getIdMaterialOdontologia() + " - " + getDescricao().concat(" - ").concat(getUnidadeMaterialOdontologia().getSigla());
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
				+ ((grupoOdontologia == null) ? 0 : grupoOdontologia.hashCode());
		result = prime * result + idMaterialOdontologia;
		result = prime
				* result
				+ ((profissionalInclusao == null) ? 0 : profissionalInclusao
						.hashCode());
		result = prime
				* result
				+ ((quantidadeMinima == null) ? 0 : quantidadeMinima.hashCode());
		result = prime
				* result
				+ ((subGrupoOdontologia == null) ? 0 : subGrupoOdontologia
						.hashCode());
		result = prime
				* result
				+ ((unidadeMaterialOdontologia == null) ? 0
						: unidadeMaterialOdontologia.hashCode());
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
		MaterialOdontologia other = (MaterialOdontologia) obj;
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
		if (grupoOdontologia == null) {
			if (other.grupoOdontologia != null)
				return false;
		} else if (!grupoOdontologia.equals(other.grupoOdontologia))
			return false;
		if (idMaterialOdontologia != other.idMaterialOdontologia)
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
		if (subGrupoOdontologia == null) {
			if (other.subGrupoOdontologia != null)
				return false;
		} else if (!subGrupoOdontologia.equals(other.subGrupoOdontologia))
			return false;
		if (unidadeMaterialOdontologia == null) {
			if (other.unidadeMaterialOdontologia != null)
				return false;
		} else if (!unidadeMaterialOdontologia
				.equals(other.unidadeMaterialOdontologia))
			return false;
		return true;
	}
	
}
