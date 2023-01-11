package esempio;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.mariadb.jdbc.client.result.ResultSetMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.Message;

public class DatabaseManagerSingleton {

	Logger logger = LoggerFactory.getLogger(DatabaseManagerSingleton.class);
	private static DatabaseManagerSingleton instance;

	private DatabaseManagerSingleton() {
		logger.debug("Instanziato Costruttore Privato Database Manager Singleton");
	}

	public static DatabaseManagerSingleton getInstance() {
		if (instance == null) {
			instance = new DatabaseManagerSingleton();
		}
		return instance;
	}

	public ArrayList<Message> getMessages(LocalDateTime clientCall) throws ClassNotFoundException, SQLException, IOException {
		
		ArrayList<Message> messagesListToSendToCLient = new ArrayList<Message>();

//		PropertiesManagerSingleton pms = PropertiesManagerSingleton.getInstance();

		String driver = "org.mariadb.jdbc.Driver";
		Class.forName(driver);
		System.out.println("La classe " + driver + " è nel classpath");

		String url = "jdbc:mariadb://centauri.proximainformatica.com:193/academyfs07";

		Connection con = DriverManager.getConnection(url, "acfs07", "acfs07");
		
		PreparedStatement query = con.prepareStatement("SELECT * FROM messages WHERE userInsertedTime >= ?");
		query.setTimestamp(1, java.sql.Timestamp.valueOf(clientCall));
		System.out.println(query.toString());
		ResultSet rs = query.executeQuery();
		ResultSetMetaData rsd = rs.getMetaData();

		while (rs.next()) {
			Message mex = new Message();
			ArrayList<Object> array = new ArrayList<>();
			for (int i = 2; i <= rsd.getColumnCount(); i++) {
				if (rs.getObject(i) != null)
					array.add(rs.getObject(i));
			}

			mex.setUserName((String) array.get(0));
			mex.setText((String) array.get(1));

			Timestamp userTime = (Timestamp) array.get(2);
			mex.setUserInsertedTime(userTime.toLocalDateTime());

			if (array.size() == rsd.getColumnCount() - 1) {
				Timestamp serverTime = (Timestamp) array.get(3);
				mex.setUserInsertedTime(serverTime.toLocalDateTime());
			}
			System.out.println(mex.toString());
			messagesListToSendToCLient.add(mex);
		}
		return messagesListToSendToCLient;
	}
}
