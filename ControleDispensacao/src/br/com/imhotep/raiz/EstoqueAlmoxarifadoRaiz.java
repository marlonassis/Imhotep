package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class EstoqueAlmoxarifadoRaiz extends PadraoHome<EstoqueAlmoxarifado>{
	
	public boolean enviar(EstoqueAlmoxarifado estoqueAlmoxarifado){
		setExibeMensagemInsercao(false);
		setInstancia(estoqueAlmoxarifado);
		return super.enviar();
	}
	
	public boolean atualizar(EstoqueAlmoxarifado estoqueAlmoxarifado){
		setExibeMensagemAtualizacao(false);
		setInstancia(estoqueAlmoxarifado);
		return super.atualizar();
	}
	
}