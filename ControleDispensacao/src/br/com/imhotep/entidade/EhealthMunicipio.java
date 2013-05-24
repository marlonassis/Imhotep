package br.com.imhotep.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoEstadoEnum;

@Entity
@Table(name = "tb_ehealth_municipio")
public class EhealthMunicipio {
	private int idEhealthMunicipio;
	private String nome;
	private String link;
	private EhealthEstado ehealthEstado;
	private TipoEstadoEnum estado;
	private String linkEstado;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_municipio_id_ehealth_municipio_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_municipio", unique = true, nullable = false)
	public int getIdEhealthMunicipio() {
		return idEhealthMunicipio;
	}
	public void setIdEhealthMunicipio(int idEhealthEstado) {
		this.idEhealthMunicipio = idEhealthEstado;
	}
	
	@Column(name="cv_nome")
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	@Column(name="cv_link")
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_estado")
	public EhealthEstado getEhealthEstado() {
		return ehealthEstado;
	}
	public void setEhealthEstado(EhealthEstado ehealthEstado) {
		this.ehealthEstado = ehealthEstado;
	}
	
	@Column(name = "tp_estado")
	@Enumerated(EnumType.STRING)
	public TipoEstadoEnum getEstado() {
		return estado;
	}
	public void setEstado(TipoEstadoEnum estado) {
		this.estado = estado;
	}
	
	@Column(name="cv_link_estado")
	public String getLinkEstado() {
		return linkEstado;
	}
	public void setLinkEstado(String linkEstado) {
		this.linkEstado = linkEstado;
	}
	
	@Transient
	public String getMunicipioEstado(){
		return nome.concat(" - ").concat(estado.name());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthMunicipio))
			return false;
		
		return ((EhealthMunicipio)obj).getIdEhealthMunicipio() == this.idEhealthMunicipio;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode() + ehealthEstado.getNome().hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
