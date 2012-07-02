package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Profissional;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="profissionalConversor")
public class ProfissionalConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Profissional> cg = new ConsultaGeral<Profissional>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idProfissional", id);
                Profissional profissional = cg.consultaUnica(new StringBuilder("select o from Profissional o where o.idProfissional = :idProfissional"), hashMap);
                return profissional;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }
    
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Profissional) value).getIdProfissional());
        }
    	
    	return "";
    }
}
                    