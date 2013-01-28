package br.com.Imhotep.negocio;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.TipoExame;
import br.com.remendo.PadraoHome;

@ManagedBean(name="tipoExameRaiz")
@SessionScoped
public class TipoExameRaiz extends PadraoHome<TipoExame>{
	
	public List<TipoExame> getTipoExameLista(){
		return super.getBusca("select o from TipoExame o");
	}
	
}
