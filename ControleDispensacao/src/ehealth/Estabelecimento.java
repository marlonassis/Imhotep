package ehealth;

public class Estabelecimento {
	
	private int idEstabelecimento;
	private Municipio municipio;
	private String nome;
	private String natureza;
	private String razaoSocial;
	private String link;
	private String linkDetalhado;
	
	public Estabelecimento(){
		
	}
	
	public Estabelecimento(int idEstabelecimento, String nome, String natureza, 
			String razaoSocial,	String link, Municipio municipio){
		this.idEstabelecimento = idEstabelecimento;
		this.nome = nome;
		this.nome = natureza;
		this.nome = razaoSocial;
		this.link = link;
		this.municipio = municipio;
	}
	
	public Estabelecimento(int idEstabelecimento, String nome, String natureza, 
			String razaoSocial,	String link, int idMunicipio){
		this.idEstabelecimento = idEstabelecimento;
		this.nome = nome;
		this.nome = natureza;
		this.nome = razaoSocial;
		this.link = link;
		this.municipio = new Municipio();
		this.municipio.setIdMunicipio(idMunicipio);
	}

	public int getIdEstabelecimento() {
		return idEstabelecimento;
	}

	public void setIdEstabelecimento(int idEstabelecimento) {
		this.idEstabelecimento = idEstabelecimento;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNatureza() {
		return natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLinkDetalhado() {
		return linkDetalhado;
	}

	public void setLinkDetalhado(String linkDetalhado) {
		this.linkDetalhado = linkDetalhado;
	}
	
	
}
