package ehealth;

public class UnidadesSaude {
	private int idEstados;
	private String nome;
	private String link;
	
	public UnidadesSaude(){
		
	}
	
	public UnidadesSaude(int idEstados, String nome, String link){
		this.idEstados = idEstados;
		this.nome = nome;
		this.link = link;
	}
	
	public int getIdEstados() {
		return idEstados;
	}
	public void setIdEstados(int idEstados) {
		this.idEstados = idEstados;
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
}
