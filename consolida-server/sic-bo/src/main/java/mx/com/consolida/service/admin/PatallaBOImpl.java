package mx.com.consolida.service.admin;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.dao.admin.PantallaDAO;
import mx.com.consolida.entity.seguridad.Pantalla;

@Service
public class PatallaBOImpl implements PantallaBO {

	@Autowired
	private PantallaDAO pantallaDao;
	
	@Transactional
	public List<Pantalla> getPantallas(){
		return pantallaDao.findAll();
	}
	
	
	@Transactional
	public Pantalla getPantallas(Long id){
		return pantallaDao.read(id);
	}

	@Transactional
	public List<Pantalla> guardarPantalla(Pantalla pantalla) {
		
		if(pantalla.getFechaAlta() == null) {
			pantalla.setFechaAlta(new Date());
		}else {
			pantalla.setFechaModificacion(new Date());
		}
		
		pantallaDao.createOrUpdate(pantalla);
		
		return pantallaDao.findAll();
	}

	@Transactional
	public List<Pantalla> eliminarPantalla(Long idPantalla) {

		pantallaDao.delete(idPantalla);
		
		return pantallaDao.findAll();
	}
	
}
