package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Cidade;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="cidadeConversor")
public class CidadeConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Cidade> cg = new ConsultaGeral<Cidade>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idCidade", id);
                Cidade cidade = cg.consultaUnica(new StringBuilder("select o from Cidade o where o.idCidade = :idCidade"), hashMap);
                return cidade;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Cidade) value).getIdCidade());
        }
    	
    	return "";
    }
}
                    