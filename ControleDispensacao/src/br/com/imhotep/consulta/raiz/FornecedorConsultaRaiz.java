package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Fornecedor;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FornecedorConsultaRaiz  extends ConsultaGeral<Fornecedor>{
	
	public List<Fornecedor> getFornecedores() {
		StringBuilder sb = new StringBuilder("select o from Fornecedor as o order by to_ascii(o.razaoSocial)");
		return new ArrayList<Fornecedor>(new ConsultaGeral<Fornecedor>().consulta(sb, null));
	}
	
}
