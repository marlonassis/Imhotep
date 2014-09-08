package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.DevolucaoMaterial;
import br.com.imhotep.raiz.DevolucaoMaterialRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class DevolucaoMaterialConsultaRaiz  extends ConsultaGeral<DevolucaoMaterial>{

	public void consultarDevolucoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from DevolucaoMaterial o where o.status = 'P'");
		ConsultaGeral<DevolucaoMaterial> cg = new ConsultaGeral<DevolucaoMaterial>();
		DevolucaoMaterialRaiz.getInstanciaAtual().setDevolucoesPendentes(new ArrayList<DevolucaoMaterial>(cg.consulta(sb, null)));
	}
	
	public Long quantidadeItens(DevolucaoMaterial dm){ 
		String sql = "select count(o.idDevolucaoMaterialItem) from DevolucaoMaterialItem o "+ 
					 "where o.devolucaoMaterial.idDevolucaoMaterial = "+dm.getIdDevolucaoMaterial();
		return new ConsultaGeral<Long>(new StringBuilder(sql)).consultaUnica();
	}
	
	public DevolucaoMaterial getDevolucao(int id){
		String sql = "select o from DevolucaoMaterial o "+ 
				 "where o.idDevolucaoMaterial = "+id;
		return new ConsultaGeral<DevolucaoMaterial>(new StringBuilder(sql)).consultaUnica();
	}
	
}