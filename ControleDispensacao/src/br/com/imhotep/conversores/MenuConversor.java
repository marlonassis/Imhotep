package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.Menu;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="menuConversor")
public class MenuConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Menu> cg = new ConsultaGeral<Menu>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idMenu", id);
                Menu menu = cg.consultaUnica(new StringBuilder("select o from Menu o where o.idMenu = :idMenu"), hashMap);
                return menu;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Menu) value).getIdMenu());
        }
    	
    	return "";
    }
}
                    