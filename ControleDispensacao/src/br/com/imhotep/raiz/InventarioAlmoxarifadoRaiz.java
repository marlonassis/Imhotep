package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioAlmoxarifado;
import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioAlmoxarifadoRaiz extends PadraoRaiz<InventarioAlmoxarifado>{
	
	@Override
	protected void preEnvio() {
		apagarInventario();
		adicionarInformacoesBasicas();
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		MaterialAlmoxarifado material = getInstancia().getMaterial();
		super.novaInstancia();
		getInstancia().setMaterial(material);
		super.aposEnviar();
	}

	private void adicionarInformacoesBasicas() {
		getInstancia().setDataCadastro(new Date());
		try {
			getInstancia().setProfisionalCadastro(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
	}

	private void apagarInventario() {
		String hql = "delete from InventarioAlmoxarifado where "
				+ "material.idMaterialAlmoxarifado = "+getInstancia().getMaterial().getIdMaterialAlmoxarifado() + " "
				+ "and lote = '" + getInstancia().getLote() + "'";
		super.executa(hql);
	}
	
}
