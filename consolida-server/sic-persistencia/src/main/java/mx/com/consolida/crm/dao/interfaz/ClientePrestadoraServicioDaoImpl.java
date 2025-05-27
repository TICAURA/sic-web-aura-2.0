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
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClientePrestadoraServicioDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClientePrestadoraServicio;

@Repository
public class ClientePrestadoraServicioDaoImpl extends GenericDAO<ClientePrestadoraServicio, Long> implements ClientePrestadoraServicioDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioDaoImpl.class);

	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<ClientePrestadoraServicioDto> listaClientesPrestadoras(Long idCliente) {
		try {
			final List<ClientePrestadoraServicioDto> clientePrestadoras = new ArrayList<ClientePrestadoraServicioDto>();
			StringBuilder sb = new StringBuilder();

			sb.append(" select cps.id_cliente_prestadora_servicio, cps.id_cliente, cps.id_prestadora_servicio_fondo, cps.id_prestadora_servicio, ");
			sb.append(" psf.id_consar, psf.razon_social as razon_social_prest_serv_fondo, psf.rfc as rfc_prest_serv_fondo, psf.es_fondo, ");
			sb.append(" (select rfc from sin.prestadora_servicio where id_prestadora_servicio = cps.id_prestadora_servicio and ind_estatus = 1) as rfc_prestadora, ");
			sb.append(" (select razon_social from sin.prestadora_servicio where id_prestadora_servicio = cps.id_prestadora_servicio and ind_estatus = 1) as razon_social_prestadora, ");
			sb.append(" (select es_fondo from sin.prestadora_servicio where id_prestadora_servicio = cps.id_prestadora_servicio and ind_estatus = 1) as es_fondo_prestadora ");
			sb.append(" from sin.cliente cli,sin.cliente_prestadora_servicio cps, prestadora_servicio psf ");
			sb.append(" where psf.id_prestadora_servicio = cps.id_prestadora_servicio_fondo ");
			sb.append(" and cli.id_cliente = cps.id_cliente ");
			sb.append(" and cli.ind_estatus = 1 ");
			sb.append(" and psf.ind_estatus = 1 ");
			sb.append(" and cps.ind_estatus = 1 ");
			sb.append(" and cli.id_cliente = ? ");
			
			
			@SuppressWarnings("unchecked")
			List<ClientePrestadoraServicioDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
		          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		        	  ClientePrestadoraServicioDto ps = new ClientePrestadoraServicioDto();
		        	  ps.setIdClientePrestadoraServicio(rs.getLong("id_cliente_prestadora_servicio"));
		        	  ps.setPrestadoraServicioFondo(new PrestadoraServicioDto(rs.getString("rfc_prest_serv_fondo"), rs.getString("razon_social_prest_serv_fondo"), rs.getString("id_consar"), rs.getLong("id_prestadora_servicio_fondo")));
		        	  ps.setPrestadoraServicio(new PrestadoraServicioDto(rs.getString("rfc_prestadora"), rs.getString("razon_social_prestadora"), null, rs.getLong("id_prestadora_servicio")));
		        	  ps.setClienteDto(new ClienteDto(rs.getLong("id_cliente")));
		        	  clientePrestadoras.add(ps); 
		          LOGGER.debug("Datos Prestadora de servicio: --> ps");
		          return clientePrestadoras;
			   }
			   });
			   
			   
			   return clientePrestadoras;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerPrestadorasServicio Dao ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
    @Transactional(readOnly=true)
	public Long getidFondoByIdCliente(Long idCliente) {
    	
    	try {
        	final List<Integer> integer = new ArrayList<Integer>();
        	StringBuilder sb = new StringBuilder();
    			sb.append("select id_prestadora_servicio_fondo " + 
    					" from sin.cliente_prestadora_servicio" + 
    					" where id_cliente_prestadora_servicio = (select max(id_cliente_prestadora_servicio) " + 
    					"										from sin.cliente_prestadora_servicio " + 
    					"                                        where id_cliente = ?" + 
    					"                                        and ind_estatus = 1) " + 
    					" and ind_estatus = 1 ");
    					
    			@SuppressWarnings("unchecked")
    			List<Long> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
    		          public Long mapRow(ResultSet rs, int rowNum) throws SQLException {

    		        	  Long id = rs.getLong("id_prestadora_servicio_fondo");
    
    		        	  
    		          LOGGER.debug("getidFondoByIdCliente--> "+sb);
    				return id;
    		          
    			   }
    			   });

    			   
    			   return sinUso.get(0);
    	}catch (Exception e) {
    		LOGGER.error("Ocurrio un error en obtenerEntidadFederativaXCP ", e);
			return null;
		}
	}
	

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly=true)
	@Override
	public Long getidFondoPrestadoraByIdCliente(Long idCliente) {
		Long idPrestadora=null;
		try {
		
			StringBuilder sb = new StringBuilder();
			sb.append("select id_prestadora_servicio_fondo " + 
					" from sin.cliente_prestadora_servicio" + 
					" where id_cliente_prestadora_servicio = (select ifnull(max(id_cliente_prestadora_servicio),0) " + 
					"										from sin.cliente_prestadora_servicio " + 
					"                                        where id_cliente = ?" + 
					"                                        and ind_estatus = 1) " + 
					" and ind_estatus = 1 ");
			
			idPrestadora=jdbcTemplate.queryForObject(sb.toString(), new Object[]{idCliente}, Long.class);
					return idPrestadora;	
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasComplementariasEnBorrador ", e);
		}
		return idPrestadora;
	}
	


}
