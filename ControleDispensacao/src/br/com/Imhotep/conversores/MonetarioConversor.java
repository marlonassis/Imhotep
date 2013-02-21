package br.com.Imhotep.conversores;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="monetarioConversor")
public class MonetarioConversor extends DoubleConverter {
	
	public static void main(String[] args) {
		MonetarioConversor mc = new MonetarioConversor();
		System.out.println(mc.getAsString(null, null, 0.4d));
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		arg2 = arg2.replace(".", "");
		return super.getAsObject(arg0, arg1, arg2);
	}
	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 != ""){
			double v = (Double) arg2;
			if( v == 0.0d){
				return null;
			}else{
				return new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale ("pt", "BR"))).format(v);
			}
		}
		return null;
	}

}
