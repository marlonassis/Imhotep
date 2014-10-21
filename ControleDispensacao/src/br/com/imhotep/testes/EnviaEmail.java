package br.com.imhotep.testes;

import org.quartz.JobExecutionContext;

public  class EnviaEmail implements org.quartz.Job {
	public void execute(JobExecutionContext context) {
		System.out.println("enviando email para evisar mudanca de senha...");
		// acessar api de e-mail aqui
	}
}
