package br.com.imhotep.consulta.raiz;

import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Material;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="statusEstoqueConsultaRaiz")
@RequestScoped
public class StatusEstoqueConsultaRaiz  extends ConsultaGeral<Estoque>{

	private boolean estoqueInsuficiente(Integer quantidade, int quantidadeSolicitada) {
		if(quantidadeSolicitada > quantidade){
			super.mensagem("Não há quantidade suficiente no estoque. O máximo disponível é de " + String.valueOf(quantidade) + " unidade(s)", "", FacesMessage.SEVERITY_ERROR);
			return true;
		}else{
			return false;
		}
	}
	
	private boolean estoqueVazio(Integer quantidade) {
		if(quantidade > 0){
			return false;
		}else{
			super.mensagem("Este medicamento está em falta.", "", FacesMessage.SEVERITY_ERROR);
			return true;
		}
	}
	
	private Object[] consultaEstoque(Material material) {
		StringBuilder sb = new StringBuilder("select CASE WHEN sum(o.quantidade) = null THEN 0 ELSE sum(o.quantidade)END, ");
		sb.append("(select CASE WHEN sum(a.quantidade) = null THEN 0 ELSE sum(a.quantidade) END ");
		sb.append("from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' and a.prescricaoItem.status = 'S' and a.prescricaoItem.material.idMaterial = :idMaterial) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial and o.bloqueado = false and o.dataValidade >= now()");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		return cg.consultaUnica(sb, map);
	}
	
	public boolean quantidadeAutorizada(Material material, int quantidadeSolicitada) {
		Object[] totais = consultaEstoque(material);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		return !estoqueVazio(estoqueAtual) && !estoqueInsuficiente(estoqueAtual, quantidadeSolicitada);
	}
	
}
