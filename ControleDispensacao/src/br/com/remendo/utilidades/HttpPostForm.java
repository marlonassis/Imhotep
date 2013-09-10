package br.com.remendo.utilidades;


public class HttpPostForm
{
	
	static String site1 = "http://pergamum.bibliotecas.ufs.br/pergamum/biblioteca_s/php/login_usu.php?flag=index.php";
	static String usuario = "201010007070";
	static String senha = "123789";

	public static void main(String[] args) {
//			HttpUnitOptions.setScriptingEnabled(false);
//			WebConversation wc = new WebConversation();
//			WebResponse resp;
//			resp = wc.getResponse("http://pergamum.bibliotecas.ufs.br/pergamum/biblioteca_s/php/login_usu.php?flag=index.php" );
//			WebForm form = resp.getForms()[0];
//			form.setParameter("login", usuario);
//			form.setParameter("password", senha);
//			WebResponse jdoc = form.submit(); 
//			WebTable[] tables = jdoc.getTables();
//			
//			String[] split = tables[0].asText()[0][1].split("\n");
//			List<String[]> res = new ArrayList<String[]>();
//			int i = 45;
//			try {
//				while(true){
//					if(i+3 > split.length){
//						break;
//					}
//					String livro = removeEspacos(split[i]);
//					String data = split[i+3].replaceAll(" ", "");
//					if(!data.isEmpty()){
//						data = data.substring(1, split[i+3].replaceAll(" ", "").length());
//					}
//					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//					df.parse(data);
//					String[] ress = {livro, data};
//					res.add(ress);
//					i += 13;
//				}
//			} catch (ParseException e) {
//			}
//			
//			System.out.println("Resultados:");
//			
//			for(String[] item : res){
//				System.out.println("Livro: " + item[0] + " - Data: " + item[1]);
//			}
			
		
	}

	private static String removeEspacos(String nome){
		char[] nomeSplit = nome.toCharArray();
		String nomeLivro = "";
		boolean achouNomeLivro = false;
		int cont = 0;
		for(int i = 0; i < nomeSplit.length; i++){
			//conta os espaços em branco
			if(nomeSplit[i] == 32){
				cont++;
			}
			//caso não haja mais espaços em branco, estamos no nome do livro
			if(nomeSplit[i] != 32){
				achouNomeLivro = true;
				cont = 0;
			}
			//se já foi encontrado o nome do livro e houve dois espaços consecutivos, então chegamos ao fim do nome do livro
			if(achouNomeLivro && cont > 1){
				break;
			}
			if(achouNomeLivro){
				nomeLivro = nomeLivro.concat(String.valueOf(nomeSplit[i]));
			}
		}
		return nomeLivro;
	}
	
}