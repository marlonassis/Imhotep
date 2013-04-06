package br.com.imhotep.auxiliar;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.imhotep.enums.TipoBloqueioLoteEnum;
import br.com.imhotep.enums.TipoBooleanEnum;
import br.com.imhotep.enums.TipoCuidadosPacienteEnum;
import br.com.imhotep.enums.TipoEscolaridadeEnum;
import br.com.imhotep.enums.TipoEstadoCivilEnum;
import br.com.imhotep.enums.TipoIndicacaoEnum;
import br.com.imhotep.enums.TipoLogradouroEnum;
import br.com.imhotep.enums.TipoMetodoExameEnum;
import br.com.imhotep.enums.TipoMotivosRejeicaoAmostraTestePezinhoEnum;
import br.com.imhotep.enums.TipoMovimentacaoEnum;
import br.com.imhotep.enums.TipoOperacaoEnum;
import br.com.imhotep.enums.TipoPrescricaoInadequadaEnum;
import br.com.imhotep.enums.TipoSexoEnum;
import br.com.imhotep.enums.TipoSituacaoEnum;
import br.com.imhotep.enums.TipoStatusEnum;
import br.com.imhotep.enums.TipoSubIndicacaoProfilaxiaEnum;
import br.com.imhotep.enums.TipoSubIndicacaoTerapeuticaEnum;
import br.com.imhotep.enums.TipoUnidadeSaudeEnum;
import br.com.imhotep.enums.TipoUsuarioLogEnum;
import br.com.imhotep.enums.TipoViaAdministracaoMedicamentoEnum;



/**
 * @author marlonassis
 */
@ManagedBean
@RequestScoped
public class ItensPadrao{
	
	public List<SelectItem> getSexoEnumItens(){
		List<SelectItem> melhorDiaEnumItens = new ArrayList<SelectItem>();
		for(TipoSexoEnum item : TipoSexoEnum.values()){
			melhorDiaEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return melhorDiaEnumItens;
	}

	public List<SelectItem> getSituacaoEnumItens(){
		List<SelectItem> situacaoEnumItens = new ArrayList<SelectItem>();
		for(TipoSituacaoEnum item : TipoSituacaoEnum.values()){
			situacaoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return situacaoEnumItens;
	}
	
	public List<SelectItem> getBooleanEnumItens(){
		List<SelectItem> booleanEnumItens = new ArrayList<SelectItem>();
		for(TipoBooleanEnum item : TipoBooleanEnum.values()){
			booleanEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return booleanEnumItens;
	}

	public List<SelectItem> getStatusEnumItens(){
		List<SelectItem> statusEnumItens = new ArrayList<SelectItem>();
		for(TipoStatusEnum item : TipoStatusEnum.values()){
			statusEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return statusEnumItens;
	}
	
	public List<SelectItem> getLogradouroEnumItens(){
		List<SelectItem> logradouroEnumItens = new ArrayList<SelectItem>();
		for(TipoLogradouroEnum item : TipoLogradouroEnum.values()){
			logradouroEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return logradouroEnumItens;
	}
	
	public List<SelectItem> getOperacaoEnumItens(){
		List<SelectItem> tipoOperacaoEnumItens = new ArrayList<SelectItem>();
		for(TipoOperacaoEnum item : TipoOperacaoEnum.values()){
			tipoOperacaoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoOperacaoEnumItens;
	}
	
	public List<SelectItem> getTipoIndicacaoEnumItens(){
		List<SelectItem> tipoIndicacaoEnumItens = new ArrayList<SelectItem>();
		for(TipoIndicacaoEnum item : TipoIndicacaoEnum.values()){
			tipoIndicacaoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoIndicacaoEnumItens;
	}

	public List<SelectItem> getTipoPrescricaoInadequadaEnumItens(){
		List<SelectItem> tipoPrescricaoInadequadaEnumItens = new ArrayList<SelectItem>();
		for(TipoPrescricaoInadequadaEnum item : TipoPrescricaoInadequadaEnum.values()){
			tipoPrescricaoInadequadaEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoPrescricaoInadequadaEnumItens;
	}
	
	public List<SelectItem> getTipoSubIndicacaoProfilaxiaEnumItens(){
		List<SelectItem> tipoSubIndicacaoProfilaxiaEnumItens = new ArrayList<SelectItem>();
		for(TipoSubIndicacaoProfilaxiaEnum item : TipoSubIndicacaoProfilaxiaEnum.values()){
			tipoSubIndicacaoProfilaxiaEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoSubIndicacaoProfilaxiaEnumItens;
	}
	
	public List<SelectItem> getTipoSubIndicacaoTerapeuticaEnumItens(){
		List<SelectItem> tipoSubIndicacaoTerapeuticaEnumItens = new ArrayList<SelectItem>();
		for(TipoSubIndicacaoTerapeuticaEnum item : TipoSubIndicacaoTerapeuticaEnum.values()){
			tipoSubIndicacaoTerapeuticaEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoSubIndicacaoTerapeuticaEnumItens;
	}
	
	public List<SelectItem> getTipoCuidadosPacienteEnumItens(){
		List<SelectItem> tipoCuidadosPacienteEnumItens = new ArrayList<SelectItem>();
		for(TipoCuidadosPacienteEnum item : TipoCuidadosPacienteEnum.values()){
			tipoCuidadosPacienteEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoCuidadosPacienteEnumItens;
	}
	
	public List<SelectItem> getTipoMovimentacaoEnumItens(){
		List<SelectItem> tipoMovimentacaoEnumItens = new ArrayList<SelectItem>();
		for(TipoMovimentacaoEnum item : TipoMovimentacaoEnum.values()){
			tipoMovimentacaoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoMovimentacaoEnumItens;
	}

	public List<SelectItem> getTipoEstadoCivilEnumItens(){
		List<SelectItem> tipoEstadoCivilEnumItens = new ArrayList<SelectItem>();
		for(TipoEstadoCivilEnum item : TipoEstadoCivilEnum.values()){
			tipoEstadoCivilEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoEstadoCivilEnumItens;
	}
	
	public List<SelectItem> getTipoEscolaridadeEnumItens(){
		List<SelectItem> tipoEscolaridadeEnumItens = new ArrayList<SelectItem>();
		for(TipoEscolaridadeEnum item : TipoEscolaridadeEnum.values()){
			tipoEscolaridadeEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoEscolaridadeEnumItens;
	}
	
	public List<SelectItem> getTipoViaAdministracaoMedicamentoEnumItens(){
		List<SelectItem> tipoViaAdministracaoMedicamentoEnumItens = new ArrayList<SelectItem>();
		for(TipoViaAdministracaoMedicamentoEnum item : TipoViaAdministracaoMedicamentoEnum.values()){
			tipoViaAdministracaoMedicamentoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoViaAdministracaoMedicamentoEnumItens;
	}
	
	public List<SelectItem> getTipoMetodoExameEnumItens(){
		List<SelectItem> tipoMetodoExameEnumItens = new ArrayList<SelectItem>();
		for(TipoMetodoExameEnum item : TipoMetodoExameEnum.values()){
			tipoMetodoExameEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoMetodoExameEnumItens;
	}
	
	public List<SelectItem> getTipoUnidadeSaudeEnumItens(){
		List<SelectItem> tipoUnidadeSaudeEnumItens = new ArrayList<SelectItem>();
		for(TipoUnidadeSaudeEnum item : TipoUnidadeSaudeEnum.values()){
			tipoUnidadeSaudeEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoUnidadeSaudeEnumItens;
	}

	public List<SelectItem> getTipoMotivosRejeicaoAmostraTestePezinhoEnumItens(){
		List<SelectItem> tipoMotivosRejeicaoAmostraTestePezinhoEnumItens = new ArrayList<SelectItem>();
		for(TipoMotivosRejeicaoAmostraTestePezinhoEnum item : TipoMotivosRejeicaoAmostraTestePezinhoEnum.values()){
			tipoMotivosRejeicaoAmostraTestePezinhoEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoMotivosRejeicaoAmostraTestePezinhoEnumItens;
	}
	
	public List<SelectItem> getTipoBloqueioLoteEnumItens(){
		List<SelectItem> tipoBloqueioLoteEnumItens = new ArrayList<SelectItem>();
		for(TipoBloqueioLoteEnum item : TipoBloqueioLoteEnum.values()){
			tipoBloqueioLoteEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoBloqueioLoteEnumItens;
	}
	
	public List<SelectItem> getTipoUsuarioLogEnumItens(){
		List<SelectItem> tipoUsuarioLogEnumItens = new ArrayList<SelectItem>();
		for(TipoUsuarioLogEnum item : TipoUsuarioLogEnum.values()){
			tipoUsuarioLogEnumItens.add(new SelectItem(item, item.getLabel()));
		}
		return tipoUsuarioLogEnumItens;
	}
}
