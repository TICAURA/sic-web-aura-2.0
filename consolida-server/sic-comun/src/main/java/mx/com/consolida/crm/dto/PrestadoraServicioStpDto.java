package mx.com.consolida.crm.dto;

import java.io.Serializable;


public class PrestadoraServicioStpDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioStp;
	private PrestadoraServicioDto prestadoraServicioDto;
	private String nombreCentroCostos;
	private String clabeCuentaOrdenante;

	
	
	public PrestadoraServicioStpDto() {
	}



	public Long getIdPrestadoraServicioStp() {
		return idPrestadoraServicioStp;
	}



	public void setIdPrestadoraServicioStp(Long idPrestadoraServicioStp) {
		this.idPrestadoraServicioStp = idPrestadoraServicioStp;
	}



	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}



	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
	}



	public String getNombreCentroCostos() {
		return nombreCentroCostos;
	}



	public void setNombreCentroCostos(String nombreCentroCostos) {
		this.nombreCentroCostos = nombreCentroCostos;
	}



	public String getClabeCuentaOrdenante() {
		return clabeCuentaOrdenante;
	}



	public void setClabeCuentaOrdenante(String clabeCuentaOrdenante) {
		this.clabeCuentaOrdenante = clabeCuentaOrdenante;
	}


}