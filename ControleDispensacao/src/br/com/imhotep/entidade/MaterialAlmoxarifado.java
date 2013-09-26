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
	private static final long serialVersionUID = 4181799696054317901L;
	
	private int idMaterialAlmoxarifado;
	private String descricao;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private UnidadeMaterialAlmoxarifado unidadeMaterialAlmoxarifado;
	private Date dataInclusao;
	private Profissional profissionalInclusao;
	private Integer quantidadeMinima;
	private Boolean bloqueado;
	
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
	
	@Transient
	public String getDescricaoUnidadeMaterial(){
		if(getDescricao() != null && getUnidadeMaterialAlmoxarifado() != null)
			return getDescricao().concat(" - ").concat(getUnidadeMaterialAlmoxarifado().getSigla());
		return "";
	}
	
	
	@Override
	public String toString() {
		return descricao;
	}
}
