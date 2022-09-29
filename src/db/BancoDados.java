package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class BancoDados {

	@SuppressWarnings("unused")
	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			Properties properties = loadProperties();
			String urlBanco = properties.getProperty("dburl");
			connection =  DriverManager.getConnection(urlBanco, properties);
		}catch(SQLException erro){
			throw new BDadosException(erro.getMessage());
		}
		return connection;
	}

	private static Properties loadProperties() {
		try (FileInputStream file = new FileInputStream("")) {
			Properties properties = new Properties();
			properties.load(file);
			return properties;
		} catch (IOException erro) {
			throw new BDadosException(erro.getMessage());
		}
	}

	public static void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException erro) {
				throw new BDadosException(erro.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet result) {
		if (result != null) {
			try {
				result.close();
			} catch (SQLException erro) {
				throw new BDadosException(erro.getMessage());
			}
		}
	}
}
