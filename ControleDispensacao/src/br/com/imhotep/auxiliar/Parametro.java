package br.com.imhotep.auxiliar;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
	
	public boolean isProfissionalAdministrador(){
		try {
			Profissional profissionalLogado = Autenticador.getProfissionalLogado();
			if(profissionalLogado == null){
				return false;
			}
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select exists (select g.id_lotacao_profissional_funcao from administrativo.tb_lotacao_profissional_funcao g ");
			stringBuilder.append("inner join administrativo.tb_lotacao_profissional f ");
			stringBuilder.append("on f.id_profissional = ");
			stringBuilder.append(profissionalLogado.getIdProfissional());
			stringBuilder.append("inner join administrativo.tb_estrutura_organizacional_funcao h ");
			stringBuilder.append("on h.id_estrutura_organizacional_funcao = g.id_estrutura_organizacional_funcao ");
			stringBuilder.append("where g.id_lotacao_profissional = f.id_lotacao_profissional and h.id_funcao = ");
			stringBuilder.append(Constantes.ID_FUNCAO_ADMINISTRADOR);
			stringBuilder.append(" ) as adm ");
			LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
			ResultSet rs = lm.consultar(stringBuilder.toString());
			rs.next();
			return rs.getBoolean("adm");
		} catch (ExcecaoProfissionalLogado e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
			
			
	
	private static boolean verificaCargo(String cargo){
		try{
			Autenticador autenticador = Autenticador.getInstancia();
			Profissional profissionalAtual =  autenticador == null ? null : autenticador.getProfissionalAtual();
			if(profissionalAtual != null){
				String sql = "select b.cv_nome from administrativo.tb_cargo_profissional a "+
								"inner join administrativo.tb_cargo b on a.id_cargo = b.id_cargo "+
								"inner join tb_profissional c on a.id_profissional = c.id_profissional "+
								"where c.id_profissional = "+profissionalAtual.getIdProfissional();
				LinhaMecanica lm = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL);
				List<Object[]> listaResultado = lm.getListaResultado(sql);
				for(Object[] obj : listaResultado){
					String c = String.valueOf(obj[0]);
					if(c.equalsIgnoreCase(cargo)){
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
		return verificaCargo("Teste");
	}
	
	public static boolean isUsuarioFarmaceutico(){
		return verificaCargo("Farmac");
	}
	
	public boolean getUsuarioFarmaceutico(){
		return isUsuarioFarmaceutico();
	}
	
	public boolean getUsuarioTeste(){
		return isUsuarioTeste();
	}
	
	public static boolean isUsuarioEngenheiro(){
		return verificaCargo("Engenharia");
	}
	
	public boolean getUsuarioEngenheiro(){
		return isUsuarioEngenheiro();
	}
	
	public static boolean isUsuarioMedico(){
		return verificaCargo("Médico");
	}
	
	public boolean getUsuarioMedico(){
		return isUsuarioMedico();
	}

	public static boolean isEngenharia(){
		return verificaCargo("Engenharia");
	}
	
	public static TipoMovimento tipoMovimentoDispensacao(){
		ConsultaGeral<TipoMovimento> cg = new ConsultaGeral<TipoMovimento>();
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("tipoMovimento", "Dispensação".toLowerCase());
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where");
		sb.append(" lower(to_ascii(o.descricao)) = to_ascii(:tipoMovimento)");
		return cg.consultaUnica(sb, hashMap);
	}

	public static String diretorioArquivosConsultasAGHU(){
		StringBuilder sb = new StringBuilder("select o.valor from Configuracao o where o.nome = 'PathConsultasAGHU'");
		return new ConsultaGeral<String>().consultaUnica(sb, null);
	}
	
	public static byte[] logoTipoHU(){
		StringBuilder sb = new StringBuilder("select o.valorByte from Configuracao o where o.nome = 'Logo HU'");
		return new ConsultaGeral<byte[]>().consultaUnica(sb, null);
	}
	
	public static boolean getLiberadoSolicitacaoMedicamentoForaHU(){
		String valor = null;
		try {
			ResultSet rs = new LinhaMecanica(Constantes.NOME_BANCO_IMHOTEP, Constantes.IP_LOCAL)
			.consultar("SELECT cv_valor FROM tb_configuracao where cv_nome = 'solicitacaoMedicamentoForaHU'");
			if(rs.next()){
				valor = rs.getString("cv_valor");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(valor == null || valor.equals("false")){
			return false;
		}else{
			return true;
		}
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
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoAlmoxarifadoDevolucaoDispensacao(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 7");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
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
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoDispensacaoSimplesAlmoxarifado(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 6");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
	}
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoInventarioEntradaAlmoxarifado(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 10");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
	}
	
	public static TipoMovimentoAlmoxarifado tipoMovimentoInventarioSaidaAlmoxarifado(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.idTipoMovimentoAlmoxarifado = 11");
		return new ConsultaGeral<TipoMovimentoAlmoxarifado>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoInventarioEntrada(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 30");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static TipoMovimento tipoMovimentoInventarioSaida(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimento o where o.idTipoMovimento = 29");
		return new ConsultaGeral<TipoMovimento>().consultaUnica(sb, null);
	}
	
	public static List<TipoMovimentoAlmoxarifado> tiposMovimentoEntrada(){
		StringBuilder sb = new StringBuilder("select o from TipoMovimentoAlmoxarifado o where o.tipoOperacao = 'E'");
		return new ArrayList<TipoMovimentoAlmoxarifado>(new ConsultaGeral<TipoMovimentoAlmoxarifado>().consulta(sb, null));
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
		sb.append(" lower(to_ascii(o.descricao)) = lower(to_ascii('Devolu��o de medicamento dispensado'))");
		sb.append(" or lower(to_ascii(o.descricao)) = lower(to_ascii('Sa�da de medicamento dispensado'))");
		return new ArrayList<TipoMovimento>(cg.consulta(sb, null));
	}
	
	public static boolean usuarioEnfermeiroMedico(Usuario usuario){
		String usuarioEspecialidade = usuarioEspecialidade(usuario);
		return usuarioEspecialidade.equalsIgnoreCase("Enfermagem") || usuarioEspecialidade.equalsIgnoreCase("M�dico");
	}
	
	public static boolean usuarioEnfermeiro(Usuario usuario){
		return usuarioEspecialidade(usuario).equalsIgnoreCase("Enfermagem");
	}
	
	public static boolean usuarioMedico(Usuario usuario){
		return usuarioEspecialidade(usuario).equalsIgnoreCase("M�dico");
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
