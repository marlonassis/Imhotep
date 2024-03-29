package br.com.imhotep.auxiliar;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class Constantes {
	public static final String PATTERN_CHAVE_VERIFICACAO_PROFISSIONAL = "{cpf}{dataNascimento}";
	
	public static final String NOME_APLICACAO = "imhotep";
	
	public static final String DIR_RELATORIO = "/WEB-INF/classes/br/com/imhotep/relatorio/";
	public static final String PAGINA_TROCA_SENHA = "/"+NOME_APLICACAO+"/PaginasWeb/ConfiguracaoUsuario/usuarioTrocaSenha.hu";
	public static final String PAGINA_HOME = "/"+NOME_APLICACAO+"/PaginasWeb/home.hu";
	public static final String PAGINA_ENTRADA_PACIENTE = "/"+NOME_APLICACAO+"/PaginasWeb/Paciente/PacienteEntrada/pacienteEntrada.hu";
	public static final String PAGINA_VIZUALIZA_PRESCRICAO = "/"+NOME_APLICACAO+"/PaginasWeb/Prescricao/ConsultaPrescricao/consultaPrescricao.hu";
	public static final String PAGINA_AJUSTE_ESTOQUE = "/"+NOME_APLICACAO+"/PaginasWeb/Movimentacao/AjusteEstoque/ajusteEstoque.hu";
	public static final String PAGINA_LOGIN = "/"+NOME_APLICACAO+"/PaginasWeb/login.hu";
	public static final String PAGINA_MANUTENCAO = "/"+NOME_APLICACAO+"/PaginasWeb/manutencao.hu";
	public static final String PAGINA_RECUSA_IEXPLORER = "/"+NOME_APLICACAO+"/PaginasWeb/recusaIExplorer.hu";
	public static final String PAGINA_RECUPERACAO_SENHA = "/"+NOME_APLICACAO+"/PaginasWeb/Publico/recuperarSenha.hu";
	public static final String PAGINA_SOLICITACAO_MATERIAL = "/"+NOME_APLICACAO+"/PaginasWeb/Solicitacoes/SolicitacaoMaterialAlmoxarifadoUnidades/solicitacaoMaterialAlmoxarifadoUnidades.hu";
	public static final String PAGINA_SOLICITACAO_MEDICAMENTO = "/"+NOME_APLICACAO+"/PaginasWeb/Solicitacoes/SolicitacaoMedicamentoUnidade/solicitacaoMedicamentoUnidade.hu";
	public static final String PAGINA_DEVOLUCAO_MATERIAL = "/"+NOME_APLICACAO+"/PaginasWeb/Almoxarifado/DevolucaoMaterial/devolucaoMaterial.hu";
	public static final String PAGINA_DEVOLUCAO_MEDICAMENTO = "/"+NOME_APLICACAO+"/PaginasWeb/Solicitacoes/DevolucaoMedicamento/devolucaoMedicamento.hu";
	
	public static final String PAINEL_MEDICAMENTO_VENCIDO = "Medicamentos Vencidos";
	public static final String PAINEL_SOLICITACOES_MEDICAMENTO_USUARIO = "Solicitações de Medicamento pelo Usuário";
	//TODO TAF 5
	public static final String PAINEL_MATERIAL_ALMOXARIFADO_VENCIDO = "Materiais Vencidos";
	
	public static final String MATERIAL_ANTIBIOTICO = "ANTIBIÓTICO";
	
	public static final String PRESCRICAO_PACIENTE_TAB = "prescricaoPacienteTab";
	public static final String PRESCRICAO_FARMACOLOGICA_TAB = "prescricaoFarmacologicaTab";
	public static final String PRESCRICAO_LIBERACAO_TAB = "prescricaoLiberacaoTab";
	public static final String PRESCRICAO_CUIDADOS_TAB = "prescricaoCuidadosTab";
	public static final String PRESCRICAO_CONFIRMACAO_TAB = "prescricaoConfirmacaoTab";
	
	public static final String RELATORIO_CUSTO_ESTOQUE_PATH = "/WEB-INF/classes/br/com/imhotep/relatorio/RelatorioCustoEstoque.jasper";
	
	public static final String IP_HU = "200.133.41.3";
	public static final String IP_IMHOTEP_REMOTO = "200.133.41.8";
	public static final String IP_LOCAL = "127.0.0.1";
	
	public static final String NOME_BANCO_IMHOTEP = "db_imhotep";
	public static final String USUARIO_BANCO = "imhotep";
	public static final String SENHA_BANCO = "##Imhotep09*123789!@#*()";
	public static final String URL_BANCO = "jdbc:postgresql://" + IP_LOCAL + ":5432/" + NOME_BANCO_IMHOTEP;
	
	public static final String NOME_BANCO_AGHU = "dbaghu";
	public static final String USUARIO_BANCO_AGHU = "ugen_integra";
	public static final String SENHA_BANCO_AGHU = "aghuintegracao";
	public static final String PORTA_BANCO_AGHU = "6544";
	public static final String IP_AGHU = "192.168.20.12";
	
	public static final String NOME_ROOT_ORGANIZACAO = "EBSERH";
	
	public static final String MENSAGEM_RELATORIO_VAZIO = "Nenhum registro encontrado.";
	
	public static final Severity ERROR  = FacesMessage.SEVERITY_ERROR;
	public static final Severity FATAL = FacesMessage.SEVERITY_FATAL;
	public static final Severity INFO  = FacesMessage.SEVERITY_INFO;
	public static final Severity WARN  = FacesMessage.SEVERITY_WARN;
	
	public static final Locale LOCALE_BRASIL = new Locale ("pt", "BR");
	
	public static final String JUSTIFICATIVA_ENTRADA_SEM_NOTA = "Entrada sem nota fiscal";
	public static final String JUSTIFICATIVA_RECUSA_SOLICITACAO_ITEM = "Solicitação Recusada";
	
	public static final Integer QUANTIDADE_BLOQUEIO_USUARIO = 5;
	public static final String SENHA_PADRAO = "123456";
	
	public static final int ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_SAIDA_ALMOXARIFADO = 4;
	public static final int ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_PERDA_ALMOXARIFADO = 5;
	public static final int ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_ALMOXARIFADO = 6;
	
	public static final int ID_TIPO_MOVIMENTO_DOACAO_ENTRADA = 18;
	public static final int ID_TIPO_MOVIMENTO_DOACAO_SAIDA = 16;
	
	public static final int ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_SAIDA_FARMACIA = 23;
	public static final int ID_TIPO_MOVIMENTO_SAIDA_AJUSTE_PERDA_FARMACIA = 25;
	public static final int ID_TIPO_MOVIMENTO_DEVOLUCAO_MEDICAMENTO = 22;
	public static final int ID_TIPO_MOVIMENTO_SAIDA_DISPENSACAO_FARMACIA = 21;
	public static final int ID_FUNCAO_ADMINISTRADOR = 5;
	public static final String IDS_MENU_CHEFIA = "7, 114, 186";
	
	public static final int ID_UNIDADE_LABORATORIO = 18;
}
