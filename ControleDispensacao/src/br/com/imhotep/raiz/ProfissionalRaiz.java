package br.com.imhotep.raiz;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.com.imhotep.auxiliar.Utilitarios;
import br.com.imhotep.entidade.AlteracaoLocacaoLog;
import br.com.imhotep.entidade.Cargo;
import br.com.imhotep.entidade.CargoProfissional;
import br.com.imhotep.entidade.EstruturaOrganizacional;
import br.com.imhotep.entidade.Funcao;
import br.com.imhotep.entidade.LotacaoProfissional;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.TipoConselho;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.enums.TipoCrudEnum;
import br.com.imhotep.enums.TipoLotacaoProfissionalEnum;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.PadraoRaiz;

@ManagedBean
@SessionScoped
public class ProfissionalRaiz extends PadraoRaiz<Profissional>{
	
	private Funcao funcao = new Funcao();
	private Cargo cargo = new Cargo();
	private List<Funcao> funcoes = new ArrayList<Funcao>();
	private List<Cargo> cargos = new ArrayList<Cargo>();
	private TipoConselho tipoConselho;
	private EstruturaOrganizacional estruturaOrganizacional;
	
	
	public void addLotacao(){
		LotacaoProfissional lotacaoProfissional = new LotacaoProfissional();
		lotacaoProfissional.setEstruturaOrganizacional(getEstruturaOrganizacional());
		lotacaoProfissional.setProfissional(getInstancia());
		lotacaoProfissional.setTipoLotacao(TipoLotacaoProfissionalEnum.E);
		if(super.enviarGenerico(lotacaoProfissional)){
			gerarLogLotacao(TipoCrudEnum.I, null, lotacaoProfissional.getEstruturaOrganizacional(), lotacaoProfissional.getProfissional());
		}
		setEstruturaOrganizacional(new EstruturaOrganizacional());
	}
	
	private void gerarLogLotacao(TipoCrudEnum tipo, String justificativa, EstruturaOrganizacional eo, Profissional profissional) {
		AlteracaoLocacaoLogRaiz allr = new AlteracaoLocacaoLogRaiz();
		AlteracaoLocacaoLog log = allr.montarLog(eo.getNome(), justificativa , profissional, tipo);
		allr.setInstancia(log);
		allr.enviar();
	}
	
	public ProfissionalRaiz() {
		novaInstancia();
	}
	
	@Override
	public void novaInstancia() {
		super.novaInstancia();
		getInstancia().setUsuario(new Usuario());
	}
	
	public void addCargoProfissional(){
		CargoProfissional cp = new CargoProfissional();
		cp.setCargo(getCargo());
		cp.setProfissional(getInstancia());
		super.enviarGenerico(cp);
		setCargo(null);
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

	public boolean atualizar(Profissional profissional) {
		setInstancia(profissional);
		return super.atualizar();
	}
	
	public void removeCargoProfissional(Cargo cargo){
		String hql = "delete from CargoProfissional where profissional.idProfissional = "+getInstancia().getIdProfissional() +
						" and cargo.idCargo = "+cargo.getIdCargo();
		super.executa(hql);
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
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Este usu�rio j� foi escolhido. informe outro login.", "Inser��o n�o efetuada."));
		}
		return false;
	}

	public TipoConselho getTipoConselho() {
		return tipoConselho;
	}

	public void setTipoConselho(TipoConselho tipoConselho) {
		this.tipoConselho = tipoConselho;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public List<Funcao> getFuncoes() {
		return funcoes;
	}

	public void setFuncoes(List<Funcao> funcoes) {
		this.funcoes = funcoes;
	}

	public List<Cargo> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargo> cargos) {
		this.cargos = cargos;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public EstruturaOrganizacional getEstruturaOrganizacional() {
		return estruturaOrganizacional;
	}

	public void setEstruturaOrganizacional(EstruturaOrganizacional estruturaOrganizacional) {
		this.estruturaOrganizacional = estruturaOrganizacional;
	}

}
