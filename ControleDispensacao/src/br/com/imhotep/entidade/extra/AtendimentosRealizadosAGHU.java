package br.com.imhotep.entidade.extra;

import java.util.Date;

import br.com.imhotep.auxiliar.Utilitarios;

public class AtendimentosRealizadosAGHU {
	private Integer prontuario;
	private String nome;
	private Long cartaoSus;
	private Date dataNascimento;
	private String logradouro;
	private Integer numero;
	private String complemento;
	private String bairro;
	private Integer cep;
	private String municipio;
	private String crm;
	private String uf;
	
	public AtendimentosRealizadosAGHU(){
		super();
	}
	
	public AtendimentosRealizadosAGHU(Integer prontuario, String nome,
			Long cartaoSus, Date dataNascimento, String logradouro,
			Integer numero, String complemento, String bairro, Integer cep, String municipio, String crm, String uf) {
		super();
		this.prontuario = prontuario;
		this.nome = nome;
		this.cartaoSus = cartaoSus;
		this.dataNascimento = dataNascimento;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.municipio = municipio;
		this.crm = crm;
		this.uf = uf;
	}
	
	public Integer getProntuario() {
		return prontuario;
	}
	public void setProntuario(Integer prontuario) {
		this.prontuario = prontuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getCartaoSus() {
		return cartaoSus;
	}
	public void setCartaoSus(Long cartaoSus) {
		this.cartaoSus = cartaoSus;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	
	public Integer getIdade(){
		if(dataNascimento != null)
			return Utilitarios.idadeAtualInteger(dataNascimento);
		else
			return null;
	}
	
	public String getProntuarioString(){
		String p = "";
		if(getProntuario() != null){
			String prontuarioS = String.valueOf(getProntuario());
			int length = prontuarioS.length();
			p = prontuarioS.substring(0, length-1);
			p = p.concat("/").concat(prontuarioS.substring(length-1, length));
		}
		return p;
	}
	
	public String getEndereco(){
		Object[] array = null;
		if(getLogradouro() != null && !getLogradouro().isEmpty() && !getLogradouro().equals("null"))
			array = Utilitarios.addElemento(array, getLogradouro());
		
		if(getNumero() != null)
			array = Utilitarios.addElemento(array, String.valueOf(getNumero()));
			
		if(getBairro() != null && !getBairro().isEmpty() && !getBairro().equals("null"))
			array = Utilitarios.addElemento(array, getBairro());

		if(getCep() != null)
			array = Utilitarios.addElemento(array, String.valueOf(getCep()));
		
		if(getMunicipio() != null && !getMunicipio().isEmpty() && !getMunicipio().equals("null"))
			array = Utilitarios.addElemento(array, getMunicipio().concat(" - ").concat(getUf()));
		
		String res = "";
		if(array == null){
			String[] a = {};
			array = a;
		}
		for(int i = 0; i < array.length; i++){
			res += (String) array[i];
			if(i < array.length - 1){
				res += ", ";
			}
		}
		
		return res;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cartaoSus == null) ? 0 : cartaoSus.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((prontuario == null) ? 0 : prontuario.hashCode());
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
		AtendimentosRealizadosAGHU other = (AtendimentosRealizadosAGHU) obj;
		if (cartaoSus == null) {
			if (other.cartaoSus != null)
				return false;
		} else if (!cartaoSus.equals(other.cartaoSus))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (prontuario == null) {
			if (other.prontuario != null)
				return false;
		} else if (!prontuario.equals(other.prontuario))
			return false;
		return true;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	
	
	
}
