package mx.com.consolida.alquimia;

public class TransferenciaAlquimiaResponse {
	private String error;
	private String id_transaccion;
	private String folio_orden;
	private String message; 
	private String pendiente;
	private Object obj_res;
	private Object obj;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getId_transaccion() {
		return id_transaccion;
	}
	public void setId_transaccion(String id_transaccion) {
		this.id_transaccion = id_transaccion;
	}
	public String getFolio_orden() {
		return folio_orden;
	}
	public void setFolio_orden(String folio_orden) {
		this.folio_orden = folio_orden;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPendiente() {
		return pendiente;
	}
	public void setPendiente(String pendiente) {
		this.pendiente = pendiente;
	}
	public Object getObj_res() {
		return obj_res;
	}
	public void setObj_res(Object obj_res) {
		this.obj_res = obj_res;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
	}
