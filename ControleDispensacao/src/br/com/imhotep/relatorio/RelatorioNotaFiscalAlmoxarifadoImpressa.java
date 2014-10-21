package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.NotaFiscalAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;

@ManagedBean
@ViewScoped
public class RelatorioNotaFiscalAlmoxarifadoImpressa extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio(String id){
		try {
			NotaFiscalAlmoxarifado nf = new NotaFiscalAlmoxarifadoConsultaRaiz().consultar(Integer.valueOf(id));
			gerar(nf);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void gerar(NotaFiscalAlmoxarifado notaFiscalAlmoxarifado) throws ClassNotFoundException, IOException, JRException, SQLException {
		
		String nomeRelatorio = notaFiscalAlmoxarifado.getIdentificacao()+".pdf";
		String caminho = Constantes.DIR_RELATORIO + "RelatorioNotaFiscalAlmoxarifadoImpressa.jasper";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//injetando o relat—rio de materiais
		InputStream subInputStreamNotaFiscalItens = this.getClass().getResourceAsStream("RelatorioNotaFiscalAlmoxarifadoItens.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MATERIAIS", subInputStreamNotaFiscalItens);
		
		super.geraRelatorio(caminho, nomeRelatorio, notaFiscalAlmoxarifado, map);
	}
	
}
