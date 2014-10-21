package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.TipoExame;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="tipoExameConversor")
public class TipoExameConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<TipoExame> cg = new ConsultaGeral<TipoExame>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idTipoExame", id);
                TipoExame tipoExame = cg.consultaUnica(new StringBuilder("select o from TipoExame o where o.idTipoExame = :idTipoExame"), hashMap);
                return tipoExame;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((TipoExame) value).getIdTipoExame());
        }
    	
    	return "";
    }
}
                    