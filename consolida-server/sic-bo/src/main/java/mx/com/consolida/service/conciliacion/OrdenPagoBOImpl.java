package mx.com.consolida.service.conciliacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.conciliaciones.CargaOrdenesPagoDto;
import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.conciliacion.OrdenPagoDao;
import mx.com.consolida.entity.ppp.PppColaboradorEstatus;


@Service
public class OrdenPagoBOImpl  implements OrdenPagoBO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrdenPagoBOImpl.class);

	@Autowired
	private OrdenPagoDao ordenPagoDao;
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<OrdenPagoDto> getOrdenesPagoByIdCarga(long idCarga) {
		return ordenPagoDao.getOrdenPagoByIdCarga(idCarga);
		
	}
	

	public Boolean existeOrdenPagoById(Long idOrdenOrigen) {
		try {
		StringBuilder sb = new StringBuilder();								
		sb.append(" SELECT distinct id_orden_origen ");
		sb.append(" FROM orden_pago  op  ");
				sb.append(" where id_orden_origen= "+ idOrdenOrigen);

				List<Long> listaIds = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
					public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
												
						return rs.getLong("id_orden_origen");	
					}
				});
					
	    
	     if (listaIds.size()>0) {
	    	 	return true;
	     }
		return  false;
		
		}catch(Error e) {
			return  false;
	     }
	     
	     
	    
		
	     
		
	}

	
}
