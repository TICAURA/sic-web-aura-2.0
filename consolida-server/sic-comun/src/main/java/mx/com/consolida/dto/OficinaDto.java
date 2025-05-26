package mx.com.consolida.dto;

import java.io.Serializable;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.usuario.UsuarioDTO;

/**
 *
 * @author Abel
 */
public class OficinaDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idOficina;
    private String clave;
    private String descripcion;
    private String rfc;
    private String razonSocial;
    private String nombreContacto;
    private String telefonoContacto;
    private String correoElectronicoContacto;
    private String ciudadSede;
    private String fechaAlta;
    private String fechaModificacion;
    private String indEstatus;
    private DomicilioDto domicilio;
    private CatGeneralDto entidadFederativaSede;
    private CuentaBancariaDto cuentaBancaria;
    private UsuarioDTO usuarioAlta;
    private UsuarioDTO usuarioModificacion;
    private Long idOficinaCanalVenta;
    private Long idCanalVenta;
    

    public OficinaDto() {
    }

    public OficinaDto(Long idOficina) {
        this.idOficina = idOficina;
    }


    public OficinaDto(Long idOficina,  String rfc, String razonSocial) {
        this.idOficina = idOficina;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
    }
    
    
    public OficinaDto(Long idOficina, String clave, String descripcion, String rfc, String razonSocial, String nombreContacto, String telefonoContacto, String correoElectronicoContacto, String ciudadSede, String fechaAlta, String indEstatus) {
        this.idOficina = idOficina;
        this.clave = clave;
        this.descripcion = descripcion;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.correoElectronicoContacto = correoElectronicoContacto;
        this.ciudadSede = ciudadSede;
        this.fechaAlta = fechaAlta;
        this.indEstatus = indEstatus;
    }

	public Long getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(Long idOficina) {
		this.idOficina = idOficina;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getTelefonoContacto() {
		return telefonoContacto;
	}

	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}

	public String getCorreoElectronicoContacto() {
		return correoElectronicoContacto;
	}

	public void setCorreoElectronicoContacto(String correoElectronicoContacto) {
		this.correoElectronicoContacto = correoElectronicoContacto;
	}

	public String getCiudadSede() {
		return ciudadSede;
	}

	public void setCiudadSede(String ciudadSede) {
		this.ciudadSede = ciudadSede;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(String indEstatus) {
		this.indEstatus = indEstatus;
	}

	public DomicilioDto getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioDto domicilio) {
		this.domicilio = domicilio;
	}

	public CatGeneralDto getEntidadFederativaSede() {
		return entidadFederativaSede;
	}

	public void setEntidadFederativaSede(CatGeneralDto entidadFederativaSede) {
		this.entidadFederativaSede = entidadFederativaSede;
	}

	public CuentaBancariaDto getCuentaBancaria() {
		return cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancariaDto cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public UsuarioDTO getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(UsuarioDTO usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	

	public Long getIdOficinaCanalVenta() {
		return idOficinaCanalVenta;
	}

	public void setIdOficinaCanalVenta(Long idOficinaCanalVenta) {
		this.idOficinaCanalVenta = idOficinaCanalVenta;
	}

	public Long getIdCanalVenta() {
		return idCanalVenta;
	}

	public void setIdCanalVenta(Long idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}

	@Override
	public String toString() {
		return "OficinaDto [idOficina=" + idOficina + "]";
	}

}
