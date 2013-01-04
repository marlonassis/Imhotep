package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.ControleDispensacao.entidade.SqlExecutado;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.remendo.PadraoHome;

@ManagedBean(name="sqlExecutadoHome")
@RequestScoped
public class SqlExecutadoHome extends PadraoHome<SqlExecutado>{
	
	public void informaSqlExecutado(){
		getInstancia().setExecutadoBaseProducao(TipoStatusEnum.S);
		super.atualizar();
	}
	
	public void informaSqlNaoExecutado(){
		getInstancia().setExecutadoBaseProducao(TipoStatusEnum.N);
		super.atualizar();
	}
	
	public boolean enviar(SqlExecutado sqlExecutado) {
		setInstancia(sqlExecutado);
		return super.enviar();
	}
	
}
