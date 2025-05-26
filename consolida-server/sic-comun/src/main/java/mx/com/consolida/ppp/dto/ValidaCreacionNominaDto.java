package mx.com.consolida.ppp.dto;

import java.io.Serializable;

public class ValidaCreacionNominaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public boolean esCliente = false;
	public String moduloClienteError;
	
	public boolean esPrestadoraServicio = false;
	public String moduloPresatdoraError;
	
	public boolean isEsCliente() {
		return esCliente;
	}
	public void setEsCliente(boolean esCliente) {
		this.esCliente = esCliente;
	}
	public String getModuloClienteError() {
		return moduloClienteError;
	}
	public void setModuloClienteError(String moduloClienteError) {
		this.moduloClienteError = moduloClienteError;
	}
	public boolean isEsPrestadoraServicio() {
		return esPrestadoraServicio;
	}
	public void setEsPrestadoraServicio(boolean esPrestadoraServicio) {
		this.esPrestadoraServicio = esPrestadoraServicio;
	}
	public String getModuloPresatdoraError() {
		return moduloPresatdoraError;
	}
	public void setModuloPresatdoraError(String moduloPresatdoraError) {
		this.moduloPresatdoraError = moduloPresatdoraError;
	}
	
	

}
