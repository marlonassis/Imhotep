package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="estoqueHome")
@SessionScoped
public class EstoqueHome extends PadraoHome<Estoque>{
	
	public static EstoqueHome getInstanciaEstoque(){
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);    
		return (EstoqueHome) session.getAttribute("estoqueHome");
	}

	public List<Estoque> listaEstoqueMaterialDispensacao(Material material){
		return getBusca("select o from Estoque o where o.dataValidade >= now() and o.quantidade > 0 and o.bloqueado = 'N' and o.material.idMaterial = " + material.getIdMaterial() + " order by o.dataValidade"); 
	}

	//lista do estoque que contém os medicamentos que estão com a data de vencimento dentro do período
	public List<Estoque> listaEstoqueRelatorioVencimentoPeriodo(Date dataIni, Date dataFim){
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o from Estoque o where o.dataValidade >= :dataIni and o.dataValidade <= :dataFim";
		return new ArrayList<Estoque>(cg.consulta(new StringBuilder(sql), map));
	}
	
	public List<Estoque> listaEstoqueRelatorioGeral(){
		return getBusca("select o from Estoque o where o.bloqueado = 'N'"); 
	}
	
	public List<Estoque> listaEstoqueMaterial(Material material){
		return getBusca("select o from Estoque o where o.material.idMaterial = " + material.getIdMaterial()); 
	}
	
	public boolean estoqueInsuficiente(Integer quantidade, Integer quantidadeDoses, Integer quantidadePorDose) {
		int quantidadeReserva = quantidadeDoses * quantidadePorDose;
		if(quantidadeReserva > quantidade){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não há quantidade suficiente no estoque. O máximo disponível é de " + String.valueOf(quantidade), ""));
			return true;
		}else{
			return false;
		}
	}

	public boolean estoqueVazio(Integer quantidade) {
		if(quantidade > 0){
			return false;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este medicamento está em falta.", ""));
			return true;
		}
	}

	public Object[] consultaEstoque(Material material) {
		StringBuilder sb = new StringBuilder("select CASE WHEN sum(o.quantidade) = null THEN 0 ELSE sum(o.quantidade)END, ");
		sb.append("(select CASE WHEN sum(a.quantidade) = null THEN 0 ELSE sum(a.quantidade) END ");
		sb.append("from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' and a.prescricaoItem.status = 'S' and a.prescricaoItem.material.idMaterial = :idMaterial) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial and o.bloqueado = 'N' and o.dataValidade >= now()");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		return cg.consultaUnica(sb, map);
	}
	
	@Override
	public boolean enviar() {
		getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().setDataInclusao(new Date());
		return super.enviar();
	}
	
}
