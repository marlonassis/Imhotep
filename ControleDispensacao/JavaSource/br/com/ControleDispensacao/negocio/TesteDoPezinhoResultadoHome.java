package br.com.ControleDispensacao.negocio;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TesteDoPezinho;
import br.com.ControleDispensacao.entidade.TesteDoPezinhoResultado;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="testeDoPezinhoResultadoHome")
@SessionScoped
public class TesteDoPezinhoResultadoHome extends PadraoHome<TesteDoPezinhoResultado>{
	private TesteDoPezinho testeDoPezinho;
	
	@Override
	public boolean atualizar() {
		TesteDoPezinhoHome tph = new TesteDoPezinhoHome();
		tph.setInstancia(getTesteDoPezinho());
		return tph.atualizar();
	}
	
	public void addResultado() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		getInstancia().setTesteDoPezinho(getTesteDoPezinho());
		getInstancia().setProfissionalCadastro(Autenticador.getInstancia().getProfissionalAtual());
		getInstancia().setDataCadastro(new Date());
		super.enviar();
		super.novaInstancia();
	}
	
	public boolean apagarExame() {
		TesteDoPezinhoResultadoHome tprh = new TesteDoPezinhoResultadoHome();
		tprh.setInstancia(getInstancia());
		return tprh.apagar();
	}
	
	@Override
	public void novaInstancia() {
		testeDoPezinho = null;
		super.novaInstancia();
	}
	
	public List<TesteDoPezinhoResultado> getListaResultadoPezinho(){
		if(getTesteDoPezinho() != null){
			return super.getBusca("select o from TesteDoPezinhoResultado o where o.testeDoPezinho.idTesteDoPezinho = "+getTesteDoPezinho().getIdTesteDoPezinho());
		}
		return null;
	}

	public TesteDoPezinho getTesteDoPezinho() {
		return testeDoPezinho;
	}

	public void setTesteDoPezinho(TesteDoPezinho testeDoPezinho) {
		this.testeDoPezinho = testeDoPezinho;
	}
}
