package factory;

import java.sql.Connection;
import java.sql.SQLException;


public class TestConexión {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
    	Connection connection = connectionFactory.recuperarConexion(); 

        System.out.println("Cerrando la conexión");

        connection.close();
        
        System.out.println("Conexión cerrada");
    }

}
