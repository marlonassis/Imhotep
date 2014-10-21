package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.TipoMovimento;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="tipoMovimentoConversor")
public class TipoMovimentoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idTipoMovimento", id);
                TipoMovimento tipoMovimento = cg.consultaUnica(new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = :idTipoMovimento"), hashMap);
                return tipoMovimento;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((TipoMovimento) value).getIdTipoMovimento());
        }
    	
    	return "";
    }
}
                    