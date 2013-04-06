package br.com.imhotep.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Parametro;

public class JobBackUp implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		realizaBackup();
	}
	
	private static void realizaBackup(){
		String backupAtivo = Parametro.getBackupAtivo();
		if(backupAtivo.equals("S")){
		    try{
		    	String diretorioBackup = Parametro.getDiretorioBackupImhotep();
		    	String diretorioPostgres = Parametro.getDiretorioPostgres();
		    	String banco = Constantes.NOME_BANCO;
		    	diretorioBackup += new SimpleDateFormat("dd-MM-yyyy").format(new Date());
				criarDiretorio(diretorioBackup);
		        ProcessBuilder pb;  
		        
		        String nomeBanco = banco + "_" + new SimpleDateFormat("HH:mm:ss").format(new Date());
				pb = new ProcessBuilder(diretorioPostgres+"bin/pg_dump", "-i", "-h", "localhost", "-p", "5432","-U", Constantes.USUARIO_BANCO, "-F", "t", "-b", "-v" ,"-f", diretorioBackup+"/"+nomeBanco, banco);  
		        pb.environment().put("PGPASSWORD", Constantes.SENHA_BANCO);  
		        pb.redirectErrorStream(true);
		        pb.start();   
	        }catch(Exception ex){  
	           ex.printStackTrace();
	        }
		}
	}

	private static void criarDiretorio(String diretorioBackup) throws IOException {
		Runtime r = Runtime.getRuntime();
		Process p = null; 
		r.exec("mkdir " + diretorioBackup);
		exibeStake(p);
	}

	private static void exibeStake(Process p) throws IOException {
		if(p != null){    
			OutputStream outputStream = p.getOutputStream();    
			outputStream.flush();    
			outputStream.close();    
			InputStreamReader streamReader = new InputStreamReader( p.getErrorStream() );    
			BufferedReader reader = new BufferedReader( streamReader );    
			String linha;    
			while( (linha = reader.readLine()) != null )    
				System.out.println(linha);    
		}
	}
	
}
