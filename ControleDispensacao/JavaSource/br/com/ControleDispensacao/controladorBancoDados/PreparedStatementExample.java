package br.com.ControleDispensacao.controladorBancoDados;

import java.sql.*;
import java.util.Properties;

	public class PreparedStatementExample {

	    public static void main(java.lang.String[] args) 
	    {
	        // Load the following from a properties object.
	        String DRIVER = "com.ibm.db2.jdbc.app.DB2Driver";
	        String URL    = "jdbc:db2://*local";

	        // Register the native JDBC driver. If the driver cannot 
	        // be registered, the test cannot continue.
	        try {
	            Class.forName(DRIVER);
	        } catch (Exception e) {
	            System.out.println("Driver failed to register.");
	            System.out.println(e.getMessage());
	            System.exit(1);
	        }

	        Connection c = null;
	        Statement s = null;

	        //    This program creates a table that is 
	        //    used by prepared statements later.
	        try {
	            // Create the connection properties.
	            Properties properties = new Properties ();
	            properties.put ("user", "userid");
	            properties.put ("password", "password");

	            // Connect to the local database.
	            c = DriverManager.getConnection(URL, properties);

	            // Create a Statement object.
	            s = c.createStatement();
	            // Delete the test table if it exists.  Note that 
	            // this example assumes throughout that the collection
	            // MYLIBRARY exists on the system.
	            try {
	                s.executeUpdate("DROP TABLE MYLIBRARY.MYTABLE");
	            } catch (SQLException e) {
	                // Just continue... the table probably did not exist.  
	            }

	            // Run an SQL statement that creates a table in the database.
	            s.executeUpdate("CREATE TABLE MYLIBRARY.MYTABLE (NAME VARCHAR(20), ID INTEGER)");

	        } catch (SQLException sqle) {
	            System.out.println("Database processing has failed.");
	            System.out.println("Reason: " + sqle.getMessage());
	        } finally {
	            // Close database resources
	            try {
	                if (s != null) {
	                    s.close();
	                }
	            } catch (SQLException e) {
	                System.out.println("Cleanup failed to close Statement.");
	            }
	        }


	        //    This program then uses a prepared statement to insert many 
	        //    rows into the database.
	        PreparedStatement ps = null;

	        //    Use a prepared statement to query the database 
	        //    table that has been created and return data from it. In
	        //    this example, the parameter used is arbitrarily set to 
	        //    5, meaning return all rows where the ID field is less than
	        //    or equal to 5.
	        try {
	            ps = c.prepareStatement("SELECT * FROM MYLIBRARY.MYTABLE " + 
	                                    "WHERE ID <= ?");

	            ps.setInt(1, 5);

	            // Run an SQL query on the table.
	            ResultSet rs = ps.executeQuery();
	            // Display all the data in the table.
	            while (rs.next()) {
	                System.out.println("Employee " + rs.getString(1) + " has ID " + rs.getInt(2));
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
	            } catch (SQLException e) {
	                System.out.println("Cleanup failed to close Connection.");
	            }

	        }
	    }
	

}
