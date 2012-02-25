package br.com.ControleDispensacao.negocio;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="estoqueHome")
@SessionScoped
public class EstoqueHome extends PadraoHome<Estoque>{
	
	public static EstoqueHome getInstanciaEstoque(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return (EstoqueHome) session.getAttribute("estoqueHome");
	}
	
	public List<Estoque> listaEstoqueMaterial(Material material){
		return getBusca("select o from Estoque o where o.material.idMaterial = " + material.getIdMaterial()); 
	}
	
	@Override
	public boolean atualizar() {
		getInstancia().setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataAlteracao(new Date());
		return super.atualizar();
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
