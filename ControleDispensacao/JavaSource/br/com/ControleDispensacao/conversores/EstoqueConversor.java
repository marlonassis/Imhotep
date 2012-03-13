package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="estoqueConversor")
public class EstoqueConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idEstoque", id);
                Estoque estoque = cg.consultaUnica(new StringBuilder("select o from Estoque o where o.idEstoque = :idEstoque"), hashMap);
                return estoque;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Estoque) value).getIdEstoque());
        }
    	
    	return "";
    }
}
                    