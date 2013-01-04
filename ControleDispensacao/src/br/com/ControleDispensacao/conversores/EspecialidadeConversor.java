package br.com.ControleDispensacao.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.remendo.ConsultaGeral;

@FacesConverter(value="especialidadeConversor")
public class EspecialidadeConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Especialidade> cg = new ConsultaGeral<Especialidade>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idEspecialidade", id);
                Especialidade especialidade = cg.consultaUnica(new StringBuilder("select o from Especialidade o where o.idEspecialidade = :idEspecialidade"), hashMap);
                return especialidade;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Especialidade) value).getIdEspecialidade());
        }
    	
    	return "";
    }
}
                    