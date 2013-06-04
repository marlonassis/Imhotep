package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.Estoque;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EstoqueRaiz extends PadraoHome<Estoque>{
	
	//o autenticador carrega essa lista quando o usuário tiver atuorização para vê-la
	private List<Estoque> estoqueVencido = new ArrayList<Estoque>();
	
	public static EstoqueRaiz getInstanciaAtual(){
		try {
			return (EstoqueRaiz) Utilitarios.procuraInstancia(EstoqueRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstoqueRaiz() {
	}
	
	public EstoqueRaiz(Estoque estoque) {
		setInstancia(estoque);
	}

	public List<Estoque> getEstoqueVencido() {
		return estoqueVencido;
	}

	public void setEstoqueVencido(List<Estoque> estoqueVencido) {
		this.estoqueVencido = estoqueVencido;
	}
	
}
