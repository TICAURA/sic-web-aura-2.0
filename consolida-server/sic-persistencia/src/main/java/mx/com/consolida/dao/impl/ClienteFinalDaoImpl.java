package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.ClienteFinalDao;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.crm.Cliente;


@Repository
public class ClienteFinalDaoImpl extends GenericDAO<Cliente, Integer> implements ClienteFinalDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	ModelMapper mapper = new ModelMapper();
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteFinalDaoImpl.class);
	
	public List<ClienteTempDto> obtenerClientes(){
//		final List<ClienteTempDto> clientes = new ArrayList<ClienteTempDto>();
//		StringBuilder sb = new StringBuilder();
//		sb.append(" select "
//				+ " cl.id_cliente, " + 
//				" cl.razon_social, " + 
//				" cl.rfc, " + 
//				" (select count(*) from cotizacion where id_cliente_temp = cl.id_cliente) as numeroCotizaciones, " + 
//				" ctp.descripcion as tipoPersona " + 
//				" from sin.cliente cl " + 
//				" join sin.cat_general ctp on ctp.id_cat_general = cl.id_tipo_persona "
//				+ " where cl.id_estatus = 1");
//		
//		
//		@SuppressWarnings("unchecked")
//		List<ClienteTempDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
//	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
//	        	  ClienteTempDto cliente = new ClienteTempDto();
//	        	  cliente.setIdClienteTemp(rs.getLong("id_cliente"));
//	        	  cliente.setRazonSocial(rs.getString("razon_social"));
//	        	  cliente.setRfc(rs.getString("rfc"));
//	        	  cliente.setNumeroCotizaciones(rs.getInt("numeroCotizaciones"));
//	        	  cliente.setIdTipoPersona(new CatGeneralDto(rs.getString("tipoPersona")));
//	        	  clientes.add(cliente); 
//	          LOGGER.debug("Datos Menu--> ");
//	          return clientes;
//		   }
//		   });
		   
		
		
		
		   
		   return null;
	}
	
	@Transactional
	public ClienteTempDto obtenerClienteById(Integer idCliente){
		Cliente cliente = new Cliente();
		ClienteTempDto ClienteTempDto = new ClienteTempDto();
		cliente = read(idCliente);
		ClienteTempDto = mapper.map(cliente, ClienteTempDto.class);
		return ClienteTempDto!=null ? ClienteTempDto : new ClienteTempDto();
	}
	
	@Transactional
	public List<CotizacionDto> obtenerCotizaciones(Integer idClienteTemp){
		final List<CotizacionDto> list = new ArrayList<CotizacionDto>();
		StringBuilder sb = new StringBuilder();
		sb.append(" select c.id_cotizacion, "
				+ "c.porcentaje_nom_fis, "
				+ "c.porcentaje_ppp, "
				+ "ctp.descripcion_tipo_pago "
				+ "from sin.cotizacion c join sin.cat_tipo_pago ctp on c.id_periodicidad = ctp.id_tipo_pago where c.id_cliente_temp = " + idClienteTemp
				+ " and ind_estatus = 1");
		@SuppressWarnings("unchecked")
		List<CotizacionDto> sinUso = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
	          public Object mapRow(ResultSet rs, int rowNum) throws SQLException {	        	  
	        	  CotizacionDto objDto = new CotizacionDto();
	        	  objDto.setIdCotizacion(rs.getLong("id_cotizacion"));
	        	  objDto.setPorcentajePpp(rs.getInt("porcentaje_ppp"));
	        	  objDto.setPorcentajeNomFis(rs.getInt("porcentaje_nom_fis"));
	        	  objDto.setIdPeriodicidad(new CatTipoPagoDto(rs.getString(("descripcion_tipo_pago"))));
	        	  list.add(objDto); 
	          LOGGER.debug("Datos Menu--> ");
	          return list;
		   }
		   });
		return list;
	}
	

}

