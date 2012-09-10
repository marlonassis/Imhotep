package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.EstoqueCentroCirurgicoLivro;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="estoqueCentroCirurgicoLivroEstoqueAtualConsulta")
@SessionScoped
public class EstoqueCentroCirurgicoLivroEstoqueAtualConsulta extends PadraoConsulta<EstoqueCentroCirurgicoLivro> {
	public EstoqueCentroCirurgicoLivroEstoqueAtualConsulta(){
		getCamposConsulta().put("o.estoqueCentroCirurgico.lote", INCLUINDO_TUDO);
		getCamposConsulta().put("o.estoqueCentroCirurgico.material", IGUAL);
		setOrderBy("o.estoqueCentroCirurgico.material.descricao, o.estoqueCentroCirurgico.lote, o.estoqueCentroCirurgico.dataValidade");
	}
	
	@Override
	public List<EstoqueCentroCirurgicoLivro> getList() {
		setConsultaGeral(new ConsultaGeral<EstoqueCentroCirurgicoLivro>());
		String sql = "select o from EstoqueCentroCirurgicoLivro o " + 
					 "where o.dataMovimento = (select max(b.dataMovimento) from EstoqueCentroCirurgicoLivro b where " +
					 "b.estoqueCentroCirurgico = o.estoqueCentroCirurgico and b.estoqueCentroCirurgico.bloqueado = 'N')";
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		return super.getList();
	}
}
