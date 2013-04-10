package ehealth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.imhotep.linhaMecanica.LinhaMecanica;
//package br.com.imhotep.testes;


public class test{

	public static boolean existeEstadoLinha(String linha, List<Estado> estados){
    	for(Estado estado : estados){
    		if(linha.toLowerCase().contains(estado.getNome().toLowerCase())){
    			estados.remove(estado);
    			return true;
    		}
    	}
    	return false;
    }
	
    public static void main(String... args) {  
    	try{
	    	String linkDataSus = "http://cnes.datasus.gov.br/";
	    	LinhaMecanica lm = new LinhaMecanica();
			lm.setNomeBanco("db_ehealth");
			ResultSet rs = lm.consultar("select id_estados, cv_nome, cv_link from tb_estados order by cv_nome");
			List<Estado> estados = new ArrayList<Estado>();
			while (rs.next()) {
				estados.add(new Estado(rs.getInt("id_estados"), rs.getString("cv_nome"), rs.getString("cv_link")));
			}
			
			for(Estado estado : estados){
				InputStream siteEstado = new URL(linkDataSus + estado.getLink()).openStream();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(siteEstado, "ISO-8859-1"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				int i = 0;
				while ((line = reader.readLine()) != null) {
					if(line.toLowerCase().contains("nomeestado="+estado.getNome().toLowerCase())){
						sb.append(line).append("\n");
						i++;
					}
				}
				System.out.println(sb.toString());
				System.out.println(i);
				break;
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
     }

}