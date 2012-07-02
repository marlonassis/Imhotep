package br.com.nucleo.conversor;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="stringDateConversor")
public class StringDateConversor implements Converter {

	
	private String validaData(String dataS){
		String splitS[] = dataS.split("/");
		if(splitS.length == 2){
			dataS = "01/".concat(splitS[0]).concat("/").concat(splitS[1]);
		}
		return dataS;
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if (!arg2.trim().equals("")) {
			arg2 = validaData(arg2);
			try {
				Date data = new SimpleDateFormat("dd/MM/yyyy").parse(arg2);
				return data;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (arg2 != null) {
			String dataS = new SimpleDateFormat("dd/MM/yyyy").format(arg2);
			return dataS;
		}
		return null;
	}
}
                    