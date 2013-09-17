package br.com.imhotep.auxiliar;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class Constantes {
	public static final String PATTERN_CHAVE_VERIFICACAO_PROFISSIONAL = "{cpf}{dataNascimento}";
	
	public static final String DIR_RELATORIO = "/WEB-INF/classes/br/com/imhotep/relatorio/";
	public static final String PAGINA_TROCA_SENHA = "/imhotep/PaginasWeb/ConfiguracaoUsuario/usuarioTrocaSenha.hu";
	public static final String PAGINA_HOME = "/imhotep/PaginasWeb/home.hu";
	public static final String PAGINA_ENTRADA_PACIENTE = "/imhotep/PaginasWeb/Paciente/PacienteEntrada/pacienteEntrada.hu";
	public static final String PAGINA_VIZUALIZA_PRESCRICAO = "/imhotep/PaginasWeb/Prescricao/ConsultaPrescricao/consultaPrescricao.hu";
	public static final String PAGINA_AJUSTE_ESTOQUE = "/imhotep/PaginasWeb/Movimentacao/AjusteEstoque/ajusteEstoque.hu";
	public static final String PAGINA_LOGIN = "/imhotep/PaginasWeb/login.hu";
	public static final String PAGINA_MANUTENCAO = "/imhotep/PaginasWeb/manutencao.hu";
	public static final String PAGINA_RECUSA_IEXPLORER = "/imhotep/PaginasWeb/recusaIExplorer.hu";
	public static final String PAGINA_RECUPERACAO_SENHA = "/imhotep/PaginasWeb/Publico/recuperarSenha.hu";
	
	public static final String PAINEL_MEDICAMENTO_VENCIDO = "Medicamentos Vencidos";
	public static final String PAINEL_SOLICITACOES_MEDICAMENTO_USUARIO = "Solicitações de Medicamento pelo Usuário";
	
	public static final String MATERIAL_ANTIBIOTICO = "ANTIBIÓTICO";
	
	public static final String PRESCRICAO_PACIENTE_TAB = "prescricaoPacienteTab";
	public static final String PRESCRICAO_FARMACOLOGICA_TAB = "prescricaoFarmacologicaTab";
	public static final String PRESCRICAO_LIBERACAO_TAB = "prescricaoLiberacaoTab";
	public static final String PRESCRICAO_CUIDADOS_TAB = "prescricaoCuidadosTab";
	public static final String PRESCRICAO_CONFIRMACAO_TAB = "prescricaoConfirmacaoTab";
	
	public static final String RELATORIO_CUSTO_ESTOQUE_PATH = "/WEB-INF/classes/br/com/imhotep/relatorio/RelatorioCustoEstoque.jasper";
	
	public static final String URL_BANCO = "jdbc:postgresql://127.0.0.1:5432/db_imhotep";
	public static final String NOME_BANCO = "db_imhotep";
	public static final String USUARIO_BANCO = "imhotep";
	public static final String SENHA_BANCO = "##Imhotep09*123789!@#*()";
	
	public static final String IP_HU = "200.133.41.5";
	public static final String IP_LOCAL = "127.0.0.1";
	
	public static final String MENSAGEM_RELATORIO_VAZIO = "Nenhum registro encontrado.";
	
	public static final Severity ERROR  = FacesMessage.SEVERITY_ERROR;
	public static final Severity FATAL = FacesMessage.SEVERITY_FATAL;
	public static final Severity INFO  = FacesMessage.SEVERITY_INFO;
	public static final Severity WARN  = FacesMessage.SEVERITY_WARN;
	
	public static final Locale LOCALE_BRASIL = new Locale ("pt", "BR");
	
}
