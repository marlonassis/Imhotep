package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.entidade.SqlExecutado;
import br.com.remendo.PadraoHome;

@ManagedBean(name="sqlExecutadoHome")
@RequestScoped
public class SqlExecutadoHome extends PadraoHome<SqlExecutado>{
	
	public boolean enviar(SqlExecutado sqlExecutado) {
		setInstancia(sqlExecutado);
		return super.enviar();
	}
	
}
