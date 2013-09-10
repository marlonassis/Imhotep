package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tb_unidade_material_almoxarifado")
public class UnidadeMaterialAlmoxarifado implements Serializable {
	private static final long serialVersionUID = 5064459800993196434L;
	
	private int idUnidadeMaterialAlmoxarifado;
	private String descricao;
	private String sigla;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_unidade_material_almoxarif_id_unidade_material_almoxarif_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_unidade_material_almoxarifado", unique = true, nullable = false)
	public int getIdUnidadeMaterialAlmoxarifado() {
		return this.idUnidadeMaterialAlmoxarifado;
	}
	
	public void setIdUnidadeMaterialAlmoxarifado(int idUnidadeMaterialAlmoxarifado){
		this.idUnidadeMaterialAlmoxarifado = idUnidadeMaterialAlmoxarifado;
	}

	@Column(name = "cv_unidade")
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "cv_sigla")
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof UnidadeMaterialAlmoxarifado))
			return false;
		
		return ((UnidadeMaterialAlmoxarifado)obj).getIdUnidadeMaterialAlmoxarifado() == this.idUnidadeMaterialAlmoxarifado;
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
