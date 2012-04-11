package br.com.ControleDispensacao.testes;

import java.io.IOException;

public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runtime run = Runtime.getRuntime();
		try {
			run.exec("/opt/PostgreSQL/9.1/bin/pg_dump -U postgres -E latin1 -F custom -b -v db_farmacia > /home/marlonassis2/banco_backup/teste");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
