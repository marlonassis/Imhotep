package br.com.imhotep.temp;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage.Severity;

import br.com.imhotep.auxiliar.Constantes;
import br.com.remendo.PadraoFluxo;

public class PadraoFluxoTemp extends PadraoFluxo {

	private static Map<String, Object> objetoSalvar = new LinkedHashMap<String, Object>();
	private static Map<String, Object> objetoAtualizar = new LinkedHashMap<String, Object>();
	private static Map<String, Object> objetoDeletar = new LinkedHashMap<String, Object>();
	
	public PadraoFluxoTemp(){
		super();
		setMensagemErro("Erro ao processar o fluxo!");
		setMensagemSucesso("Cadastro realizado com sucesso!");
	}
	
	public PadraoFluxoTemp(String mensagemErro, String mensagemSucesso) {
		super();
		setMensagemErro(mensagemErro);
		setMensagemSucesso(mensagemSucesso);
	}
	
	public PadraoFluxoTemp(boolean exibeMensagemDelecao, boolean exibeMensagemInsercao, boolean exibeMensagemAtualizacao) {
		super();
		setMensagemErro("Erro ao processar o fluxo!");
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
	
	public static boolean finalizarFluxo() throws ExcecaoPadraoFluxo{
		return new PadraoFluxoTemp().processarFluxo();
	}
	
	//ap�s adicionado todos os itens nos dois para serem persistidos, este m�todo varre cada um desses maps para finalizar o processo 
	public boolean processarFluxo() throws ExcecaoPadraoFluxo{
		boolean status=false;
		String posicao = "Iniciar Transa��o";
		iniciarTransacao();
		try{
			Set<String> keys = getObjetoSalvar().keySet();
			for(String chave : keys){
				posicao = "Save - ".concat(chave);
				session.save(getObjetoSalvar().get(chave));
			}
			
			keys = getObjetoAtualizar().keySet();
			for(String chave : keys){
				posicao = "Merge - ".concat(chave);
				session.merge(getObjetoAtualizar().get(chave));
			}
			
			keys = getObjetoDeletar().keySet();
			for(String chave : keys){
				posicao = "Delete - ".concat(chave);
				session.delete(getObjetoDeletar().get(chave));
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
	
	public static void limparFluxo(){
		objetoSalvar = new LinkedHashMap<String, Object>();
		objetoAtualizar = new LinkedHashMap<String, Object>();
		objetoDeletar = new LinkedHashMap<String, Object>();
	}
	
	public static Map<String, Object> getObjetoSalvar() {
		return PadraoFluxoTemp.objetoSalvar;
	}
	public static void setObjetoSalvar(Map<String, Object> objetoSalvar) {
		PadraoFluxoTemp.objetoSalvar = objetoSalvar;
	}

	public static Map<String, Object> getObjetoAtualizar() {
		return PadraoFluxoTemp.objetoAtualizar;
	}

	public void setObjetoAtualizar(Map<String, Object> objetoAtualizar) {
		PadraoFluxoTemp.objetoAtualizar = objetoAtualizar;
	}

	public static Map<String, Object> getObjetoDeletar() {
		return objetoDeletar;
	}

	public static void setObjetoDeletar(Map<String, Object> objetoDeletar) {
		PadraoFluxoTemp.objetoDeletar = objetoDeletar;
	}

}
