package br.com.imhotep.conversores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.auxiliar.Constantes;

@FacesConverter(value="dataConversor")
public class DataConversor extends DoubleConverter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 != null && !arg2.isEmpty()){
			try {
				return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Constantes.LOCALE_BRASIL).parse(arg2);
			} catch (ParseException e) {
				e.printStackTrace();
				try {
					return new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_BRASIL).parse(arg2);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 != ""){
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Constantes.LOCALE_BRASIL).format((Date) arg2);
		}
		return "";
	}

}
