package br.com.ControleDispensacao.negocio;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ControleDispensacao.entidade.PrescricaoItem;
import br.com.nucleo.PadraoHome;

@ManagedBean(name="prescricaoItemHome")
@SessionScoped
public class PrescricaoItemHome extends PadraoHome<PrescricaoItem>{
}