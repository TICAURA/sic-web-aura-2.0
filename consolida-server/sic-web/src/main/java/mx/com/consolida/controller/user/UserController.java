package mx.com.consolida.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.service.admin.CanalVentaBO;
import mx.com.consolida.usuario.UserDetailNomina;
import mx.com.consolida.usuario.UsuarioDTO;

@Controller
public class UserController {
	@Autowired
	private CanalVentaBO canalVenta;

	@RequestMapping(value="/")
	@ResponseBody
	public String urlInit(){
		return "redirect:"+"/index.html";
	}
	
	@RequestMapping(value="/#/")
	@ResponseBody
	public String urlInitHash(){
		return "redirect:"+"/index.html";
	}
	
	@RequestMapping(value="/user")
	public String user(){
		return "redirect:"+"/index.html";
	}
	
	@RequestMapping(value="/userAutenticado")
	@ResponseBody
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
		  usuarioDTO.setSegundoApellido(user.getSegundoApellido()!=null ? user.getSegundoApellido() : "");
		  usuarioDTO.setUsuarioRols(user.getUsuarioRols());
		  usuarioDTO.setRol(user.getUsuarioRols().get(0).getRol());
		  
		  return  usuarioDTO;
	  }
	
	  return usuarioDTO;
	      
	}
	
	@RequestMapping(value = "/usuarioAplicativo")
	@ResponseBody
	public UsuarioDTO getUsarioAplicativo() {
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailNomina) {
			UserDetailNomina user = (UserDetailNomina) authentication.getPrincipal();
			usuarioDTO.setIdUsuario(user.getIdUsuario());
			usuarioDTO.setUsuario(user.getUsername());
			usuarioDTO.setNombre(user.getNombre());
			usuarioDTO.setPrimerApellido(user.getPrimerApellido());
			usuarioDTO.setSegundoApellido(user.getSegundoApellido());
			usuarioDTO.setUsuarioRols(user.getUsuarioRols());
			boolean banderaFilterPromotor = false;
			banderaFilterPromotor = usuarioDTO.getUsuarioRols().stream().anyMatch(
					rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave()));
			if (banderaFilterPromotor) {
				usuarioDTO.setCanalVentaDto(canalVenta.obtenerCanalVentaByIdUsuario(user.getIdUsuario()));
			}
			return usuarioDTO;
		}
	
	  return usuarioDTO;
	      
	}
}
