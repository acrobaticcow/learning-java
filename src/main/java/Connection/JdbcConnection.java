package Connection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.jdbc.MysqlDataSource;

public class JdbcConnection {
	public static void main(String[] args) throws SQLException {
		var user = "root";
		var password = "66773508";
		var dbName = "account";
		// Connection connectionDM = getConnectionDM(user, password, dbName);
		Connection connectionDS = getConnectionDS(user, password, dbName);
		System.out.println("Is connection close " + connectionDS.isClosed());
	}

	private static void FindAndPrntPersonByName(Connection connection, String name) throws SQLException {
		String sql = "SELECT * FROM person WHERE name = ?";

		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, name);

		var resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String personName = resultSet.getString("name");
			int age = resultSet.getInt("age");
			System.out.println("ID: " + id + ", Name: " + personName + ", Age: " + age);
		}
	}

	private static void findAndPrintPersonOlderThanAge(Connection connection, int age) throws SQLException {
		String sql = "call find_person_older_than_age(?)";
		CallableStatement callableStatement = connection.prepareCall(sql);
		callableStatement.setInt(1, age);
		var resultSet = callableStatement.executeQuery();
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			int personAge = resultSet.getInt("age");
			System.out.println("ID: " + id + ", Name: " + name + ", Age: " + personAge);
		}
	}

	private static Connection getConnectionDM(String user, String password, String dbName) throws SQLException {
		String dbProvider = "mysql"; // or "postgresql", "oracle", etc.
		String hostName = "127.0.0.1"; // or your database server address
		String port = "3306"; // default MySQL port, change as needed
		var url = "jdbc:" + dbProvider + "://" + hostName + ":" + port + "/" + dbName;
		return DriverManager.getConnection(url, user, password);
	}

	private static Connection getConnectionDS(String user, String password, String dbName) throws SQLException {
		DataSource dataSource = createDataSource(user, password, dbName);
		Context ctx = createAndBindContext(dataSource, dbName);

		if (ctx == null) {
			System.err.println("Failed to create and bind context.");
			return null;
		}

		try {
			return ((DataSource) ctx.lookup("jdbc/" + dbName)).getConnection();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static DataSource createDataSource(String user, String password, String dbName) {
		// This method can be implemented to create a DataSource if needed
		// For example, using HikariCP or Apache DBCP
		var dataSource = new MysqlDataSource();
		dataSource.setPort(3306);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setDatabaseName(dbName);
		return dataSource; // Placeholder return, implement as needed
	}

	private static Context createAndBindContext(DataSource dataSource, String dbName) {
		var env = new Hashtable<String, String>(
				Map.of(Context.INITIAL_CONTEXT_FACTORY, "org.osjava.sj.SimpleContextFactory", "org.osjava.sj.root",
						"src/main/resources"));
		Context ctx;
		try {
			ctx = new InitialContext(env);
			ctx.bind("jdbc/" + dbName, dataSource);
			return ctx;
		} catch (NamingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
