package br.com.imhotep.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import br.com.imhotep.auxiliar.Utilities;
import br.com.remendo.gerenciador.GerenciadorConexao;

/**
 * @author marlonassis
 */

@FacesConverter(value="entidadeConversor")
public class EntidadeConversor extends GerenciadorConexao implements Converter {
	
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
    	Object resultado = null;
        if (submittedValue != null && !submittedValue.trim().equals("")) {
        	try {
        		Object obj = component.getValueExpression("value").getType(facesContext.getELContext()).newInstance();
        		String nomePropriedadeId = Utilities.getNomePropriedadeId(obj);
        		iniciarTransacao();
        		int id = Integer.parseInt(submittedValue);
        		Criteria cr = session.createCriteria(obj.getClass());
				cr.add(Restrictions.eq(nomePropriedadeId, id));
				cr.setMaxResults(1);
				resultado = cr.list().get(0);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}finally{
				finallyTransacao();
			}
        }

        return resultado;
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
    	if (value != null && !value.equals("")) {
			return String.valueOf(Utilities.getValorPropriedadeId(value));
        }
    	
    	return "";
    }
}
                    