package br.com.imhotep.raiz;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.TipoExame;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="tipoExameRaiz")
@SessionScoped
public class TipoExameRaiz extends PadraoRaiz<TipoExame>{
	
	public List<TipoExame> getTipoExameLista(){
		return super.getBusca("select o from TipoExame o");
	}
	
}
