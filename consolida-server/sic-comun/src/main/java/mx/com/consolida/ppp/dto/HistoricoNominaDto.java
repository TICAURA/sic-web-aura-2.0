package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.usuario.UsuarioDTO;

public class HistoricoNominaDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNominaPpp;
	
	private String observacion;
	
	private String descEstatusNomina;
	
	private Date fechaMovimiento;
	
	private String fechaMovimientoFormato;
	
	private String nombreUsuarioMovimiento;

	public Long getIdNominaPpp() {
		return idNominaPpp;
	}

	public void setIdNominaPpp(Long idNominaPpp) {
		this.idNominaPpp = idNominaPpp;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

	public String getNombreUsuarioMovimiento() {
		return nombreUsuarioMovimiento;
	}

	public void setNombreUsuarioMovimiento(String nombreUsuarioMovimiento) {
		this.nombreUsuarioMovimiento = nombreUsuarioMovimiento;
	}

	public String getDescEstatusNomina() {
		return descEstatusNomina;
	}

	public void setDescEstatusNomina(String descEstatusNomina) {
		this.descEstatusNomina = descEstatusNomina;
	}

	public String getFechaMovimientoFormato() {
		return fechaMovimientoFormato;
	}

	public void setFechaMovimientoFormato(String fechaMovimientoFormato) {
		this.fechaMovimientoFormato = fechaMovimientoFormato;
	}
	
	

}
