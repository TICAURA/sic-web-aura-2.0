package mx.com.consolida.ppp.dto;

import com.lgec.enlacefi.spei.integration.h2h.OrdenPagoWS;

public class ResultadoRastreoDto {
	
	private int id;
	private OrdenPagoWS ordenPago; 
	private String descripcionError;
		
	/**
	 * 
	 */
	public ResultadoRastreoDto() {
		//
		this.ordenPago=new OrdenPagoWS();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public OrdenPagoWS getOrdenPago() {
		return ordenPago;
	}
	public void setOrdenPago(OrdenPagoWS ordenPago) {
		this.ordenPago = ordenPago;
	}
	public String getDescripcionError() {
		return descripcionError;
	}
	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}
	
	
	
	

}
