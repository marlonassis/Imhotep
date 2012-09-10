package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Material;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="materialConversor")
public class MaterialConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Material> cg = new ConsultaGeral<Material>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idMaterial", id);
                Material material = cg.consultaUnica(new StringBuilder("select o from Material o where o.idMaterial = :idMaterial"), hashMap);
                return material;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Material) value).getIdMaterial());
        }
    	
    	return "";
    }
}
                    