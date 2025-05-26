package mx.com.consolida.conciliaciones;

import java.util.Date;

public class ClientesDetalleDto {
	private static final long serialVersionUID = 1L;
	private String razonSocial;
	private String rfc;
	private String prestadoraServicio;
	private Date fechaOperacion;
	private Double monto;
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getPrestadoraServicio() {
		return prestadoraServicio;
	}
	public void setPrestadoraServicio(String prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	

}
