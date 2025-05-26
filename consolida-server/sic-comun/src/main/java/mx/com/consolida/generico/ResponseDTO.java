package mx.com.consolida.generico;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResponseDTO implements Serializable {

	private String estatusResponse = ResponseEstatusEnum.SUCCESS.name();
	private String mensajeExito;
	private String mensajeError;
	
	public String getEstatusResponse() {
		return estatusResponse;
	}
	public void setEstatusResponse(String estatusResponse) {
		this.estatusResponse = estatusResponse;
	}
	public String getMensajeExito() {
		return mensajeExito;
	}
	public void setMensajeExito(String mensajeExito) {
		this.mensajeExito = mensajeExito;
	}
	public String getMensajeError() {
		return mensajeError;
	}
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}	
}
