package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */

public class CanalVentaDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idCanalVenta;
    private CatGeneralDto tipoCanalVenta;
    private String razonSocial;
    private String generaCotizacion;
    private String empresaOficina;
    private String pagoOficina;
    private String nombreCompleto;
    
    private CatGeneralDto entidadFederativaSede;
    private String ciudadSede;
    
    private Date fechaAlta;
    private Date fechaModificaicon;
    private String indEstatus;
    
    private DomicilioDto domicilio;
    private UsuarioDTO usuario;
    private CuentaBancariaDto cuentaBancaria;
    
    private Long idusuarioAlta;
    private Long idUsuarioCanalVenta;
    
    private OficinaDto oficina;

    public CanalVentaDto() {
    }

    public CanalVentaDto(Long idCanalVenta) {
        this.idCanalVenta = idCanalVenta;
    }


	public Long getIdCanalVenta() {
		return idCanalVenta;
	}

	public void setIdCanalVenta(Long idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getGeneraCotizacion() {
		return generaCotizacion;
	}

	public void setGeneraCotizacion(String generaCotizacion) {
		this.generaCotizacion = generaCotizacion;
	}

	public String getEmpresaOficina() {
		return empresaOficina;
	}

	public void setEmpresaOficina(String empresaOficina) {
		this.empresaOficina = empresaOficina;
	}

	public String getPagoOficina() {
		return pagoOficina;
	}

	public void setPagoOficina(String pagoOficina) {
		this.pagoOficina = pagoOficina;
	}

	public String getCiudadSede() {
		return ciudadSede;
	}

	public void setCiudadSede(String ciudadSede) {
		this.ciudadSede = ciudadSede;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaModificaicon() {
		return fechaModificaicon;
	}

	public void setFechaModificaicon(Date fechaModificaicon) {
		this.fechaModificaicon = fechaModificaicon;
	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}


	public CatGeneralDto getTipoCanalVenta() {
		return tipoCanalVenta;
	}

	public void setTipoCanalVenta(CatGeneralDto tipoCanalVenta) {
		this.tipoCanalVenta = tipoCanalVenta;
	}

	public CatGeneralDto getEntidadFederativaSede() {
		return entidadFederativaSede;
	}

	public void setEntidadFederativaSede(CatGeneralDto entidadFederativaSede) {
		this.entidadFederativaSede = entidadFederativaSede;
	}

	public DomicilioDto getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioDto domicilio) {
		this.domicilio = domicilio;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public CuentaBancariaDto getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancariaDto cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public Long getIdusuarioAlta() {
		return idusuarioAlta;
	}

	public void setIdusuarioAlta(Long idusuarioAlta) {
		this.idusuarioAlta = idusuarioAlta;
	}

	public Long getIdUsuarioCanalVenta() {
		return idUsuarioCanalVenta;
	}

	public void setIdUsuarioCanalVenta(Long idUsuarioCanalVenta) {
		this.idUsuarioCanalVenta = idUsuarioCanalVenta;
	}
	
	
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public OficinaDto getOficina() {
		return oficina;
	}

	public void setOficina(OficinaDto oficina) {
		this.oficina = oficina;
	}

	@Override
	public String toString() {
		return "CanalVenta [idCanalVenta=" + idCanalVenta + "]";
	}
	
}
