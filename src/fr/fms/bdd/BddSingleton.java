package fr.fms.bdd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class BddSingleton {

	// l instance unique
	private static BddSingleton single_connection = null;

	// connection
	private Connection connection;
	public static int count;
	public static int countMethod;

	public Connection getConnection() {

		String filename = "files/config.properties";

		try (InputStream input = new FileInputStream(filename)) {

			Properties prop = new Properties();

			prop.load(input);

			// driver
			String driver = prop.getProperty("db.driver.class");
			if (driver != null)
				Class.forName(driver);

			// params
			String url = prop.getProperty("db.url");
			String login = prop.getProperty("db.login");
			String password = prop.getProperty("db.password");

			// connection
			connection = DriverManager.getConnection(url, login, password);

		} catch (IOException | SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		countMethod++;
		return connection;
	}

	// contructor privé
	private BddSingleton() {
		this.connection = getConnection();
		count++;
	}

	public static BddSingleton getInstance() {
		if (single_connection == null)
			single_connection = new BddSingleton();

		return single_connection;
	}

}
