package br.com.remendo.gerenciador;



public class Autenticador {
	
	private Usuario usuarioAtual;
	
	public Usuario getUsuarioAtual() {
		return usuarioAtual;
	}

	public void setUsuarioAtual(Usuario usuarioAtual) {
		this.usuarioAtual = usuarioAtual;
	}

	public static Autenticador getInstancia() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return (Autenticador) new ControleInstancia().procuraInstancia(Autenticador.class);
	}
	
}
