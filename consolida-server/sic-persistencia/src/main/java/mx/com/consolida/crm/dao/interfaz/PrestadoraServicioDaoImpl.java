package mx.com.consolida.crm.dao.interfaz;

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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.CelulaPrestadoraServicioDto;
import mx.com.consolida.crm.dto.DatosListaFichaDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.FichaTecnicaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PrestadoraServicioDaoImpl extends GenericDAO<PrestadoraServicio, Long> implements PrestadoraServicioDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioDaoImpl.class);

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioDto> obtenerPrestadorasServicio(Long idCelula) {
		try {
			final List<PrestadoraServicioDto> prestadoras = new ArrayList<PrestadoraServicioDto>();
			StringBuilder sb = new StringBuilder();

//			sb.append(" select ps.id_prestadora_servicio, ps.rfc, ps.nombre_corto, ps.razon_social, ps.es_fondo, ");
//			sb.append(" (select count(*) from sin.prestadora_servicio_producto where id_prestadora_servicio = ps.id_prestadora_servicio) as totalProductosRegistrados ");
//			sb.append(" from sin.prestadora_servicio ps");
//			sb.append(" where ps.ind_estatus = 1 ");
			
			sb.append(" select ps.id_prestadora_servicio, ps.rfc, ps.nombre_corto, ps.razon_social, ps.es_fondo,   ");
			sb.append(" (select count(*) from sin.prestadora_servicio_producto where id_prestadora_servicio = ps.id_prestadora_servicio) as totalProductosRegistrados   ");
			sb.append(" from sin.prestadora_servicio ps  ");
			sb.append(" left join celula_prestadora_servicio cps on  ");
			sb.append(" cps.id_prestadora_servicio = ps.id_prestadora_servicio  ");
			sb.append(" left join celula cel on  ");
			sb.append(" cel.id_celula = cps.id_celula  ");
			sb.append(" where ps.ind_estatus = 1  ");
			if(idCelula!=null) {
				sb.append(" and cel.id_celula = ?  ");
			}
			
			Object[] object = null;
			if(idCelula!=null) {
				object = new Object[] {idCelula};
			}else {
				object = new Object[] {};
			}
			
			
			@SuppressWarnings("unchecked")
			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), object, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  PrestadoraServicioDto ps = new PrestadoraServicioDto();
		        	  ps.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
		        	  ps.setRazonSocial(rs.getString("razon_social"));
		        	  ps.setRfc(rs.getString("rfc"));
		        	  ps.setEsFondo(rs.getBoolean("es_fondo"));
		        	  ps.setNombreCorto(rs.getString("nombre_corto"));
		        	  ps.setTotalProductosRegistrados(rs.getInt("totalProductosRegistrados"));
		        	 
		        	  prestadoras.add(ps); 
		          LOGGER.debug("Datos Prestadora de servicio: --> ps");
		          return prestadoras;
			   }
			   });
			   
			   
			   return prestadoras;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerPrestadorasServicio Dao ", e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
	public PrestadoraServicioDto obtenerEntidadFederativaXCP(String codigoPostal) {
    	
    	try {
        	final List<PrestadoraServicioDto> clientes = new ArrayList<PrestadoraServicioDto>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_entidad_federativa, id_municipio from sin.cat_codigo_postal where codigo_postal = ").append(codigoPostal);
    					
    			@SuppressWarnings("unchecked")
    			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
    		          public PrestadoraServicioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  PrestadoraServicioDto cliente =  new PrestadoraServicioDto();
    		        	  Long idEntidad = rs.getLong("id_entidad_federativa");
    		        	  Long idMunicipio = rs.getLong("id_municipio");
    		        	  
    		        	  cliente.setPrestadoraServicioDomicilioDto(new DomicilioComunDto());
    		        	  cliente.getPrestadoraServicioDomicilioDto().setDomicilio(new DomicilioDto());
    		        	  cliente.getPrestadoraServicioDomicilioDto().getDomicilio().setCatMunicipios(new CatGeneralDto());
    		        	  
    		        	  cliente.getPrestadoraServicioDomicilioDto().getDomicilio().setIdEntidadFederativa(idEntidad);
    		        	  cliente.getPrestadoraServicioDomicilioDto().getDomicilio().getCatMunicipios().setIdCatGeneral(idMunicipio);
    		        	  clientes.add(cliente);
    		        	  
    		          LOGGER.debug("obtenerEntidadFederativaXCP--> "+sb);
    				return cliente;
    		          
    			   }
    			   });
    			
    			   if(clientes.isEmpty()) {
    				   PrestadoraServicioDto clienteVacio =  new PrestadoraServicioDto();
    				   clientes.add(clienteVacio);
    			   }
    			   
    			   return clientes.get(0);
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return new PrestadoraServicioDto();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioDto> listaPrestdorasFondoYSinFondoByIdCelula(Long idCelula, boolean esFondo) {
    	try {
        	final List<PrestadoraServicioDto> listaPrestdorasServFondo = new ArrayList<PrestadoraServicioDto>();
        	StringBuilder sb = new StringBuilder();
        	sb.append(" select pres.id_prestadora_servicio, pres.rfc, pres.nombre_corto, pres.razon_social, pres.es_fondo, ");
        	sb.append(" celpres.id_celula_prestadora_servicio, celpres.id_celula  ");
			sb.append(" from sin.prestadora_servicio pres, celula_prestadora_servicio celpres ");
			sb.append(" where celpres.id_prestadora_servicio = pres.id_prestadora_servicio ");
			if(esFondo) {
				sb.append(" and pres.es_fondo = 1 ");
			}else {
				sb.append(" and pres.es_fondo = 0 ");
			}
			sb.append(" and celpres.id_celula = ? ");
			sb.append(" and pres.ind_estatus = 1 ");
      
    					
			return jdbcTemplate.query(sb.toString(), new Object[] { idCelula }, new RowMapper() {
				public PrestadoraServicioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioDto presatdora = new PrestadoraServicioDto();
					presatdora.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
					presatdora.setRfc(rs.getString("rfc"));
					presatdora.setNombreCorto(rs.getString("nombre_corto"));
					presatdora.setRazonSocial(rs.getString("razon_social"));
					presatdora.setEsFondo(rs.getLong("es_fondo") != 0 ? true : false);
					
					CelulaPrestadoraServicioDto celulaPrestadoraServicioDto = new CelulaPrestadoraServicioDto();
					celulaPrestadoraServicioDto.setIdCelulaPrestadoraServicio(rs.getLong("id_celula_prestadora_servicio"));
					celulaPrestadoraServicioDto.setCelula(new CelulaDto(rs.getLong("id_celula")));
					presatdora.setCelulaPrestadoraServicioDto(celulaPrestadoraServicioDto);

					LOGGER.debug("listaPrestdorasFondoYSinFondoByIdCelula--> " + sb);
					return presatdora;

				}
			});

    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en listaPrestdorasFondoYSinFondoByIdCelula ", e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioDto> verificarFondo(Long idCelula) {
		try {
			final List<PrestadoraServicioDto> prestadoras = new ArrayList<PrestadoraServicioDto>();
			StringBuilder sb = new StringBuilder();

			sb.append(" select p.es_fondo, p.id_consar from celula_prestadora_servicio c, prestadora_servicio p ");
			sb.append(" where c.id_prestadora_servicio = p.id_prestadora_servicio ");
			sb.append(" and c.id_celula=? and p.es_fondo=1 and p.ind_estatus = 1");
			
			
			@SuppressWarnings("unchecked")
			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idCelula}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  PrestadoraServicioDto ps = new PrestadoraServicioDto();
		        	  ps.setEsFondo(rs.getBoolean("es_fondo"));
		        	  ps.setIdConsar(rs.getString("id_consar"));
		        	 
		        	  prestadoras.add(ps); 
		          LOGGER.debug("Datos Prestadora de servicio: --> ps");
		          return prestadoras;
			   }
			   });
			  

			if(prestadoras.isEmpty()) {
				PrestadoraServicioDto ps = new PrestadoraServicioDto();
				prestadoras.add(ps);
			   }
			   
			   return prestadoras;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerPrestadorasServicio Dao ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly=true)
	public PrestadoraServicioDto obtenerPrestadoraServicioArchivoLogotipo(Long idPrestadoraServicio) {
		
			final List<PrestadoraServicioDto> clientes = new ArrayList<PrestadoraServicioDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select logo "); 
			sb.append(" from sin.prestadora_servicio "); 
			sb.append(" where id_prestadora_servicio=").append(idPrestadoraServicio);
			
			
			@SuppressWarnings("unchecked")
			List<PrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
		          public PrestadoraServicioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  PrestadoraServicioDto prestadoraDto =  new PrestadoraServicioDto();
		        	 prestadoraDto.setArchivoRecuperadoLogotipo(rs.getString("logo"));
		        	  clientes.add(prestadoraDto);
		        	  
		          LOGGER.debug("obtenerPrestadoraServicioArchivoLogotipo--> "+sb);
		          return prestadoraDto;
			   }
			   });
			
			   return clientes.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosPrestadora(Long idPrestadora) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,  ");
		sb.append(" psd.id_prestadora_servicio_documento, psd.id_prestadora_servicio  , psd.id_CMS , psd.nombre_archivo   ");
		sb.append(" from definicion_documento dd ");
		sb.append(" left join (select psdi.* from prestadora_servicio_doctos psdi where psdi.id_prestadora_servicio = ? ) psd ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento ");
		sb.append(" where dd.id_definicion_documento  in (1,2,3,4,5,6,7,8,9,10)   and dd.ind_estatus = 1 ");
		
		// 1,2,3,4,5,6,7,8,9,10 son todas las definiciones que tiene la prestadora de servicios
		
		
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
	          public DocumentoCSMDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	          
	          DocumentoCSMDto documento = new DocumentoCSMDto();
	          documento.setIdPrestadora(idPrestadora);
	          documento.setIdCMS(rs.getLong("id_CMS"));
	          DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();
	          
	          definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
	          definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
	          definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
	          documento.setDefinicion(definicionDocumento);
	          	 
	          documento.setNombreArchivo(rs.getString("nombre_archivo"));
	          documento.setIdPrestadoraServicioDoctumentos(rs.getLong("id_prestadora_servicio_documento"));
	        	  
	          return documento;
		   }
		   });
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosPrestadoraDefinicionDocto(String listaDefinicionDocumento, Long idPrestadora) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,  ");
		sb.append(" psd.id_prestadora_servicio_documento, psd.id_prestadora_servicio  , psd.id_CMS , psd.nombre_archivo   ");
		sb.append(" from definicion_documento dd ");
		sb.append(" left join (select psdi.* from prestadora_servicio_doctos psdi where psdi.id_prestadora_servicio = ? ) psd ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento ");
		sb.append(" where dd.id_definicion_documento  in (").append(listaDefinicionDocumento).append(")");
		
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
	          public DocumentoCSMDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	          
	          DocumentoCSMDto documento = new DocumentoCSMDto();
	          documento.setIdPrestadora(idPrestadora);
	          documento.setIdCMS(rs.getLong("id_CMS"));
	          DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();
	          
	          definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
	          definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
	          definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
	          documento.setDefinicion(definicionDocumento);
	          	 
	          documento.setNombreArchivo(rs.getString("nombre_archivo"));
	          documento.setIdPrestadoraServicioDoctumentos(rs.getLong("id_prestadora_servicio_documento"));
	        	  
	          return documento;
		   }
		   });
	}
	
	public FichaTecnicaDto fichaTecnica(Long idPrestadora) throws Exception {
		FichaTecnicaDto ficha = new FichaTecnicaDto();
		String sql = "select \r\n"+ 
//				"ps.nombre_centro_costos as nombreCentroCostos , \r\n" + 
				"ps.id_prestadora_servicio as idPrestadoraServicio, \r\n" + 
				"ps.id_consar as idConsar, ps.nombre_corto as nombreCorto, \r\n" + 
				"ps.es_fondo as isFondo, \r\n" + 
				"ps.razon_social as razonSocial,\r\n" + 
				"ps.logo as logotipo,\r\n" + 
				"c.nombre as nombreCelula,\r\n" + 
				"c.clave as claveCelula,\r\n" + 
				"cgrp.descripcion as descripcionRiesgo,\r\n" + 
				"cef.descripcion_entidad_federativa as descEntidad,\r\n" + 
				"cm.descripcion as descMunicipio,\r\n" + 
				"(\r\n" + 
				"	select \r\n" + 
				"(select count(distinct c.id_cat_grupo) \r\n" + 
				"	from cliente c \r\n" + 
				"	join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
				"where  cps.id_prestadora_servicio =  psd.id_prestadora_servicio) +\r\n" + 
				"(\r\n" + 
				"select count(distinct c.id_cat_grupo) \r\n" + 
				"	from cliente c \r\n" + 
				"	join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
				"where  cps.id_prestadora_servicio_fondo =  psd.id_prestadora_servicio\r\n" + 
				")as total\r\n" + 
				"from dual\r\n" + 
				") as totalClienteByGrupo,\r\n" + 
				"(\r\n" + 
				"	select count(*)\r\n" + 
				"	from cliente c \r\n" + 
				"	join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente and c.id_tipo_persona = 22 where  cps.id_prestadora_servicio_fondo = psd.id_prestadora_servicio\r\n" + 
				"	\r\n" + 
				") as totalClienteRozonSocial, \r\n"
				+ " ( select count(*) from prestadora_servicio ps \r\n" + 
				"join cliente_prestadora_servicio cps on cps.id_prestadora_servicio = ps.id_prestadora_servicio and cps.ind_estatus = 1 \r\n" + 
				"join nomina_cliente nc on nc.id_cliente = cps.id_cliente and nc.ind_estatus = 1 \r\n" + 
				"join colaborador_nomina cn on cn.id_nomina = nc.id_nomina_cliente and cn.ind_estatus = 1 \r\n" + 
				"join colaborador c on c.id_colaborador = cn.id_colaborador and c.ind_estatus = 1 \r\n" + 
				"where ps.id_prestadora_servicio = " + idPrestadora + " ) as totalColaboradores " +
				"from prestadora_servicio ps \r\n" + 
				"left join celula_prestadora_servicio cps on ps.id_prestadora_servicio = cps.id_prestadora_servicio and cps.ind_estatus = 1 \r\n" + 
				"left join celula c on c.id_celula = cps.id_celula and c.ind_estatus = 1 \r\n" + 
				"left join prestadora_servicio_clase_fraccion_prima pscfp on pscfp.id_prestadora_servicio = ps.id_prestadora_servicio and pscfp.ind_estatus = 1 \r\n" + 
				"left join cat_general cgrp on cgrp.id_cat_general = pscfp.id_cat_clase \r\n" + 
				"left join prestadora_servicio_domicilio psd on psd.id_prestadora_servicio = ps.id_prestadora_servicio \r\n" + 
				"left join domicilio d on d.id_domicilio = psd.id_domicilio \r\n" + 
				"left join cat_entidad_federativa cef on cef.id_entidad_federativa = d.id_entidad_federativa \r\n" + 
				"left join cat_municipios cm on cm.id_cat_municipios = d.id_municipio \r\n" + 
				"where ps.id_prestadora_servicio = " + idPrestadora;
		
		@SuppressWarnings("unchecked")
		List<FichaTecnicaDto> list = jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
	          public FichaTecnicaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	          
	        	  FichaTecnicaDto fichaTecnica = new FichaTecnicaDto();
	        	  fichaTecnica.setIdPrestadoraServicio(rs.getLong("idPrestadoraServicio"));
	        	  fichaTecnica.setIdConsar(rs.getString("idConsar"));
	        	  fichaTecnica.setNombreCorto(rs.getString("nombreCorto"));
	        	  fichaTecnica.setIsFondo(rs.getLong("isFondo"));
	        	  fichaTecnica.setRazonSocial(rs.getString("razonSocial"));
	        	  fichaTecnica.setLogotipo(rs.getString("logotipo"));
	        	  fichaTecnica.setNombreCelula(rs.getString("nombreCelula"));
	        	  fichaTecnica.setClaveCelula(rs.getString("claveCelula"));
	        	  fichaTecnica.setDescripcionRiesgo(rs.getString("descripcionRiesgo"));
	        	  fichaTecnica.setDescEntidad(rs.getString("descEntidad"));
	        	  fichaTecnica.setDescMunicipio(rs.getString("descMunicipio"));
	        	  fichaTecnica.setTotalClienteByGrupo(rs.getLong("totalClienteByGrupo"));
	        	  fichaTecnica.setTotalClienteRozonSocial(rs.getLong("totalClienteRozonSocial"));
//	        	  fichaTecnica.setNombreCentroCostos(rs.getString("nombreCentroCostos"));
	        	  fichaTecnica.setTotalColaboradores(rs.getLong("totalColaboradores"));
	        	  
	        	  String sqlActividadEconomica ="select \r\n" + 
	        	  		"\"\" as accionista,\r\n" + 
	        	  		"0 as porcentaje,\r\n" + 
	        	  		"\"\" as nombreDocumento,\r\n" + 
	        	  		"\"\" as descripcionDefinicion,\r\n" + 
	        	  		"0 as idCMS,\r\n" + 
	        	  		"cggc.descripcion as grupo, \r\n" + 
	        	  		"csgc.descripcion as subgrupo \r\n" + 
	        	  		"from \r\n" + 
	        	  		"prestadora_servicio_giro_comercial psgc \r\n" + 
	        	  		"join cat_general cggc on psgc.id_giro_comercial = cggc.id_cat_general\r\n" + 
	        	  		"join cat_general csgc on csgc.id_cat_general = psgc.id_cat_sub_giro_comercial and psgc.id_prestadora_servicio = "+fichaTecnica.getIdPrestadoraServicio();
	        	  String sqlAccionistas = "select \r\n" + 
	        	  		"concat(per.nombre,concat(\" \",concat(per.apellido_paterno,concat(\" \", IFNULL(per.apellido_materno,\"\"))))) as accionista,\r\n" + 
	        	  		"psal.porcentaje_accion as porcentaje,\r\n" + 
	        	  		"\"\" as nombreDocumento,\r\n" + 
	        	  		"\"\" as descripcionDefinicion,\r\n" + 
	        	  		"0 as idCMS,\r\n" + 
	        	  		"\"\" as grupo, \r\n" + 
	        	  		"\"\" as subgrupo\r\n" + 
	        	  		"from sin.prestadora_servicio_accionista psal,sin.prestadora_servicio ps, sin.persona per,  \r\n" + 
	        	  		"			 sin.prestadora_servicio_accionista_domicilio psad, sin.domicilio d \r\n" + 
	        	  		"			 where per.id_persona = psal.id_persona \r\n" + 
	        	  		"			 and ps.id_prestadora_servicio = psal.id_prestadora_servicio \r\n" + 
	        	  		"			 and ps.id_prestadora_servicio = "+fichaTecnica.getIdPrestadoraServicio()+" \r\n" + 
	        	  		"			 and per.ind_estatus = 1  \r\n" + 
	        	  		"			 and psal.ind_estatus = 1 \r\n" + 
	        	  		"			 and psal.id_prestadora_servicio_accionista = psad.id_prestadora_servicio_accionista \r\n" + 
	        	  		"			 and d.id_domicilio = psad.id_domicilio ";
	        	  String SqlDocumentos = "select \r\n" + 
	        	  		"\"\" as accionista,\r\n" + 
	        	  		"0 as porcentaje,\r\n" + 
	        	  		"psd.nombre_archivo as nombreDocumento,\r\n" + 
	        	  		"dd.nombre_documento as descripcionDefinicion,\r\n" + 
	        	  		"psd.id_CMS idCMS,\r\n" + 
	        	  		"\"\" as grupo, \r\n" + 
	        	  		"\"\" as subgrupo \r\n" + 
	        	  		"from definicion_documento dd\r\n" + 
	        	  		"left join (select psdi.* from prestadora_servicio_doctos psdi where psdi.id_prestadora_servicio = "+fichaTecnica.getIdPrestadoraServicio()+" ) psd\r\n" + 
	        	  		"on dd.id_definicion_documento = psd.id_definicion_documento\r\n" + 
	        	  		"where dd.id_definicion_documento  in (1,2,3,4,5,6,7,8,9,10) ";
	        	  String representanteLegal = "select concat(p.nombre,concat(\" \",concat(p.apellido_paterno,concat(\" \", IFNULL(p.apellido_materno,\"\"))))) as nombreDescripcion\r\n" + 
	        	  		"from prestadora_servicio_representante_legal rl \r\n" + 
	        	  		"join persona p on p.id_persona = rl.id_persona where rl.id_prestadora_servicio = " + fichaTecnica.getIdPrestadoraServicio();
	        	  String registroPatronal = "SELECT registro_patronal as nombreDescripcion FROM prestadora_servicio_registro_patronal where id_prestadora_servicio ="+ fichaTecnica.getIdPrestadoraServicio();
	        	  try {
	        		  fichaTecnica.setRepresentantesLegales(listasPrestadora1(representanteLegal));
	        		  fichaTecnica.setActividadEconomica(listasPrestadora(sqlActividadEconomica));
	        		  if(fichaTecnica.getIsFondo()==1) {
		        		  String sqlPrestadoras = " select pres.id_prestadora_servicio as idPrestadoraServicio,\r\n" + 
		        		  		" pres.nombre_corto as nombreCorto, \r\n" + 
		        		  		"(select \r\n" + 
		        		  		"	(select count(distinct c.id_cat_grupo) \r\n" + 
		        		  		"		from cliente c \r\n" + 
		        		  		"		join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
		        		  		"	where  cps.id_prestadora_servicio =  pres.id_prestadora_servicio) +\r\n" + 
		        		  		"	(select count(distinct c.id_cat_grupo) \r\n" + 
		        		  		"		from cliente c \r\n" + 
		        		  		"		join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente \r\n" + 
		        		  		"	where  cps.id_prestadora_servicio_fondo =  pres.id_prestadora_servicio\r\n" + 
		        		  		"	)as total\r\n" + 
		        		  		"	from dual\r\n" + 
		        		  		") as totalClienteByGrupo,\r\n" + 
		        		  		"(select \r\n" + 
		        		  		"	count(*)\r\n" + 
		        		  		"	from cliente c \r\n" + 
		        		  		"	join cliente_prestadora_servicio cps on c.id_cliente = cps.id_cliente and c.id_tipo_persona = 22 where  cps.id_prestadora_servicio_fondo = pres.id_prestadora_servicio\r\n" + 
		        		  		") as totalClienteRozonSocial,  \r\n" +
		        		  		" ( select count(*) from prestadora_servicio ps \r\n" + 
		        				"join cliente_prestadora_servicio cps on cps.id_prestadora_servicio = ps.id_prestadora_servicio and cps.ind_estatus = 1 \r\n" + 
		        				"join nomina_cliente nc on nc.id_cliente = cps.id_cliente and nc.ind_estatus = 1 \r\n" + 
		        				"join colaborador_nomina cn on cn.id_nomina = nc.id_nomina_cliente and cn.ind_estatus = 1 \r\n" + 
		        				"join colaborador c on c.id_colaborador = cn.id_colaborador and c.ind_estatus = 1 \r\n" + 
		        				"where ps.id_prestadora_servicio = pres.id_prestadora_servicio ) as totalColaboradores " +
		        				
		        		  		"from sin.prestadora_servicio pres, celula_prestadora_servicio celpres \r\n" + 
		        		  		"where celpres.id_prestadora_servicio = pres.id_prestadora_servicio \r\n" + 
		        		  		"and pres.es_fondo = 0  and celpres.id_celula = (select id_celula from celula_prestadora_servicio where id_prestadora_servicio = "+fichaTecnica.getIdPrestadoraServicio()+")\r\n" + 
		        		  		"and pres.ind_estatus = 1 ";
		        		  fichaTecnica.setPrestadorasAsociadas(listasPrestadoras(sqlPrestadoras));;
		        	  } else {
		        		  fichaTecnica.setAccionistas(listasPrestadora(sqlAccionistas));
		        		  fichaTecnica.setDocumentos(listasPrestadora(SqlDocumentos));
		        		  fichaTecnica.setRegistroPatronales(listasPrestadora1(registroPatronal));
		        	  }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          return fichaTecnica;
		   }
		   });
		ficha = !list.isEmpty() ? list.get(0):null;
		return ficha;
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private List<DatosListaFichaDto> listasPrestadora(String sql) throws Exception {
		return jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
	          public DatosListaFichaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	  DatosListaFichaDto dto =  new DatosListaFichaDto();
	        	  dto.setAccionista(rs.getString("accionista"));
	        	  dto.setDescripcionDefinicion(rs.getString("descripcionDefinicion"));
	        	  dto.setGrupo(rs.getString("grupo"));
	        	  dto.setIdCMS(rs.getLong("idCMS"));
	        	  dto.setNombreDocumento(rs.getString("nombreDocumento"));
	        	  dto.setPorcentaje(rs.getLong("porcentaje"));
	        	  dto.setSubgrupo(rs.getString("subgrupo"));
	          return dto;
		   }
		   });
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private List<DatosListaFichaDto> listasPrestadora1(String sql) throws Exception {
		return jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
	          public DatosListaFichaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	  DatosListaFichaDto dto =  new DatosListaFichaDto();
	        	  dto.setNombreOdescripcion(rs.getString("nombreDescripcion"));
	          return dto;
		   }
		   });
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private List<FichaTecnicaDto> listasPrestadoras(String sql) throws Exception {
		return jdbcTemplate.query(sql, new Object[] {}, new RowMapper() {
	          public FichaTecnicaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	        	  FichaTecnicaDto dto =  new FichaTecnicaDto();
	        	  dto.setIdPrestadoraServicio(rs.getLong("idPrestadoraServicio"));
	        	  dto.setNombreCorto(rs.getString("nombreCorto"));
	        	  dto.setTotalClienteByGrupo(rs.getLong("totalClienteByGrupo"));
	        	  dto.setTotalClienteRozonSocial(rs.getLong("totalClienteRozonSocial"));
	        	  dto.setTotalColaboradores(rs.getLong("totalColaboradores"));
	        	  return dto;
	          }
		   });
	}
	
	
	
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public  List<PrestadoraServicioDto> listPrestadoraServicioByIdCelulaAndIdCliente(Long idCelula, Long idCliente, boolean esFondo){
		try {
			
			StringBuilder sb = new StringBuilder();
			

			if(esFondo) {
				sb.append(" select ps.id_prestadora_servicio, ps.nombre_corto, ps.razon_social ");
				sb.append(" from sin.celula_prestadora_servicio celps,  sin.prestadora_servicio ps ");
				sb.append(" where ps.id_prestadora_servicio = celps.id_prestadora_servicio ");
				sb.append(" and ps.es_fondo = 1 ");
				sb.append(" and celps.id_celula = (select id_celula from sin.celula_cliente where id_cliente = ? and ind_estatus = 1) ");
				sb.append(" and ps.ind_estatus = 1  ");
			}else {
				sb.append(" select ps.id_prestadora_servicio, ps.nombre_corto, ps.razon_social ");
				sb.append(" from sin.celula_prestadora_servicio celps, sin.cliente_prestadora_servicio clips, sin.prestadora_servicio ps ");
				sb.append(" where ps.id_prestadora_servicio = celps.id_prestadora_servicio  ");
				sb.append(" and clips.id_prestadora_servicio  = ps.id_prestadora_servicio ");
				sb.append(" and clips.id_prestadora_servicio = celps.id_prestadora_servicio ");
				sb.append(" and ps.es_fondo = 0 ");
				sb.append(" and celps.id_celula = ? ");
				sb.append(" and clips.id_cliente = ? ");
				sb.append(" and ps.ind_estatus = 1 ");
				sb.append(" and clips.ind_estatus = 1 ");
			}

			Object[] object = null;
			
			if(esFondo) {
				object = new Object[] {idCliente};
			}else {
				object = new Object[] {idCelula, idCliente};
			}
			
			return jdbcTemplate.query(sb.toString(), object, new RowMapper() {
		          public PrestadoraServicioDto mapRow(ResultSet rs, int rowNum) throws SQLException {	          
		          
		          PrestadoraServicioDto ps = new PrestadoraServicioDto();
	        	  ps.setIdPrestadoraServicio(rs.getLong("id_prestadora_servicio"));
	        	  ps.setRazonSocial(rs.getString("razon_social"));
	        	  ps.setNombreCorto(rs.getString("nombre_corto"));
	        	  
	        	  return ps;
		          
			   }
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listPrestadoraServicioByIdCelulaAndIdCliente Dao ", e);
			return Collections.emptyList();
		}
	}
}
