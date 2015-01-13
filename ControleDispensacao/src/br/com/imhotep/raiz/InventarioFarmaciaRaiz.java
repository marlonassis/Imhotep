package br.com.imhotep.raiz;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.imhotep.enums.TipoStatusInventarioEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaRaiz extends PadraoRaiz<InventarioFarmacia>{
	
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
