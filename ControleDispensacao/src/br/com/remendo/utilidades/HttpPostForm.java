package br.com.remendo.utilidades;

import java.util.List;

public class HttpPostForm
	{
		
		static String site1 = "http://pergamum.bibliotecas.ufs.br/pergamum/biblioteca_s/php/login_usu.php?flag=index.php";
		static String usuario = "201010007070";
		static String senha = "123789";

		public static void main(String[] args) {
			System.out.println("inicio");
			List<String[]> itens = new HttpPostForm().getItens();
			System.out.println("meio");
			for(String[] item : itens){
				System.out.println(item[0] + " - " + item[1]);
			}
			System.out.println("fim");
		}
		
		public List<String[]> getItens(){
//			try {
//				HttpUnitOptions.setScriptingEnabled(false);
//				WebConversation wc = new WebConversation();
//				WebResponse resp;
//				resp = wc.getResponse("http://pergamum.bibliotecas.ufs.br/pergamum/biblioteca_s/php/login_usu.php?flag=index.php" );
//				WebForm form = resp.getForms()[0];
//				form.setParameter("login", usuario);
//				form.setParameter("password", senha);
//				WebResponse jdoc = form.submit(); 
//				WebTable[] tables = jdoc.getTables();
//				
//				String[] split = tables[0].asText()[0][1].split("\n");
//				List<String[]> res = new ArrayList<String[]>();
//				int i = 45;
//				try {
//					while(true){
//						if(i+3 > split.length){
//							break;
//						}
//						String livro = removeEspacos(split[i]);
//						String data = split[i+3].replaceAll(" ", "");
//						if(!data.isEmpty()){
//							data = data.substring(1, split[i+3].replaceAll(" ", "").length());
//						}
//						new SimpleDateFormat("dd/MM/yyyy").parse(data);
//						String[] ress = {livro, data};
//						res.add(ress);
//						i += 13;
//					}
//				} catch (ParseException e) {
//				}
//				
//				return res;
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (SAXException e) {
//				e.printStackTrace();
//			}
			return null;
		}

		private String removeEspacos(String nome){
			char[] nomeSplit = nome.toCharArray();
			String nomeLivro = "";
			boolean achouNomeLivro = false;
			int cont = 0;
			for(int i = 0; i < nomeSplit.length; i++){
				//conta os espa√ßos em branco
				if(nomeSplit[i] == 32){
					cont++;
				}
				//caso n√£o haja mais espa√ßos em branco, estamos no nome do livro
				if(nomeSplit[i] != 32){
					achouNomeLivro = true;
					cont = 0;
				}
				//se j√° foi encontrado o nome do livro e houve dois espa√ßos consecutivos, ent√£o chegamos ao fim do nome do livro
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