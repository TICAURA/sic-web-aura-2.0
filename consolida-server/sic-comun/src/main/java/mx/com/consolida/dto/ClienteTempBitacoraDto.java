package mx.com.consolida.dto; 

import java.io.Serializable;
import java.util.Date;

public class ClienteTempBitacoraDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idClienteTempBitacora;
	private Long idClienteTemp;
	private CatTipoEventoDto tipoEvento;
	private String observacion;
	private Date fechaEvento;
	private Date fechaAlta;
	
	
	public Long getIdClienteTempBitacora() {
		return idClienteTempBitacora;
	}
	public void setIdClienteTempBitacora(Long idClienteTempBitacora) {
		this.idClienteTempBitacora = idClienteTempBitacora;
	}
	public CatTipoEventoDto getTipoEvento() {
		return tipoEvento;
	}
	public void setTipoEvento(CatTipoEventoDto tipoEvento) {
		this.tipoEvento = tipoEvento;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Date getFechaEvento() {
		return fechaEvento;
	}
	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Long getIdClienteTemp() {
		return idClienteTemp;
	}
	public void setIdClienteTemp(Long idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}
	
}
