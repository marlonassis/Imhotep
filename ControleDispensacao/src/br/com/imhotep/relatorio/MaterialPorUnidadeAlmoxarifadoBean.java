package br.com.imhotep.relatorio;

/**
 * Criada por Asclepíades Neto 
 * Data: 02/09/2014
 * Funcionalidade: Relatório de materiais/medicamentos por unidade
 * XHTML: /PaginasWeb/Relatorios/Almoxarifado/Material/materialAlmoxarifado.xhtml
 */

import java.io.IOException;
import java.io.InputStream;
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
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioMateriaisPorUnidade;
import br.com.imhotep.entidade.GrupoAlmoxarifado;
import br.com.imhotep.entidade.SubGrupoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.extra.RelMaterialPorUnidade;
import br.com.imhotep.relatorio.PadraoRelatorio;

@ManagedBean(name="materialPorUnidadeAlmoxarifadoBean")
@ViewScoped
public class MaterialPorUnidadeAlmoxarifadoBean extends PadraoRelatorio{
//TODO Rel 1
	private static final long serialVersionUID = 1L;
	private Date dataIni;
	private Date dataFim;
	private Unidade unidade;
	private GrupoAlmoxarifado grupoAlmoxarifado;
	private SubGrupoAlmoxarifado subGrupoAlmoxarifado;
	private List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList;
	
	public void gerarRelatorioAloxarifado() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioAlmoxarifadoUnidade.jasper";
		/**/String nomeRelatorio = "MaterialUnidadeAlmoxarifado-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		
		ConsultaRelatorioMateriaisPorUnidade consulta = new ConsultaRelatorioMateriaisPorUnidade();
		List<RelMaterialPorUnidade> lista = 
			consulta.consultaAlmoxarifado( unidade, grupoAlmoxarifado, subGrupoAlmoxarifado, dataIni, dataFim );
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("Setor", "Almoxarifado");
		
		InputStream subInputStreamNotaFiscal = this.getClass().getResourceAsStream("RelatorioAlmoxarifadoUnidadeSubreport.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ITENS", subInputStreamNotaFiscal);
		
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
	
	public List<SubGrupoAlmoxarifado> getSubGrupoAlmoxarifadoList() {
		return subGrupoAlmoxarifadoList;
	}

	public void setSubGrupoAlmoxarifadoList(List<SubGrupoAlmoxarifado> subGrupoAlmoxarifadoList) {
		this.subGrupoAlmoxarifadoList = subGrupoAlmoxarifadoList;
	}
	
	public void setSubGrupoAlmoxarifado(SubGrupoAlmoxarifado subGrupoAlmoxarifado) {
		this.subGrupoAlmoxarifado = subGrupoAlmoxarifado;
	}
	
	public SubGrupoAlmoxarifado getSubGrupoAlmoxarifado() {
		return subGrupoAlmoxarifado;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}
