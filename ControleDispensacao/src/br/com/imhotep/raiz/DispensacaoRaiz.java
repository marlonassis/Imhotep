package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import br.com.imhotep.entidade.Estoque;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.MovimentoLivro;
import br.com.imhotep.entidade.Prescricao;
import br.com.imhotep.entidade.PrescricaoItem;
import br.com.imhotep.entidade.PrescricaoItemDose;
import br.com.imhotep.entidade.PrescricaoItemEstoqueSaida;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.enums.TipoStatusEnum;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoHome;

@ManagedBean(name="dispensacaoRaiz")
@SessionScoped
public class DispensacaoRaiz extends PadraoHome<PrescricaoItem> {

	private Prescricao prescricao;
	private Integer quantidadeAjuste;
	private TipoMovimento tipoMovimentoAjuste;
	private String numeroPrescricao;
	private List<PrescricaoItemEstoqueSaida> listPrescricaoItemEstoqueSaida;
	private PrescricaoItemEstoqueSaida prescricaoItemEstoqueSaida;
	private TipoMovimento tipoMovimento;
	
	public void linhaSelecionada(SelectEvent selectEvent){
		prescricao = (Prescricao) selectEvent.getObject();
	}

	public List<PrescricaoItemEstoqueSaida> listaReciboPrescricao(){
		if(getPrescricao() != null){
			ConsultaGeral<PrescricaoItemEstoqueSaida> cg = new ConsultaGeral<PrescricaoItemEstoqueSaida>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", getPrescricao().getIdPrescricao());
			return new ArrayList<PrescricaoItemEstoqueSaida>(cg.consulta(new StringBuilder("select o from PrescricaoItemEstoqueSaida o where o.prescricaoItem.prescricao.idPrescricao = :idPrescricao"), hm));
		}
		return null;
	}
	
	public void limpaInstancia(){
		prescricao = new Prescricao();
	}
	
	public void novaPesquisa(){
		setListPrescricaoItemEstoqueSaida(null);
		numeroPrescricao = null;
	}
	
	public void ajustarEstoqueDispensado(){
		AjusteEstoqueRaiz aeh = new AjusteEstoqueRaiz();
//		aeh.setInstancia(prescricaoItemEstoqueSaida.getEstoque());
//		aeh.setMaterial(prescricaoItemEstoqueSaida.getEstoque().getMaterial());
		if(aeh.enviar()){
			try{
				if(tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.E)){
					prescricaoItemEstoqueSaida.setQuantidadeSaida(prescricaoItemEstoqueSaida.getQuantidadeSaida() - getQuantidadeAjuste());
				}else{
					prescricaoItemEstoqueSaida.setQuantidadeSaida(prescricaoItemEstoqueSaida.getQuantidadeSaida() + getQuantidadeAjuste());
				}
				iniciarTransacao();
				session.merge(prescricaoItemEstoqueSaida);
				String msg = "Ajuste de estoque realizada com sucesso!";
				finalizaTransacao(msg);
			}catch (Exception e) {
				String msg = "Ocorreu um erro ao ajustar o estoque.";
				catchTransacao(msg, e);
			}finally{
				finallyTransacao();
			}
			
		}
	}
	
	private List<PrescricaoItemEstoqueSaida> pesquisaPrescricaoItemEstoqueSaida(){
		ConsultaGeral<PrescricaoItemEstoqueSaida> cg = new ConsultaGeral<PrescricaoItemEstoqueSaida>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idPrescricao", Integer.valueOf(getNumeroPrescricao()));
		StringBuilder sb = new StringBuilder("select o from PrescricaoItemEstoqueSaida o where");
		sb.append(" o.prescricaoItem.prescricao.idPrescricao = :idPrescricao and o.prescricaoItem.dispensado = 'S'");
		return (List<PrescricaoItemEstoqueSaida>) cg.consulta(sb, hashMap);
	}
	
	public void carregaEstoqueEdicao(){
		setListPrescricaoItemEstoqueSaida(pesquisaPrescricaoItemEstoqueSaida());
		if(getListPrescricaoItemEstoqueSaida() == null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Não foi encontrado a prescrição.", ""));
		}
	}
	
	public void dispensar(){
		prescricao.setDispensado(true);
		prescricao.setDataDipensacao(new Date());
		PrescricaoRaiz ph = new PrescricaoRaiz();
		atualizaItens(prescricao);
		ph.setInstancia(prescricao);
		ph.atualizar();
	}
	
	private void atualizaItens(Prescricao prescricao) {
		for(PrescricaoItem prescricaoItem : prescricao.getPrescricaoItens()){
			prescricaoItem.setDispensado(TipoStatusEnum.S);
		}
	}

	public List<PrescricaoItemEstoqueSaida> listaPrescricaoItemEstoqueSaida(PrescricaoItem prescricaoItem){
		ConsultaGeral<PrescricaoItemEstoqueSaida> cg = new ConsultaGeral<PrescricaoItemEstoqueSaida>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idPrescricaoItem", prescricaoItem.getIdPrescricaoItem());
		StringBuilder sb = new StringBuilder("select o from PrescricaoItemEstoqueSaida o where");
		sb.append(" o.prescricaoItem.idPrescricaoItem = :idPrescricaoItem");
		Collection<PrescricaoItemEstoqueSaida> consulta = cg.consulta(sb, hashMap);
		if(consulta != null){
			return new ArrayList<PrescricaoItemEstoqueSaida>(consulta);
		}else{
			return null;
		}
	}
	
	private Integer saldoAnterior(PrescricaoItem prescricaoItem){
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idMaterial", prescricaoItem.getMaterial().getIdMaterial());
		StringBuilder sb = new StringBuilder("select sum(o.quantidade) from Estoque o where");
		sb.append(" o.material.idMaterial = :idMaterial and o.bloqueado = false");
		Long quantidadeAtual = cg.consultaUnica(sb, hashMap);
		return quantidadeAtual == null ? 0 : quantidadeAtual.intValue();
	}
	
	public Object[] consultaEstoque(Material material, Prescricao prescricao) {
		StringBuilder sb = new StringBuilder("select CASE WHEN sum(o.quantidade) = null THEN 0 ELSE sum(o.quantidade)END, ");
		sb.append("(select CASE WHEN sum(a.quantidade) = null THEN 0 ELSE sum(a.quantidade) END ");
		sb.append("from PrescricaoItemDose a where a.prescricaoItem.dispensado = 'N' and a.prescricaoItem.material.idMaterial = :idMaterial and a.prescricaoItem.prescricao.idPrescricao != :prescricao ) ");
		sb.append("from Estoque o where o.material.idMaterial = :idMaterial and o.bloqueado = false");
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("idMaterial", material.getIdMaterial());
		map.put("prescricao", prescricao.getIdPrescricao());
		
		ConsultaGeral<Object[]> cg = new ConsultaGeral<Object[]>();
		return cg.consultaUnica(sb, map);
	}
	
	private boolean liberaDose(PrescricaoItem prescricaoItem) {
		Object[] totais = consultaEstoque(prescricaoItem.getMaterial(), prescricao);
		Integer estoqueAtual = (Integer) totais[0] - (Integer) totais[1];
		Integer sobra = estoqueAtual - prescricaoItem.getQuantidadeLiberada();
		boolean b = sobra >= 0;
		
		if(!b){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Quantidade insufiente em estoque.", "A quantidade disponível em estoque é de ".concat(String.valueOf(estoqueAtual))));
			prescricaoItem.setQuantidadeLiberada(null);
		}
		
		return b;
	}
	
	public void editEvent(RowEditEvent obj){
		PrescricaoItem prescricaoItem = (PrescricaoItem) obj.getObject();
		if(prescricaoItem.getQuantidadeLiberada() != null){
			if(liberaDose(prescricaoItem)){
				try{
					iniciarTransacao();
					Integer saldoAnterior = saldoAnterior(prescricaoItem);
					prescricaoItem.setDispensado(TipoStatusEnum.S);
					session.merge(prescricaoItem);
					
					atualizaEstoque(prescricaoItem);
					
					geraMovimentoLivro(saldoAnterior, prescricaoItem.getQuantidadeLiberada(), prescricaoItem.getMaterial());
					String msg = "Dispensação realizada com sucesso.";
					finalizaTransacao(msg);
				}catch(Exception e){
					String msg = "Ocorreu um erro ao fazer a dispensação.";
					catchTransacao(msg, e);
				}finally{
					finallyTransacao();
				}
			}
		}
	}
	
	private void geraMovimentoLivro(Integer saldoAnterior, Integer quantidadeLiberada, Material material){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(new Date());
		try{
			movimentoLivroAtual.setUnidadeCadastrante(Autenticador.getInstancia().getUnidadeAtual());
			movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em DispensacaoHome");
		}
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		movimentoLivroAtual.setQuantidadeMovimentacao(quantidadeLiberada);
		session.save(movimentoLivroAtual);
	}
	
	private void atualizaEstoque(PrescricaoItem prescricaoIteml) {
		List<Estoque> list = new ArrayList<Estoque>();//new EstoqueRaiz().listaEstoqueMaterialDispensacao(null);
		Integer quantidadeLiberada = 0, cont = 0, sobra = 0;
		boolean atualizouEstoque = quantidadeLiberada == 0;
		while(!atualizouEstoque){
			Estoque estoque = list.get(cont);
			sobra = estoque.getQuantidadeAtual() - quantidadeLiberada;
			Integer quantidadeDispensada;
			if(sobra < 0){
				estoque.setQuantidadeAtual(0);
				sobra *= -1;
				quantidadeDispensada = quantidadeLiberada - sobra;
				quantidadeLiberada = sobra;
				cont++;
			}else{
				quantidadeDispensada = estoque.getQuantidadeAtual() - sobra; 
				estoque.setQuantidadeAtual(sobra);
				atualizouEstoque = true;
			}
			geraPrescricaoItemEstoqueSaida(prescricaoIteml, quantidadeDispensada, estoque);
			session.merge(estoque);
		}
	}

	private void geraPrescricaoItemEstoqueSaida(PrescricaoItem prescricaoItem, Integer valor, Estoque estoque) {
		PrescricaoItemEstoqueSaida pies = new PrescricaoItemEstoqueSaida();
		pies.setEstoque(estoque);
		pies.setPrescricaoItem(prescricaoItem);
		pies.setQuantidadeSaida(valor);
		session.save(pies);
	}

	public String quantidadeTotalDoses(Set<PrescricaoItemDose> prescricaoItens){
		if(prescricaoItens != null){
			Integer total = 0;
//			for(PrescricaoItemDose pid : prescricaoItens){
//				total += pid.getQuantidade();
//			}
			return String.valueOf(total);
		}
		return null;
	}
	
	public Prescricao getPrescricao() {
		return prescricao;
	}

	public void setPrescricao(Prescricao prescricao) {
		this.prescricao = prescricao;
	}

	public Integer getQuantidadeAjuste() {
		return quantidadeAjuste;
	}

	public void setQuantidadeAjuste(Integer quantidadeAjuste) {
		this.quantidadeAjuste = quantidadeAjuste;
	}

	public TipoMovimento getTipoMovimentoAjuste() {
		return tipoMovimentoAjuste;
	}

	public void setTipoMovimentoAjuste(TipoMovimento tipoMovimentoAjuste) {
		this.tipoMovimentoAjuste = tipoMovimentoAjuste;
	}

	public String getNumeroPrescricao() {
		return numeroPrescricao;
	}

	public void setNumeroPrescricao(String numeroPrescricao) {
		this.numeroPrescricao = numeroPrescricao;
	}

	public List<PrescricaoItemEstoqueSaida> getListPrescricaoItemEstoqueSaida() {
		return listPrescricaoItemEstoqueSaida;
	}

	public void setListPrescricaoItemEstoqueSaida(
			List<PrescricaoItemEstoqueSaida> listPrescricaoItemEstoqueSaida) {
		this.listPrescricaoItemEstoqueSaida = listPrescricaoItemEstoqueSaida;
	}

	public PrescricaoItemEstoqueSaida getPrescricaoItemEstoqueSaida() {
		return prescricaoItemEstoqueSaida;
	}

	public void setPrescricaoItemEstoqueSaida(PrescricaoItemEstoqueSaida prescricaoItemEstoqueSaida) {
		setQuantidadeAjuste(null);
		this.prescricaoItemEstoqueSaida = prescricaoItemEstoqueSaida;
	}

	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}

	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}


}
