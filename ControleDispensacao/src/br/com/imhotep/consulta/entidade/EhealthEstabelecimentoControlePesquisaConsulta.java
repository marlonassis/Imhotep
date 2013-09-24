package br.com.imhotep.consulta.entidade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.EhealthEstabelecimento;
import br.com.imhotep.entidade.EhealthMunicipio;
import br.com.imhotep.enums.TipoEstadoEnum;
import br.com.remendo.ConsultaGeral;
import br.com.remendo.PadraoConsulta;


@ManagedBean
@SessionScoped
public class EhealthEstabelecimentoControlePesquisaConsulta extends PadraoConsulta<EhealthEstabelecimento> {
	
	private TipoEstadoEnum estado;
	
	public EhealthEstabelecimentoControlePesquisaConsulta(){
		getCamposConsulta().put("o.nome", INCLUINDO_TUDO);
		getCamposConsulta().put("o.razaoSocial", INCLUINDO_TUDO);
		getCamposConsulta().put("o.tipoUnidade", IGUAL);
		getCamposConsulta().put("o.tipoNatureza", IGUAL);
		getCamposConsulta().put("o.ehealthMunicipio", IGUAL);
		setOrderBy("to_ascii(o.nome)");
	}
	
	@Override
	public void novaInstancia() {
		setEstado(null);
		super.novaInstancia();
	}
	
	@Override
	public void carregarResultado(){
		setPesquisaGuiada(true);
		setConsultaGeral(new ConsultaGeral<EhealthEstabelecimento>());
		getConsultaGeral().setSqlConsultaSB(new StringBuilder("select o from EhealthEstabelecimento o where 1=1"));
		super.carregarResultado();
	}
	
	public List<EhealthMunicipio> getMunicipios(){
		if(getEstado() == null)
			return new ArrayList<EhealthMunicipio>();
		StringBuilder stringB = new StringBuilder("select o from EhealthMunicipio o where o.estado = '");
		stringB.append(getEstado().name());
		stringB.append("' order by to_ascii(o.nome)");
		Collection<EhealthMunicipio> consulta = new ConsultaGeral<EhealthMunicipio>().consulta(stringB, new HashMap<Object, Object>());
		if(consulta != null)
			return new ArrayList<EhealthMunicipio>(consulta);
		else
			return new ArrayList<EhealthMunicipio>();
	}

	public TipoEstadoEnum getEstado() {
		return estado;
	}

	public void setEstado(TipoEstadoEnum estado) {
		this.estado = estado;
	}
	
}
