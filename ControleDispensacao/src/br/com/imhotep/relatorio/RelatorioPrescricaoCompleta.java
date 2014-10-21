package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.PrescricaoConsultaRaiz;
import br.com.imhotep.entidade.Prescricao;

@ManagedBean
@ViewScoped
public class RelatorioPrescricaoCompleta extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio(String id){
		try {
			Prescricao p = new PrescricaoConsultaRaiz().consultar(Integer.valueOf(id));
			gerar(p);
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
	
	public void gerarRelatorio(Prescricao prescricao) throws ClassNotFoundException, IOException, JRException, SQLException {
		gerar(prescricao);
	}
	
	private void gerar(Prescricao prescricao) throws ClassNotFoundException, IOException, JRException, SQLException {
		
		String nomeRelatorio = (prescricao.getPaciente().getNome()+"-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf").replaceAll(" ", "_");
		String caminho = Constantes.DIR_RELATORIO + "PrescricaoIndividual.jasper";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//injetando o relat—rio de f‡rmacos
		InputStream subInputStreamFarmaco = this.getClass().getResourceAsStream("PrescricaoIndividualFarmaco.jasper");
		map.put("SUBREPORT_INPUT_STREAM_FARMACO", subInputStreamFarmaco);
		//injetando o relat—rio de doses dos f‡rmacos
		InputStream subInputStreamFarmacoDose = this.getClass().getResourceAsStream("PrescricaoIndividualFarmacoDose.jasper");
		map.put("SUBREPORT_INPUT_STREAM_DOSE_UM", subInputStreamFarmacoDose);
		//injetando o relat—rio de cuidados
		InputStream subInputStreamCuidados = this.getClass().getResourceAsStream("PrescricaoIndividualCuidados.jasper");
		map.put("SUBREPORT_INPUT_STREAM_CUIDADOS", subInputStreamCuidados);
		
		super.geraRelatorio(caminho, nomeRelatorio, prescricao, map);
	}
	
}
