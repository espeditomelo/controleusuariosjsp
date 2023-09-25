package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnectionBD {
	
	private static String bd = "jdbc:postgresql://localhost:5433/cursojsp?autoReconnect=true";
	private static String user = "postgres";
	private static String password = "postgres";	
	private static Connection conn = null;
	
	static {
		conectar();
	}
	
	private SingleConnectionBD() {
		conectar();
	}
	
	public static Connection getConnection() {
		return conn;
	}
		
	private static void conectar() {
		try {
			if (conn == null) {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(bd, user, password);
				conn.setAutoCommit(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
