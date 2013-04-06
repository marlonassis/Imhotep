package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.Familia;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="familiaConversor")
public class FamiliaConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Familia> cg = new ConsultaGeral<Familia>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idFamilia", id);
                Familia familia = cg.consultaUnica(new StringBuilder("select o from Familia o where o.idFamilia = :idFamilia"), hashMap);
                return familia;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Familia) value).getIdFamilia());
        }
    	
    	return "";
    }
}
                    