package br.com.imhotep.entidade.extra;

public class FinanceiroGrupoMaterialDataMovimento {
	private String tipoMovimento;
	private int total;
	
	public FinanceiroGrupoMaterialDataMovimento(){
		super();
	}
	
	public FinanceiroGrupoMaterialDataMovimento(String tipoMovimento, int total){
		super();
		this.tipoMovimento = tipoMovimento;
		this.total = total;
	}
	
	public String getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(String tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
