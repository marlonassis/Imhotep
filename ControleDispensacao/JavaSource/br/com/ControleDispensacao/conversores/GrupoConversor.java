package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Grupo;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="grupoConversor")
public class GrupoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Grupo> cg = new ConsultaGeral<Grupo>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idGrupo", id);
                Grupo grupo = cg.consultaUnica(new StringBuilder("select o from Grupo o where o.idGrupo = :idGrupo"), hashMap);
                return grupo;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Grupo) value).getIdGrupo());
        }
    	
    	return "";
    }
}
                    