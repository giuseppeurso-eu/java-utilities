package eu.giuseppeurso.utilities.databases;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


/**
 * Java Mysql JDBC connection example
 */
public class Mysql {
 
	static ResourceBundle settings = ResourceBundle.getBundle("configuration");
	private static String dbhost = settings.getString("my.host");
	private static String dbport = settings.getString("my.port");
	private static String dbname = settings.getString("my.dbname");
	private static String dbuser = settings.getString("my.user");
	private static String dbpasswd = settings.getString("my.passwd");
	
	public static void main(String[] arg) {
		String query = "SELECT * FROM customers;";
		executeQuery(query);
		
	}
	
	/**
	 * Retrieves the database connection
	 */
	public static Connection getConnection(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySQL JDBC Driver not found");
			e.printStackTrace();
			return null;
		}
		System.out.println("MySQL JDBC Driver registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+dbhost+":"+dbport+"/"+dbname, dbuser, dbpasswd);
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return null;
		}
		if (connection != null) {
			System.out.println("Connection OK!");
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}
	  }
	
	public static void executeQuery(String query){
		try{  
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			
			ResultSet rs=stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			System.out.println("Executing query: "+query);
			System.out.println("");
			System.out.println(rsmd.getColumnName(1)+" | "+rsmd.getColumnName(2)+" | "+rsmd.getColumnName(3)+" | "+rsmd.getColumnName(4)+" | "+rsmd.getColumnName(5)+" | "+rsmd.getColumnName(6)+" | "+rsmd.getColumnName(7));
			System.out.println("---------------------------------------------------------------------------------");
			int results=0;
			while(rs.next()){ 
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+" "+rs.getString(6)+" "+rs.getString(7));
				results++;				
			}
			System.out.println("");
			System.out.println("Total results: "+results);
			conn.close();  
		}catch(Exception e){
			System.out.println(e);
		}  
	}
 
}