package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstadoEnum {
	
	AC("Acre"), 
	AL("Alagoas"), 
	AM("Amazonas"), 
	AP("Amapá"), 
	BA("Bahia"), 
	CE("Ceará"), 
	DF("Distrito Federal"), 
	ES("Espirito Santo"), 
	GO("Goiás"),   
    MA("Maranhão"), 
    MG("Minas Gerais"), 
    MS("Mato Grosso do Sul"), 
    MT("Mato Grosso"), 
    PA("Pará"), 
    PB("Paraná"), 
    PE("Pernambuco"), 
    PI("Piauí"), 
    PR("Paraná"),   
    RJ("Rio de Janeiro"), 
    RN("Rio Grande do Norte"), 
    RO("Rondônia"), 
    RR("Roraima"), 
    RS("Rio Grande do Sul"), 
    SC("Santa Cararina"), 
    SE("Sergipe"), 
    SP("São Paulo"), 
    TO("Tocantins"); 
	
	private String label;
	
	TipoEstadoEnum(String label){
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
