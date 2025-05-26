package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ColaboradorTempDao {
	
	public ColaboradoresTempDto guardarColaborador(ColaboradoresTempDto cotizacionColaborador, UsuarioAplicativo usuarioAplicativo);
	public List<ColaboradoresTempDto> obtenercotizacionesColaboradoresByIdCot(Long idCotizacion);
	public void eliminadoLogicoEmpleados(Long idCotizacion);
}
