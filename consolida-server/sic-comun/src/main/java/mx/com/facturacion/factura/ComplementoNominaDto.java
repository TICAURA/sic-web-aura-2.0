package mx.com.facturacion.factura;

import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;

public class ComplementoNominaDto {
	
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaPago;
	private String fechaInicioFormato;
	private String fechaFinFormato;
	private String fechaPagoFormato;
	
	private CatGeneralDto periodicidad;
	private BigDecimal montoPPP;
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public CatGeneralDto getPeriodicidad() {
		return periodicidad;
	}
	public void setPeriodicidad(CatGeneralDto periodicidad) {
		this.periodicidad = periodicidad;
	}
	public BigDecimal getMontoPPP() {
		return montoPPP;
	}
	public void setMontoPPP(BigDecimal montoPPP) {
		this.montoPPP = montoPPP;
	}
	public Date getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getFechaInicioFormato() {
		return fechaInicioFormato;
	}
	public void setFechaInicioFormato(String fechaInicioFormato) {
		this.fechaInicioFormato = fechaInicioFormato;
	}
	public String getFechaFinFormato() {
		return fechaFinFormato;
	}
	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}
	public String getFechaPagoFormato() {
		return fechaPagoFormato;
	}
	public void setFechaPagoFormato(String fechaPagoFormato) {
		this.fechaPagoFormato = fechaPagoFormato;
	}

}
