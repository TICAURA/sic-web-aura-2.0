package mx.com.consolida.crm.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;


public class DomicilioComunDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/// Domicilio de cliente
	private Long idClienteDomicilio;
	private ClienteDto cliente;
	////
	
	/// Domicilio de prestadora de servicio
	private Long idPrestadoraServicioDomicilio;
	private PrestadoraServicioDto prestadoraServicio;
	//////////
	private DomicilioDto domicilio;
	private DomicilioDto domicilioComercial;
	private Date fechaAlta;
	private Long indEstatus;
	
	List<CatGeneralDto> municipios;
	List<CatGeneralDto> municipiosDomicilioComercial;
	private List<CatGeneralDto> entidadFederativa;
	
	private ClienteMedioContactoDto clienteMedioContactoDto;
	
	private ClienteMedioContactoDto clienteMedioContactoCEODto;
	
	
	public DomicilioComunDto () {
	}
	
	public DomicilioComunDto (Long idPrestadoraServicioDomicilio,DomicilioDto domicilio) {
		this.idPrestadoraServicioDomicilio = idPrestadoraServicioDomicilio;
		this.domicilio = domicilio;
	}

	public Long getIdPrestadoraServicioDomicilio() {
		return idPrestadoraServicioDomicilio;
	}

	public void setIdPrestadoraServicioDomicilio(Long idPrestadoraServicioDomicilio) {
		this.idPrestadoraServicioDomicilio = idPrestadoraServicioDomicilio;
	}

	public DomicilioDto getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(DomicilioDto domicilio) {
		this.domicilio = domicilio;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Long getIndEstatus() {
		return indEstatus;
	}

	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public Long getIdClienteDomicilio() {
		return idClienteDomicilio;
	}

	public void setIdClienteDomicilio(Long idClienteDomicilio) {
		this.idClienteDomicilio = idClienteDomicilio;
	}

	public ClienteDto getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDto cliente) {
		this.cliente = cliente;
	}

	public DomicilioDto getDomicilioComercial() {
		return domicilioComercial;
	}

	public void setDomicilioComercial(DomicilioDto domicilioComercial) {
		this.domicilioComercial = domicilioComercial;
	}

	public List<CatGeneralDto> getMunicipios() {
		return municipios;
	}

	public void setMunicipios(List<CatGeneralDto> municipios) {
		this.municipios = municipios;
	}

	public List<CatGeneralDto> getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(List<CatGeneralDto> entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public List<CatGeneralDto> getMunicipiosDomicilioComercial() {
		return municipiosDomicilioComercial;
	}

	public void setMunicipiosDomicilioComercial(List<CatGeneralDto> municipiosDomicilioComercial) {
		this.municipiosDomicilioComercial = municipiosDomicilioComercial;
	}

	public ClienteMedioContactoDto getClienteMedioContactoDto() {
		return clienteMedioContactoDto;
	}

	public void setClienteMedioContactoDto(ClienteMedioContactoDto clienteMedioContactoDto) {
		this.clienteMedioContactoDto = clienteMedioContactoDto;
	}

	public ClienteMedioContactoDto getClienteMedioContactoCEODto() {
		return clienteMedioContactoCEODto;
	}

	public void setClienteMedioContactoCEODto(ClienteMedioContactoDto clienteMedioContactoCEODto) {
		this.clienteMedioContactoCEODto = clienteMedioContactoCEODto;
	}

}