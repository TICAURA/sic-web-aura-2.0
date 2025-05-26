package mx.com.consolida.dao.interfaz;

import mx.com.consolida.dto.CotizacionTotalesDto;

public interface CotizacionTotalDao {
	
	public CotizacionTotalesDto guardarcotizacionTotal(CotizacionTotalesDto cotizacionColaborador);
	public CotizacionTotalesDto obtenerTotalByIdCotizacion(Long idCotizacion);
	public void eliminadoLogicoCotizacionTotal(Long idCotizacion);
}
