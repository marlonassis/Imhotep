package br.com.remendo.conversor;



import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="timeStringConversor")
public class TimeStringConversor implements Converter {

	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if (!arg2.trim().equals("")) {
			try {
				SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");  
				Date data = formatador.parse(arg2);  
				Time time = new Time(data.getTime()); 
				return time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		if (arg2 != null) {
			Time time = (Time) arg2;
			Date data = new Date(time.getTime()); 
			String dataS = new SimpleDateFormat("HH:mm").format(data);
			return dataS;
		}
		return "00:00";
	}
}
                    