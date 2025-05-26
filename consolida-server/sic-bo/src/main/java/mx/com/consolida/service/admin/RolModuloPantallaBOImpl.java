package mx.com.consolida.service.admin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.dao.admin.RolModuloDAO;
import mx.com.consolida.dao.admin.RolPantallaDAO;
import mx.com.consolida.dto.admin.ModuloDTO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.dto.admin.RolModuloPantallaDTO;
import mx.com.consolida.entity.seguridad.Modulo;
import mx.com.consolida.entity.seguridad.Pantalla;
import mx.com.consolida.entity.seguridad.Rol;
import mx.com.consolida.entity.seguridad.RolModulo;
import mx.com.consolida.entity.seguridad.RolPantalla;

@Service
public class RolModuloPantallaBOImpl  implements RolModuloPantallaBO{
	
	@Autowired
	private RolModuloDAO rolModuloDAO;
	
	@Autowired
	private RolPantallaDAO rolPantallaDAO;
	
	
	@Transactional
	public List<RolModuloPantallaDTO> rolModulosPantallas(){
		return rolModuloDAO.getRolModulosPantalla();
	}
	
	
	@Transactional
	public List<ModuloDTO> getModulosByIdRol(Long idRol){
		return rolModuloDAO.getModulosByIdRol(idRol);
	}
	
	@Transactional
	public List<PantallaDTO> getPantallasByIdRol(Long idRol){
		return rolPantallaDAO.getPantallasByIdRol(idRol);
	}
	
	@Transactional
	public List<ModuloDTO> guardarRolModulo(ModuloDTO moduloDTO){
		
		if(moduloDTO.getAsignado()) {
			RolModulo rolModulo = new RolModulo();
			Rol rol = new Rol();
			Modulo modulo = new Modulo();
			
			rol.setIdRol(moduloDTO.getIdRol());
			modulo.setIdModulo(moduloDTO.getIdModulo());
			
			rolModulo.setIndEstatus("1");
			rolModulo.setRol(rol);
			rolModulo.setModulo(modulo);
			
			rolModuloDAO.create(rolModulo);
		}else {
			rolModuloDAO.delete(moduloDTO.getIdRolModulo());
		}
		
		
		return rolModuloDAO.getModulosByIdRol(moduloDTO.getIdModulo());	
	}
	
	
	@Transactional
	public List<PantallaDTO> guardarRolPantalla(PantallaDTO pantallaDTO){
		
		if(pantallaDTO.getAsignado()) {
			RolPantalla rolModulo = new RolPantalla();
			Rol rol = new Rol();
			Pantalla pantalla = new Pantalla();
			
			rol.setIdRol(pantallaDTO.getIdRol());
			pantalla.setIdPantalla(pantallaDTO.getIdPantalla());
			
			rolModulo.setIndEstatus("1");
			rolModulo.setRol(rol);
			rolModulo.setPantalla(pantalla);
			
			rolPantallaDAO.create(rolModulo);
		}else {
			rolPantallaDAO.delete(pantallaDTO.getIdRolPantalla());
		}
		
		
		return rolPantallaDAO.getPantallasByIdRol(pantallaDTO.getIdRol());	
	}
}
