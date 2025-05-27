package mx.com.consolida.crm.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.CelulaClienteDao;
import mx.com.consolida.crm.dao.interfaz.CelulaDao;
import mx.com.consolida.crm.dao.interfaz.UsuarioCelulaDAO;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioSicofiDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.entity.seguridad.UsuarioCelula;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.ResponseUsuarioDTO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class CelulaBOImpl implements CelulaBO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CelulaBOImpl.class);

	@Autowired
	private CelulaDao celulaDao;

	@Autowired
	private UsuarioCelulaDAO usuarioCelulaDAO;

	@Autowired
	private CelulaClienteDao celulaClienteDao;

	@Autowired
	private UsuarioBO usuarioBO;

	@Transactional
	public void guardarCelula(CelulaDto celulaDto, Long idUsuarioAutenticado) {

		try {

			Celula celula = new Celula();

			celula.setIdCelula(celulaDto.getIdCelula());
			celula.setClave(celulaDto.getClave());
			celula.setNombre(celulaDto.getNombre());
			celula.setFechaAlta(new Date());

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(idUsuarioAutenticado);

			celula.setUsuarioAlta(usuario);
			celula.setIndEstatus(celulaDto.getIndEstatus());

			celulaDao.createOrUpdate(celula);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarCelula ", e);
		}

	}

	@Transactional
	public void guardarAdministrador(CelulaDto celulaDto, Long idUsuarioAutenticado) {
		// Iniciar por guardar el usuario 9 Gerente de celula
		Long idRolAdministrador = 9L;
		UsuarioDTO administradorCelula = celulaDto.getAdministrador();
		administradorCelula.setIndEstatus(celulaDto.getIndEstatus());
		celulaDto.getAdministrador().setRol(new RolDTO(idRolAdministrador));
		ResponseUsuarioDTO response = usuarioBO.guardarUsuario(administradorCelula, idUsuarioAutenticado);

		// Crear administrador
		if (administradorCelula.getIdUsuarioCelula() == null || administradorCelula.getIdUsuarioCelula() == 0L) {
			UsuarioCelula usaurioCelula = new UsuarioCelula();

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(response.getIdUsuario());

			Celula celula = new Celula();
			celula.setIdCelula(celulaDto.getIdCelula());
			usaurioCelula.setCelula(celula);

			usaurioCelula.setUsuario(usuario);
			usaurioCelula.setFechaAlta(new Date());
			usaurioCelula.setIndEstatus(celulaDto.getIndEstatus());

			usuarioCelulaDAO.create(usaurioCelula);
		} else {
			// Actualizar el administrador de la celula
			UsuarioCelula usaurioCelula = usuarioCelulaDAO.read(administradorCelula.getIdUsuarioCelula());
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(response.getIdUsuario());

			Celula celula = new Celula();
			celula.setIdCelula(celulaDto.getIdCelula());
			usaurioCelula.setCelula(celula);

			usaurioCelula.setUsuario(usuario);
			usaurioCelula.setFechaModificacion(new Date());
			usaurioCelula.setIndEstatus(celulaDto.getIndEstatus());

			usuarioCelulaDAO.update(usaurioCelula);
		}

	}

	@Transactional
	public void guardarIntegranteCelula(CelulaDto celulaDto, Long idUsuarioAutenticado) {
		// Iniciar por guardar el usuario

		UsuarioDTO integranteCelula = celulaDto.getUsuario();
		integranteCelula.setIndEstatus(celulaDto.getIndEstatus());

		ResponseUsuarioDTO response = usuarioBO.guardarUsuario(integranteCelula, idUsuarioAutenticado);

		// Crear administrador
		if (integranteCelula.getIdUsuarioCelula() == null || integranteCelula.getIdUsuarioCelula() == 0L) {
			UsuarioCelula usaurioCelula = new UsuarioCelula();

			Usuario usuario = new Usuario();
			usuario.setIdUsuario(response.getIdUsuario());

			Celula celula = new Celula();
			celula.setIdCelula(celulaDto.getIdCelula());
			usaurioCelula.setCelula(celula);

			usaurioCelula.setUsuario(usuario);
			usaurioCelula.setFechaAlta(new Date());
			usaurioCelula.setIndEstatus(celulaDto.getIndEstatus());

			usuarioCelulaDAO.create(usaurioCelula);
		} else {
			// Actualizar el administrador de la celula
			UsuarioCelula usaurioCelula = usuarioCelulaDAO.read(integranteCelula.getIdUsuarioCelula());
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(response.getIdUsuario());

			Celula celula = new Celula();
			celula.setIdCelula(celulaDto.getIdCelula());
			usaurioCelula.setCelula(celula);

			usaurioCelula.setUsuario(usuario);
			usaurioCelula.setFechaModificacion(new Date());
			usaurioCelula.setIndEstatus(celulaDto.getIndEstatus());

			usuarioCelulaDAO.update(usaurioCelula);
		}

	}

	@Transactional(readOnly = true)
	public List<CelulaDto> listarTodasLasCelulas() {
		try {

			return celulaDao.listarTodasCelularRegistradas();

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}

	@Transactional(readOnly = true)
	public CelulaDto consultarDatosCelula(String rfcCelula) {
		try {

			return celulaDao.consultarDatosCelula(rfcCelula);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}

	@Transactional(readOnly = true)
	public CelulaDto consultarDatosCelula(Long idRol, Long idUsuario) {
		try {

			return celulaDao.consultarDatosCelula(idRol, idUsuario);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<CelulaDto> consultarCelulaPorUsuario(Long idUsuario) {
		try {

			return celulaDao.consultarCelulaPorUsuario(idUsuario);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en consultarCelulaPorUsuario ", e);
			return null;
		}
	}

	@Transactional(readOnly = true)
	public List<UsuarioDTO> consultarUsuariosCelula(Long idCelula) {
		try {

			return celulaDao.consultarUsuariosCelula(idCelula);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
		}

		return null;
	}

	@Transactional
	public void eliminarCelula(Long idCelula) {
		try {

			Celula celula = celulaDao.read(idCelula);
			celula.setIndEstatus("0");

			celulaDao.update(celula);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarCelula {}", idCelula, e);
		}
	}

	@Transactional(readOnly = true)
	public List<UsuarioDTO> consultarUsuariosByCelulaRol(Long idCelula, Long idRol) {
		try {

			return celulaDao.consultarUsuariosByCelulaRol(idCelula, idRol);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
		}

		return null;
	}

	@Transactional(readOnly = true)
	public CelulaDto consultarDatosCelulaByIdCliente(Long idCliente) {
		try {

			CelulaCliente celulaCliente = celulaClienteDao.getCelulaByIdCliente(idCliente);

			if (celulaCliente != null && celulaCliente.getCelula() != null
					&& celulaCliente.getCelula().getIdCelula() != null) {
				CelulaDto celulaDto = new CelulaDto();
				celulaDto.setIdCelula(celulaCliente.getCelula().getIdCelula());
				celulaDto.setNombre(celulaCliente.getCelula().getNombre());

				return celulaDto;
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en consultarDatosCelulaByIdCliente ", e);
		}

		return null;
	}

	@Override
	public List<CelulaDto> listarTodasLasCelulasCliente() {
		try {

			return celulaDao.listarTodasCelulasCliente();

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}

	@Override
	public List<PrestadoraServicioSicofiDto> listaPrestadorasSicofi() {
		try {

			return celulaDao.listaPrestadorasSicofi();

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}
	
	@Override
	public List<PrestadoraServicioSicofiDto> listaPrestadorasSicofiProvision(String claveProvision , String fondo ) {
		try {

			return celulaDao.listaPrestadorasSicofiProvision( claveProvision, fondo );

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarTodasLasCelulas ", e);
			return null;
		}
	}

}
