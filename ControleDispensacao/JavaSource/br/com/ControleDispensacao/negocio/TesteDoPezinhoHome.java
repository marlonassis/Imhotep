package br.com.ControleDispensacao.negocio;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TesteDoPezinho;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="testeDoPezinhoHome")
@SessionScoped
public class TesteDoPezinhoHome extends PadraoHome<TesteDoPezinho>{
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setDataCadastro(new Date());
			getInstancia().setProfissionalCadastrante(Autenticador.getInstancia().getProfissionalAtual());
			return super.enviar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
