package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.PacienteEntrada;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@ViewScoped
public class RelatorioAdmissaoPacienteEntrada extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio(String id) throws ClassNotFoundException, IOException, JRException, SQLException {
		gerar(new ConsultaGeral<PacienteEntrada>(new StringBuilder("select o from PacienteEntrada o where o.idPacienteEntrada = "+id)).consultaUnica());
	}
	
	public void gerarRelatorio(PacienteEntrada pacienteEntrada) throws ClassNotFoundException, IOException, JRException, SQLException {
		gerar(pacienteEntrada);
	}
	
	private void gerar(PacienteEntrada pacienteEntrada) throws ClassNotFoundException, IOException, JRException, SQLException {
		
		String nomeRelatorio = pacienteEntrada.getPaciente().getNome().concat("-").concat(new SimpleDateFormat("dd-MM-yyyy").format(pacienteEntrada.getDataAtendimento()));
		String caminho = Constantes.DIR_RELATORIO + "AdmissaoPacienteEntrada.jasper";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//injetando o relatório com o(s) cid(s)
		InputStream subInputStreamCid = this.getClass().getResourceAsStream("AdmissaoPacienteEntradaCid.jasper");
		map.put("SUBREPORT_INPUT_STREAM_CID", subInputStreamCid);
		//injetando o relatório de doses do(s) procedimento(s)
		InputStream subInputStreamProcedimento = this.getClass().getResourceAsStream("AdmissaoPacienteEntradaProcedimento.jasper");
		map.put("SUBREPORT_INPUT_STREAM_PROCEDIMENTO_SAUDE", subInputStreamProcedimento);
		//inputstream 
		map.put("LOGO_HU", Utilitarios.getImagemLogoHU());
		
		super.geraRelatorio(caminho, nomeRelatorio, pacienteEntrada, map);
	}
	
}
