package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.InventarioFarmacia;
import br.com.imhotep.entidade.Material;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class InventarioFarmaciaRaiz extends PadraoRaiz<InventarioFarmacia>{
	
	@Override
	protected void preEnvio() {
		apagarInventario();
		adicionarInformacoesBasicas();
		super.preEnvio();
	}

	@Override
	protected void aposEnviar() {
		Material material = getInstancia().getMaterial();
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
		String hql = "delete from InventarioFarmacia where "
				+ "material.idMaterial = "+getInstancia().getMaterial().getIdMaterial() + " "
				+ "and lote = '" + getInstancia().getLote() + "'";
		super.executa(hql);
	}
	
}
