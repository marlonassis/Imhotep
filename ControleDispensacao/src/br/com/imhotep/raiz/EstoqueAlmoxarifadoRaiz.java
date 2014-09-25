package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.EstoqueAlmoxarifado;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstoqueAlmoxarifadoRaiz extends PadraoRaiz<EstoqueAlmoxarifado>{
	
	//o autenticador carrega essa lista quando o usu‡rio tiver atuoriza?‹o para v?-la
	private List<EstoqueAlmoxarifado> estoqueVencido = new ArrayList<EstoqueAlmoxarifado>();
		
	public boolean enviar(EstoqueAlmoxarifado estoqueAlmoxarifado){
		setExibeMensagemInsercao(false);
		setInstancia(estoqueAlmoxarifado);
		return super.enviar();
	}
	
	public static EstoqueAlmoxarifadoRaiz getInstanciaAtual(){
		try {
			return (EstoqueAlmoxarifadoRaiz) Utilitarios.procuraInstancia(EstoqueAlmoxarifadoRaiz.class);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean atualizar(EstoqueAlmoxarifado estoqueAlmoxarifado){
		setExibeMensagemAtualizacao(false);
		setInstancia(estoqueAlmoxarifado);
		return super.atualizar();
	}

	public List<EstoqueAlmoxarifado> getEstoqueVencido() {
		return estoqueVencido;
	}

	public void setEstoqueVencido(List<EstoqueAlmoxarifado> estoqueVencido) {
		this.estoqueVencido = estoqueVencido;
	}
	
}