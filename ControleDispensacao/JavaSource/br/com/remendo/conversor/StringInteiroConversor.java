package br.com.remendo.conversor;



import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="stringInteiroConversor")
public class StringInteiroConversor implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if (!arg2.trim().equals("")) {
			NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
			try {
				Number valor = nf.parse(arg2);
				return valor;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (arg2 != null) {
			String valorS = ((Number) arg2).toString();
			while(valorS.length() < 3){
				valorS.concat("0");
			}
			return valorS;
		}
		return null;
	}
}
                    