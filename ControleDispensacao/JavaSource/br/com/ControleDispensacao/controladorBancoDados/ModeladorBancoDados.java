package br.com.ControleDispensacao.controladorBancoDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.remendo.gerenciador.GerenciadorConexao;

@ManagedBean(name="modeladorBancoDados")
@SessionScoped
public class ModeladorBancoDados extends GerenciadorConexao{
	private String tabelaEdicao;
	
	private String driver = "org.postgresql.Driver";
	private String url    = "jdbc:postgresql://127.0.0.1:5432/db_farmacia";

	private Connection c = null;
	private Statement s = null;
	
	private Tabela tabela = new Tabela();
	private PropriedadeTabela propriedadeTabela = new PropriedadeTabela();
	private ConstraintTabela constraintTabela = new ConstraintTabela();
	private List<String> comandosExecutar = new ArrayList<String>();
	private List<PropriedadeTabela> propriedadesReferencia = new ArrayList<PropriedadeTabela>();
	
	public void adicionarConstraint(){
		String inserirConstraint = "alter table public."+getTabela().getNomeTabela()+" add ";
		comandosExecutar.add(gerarConstraint(getConstraintTabela(), inserirConstraint));
		getTabela().getConstraints().add(getConstraintTabela());
		setConstraintTabela(new ConstraintTabela());
	}
	
	public void limparDados(){
		tabela = new Tabela();
		propriedadeTabela = new PropriedadeTabela();
		comandosExecutar = new ArrayList<String>();
		tabelaEdicao = null;
	}
	
	public void executarSqls(){
		for(String query : comandosExecutar)
			executarQuery(query);
		comandosExecutar = new ArrayList<String>();
	}
	
	public void removerPropriedade(PropriedadeTabela prop){
		comandosExecutar.add("alter table public."+getTabela().getNomeTabela()+" drop column "+prop.getNomePropriedade()+";");
		getTabela().getPropriedades().remove(prop);
	}
	
	public void adicionarPropriedade(){
		comandosExecutar.add("alter table public."+getTabela().getNomeTabela()+" add column "+getPropriedadeTabela().getNomePropriedade()+" "+getPropriedadeTabela().getTipoPropriedade()+" "+(!getPropriedadeTabela().isNulo() ? " not null;" : ";"));
		getTabela().getPropriedades().add(getPropriedadeTabela());
		setPropriedadeTabela(new PropriedadeTabela());
	}
	
	public void editarTabela(){
		setTabela(new Tabela());
		getTabela().setNomeTabela(getTabelaEdicao());
		carregaColunas(getTabela());
	}
	
	public void carregarPropriedadesReferencia(){
		Tabela tabela = new Tabela();
		tabela.setNomeTabela(getConstraintTabela().getNomeTabelaReferencia());
		carregaColunas(tabela);
		setPropriedadesReferencia(tabela.getPropriedades());
	}
	
	public void criarTabela(){
		String query = "create table public."+getTabela().getNomeTabela();
		query = montaSqlCriarTabelaCompleta(query, getTabela());
		executarQuery(query);
		comandosExecutar = new ArrayList<String>();
		setTabelaEdicao(getTabela().getNomeTabela());
	}
	
	public void apagarTabela(){
		String query = "DROP TABLE public."+getTabelaEdicao();
		executarQuery(query);
	}

	private String gerarConstraint(ConstraintTabela constraintTabela2, String constraintInicial) {
		constraintInicial = " constraint "+constraintTabela2.getNomeConstraint();
		if(constraintTabela2.getTipoConstraint().equals("PRIMARY KEY")){
			constraintInicial = constraintInicial.concat(" PRIMARY KEY (");
			constraintInicial = constraintInicial.concat(constraintTabela2.getNomePropriedadeLocal());
			constraintInicial = constraintInicial.concat(")");
		}else{
			if(constraintTabela2.getTipoConstraint().equals("FOREIGN KEY")){
				constraintInicial = constraintInicial.concat(" FOREIGN KEY (");
				constraintInicial = constraintInicial.concat(constraintTabela2.getNomePropriedadeLocal());
				constraintInicial = constraintInicial.concat(") ");
				constraintInicial = constraintInicial.concat("references ");
				constraintInicial = constraintInicial.concat(constraintTabela2.getNomeTabelaReferencia());
				constraintInicial = constraintInicial.concat(" (").concat(constraintTabela2.getNomePropriedadeLocal()).concat(")");
				constraintInicial = constraintInicial.concat(" match simple on update ");
				constraintInicial = constraintInicial.concat(constraintTabela2.getTipoUpdate());
				constraintInicial = constraintInicial.concat("on delete ");
				constraintInicial = constraintInicial.concat(constraintTabela2.getTipoDelete());
			}else{
				if(constraintTabela2.getTipoConstraint().equals("UNIQUE")){
					constraintInicial = constraintInicial.concat(" UNIQUE (");
					constraintInicial = constraintInicial.concat(constraintTabela2.getNomePropriedadeLocal());
					constraintInicial = constraintInicial.concat(") ");
				}
			}
		}
		
		return constraintInicial;
	}
	
	private String montaSqlCriarTabelaCompleta(String sqlInicial, Tabela tabela){
		String sqlParcial = gerarSqlPropriedades(sqlInicial, tabela);
		sqlParcial = sqlParcial.concat(gerarSqlConstraint(", ", tabela));
		return sqlParcial;
	}
	
	private String gerarSqlConstraint(String sqlInicial, Tabela tabela) {
		String sqlCompleto = sqlInicial;
		int i = 0;
		int size = tabela.getConstraints().size();
		while(size > i){
			ConstraintTabela cons = tabela.getConstraints().get(i);
			sqlCompleto = sqlCompleto.concat(gerarConstraint(cons, ""));
			sqlCompleto = sqlCompleto.concat(((size - 1) == i) ? ")" : ",");
			i++;
		}
		return sqlCompleto;
	}
	
	private String gerarSqlPropriedades(String sqlInicial, Tabela tabela) {
		String sqlCompleto = sqlInicial;
		sqlCompleto = sqlCompleto.concat("( ");
		int i = 0;
		int size = tabela.getPropriedades().size();
		while(size > i){
			PropriedadeTabela prop = tabela.getPropriedades().get(i);
			
			sqlCompleto = sqlCompleto.concat(prop.getNomePropriedade()).concat(" ");
			sqlCompleto = sqlCompleto.concat(prop.getTipoPropriedade()).concat(" ");
			sqlCompleto = sqlCompleto.concat(prop.isNulo() ? "" : " not null");
			sqlCompleto = sqlCompleto.concat(((size - 1) == i) ? "" : ",");
			i++;
		}
		return sqlCompleto;
	}
	
	private void carregaColunas(Tabela tabela){
		registrarDriver();
        PreparedStatement ps = null;
        try {
        	String sql = "SELECT a.column_name, a.data_type, a.is_nullable FROM information_schema.columns a " +
        				 "WHERE a.table_schema = 'public' " +
        				 "AND a.table_name = '"+tabela.getNomeTabela()+"'; ";
        	c = createConnection();
        	s = c.createStatement();
            ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	PropriedadeTabela pt = new PropriedadeTabela();
            	pt.setNomePropriedade(rs.getString(1));
            	pt.setTipoPropriedade(rs.getString(2));
            	pt.setNulo(rs.getBoolean(3));
            	tabela.getPropriedades().add(pt);
            }
        } catch (SQLException sqle) {
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
        } finally {
            // Close database resources
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Statement.");
            }

            try {
                if (c != null) {
                    c.close();
                }
                if (s != null) {
                    s.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Connection.");
            }

        }
		
	}
	
	public List<String> getTabelas(){
		registrarDriver();
        PreparedStatement ps = null;
        List<String> listaRet = null;
        try {
        	String sql = "SELECT a.tablename FROM pg_catalog.pg_tables a "+
					 "WHERE a.schemaname NOT IN ('pg_catalog', 'information_schema', 'pg_toast') "+
					 "ORDER BY a.tablename";
        	c = createConnection();
        	s = c.createStatement();
            ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            listaRet = new ArrayList<String>();
            while (rs.next()) {
            	listaRet.add(rs.getString(1));
            }
        } catch (SQLException sqle) {
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
        } finally {
            // Close database resources
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Statement.");
            }

            try {
                if (c != null) {
                    c.close();
                }
                if (s != null) {
                    s.close();
                }
            } catch (SQLException e) {
                System.out.println("Cleanup failed to close Connection.");
            }

        }
		
        return listaRet;
	}

	private void executarQuery(String query) {
		registrarDriver();
        try {
        	c = createConnection();
        	s = c.createStatement();
			s.executeUpdate(query);
        } catch (SQLException sqle) {
        	super.mensagem("Falha no processamento no banco de dados", null, FacesMessage.SEVERITY_FATAL);
            System.out.println("Database processing has failed.");
            System.out.println("Reason: " + sqle.getMessage());
        } finally {
            try {
                if (s != null) {
                    s.close();
                }
                if (c != null) {
                    c.close();
                }
            } catch (SQLException e) {
            	super.mensagem("Falha ao fechar o statement.", null, FacesMessage.SEVERITY_FATAL);
                System.out.println("Cleanup failed to close Statement.");
            }
        }
	}

	private Connection createConnection() throws SQLException {
		// Create the connection properties.
		Properties properties = new Properties ();
		properties.put ("user", "postgres");
		properties.put ("password", "postgres");

		// Connect to the local database.
		return DriverManager.getConnection(url, properties);
	}

	private void registrarDriver() {
		// Register the native JDBC driver. If the driver cannot 
        // be registered, the test cannot continue.
        try {
            Class.forName(driver);
        } catch (Exception e) {
        	super.mensagem("Driver não encontrado", null, FacesMessage.SEVERITY_FATAL);
            System.out.println("Driver failed to register.");
            System.out.println(e.getMessage());
            System.exit(1);
        }
	}
	
	
	public String getTabelaEdicao() {
		return tabelaEdicao;
	}

	public void setTabelaEdicao(String tabelaEdicao) {
		this.tabelaEdicao = tabelaEdicao;
	}

	public Tabela getTabela() {
		return tabela;
	}

	public void setTabela(Tabela tabela) {
		this.tabela = tabela;
	}

	public PropriedadeTabela getPropriedadeTabela() {
		return propriedadeTabela;
	}

	public void setPropriedadeTabela(PropriedadeTabela propriedadeTabela) {
		this.propriedadeTabela = propriedadeTabela;
	}

	public ConstraintTabela getConstraintTabela() {
		return constraintTabela;
	}

	public void setConstraintTabela(ConstraintTabela constraintTabela) {
		this.constraintTabela = constraintTabela;
	}

	public List<PropriedadeTabela> getPropriedadesReferencia() {
		return propriedadesReferencia;
	}

	public void setPropriedadesReferencia(List<PropriedadeTabela> propriedadesReferencia) {
		this.propriedadesReferencia = propriedadesReferencia;
	}
}
