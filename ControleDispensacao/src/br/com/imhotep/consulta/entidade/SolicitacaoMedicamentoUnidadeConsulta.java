package br.com.imhotep.consulta.entidade;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidadeItem;
import br.com.imhotep.entidade.extra.SolicitacaoMedicamento;
import br.com.imhotep.temp.PadraoConsulta;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@SessionScoped
public class SolicitacaoMedicamentoUnidadeConsulta extends PadraoConsulta<SolicitacaoMedicamentoUnidade> {
	public SolicitacaoMedicamentoUnidadeConsulta(){
		getCamposConsulta().put("o.unidadeDestino", IGUAL);
		getCamposConsulta().put("o.profissionalInsercao", IGUAL);
		getCamposConsulta().put("o.dataInsercao", IGUAL);
		setOrderBy("to_ascii(o.unidadeDestino.nome)");
	}
	
	@Override
	public List<SolicitacaoMedicamentoUnidade> getList() {
		setPesquisaGuiada(false);
		setConsultaGeral(new ConsultaGeral<SolicitacaoMedicamentoUnidade>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from SolicitacaoMedicamentoUnidade o where 1=1"));
		return super.getList();
	}

	public TreeNode getRoot() {
		List<SolicitacaoMedicamentoUnidade> itens = getList();
		TreeNode root = new DefaultTreeNode("root", null);
		if(itens != null){
			TreeNode solicitacao=null;
			SolicitacaoMedicamento sm = null;
			for(SolicitacaoMedicamentoUnidade item : itens){
				sm = new SolicitacaoMedicamento(item.getIdSolicitacaoMedicamentoUnidade(), 
						item.getUnidadeDestino().getNome(), item.getItens().size(), item.getDataInsercao(), item.getProfissionalInsercao(), item.getStatusDispensacao());
				solicitacao = new DefaultTreeNode(sm, root);
		        for(SolicitacaoMedicamentoUnidadeItem itemMed : item.getItens()){
		        	sm = new SolicitacaoMedicamento(item.getIdSolicitacaoMedicamentoUnidade(), 
							itemMed.getMaterial().getDescricao(), itemMed.getQuantidadeSolicitada(), itemMed.getDataInsercao(), null, null);
		        	new DefaultTreeNode(sm, solicitacao);
		        }
			}
		}
		return root;
	}

}