package br.com.nucleo;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.ControleDispensacao.auxiliar.IPadraoConsulta;
import br.com.nucleo.gerenciador.GerenciadorConexao;

public abstract class PadraoConsulta<T> extends GerenciadorConexao implements IPadraoConsulta {

	private T instancia;
	private ConsultaGeral<T> consultaGeral = new ConsultaGeral<T>();
	private HashMap<String, String> camposConsulta =  new HashMap<String, String>();
	private boolean contendoTodosCampos = true;
	private int registrosEncontrados;
	public static final String IGUAL = " = ";
	public static final String DIFERENTE = " != ";
	public static final String MAIOR = " > ";
	public static final String MAIOR_IGUAL = " >= ";
	public static final String MENOR = "<";
	public static final String MENOR_IGUAL = " <= ";
	public static final String CONTENDO = "between valor and valor2";
	public static final String NAO_CONTENDO = "not between valor and valor2";
//	public static final String INCLUINDO_INICIO = "like valor %";
//	public static final String INCLUINDO_FIM = "like % valor ";
	public static final String INCLUINDO_TUDO = " like ";
	private String orderBy;
	private String groupBy;
	
	@SuppressWarnings("unchecked")
	public PadraoConsulta() {
		setConsultaGeral(new ConsultaGeral<T>());
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
	
	/**
	 * método que verifica se existe algum valor na propriedade a ser procurada
	 * @param Object arg0
	 * @return true se o campo não for nulo
	 */
	private boolean campoNaoNulo(Object valor){
		if(valor instanceof Integer || valor instanceof Float || valor instanceof Double || valor instanceof Number || valor instanceof String){
			return valor != null && !valor.toString().equals("0") && !valor.toString().equals("0.0") && !valor.toString().isEmpty();
		}else{
			return valor != null;
		}
	}
	
	/**
	 * método que adiciona o cast para alguns tipos de valor
	 * @param String arg0 Object arg1
	 * @return String com o cast
	 */
	private String addCast(String campo, Object valor){
		if(valor instanceof Float){
			return " cast(:" + campo + " as float)";
		}
		return ":"+campo;
	}
	
	private String quebraCampoContendo(final String campo, final int pos){
		String[] quebra = campo.split(",") ;
		return quebra[pos].trim();
	}
	
	
	public List<T> getList(){
		EntityManager em = getEntityManager();
		List<T> resultadoBuscaList = null;
        try{
        	boolean adicionadoWhere=false;
        	//insere a clausula 'where' caso não exista ainda
        	if(!camposConsulta.isEmpty()){
	        		        	
	    		Set<String> campos = camposConsulta.keySet();
	    		//laço para pecorrer todos itens da pesquisa que o programador informou no consulta.java
	    		for(String campo : campos){
	    			//criando array com todos os itens da pesquisa. ex.: o.usuario.pessoa -> [o] [usuario] [pessoa]
	    			String[] campoConsultaDesmembrado = campo.split("\\.");
	    			
	    			//a variável campoSubS recebe o nome do item do campoConsultaDesmenbrado alterado para o padrão de um método get no camelcase. ex.: getUsuario ou getPessoa 
	    			String campoSubS = "";
	    			
	    			Object obj = instancia;
	    			
	    			//laço que pecorre todos os níveis do hql para para poder pegar o valor que será consultado. ex.: o.usuario.pessoa -> primeiro vai pegar o getUsuario para depois pegar o getPessoa
	    			for(int i = 1 ; i < campoConsultaDesmembrado.length; i++){
	    				String campoConsulta = campoConsultaDesmembrado[i];
    					campoSubS = "get".concat(campoConsulta.substring(0,1).toUpperCase().trim().concat(campoConsulta.substring(1).trim()));
    					if(obj != null){
				            Class cls = Class.forName(obj.getClass().getName());  
				            Method meth = cls.getMethod(campoSubS, null);  
			            	obj = meth.invoke(obj, null);
    					}
	    			}
	    			
    				if(campoNaoNulo(obj)){
    					String operador = camposConsulta.get(campo);
    					consultaGeral.getAddValorConsulta().put(campoSubS, (operador.contains("like") ? "%" + obj.toString().toLowerCase() + "%" : obj));
		    			String campo2 = addCast(campoSubS, obj);
		    			
		    			//se o usuário já adicionou a cláusula 'where' apenas é adicionado o campo de pesquisa e é colocado false para a varíavel 'adicionaWhere' para que insira o operado a partir dos próximos campos
		    			String clausula = " "+ (operador.contains("like") ? "lower(" + campo + ")" : campo) + " "+ operador + campo2;//.replaceAll("valor", campo2);
		    			if(adicionadoWhere){
		    				adicionadoWhere = false;
	    					consultaGeral.getSqlConsultaSB().append(clausula);
		    			}else{
		    				consultaGeral.getSqlConsultaSB().append(" "+ (contendoTodosCampos ? " and " : " or ") + clausula);
		    			}
    				}
	    		}
        	}
        	
        	if(orderBy != null && !orderBy.isEmpty()){
        		consultaGeral.getSqlConsultaSB().append(" order by ");
        		consultaGeral.getSqlConsultaSB().append(orderBy);
        	}
        	
        	if(groupBy != null && !groupBy.isEmpty()){
        		consultaGeral.getSqlConsultaSB().append(" group by ").append(groupBy);
        	}
        	
            resultadoBuscaList = (List<T>) consultaGeral.resultadoBuscaList();
            setRegistrosEncontrados(resultadoBuscaList.size());
        }catch (Exception e) {
        	e.printStackTrace();
            //se der algo de errado vem parar aqui, onde eh cancelado
            em.getTransaction().rollback();
        }finally{
        	em.close();
        }
        return resultadoBuscaList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void novaInstancia(){
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
	
	private String nomeCompletoClasse(){
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
	
	public void setInstancia(T instancia) {
		this.instancia = instancia;
	}

	public T getInstancia() {
		return instancia;
	}


	public void setConsultaGeral(ConsultaGeral<T> consultaGeral) {
		this.consultaGeral = consultaGeral;
	}

	public ConsultaGeral<T> getConsultaGeral() {
		return consultaGeral;
	}

	public void setCamposConsulta(HashMap<String, String> camposConsulta) {
		this.camposConsulta = camposConsulta;
	}

	public HashMap<String, String> getCamposConsulta() {
		return camposConsulta;
	}

	public void setContendoTodosCampos(boolean contendoTodosCampos) {
		this.contendoTodosCampos = contendoTodosCampos;
	}

	public boolean isContendoTodosCampos() {
		return contendoTodosCampos;
	}

	public void setRegistrosEncontrados(int registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}

	public int getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	
}