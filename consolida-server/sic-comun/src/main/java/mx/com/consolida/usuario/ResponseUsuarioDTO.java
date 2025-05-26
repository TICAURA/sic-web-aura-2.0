package mx.com.consolida.usuario;

import mx.com.consolida.generico.ResponseDTO;

public class ResponseUsuarioDTO extends ResponseDTO {

	private Long idUsuario;
	private Long idPersona;
	
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdPersona() {
		return idPersona;
	}
	public void setIdPersona(Long idPersona) {
		this.idPersona = idPersona;
	}
	
	
	
}
