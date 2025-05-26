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
import mx.com.consolida.crm.dto.PrestadoraServicioActividadDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioGiroComercial;

@Repository
public class PrestadoraServicioGiroComercialDaoImpl extends GenericDAO<PrestadoraServicioGiroComercial, Long> implements PrestadoraServicioGiroComercialDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioGiroComercialDaoImpl.class);
	
	@Override
	public List<PrestadoraServicioActividadDto> convertirGiroADto(List<PrestadoraServicioGiroComercial> prestadoraGiroComercial) {
		
		try {
			
			List<PrestadoraServicioActividadDto> actividad = new ArrayList<PrestadoraServicioActividadDto>();
			
			for(PrestadoraServicioGiroComercial cliente: prestadoraGiroComercial) {
				
				if(cliente!=null && cliente.getIndEstatus()!=0) {
					PrestadoraServicioActividadDto prestadoraActividad = new PrestadoraServicioActividadDto();
					
					prestadoraActividad.setIdPrestadoraGiroComercial(cliente.getIdPrestadoraServicioGiroComercial());
					prestadoraActividad.setCatGiroComercial(new CatGeneralDto());
					prestadoraActividad.getCatGiroComercial().setIdCatGeneral(cliente.getCatGiroComercial().getIdCatGeneral());
					prestadoraActividad.getCatGiroComercial().setDescripcion(cliente.getCatGiroComercial().getDescripcion());
					prestadoraActividad.setSubgiroComercial(new CatSubGiroComercialDto());
					prestadoraActividad.getSubgiroComercial().setIdCatSubGiroComercial(cliente.getCatSubGiroComercial().getIdCatSubGiroComercial());
					prestadoraActividad.getSubgiroComercial().setDescripcion(cliente.getCatSubGiroComercial().getDescripcion());
					
					actividad.add(prestadoraActividad);
				}
				
			}
			return actividad;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en convertirGiroADto ", e);
			return Collections.emptyList();
		}
	}
	
	
    @SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
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
