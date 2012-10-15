package br.com.ControleDispensacao.negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Doacao;
import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.Hospital;
import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;
import br.com.remendo.utilidades.Utilities;

@ManagedBean(name="ajusteEstoqueHome")
@SessionScoped
public class AjusteEstoqueHome extends PadraoHome<Estoque>{
	static final String AJUSTE_ESTOQUE_HOME = "ajusteEstoqueHome";
	//variáveis internas
	private Integer saldoAnterior;
	//
	//variáveis usadas no cadastro de ajuste
	private ItensMovimentoGeral itensMovimentoGeral;
	private List<Estoque> estoqueList;
	private Material material;
	//
	private boolean ajusteDispensacao = false;
	
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
		getItensMovimentoGeral().setEstoque(getInstancia());
		carregaEstoqueMaterial();
	}
	
	public static AjusteEstoqueHome getInstanciaHome() {
		try {
			return (AjusteEstoqueHome) Utilities.procuraInstancia(AjusteEstoqueHome.class);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void novaInstancia() {
		saldoAnterior = 0;
		itensMovimentoGeral = new ItensMovimentoGeral();
		setEstoqueList(null);
		super.novaInstancia();
		itensMovimentoGeral.setMovimentoGeral(new MovimentoGeral());
		material = null;
	}
	
	public void carregaEstoqueMaterial(){
		if(material != null){
			setEstoqueList(new ArrayList<Estoque>(new EstoqueHome().listaEstoqueMaterial(material)));
		}
	}
	
	public AjusteEstoqueHome() {
		itensMovimentoGeral = new ItensMovimentoGeral();
		itensMovimentoGeral.setMovimentoGeral(new MovimentoGeral());
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
		TipoOperacaoEnum tipoOperacao = itensMovimentoGeral.getMovimentoGeral().getTipoMovimento().getTipoOperacao();
		if(!tipoOperacao.equals(TipoOperacaoEnum.Entrada) && itensMovimentoGeral.getQuantidade() > getInstancia().getQuantidade()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esta é uma operação de saída ou perda. Informe uma quantidade que esteja no estoque.", "Ajuste não efetuado!"));
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean enviar() {
		boolean ret=false;
		//verifica se existe quantidade suficiente em estoque para uma perda ou saída 
		if(verificaQuantidadeTipoOperacao()){
			try{
				iniciarTransacao();
				geraMovimentoGeral();
				saldoAnterior = saldoAnterior();
				atualizarEstoque();
				carregaItem();
				session.merge(getInstancia());
				session.merge(itensMovimentoGeral);
				geraMovimentoLivro();
				gerarDoacao();
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

	private void gerarDoacao() {
		Hospital hospital = itensMovimentoGeral.getHospital();
		if(hospital != null){
			Date dataDoacao = itensMovimentoGeral.getDataDoacao();
			Material material = getInstancia().getMaterial();
			Integer quantidade = itensMovimentoGeral.getQuantidade();
			TipoOperacaoEnum tipoOperacao = itensMovimentoGeral.getMovimentoGeral().getTipoMovimento().getTipoOperacao();
			Doacao instancia = new DoacaoHome(dataDoacao, hospital, material, quantidade, tipoOperacao).getInstancia();
			session.save(instancia);
		}
	}

	private void carregaItem() {
		itensMovimentoGeral.setDataCriacao(new Date());
		itensMovimentoGeral.setEstoque(getInstancia());
	}
	
	private void geraMovimentoLivro(){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(new Date());
		movimentoLivroAtual.setHistorico(itensMovimentoGeral.getMovimentoGeral().getTipoMovimento().getDescricao());
		movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
		movimentoLivroAtual.setMovimentoGeral(itensMovimentoGeral.getMovimentoGeral());
		movimentoLivroAtual.setTipoMovimento(itensMovimentoGeral.getMovimentoGeral().getTipoMovimento());
		try {
			movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar a unidade atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em AjusteEstoqueHome");
		}
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		
		Integer quantidadeMovimentacao = getItensMovimentoGeral().getQuantidade();
		if(movimentoLivroAtual.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Entrada)){
			movimentoLivroAtual.setQuantidadeEntrada(quantidadeMovimentacao);
			movimentoLivroAtual.setSaldoAtual(quantidadeMovimentacao + saldoAnterior);
		}else{
			if(movimentoLivroAtual.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Perda)){
				movimentoLivroAtual.setQuantidadePerda(quantidadeMovimentacao);
			}else{
				movimentoLivroAtual.setQuantidadeSaida(quantidadeMovimentacao);
			}
			movimentoLivroAtual.setSaldoAtual(saldoAnterior - quantidadeMovimentacao);
		}
		
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
		TipoOperacaoEnum tipoOperacao = itensMovimentoGeral.getMovimentoGeral().getTipoMovimento().getTipoOperacao();
		if(tipoOperacao.equals(TipoOperacaoEnum.Entrada)){
			getInstancia().setQuantidade(getInstancia().getQuantidade() + itensMovimentoGeral.getQuantidade());
		}else{
			getInstancia().setQuantidade(getInstancia().getQuantidade() - itensMovimentoGeral.getQuantidade());
		}
	}
	
	private void geraMovimentoGeral() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String chaveUnica = null;
		try{
			chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(super.getIdSessao());
			itensMovimentoGeral.getMovimentoGeral().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			itensMovimentoGeral.getMovimentoGeral().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao acessar o autenticador.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em AjusteEstoqueHome");
		}
		itensMovimentoGeral.getMovimentoGeral().setNumeroControle(chaveUnica);
		itensMovimentoGeral.getMovimentoGeral().setDataInclusao(new Date());
		session.save(itensMovimentoGeral.getMovimentoGeral());
	}

	public Collection<Estoque> getEstoqueList() {
		return estoqueList;
	}

	public void setEstoqueList(List<Estoque> estoqueList) {
		this.estoqueList = estoqueList;
	}

	public ItensMovimentoGeral getItensMovimentoGeral() {
		return itensMovimentoGeral;
	}

	public void setItensMovimentoGeral(ItensMovimentoGeral itensMovimentoGeral) {
		this.itensMovimentoGeral = itensMovimentoGeral;
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

}
