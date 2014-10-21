package br.com.imhotep.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author marlonassis
 */

@FacesConverter(value="enumConversor")
public class EnumConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
        	try {
        		String[] clientId = component.getClientId().split("\\:");
				Enum<?>[] enumConstantes = new br.com.remendo.utilidades.Utilitarios().getEnumConstantes(clientId[clientId.length-1]);
        		for(Enum<?> item : enumConstantes){
        			if(item.name().equals(submittedValue)){
        				return item;
        			}
        		}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
        }
        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		Enum<?> enumm = (Enum<?>) value;
			return enumm.name();
        }
    	
    	return "";
    }
}
                    