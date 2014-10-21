package br.com.imhotep.entidade;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.imhotep.enums.TipoEhealthNaturezaEnum;
import br.com.imhotep.enums.TipoEhealthUnidadeSaudeEnum;

@Entity
@Table(name = "tb_ehealth_estabelecimento", schema="ehealth")
public class EhealthEstabelecimento {
	private int idEhealthEstabelecimento;
	private String nome;
	private String link;
	private TipoEhealthNaturezaEnum tipoNatureza;
	private String razaoSocial;
	private String endereco;
	private String linkDetalhado;
	private EhealthMunicipio ehealthMunicipio;
	private Profissional pesquisador;
	private Date dataCadastro;
	private TipoEhealthUnidadeSaudeEnum tipoUnidade;
	private EhealthFormulario formulario;
	
	@SequenceGenerator(name = "generator", sequenceName = "ehealth.tb_ehealth_estabelecimento_id_ehealth_estabelecimento_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_ehealth_estabelecimento", unique = true, nullable = false)
	public int getIdEhealthEstabelecimento() {
		return idEhealthEstabelecimento;
	}
	public void setIdEhealthEstabelecimento(int idEhealthEstabelecimento) {
		this.idEhealthEstabelecimento = idEhealthEstabelecimento;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_pesquisador")
	public Profissional getPesquisador() {
		return pesquisador;
	}
	public void setPesquisador(Profissional pesquisador) {
		this.pesquisador = pesquisador;
	}
	
	@Column(name="tp_tipo_unidade")
	@Enumerated(EnumType.STRING)
	public TipoEhealthUnidadeSaudeEnum getTipoUnidade() {
		return tipoUnidade;
	}
	public void setTipoUnidade(TipoEhealthUnidadeSaudeEnum tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_data_cadastro")
	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	@OneToOne(mappedBy="ehealthEstabelecimento")  
	public EhealthFormulario getFormulario() {
		return formulario;
	}
	public void setFormulario(EhealthFormulario formulario) {
		this.formulario = formulario;
	}
	
	@Transient
	public String getLocalizacao(){
		return getEhealthMunicipio().getNome().concat(" - ").concat(getEhealthMunicipio().getEhealthEstado().getNomeEstadoPais());
	}
	
	@Override
	public String toString() {
		return nome;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataCadastro == null) ? 0 : dataCadastro.hashCode());
		result = prime
				* result
				+ ((ehealthMunicipio == null) ? 0 : ehealthMunicipio.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + idEhealthEstabelecimento;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result
				+ ((linkDetalhado == null) ? 0 : linkDetalhado.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime
				* result
				+ ((pesquisador == null) ? 0
						: pesquisador.hashCode());
		result = prime * result
				+ ((razaoSocial == null) ? 0 : razaoSocial.hashCode());
		result = prime * result
				+ ((tipoNatureza == null) ? 0 : tipoNatureza.hashCode());
		result = prime * result
				+ ((tipoUnidade == null) ? 0 : tipoUnidade.hashCode());
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
		EhealthEstabelecimento other = (EhealthEstabelecimento) obj;
		if (dataCadastro == null) {
			if (other.dataCadastro != null)
				return false;
		} else if (!dataCadastro.equals(other.dataCadastro))
			return false;
		if (ehealthMunicipio == null) {
			if (other.ehealthMunicipio != null)
				return false;
		} else if (!ehealthMunicipio.equals(other.ehealthMunicipio))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (idEhealthEstabelecimento != other.idEhealthEstabelecimento)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (linkDetalhado == null) {
			if (other.linkDetalhado != null)
				return false;
		} else if (!linkDetalhado.equals(other.linkDetalhado))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pesquisador == null) {
			if (other.pesquisador != null)
				return false;
		} else if (!pesquisador
				.equals(other.pesquisador))
			return false;
		if (razaoSocial == null) {
			if (other.razaoSocial != null)
				return false;
		} else if (!razaoSocial.equals(other.razaoSocial))
			return false;
		if (tipoNatureza != other.tipoNatureza)
			return false;
		if (tipoUnidade != other.tipoUnidade)
			return false;
		return true;
	}
	
}