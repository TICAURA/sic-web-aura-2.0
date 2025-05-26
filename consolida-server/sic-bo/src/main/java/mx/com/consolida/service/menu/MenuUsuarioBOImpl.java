package mx.com.consolida.service.menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.dao.menu.MenuDAOJDBC;
import mx.com.consolida.dao.usuario.UsuarioRolDAO;
import mx.com.consolida.entity.seguridad.UsuarioRol;
import mx.com.consolida.menu.MenuDTO;
import mx.com.consolida.menu.MenuUsuarioDTO;
import mx.com.consolida.menu.TipoMenuEnum;

@Service
public class MenuUsuarioBOImpl implements MenuUsuarioBO {

	@Autowired
	private MenuDAOJDBC menuDAOJDBC;
	
	@Autowired
	private UsuarioRolDAO usuarioRolDAO;
	
	
	public MenuUsuarioDTO getMenuUsuario(Long idusuario){
		
		//Consultar rol de usuario
		UsuarioRol usuarioRol = usuarioRolDAO.findByidUsuario(idusuario);
		MenuUsuarioDTO  source =  menuDAOJDBC.consultarMenu(usuarioRol.getRol().getIdRol());
		List<MenuDTO> menus = source.getMenus();
		
		List<MenuDTO> menuOrdenado = new ArrayList<MenuDTO>();
		
		MenuDTO menuNivelUno= new MenuDTO();
		
		
		for (MenuDTO menuDTO : menus) {
			if(TipoMenuEnum.MENU.name().equals(menuDTO.getTipoMenu())){
				
				menuNivelUno = menuDTO;
				menuNivelUno.setMenuDTOs(new ArrayList<MenuDTO>());
				menuOrdenado.add(menuNivelUno);	
				
			}else{
				menuNivelUno.getMenuDTOs().add(menuDTO);
			}
		}
		
		source.setMenus(menuOrdenado);
		return source;
	}
	
}
