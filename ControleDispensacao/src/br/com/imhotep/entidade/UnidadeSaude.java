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

import br.com.imhotep.enums.TipoUnidadeSaudeEnum;

@Entity
@Table(name = "tb_unidade_saude")
public class UnidadeSaude {
	private int idUnidadeSaude;
	private String estabelecimento;
	private String endereco;
	private String bairro;
	private Long telefone;
	private TipoUnidadeSaudeEnum tipoUnidade;
	private Cidade cidade;
	
	@SequenceGenerator(name = "generator", sequenceName = "public.tb_unidade_saude_id_unidade_saude_seq")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id_unidade_saude", unique = true, nullable = false)
	public int getIdUnidadeSaude() {
		return this.idUnidadeSaude;
	}
	
	public void setIdUnidadeSaude(int idUnidadeSaude){
		this.idUnidadeSaude = idUnidadeSaude;
	}
	
	@Column(name = "cv_tipo_unidade")
	@Enumerated(EnumType.STRING)
	public TipoUnidadeSaudeEnum getTipoUnidade() {
		return tipoUnidade;
	}
	public void setTipoUnidade(TipoUnidadeSaudeEnum tipoUnidade) {
		this.tipoUnidade = tipoUnidade;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cidade")
	public Cidade getCidade(){
		return cidade;
	}
	
	public void setCidade(Cidade cidade){
		this.cidade = cidade;
	}
	
	@Column(name = "cv_estabelecimento")
	public String getEstabelecimento() {
		return estabelecimento;
	}
	public void setEstabelecimento(String estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
	@Column(name = "cv_endereco")
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	@Column(name = "cv_bairro")
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	@Column(name = "in_telefone")
	public Long getTelefone() {
		return telefone;
	}
	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(!(obj instanceof UnidadeSaude))
			return false;
		
		return ((UnidadeSaude)obj).getIdUnidadeSaude() == this.idUnidadeSaude;
	}

	@Override
	public String toString() {
		return this.estabelecimento;
	}
	
}
