package mx.com.consolida.ppp.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.entity.ppp.CatEstatusColaborador;
import mx.com.consolida.entity.ppp.CatEstatusNomina;
import mx.com.consolida.entity.ppp.PppColaborador;
import mx.com.consolida.entity.ppp.PppColaboradorClabeInterbancaria;
import mx.com.consolida.entity.ppp.PppColaboradorEstatus;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.ppp.dao.interfaz.CatEstatusColaboradorDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorClabeDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorEstatusDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaEstatusDao;
import mx.com.consolida.ppp.dto.PppColaboradorEstatusDto;
import mx.com.consolida.ppp.service.interfaz.ColaboradorPPPBO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class ColaboradorPPPBOImpl implements ColaboradorPPPBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ColaboradorPPPBOImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private PppColaboradorDao pppColaboradorDao;
	
	@Autowired
	private PppColaboradorClabeDao pppColaboradorClabeDao;
	
	@Autowired
	private PppColaboradorEstatusDao pppColaboradorEstatusDao;
	
	@Autowired
	private	CatEstatusColaboradorDao catEstatusColaboradorDao;
	
	@Autowired
	private PppNominaDao pppNominaDao;
	
	@Autowired
	private PppNominaEstatusDao pppNominaEstatusDao;

	
	@Override
	@Transactional
	public void guardarColaboradores(List<EmpleadoDTO> colaboradores, Long idUsuarioAplicativo) {
		try {
//			Long idPppNomina = obtenerIdPppNominaXIdNominaCliente(colaboradores.get(0).getNominaClienteDto().getIdNominaCliente());
			PppNomina pppNomina = pppNominaDao.read(colaboradores.get(0).getIdNomina());
			
			pppColaboradorDao.eliminarColaboradores(pppNomina.getIdPppNomina());
			
			CatEstatusColaborador estatusColaborador = new CatEstatusColaborador();
			
			for(EmpleadoDTO colaborador: colaboradores) {
			PppColaborador pppColaborador = new PppColaborador();
			PppColaboradorClabeInterbancaria colaboradorClabe = new PppColaboradorClabeInterbancaria();
			PppColaboradorEstatus colaboradorEstatus = new PppColaboradorEstatus();
			
			if(colaborador.getIsEditarColaborador() != null) {			
			if(colaborador.getIsEditarColaborador() == true) {
			estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.EDITAR.getId());
			}else {
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.CARGADO.getId());
			}
			}else {
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.CARGADO.getId());
			}
			
			pppColaborador.setPppNomina(pppNomina);
			pppColaborador.setNombre(colaborador.getNombre().toUpperCase());
			pppColaborador.setApellidoPaterno(colaborador.getApellidoPaterno().toUpperCase());
			if(colaborador.getApellidoMaterno() != null) {
				pppColaborador.setApellidoMaterno(colaborador.getApellidoMaterno().toUpperCase());
			}
			pppColaborador.setMontoPpp(colaborador.getMontoPPP());
			pppColaborador.setRfc(colaborador.getRfc().toUpperCase());
			pppColaborador.setCurp(colaborador.getCurp().toUpperCase());
			
			pppColaborador.setNumeroSeguroSocial(colaborador.getNss());
			if(colaborador.getCorreoElectronico() != null) {
			pppColaborador.setCorreoElectronico(colaborador.getCorreoElectronico());
			}
			pppColaborador.setPuesto(colaborador.getPuesto()!=null?colaborador.getPuesto():null );
			pppColaborador.setNumeroAfiliacion(colaborador.getNumeroAfiliacion()!=null?colaborador.getNumeroAfiliacion():null);
			pppColaborador.setInstitucionContraparte(colaborador.getInstitucionContraparte());
		
			//timbre
		    pppColaborador.setCodigoPostal(colaborador.getCodigoPostal());
			pppColaborador.setDomicilio(colaborador.getDomicilio());
			
			pppColaborador.setFechaAlta(new Date());
			pppColaborador.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			pppColaborador.setUsuarioAlta(new Usuario());
			pppColaborador.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			pppColaboradorDao.createOrUpdate(pppColaborador);
			
			if(colaborador.getClabe().length() == 18) {
			colaboradorClabe.setClabeInterbancaria(colaborador.getClabe());
			}else {
			colaboradorClabe.setNumeroCuenta(colaborador.getClabe());
			}

			colaboradorClabe.setPppColaborador(pppColaborador);
			
			colaboradorClabe.setFechaAlta(new Date());
			colaboradorClabe.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorClabe.setUsuarioAlta(new Usuario());
			colaboradorClabe.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			pppColaboradorClabeDao.createOrUpdate(colaboradorClabe);
			
			colaboradorEstatus.setPppColaborador(pppColaborador);
			colaboradorEstatus.setFechaAlta(new Date());
			colaboradorEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatus.setUsuarioAlta(new Usuario());
			colaboradorEstatus.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			colaboradorEstatus.setCatEstatusColaborador(estatusColaborador);
			
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatus);
			
			
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}
	
	
	@Override
	@Transactional
	public void guardarColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
			
			
			CatEstatusColaborador estatusColaborador = new CatEstatusColaborador();
			
			PppColaborador pppColaborador = new PppColaborador();
			PppColaboradorClabeInterbancaria colaboradorClabe = new PppColaboradorClabeInterbancaria();
			PppColaboradorEstatus colaboradorEstatus = new PppColaboradorEstatus();
			
			pppColaborador = pppColaboradorDao.read(colaborador.getIdPppColaborador());
			estatusColaborador= new CatEstatusColaborador();
			if (colaborador.getIdEstatus()!=null && (colaborador.getIdEstatus()==10 || colaborador.getIdEstatus()==16)) {
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.EDITAR_TIMBRE.getId());
			}else {
			
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.EDITAR.getId());
			}
			
			pppColaborador.setNombre(colaborador.getNombre().toUpperCase());
			pppColaborador.setApellidoPaterno(colaborador.getApellidoPaterno().toUpperCase());
			if(colaborador.getApellidoMaterno() != null) {
				pppColaborador.setApellidoMaterno(colaborador.getApellidoMaterno().toUpperCase());
			}
			pppColaborador.setMontoPpp(colaborador.getMontoPPP());
			pppColaborador.setRfc(colaborador.getRfc().toUpperCase());
			pppColaborador.setCurp(colaborador.getCurp().toUpperCase());
			pppColaborador.setCodigoPostal(colaborador.getCodigoPostal());
			pppColaborador.setDomicilio(colaborador.getDomicilio());
			
			pppColaborador.setNumeroSeguroSocial(colaborador.getNss());
			if(colaborador.getCorreoElectronico() != null) {
			pppColaborador.setCorreoElectronico(colaborador.getCorreoElectronico());
			}
			pppColaborador.setInstitucionContraparte(colaborador.getInstitucionContraparte());
			
			pppColaborador.setFechaModificacion(new Date());
			pppColaborador.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			pppColaborador.setUsuarioModificacion(new Usuario());
			pppColaborador.getUsuarioModificacion().setIdUsuario(idUsuarioAplicativo);
			
			pppColaboradorDao.update(pppColaborador);
			
			colaboradorClabe = pppColaboradorClabeDao.read(colaborador.getIdPppColaboradorClabeInterbancaria());
			if(colaborador.getClabe().length() == 18) {
			colaboradorClabe.setClabeInterbancaria(colaborador.getClabe());
			}else {
			colaboradorClabe.setNumeroCuenta(colaborador.getClabe());
			}

			colaboradorClabe.setFechaModificacion(new Date());
			colaboradorClabe.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorClabe.setUsuarioModificacion(new Usuario());
			colaboradorClabe.getUsuarioModificacion().setIdUsuario(idUsuarioAplicativo);
			
			pppColaboradorClabeDao.update(colaboradorClabe);
			
		
			
			colaboradorEstatus.setPppColaborador(pppColaborador);
			colaboradorEstatus.setFechaAlta(new Date());
			colaboradorEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatus.setUsuarioAlta(new Usuario());
			colaboradorEstatus.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			colaboradorEstatus.setCatEstatusColaborador(estatusColaborador);
			
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatus);
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}
	
	@Override
	@Transactional
	public void bloquearColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
			CatEstatusColaborador estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.BLOQUEADO.getId());
			PppColaborador pppColaborador = pppColaboradorDao.read(colaborador.getIdPppColaborador());
			PppColaboradorEstatus colaboradorEstatus = new PppColaboradorEstatus();
			
			colaboradorEstatus.setPppColaborador(pppColaborador);
			colaboradorEstatus.setFechaAlta(new Date());
			colaboradorEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatus.setUsuarioAlta(new Usuario());
			colaboradorEstatus.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			colaboradorEstatus.setCatEstatusColaborador(estatusColaborador);
			
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatus);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}
	
	
	@Override
	@Transactional
	public void desbloquearColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
			CatEstatusColaborador estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.DESBLOQUEADO.getId());
			
			PppColaborador pppColaborador = pppColaboradorDao.read(colaborador.getIdPppColaborador());
			PppColaboradorEstatus colaboradorEstatus = new PppColaboradorEstatus();
			
			colaboradorEstatus.setPppColaborador(pppColaborador);
			colaboradorEstatus.setFechaAlta(new Date());
			colaboradorEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatus.setUsuarioAlta(new Usuario());
			colaboradorEstatus.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			colaboradorEstatus.setCatEstatusColaborador(estatusColaborador);
			
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatus);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}
	
	@Override
	@Transactional
	public void eliminarColaboradores(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
			PppNomina pppNomina = pppNominaDao.read(colaborador.getIdNomina());
			pppColaboradorDao.eliminarColaboradores(pppNomina.getIdPppNomina());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = true)
	private Long obtenerIdPppNominaXIdNominaCliente(Long idNominaCliente) {
			try {
				
				StringBuilder sb = new StringBuilder();					
				sb.append(" SELECT  id_ppp_nomina "); 
				sb.append(" from sin.ppp_nomina ");
				sb.append(" where id_nomina_cliente=? ");

				
				List<Long> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNominaCliente}, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Long id = rs.getLong("id_ppp_nomina");
						
						return id;	
					}
				});
				
				if (nomina != null && !nomina.isEmpty()) {
					return nomina.get(0);
				} else {
					return idNominaCliente;
				}

				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", e);
			}
			return idNominaCliente;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoDTO> cargaInicialColaboradores(Long idNominaCliente) {
		
		try {
			StringBuilder sb = new StringBuilder();								
			sb.append(" select	pc.id_ppp_colaborador,	pn.id_ppp_nomina,	pc.rfc,	pc.nombre,	pc.apellido_paterno,\r\n" + 
					"	apellido_materno,	pc.monto_ppp, pc.domicilio, pc.codigo_postal, 	pcci.numero_cuenta,	pcci.clabe_interbancaria,\r\n" + 
					"	pc.curp,	pc.numero_seguro_social,	pc.correo_electronico,	pc.puesto, pc.numero_afiliacion, \r\n" + 
					"	pc.institucion_contraparte	 , pcci.id_ppp_colaborador_clabe_interbancaria,\r\n" + 
					"	totales.totalColaboradores, 	totales.totalMontoPPP,\r\n" + 
					"	cec.id_cat_estatus_colaborador,	cec.descripcion ,	ce.observacion,\r\n" + 
					"	(select		max(pcd.id_CMS)\r\n" + 
					"		from		sin.ppp_colaborador_documento pcd\r\n" + 
					"		where 		pcd.id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"			and pcd.id_definicion_documento = 69) as ppp_colaborador_factura ,\r\n" + 
					"	docxml.uuid,\r\n" + 
					"	docxml.id_cms ppp_colaborador_xml_factura ,\r\n" + 
					"  docxml.fecha_alta  fecha_dispersion,\r\n" +
					/*" ( select	MAX(fecha_alta)	 fecha_alta from	ppp_colaborador_stp \n"+
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador) as fecha_dispersion ,  \n" +*/
					"	(select		id_ppp_colaborador_stp	\r\n" + 
					"		from		sin.ppp_colaborador_stp\r\n" + 
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"		and fecha_alta =(select	max(fecha_alta)\r\n" + 
					"								from		ppp_colaborador_stp\r\n" + 
					"								where			id_ppp_colaborador = pc.id_ppp_colaborador)) as id_ppp_colaborador_stp,\r\n" + 
					"	(select		cve_orden_pago	\r\n" + 
					"		from		sin.ppp_colaborador_stp\r\n" + 
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"			and fecha_alta =(select	max(fecha_alta)\r\n" + 
					"								from ppp_colaborador_stp\r\n" + 
					"								where	id_ppp_colaborador = pc.id_ppp_colaborador)) as cve_orden_pago,\r\n" + 
					"	(select id_stp\r\n" + 
					"		from sin.ppp_colaborador_stp\r\n" + 
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"				and fecha_alta =(		select			max(fecha_alta)\r\n" + 
					"		from			ppp_colaborador_stp\r\n" + 
					"		where id_ppp_colaborador = pc.id_ppp_colaborador)) as id_stp,\r\n" + 
					"	(select		descripcion_error_stp\r\n" + 
					"		from	sin.ppp_colaborador_stp\r\n" + 
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"			and fecha_alta =(select	max(fecha_alta)\r\n" + 
					"		from ppp_colaborador_stp\r\n" + 
					"		where	id_ppp_colaborador = pc.id_ppp_colaborador)) as descripcion_error_stp,\r\n" + 
					"	(select		fecha_alta\r\n" + 
					"		from		sin.ppp_colaborador_stp\r\n" + 
					"		where		id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"			and fecha_alta =(select	max(fecha_alta)\r\n" + 
					"								from ppp_colaborador_stp\r\n" + 
					"								where	id_ppp_colaborador = pc.id_ppp_colaborador)) as fecha_operacion\r\n" + 
					"        from\r\n" + 
					"	sin.ppp_nomina pn \r\n" + 
					"	join 	sin.ppp_colaborador pc on pn.id_ppp_nomina = pc.id_ppp_nomina \r\n" + 
					"	join sin.ppp_colaborador_clabe_interbancaria pcci on   pc.id_ppp_colaborador = pcci.id_ppp_colaborador and pcci.ind_estatus =1\r\n" + 
					"	join sin.ppp_colaborador_estatus ce on pc.id_ppp_colaborador = ce.id_ppp_colaborador and ce.ind_estatus =1\r\n" + 
					"	join  sin.cat_estatus_colaborador cec on ce.id_cat_estatus_colaborador = cec.id_cat_estatus_colaborador \r\n" + 
					"	join (	select		pce.id_ppp_colaborador , 	max(pce.id_ppp_colaborador_estatus) as id_ppp_colaborador_estatus\r\n" + 
					"	    		from		sin.ppp_colaborador_estatus pce\r\n" + 
					"					join sin.ppp_colaborador pc on 		pce.id_ppp_colaborador = pc.id_ppp_colaborador\r\n" + 
					"				group by		pce.id_ppp_colaborador) uce  on ce.id_ppp_colaborador_estatus = uce.id_ppp_colaborador_estatus\r\n" + 
					"left join (select pcd.id_ppp_colaborador, pcd.uuid, pcd.id_CMS, max(id_ppp_colaborador_documento) id_ppp_colaborador_documento ,\r\n" + 
					"		max(fecha_hora_certificacion)	fecha_hora_certificacion,  max(pcd.fecha_alta)	fecha_alta\r\n" + 
					"		from ppp_colaborador_documento pcd join ppp_colaborador pc \r\n" + 
					"			on pcd.id_ppp_colaborador = pc.id_ppp_colaborador \r\n" + 
					"		where id_definicion_documento =70 \r\n" + 
					"		group by		pcd.id_ppp_colaborador) docxml on pc.id_ppp_colaborador= docxml.id_ppp_colaborador \r\n" + 
					"	join (select id_ppp_nomina , count(id_ppp_colaborador ) totalColaboradores, sum(monto_ppp) totalMontoPPP\r\n" + 
					"			 from sin.ppp_colaborador group by id_ppp_nomina 		 ) totales on pc.id_ppp_nomina =totales.id_ppp_nomina\r\n" + 
					"where 	pc.id_ppp_nomina =? ");
			

			
			List<EmpleadoDTO> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNominaCliente}, new RowMapper() {
				public EmpleadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					EmpleadoDTO empleado = new EmpleadoDTO();
					empleado.setIdPppColaborador(rs.getLong("id_ppp_colaborador"));
					empleado.setNombre(rs.getString("nombre"));
					empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
					empleado.setApellidoMaterno(rs.getString("apellido_materno"));
					empleado.setRfc(rs.getString("rfc"));
					empleado.setMontoPPP(rs.getBigDecimal("monto_ppp"));
					empleado.setDescError(rs.getString("observacion"));
					empleado.setNumeroCuenta(rs.getBigDecimal("numero_cuenta")!=null?rs.getBigDecimal("numero_cuenta").toString().length()==16? rs.getBigDecimal("numero_cuenta"):null :null );
					empleado.setClabe(rs.getString("clabe_interbancaria")!=null?rs.getString("clabe_interbancaria").length()==18?rs.getString("clabe_interbancaria"):null:null) ;
					empleado.setEstatus(1);
					empleado.setTotalColaboradores(rs.getInt("totalColaboradores"));
					empleado.setTotalMontoPPP(rs.getDouble("totalMontoPPP"));
					empleado.setDescEstatus(rs.getString("descripcion"));
					empleado.setIdEstatus(rs.getLong("id_cat_estatus_colaborador"));
					empleado.setCveOrdenPago(rs.getString("cve_orden_pago"));
					empleado.setIdStp(rs.getInt("id_stp"));
					empleado.setIdPpppColaboradorStp(rs.getLong("id_ppp_colaborador_stp"));
					empleado.setDescripcionErrorStp(rs.getString("descripcion_error_stp"));
					empleado.setCurp(rs.getString("curp"));
					empleado.setFechaDispersion(rs.getTimestamp("fecha_dispersion")!=null?rs.getTimestamp("fecha_dispersion"):null );
					empleado.setFechaOperacion(rs.getTimestamp("fecha_operacion")!=null?rs.getTimestamp("fecha_operacion"):null );
					empleado.setIdCmsExcel(rs.getLong("ppp_colaborador_xml_factura"));
					empleado.setIdCmsPdf(rs.getLong("ppp_colaborador_factura"));
					empleado.setNss(rs.getString("numero_seguro_social"));
					empleado.setCorreoElectronico(rs.getString("correo_electronico"));
					empleado.setInstitucionContraparte(rs.getString("institucion_contraparte"));
					empleado.setIdPppColaboradorClabeInterbancaria(rs.getLong("id_ppp_colaborador_clabe_interbancaria"));
					empleado.setPuesto(rs.getString("puesto"));
					empleado.setUuid(rs.getString("uuid"));
					empleado.setDomicilio(rs.getString("domicilio"));
					empleado.setCodigoPostal(rs.getString("codigo_postal"));
					empleado.setNumeroAfiliacion(rs.getString("numero_afiliacion"));
					return empleado;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
					List<EmpleadoDTO> nominaOrdenadaPorEstatus = new ArrayList<EmpleadoDTO>();
					boolean tieneEstatusError = false;
					for(EmpleadoDTO empleado : nomina) {
						if(empleado.getIdEstatus() == 9) {
							tieneEstatusError = true;
						}
					}
					
					if(tieneEstatusError) {
					for(EmpleadoDTO empleado : nomina) {
						if(empleado.getIdEstatus() == 9) {
							nominaOrdenadaPorEstatus.add(empleado);
						}
					}
					for(EmpleadoDTO empleado : nomina) {
						if(empleado.getIdEstatus() != 9) {
							nominaOrdenadaPorEstatus.add(empleado);
						}
					}
					return nominaOrdenadaPorEstatus;
				}else {
					return nomina;
				}
				
			} else {
				return new ArrayList<EmpleadoDTO>();
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
	return new ArrayList<EmpleadoDTO>();	
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoDTO> cargaInicialColaboradoresNominasPadre(Long idnominaPadre) {
		try {
			StringBuilder sb = new StringBuilder();								
			sb.append(" SELECT pp.id_ppp_colaborador, pp.nombre, pp.apellido_paterno, pp.apellido_materno, pp.curp, pp.rfc, pp.codigo_postal ");
			sb.append(" FROM sin.ppp_colaborador PP   ");
			sb.append(" JOIN ppp_colaborador_estatus CO ON PP.ID_PPP_COLABORADOR=CO.ID_PPP_COLABORADOR ");
			sb.append(" JOIN (SELECT max(id_ppp_colaborador_estatus ) id_ppp_colaborador_estatus,  id_ppp_colaborador  ");
			sb.append(" FROM ppp_colaborador_estatus GROUP BY id_ppp_colaborador ) cue ON co.id_ppp_colaborador_estatus =cue.id_ppp_colaborador_estatus ");
			sb.append(" WHERE PP.id_ppp_nomina IN (?) AND CO.id_cat_estatus_colaborador <> 4 and   CO.id_cat_estatus_colaborador <> 9 and   CO.id_cat_estatus_colaborador <> 13 AND  CO.id_cat_estatus_colaborador <>5");

			List<EmpleadoDTO> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idnominaPadre}, new RowMapper() {
				public EmpleadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					EmpleadoDTO empleado = new EmpleadoDTO();
					empleado.setIdPppColaborador(rs.getLong("id_ppp_colaborador"));
					empleado.setNombre(rs.getString("nombre"));
					empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
					empleado.setApellidoMaterno(rs.getString("apellido_materno"));
					empleado.setCurp(rs.getString("curp"));
					empleado.setRfc(rs.getString("rfc"));	
					
					return empleado;	
				}
			});
			
			
			return nomina;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
	return new ArrayList<EmpleadoDTO>();	
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<EmpleadoDTO> obtenerColaboradoresComplementarias(Long idnominaPadre) {
		try {
			StringBuilder sb = new StringBuilder();								
			sb.append(" SELECT pp.id_ppp_colaborador, pp.nombre, pp.apellido_paterno, pp.apellido_materno, pp.curp, pp.rfc, pp.codigo_postal ");
			sb.append(" FROM sin.ppp_colaborador PP   ");
			sb.append(" INNER JOIN ppp_colaborador_estatus CO ON PP.ID_PPP_COLABORADOR=CO.ID_PPP_COLABORADOR ");
			sb.append(" WHERE PP.id_ppp_nomina IN (SELECT id_ppp_nomina FROM sin.ppp_nomina_complementaria WHERE ID_PPP_NOMINA_PADRE=? and ind_estatus=1) ");
			sb.append("	AND CO.id_cat_estatus_colaborador <> 4 and  CO.id_cat_estatus_colaborador <> 9 ");
			sb.append(" and  CO.fecha_alta=(select max(fecha_alta) from ppp_colaborador_estatus where id_ppp_colaborador=PP.id_ppp_colaborador) ");
					
			List<EmpleadoDTO> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idnominaPadre}, new RowMapper() {
				public EmpleadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					EmpleadoDTO empleado = new EmpleadoDTO();
					empleado.setIdPppColaborador(rs.getLong("id_ppp_colaborador"));
					empleado.setNombre(rs.getString("nombre"));
					empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
					empleado.setApellidoMaterno(rs.getString("apellido_materno"));
					empleado.setCurp(rs.getString("curp"));
					empleado.setRfc(rs.getString("rfc"));							
					return empleado;	
				}
			});
			return nomina;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialColaboradores ", e);
		}
	return new ArrayList<EmpleadoDTO>();	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<PppColaboradorEstatusDto> cargaEstatusColaborador(EmpleadoDTO colaborador) {
		try {
			StringBuilder sb = new StringBuilder();					
			sb.append(" select ce.id_ppp_colaborador_estatus, ce.id_ppp_colaborador, ce.id_cat_estatus_colaborador, ce.fecha_alta, ");
			sb.append(" cec.descripcion, p.nombre, p.apellido_paterno, p.apellido_materno "); 
			sb.append(" from sin.ppp_colaborador_estatus ce, cat_estatus_colaborador cec,  persona p, usuario usu  "); 
			sb.append(" where cec.id_cat_estatus_colaborador = ce.id_cat_estatus_colaborador "); 
			sb.append(" and p.id_persona = usu.id_persona  ");
			sb.append(" and ce.usuario_alta = usu.id_usuario  ");
			sb.append("	and ce.id_ppp_colaborador =").append(colaborador.getIdPppColaborador());
			sb.append(" order by ce.fecha_alta desc");

			
			List<PppColaboradorEstatusDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public PppColaboradorEstatusDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PppColaboradorEstatusDto empleado = new PppColaboradorEstatusDto();
					UsuarioDTO usuario = new UsuarioDTO();
					
					empleado.setCatEstatusColaborador(new CatEstatusDto());
					empleado.setPppColaboradorDto(new EmpleadoDTO());
					
					empleado.getPppColaboradorDto().setIdPppColaborador(rs.getLong("id_ppp_colaborador"));
					empleado.getPppColaboradorDto().setNombre(colaborador.getNombre());
					empleado.getPppColaboradorDto().setRfc(colaborador.getRfc());
					empleado.getPppColaboradorDto().setMontoPPP(colaborador.getMontoPPP());
					empleado.getPppColaboradorDto().setClabe(colaborador.getClabe());
					
					empleado.setIdPppColaboradorEstatus(rs.getLong("id_ppp_colaborador_estatus"));
					empleado.getCatEstatusColaborador().setDescripcionEstatus(rs.getString("descripcion"));
					empleado.setFechaAlta(rs.getDate("fecha_alta"));
					empleado.setFechaAltaFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_alta")));
					
					usuario.setNombre(rs.getString("nombre"));
					usuario.setPrimerApellido(rs.getString("apellido_paterno"));
					usuario.setSegundoApellido(rs.getString("apellido_materno"));
					
					
					empleado.setUsuarioAlta(usuario);
					
					
					return empleado;	
				}
			});
			
			if (nomina != null && !nomina.isEmpty()) {
				return nomina;
			} else {
				return new ArrayList<PppColaboradorEstatusDto>();
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaEstatusColaborador ", e);
		}
		return new ArrayList<PppColaboradorEstatusDto>();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Boolean cambiarEstatusNomina(Long idPppNomina, Long idUsuarioAplicativo, Long idEstatusNomina) {
			try {

				//Consulta los estatus activos y los apaga  , inserta el nuevo estatus
				List<PppNominaEstatus> estatusActivos = pppNominaEstatusDao.getPppNominaEstatusActivo(idPppNomina);
				
				for (PppNominaEstatus pppNominaEstatus : estatusActivos) {
					pppNominaEstatus.setIndEstatus(IndEstatusEnum.INACTIVO.getEstatus());
					pppNominaEstatusDao.update(pppNominaEstatus);
				}
				
				PppNominaEstatus pppNominaEstatus = new PppNominaEstatus();
				pppNominaEstatus.setCatEstatusNomina(new CatEstatusNomina(idEstatusNomina));
				pppNominaEstatus.setPppNomina(new PppNomina(idPppNomina));
				pppNominaEstatus.setFechaAlta(new Date());
				pppNominaEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				pppNominaEstatus.setObservacion(null);
				pppNominaEstatus.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
				
				pppNominaEstatusDao.create(pppNominaEstatus);
				
				return Boolean.TRUE;

			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cambiarEstatusNomina ", e);
				return Boolean.FALSE;
			}
			
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional (readOnly = true)
	public Boolean existenUsuariosSinTimbrar(Long idPppNomina) {
			try {
				
				Long totalColaboradoresNomina = pppColaboradorDao.totalColaboradoresByIdPppNomina(idPppNomina, 1L);
				Long totalColaboradoresTimbrados = pppColaboradorDao.totalColaboradoresByIdPppNomina(idPppNomina,2L);
				
				if(totalColaboradoresNomina != null && totalColaboradoresTimbrados != null) {
					
					if( totalColaboradoresTimbrados < totalColaboradoresNomina) {
						return Boolean.TRUE;
					}
				}
				
				return Boolean.FALSE;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en cambiarEstatusNomina ", e);
				return null;
			}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional (readOnly = true)
	public Boolean existenColaboradodaresTimbrados(Long idPppNomina) {
			try {

				Long totalColaboradoresTimbrados = pppColaboradorDao.totalColaboradoresByIdPppNomina(idPppNomina, 2L);
				
				if(totalColaboradoresTimbrados != null && totalColaboradoresTimbrados > 0) {
					return Boolean.TRUE;
				}
				
				return Boolean.FALSE;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en existenColaboradodaresTimbrados ", e);
				return Boolean.FALSE;
			}
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional (readOnly = true)
	public Boolean existenColaboradodaresPorTimbrar(Long idPppNomina) {
			try {

				Long totalColaboradoresTimbrados = pppColaboradorDao.totalColaboradoresByIdPppNomina(idPppNomina, 3L);
				
				if(totalColaboradoresTimbrados != null && totalColaboradoresTimbrados > 0) {
					return Boolean.TRUE;
				}
				
				return Boolean.FALSE;
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en existenColaboradodaresTimbrados ", e);
				return Boolean.FALSE;
			}
	}
	
	@Override
	@Transactional
	public void guardarColaboradorDirecto(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
//			Long idPppNomina = obtenerIdPppNominaXIdNominaCliente(colaboradores.get(0).getNominaClienteDto().getIdNominaCliente());
			PppNomina pppNomina = pppNominaDao.read(colaborador.getIdNomina());
			
//			pppColaboradorDao.eliminarColaboradores(pppNomina.getIdPppNomina());
			
			CatEstatusColaborador estatusColaborador = new CatEstatusColaborador();
			
			PppColaborador pppColaborador = new PppColaborador();
			PppColaboradorClabeInterbancaria colaboradorClabe = new PppColaboradorClabeInterbancaria();
			PppColaboradorEstatus colaboradorEstatus = new PppColaboradorEstatus();
			
			if(colaborador.getIsEditarColaborador() != null) {			
			if(colaborador.getIsEditarColaborador() == true) {
			estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.EDITAR.getId());
			}else {
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.CARGADO.getId());
			}
			}else {
				estatusColaborador = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.CARGADO.getId());
			}
			
			pppColaborador.setPppNomina(pppNomina);
			pppColaborador.setNombre(colaborador.getNombre().toUpperCase());
			pppColaborador.setApellidoPaterno(colaborador.getApellidoPaterno().toUpperCase());
			if(colaborador.getApellidoMaterno() != null) {
				pppColaborador.setApellidoMaterno(colaborador.getApellidoMaterno().toUpperCase());
			}
			pppColaborador.setMontoPpp(colaborador.getMontoPPP());
			pppColaborador.setCodigoPostal(colaborador.getCodigoPostal());
			pppColaborador.setRfc(colaborador.getRfc().toUpperCase());
			pppColaborador.setCurp(colaborador.getCurp().toUpperCase());
			
			pppColaborador.setNumeroSeguroSocial(colaborador.getNss());
			if(colaborador.getCorreoElectronico() != null) {
			pppColaborador.setCorreoElectronico(colaborador.getCorreoElectronico());
			}
			pppColaborador.setInstitucionContraparte(colaborador.getInstitucionContraparte());
			pppColaborador.setPuesto(colaborador.getPuesto()!=null?colaborador.getPuesto():null);
			pppColaborador.setFechaAlta(new Date());
			pppColaborador.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			pppColaborador.setUsuarioAlta(new Usuario());
			pppColaborador.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			pppColaboradorDao.createOrUpdate(pppColaborador);
			
			if(colaborador.getClabe().length() == 18) {
			colaboradorClabe.setClabeInterbancaria(colaborador.getClabe());
			}else {
			colaboradorClabe.setNumeroCuenta(colaborador.getClabe());
			}

			colaboradorClabe.setPppColaborador(pppColaborador);
			
			colaboradorClabe.setFechaAlta(new Date());
			colaboradorClabe.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorClabe.setUsuarioAlta(new Usuario());
			colaboradorClabe.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			pppColaboradorClabeDao.createOrUpdate(colaboradorClabe);
			
			colaboradorEstatus.setPppColaborador(pppColaborador);
			colaboradorEstatus.setFechaAlta(new Date());
			colaboradorEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatus.setUsuarioAlta(new Usuario());
			colaboradorEstatus.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
			
			colaboradorEstatus.setCatEstatusColaborador(estatusColaborador);
			
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatus);
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}
	
	
@Override
	@Transactional
	public void eliminarColaborador(EmpleadoDTO colaborador, Long idUsuarioAplicativo) {
		try {
			pppColaboradorDao.eliminarColaborador(colaborador.getIdPppColaborador());
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
	}

@SuppressWarnings({ "unchecked", "rawtypes" })
@Override
@Transactional(readOnly = true)
public List<EmpleadoDTO> cargaColaboradoresRespuestaStp(Long idNomina) {
	
	try {
		StringBuilder sb = new StringBuilder();								
		sb.append("select nom.id_ppp_nomina, col.id_ppp_colaborador, stp.id_ppp_colaborador_stp, stp.descripcion_error_stp, ");
		sb.append(" res.id_stp, res.estado, res.causa_devolucion from ppp_nomina nom ");
		sb.append(" inner join ppp_colaborador col on nom.id_ppp_nomina=col.id_ppp_nomina ");
		sb.append(" left join ppp_colaborador_stp stp on col.id_ppp_colaborador=stp.id_ppp_colaborador ");
		sb.append(" left join stp_respuesta res on stp.id_stp= res.id_stp ");
		sb.append(" where nom.id_ppp_nomina=?  ");
		
		List<EmpleadoDTO> nomina = jdbcTemplate.query(sb.toString(), new Object[] {idNomina }, new RowMapper() {
			public EmpleadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				EmpleadoDTO empleado = new EmpleadoDTO();
				empleado.setIdNomina(rs.getLong("id_ppp_nomina"));
				empleado.setIdPppColaborador(rs.getLong("id_ppp_colaborador"));
				empleado.setIdPpppColaboradorStp(rs.getLong("id_ppp_colaborador_stp"));
				empleado.setIdStp(rs.getInt("id_stp"));	
				empleado.setDescError(rs.getString("descripcion_error_stp"));	
				empleado.setEstado(rs.getString("estado"));
				empleado.setCausaDevolucion(rs.getString("causa_devolucion"));
				return empleado;	
			}
		});			
		
		if (nomina != null && !nomina.isEmpty()) {
			return nomina;
		} else {
			return new ArrayList<EmpleadoDTO>();
		}
		
	}catch (Exception e) {
		LOGGER.error("Ocurrio un error en cargaColaboradoresRespuestaStp ", e);
	}
return new ArrayList<EmpleadoDTO>();	
}




}
