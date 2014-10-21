package br.com.imhotep.enums;

/**
 * @author marlonassis
 */

public enum TipoEstadoEnum {
	
	AC("Acre"), 
	AL("Alagoas"), 
	AM("Amazonas"), 
	AP("Amap‡"), 
	BA("Bahia"), 
	CE("Cear‡"), 
	DF("Distrito Federal"), 
	ES("Esp’rito Santo"), 
	GO("Goi‡s"),   
    MA("Maranh‹o"), 
    MG("Minas Gerais"), 
    MS("Mato Grosso do Sul"), 
    MT("Mato Grosso"), 
    PA("Par‡"), 
    PB("Para’ba"), 
    PE("Pernambuco"), 
    PI("Piau’"), 
    PR("Paran‡"),   
    RJ("Rio de Janeiro"), 
    RN("Rio Grande do Norte"), 
    RO("Rond™nia"), 
    RR("Roraima"), 
    RS("Rio Grande do Sul"), 
    SC("Santa Catarina"), 
    SE("Sergipe"), 
    SP("S‹o Paulo"), 
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
