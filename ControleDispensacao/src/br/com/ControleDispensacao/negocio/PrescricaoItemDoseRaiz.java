package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.PrescricaoItemDose;
import br.com.remendo.PadraoHome;

@ManagedBean(name="prescricaoItemDoseRaiz")
@SessionScoped
public class PrescricaoItemDoseRaiz extends PadraoHome<PrescricaoItemDose>{
}