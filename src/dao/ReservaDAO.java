package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import modelo.Reserva;

public class ReservaDAO {
	
	private Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		String sql = "INSERT INTO reservas (FechaEntrada,FechaSalida,Valor,FormaPago)"
				+ "VALUES(?,?,?,?)";
		try (PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstm.setDate(1, reserva.getFechaEntrada());
			pstm.setDate(2, reserva.getFechaSalida());
			pstm.setString(3, reserva.getValor());
			pstm.setString(4, reserva.getFormaPago());
			
			pstm.executeUpdate();
			
			try (ResultSet rst = pstm.getGeneratedKeys()){
				while (rst.next()) {
					reserva.setId(rst.getInt(1));
				}
				
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscar() {
        List<Reserva> reserva = new ArrayList<>();

        try {
            final PreparedStatement statement = connection
                    .prepareStatement("SELECT Id, FechaEntrada, FechaSalida, Valor, FormaPago FROM reservas");
    
            try (statement) {
                statement.execute();
    
                final ResultSet resultSet = statement.getResultSet();
    
                try (resultSet) {
                    while (resultSet.next()) {
                        reserva.add(new Reserva(
                                resultSet.getInt("Id"),
                                resultSet.getDate("FechaEntrada"),
                                resultSet.getDate("FechaSalida"),
                                resultSet.getString("Valor"),
                                resultSet.getString("FormaPago")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reserva;
    }
	
	public List<Reserva> buscarId(String Id) {
		List<Reserva> reserva = new ArrayList<>();
		try {
			String sql = "SELECT Id, FechaEntrada, FechaSalida, Valor, FormaPago FROM reservas";
			
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				
				transformarResultSetEnReserva(reserva, pstm);
			}
			return reserva;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void modificar(Date fechaEntrada, Date fechaSalida, String valor, String formaPago, Integer id) {
		try (PreparedStatement stm = connection
				.prepareStatement("UPDATE reservas SET fechaEntrada = ?, fechaSalida = ?, valor = ?, formaPago = ? WHERE id = ?")) {
			stm.setDate(1, fechaEntrada);
			stm.setDate(2, fechaSalida);
			stm.setString(3, valor);
			stm.setString(4, formaPago);
			stm.setInt(5, id);
			stm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public int eliminar(Integer id) {
        try {
            final PreparedStatement statement = connection.prepareStatement("DELETE FROM reservas WHERE ID = ?");

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
	
	private void transformarResultSetEnReserva(List<Reserva> reserva, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Reserva produto = new Reserva(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getString(4), rst.getString(5));

				reserva.add(produto);
			}
		}
	}

}
