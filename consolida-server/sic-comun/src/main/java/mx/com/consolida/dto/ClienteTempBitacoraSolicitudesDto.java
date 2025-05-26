package mx.com.consolida.dto; 

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import mx.com.consolida.comunes.dto.CatEstatusDto;

public class ClienteTempBitacoraSolicitudesDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idClienteTempBitacoraSolicitudes;
	private Long idClienteTemp;
	private Long idCotizacion;
	private String  observacion;
	private CatEstatusDto catEstatus;
	private Date fechaAlta;
	private Map <String ,Object > archivo;
	private String nombreArchivo;
	private String archivoRecuperado;
	
	
	public Long getIdClienteTempBitacoraSolicitudes() {
		return idClienteTempBitacoraSolicitudes;
	}
	public void setIdClienteTempBitacoraSolicitudes(Long idClienteTempBitacoraSolicitudes) {
		this.idClienteTempBitacoraSolicitudes = idClienteTempBitacoraSolicitudes;
	}
	public Long getIdClienteTemp() {
		return idClienteTemp;
	}
	public void setIdClienteTemp(Long idClienteTemp) {
		this.idClienteTemp = idClienteTemp;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public CatEstatusDto getCatEstatus() {
		return catEstatus;
	}
	public void setCatEstatus(CatEstatusDto catEstatus) {
		this.catEstatus = catEstatus;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Long getIdCotizacion() {
		return idCotizacion;
	}
	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	public Map<String, Object> getArchivo() {
		return archivo;
	}
	public void setArchivo(Map<String, Object> archivo) {
		this.archivo = archivo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getArchivoRecuperado() {
		return archivoRecuperado;
	}
	public void setArchivoRecuperado(String archivoRecuperado) {
		this.archivoRecuperado = archivoRecuperado;
	}
			
}
