package br.com.ControleDispensacao.controladorBancoDados;

public class ConstraintTabela {
	private String nomeConstraint;
	private String tipoConstraint;
	private String nomePropriedadeLocal;
	private String nomeTabelaReferencia;
	private String nomePropriedadeReferencia;
	private String tipoDelete;
	private String tipoUpdate;
	private String nomeCompletoConstraint;
	
	public String getNomeConstraint() {
		return nomeConstraint;
	}
	public void setNomeConstraint(String nomeConstraint) {
		this.nomeConstraint = nomeConstraint;
	}
	
	public String getTipoConstraint() {
		return tipoConstraint;
	}
	public void setTipoConstraint(String tipoConstraint) {
		this.tipoConstraint = tipoConstraint;
	}
	
	public String getNomePropriedadeLocal() {
		return nomePropriedadeLocal;
	}
	public void setNomePropriedadeLocal(String nomeColunaLocal) {
		this.nomePropriedadeLocal = nomeColunaLocal;
	}
	
	public String getNomeTabelaReferencia() {
		return nomeTabelaReferencia;
	}
	public void setNomeTabelaReferencia(String nomeTabelaReferencia) {
		this.nomeTabelaReferencia = nomeTabelaReferencia;
	}
	
	public String getNomePropriedadeReferencia() {
		return nomePropriedadeReferencia;
	}
	public void setNomePropriedadeReferencia(String nomeColunaReferencia) {
		this.nomePropriedadeReferencia = nomeColunaReferencia;
	}
	
	public String getTipoDelete() {
		return tipoDelete;
	}
	public void setTipoDelete(String tipoDelete) {
		this.tipoDelete = tipoDelete;
	}
	
	public String getTipoUpdate() {
		return tipoUpdate;
	}
	public void setTipoUpdate(String tipoUpdate) {
		this.tipoUpdate = tipoUpdate;
	}
	
}
