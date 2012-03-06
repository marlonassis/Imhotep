package br.com.ControleDispensacao.relatorio;

	import java.io.Serializable;
	import java.util.Date;
	import java.util.HashMap;
	import java.util.List;
	import java.util.TimeZone;
	import javax.annotation.PostConstruct;
	import javax.annotation.PreDestroy;
	import javax.faces.bean.ManagedBean;
	import javax.faces.bean.ViewScoped;
	import javax.faces.context.FacesContext;
	import javax.servlet.ServletContext;
	import javax.servlet.http.HttpServletResponse;
//	import net.sf.jasperreports.engine.JasperExportManager;
//	import net.sf.jasperreports.engine.JasperFillManager;
//	import net.sf.jasperreports.engine.JasperPrint;

	public class SegundoPDF {

//	    private TimeZone timeZone = TimeZone.getDefault();
//	    private JasperPrint impressao;
//	    private HashMap<String, Serializable> parametroMap;
//	    private FacesContext context;
//	    private ServletContext servletContext;
//	    private String mensagem, caminhoRelatorio;
//	    private Date dataInicio, dataFim;
//	    private boolean exibeDialog, exibeTabelaCliente;
//	    private Cliente cliente;
//	    private List<Cliente> listaClientes;
//
//	    @PostConstruct
//	    public void init() {
//	        // Chamando ao instanciar o escopo de visao 
//	        cliente = new Cliente();
//	    }
//
//	    @PreDestroy
//	    public void destroy() {
//	        // A chamada eh realizada quando a pagina eh alterada 
//	    }
//
//	    public String getMensagem() {
//	        return mensagem;
//	    }
//
//	    public void setMensagem(String mensagem) {
//	        this.mensagem = mensagem;
//	    }
//
//	    public boolean isExibeDialog() {
//	        return exibeDialog;
//	    }
//
//	    public void setExibeDialog(boolean exibeDialog) {
//	        this.exibeDialog = exibeDialog;
//	    }
//
//	    public boolean isExibeTabelaCliente() {
//	        return exibeTabelaCliente;
//	    }
//
//	    public void setExibeTabelaCliente(boolean exibeTabelaCliente) {
//	        this.exibeTabelaCliente = exibeTabelaCliente;
//	    }
//
//	    public Date getDataInicio() {
//	        return dataInicio;
//	    }
//
//	    public void setDataInicio(Date dataInicio) {
//	        this.dataInicio = dataInicio;
//	    }
//
//	    public Date getDataFim() {
//	        return dataFim;
//	    }
//
//	    public void setDataFim(Date dataFim) {
//	        this.dataFim = dataFim;
//	    }
//
//	    public TimeZone getTimeZone() {
//	        return timeZone;
//	    }
//
//	    public Cliente getCliente() {
//	        return cliente;
//	    }
//
//	    public void setCliente(Cliente cliente) {
//	        this.cliente = cliente;
//	    }
//
//	    public List<Cliente> getListaClientes() {
//	        return listaClientes;
//	    }
//
//	    public void setListaClientes(List<Cliente> listaClientes) {
//	        this.listaClientes = listaClientes;
//	    }
//
//	    public void carregaClientesPeloNome() {
//	        ClienteController clienteController = new ClienteController();
//	        clienteController.carregaClientesPeloNome(cliente.getNmCliente());
//	        setListaClientes(clienteController.getListaClientes());
//	    }
//
//	    public String montaSQL(Integer idCliente, String dataInicio, String dataFim) {
//	        String sql = " 1 = 1";
//
//	        if (!dataInicio.isEmpty() && !dataFim.isEmpty()) {
//	            sql += " AND ( ped.DATA_EMISSAO BETWEEN STR_TO_DATE('" + dataInicio + "', '%d/%m/%Y') AND STR_TO_DATE('" + dataFim + "', '%d/%m/%Y') )";
//	        }
//
//	        if (idCliente != null) {
//	            sql += " AND cli.ID_CLIENTE = '" + idCliente + "'";
//	        }
//
//	        sql += " GROUP BY cli.ID_CLIENTE, ped.ID_PEDIDO, prod.ID_PRODUTO, pp.ID_PED_PROD";
//
//	        return sql;
//	    }
//
//	    public void geraRelatorio() {
//	        context = FacesContext.getCurrentInstance();
//	        servletContext = (ServletContext) context.getExternalContext().getContext();
//
//	        caminhoRelatorio = servletContext.getRealPath("/relatorios/jasper/RelatorioPedidosCliente.jasper");
//	        mensagem += "Caminho do relatório: " + caminhoRelatorio + "\n ";
//
//	        mensagem += "Sistema Operacional: " + Funcoes.retornaSistemaOperacional().toLowerCase() + "\n ";
//
//	        parametroMap = new HashMap<String, Serializable>();
//	        String sql = montaSQL(cliente.getIdCliente(),
//	                dataInicio != null ? Funcoes.formataDataEmString(dataInicio) : "",
//	                dataFim != null ? Funcoes.formataDataEmString(dataFim) : "");
//	        parametroMap.put("sql", sql);
//
//	        /* criando novo cliente apos passagem de parametro para nova pesquisa */
//	        cliente = new Cliente();
//	        String dataIni = dataInicio != null ? Funcoes.formataDataEmString(dataInicio) : "00/00/0000";
//	        String dataF = dataFim != null ? Funcoes.formataDataEmString(dataFim) : "00/00/0000";
//	        String periodo = "Período de: " + dataIni + " a " + dataF;
//	        parametroMap.put("periodo", periodo);
//	        setExibeDialog(true);
//	        enviarPdf();
//	    }
//
//	    public void enviarPdf() {
//	        // Carrega o xml de definição do relatório
//	        try {
//	            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
//	            // Configura o response para suportar o relatório
//	            response.setContentType("application/pdf");
//	            // response.addHeader("Content-disposition", "inline; filename=\"arquivo.pdf\"");
//	            response.addHeader("Content-disposition", "attachment; filename=\"arquivo.pdf\"");
//
//	            // Preenche o relatório com os parametros e o data source
//	            impressao = JasperFillManager.fillReport(caminhoRelatorio, parametroMap, HibernateUtil.recuperaConexao());
//	            // Exporta o relatório
//	            JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());
//	            // Salva o estado da aplicação no contexto do JSF
//	            context.getApplication().getStateManager().saveView(context);
//	            // Fecha o stream do response
//	            context.responseComplete();
//	        } catch (Exception e) {
//	            setExibeDialog(true);
//	            mensagem += e;
//	        }
//	    }

	
}
