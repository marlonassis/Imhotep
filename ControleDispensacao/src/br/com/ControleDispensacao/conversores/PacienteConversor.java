package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Paciente;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="pacienteConversor")
public class PacienteConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Paciente> cg = new ConsultaGeral<Paciente>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idPaciente", id);
                Paciente paciente = cg.consultaUnica(new StringBuilder("select o from Paciente o where o.idPaciente = :idPaciente"), hashMap);
                return paciente;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Paciente) value).getIdPaciente());
        }
    	
    	return "";
    }
}
                    