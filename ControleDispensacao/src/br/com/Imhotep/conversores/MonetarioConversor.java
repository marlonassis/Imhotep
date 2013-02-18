package br.com.Imhotep.conversores;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="monetarioConversor")
public class MonetarioConversor extends br.com.remendo.conversor.MonetarioConversor {
	private final NumberFormat formatter = new DecimalFormat("#,###.##");
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
		if(value != null && value != ""){
			double v = (Double) value;
			if( v == 0.0){
				return null;
			}else{
				String format = formatter.format(v);
				return format;
			}
		}
		
		return null;
	}
}
