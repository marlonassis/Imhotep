package br.com.Imhotep.consultaAutoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.Imhotep.entidade.Fornecedor;
import br.com.remendo.ConsultaGeral;

@ManagedBean(name="fornecedorAutoComplete")
@RequestScoped
public class FornecedorAutoComplete extends ConsultaGeral<Fornecedor> {
	
	public Collection<Fornecedor> autoComplete(String string){
		StringBuilder stringB = new StringBuilder("select o from Fornecedor o where lower(to_ascii(o.nomeFantasia)) like lower(to_ascii('%"+string+"%')) or lower(to_ascii(o.razaoSocial)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
