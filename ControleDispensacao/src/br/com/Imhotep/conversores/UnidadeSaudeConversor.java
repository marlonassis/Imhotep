package br.com.Imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.entidade.UnidadeSaude;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="unidadeSaudeConversor")
public class UnidadeSaudeConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<UnidadeSaude> cg = new ConsultaGeral<UnidadeSaude>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idUnidadeSaude", id);
                UnidadeSaude unidadeSaude = cg.consultaUnica(new StringBuilder("select o from UnidadeSaude o where o.idUnidadeSaude = :idUnidadeSaude"), hashMap);
                return unidadeSaude;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((UnidadeSaude) value).getIdUnidadeSaude());
        }
    	
    	return "";
    }
}
                    