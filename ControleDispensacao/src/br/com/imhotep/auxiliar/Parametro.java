package br.com.imhotep.auxiliar;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.imhotep.entidade.Especialidade;
import br.com.imhotep.entidade.Profissional;
import br.com.imhotep.entidade.TipoMovimento;
import br.com.imhotep.entidade.TipoMovimentoAlmoxarifado;
import br.com.imhotep.entidade.Unidade;
import br.com.imhotep.entidade.Usuario;
import br.com.imhotep.excecoes.ExcecaoProfissionalLogado;
import br.com.imhotep.linhaMecanica.LinhaMecanica;
import br.com.imhotep.seguranca.Autenticador;
import br.com.remendo.ConsultaGeral;

@ManagedBean
@ViewScoped
public class Parametro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private static boolean verificaEspecialidade(String especialidade){
		try{
			Autenticador autenticador = Autenticador.getInstancia();
			Profissional profissionaAtual =  autenticador == null ? null : autenticador.getProfissionalAtual();
			if(profissionaAtual != null){
				for(Especialidade espe : profissionaAtual.getEspecialidades()){
					if(espe.getDescricao().equalsIgnoreCase(especialidade)){
						return true;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean isUsuarioTeste(){
		return verificaEspecialidade("Teste");
	}
	
	public static boolean isUsuarioFarmaceutico(){
		return verificaEspecialidade("Farmac");
	}
	
	public boolean getUsuarioFarmaceutico(){
		return isUsuarioFarmaceutico();
	}
	
	public boolean getUsuarioTeste(){
		return isUsuarioTeste();
	}
	
	public static boolean isUsuarioEngenheiro(){
		return verificaEspecialidade("Engenharia");
	}
	
	public boolean getUsuarioEngenheiro(){
		return isUsuarioEngenheiro();
	}
	
	public static boolean isUsuarioMedico(){
		return verificaEspecialidade("Médico");
	}
	
	public boolean getUsuarioMedico(){
		return isUsuarioMedico();
	}

	public boolean isEspecialidade(String especialidade){
		Profissional p;
		try {
			p = Autenticador.getProfissionalLogado();
			for(Especialidade e : p.getEspecialidades()){
				if(e.getDescricao().equals(especialidade)){
					return true;
				}
			}
		} catch (ExcecaoProfissionalLogado e1) {
			e1.printStackTrace();
		}
		return false;
	}
	
	public static boolean isEngenharia(){
		return new Parametro().isEspecialidade("Engenharia");
	}
	
	public static TipoMovimento tipoMovimentoDispensacao(){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("tipoMovimento", "Dispensação".toLowerCase());
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(to_ascii(o.descricao)) = to_ascii(:tipoMovimento)");
		return cg.consultaUnica(sb, hashMap);
	}

	public static byte[] logoTipoHU(){
		StringBuilder sb = new StringBuilder("select o.valorByte from Configuracao o where o.nome = 'Logo HU'");
		return new ConsultaGeral<byte[]>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoDoacaoRecebida(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 18");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static Unidade unidadeCPD(){
		StringBuilder sb = new StringBuilder("select o from Unidade o where o.idUnidade = 27");
		return new ConsultaGeral<Unidade>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoDispensacaoSimples(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 21");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoDevolucaoDispensacao(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 22");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoDoacaoEnviada(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 16");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoNotaFiscalEntrada(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 19");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoSemNotaFiscalEntrada(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 20");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoEntradaSemNotaFiscalAlmoxarifado(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 2");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
	}
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoNotaFiscalEntradaAlmoxarifado(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 1");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
	}
	
	private static TipoMovimento tipoMovimento(String movimento){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("dsTipoMovimento", movimento.toLowerCase());
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(to_ascii(o.descricao)) = to_ascii(:dsTipoMovimento)");
		return cg.consultaUnica(sb, hashMap);
	}
	
	private static String tipoConfiguracao(String parametro){
		ConsultaGeral<String> cg = new ConsultaGeral<String>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("dsParametro", parametro);
		StringBuilder sb = new StringBuilder("select o.valor from Configuracao o where");
		sb.append(" to_ascii(o.nome) = to_ascii(:dsParametro)");
		return cg.consultaUnica(sb, hashMap);
	}
	
	public static boolean isManutencao(){
		return Boolean.valueOf(tipoConfiguracao("Manutencao"));
	}
	
	public static String getNomeBancoFarmacia(){
		return tipoConfiguracao("NomeBancoPostgres");
	}
	
	public static String getDiretorioBackupImhotep(){
		return tipoConfiguracao("DiretorioBackupImhotep");
	}
	
	public static String getBackupAtivo(){
		return tipoConfiguracao("BackupAtivo");
	}
	
	public static String getDiretorioPostgres(){
		return tipoConfiguracao("DiretorioPostgres");
	}
	
	public static TipoMovimento tipoMovimentoEntrada(){
		return tipoMovimento("Entrada");
	}
	
	public static TipoMovimento tipoMovimentoSaida(){
		return tipoMovimento("Saida");
	}
	
	public static TipoMovimento tipoMovimentoPerda(){
		return tipoMovimento("Perda");
	}
	
	public static List<TipoMovimento> tiposMovimentoAjusteDispensacao(){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(to_ascii(o.descricao)) = lower(to_ascii('Devolução de medicamento dispensado'))");
		sb.append(" or lower(to_ascii(o.descricao)) = lower(to_ascii('Saída de medicamento dispensado'))");
		return new ArrayList<TipoMovimento>(cg.consulta(sb, null));
	}
	
	public static boolean usuarioEnfermeiroMedico(Usuario usuario){
		String usuarioEspecialidade = usuarioEspecialidade(usuario);
		return usuarioEspecialidade.equalsIgnoreCase("Enfermagem") || usuarioEspecialidade.equalsIgnoreCase("Médico");
	}
	
	public static boolean usuarioEnfermeiro(Usuario usuario){
		return usuarioEspecialidade(usuario).equalsIgnoreCase("Enfermagem");
	}
	
	public static boolean usuarioMedico(Usuario usuario){
		return usuarioEspecialidade(usuario).equalsIgnoreCase("Médico");
	}
	
	private static String usuarioEspecialidade(Usuario usuario){
//		ConsultaGeral<String> cg = new ConsultaGeral<String>();
//		HashMap<Object, Object> hm = new HashMap<Object, Object>();
//		hm.put("idUsuario", usuario.getIdUsuario());
//		StringBuilder sb = new StringBuilder("select o.especialidade.especialidadePai.descricao from Profissional o where o.usuario.idUsuario = :idUsuario");
//		return cg.consultaUnica(sb, hm);
		return "";
	}
	
	public static boolean profissionalEnfermeiroMedico(Profissional profissional){
		return false;
	}
	
	public static boolean profissionalEnfermeiro(Profissional profissional){
		return false;
	}
	
	public static boolean profissionalMedico(Profissional profissional){
		return false;
	}
}
