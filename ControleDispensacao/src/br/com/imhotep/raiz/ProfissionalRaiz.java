package br.com.imhotep.raiz;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.consulta.raiz.EspecialidadeConsultaRaiz;
import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.TipoConselho;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoHome;

@ManagedBean
@SessionScoped
public class ProfissionalRaiz extends PadraoHome<Profissional>{
	
	private TipoConselho tipoConselho;
	private Especialidade especialidade;
	
	public ProfissionalRaiz() {
		novaInstancia();
	}
	
	public void gerarChave(){
		String chaveAberta;
		try {
			chaveAberta = Utilitarios.getPatternChaveUnicaPreparada(Autenticador.getInstancia().getProfissionalRecuperacao());;
			String chave = Utilitarios.encriptaParaMd5(chaveAberta);
			Profissional p = Autenticador.getProfissionalLogado();
			p.setChaveVerificacao(chave);
			p.setDataNascimento(Autenticador.getInstancia().getProfissionalRecuperacao().getDataNascimento());
			setInstancia(p);
			super.atualizar();
			super.novaInstancia();
			Autenticador.getInstancia().setProfissionalRecuperacao(new Profissional());
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setUsuario(new Usuario());
		setEspecialidade(new Especialidade());
	}
	
	public List<Especialidade> getListaEspecialidade(){
		if(getTipoConselho() != null){
			return new EspecialidadeConsultaRaiz().listaEspecialidadePorTipoConselho(getTipoConselho().getIdTipoConselho());
		}else{
			return new EspecialidadeConsultaRaiz().listaEspecialidadePorTipoConselho(null);
		}
	}
	
	public boolean atualizar(Profissional profissional) {
		setInstancia(profissional);
		return super.atualizar();
	}
	
	public void addEspecialidadeProfissional(){
		if(new ProfissionalEspecialidadeRaiz().enviar(getEspecialidade(), getInstancia())){
			if(getInstancia().getEspecialidades() == null){
				getInstancia().setEspecialidades(new HashSet<Especialidade>());
			}
			getInstancia().getEspecialidades().add(getEspecialidade());
			setEspecialidade(null);
		}
	}
	
	public void remEspecialidadeProfissional(Especialidade linha){
		if(new ProfissionalEspecialidadeRaiz().executa("delete from ProfissionalEspecialidade where especialidade.idEspecialidade = "+linha.getIdEspecialidade()+" and profissional.idProfissional = "+getInstancia().getIdProfissional()).equals(1)){
			getInstancia().getEspecialidades().remove(linha);
		}
	}
	
	@Override
	public boolean enviar() {
		if(new UsuarioRaiz().procurarUsuario(getInstancia().getUsuario().getLogin()) == null){
			try{
				getInstancia().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
				getInstancia().getUsuario().setUsuarioInclusao(Autenticador.getInstancia().getUsuarioAtual());
			} catch (Exception e) {
				e.printStackTrace();
				super.mensagem("Erro ao acessar o autenticador.", null, FacesMessage.SEVERITY_ERROR);
				System.out.print("Erro em ProfissionalHome");
			}
			getInstancia().setEspecialidade(new Especialidade());
			getInstancia().getEspecialidade().setIdEspecialidade(1);
			getInstancia().setDataInclusao(new Date());
			getInstancia().getUsuario().setDataInclusao(new Date());
			getInstancia().getUsuario().setQuantidadeErroLogin(0);
			getInstancia().getUsuario().setSenha(Utilitarios.encriptaParaMd5(String.valueOf("123456")));
			if(super.enviar()){
//				boolean exibeMensagemInsercao = false;
//				boolean res = new AutorizaUnidadeProfissionalRaiz().enviar(getInstancia(), getUnidade(), exibeMensagemInsercao);
				return true;
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este usuário já foi escolhido. informe outro login.", "Inserção não efetuada."));
		}
		return false;
	}

	public TipoConselho getTipoConselho() {
		return tipoConselho;
	}

	public void setTipoConselho(TipoConselho tipoConselho) {
		this.tipoConselho = tipoConselho;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

}
