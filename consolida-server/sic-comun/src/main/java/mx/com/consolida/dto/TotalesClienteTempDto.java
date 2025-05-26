package mx.com.consolida.dto;

import java.io.Serializable;

/**
 *
 * @author 
 */
public class TotalesClienteTempDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer totalProspectos;
    private Integer totalProspectosConCotizacion;
    private Integer totalProspectosEnProceso;
    private Integer totalProspectosEnCotizacion;
    private Integer totalProspectosEnAutorizacion;
    private Integer totalProspectosRechazados;
    private Integer totalProspectosAutorizados;
    private Integer totalProspectosAutorizadosMesaCtrl;
    private Integer totalProspectosDeclinados;
    private Integer totalProspectosDeclinadosMesaCtrl;
    
    
	public Integer getTotalProspectos() {
		return totalProspectos;
	}
	public void setTotalProspectos(Integer totalProspectos) {
		this.totalProspectos = totalProspectos;
	}
	public Integer getTotalProspectosEnProceso() {
		return totalProspectosEnProceso;
	}
	public void setTotalProspectosEnProceso(Integer totalProspectosEnProceso) {
		this.totalProspectosEnProceso = totalProspectosEnProceso;
	}
	public Integer getTotalProspectosEnCotizacion() {
		return totalProspectosEnCotizacion;
	}
	public void setTotalProspectosEnCotizacion(Integer totalProspectosEnCotizacion) {
		this.totalProspectosEnCotizacion = totalProspectosEnCotizacion;
	}
	public Integer getTotalProspectosEnAutorizacion() {
		return totalProspectosEnAutorizacion;
	}
	public void setTotalProspectosEnAutorizacion(Integer totalProspectosEnAutorizacion) {
		this.totalProspectosEnAutorizacion = totalProspectosEnAutorizacion;
	}
	public Integer getTotalProspectosRechazados() {
		return totalProspectosRechazados;
	}
	public void setTotalProspectosRechazados(Integer totalProspectosRechazados) {
		this.totalProspectosRechazados = totalProspectosRechazados;
	}
	public Integer getTotalProspectosAutorizados() {
		return totalProspectosAutorizados;
	}
	public void setTotalProspectosAutorizados(Integer totalProspectosAutorizados) {
		this.totalProspectosAutorizados = totalProspectosAutorizados;
	}
	public Integer getTotalProspectosAutorizadosMesaCtrl() {
		return totalProspectosAutorizadosMesaCtrl;
	}
	public void setTotalProspectosAutorizadosMesaCtrl(Integer totalProspectosAutorizadosMesaCtrl) {
		this.totalProspectosAutorizadosMesaCtrl = totalProspectosAutorizadosMesaCtrl;
	}
	public Integer getTotalProspectosDeclinados() {
		return totalProspectosDeclinados;
	}
	public void setTotalProspectosDeclinados(Integer totalProspectosDeclinados) {
		this.totalProspectosDeclinados = totalProspectosDeclinados;
	}
	public Integer getTotalProspectosDeclinadosMesaCtrl() {
		return totalProspectosDeclinadosMesaCtrl;
	}
	public void setTotalProspectosDeclinadosMesaCtrl(Integer totalProspectosDeclinadosMesaCtrl) {
		this.totalProspectosDeclinadosMesaCtrl = totalProspectosDeclinadosMesaCtrl;
	}
	public Integer getTotalProspectosConCotizacion() {
		return totalProspectosConCotizacion;
	}
	public void setTotalProspectosConCotizacion(Integer totalProspectosConCotizacion) {
		this.totalProspectosConCotizacion = totalProspectosConCotizacion;
	}
    
}
