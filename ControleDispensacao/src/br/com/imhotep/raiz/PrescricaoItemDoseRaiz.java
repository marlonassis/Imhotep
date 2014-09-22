package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.PrescricaoItemDose;
import br.com.remendo.PadraoRaiz;

@ManagedBean(name="prescricaoItemDoseRaiz")
@SessionScoped
public class PrescricaoItemDoseRaiz extends PadraoRaiz<PrescricaoItemDose>{
}