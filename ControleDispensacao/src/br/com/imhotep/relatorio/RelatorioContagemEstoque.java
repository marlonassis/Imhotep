package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueConsultaRaiz;
import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Profissional;

@ManagedBean
@ViewScoped
public class RelatorioContagemEstoque extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private Profissional profissional;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioContagemEstoque.jasper";
		String nomeRelatorio = "RelatorioEstoqueVencido-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<Estoque> lista = new EstoqueConsultaRaiz().consultarEstoqueValido();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("nomeResponsavel", getProfissional().getNome());
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	
}
