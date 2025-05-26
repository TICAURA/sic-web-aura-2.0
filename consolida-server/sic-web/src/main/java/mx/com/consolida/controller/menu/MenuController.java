package mx.com.consolida.controller.menu;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.menu.MenuUsuarioDTO;
import mx.com.consolida.service.interfaz.CotizadorBO;
import mx.com.consolida.service.menu.MenuUsuarioBO;
import mx.com.consolida.usuario.UserDetailNomina;
import mx.com.consolida.usuario.UsuarioDTO;

@Controller
@RequestMapping("menu")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO })
public class MenuController {
	
	@Autowired
	private MenuUsuarioBO menuUsuarioBO;
	@Autowired
	private CotizadorBO cotizadorBO;
	

	@RequestMapping(value="/version")
	@ResponseBody
	public String version(){
		return "1.0";
	}
	
	
	@RequestMapping(value="/libro")
	@ResponseBody
	public Libro libro(){
		Libro libro = new Libro();
		libro.setNombre("Principito");
		return libro;
	}
	
	@RequestMapping(value="/menus")
	@ResponseBody
	public MenuUsuarioDTO getMenuUsuarioRol(final Principal principal, final Model model){
		MenuUsuarioDTO menuUsuarioDTO = new MenuUsuarioDTO();
		final Authentication authentication = (Authentication) principal;
		if(authentication != null) {
			UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
			UserDetailNomina usuario = (UserDetailNomina) authentication.getPrincipal();
			usuarioAplicativo.setUsuario(usuario.getUsername());
			usuarioAplicativo.setIdUsuario(usuario.getIdUsuario());
			usuarioAplicativo.setUsuarioRols(usuario.getUsuarioRols());
			
			UsuarioDTO usuarioDTO = new UsuarioDTO();
		    usuarioDTO.setIdUsuario(usuario.getIdUsuario());
		    usuarioDTO.setUsuario(usuario.getUsername());
		    usuarioDTO.setNombre(usuario.getNombre());
		    usuarioDTO.setPrimerApellido(usuario.getPrimerApellido());
		    usuarioDTO.setSegundoApellido(usuario.getSegundoApellido());
		    usuarioDTO.setUsuarioRols(usuario.getUsuarioRols());
			
			menuUsuarioDTO  = menuUsuarioBO.getMenuUsuario(usuario.getIdUsuario());
		    menuUsuarioDTO.setUsuario(usuarioDTO);

			model.addAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO,usuarioAplicativo);
			
			return menuUsuarioDTO;
		}else {
			return menuUsuarioDTO;
		}
	}
	
}

class Libro{
	
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}