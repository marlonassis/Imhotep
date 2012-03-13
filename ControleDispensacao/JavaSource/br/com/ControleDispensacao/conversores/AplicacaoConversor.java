package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Aplicacao;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="aplicacaoConversor")
public class AplicacaoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Aplicacao> cg = new ConsultaGeral<Aplicacao>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idAplicacao", id);
                Aplicacao aplicacao = cg.consultaUnica(new StringBuilder("select o from Aplicacao o where o.idAplicacao = :idAplicacao"), hashMap);
                return aplicacao;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Aplicacao) value).getIdAplicacao());
        }
    	
    	return "";
    }
}
                    