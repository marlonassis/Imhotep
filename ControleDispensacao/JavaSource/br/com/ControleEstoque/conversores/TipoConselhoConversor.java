package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Grupo;
import br.com.ControleDispensacao.entidade.TipoConselho;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="tipoConselhoConversor")
public class TipoConselhoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<TipoConselho> cg = new ConsultaGeral<TipoConselho>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idTipoConselho", id);
                TipoConselho tipoConselho = cg.consultaUnica(new StringBuilder("select o from TipoConselho o where o.idTipoConselho = :idTipoConselho"), hashMap);
                return tipoConselho;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((TipoConselho) value).getIdTipoConselho());
        }
    	
    	return "";
    }
}
                    