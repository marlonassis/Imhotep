package br.com.Imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.Unidade;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;
import br.com.remendo.utilidades.Utilities;

@ManagedBean(name="ajusteEstoqueRaiz")
@SessionScoped
public class AjusteEstoqueRaiz extends PadraoHome<Estoque>{
	static final String AJUSTE_ESTOQUE_HOME = "ajusteEstoqueHome";
	//variáveis internas
	private Integer saldoAnterior;
	//
	//variáveis usadas no cadastro de ajuste
	private List<Estoque> estoqueList;
	private Material material;
	//
	private boolean ajusteDispensacao = false;
	private Unidade unidade;
	
	private Estoque carregaEstoqueLote(String lote) {
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("lote", lote);
		StringBuilder sb = new StringBuilder("select o from Estoque o where o.lote = :lote");
		Estoque estoqueAtual = cg.consultaUnica(sb, hashMap);
		
		if(estoqueAtual != null){
			return estoqueAtual;
		}
		return null;
	}
	
	public void carregaEstoqueString(String lote){
		if(!lote.isEmpty()){
			carregaAjusteAutomatico(carregaEstoqueLote(lote));
		}
	}
	
	public void carregaAjusteAutomatico(Estoque estoque){
		setInstancia(estoque);
		material = getInstancia().getMaterial();
		carregaEstoqueMaterial();
	}
	
	public static AjusteEstoqueRaiz getInstanciaHome() {
		try {
			return (AjusteEstoqueRaiz) Utilities.procuraInstancia(AjusteEstoqueRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void novaInstancia() {
		saldoAnterior = 0;
		setEstoqueList(null);
		super.novaInstancia();
		material = null;
		unidade = null;
	}
	
	public void carregaEstoqueMaterial(){
		if(material != null){
			setEstoqueList(new ArrayList<Estoque>(new EstoqueRaiz().listaEstoqueMaterial(material)));
		}
	}
	
	@Override
	public boolean apagar() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não é permitido apagar uma entrada de material", "Deleção não autorizada."));
		return false;
	}
	
	@Override
	public boolean atualizar() {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não é permitido atualizar uma entrada de material", "Atualização não autorizada."));
		return false;
	}
	
	private boolean verificaQuantidadeTipoOperacao(){
		TipoOperacaoEnum tipoOperacao = null;
		if(!tipoOperacao.equals(TipoOperacaoEnum.Entrada) && 0 > getInstancia().getQuantidadeAtual()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esta é uma operação de saída ou perda. Informe uma quantidade que esteja no estoque.", "Ajuste não efetuado!"));
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean enviar() {
		boolean ret=false;
		Date data = new Date();
		//verifica se existe quantidade suficiente em estoque para uma perda ou saída 
		if(verificaQuantidadeTipoOperacao()){
			try{
				iniciarTransacao();
				saldoAnterior = saldoAnterior();
				atualizarEstoque();
				session.merge(getInstancia());
				geraMovimentoLivro(data);
				String msg = "Ajuste de estoque realizada com sucesso!";
				finalizaTransacao(msg);
				novaInstancia();
				ret = true;
			}catch (Exception e) {
				getInstancia().setIdEstoque(0);
				String msg = "Ocorreu um erro ao ajustar o estoque.";
				catchTransacao(msg, e);
			}finally{
				finallyTransacao();
			}
		}
		return ret;
	}

	private void geraMovimentoLivro(Date data){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(data);
		movimentoLivroAtual.setUnidadeReceptora(getUnidade());
		movimentoLivroAtual.setEstoque(getInstancia());
		try {
			movimentoLivroAtual.setUnidadeCadastrante(Autenticador.getInstancia().getUnidadeAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar a unidade atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em AjusteEstoqueHome");
		}
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		
		Integer quantidadeMovimentacao = 0;
		movimentoLivroAtual.setQuantidadeMovimentacao(quantidadeMovimentacao);
		
		try {
			movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o usuário atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em AjusteEstoqueHome");
		}
		
		session.save(movimentoLivroAtual);
	}

	private Integer saldoAnterior(){
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idMaterial", getInstancia().getMaterial().getIdMaterial());
		StringBuilder sb = new StringBuilder("select sum(o.quantidade) from Estoque o where");
		sb.append(" o.material.idMaterial = :idMaterial");
		Long quantidadeAtual = cg.consultaUnica(sb, hashMap);
		return quantidadeAtual == null ? 0 : quantidadeAtual.intValue();
	}
	
	private void atualizarEstoque() {
		TipoOperacaoEnum tipoOperacao = null;
		if(tipoOperacao.equals(TipoOperacaoEnum.Entrada)){
			getInstancia().setQuantidadeAtual(getInstancia().getQuantidadeAtual() + 0);
		}else{
			getInstancia().setQuantidadeAtual(getInstancia().getQuantidadeAtual() - 0);
		}
	}
	
	public Collection<Estoque> getEstoqueList() {
		return estoqueList;
	}

	public void setEstoqueList(List<Estoque> estoqueList) {
		this.estoqueList = estoqueList;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public boolean isAjusteDispensacao() {
		return ajusteDispensacao;
	}

	public void setAjusteDispensacao(boolean ajusteDispensacao) {
		this.ajusteDispensacao = ajusteDispensacao;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}
