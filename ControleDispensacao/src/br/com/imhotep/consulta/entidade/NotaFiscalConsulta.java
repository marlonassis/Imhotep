package br.com.imhotep.consulta.entidade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.Material;
import br.com.imhotep.entidade.NotaFiscal;
import br.com.remendo.ConsultaGeral;
import br.com.imhotep.temp.PadraoConsulta;

@ManagedBean
@SessionScoped
public class NotaFiscalConsulta extends PadraoConsulta<NotaFiscal> {
	private Material material;
	
	public NotaFiscalConsulta(){
		getCamposConsulta().put("o.fornecedor", IGUAL);
		getCamposConsulta().put("o.identificacaoNotaFiscal", INCLUINDO_TUDO);
		setOrderBy("o.dataInsercao desc");
	}
	
	@Override
	public void novaInstancia() {
		setMaterial(new Material());
		super.novaInstancia();
	}
	
	@Override
	public void carregarResultado(){
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<NotaFiscal>());
		String sql = null;
		if(getMaterial() == null || getMaterial().getIdMaterial() == 0)
			sql = "select o from NotaFiscal o where 1=1";
		else
			sql = "select o from NotaFiscal o join o.itens a where a.estoque.material.idMaterial = "+getMaterial().getIdMaterial();
		
		getConsultaGeral().setSqlConsultaSB(new StringBuilder(sql));
		super.carregarResultado();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
}
