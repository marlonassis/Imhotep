package br.com.ControleDispensacao.auxiliar;

import java.io.Serializable;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.ControleDispensacao.entidade.Especialidade;
import br.com.ControleDispensacao.entidade.Profissional;
import br.com.ControleDispensacao.entidade.TipoMovimento;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;

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

	public static TipoMovimento tipoMovimentoDispensacao(){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("dsTipoMovimento", "Dispensação".toLowerCase());
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(o.descricao) = :dsTipoMovimento");
		return cg.consultaUnica(sb, hashMap);
	}
	
	public static TipoMovimento tipoMovimentoEntrada(){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("dsTipoMovimento", "Entrada".toLowerCase());
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(o.descricao) = :dsTipoMovimento");
		return cg.consultaUnica(sb, hashMap);
	}
}
