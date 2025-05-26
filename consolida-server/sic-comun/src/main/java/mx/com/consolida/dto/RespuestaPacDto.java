package mx.com.consolida.dto;

import java.io.Serializable;

public class RespuestaPacDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String xmlFactura;
	private String responseCode;
	private String responseMessage;
	
	public String getXmlFactura() {
		return xmlFactura;
	}
	public void setXmlFactura(String xmlFactura) {
		this.xmlFactura = xmlFactura;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	
	
}
