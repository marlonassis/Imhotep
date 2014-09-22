package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.NotaFiscalConsultaRaiz;
import br.com.imhotep.entidade.NotaFiscal;

@ManagedBean
@ViewScoped
public class RelatorioNotaFiscalImpressa extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio(String id){
		try {
			NotaFiscal nf = new NotaFiscalConsultaRaiz().consultar(Integer.valueOf(id));
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
	
	private void gerar(NotaFiscal notaFiscal) throws ClassNotFoundException, IOException, JRException, SQLException {
		
		String nomeRelatorio = notaFiscal.getIdentificacaoNotaFiscal()+".pdf";
		String caminho = Constantes.DIR_RELATORIO + "RelatorioNotaFiscalImpressa.jasper";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//injetando o relat—rio de f‡rmacos
		InputStream subInputStreamNotaFiscalItens = this.getClass().getResourceAsStream("RelatorioNotaFiscalItens.jasper");
		map.put("SUBREPORT_INPUT_STREAM_MEDICAMENTOS", subInputStreamNotaFiscalItens);
		
		super.geraRelatorio(caminho, nomeRelatorio, notaFiscal, map);
	}
	
}
