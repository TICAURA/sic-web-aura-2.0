package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class PppColaboradorEstatusDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPppColaboradorEstatus;
	private EmpleadoDTO pppColaboradorDto;
	private Date fechaAlta;
	private String fechaAltaFormato;
	private UsuarioDTO usuarioAlta;
	private CatEstatusDto catEstatusColaborador;
	private Integer consecutivo;
	
	
	public Long getIdPppColaboradorEstatus() {
		return idPppColaboradorEstatus;
	}
	public void setIdPppColaboradorEstatus(Long idPppColaboradorEstatus) {
		this.idPppColaboradorEstatus = idPppColaboradorEstatus;
	}
	public EmpleadoDTO getPppColaboradorDto() {
		return pppColaboradorDto;
	}
	public void setPppColaboradorDto(EmpleadoDTO pppColaboradorDto) {
		this.pppColaboradorDto = pppColaboradorDto;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public CatEstatusDto getCatEstatusColaborador() {
		return catEstatusColaborador;
	}
	public void setCatEstatusColaborador(CatEstatusDto catEstatusColaborador) {
		this.catEstatusColaborador = catEstatusColaborador;
	}
	public Integer getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(Integer consecutivo) {
		this.consecutivo = consecutivo;
	}
	public String getFechaAltaFormato() {
		return fechaAltaFormato;
	}
	public void setFechaAltaFormato(String fechaAltaFormato) {
		this.fechaAltaFormato = fechaAltaFormato;
	}
	
	
	
}
