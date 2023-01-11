package esempio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDBConnection {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	//driver di tipo “bridge jdbc-odbc
	String driver = "org.mariadb.jdbc.Driver";
	Class.forName(driver);
	System.out.println("la classe " + driver + " è nel calss path");
	
	//stringa di connessione
	String url = "jdbc:mariadb://centauri.proximainformatica.com:193/";
	//connessione con username e password
	Connection con = DriverManager.getConnection (url, "acfs07", "acfs07");
	
	System.out.println("sono riuscacfs07ito a connettermi all'url " + url);
	//inizio codice
	
	
	
	//fine codice
	con.close();
	System.out.println("connessione chiusa");
	
	}
}
