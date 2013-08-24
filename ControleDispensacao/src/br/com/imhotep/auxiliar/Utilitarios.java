package br.com.imhotep.auxiliar;


import java.text.SimpleDateFormat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Profissional;


/**
 * @author marlonassis
 *
 */
@ManagedBean(name="util")
@RequestScoped
public class Utilitarios extends br.com.remendo.utilidades.Utilitarios {
	
	public static String getPatternChaveUnicaPreparada(Profissional profissional){
		String valor = Constantes.PATTERN_CHAVE_VERIFICACAO_PROFISSIONAL;
		valor = valor.replace("{cpf}", profissional.getCpf());
		valor = valor.replace("{dataNascimento}", new SimpleDateFormat("dd/MM/yyyy").format(profissional.getDataNascimento()));
		return valor;
	}

}
