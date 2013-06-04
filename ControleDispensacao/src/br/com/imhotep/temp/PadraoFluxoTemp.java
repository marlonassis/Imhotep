package br.com.imhotep.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage.Severity;

import br.com.imhotep.auxiliar.Constantes;
import br.com.remendo.PadraoFluxo;

public class PadraoFluxoTemp extends PadraoFluxo {

	private Map<String, Object> objetoSalvar = new HashMap<String, Object>();
	private Map<String, Object> objetoAtualizar = new HashMap<String, Object>();
	
	public PadraoFluxoTemp(){
		super();
		setMensagemErro("Erro ao salvar!");
		setMensagemSucesso("Cadastro realizado com sucesso!");
	}
	
	public PadraoFluxoTemp(String mensagemErro, String mensagemSucesso) {
		super();
		setMensagemErro(mensagemErro);
		setMensagemSucesso(mensagemSucesso);
	}
	
	public PadraoFluxoTemp(boolean exibeMensagemDelecao, boolean exibeMensagemInsercao, boolean exibeMensagemAtualizacao) {
		super();
		setMensagemErro("Erro ao salvar!");
		setMensagemSucesso("Cadastro realizado com sucesso!");
		setExibeMensagemAtualizacao(exibeMensagemAtualizacao);
		setExibeMensagemDelecao(exibeMensagemDelecao);
		setExibeMensagemInsercao(exibeMensagemInsercao);
	}
	
	public PadraoFluxoTemp(String mensagemErro, String mensagemSucesso, boolean exibeMensagemDelecao, boolean exibeMensagemInsercao, boolean exibeMensagemAtualizacao) {
		super();
		setMensagemErro(mensagemErro);
		setMensagemSucesso(mensagemSucesso);
		setExibeMensagemAtualizacao(exibeMensagemAtualizacao);
		setExibeMensagemDelecao(exibeMensagemDelecao);
		setExibeMensagemInsercao(exibeMensagemInsercao);
	}
	
	public boolean processarFluxo() throws ExcecaoPadraoFluxo{
		boolean status=false;
		String posicao = "Iniciar Transação";
		iniciarTransacao();
		try{
			Set<String> chavesSalvar = getObjetoSalvar().keySet();
			for(String chave : chavesSalvar){
				posicao = "Save - ".concat(chave);
				session.save(getObjetoSalvar().get(chave));
			}
			
			Set<String> chavesAtualizar = getObjetoAtualizar().keySet();
			for(String chave : chavesAtualizar){
				posicao = "Merge - ".concat(chave);
				session.merge(getObjetoAtualizar().get(chave));
			}
			
			posicao = "Flush";
			session.flush();  
			posicao = "Commit";
			tx.commit();
			if(isExibeMensagemInsercao()){
				mensagemInsercao(getMensagemSucesso(), null, Constantes.INFO);
			}
			status = true;
			aposEnviar();
		}catch(Exception e){
			mensagem(getMensagemErro(), posicao, Constantes.ERROR);
			System.out.println("Fluxo: Erro ao processar o fluxo: " + posicao);
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally{
			finallyTransacao();
		}
		if(!status)
			throw new ExcecaoPadraoFluxo();
		return status;
	}

	protected void mensagemInsercao(String msg, String msg2, Severity tipoMensagem) {
		if(isExibeMensagemInsercao()){
			super.mensagem(msg, msg2, tipoMensagem);
		}
	}
	
	public Map<String, Object> getObjetoSalvar() {
		return objetoSalvar;
	}
	public void setObjetoSalvar(Map<String, Object> objetoSalvar) {
		this.objetoSalvar = objetoSalvar;
	}

	public Map<String, Object> getObjetoAtualizar() {
		return objetoAtualizar;
	}

	public void setObjetoAtualizar(Map<String, Object> objetoAtualizar) {
		this.objetoAtualizar = objetoAtualizar;
	}

}
