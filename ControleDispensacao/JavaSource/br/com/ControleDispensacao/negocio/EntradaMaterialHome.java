package br.com.ControleDispensacao.negocio;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.entidade.Doacao;
import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.Hospital;
import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="entradaMaterialHome")
@SessionScoped
public class EntradaMaterialHome extends PadraoHome<Estoque>{
	private ItensMovimentoGeral itensMovimentoGeral;
	private boolean loteEncontrado;

	public void editRowEvent(RowEditEvent obj){
		Estoque estoque = (Estoque) obj.getObject();
		if(!achouEstoque(estoque.getLote())){
			setInstancia(estoque);
			super.atualizar();
		}
	}
	
	private boolean achouEstoque(String lote) {
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("lote", lote);
		StringBuilder sb = new StringBuilder("select o.lote from Estoque o where o.lote = :lote");
		String loteAchado = cg.consultaUnica(sb, hashMap);
		
		if(loteAchado != null && !loteAchado.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esse lote já foi cadastrado", "Atualização não efetuada."));
			return true;
		}
		return false;
	}

	public void procurarLote() throws IOException{
		Estoque estoque = existeLote();
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ControleDispensacao/PaginasWeb/Movimentacao/AjusteEstoque/ajusteEstoque.jsf?lote="+estoque.getLote());
		}
	}
	
	private Estoque existeLote() {
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("lote", getInstancia().getLote());
		StringBuilder sb = new StringBuilder("select o from Estoque o where o.lote = :lote");
		Estoque estoqueAtual = cg.consultaUnica(sb, hashMap);
		
		if(estoqueAtual != null){
			return estoqueAtual;
		}
		return null;
	}
	
	public EntradaMaterialHome() {
		setItensMovimentoGeral(new ItensMovimentoGeral());
		getItensMovimentoGeral().setMovimentoGeral(new MovimentoGeral());
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
		String numeroDocumento = getItensMovimentoGeral().getMovimentoGeral().getNumeroDocumento();
		super.novaInstancia();
		setItensMovimentoGeral(new ItensMovimentoGeral());
		getItensMovimentoGeral().setMovimentoGeral(new MovimentoGeral());
		getItensMovimentoGeral().getMovimentoGeral().setNumeroDocumento(numeroDocumento);
	}
	
	@Override
	public boolean enviar() {
		boolean ret=false;
		if(camposPreechidos() && carregaMovimentoGeral()){
			try{
				iniciarTransacao();
				if(!existeEstoque()){
					session.save(getInstancia());
					geraItensMovimentoGeral();
					geraMovimentoLivro();
					gerarDoacao();
					session.flush();  
					tx.commit(); 
					ret = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Entrada de material cadastrada com sucesso!", "Documento: ".concat(getItensMovimentoGeral().getMovimentoGeral().getNumeroDocumento()).concat(". Lote: ").concat(getInstancia().getLote()) ));
				}
			}catch (Exception e) {
				e.printStackTrace();
				session.getTransaction().rollback();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ocorreu um erro ao cadastrar a entrada do material", e.getMessage()));
				getInstancia().setIdEstoque(0);
			}finally{
				session.close(); // Fecha sessão
				factory.close();
			}
		}
		return ret;
	}
	
	private boolean camposPreechidos() {
		if(itensMovimentoGeral.getHospital() != null && itensMovimentoGeral.getDataDoacao() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe a data da doação.", null));
			return false;
		}
		if(getInstancia().getLote().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o lote.", null));
			return false;
		}else{
			if(itensMovimentoGeral.getMovimentoGeral().getNumeroDocumento().isEmpty()){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o número do documento.", null));
				return false;
			}else{
				if(getInstancia().getMaterial() == null){
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o material.", null));
					return false;
				}else{
					if(getInstancia().getFabricante() == null){
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o fabricante.", null));
						return false;
					}else{
						if(getInstancia().getDataValidade() == null){
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe a data de validade.", null));
							return false;
						}else{
							if(itensMovimentoGeral.getQuantidade() == 0 || itensMovimentoGeral.getQuantidade() == null){
								FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe a quantidade.", null));
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	private void geraItensMovimentoGeral() {
		getItensMovimentoGeral().setDataCriacao(new Date());
		getItensMovimentoGeral().setEstoque(getInstancia());
		session.save(getItensMovimentoGeral());
	}

	private void gerarDoacao() {
		Hospital hospital = getItensMovimentoGeral().getHospital();
		if(hospital != null){
			Date dataDoacao = getItensMovimentoGeral().getDataDoacao();
			Material material = getInstancia().getMaterial();
			Integer quantidade = getItensMovimentoGeral().getQuantidade();
			Doacao instancia = new DoacaoHome(dataDoacao, hospital, material, quantidade, TipoOperacaoEnum.Entrada).getInstancia();
			session.save(instancia);
		}
	}
	
	private void geraMovimentoLivro(){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(new Date());
		movimentoLivroAtual.setHistorico(getItensMovimentoGeral().getMovimentoGeral().getTipoMovimento().getDescricao());
		movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
		movimentoLivroAtual.setMovimentoGeral(getItensMovimentoGeral().getMovimentoGeral());
		movimentoLivroAtual.setQuantidadeEntrada(getItensMovimentoGeral().getQuantidade());
		movimentoLivroAtual.setSaldoAtual(getItensMovimentoGeral().getQuantidade());
		movimentoLivroAtual.setSaldoAnterior(0);
		movimentoLivroAtual.setTipoMovimento(getItensMovimentoGeral().getMovimentoGeral().getTipoMovimento());
		movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
		session.save(movimentoLivroAtual);
	}

	private boolean existeEstoque() {
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Houve uma tentativa de duplicação de lote!", "O lote ".concat(getInstancia().getLote().concat(" já foi cadastrado. Efetue um ajuste de estoque para poder atualizá-lo."))));
			return true;
		}else{
			estoqueAtual = new Estoque();
			estoqueAtual.setBloqueado(TipoStatusEnum.N);
			estoqueAtual.setDataValidade(getInstancia().getDataValidade());
			estoqueAtual.setFabricante(getInstancia().getFabricante());
			estoqueAtual.setLote(getInstancia().getLote());
			estoqueAtual.setMaterial(getInstancia().getMaterial());
			estoqueAtual.setQuantidade(getItensMovimentoGeral().getQuantidade());
			estoqueAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			estoqueAtual.setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			estoqueAtual.setDataInclusao(new Date());
			setInstancia(estoqueAtual);
		}
		return false;
	}
	
	private boolean carregaMovimentoGeral() {
		//procura algum movimento no banco de dados para associar ao item
		ConsultaGeral<MovimentoGeral> cg = new ConsultaGeral<MovimentoGeral>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("numeroDocumento", getItensMovimentoGeral().getMovimentoGeral().getNumeroDocumento());
		MovimentoGeral movimentoGeral = cg.consultaUnica(new StringBuilder("select o from MovimentoGeral o where o.numeroDocumento = :numeroDocumento"), hashMap);
		getItensMovimentoGeral().getMovimentoGeral().setIdMovimentoGeral(0);
		if(movimentoGeral == null){
			//caso não encontre um movimento geral será gerado um novo
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(getIdSessao());
			getItensMovimentoGeral().getMovimentoGeral().setNumeroControle(chaveUnica);
			getItensMovimentoGeral().getMovimentoGeral().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			getItensMovimentoGeral().getMovimentoGeral().setDataInclusao(new Date());
			getItensMovimentoGeral().getMovimentoGeral().setDataInclusao(new Date());
			getItensMovimentoGeral().getMovimentoGeral().setTipoMovimento(Parametro.tipoMovimentoEntrada());
			getItensMovimentoGeral().getMovimentoGeral().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		}else{
			//como foi encontrado um movimento geral então a operacao é de atualização do movimento geral
			getItensMovimentoGeral().setMovimentoGeral(movimentoGeral);
		}
		return true;
	}

	
	public ItensMovimentoGeral getItensMovimentoGeral() {
		return itensMovimentoGeral;
	}


	public void setItensMovimentoGeral(ItensMovimentoGeral itensMovimentoGeral) {
		this.itensMovimentoGeral = itensMovimentoGeral;
	}

	public boolean isLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}
	
}
