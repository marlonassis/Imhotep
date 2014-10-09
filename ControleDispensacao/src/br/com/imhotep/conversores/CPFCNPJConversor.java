package br.com.imhotep.conversores;



import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.remendo.validador.CpfCnpjValidador;

@FacesConverter(value="cpfCnpjConversor")
public class CPFCNPJConversor extends br.com.remendo.conversor.CNPJConversor {
	private CpfCnpjValidador validador = new CpfCnpjValidador();
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		if(arg2 != null && arg2 != ""){
			arg2 = arg2.replace("/", "");
			arg2 = arg2.replace(".", "");
			arg2 = arg2.replace("-", "");
			boolean cnpjValido = validador.isValidCNPJ(arg2);
			boolean cpfValido = validador.isValidCPF(arg2);
			if(!cnpjValido && !cpfValido){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF ou CNPJ inv‡lido", null));
				return null;
			}
			return arg2;
		}
		return arg2;
	}

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) throws ConverterException {
		return arg2.toString();
	}
}
                    