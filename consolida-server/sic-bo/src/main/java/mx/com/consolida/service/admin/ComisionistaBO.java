package mx.com.consolida.service.admin;

import java.util.List;

import mx.com.consolida.dto.ComisionistaDto;


public interface ComisionistaBO {
	
	public void guardarComisionista(ComisionistaDto comisionista , Long idUsuarioAutenticado) ;
	
	public List<ComisionistaDto> listarTodosComisionistas() ;
	
	public ComisionistaDto obtenerComisionistaByIdComisionista(Long idComisionista);
	
	
	public Long asignacionClienteComisiones(ComisionistaDto comisionistaDto  , Long idUsuarioAutenticado);


	
	
}
