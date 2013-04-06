package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.Painel;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="painelConversor")
public class PainelConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Painel> cg = new ConsultaGeral<Painel>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idPainel", id);
                Painel painel = cg.consultaUnica(new StringBuilder("select o from Painel o where o.idPainel = :idPainel"), hashMap);
                return painel;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Painel) value).getIdPainel());
        }
    	
    	return "";
    }
}
                    