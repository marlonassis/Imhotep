package br.com.ControleDispensacao.auxiliar;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;

import br.com.ControleDispensacao.enums.TipoBooleanEnum;
import br.com.ControleDispensacao.enums.TipoLogradouroEnum;
import br.com.ControleDispensacao.enums.TipoSexoEnum;
import br.com.ControleDispensacao.enums.TipoSituacaoEnum;
import br.com.ControleDispensacao.enums.TipoStatusEnum;



/**
 * @author marlonassis
 *
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
}
