package br.com.imhotep.raiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Constantes;
import br.com.imhotep.consulta.raiz.SolicitacaoMedicamentoUnidadeConsultaRaiz;
import br.com.imhotep.entidade.SolicitacaoMedicamentoUnidade;
import br.com.imhotep.entidade.extra.SolicitacoesDevolucoesProfissional;
import br.com.imhotep.seguranca.GerenciadorRequisicao;
import br.com.remendo.PadraoRaiz;
import br.com.remendo.gerenciador.ControleInstancia;

@ManagedBean
@SessionScoped
public class SolicitacoesDevolucoesProfissionalRaiz extends PadraoRaiz<SolicitacaoMedicamentoUnidade> {

	private List<SolicitacoesDevolucoesProfissional> solicitacoesProfissional = new ArrayList<SolicitacoesDevolucoesProfissional>();
	private SolicitacoesDevolucoesProfissional itemConsulta = new SolicitacoesDevolucoesProfissional();
	private boolean exibirDialogConsultaSolicitacao;
	
	
	public void abrirDialogConsultaSolicitacao(){
		setExibirDialogConsultaSolicitacao(true);
	}
	
	public void carregarSolicitacao(){
		try {
			if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_DEVOLUCAO_ALMOXARIFADO)){
				DevolucaoMaterialRaiz dmr;
				dmr = (DevolucaoMaterialRaiz) new ControleInstancia().procuraInstancia(DevolucaoMaterialRaiz.class);
				dmr.setInstancia(dmr.getBusca("select o from DevolucaoMaterial o where o.idDevolucaoMaterial ="+getItemConsulta().getCodigo()).get(0));
				new GerenciadorRequisicao().redirecionarPagina(Constantes.PAGINA_DEVOLUCAO_MATERIAL);
			}else{
				if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_DEVOLUCAO_FARMACIA)){
					DevolucaoMedicamentoRaiz dmr = (DevolucaoMedicamentoRaiz) new ControleInstancia().procuraInstancia(DevolucaoMedicamentoRaiz.class);
					dmr.setInstancia(dmr.getBusca("select o from DevolucaoMedicamento o where o.idDevolucaoMedicamento ="+getItemConsulta().getCodigo()).get(0));
					new GerenciadorRequisicao().redirecionarPagina(Constantes.PAGINA_DEVOLUCAO_MEDICAMENTO);
				}else{
					if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_SOLICITACAO_ALMOXARIFADO)){
						SolicitacaoMaterialAlmoxarifadoUnidadeRaiz smau = (SolicitacaoMaterialAlmoxarifadoUnidadeRaiz) new ControleInstancia().procuraInstancia(SolicitacaoMaterialAlmoxarifadoUnidadeRaiz.class);
						smau.setInstancia(smau.getBusca("select o from SolicitacaoMaterialAlmoxarifadoUnidade o where o.idSolicitacaoMaterialAlmoxarifadoUnidade ="+getItemConsulta().getCodigo()).get(0));
						new GerenciadorRequisicao().redirecionarPagina(Constantes.PAGINA_SOLICITACAO_MATERIAL);
					}else{
						if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_SOLICITACAO_FARMACIA)){
							SolicitacaoMedicamentoUnidadeRaiz smu = (SolicitacaoMedicamentoUnidadeRaiz) new ControleInstancia().procuraInstancia(SolicitacaoMedicamentoUnidadeRaiz.class);
							smu.setInstancia(smu.getBusca("select o from SolicitacaoMedicamentoUnidade o where o.idSolicitacaoMedicamentoUnidade ="+getItemConsulta().getCodigo()).get(0));
							new GerenciadorRequisicao().redirecionarPagina(Constantes.PAGINA_SOLICITACAO_MEDICAMENTO);
						}else{
							try {
								throw new Exception("Falha ao apagar");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setItemConsulta(new SolicitacoesDevolucoesProfissional());
	}
	
	public void deletarSolicitacao(){
		String hqlDelete = "";
		if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_DEVOLUCAO_ALMOXARIFADO)){
			hqlDelete = "delete from DevolucaoMaterial o where o.idDevolucaoMaterial ="+getItemConsulta().getCodigo();
		}else{
			if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_DEVOLUCAO_FARMACIA)){
				hqlDelete = "delete from DevolucaoMedicamento o where o.idDevolucaoMedicamento ="+getItemConsulta().getCodigo();
			}else{
				if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_SOLICITACAO_ALMOXARIFADO)){
					hqlDelete = "delete from SolicitacaoMaterialAlmoxarifadoUnidade o where o.idSolicitacaoMaterialAlmoxarifadoUnidade ="+getItemConsulta().getCodigo();
				}else{
					if(getItemConsulta().getTipo().equals(SolicitacaoMedicamentoUnidadeConsultaRaiz.SIGLA_SOLICITACAO_FARMACIA)){
						hqlDelete = "delete from SolicitacaoMedicamentoUnidade o where o.idSolicitacaoMedicamentoUnidade ="+getItemConsulta().getCodigo();
					}else{
						try {
							throw new Exception("Falha ao apagar");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		super.executa(hqlDelete);
		setItemConsulta(new SolicitacoesDevolucoesProfissional());
		try {
			((SolicitacaoMedicamentoUnidadeConsultaRaiz) new ControleInstancia().procuraInstancia(SolicitacaoMedicamentoUnidadeConsultaRaiz.class)).consultarSolicitacoesProfissional();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void fecharDialogConsultaSolicitacao(){
		setExibirDialogConsultaSolicitacao(false);
		setItemConsulta(new SolicitacoesDevolucoesProfissional());
	}
	
	public List<SolicitacoesDevolucoesProfissional> getSolicitacoesProfissional() {
		return solicitacoesProfissional;
	}

	public void setSolicitacoesProfissional(List<SolicitacoesDevolucoesProfissional> solicitacoesProfissional) {
		this.solicitacoesProfissional = solicitacoesProfissional;
	}

	public SolicitacoesDevolucoesProfissional getItemConsulta() {
		return itemConsulta;
	}

	public void setItemConsulta(SolicitacoesDevolucoesProfissional itemConsulta) {
		this.itemConsulta = itemConsulta;
	}

	public boolean isExibirDialogConsultaSolicitacao() {
		return exibirDialogConsultaSolicitacao;
	}

	public void setExibirDialogConsultaSolicitacao(
			boolean exibirDialogConsultaSolicitacao) {
		this.exibirDialogConsultaSolicitacao = exibirDialogConsultaSolicitacao;
	}
	
}
