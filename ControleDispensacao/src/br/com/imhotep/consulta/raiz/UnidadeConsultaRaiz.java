package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Unidade;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class UnidadeConsultaRaiz  extends ConsultaGeral<Unidade>{
	
	public List<Unidade> getTodasUnidade() {
		StringBuilder sb = new StringBuilder("select o from Unidade as o order by to_ascii(o.nome)");
		return new ArrayList<Unidade>(new ConsultaGeral<Unidade>().consulta(sb, null));
	}
	
	public List<Unidade> getUnidadesSaude() {
		StringBuilder sb = new StringBuilder("select o.unidade from UnidadeSolicitacao as o order by to_ascii(lower(o.unidade.nome))");
		return new ArrayList<Unidade>(new ConsultaGeral<Unidade>().consulta(sb, null));
	}
	
}
