package eu.giuseppeurso.utilities.databases;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Postgresql {
 
	static ResourceBundle settings = ResourceBundle.getBundle("configuration");
	private static String dbhost = settings.getString("psql.host");
	private static String dbport = settings.getString("psql.port");
	private static String dbname = settings.getString("psql.dbname");
	private static String dbuser = settings.getString("psql.user");
	private static String dbpasswd = settings.getString("psql.passwd");
	
	public static void main(String[] argv) {
 
		System.out.println("-------- PostgreSQL JDBC Connection Testing ------------");
 
		try {
 			Class.forName("org.postgresql.Driver");
 		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
			e.printStackTrace();
			return; 
		}
 
		System.out.println("PostgreSQL JDBC Driver Registered!");
 		Connection connection = null;
 		try {
 			connection = DriverManager.getConnection("jdbc:postgresql://"+dbhost+":"+dbport+"/"+dbname, dbuser, dbpasswd);
 		} catch (SQLException e) {
 			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 		}
 
		if (connection != null) {
			System.out.println("Connection OK.");
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
 
}