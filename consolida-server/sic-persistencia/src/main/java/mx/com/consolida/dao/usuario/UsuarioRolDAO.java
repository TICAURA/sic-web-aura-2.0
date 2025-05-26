package mx.com.consolida.dao.usuario;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.seguridad.UsuarioRol;
import mx.com.consolida.usuario.UsuarioDTO;

public interface UsuarioRolDAO extends DAO<UsuarioRol, Long> {
	public UsuarioRol findByidUsuario(final Long idUsuario);
}
