package mx.com.consolida.service.admin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.dao.admin.ModuloDAO;
import mx.com.consolida.dao.admin.ModuloPantallaDAO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.Modulo;
import mx.com.consolida.entity.seguridad.ModuloPantalla;
import mx.com.consolida.entity.seguridad.Pantalla;

@Service
public class ModuloBOImpl implements ModuloBO {

	@Autowired
	private ModuloDAO moduloDAO;
	
	@Autowired
	private ModuloPantallaDAO moduloPantallaDAO;
	
	@Transactional
	public List<Modulo> getModulos() {

		return moduloDAO.findAll();
	}

	@Transactional
	public Modulo getModulos(Long id) {

		return moduloDAO.read(id);
	}

	@Transactional
	public List<Modulo> guardarModulo(Modulo modulo) {
		
		moduloDAO.createOrUpdate(modulo);
		
		return moduloDAO.findAll();
	}
 
	@Transactional
	public List<Modulo> eliminarModulo(Long idModulo) {
		
		moduloDAO.delete(idModulo);
		
		return moduloDAO.findAll();
	}

	@Transactional
	public List<PantallaDTO> pantallaModulo(Long idModulo) {

		return moduloPantallaDAO.getPantallaByIdModulo(idModulo);
	}

	@Transactional
	public void guardarMododuloPantalla(PantallaDTO pantallaDTO) {
		//true es asignado false es desasignado
		
		if(pantallaDTO.getAsignado()) {
			//Si esta asignado se crea el insert para el modulo pantalla
			ModuloPantalla moduloPantalla = new ModuloPantalla();
			Modulo modulo = new Modulo();
			Pantalla pantalla = new Pantalla();
			
			modulo.setIdModulo(pantallaDTO.getIdModulo());
			pantalla.setIdPantalla(pantallaDTO.getIdPantalla());
			moduloPantalla.setModulo(modulo);
			moduloPantalla.setPantalla(pantalla);
			moduloPantalla.setIndEstatus("1");
			
			moduloPantallaDAO.create(moduloPantalla);
		}else {
			//Si NO esta asignado se borra el modulo pantalla
			moduloPantallaDAO.delete(pantallaDTO.getIdModuloPantalla());
		}
	}
	

}
