package br.com.remendo.conversor;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="monetarioConversor")
public class MonetarioConversor implements Converter {
	
	private final NumberFormat formatter = new DecimalFormat("#,###.##");

	public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
		if (value != null && value != "") {
			try {
				Double valor = null;
				valor = formatter.parse(value).doubleValue();
				return valor;
			} catch (Exception e) {
				throw new ConverterException(new FacesMessage("Formato inválido: " + value));
			}
		}
		return null;
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if(value != null && value != ""){
			if(((Number) value).doubleValue() == 0.0){
				return null;
			}else{
				String format = formatter.format(value);
				return format;
			}
		}
		
		return null;
	}
	
}
