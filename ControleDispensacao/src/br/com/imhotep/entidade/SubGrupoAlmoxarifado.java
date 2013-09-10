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
@Table(name = "tb_sub_grupo_almoxarifado")
public class SubGrupoAlmoxarifado implements Serializable{
	private static final long serialVersionUID = 4645038570657161211L;
	
	private int idSubGrupoAlmoxarifado;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private String descricao;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_sub_grupo_almoxarifado_id_sub_grupo_almoxarifado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_sub_grupo_almoxarifado", unique = true, nullable = false)
	public int getIdSubGrupoAlmoxarifado() {
		return this.idSubGrupoAlmoxarifado;
	}
	
	public void setIdSubGrupoAlmoxarifado(int idSubGrupoAlmoxarifado){
		this.idSubGrupoAlmoxarifado = idSubGrupoAlmoxarifado;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_grupo_almoxarifado")
	public GrupoAlmoxarifado getGrupoAlmoxarifado(){
		return grupoAlmoxarifado;
	}
	
	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado){
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}
	
	@Column(name = "cv_descricao")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof SubGrupoAlmoxarifado))
			return false;
		
		return ((SubGrupoAlmoxarifado)obj).getIdSubGrupoAlmoxarifado() == this.idSubGrupoAlmoxarifado;
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
