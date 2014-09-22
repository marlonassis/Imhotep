	package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.controle.ControlePainelAviso;
import br.com.imhotep.entidade.PainelAviso;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidade;
import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;
import br.com.imhotep.enums.TipoStatusDispensacaoEnum;
import br.com.imhotep.enums.TipoStatusSolicitacaoItemEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueAlmoxarifadoVazio;
import br.com.imhotep.excecoes.ExcecaoEstoqueReservado;
import br.com.imhotep.excecoes.ExcecaoEstoqueSaldoInsuficiente;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.excecoes.ExcecaoQuantidadeZero;
import br.com.imhotep.excecoes.ExcecaoSolicitacaoItemInseridaDuasVezes;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class SolicitacaoMaterialAlmoxarifadoUnidadeSolicitacaoRaiz extends PadraoRaiz<SolicitacaoMaterialAlmoxarifadoUnidade>{
	
	private Boolean exibirModalAlterarQuantidadeItem = false;
	private SolicitacaoMaterialAlmoxarifadoUnidadeItem itemSolicitacao = new SolicitacaoMaterialAlmoxarifadoUnidadeItem();
	private Integer quantidadeAlteracaoItem;
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setDataInsercao(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.A);
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	public boolean atualizar() {
		if(getInstancia().getItens() != null && getInstancia().getItens().size() > 0){
			getInstancia().setDataFechamento(new Date());
			getInstancia().setStatusDispensacao(TipoStatusDispensacaoEnum.P);
			if(super.atualizar()){
				ControlePainelAviso.getInstancia().setAvisosNaoMonitorado(new ArrayList<PainelAviso>());
				ControlePainelAviso.getInstancia().gerarAvisoRM(getInstancia().getIdSolicitacaoMaterialAlmoxarifadoUnidade(), getInstancia().getUnidadeDestino());
				super.novaInstancia();
				itemSolicitacao = new SolicitacaoMaterialAlmoxarifadoUnidadeItem();
				return true;
			}
		}
		super.mensagem("Adicione algum material", "", Constantes.WARN);
		return false;
	}
	
	public void exibirModalAlterarItemAdicionado(){
		setQuantidadeAlteracaoItem(null);
		setExibirModalAlterarQuantidadeItem(true);
	}
	
	public void cancelarQuantidadeSolicitacaoAberta(){
		setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
		setQuantidadeAlteracaoItem(null);
		setExibirModalAlterarQuantidadeItem(false);
	}
	
	public void apagarItemSolicitacaoAberta(){
		if(new SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(getItemSolicitacao()).apagar()){
			getInstancia().getItens().remove(getItemSolicitacao());
			setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
		}
	}
	
	public void alterarQuantidadeSolicitacaoAberta(){
		try {
			new ControleEstoqueAlmoxarifadoTemp().liberarSolicitacao(getQuantidadeAlteracaoItem(), getItemSolicitacao().getMaterialAlmoxarifado(), getInstancia());
			getItemSolicitacao().setQuantidadeSolicitada(getQuantidadeAlteracaoItem());
			if(new SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(getItemSolicitacao()).atualizar()){
				setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
				setQuantidadeAlteracaoItem(null);
				setExibirModalAlterarQuantidadeItem(false);
			}
		} catch (ExcecaoEstoqueSaldoInsuficiente e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueReservado e) {
			e.printStackTrace();
		} catch (ExcecaoQuantidadeZero e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueAlmoxarifadoVazio e) {
			e.printStackTrace();
		}
	}
	
	public void addItem(){
		try {
			if(getInstancia().getItens() == null){
				getInstancia().setItens(new ArrayList<SolicitacaoMaterialAlmoxarifadoUnidadeItem>());
			}
			verificarItemDuplicado();
			new ControleEstoqueAlmoxarifadoTemp().liberarSolicitacao(getItemSolicitacao().getQuantidadeSolicitada(), getItemSolicitacao().getMaterialAlmoxarifado(), getInstancia());
			getItemSolicitacao().setStatusItem(TipoStatusSolicitacaoItemEnum.P);
			getItemSolicitacao().setSolicitacaoMaterialAlmoxarifadoUnidade(getInstancia());
			if(new SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(getItemSolicitacao()).enviar()){
				getInstancia().getItens().add(getItemSolicitacao());
				setItemSolicitacao(new SolicitacaoMaterialAlmoxarifadoUnidadeItem());
			}
		} catch (ExcecaoEstoqueSaldoInsuficiente e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueReservado e) {
			e.printStackTrace();
		} catch (ExcecaoQuantidadeZero e) {
			e.printStackTrace();
		} catch (ExcecaoEstoqueAlmoxarifadoVazio e) {
			e.printStackTrace();
		} catch (ExcecaoSolicitacaoItemInseridaDuasVezes e) {
			e.printStackTrace();
		}
	}
	
	private void verificarItemDuplicado() throws ExcecaoSolicitacaoItemInseridaDuasVezes {
		for(SolicitacaoMaterialAlmoxarifadoUnidadeItem item : getInstancia().getItens()){
			if(getItemSolicitacao().getMaterialAlmoxarifado().getIdMaterialAlmoxarifado() == item.getMaterialAlmoxarifado().getIdMaterialAlmoxarifado()){
				throw new ExcecaoSolicitacaoItemInseridaDuasVezes();
			}
		}
		
	}

	public static SolicitacaoMaterialAlmoxarifadoUnidadeSolicitacaoRaiz getInstanciaAtual(){
		try {
			return (SolicitacaoMaterialAlmoxarifadoUnidadeSolicitacaoRaiz) Utilitarios.procuraInstancia(SolicitacaoMaterialAlmoxarifadoUnidadeSolicitacaoRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SolicitacaoMaterialAlmoxarifadoUnidadeItem getItemSolicitacao() {
		return itemSolicitacao;
	}

	public void setItemSolicitacao(SolicitacaoMaterialAlmoxarifadoUnidadeItem itemSolicitacao) {
		this.itemSolicitacao = itemSolicitacao;
	}

	public Boolean getExibirModalAlterarQuantidadeItem() {
		return exibirModalAlterarQuantidadeItem;
	}

	public void setExibirModalAlterarQuantidadeItem(
			Boolean exibirModalAlterarQuantidadeItem) {
		this.exibirModalAlterarQuantidadeItem = exibirModalAlterarQuantidadeItem;
	}

	public Integer getQuantidadeAlteracaoItem() {
		return quantidadeAlteracaoItem;
	}

	public void setQuantidadeAlteracaoItem(Integer quantidadeAlteracaoItem) {
		this.quantidadeAlteracaoItem = quantidadeAlteracaoItem;
	}

}
