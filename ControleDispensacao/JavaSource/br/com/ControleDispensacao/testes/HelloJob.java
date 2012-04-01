package br.com.ControleDispensacao.testes;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
	public HelloJob() {
	}
	public void execute(JobExecutionContext context) throws JobExecutionException{
		System.err.println("Hello! HelloJob is executing.");
	}
}
