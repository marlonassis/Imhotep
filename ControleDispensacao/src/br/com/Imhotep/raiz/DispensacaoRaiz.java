package br.com.Imhotep.raiz;

import java.text.SimpleDateFormat;
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

import br.com.Imhotep.auxiliar.Parametro;
import br.com.Imhotep.entidade.Estoque;
import br.com.Imhotep.entidade.ItensMovimentoGeral;
import br.com.Imhotep.entidade.Material;
import br.com.Imhotep.entidade.MovimentoGeral;
import br.com.Imhotep.entidade.MovimentoLivro;
import br.com.Imhotep.entidade.Prescricao;
import br.com.Imhotep.entidade.PrescricaoItem;
import br.com.Imhotep.entidade.PrescricaoItemDose;
import br.com.Imhotep.entidade.PrescricaoItemEstoqueSaida;
import br.com.Imhotep.entidade.TipoMovimento;
import br.com.Imhotep.enums.TipoOperacaoEnum;
import br.com.Imhotep.enums.TipoStatusEnum;
import br.com.Imhotep.seguranca.Autenticador;
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
		aeh.setInstancia(prescricaoItemEstoqueSaida.getEstoque());
		aeh.getItensMovimentoGeral().getMovimentoGeral().setTipoMovimento(tipoMovimento);
		aeh.getItensMovimentoGeral().getMovimentoGeral().setMotivo("Ajuste de dispensação");
		aeh.setMaterial(prescricaoItemEstoqueSaida.getEstoque().getMaterial());
		aeh.getItensMovimentoGeral().setQuantidade(quantidadeAjuste);
		if(aeh.enviar()){
			try{
				if(tipoMovimento.getTipoOperacao().equals(TipoOperacaoEnum.Entrada)){
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
		prescricao.setDispensado(TipoStatusEnum.S);
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
					MovimentoGeral movimentoGeral = null;
					
					movimentoGeral = geraMovimentoGeral();
					
					atualizaEstoque(prescricaoItem, movimentoGeral);
					
					geraMovimentoLivro(movimentoGeral, saldoAnterior, prescricaoItem.getQuantidadeLiberada(), prescricaoItem.getMaterial());
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
	
	private MovimentoGeral geraMovimentoGeral() {
		ConsultaGeral<MovimentoGeral> cg = new ConsultaGeral<MovimentoGeral>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idPrescricao", getPrescricao().getIdPrescricao());
		StringBuilder sb = new StringBuilder("select o from MovimentoGeral o where");
		sb.append(" o.prescricao.idPrescricao = :idPrescricao");
		MovimentoGeral movimentoGeral = cg.consultaUnica(sb, hashMap);
		
		if(movimentoGeral == null){
			movimentoGeral = new MovimentoGeral();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String chaveUnica = null;
			try{
				chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(super.getIdSessao());
				movimentoGeral.setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
				movimentoGeral.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em DispensacaoHome");
			}
			movimentoGeral.setNumeroControle(chaveUnica);
			movimentoGeral.setDataInclusao(new Date());
			movimentoGeral.setMotivo("Dispensação de medicamento.");
			movimentoGeral.setNumeroDocumento(chaveUnica);
			movimentoGeral.setPrescricao(prescricao);
			movimentoGeral.setTipoMovimento(Parametro.tipoMovimentoDispensacao());
			session.save(movimentoGeral);
		}
		
		return movimentoGeral;
	}
	
	private void geraMovimentoLivro(MovimentoGeral movimentoGeral, Integer saldoAnterior, Integer quantidadeLiberada, Material material){
		MovimentoLivro movimentoLivroAtual = new MovimentoLivro();
		movimentoLivroAtual.setDataMovimento(new Date());
		movimentoLivroAtual.setMaterial(material);
		movimentoLivroAtual.setMovimentoGeral(movimentoGeral);
		movimentoLivroAtual.setTipoMovimento(movimentoGeral.getTipoMovimento());
		try{
			movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
			movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
		} catch (Exception e) {
			e.printStackTrace();
			super.mensagem("Erro ao acessar o autenticator.", null, FacesMessage.SEVERITY_ERROR);
			System.out.print("Erro em DispensacaoHome");
		}
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		movimentoLivroAtual.setQuantidadeSaida(quantidadeLiberada);
		movimentoLivroAtual.setSaldoAtual(saldoAnterior - quantidadeLiberada);
		session.save(movimentoLivroAtual);
	}
	
	private void geraItensMovimentoGeral(PrescricaoItem prescricaoItem, Estoque estoque, MovimentoGeral movimentoGeral) {
		ItensMovimentoGeral img = new ItensMovimentoGeral();
		img.setDataCriacao(new Date());
		img.setEstoque(estoque);
		img.setMovimentoGeral(movimentoGeral);
		img.setPrescricaoItem(prescricaoItem);
		img.setQuantidade(prescricaoItem.getQuantidadeLiberada());
		session.merge(img);
	}

	private void atualizaEstoque(PrescricaoItem prescricaoItem, MovimentoGeral movimentoGeral) {
		List<Estoque> list = new EstoqueRaiz().listaEstoqueMaterialDispensacao(prescricaoItem.getMaterial());
		Integer quantidadeLiberada = prescricaoItem.getQuantidadeLiberada(), cont = 0, sobra = 0;
		boolean atualizouEstoque = quantidadeLiberada == 0;
		while(!atualizouEstoque){
			Estoque estoque = list.get(cont);
			sobra = estoque.getQuantidade() - quantidadeLiberada;
			Integer quantidadeDispensada;
			if(sobra < 0){
				estoque.setQuantidade(0);
				sobra *= -1;
				quantidadeDispensada = quantidadeLiberada - sobra;
				quantidadeLiberada = sobra;
				cont++;
			}else{
				quantidadeDispensada = estoque.getQuantidade() - sobra; 
				estoque.setQuantidade(sobra);
				atualizouEstoque = true;
			}
			geraPrescricaoItemEstoqueSaida(prescricaoItem, quantidadeDispensada, estoque);
			session.merge(estoque);
			geraItensMovimentoGeral(prescricaoItem, estoque, movimentoGeral);
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
			for(PrescricaoItemDose pid : prescricaoItens){
//				total += pid.getQuantidade();
			}
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
