package ehealth;

public class Municipio {
	
	private int idMunicipio;
	private String nome;
	private String link;
	private Estado estado;
	
	public Municipio(){
		
	}
	
	public Municipio(int idMunicipio, String nome, String link, Estado estado){
		this.idMunicipio=idMunicipio;
		this.nome=nome;
		this.link=link;
		this.estado = estado;
	}
	
	public Municipio(int idMunicipio, String nome, String link, int idEstado){
		this.idMunicipio=idMunicipio;
		this.nome=nome;
		this.link=link;
		this.estado = new Estado();
		this.estado.setIdEstados(idEstado);
	}
	
	public int getIdMunicipio() {
		return idMunicipio;
	}
	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Estado getEstado() {
		return estado;
	}
	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	
}
