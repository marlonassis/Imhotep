package br.com.imhotep.raiz;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.imhotep.entidade.AlteracaoEstruturaLog;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.GrupoAdm;
import br.com.imhotep.enums.TipoCrudEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class AteracaoEstruturaLogRaiz extends PadraoRaiz<AlteracaoEstruturaLog>{
	
	public AlteracaoEstruturaLog montarLog(String justificativa, GrupoAdm grupo, TipoCrudEnum tipo, EstruturaOrganizacional estruturaOrganizacional) throws ExcecaoProfissionalLogado{
		AlteracaoEstruturaLog log = new AlteracaoEstruturaLog();
		log.setDataCadastro(new Date());
		log.setJustificativa(justificativa);
		log.setNome(estruturaOrganizacional.getNome());
		log.setProfissionalCadastro(Autenticador.getProfissionalLogado());
		log.setTipo(tipo);
		log.setGrupo(grupo == null ? null : grupo.getNome());
		if(!tipo.equals(TipoCrudEnum.D))
			log.setEstruturaOrganizacional(estruturaOrganizacional);
		return log;
	}

}
