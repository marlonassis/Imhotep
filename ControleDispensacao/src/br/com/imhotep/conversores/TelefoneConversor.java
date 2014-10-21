package br.com.imhotep.conversores;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="telefoneConversor")
public class TelefoneConversor implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if(arg2 != null && arg2 != ""){
			arg2 = arg2.replace("(", "");
			arg2 = arg2.replace(")", "");
			arg2 = arg2.replace("-", "");
			return arg2;
		}
		return arg2;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if(arg2 == null){
			return "";
		}
		return arg2.toString();
	}
	
}
                    