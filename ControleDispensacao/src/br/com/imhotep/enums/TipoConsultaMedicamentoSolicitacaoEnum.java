package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoConsultaMedicamentoSolicitacaoEnum {
	TM("Todos os Medicamentos"), 
	MS("Medicamento Mais Solicitados"),
	UP("òltimo Pedido");
	
	private String label;
	
	TipoConsultaMedicamentoSolicitacaoEnum(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	@Override
	public String toString() {
		return label;
	}
}
