
package mx.com.consolida.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.entity.seguridad.Pantalla;
import mx.com.consolida.service.admin.PantallaBO;

@RestController
@RequestMapping(value="/admin")	
public class PantallaController {
	
	@Autowired
	private PantallaBO pantallaBO;
	
	@RequestMapping(value="/pantalla" ,method = RequestMethod.GET)
	public List<Pantalla> getAllPantallas(){
		return   pantallaBO.getPantallas(); 
	}

	@RequestMapping(value="/pantalla/{id}" , method= RequestMethod.GET)
	public Pantalla getAllPantallas(@PathVariable Long id){
		return pantallaBO.getPantallas(id);
	}
	
	@RequestMapping(value="/pantalla" , method= RequestMethod.POST)
	public List<Pantalla> guardarPantalla(@RequestBody Pantalla pantalla){
		
		return pantallaBO.guardarPantalla(pantalla);
	}
	
	@RequestMapping(value="/pantalla" , method= RequestMethod.DELETE)
	public List<Pantalla> eliminarPantalla(@PathVariable Long idPantalla){
		
		return pantallaBO.eliminarPantalla(idPantalla);
	}
}