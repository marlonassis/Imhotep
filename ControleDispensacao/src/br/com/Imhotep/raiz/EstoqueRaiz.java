package br.com.Imhotep.raiz;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="estoqueRaiz")
@SessionScoped
public class EstoqueRaiz extends PadraoHome<Estoque>{
	
	public EstoqueRaiz() {
	}
	
	public EstoqueRaiz(Estoque estoque) {
		setInstancia(estoque);
	}
	
//	public List<Estoque> listaEstoqueMaterialDispensacao(Material material){
//		return getBusca("select o from Estoque o where o.dataValidade >= now() and o.quantidade > 0 and o.bloqueado = false and o.material.idMaterial = " + material.getIdMaterial() + " order by o.dataValidade"); 
//	}
//
//	public Collection<Estoque> getListaEstoqueCentroCirurgicoAutoComplete(String sql){
//		return super.getBusca("select o from Estoque as o where o.dataValidade >= now() and o.bloqueado = false and lower(to_ascii(o.material.descricao)) like lower(to_ascii('%"+sql+"%')) order by o.material.descricao, o.lote ");
//	}	
//	
//	@Override
//	public boolean enviar() {
//		try {
//			getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
//		} catch (Exception e) {
//			e.printStackTrace();
//			super.mensagem("Erro ao pegar o usuário Atual.", null, FacesMessage.SEVERITY_ERROR);
//			System.out.print("Erro em EstoqueHome");
//		}
//		getInstancia().setDataInclusao(new Date());
//		return super.enviar();
//	}
	
}
