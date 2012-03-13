package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.GrupoOrigemPrescricao;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="grupoOrigemPrescricaoConversor")
public class GrupoOrigemPrescricaoConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<GrupoOrigemPrescricao> cg = new ConsultaGeral<GrupoOrigemPrescricao>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idGrupoOrigemPrescricao", id);
                GrupoOrigemPrescricao grupoOrigemPrescricao = cg.consultaUnica(new StringBuilder("select o from GrupoOrigemPrescricao o where o.idGrupoOrigemPrescricao = :idGrupoOrigemPrescricao"), hashMap);
                return grupoOrigemPrescricao;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((GrupoOrigemPrescricao) value).getIdGrupoOrigemPrescricao());
        }
    	
    	return "";
    }
}
                    