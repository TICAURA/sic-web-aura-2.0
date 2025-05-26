package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.comunes.dto.CatEstatusDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.ClienteTempEstatusDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.MedioContactoDto;
import mx.com.consolida.dto.TotalesClienteTempDto;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.generico.UsuarioAplicativo;


@Repository
public class ClienteTempDaoImpl extends GenericDAO<ClienteTemp, Long> implements ClienteTempDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteTempDaoImpl.class);
	
	@Transactional
	public List<ClienteTempDto> obtenerClientes(UsuarioAplicativo usuarioAplicativo) {
		boolean banderaFilter = false;
		banderaFilter = usuarioAplicativo.getUsuarioRols().stream().anyMatch(
				rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave())
				);
		try {
			final List<ClienteTempDto> clientes = new ArrayList<ClienteTempDto>();
			StringBuilder sb = new StringBuilder();

			sb.append(" select  cltmp.id_cliente_temp, cltmp.nombre_comercial,  cltmp.razon_social, cltmp.nombre, cltmp.apellido_paterno, cltmp.apellido_materno, cltmp.rfc, " + 
					" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as numeroCotizaciones, " + 
					" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=(select id_cat_general from sin.cat_general where clave='TIP_COT_BAS')) as numeroPreCotizaciones, " + 
					" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=(select id_cat_general from sin.cat_general where clave='TIP_COT_GEN')) as numeroCotizacionesDetalladas, " + 
					" ctp.descripcion as tipoPersona, " + 
					" (select count(*) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as numeroEventos,  " + 
					" (select MAX(fecha_alta) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimoEvento, " + 
					" (select MAX(fecha_alta) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimaCotizacion, " + 
					" ce.id_estatus, ce.descripcion_estatus " + 
					" from sin.cliente_temp cltmp  ");
			if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
			sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona  " + 
					" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
			sb.append(" and cte.ind_estatus = 1 ");
//			sb.append(" and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp)");
			sb.append(" and cte.id_estatus <> 9 " + 
					" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cltmp.ind_estatus = 1  order by cltmp.fecha_alta DESC");
			
			
			@SuppressWarnings("unchecked")
			List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	 List<ClienteTempEstatusDto> estatusCliente =  new ArrayList<ClienteTempEstatusDto>();
		        	 ClienteTempEstatusDto clienteTemEstatusDto =  new ClienteTempEstatusDto();
		        	  ClienteTempDto cliente = new ClienteTempDto();
		        	  cliente.setIdClienteTemp(rs.getLong("id_cliente_temp"));
		        	  cliente.setNombreComercial(rs.getString("nombre_comercial"));
		        	  cliente.setRazonSocial(rs.getString("razon_social"));
		        	  cliente.setNombre(rs.getString("nombre"));
		        	  cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
		        	  cliente.setApellidoMaterno(rs.getString("apellido_materno"));
		        	  cliente.setRfc(rs.getString("rfc"));
		        	  cliente.setNumeroCotizaciones(rs.getInt("numeroCotizaciones"));
		        	  cliente.setNumeroPreCotizaciones(rs.getInt("numeroPreCotizaciones"));
		        	  cliente.setNumeroCotizacionesDetalladas(rs.getInt("numeroCotizacionesDetalladas"));
		        	  cliente.setIdTipoPersona(new CatGeneralDto(rs.getString("tipoPersona")));
		        	  cliente.setNumeroEventos(rs.getInt("numeroEventos"));
		        	  cliente.setFechaUltimoEvento(rs.getDate("fechaUltimoEvento"));
		        	  cliente.setFechaUltimaCotizacion(rs.getDate("fechaUltimaCotizacion"));
		        	  
		        	  clienteTemEstatusDto.setIdEstatus(new CatEstatusDto());
		        	  clienteTemEstatusDto.getIdEstatus().setIdEstatus(rs.getLong("id_estatus"));
		        	  clienteTemEstatusDto.getIdEstatus().setDescripcionEstatus(rs.getString("descripcion_estatus"));
		        	  estatusCliente.add(clienteTemEstatusDto);
		        	  
		        	  cliente.setEstatusCliente(estatusCliente);
		        	  clientes.add(cliente); 
		          LOGGER.debug("Datos Menu--> ");
		          return clientes;
			   }
			   });
			   
			   
			   return clientes;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerClientes ", e);
			return Collections.emptyList();
		}
		
		

	}
	
	@Transactional
	public ClienteTempDto obtenerClienteById(Long idClienteTemp) {
		ClienteTempDto clienteDto = new ClienteTempDto();
		clienteDto = conv().map(read(idClienteTemp), ClienteTempDto.class);
		return clienteDto != null ? clienteDto : new ClienteTempDto();
	}
	
	@Transactional
	public List<CotizacionDto> obtenerCotizaciones(Long idClienteTemp,Long idTipoSolCotizacion){
		final List<CotizacionDto> list = new ArrayList<CotizacionDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select c.id_cotizacion, c.porcentaje_nom_fis,c.fecha_alta as fechaAltaCotizacion, c.porcentaje_ppp, ctp.descripcion_tipo_pago, ce.id_estatus ,ce.descripcion_estatus, " + 
				" (select count(*) from sin.cotizacion cot, sin.colaboradores_temp ct " + 
				" where ct.id_cotizacion = cot.id_cotizacion and cot.id_cotizacion = c.id_cotizacion and ct.ind_estatus= 1) as totalColaboradores, c.id_tipo_sol_cotizacion as tipoCotizacion " + 
				" from sin.cotizacion c " + 
				" left join sin.cat_estatus ce on ce.id_estatus = c.id_estatus " + 
				" left join sin.cat_tipo_pago ctp on c.id_periodicidad = ctp.id_tipo_pago where c.id_cliente_temp =" + idClienteTemp )
		.append(" and id_Tipo_Sol_Cotizacion = ").append(idTipoSolCotizacion);
		@SuppressWarnings("unchecked")
		List<CotizacionDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public List<CotizacionDto> mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  CotizacionDto objDto = new CotizacionDto();
	        	  objDto.setIdCotizacion(rs.getLong("id_cotizacion"));
//	        	  objDto.setPorcentajePpp(rs.getInt("porcentaje_ppp"));
//	        	  objDto.setPorcentajeNomFis(rs.getInt("porcentaje_nom_fis"));
	        	  objDto.setIdPeriodicidad(new CatTipoPagoDto(rs.getString(("descripcion_tipo_pago"))));
	        	  objDto.setFechaAlta(rs.getDate("fechaAltaCotizacion"));
	        	  objDto.setIdTipoSolCotizacion(new CatGeneralDto());
	        	  objDto.getIdTipoSolCotizacion().setIdCatGeneral(rs.getLong("tipoCotizacion"));
	        	  
	        	  if(objDto.getIdTipoSolCotizacion() != null && objDto.getIdTipoSolCotizacion().getIdCatGeneral() != null && 
	        			  objDto.getIdTipoSolCotizacion().getIdCatGeneral().equals(44)) {
	        		  objDto.getIdTipoSolCotizacion().setDescripcion("COTIZACION BASICA");
	        	  }else if(objDto.getIdTipoSolCotizacion() != null && objDto.getIdTipoSolCotizacion().getIdCatGeneral() != null && 
	        			  objDto.getIdTipoSolCotizacion().getIdCatGeneral().equals(45)) {
	        		  objDto.getIdTipoSolCotizacion().setDescripcion("COTIZACION DETALLADA");
	        	  }
	        	  
	        	  CatGeneralDto estatus = new CatGeneralDto();
	        	  
	        	  objDto.setIdEstatus(estatus);
	        	  objDto.getIdEstatus().setDescripcion(rs.getString("descripcion_estatus"));
	        	  objDto.getIdEstatus().setIdCatGeneral(rs.getLong("id_estatus"));
	        	  objDto.setTotalColaboradores(rs.getInt("totalColaboradores"));
	        	  list.add(objDto); 
	          LOGGER.debug("Datos Menu--> ");
	          return list;
		   }
		   });
		return list;
	}

	@Override
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Transactional
	public List<ClienteTempDto> obtenerBitacoraSolicitudesCotizacion() {
		final List<ClienteTempDto> list = new ArrayList<ClienteTempDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select ct.id_cliente_temp, ct.nombre_comercial, ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social,  ctbs.id_cliente_temp, "); 
		sb.append(" ce.id_estatus, ce2.descripcion_estatus,"); 
		sb.append(" ctbs.id_cliente_temp_bitacora_solicitudes, ctbs.observacion, ctbs.fecha_alta,  ce.id_estatus, ce.descripcion_estatus, ctbs.nombre_archivo "); 
		sb.append(" from sin.cliente_temp_bitacora_solicitudes ctbs "); 
		sb.append(" join sin.cat_estatus ce on ce.id_estatus = ctbs.id_estatus and ctbs.id_estatus in (3,4) "); 
		sb.append(" join sin.cliente_temp ct on ct.id_cliente_temp = ctbs.id_cliente_temp "); 
		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =ct.id_cliente_temp ");
		sb.append("and cte.ind_estatus = 1");
//		sb.append(" and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = ct.id_cliente_temp) "); 
		sb.append(" join sin.cat_estatus ce2 on ce2.id_estatus = cte.id_estatus and cte.id_estatus in (3,4) "); 
		sb.append(" where ctbs.ind_estatus = 1  order by ctbs.fecha_alta desc");
		
		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  ClienteTempDto dto = new ClienteTempDto();
	        	  ClienteTempBitacoraSolicitudesDto solicitudes =  new ClienteTempBitacoraSolicitudesDto();
	        	  CotizacionDto cotizacion = new CotizacionDto();
	        	  
	        	  dto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  dto.setNombreComercial(rs.getString("nombre_comercial"));
	        	  dto.setNombre(rs.getString("nombre"));
	        	  dto.setApellidoPaterno(rs.getString("apellido_paterno"));
	        	  dto.setApellidoMaterno(rs.getString("apellido_materno"));
	        	  dto.setRazonSocial(rs.getString("razon_social"));
	        	  
	        	  solicitudes.setIdClienteTempBitacoraSolicitudes(rs.getLong("id_cliente_temp_bitacora_solicitudes"));
	        	  solicitudes.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  solicitudes.setObservacion(rs.getString("observacion"));
	        	  solicitudes.setFechaAlta(rs.getDate("fecha_alta"));
	        	  solicitudes.setNombreArchivo(rs.getString("nombre_archivo"));
	        	  
	        	  CatEstatusDto catEstatusDto = new CatEstatusDto();
	        	  catEstatusDto.setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  solicitudes.setCatEstatus(catEstatusDto);
	        	  
	        	  dto.setClienteTempBitacoraSolicitudesDto(solicitudes);
	        	  
	        	  list.add(dto); 
	          LOGGER.debug("Datos Menu--> ");
			return dto;
	         
		   }
		   });
		return list;
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Transactional
	public List<ClienteTempDto> obtenerBitacoraCotizacion(Long idTipoSolCotizacion) {
		final List<ClienteTempDto> list = new ArrayList<ClienteTempDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct ct.id_cliente_temp, ct.nombre_comercial, ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social, ");
		sb.append(" ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social, c.id_cotizacion, c.cve_cotizacion, c.fecha_alta as cotizacionFechaAlta, ");
		sb.append(" c.id_periodicidad, ctp.descripcion_tipo_pago, cec.descripcion_estatus, c.porcentaje_nom_fis, c.porcentaje_ppp, ");
		sb.append(" (select count(*) from sin.colaboradores_temp colt where colt.id_cotizacion= c.id_cotizacion and ind_estatus=1) as totalColaboradores, c.observacion_autorizador, c.id_tipo_sol_cotizacion as tipoCotizacion  ");
		sb.append(" from sin.cliente_temp ct "); 
		sb.append(" join sin.cotizacion c on c.id_cliente_temp = ct.id_cliente_temp ");
		sb.append(" left join sin.cotizacion_estatus cte on cte.id_cotizacion = c.id_cotizacion ");
		sb.append(" left join sin.cat_estatus cec on  cec.id_estatus = c.id_estatus ");
		sb.append(" left join sin.cat_tipo_pago ctp on ctp.id_tipo_pago = c.id_periodicidad ");
		sb.append(" where c.id_estatus = 11");
		if(idTipoSolCotizacion!=null) {
			sb.append(" and c.id_tipo_sol_cotizacion = " + idTipoSolCotizacion);
		}
		sb.append(" order by c.fecha_alta desc ");
		
		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  ClienteTempDto dto = new ClienteTempDto();
	        	  CotizacionDto cotizacion = new CotizacionDto();
	        	  
	        	  dto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  dto.setNombreComercial(rs.getString("nombre_comercial"));
	        	  dto.setNombre(rs.getString("nombre"));
	        	  dto.setApellidoPaterno(rs.getString("apellido_paterno"));
	        	  dto.setApellidoMaterno(rs.getString("apellido_materno"));
	        	  dto.setRazonSocial(rs.getString("razon_social"));
	        	  
	        	  
	        	  cotizacion.setIdCotizacion(rs.getLong("id_cotizacion"));
	        	  if(cotizacion.getIdCotizacion() == null || cotizacion.getIdCotizacion() == 0) {
	        	  dto.setCotizacionDto(null);
	        	  }else {
	        		  cotizacion.setFechaAlta(rs.getDate("cotizacionFechaAlta"));
	        		  cotizacion.setPorcentajePpp(rs.getInt("porcentaje_ppp"));
	        	  cotizacion.setPorcentajeNomFis(rs.getInt("porcentaje_nom_fis"));
	        	  cotizacion.setIdPeriodicidad(new CatTipoPagoDto(rs.getString(("descripcion_tipo_pago"))));
	        	  cotizacion.setTotalColaboradores(rs.getInt("totalColaboradores"));
	        	  cotizacion.setObservacionAutorizador(rs.getString("observacion_autorizador"));
	        	  cotizacion.setCveCotizacion(rs.getString("cve_cotizacion"));
	        	
	        	  CatEstatusDto catEstatusDtoCotizacion = new CatEstatusDto();
	        	  catEstatusDtoCotizacion.setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  cotizacion.setCatEstatusCotizacion(catEstatusDtoCotizacion);
	        	  cotizacion.setIdTipoSolCotizacion(new CatGeneralDto());
	        	  cotizacion.getIdTipoSolCotizacion().setIdCatGeneral(rs.getLong("tipoCotizacion"));
	        	  if(cotizacion.getIdTipoSolCotizacion() != null && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral() != null
	        			  && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral().equals(44)) {
	        		  cotizacion.getIdTipoSolCotizacion().setDescripcion("COTIZACION BASICA");
	        	  }else if(cotizacion.getIdTipoSolCotizacion() != null && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral() != null
	        			  && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral().equals(45)) {
	        		  cotizacion.getIdTipoSolCotizacion().setDescripcion("COTIZACION GENERAL");
	        	  }
	        	  dto.setCotizacionDto(cotizacion);
	        	  }
	        	  
	        	  list.add(dto); 
	          LOGGER.debug("Datos Menu--> ");
			return dto;
	         
		   }
		   });
		return list;
	}
	
	
	@Override
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Transactional
	public List<ClienteTempDto> obtenerBitacoraCotizacionByIdEstatus(Long idEstatus) {
		final List<ClienteTempDto> list = new ArrayList<ClienteTempDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select distinct ct.id_cliente_temp, ct.nombre_comercial, ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social, ");
		sb.append(" ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social, c.id_cotizacion,c.fecha_alta as cotizacionFechaAlta, ");
		sb.append(" c.id_periodicidad, ctp.descripcion_tipo_pago, cec.descripcion_estatus, c.porcentaje_nom_fis, c.porcentaje_ppp, ");
		sb.append(" (select count(*) from sin.colaboradores_temp colt where colt.id_cotizacion= c.id_cotizacion and ind_estatus=1) as totalColaboradores, c.observacion_autorizador, c.id_tipo_sol_cotizacion as tipoCotizacion  ");
		sb.append(" from sin.cliente_temp ct "); 
		sb.append(" join sin.cotizacion c on c.id_cliente_temp = ct.id_cliente_temp ");
		sb.append(" left join sin.cotizacion_estatus cte on cte.id_cotizacion = c.id_cotizacion ");
		sb.append(" left join sin.cat_estatus cec on  cec.id_estatus = c.id_estatus ");
		sb.append(" left join sin.cat_tipo_pago ctp on ctp.id_tipo_pago = c.id_periodicidad ");
		sb.append(" where c.id_estatus =").append(idEstatus).append(" order by c.fecha_alta desc ");
		
		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  ClienteTempDto dto = new ClienteTempDto();
	        	  CotizacionDto cotizacion = new CotizacionDto();
	        	  
	        	  dto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  dto.setNombreComercial(rs.getString("nombre_comercial"));
	        	  dto.setNombre(rs.getString("nombre"));
	        	  dto.setApellidoPaterno(rs.getString("apellido_paterno"));
	        	  dto.setApellidoMaterno(rs.getString("apellido_materno"));
	        	  dto.setRazonSocial(rs.getString("razon_social"));
	        	  
	        	  
	        	  cotizacion.setIdCotizacion(rs.getLong("id_cotizacion"));
	        	  if(cotizacion.getIdCotizacion() == null || cotizacion.getIdCotizacion() == 0) {
	        	  dto.setCotizacionDto(null);
	        	  }else {
	        		  cotizacion.setFechaAlta(rs.getDate("cotizacionFechaAlta"));
	        		  cotizacion.setPorcentajePpp(rs.getInt("porcentaje_ppp"));
	        	  cotizacion.setPorcentajeNomFis(rs.getInt("porcentaje_nom_fis"));
	        	  cotizacion.setIdPeriodicidad(new CatTipoPagoDto(rs.getString(("descripcion_tipo_pago"))));
	        	  cotizacion.setTotalColaboradores(rs.getInt("totalColaboradores"));
	        	  cotizacion.setObservacionAutorizador(rs.getString("observacion_autorizador"));
	        	
	        	  CatEstatusDto catEstatusDtoCotizacion = new CatEstatusDto();
	        	  catEstatusDtoCotizacion.setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  cotizacion.setCatEstatusCotizacion(catEstatusDtoCotizacion);
	        	  cotizacion.setIdTipoSolCotizacion(new CatGeneralDto());
	        	  cotizacion.getIdTipoSolCotizacion().setIdCatGeneral(rs.getLong("tipoCotizacion"));
	        	  if(cotizacion.getIdTipoSolCotizacion() != null && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral() != null
	        			  && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral().equals(44)) {
	        		  cotizacion.getIdTipoSolCotizacion().setDescripcion("COTIZACION BASICA");
	        	  }else if(cotizacion.getIdTipoSolCotizacion() != null && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral() != null
	        			  && cotizacion.getIdTipoSolCotizacion().getIdCatGeneral().equals(45)) {
	        		  cotizacion.getIdTipoSolCotizacion().setDescripcion("COTIZACION GENERAL");
	        	  }
	        	  dto.setCotizacionDto(cotizacion);
	        	  }
	        	  
	        	  list.add(dto); 
	          LOGGER.debug("Datos Menu--> ");
			return dto;
	         
		   }
		   });
		return list;
	}


	@Override
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Transactional
	public List<ClienteTempDto> obtenerBitacoraSolicitudesAutorizador() {
		final List<ClienteTempDto> list = new ArrayList<ClienteTempDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select cl.*, ctp1.descripcion as tipo_producto, c.id_cotizacion, catPago.descripcion_tipo_pago,(select count(*) from cotizacion_colaborador where id_cotizacion = c.id_cotizacion and ind_estatus = 1) as totalColaboradores, \r\n"
				+ "(select observacion from cliente_temp_bitacora_solicitudes where id_cotizacion = c.id_cotizacion) as observacionSolCotizacion \r\n"
				+ " from cotizacion c join \r\n" + 
				" (select cltmp.id_cliente_temp, cltmp.nombre, cltmp.apellido_paterno, cltmp.apellido_materno, cltmp.razon_social,ce.descripcion_estatus\r\n" + 
				" from sin.cliente_temp cltmp\r\n" + 
				" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona\r\n" + 
				" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp\r\n");
//		sb.append(" and cte.id_cliente_temp_estatus = (select max(clte.id_cliente_temp_estatus) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) \r\n" );
		sb.append("and cte.ind_estatus = 1");
		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where\r\n" + 
				" cte.id_estatus in (2,3,4,5,6) \r\n" + 
				" and cltmp.ind_estatus = 1 order by cltmp.fecha_alta desc) cl\r\n" + 
				" on cl.id_cliente_temp = c.id_cliente_temp\r\n" + 
				" and c.id_estatus = 4\r\n" + 
				" join cat_tipo_pago catPago on catPago.id_tipo_pago = c.id_periodicidad \r\n")
		.append("join sin.cat_general ctp1 on ctp1.id_cat_general = c.id_imss");
		/*sb.append(" select distinct ct.nombre_comercial, ct.nombre, ct.apellido_paterno, ct.apellido_materno, ct.razon_social, "); 
		sb.append(" ctbs.id_cliente_temp_bitacora_solicitudes,  ctbs.id_cliente_temp, ctbs.observacion, ctbs.fecha_alta, "); 
		sb.append(" ce.descripcion_estatus,  ct.nombre, ct.apellido_paterno, ct.apellido_materno,  ct.razon_social, c.id_cotizacion, "); 
		sb.append(" c.id_periodicidad, ctp.descripcion_tipo_pago, cec.descripcion_estatus, c.porcentaje_nom_fis, porcentaje_ppp, "); 
		sb.append(" (select count(*) from sin.colaboradores_temp colt where colt.id_cotizacion= c.id_cotizacion and ind_estatus=1) as totalColaboradores "); 
		sb.append(" from sin.cliente_temp_bitacora_solicitudes ctbs "); 
		sb.append(" left join sin.cat_estatus ce on ce.id_estatus = ctbs.id_estatus "); 
		sb.append(" left join sin.cliente_temp ct on ct.id_cliente_temp = ctbs.id_cliente_temp "); 
		sb.append(" left join sin.cotizacion c on c.id_cliente_temp = ct.id_cliente_temp and c.id_estatus=4  and c.ind_estatus=1 "); 
		sb.append(" left join sin.cat_estatus cec on  cec.id_estatus = c.id_estatus "); 
		sb.append(" left join sin.cat_tipo_pago ctp on ctp.id_tipo_pago = c.id_periodicidad "); 
		sb.append(" where ctbs.fecha_alta = (select max(ctbs2.fecha_alta) from cliente_temp_bitacora_solicitudes ctbs2 where ctbs2.id_cliente_temp = ct.id_cliente_temp)  "); 
		sb.append(" and ce.id_estatus = cec.id_estatus order by ctbs.fecha_alta desc ");*/
		
		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempDto mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  ClienteTempDto dto = new ClienteTempDto();
	        	  ClienteTempBitacoraSolicitudesDto solicitudes =  new ClienteTempBitacoraSolicitudesDto();
	        	  CotizacionDto cotizacion = new CotizacionDto();
	        	  
	        	  dto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
//	        	  dto.setNombreComercial(rs.getString("nombre_comercial"));
	        	  dto.setNombre(rs.getString("nombre"));
	        	  dto.setApellidoPaterno(rs.getString("apellido_paterno"));
	        	  dto.setApellidoMaterno(rs.getString("apellido_materno"));
	        	  dto.setRazonSocial(rs.getString("razon_social"));
	        	  
//	        	  solicitudes.setIdClienteTempBitacoraSolicitudes(rs.getLong("id_cliente_temp_bitacora_solicitudes"));
//	        	  solicitudes.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  solicitudes.setObservacion(rs.getString("observacionSolCotizacion"));
//	        	  solicitudes.setFechaAlta(rs.getDate("fecha_alta"));
	        	  
	        	  CatEstatusDto catEstatusDto = new CatEstatusDto();
	        	  catEstatusDto.setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  solicitudes.setCatEstatus(catEstatusDto);
	        	  
	        	  dto.setClienteTempBitacoraSolicitudesDto(solicitudes);
	        	  
	        	  cotizacion.setIdCotizacion(rs.getLong("id_cotizacion"));
//	        	  cotizacion.setPorcentajePpp(rs.getInt("porcentaje_ppp"));
//	        	  cotizacion.setPorcentajeNomFis(rs.getInt("porcentaje_nom_fis"));
	        	  cotizacion.setIdPeriodicidad(new CatTipoPagoDto(rs.getString(("descripcion_tipo_pago"))));
	        	  cotizacion.setIdImss(new CatGeneralDto(rs.getString("tipo_producto")));
	        	  cotizacion.setTotalColaboradores(rs.getInt("totalColaboradores"));
	        	  
	        	  CatEstatusDto catEstatusDtoCotizacion = new CatEstatusDto();
	        	  catEstatusDtoCotizacion.setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  solicitudes.setCatEstatus(catEstatusDtoCotizacion);
	        	  cotizacion.setCatEstatusCotizacion(catEstatusDtoCotizacion);
	        	  
	        	  dto.setCotizacionDto(cotizacion);
	        	  
	        	  list.add(dto); 
	          LOGGER.debug("Datos Menu--> ");
			return dto;
	         
		   }
		   });
		return list;
	}

	@Override
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Transactional
	public String obtenerNombreEstadoXCveEstado(String estado) {
		final List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		
		sb.append("select descripcion_entidad_federativa from sin.cat_entidad_federativa where id_entidad_federativa='").append(estado).append("'");
		List<String> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public String mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  String dto = null;
	        	  dto = rs.getString("descripcion_entidad_federativa");
	        	  list.add(dto);
	        	  
	        	  return dto;
	          }
		});  
		return list.get(0);
	}

    @Override
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
    @Transactional
	public String obtenerNombreAlcaldiaXIdMunicipio(Long alcaldia) {
    	final List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select  descripcion  from sin.cat_municipios where id_cat_municipios=").append(alcaldia);
		List<String> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public String mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  String dto = null;
	        	  dto = rs.getString("descripcion");
	        	  list.add(dto);
	        	  
	        	  return dto;
	          }
		});  
		return list.get(0);
	}
    
    @Override
    @SuppressWarnings({ "unchecked", "unused", "rawtypes" })
    @Transactional
	public String obtenerNombreGiroComercialXId(Long idGiroComercial) {
    	final List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		sb.append("select descripcion from sin.cat_general where id_cat_general=").append(idGiroComercial);
		List<String> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public String mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  String dto = null;
	        	  dto = rs.getString("descripcion");
	        	  list.add(dto);
	        	  
	        	  return dto;
	          }
		});  
		return list.get(0);
	}
    
    @Override
    @Transactional
    public List<ClienteTempDto> obtenerContadorSeguimientoXEstatus(Long idEstatus,UsuarioAplicativo usuarioAplicativo){
    	boolean banderaFilter = false;
		banderaFilter = usuarioAplicativo.getUsuarioRols().stream().anyMatch(rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave()));
		
    	try {
    		final List<ClienteTempDto> clientes = new ArrayList<ClienteTempDto>();
    		StringBuilder sb = new StringBuilder();
    		sb.append(" select distinct cltmp.id_cliente_temp, ");
    		sb.append("cltmp.razon_social, cltmp.nombre, cltmp.apellido_paterno, cltmp.apellido_materno, cltmp.nombre_comercial, cltmp.rfc, ");
    		
    		if(idEstatus == 23456L){
    			sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_estatus = 11  and id_tipo_sol_cotizacion = 45 ) as numeroCotizacionesEnProceso, ");
    			sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_estatus in (4) and id_tipo_sol_cotizacion = 45 ) as numeroCotizacionesEnAutorizacion, ");
    			sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_estatus = 6  and id_tipo_sol_cotizacion = 45 ) as numeroCotizacionesAutorizacion, ");
    			sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_estatus = 5  and id_tipo_sol_cotizacion = 45 ) as numeroCotizacionesRechazadas, ");
    		}
    		
    		sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as numeroCotizaciones, ");
    		sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=44) as numeroPreCotizaciones, ");
    		sb.append("(select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=45) as numeroCotizacionesDetalladas, ");
    		sb.append("ctp.descripcion as tipoPersona, ");
    		sb.append(" (select count(*) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as numeroEventos, ");
    		sb.append(" (select MAX(fecha_alta) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimoEvento, ");
    		sb.append(" (select MAX(fecha_alta) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimaCotizacion, "); 
    		sb.append(" ce.descripcion_estatus ");
    		sb.append(" from sin.cliente_temp cltmp ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona ");
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append("and cte.ind_estatus = 1");
    		//sb.append(" and cte.id_cliente_temp = (select max(clte.id_cliente_temp) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) ");
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus where ");
    		if(idEstatus == 23456L){
    			sb.append(" cte.id_estatus in (2,3,4,5,6) ").append(" and (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) != 0 ");
    		}else if(idEstatus==2L) {
    			sb.append(" cte.id_estatus = ").append(idEstatus).append(" and (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) = 0");
    		}else if(idEstatus == 3L) {
    			sb.append(" cte.id_estatus in (2,3) ").append(" and (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) != 0 ");
    		}else {
    			sb.append(" cte.id_estatus = ").append(idEstatus);
    		}
    	
    		
    		sb.append(" and cltmp.ind_estatus = 1 order by cltmp.fecha_alta desc ");
    		
    		
    		@SuppressWarnings("unchecked")
    		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        	 List<ClienteTempEstatusDto> estatusCliente =  new ArrayList<ClienteTempEstatusDto>();
    	        	 ClienteTempEstatusDto clienteTemEstatusDto =  new ClienteTempEstatusDto();
    	        	  ClienteTempDto cliente = new ClienteTempDto();
    	        	  cliente.setNombreComercial(rs.getString("nombre_comercial"));
    	        	  cliente.setIdClienteTemp(rs.getLong("id_cliente_temp"));
    	        	  cliente.setRazonSocial(rs.getString("razon_social"));
    	        	  cliente.setNombre(rs.getString("nombre"));
    	        	  cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
    	        	  cliente.setApellidoMaterno(rs.getString("apellido_materno"));
    	        	  cliente.setRfc(rs.getString("rfc"));
    	        	  cliente.setNumeroCotizaciones(rs.getInt("numeroCotizaciones"));
    	        	  cliente.setNumeroPreCotizaciones(rs.getInt("numeroPreCotizaciones"));
//    	        	  cliente.setNumeroCotizacionesEstatus(rs.getInt("numeroCotizacionesEstatus"));
    	        	  cliente.setNumeroCotizacionesDetalladas(rs.getInt("numeroCotizacionesDetalladas"));
    	        	  if(idEstatus == 23456L) {
    	        		  cliente.setNumeroCotizacionesEnProceso(rs.getInt("numeroCotizacionesEnProceso"));
        	        	  cliente.setNumeroCotizacionesEnAutorizacion(rs.getInt("numeroCotizacionesEnAutorizacion"));
        	        	  cliente.setNumeroCotizacionesAutorizacion(rs.getInt("numeroCotizacionesAutorizacion"));
        	        	  cliente.setNumeroCotizacionesRechazadas(rs.getInt("numeroCotizacionesRechazadas"));
    	        	  }
    	        	  
    	        	  
    	        	  cliente.setIdTipoPersona(new CatGeneralDto(rs.getString("tipoPersona")));
    	        	  cliente.setNumeroEventos(rs.getInt("numeroEventos"));
    	        	  cliente.setFechaUltimoEvento(rs.getDate("fechaUltimoEvento"));
    	        	  cliente.setFechaUltimaCotizacion(rs.getDate("fechaUltimaCotizacion"));
    	        	  
    	        	  clienteTemEstatusDto.setIdEstatus(new CatEstatusDto());
    	        	  clienteTemEstatusDto.getIdEstatus().setDescripcionEstatus(rs.getString("descripcion_estatus"));
    	        	  estatusCliente.add(clienteTemEstatusDto);
    	        	  
    	        	  cliente.setEstatusCliente(estatusCliente);
    	        	  clientes.add(cliente); 
    	          LOGGER.debug("obtenerContadorSeguimientoXEstatus--> "+sb);
    	          return clientes;
    		   }
    		   });
    		   
    		   
    		   return clientes;
    	}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerContadorSeguimientoXEstatus ", e);
			return Collections.emptyList();
		}
    	

	}
    
    
    @Override
    @Transactional
    public TotalesClienteTempDto obtenerContadoresTotales(UsuarioAplicativo usuarioAplicativo){
    	boolean banderaFilter = false;
		banderaFilter = usuarioAplicativo.getUsuarioRols().stream().anyMatch(rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave()));
		
    	
    	try {
    		
    		final List<TotalesClienteTempDto> clientes = new ArrayList<TotalesClienteTempDto>();
    		StringBuilder sb = new StringBuilder();
    		sb.append(" select  distinct  (select  COUNT(*) from sin.cliente_temp cltmp "); 
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp "); 
//    		sb.append("  and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append("  and cte.id_estatus <> 7 and cte.id_estatus <> 10 and cte.ind_estatus = 1) as totalProspectos, ");
    		
    		sb.append(" (select  count(*)  from sin.cliente_temp cltmp  ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona  "); 
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp  ");
    		sb.append("and cte.ind_estatus = 1");
//    		sb.append("and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp)  "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 2 and cltmp.ind_estatus = 1 ");
    		/*AJMO*/
    			sb.append(" and (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) = 0 ");
    		sb.append(") as totalProspectosEnProceso,  "); 
    		
    		sb.append(" (select  count(*)   from sin.cliente_temp cltmp ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona "); 
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where  ");
    		/*AJMO  sb.append(" cte.id_estatus = 3) " );*/
    		sb.append(" cte.id_estatus in (2,3) and (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) != 0 " );
    		sb.append(" ) as totalProspectosEnCotizacion," );
    		
    		sb.append(" (select  count(*) 	from sin.cliente_temp cltmp join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 4) as totalProspectosEnAutorizacion, ");
    		
    		sb.append(" (select  count(*) 	from sin.cliente_temp cltmp");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona "); 
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 5) as totalProspectosRechazados, "); 
    		sb.append(" (select  count(*) 	from sin.cliente_temp cltmp");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona "); 
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 6) as totalProspectosAutorizados, "); 
    		
    		sb.append("  (select  count(*) 	from sin.cliente_temp cltmp  ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona "); 
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) ");  
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 8) as totalProspectosAutorizadosMesaCtrl, ");
    		sb.append("  (select  count(*) 	from sin.cliente_temp cltmp  ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona ");
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) ");  
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 9) as totalProspectosDeclinados, ");
    		sb.append("  (select  count(*) 	from sin.cliente_temp cltmp  ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona ");
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) "); 
    		sb.append(" join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cte.id_estatus = 16) as totalProspectosDeclinadosMesaCtrl ");
    		sb.append(" from sin.cliente_temp cltmp ");
    		if(banderaFilter) {
				sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
			}
    		sb.append(" join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona ");
    		sb.append(" join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp ");
    		sb.append(" and cte.ind_estatus = 1 ");
//    		sb.append("	and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp) ");  
    		sb.append(" order by cltmp.fecha_alta desc ");
    		
    		
    		@SuppressWarnings("unchecked")
    		List<TotalesClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    	        	 TotalesClienteTempDto totalesClienteTempDto =  new TotalesClienteTempDto();
    	        	 totalesClienteTempDto.setTotalProspectos(rs.getInt("totalProspectos"));
    	        	 totalesClienteTempDto.setTotalProspectosEnProceso(rs.getInt("totalProspectosEnProceso"));
    	        	 totalesClienteTempDto.setTotalProspectosEnCotizacion(rs.getInt("totalProspectosEnCotizacion"));
    	        	 totalesClienteTempDto.setTotalProspectosEnAutorizacion(rs.getInt("totalProspectosEnAutorizacion"));
    	        	 totalesClienteTempDto.setTotalProspectosRechazados(rs.getInt("totalProspectosRechazados"));
    	        	 totalesClienteTempDto.setTotalProspectosAutorizados(rs.getInt("totalProspectosAutorizados"));
    	        	 totalesClienteTempDto.setTotalProspectosAutorizadosMesaCtrl(rs.getInt("totalProspectosAutorizadosMesaCtrl"));
    	        	 totalesClienteTempDto.setTotalProspectosDeclinados(rs.getInt("totalProspectosDeclinados"));
    	        	 totalesClienteTempDto.setTotalProspectosDeclinadosMesaCtrl(rs.getInt("totalProspectosDeclinadosMesaCtrl"));
    	        	  
    	        	  clientes.add(totalesClienteTempDto); 
    	          LOGGER.debug("obtenerContadorSeguimientoXEstatus--> "+sb);
    	          return clientes;
    		   }
    		   });
    		   
    		   
    		   return clientes.get(0);
    		
    	}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerContadoresTotales ", e);
			return new TotalesClienteTempDto();
		}
    	

	}

    @Override
    @Transactional
	public List<ClienteTempDto> obtenerRegistrosContadorPrincipal(UsuarioAplicativo usuarioAplicativo) {
    	boolean banderaFilter = false;
		banderaFilter = usuarioAplicativo.getUsuarioRols().stream().anyMatch(rol -> rol.getRol().getNombre().equals(RolUsuarioENUM.PROMOTOR_VENTAS.getClave()));
		
		final List<ClienteTempDto> clientes = new ArrayList<ClienteTempDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select cltmp.id_cliente_temp, cltmp.nombre_comercial, "); 
		sb.append(" cltmp.razon_social, cltmp.nombre, cltmp.apellido_paterno, cltmp.apellido_materno, cltmp.rfc,   "); 
		sb.append(" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as numeroCotizaciones,   "); 
		sb.append(" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=44) as numeroPreCotizaciones, ");
		sb.append(" (select count(*) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp and id_tipo_sol_cotizacion=45) as numeroCotizacionesDetalladas, ");
		sb.append(" ctp.descripcion as tipoPersona,  "); 
		sb.append(" (select count(*) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as numeroEventos,   "); 
		sb.append(" (select MAX(fecha_alta) from cliente_temp_bitacora where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimoEvento, ");
		sb.append(" (select MAX(fecha_alta) from cotizacion where id_cliente_temp = cltmp.id_cliente_temp) as fechaUltimaCotizacion, "); 
		sb.append("	ce.descripcion_estatus  "); 
		sb.append(" from sin.cliente_temp cltmp   ");
		if(banderaFilter) {
			sb.append(" join canal_venta cv on cltmp.id_canal_venta =cv.id_canal_venta and id_usuario_canal_venta = ").append(usuarioAplicativo.getIdUsuario());
		}
		sb.append(" left join sin.cat_general ctp on ctp.id_cat_general = cltmp.id_tipo_persona   "); 
		sb.append(" left join sin.cliente_temp_estatus cte on cte.id_cliente_temp =cltmp.id_cliente_temp  "); 
		
		
		sb.append(" and cte.fecha_alta = (select max(clte.fecha_alta) from cliente_temp_estatus clte where clte.id_cliente_temp = cltmp.id_cliente_temp ");
		sb.append(" and clte.id_estatus <> 7 and clte.id_estatus <> 10 and clte.ind_estatus=1 ) "); 
		sb.append(" left join sin.cat_estatus ce on ce.id_estatus = cte.id_estatus  where cltmp.ind_estatus = 1"); 
		sb.append(" order by cltmp.fecha_alta desc ");
		
		@SuppressWarnings("unchecked")
		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 List<ClienteTempEstatusDto> estatusCliente =  new ArrayList<ClienteTempEstatusDto>();
	        	 ClienteTempEstatusDto clienteTemEstatusDto =  new ClienteTempEstatusDto();
	        	  ClienteTempDto cliente = new ClienteTempDto();
	        	  cliente.setNombreComercial(rs.getString("nombre_comercial"));
	        	  cliente.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  cliente.setRazonSocial(rs.getString("razon_social"));
	        	  cliente.setNombre(rs.getString("nombre"));
	        	  cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
	        	  cliente.setApellidoMaterno(rs.getString("apellido_materno"));
	        	  cliente.setRfc(rs.getString("rfc"));
	        	  cliente.setNumeroCotizaciones(rs.getInt("numeroCotizaciones"));
	        	  cliente.setNumeroPreCotizaciones(rs.getInt("numeroPreCotizaciones"));
	        	  cliente.setNumeroCotizacionesDetalladas(rs.getInt("numeroCotizacionesDetalladas"));
	        	  cliente.setIdTipoPersona(new CatGeneralDto(rs.getString("tipoPersona")));
	        	  cliente.setNumeroEventos(rs.getInt("numeroEventos"));
	        	  cliente.setFechaUltimoEvento(rs.getDate("fechaUltimoEvento"));
	        	  cliente.setFechaUltimaCotizacion(rs.getDate("fechaUltimaCotizacion"));
	        	  
	        	  clienteTemEstatusDto.setIdEstatus(new CatEstatusDto());
	        	  clienteTemEstatusDto.getIdEstatus().setDescripcionEstatus(rs.getString("descripcion_estatus"));
	        	  estatusCliente.add(clienteTemEstatusDto);
	        	  
	        	  cliente.setEstatusCliente(estatusCliente);
	        	  clientes.add(cliente); 
	          LOGGER.debug("obtenerRegistrosContadorPrincipal--> "+sb);
	          return clientes;
		   }
		   });
		   
		   
		   return clientes;
	}
    
    @Override
    @Transactional
	public List<ClienteTempBitacoraSolicitudesDto> obtenerArchivoBitacoraSolicitudes(Long idClienteTempBitacoraSolicitudes) {
		final List<ClienteTempBitacoraSolicitudesDto> clientes = new ArrayList<ClienteTempBitacoraSolicitudesDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select archivo, nombre_archivo "); 
		sb.append(" from sin.cliente_temp_bitacora_solicitudes "); 
		sb.append(" where id_cliente_temp_bitacora_solicitudes=").append(idClienteTempBitacoraSolicitudes);
		
		
		@SuppressWarnings("unchecked")
		List<ClienteTempBitacoraSolicitudesDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 ClienteTempBitacoraSolicitudesDto clienteTempBiotacoraSolicitudesDto =  new ClienteTempBitacoraSolicitudesDto();
	        	 clienteTempBiotacoraSolicitudesDto.setNombreArchivo(rs.getString("nombre_archivo"));
	        	 clienteTempBiotacoraSolicitudesDto.setArchivoRecuperado(rs.getString("archivo"));
	        	  clientes.add(clienteTempBiotacoraSolicitudesDto);
	        	  
	          LOGGER.debug("obtenerArchivoBitacoraSolicitudes--> "+sb);
	          return clientes;
		   }
		   });
		   
		   
		   return clientes;
	}
    
    @Override
    @Transactional
	public ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdClienteTemp(Long idClienteTemp) {
		final List<ClienteTempBitacoraSolicitudesDto> clientes = new ArrayList<ClienteTempBitacoraSolicitudesDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select id_cliente_temp "); 
		sb.append(" from sin.cliente_temp_bitacora_solicitudes "); 
		sb.append(" where id_cliente_temp=").append(idClienteTemp);
		
		
		@SuppressWarnings("unchecked")
		List<ClienteTempBitacoraSolicitudesDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempBitacoraSolicitudesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto =  new ClienteTempBitacoraSolicitudesDto();
	        	 clienteTempBitacoraSolicitudesDto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	  clientes.add(clienteTempBitacoraSolicitudesDto);
	        	  
	          LOGGER.debug("obtenerBitacoraSolicitudesXIdClienteTemp--> "+sb);
			return clienteTempBitacoraSolicitudesDto;
	          
		   }
		   });
		   if(clientes.isEmpty()) {
			   ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto =  new ClienteTempBitacoraSolicitudesDto();
			   clientes.add(clienteTempBitacoraSolicitudesDto);
		   }
		   
		   return clientes.get(0);
	}
    
    @Override
    @Transactional
    public ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdCotizacion(Long idCotizacion) {
    	final List<ClienteTempBitacoraSolicitudesDto> clientes = new ArrayList<ClienteTempBitacoraSolicitudesDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select \r\n"+ 
				" id_cliente_temp_bitacora_solicitudes,\r\n" + 
				" id_cliente_temp,\r\n" + 
				" id_cotizacion,\r\n" + 
				" id_estatus,\r\n" + 
				" observacion,\r\n" + 
				" archivo,\r\n" + 
				" nombre_archivo,\r\n" + 
				" fecha_alta,\r\n" + 
				" fecha_modificacion,\r\n" + 
				" usuario_alta,\r\n" + 
				" usuario_modificacion,\r\n" + 
				" ind_estatus \r\n"); 
		sb.append(" from sin.cliente_temp_bitacora_solicitudes "); 
		sb.append(" where id_cotizacion =").append(idCotizacion);
		
		
		@SuppressWarnings("unchecked")
		List<ClienteTempBitacoraSolicitudesDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public ClienteTempBitacoraSolicitudesDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	 ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto =  new ClienteTempBitacoraSolicitudesDto();
	        	 clienteTempBitacoraSolicitudesDto.setIdClienteTempBitacoraSolicitudes(rs.getLong("id_cliente_temp_bitacora_solicitudes"));
	        	 clienteTempBitacoraSolicitudesDto.setIdClienteTemp(rs.getLong("id_cliente_temp"));
	        	 clienteTempBitacoraSolicitudesDto.setIdCotizacion(rs.getLong("id_cotizacion"));
	        	 clienteTempBitacoraSolicitudesDto.setCatEstatus(new CatEstatusDto(rs.getLong("id_estatus")));
	        	 clienteTempBitacoraSolicitudesDto.setObservacion(rs.getString("observacion"));
	        	 clienteTempBitacoraSolicitudesDto.setArchivoRecuperado(rs.getString("archivo"));
	        	 clienteTempBitacoraSolicitudesDto.setNombreArchivo(rs.getString("nombre_archivo"));
	        	 clienteTempBitacoraSolicitudesDto.setFechaAlta(rs.getDate("fecha_alta"));
	        	 
	        	 clientes.add(clienteTempBitacoraSolicitudesDto);
	        	  
	          LOGGER.debug("obtenerBitacoraSolicitudesXIdClienteTemp--> "+sb);
			return clienteTempBitacoraSolicitudesDto;
	          
		   }
		   });
		   if(clientes.isEmpty()) {
			   ClienteTempBitacoraSolicitudesDto clienteTempBitacoraSolicitudesDto =  new ClienteTempBitacoraSolicitudesDto();
			   clientes.add(clienteTempBitacoraSolicitudesDto);
		   }
		   
		   return clientes.get(0);
    }

    @SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional
	public ClienteTempDto obtenerEntidadFederativaXCP(String codigoPostal) {
    	
    	try {
        	final List<ClienteTempDto> clientes = new ArrayList<ClienteTempDto>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_entidad_federativa, id_municipio from sin.cat_codigo_postal where codigo_postal = ").append(codigoPostal);
    					
    			@SuppressWarnings("unchecked")
    			List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    		          public ClienteTempDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  ClienteTempDto cliente =  new ClienteTempDto();
    		        	  cliente.setIdMedioContacto(new MedioContactoDto());
    		        	  
    		        	  Long idEntidad = rs.getLong("id_entidad_federativa");
    		        	  Long idMunicipio = rs.getLong("id_municipio");
    		        	  
    		        	  cliente.getIdMedioContacto().setEstado(rs.getString("id_entidad_federativa"));
    		        	  cliente.getIdMedioContacto().setNombreAlcaldia(rs.getString("id_municipio"));
    		        	  clientes.add(cliente);
    		        	  
    		          LOGGER.debug("obtenerBitacoraSolicitudesXIdClienteTemp--> "+sb);
    				return cliente;
    		          
    			   }
    			   });
    			
    			   if(clientes.isEmpty()) {
    				   ClienteTempDto clienteVacio =  new ClienteTempDto();
    				   clientes.add(clienteVacio);
    			   }
    			   
    			   return clientes.get(0);
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return new ClienteTempDto();
		}
    	

		}

    @SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional
	public List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro) {
    	try {
        	final List<CatSubGiroComercialDto> clientes = new ArrayList<CatSubGiroComercialDto>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_cat_sub_giro_comercial, id_giro_comercial, descripcion from sin.cat_sub_giro_comercial where id_giro_comercial = ").append(idGiro).append( " order by descripcion asc");
    					
    			@SuppressWarnings("unchecked")
    			List<CatSubGiroComercialDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    		          public CatSubGiroComercialDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  CatSubGiroComercialDto subGiro = new CatSubGiroComercialDto();
    		        	  
    		        	  subGiro.setIdCatSubGiroComercial(rs.getLong("id_cat_sub_giro_comercial"));
    		        	  subGiro.setIdGiroComercial(rs.getLong("id_giro_comercial"));
    		        	  subGiro.setDescripcion(rs.getString("descripcion"));
    		        	  
    		        	  clientes.add(subGiro);
    		        	  
    		          LOGGER.debug("obtenerBitacoraSolicitudesXIdClienteTemp--> "+sb);
    				return subGiro;
    		          
    			   }
    			   });
    			
    			   if(clientes.isEmpty()) {
    				   CatSubGiroComercialDto clienteVacio =  new CatSubGiroComercialDto();
    				   clientes.add(clienteVacio);
    			   }
    			   
    			   return clientes;
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return new ArrayList<CatSubGiroComercialDto>();
		}
	}


}

