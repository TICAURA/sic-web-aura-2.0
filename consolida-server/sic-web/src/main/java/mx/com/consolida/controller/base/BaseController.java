package mx.com.consolida.controller.base;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import mx.com.consolida.usuario.UserDetailNomina;
import mx.com.consolida.usuario.UsuarioDTO;

public class BaseController {
	
	public UsuarioDTO getUser() {
		  
		  UsuarioDTO usuarioDTO = new UsuarioDTO();
		  SecurityContext securityContext = SecurityContextHolder.getContext();
		  Authentication authentication = securityContext.getAuthentication();
		
		  if (authentication != null && authentication.getPrincipal() instanceof UserDetailNomina) {
			  UserDetailNomina user =  (UserDetailNomina) authentication.getPrincipal();
			  
			  
			  usuarioDTO.setIdUsuario(user.getIdUsuario());
			  usuarioDTO.setUsuario(user.getUsername());
			  usuarioDTO.setNombre(user.getNombre());
			  usuarioDTO.setPrimerApellido(user.getPrimerApellido());
			  usuarioDTO.setSegundoApellido(user.getSegundoApellido());
			  usuarioDTO.setRol(user.getRolSeleccionado().getRol());
			  
			  return  usuarioDTO;
		  }
		
		  return usuarioDTO;
		      
		}
}
