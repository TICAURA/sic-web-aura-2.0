package mx.com.consolida.reporte;

import java.io.Serializable;

public class DemoColaboradorReporteDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nombre;
	private String montoNomina;
	private String montoPPP;
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMontoNomina() {
		return montoNomina;
	}
	public void setMontoNomina(String montoNomina) {
		this.montoNomina = montoNomina;
	}
	public String getMontoPPP() {
		return montoPPP;
	}
	public void setMontoPPP(String montoPPP) {
		this.montoPPP = montoPPP;
	}
	
	
}
