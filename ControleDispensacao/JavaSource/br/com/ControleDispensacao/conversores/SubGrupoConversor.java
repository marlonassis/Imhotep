package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.SubGrupo;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="subGrupoConversor")
public class SubGrupoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<SubGrupo> cg = new ConsultaGeral<SubGrupo>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idSubGrupo", id);
                SubGrupo subgrupo = cg.consultaUnica(new StringBuilder("select o from SubGrupo o where o.idSubGrupo = :idSubGrupo"), hashMap);
                return subgrupo;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((SubGrupo) value).getIdSubGrupo());
        }
    	
    	return "";
    }
}
                    