package dao;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Huesped;

public class HuespedDAO {
private Connection connection;
	
	public HuespedDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Huesped huesped) {
		String sql = "INSERT INTO huespedes (Nombre,Apellido,FechaNacimiento,Nacionalidad, Telefono, IdReserva)"
				+ "VALUES(?,?,?,?,?,?)";
		try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setString(1, huesped.getNombre());
			pstm.setString(2, huesped.getApellido());
			pstm.setDate(3, huesped.getFechaNacimiento());
			pstm.setString(4, huesped.getNacionalidad());
			pstm.setString(5, huesped.getTelefono());
			pstm.setInt(6, huesped.getIdReserva());
			
			
			
			pstm.executeUpdate();
			
			try (ResultSet rst = pstm.getGeneratedKeys()){
				while (rst.next()) {
					huesped.setId(rst.getInt(1));
				}
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> listarHuespedes() {
        List<Huesped> huesped = new ArrayList<>();

        try {
            final PreparedStatement statement = connection
                    .prepareStatement("SELECT Id, Nombre, Apellido, FechaNacimiento, Nacionalidad, Telefono, IdReserva FROM huespedes");
    
            try (statement) {
                statement.execute();
    
                ResultSet resultSet = statement.getResultSet();
    
                try (resultSet) {
                    while (resultSet.next()) {
                        huesped.add(new Huesped(
                                resultSet.getInt("Id"),
                                resultSet.getString("Nombre"),
                                resultSet.getString("Apellido"),
                                resultSet.getDate("FechaNacimiento"),
                                resultSet.getString("Nacionalidad"),
                                resultSet.getString("Telefono"),
                                resultSet.getInt("IdReserva"))
                        		);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return huesped;
    }
	
	public List<Huesped> buscarId(String id) {
		List<Huesped> huesped = new ArrayList<Huesped>();
		try {

			String sql = "SELECT id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva FROM huespedes WHERE idReserva = ?";

			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();

				transformarResultSetEnHuesped(huesped, pstm);
			}
			return huesped;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void modificar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idReserva, Integer id) {
        try (PreparedStatement pstm = connection
        		.prepareStatement(
                    "UPDATE huespedes SET, nombre = ?, apellido = ?, fechaNacimiento = ?, nacionalidad = ?, telefono = ?, idReserva = ? WHERE id = ?")) {
            	pstm.setString(1, nombre);
            	pstm.setString(2, apellido);
            	pstm.setDate(3, fechaNacimiento);
            	pstm.setString(4, nacionalidad);
            	pstm.setString(5, telefono);
            	pstm.setInt(6, idReserva);
            	pstm.setInt(7, id);
            	pstm.execute();
            
       } catch (SQLException e) {
                    	throw new RuntimeException(e);
                    }
	}
	
	public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM huespedes WHERE ID = ?");

            try (statement) {
                statement.setInt(1, id);
                statement.execute();

                int updateCount = statement.getUpdateCount();

                return updateCount;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } 
	
	private void transformarResultSetEnHuesped(List<Huesped> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Huesped huesped = new Huesped(rst.getInt(1), rst.getString(2), rst.getString(3), rst.getDate(4), rst.getString(5), rst.getString(6), rst.getInt(7));
				reservas.add(huesped);
			}
		}				
	}

}
