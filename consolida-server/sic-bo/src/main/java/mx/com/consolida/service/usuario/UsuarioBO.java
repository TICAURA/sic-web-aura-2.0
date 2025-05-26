package mx.com.consolida.service.usuario;

import java.util.List;

import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ResponseDTO;
import mx.com.consolida.usuario.ResponseUsuarioDTO;
import mx.com.consolida.usuario.UsuarioDTO;

public interface UsuarioBO  {
	public ResponseUsuarioDTO guardarUsuario(UsuarioDTO usuario , Long idUsuarioAutenticado);
	
	public ResponseDTO obtenerUsuario(UsuarioDTO usuarioDTO);
	
	public UsuarioDTO usuarioAutenticacion(String user) throws Exception;
	
//	public UsuarioMonitorDTO getDatosGenerales(UsuarioMonitorDTO userBilleDTO);
	
	public void activarCuentaUsuario(String token) throws BusinessException;
	
	public List<UsuarioDTO> getUsuarios();
	
	public List<UsuarioDTO> getUsuariosByRol(Long idRol);
	
	Long getIdCelulaByIdUsuario(Long idUsuario);
		
}
	