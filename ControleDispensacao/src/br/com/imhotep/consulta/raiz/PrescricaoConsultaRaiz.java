package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Prescricao;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PrescricaoConsultaRaiz  extends ConsultaGeral<Prescricao>{

	public List<Prescricao> consultar() {
		StringBuilder sb = new StringBuilder("select o from Prescricao o");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		
		ConsultaGeral<Prescricao> cg = new ConsultaGeral<Prescricao>();
		return new ArrayList<Prescricao>(cg.consulta(sb, map));
	}
	
	public Prescricao consultar(int idPrescricao) {
		StringBuilder sb = new StringBuilder("select o from Prescricao o where o.idPrescricao = "+idPrescricao);
		return new ConsultaGeral<Prescricao>().consultaUnica(sb);
	}
	
}
