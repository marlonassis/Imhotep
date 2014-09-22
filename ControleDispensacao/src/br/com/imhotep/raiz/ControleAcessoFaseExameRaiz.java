package br.com.imhotep.raiz;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.entidade.LaboratorioExameAutorizaProfissional;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoExameEnum;
import br.com.imhotep.enums.TipoAutorizacaoSolicitacaoItemExameEnum;
import br.com.imhotep.enums.TipoStatusAutorizaExameAcaoEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class ControleAcessoFaseExameRaiz extends PadraoRaiz<LaboratorioExameAutorizaProfissional>{
	
	private boolean exibirDialogAddProfissional;
	private TipoAutorizacaoSolicitacaoExameEnum[] fasesSolicitacao;
	private TipoAutorizacaoSolicitacaoItemExameEnum[] fasesSolicitacaoItem;
	private Profissional profissional;
	private Profissional profissionalUnidade;
	
	public String faseLinha(LaboratorioExameAutorizaProfissional linha){
		if(linha.getFaseSolicitacao() != null)
			return linha.getFaseSolicitacao().getLabel();
		else
			return linha.getFaseSolicitacaoItem().getLabel();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		setProfissional(null);
	}
	
	public void exibirDialogAddProfissional(){
		setExibirDialogAddProfissional(true);
	}
	
	public void ocultarDialogAddProfissional(){
		setExibirDialogAddProfissional(false);
	}
	
	public void removerProfissionalLaboratorio(){
		String sql = "delete from tb_autoriza_unidade_profissional where id_profissional = " + getProfissionalUnidade().getIdProfissional() + 
				" and id_unidade = " + Constantes.ID_UNIDADE_LABORATORIO;
		new LinhaMecanica("db_imhotep").executarCUD(sql);
		setProfissionalUnidade(null);
	}
	
	public void adicionarProfissionalLaboratorio(){
		Unidade unidade = new Unidade();
		unidade.setIdUnidade(Constantes.ID_UNIDADE_LABORATORIO);
		if(new AutorizaUnidadeProfissionalRaiz().enviar(getProfissionalUnidade(), unidade , true)){
			setProfissionalUnidade(null);
		}
	}
	
	public String getFaseString(TipoStatusAutorizaExameAcaoEnum[] fases){
		String res = "";
		for(TipoStatusAutorizaExameAcaoEnum item : fases){
			res += "'" + item.name() + "', ";
		}
		
		return res+"''";
	}
	
	private int getIdProfissionalLogado(){
		try {
			return Autenticador.getProfissionalLogado().getIdProfissional();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public boolean enviar() {
		setExibeMensagemInsercao(false);
		String sql = "delete from laboratorio.tb_laboratorio_exame_autoriza_profissional where id_profissional = " + getProfissional().getIdProfissional();
		LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
		lm.executarCUD(sql);
		adicionarAutorizacaoSolicitacao(lm);
		adicionarAutorizacaoSolicitacaoItem(lm);
		setProfissional(null);
		super.mensagem("Autorização realizada com sucesso", null, Constantes.INFO);
		return true;
	}

	private void adicionarAutorizacaoSolicitacaoItem(LinhaMecanica lm) {
		String sql;
		for(TipoAutorizacaoSolicitacaoItemExameEnum item : getFasesSolicitacaoItem()){
			sql = "INSERT INTO laboratorio.tb_laboratorio_exame_autoriza_profissional( "+
				  "          id_profissional, "+ 
				  "          tp_fase_solicitacao_item, dt_data_cadastro, id_profissional_cadastro) "+
				  "  VALUES ("+getProfissional().getIdProfissional()+",  "+
				  "          '"+item.name()+"', now(), "+getIdProfissionalLogado()+");";
			lm.executarCUD(sql);
		}
	}

	private void adicionarAutorizacaoSolicitacao(LinhaMecanica lm) {
		String sql;
		for(TipoAutorizacaoSolicitacaoExameEnum item : getFasesSolicitacao()){
			sql = "INSERT INTO laboratorio.tb_laboratorio_exame_autoriza_profissional( "+
				  "          id_profissional, "+ 
				  "          tp_fase_solicitacao, dt_data_cadastro, id_profissional_cadastro) "+
				  "  VALUES ("+getProfissional().getIdProfissional()+",  "+
				  "          '"+item.name()+"', now(), "+getIdProfissionalLogado()+");";
			lm.executarCUD(sql);
		}
	}
	
	public void carregarFasesAutorizadas() {
		if(getProfissional() != null){
			fasesAutorizadasSolicitacao();
			fasesAutorizadasSolicitacaoItem();
		}
	}

	private void fasesAutorizadasSolicitacao() {
		String hql = "select o.faseSolicitacao from LaboratorioExameAutorizaProfissional o where o.profissional.idProfissional = "+getProfissional().getIdProfissional();
		Collection<TipoAutorizacaoSolicitacaoExameEnum> consulta = new ConsultaGeral<TipoAutorizacaoSolicitacaoExameEnum>(new StringBuilder(hql)).consulta();
		fasesSolicitacao = consulta.toArray(new TipoAutorizacaoSolicitacaoExameEnum[0]);
	}

	private void fasesAutorizadasSolicitacaoItem() {
		String hql = "select o.faseSolicitacaoItem from LaboratorioExameAutorizaProfissional o where o.profissional.idProfissional = "+getProfissional().getIdProfissional();
		Collection<TipoAutorizacaoSolicitacaoItemExameEnum> consulta = new ConsultaGeral<TipoAutorizacaoSolicitacaoItemExameEnum>(new StringBuilder(hql)).consulta();
		fasesSolicitacaoItem = consulta.toArray(new TipoAutorizacaoSolicitacaoItemExameEnum[0]);
	}
	
	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public Profissional getProfissionalUnidade() {
		return profissionalUnidade;
	}

	public void setProfissionalUnidade(Profissional profissionalUnidade) {
		this.profissionalUnidade = profissionalUnidade;
	}

	public boolean isExibirDialogAddProfissional() {
		return exibirDialogAddProfissional;
	}

	public void setExibirDialogAddProfissional(boolean exibirDialogAddProfissional) {
		this.exibirDialogAddProfissional = exibirDialogAddProfissional;
	}

	public TipoAutorizacaoSolicitacaoItemExameEnum[] getFasesSolicitacaoItem() {
		return fasesSolicitacaoItem;
	}

	public void setFasesSolicitacaoItem(TipoAutorizacaoSolicitacaoItemExameEnum[] fasesSolicitacaoItem) {
		this.fasesSolicitacaoItem = fasesSolicitacaoItem;
	}

	public TipoAutorizacaoSolicitacaoExameEnum[] getFasesSolicitacao() {
		return fasesSolicitacao;
	}

	public void setFasesSolicitacao(TipoAutorizacaoSolicitacaoExameEnum[] fasesSolicitacao) {
		this.fasesSolicitacao = fasesSolicitacao;
	}
	
	
}
