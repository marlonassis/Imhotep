package br.com.ControleDispensacao.controladorBancoDados;

public class ConstraintTabela {
	private String nomeConstraint;
	private String tipoConstraint;
	private String nomePropriedadeLocal;
	private String nomeTabelaReferencia;
	private String nomePropriedadeReferencia;
	private String tipoDelete;
	private String tipoUpdate;
	
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
	
	@Override
	public String toString() {
		String constraintInicial = " constraint "+getNomeConstraint();
		if(getTipoConstraint().equals("PRIMARY KEY")){
			constraintInicial = constraintInicial.concat(" PRIMARY KEY (");
			constraintInicial = constraintInicial.concat(getNomePropriedadeLocal());
			constraintInicial = constraintInicial.concat(")");
		}else{
			if(getTipoConstraint().equals("FOREIGN KEY")){
				constraintInicial = constraintInicial.concat(" FOREIGN KEY (");
				constraintInicial = constraintInicial.concat(getNomePropriedadeLocal());
				constraintInicial = constraintInicial.concat(") ");
				constraintInicial = constraintInicial.concat("references ");
				constraintInicial = constraintInicial.concat(getNomeTabelaReferencia());
				constraintInicial = constraintInicial.concat(" (").concat(getNomePropriedadeLocal()).concat(")");
				constraintInicial = constraintInicial.concat(" match simple on update ");
				constraintInicial = constraintInicial.concat(getTipoUpdate());
				constraintInicial = constraintInicial.concat("on delete ");
				constraintInicial = constraintInicial.concat(getTipoDelete());
			}else{
				if(getTipoConstraint().equals("UNIQUE")){
					constraintInicial = constraintInicial.concat(" UNIQUE (");
					constraintInicial = constraintInicial.concat(getNomePropriedadeLocal());
					constraintInicial = constraintInicial.concat(") ");
				}
			}
		}
		
		return constraintInicial;
	}
}
