package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.consulta.raiz.EstoqueAlmoxarifadoConsultaRaiz;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.imhotep.entidade.InventarioAlmoxarifadoContagem;
import br.com.imhotep.entidade.InventarioMaterial;
import br.com.imhotep.entidade.InventarioMaterialEstoque;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.imhotep.temp.ConsultaGeral;
import br.com.imhotep.temp.ExcecaoPadraoFluxo;
import br.com.imhotep.temp.PadraoFluxoTemp;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioAlmoxarifadoContagemRaiz extends PadraoRaiz<InventarioAlmoxarifadoContagem>{
	private InventarioAlmoxarifado inventarioAlmoxarifado;
	private InventarioMaterial inventarioMaterial;
	private EstoqueAlmoxarifado estoqueAferido = new EstoqueAlmoxarifado();
	private Double quantidadeContada;
	private boolean exibirDialogContagem;
	private boolean exibirDialogContagemItem;
	private List<InventarioMaterial> materials = new ArrayList<InventarioMaterial>();
	private List<InventarioMaterialEstoque> estoqueContagem = new ArrayList<InventarioMaterialEstoque>();
	private List<EstoqueAlmoxarifado> estoquesValidos = new ArrayList<EstoqueAlmoxarifado>();
	
	public void apagarItemLote(InventarioMaterialEstoque item){
		if(super.apagarGenerico(item)){
			carregarLotesContados();
		}
	}
	
	public void apagarAfericao(InventarioAlmoxarifadoContagem item){
		List<InventarioAlmoxarifadoContagem> lista = listaContagensAferidas(item.getInventarioMaterialEstoque());
		if(lista.size() == 1){
			super.apagarGenerico(item.getInventarioMaterialEstoque());
			carregarLotesContados();
		}else{
			super.apagarGenerico(item);
		}
	}
	
	public void cadastrarContagem(){
		try {
			PadraoFluxoTemp.limparFluxo();
			InventarioAlmoxarifadoContagem ifc = new InventarioAlmoxarifadoContagem();
			ifc.setDataContagem(new Date());
			InventarioMaterialEstoque estoqueAferido = verificarExistenciaContagem();
			ifc.setInventarioMaterialEstoque(estoqueAferido);
			ifc.setProfissionalContagem(Autenticador.getProfissionalLogado());
			ifc.setQuantidadeContada(getQuantidadeContada());
			if(estoqueAferido.getIdInventarioMaterialEstoque() == 0)
				PadraoFluxoTemp.getObjetoSalvar().put("InventarioMaterialEstoque - " + estoqueAferido.hashCode(), estoqueAferido);
			PadraoFluxoTemp.getObjetoSalvar().put("InventarioAlmoxarifadoContagem - " + ifc.hashCode(), ifc);
			PadraoFluxoTemp.finalizarFluxo();
			carregarLotesContados();
			setEstoqueAlmoxarifadoAferido(new EstoqueAlmoxarifado());
			setQuantidadeContada(null);
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (ExcecaoPadraoFluxo e) {
			e.printStackTrace();
		}
		PadraoFluxoTemp.limparFluxo();
	}
	
	public List<InventarioAlmoxarifadoContagem> listaContagensAferidas(InventarioMaterialEstoque inventarioMaterialEstoque){
		int idInventarioMaterialEstoque = inventarioMaterialEstoque.getIdInventarioMaterialEstoque();
		String hql = "select o from InventarioAlmoxarifadoContagem o where o.inventarioMaterialEstoque.idInventarioMaterialEstoque = " + idInventarioMaterialEstoque;
		Collection<InventarioAlmoxarifadoContagem> consulta = new ConsultaGeral<InventarioAlmoxarifadoContagem>(new StringBuilder(hql)).consulta();
		return new ArrayList<InventarioAlmoxarifadoContagem>(consulta);
	}
	
	private InventarioMaterialEstoque verificarExistenciaContagem() {
		InventarioMaterialEstoque estoqueAferido = new InventarioMaterialEstoque();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from InventarioMaterialEstoque o where o.estoque.idEstoqueAlmoxarifado = ");
		stringBuilder.append(getEstoqueAlmoxarifadoAferido().getIdEstoqueAlmoxarifado());
		stringBuilder.append(" and o.inventarioMaterial.idInventarioMaterial = ");
		stringBuilder.append(getInventarioMaterial().getIdInventarioMaterial());
		
		InventarioMaterialEstoque registro = new ConsultaGeral<InventarioMaterialEstoque>(stringBuilder).consultaUnica();
		
		if(registro == null){
			estoqueAferido.setInventarioMaterial(getInventarioMaterial());
			estoqueAferido.setEstoque(getEstoqueAlmoxarifadoAferido());
			return estoqueAferido;
		}else{
			return registro;
		}
	}
	
	private void carregarLotesContados() {
		StringBuilder sb = new StringBuilder();
		int idInventarioMaterial = getInventarioMaterial().getIdInventarioMaterial();
		sb.append("select o from InventarioMaterialEstoque o where o.inventarioMaterial.idInventarioMaterial = ");
		sb.append(idInventarioMaterial);
		sb.append(" order by o.estoque.lote");
		Collection<InventarioMaterialEstoque> consulta = new ConsultaGeral<InventarioMaterialEstoque>(sb).consulta();
		setEstoqueAlmoxarifadoContagem(new ArrayList<InventarioMaterialEstoque>(consulta));
	}

	public void exibirDialogContagemItem(){
		carregarLotesValidos();
		carregarLotesContados();
		setExibirDialogContagemItem(true);
	}

	private void carregarLotesValidos() {
		MaterialAlmoxarifado material = getInventarioMaterial().getMaterial();
		setEstoqueAlmoxarifadosValidos(new EstoqueAlmoxarifadoConsultaRaiz().consultarEstoquesMaterial(material));
	}
	
	public void ocultarDialogContagemItem(){
		setExibirDialogContagemItem(false);
	}
	
	public void exibirDialogContagem(){
		carregarMaterials();
		setExibirDialogContagem(true);
	}

	public void ocultarDialogContagem(){
		setExibirDialogContagem(false);
	}
	
	private void carregarMaterials() {
		int id = getInventarioAlmoxarifado().getIdInventarioAlmoxarifado();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select o from InventarioMaterial o where o.inventarioAlmoxarifado.idInventarioAlmoxarifado = ");
		stringBuilder.append(id);
		stringBuilder.append(" order by to_ascii(lower(o.material.descricao))");
		ArrayList<InventarioMaterial> materials2 = new ArrayList<InventarioMaterial>(new ConsultaGeral<InventarioMaterial>(stringBuilder).consulta());
		setMaterials(materials2);
	}
	
	public InventarioMaterial getInventarioMaterial() {
		return inventarioMaterial;
	}

	public void setInventarioMaterial(InventarioMaterial inventarioMaterial) {
		this.inventarioMaterial = inventarioMaterial;
	}

	public boolean isExibirDialogContagem() {
		return exibirDialogContagem;
	}

	public void setExibirDialogContagem(boolean exibirDialogContagem) {
		this.exibirDialogContagem = exibirDialogContagem;
	}

	public List<InventarioMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(List<InventarioMaterial> materials) {
		this.materials = materials;
	}

	public List<InventarioMaterialEstoque> getEstoqueAlmoxarifadoContagem() {
		return estoqueContagem;
	}

	public void setEstoqueAlmoxarifadoContagem(List<InventarioMaterialEstoque> estoqueContagem) {
		this.estoqueContagem = estoqueContagem;
	}

	public InventarioAlmoxarifado getInventarioAlmoxarifado() {
		return inventarioAlmoxarifado;
	}

	public void setInventarioAlmoxarifado(InventarioAlmoxarifado inventarioAlmoxarifado) {
		this.inventarioAlmoxarifado = inventarioAlmoxarifado;
	}

	public boolean isExibirDialogContagemItem() {
		return exibirDialogContagemItem;
	}

	public void setExibirDialogContagemItem(boolean exibirDialogContagemItem) {
		this.exibirDialogContagemItem = exibirDialogContagemItem;
	}

	public List<EstoqueAlmoxarifado> getEstoqueAlmoxarifadosValidos() {
		return estoquesValidos;
	}

	public void setEstoqueAlmoxarifadosValidos(List<EstoqueAlmoxarifado> estoquesValidos) {
		this.estoquesValidos = estoquesValidos;
	}

	public Double getQuantidadeContada() {
		return quantidadeContada;
	}

	public void setQuantidadeContada(Double quantidadeContada) {
		this.quantidadeContada = quantidadeContada;
	}

	public EstoqueAlmoxarifado getEstoqueAlmoxarifadoAferido() {
		return estoqueAferido;
	}

	public void setEstoqueAlmoxarifadoAferido(EstoqueAlmoxarifado estoqueAferido) {
		this.estoqueAferido = estoqueAferido;
	}
	
	
}
