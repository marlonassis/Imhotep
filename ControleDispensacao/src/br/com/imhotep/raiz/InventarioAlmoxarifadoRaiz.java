package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.imhotep.enums.TipoStatusInventarioEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioAlmoxarifadoRaiz extends PadraoRaiz<InventarioAlmoxarifado>{
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalCadastro(Autenticador.getProfissionalLogado());
			getInstancia().setTipoStatus(TipoStatusInventarioEnum.A);
			super.preEnvio();
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}
	
}
