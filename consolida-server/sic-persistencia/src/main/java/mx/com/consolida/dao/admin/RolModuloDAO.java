package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.admin.ModuloDTO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.dto.admin.RolModuloPantallaDTO;
import mx.com.consolida.entity.seguridad.RolModulo;

public interface RolModuloDAO  extends DAO<RolModulo,Long> {
	
	public List<RolModuloPantallaDTO> getRolModulosPantalla();
	
	public List<ModuloDTO> getModulosByIdRol(Long idRol);
	

}
