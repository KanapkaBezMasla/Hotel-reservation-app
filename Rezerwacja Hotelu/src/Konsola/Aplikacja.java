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
	public static String password = "PanMnie3-ma";
	
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
		/*
		try {
			Object newInstance = Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Blad z klas¹!");
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e1) {
			System.out.println("Blad z connection!");
			e1.printStackTrace();
		}*/
		//PreparedStatement ps = connection.prepareStatement("INSERT INTO RezerwacjaHotelu.login (PASSWORD, USERNAME) VALUES ('admin1', 'admin1');");

		//int status = ps.executeUpdate(); //jeœli dobrze zasz³o wstawinie funkcja zwróci liczbê wiêksz¹ od 0
		
		//if(status != 0) {
		//	System.out.println("DB was connected\n Wstawiono dane poprawnie.");
		//}
		
		new Logowanie();
		//do {
		//	TimeUnit.SECONDS.sleep(100);
			//System.out.println();
			//System.out.print(zalogowanoPoprawnie);
		//}while(Aplikacja.zalogowanoPoprawnie == false);
		//System.out.println("dwindwe" + zalogowanoPoprawnie);
		//if(turysta == true)
		//	new AplikacjaTurysty();
		//else
		//	new AplikacjaPracownika();
	}
	
	Aplikacja() {}


}
