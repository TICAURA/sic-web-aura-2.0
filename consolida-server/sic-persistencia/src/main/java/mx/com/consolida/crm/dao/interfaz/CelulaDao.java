package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.usuario.UsuarioDTO;

public interface CelulaDao extends DAO<Celula,Long>{
	
	List<CelulaDto> listarTodasCelularRegistradas();
	
	CelulaDto consultarDatosCelula(String  rfcCelula);
	
	CelulaDto consultarDatosCelula(Long idRol, Long  idUsuario);
	
	List<UsuarioDTO> consultarUsuariosCelula(Long  idCelula); 
	
	List<UsuarioDTO> consultarUsuariosByCelulaRol(Long  idCelula, Long idRol);

}
