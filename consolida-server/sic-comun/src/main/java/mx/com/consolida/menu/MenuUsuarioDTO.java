package mx.com.consolida.menu;

import java.io.Serializable;
import java.util.List;

import mx.com.consolida.usuario.UsuarioDTO;

@SuppressWarnings("serial")
public class MenuUsuarioDTO implements Serializable{
	
	private UsuarioDTO usuario;
	private List<MenuDTO> menus;

	public List<MenuDTO> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuDTO> menus) {
		this.menus = menus;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	
	
}