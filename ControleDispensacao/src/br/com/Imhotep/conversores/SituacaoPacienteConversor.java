package br.com.Imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.entidade.SituacaoPaciente;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="situacaoPacienteConversor")
public class SituacaoPacienteConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<SituacaoPaciente> cg = new ConsultaGeral<SituacaoPaciente>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idSituacaoPaciente", id);
                SituacaoPaciente situacaoPaciente = cg.consultaUnica(new StringBuilder("select o from SituacaoPaciente o where o.idSituacaoPaciente = :idSituacaoPaciente"), hashMap);
                return situacaoPaciente;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((SituacaoPaciente) value).getIdSituacaoPaciente());
        }
    	
    	return "";
    }
}
                    