package mx.com.consolida.dao.usuario;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.usuario.UsuarioDTO;


public interface UsuarioDAO extends DAO<Usuario, Long> {
	
	public Usuario getById(int i);
	
	public Usuario getUsuarioAutenticacion(UsuarioDTO usuario);
	
	public Usuario getExisteUsuario(UsuarioDTO usuario);
	
	public Usuario getExisteUsuario(String usuario);
	
	public List<UsuarioDTO> getUsuarios();
	
	public List<UsuarioDTO> getListUsuariosByRol(Long idRol);
}
