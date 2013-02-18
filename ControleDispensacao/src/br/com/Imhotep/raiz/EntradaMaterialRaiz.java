package br.com.Imhotep.raiz;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.auxiliar.Parametro;
import br.com.Imhotep.entidade.Doacao;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.Hospital;
import br.com.Imhotep.entidade.ItensMovimentoGeral;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoGeral;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="entradaMaterialRaiz")
@SessionScoped
public class EntradaMaterialRaiz extends PadraoHome<Estoque>{
	private ItensMovimentoGeral itensMovimentoGeral;
	private boolean loteEncontrado;

	public void editRowEvent(RowEditEvent obj){
		Estoque estoque = (Estoque) obj.getObject();
		if(!achouEstoque(estoque.getLote(), estoque.getMaterial().getIdMaterial())){
			setInstancia(estoque);
			super.atualizar();
		}
	}
	
	private boolean achouEstoque(String lote, Integer idMaterial) {
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("lote", lote);
		StringBuilder sb = new StringBuilder("select o.lote from Estoque o where o.lote = :lote and o.material.idMaterial != " + idMaterial);
		String loteAchado = cg.consultaUnica(sb, hashMap);
		
		if(loteAchado != null && !loteAchado.isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Esse lote já foi cadastrado", "Atualização não efetuada."));
			return true;
		}
		return false;
	}

	public void procurarLote() throws IOException{
		Estoque estoque = existeLote(getInstancia().getLote());
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			FacesContext.getCurrentInstance().getExternalContext().redirect(Constantes.PAGINA_AJUSTE_ESTOQUE + "?lote="+estoque.getLote());
		}
	}
	
	private Estoque existeLote(String lote) {
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
	
	public EntradaMaterialRaiz() {
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
		Date data = new Date();
		if(camposPreechidos() && carregaMovimentoGeral(data)){
			try{
				iniciarTransacao();
				if(!existeEstoque(data)){
					session.save(getInstancia());
					geraItensMovimentoGeral(data);
					geraMovimentoLivro(data);
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
				super.finallyTransacao();
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

	private void geraItensMovimentoGeral(Date data) {
		getItensMovimentoGeral().setDataCriacao(data);
		getItensMovimentoGeral().setEstoque(getInstancia());
		session.save(getItensMovimentoGeral());
	}

	private void gerarDoacao() {
		Hospital hospital = getItensMovimentoGeral().getHospital();
		if(hospital != null){
			Date dataDoacao = getItensMovimentoGeral().getDataDoacao();
			Material material = getInstancia().getMaterial();
			Integer quantidade = getItensMovimentoGeral().getQuantidade();
			Doacao instancia = new DoacaoRaiz(dataDoacao, hospital, material, quantidade, TipoOperacaoEnum.Entrada).getInstancia();
			session.save(instancia);
		}
	}
	
	private void geraMovimentoLivro(Date data){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(data);
		movimentoLivroAtual.setHistorico(getItensMovimentoGeral().getMovimentoGeral().getTipoMovimento().getDescricao());
		movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
		movimentoLivroAtual.setMovimentoGeral(getItensMovimentoGeral().getMovimentoGeral());
		movimentoLivroAtual.setQuantidadeEntrada(getItensMovimentoGeral().getQuantidade());
		movimentoLivroAtual.setSaldoAtual(getItensMovimentoGeral().getQuantidade());
		movimentoLivroAtual.setSaldoAnterior(0);
		movimentoLivroAtual.setTipoMovimento(getItensMovimentoGeral().getMovimentoGeral().getTipoMovimento());
		movimentoLivroAtual.setEstoque(getInstancia());
		try{
			movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EntradaMaterialHome");
		}
		session.save(movimentoLivroAtual);
	}

	private boolean existeEstoque(Date data) {
		ConsultaGeral<Estoque> cg = new ConsultaGeral<Estoque>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idFabricante", getInstancia().getFabricante().getIdFabricante());
		hashMap.put("idMaterial", getInstancia().getMaterial().getIdMaterial());
		try {
			hashMap.put("idUnidade", Autenticador.getInstancia().getUnidadeAtual().getIdUnidade());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao acessar o pegar a unidade atual.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em EntradaMaterialHome");
		}
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
			estoqueAtual.setBloqueado(false);
			estoqueAtual.setDataValidade(getInstancia().getDataValidade());
			estoqueAtual.setFabricante(getInstancia().getFabricante());
			estoqueAtual.setLote(getInstancia().getLote());
			estoqueAtual.setMaterial(getInstancia().getMaterial());
			estoqueAtual.setQuantidade(getItensMovimentoGeral().getQuantidade());
			try{
				estoqueAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
				estoqueAtual.setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em EntradaMaterialHome");
			}
			estoqueAtual.setDataInclusao(data);
			estoqueAtual.setFornecedor(getInstancia().getFornecedor());
			estoqueAtual.setValorUnitario(getInstancia().getValorUnitario());
			setInstancia(estoqueAtual);
		}
		return false;
	}
	
	private boolean carregaMovimentoGeral(Date data) {
		//procura algum movimento no banco de dados para associar ao item
		ConsultaGeral<MovimentoGeral> cg = new ConsultaGeral<MovimentoGeral>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("numeroDocumento", getItensMovimentoGeral().getMovimentoGeral().getNumeroDocumento());
		MovimentoGeral movimentoGeral = cg.consultaUnica(new StringBuilder("select o from MovimentoGeral o where o.numeroDocumento = :numeroDocumento"), hashMap);
		getItensMovimentoGeral().getMovimentoGeral().setIdMovimentoGeral(0);
		if(movimentoGeral == null){
			//caso não encontre um movimento geral será gerado um novo
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String chaveUnica = null;
			try{
				chaveUnica = sdf.format(data).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(getIdSessao());
				getItensMovimentoGeral().getMovimentoGeral().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
				getItensMovimentoGeral().getMovimentoGeral().setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em EntradaMaterialHome");
			}
			getItensMovimentoGeral().getMovimentoGeral().setNumeroControle(chaveUnica);
			getItensMovimentoGeral().getMovimentoGeral().setDataInclusao(data);
			getItensMovimentoGeral().getMovimentoGeral().setTipoMovimento(Parametro.tipoMovimentoEntrada());
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
