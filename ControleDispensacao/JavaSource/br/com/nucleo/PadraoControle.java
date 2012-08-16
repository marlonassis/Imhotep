package br.com.nucleo;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.nucleo.gerenciador.GerenciadorConexao;
import br.com.nucleo.interfaces.IPadraoHome;

public abstract class PadraoControle extends GerenciadorConexao implements IPadraoHome {

	private Object instancia;
	private boolean exibeMensagemAtualizacao = true;
	private boolean exibeMensagemDelecao = true;
	private boolean exibeMensagemInsercao = true;
	
	@Override
	public boolean enviar() {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.save(instancia);  
			session.flush();  
			tx.commit();  
			ret = true;
			if(isExibeMensagemInsercao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cadastro realizado com sucesso", "Registro cadastrado!"));
			}
			aposEnviar();
		}
		catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao cadastrar", e.getCause().getMessage()));
		}finally{
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}

	public void aposEnviar() {
		
	}


	@Override
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
			session.close(); // Fecha sessão
			factory.close();
		}
		return ret;
	}
	
	@Override
	public boolean atualizar() {
		return atualizarGenerico(instancia) != null;
	}
	
	@Override
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
			session.close(); // Fecha sessão
			factory.close();
		}
		return o;
	}
	
	@Override
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
			session.close(); // Fecha sessão
			factory.close();
		}
		
		return res;
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
}
