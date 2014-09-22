package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.LaboratorioExame;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="laboratorioExameArrayConversor")
public class LaboratorioExameArrayConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<LaboratorioExame> cg = new ConsultaGeral<LaboratorioExame>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idLaboratorioExame", id);
                LaboratorioExame exame = cg.consultaUnica(new StringBuilder("select o from LaboratorioExame o where o.idLaboratorioExame = :idLaboratorioExame"), hashMap);
                return exame;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((LaboratorioExame) value).getIdLaboratorioExame());
        }
    	
    	return "";
    }
}
                    