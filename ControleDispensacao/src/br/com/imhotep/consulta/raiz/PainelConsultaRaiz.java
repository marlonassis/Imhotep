package br.com.imhotep.consulta.raiz;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.imhotep.entidade.Painel;
import br.com.imhotep.entidade.Profissional;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@RequestScoped
public class PainelConsultaRaiz  extends ConsultaGeral<Painel>{

	public List<Painel> getTodosPaineis(){
		return new ArrayList<Painel>(new ConsultaGeral<Painel>(new StringBuilder("select o from Painel o order by to_ascii(lower(o.descricao))")).consulta());
	}
	
	public List<Painel> todosPaineis(Profissional profissional){
		return new ArrayList<Painel>(new ConsultaGeral<Painel>(new StringBuilder("select o.painel from AutorizaPainelProfissional o where o.profissional.idProfissional = "+profissional.getIdProfissional())).consulta());
	}
	
}
