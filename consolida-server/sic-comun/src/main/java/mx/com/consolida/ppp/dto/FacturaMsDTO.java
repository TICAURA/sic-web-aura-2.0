package mx.com.consolida.ppp.dto;

import java.io.Serializable;

import mx.com.documento.DocumentoMSDto;

public class FacturaMsDTO implements Serializable {
	
	private DocumentoMSDto pdf;
	private DocumentoMSDto xml;
	
	private String responseCode ="200";
	private String responseMessage ="El archivo se guardo exitosamente";
	
	public FacturaMsDTO() {
	}
	
	public FacturaMsDTO(String responseCode, String responseMessage) {
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}
	
	public DocumentoMSDto getPdf() {
		return pdf;
	}
	public void setPdf(DocumentoMSDto pdf) {
		this.pdf = pdf;
	}
	public DocumentoMSDto getXml() {
		return xml;
	}
	public void setXml(DocumentoMSDto xml) {
		this.xml = xml;
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