package mx.com.consolida.service.usuario;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.UsuarioCelulaDAO;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.dao.usuario.DatosGeneralesUsuarioDAO;
import mx.com.consolida.dao.usuario.PersonaDAO;
import mx.com.consolida.dao.usuario.UsuarioDAO;
import mx.com.consolida.dao.usuario.UsuarioRolDAO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.seguridad.Persona;
import mx.com.consolida.entity.seguridad.Rol;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.entity.seguridad.UsuarioCelula;
import mx.com.consolida.entity.seguridad.UsuarioRol;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ResponseDTO;
import mx.com.consolida.generico.ResponseEstatusEnum;
import mx.com.consolida.usuario.ResponseUsuarioDTO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class UsuarioBOImpl implements UsuarioBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioBOImpl.class);

	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@Autowired
	private PersonaDAO personaDAO;
	
	@Autowired
	private UsuarioRolDAO usuarioRolDAO;
	
	@Autowired
	private UsuarioCelulaDAO usuarioCelulaDAO;
	
	@Autowired
	private DatosGeneralesUsuarioDAO datosGenerales;
	
	
	@Transactional
	public ResponseDTO existeUsuario(UsuarioDTO usuarioDTO){
		ResponseDTO response = new ResponseDTO();
		
		Usuario usuario = usuarioDAO.getExisteUsuario(usuarioDTO);
		
		if(usuario != null){
			response.setEstatusResponse(ResponseEstatusEnum.ERROR.name());
			response.setMensajeError("La cuenta de correo electronico no esta disponible");
		}else{
			response.setEstatusResponse(ResponseEstatusEnum.SUCCESS.name());
			
		}
		
		return response;
	}
	
	
	@Transactional
	public ResponseDTO obtenerUsuario(UsuarioDTO usuarioDTO){
		
		ResponseDTO response = new ResponseDTO();
		
		Usuario usuario = usuarioDAO.getUsuarioAutenticacion(usuarioDTO);
		
		if(usuario != null){
			response.setEstatusResponse(ResponseEstatusEnum.SUCCESS.name());
		}else{
			response.setEstatusResponse(ResponseEstatusEnum.ERROR.name());
			response.setMensajeError("No se encontro ningun usuario");
		}
		
		return response;
	}
	
	@Transactional
	public UsuarioDTO usuarioAutenticacion(String user) throws Exception{
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		Usuario usuario = usuarioDAO.getExisteUsuario(user);
		
		if(usuario != null){
			ModelMapper model = new ModelMapper();
			model.getConfiguration().setAmbiguityIgnored(true);
			usuarioDTO = model.map(usuario, UsuarioDTO.class);
			usuarioDTO.setIdUsuario(usuario.getIdUsuario());
			usuarioDTO.setUsuario(usuario.getUsuario());
			usuarioDTO.setPassword(usuario.getPassword());
			Persona datosUsuario = usuario.getPersona();
			
			if(datosUsuario != null) {
				usuarioDTO.setNombre(datosUsuario.getNombre());
				usuarioDTO.setPrimerApellido(datosUsuario.getApellidoPaterno());
				usuarioDTO.setSegundoApellido(datosUsuario.getApellidoMaterno());
			}
			List<CelulaDto> celula =usuarioDAO.getCelula(usuario.getIdUsuario());
			usuarioDTO.setCelula(celula.get(0));
		

			
		}else{
			throw new Exception("No se encontro el usuario o no se encuentra activo");
		}
		
		return usuarioDTO;
	}

	@Transactional
	public ResponseUsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO , Long idPersonaAutenticada) {
		ResponseUsuarioDTO responseUsuarioDTO = new ResponseUsuarioDTO();
		
		if(usuarioDTO.getPassword() != null) {
			String md5Hex = DigestUtils
				      .md5Hex(usuarioDTO.getPassword());
			
			usuarioDTO.setPassword(md5Hex);
		}
		
		Persona persona = null;
		if(usuarioDTO.getIdPersona() == null || usuarioDTO.getIdPersona()== 0L ) {
			
			persona = new Persona();
			persona.setNombre(usuarioDTO.getNombre());
			persona.setApellidoPaterno(usuarioDTO.getPrimerApellido());
			persona.setApellidoMaterno(usuarioDTO.getSegundoApellido());
			persona.setCorreo_electronico(usuarioDTO.getCorreo());
			persona.setTelefono(usuarioDTO.getTelefono());
			persona.setRfc(usuarioDTO.getRfc());
			persona.setUsuarioAlta(idPersonaAutenticada);
			persona.setFechaAlta(new Date());
			persona.setIndEstatus(usuarioDTO.getIndEstatus());
			datosGenerales.create(persona);
		}else {
			persona = personaDAO.read(usuarioDTO.getIdPersona());
			persona.setNombre(usuarioDTO.getNombre());
			persona.setApellidoPaterno(usuarioDTO.getPrimerApellido());
			persona.setApellidoMaterno(usuarioDTO.getSegundoApellido());
			persona.setCorreo_electronico(usuarioDTO.getCorreo());
			persona.setTelefono(usuarioDTO.getTelefono());
			persona.setRfc(usuarioDTO.getRfc());
			persona.setFechaModificacion(new Date());
			persona.setUsuarioModificacion(idPersonaAutenticada);
			persona.setIndEstatus(usuarioDTO.getIndEstatus());
			datosGenerales.update(persona);
		}
		
		Usuario usuario = null;
		
		if(usuarioDTO.getIdUsuario() == null || usuarioDTO.getIdUsuario() == 0L) {
			usuario = new Usuario();
			usuario.setUsuario(usuarioDTO.getUsuario());
			usuario.setPassword(usuarioDTO.getPassword());	
			usuario.setFechaAlta(new Date());
			usuario.setIndEstatus(usuarioDTO.getIndEstatus());
			usuario.setPersona(persona);
			usuario.setReintentos(0);
			usuarioDAO.create(usuario);
		}else {
			usuario = usuarioDAO.read(usuarioDTO.getIdUsuario());
			usuario.setUsuario(usuarioDTO.getUsuario());

			//Si no lo mandan a actualizar se deja el password anterior
			if(usuarioDTO.getPassword() != null) {
				usuario.setPassword(usuarioDTO.getPassword());	
			}
			
			usuario.setFechaModificacion(new Date());
			usuario.setIndEstatus(usuarioDTO.getIndEstatus());
			usuario.setPersona(persona);
			usuario.setReintentos(0);
			usuarioDAO.update(usuario);
		}
		
		
		if(usuarioDTO.getIdUsuarioRol() == null || usuarioDTO.getIdUsuarioRol()  == 0L) {
			UsuarioRol usuarioRol = new UsuarioRol();
			Rol rol = new Rol();
			rol.setIdRol(usuarioDTO.getRol().getIdRol());
			usuarioRol.setRol(rol);
			
			usuarioRol.setUsuario(usuario);
			usuarioRol.setFechaAlta(new Date());
			usuarioRol.setIndEstatus(usuarioDTO.getIndEstatus());
			usuarioRolDAO.create(usuarioRol);
		}else {
			UsuarioRol usuarioRol = usuarioRolDAO.read(usuarioDTO.getIdUsuarioRol());
			Rol rol = new Rol();
			rol.setIdRol(usuarioDTO.getRol().getIdRol());
			usuarioRol.setRol(rol);
			
			usuarioRol.setUsuario(usuario);
			usuarioRol.setFechaModificacion(new Date());
			usuarioRol.setIndEstatus(usuarioDTO.getIndEstatus());
			usuarioRolDAO.update(usuarioRol);
		}
		responseUsuarioDTO.setIdUsuario(usuario.getIdUsuario());
		responseUsuarioDTO.setIdPersona(persona.getIdPersona());
		
		return responseUsuarioDTO;
	}


	public void activarCuentaUsuario(String token) throws BusinessException {
		// TODO Auto-generated method stub
		
	}
	
//	@Transactional
//	public UsuarioMonitorDTO getDatosGenerales(UsuarioMonitorDTO userBilleDTO){
//		
//		DatosGeneralesUsuario usuarioDB = datosGeneralesUsuarioDAO.datosGeneralesByUsuario(userBilleDTO.getIdUsuario());
//		
//		if(usuarioDB != null){
//			userBilleDTO.setNombre(usuarioDB.getNombre());
//			userBilleDTO.setSegundoApellido(usuarioDB.getPrimerApellido());
//			userBilleDTO.setPrimerApellido(usuarioDB.getPrimerApellido());
//		}else{
//			return userBilleDTO;
//		}
//		
//		return userBilleDTO;
//	}
	

	
	@Transactional
	public List<UsuarioDTO> getUsuarios(){
		return usuarioDAO.getUsuarios();
	}


	@Transactional(readOnly = true)
	public List<UsuarioDTO> getUsuariosByRol(Long idRol) {
		try {
			return usuarioDAO.getListUsuariosByRol(idRol);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getUsuariosByRol ", e);
			return Collections.emptyList();
		}
		
	}
	
	@Transactional(readOnly = true)
	public Long getIdCelulaByIdUsuario(Long idUsuario) {
		try {
			
			UsuarioCelula usuarioCel =  usuarioCelulaDAO.getUsuarioCelulaByIdUsuario(idUsuario);
			
			if(usuarioCel!=null && usuarioCel.getCelula().getIdCelula()!=null) {
				return usuarioCel.getCelula().getIdCelula();
			}else {
				return null;
			}
				
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getIdCelulaByIdUsuario ", e);
			return null;
		}
	}
	
	
	@Transactional(readOnly = true)
	public CelulaDto getDatosCelula(Long idUsuario) {
		try {
				
			CelulaDto celula= new CelulaDto();
			List<CelulaDto> celul =usuarioDAO.getCelula(idUsuario);
			celula.setIdCelula(celul.get(0).getIdCelula());
			celula.setNombre(celul.get(0).getNombre());
					
		
			
			if(celula!=null && celula.getIdCelula()!=null) {
				return celula;
			}else {
				return new CelulaDto();
			}
				
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosCelula ", e);
			return null;
		}
	}

}
