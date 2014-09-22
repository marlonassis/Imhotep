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
	public String toString() {
		return descricao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime
				* result
				+ ((grupoAlmoxarifado == null) ? 0 : grupoAlmoxarifado
						.hashCode());
		result = prime * result + idSubGrupoAlmoxarifado;
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
		SubGrupoAlmoxarifado other = (SubGrupoAlmoxarifado) obj;
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
		if (idSubGrupoAlmoxarifado != other.idSubGrupoAlmoxarifado)
			return false;
		return true;
	}
	
}
