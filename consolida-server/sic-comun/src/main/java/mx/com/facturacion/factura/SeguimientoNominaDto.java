package mx.com.facturacion.factura;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;

@SuppressWarnings("serial")
public class SeguimientoNominaDto implements Serializable{
	
	private Long idCelula;
	
	private CatGeneralDto catEstatusNomina;
	
	private Date fechaInicio;
	
	private Date fechaFin;
	
	public SeguimientoNominaDto() {
		
	}
	
	public SeguimientoNominaDto(Long idCelula, CatGeneralDto catEstatusNomina, Date fechaInicio, Date fechaFin) {
		 this.idCelula = idCelula;	
		 this.catEstatusNomina = catEstatusNomina;		
		 this.fechaInicio = fechaInicio;		
		 this.fechaFin = fechaFin;	
	}

	public Long getIdCelula() {
		return idCelula;
	}

	public void setIdCelula(Long idCelula) {
		this.idCelula = idCelula;
	}


	public CatGeneralDto getCatEstatusNomina() {
		return catEstatusNomina;
	}

	public void setCatEstatusNomina(CatGeneralDto catEstatusNomina) {
		this.catEstatusNomina = catEstatusNomina;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
}
