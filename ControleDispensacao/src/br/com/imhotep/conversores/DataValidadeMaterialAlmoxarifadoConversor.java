package br.com.imhotep.conversores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DoubleConverter;
import javax.faces.convert.FacesConverter;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;

@FacesConverter(value="dataValidadeMaterialAlmoxarifadoConversor")
public class DataValidadeMaterialAlmoxarifadoConversor extends DoubleConverter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if(arg2 != null && !arg2.isEmpty()){
			try {
				Date dt = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_BRASIL).parse(arg2);
				dt = new Utilitarios().ajustarUltimaHoraDia(dt);
				return dt;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if(arg2 != null && arg2 != ""){
			Date dt = (Date) arg2;
			dt = new Utilitarios().ajustarUltimaHoraDia(dt);
			return new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_BRASIL).format(dt);
		}
		return "";
	}

}
