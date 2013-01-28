package br.com.Imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="unidadeConversor")
public class UnidadeConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Unidade> cg = new ConsultaGeral<Unidade>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idUnidade", id);
                Unidade unidade = cg.consultaUnica(new StringBuilder("select o from Unidade o where o.idUnidade = :idUnidade"), hashMap);
                return unidade;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Unidade) value).getIdUnidade());
        }
    	
    	return "";
    }
}
                    