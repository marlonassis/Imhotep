package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.MaterialAlmoxarifado;
import br.com.imhotep.entidade.NotaFiscalAlmoxarifado;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;

@ManagedBean
@SessionScoped
public class NotaFiscalAlmoxarifadoConsulta extends PadraoConsulta<NotaFiscalAlmoxarifado> {
	private MaterialAlmoxarifado materialAlmoxarifado;
	
	public NotaFiscalAlmoxarifadoConsulta(){
		getCamposConsulta().put("o.fornecedor", IGUAL);
		getCamposConsulta().put("o.identificacao", INCLUINDO_TUDO);
		setOrderBy("o.dataInsercao desc");
	}
	
	@Override
	public void novaInstancia() {
		setMaterialAlmoxarifado(new MaterialAlmoxarifado());
		super.novaInstancia();
	}
	
	@Override
	public void carregarResultado(){
		setPesquisaGuiada(true);
		setPesquisaCamposDespadronizado(true);
		setConsultaGeral(new ConsultaGeral<NotaFiscalAlmoxarifado>());
		String sql = null;
		if(getMaterialAlmoxarifado() == null || getMaterialAlmoxarifado().getIdMaterialAlmoxarifado() == 0)
			sql = "select o from NotaFiscalAlmoxarifado o where 1=1";
		else
			sql = "select distinct o from NotaFiscalAlmoxarifado o join o.itens a where a.estoqueAlmoxarifado.materialAlmoxarifado.idMaterialAlmoxarifado = "+getMaterialAlmoxarifado().getIdMaterialAlmoxarifado();
		
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		super.carregarResultado();
	}

	public MaterialAlmoxarifado getMaterialAlmoxarifado() {
		return materialAlmoxarifado;
	}

	public void setMaterialAlmoxarifado(MaterialAlmoxarifado materialAlmoxarifado) {
		this.materialAlmoxarifado = materialAlmoxarifado;
	}
}
