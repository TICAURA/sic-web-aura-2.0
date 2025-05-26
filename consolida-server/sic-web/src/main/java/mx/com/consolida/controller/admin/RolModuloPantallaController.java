package mx.com.consolida.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.dto.admin.ModuloDTO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.dto.admin.RolModuloPantallaDTO;
import mx.com.consolida.service.admin.RolModuloPantallaBO;

@RestController
@RequestMapping(value="/admin")	
public class RolModuloPantallaController {
	
	@Autowired
	private RolModuloPantallaBO rolModuloPantallaBO;
	
	@RequestMapping(value="/rolModulosPantallas" ,method = RequestMethod.GET)
	public List<RolModuloPantallaDTO> getRolModuloPantalla(){
		return  rolModuloPantallaBO.rolModulosPantallas();
	}
	
	@RequestMapping(value="/rolModulos/{idRol}" ,method = RequestMethod.GET)
	public List<ModuloDTO> getModulosByIdRol(@PathVariable Long idRol){
		return rolModuloPantallaBO.getModulosByIdRol(idRol);
	}
	
	@RequestMapping(value="/rolPantallas/{idRol}" ,method = RequestMethod.GET)
	public List<PantallaDTO> getPantallasByIdRol(@PathVariable Long idRol){
		return rolModuloPantallaBO.getPantallasByIdRol(idRol);
	}
	
	
	@RequestMapping(value="/rolModulos" ,method = RequestMethod.POST)
	public List<ModuloDTO> gardarModulosByIdRol(@RequestBody ModuloDTO moduloDTO){
		return rolModuloPantallaBO.guardarRolModulo(moduloDTO);
	}
	
	@RequestMapping(value="/rolPantallas" ,method = RequestMethod.POST)
	public List<PantallaDTO> guardarPantallasByIdRol(@RequestBody PantallaDTO pantallaDTO){
		return rolModuloPantallaBO.guardarRolPantalla(pantallaDTO);
	}


//	@RequestMapping(value="/rolModulo" , method= RequestMethod.POST)
//	public List<Rol> guardarRol(@RequestBody Rol rol){
//		
//		return rolBO.guardarRol(rol);
//	}
	
	
	
	
}
