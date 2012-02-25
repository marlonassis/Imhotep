package br.com.ControleDispensacao.negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.ControleDispensacao.entidade.TipoMovimento;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="entradaMaterialHome")
@SessionScoped
public class EntradaMaterialHome extends PadraoHome<ItensMovimentoGeral>{
	private Integer saldoAnterior;
	private Integer quantidadeEntrada;
	
	public EntradaMaterialHome() {
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
	public void novaInstancia() {
		String numeroDocumento = getInstancia().getMovimentoGeral().getNumeroDocumento();
		super.novaInstancia();
		getInstancia().setMovimentoGeral(new MovimentoGeral());
		getInstancia().getMovimentoGeral().setNumeroDocumento(numeroDocumento);
	}
	
	
	@Override
	public boolean enviar() {
		boolean ret=false;
		if(Autenticador.getInstancia().getUnidadeAtual() != null){
			//PRIMEIRO
			if(carregaMovimentoGeral()){
				try{
					//SEGUNDO
					iniciarTransacao();
					session.save(getInstancia());
					//TERCEIRO
					quantidadeEntrada = getInstancia().getQuantidade();
					saldoAnterior = saldoAnterior();
					geraEstoque();
					//SÉTIMO
					geraMovimentoLivro();
					session.flush();  
					tx.commit(); 
					ret = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Entrada de material cadastrada com sucesso!", "Documento: ".concat(getInstancia().getMovimentoGeral().getNumeroDocumento()).concat(". Lote: ").concat(getInstancia().getLote()) ));
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
	
	private void geraMovimentoLivro(){
		ConsultaGeral<MovimentoLivro> cg = new ConsultaGeral<MovimentoLivro>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idMovimentoGeral", getInstancia().getMovimentoGeral().getIdMovimentoGeral());
		hashMap.put("idMaterial", getInstancia().getMaterial().getIdMaterial());
		hashMap.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		StringBuilder sb = new StringBuilder("select o from MovimentoLivro o where");
		sb.append(" o.movimentoGeral.idMovimentoGeral = :idMovimentoGeral ");
		sb.append(" and o.unidade.idUnidade = :idUnidade ");
		sb.append(" and o.material.idMaterial = :idMaterial ");
		MovimentoLivro movimentoLivroAtual = cg.consultaUnica(sb, hashMap);
		if(movimentoLivroAtual != null){
			movimentoLivroAtual.setDataMovimento(new Date());
			movimentoLivroAtual.setQuantidadeEntrada(quantidadeEntrada);
			movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
			movimentoLivroAtual.setSaldoAtual(quantidadeEntrada + saldoAnterior);
			session.merge(movimentoLivroAtual);
		}else{
			movimentoLivroAtual = new MovimentoLivro();
			movimentoLivroAtual.setDataMovimento(new Date());
			movimentoLivroAtual.setHistorico(getInstancia().getMovimentoGeral().getTipoMovimento().getDescricao());
			movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
			movimentoLivroAtual.setMovimentoGeral(getInstancia().getMovimentoGeral());
			movimentoLivroAtual.setQuantidadeEntrada(getInstancia().getQuantidade());
			movimentoLivroAtual.setSaldoAtual(getInstancia().getQuantidade());
			movimentoLivroAtual.setTipoMovimento(getInstancia().getMovimentoGeral().getTipoMovimento());
			movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			session.save(movimentoLivroAtual);
		}
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
	
	private void geraEstoque() {
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idFabricante", getInstancia().getFabricante().getIdFabricante());
		hashMap.put("idMaterial", getInstancia().getMaterial().getIdMaterial());
		hashMap.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		hashMap.put("lote", getInstancia().getLote());
		StringBuilder sb = new StringBuilder("select o from Estoque o where o.fabricante.idFabricante = :idFabricante");
		sb.append(" and o.material.idMaterial = :idMaterial ");
		sb.append(" and o.unidade.idUnidade = :idUnidade ");
		sb.append(" and o.lote = :lote ");
		Estoque estoqueAtual = cg.consultaUnica(sb, hashMap);
		
		if(estoqueAtual != null){
			estoqueAtual.setQuantidade(getInstancia().getQuantidade() + estoqueAtual.getQuantidade());
			estoqueAtual.setUsuarioAlteracao(Autenticador.getInstancia().getUsuarioAtual());
			estoqueAtual.setDataAlteracao(new Date());
			session.merge(estoqueAtual);
		}else{
			estoqueAtual = new Estoque();
			estoqueAtual.setBloqueado(TipoStatusEnum.N);
			estoqueAtual.setDataValidade(getInstancia().getDataValidade());
			estoqueAtual.setFabricante(getInstancia().getFabricante());
			estoqueAtual.setLote(getInstancia().getLote());
			estoqueAtual.setMaterial(getInstancia().getMaterial());
			estoqueAtual.setQuantidade(getInstancia().getQuantidade());
			estoqueAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			estoqueAtual.setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			estoqueAtual.setDataInclusao(new Date());
			session.save(estoqueAtual);
		}
	}
	
	private boolean carregaMovimentoGeral() {
		//procura algum movimento no banco de dados para associar ao item
		ConsultaGeral<MovimentoGeral> cg = new ConsultaGeral<MovimentoGeral>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("numeroDocumento", getInstancia().getMovimentoGeral().getNumeroDocumento());
		MovimentoGeral movimentoGeral = cg.consultaUnica(new StringBuilder("select o from MovimentoGeral o where o.numeroDocumento = :numeroDocumento"), hashMap);
		getInstancia().getMovimentoGeral().setIdMovimentoGeral(0);
		if(movimentoGeral == null){
			//caso não encontre um movimento geral será gerado um novo
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(getIdSessao());
			getInstancia().getMovimentoGeral().setNumeroControle(chaveUnica);
			getInstancia().getMovimentoGeral().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getInstancia().getMovimentoGeral().setDataInclusao(new Date());
			getInstancia().getMovimentoGeral().setDataInclusao(new Date());
			getInstancia().getMovimentoGeral().setTipoMovimento(new TipoMovimento());
			getInstancia().getMovimentoGeral().getTipoMovimento().setIdTipoMovimento(1);
			getInstancia().getMovimentoGeral().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		}else{
			//como foi encontrado um movimento geral então a operacao é de atualização do movimento geral
			//somente é permitido inserir um lote se o usuário cadastrante é da mesma unidade do usuário que cadastrou o lote da primeira vez
			if(movimentoGeral.getUnidade().equals(Autenticador.getInstancia().getUnidadeAtual())){
				getInstancia().setMovimentoGeral(movimentoGeral);
			}else{
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esse documento já existe e você não é da unidade que o cadastrou.", "Apenas quem pode inserir estoque nesse documento é o usuário que for da unidade "+movimentoGeral.getUnidade().getNome()+"!"));
				return false;
			}
		}
		return true;
	}
	
}
