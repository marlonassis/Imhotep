package br.com.ControleEstoque.conversores;



import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ControleDispensacao.entidade.Livro;
import br.com.nucleo.ConsultaGeral;

@FacesConverter(value="livroConversor")
public class LivroConversor implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue != null && !submittedValue.trim().equals("")) {
            try {
                int id = Integer.parseInt(submittedValue);
                ConsultaGeral<Livro> cg = new ConsultaGeral<Livro>();
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("idLivro", id);
                Livro livro = cg.consultaUnica(new StringBuilder("select o from Livro o where o.idLivro = :idLivro"), hashMap);
                return livro;
            } catch(NumberFormatException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
    		return String.valueOf(((Livro) value).getIdLivro());
        }
    	
    	return "";
    }
}
                    