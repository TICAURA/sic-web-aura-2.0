package mx.com.consolida.service.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.service.interfaz.DomicilioBO;
import mx.com.consolida.dao.admin.CanalVentaCuentaBancariaDAO;
import mx.com.consolida.dao.admin.CanalVentaDAO;
import mx.com.consolida.dao.admin.OficinaCanalVentaDAO;
import mx.com.consolida.dao.admin.OficinaCuentaBancariaDAO;
import mx.com.consolida.dao.admin.OficinaDAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.CanalVenta;
import mx.com.consolida.entity.CanalVentaCuentaBancaria;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.Oficina;
import mx.com.consolida.entity.OficinaCanalVenta;
import mx.com.consolida.entity.OficinaCuentaBancaria;
import mx.com.consolida.entity.crm.CatEntidadFederativa;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.PersonaDto;
import mx.com.consolida.usuario.ResponseUsuarioDTO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class CanalVentaBOImpl implements CanalVentaBO {

	@Autowired
	private CanalVentaDAO canalVentaDAO;
	
	@Autowired
	private OficinaDAO oficinaDAO;
	
	@Autowired
	private OficinaCuentaBancariaDAO oficinaCuentaBancariaDAO;
	
	@Autowired
	private DomicilioBO domicilioBO;
	
	@Autowired
	private CanalVentaCuentaBancariaDAO canalVentaCuentaBancariaDAO;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	@Autowired
	private OficinaCanalVentaDAO oficinaCanalVentaDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public void guardarCanalVenta(CanalVentaDto canalVentaDto , Long idUsuarioAutenticado) {
		
		canalVentaDto.getUsuario().setRol(new RolDTO(RolUsuarioENUM.PROMOTOR_VENTAS.getId()));
		ResponseUsuarioDTO response = usuarioBO.guardarUsuario(canalVentaDto.getUsuario(), idUsuarioAutenticado);
		
		
		Long idDomicilio = domicilioBO.guardarDomicilio(canalVentaDto.getDomicilio(), idUsuarioAutenticado);
		Usuario usuarioAlta = new Usuario();
		usuarioAlta.setIdUsuario(idUsuarioAutenticado);
		
		
		 
		 
		 CanalVenta canalVenta = new CanalVenta();
		 if(canalVentaDto.getIdCanalVenta() != null && canalVentaDto.getIdCanalVenta()>0) {
			 canalVenta = canalVentaDAO.read(canalVentaDto.getIdCanalVenta());
		 }
		 
		 canalVenta.setCiudadSede(canalVentaDto.getCiudadSede());
		 canalVenta.setEmpresaOficina(canalVentaDto.getEmpresaOficina());
		 
		 canalVenta.setGeneraCotizacion(canalVentaDto.getGeneraCotizacion());
		 
		 Domicilio domicilio = new Domicilio();
		 domicilio.setIdDomicilio(idDomicilio);
		 canalVenta.setIdDomicilio(domicilio);
		 
		 CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
		 catEntidadFederativa.setIdEntidadFederativa(canalVentaDto.getEntidadFederativaSede().getIdCatGeneral());
		 canalVenta.setIdEntidadFederativaSede(catEntidadFederativa);
		 
		 CatGeneral tipoCanalVenta = new CatGeneral();
		 tipoCanalVenta.setIdCatGeneral(canalVentaDto.getTipoCanalVenta().getIdCatGeneral());
		 canalVenta.setIdTipoCanalVenta(tipoCanalVenta);
		 Usuario usuario = new Usuario();
		 usuario.setIdUsuario(response.getIdUsuario());
		 
		 canalVenta.setIdUsuarioCanalVenta(usuario);
		 canalVenta.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		 canalVenta.setPagoOficina(canalVentaDto.getPagoOficina());
		 canalVenta.setRazonSocial(canalVentaDto.getRazonSocial());
		 
		 
		 if(canalVentaDto.getIdCanalVenta() != null && canalVentaDto.getIdCanalVenta()>0) {
			 canalVenta.setUsuarioModificacion(usuarioAlta);
			 canalVenta.setFechaModificaicon(new Date());
		 }else {
			 canalVenta.setUsuarioAlta(usuarioAlta);
			 canalVenta.setFechaAlta(new Date());
		 }
		 
		 canalVentaDAO.createOrUpdate(canalVenta);
		 
		 
		 
		CuentaBancariaDto cuentaBancariaDto = canalVentaDto.getCuentaBancaria();
			
		CanalVentaCuentaBancaria  canalCuentaBancaria= new CanalVentaCuentaBancaria();
		
		
		if(cuentaBancariaDto.getIdCanalVentaCuentaBancaria() != null && cuentaBancariaDto.getIdCanalVentaCuentaBancaria() > 0) {
			canalCuentaBancaria = canalVentaCuentaBancariaDAO.read(cuentaBancariaDto.getIdCanalVentaCuentaBancaria());
		}
		
		CatGeneral banco = new CatGeneral();
		banco.setIdCatGeneral(cuentaBancariaDto.getCatBanco().getIdCatGeneral());

		canalCuentaBancaria.setIdBanco(banco);
		canalCuentaBancaria.setNumeroCuenta(cuentaBancariaDto.getNumeroCuenta());
		canalCuentaBancaria.setClabeInterbancaria(cuentaBancariaDto.getClabeInterbancaria());
		canalCuentaBancaria.setIdCanalVenta(canalVenta);
		// 55 Recepcion de depositos
		CatGeneral catTipoCuenta  = new CatGeneral();
		catTipoCuenta.setIdCatGeneral(55L);
		canalCuentaBancaria.setIdTipoCuenta(catTipoCuenta);
		canalCuentaBancaria.setIndEstatus(1L);
		if(cuentaBancariaDto.getIdCanalVentaCuentaBancaria() != null && cuentaBancariaDto.getIdCanalVentaCuentaBancaria() > 0) {
			
			canalCuentaBancaria.setUsuarioModificacion(usuarioAlta);
			canalCuentaBancaria.setFechaModificacion(new Date());
			
		}else {
			canalCuentaBancaria.setUsuarioAlta(usuarioAlta);
			canalCuentaBancaria.setFechaAlta(new Date());
			
		}
		

		canalVentaCuentaBancariaDAO.createOrUpdate(canalCuentaBancaria);
		
		 Long idCanalCuentaBancaria = canalCuentaBancaria.getIdCanalVentaCuentaBancaria();
		 
	}
	
	
	
	@Override
	@Transactional
	public void guardarOficinaCanalVenta(OficinaDto oficinalDto , Long idUsuarioAutenticado) {
		
		Long idDomicilio = domicilioBO.guardarDomicilio(oficinalDto.getDomicilio(), idUsuarioAutenticado);
		
		Usuario usuarioAlta = new Usuario();
		usuarioAlta.setIdUsuario(idUsuarioAutenticado);
		
		 Oficina oficina = new Oficina();
		 if(oficinalDto.getIdOficina() != null && oficinalDto.getIdOficina()>0) {
			 oficina = oficinaDAO.read(oficinalDto.getIdOficina());
		 }
		 
		 oficina.setClave(oficinalDto.getClave());
		 oficina.setDescripcion(oficinalDto.getDescripcion());
		 oficina.setRfc(oficinalDto.getRfc());
		 oficina.setRazonSocial(oficinalDto.getRazonSocial());
		 oficina.setNombreContacto(oficinalDto.getNombreContacto());
		 oficina.setCorreoElectronicoContacto(oficinalDto.getCorreoElectronicoContacto());
		 oficina.setTelefonoContacto(oficinalDto.getTelefonoContacto());
		 oficina.setCiudadSede(oficinalDto.getCiudadSede());
		 
		 Domicilio domicilio = new Domicilio();
		 domicilio.setIdDomicilio(idDomicilio);
		 oficina.setIdDomicilio(domicilio);
		 
		 CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
		 catEntidadFederativa.setIdEntidadFederativa(oficinalDto.getEntidadFederativaSede().getIdCatGeneral());
		 oficina.setIdEntidadFederativaSede(catEntidadFederativa);
		 
		 oficina.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		 
		 
		 if(oficinalDto.getIdOficina() != null && oficinalDto.getIdOficina()>0) {
			 oficina.setUsuarioModificacion(usuarioAlta);
			 oficina.setFechaModificacion(new Date());
		 }else {
			 oficina.setUsuarioAlta(usuarioAlta);
			 oficina.setFechaAlta(new Date());
		 }
		 
		 oficinaDAO.createOrUpdate(oficina);
		 
		CuentaBancariaDto cuentaBancariaDto = oficinalDto.getCuentaBancaria();
			
		OficinaCuentaBancaria  canalCuentaBancaria= new OficinaCuentaBancaria();
		
		
		if(cuentaBancariaDto.getIdOficinaCuentaBancaria() != null && cuentaBancariaDto.getIdOficinaCuentaBancaria() > 0) {
			canalCuentaBancaria = oficinaCuentaBancariaDAO.read(cuentaBancariaDto.getIdOficinaCuentaBancaria());
		}
		
		CatGeneral banco = new CatGeneral();
		banco.setIdCatGeneral(cuentaBancariaDto.getCatBanco().getIdCatGeneral());

		canalCuentaBancaria.setIdBanco(banco);
		canalCuentaBancaria.setNumeroCuenta(cuentaBancariaDto.getNumeroCuenta());
		canalCuentaBancaria.setClabeInterbancaria(cuentaBancariaDto.getClabeInterbancaria());
		canalCuentaBancaria.setIdOficina(oficina);
		// 55 Recepcion de depositos
		CatGeneral catTipoCuenta  = new CatGeneral();
		catTipoCuenta.setIdCatGeneral(55L);
		canalCuentaBancaria.setIdTipoCuenta(catTipoCuenta);
		canalCuentaBancaria.setIndEstatus(1L);
		if(cuentaBancariaDto.getIdOficinaCuentaBancaria() != null && cuentaBancariaDto.getIdOficinaCuentaBancaria() > 0) {
			
			canalCuentaBancaria.setUsuarioModificacion(usuarioAlta);
			canalCuentaBancaria.setFechaModificacion(new Date());
			
		}else {
			canalCuentaBancaria.setUsuarioAlta(usuarioAlta);
			canalCuentaBancaria.setFechaAlta(new Date());
			
		}
		

		oficinaCuentaBancariaDAO.createOrUpdate(canalCuentaBancaria);
		
	}
	
	
	@Transactional
	public List<CanalVentaDto> listarTodasCanalesVenta() {
		return canalVentaDAO.listarTodasCanalesVenta();
	}

	

	@Transactional
	public CanalVentaDto obtenerCanalVentaByIdUsuario(Long idUsuario) {
		return canalVentaDAO.obtenerCanalVentaByIdUsuario(idUsuario);
	}
	
	@Transactional
	public CanalVentaDto obtenerCanalVentaByIdCanalVenta(Long idCanalVenta) {
		return canalVentaDAO.obtenerCanalVentaByIdCanalVenta(idCanalVenta);
	}
	
	
	@Transactional
	public List<OficinaDto> listarTodasOficinas(){
		return canalVentaDAO.listarTodasOficinas();
	}
	
	
	@Transactional
	public OficinaDto oficinaByIdOficina(Long idOficina) {
		return canalVentaDAO.oficinaByIdOficina(idOficina);
	}


	@Transactional
	public List<OficinaDto> obtenerOficinas() {
		
		return canalVentaDAO.obtenerOficinas();
	}
	
	
	@Transactional
	public Long asignacionOficinaCanalVenta(CanalVentaDto canalVentaDto  , Long idUsuarioAutenticado) {
		OficinaCanalVenta oficinaCanalVenta = new OficinaCanalVenta() ;
		
		if(canalVentaDto.getOficina() != null && canalVentaDto.getOficina().getIdOficinaCanalVenta() != null && canalVentaDto.getOficina().getIdOficinaCanalVenta() >0) {
			oficinaCanalVenta = oficinaCanalVentaDAO.read(canalVentaDto.getOficina().getIdOficinaCanalVenta());
		}
		
		Oficina oficina = new Oficina();
		oficina.setIdOficina(canalVentaDto.getOficina().getIdOficina());
		
		oficinaCanalVenta.setIdOficina(oficina);
		
		CanalVenta canalVenta = new CanalVenta();
		canalVenta.setIdCanalVenta(canalVentaDto.getIdCanalVenta());
		
		
		oficinaCanalVenta.setIdCanalVenta(canalVenta);
		
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(idUsuarioAutenticado);
		
		
		if(canalVentaDto.getOficina() != null && canalVentaDto.getOficina().getIdOficinaCanalVenta() != null && canalVentaDto.getOficina().getIdOficinaCanalVenta() >0) {
			oficinaCanalVenta.setUsuarioModificacion(usuario);
			oficinaCanalVenta.setFechaModificacion(new Date());
		}else {
			oficinaCanalVenta.setUsuarioAlta(usuario);
			oficinaCanalVenta.setFechaAlta(new Date());
		}
		
		
		
		oficinaCanalVenta.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		oficinaCanalVentaDAO.createOrUpdate(oficinaCanalVenta);
		
		
		return oficinaCanalVenta.getIdOficinaCanalVenta();
		
	}	
		
		

	
	@Transactional
	public List<CanalVentaDto> obtenerPromotores(){
		return canalVentaDAO.obtenerPromotores();

	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional
	public List<UsuarioDTO> obtenerUsuarioXTipoCanalVenta(String idTipoCanalVenta) {
		
			List<UsuarioDTO> listUsuarios =  new ArrayList<UsuarioDTO>();
			try {
				
				StringBuilder sb = new StringBuilder();
				sb.append(" select u.id_usuario, u.id_persona, p.id_persona, p.nombre, p.apellido_paterno ,p.apellido_materno, p.rfc, p.correo_electronico, p.telefono, ");
				sb.append(" concat_ws( ' ', p.nombre, p.apellido_paterno, p.apellido_materno) as nombreCompleto ");
				sb.append(" from canal_venta cv join usuario u on u.id_usuario=cv.id_usuario_canal_venta join persona p on p.id_persona = u.id_persona ");
				sb.append(" where cv.id_tipo_canal_venta = ?");
				
				return jdbcTemplate.query(sb.toString(), new Object[] {idTipoCanalVenta}, new RowMapper() {
					public UsuarioDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
						UsuarioDTO usuario = new UsuarioDTO();
						usuario.setPersona(new PersonaDto());
						usuario.setIdUsuario(rs.getLong("id_usuario"));
						usuario.setIdPersona(rs.getLong("id_persona"));
						
						usuario.getPersona().setIdPersona(rs.getLong("id_persona"));
						usuario.getPersona().setNombre(rs.getString("nombre"));
						usuario.getPersona().setApellidoPaterno(rs.getString("apellido_paterno"));
						usuario.getPersona().setApellidoMaterno(rs.getString("apellido_materno"));
						usuario.getPersona().setRfc(rs.getString("rfc"));
						usuario.setNombreCompleto(rs.getString("nombreCompleto"));
						listUsuarios.add(usuario);
						return usuario;	
					}
				});
				
			}catch (Exception e) {
				return Collections.emptyList();
			}
	}
}
