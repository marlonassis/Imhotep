package br.com.imhotep.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.entidade.Fornecedor;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="fornecedorConversor")
public class FornecedorConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Fornecedor> cg = new ConsultaGeral<Fornecedor>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idFornecedor", id);
                Fornecedor fornecedor = cg.consultaUnica(new StringBuilder("select o from Fornecedor o where o.idFornecedor = :idFornecedor"), hashMap);
                return fornecedor;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Fornecedor) value).getIdFornecedor());
        }
    	
    	return "";
    }
}
                    