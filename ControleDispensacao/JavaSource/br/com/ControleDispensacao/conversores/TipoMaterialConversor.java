package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.TipoMaterial;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="tipoMaterialConversor")
public class TipoMaterialConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<TipoMaterial> cg = new ConsultaGeral<TipoMaterial>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idTipoMaterial", id);
                TipoMaterial tipoMaterial = cg.consultaUnica(new StringBuilder("select o from TipoMaterial o where o.idTipoMaterial = :idTipoMaterial"), hashMap);
                return tipoMaterial;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((TipoMaterial) value).getIdTipoMaterial());
        }
    	
    	return "";
    }
}
                    