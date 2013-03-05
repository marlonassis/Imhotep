package br.com.imhotep.temp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage.Severity;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.remendo.PadraoFluxo;

public class PadraoFluxoTemp extends PadraoFluxo {

	private Map<String, Object> objetoSalvar = new HashMap<String, Object>();
	private String mensagemErro;
	private String mensagemSucesso;
	
	public PadraoFluxoTemp() {
		setMensagemErro("Erro ao salvar!");
		setMensagemSucesso("Cadastro realizado com sucesso!");
	}
	
	public PadraoFluxoTemp(String mensagemErro, String mensagemSucesso) {
		setMensagemErro(mensagemErro);
		setMensagemSucesso(mensagemSucesso);
	}
	
	public boolean processarFluxo(){
		boolean status=false;
		String posicao = "Iniciar Transação";
		iniciarTransacao();
		try{
			Set<String> chaves = getObjetoSalvar().keySet();
			for(String chave : chaves){
				posicao = "Save - ".concat(chave);
				session.save(getObjetoSalvar().get(chave));
			}
			posicao = "Flush";
			session.flush();  
			posicao = "Commit";
			tx.commit();
			mensagemInsercao(getMensagemSucesso(), null, Constantes.INFO);
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

	public String getMensagemErro() {
		return mensagemErro;
	}

	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	public String getMensagemSucesso() {
		return mensagemSucesso;
	}

	public void setMensagemSucesso(String mensagemSucesso) {
		this.mensagemSucesso = mensagemSucesso;
	}
	
}
