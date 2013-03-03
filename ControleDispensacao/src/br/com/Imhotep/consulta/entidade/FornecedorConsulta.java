package br.com.Imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;
import br.com.Imhotep.entidade.Fornecedor;

@ManagedBean(name="fornecedorConsulta")
@SessionScoped
public class FornecedorConsulta extends PadraoConsulta<Fornecedor> {
	public FornecedorConsulta(){
		getCamposConsulta().put("o.nomeFantasia", INCLUINDO_TUDO);
		getCamposConsulta().put("o.cadastroPessoaFisicaJuridica", INCLUINDO_TUDO);
		getCamposConsulta().put("o.cidade", IGUAL);
		setOrderBy("to_ascii(o.nomeFantasia)");
	}
	
	@Override
	public List<Fornecedor> getList() {
		setConsultaGeral(new ConsultaGeral<Fornecedor>());
		String sql = "select o from Fornecedor o where 1=1";
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		return super.getList();
	}
}
