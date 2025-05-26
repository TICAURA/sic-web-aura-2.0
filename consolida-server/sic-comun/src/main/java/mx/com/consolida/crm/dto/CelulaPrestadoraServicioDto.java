package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;


public class CelulaPrestadoraServicioDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Long idCelulaPrestadoraServicio;
	private CelulaDto celula;
	private PrestadoraServicioDto prestadoraServicio;
	private Date fechaAlta;
	private Long indEstatus;
	
	public CelulaPrestadoraServicioDto () {
	}
	
	public CelulaPrestadoraServicioDto (Long idCelulaPrestadoraServicio, CelulaDto celula, PrestadoraServicioDto prestadoraServicio) {
		this.idCelulaPrestadoraServicio = idCelulaPrestadoraServicio;
		this.celula = celula;
		this.prestadoraServicio = prestadoraServicio;
	}

	public Long getIdCelulaPrestadoraServicio() {
		return idCelulaPrestadoraServicio;
	}

	public void setIdCelulaPrestadoraServicio(Long idCelulaPrestadoraServicio) {
		this.idCelulaPrestadoraServicio = idCelulaPrestadoraServicio;
	}

	public CelulaDto getCelula() {
		return celula;
	}

	public void setCelula(CelulaDto celula) {
		this.celula = celula;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

}
