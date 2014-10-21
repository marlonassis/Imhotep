package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.auxiliar.Parametro;
import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class EspecialidadeConsultaRaiz  extends ConsultaGeral<Especialidade>{
	
	public List<Especialidade> getConsultarEspecialidadesFilha() {
		try {
			StringBuilder sb = null;
			if(Parametro.isEngenharia()){
				sb = new StringBuilder("select o from Especialidade o");
			}else{
				Integer idProfissionalAtual = Autenticador.getProfissionalLogado().getIdProfissional();
				sb = new StringBuilder("select o.especialidade from ProfissionalEspecialidade o where o.profissional.idProfissional = "+idProfissionalAtual);
			}
			return new ArrayList<Especialidade>(new ConsultaGeral<Especialidade>().consulta(sb, null));
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		}
		return new ArrayList<Especialidade>();
	} 
	
	public List<Especialidade> consultarEspecialidadesPai() {
		StringBuilder sb = new StringBuilder("select o from Especialidade o where o.especialidadePai is null order by to_ascii(o.descricao)");
		return new ArrayList<Especialidade>(new ConsultaGeral<Especialidade>().consulta(sb, null));
	}
	
	public List<Especialidade> consultarEspecialidades() {
		StringBuilder sb = new StringBuilder("select o from Especialidade o order by to_ascii(o.descricao)");
		return new ArrayList<Especialidade>(new ConsultaGeral<Especialidade>().consulta(sb, null));
	}
	
	public List<Especialidade> listaEspecialidadePorTipoConselho(Integer idTipoConselho){
		StringBuilder sb = new StringBuilder();
		if(idTipoConselho != null){
			sb.append("select o from Especialidade as o where o.tipoConselho.idTipoConselho = " + idTipoConselho + " order by to_ascii(o.descricao)");
		}else{
			sb.append("select o from Especialidade as o where o.tipoConselho is null order by to_ascii(o.descricao)");
		}
		return new ArrayList<Especialidade>(new ConsultaGeral<Especialidade>().consulta(sb, null));
	}
	
}
