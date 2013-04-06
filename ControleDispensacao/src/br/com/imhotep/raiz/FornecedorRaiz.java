package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Fornecedor;
import br.com.remendo.PadraoHome;

@ManagedBean(name="fornecedorRaiz")
@SessionScoped
public class FornecedorRaiz extends PadraoHome<Fornecedor>{

}
