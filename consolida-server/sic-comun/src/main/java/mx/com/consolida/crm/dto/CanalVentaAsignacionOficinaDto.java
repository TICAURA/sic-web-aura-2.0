package mx.com.consolida.crm.dto;

public class CanalVentaAsignacionOficinaDto {
	
	private Long idOficina;
	private String rfcOficina;
	private String razonSocial;
	
	private Long idOficinaSeleccionada;
	private Long idOficinaCanalVenta;
	
	public Long getIdOficina() {
		return idOficina;
	}
	public void setIdOficina(Long idOficina) {
		this.idOficina = idOficina;
	}
	public String getRfcOficina() {
		return rfcOficina;
	}
	public void setRfcOficina(String rfcOficina) {
		this.rfcOficina = rfcOficina;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public Long getIdOficinaSeleccionada() {
		return idOficinaSeleccionada;
	}
	public void setIdOficinaSeleccionada(Long idOficinaSeleccionada) {
		this.idOficinaSeleccionada = idOficinaSeleccionada;
	}
	public Long getIdOficinaCanalVenta() {
		return idOficinaCanalVenta;
	}
	public void setIdOficinaCanalVenta(Long idOficinaCanalVenta) {
		this.idOficinaCanalVenta = idOficinaCanalVenta;
	}
	
	
}
