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
import javax.persistence.Transient;

@Entity
@Table(name = "tb_ehealth_estado", schema="ehealth")
public class EhealthEstado implements Serializable{
	
	private static final long serialVersionUID = 8749933715405774067L;
	
	private int idEhealthEstado;
	private String nome;
	private String regiao;
	private EhealthPais pais;
	private String sigla;
	private String link;
	
	@SequenceGenerator(name = "generator", sequenceName = "ehealth.tb_ehealth_estado_id_ehealth_estado_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_estado", unique = true, nullable = false)
	public int getIdEhealthEstado() {
		return idEhealthEstado;
	}
	public void setIdEhealthEstado(int idEhealthEstado) {
		this.idEhealthEstado = idEhealthEstado;
	}
	
	@Column(name="cv_nome")
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	@Column(name="cv_sigla_estado")
	public String getSigla(){
		return sigla;
	}
	public void setSigla(String sigla){
		this.sigla = sigla;
	}
	
	@Column(name="cv_regiao")
	public String getRegiao(){
		return regiao;
	}
	public void setRegiao(String regiao){
		this.regiao = regiao;
	}
	
	@Column(name="cv_link")
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_pais")
	public EhealthPais getPais() {
		return this.pais;
	}

	public void setPais(EhealthPais pais) {
		this.pais = pais;
	}
	
	@Transient
	public String getSiglaEstadoPais(){
		return getSigla().concat(" - ").concat(getPais().getSigla());
	}
	
	@Transient
	public String getNomeEstadoPais(){
		return getNome().concat(" - ").concat(getPais().getSigla());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEhealthEstado;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((pais == null) ? 0 : pais.hashCode());
		result = prime * result + ((sigla == null) ? 0 : sigla.hashCode());
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
		EhealthEstado other = (EhealthEstado) obj;
		if (idEhealthEstado != other.idEhealthEstado)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pais == null) {
			if (other.pais != null)
				return false;
		} else if (!pais.equals(other.pais))
			return false;
		if (sigla == null) {
			if (other.sigla != null)
				return false;
		} else if (!sigla.equals(other.sigla))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
