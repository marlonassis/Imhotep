package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueContagem;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EstoqueContagemRaiz extends PadraoHome<EstoqueContagem>{

	public EstoqueContagemRaiz() {
		super();
		novaInstancia();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setDataContagem(new Date());
	}
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setResponsavel(Autenticador.getProfissionalLogado());
			getInstancia().setDataCadastro(new Date());
			getInstancia().setQuantidadeAtual(getInstancia().getEstoque().getQuantidadeAtual());
			if(super.enviar()){
				novaInstancia();
				return true;
			}else{
				return false;
			}
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
