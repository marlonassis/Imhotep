package br.com.imhotep.relatorio;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.SubGrupoAlmoxarifadoConsultaRaiz;
import br.com.imhotep.consulta.relatorio.ConsultaMateriaisSemConsumo;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.relatorio.excel.MaterialSemConsumoAlmoxarixadoExcel;

/**
 * Criada por Asclepíades Neto 
 * Data: 09/09/2014
 * Funcionalidade: Relatório de materiais do almoxarifado sem consumo.
 * XHTML: /PaginasWeb/Relatorios/Almoxarifado/Material/materialSemConsumoAlmoxarifado.xhtml
 */

@ManagedBean(name="matSemConsAlmoxBean")
@ViewScoped
public class MaterialSemConsumoAlmoxarifadoBean extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private Date dataIni;
	private Date dataFim;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;
	private boolean excel;
	
	public void gerarRelatorioAloxarifado() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMateriaisSemConsumo.";
		String nomeRelatorio;
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		
		ConsultaMateriaisSemConsumo consulta = new ConsultaMateriaisSemConsumo();
		List<MaterialAlmoxarifado> lista = 
			consulta.consultaAlmoxarifado( grupoAlmoxarifado, subGrupoAlmoxarifado, dataIni, dataFim );
		
		String dataIniString = new SimpleDateFormat("dd/MM/yyyy").format(dataIni);
		String dataFimString = new SimpleDateFormat("dd/MM/yyyy").format(dataFim);
			
		//Requisito Funcional #20
		if (excel==false){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dataIni", dataIniString );
			map.put("dataFim", dataFimString );
			map.put("Setor", "Almoxarifado");
			
			nomeRelatorio = "MaterialSemConsumoAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
			super.geraRelatorio(caminho+"jasper", nomeRelatorio, lista, map);
		}
		else{			
			nomeRelatorio = "MaterialSemConsumoAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".xls";
			MaterialSemConsumoAlmoxarixadoExcel exc;
	        exc = new MaterialSemConsumoAlmoxarixadoExcel(lista, "Almoxarifado", dataIniString+" a "+dataFimString,4);
	        exc.gerarPlanilha();
			super.geraRelatorioExcel(nomeRelatorio, exc.getWorkbook());
		}
		
	}
	
	public void atualizaSubGrupoAmoxarifado(){
		if(getGrupoAlmoxarifado() != null){
			setSubGrupoAlmoxarifadoList(new SubGrupoAlmoxarifadoConsultaRaiz().consultarSubGrupoGrupo(getGrupoAlmoxarifado().getIdGrupoAlmoxarifado()));
			if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty()){
				setSubGrupoAlmoxarifado(null);
			}
		}else
			setSubGrupoAlmoxarifadoList(new ArrayList<SubGrupoAlmoxarifado>());
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoAlmoxarifadoList() == null || getSubGrupoAlmoxarifadoList().isEmpty())
			return false;
		return true;
	}
	
	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public GrupoAlmoxarifado getGrupoAlmoxarifado() {
		return grupoAlmoxarifado;
	}
	public void setGrupoAlmoxarifado(GrupoAlmoxarifado grupoAlmoxarifado) {
		this.grupoAlmoxarifado = grupoAlmoxarifado;
	}
	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return subGrupoAlmoxarifado;
	}
	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
	}
	public List<SubGrupoAlmoxarifado> getSubGrupoAlmoxarifadoList() {
		return subGrupoAlmoxarifadoList;
	}
	public void setSubGrupoAlmoxarifadoList(
			List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList) {
		this.subGrupoAlmoxarifadoList = subGrupoAlmoxarifadoList;
	}

	public boolean isExcel() {
		return excel;
	}

	public void setExcel(boolean excel) {
		this.excel = excel;
	}
	
	
}
