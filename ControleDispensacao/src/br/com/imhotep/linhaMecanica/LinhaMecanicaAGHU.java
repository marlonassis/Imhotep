package br.com.imhotep.linhaMecanica;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.imhotep.auxiliar.Constantes;
import br.com.remendo.utilidades.Utilitarios;

public class LinhaMecanicaAGHU extends GerenciadorMecanico {
	
	public LinhaMecanicaAGHU(){
		super();
		super.setIp(Constantes.IP_AGHU);
		super.setNomeBanco(Constantes.NOME_BANCO_AGHU);
		super.setPorta(Constantes.PORTA_BANCO_AGHU);
		super.setUsuarioBanco(Constantes.USUARIO_BANCO_AGHU);
		super.setSenhaBanco(Constantes.SENHA_BANCO_AGHU);
	}
	
	public LinhaMecanicaAGHU(String ip, String nomeBanco, String porta, String usuarioBanco, String senhaBanco){
		super();
		super.setIp(ip);
		super.setNomeBanco(nomeBanco);
		super.setPorta(porta);
		super.setUsuarioBanco(usuarioBanco);
		super.setSenhaBanco(senhaBanco);
	}
	
	public boolean executarCUD(String sql){
		return super.executarQuery(sql);
	}
	
	public List<Object[]> getListaResultado(String sql){
		String[] res = sql.substring(sql.indexOf("select")+6, sql.indexOf("from")-1).split(",");
		return getListaResultado(sql, res.length);
	}
	
	public List<Object[]> getListaResultado(String sql, int quantidadeColunas){
		ResultSet rs = super.consultar(sql);
		List<Object[]> res = new ArrayList<Object[]>();
		try {
			while(rs.next()){
				Object[] array=null;
				for(int cont = 1;cont <= quantidadeColunas;cont++){
					array = Utilitarios.addElemento(array, rs.getObject(cont));
				}
				res.add(array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
}
