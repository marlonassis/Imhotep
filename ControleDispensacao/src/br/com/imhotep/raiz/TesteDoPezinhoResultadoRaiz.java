package br.com.imhotep.raiz;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TesteDoPezinho;
import br.com.imhotep.entidade.TesteDoPezinhoResultado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="testeDoPezinhoResultadoRaiz")
@SessionScoped
public class TesteDoPezinhoResultadoRaiz extends PadraoRaiz<TesteDoPezinhoResultado>{
	private TesteDoPezinho testeDoPezinho;
	
	@Override
	public boolean atualizar() {
		TesteDoPezinhoRaiz tph = new TesteDoPezinhoRaiz();
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
		TesteDoPezinhoResultadoRaiz tprh = new TesteDoPezinhoResultadoRaiz();
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
