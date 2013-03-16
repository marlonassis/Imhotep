package br.com.Imhotep.raiz;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.Imhotep.auxiliar.Constantes;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.EstoqueLog;
import br.com.Imhotep.enums.TipoEstoqueLog;
import br.com.imhotep.consulta.raiz.DoacaoEstoqueConsultaRaiz;
import br.com.imhotep.consulta.raiz.EstoqueLoteConsultaRaiz;
import br.com.imhotep.consulta.raiz.MovimentoSaidaEstoqueConsultaRaiz;
import br.com.imhotep.consulta.raiz.NotaFiscalEstoqueConsultaRaiz;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.remendo.PadraoHome;

@ManagedBean(name="alterarApagarLoteRaiz")
@SessionScoped
public class AlterarApagarLoteRaiz extends PadraoHome<Estoque> {
	
	private boolean loteEncontrado;
	private boolean loteDuplicado;
	private Estoque estoqueDuplicado = new Estoque();
	private String loteAntigo;
	private Date dataAntiga;
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
	
	public void carregarEstoqueConsultaMaterial(Estoque estoque){
		loteEncontrado = true;
		setInstancia(estoque);
		setLoteAntigo(estoque.getLote());
		setDataAntiga(estoque.getDataValidade());
	}
	
	public void procurarLote(){
		Estoque estoque = new EstoqueLoteConsultaRaiz().consultar(loteAntigo);
		loteEncontrado = estoque != null;
		if(loteEncontrado){
			setInstancia(estoque);
			setDataAntiga(getInstancia().getDataValidade());
		}else{
			mensagem("Lote não encontrado.", loteAntigo, Constantes.WARN);
		}
	}
	
	public void cancelarFusao(){
		limparFusao();
	}

	private void limparFusao() {
		loteDuplicado=false;
		estoqueDuplicado = new Estoque();
	}
	
	public void apagarLote(){
		boolean existeMovimentoSaida = new MovimentoSaidaEstoqueConsultaRaiz().existeMovimentoSaida(getInstancia());
		boolean existeNotaFiscal = new NotaFiscalEstoqueConsultaRaiz().existeNotaFiscao(getInstancia());
		boolean existeDoacao = new DoacaoEstoqueConsultaRaiz().existeDoacao(getInstancia());
		tentarDeletarEstoque(existeMovimentoSaida, existeNotaFiscal, existeDoacao);
		mensagemVerificacaoLoteNaoDeletado(existeMovimentoSaida, existeNotaFiscal, existeDoacao);
	}

	
	private void tentarDeletarEstoque(boolean existeMovimentoSaida, boolean existeNotaFiscal, boolean existeDoacao) {
		if(!existeMovimentoSaida && !existeNotaFiscal && !existeDoacao){
			String hqlEstoque = "delete from Estoque o where o.idEstoque = "+getInstancia().getIdEstoque();
			if(new LinhaMecanica().apagarMovimentoLivroEstoque(getInstancia().getIdEstoque()))
				if(super.executa(hqlEstoque)>0){
					super.mensagem("Deleção realizada com sucesso.", null, Constantes.INFO);
					EstoqueLog log = EstoqueLogRaiz.carregarLog(new Date(), getInstancia().getLote(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.D, sdf.format(getInstancia().getDataValidade()));
					new EstoqueLogRaiz().gerarLog(log);
				}
				else
					super.mensagem("Não foi possível deletar.", null, Constantes.WARN);
			else
				super.mensagem("Não foi possível deletar.", null, Constantes.WARN);
		}
	}

	private void mensagemVerificacaoLoteNaoDeletado(boolean existeMovimentoSaida,
			boolean existeNotaFiscal, boolean existeDoacao) {
		if(existeNotaFiscal){
			super.mensagem("Esse lote está associado a alguma nota fiscal.", null, Constantes.ERROR);
			return;
		}
		if(existeDoacao){
			super.mensagem("Existem doações para esse lote.", "Não é permitido apagar algum lote que possua doação em seus registros.", Constantes.ERROR);
			return;
		}
		if(existeMovimentoSaida){
			super.mensagem("Esse lote tem movimentos de saída.", "Não é permitido apagar um lote que possua resgistros de saída.", Constantes.ERROR);
		}
	}
	
	public void fundirLotes(){
		if(new LinhaMecanica().fluxoFusaoEstoque(getInstancia().getIdEstoque(), getEstoqueDuplicado().getIdEstoque(), getInstancia().getMaterial().getIdMaterial())){
			Date data = new Date();
			EstoqueLog[] log = {EstoqueLogRaiz.carregarLog(data, getLoteAntigo(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.G, sdf.format(getInstancia().getDataValidade())),
			EstoqueLogRaiz.carregarLog(data, getEstoqueDuplicado().getLote(), getEstoqueDuplicado().getMaterial().getDescricao(), TipoEstoqueLog.F, sdf.format(getInstancia().getDataValidade()))};
			new EstoqueLogRaiz().gerarLog(log);
			
			mensagem("Fusão realizada com sucesso.", null, Constantes.INFO);
			setInstancia(getEstoqueDuplicado());
			limparFusao();
		}else{
			mensagem("Fusão não realizada.", null, Constantes.ERROR);
		}
		
	}
	
	private void limpar(){
		limparFusao();
		loteEncontrado=false;
		loteAntigo=null;
		dataAntiga=null;
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		limpar();
	}
	
	@Override
	public boolean atualizar() {
		setEstoqueDuplicado(new EstoqueLoteConsultaRaiz().consultar(getInstancia().getLote()));
		setLoteDuplicado(getEstoqueDuplicado() != null && getEstoqueDuplicado().getIdEstoque() != getInstancia().getIdEstoque());
		if(!isLoteDuplicado()){
			if(super.atualizar()){
				Date data = new Date();
				EstoqueLog[] log = {EstoqueLogRaiz.carregarLog(data, getInstancia().getLote(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.B, sdf.format(getInstancia().getDataValidade())),
				EstoqueLogRaiz.carregarLog(data, getLoteAntigo(), getInstancia().getMaterial().getDescricao(), TipoEstoqueLog.A, sdf.format(getDataAntiga()))};
				new EstoqueLogRaiz().gerarLog(log);
				return true;
			}
			else
				return false;
		}
		return false;
	}
	
	public boolean isLoteEncontrado() {
		return loteEncontrado;
	}

	public void setLoteEncontrado(boolean loteEncontrado) {
		this.loteEncontrado = loteEncontrado;
	}

	public String getLoteAntigo() {
		return loteAntigo;
	}

	public void setLoteAntigo(String loteAntigo) {
		this.loteAntigo = loteAntigo;
	}

	public boolean isLoteDuplicado() {
		return loteDuplicado;
	}

	public void setLoteDuplicado(boolean loteDuplicado) {
		this.loteDuplicado = loteDuplicado;
	}

	public Estoque getEstoqueDuplicado() {
		return estoqueDuplicado;
	}

	public void setEstoqueDuplicado(Estoque estoqueDuplicado) {
		this.estoqueDuplicado = estoqueDuplicado;
	}

	public Date getDataAntiga() {
		return dataAntiga;
	}

	public void setDataAntiga(Date dataAntiga) {
		this.dataAntiga = dataAntiga;
	}
	
}
