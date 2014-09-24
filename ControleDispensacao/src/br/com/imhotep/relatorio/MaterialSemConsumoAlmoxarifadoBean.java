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

/**
 * Criada por Asclepíades Neto 
 * Data: 09/09/2014
 * Funcionalidade: Relatório de materiais do almoxarifado sem consumo.
 * XHTML: /PaginasWeb/Relatorios/Almoxarifado/Material/materialSemConsumoAlmoxarifado.xhtml
 */
//TODO REL3 - ALMOXARIFADO
@ManagedBean(name="matSemConsAlmoxBean")
@ViewScoped
public class MaterialSemConsumoAlmoxarifadoBean extends PadraoRelatorio{
	
	private static final long serialVersionUID = 1L;
	private Date dataIni;
	private Date dataFim;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;
	
	public void gerarRelatorioAloxarifado() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioMateriaisSemConsumo.jasper";
		String nomeRelatorio = "MaterialSemConsumoAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		
		ConsultaMateriaisSemConsumo consulta = new ConsultaMateriaisSemConsumo();
		List<MaterialAlmoxarifado> lista = 
			consulta.consultaAlmoxarifado( grupoAlmoxarifado, subGrupoAlmoxarifado, dataIni, dataFim );
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("Setor", "Almoxarifado");
		
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
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
	
	
}
