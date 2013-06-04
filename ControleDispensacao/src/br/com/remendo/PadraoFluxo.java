package br.com.remendo;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.remendo.interfaces.IPadraoHome;

public abstract class PadraoFluxo extends PadraoGeral implements IPadraoHome {

	private Object instancia;
	private boolean exibeMensagemAtualizacao = true;
	private boolean exibeMensagemDelecao = true;
	private boolean exibeMensagemInsercao = true;
	private String mensagemErro="Ocorrreu um erro ao cadastrar";
	private String mensagemSucesso="Cadastro realizado com sucesso";
	
	public boolean enviar() {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.save(instancia);  
			session.flush();  
			tx.commit();  
			ret = true;
			if(isExibeMensagemInsercao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, getMensagemSucesso(), null));
			}
			aposEnviar();
		}
		catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMensagemErro(), e.getCause().getMessage()));
		}finally{
			finallyTransacao();
		}
		return ret;
	}

	public void aposEnviar() {
		
	}


	public boolean apagar() {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.delete(instancia); // Realiza persistência
			tx.commit(); // Finaliza transação
			setInstancia(null);
			ret = true;
			if(isExibeMensagemDelecao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Deleção realizada com sucesso", "Registro apagado!"));
			}
		}catch (Exception e) {
			e.printStackTrace();
			if(isExibeMensagemDelecao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao apagar", e.getCause().getMessage()));
			}
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			finallyTransacao();
		}
		return ret;
	}
	
	public boolean atualizar() {
		return atualizarGenerico(instancia) != null;
	}
	
	public Object atualizarGenerico(Object obj) {
		Object o = null;
		try{
			iniciarTransacao();
			session.merge(obj); // Realiza persistência
			tx.commit(); // Finaliza transação
			o = obj;
			if(isExibeMensagemAtualizacao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Atualização realizada com sucesso", "Registro atualizado!"));
			}
		}catch (org.hibernate.exception.ConstraintViolationException e) {
			session.getTransaction().rollback();
			if(isExibeMensagemAtualizacao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro já cadastrado.","Ocorreu uma tentativa de duplicação!"));
			}
		}catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			if(isExibeMensagemAtualizacao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao atualizar", e.getCause().getMessage()));
			}
		}finally{
			finallyTransacao();
		}
		return o;
	}
	
	public Integer executa(String sql) {
		Integer res = null; 
		try{
			iniciarTransacao();
			res = session.createQuery(sql).executeUpdate();
		}catch (Exception e) {
			if(session != null){
				session.getTransaction().rollback();
			}
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao executar o comando", e.getCause().getMessage()));
		}finally{
			finallyTransacao();
		}
		
		return res;
	}
	
	protected void mensagem(String msg, String msg2, Severity tipoMensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(tipoMensagem,msg, msg2));
	}
	
	public void setInstancia(Object instancia) {
		this.instancia = instancia;
	}

	public Object getInstancia() {
		return instancia;
	}
	
	public boolean isExibeMensagemAtualizacao() {
		return exibeMensagemAtualizacao;
	}

	public void setExibeMensagemAtualizacao(boolean exibeMensagemAtualizacao) {
		this.exibeMensagemAtualizacao = exibeMensagemAtualizacao;
	}

	public boolean isExibeMensagemDelecao() {
		return exibeMensagemDelecao;
	}

	public void setExibeMensagemDelecao(boolean exibeMensagemDelecao) {
		this.exibeMensagemDelecao = exibeMensagemDelecao;
	}

	public boolean isExibeMensagemInsercao() {
		return exibeMensagemInsercao;
	}

	public void setExibeMensagemInsercao(boolean exibeMensagemInsercao) {
		this.exibeMensagemInsercao = exibeMensagemInsercao;
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
