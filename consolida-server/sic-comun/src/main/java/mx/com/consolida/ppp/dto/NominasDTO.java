package mx.com.consolida.ppp.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class NominasDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idNomina;
	private NominaClienteDto nominaClienteDto;
	private CatGeneralDto catEstatusNomina;
	private String claveNomina;
	private Date fechaInicioNomina;	
	private Date fechaFinNomina;
	private String fechaInicioNominaFormato;
	private String fechaFinNominaFormato;
	private Boolean esNominaComplementaria =false;
	private Double montoTotalPpp;
	private Boolean aFacturar =false;
	
	public NominasDTO() {
		
	}

	public Long getIdNomina() {
		return idNomina;
	}

	public void setIdNomina(Long idNomina) {
		this.idNomina = idNomina;
	}

	public NominaClienteDto getNominaClienteDto() {
		return nominaClienteDto;
	}

	public void setNominaClienteDto(NominaClienteDto nominaClienteDto) {
		this.nominaClienteDto = nominaClienteDto;
	}

	public CatGeneralDto getCatEstatusNomina() {
		return catEstatusNomina;
	}

	public void setCatEstatusNomina(CatGeneralDto catEstatusNomina) {
		this.catEstatusNomina = catEstatusNomina;
	}

	public String getClaveNomina() {
		return claveNomina;
	}

	public void setClaveNomina(String claveNomina) {
		this.claveNomina = claveNomina;
	}

	public Date getFechaInicioNomina() {
		return fechaInicioNomina;
	}

	public void setFechaInicioNomina(Date fechaInicioNomina) {
		this.fechaInicioNomina = fechaInicioNomina;
	}

	public Date getFechaFinNomina() {
		return fechaFinNomina;
	}

	public void setFechaFinNomina(Date fechaFinNomina) {
		this.fechaFinNomina = fechaFinNomina;
	}

	public String getFechaInicioNominaFormato() {
		return fechaInicioNominaFormato;
	}

	public void setFechaInicioNominaFormato(String fechaInicioNominaFormato) {
		this.fechaInicioNominaFormato = fechaInicioNominaFormato;
	}

	public String getFechaFinNominaFormato() {
		return fechaFinNominaFormato;
	}

	public void setFechaFinNominaFormato(String fechaFinNominaFormato) {
		this.fechaFinNominaFormato = fechaFinNominaFormato;
	}

	public Boolean getEsNominaComplementaria() {
		return esNominaComplementaria;
	}

	public void setEsNominaComplementaria(Boolean esNominaComplementaria) {
		this.esNominaComplementaria = esNominaComplementaria;
	}

	public Double getMontoTotalPpp() {
		return montoTotalPpp;
	}

	public void setMontoTotalPpp(Double montoTotalPpp) {
		this.montoTotalPpp = montoTotalPpp;
	}

	public Boolean getaFacturar() {
		return aFacturar;
	}

	public void setaFacturar(Boolean aFacturar) {
		this.aFacturar = aFacturar;
	}
	
	
}
