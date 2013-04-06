package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.ListaEspecial;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="listaEspecialConversor")
public class ListaConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<ListaEspecial> cg = new ConsultaGeral<ListaEspecial>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idListaEspecial", id);
                ListaEspecial listaEspecial = cg.consultaUnica(new StringBuilder("select o from ListaEspecial o where o.idListaEspecial = :idListaEspecial"), hashMap);
                return listaEspecial;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((ListaEspecial) value).getIdListaEspecial());
        }
    	
    	return "";
    }
}
                    