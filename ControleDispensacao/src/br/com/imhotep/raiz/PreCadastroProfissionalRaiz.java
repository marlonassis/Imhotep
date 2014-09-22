package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.RestringirAcessoRedeHU;
import br.com.imhotep.entidade.PreCadastroProfissional;
import br.com.imhotep.excecoes.ExcecaoForaRedeHU;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PreCadastroProfissionalRaiz extends PadraoRaiz<PreCadastroProfissional>{
	
	@Override
	protected void preEnvio() {
		getInstancia().setDataInsercao(new Date());
		super.preEnvio();
	}
	
	@Override
	public boolean enviar() {
		try {
			new RestringirAcessoRedeHU().validarAcessoRedeHU();
			if(super.enviar()){
				super.novaInstancia();
				return true;
			}
		} catch (ExcecaoForaRedeHU e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void efetivarCadastro(){
		getInstancia().setDataEfetivacao(new Date());
		getInstancia().setCadastroEfetivado(true);
		super.atualizar();
		super.novaInstancia();
	}
	
}
