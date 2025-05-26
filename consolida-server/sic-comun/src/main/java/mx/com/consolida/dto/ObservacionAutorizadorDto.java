/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.consolida.dto;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class ObservacionAutorizadorDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idCotizacion;
    private Long idClienteTempBitacoraCotizaciones;
    private String observacionAutorizador;
    
	public Long getIdCotizacion() {
		return idCotizacion;
	}
	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	public Long getIdClienteTempBitacoraCotizaciones() {
		return idClienteTempBitacoraCotizaciones;
	}
	public void setIdClienteTempBitacoraCotizaciones(Long idClienteTempBitacoraCotizaciones) {
		this.idClienteTempBitacoraCotizaciones = idClienteTempBitacoraCotizaciones;
	}
	public String getObservacionAutorizador() {
		return observacionAutorizador;
	}
	public void setObservacionAutorizador(String observacionAutorizador) {
		this.observacionAutorizador = observacionAutorizador;
	}

    
    
}
