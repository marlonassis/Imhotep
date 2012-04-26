package br.com.ControleDispensacao.negocio;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import br.com.ControleDispensacao.auxiliar.Parametro;
import br.com.ControleDispensacao.entidade.Estoque;
import br.com.ControleDispensacao.entidade.ItensMovimentoGeral;
import br.com.ControleDispensacao.entidade.Material;
import br.com.ControleDispensacao.entidade.MovimentoGeral;
import br.com.ControleDispensacao.entidade.MovimentoLivro;
import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.seguranca.Autenticador;
import br.com.nucleo.ConsultaGeral;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="dispensacaoHome")
@SessionScoped
public class DispensacaoHome extends PadraoHome<PrescricaoItem> {

	private Prescricao prescricao;
	
	public void linhaSelecionada(SelectEvent selectEvent){
		prescricao = (Prescricao) selectEvent.getObject();
	}
	
	public void limpaInstancia(){
		prescricao = new Prescricao();
	}
	
	private Integer saldoAnterior(PrescricaoItem prescricaoItem){
		ConsultaGeral<Long> cg = new ConsultaGeral<Long>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("idMaterial", prescricaoItem.getMaterial().getIdMaterial());
		StringBuilder sb = new StringBuilder("select sum(o.quantidade) from Estoque o where");
		sb.append(" o.material.idMaterial = :idMaterial and o.bloqueado = 'N'");
		Long quantidadeAtual = cg.consultaUnica(sb, hashMap);
		return quantidadeAtual == null ? 0 : quantidadeAtual.intValue();
	}
	
	public void editEvent(RowEditEvent obj){
		PrescricaoItem prescricaoItem = (PrescricaoItem) obj.getObject();
		if(prescricaoItem.getQuantidadeLiberada() != null){
			try{
				iniciarTransacao();
				Integer saldoAnterior = saldoAnterior(prescricaoItem);
				prescricaoItem.setDispensado(TipoStatusEnum.S);
				session.merge(prescricaoItem);
				MovimentoGeral movimentoGeral = geraMovimentoGeral();
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
			String chaveUnica = sdf.format(new Date()).concat(String.valueOf(Autenticador.getInstancia().getUnidadeAtual().getIdUnidade())).concat(super.getIdSessao());
			movimentoGeral.setNumeroControle(chaveUnica);
			movimentoGeral.setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			movimentoGeral.setDataInclusao(new Date());
			movimentoGeral.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
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
		movimentoLivroAtual.setUnidade(Autenticador.getInstancia().getUnidadeAtual());
		movimentoLivroAtual.setSaldoAnterior(saldoAnterior);
		movimentoLivroAtual.setQuantidadeSaida(quantidadeLiberada);
		movimentoLivroAtual.setSaldoAtual(saldoAnterior - quantidadeLiberada);
		movimentoLivroAtual.setUsuarioMovimentacao(Autenticador.getInstancia().getUsuarioAtual());
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
		List<Estoque> list = new EstoqueHome().listaEstoqueMaterial(prescricaoItem.getMaterial());
		Integer quantidadeLiberada = prescricaoItem.getQuantidadeLiberada(), cont = 0, sobra = 0;
		boolean atualizouEstoque = quantidadeLiberada == 0;
		while(!atualizouEstoque){
			Estoque estoque = list.get(cont);
			sobra = estoque.getQuantidade() - quantidadeLiberada;
			if(sobra < 0){
				estoque.setQuantidade(0);
				sobra *= -1;
				cont++;
			}else{
				estoque.setQuantidade(sobra);
				atualizouEstoque = true;
			}
			session.merge(estoque);
			geraItensMovimentoGeral(prescricaoItem, estoque, movimentoGeral);
		}
	}

	public String quantidadeTotalDoses(Set<PrescricaoItemDose> prescricaoItens){
		if(prescricaoItens != null){
			Integer total = 0;
			for(PrescricaoItemDose pid : prescricaoItens){
				total += pid.getQuantidade();
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

}
