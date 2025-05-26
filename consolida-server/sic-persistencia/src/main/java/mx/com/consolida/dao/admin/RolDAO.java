package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Rol;

public interface RolDAO extends DAO<Rol,Long> {
	public List<RolDTO> rolesCelula();
}
