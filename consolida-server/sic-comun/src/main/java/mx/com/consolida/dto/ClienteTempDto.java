package mx.com.consolida.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;

/**
 *
 * @author 
 */
public class ClienteTempDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long idClienteTemp;
    private String rfc;
    private String razonSocial;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreComercial;
    private Long idEstatus;
    private Date fechaAlta;
    private Date fechaModificacion;
    private String usuarioAlta;
    private Long idCanalVenta;
    private CatGeneralDto idTipoPersona;
    private MedioContactoDto idMedioContacto;
    private PersonaContactoDto idPersonaContacto;
    private List<CotizacionDto> Cotizaciones;
    private List<CotizacionDto> preCotizaciones;
    private CatGrupoDto grupo;
    private List<CatGrupoDto> grupos;
    
    private int numeroCotizaciones;
    private int numeroPreCotizaciones;
    private int numeroCotizacionesDetalladas;
    private int numeroEventos;
    private int numeroCotizacionesEstatus;
    
    private int numeroCotizacionesEnProceso;
    private int numeroCotizacionesEnAutorizacion;
    private int numeroCotizacionesAutorizacion;
    private int numeroCotizacionesRechazadas;
    
    private Date fechaUltimoEvento;
    private Date fechaUltimaCotizacion;
    
    private List<ClienteTempBitacoraDto> bitacora;
    private List<ClienteTempEstatusDto> estatusCliente;
    private List<CatTipoEventoDto> listCatTipoEventoDto;
    
    private List<ClienteTempDto> listClienteTempDto;
    private List<ClienteTempBitacoraSolicitudesDto> listClienteTempBitacoraSolicitudesDto;
    private ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto;
    
    private List<CatGeneralDto> entidadFederativa;
    private List<CatGeneralDto> municipios;
    private List<CatGeneralDto> girosComerciales;
    private Long idGiroComercial;
    private String nombreGiroComercial;
    private List<CatGeneralDto> catEstatus;
    private CotizacionDto cotizacionDto;
    private CotizacionDto preCotizacionDto;

    private String motivoRechazo;
    
    private Long idSubGiroComercial;
    private List<CatSubGiroComercialDto> catSubGiroComercial;
    
    public ClienteTempDto() {
    }

    public ClienteTempDto(Long idClienteTemp) {
        this.idClienteTemp = idClienteTemp;
    }

    public ClienteTempDto(Long idClienteTemp, String nombre, String apellidoPaterno, String apellidoMaterno, String rfc, String razonSocial, Date fechaAlta, String usuarioAlta) {
        this.idClienteTemp = idClienteTemp;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.fechaAlta = fechaAlta;
        this.usuarioAlta = usuarioAlta;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno =apellidoMaterno;
    }

    public Long getIdClienteTemp() {
        return idClienteTemp;
    }

    public void setIdClienteTemp(Long idClienteTemp) {
        this.idClienteTemp = idClienteTemp;
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

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public CatGeneralDto getIdTipoPersona() {
        return idTipoPersona;
    }

    public void setIdTipoPersona(CatGeneralDto idTipoPersona) {
        this.idTipoPersona = idTipoPersona;
    }

    public int getNumeroCotizaciones() {
		return numeroCotizaciones;
	}

	public void setNumeroCotizaciones(int numeroCotizaciones) {
		this.numeroCotizaciones = numeroCotizaciones;
	}


	public List<CotizacionDto> getCotizaciones() {
		return Cotizaciones;
	}

	public void setCotizaciones(List<CotizacionDto> cotizaciones) {
		Cotizaciones = cotizaciones;
	}
    

	public MedioContactoDto getIdMedioContacto() {
		return idMedioContacto;
	}

	public void setIdMedioContacto(MedioContactoDto idMedioContacto) {
		this.idMedioContacto = idMedioContacto;
	}

	public PersonaContactoDto getIdPersonaContacto() {
		return idPersonaContacto;
	}

	public void setIdPersonaContacto(PersonaContactoDto idPersonaContacto) {
		this.idPersonaContacto = idPersonaContacto;
	}

	
	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}
	
	public CotizacionDto getCotizacionDto() {
		return cotizacionDto;
	}

	public void setCotizacionDto(CotizacionDto cotizacionDto) {
		this.cotizacionDto = cotizacionDto;
	}

	public CatGrupoDto getGrupo() {
		return grupo;
	}

	public void setGrupo(CatGrupoDto grupo) {
		this.grupo = grupo;
	}
	
	public List<CatGrupoDto> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<CatGrupoDto> grupos) {
		this.grupos = grupos;
	}

	public List<ClienteTempBitacoraDto> getBitacora() {
		return bitacora;
	}

	public void setBitacora(List<ClienteTempBitacoraDto> bitacora) {
		this.bitacora = bitacora;
	}

	public List<ClienteTempEstatusDto> getEstatusCliente() {
		return estatusCliente;
	}

	public void setEstatusCliente(List<ClienteTempEstatusDto> estatusCliente) {
		this.estatusCliente = estatusCliente;
	}

	public List<CatTipoEventoDto> getListCatTipoEventoDto() {
		return listCatTipoEventoDto;
	}

	public void setListCatTipoEventoDto(List<CatTipoEventoDto> listCatTipoEventoDto) {
		this.listCatTipoEventoDto = listCatTipoEventoDto;
	}

	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	
	public int getNumeroEventos() {
		return numeroEventos;
	}

	public void setNumeroEventos(int numeroEventos) {
		this.numeroEventos = numeroEventos;
	}

	public Date getFechaUltimoEvento() {
		return fechaUltimoEvento;
	}

	public void setFechaUltimoEvento(Date fechaUltimoEvento) {
		this.fechaUltimoEvento = fechaUltimoEvento;
	}

	public List<ClienteTempBitacoraSolicitudesDto> getListClienteTempBitacoraSolicitudesDto() {
		return listClienteTempBitacoraSolicitudesDto;
	}

	public void setListClienteTempBitacoraSolicitudesDto(
			List<ClienteTempBitacoraSolicitudesDto> listClienteTempBitacoraSolicitudesDto) {
		this.listClienteTempBitacoraSolicitudesDto = listClienteTempBitacoraSolicitudesDto;
	}

	public ClienteTempBitacoraSolicitudesDto getClienteTempBitacoraSolicitudesDto() {
		return clienteTempBitacoraSolicitudesDto;
	}

	public void setClienteTempBitacoraSolicitudesDto(ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto) {
		this.clienteTempBitacoraSolicitudesDto = clienteTempBitacoraSolicitudesDto;
	}

	public List<ClienteTempDto> getListClienteTempDto() {
		return listClienteTempDto;
	}

	public void setListClienteTempDto(List<ClienteTempDto> listClienteTempDto) {
		this.listClienteTempDto = listClienteTempDto;
	}
	

	public List<CatGeneralDto> getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(List<CatGeneralDto> entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public List<CatGeneralDto> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<CatGeneralDto> municipios) {
		this.municipios = municipios;
	}
	
	public List<CatGeneralDto> getGirosComerciales() {
		return girosComerciales;
	}

	public void setGirosComerciales(List<CatGeneralDto> girosComerciales) {
		this.girosComerciales = girosComerciales;
	}

	public Long getIdGiroComercial() {
		return idGiroComercial;
	}

	public void setIdGiroComercial(Long idGiroComercial) {
		this.idGiroComercial = idGiroComercial;
	}
	
	public String getNombreGiroComercial() {
		return nombreGiroComercial;
	}

	public void setNombreGiroComercial(String nombreGiroComercial) {
		this.nombreGiroComercial = nombreGiroComercial;
	}
	
	public List<CatGeneralDto> getCatEstatus() {
		return catEstatus;
	}

	public void setCatEstatus(List<CatGeneralDto> catEstatus) {
		this.catEstatus = catEstatus;
	}
	
	public int getNumeroPreCotizaciones() {
		return numeroPreCotizaciones;
	}

	public void setNumeroPreCotizaciones(int numeroPreCotizaciones) {
		this.numeroPreCotizaciones = numeroPreCotizaciones;
	}

	public int getNumeroCotizacionesDetalladas() {
		return numeroCotizacionesDetalladas;
	}

	public void setNumeroCotizacionesDetalladas(int numeroCotizacionesDetalladas) {
		this.numeroCotizacionesDetalladas = numeroCotizacionesDetalladas;
	}

	public Date getFechaUltimaCotizacion() {
		return fechaUltimaCotizacion;
	}

	public void setFechaUltimaCotizacion(Date fechaUltimaCotizacion) {
		this.fechaUltimaCotizacion = fechaUltimaCotizacion;
	}

	public Long getIdCanalVenta() {
		return idCanalVenta;
	}

	public void setIdCanalVenta(Long idCanalVenta) {
		this.idCanalVenta = idCanalVenta;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	
	public Long getIdSubGiroComercial() {
		return idSubGiroComercial;
	}

	public void setIdSubGiroComercial(Long idSubGiroComercial) {
		this.idSubGiroComercial = idSubGiroComercial;
	}

	public List<CatSubGiroComercialDto> getCatSubGiroComercial() {
		return catSubGiroComercial;
	}

	public void setCatSubGiroComercial(List<CatSubGiroComercialDto> catSubGiroComercial) {
		this.catSubGiroComercial = catSubGiroComercial;
	}

	public int getNumeroCotizacionesEstatus() {
		return numeroCotizacionesEstatus;
	}

	public void setNumeroCotizacionesEstatus(int numeroCotizacionesEstatus) {
		this.numeroCotizacionesEstatus = numeroCotizacionesEstatus;
	}

	public int getNumeroCotizacionesEnProceso() {
		return numeroCotizacionesEnProceso;
	}

	public void setNumeroCotizacionesEnProceso(int numeroCotizacionesEnProceso) {
		this.numeroCotizacionesEnProceso = numeroCotizacionesEnProceso;
	}

	public int getNumeroCotizacionesEnAutorizacion() {
		return numeroCotizacionesEnAutorizacion;
	}

	public void setNumeroCotizacionesEnAutorizacion(int numeroCotizacionesEnAutorizacion) {
		this.numeroCotizacionesEnAutorizacion = numeroCotizacionesEnAutorizacion;
	}

	public int getNumeroCotizacionesAutorizacion() {
		return numeroCotizacionesAutorizacion;
	}

	public void setNumeroCotizacionesAutorizacion(int numeroCotizacionesAutorizacion) {
		this.numeroCotizacionesAutorizacion = numeroCotizacionesAutorizacion;
	}

	public int getNumeroCotizacionesRechazadas() {
		return numeroCotizacionesRechazadas;
	}

	public void setNumeroCotizacionesRechazadas(int numeroCotizacionesRechazadas) {
		this.numeroCotizacionesRechazadas = numeroCotizacionesRechazadas;
	}

	public List<CotizacionDto> getPreCotizaciones() {
		return preCotizaciones;
	}

	public void setPreCotizaciones(List<CotizacionDto> preCotizaciones) {
		this.preCotizaciones = preCotizaciones;
	}

	public CotizacionDto getPreCotizacionDto() {
		return preCotizacionDto;
	}

	public void setPreCotizacionDto(CotizacionDto preCotizacionDto) {
		this.preCotizacionDto = preCotizacionDto;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (idClienteTemp != null ? idClienteTemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClienteTempDto)) {
            return false;
        }
        ClienteTempDto other = (ClienteTempDto) object;
        if ((this.idClienteTemp == null && other.idClienteTemp != null) || (this.idClienteTemp != null && !this.idClienteTemp.equals(other.idClienteTemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.com.consolida.dto.ClienteTempDto[ idClienteTemp=" + idClienteTemp + " ]";
    }
    
}
