package br.com.Imhotep.conversores;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.Imhotep.enums.TipoLogradouroEnum;

@FacesConverter(value="tipoLogradouroConversor")
public class TipoLogradouroConversor implements Converter {
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if(arg2 != "" && arg2.equals("Selecione...")){
			return "";
		}
		return arg2 == "" ? "" : procuraTipo(arg2);
	}

	private TipoLogradouroEnum procuraTipo(String arg1) {
		for(TipoLogradouroEnum tipo : TipoLogradouroEnum.values()){
			if(tipo.name().equals(arg1)){
				return tipo;
			}
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		return arg2 == "" ? "" : ((TipoLogradouroEnum) arg2).name() ;
	}

}
                    