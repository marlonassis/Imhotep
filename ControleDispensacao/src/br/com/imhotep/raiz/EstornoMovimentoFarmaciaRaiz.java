package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AlmoxarifadoUnidadeCota;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class EstornoMovimentoFarmaciaRaiz extends PadraoRaiz<AlmoxarifadoUnidadeCota>{
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setProfissionalCadastrante(Autenticador.getProfissionalLogado());
			getInstancia().setDataCadastro(new Date());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
	@Override
	protected void aposEnviar() {
		Unidade u = getInstancia().getUnidade();
		super.novaInstancia();
		getInstancia().setUnidade(u);
		super.aposEnviar();
	}
	
}
