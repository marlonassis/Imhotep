package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.DevolucaoMedicamento;
import br.com.imhotep.raiz.DevolucaoMedicamentoRaiz;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class DevolucaoMedicamentoConsultaRaiz  extends ConsultaGeral<DevolucaoMedicamento>{

	public void consultarDevolucoesPendentes() {
		StringBuilder sb = new StringBuilder("select o from DevolucaoMedicamento o where o.status = 'P'");
		ConsultaGeral<DevolucaoMedicamento> cg = new ConsultaGeral<DevolucaoMedicamento>();
		DevolucaoMedicamentoRaiz.getInstanciaAtual().setDevolucoesPendentes(new ArrayList<DevolucaoMedicamento>(cg.consulta(sb, null)));
	}
	
	public Long quantidadeItens(DevolucaoMedicamento dm){ 
		String sql = "select count(o.idDevolucaoMedicamentoItem) from DevolucaoMedicamentoItem o "+ 
					 "where o.devolucaoMedicamento.idDevolucaoMedicamento = "+dm.getIdDevolucaoMedicamento();
		return new ConsultaGeral<Long>(new StringBuilder(sql)).consultaUnica();
	}
	
	public DevolucaoMedicamento getDevolucao(int id){
		String sql = "select o from DevolucaoMedicamento o "+ 
				 "where o.idDevolucaoMedicamento = "+id;
		return new ConsultaGeral<DevolucaoMedicamento>(new StringBuilder(sql)).consultaUnica();
	}
	
}