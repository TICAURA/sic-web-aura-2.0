package mx.com.consolida.dao.admin;

import java.util.List;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.dto.ComisionistaDto;
import mx.com.consolida.entity.Comisionista;

public interface ComisionistaDAO   extends DAO<Comisionista,Long> {
	
	public List<ComisionistaDto> listarAllComisionistas() ;

	public ComisionistaDto obtenerComisionistaByIdComisionista(Long idComisionista);
	
}
