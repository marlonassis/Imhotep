package br.com.imhotep.raiz;

import java.util.Date;

import br.com.imhotep.entidade.SolicitacaoMaterialAlmoxarifadoUnidadeItem;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

public class SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz extends PadraoRaiz<SolicitacaoMaterialAlmoxarifadoUnidadeItem> {
	public SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(){
		super();
	}
	
	public SolicitacaoMaterialAlmoxarifadoUnidadeItemRaiz(SolicitacaoMaterialAlmoxarifadoUnidadeItem item){
		super();
		setInstancia(item);
	}
	
	@Override
	protected void preEnvio() {
		try {
			getInstancia().setDataInsercao(new Date());
			getInstancia().setProfissionalInsercao(Autenticador.getProfissionalLogado());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		super.preEnvio();
	}
	
}
