package br.com.imhotep.entidade;

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

import br.com.imhotep.auxiliar.Constantes;

@Entity
@Table(name = "tb_material")
public class Material {
	private int idMaterial;
	private UnidadeMaterial unidadeMaterial;
	private TipoMaterial tipoMaterial;
	private Familia familia;
	private ListaEspecial listaEspecial;
	private Integer codigoMaterial;
	private String descricao;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Integer quantidadeMinima;
	
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
	
	@Column(name = "in_codigo_material")
	public Integer getCodigoMaterial() {
		return this.codigoMaterial;
	}

	public void setCodigoMaterial(Integer codigoMaterial) {
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
		return getDescricao().concat(" - ").concat(getUnidadeMaterial().getSigla());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof Material))
			return false;
		
		return ((Material)obj).getIdMaterial() == this.idMaterial;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + descricao.hashCode();
	}

	@Override
	public String toString() {
		return descricao;
	}
}
