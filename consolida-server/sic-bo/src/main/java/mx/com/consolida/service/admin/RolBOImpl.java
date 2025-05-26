package mx.com.consolida.service.admin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.admin.RolDAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Rol;

@Repository
public class RolBOImpl implements RolBO{

	@Autowired
	private RolDAO rolDAO;
	
	@Transactional
	public List<Rol> getRoles() {
		
		return rolDAO.findAll();
	}

	@Transactional
	public Rol getRol(Long id) {

		return rolDAO.read(id);
	}

	@Transactional
	public List<Rol> guardarRol(Rol rol) {

		rolDAO.createOrUpdate(rol);
		
		return rolDAO.findAll();
	}

	@Transactional
	public List<Rol> eliminarRol(Long idRol) {
		
		rolDAO.delete(idRol);
		
		return rolDAO.findAll(); 
	}
	
	@Transactional
	public List<RolDTO> rolesCelula(){
		
		return rolDAO.rolesCelula();
	}

}
