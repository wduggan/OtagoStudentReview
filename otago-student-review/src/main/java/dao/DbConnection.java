package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class DbConnection {
    
   private static final String DEFAULT_URI = "jdbc:mysql://b21642e1e5e457:b968b3bad326309@us-cdbr-east-03.cleardb.com/heroku_1d4e86de2a10abe?reconnect=true";
   private static final String USERNAME = "b21642e1e5e457";
   private static final String PASSWORD = "b968b3bad326309 ";
   private static DriverManager manager;

   public static Connection getConnection(String uri) throws SQLException, ClassNotFoundException {
       Class.forName("com.mysql.cj.jdbc.Driver");
       return manager.getConnection(uri,USERNAME,PASSWORD);
         
   }
	
	public static String getDefaultConnectionUri(){
            return DEFAULT_URI;
        }
}