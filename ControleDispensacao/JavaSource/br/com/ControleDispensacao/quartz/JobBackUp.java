package br.com.ControleDispensacao.quartz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.ControleDispensacao.entidade.ErroAplicacao;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.negocio.ErroAplicacaoHome;
import br.com.ControleDispensacao.seguranca.Autenticador;

public class JobBackUp implements Job{
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			realizaBackup();
		} catch (IOException e) {
			e.printStackTrace();
			geraErroAplicacao(e);
		}
	}
	
	private void geraErroAplicacao(IOException e) {
		ErroAplicacao ea = new ErroAplicacao();
		ea.setAtendido(TipoStatusEnum.N);
		ea.setDataOcorrencia(new Date());
		ea.setMessage(e.getMessage());
		ea.setMetodo(this.getClass().getName().concat(".executaBackUp()"));
		ea.setStackTrace(e.getStackTrace().toString());
		ea.setUsuario(Autenticador.getInstancia().getUsuarioAtual());
		ErroAplicacaoHome eah = new ErroAplicacaoHome(ea);
		eah.enviar();
	}
	
	private void realizaBackup() throws IOException {
		Runtime r = Runtime.getRuntime();                
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String diretorio = sdf.format(new Date());
		sdf = new SimpleDateFormat("HH:mm:ss");
		Process p = null; 
		
		String novoDiretorio = "mkdir /home/farmacia/banco_backup/"+diretorio;
		r.exec(novoDiretorio);
		exibeStake(p);
		
		String caminhoBackUp = "/home/farmacia/banco_backup/"+diretorio+"/bd_farmacia_"+sdf.format(new Date());
		p = r.exec("/opt/PostgreSQL/9.1/bin/pg_dump -U postgres -E latin1 -F custom -b -v -f "+caminhoBackUp+" db_farmacia");    
		exibeStake(p);    
	         
	}

	private void exibeStake(Process p) throws IOException {
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
