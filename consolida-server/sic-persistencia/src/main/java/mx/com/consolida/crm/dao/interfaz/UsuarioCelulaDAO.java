package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.seguridad.UsuarioCelula;

public interface UsuarioCelulaDAO extends DAO<UsuarioCelula,Long>{
	
	UsuarioCelula getUsuarioCelulaByIdUsuario(Long idUsuario);

}
