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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.CatGeneral;

@Repository
public class CatalogoDaoImpl extends GenericDAO<CatGeneral, Integer> implements CatalogoDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CatalogoDaoImpl.class);
	
	@Transactional
	public List<CatGeneralDto> obtenerCatGeneralByClvMaestro(String clv){
		try {
			String sql = "select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus "
					+ " from sin.cat_general where ind_Estatus = 1 "
					+ " and id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave ='" + clv + "') order by descripcion asc";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestro ", e);
			return Collections.emptyList();
		}
	}
	
	@Transactional
	public List<CatGeneralDto> obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(String clv){
		try {
			String sql = "select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus "
					+ " from sin.cat_general where ind_Estatus = 1 "
					+ " and id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave ='" + clv + "') order by id_cat_general asc";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestro ", e);
			return Collections.emptyList();
		}
	}
	
	public CatGeneralDto obtenerCatGeneralByClv(String clv) {
		try {
			String sql = "select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus "
					+ " from sin.cat_general where ind_Estatus = 1 and clave ='"+ clv + "'";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data.get(0);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClv ", e);
			return new CatGeneralDto();
		}
	}
	
	@Override
	public CatGeneralDto obtenerCatGeneralByClvMaestroClv(String maestro, String clv) {
		try {
			String sql = "select id_cat_general as idCatGeneral, clave, descripcion, ind_estatus as indEstatus\n" 
					+ "from cat_general where id_cat_maestro = (select id_cat_maestro from cat_maestro where clave = '"+ maestro + "'"
					+ ") and clave = '"+ clv + "'";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data.get(0);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClv ", e);
			return new CatGeneralDto();
		}
	}
	
	
	
	@Override
	public List<CatGeneralDto> obtenerListaProductos() {
		try {
			String sql = "select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus \n"
					+ "	from sin.cat_general where ind_Estatus = 1 and id_cat_maestro in (30,57)";
					
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerListaProductos ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CatGeneralDto> obtenerCatEntidadFederativaByClvMaestro(String clv){
		try {
			String sql = "select id_entidad_federativa as idCatGeneral, cve_entidad_federativa as clave, descripcion_entidad_federativa as descripcion, ind_estatus as indEstatus "
					+ " from sin.cat_entidad_federativa where cve_cat_general = ("
					+ " select clave from sin.cat_general where ind_Estatus = 1 and "
					+ " id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave ='"
					+ clv + "'))";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatEntidadFederativaByClvMaestro ", e);
			return Collections.emptyList();
		}
	}
	
	
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CatGeneralDto> obtenerCatMunicipioByClvMaestro(String clv){
		try {
			String sql = "select id_cat_municipios as idCatGeneral, cve_municipio as clave, descripcion, cve_entidad_federativa, ind_estatus as indEstatus  "
					+ " from sin.cat_municipios where cve_cat_general = ("
					+ " select clave from sin.cat_general where ind_Estatus = 1 and "
					+ " id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave ='"
					+ clv + "'))";
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatMunicipioByClvMaestro ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<CatGeneralDto> obtenerCatMunicipioByClvMaestroByEntidadFed(String clv, String entidad){
		
		try {
		String sql = "select id_cat_municipios as idCatGeneral, cve_municipio as clave, descripcion, id_entidad_federativa, ind_estatus as indEstatus  "
				+ " from sin.cat_municipios where cve_cat_general = (\n" + 
				" select clave from sin.cat_general where ind_Estatus = 1 and " + 
				" id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave ='"+ clv + "') "
						+ "and id_entidad_federativa = '"+ entidad +"')";
		List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
		return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatMunicipioByClvMaestroByEntidadFed ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CatGeneralDto> obtenerCatMunicipioByEntidadFedByCveMun(String entidad, String municipio){
		
		try {
			String sql = "select id_cat_municipios as idCatGeneral, cve_municipio as clave, descripcion, id_entidad_federativa, ind_estatus as indEstatus  "
					+ " from sin.cat_municipios where id_entidad_federativa = "+entidad +
					" and id_cat_municipios = "+municipio;
					
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatMunicipioByEntidadFedByCveMun ", e);
			return Collections.emptyList();
		}
		

	}
	
	@Override
	@Transactional
	public List<CatTipoPagoDto> otenerTipoPagoActivo(){
		try {
			String sql = " select id_tipo_pago as idTipoPago, cve_tipo_pago as cveTipoPago, "
					+ " descripcion_tipo_pago as descripcionTipoPago ," + " dias_naturales as diasNaturales, "
					+ " dias_periodo as diasPeriodo, " + " por_anio as porAnio," + " fecha_alta as fechaAlta "
					+ " from cat_tipo_pago order by id_tipo_pago asc ";
			List<CatTipoPagoDto> data = (List<CatTipoPagoDto>) (List<?>) callNativeQuery(sql, "obtenerTipoPago", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en otenerTipoPagoActivo ", e);
			return Collections.emptyList();
		}
	}
	
	@Transactional
	@Override
	public List<CatGeneralDto> obtenerCatEstatusInicialCotizaciones() {
		try {
			String sql = " select id_estatus as idCatGeneral, cve_estatus as clave, descripcion_estatus as descripcion, ind_estatus as indEstatus "
					+ " from cat_estatus where cve_estatus in ( 'COTI_EN_PROCESO','COTI_RECHAZO','COTI_AUT','ENVI_AUT') ";
			@SuppressWarnings("unchecked")
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatEstatusInicialCotizaciones ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<CatProductoDto> obtenerCatProducto() {
		try {
			final List<CatProductoDto> list = new ArrayList<CatProductoDto>();
			StringBuilder sb = new StringBuilder();
			sb.append(" select cps.clave, cps.descripcion, cp.id_cat_producto, cp.id_cat_producto_sat, cp.clave as claveProducto , cp.descripcion as descripcionProducto");
			sb.append(" from cat_producto cp, cat_producto_sat cps ");
			sb.append(" where cp.id_cat_producto_sat = cps.id_cat_producto_sat ");

			List<CatProductoDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public CatProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatProductoDto producto = new CatProductoDto();

					producto.setClaveSAT(rs.getString("clave"));
					producto.setDescripcionSAT(rs.getString("descripcion"));
					producto.setIdCatProducto(rs.getLong("id_cat_producto"));
					producto.setIdCatProductoSAT(rs.getLong("id_cat_producto_sat"));
					producto.setClaveProducto(rs.getString("claveProducto"));
					producto.setDescripcionProducto(rs.getString("descripcionProducto"));

					list.add(producto);
					return producto;
				}
			});
			return list;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatProducto ", e.getMessage());
			return Collections.emptyList();
		}
	}
	
	
    @SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
	public List<CatSubGiroComercialDto> obtenerCatSubgiroComercialXIdGiro(Long idGiro) {
    	try {
        	final List<CatSubGiroComercialDto> clientes = new ArrayList<CatSubGiroComercialDto>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_cat_sub_giro_comercial, id_giro_comercial, descripcion from sin.cat_sub_giro_comercial where id_giro_comercial = ? order by descripcion asc");
    					
    			@SuppressWarnings("unchecked")
    			List<CatSubGiroComercialDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idGiro}, new RowMapper() {
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
    
	
	
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
    @Transactional(readOnly=true)
	public List<CatGeneralDto> obtenerCatGeneralNominaByClvMaestro(String clv) {
    	try {
        		StringBuilder sb = new StringBuilder();
				sb.append(" select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus ");
				sb.append(" from sin.cat_general where ind_Estatus = 1  ");
				sb.append(" and id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave =? )");

    			return jdbcTemplate.query(sb.toString(), new Object[] {clv}, new RowMapper() {
    		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  CatGeneralDto catGeneralDto = new CatGeneralDto();
    		        	  catGeneralDto.setIdCatGeneral(rs.getLong("idCatGeneral"));
    		        	  catGeneralDto.setClave(rs.getString("clave"));
    		        	  catGeneralDto.setDescripcion(rs.getString("descripcion"));
    		        	  catGeneralDto.setIdMasdescripcion(rs.getString("clave")+" - "+rs.getString("descripcion"));
    		        	  return catGeneralDto;

    			   }
    			});

    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerCatGeneralNominaByClvMaestro ", e);
    		return Collections.emptyList();
		}
	}
    
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
 	@Override
     @Transactional(readOnly=true)
 	public List<CatGeneralDto> obtenerCatGeneralNominaByClvMaestroAndClaveGeneral(String clv , String clvGeneral) {
     	try {
         		StringBuilder sb = new StringBuilder();
 				sb.append(" select id_cat_general as idCatGeneral, clave, descripcion,ind_estatus as indEstatus ");
 				sb.append(" from sin.cat_general where ind_Estatus = 1  ");
 				sb.append(" and id_cat_maestro = (select id_cat_maestro from sin.cat_maestro where ind_estatus = 1 and clave =? )");
 				sb.append(" clave = ? ");

     			return jdbcTemplate.query(sb.toString(), new Object[] {clv, clvGeneral}, new RowMapper() {
     		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
     		        	  CatGeneralDto catGeneralDto = new CatGeneralDto();
     		        	  catGeneralDto.setIdCatGeneral(rs.getLong("idCatGeneral"));
     		        	  catGeneralDto.setClave(rs.getString("clave"));
     		        	  catGeneralDto.setDescripcion(rs.getString("descripcion"));
     		        	  catGeneralDto.setIdMasdescripcion(rs.getString("clave")+" - "+rs.getString("descripcion"));
     		        	  return catGeneralDto;

     			   }
     			});

     	}catch (Exception e) {
     		LOGGER.error("Ocurrio un error en obtenerCatGeneralNominaByClvMaestro ", e);
     		return Collections.emptyList();
 		}
 	}
    
    @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
    @Transactional(readOnly=true)
	public List<CatGeneralDto> listaConceptoFacturaCrmByIdCliente(Long idCliente) {
    	try {
        		StringBuilder sb = new StringBuilder();
				sb.append(" select ccf.id_concepto_facturacion_cliente, ccf.concepto_facturacion ");
				sb.append(" from sin.cliente_concepto_facturacion ccf ");
				sb.append(" where id_cliente = ?  ");
				sb.append(" and ind_estatus = 1");

    			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
    		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
    		        	  CatGeneralDto catGeneralDto = new CatGeneralDto();
    		        	  catGeneralDto.setIdCatGeneral(rs.getLong("id_concepto_facturacion_cliente"));
    		        	  catGeneralDto.setDescripcion(rs.getString("concepto_facturacion"));
    		        	  return catGeneralDto;

    			   }
    			});

    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en listaConceptoFacturaByIdCliente ", e);
    		return Collections.emptyList();
		}
	}

	   @SuppressWarnings({ "rawtypes", "unused", "unchecked" })
		@Override
	    @Transactional(readOnly=true)
		public List<CatGeneralDto> catFormulasByIdTipoFormula(Long idTipoFormula) {
	    	try {
	        		StringBuilder sb = new StringBuilder();
					sb.append(" select distinct cf.id_cat_formulas,cf.clave, cf.descripcion ");
					sb.append(" from sin.cat_formulas cf, sin.fomula_tipo ft, sin.cat_tipo_formula ctf ");
					sb.append(" where ft.id_cat_formulas = cf.id_cat_formulas ");
					sb.append(" and ctf.id_cat_tipo_formula = ft.id_cat_tipo_formula ");
					sb.append(" and ctf.id_cat_tipo_formula = ? ");
					sb.append(" and cf.ind_estatus = 1");

	    			return jdbcTemplate.query(sb.toString(), new Object[] {idTipoFormula}, new RowMapper() {
	    		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	    		        	  CatGeneralDto catGeneralDto = new CatGeneralDto();
	    		        	  catGeneralDto.setIdCatGeneral(rs.getLong("id_cat_formulas"));
	    		        	  catGeneralDto.setClave(rs.getString("clave"));
	    		        	  catGeneralDto.setDescripcion(rs.getString("descripcion"));
	    		        	  return catGeneralDto;

	    			   }
	    			});

	    	}catch (Exception e) {
	    		LOGGER.error("Ocurrio un error en listaConceptoFacturaByIdCliente ", e);
	    		return Collections.emptyList();
			}
		}
	   
		@Transactional
		public List<CatGeneralDto> obtenerCatCelula(){
			try {
				String sql = " select id_celula as idCatGeneral, clave, nombre as descripcion, ind_estatus as indEstatus  from sin.celula where ind_estatus = 1 ";				
				List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
				return data;

			} catch (Exception e) {
				LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestro ", e);
				return Collections.emptyList();
			}
		}
	
}
