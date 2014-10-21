package br.com.imhotep.relatorio;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.EstoqueContagemConsultaRaiz;
import br.com.imhotep.entidade.EstoqueContagem;
import br.com.imhotep.entidade.Profissional;

@ManagedBean
@ViewScoped
public class RelatorioEstoqueContagem extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	
	private List<Profissional> profissionais(List<EstoqueContagem> lista){
		Set<Profissional> profissionais = new HashSet<Profissional>();
		for(EstoqueContagem item : lista){
			profissionais.add(item.getResponsavel());
		}
		return new ArrayList<Profissional>(profissionais);
	}
	
	private Date maiorData(List<EstoqueContagem> lista){
		if(lista != null && !lista.isEmpty()){
			Date maiorData = lista.get(0).getDataCadastro();
			for(EstoqueContagem item : lista){
				if(item.getDataCadastro().compareTo(maiorData) > 0){
					maiorData = item.getDataCadastro();
				}
			}
			return maiorData;
		}
		return new Date();
	}
	
	private Date menorData(List<EstoqueContagem> lista){
		if(lista != null && !lista.isEmpty()){
			Date menorData = lista.get(0).getDataCadastro();
			for(EstoqueContagem item : lista){
				if(item.getDataCadastro().compareTo(menorData) < 0){
					menorData = item.getDataCadastro();
				}
			}
			return menorData;
		}
		return new Date();
	}
	
	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioEstoqueContagem.jasper";
		String nomeRelatorio = "RelatorioEstoqueContagem-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		List<EstoqueContagem> lista = new EstoqueContagemConsultaRaiz().consultarEstoquesContagem();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("responsaveis", profissionais(lista));
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(menorData(lista)));
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy HH:mm").format(maiorData(lista)));
		
		//injetando o relat—rio de f‡rmacos
		InputStream subInputStreamResponsaveis = this.getClass().getResourceAsStream("RelatorioEstoqueContagemResponsaveis.jasper");
		map.put("SUBREPORT_INPUT_STREAM_RESPONSAVEIS", subInputStreamResponsaveis);
		
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
	}

}
