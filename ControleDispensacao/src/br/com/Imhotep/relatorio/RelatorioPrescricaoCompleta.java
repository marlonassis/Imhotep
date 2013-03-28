package br.com.Imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.Imhotep.controle.ControleInstancia;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.raiz.PrescricaoRaiz;

@ManagedBean
@ViewScoped
public class RelatorioPrescricaoCompleta extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		Prescricao prescricao = new Prescricao();
		try {
			prescricao = getPrescricaoSessao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		gerar(prescricao);
	}
	
	private Prescricao getPrescricaoSessao() throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		return ((PrescricaoRaiz) new ControleInstancia().procuraInstancia(PrescricaoRaiz.class)).getPrescricaoVisualizacao();
	}
	
	private void gerar(Prescricao prescricao) throws ClassNotFoundException, IOException, JRException, SQLException {
		
		String nomeRelatorio = (prescricao.getPaciente().getNome()+"-"+new SimpleDateFormat("dd-MM-yyyy").format(prescricao.getDataConclusao())+".pdf").replaceAll(" ", "_");
		String caminho = "/WEB-INF/classes/br/com/Imhotep/relatorio/PrescricaoIndividual.jasper";
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		//injetando o relatório de fármacos
		InputStream subInputStreamFarmaco = this.getClass().getResourceAsStream("PrescricaoIndividualFarmaco.jasper");
		map.put("SUBREPORT_INPUT_STREAM_FARMACO", subInputStreamFarmaco);
		//injetando o relatório de doses dos fármacos
		InputStream subInputStreamFarmacoDose = this.getClass().getResourceAsStream("PrescricaoIndividualFarmacoDose.jasper");
		map.put("SUBREPORT_INPUT_STREAM_DOSE_UM", subInputStreamFarmacoDose);
		//injetando o relatório de cuidados
		InputStream subInputStreamCuidados = this.getClass().getResourceAsStream("PrescricaoIndividualCuidados.jasper");
		map.put("SUBREPORT_INPUT_STREAM_CUIDADOS", subInputStreamCuidados);
		
		super.geraRelatorio(caminho, nomeRelatorio, prescricao, map);
	}
	
}
