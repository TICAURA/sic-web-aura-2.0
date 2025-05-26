package mx.com.consolida.comunes.dto;

import java.io.Serializable;

import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class RepresentanteLegalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idGenericoRepresentanteLegal;

	private ClienteDto clienteDto;

	private PrestadoraServicioDto PrestadoraServicioDto;
	
	private Long idPersona;

	private String nombre;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String rfc;
	
	private String curp;

	private String contrasenaCertificado;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;

	public RepresentanteLegalDto() {
		
	}
	
	public RepresentanteLegalDto(Long idGenericoRepresentanteLegal) {
		this.idGenericoRepresentanteLegal = idGenericoRepresentanteLegal;
		
	}

	public Long getIdGenericoRepresentanteLegal() {
		return idGenericoRepresentanteLegal;
	}

	public void setIdGenericoRepresentanteLegal(Long idGenericoRepresentanteLegal) {
		this.idGenericoRepresentanteLegal = idGenericoRepresentanteLegal;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return PrestadoraServicioDto;
	}

	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		PrestadoraServicioDto = prestadoraServicioDto;
	}


	public String getContrasenaCertificado() {
		return contrasenaCertificado;
	}

	public void setContrasenaCertificado(String contrasenaCertificado) {
		this.contrasenaCertificado = contrasenaCertificado;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public Long getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}

}
