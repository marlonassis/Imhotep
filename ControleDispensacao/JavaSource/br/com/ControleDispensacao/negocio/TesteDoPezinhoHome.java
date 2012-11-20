package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TesteDoPezinho;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="testeDoPezinhoHome")
@SessionScoped
public class TesteDoPezinhoHome extends PadraoHome<TesteDoPezinho>{
	
	public List<TesteDoPezinho> listaTestesDoPezinhoColeta(Date dataIni, Date dataFim){
		ConsultaGeral<TesteDoPezinho> cg = new ConsultaGeral<TesteDoPezinho>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o from TesteDoPezinho o where o.dataColeta >= :dataIni and o.dataColeta <= :dataFim";
		return new ArrayList<TesteDoPezinho>(cg.consulta(new StringBuilder(sql), map));
	}
	
	@Override
	public boolean enviar() {
		try {
			getInstancia().setDataCadastro(new Date());
			getInstancia().setProfissionalCadastrante(Autenticador.getInstancia().getProfissionalAtual());
			return super.enviar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
