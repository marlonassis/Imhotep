package br.com.ControleDispensacao.consultaEntidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.MotivoFimReceita;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean(name="motivoFimReceitaConsulta")
@SessionScoped
public class MotivoFimReceitaConsulta extends PadraoConsulta<MotivoFimReceita> {
	public MotivoFimReceitaConsulta(){
		getCamposConsulta().put("o.descricao", INCLUINDO_TUDO);
		setOrderBy("o.descricao");
	}
	
	@Override
	public List<MotivoFimReceita> getList() {
		setConsultaGeral(new ConsultaGeral<MotivoFimReceita>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from MotivoFimReceita o where 1=1"));
		return super.getList();
	}
}
