package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.Convenio;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="convenioConversor")
public class ConvenioConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Convenio> cg = new ConsultaGeral<Convenio>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idConvenio", id);
                Convenio convenio = cg.consultaUnica(new StringBuilder("select o from Convenio o where o.idConvenio = :idConvenio"), hashMap);
                return convenio;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Convenio) value).getIdConvenio());
        }
    	
    	return "";
    }
}
                    