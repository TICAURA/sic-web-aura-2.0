package mx.com.consolida.service.interfaz;

import java.util.List;

import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.CotizacionTotalesDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface CotizadorBO  {
	CotizacionDto obtenerCotizacionById(Long id);
	CotizacionDto obtenercotizacionAutorizar(Long idClienteTemp);
	CotizacionDto guardarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo, Long idClienteTempBitacoraSolicitudes);
	CotizacionDto editarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo);
	void guardarBitacora(ClienteTempBitacoraDto bitacora, UsuarioAplicativo usuarioAplicativo);
	void guardarSolicitudCotizacion(ClienteTempBitacoraSolicitudesDto solicitarCotizacion, UsuarioAplicativo usuarioAplicativo);
	
	CotizacionColaboradorDto guardarcotizacionColaborador(CotizacionColaboradorDto cotizacionColaborador, UsuarioAplicativo usuarioAplicativo);
	public CotizacionColaboradorDto obtenerCotizacionColaboradorById(Long idCotizacionColaborador);
	public void eliminadoLogicocotizacionColaborador(Long idCotizacion);
	public List<CotizacionColaboradorDto> obtenerColaboradoresByIdCotizacion(Long idCotizacion);
	
	public List<ColaboradoresTempDto> obtenercotizacionesColaboradoresByIdCot(Long idCotizacion);
	
	public CotizacionTotalesDto guardarcotizacionTotal(CotizacionTotalesDto cotizacionColaborador);
	public void eliminadoLogicoCotizacionTotal(Long idCotizacion);
	public CotizacionTotalesDto obtenerTotalByIdCotizacion(Long idCotizacion);
	
	public ColaboradoresTempDto guardarEmpleado(ColaboradoresTempDto empl, UsuarioAplicativo usuarioAplicativo);
	public void eliminadoLogicoEmpleados(Long idCotizacion);
	void solicitarAutorizacionCotizacion(CotizacionDto cotizacion);
	List<CotizacionDto> obtenerCotizacionesXIdClienteTemp(Long idClienteTemp);
	void actualizarEstatusCotizaciones(List<CotizacionDto> listCotizaciones, UsuarioAplicativo usuarioAplicativo, String motivo);
	public Boolean rechazarProspecto(String motivo, UsuarioAplicativo usuarioAplicativo, Long idClienteTemp);
	public Boolean autorizarProspecto(UsuarioAplicativo usuarioAplicativo, Long idClienteTemp);
}
