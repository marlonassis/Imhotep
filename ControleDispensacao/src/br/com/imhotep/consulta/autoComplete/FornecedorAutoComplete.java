package br.com.imhotep.consulta.autoComplete;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Fornecedor;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class FornecedorAutoComplete extends ConsultaGeral<Fornecedor> {
	
	public Collection<Fornecedor> autoComplete(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from Fornecedor o where lower(to_ascii(o.nomeFantasia)) like lower(to_ascii('%"+string+"%')) or lower(to_ascii(o.razaoSocial)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
	public Collection<Fornecedor> autoCompleteCnpj(String string){
		string = string.trim();
		StringBuilder stringB = new StringBuilder("select o from Fornecedor o where lower(to_ascii(o.cadastroPessoaFisicaJuridica)) like lower(to_ascii('%"+string+"%'))");
		return super.consulta(stringB, null);
	}
	
}
