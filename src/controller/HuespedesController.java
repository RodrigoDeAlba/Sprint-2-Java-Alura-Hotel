package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.HuespedDAO;
import factory.ConnectionFactory;
import modelo.Huesped;

public class HuespedesController {
private HuespedDAO huespedDAO;
	
	public HuespedesController() {
		Connection connection = new ConnectionFactory().recuperarConexion();
		this.huespedDAO = new HuespedDAO(connection);
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDAO.guardar(huesped);
	}
	
	public List<Huesped> listarHuespedes() {
		return this.huespedDAO.listarHuespedes();
	}
	
	public List<Huesped> buscarId(String id) {
		return this.huespedDAO.buscarId(id);
	}
	
	public void modificar(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		this.huespedDAO.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void eliminar(Integer id) {
		this.huespedDAO.eliminar(id);
	}

}
