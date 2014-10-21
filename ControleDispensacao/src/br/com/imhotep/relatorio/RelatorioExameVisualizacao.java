package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.LaboratorioSolicitacao;

@ManagedBean
@ViewScoped
public class RelatorioExameVisualizacao extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio(LaboratorioSolicitacao obj) throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioExameResultado.jasper";
		String nomeRelatorio = "Exame-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("SOLICITACAO_EXAME", obj);
		super.geraRelatorio(caminho, nomeRelatorio, new ArrayList<LaboratorioSolicitacao>(), map);
	}
	
}
