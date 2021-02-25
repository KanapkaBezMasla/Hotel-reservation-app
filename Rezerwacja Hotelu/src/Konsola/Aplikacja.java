package Konsola;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;



public class Aplikacja {

	public static final Aplikacja INSTANCE = new Aplikacja();
	
	public static String login = "_";
	public static boolean turysta = false;
	public static boolean pracownik = false;
	public static boolean zalogowanoPoprawnie;
	public static Connection connection = null;
	public static String databaseName = "";
	public static String url = "jdbc:mysql://localhost:3306/" + databaseName;
	public static String username = "root";
	public static String password = "Tu nalezy wpisac haslo";
	
	public static Connection getConnection() {

	    Connection cnx = null;
	    MysqlDataSource datasource = new MysqlDataSource();
	    datasource.setServerName("localhost");
	    datasource.setUser(username);
	    datasource.setPassword(password);
	    datasource.setDatabaseName("RezerwacjaHotelu");
	    datasource.setPortNumber(3306);

	    try {
	        cnx = datasource.getConnection();
	    } catch (SQLException ex) {

	    }

	    return cnx;

	}
	
	public static void main(String[] args) throws SQLException, InterruptedException {	
		new Logowanie();
	}
	
	Aplikacja() {}


}
