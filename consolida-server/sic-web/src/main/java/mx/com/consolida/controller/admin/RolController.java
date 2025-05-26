package mx.com.consolida.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Rol;
import mx.com.consolida.service.admin.RolBO;

@RestController
@RequestMapping(value="/admin")	
public class RolController {

	@Autowired
	private RolBO rolBO;
	
	@RequestMapping(value="/rol" ,method = RequestMethod.GET)
	public List<Rol> getAllRoles(){
		return   rolBO.getRoles(); 
	}

	@RequestMapping(value="/rol/{id}" , method= RequestMethod.GET)
	public Rol getAllRoles(@PathVariable Long id){
		return rolBO.getRol(id);
	}
	
	@RequestMapping(value="/rol" , method= RequestMethod.POST)
	public List<Rol> guardarRol(@RequestBody Rol rol){
		
		return rolBO.guardarRol(rol);
	}
	
	@RequestMapping(value="/rol" , method= RequestMethod.DELETE)
	public List<Rol> eliminarRol(@PathVariable Long idRol){
		
		return rolBO.eliminarRol(idRol);
	}
	
	
	@RequestMapping(value="/rol/celula" ,method = RequestMethod.GET)
	public List<RolDTO> getAllRolesCelula(){
		return   rolBO.rolesCelula(); 
	}
	
	
}
