package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueLockAcimaUmMinuto;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;

public class AtualizadorEstoqueAlmoxarifadoLM extends GerenciadorMecanico{
	
	private static final String DB_BANCO_IMHOTEP = "db_imhotep";
	
	public void lockEstoque(int idEstoqueAlmoxarifado, int cont) throws ExcecaoEstoqueLockAcimaUmMinuto, ExcecaoEstoqueLock {
		try {
			setNomeBanco(DB_BANCO_IMHOTEP);
			setIp("127.0.0.1");
			ResultSet rs = consultar("select bl_lock, dt_data_lock from tb_estoque_almoxarifado where id_estoque_almoxarifado = "+idEstoqueAlmoxarifado);
			while (rs.next()) { 
				boolean lock = rs.getBoolean("bl_lock");
				Date dataLock = rs.getDate("dt_data_lock");
				if(lock){
					Date data = new Date();     
					long differenceMilliSeconds = data.getTime() - dataLock.getTime();     
					long minutos = differenceMilliSeconds/1000/60;
					//verifica se o estoque está em lock por mais de um minuto 
					if(minutos >= 1){
						throw new ExcecaoEstoqueLockAcimaUmMinuto();
					}else{
						//caso não esteja em lock por mais de um minuto, verifica-se se já houve 5 tentativas de lock
						if(cont < 5)
							lockEstoque(idEstoqueAlmoxarifado, cont++);
						else
							//existindo 5 tentativas de lock, o sistema dispara a exceção para alertar ao usuário que ele deve fazer o unlock manualmente
							throw new ExcecaoEstoqueLock();
					}
				}else{
					String sqlLock = "update tb_estoque_almoxarifado set bl_lock = true, dt_data_lock = now() where id_estoque_almoxarifado = "+idEstoqueAlmoxarifado;
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
	
	public void unLockEstoque(int idEstoqueAlmoxarifado) throws ExcecaoEstoqueUnLock{
		setNomeBanco(DB_BANCO_IMHOTEP);
		setIp("127.0.0.1");
		String sqlUnLock = "update tb_estoque_almoxarifado set bl_lock = false, dt_data_lock = null where id_estoque_almoxarifado = "+idEstoqueAlmoxarifado;
		if(!executarQuery(sqlUnLock))
			throw new ExcecaoEstoqueUnLock();
	}
}
