package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.EstoqueCentroCirurgico;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="estoqueCentroCirurgicoConversor")
public class EstoqueCentroCirurgicoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<EstoqueCentroCirurgico> cg = new ConsultaGeral<EstoqueCentroCirurgico>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idEstoqueCentroCirurgico", id);
                EstoqueCentroCirurgico estoqueCentroCirurgico = cg.consultaUnica(new StringBuilder("select o from EstoqueCentroCirurgico o where o.idEstoqueCentroCirurgico = :idEstoqueCentroCirurgico"), hashMap);
                return estoqueCentroCirurgico;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((EstoqueCentroCirurgico) value).getIdEstoqueCentroCirurgico());
        }
    	
    	return "";
    }
}
                    