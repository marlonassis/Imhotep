package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialLog;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class MaterialLogRaiz extends PadraoRaiz<MaterialLog>{
	public void setDadosBasicos(Date dataLog){
		try {
			getInstancia().setDataLog(dataLog);
			getInstancia().setProfissionalAlteracao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
}
