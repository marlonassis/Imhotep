package br.com.imhotep.relatorio;

/**
 * Criado por Asclepíades Neto 
 * Data: 02/09/2014
 * Funcionalidade: Relatório de materiais/medicamentos por unidade
 * XHTML: /PaginasWeb/Relatorios/Farmacia/Estoque/materialPorUnidadeFarmacia.xhtml
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
import br.com.imhotep.consulta.raiz.FamiliaConsultaRaiz;
import br.com.imhotep.consulta.raiz.SubGrupoConsultaRaiz;
import br.com.imhotep.consulta.relatorio.ConsultaRelatorioMateriaisPorUnidade;
import br.com.imhotep.entidade.Familia;
import br.com.imhotep.entidade.Grupo;
import br.com.imhotep.entidade.SubGrupo;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.extra.RelMaterialPorUnidade;
import br.com.imhotep.relatorio.PadraoRelatorio;

@ManagedBean(name="materialPorUnidadeFarmaciaBean")
@ViewScoped
public class MaterialPorUnidadeFarmaciaBean extends PadraoRelatorio{
//TODO Rel 1
	private static final long serialVersionUID = 1L;
	private Date dataIni;
	private Date dataFim;
	private Unidade unidade;
	private Grupo grupo;
	private SubGrupo subGrupo;
	private Familia familia;
	private List<SubGrupo> subGrupoList;
	private List<Familia> familiaList;

	public void gerarRelatorio() throws ClassNotFoundException, IOException, JRException, SQLException {
		String caminho = Constantes.DIR_RELATORIO + "RelatorioAlmoxarifadoUnidade.jasper";
		String nomeRelatorio = "MaterialUnidadeFarmacia-"+new SimpleDateFormat("dd-MM-yyyy").format(new Date())+".pdf";
		dataFim = new Utilitarios().ajustarUltimaHoraDia(dataFim);
		
		ConsultaRelatorioMateriaisPorUnidade consulta = new ConsultaRelatorioMateriaisPorUnidade();
		List<RelMaterialPorUnidade> lista = 
			consulta.consultaFarmacia( unidade, grupo, subGrupo, dataIni, dataFim, familia );
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dataIni", new SimpleDateFormat("dd/MM/yyyy").format(dataIni) );
		map.put("dataFim", new SimpleDateFormat("dd/MM/yyyy").format(dataFim) );
		map.put("Setor", "Farmácia");
		
		InputStream subInputStreamNotaFiscal = this.getClass().getResourceAsStream("RelatorioAlmoxarifadoUnidadeSubreport.jasper");
		map.put("SUBREPORT_INPUT_STREAM_ITENS", subInputStreamNotaFiscal);
		
		super.geraRelatorio(caminho, nomeRelatorio, lista, map);
	}
	
	public void atualizaSubGrupo(){
		if(getGrupo() != null){
			setSubGrupoList(new SubGrupoConsultaRaiz().consultarSubGrupoGrupo(getGrupo().getIdGrupo()));
			if(getSubGrupoList() == null || getSubGrupoList().isEmpty()){
				setSubGrupo(null);
			}
		}else
			setSubGrupoList(new ArrayList<SubGrupo>());
	}
	
	public void atualizaFamilia(){
		if( getSubGrupo() != null){
			setFamiliaList(new FamiliaConsultaRaiz().consultarFamiliasSubGrupo(getSubGrupo().getIdSubGrupo()));
			if(getFamiliaList() == null || getFamiliaList().isEmpty()){
				setFamilia(null);
			}
		}else
			setFamiliaList(new ArrayList<Familia>());
	}
	
	public boolean getExibirComboSubGrupo(){
		if(getSubGrupoList() == null || getSubGrupoList().isEmpty())
			return false;
		return true;
	}
	
	public boolean getExibirComboFamilia(){
		if(getFamiliaList() == null || getFamiliaList().isEmpty())
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
	
	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public SubGrupo getSubGrupo() {
		return subGrupo;
	}

	public void setSubGrupo(SubGrupo subGrupo) {
		this.subGrupo = subGrupo;
	}

	public List<SubGrupo> getSubGrupoList() {
		return subGrupoList;
	}

	public void setSubGrupoList(List<SubGrupo> subGrupoList) {
		this.subGrupoList = subGrupoList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}
	
	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public List<Familia> getFamiliaList() {
		return familiaList;
	}

	public void setFamiliaList(List<Familia> familiaList) {
		this.familiaList = familiaList;
	}

}
