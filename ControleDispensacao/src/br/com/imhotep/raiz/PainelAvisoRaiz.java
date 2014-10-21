package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.PainelAviso;
import br.com.imhotep.entidade.PainelAvisoEspecialidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class PainelAvisoRaiz extends PadraoRaiz<PainelAviso>{
	
	private Especialidade especialidade;
	
	public void addEspecialidadeProfissional(){
		PainelAvisoEspecialidade pae = new PainelAvisoEspecialidade();
		pae.setEspecialidade(getEspecialidade());
		pae.setPainelAviso(getInstancia());
		if(new PainelAvisoEspecialidadeRaiz(pae).enviar()){
			getInstancia().getEspecialidades().add(pae);
			setEspecialidade(null);
		}
	}
	
	public void remEspecialidadeProfissional(PainelAvisoEspecialidade linha){
		if(new PainelAvisoEspecialidadeRaiz(linha).apagar()){
			getInstancia().getEspecialidades().remove(linha);
			setEspecialidade(null);
		}
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		getInstancia().setDataInsercao(new Date());
		super.preEnvio();
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}
	
}
