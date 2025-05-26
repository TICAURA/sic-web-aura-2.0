package mx.com.consolida.comunes.dto;

import java.io.Serializable;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.usuario.UsuarioDTO;

public class ApoderadoLegalDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idGenericoApoderadoLegal;

	private ClienteDto clienteDto;

	private PrestadoraServicioDto prestadoraServicioDto;

	private Long idPersona;

	private String nombre;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String rfc;
	
	private String curp;

	private CatGeneralDto catFacultadDto;

	private String poderNotarial;

	private UsuarioDTO usuarioAlta;

	private UsuarioDTO usuarioModificacion;
	
	public ApoderadoLegalDto() {
		
	}
	
	public ApoderadoLegalDto(Long idGenericoApoderadoLegal) {
		this.idGenericoApoderadoLegal = idGenericoApoderadoLegal;
		
	}

	public Long getIdGenericoApoderadoLegal() {
		return idGenericoApoderadoLegal;
	}

	public void setIdGenericoApoderadoLegal(Long idGenericoApoderadoLegal) {
		this.idGenericoApoderadoLegal = idGenericoApoderadoLegal;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public PrestadoraServicioDto getPrestadoraServicioDto() {
		return prestadoraServicioDto;
	}

	public void setPrestadoraServicioDto(PrestadoraServicioDto prestadoraServicioDto) {
		this.prestadoraServicioDto = prestadoraServicioDto;
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

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public CatGeneralDto getCatFacultadDto() {
		return catFacultadDto;
	}

	public void setCatFacultadDto(CatGeneralDto catFacultadDto) {
		this.catFacultadDto = catFacultadDto;
	}

	public String getPoderNotarial() {
		return poderNotarial;
	}

	public void setPoderNotarial(String poderNotarial) {
		this.poderNotarial = poderNotarial;
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
	
}
