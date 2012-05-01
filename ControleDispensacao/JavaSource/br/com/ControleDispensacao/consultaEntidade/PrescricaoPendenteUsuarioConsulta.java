package br.com.ControleDispensacao.consultaEntidade;

import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoConsulta;

@ManagedBean(name="prescricaoPendenteUsuarioConsulta")
@SessionScoped
public class PrescricaoPendenteUsuarioConsulta extends PadraoConsulta<Prescricao> {
	public PrescricaoPendenteUsuarioConsulta(){
		setOrderBy("o.dataInclusao desc");
	}
	
	@Override
	public List<Prescricao> getList() {
		setConsultaGeral(new ConsultaGeral<Prescricao>());
		carregaValoresConsulta();
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from Prescricao o where o.usuarioInclusao.idUsuario = :usuarioInclusao and o.dispensavel = 'N' "));
		return super.getList();
	}
	
	private void carregaValoresConsulta() {
		HashMap<Object, Object> restricaoConsulta = new HashMap<Object, Object>();
		restricaoConsulta.put("usuarioInclusao", Autenticador.getInstancia().getUsuarioAtual().getIdUsuario());
		getConsultaGeral().setAddValorConsulta(restricaoConsulta);
	}
}
