package br.com.ControleDispensacao.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.EstoqueCentroCirurgico;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean(name="estoqueCentroCirurgicoHome")
@SessionScoped
public class EstoqueCentroCirurgicoHome extends PadraoHome<EstoqueCentroCirurgico> {
	private Estoque estoqueApoio = new Estoque();
	private EstoqueCentroCirurgico estoqueBloqueio = new EstoqueCentroCirurgico();
	
	public Estoque getEstoqueApoio() {
		return estoqueApoio;
	}

	public void setEstoqueApoio(Estoque estoqueApoio) {
		this.estoqueApoio = estoqueApoio;
	}
	
	private void atualizaEstoque(EstoqueCentroCirurgico estoqueCentroCirurgico) {
		setInstancia(estoqueCentroCirurgico);
		super.atualizar();
		setInstancia(new EstoqueCentroCirurgico());
	}
	
	@Override
	public void novaInstancia() {
		setEstoqueApoio(new Estoque());
		super.novaInstancia();
	}
	
	private boolean loteNaoEncontrado(String lote){
		List<EstoqueCentroCirurgico> objs = super.getBusca("select o from EstoqueCentroCirurgico o where o.bloqueado = 'N' and o.lote = '"+lote+"'");
		return objs.size() == 0;
	}
	
	public List<EstoqueCentroCirurgico> getEstoqueCentroCirurgicoList(){
		Set<EstoqueCentroCirurgico> set = new HashSet<EstoqueCentroCirurgico>(super.getBusca("select o from EstoqueCentroCirurgico o where o.bloqueado = 'N' order by o.material.descricao, o.lote, o.dataValidade"));
		return new ArrayList<EstoqueCentroCirurgico>(set);
	}
	
	
	@Override
	public boolean enviar() {
		if(loteNaoEncontrado(getEstoqueApoio().getLote())){
			inicializarInstancia(getInstancia());
			return super.enviar();
		}else{
			super.mensagem("Esse lote já está cadastrado.", "Faça uma busca no estoque e atualize-o.", FacesMessage.SEVERITY_ERROR);
		}
		return false;
	}

	private void inicializarInstancia(EstoqueCentroCirurgico estoqueCentroCirurgico) {
		estoqueCentroCirurgico.setBloqueado(TipoStatusEnum.N);
		estoqueCentroCirurgico.setDataValidade(getEstoqueApoio().getDataValidade());
		estoqueCentroCirurgico.setFabricante(getEstoqueApoio().getFabricante());
		estoqueCentroCirurgico.setLote(getEstoqueApoio().getLote());
		estoqueCentroCirurgico.setMaterial(getEstoqueApoio().getMaterial());
		estoqueCentroCirurgico.setDataCadastro(new Date());
		try {
			estoqueCentroCirurgico.setProfissionalCadastro(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional Atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EstoqueCentroCirurgico");
		}
	}
	
	public void liberarLote(EstoqueCentroCirurgico estoqueCentroCirurgico){
		estoqueCentroCirurgico.setBloqueado(TipoStatusEnum.N);
		estoqueCentroCirurgico.setMotivoBloqueio(null);
		atualizaEstoque(estoqueCentroCirurgico);
	}
	
	public void bloqueioLote(){
		if(getEstoqueBloqueio().getMotivoBloqueio().isEmpty()){
			super.mensagem("Informe o motivo do bloqueio", "O motivo é obrigatório.", FacesMessage.SEVERITY_ERROR);
		}else{
			processoAtualizacao();
		}
	}

	private void processoAtualizacao() {
		carregarDadosParaBloqueio(getEstoqueBloqueio());
		atualizaEstoque(getEstoqueBloqueio());
		setEstoqueBloqueio(new EstoqueCentroCirurgico());
	}

	private void carregarDadosParaBloqueio(EstoqueCentroCirurgico estoqueCentroCirurgico) {
		estoqueCentroCirurgico.setBloqueado(TipoStatusEnum.S);
		estoqueCentroCirurgico.setDataBloqueio(new Date());
		try {
			estoqueCentroCirurgico.setProfissionalBloqueio(Autenticador.getInstancia().getProfissionalAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao pegar o profissional Atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EstoqueCentroCirurgicoHome");
		}
	}

	public EstoqueCentroCirurgico getEstoqueBloqueio() {
		return estoqueBloqueio;
	}

	public void setEstoqueBloqueio(EstoqueCentroCirurgico estoqueBloqueio) {
		this.estoqueBloqueio = estoqueBloqueio;
	}

}
