package br.com.Imhotep.raiz;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.seguranca.Autenticador;
import br.com.imhotep.consulta.raiz.LoteExistenteConsultaRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="entradaMaterialRaiz")
@SessionScoped
public class EntradaMaterialRaiz extends PadraoHome<Estoque>{
	private Boolean loteEncontrado;

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
		loteEncontrado = new LoteExistenteConsultaRaiz().consultar(getInstancia().getLote());
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
		Date data = new Date();
		if(camposPreechidos()){
			try{
				iniciarTransacao();
				if(!existeEstoque(data)){
					session.save(getInstancia());
					geraMovimentoLivro(data);
					session.flush();  
					tx.commit(); 
					ret = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Entrada de material cadastrada com sucesso!", "Lote: ".concat(getInstancia().getLote()) ));
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
		if(getInstancia().getLote().isEmpty()){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Informe o lote.", null));
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
					}
				}
			}
		}
		return true;
	}

	private void geraMovimentoLivro(Date data){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(data);
		movimentoLivroAtual.setMaterial(getInstancia().getMaterial());
		movimentoLivroAtual.setSaldoAnterior(0);
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
	
	public Boolean getLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(Boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}
	
}
