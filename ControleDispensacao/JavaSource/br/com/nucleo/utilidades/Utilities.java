package br.com.nucleo.utilidades;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import sun.misc.BASE64Encoder;



/**
 * @author marlonassis
 *
 */
@ManagedBean(name="util")
@RequestScoped
public class Utilities{
	
	
	public int converteString(String valor){
		return  valor.isEmpty() ? 0 : Integer.parseInt(valor);
	}
	
	public String getDataAtual(){
		return new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
	}
	
	public String getHoraAtual(){
		return new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	}
//    private HtmlToolBar toolBar;
//    private HtmlToolBarGroup group;
//    private HtmlCommandLink link;
//    @In(create = true, required=false)  
//    protected static EntityManager em;
//    
//    @SuppressWarnings("unchecked")
//    public static List<Object> consulta(StringBuilder stringB, HashMap<Object, Object> hashMap ){
//    	StringBuilder hql = new StringBuilder(stringB);
//    	em = (EntityManager) instancia("entityManager");
//    	Query query = em.createQuery(hql.toString());
//    	Set<Object> set = hashMap.keySet();
//    	for(Object obj : set)
//    		query.setParameter((String) obj, hashMap.get(obj));
//		List<Object> objects = (List<Object>) query.getResultList();
//    	return objects;
//    }
//    
//	public static String semanaNome(Integer mesNum){
//		String[] semana = new String[]{"Domingo","Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"};
//		return semana[mesNum];
//	}
//	

	
	/**
	 * Método que retorna o nome do Mês de acordo com o valor repassado por argumento
	 * @param int arg0
	 * @return nome do mês
	 */
	public static String mes(int arg0){
		String[] meses = DateFormatSymbols.getInstance().getMonths();
		return meses[arg0];
	}
	
	public static int qtdDiaMes(String mes, Integer ano){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, posMes(mes));
		c.set(Calendar.YEAR, ano);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Método que retorna a posição do Mês de acordo com o valor repassado por argumento
	 * @param int arg0
	 * @return número do mês
	 */
	public static Integer posMes(String arg0){
		String[] meses = DateFormatSymbols.getInstance().getMonths();
		int pos = 0;
		for(String mes : meses){
			if(arg0.equals(mes)){
				return pos;
			}
			pos++;
		}
		return null;
	}
	
//	public static Integer mesNum(String mes){
//		if(mes.equals("Janeiro"))
//			return 1;
//		if(mes.equals("Fevereiro"))
//			return 2;
//		if(mes.equals("Março"))
//			return 3;
//		if(mes.equals("Abril"))
//			return 4;
//		if(mes.equals("Maio"))
//			return 5;
//		if(mes.equals("Junho"))
//			return 6;
//		if(mes.equals("Julho"))
//			return 7;
//		if(mes.equals("Agosto"))
//			return 8;
//		if(mes.equals("Setembro"))
//			return 9;
//		if(mes.equals("Outubro"))
//			return 10;
//		if(mes.equals("Novembro"))
//			return 11;
//		if(mes.equals("Dezembro"))
//			return 12;
//		return 0;
//	}    
//    
//	/**
//	 *
//	 *retorna uma lista com os anos para uma combo que permitir ao usuário selecionar um ano para consulta 
//	 * @return List<Integer>
//	 */
//   public List<Integer> listaAno(){
//    	List<Integer> list = new ArrayList<Integer>();
//    	list.add(2000);
//    	list.add(2001);
//    	list.add(2002);
//    	list.add(2003);
//    	list.add(2004);
//    	list.add(2005);
//    	list.add(2006);
//    	list.add(2007);
//    	list.add(2008);
//    	list.add(2009);
//    	list.add(2010);
//    	list.add(2011);
//    	list.add(2012);
//    	list.add(2013);
//    	list.add(2014);
//    	return list;
//    }
//	
//    /** Retorna os nomes dos meses do ano Gregoriano
//     * @return List<String>
//     */
//    public List<String> listaMeses(){
//    	List<String> list = new ArrayList<String>();
//    	for(MesesAnoEnum a : MesesAnoEnum.values())
//    		list.add(new String(a.getLabel()));
//    	return list;
//    }
//    
//	public void setToolBar(HtmlToolBar toolBar) {
//		this.toolBar = toolBar;
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	 private HtmlToolBar htmlToolBar;
//	 private int countId = 1;
//	   
//	 @SuppressWarnings("unchecked")
//	 public HtmlToolBar getHtmlToolBar(){
//	   try{
////	    ArrayList<Menu> itens = (ArrayList<Menu>) getMenuDAO().listar(Menu.class); //UTILIZAR SEU M�TODO DE LISTAGEM.
//	 
//	    FacesContext context = FacesContext.getCurrentInstance(); 
//	    htmlToolBar = (HtmlToolBar) context.getApplication().createComponent(HtmlToolBar.COMPONENT_TYPE);
//	    htmlToolBar.setId("myToolBar" + countId++);
//	 
//	    //BOT�O SAIR
//	    HtmlToolBarGroup htmlToolBarGroup = (HtmlToolBarGroup) context.getApplication().createComponent(HtmlToolBarGroup.COMPONENT_TYPE);
//	    htmlToolBarGroup.setLocation("right");
//	    htmlToolBarGroup.setId("myToolBarGroup" + countId++);
//	     
//	    HtmlMenuItem menu = (HtmlMenuItem) context.getApplication().createComponent(HtmlMenuItem.COMPONENT_TYPE);  
//	    menu.setValue("Sair");
//	    menu.setSubmitMode("ajax");
//	    MethodExpression action = context.getApplication().getExpressionFactory().createMethodExpression(context.getELContext(), "#{menuControle.logout}", String.class, new Class[]{});
//	    menu.setActionExpression(action);
//	     
//	    htmlToolBarGroup.getChildren().add(menu);
//	    htmlToolBar.getChildren().add(htmlToolBarGroup);
//	     
//	     return htmlToolBar;
//	     
////	    Map<Long, Map<Long, Menu>> menuTotal = new HashMap<Long, Map<Long, Menu>>();
////	       
////	    for (Menu item : itens) {  
////	     Long idPai = item.getMenuSuperior() == null ? 0L : item.getMenuSuperior().getId();  
////	      
////	     if (!menuTotal.containsKey(idPai)){
////	      menuTotal.put(idPai, new HashMap<Long, Menu>());
////	     }
////	     menuTotal.get(idPai).put(item.getId(), item);  
////	    }
////	     
////	    montaMenu(context, htmlToolBar, menuTotal, 0L);
////	         
//	   }catch (Exception e) {
//	    e.printStackTrace();
//	   }
//	return htmlToolBar;
//	 
////	  return htmlToolBar;
//	 }
	  
	 
	 
	 
	 
	 
	public static Object instancia(String entidade){
//		Object obj = (Object) Component.getInstance(entidade);
		return null;
	}
//	
//	public List<SelectItem> listaBoolean(String um, String dois) {
//		List<SelectItem> itens = new ArrayList<SelectItem>();
//		itens.add(new SelectItem(Boolean.TRUE, um));
//		itens.add(new SelectItem(Boolean.FALSE, dois));
//		return itens;
//	}
//
//	/**
//	 * Pega a data em string e retora um date no formato dd/MM/yyyy 
//	 * @param String arg0
//	 * @return Date
//	 * @throws ParseException
//	 */
//	public static Date dataFormatadaString(String data) throws ParseException {
//		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		Date iniDate = format.parse(data);
//		return iniDate;
//	}
//	
//	/**
//	 * Retorna a data formatada no padrão yyy-MM-dd
//	 * @param Date arg0
//	 * @return String
//	 */
//	public static String dataFormatada(Date data) {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		return format.format(data);
//	}
//	
	/**
	 * encripta qualquer string em MD5 
	 * @param senha
	 * @return String
	 */
	public static String encripta (String senha) {     
		try {
            MessageDigest digest = MessageDigest.getInstance("MD5");      
            digest.update(senha.getBytes());      
            BASE64Encoder encoder = new BASE64Encoder ();      
            return encoder.encode (digest.digest ());      
       } catch (NoSuchAlgorithmException ns) {     
            ns.printStackTrace ();      
            return senha;      
       }      
	}
	
	public static String md5(String input) {  
	    String result = input;  
	    if(input != null) {  
	        MessageDigest md;
			try {
				md = MessageDigest.getInstance("MD5");
				md.update(input.getBytes());  
				BigInteger hash = new BigInteger(1, md.digest());  
				result = hash.toString(16);  
				while(result.length() < 32) {  
					result = "0" + result;  
				}  
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //or "SHA-1"  
	    }  
	    return result;  
	} 
//	
//	/**
//	 * seta zero no id do objeto
//	 * @param Objeto genérico
//	 * @return Object
//	 */
//	public static Object cloneObj(Object obj){
//		try {
//			String nomeCompleto = obj.getClass().getName();
//			String nomeMetodo = "setId".concat(String.copyValueOf(nomeCompleto.toCharArray(), nomeCompleto.indexOf("entity") + 7, nomeCompleto.length() - nomeCompleto.indexOf("entity") - 7));
//			Statement stmt = new Statement(obj, nomeMetodo, new Object[] {new Integer(0)});  
//			stmt.execute();  
//			
////			obj.getClass().getDeclaredMethod(nomeMetodo).invoke(obj,  0);
//			
//		}catch (Throwable e) {
//			e.printStackTrace();
//		}
//		
//		return obj;
//	}
}
