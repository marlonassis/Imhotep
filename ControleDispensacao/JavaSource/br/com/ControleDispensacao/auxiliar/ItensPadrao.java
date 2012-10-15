package br.com.ControleDispensacao.auxiliar;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.enums.TipoCuidadosPacienteEnum;
import br.com.ControleDispensacao.enums.TipoEscolaridadeEnum;
import br.com.ControleDispensacao.enums.TipoEstadoCivilEnum;
import br.com.ControleDispensacao.enums.TipoIndicacaoEnum;
import br.com.ControleDispensacao.enums.TipoLogradouroEnum;
import br.com.ControleDispensacao.enums.TipoMovimentacaoEnum;
import br.com.ControleDispensacao.enums.TipoOperacaoEnum;
import br.com.ControleDispensacao.enums.TipoPrescricaoInadequadaEnum;
import br.com.ControleDispensacao.enums.TipoSexoEnum;
import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;
import br.com.ControleDispensacao.enums.TipoSubIndicacaoProfilaxiaEnum;
import br.com.ControleDispensacao.enums.TipoSubIndicacaoTerapeuticaEnum;



/**
 * @author marlonassis
 */
@ManagedBean(name="itensPadrao")
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
	
}
