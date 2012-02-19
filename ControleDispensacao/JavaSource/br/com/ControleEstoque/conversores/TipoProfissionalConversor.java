package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.TipoProfissional;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="tipoProfissionalConversor")
public class TipoProfissionalConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<TipoProfissional> cg = new ConsultaGeral<TipoProfissional>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idTipoProfissional", id);
                TipoProfissional tipoProfissional = cg.consultaUnica(new StringBuilder("select o from TipoProfissional o where o.idTipoProfissional = :idTipoProfissional"), hashMap);
                return tipoProfissional;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((TipoProfissional) value).getIdTipoProfissional());
        }
    	
    	return "";
    }
}
                    