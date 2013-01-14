package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.TesteDoPezinho;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="testeDoPezinhoRaiz")
@SessionScoped
public class TesteDoPezinhoRaiz extends PadraoHome<TesteDoPezinho>{
	
	public boolean atualizarLote(TesteDoPezinho obj){
		setInstancia(obj);
		setExibeMensagemAtualizacao(false);
		return super.atualizar();
	}
	
	public void gerarLote(){
		List<TesteDoPezinho> testesSemLote = buscarTestesSemLote();
		TesteDoPezinho ultimoTesteComLote = buscarUltimoTesteComLote();
		String tipo = ultimoTesteComLote.getTipo();
		Long lote = ultimoTesteComLote.getLote();
		
		for(TesteDoPezinho obj : testesSemLote){
			Object res[] = gerarProximoTipo(tipo, lote);
			obj.setTipo((String) res[0]);
			obj.setLote((Long) res[1]);
			if(!new TesteDoPezinhoRaiz().atualizarLote(obj)){
				super.mensagem("Erro ao gerar o lote", null, FacesMessage.SEVERITY_ERROR);
				break;
			}
		}
	}
	
	private Object[] gerarProximoTipo(String tipo, Long lote) {
		char[] tip = tipo.toCharArray();
		char letra = tip[0];
		String v1 = String.valueOf(tip[1]);
		boolean tamTipo = tip.length == 3;
		String v2 = tamTipo ? String.valueOf(tip[2]) : "";
		Integer v = Integer.valueOf(v1.concat(v2));
		
		if(letra == 71 && v == 10){
			letra = 65;
			v = 1;
			lote++;
		}else{
			if(letra < 71 && v == 12){
				letra++;
				v = 1;
			}else{
				v++;
			}
		}
		
		String ret = String.valueOf(letra).concat(v.toString());
		
		Object res[] = {ret, lote};
		
		return res;
	}

	public List<TesteDoPezinho> listaTestesDoPezinhoColeta(Date dataIni, Date dataFim){
		return buscarTestesPorPeriodo(dataIni, dataFim);
	}

	private List<TesteDoPezinho> buscarTestesPorPeriodo(Date dataIni, Date dataFim) {
		ConsultaGeral<TesteDoPezinho> cg = new ConsultaGeral<TesteDoPezinho>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("dataIni", dataIni);
		map.put("dataFim", dataFim);
		String sql = "select o from TesteDoPezinho o where o.dataColeta >= :dataIni and o.dataColeta <= :dataFim";
		return new ArrayList<TesteDoPezinho>(cg.consulta(new StringBuilder(sql), map));
	}
	
	private List<TesteDoPezinho> buscarTestesSemLote() {
		ConsultaGeral<TesteDoPezinho> cg = new ConsultaGeral<TesteDoPezinho>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		String sql = "select o from TesteDoPezinho o where o.lote is null";
		return new ArrayList<TesteDoPezinho>(cg.consulta(new StringBuilder(sql), map));
	}
	
	private TesteDoPezinho buscarUltimoTesteComLote() {
		ConsultaGeral<TesteDoPezinho> cg = new ConsultaGeral<TesteDoPezinho>();
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		String sql = "select o from TesteDoPezinho o where o.dataCadastro = (select max(a.dataCadastro) from TesteDoPezinho a where a.lote != null)";
		return cg.consultaUnica(new StringBuilder(sql), map);
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
