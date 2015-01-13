package br.com.imhotep.raiz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.controle.ControleEstoqueAlmoxarifadoTemp;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.imhotep.entidade.InventarioAlmoxarifadoContagem;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.MovimentoLivroAlmoxarifado;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.enums.TipoStatusInventarioEnum;
import br.com.imhotep.excecoes.ExcecaoEstoqueLock;
import br.com.imhotep.excecoes.ExcecaoEstoqueUnLock;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioAlmoxarifadoFinalizacaoRaiz extends PadraoRaiz<InventarioAlmoxarifadoContagem>{
	private static final int QUANTIDADE_MINIMA_CONTAGEM = 1;
	private InventarioAlmoxarifado inventarioAlmoxarifado;
	private List<InventarioAlmoxarifadoContagem> itensContadosMaisUmaVez = new ArrayList<InventarioAlmoxarifadoContagem>();
	private List<InventarioAlmoxarifadoContagem> itensInventariados = new ArrayList<InventarioAlmoxarifadoContagem>();
	private boolean exibirDialogCorrecaoItemContadoVariasVezes = false;
	private boolean exibirDialogConferencia = false;
	private MaterialAlmoxarifado materialCorrecao;
	
	public void ocultarDialogCorrecaoItemContadoVariasVezes(){
		setExibirDialogCorrecaoItemContadoVariasVezes(false);
	}
	
	public void ocultarDialogConferencia(){
		setExibirDialogConferencia(false);
		itensContadosMaisUmaVez = new ArrayList<InventarioAlmoxarifadoContagem>();
		itensInventariados = new ArrayList<InventarioAlmoxarifadoContagem>();
		materialCorrecao = null;
	}
	
	public void removerItemContado(InventarioAlmoxarifadoContagem item){
		if(super.apagarGenerico(item)){
			if(getItensContadosMaisUmaVez().remove(item) && getItensContadosMaisUmaVez().size() == QUANTIDADE_MINIMA_CONTAGEM)
				preInicioLiberacao();
		}
	}
	
	public void preInicioLiberacao(){
		carregarItensContadosMaisDeUmaVez(getInventarioAlmoxarifado());
		if(getItensContadosMaisUmaVez().size() > 0){
			setExibirDialogCorrecaoItemContadoVariasVezes(true);
		}else{
			setExibirDialogCorrecaoItemContadoVariasVezes(false);
			carregarItensInventariados();
			setExibirDialogConferencia(true);
		}
	}

	private void carregarItensInventariados() {
		setExibirDialogConferencia(true);
		StringBuilder sb = new StringBuilder();
		sb.append("select o from InventarioAlmoxarifadoContagem o where ");
		sb.append("o.inventarioMaterialEstoque.inventarioMaterial.inventarioAlmoxarifado.idInventarioAlmoxarifado = ");
		sb.append(getInventarioAlmoxarifado().getIdInventarioAlmoxarifado());
		sb.append(" order by lower(to_ascii(o.inventarioMaterialEstoque.inventarioMaterial.material.descricao))");
		setItensInventariados(new ArrayList<InventarioAlmoxarifadoContagem>(new ConsultaGeral<InventarioAlmoxarifadoContagem>(sb).consulta()));
	}
	
	public void finalizarInventario(){
		List<EstoqueAlmoxarifado> estoquesLock = new ArrayList<EstoqueAlmoxarifado>();
		try {
			carregarItensInventariados();
			PadraoFluxoTemp.limparFluxo();
			final int VALORES_IGUAIS = 0;
			final int PRIMEIRO_VALOR_MAIOR = 1;
			final int PRIMEIRO_VALOR_MENOR = -1;
			TipoMovimentoAlmoxarifado tipoMovimentoInventarioEntradaAlmoxarifado = Parametro.tipoMovimentoInventarioEntradaAlmoxarifado();
			TipoMovimentoAlmoxarifado tipoMovimentoInventarioSaidaAlmoxarifado = Parametro.tipoMovimentoInventarioSaidaAlmoxarifado();
			Date dataAtual = new Date();
			int idInventarioAlmoxarifado = getInventarioAlmoxarifado().getIdInventarioAlmoxarifado();
			for(InventarioAlmoxarifadoContagem item : getItensInventariados()){
				item.getInventarioMaterialEstoque().getEstoque().setQuantidadeAtual(
						new EstoqueAlmoxarifadoConsultaRaiz().saldoRetroativo(
								item.getInventarioMaterialEstoque().getEstoque(), dataAtual));
				TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifado = null;
				Double quantidadeMovimentada = null;
				Double quantidadeAtual = item.getInventarioMaterialEstoque().getEstoque().getQuantidadeAtual();
				int valorComparacao = item.getQuantidadeContada().compareTo(quantidadeAtual);
				if(valorComparacao == VALORES_IGUAIS){
					continue;
				}else{
					if(valorComparacao == PRIMEIRO_VALOR_MAIOR){
						tipoMovimentoAlmoxarifado = tipoMovimentoInventarioEntradaAlmoxarifado;
						quantidadeMovimentada = item.getQuantidadeContada().doubleValue() - quantidadeAtual.doubleValue();
					}else{
						if(valorComparacao == PRIMEIRO_VALOR_MENOR){
							tipoMovimentoAlmoxarifado = tipoMovimentoInventarioSaidaAlmoxarifado;
							quantidadeMovimentada = quantidadeAtual.doubleValue() - item.getQuantidadeContada().doubleValue();
						}
					}
				}
				EstoqueAlmoxarifado estoqueAlmoxarifado = item.getInventarioMaterialEstoque().getEstoque();
				String justificativa = "Inventário: " + idInventarioAlmoxarifado;
				estoquesLock.add(estoqueAlmoxarifado);
				MovimentoLivroAlmoxarifado mla = new ControleEstoqueAlmoxarifadoTemp().getMovimentoLivroPronto(dataAtual, estoqueAlmoxarifado , tipoMovimentoAlmoxarifado, 
																												justificativa , quantidadeMovimentada);
				item.getInventarioMaterialEstoque().setMovimentoLivroAlmoxarifado(mla);
				PadraoFluxoTemp.getObjetoSalvar().put("MLA - " + mla.hashCode(), mla);
				PadraoFluxoTemp.getObjetoAtualizar().put("EA - " + mla.getEstoqueAlmoxarifado().hashCode(), mla.getEstoqueAlmoxarifado());
				PadraoFluxoTemp.getObjetoAtualizar().put("IME - " + item.getInventarioMaterialEstoque().hashCode(), item.getInventarioMaterialEstoque());
			}
			getInventarioAlmoxarifado().setDataFinalizacao(dataAtual);
			getInventarioAlmoxarifado().setProfissionalFinalizacao(Autenticador.getProfissionalLogado());
			getInventarioAlmoxarifado().setTipoStatus(TipoStatusInventarioEnum.F);
			PadraoFluxoTemp.getObjetoAtualizar().put("IA - " + getInventarioAlmoxarifado().hashCode(), getInventarioAlmoxarifado());
			
			PadraoFluxoTemp.finalizarFluxo();
			
		} catch (ExcecaoEstoqueLock e) {
			e.printStackTrace();
			getInventarioAlmoxarifado().setDataFinalizacao(null);
			getInventarioAlmoxarifado().setProfissionalFinalizacao(null);
			getInventarioAlmoxarifado().setTipoStatus(null);
			removerLockLotes(estoquesLock, estoquesLock.size() - 1);
		} catch (Exception e) {
			e.printStackTrace();
			getInventarioAlmoxarifado().setDataFinalizacao(null);
			getInventarioAlmoxarifado().setProfissionalFinalizacao(null);
			getInventarioAlmoxarifado().setTipoStatus(null);
			removerLockLotes(estoquesLock, estoquesLock.size());
		}
		PadraoFluxoTemp.limparFluxo();
		ocultarDialogConferencia();
	}
	
	private void removerLockLotes(List<EstoqueAlmoxarifado> estoquesLock, int qtdItens) {
		for(int i = 0; i < qtdItens; i++){
			try {
				EstoqueAlmoxarifado item = estoquesLock.get(i);
				new ControleEstoqueAlmoxarifadoTemp().unLockEstoque(item);
			} catch (ExcecaoEstoqueUnLock e) {
				e.printStackTrace();
			}
		}
	}
	
	private void carregarItensContadosMaisDeUmaVez(InventarioAlmoxarifado inventarioAlmoxarifado){
		setMaterialCorrecao(null);
		setItensContadosMaisUmaVez(new ArrayList<InventarioAlmoxarifadoContagem>());
		String sql = getSqlItensContadosMaisDeUmaVez(inventarioAlmoxarifado);
		LinhaMecanica lm = new LinhaMecanica();
		ResultSet rs = lm.consultar(sql);
		try {
			if(rs.next()){
				int idIM = rs.getInt("id_inventario_material");
				StringBuilder sb = new StringBuilder();
				sb.append("select o from InventarioAlmoxarifadoContagem o where o.inventarioMaterialEstoque.inventarioMaterial.idInventarioMaterial = ");
				sb.append(idIM);
				setItensContadosMaisUmaVez(new ArrayList<InventarioAlmoxarifadoContagem>(new ConsultaGeral<InventarioAlmoxarifadoContagem>(sb).consulta()));
				setMaterialCorrecao(getItensContadosMaisUmaVez().get(0).getInventarioMaterialEstoque().getEstoque().getMaterialAlmoxarifado());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getSqlItensContadosMaisDeUmaVez(InventarioAlmoxarifado inventarioAlmoxarifado){
		StringBuilder sb = new StringBuilder();
		sb.append("select c.id_inventario_material, count(a.id_inventario_material_estoque) from almoxarifado.tb_inventario_contagem a ");
		sb.append("inner join almoxarifado.tb_inventario_material_estoque b on a.id_inventario_material_estoque = b.id_inventario_material_estoque ");
		sb.append("inner join almoxarifado.tb_inventario_material c on c.id_inventario_material = b.id_inventario_material ");
		sb.append("where c.id_inventario = ");
		sb.append(inventarioAlmoxarifado.getIdInventarioAlmoxarifado());
		sb.append(" group by c.id_inventario_material, a.id_inventario_material_estoque ");
		sb.append("having count(a.id_inventario_material_estoque) > 1 ");
		return sb.toString();
	}
	
	public InventarioAlmoxarifado getInventarioAlmoxarifado() {
		return inventarioAlmoxarifado;
	}

	public void setInventarioAlmoxarifado(InventarioAlmoxarifado inventarioAlmoxarifado) {
		this.inventarioAlmoxarifado = inventarioAlmoxarifado;
	}

	public List<InventarioAlmoxarifadoContagem> getItensContadosMaisUmaVez() {
		return itensContadosMaisUmaVez;
	}

	public void setItensContadosMaisUmaVez(List<InventarioAlmoxarifadoContagem> itensContadosMaisUmaVez) {
		this.itensContadosMaisUmaVez = itensContadosMaisUmaVez;
	}

	public boolean isExibirDialogCorrecaoItemContadoVariasVezes() {
		return exibirDialogCorrecaoItemContadoVariasVezes;
	}

	public void setExibirDialogCorrecaoItemContadoVariasVezes(
			boolean exibirDialogCorrecaoItemContadoVariasVezes) {
		this.exibirDialogCorrecaoItemContadoVariasVezes = exibirDialogCorrecaoItemContadoVariasVezes;
	}

	public boolean isExibirDialogConferencia() {
		return exibirDialogConferencia;
	}

	public void setExibirDialogConferencia(boolean exibirDialogConferencia) {
		this.exibirDialogConferencia = exibirDialogConferencia;
	}

	public List<InventarioAlmoxarifadoContagem> getItensInventariados() {
		return itensInventariados;
	}

	public void setItensInventariados(List<InventarioAlmoxarifadoContagem> itensInventariados) {
		this.itensInventariados = itensInventariados;
	}

	public MaterialAlmoxarifado getMaterialCorrecao() {
		return materialCorrecao;
	}

	public void setMaterialCorrecao(MaterialAlmoxarifado materialCorrecao) {
		this.materialCorrecao = materialCorrecao;
	}
	
}
