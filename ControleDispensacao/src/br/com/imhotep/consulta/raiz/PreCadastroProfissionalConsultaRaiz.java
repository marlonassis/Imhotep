package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.PreCadastroProfissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PreCadastroProfissionalConsultaRaiz  extends ConsultaGeral<PreCadastroProfissional>{

	public List<PreCadastroProfissional> getConsultaCadastrosPendentes() {
		StringBuilder sb = new StringBuilder("select o from PreCadastroProfissional o where o.cadastroEfetivado = false order by o.dataInsercao");
		ConsultaGeral<PreCadastroProfissional> cg = new ConsultaGeral<PreCadastroProfissional>();
		return new ArrayList<PreCadastroProfissional>(cg.consulta(sb, null));
	}
	
	
}
