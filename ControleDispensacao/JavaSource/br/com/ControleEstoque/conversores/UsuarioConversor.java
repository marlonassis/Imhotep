package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Usuario;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="usuarioConversor")
public class UsuarioConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Usuario> cg = new ConsultaGeral<Usuario>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idUsuario", id);
                Usuario usuario = cg.consultaUnica(new StringBuilder("select o from Usuario o where o.idUsuario = :idUsuario"), hashMap);
                return usuario;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Usuario) value).getIdUsuario());
        }
    	
    	return "";
    }
}
                    