package eu.giuseppeurso.utilities.databases;

import java.sql.*;
import java.util.ResourceBundle;

public class MicrosoftSQLServer
{
	
	static ResourceBundle settings = ResourceBundle.getBundle("configuration");
	private static String dbconnectionstring = settings.getString("msql.connectionstring");
	private static String dbuser = settings.getString("msql.user");
	private static String dbpasswd = settings.getString("msql.passwd");
	
    public static void main(String[] args)
    {
        //dbConnect("jdbc:sqlserver://"+dbhost+"\\SQLEXPRESS:"+dbport+";databaseName="+dbname+";integratedSecurity=true;",dbuser,dbpasswd);
        // db.dbConnect("jdbc:sqlserver://{computer-name}\\SQLEXPRESS:1433;databaseName=abc;integratedSecurity=true;","sa","password");
        //jdbc:sqlserver://1.1.1.1:1433;databaseName=db_test
    	dbConnect(dbconnectionstring,dbuser,dbpasswd);
    }


    /**
     * Test DB connection method 
     * @param db_connect_string
     * @param db_userid
     * @param db_password
     */
    public static void dbConnect(String db_connect_string, String db_userid, String db_password)
    {
    	System.out.println("--------------------------------------------");
    	System.out.println("Microsoft SQL Server JDBC Connection Testing");
    	
        try
        {
        	System.out.println("Chec connection string: "+ dbconnectionstring);
        	System.out.println("Username: "+dbuser);
        	System.out.println("Password: "+dbpasswd);
        	System.out.println("Trying to connect, please wait...");
            Connection conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
            System.out.println("SUCCESFULLY CONNECTED!!!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
};