package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;


public class ConnectionFactory {
	public DataSource dataSource;
	
	public ConnectionFactory() {
		ComboPooledDataSource combopooled = new ComboPooledDataSource();
		combopooled.setJdbcUrl("jdbc:mysql://localhost/alura hotel?useTimeZone=true&serverTimezone=UTC");
		combopooled.setUser("root");
		combopooled.setPassword("DannyBell1");
		combopooled.setMaxPoolSize(10);
		
		this.dataSource = combopooled;
	}
	
	public Connection recuperarConexion() {
		try {
			return this.dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
