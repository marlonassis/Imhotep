package br.com.imhotep.linhaMecanica.atualizador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.linhaMecanica.GerenciadorMecanico;

public class AtualizadorEstoqueLM extends GerenciadorMecanico{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public void lockEstoque(int idEstoque, String lote, int cont) throws ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock {
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			setIp("127.0.0.1");
			ResultSet rs = consultar("select bl_lock, dt_data_lock from tb_estoque where id_estoque = "+idEstoque);
			while (rs.next()) { 
				boolean lock = rs.getBoolean("bl_lock");
				Date dataLock = rs.getTimestamp("dt_data_lock");
				if(lock){
					Date data = new Date();     
					long differenceMilliSeconds = data.getTime() - dataLock.getTime();     
					long minutos = differenceMilliSeconds/1000/60;
					//verifica se o estoque est‡ em lock por mais de um minuto 
					if(minutos >= 1){
						throw new ExcecaoEstoqueLockAcimaUmMinuto(lote);
					}else{
						//caso n‹o esteja em lock por mais de um minuto, verifica-se se j‡ houve 5 tentativas de lock
						if(cont < 5)
							lockEstoque(idEstoque, lote, cont++);
						else
							//existindo 5 tentativas de lock, o sistema dispara a exce‹o para alertar ao usu‡rio que ele deve fazer o unlock manualmente
							throw new ExcecaoEstoqueLock();
					}
				}else{
					String sqlLock = "update tb_estoque set bl_lock = true, dt_data_lock = now() where id_estoque = "+idEstoque;
					if(!executarQuery(sqlLock)){
						throw new ExcecaoEstoqueLock();
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ExcecaoEstoqueLock();
		}
	}
	
	public void unLockEstoque(int idEstoque) throws ExcecaoEstoqueUnLock{
		setNomeBanco(DB_BANCO_IMHOTEP);
		setIp("127.0.0.1");
		String sqlUnLock = "update tb_estoque set bl_lock = false, dt_data_lock = null where id_estoque = "+idEstoque;
		if(!executarQuery(sqlUnLock))
			throw new ExcecaoEstoqueUnLock();
	}
}
