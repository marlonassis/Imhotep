package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.UnidadeMaterial;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="unidadeMaterialConversor")
public class UnidadeMaterialConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<UnidadeMaterial> cg = new ConsultaGeral<UnidadeMaterial>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idUnidadeMaterial", id);
                UnidadeMaterial unidadeMaterial = cg.consultaUnica(new StringBuilder("select o from UnidadeMaterial o where o.idUnidadeMaterial = :idUnidadeMaterial"), hashMap);
                return unidadeMaterial;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((UnidadeMaterial) value).getIdUnidadeMaterial());
        }
    	
    	return "";
    }
}
                    