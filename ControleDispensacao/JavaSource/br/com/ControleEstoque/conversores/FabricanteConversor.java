package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Fabricante;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="fabricanteConversor")
public class FabricanteConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Fabricante> cg = new ConsultaGeral<Fabricante>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idFabricante", id);
                Fabricante fabricante = cg.consultaUnica(new StringBuilder("select o from Fabricante o where o.idFabricante = :idFabricante"), hashMap);
                return fabricante;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Fabricante) value).getIdFabricante());
        }
    	
    	return "";
    }
}
                    