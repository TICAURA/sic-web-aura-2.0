package mx.com.consolida.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.UsuarioDTO;

@RestController
@RequestMapping(value="/admin")
public class UsuarioController extends BaseController {
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	@RequestMapping(value="/usuarios" ,method = RequestMethod.GET)
	public List<UsuarioDTO> getAllRoles(){
		return   usuarioBO.getUsuarios();
	}

//	@RequestMapping(value="/rol/{id}" , method= RequestMethod.GET)
//	public Rol getAllRoles(@PathVariable Long id){
//		return usuarioBO.getRol(id);
//	}
	
	@RequestMapping(value="/usuario" , method= RequestMethod.POST)
	public List<UsuarioDTO> guardarUsuario(@RequestBody UsuarioDTO usuario, Model model){
		
		
		usuarioBO.guardarUsuario(usuario,getUser().getIdUsuario());
		
		return  usuarioBO.getUsuarios();
	}
	
//	@RequestMapping(value="/rol" , method= RequestMethod.DELETE)
//	public List<Rol> eliminarRol(@PathVariable Long idRol){
//		
//		return usuarioBO.eliminarRol(idRol);
//	}

}
