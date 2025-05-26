package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.Cotizacion;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface CotizadorDao {
	public CotizacionDto obtenerCotizacionById(Long idCotizacion);
	
	public CotizacionDto obtenercotizacionAutorizar(Long idClienteTemp);
	
	public CotizacionDto guardarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo, Long idClienteTempBitacoraSolicitudes);
	
	public CotizacionDto editarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo);

	CotizacionDto actualizarCotizacion(CotizacionDto cotizacion);

	public void guardarObservacionAutorizador(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo);

	public void solicitarAutorizacionCotizacion(CotizacionDto cotizacion);

	Cotizacion obtenerCotizacionXId(Long idCotizacion);

	List<CotizacionDto> obtenerCotizacionesXIdClienteTemp(Long idClienteTemp);

	public void actualizarEstatusCotizaciones(List<CotizacionDto> listCotizaciones, UsuarioAplicativo usuarioAplicativo, String motivo);

	public Boolean rechazarProspecto(String motivo, UsuarioAplicativo usuarioAplicativo, Long idClienteTemp);

	public Boolean autorizarProspecto(UsuarioAplicativo usuarioAplicativo, Long idClienteTemp);
	
}

