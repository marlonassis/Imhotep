package br.com.ControleDispensacao.fluxo;

import java.util.HashMap;
import java.util.List;

import br.com.ControleDispensacao.entidade.Prescricao;
import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.ControleDispensacao.negocio.PrescricaoRaiz;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoFluxo;

public class FluxoPrescricaoConfirmacao extends PadraoFluxo{
	
	private Prescricao prescricaoAtual = PrescricaoRaiz.getInstanciaHome().getPrescricaoAtual();
	
	private List<PrescricaoItem> itensLiberadosFimPrescricao(Prescricao prescricao){
		if(prescricao != null){
			ConsultaGeral<PrescricaoItem> cg = new ConsultaGeral<PrescricaoItem>();
			HashMap<Object, Object> hm = new HashMap<Object, Object>();
			hm.put("idPrescricao", prescricao.getIdPrescricao());
			
			String sql = "select o from PrescricaoItem o where " +
						 "o.prescricao.idPrescricao = :idPrescricao and (( "+
						 "exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial) " +
						 " and profissionalLiberacao is not null) or (lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO') and " +
						 "controleMedicacaoRestritoSCHI is not null)" +
						 "or (not exists (select a from LiberaMaterialEspecialidade a where a.material.idMaterial = o.material.idMaterial)" +
						 " and not lower(o.material.familia.subGrupo.grupo.descricao) = lower('ANTIBIÓTICO'))) ";
			
			return (List<PrescricaoItem>) cg.consulta(new StringBuilder(sql), hm);
		}
		return null;
	}
	
	public List<PrescricaoItem> getItensLiberados(){
		return itensLiberadosFimPrescricao(prescricaoAtual);
	}
	
	public List<PrescricaoItem> getItensLiberados(Prescricao prescricaoInformada){
		return itensLiberadosFimPrescricao(prescricaoInformada);
	}
}
