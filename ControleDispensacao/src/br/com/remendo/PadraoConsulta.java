package br.com.remendo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import br.com.remendo.ConsultaGeral;
import br.com.remendo.gerenciador.GerenciadorConexao;
import br.com.remendo.interfaces.IPadraoConsulta;

public abstract class PadraoConsulta<T> extends GerenciadorConexao implements IPadraoConsulta {

	private T instancia;
	private ConsultaGeral<T> consultaGeral = new ConsultaGeral<T>();
	private HashMap<String, String> camposConsulta =  new HashMap<String, String>();
	private boolean contendoTodosCampos = true;
	private int registrosEncontrados;
	protected static final String IGUAL = " = ";
	protected static final String IGUAL_DATA = " cast(DATA as date) ";
	protected static final String DIFERENTE = " != ";
	protected static final String MAIOR = " > ";
	protected static final String MAIOR_IGUAL = " >= ";
	protected static final String MENOR = "<";
	protected static final String MENOR_IGUAL = " <= ";
	protected static final String CONTENDO = "between valor and valor2";
	protected static final String NAO_CONTENDO = "not between valor and valor2";
	protected static final String INCLUINDO_TUDO = " like ";
	private String orderBy;
	private String groupBy;
	private boolean pesquisaGuiada; 
	private boolean pesquisaCamposDespadronizado;
	private List<T> lista = new ArrayList<T>();
	
	public void carregarResultado(){
		setLista(getList());
	}
	
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
	 * m�todo que verifica se existe algum valor na propriedade a ser procurada
	 * @param Object arg0
	 * @return true se o campo n�o for nulo
	 */
	protected boolean campoNaoNulo(Object valor){
		if(valor instanceof Integer || valor instanceof Float || valor instanceof Double || valor instanceof Number || valor instanceof String){
			return valor != null && !valor.toString().equals("0") && !valor.toString().equals("0.0") && !valor.toString().isEmpty();
		}else{
			return valor != null;
		}
	}
	
	protected boolean isPesquisaValorada(){
		if(!camposConsulta.isEmpty()){
    		Set<String> campos = camposConsulta.keySet();
    		//la�o para pecorrer todos itens da pesquisa que o programador informou no consulta.java
    		for(String campo : campos){
				try {
					Object obj = getValorPropriedadePesquisada(campo);
					if(campoNaoNulo(obj)){
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
		}
		return false;
	}
	
	/**
	 * m�todo que adiciona o cast para alguns tipos de valor
	 * @param String arg0 Object arg1
	 * @return String com o cast
	 */
	private String addCast(String campo, Object valor){
		if(valor instanceof Float){
			return " cast(:" + campo + " as float)";
		}else{
			if(valor instanceof String){
				return " to_ascii(:" + campo + ")";
			}
		}
		return ":"+campo;
	}
	
	private Object getValorPropriedadePesquisada(String campo) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		String campoSubS;
		Object obj = instancia;
		//criando array com todos os itens da pesquisa. ex.: o.usuario.pessoa -> [o] [usuario] [pessoa]
		String[] campoConsultaDesmembrado = campo.split("\\.");
		//a vari�vel campoSubS recebe o nome do item do campoConsultaDesmenbrado alterado para o padr�o de um m�todo get no camelcase. ex.: getUsuario ou getPessoa 
		//la�o que pecorre todos os n�veis do hql para para poder pegar o valor que ser� consultado. ex.: o.usuario.pessoa -> primeiro vai pegar o getUsuario para depois pegar o getPessoa
		for(int i = 1 ; i < campoConsultaDesmembrado.length; i++){
			String campoConsulta = campoConsultaDesmembrado[i];
			campoConsulta = campoConsulta.replace(")", "");
			campoSubS = "get".concat(campoConsulta.substring(0,1).toUpperCase().trim().concat(campoConsulta.substring(1).trim()));
			if(obj != null){
	            Class cls = Class.forName(obj.getClass().getName());  
	            Method meth = cls.getMethod(campoSubS, null);  
            	obj = meth.invoke(obj, null);
			}
		}
		return obj;
	}
	
	public boolean isTodosCamposVazios(){
		if(!camposConsulta.isEmpty()){
			try {
	    		Set<String> campos = camposConsulta.keySet();
	    		for(String campo : campos){
	    			Object obj = getValorPropriedadePesquisada(campo);
					if(campoNaoNulo(obj)){
						return false;
					}
	    		}
	    		return true;
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public List<T> getList(){
		List<T> resultadoBuscaList = null;
        try{
        	boolean achouCampoPreenchidoPeloUsuario = false;
        	boolean adicionadoWhere=false;
        	//insere a clausula 'where' caso n�o exista ainda
        	if(!camposConsulta.isEmpty()){
	    		Set<String> campos = camposConsulta.keySet();
	    		//la�o para pecorrer todos itens da pesquisa que o programador informou no consulta.java
	    		for(String campo : campos){
	    			Object obj = getValorPropriedadePesquisada(campo);
    				if(campoNaoNulo(obj)){
    					achouCampoPreenchidoPeloUsuario = true;
    					String operador = camposConsulta.get(campo);
    					String parametro = "param_" + String.valueOf(obj.hashCode()).replaceAll("-", "_");
						consultaGeral.getAddValorConsulta().put(parametro, (operador.contains("like") ? "%" + obj.toString().toLowerCase() + "%" : obj));
		    			String campo2 = addCast(parametro, obj);
		    			
		    			//se o usu�rio j� adicionou a cl�usula 'where' apenas � adicionado o campo de pesquisa e � colocado false para a var�avel 'adicionaWhere' para que insira o operado a partir dos pr�ximos campos
		    			String clausula = " "+ (operador.contains(INCLUINDO_TUDO.trim()) ? "lower(to_ascii(" + campo + "))" : 
		    									(operador.contains(IGUAL_DATA.trim()) ? IGUAL_DATA.replaceAll("DATA", campo) : campo)) + " "+ (operador.equals(IGUAL_DATA) ? " = " : operador) + campo2;//.replaceAll("valor", campo2);
		    			if(adicionadoWhere){
		    				adicionadoWhere = false;
	    					consultaGeral.getSqlConsultaSB().append(clausula);
		    			}else{
		    				consultaGeral.getSqlConsultaSB().append(" " + (contendoTodosCampos ? " and " : " or ") + clausula);
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
        	
        	
        	if(achouCampoPreenchidoPeloUsuario || isPesquisaCamposDespadronizado()){
        		resultadoBuscaList = (List<T>) consultaGeral.resultadoBuscaList();
        	}else{
        		//verifica se a pesquisa deve ser guiada pelo usuario para os casos em que n�o existe algum campo de pesquisa
        		//essa restri��o foi criada para evitar que entidades com muitos itens estourem o heap do java
        		if(isPesquisaGuiada()){
        			return null;
        		}else{
        			resultadoBuscaList = (List<T>) consultaGeral.resultadoBuscaList();
        		}
        	}
            setRegistrosEncontrados(resultadoBuscaList.size());
        }catch (Exception e) {
        	e.printStackTrace();
            //se der algo de errado vem parar aqui, onde eh cancelado
        }
        return resultadoBuscaList;
	}
	
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

	public boolean isPesquisaGuiada() {
		return pesquisaGuiada;
	}

	public void setPesquisaGuiada(boolean pesquisaGuiada) {
		this.pesquisaGuiada = pesquisaGuiada;
	}

	public List<T> getLista() {
		return lista;
	}

	public void setLista(List<T> lista) {
		this.lista = lista;
	}

	public boolean isPesquisaCamposDespadronizado() {
		return pesquisaCamposDespadronizado;
	}

	public void setPesquisaCamposDespadronizado(boolean pesquisaCamposDespadronizado) {
		this.pesquisaCamposDespadronizado = pesquisaCamposDespadronizado;
	}

}
