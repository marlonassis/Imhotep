package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.LaboratorioExame;
import br.com.imhotep.entidade.LaboratorioSetor;
import br.com.imhotep.entidade.LaboratorioSetorExame;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class ExamesRaiz extends PadraoRaiz<LaboratorioSetor>{
	
	
	private LaboratorioSetor setor = new LaboratorioSetor();
	private LaboratorioExame exame = new LaboratorioExame();
	private LaboratorioExame exameAdd = new LaboratorioExame();
	
	private List<LaboratorioExame> listaExames = new ArrayList<LaboratorioExame>();
	private List<LaboratorioExame> listaExamesOut = new ArrayList<LaboratorioExame>();
	
	
	public ExamesRaiz(){
		setInstancia(new LaboratorioSetor());
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalCadastro(Autenticador.getProfissionalLogado());
			getInstancia().setDataCadastro(new Date());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	public void carregarListaExames(){
		String hql = "select o.laboratorioExame from LaboratorioSetorExame o where o.laboratorioSetor.idLaboratorioSetor = "+setor.getIdLaboratorioSetor();
		setListaExames(new ArrayList<LaboratorioExame>(new ConsultaGeral<LaboratorioExame>(new StringBuilder(hql)).consulta()));
		String hql2 = "select o from LaboratorioExame o  where o not in ("+hql+")";
		setListaExamesOut(new ArrayList<LaboratorioExame>(new ConsultaGeral<LaboratorioExame>(new StringBuilder(hql2)).consulta()));
	}
	
	public void removerExameSetor(){
		String hql = "delete LaboratorioSetorExame o where o.laboratorioExame.idLaboratorioExame = " + getExame().getIdLaboratorioExame() +
				" and o.laboratorioSetor.idLaboratorioSetor = "+ getSetor().getIdLaboratorioSetor();
		executa(hql);
		carregarListaExames();
	}
	
	public void adicionarExameSetor(){
		try {
			LaboratorioSetorExame lse = new LaboratorioSetorExame();
			lse.setDataCadastro(new Date());
			lse.setProfissionalCadastro(Autenticador.getProfissionalLogado());
			lse.setLaboratorioSetor(getSetor());
			lse.setLaboratorioExame(getExameAdd());
			enviarGenerico(lse);
			carregarListaExames();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
	public boolean getExibirExames(){
		if(getSetor() != null && getSetor().getIdLaboratorioSetor() != 0){
			return true;
		}
		return false;
	}
	
	public LaboratorioSetor getSetor() {
		return setor;
	}

	public void setSetor(LaboratorioSetor setor) {
		this.setor = setor;
	}

	public LaboratorioExame getExame() {
		return exame;
	}

	public void setExame(LaboratorioExame exame) {
		this.exame = exame;
	}

	public List<LaboratorioExame> getListaExames() {
		return listaExames;
	}

	public void setListaExames(List<LaboratorioExame> listaExames) {
		this.listaExames = listaExames;
	}

	public List<LaboratorioExame> getListaExamesOut() {
		return listaExamesOut;
	}

	public void setListaExamesOut(List<LaboratorioExame> listaExamesOut) {
		this.listaExamesOut = listaExamesOut;
	}

	public LaboratorioExame getExameAdd() {
		return exameAdd;
	}

	public void setExameAdd(LaboratorioExame exameAdd) {
		this.exameAdd = exameAdd;
	}

}
