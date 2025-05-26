package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.EmpleadoDTO;

public class DatosDispersionAlquimia implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7123110019866327609L;
	private List<EmpleadoDTO> colaboradores;
	private Long idPrestadoraServicioStp;
	
	public List<EmpleadoDTO> getColaboradores() {
		return colaboradores;
	}
	public void setColaboradores(List<EmpleadoDTO> colaboradores) {
		this.colaboradores = colaboradores;
	}
	public Long getIdPrestadoraServicioStp() {
		return idPrestadoraServicioStp;
	}
	public void setIdPrestadoraServicioStp(Long idPrestadoraServicioStp) {
		this.idPrestadoraServicioStp = idPrestadoraServicioStp;
	}

	

}
