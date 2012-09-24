package br.com.ControleDispensacao.controladorBancoDados;

import java.util.ArrayList;
import java.util.List;

public class Tabela {
	private String nomeTabela;
	private List<PropriedadeTabela> propriedades = new ArrayList<PropriedadeTabela>();
	private List<ConstraintTabela> constraints = new ArrayList<ConstraintTabela>();
	
	public String getNomeTabela() {
		return nomeTabela;
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	
	public List<PropriedadeTabela> getPropriedades() {
		return propriedades;
	}
	public void setPropriedades(List<PropriedadeTabela> propriedades) {
		this.propriedades = propriedades;
	}
	
	public List<ConstraintTabela> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<ConstraintTabela> constraints) {
		this.constraints = constraints;
	}
	
}
