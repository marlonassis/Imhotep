package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Leito;
import br.com.imhotep.entidade.LeitoLog;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.enums.TipoStatusLeitoEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class LeitoRaiz extends PadraoRaiz<Leito>{
	
	private Unidade unidadeAntiga;
	
	@Override
	public void novaInstancia() {
		setUnidadeAntiga(null);
		super.novaInstancia();
	}
	
	@Override
	public void setInstancia(Leito instancia) {
		setUnidadeAntiga(instancia.getUnidade());
		super.setInstancia(instancia);
	}
	
	@Override
	protected void preEnvio() {
		try {
			setUnidadeAntiga(getInstancia().getUnidade());
			getInstancia().setProfissionalCadastro(Autenticador.getProfissionalLogado());
			getInstancia().setDataCadastro(new Date());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		gerarLog(getInstancia().getStatusLeito());
		super.aposEnviar();
	}
	
	private void gerarLog(TipoStatusLeitoEnum tipoStatusLeitoEnum) {
		LeitoLog log = new LeitoLog();
		log.setLeito(getInstancia());
		log.setTipoStatus(tipoStatusLeitoEnum);
		log.setUnidadeAntiga(getUnidadeAntiga());
		log.setUnidadeAtual(getInstancia().getUnidade());
		log.setUnidadeEmprestimo(getInstancia().getUnidadeEmprestimo());
		new LeitoLogRaiz().gerarLog(log);
	}

	@Override
	public boolean atualizar() {
		if(super.atualizar()){
			gerarLog(getInstancia().getStatusLeito());
			return true;
		}
		return false;
	}
	
	@Override
	public boolean apagar() {
		getInstancia().setStatusLeito(TipoStatusLeitoEnum.E);
		if(super.atualizar()){
			gerarLog(TipoStatusLeitoEnum.E);
			novaInstancia();
			return true;
		}
		return false;
	}
	
	public Unidade getUnidadeAntiga() {
		return unidadeAntiga;
	}

	public void setUnidadeAntiga(Unidade unidadeAntiga) {
		this.unidadeAntiga = unidadeAntiga;
	}
	
}
