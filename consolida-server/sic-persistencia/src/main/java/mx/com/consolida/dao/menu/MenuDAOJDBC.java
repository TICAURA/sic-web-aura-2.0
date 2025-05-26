package mx.com.consolida.dao.menu;

import mx.com.consolida.menu.MenuUsuarioDTO;

public interface MenuDAOJDBC {
	public MenuUsuarioDTO consultarMenu(Long idRol);
}
