package br.com.Imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.entidade.Hospital;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="hospitalConversor")
public class HospitalConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Hospital> cg = new ConsultaGeral<Hospital>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idHospital", id);
                Hospital hospital = cg.consultaUnica(new StringBuilder("select o from Hospital o where o.idHospital = :idHospital"), hashMap);
                return hospital;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Hospital) value).getIdHospital());
        }
    	
    	return "";
    }
}
                    