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

import br.com.imhotep.enums.TipoEhealthNaturezaEnum;

@Entity
@Table(name = "tb_ehealth_estabelecimento")
public class EhealthEstabelecimento {
	private int idEhealthEstabelecimento;
	private String nome;
	private String link;
	private TipoEhealthNaturezaEnum tipoNatureza;
	private String razaoSocial;
	private String endereco;
	private String linkDetalhado;
	private EhealthMunicipio ehealthMunicipio;

	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_ehealth_estabelecimento_id_ehealth_estabelecimento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_estabelecimento", unique = true, nullable = false)
	public int getIdEhealthMunicipio() {
		return idEhealthEstabelecimento;
	}
	public void setIdEhealthMunicipio(int idEhealthEstado) {
		this.idEhealthEstabelecimento = idEhealthEstado;
	}
	
	@Column(name="cv_nome")
	public String getNome(){
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	@Column(name="cv_razao_social")
	public String getRazaoSocial(){
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial){
		this.razaoSocial = razaoSocial;
	}
	
	@Column(name="cv_endereco")
	public String getEndereco(){
		return endereco;
	}
	public void setEndereco(String endereco){
		this.endereco = endereco;
	}
	
	@Column(name="cv_link_detalhado")
	public String getLinkDetalhado(){
		return linkDetalhado;
	}
	public void setLinkDetalhado(String linkDetalhado){
		this.linkDetalhado = linkDetalhado;
	}
	
	@Column(name="cv_link")
	public String getLink(){
		return link;
	}
	public void setLink(String link){
		this.link = link;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_ehealth_municipio")
	public EhealthMunicipio getEhealthMunicipio() {
		return ehealthMunicipio;
	}
	public void setEhealthMunicipio(EhealthMunicipio ehealthMunicipio) {
		this.ehealthMunicipio = ehealthMunicipio;
	}
	
	@Column(name = "tp_tipo_natureza")
	@Enumerated(EnumType.STRING)
	public TipoEhealthNaturezaEnum getTipoNatureza() {
		return tipoNatureza;
	}
	public void setTipoNatureza(TipoEhealthNaturezaEnum tipoNatureza) {
		this.tipoNatureza = tipoNatureza;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof EhealthEstabelecimento))
			return false;
		
		return ((EhealthEstabelecimento)obj).getIdEhealthMunicipio() == this.idEhealthEstabelecimento;
	}

	@Override
	public int hashCode() {
	    int hash = 1;
	    return hash * 31 + nome.hashCode() + ehealthMunicipio.hashCode();
	}

	@Override
	public String toString() {
		return nome;
	}
}
