package modelo;

import java.awt.GraphicsConfiguration;
import java.sql.Date;

public class Reserva {
	
	private Integer id;
	private Date FechaEntrada;
	private Date FechaSalida;
	private String Valor;
	private String FormaPago;
	
	public Reserva(Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		super();
		this.FechaEntrada = fechaEntrada;
		this.FechaSalida = fechaSalida;
		this.Valor = valor;
		this.FormaPago = formaPago;
	}
	public Reserva(Integer id, Date fechaEntrada, Date fechaSalida, String valor, String formaPago) {
		this.id = id;
		this.FechaEntrada = fechaEntrada;
		this.FechaSalida = fechaSalida;
		this.Valor = valor;
		this.FormaPago = formaPago;
		
		// TODO Auto-generated constructor stub
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFechaEntrada() {
		return FechaEntrada;
	}
	public Date getFechaSalida() {
		return FechaSalida;
	}
	public String getValor() {
		return Valor;
	}
	public String getFormaPago() {
		return FormaPago;
	}
	
	

}
