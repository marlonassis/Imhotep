package br.com.ControleDispensacao.controladorBancoDados;

public class PropriedadeTabela {
	private String nomePropriedade;
	private String tipoPropriedade;
	private boolean nulo;
	
	public String getNomePropriedade() {
		return nomePropriedade;
	}
	public void setNomePropriedade(String nomePropriedade) {
		this.nomePropriedade = nomePropriedade;
	}
	public String getTipoPropriedade() {
		return tipoPropriedade;
	}
	public void setTipoPropriedade(String tipoPropriedade) {
		this.tipoPropriedade = tipoPropriedade;
	}
	public boolean isNulo() {
		return nulo;
	}
	public void setNulo(boolean nulo) {
		this.nulo = nulo;
	}
}
