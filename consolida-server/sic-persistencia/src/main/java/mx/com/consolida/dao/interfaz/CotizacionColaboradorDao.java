package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface CotizacionColaboradorDao {

	public CotizacionColaboradorDto guardarcotizacionColaborador(CotizacionColaboradorDto cotizacionColaborador, UsuarioAplicativo usuarioAplicativo);
	public void eliminadoLogicocotizacionColaborador(Long idCotizacion);
	public CotizacionColaboradorDto obtenerCotizacionColaboradorById(Long idCotizacionColaborador);
	
	public List<CotizacionColaboradorDto> obtenerColaboradoresByIdCotizacion(Long idCotizacion);

}
