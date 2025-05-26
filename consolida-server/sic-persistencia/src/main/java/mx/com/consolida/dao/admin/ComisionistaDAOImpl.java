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
import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.ComisionistaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.entity.CanalVenta;
import mx.com.consolida.entity.Comisionista;
import mx.com.consolida.usuario.PersonaDto;

@Repository
public class ComisionistaDAOImpl extends GenericDAO<Comisionista, Long> implements ComisionistaDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComisionistaDAOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ComisionistaDto> listarAllComisionistas() {

		try {
			StringBuilder sb = new StringBuilder();
            sb.append("SELECT cv.id_comisionista, ");
			sb.append("cv.id_tipo_persona, ct2.clave  as claveTipoPersona , ct2.descripcion  as descTipoPersona, ");
			sb.append(" cv.id_tipo_canal_venta, ct1.clave  as claveTipoCanal , ct1.descripcion  as descTipoCanal, ");
			sb.append(" cv.id_concepto_cfdi, ct3.clave  as claveConceptoCFDI , ct3.descripcion  as descConceptoCFDI, ");
			sb.append(" cv.id_tipo_pago, ct4.clave  as clavetipoPago , ct4.descripcion  as desctipoPago, ");
			sb.append(" cv.id_subtipo_pago, ct5.clave  as clavesubtipoPago , ct5.descripcion  as descsubtipoPago, ");
			sb.append(" cv.razon_social,  cv.rfc , ");
			sb.append(" p.id_persona ,   p.nombre as nombrePersona, p.apellido_paterno , p.apellido_materno , ");   
			sb.append(" cv.id_domicilio,  d.codigo_postal  , d.id_entidad_federativa ,  d.id_municipio, d.id_tipo_domicilio, ");
			sb.append(" p.correo_electronico  , p.telefono , ");
			sb.append(" cvcb.id_comisionista_cuenta_bancaria  ,  cvcb.id_banco  , cvcb.id_tipo_cuenta , ");
			sb.append(" cvcb.clabe_interbancaria  ,  cv.ind_estatus ");
			sb.append(" from comisionistas cv ");  
			sb.append(" inner join comisionista_cuenta_bancaria cvcb on cvcb.id_comisionista  = cv.id_comisionista  ");
			sb.append(" inner join persona  p on cv.id_persona  = p.id_persona  ");
			sb.append(" inner join domicilio  d on cv.id_domicilio  = d.id_domicilio   ");
			sb.append("inner join cat_general  ct1 on ct1.id_cat_general  = cv.id_tipo_canal_venta ");  
			sb.append("inner join cat_general  ct2 on ct2.id_cat_general  = cv.id_tipo_persona   ");
			sb.append("inner join cat_general  ct3 on ct3.id_cat_general  = cv.id_concepto_cfdi ");
			sb.append(" inner join cat_general  ct4 on ct4.id_cat_general  = cv.id_tipo_pago ");
			sb.append("inner join cat_general  ct5 on ct5.id_cat_general  = cv.id_subtipo_pago ");

			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					ComisionistaDto comis = new ComisionistaDto();

					comis.setIdComisionista(rs.getLong("id_comisionista"));
					comis.setIndEstatus(rs.getString("ind_estatus"));
					comis.setRazonSocial(rs.getString("razon_social"));

					CatGeneralDto tipoCanalVenta = new CatGeneralDto();
					tipoCanalVenta.setIdCatGeneral(rs.getLong("id_tipo_canal_venta"));
					tipoCanalVenta.setClave(rs.getString("claveTipoCanal"));
					tipoCanalVenta.setDescripcion(rs.getString("descTipoCanal"));
					comis.setTipoCanalVenta(tipoCanalVenta);
					
					CatGeneralDto tipoPersona = new CatGeneralDto();
					tipoPersona.setIdCatGeneral(rs.getLong("id_tipo_persona"));
					tipoPersona.setClave(rs.getString("claveTipoPersona"));
					tipoPersona.setDescripcion(rs.getString("descTipoPersona"));
					comis.setTipoPersona(tipoPersona);
					
					CatGeneralDto conceptoCFDI = new CatGeneralDto();
					conceptoCFDI.setIdCatGeneral(rs.getLong("id_concepto_cfdi"));
					conceptoCFDI.setClave(rs.getString("claveConceptoCFDI"));
					conceptoCFDI.setDescripcion(rs.getString("descConceptoCFDI"));
					comis.setConcepto_fdi(conceptoCFDI);
					
					CatGeneralDto tipoPago = new CatGeneralDto();
					tipoPago.setIdCatGeneral(rs.getLong("id_tipo_pago"));
					tipoPago.setClave(rs.getString("clavetipoPago"));
					tipoPago.setDescripcion(rs.getString("desctipoPago"));
					comis.setTipo_pago(tipoPago);
					
					CatGeneralDto subtipoPago = new CatGeneralDto();
					subtipoPago.setIdCatGeneral(rs.getLong("id_subtipo_pago"));
					subtipoPago.setClave(rs.getString("clavesubtipoPago"));
					subtipoPago.setDescripcion(rs.getString("descsubtipoPago"));
					comis.setSubtipo_pago(subtipoPago);
			
				
					DomicilioDto domicilio = new DomicilioDto();		
					domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
					domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
					domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
					domicilio.setCodigoPostal(rs.getString("codigo_postal"));
					domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
					comis.setDomicilio(domicilio);

				
					PersonaDto person = new PersonaDto();
					person.setNombre(rs.getString("nombrePersona"));
					person.setApellidoPaterno(rs.getString("apellido_paterno"));
					person.setApellidoMaterno(rs.getString("apellido_materno"));
					person.setCorreo(rs.getString("correo_electronico"));
					person.setTelefono(rs.getString("telefono"));
					person.setRfc(rs.getString("rfc"));
	
					CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
					cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
					cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
					cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

					cuentaBancariaDto.setIdCanalVentaCuentaBancaria(rs.getLong("id_canal_venta_cuenta_bancaria"));
					cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

					comis.setCuentaBancaria(cuentaBancariaDto);
					comis.setPersona(person);


					return comis;

				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodos los comisionistas ", e);
			return Collections.emptyList();
		}

	}

	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ComisionistaDto obtenerComisionistaByIdComisionista(Long idComisionista) {

		try {
			StringBuilder sb = new StringBuilder();
            sb.append("SELECT cv.id_comisionista, ");
			sb.append("cv.id_tipo_persona, ct2.clave  as claveTipoPersona , ct2.descripcion  as descTipoPersona, ");
			sb.append(" cv.id_tipo_canal_venta, ct1.clave  as claveTipoCanal , ct1.descripcion  as descTipoCanal, ");
			sb.append(" cv.id_concepto_cfdi, ct3.clave  as claveConceptoCFDI , ct3.descripcion  as descConceptoCFDI, ");
			sb.append(" cv.id_tipo_pago, ct4.clave  as clavetipoPago , ct4.descripcion  as desctipoPago, ");
			sb.append(" cv.id_subtipo_pago, ct5.clave  as clavesubtipoPago , ct5.descripcion  as descsubtipoPago, ");
			sb.append(" cv.razon_social,  cv.rfc , ");
			sb.append(" p.id_persona ,   p.nombre as nombrePersona, p.apellido_paterno , p.apellido_materno , ");   
			sb.append(" cv.id_domicilio,  d.codigo_postal  , d.id_entidad_federativa ,  d.id_municipio, d.id_tipo_domicilio, ");
			sb.append(" p.correo_electronico  , p.telefono , ");
			sb.append(" cvcb.id_comisionista_cuenta_bancaria  ,  cvcb.id_banco  , cvcb.id_tipo_cuenta , ");
			sb.append(" cvcb.clabe_interbancaria  ,  cv.ind_estatus ");
			sb.append(" from comisionistas cv ");  
			sb.append(" inner join comisionista_cuenta_bancaria cvcb on cvcb.id_comisionista  = cv.id_comisionista  ");
			sb.append(" inner join persona  p on cv.id_persona  = p.id_persona  ");
			sb.append(" inner join domicilio  d on cv.id_domicilio  = d.id_domicilio   ");
			sb.append(" inner join cat_general  ct1 on ct1.id_cat_general  = cv.id_tipo_canal_venta ");  
			sb.append("  inner join cat_general  ct2 on ct2.id_cat_general  = cv.id_tipo_persona   ");
			sb.append(" inner join cat_general  ct3 on ct3.id_cat_general  = cv.id_concepto_cfdi ");
			sb.append(" inner join cat_general  ct4 on ct4.id_cat_general  = cv.id_tipo_pago ");
			sb.append(" inner join cat_general  ct5 on ct5.id_cat_general  = cv.id_subtipo_pago ");
			sb.append(" where cv.id_comisionista = ? ");

			List<ComisionistaDto> list = jdbcTemplate.query(sb.toString(), new Object[] { idComisionista },
					new RowMapper() {
						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
							ComisionistaDto comis = new ComisionistaDto();
							
							comis.setIdComisionista(rs.getLong("id_comisionista"));
							comis.setIndEstatus(rs.getString("ind_estatus"));
							comis.setRazonSocial(rs.getString("razon_social"));

							CatGeneralDto tipoCanalVenta = new CatGeneralDto();
							tipoCanalVenta.setIdCatGeneral(rs.getLong("id_tipo_canal_venta"));
							tipoCanalVenta.setClave(rs.getString("claveTipoCanal"));
							tipoCanalVenta.setDescripcion(rs.getString("descTipoCanal"));
							comis.setTipoCanalVenta(tipoCanalVenta);
							
							CatGeneralDto tipoPersona = new CatGeneralDto();
							tipoPersona.setIdCatGeneral(rs.getLong("id_tipo_persona"));
							tipoPersona.setClave(rs.getString("claveTipoPersona"));
							tipoPersona.setDescripcion(rs.getString("descTipoPersona"));
							comis.setTipoPersona(tipoPersona);
							
							CatGeneralDto conceptoCFDI = new CatGeneralDto();
							conceptoCFDI.setIdCatGeneral(rs.getLong("id_concepto_cfdi"));
							conceptoCFDI.setClave(rs.getString("claveConceptoCFDI"));
							conceptoCFDI.setDescripcion(rs.getString("descConceptoCFDI"));
							comis.setConcepto_fdi(conceptoCFDI);
							
							CatGeneralDto tipoPago = new CatGeneralDto();
							tipoPago.setIdCatGeneral(rs.getLong("id_tipo_pago"));
							tipoPago.setClave(rs.getString("clavetipoPago"));
							tipoPago.setDescripcion(rs.getString("desctipoPago"));
							comis.setTipo_pago(tipoPago);
							
							CatGeneralDto subtipoPago = new CatGeneralDto();
							subtipoPago.setIdCatGeneral(rs.getLong("id_subtipo_pago"));
							subtipoPago.setClave(rs.getString("clavesubtipoPago"));
							subtipoPago.setDescripcion(rs.getString("descsubtipoPago"));
							comis.setSubtipo_pago(subtipoPago);
					
						
							DomicilioDto domicilio = new DomicilioDto();		
							domicilio.setCatEntidadFederativa(new CatGeneralDto(rs.getLong("id_entidad_federativa")));
							domicilio.setCatMunicipios(new CatGeneralDto(rs.getLong("id_municipio")));
							domicilio.setCatTipoDomicilio(new CatGeneralDto(rs.getLong("id_tipo_domicilio")));
							domicilio.setCodigoPostal(rs.getString("codigo_postal"));
							domicilio.setIdDomicilio(rs.getLong("id_domicilio"));
							comis.setDomicilio(domicilio);

						
							PersonaDto person = new PersonaDto();
							person.setNombre(rs.getString("nombrePersona"));
							person.setApellidoPaterno(rs.getString("apellido_paterno"));
							person.setApellidoMaterno(rs.getString("apellido_materno"));
							person.setCorreo(rs.getString("correo_electronico"));
							person.setTelefono(rs.getString("telefono"));
							person.setRfc(rs.getString("rfc"));
			
							CuentaBancariaDto cuentaBancariaDto = new CuentaBancariaDto();
							cuentaBancariaDto.setCatBanco(new CatGeneralDto(rs.getLong("id_banco")));
							cuentaBancariaDto.setCatTipoCuenta(new CatGeneralDto(rs.getLong("id_tipo_cuenta")));
							cuentaBancariaDto.setClabeInterbancaria(rs.getString("clabe_interbancaria"));

							cuentaBancariaDto.setIdCanalVentaCuentaBancaria(rs.getLong("id_canal_venta_cuenta_bancaria"));
							cuentaBancariaDto.setNumeroCuenta(rs.getString("numero_cuenta"));

							comis.setCuentaBancaria(cuentaBancariaDto);
							comis.setPersona(person);


							return comis;


						}
					});

			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarComisionistas ", e);
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
