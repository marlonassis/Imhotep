package br.com.imhotep.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoEhealthContinenteEnum;

@Entity
@Table(name = "tb_ehealth_pais", schema="ehealth")
public class EhealthPais implements Serializable {
	
	private static final long serialVersionUID = 7712973464885965302L;
	
	private int idEhealthPais;
	private String nome;
	private String sigla;
	private String link;
	private String capitalFederal;
	private TipoEhealthContinenteEnum tipoContinente;
	
	@SequenceGenerator(name = "generator", sequenceName = "ehealth.tb_ehealth_pais_id_ehealth_pais_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_pais", unique = true, nullable = false)
	public int getIdEhealthPais() {
		return idEhealthPais;
	}
	public void setIdEhealthPais(int idEhealthPais) {
		this.idEhealthPais = idEhealthPais;
	}
	
	@Column(name="cv_nome")
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	@Column(name="cv_sigla")
	public String getSigla(){
		return sigla;
	}
	public void setSigla(String sigla){
		this.sigla = sigla;
	}
	
	@Column(name="cv_link")
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@Column(name="cv_capital_federal")
	public String getCapitalFederal(){
		return capitalFederal;
	}
	public void setCapitalFederal(String capitalFederal){
		this.capitalFederal = capitalFederal;
	}
	
	@Column(name = "tp_continente")
	@Enumerated(EnumType.STRING)
	public TipoEhealthContinenteEnum getTipoContinente() {
		return tipoContinente;
	}
	public void setTipoContinente(TipoEhealthContinenteEnum tipoContinente) {
		this.tipoContinente = tipoContinente;
	}
	
	@Transient
	public String getNomeSigla(){
		return getNome().concat(" - ").concat(getSigla());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idEhealthPais;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((tipoContinente == null) ? 0 : tipoContinente.hashCode());
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
		EhealthPais other = (EhealthPais) obj;
		if (idEhealthPais != other.idEhealthPais)
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
		if (tipoContinente != other.tipoContinente)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return nome;
	}
}
