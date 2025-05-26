package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.admin.PantallaDTO;
import mx.com.consolida.entity.seguridad.RolPantalla;

public interface RolPantallaDAO   extends DAO<RolPantalla,Long> {

	
	public List<PantallaDTO> getPantallasByIdRol(Long idRol);
}
