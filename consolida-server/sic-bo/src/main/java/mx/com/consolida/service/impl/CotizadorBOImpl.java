package mx.com.consolida.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraDao;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraSolicitudesDao;
import mx.com.consolida.dao.interfaz.ColaboradorTempDao;
import mx.com.consolida.dao.interfaz.CotizacionColaboradorDao;
import mx.com.consolida.dao.interfaz.CotizacionTotalDao;
import mx.com.consolida.dao.interfaz.CotizadorDao;
import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.CotizacionTotalesDto;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.ClienteTempBO;
import mx.com.consolida.service.interfaz.CotizadorBO;

@Service
public class CotizadorBOImpl implements CotizadorBO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CotizadorBOImpl.class);
	
	@Autowired
	private CotizadorDao cotizadorDao;
	
	@Autowired
	private ColaboradorTempDao colaboradorDao;
	@Autowired
	private CotizacionColaboradorDao cotizacionColaboradorDao;
	@Autowired
	private CotizacionTotalDao cotizacionTotalDao;
	
	@Autowired
	private ClienteTempBitacoraDao clienteTempBitacoraDao;
	
	@Autowired
	private ClienteTempBO nuevoClienteBO;
	
	@Autowired
	private ClienteTempBitacoraSolicitudesDao clienteTempBitacoraSolicitudesDao;
	
	public CotizacionDto obtenerCotizacionById(Long id) {
		CotizacionDto dto = cotizadorDao.obtenerCotizacionById(id);
		ClienteTempBitacoraSolicitudesDto bitSolCot = nuevoClienteBO.obtenerBitacoraSolicitudesXIdCotizacion(dto.getIdCotizacion());
		if(bitSolCot!=null) {
			dto.setObservacionAutorizador(bitSolCot.getObservacion());
		}
		return dto;
	}
	
	public CotizacionDto obtenercotizacionAutorizar(Long idClienteTemp) {
		CotizacionDto dto = cotizadorDao.obtenercotizacionAutorizar(idClienteTemp);
		return dto;
	}
	
	public CotizacionDto guardarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo, Long idClienteTempBitacoraSolicitudes) {
		cotizacion.setIdEstatus(new CatGeneralDto(11l));//llamar por la clave a base de datos
		return cotizadorDao.guardarCotizacion(cotizacion, usuarioAplicativo, idClienteTempBitacoraSolicitudes);
	}
	
	public CotizacionDto editarCotizacion(CotizacionDto cotizacion, UsuarioAplicativo usuarioAplicativo) {
		return cotizadorDao.editarCotizacion(cotizacion, usuarioAplicativo);
	}

	public void guardarBitacora(ClienteTempBitacoraDto bitacora, UsuarioAplicativo usuarioAplicativo) {
		clienteTempBitacoraDao.guardarBitacora(bitacora, usuarioAplicativo);
	}

	public void guardarSolicitudCotizacion(ClienteTempBitacoraSolicitudesDto solicitarCotizacion, UsuarioAplicativo usuarioAplicativo) {
		clienteTempBitacoraSolicitudesDao.guardarSolicitudCotizacion(solicitarCotizacion, usuarioAplicativo);
	}
	
	
	public CotizacionColaboradorDto guardarcotizacionColaborador(CotizacionColaboradorDto cotizacionColaborador, UsuarioAplicativo usuarioAplicativo) {
		return cotizacionColaboradorDao.guardarcotizacionColaborador(cotizacionColaborador, usuarioAplicativo);
	}
	public CotizacionColaboradorDto obtenerCotizacionColaboradorById(Long idCotizacionColaborador) {
		return cotizacionColaboradorDao.obtenerCotizacionColaboradorById(idCotizacionColaborador);
	}
	public void eliminadoLogicocotizacionColaborador(Long idCotizacion) {
		cotizacionColaboradorDao.eliminadoLogicocotizacionColaborador(idCotizacion);
	}
	public List<CotizacionColaboradorDto> obtenerColaboradoresByIdCotizacion(Long idCotizacion) {
		return cotizacionColaboradorDao.obtenerColaboradoresByIdCotizacion(idCotizacion);
	}
	
	
	public CotizacionTotalesDto guardarcotizacionTotal(CotizacionTotalesDto cotizacionColaborador) {
		return cotizacionTotalDao.guardarcotizacionTotal(cotizacionColaborador);
	}
	public void eliminadoLogicoCotizacionTotal(Long idCotizacion) {
		cotizacionTotalDao.eliminadoLogicoCotizacionTotal(idCotizacion);
	}
	public CotizacionTotalesDto obtenerTotalByIdCotizacion(Long idCotizacion) {
		return cotizacionTotalDao.obtenerTotalByIdCotizacion(idCotizacion);
	}
	public ColaboradoresTempDto guardarEmpleado(ColaboradoresTempDto empl, UsuarioAplicativo usuarioAplicativo) {
		return colaboradorDao.guardarColaborador(empl, usuarioAplicativo);
	}
	public void eliminadoLogicoEmpleados(Long idCotizacion) {
		colaboradorDao.eliminadoLogicoEmpleados(idCotizacion);
	}
	public List<ColaboradoresTempDto> obtenercotizacionesColaboradoresByIdCot(Long idCotizacion) {
		return colaboradorDao.obtenercotizacionesColaboradoresByIdCot(idCotizacion);
	}

	public void solicitarAutorizacionCotizacion(CotizacionDto cotizacion) {
		 cotizadorDao.solicitarAutorizacionCotizacion(cotizacion);
	}

	public List<CotizacionDto> obtenerCotizacionesXIdClienteTemp(Long idClienteTemp) {
		return cotizadorDao.obtenerCotizacionesXIdClienteTemp(idClienteTemp);
	}

	public void actualizarEstatusCotizaciones(List<CotizacionDto> listCotizaciones, UsuarioAplicativo usuarioAplicativo, String motivo) {
		cotizadorDao.actualizarEstatusCotizaciones(listCotizaciones, usuarioAplicativo, motivo);
	}
	
	public Boolean rechazarProspecto(String motivo, UsuarioAplicativo usuarioAplicativo, Long idClienteTemp) {
		try {
			return cotizadorDao.rechazarProspecto(motivo, usuarioAplicativo, idClienteTemp);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en rechazarPropecto ", e);
			return Boolean.FALSE;
		}
	}
	
	public Boolean autorizarProspecto(UsuarioAplicativo usuarioAplicativo, Long idClienteTemp) {
		try {
			
			return cotizadorDao.autorizarProspecto(usuarioAplicativo, idClienteTemp);
	
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en autorizarPropecto ", e);
			return Boolean.FALSE;
		}
	}
}
