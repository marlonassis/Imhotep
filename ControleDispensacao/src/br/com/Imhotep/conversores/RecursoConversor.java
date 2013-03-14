package br.com.Imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.entidade.Recursos;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="recursoConversor")
public class RecursoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Recursos> cg = new ConsultaGeral<Recursos>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idRecurso", id);
                Recursos recrusos = cg.consultaUnica(new StringBuilder("select o from Recursos o where o.idRecurso = :idRecurso"), hashMap);
                return recrusos;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Recursos) value).getIdRecurso());
        }
    	
    	return "";
    }
}
                    