package mx.com.consolida.comunes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dto.CatEntidadFederativaDto;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.facturacion.factura.CatMunicipioDTO;

public class AccionistaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idGenericoAccionista;

	private Long idPersona;

	private String nombre;

	private String apellidoPaterno;

	private String apellidoMaterno;

	private String rfc;
	
	private Date fechaNacimiento;

	private PrestadoraServicioDto prestadoraServicio;

	private ClienteDto clienteDto;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	private BigDecimal porcentajeAccion;
	
	// domicilio
	private DomicilioDto domicilioDto;
	private List<CatGeneralDto> entidadFederativa;
    private List<CatGeneralDto> municipios;
  
	public AccionistaDto() {

	}

	public AccionistaDto(Long idGenericoAccionista) {
		this.idGenericoAccionista = idGenericoAccionista;

	}

	public Long getIdGenericoAccionista() {
		return idGenericoAccionista;
	}

	public void setIdGenericoAccionista(Long idGenericoAccionista) {
		this.idGenericoAccionista = idGenericoAccionista;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
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

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public PrestadoraServicioDto getPrestadoraServicio() {
		return prestadoraServicio;
	}

	public void setPrestadoraServicio(PrestadoraServicioDto prestadoraServicio) {
		this.prestadoraServicio = prestadoraServicio;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
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

	public BigDecimal getPorcentajeAccion() {
		return porcentajeAccion;
	}

	public void setPorcentajeAccion(BigDecimal porcentajeAccion) {
		this.porcentajeAccion = porcentajeAccion;
	}


	public DomicilioDto getDomicilioDto() {
		return domicilioDto;
	}

	public void setDomicilioDto(DomicilioDto domicilioDto) {
		this.domicilioDto = domicilioDto;
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

}
