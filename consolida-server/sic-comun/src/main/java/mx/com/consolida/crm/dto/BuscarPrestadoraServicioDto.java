package mx.com.consolida.crm.dto;

import java.io.Serializable;


public class BuscarPrestadoraServicioDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String rfc;
	private String nombreRazonSocial;
	
	public BuscarPrestadoraServicioDto () {
	}
	
	public BuscarPrestadoraServicioDto (String rfc, String nombreRazonSocial) {
		this.rfc = rfc;
		this.nombreRazonSocial = nombreRazonSocial;
	}


	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	

}
