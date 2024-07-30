package me.adamix.mekanism.database;

import com.google.gson.Gson;
import me.adamix.mekanism.Mekanism;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.StringTemplate.STR;

public class Database {

	private final String URL;
	private final Gson gson = new Gson();

	public Database() {
		Mekanism mekanism = Mekanism.getInstance();
		String path = mekanism.getDataFolder().getAbsolutePath();
		URL = STR."jdbc:sqlite:worlds/\{path}/database.db";
	}

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL);
	}

	private void createTables() {
		String sql = """
				CREATE TABLE IF NOT EXISTS chunks(
						uuid TEXT PRIMARY KEY,
						entity TEXT
					)
				""";
		try (Connection conn = getConnection();
		     Statement statement = conn.createStatement()) {

			statement.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
