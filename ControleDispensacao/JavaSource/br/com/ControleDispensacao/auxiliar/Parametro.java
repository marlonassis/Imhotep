package br.com.ControleDispensacao.auxiliar;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.seguranca.Autenticador;

@ManagedBean(name="parametro")
@ViewScoped
public class Parametro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
		return getDescricaoEspecialidade().equalsIgnoreCase("Teste");
	}
	
	public static boolean isUsuarioFarmaceutico(){
		return getDescricaoEspecialidade().equalsIgnoreCase("Farmac");
	}
	
	public boolean getUsuarioFarmaceutico(){
		return isUsuarioFarmaceutico();
	}
	
	public static boolean isUsuarioAdministrador(){
		return getDescricaoEspecialidade().equalsIgnoreCase("Administrador");
	}
	
	public boolean getUsuarioAdministrador(){
		return isUsuarioAdministrador();
	}
	
	public static boolean isUsuarioMedico(){
		return getDescricaoEspecialidade().equalsIgnoreCase("Médico");
	}
	
	public boolean getUsuarioMedico(){
		return isUsuarioMedico();
	}

}
