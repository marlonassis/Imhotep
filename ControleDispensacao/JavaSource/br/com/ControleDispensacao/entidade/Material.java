package br.com.ControleDispensacao.entidade;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;

@Entity
@Table(name = "material")
public class Material {
	private int idMaterial;
	private UnidadeMaterial unidadeMaterial;
	private Grupo grupo;
	private SubGrupo subGrupo;
	private TipoMaterial tipoMaterial;
	private Familia familia;
	private ListaEspecial listaEspecial;
	private Integer codigoMaterial;
	private String descricao;
	private TipoStatusEnum dispensavel;
	private Integer diasLimiteDisponivel;
	private TipoSituacaoEnum status;
	private Date dataInclusao;
	private Usuario usuarioInclusao;
	private Date dataAlteracao;
	private Usuario usuarioAlteracao;
	private TipoStatusEnum autorizadoDispensacao;

	@Id
	@GeneratedValue
	@Column(name = "id_material")
	public int getIdMaterial() {
		return this.idMaterial;
	}
	
	public void setIdMaterial(int idMaterial){
		this.idMaterial = idMaterial;
	}

	@Column(name = "descricao", length = 120)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "unidade_material_id_unidade_material")
	public UnidadeMaterial getUnidadeMaterial() {
		return this.unidadeMaterial;
	}

	public void setUnidadeMaterial(UnidadeMaterial unidadeMaterial) {
		this.unidadeMaterial = unidadeMaterial;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo_id_grupo")
	public Grupo getGrupo() {
		return this.grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subgrupo_id_subgrupo")
	public SubGrupo getSubGrupo() {
		return this.subGrupo;
	}
	
	public void setSubGrupo(SubGrupo subGrupo) {
		this.subGrupo = subGrupo;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_material_id_tipo_material")
	public TipoMaterial getTipoMaterial() {
		return this.tipoMaterial;
	}
	
	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "familia_id_familia")
	public Familia getFamilia() {
		return this.familia;
	}
	
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "lista_especial_id_lista_especial")
	public ListaEspecial getListaEspecial() {
		return this.listaEspecial;
	}
	
	public void setListaEspecial(ListaEspecial listaEspecial) {
		this.listaEspecial = listaEspecial;
	}
	
	@Column(name = "codigo_material")
	public Integer getCodigoMaterial() {
		return this.codigoMaterial;
	}

	public void setCodigoMaterial(Integer codigoMaterial) {
		this.codigoMaterial = codigoMaterial;
	}
	
	@Column(name = "dias_limite_disp")
	public Integer getDiasLimiteDisponivel() {
		return this.diasLimiteDisponivel;
	}

	public void setDiasLimiteDisponivel(Integer diasLimiteDisponivel) {
		this.diasLimiteDisponivel = diasLimiteDisponivel;
	}

	@Column(name = "flg_dispensavel")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getDispensavel() {
		return this.dispensavel;
	}

	public void setDispensavel(TipoStatusEnum dispensavel) {
		this.dispensavel = dispensavel;
	}
	
	@Column(name = "flg_autorizacao_disp")
	@Enumerated(EnumType.STRING)
	public TipoStatusEnum getAutorizadoDispensacao() {
		return this.autorizadoDispensacao;
	}

	public void setAutorizadoDispensacao(TipoStatusEnum autorizadoDispensacao) {
		this.autorizadoDispensacao = autorizadoDispensacao;
	}
	
	@Column(name = "status_2")
	@Enumerated(EnumType.STRING)
	public TipoSituacaoEnum getStatus() {
		return this.status;
	}

	public void setStatus(TipoSituacaoEnum status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_incl", length = 13)
	public Date getDataInclusao() {
		return this.dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usua_incl")
	public Usuario getUsuarioInclusao() {
		return this.usuarioInclusao;
	}

	public void setUsuarioInclusao(Usuario usuarioInclusao) {
		this.usuarioInclusao = usuarioInclusao;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_alt", length = 13)
	public Date getDataAlteracao() {
		return this.dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usua_alt")
	public Usuario getUsuarioAlteracao() {
		return this.usuarioAlteracao;
	}

	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
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
