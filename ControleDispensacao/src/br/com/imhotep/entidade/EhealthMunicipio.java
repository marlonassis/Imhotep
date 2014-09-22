package br.com.imhotep.entidade;

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
@Table(name = "tb_ehealth_municipio", schema="ehealth")
public class EhealthMunicipio {
	private int idEhealthMunicipio;
	private String nome;
	private String link;
	private String departamento;
	private EhealthEstado ehealthEstado;
	private String linkEstado;
	private boolean capital;
	
	@SequenceGenerator(name = "generator", sequenceName = "ehealth.tb_ehealth_municipio_id_ehealth_municipio_seq")
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
	
	@Column(name="cv_link_estado")
	public String getLinkEstado() {
		return linkEstado;
	}
	public void setLinkEstado(String linkEstado) {
		this.linkEstado = linkEstado;
	}
	
	@Column(name = "bl_capital")
	public boolean getCapital() {
		return capital;
	}
	public void setCapital(boolean capital) {
		this.capital = capital;
	}
	
	@Transient
	public String getMunicipioEstado(){
		return nome.concat(" - ").concat(getEhealthEstado().getNomeEstadoPais());
	}
	
	@Transient
	public String getSiglaCompleta(){
		return getNome().concat(" - ").concat(getEhealthEstado().getSiglaEstadoPais());
	}
	
	@Transient
	public String getNomeCapital(){
		if(getCapital()){
			return getNome().concat(" (C)");
		}
		return getNome();
	}
	
//	@Transient 
//	public int getQtdEstabelecimentos(){
//		if(getIdEhealthMunicipio() != 0 && qtdEstabelecimentos == 0){
//			String sql = "select count(id_ehealth_estabelecimento) as total from ehealth.tb_ehealth_estabelecimento where id_ehealth_municipio = "+getIdEhealthMunicipio();
//			ResultSet rs = new LinhaMecanica("db_ehealth", Constantes.IP_LOCAL).consultar(sql);
//			try {
//				if(rs.next()){
//					qtdEstabelecimentos = rs.getInt("total");
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return qtdEstabelecimentos;
//	}
	
	@Column(name="cv_departamento")
	public String getDepartamento(){
		return departamento;
	}
	public void setDepartamento(String departamento){
		this.departamento = departamento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ehealthEstado == null) ? 0 : ehealthEstado.hashCode());
		result = prime * result + idEhealthMunicipio;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((linkEstado == null) ? 0 : linkEstado.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		EhealthMunicipio other = (EhealthMunicipio) obj;
		if (ehealthEstado == null) {
			if (other.ehealthEstado != null)
				return false;
		} else if (!ehealthEstado.equals(other.ehealthEstado))
			return false;
		if (idEhealthMunicipio != other.idEhealthMunicipio)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (linkEstado == null) {
			if (other.linkEstado != null)
				return false;
		} else if (!linkEstado.equals(other.linkEstado))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}
