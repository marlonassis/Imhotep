package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Estado;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="estadoConversor")
public class EstadoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Estado> cg = new ConsultaGeral<Estado>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idEstado", id);
                Estado estado = cg.consultaUnica(new StringBuilder("select o from Estado o where o.idEstado = :idEstado"), hashMap);
                return estado;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Estado) value).getIdEstado());
        }
    	
    	return "";
    }
}
                    