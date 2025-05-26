package mx.com.consolida.alquimia;

public class AutorizacionTransaccionRequest {
	
	private String id_transaccion;
	private String id_cuenta;
	private String no_referencia;
	private String api_key;
	private String token_consumo;
	private String token_alquimia;
	public String getId_transaccion() {
		return id_transaccion;
	}
	public void setId_transaccion(String id_transaccion) {
		this.id_transaccion = id_transaccion;
	}
	public String getId_cuenta() {
		return id_cuenta;
	}
	public void setId_cuenta(String id_cuenta) {
		this.id_cuenta = id_cuenta;
	}
	public String getNo_referencia() {
		return no_referencia;
	}
	public void setNo_referencia(String no_referencia) {
		this.no_referencia = no_referencia;
	}
	public String getApi_key() {
		return api_key;
	}
	public void setApi_key(String api_key) {
		this.api_key = api_key;
	}
	public String getToken_consumo() {
		return token_consumo;
	}
	public void setToken_consumo(String token_consumo) {
		this.token_consumo = token_consumo;
	}
	public String getToken_alquimia() {
		return token_alquimia;
	}
	public void setToken_alquimia(String token_alquimia) {
		this.token_alquimia = token_alquimia;
	}
	
	

}
