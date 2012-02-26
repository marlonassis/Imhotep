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

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="ajusteEstoqueHome")
@SessionScoped
public class AjusteEstoqueHome extends PadraoHome<ItensMovimentoGeral>{
	//variáveis internas
	private Integer saldoAnterior;
	//
	//variáveis usadas no cadastro de ajuste
	private Estoque estoque;
	private List<Estoque> estoqueList;
	//
	
	@Override
	public void novaInstancia() {
		saldoAnterior = 0;
		estoque = null;
		setEstoqueList(null);
		super.novaInstancia();
		getInstancia().setMovimentoGeral(new MovimentoGeral());
	}
	
	public void carregaEstoqueMaterial(){
		Material material = getInstancia().getMaterial();
		if(material != null){
			setEstoqueList(new ArrayList<Estoque>(new EstoqueHome().listaEstoqueMaterial(material)));
		}
	}
	
	public AjusteEstoqueHome() {
		getInstancia().setMovimentoGeral(new MovimentoGeral());
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
	
	@Override
	public boolean enviar() {
		boolean ret=false;
		//verifica se o usuario está em uma unidade
		if(Autenticador.getInstancia().getUnidadeAtual() != null){
			//verifica se existe quantidade suficiente em estoque para uma perda ou saída 
			if(!getInstancia().getMovimentoGeral().getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Entrada) && estoque.getQuantidade() < getInstancia().getQuantidade()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esta é uma operação de saída ou perda. Informe uma quantidade que esteja no estoque.", "Ajuste não efetuado!"));
			}else{
				try{
					iniciarTransacao();
					geraMovimentoGeral();
					saldoAnterior = saldoAnterior();
					atualizarEstoque();
					carregaItem();
					session.persist(getInstancia());
					geraMovimentoLivro();
					session.flush();  
					tx.commit(); 
					ret = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Ajuste de estoque realizada com sucesso!", ""));
				}catch (Exception e) {
					e.printStackTrace();
					session.getTransaction().rollback();
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro ao cadastrar a entrada do material", e.getMessage()));
					getInstancia().setIdItensMovimentoGeral(0);
				}finally{
					session.close(); // Fecha sessão
					factory.close();
				}
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Você não está alocado em uma unidade", "Escolha uma unidade na combo acima do menu."));
		}
		return ret;
	}

	private void carregaItem() {
		getInstancia().setDataValidade(estoque.getDataValidade());
		getInstancia().setQuantidade(getInstancia().getQuantidade());
		getInstancia().setFabricante(estoque.getFabricante());
		getInstancia().setLote(estoque.getLote());
	}
	
	private void geraMovimentoLivro(){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(new Date());
		movimentoLivroAtual.setHistorico(getInstancia().getMovimentoGeral().getTipoMovimento().getDescricao());
		movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
		movimentoLivroAtual.setMovimentoGeral(getInstancia().getMovimentoGeral());
		movimentoLivroAtual.setTipoMovimento(getInstancia().getMovimentoGeral().getTipoMovimento());
		movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		
		if(movimentoLivroAtual.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Entrada)){
			movimentoLivroAtual.setQuantidadeEntrada(getInstancia().getQuantidade());
			movimentoLivroAtual.setSaldoAtual(getInstancia().getQuantidade() + saldoAnterior);
		}else{
			if(movimentoLivroAtual.getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Perda)){
				movimentoLivroAtual.setQuantidadePerda(getInstancia().getQuantidade());
			}else{
				movimentoLivroAtual.setQuantidadeSaida(getInstancia().getQuantidade());
			}
			movimentoLivroAtual.setSaldoAtual(saldoAnterior - getInstancia().getQuantidade());
		}
		
		session.save(movimentoLivroAtual);
	}

	private Integer saldoAnterior(){
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idMaterial", getInstancia().getMaterial().getIdMaterial());
		hashMap.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		StringBuilder sb = new StringBuilder("select sum(o.quantidade) from Estoque o where");
		sb.append(" o.material.idMaterial = :idMaterial");
		sb.append(" and o.unidade.idUnidade = :idUnidade");
		Long quantidadeAtual = cg.consultaUnica(sb, hashMap);
		return quantidadeAtual == null ? 0 : quantidadeAtual.intValue();
	}
	
	private void atualizarEstoque() {
		if(getInstancia().getMovimentoGeral().getTipoMovimento().getTipoOperacao().equals(TipoOperacaoEnum.Entrada)){
			estoque.setQuantidade(estoque.getQuantidade() + getInstancia().getQuantidade());
		}else{
			estoque.setQuantidade(estoque.getQuantidade() - getInstancia().getQuantidade());
		}
		session.merge(estoque);
	}
	
	private void geraMovimentoGeral() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(super.getIdSessao());
		getInstancia().getMovimentoGeral().setNumeroControle(chaveUnica);
		getInstancia().getMovimentoGeral().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
		getInstancia().getMovimentoGeral().setDataInclusao(new Date());
		getInstancia().getMovimentoGeral().setDataInclusao(new Date());
		getInstancia().getMovimentoGeral().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Collection<Estoque> getEstoqueList() {
		return estoqueList;
	}

	public void setEstoqueList(List<Estoque> estoqueList) {
		this.estoqueList = estoqueList;
	}

}
