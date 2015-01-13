package br.com.remendo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;

import br.com.remendo.gerenciador.GerenciadorConexao;
import br.com.remendo.interfaces.IPadraoHome;

public abstract class PadraoRaiz<T> extends GerenciadorConexao implements IPadraoHome {

	private T instancia;
	private T instanciaDelecao;
	private boolean exibirDialogPesquisa;
	private boolean exibeMensagemDelecao = true;
	private boolean exibeMensagemInsercao = true;
	private boolean exibeMensagemAtualizacao = true;

	
	public void ativarMensagensCrud(){
		setExibeMensagemAtualizacao(true);
		setExibeMensagemDelecao(true);
		setExibeMensagemInsercao(true);
	}
	
	public void desativarMensagensCrud(){
		setExibeMensagemAtualizacao(false);
		setExibeMensagemDelecao(false);
		setExibeMensagemInsercao(false);
	}
	
	public void exibirDialogPesquisa(){
		setExibirDialogPesquisa(true);
	}
	
	public void ocultarDialogPesquisa(){
		setExibirDialogPesquisa(false);
	}
	
	public void setCarregarInstancia(T instancia){
		setInstancia(instancia);
		ocultarDialogPesquisa();
	}
	
	public void carregarInstancia(T instancia){
		setInstancia(instancia);
		ocultarDialogPesquisa();
	}
	
	protected String getIdSessao(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getId();
	}
	
	public void setId(Object o){
		try{
			iniciarTransacao();
			session.get(instancia.getClass(), (Serializable) o);
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao setar o id", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			finallyTransacao();
		}
	}
	
	@SuppressWarnings("unchecked")
	public PadraoRaiz() {
		try {
			instancia = (T) Class.forName(nomeCompletoClasse()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public void novaInstancia(){
			instancia = retornarNovaInstancia();
	}

	private T retornarNovaInstancia() {
		try {
			return (T) Class.forName(nomeCompletoClasse()).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
    
	protected void preEnvio(){
		
	}
	
	public boolean enviar() {
		preEnvio();
		boolean ret = enviarGenerico(instancia);
		aposEnviar();
		if(ret){
			return true;
		}else{
			zeraId();
			return false;
		}
	}

	public boolean enviarGenerico(Object o) {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.save(o);  
			session.flush();
			tx.commit();  
			ret = true;
			if(isExibeMensagemInsercao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Cadastro realizado com sucesso", "Registro cadastrado!"));
			}
		}
		catch (org.hibernate.exception.ConstraintViolationException e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			if(isExibeMensagemInsercao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro já cadastrado.","Operação não permitida!!"));
			}
		}
		catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao cadastrar", e.getCause().getMessage()));
		}finally{
			finallyTransacao();
		}
		return ret;
	}

	protected void aposEnviar() {
		
	}


	/**
	 * M�todo que seta 0 ao ID da entidade para evitar que o registro entre em edi��o ao tentar gravar o registro sem sucesso
	 */
	private void zeraId(){
		try {
			Class partypes[] = new Class[1];  
            partypes[0] = Integer.TYPE;  
  
            Class cls = Class.forName(nomeCompletoClasse());  
            Method meth = cls.getMethod("setId"+nomeClasse(), partypes);  
  
            Object arglist[] = new Object[1];  
            arglist[0] = new Integer(0);  
  
            meth.invoke(instancia, arglist);  
            
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public boolean apagarInstancia() {
		if(apagarGenerico(getInstanciaDelecao())){
			setInstanciaDelecao(retornarNovaInstancia());
			return true;
		}
		return false;
	}
	
	public boolean apagar() {
		if(apagarGenerico(getInstancia())){
			novaInstancia();
			return true;
		}
		return false;
	}
	
	public boolean apagarGenerico(Object instancia) {
		boolean ret = false;
		try{
			iniciarTransacao();
			session.delete(instancia); // Realiza persist�ncia
			tx.commit(); // Finaliza transa��o
			ret = true;
			if(isExibeMensagemDelecao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Dele��o realizada com sucesso", "Registro apagado!"));
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
			session.merge(obj); // Realiza persist�ncia
			tx.commit(); // Finaliza transa��o
			o = obj;
			if(isExibeMensagemAtualizacao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Atualiza��o realizada com sucesso", "Registro atualizado!"));
			}
		}catch (org.hibernate.exception.ConstraintViolationException e) {
			session.getTransaction().rollback();
			if(isExibeMensagemAtualizacao()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Registro j� cadastrado.","Opera��o n�o permitida!"));
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
		
	/**
	 * M�todo que retorna todos os registros do tipo gen�rico a partir de uma consulta guiada
	 * @param String arg0
	 * @return Cole��o de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBusca(String sql) {
		List<T> lista = null;
		try{
			iniciarTransacao();
			Query query = session.createQuery(sql);
            lista = query.list();    
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu ao reallizar a busca", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			finallyTransacao();
		}
        return lista;
	}
	
	public Integer executa(String sql) {
		Integer res = null; 
		try{
			iniciarTransacao();
			res = session.createQuery(sql).executeUpdate();
			tx.commit();
//			novaInstancia();
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
	
	private  String nomeCompletoClasse(){
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();  
        String s = ((ParameterizedType) superclass).getActualTypeArguments()[0].toString();
        int fim = s.length();  
        return s.substring(6,fim);
	}
	
	private String nomeClasse(){
		String s = nomeCompletoClasse();
        int inicio = s.lastIndexOf(".") + 1;  
        int fim = s.length();  
        return s.substring(inicio,fim);
	}
	
	/**
	 * M�todo que retorna todos os registros do tipo gen�rico
	 * @param String arg0
	 * @return Cole��o de objetos
	 */
	@SuppressWarnings("unchecked")
	public List<T> getBusca(){
		List<T> lista = null;
		try{
			iniciarTransacao();
			Query query = session.createQuery("from "+nomeClasse()+" u");
			lista = query.list(); 
		}catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorrreu um erro ao efetuar a busca", e.getCause().getMessage()));
			if(session != null){
				session.getTransaction().rollback();
			}
		}finally{
			finallyTransacao();
		}
		return lista;
	}

	public boolean isEdicaoGenerico(Object obj){
		Object retId = null;
		try{
	        Method meth = Class.forName(obj.getClass().getName()).getMethod("getId".concat(obj.getClass().getSimpleName()));
	        retId = meth.invoke(obj);
		}
        catch (Throwable e) {
           e.printStackTrace();
        }
		
		if( ((Integer) retId) != null && ((Integer) retId).intValue() != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean isEdicao(){
		Object retId = null;
		try{
			if(instancia != null){
		        Method meth = Class.forName(instancia.getClass().getName()).getMethod("getId"+nomeClasse());
		        retId = meth.invoke(instancia);
			}
		}
        catch (Throwable e) {
           e.printStackTrace();
        }
		
		if( ((Integer) retId) != null && ((Integer) retId).intValue() != 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void setInstancia(T instancia) {
		this.instancia = instancia;
	}

	public T getInstancia() {
		return instancia;
	}
	
	public void setInstanciaDelecao(T instanciaDelecao) {
		this.instanciaDelecao = instanciaDelecao;
	}

	public T getInstanciaDelecao() {
		return instanciaDelecao;
	}
	
	/**
	 * M�todo que retorna a sess�o da classe desejada
	 * @param Nome da classe arg0
	 * @return 
	 * @return classe do tipo escolhido
	 */
	@SuppressWarnings({ "unchecked", "hiding" })
	public <T> T instanciaAtual(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return (T) session.getAttribute(nomeClasse());
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

	public boolean isExibirDialogPesquisa() {
		return exibirDialogPesquisa;
	}

	public void setExibirDialogPesquisa(boolean exibirDialogPesquisa) {
		this.exibirDialogPesquisa = exibirDialogPesquisa;
	}
}
