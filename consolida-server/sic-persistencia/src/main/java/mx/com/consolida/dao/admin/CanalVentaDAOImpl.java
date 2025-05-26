package mx.com.consolida.dao.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.CanalVentaAsignacionOficinaDto;
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.entity.CanalVenta;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class CanalVentaDAOImpl extends GenericDAO<CanalVenta, Long> implements CanalVentaDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(CanalVentaDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CanalVentaDto> listarTodasCanalesVenta() {

		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();

			sb.append(
					" SELECT cv.id_canal_venta  , cv.ciudad_sede , cv.empresa_oficina  , cv.genera_cotizacion ,cv.id_domicilio  ");
			sb.append(
					" , cv.id_entidad_federativa_sede  ,cv.id_tipo_canal_venta , cgtc.clave  as claveTipoCanal , cgtc.descripcion  as descTipoCanal ,cv.id_usuario_canal_venta  ,cv.ind_estatus   ");
			sb.append(" , cv.pago_oficina , cv.razon_social  ");
			sb.append(" , u.id_usuario ,u.usuario , u.ind_estatus as indEstatusUsuario    ");
			sb.append(
					" , p.id_persona ,   p.nombre as nombrePersona   ,p.apellido_paterno , p.apellido_materno ,  p.correo_electronico  , p.telefono , p.rfc as rfcCanalVenta    ");
			sb.append(" , r.id_rol ,r.nombre as nombreRol  , r.descripcion as descRol , ur.id_usuario_rol   ");
			sb.append(
					" , ocv.id_oficina_canal_venta  , o.id_oficina , o.rfc as rfcOficina  , o.razon_social as razonSocialOficina  ");
			sb.append(
					" , d.codigo_postal  ,d.calle , d.colonia  , d.id_domicilio , d.id_entidad_federativa , d.id_municipio  ,d.id_tipo_domicilio,  d.ind_estatus  ");
			sb.append(" , d.numero_exterior  , d.numero_interior  ");
			sb.append(
					" , cvcb.id_canal_venta_cuenta_bancaria  , cvcb.id_banco  , cvcb.id_tipo_cuenta  ,cvcb.ind_estatus  , cvcb.numero_cuenta  , cvcb.clabe_interbancaria  ");
			sb.append(" , cgcs.cve_entidad_federativa , cgcs.descripcion_entidad_federativa ");
			sb.append(" from canal_venta cv   ");
			sb.append(" inner join canal_venta_cuenta_bancaria cvcb on cvcb.id_canal_venta  = cv.id_canal_venta  ");
			sb.append(" inner join usuario  u on u.id_usuario  = cv.id_usuario_canal_venta  ");
			sb.append(" inner join persona  p on u.id_persona  = p.id_persona  ");
			sb.append(" inner join usuario_rol  ur on ur.id_usuario = u.id_usuario  ");
			sb.append(" inner join rol r on r.id_rol  = ur.id_rol ");
			sb.append(" inner join domicilio  d on cv.id_domicilio  = d.id_domicilio  ");
			sb.append(" inner join cat_general  cgtc on cgtc .id_cat_general  = cv.id_tipo_canal_venta  ");
			sb.append(
					" inner join cat_entidad_federativa cgcs on cgcs.id_entidad_federativa = cv.id_entidad_federativa_sede ");
			sb.append(" left join oficina_canal_venta  ocv on ocv.id_canal_venta  = cv.id_canal_venta  ");
			sb.append(" left join oficina  o on o.id_oficina  = ocv.id_oficina  ");

			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					CanalVentaDto canalVenta = new CanalVentaDto();


					canalVenta.setIdCanalVenta(rs.getLong("id_canal_venta"));
					canalVenta.setCiudadSede(rs.getString("ciudad_sede"));
					canalVenta.setEmpresaOficina(rs.getString("empresa_oficina"));

					canalVenta.setGeneraCotizacion(rs.getString("genera_cotizacion"));

					canalVenta.setIdUsuarioCanalVenta(rs.getLong("id_usuario_canal_venta"));
					canalVenta.setIndEstatus(rs.getString("ind_estatus"));
					canalVenta.setPagoOficina(rs.getString("pago_oficina"));
					canalVenta.setRazonSocial(rs.getString("razon_social"));

					CatGeneralDto tipoCanalVenta = new CatGeneralDto();
					tipoCanalVenta.setIdCatGeneral(rs.getLong("id_tipo_canal_venta"));
					tipoCanalVenta.setClave(rs.getString("claveTipoCanal"));
					tipoCanalVenta.setDescripcion(rs.getString("descTipoCanal"));
					canalVenta.setTipoCanalVenta(tipoCanalVenta);

					DomicilioDto domicilio = new DomicilioDto();

					domicilio.setCalle(rs.getString("calle"));
					domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
					domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
					domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
					domicilio.setCodigoPostal(rs.getString("codigo_postal"));
					domicilio.setColonia(rs.getString("colonia"));
					domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
					domicilio.setIndEstatus(rs.getLong("ind_estatus"));
					domicilio.setNumeroExterior(rs.getString("numero_exterior"));
					domicilio.setNumeroInterior(rs.getString("numero_interior"));
					canalVenta.setDomicilio(domicilio);

					canalVenta.setEntidadFederativaSede(new CatGeneralDto(rs.getLong("id_entidad_federativa_sede"),
							rs.getString("cve_entidad_federativa"), rs.getString("descripcion_entidad_federativa")));

					UsuarioDTO usuarioDTO = new UsuarioDTO();
					usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
					usuarioDTO.setIdPersona(rs.getLong("id_persona"));
					usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));

					usuarioDTO.setUsuario(rs.getString("usuario"));
					usuarioDTO.setNombre(rs.getString("nombrePersona"));
					usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
					usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
					usuarioDTO.setCorreo(rs.getString("correo_electronico"));
					usuarioDTO.setTelefono(rs.getString("telefono"));
					usuarioDTO.setRfc(rs.getString("rfcCanalVenta"));
					usuarioDTO.setIndEstatus(rs.getString("indEstatusUsuario"));
					
					RolDTO rol = new RolDTO();
					rol.setIdRol(rs.getLong("id_rol"));
					rol.setNombre(rs.getString("nombreRol"));
					rol.setDescripcion(rs.getString("descRol"));
					usuarioDTO.setRol(rol);

					CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
					cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
					cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
					cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

					cuentaBancariaDto.setIdCanalVentaCuentaBancaria(rs.getLong("id_canal_venta_cuenta_bancaria"));
					cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

					canalVenta.setCuentaBancaria(cuentaBancariaDto);
					canalVenta.setUsuario(usuarioDTO);

					OficinaDto oficinaDTO = new OficinaDto();
					oficinaDTO.setIdOficina(rs.getLong("id_oficina"));
					oficinaDTO.setRfc(rs.getString("rfcOficina"));
					oficinaDTO.setRazonSocial(rs.getString("razonSocialOficina"));
					oficinaDTO.setIdOficinaCanalVenta(rs.getLong("id_oficina_canal_venta"));
					
					canalVenta.setOficina(oficinaDTO);

					return canalVenta;

				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}

	}

	
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CanalVentaDto obtenerCanalVentaByIdCanalVenta(Long idCanalVenta) {

		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();

			sb.append(
					" SELECT cv.id_canal_venta  , cv.ciudad_sede , cv.empresa_oficina  , cv.genera_cotizacion ,cv.id_domicilio  ");
			sb.append(
					" , cv.id_entidad_federativa_sede  ,cv.id_tipo_canal_venta , cgtc.clave  as claveTipoCanal , cgtc.descripcion  as descTipoCanal ,cv.id_usuario_canal_venta  ,cv.ind_estatus   ");
			sb.append(" , cv.pago_oficina , cv.razon_social  ");
			sb.append(" , u.id_usuario ,u.usuario  , u.ind_estatus as indEstatusUsuario  ");
			sb.append(
					" , p.id_persona ,   p.nombre as nombrePersona   ,p.apellido_paterno , p.apellido_materno ,  p.correo_electronico  , p.telefono , p.rfc as rfcCanalVenta    ");
			sb.append(" , r.id_rol ,r.nombre as nombreRol  , r.descripcion as descRol , ur.id_usuario_rol   ");
			sb.append(
					" , ocv.id_oficina_canal_venta  , o.id_oficina , o.rfc as rfcOficina  , o.razon_social as razonSocialOficina  ");
			sb.append(
					" , d.codigo_postal  ,d.calle , d.colonia  , d.id_domicilio , d.id_entidad_federativa , d.id_municipio  ,d.id_tipo_domicilio,  d.ind_estatus  ");
			sb.append(" , d.numero_exterior  , d.numero_interior  ");
			sb.append(
					" , cvcb.id_canal_venta_cuenta_bancaria  , cvcb.id_banco  , cvcb.id_tipo_cuenta  ,cvcb.ind_estatus  , cvcb.numero_cuenta  , cvcb.clabe_interbancaria  ");
			sb.append(" , cgcs.cve_entidad_federativa , cgcs.descripcion_entidad_federativa ");
			sb.append(" from canal_venta cv   ");
			sb.append(" inner join canal_venta_cuenta_bancaria cvcb on cvcb.id_canal_venta  = cv.id_canal_venta  ");
			sb.append(" inner join usuario  u on u.id_usuario  = cv.id_usuario_canal_venta  ");
			sb.append(" inner join persona  p on u.id_persona  = p.id_persona  ");
			sb.append(" inner join usuario_rol  ur on ur.id_usuario = u.id_usuario  ");
			sb.append(" inner join rol r on r.id_rol  = ur.id_rol ");
			sb.append(" inner join domicilio  d on cv.id_domicilio  = d.id_domicilio  ");
			sb.append(" inner join cat_general  cgtc on cgtc .id_cat_general  = cv.id_tipo_canal_venta  ");
			sb.append(
					" inner join cat_entidad_federativa cgcs on cgcs.id_entidad_federativa = cv.id_entidad_federativa_sede ");
			sb.append(" left join oficina_canal_venta  ocv on ocv.id_canal_venta  = cv.id_canal_venta  ");
			sb.append(" left join oficina  o on o.id_oficina  = ocv.id_oficina  ");
			sb.append(" where cv.id_canal_venta = ? ");

			List<CanalVentaDto> list = jdbcTemplate.query(sb.toString(), new Object[] { idCanalVenta },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
							CanalVentaDto canalVenta = new CanalVentaDto();

							canalVenta.setIdCanalVenta(rs.getLong("id_canal_venta"));
							canalVenta.setCiudadSede(rs.getString("ciudad_sede"));
							canalVenta.setEmpresaOficina(rs.getString("empresa_oficina"));

							canalVenta.setGeneraCotizacion(rs.getString("genera_cotizacion"));

							canalVenta.setIdUsuarioCanalVenta(rs.getLong("id_usuario_canal_venta"));
							canalVenta.setIndEstatus(rs.getString("ind_estatus"));
							canalVenta.setPagoOficina(rs.getString("pago_oficina"));
							canalVenta.setRazonSocial(rs.getString("razon_social"));

							CatGeneralDto tipoCanalVenta = new CatGeneralDto();
							tipoCanalVenta.setIdCatGeneral(rs.getLong("id_tipo_canal_venta"));
							tipoCanalVenta.setClave(rs.getString("claveTipoCanal"));
							tipoCanalVenta.setDescripcion(rs.getString("descTipoCanal"));
							canalVenta.setTipoCanalVenta(tipoCanalVenta);

							DomicilioDto domicilio = new DomicilioDto();

							domicilio.setCalle(rs.getString("calle"));
							domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
							domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
							domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
							domicilio.setCodigoPostal(rs.getString("codigo_postal"));
							domicilio.setColonia(rs.getString("colonia"));
							domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
							domicilio.setIndEstatus(rs.getLong("ind_estatus"));
							domicilio.setNumeroExterior(rs.getString("numero_exterior"));
							domicilio.setNumeroInterior(rs.getString("numero_interior"));
							canalVenta.setDomicilio(domicilio);

							canalVenta.setEntidadFederativaSede(new CatGeneralDto(
									rs.getLong("id_entidad_federativa_sede"), rs.getString("cve_entidad_federativa"),
									rs.getString("descripcion_entidad_federativa")));

							UsuarioDTO usuarioDTO = new UsuarioDTO();
							usuarioDTO.setIdUsuario(rs.getLong("id_usuario"));
							usuarioDTO.setIdPersona(rs.getLong("id_persona"));
							usuarioDTO.setIdUsuarioRol(rs.getLong("id_usuario_rol"));

							usuarioDTO.setUsuario(rs.getString("usuario"));
							usuarioDTO.setNombre(rs.getString("nombrePersona"));
							usuarioDTO.setPrimerApellido(rs.getString("apellido_paterno"));
							usuarioDTO.setSegundoApellido(rs.getString("apellido_materno"));
							usuarioDTO.setCorreo(rs.getString("correo_electronico"));
							usuarioDTO.setTelefono(rs.getString("telefono"));
							usuarioDTO.setRfc(rs.getString("rfcCanalVenta"));
							usuarioDTO.setIndEstatus(rs.getString("indEstatusUsuario"));
							
							
							RolDTO rol = new RolDTO();
							rol.setIdRol(rs.getLong("id_rol"));
							rol.setNombre(rs.getString("nombreRol"));
							rol.setDescripcion(rs.getString("descRol"));
							usuarioDTO.setRol(rol);

							CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
							cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
							cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
							cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

							cuentaBancariaDto
									.setIdCanalVentaCuentaBancaria(rs.getLong("id_canal_venta_cuenta_bancaria"));
							cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

							canalVenta.setCuentaBancaria(cuentaBancariaDto);
							canalVenta.setUsuario(usuarioDTO);

							OficinaDto oficinaDTO = new OficinaDto();
							oficinaDTO.setIdOficina(rs.getLong("id_oficina"));
							oficinaDTO.setRfc(rs.getString("rfcOficina"));
							oficinaDTO.setRazonSocial(rs.getString("razonSocialOficina"));

							canalVenta.setOficina(oficinaDTO);

							return canalVenta;

						}
					});

			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return null;
		}

		return null;

	}
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OficinaDto> listarTodasOficinas() {

		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT o.id_oficina   , o.clave  , o.descripcion  , o.rfc  , o.razon_social  , o.nombre_contacto  , o.correo_electronico_contacto  , o.telefono_contacto  ");
			sb.append(" , o.id_entidad_federativa_sede  , cgcs.cve_entidad_federativa , cgcs.descripcion_entidad_federativa , o.ciudad_sede  ");
			sb.append(" , cvcb.id_oficina_cuenta_bancaria  , cvcb.id_banco  , cvcb.id_tipo_cuenta  ,cvcb.ind_estatus  , cvcb.numero_cuenta  , cvcb.clabe_interbancaria ");
			sb.append(" , d.codigo_postal  ,d.calle , d.colonia  , d.id_domicilio , d.id_entidad_federativa , d.id_municipio  ,d.id_tipo_domicilio,  d.ind_estatus  ");
			sb.append(" , d.numero_exterior  , d.numero_interior , ocv.id_oficina_canal_venta  , ocv.id_canal_venta  ");
			sb.append(" from oficina  o   ");
			sb.append(" inner join oficina_cuenta_bancaria cvcb on cvcb.id_oficina  = o.id_oficina  ");
			sb.append(" inner join domicilio  d on o.id_domicilio  = d.id_domicilio  ");
			sb.append(" inner join cat_entidad_federativa cgcs on cgcs.id_entidad_federativa = o.id_entidad_federativa_sede  ");
			sb.append(" left join oficina_canal_venta  ocv on ocv.id_oficina = o.id_oficina ");

			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					OficinaDto oficinaDto = new OficinaDto();

					oficinaDto.setIdOficina(rs.getLong("id_oficina"));
					oficinaDto.setClave(rs.getString("clave"));
					oficinaDto.setDescripcion(rs.getString("descripcion"));
					oficinaDto.setRfc(rs.getString("rfc"));
					oficinaDto.setRazonSocial(rs.getString("razon_social"));
					oficinaDto.setNombreContacto(rs.getString("nombre_contacto"));
					oficinaDto.setCorreoElectronicoContacto(rs.getString("correo_electronico_contacto"));
					oficinaDto.setTelefonoContacto(rs.getString("telefono_contacto"));
					oficinaDto.setCiudadSede(rs.getString("ciudad_sede"));

					DomicilioDto domicilio = new DomicilioDto();

					domicilio.setCalle(rs.getString("calle"));
					domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
					domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
					domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
					domicilio.setCodigoPostal(rs.getString("codigo_postal"));
					domicilio.setColonia(rs.getString("colonia"));
					domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
					domicilio.setIndEstatus(rs.getLong("ind_estatus"));
					domicilio.setNumeroExterior(rs.getString("numero_exterior"));
					domicilio.setNumeroInterior(rs.getString("numero_interior"));
					oficinaDto.setDomicilio(domicilio);

					oficinaDto.setEntidadFederativaSede(new CatGeneralDto(rs.getLong("id_entidad_federativa_sede"),
							rs.getString("cve_entidad_federativa"), rs.getString("descripcion_entidad_federativa")));

					
					CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
					cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
					cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
					cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

					cuentaBancariaDto.setIdOficinaCuentaBancaria(rs.getLong("id_oficina_cuenta_bancaria"));
					cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

					oficinaDto.setCuentaBancaria(cuentaBancariaDto);
					
					oficinaDto.setIdCanalVenta(rs.getLong("id_canal_venta"));
					oficinaDto.setIdOficinaCanalVenta(rs.getLong("id_oficina_canal_venta"));
					
					return oficinaDto;

				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}

	}

	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public OficinaDto oficinaByIdOficina(Long idOficina) {
		OficinaDto oficina = null;
		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();

			sb.append(" SELECT o.id_oficina   , o.clave  , o.descripcion  , o.rfc  , o.razon_social  , o.nombre_contacto  , o.correo_electronico_contacto  , o.telefono_contacto , o.ind_estatus as estatusOficina  ");
			sb.append(" , o.id_entidad_federativa_sede  , cgcs.cve_entidad_federativa , cgcs.descripcion_entidad_federativa , o.ciudad_sede  ");
			sb.append(" , cvcb.id_oficina_cuenta_bancaria  , cvcb.id_banco  , cvcb.id_tipo_cuenta  ,cvcb.ind_estatus  , cvcb.numero_cuenta  , cvcb.clabe_interbancaria ");
			sb.append(" , d.codigo_postal  ,d.calle , d.colonia  , d.id_domicilio , d.id_entidad_federativa , d.id_municipio  ,d.id_tipo_domicilio,  d.ind_estatus  ");
			sb.append(" , d.numero_exterior  , d.numero_interior , ocv.id_oficina_canal_venta  , ocv.id_canal_venta  ");
			sb.append(" from oficina  o   ");
			sb.append(" inner join oficina_cuenta_bancaria cvcb on cvcb.id_oficina  = o.id_oficina  ");
			sb.append(" inner join domicilio  d on o.id_domicilio  = d.id_domicilio  ");
			sb.append(" inner join cat_entidad_federativa cgcs on cgcs.id_entidad_federativa = o.id_entidad_federativa_sede  ");
			sb.append(" left join oficina_canal_venta  ocv on ocv.id_oficina = o.id_oficina  ");
			sb.append(" where o.id_oficina = ?  ");
			

			List<OficinaDto> oficinas = jdbcTemplate.query(sb.toString(), new Object[] {idOficina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					OficinaDto oficinaDto = new OficinaDto();

					oficinaDto.setIdOficina(rs.getLong("id_oficina"));
					oficinaDto.setClave(rs.getString("clave"));
					oficinaDto.setDescripcion(rs.getString("descripcion"));
					oficinaDto.setRfc(rs.getString("rfc"));
					oficinaDto.setRazonSocial(rs.getString("razon_social"));
					oficinaDto.setNombreContacto(rs.getString("nombre_contacto"));
					oficinaDto.setCorreoElectronicoContacto(rs.getString("correo_electronico_contacto"));
					oficinaDto.setTelefonoContacto(rs.getString("telefono_contacto"));
					oficinaDto.setCiudadSede(rs.getString("ciudad_sede"));
					oficinaDto.setIndEstatus(rs.getString("estatusOficina"));

					DomicilioDto domicilio = new DomicilioDto();

					domicilio.setCalle(rs.getString("calle"));
					domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
					domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
					domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
					domicilio.setCodigoPostal(rs.getString("codigo_postal"));
					domicilio.setColonia(rs.getString("colonia"));
					domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
					domicilio.setIndEstatus(rs.getLong("ind_estatus"));
					domicilio.setNumeroExterior(rs.getString("numero_exterior"));
					domicilio.setNumeroInterior(rs.getString("numero_interior"));
					oficinaDto.setDomicilio(domicilio);

					oficinaDto.setEntidadFederativaSede(new CatGeneralDto(rs.getLong("id_entidad_federativa_sede"),
							rs.getString("cve_entidad_federativa"), rs.getString("descripcion_entidad_federativa")));

					
					CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
					cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
					cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
					cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

					cuentaBancariaDto.setIdOficinaCuentaBancaria(rs.getLong("id_oficina_cuenta_bancaria"));
					cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

					oficinaDto.setCuentaBancaria(cuentaBancariaDto);
					
					oficinaDto.setIdCanalVenta(rs.getLong("id_canal_venta"));
					oficinaDto.setIdOficinaCanalVenta(rs.getLong("id_oficina_canal_venta"));
					
					return oficinaDto;

				}
			});
			
			
			if (oficinas != null && !oficinas.isEmpty()) {
				return oficinas.get(0);
			}
			 
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return oficina;
		}
		
		return oficina;

	}
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OficinaDto> obtenerOficinas() {
		
		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			sb.append(" select o.id_oficina , o.rfc  , o.razon_social from oficina o where o.ind_estatus = 1");
			
			return  jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					OficinaDto cvo = new OficinaDto();
					cvo.setIdOficina(rs.getLong("id_oficina"));
					cvo.setRfc(rs.getString("rfc"));
					cvo.setRazonSocial(rs.getString("razon_social"));
							
					return cvo;
				}
			});
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}

	}
	
	
	
	@Transactional
	public CanalVentaDto obtenerCanalVentaByIdUsuario(Long idUsuario) {
		try {

			 Session session = sessionFactory.getCurrentSession();
			 Query query = session.createQuery("from CanalVenta where idUsuarioCanalVenta.idUsuario = :idUsuario");
			 query.setParameter("idUsuario", idUsuario);
			 List<CanalVenta> list =  query.list();
			 CanalVentaDto cv= new CanalVentaDto();
			 if(list !=  null && !list.isEmpty()){
				 CanalVenta enty = list.get(0);
				 cv.setIdCanalVenta(enty.getIdCanalVenta());
				 cv.setGeneraCotizacion(enty.getGeneraCotizacion());
				 cv.setTipoCanalVenta(conv().map(enty.getIdTipoCanalVenta(), CatGeneralDto.class));
				 return cv;
			 }
			 return null;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCanalVentaByIdUsuario ", e);
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CanalVentaDto> obtenerPromotores() {
		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();
			sb.append(" select concat(p.nombre, concat(\" \",concat(p.apellido_paterno, concat(\" \",p.apellido_materno)))) as nombreCompleto, cv.id_canal_venta as idCanalVenta from usuario us join canal_venta cv on us.id_usuario = cv.id_usuario_canal_venta and cv.id_tipo_canal_venta = 60 join persona p on p.id_persona = us.id_persona");
			

			List<CanalVentaDto> regresar = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CanalVentaDto cv = new CanalVentaDto();
					cv.setIdCanalVenta(rs.getLong("idCanalVenta"));
					cv.setNombreCompleto(rs.getString("nombreCompleto"));
					return cv;
				}
			});
			return regresar;
			 
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return null;
		}
	}
}
