package br.com.Imhotep.auxiliar;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class Constantes {
	public static final String PAGINA_TROCA_SENHA = "/Imhotep/PaginasWeb/Usuario/usuarioTrocaSenha.hu";
	public static final String PAGINA_HOME = "/Imhotep/PaginasWeb/home.hu";
	public static final String PAGINA_ENTRADA_PACIENTE = "/Imhotep/PaginasWeb/Paciente/PacienteEntrada/pacienteEntrada.hu";
	public static final String PAGINA_VIZUALIZA_PRESCRICAO = "/Imhotep/PaginasWeb/Prescricao/ConsultaPrescricao/consultaPrescricao.hu";
	public static final String PAGINA_AJUSTE_ESTOQUE = "/Imhotep/PaginasWeb/Movimentacao/AjusteEstoque/ajusteEstoque.hu";
	public static final String PAGINA_LOGIN = "/Imhotep/PaginasWeb/login.hu";
	public static final String PAGINA_MANUTENCAO = "/Imhotep/PaginasWeb/manutencao.hu";
	
	public static final String MATERIAL_ANTIBIOTICO = "ANTIBIÓTICO";
	
	public static final String PRESCRICAO_PACIENTE_TAB = "prescricaoPacienteTab";
	public static final String PRESCRICAO_FARMACOLOGICA_TAB = "prescricaoFarmacologicaTab";
	public static final String PRESCRICAO_LIBERACAO_TAB = "prescricaoLiberacaoTab";
	public static final String PRESCRICAO_CUIDADOS_TAB = "prescricaoCuidadosTab";
	public static final String PRESCRICAO_CONFIRMACAO_TAB = "prescricaoConfirmacaoTab";
	
	public static final String RELATORIO_CUSTO_ESTOQUE_PATH = "/WEB-INF/classes/br/com/Imhotep/relatorio/RelatorioCustoEstoque.jasper";
	
	public static final String URL_BANCO = "jdbc:postgresql://127.0.0.1:5432/db_imhotep";
	public static final String NOME_BANCO = "db_imhotep";
	public static final String USUARIO_BANCO = "imhotep";
	public static final String SENHA_BANCO = "!@#imhotep*()1237890";
	
	public static final String MENSAGEM_RELATORIO_VAZIO = "Nenhum registro encontrado.";
	
	public static final Severity ERROR  = FacesMessage.SEVERITY_ERROR;
	public static final Severity FATAL = FacesMessage.SEVERITY_FATAL;
	public static final Severity INFO  = FacesMessage.SEVERITY_INFO;
	public static final Severity WARN  = FacesMessage.SEVERITY_WARN;
	
}
