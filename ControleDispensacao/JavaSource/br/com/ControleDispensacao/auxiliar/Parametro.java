package br.com.ControleDispensacao.auxiliar;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.seguranca.Autenticador;

public class Parametro {
	
	private static Especialidade getEspecialidade(){
		Profissional profissionaAtual = Autenticador.getInstancia().getProfissionalAtual();
		if(profissionaAtual != null){
			return profissionaAtual.getEspecialidade();
		}else{
			return null;
		}
	}
	
	private static String getDescricaoEspecialidade(){
		Especialidade especialidade = getEspecialidade();
		if(especialidade == null){
			return "";
		}else{
			return especialidade.getDescricao();
		}
	}
	
	public static boolean isUsuarioTeste(){
		return getDescricaoEspecialidade().equals("Teste");
	}
}
