package br.com.imhotep.mensageiro.entidade;

public class MensagemAnexo {

	private int idMensagemAnexo;
	private Mensagem mensagem;
	private String nome;
	private byte[] arquivo;
	
	
	public int getIdMensagemAnexo() {
		return idMensagemAnexo;
	}
	public void setIdMensagemAnexo(int idMensagemAnexo) {
		this.idMensagemAnexo = idMensagemAnexo;
	}
	public Mensagem getMensagem() {
		return mensagem;
	}
	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
}
