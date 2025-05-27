package mx.com.consolida.ppp.dto;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;

public class ResultadoRastreoDto {
	
	private int id;
	private OrdenPago ordenPago; 
	private String descripcionError;
		
	/**
	 * 
	 */
	public ResultadoRastreoDto() {
		//
		this.ordenPago=new OrdenPago();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrdenPago getOrdenPago() {
		return ordenPago;
	}
	public void setOrdenPago(OrdenPago ordenPago) {
		this.ordenPago = ordenPago;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	
	
	

}
