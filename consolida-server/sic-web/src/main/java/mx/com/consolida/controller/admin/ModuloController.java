package mx.com.consolida.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.Modulo;
import mx.com.consolida.service.admin.ModuloBO;

@RestController
@RequestMapping(value="/admin")	
public class ModuloController {
	
	@Autowired
	private ModuloBO moduloBO;
	
	@RequestMapping(value="/modulo" ,method = RequestMethod.GET)
	public List<Modulo> getAllPantallas(){
		return   moduloBO.getModulos(); 
	}

	@RequestMapping(value="/modulo/{id}" , method= RequestMethod.GET)
	public Modulo getAllPantallas(@PathVariable Long id){
		return moduloBO.getModulos(id);
	}
	
	@RequestMapping(value="/modulo" , method= RequestMethod.POST)
	public List<Modulo> guardarModulo(@RequestBody Modulo modulo){
		
		return moduloBO.guardarModulo(modulo);
	}
	
	@RequestMapping(value="/modulo" , method= RequestMethod.DELETE)
	public List<Modulo> eliminarModulo(@PathVariable Long idModulo){
		return moduloBO.eliminarModulo(idModulo);
	}
	
	
	@RequestMapping(value="/moduloPantalla/{idModulo}" , method= RequestMethod.GET)
	public List<PantallaDTO> pantallaModulo(@PathVariable Long idModulo){
		return moduloBO.pantallaModulo(idModulo);
	}
	
	
	@RequestMapping(value="/moduloPantalla" , method= RequestMethod.POST)
	public List<PantallaDTO> pantallaModulo(@RequestBody PantallaDTO pantallaDTO){
			
		moduloBO.guardarMododuloPantalla(pantallaDTO);
		
		return moduloBO.pantallaModulo(pantallaDTO.getIdModulo());
	}

}
