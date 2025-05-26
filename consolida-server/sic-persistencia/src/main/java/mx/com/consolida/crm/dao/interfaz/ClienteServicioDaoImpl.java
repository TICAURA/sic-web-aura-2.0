package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;

@Repository
public class ClienteServicioDaoImpl extends GenericDAO<ClienteServicio, Long> implements ClienteServicioDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteServicioDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public ClienteServicio obtenerClienteServicioXIdPrestServProducto(Long idPrestadoraServicioProducto, Long idCliente) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select prod from ClienteServicio prod where prod.prestadoraServicioProducto.idPrestadoraServicioProducto = :idPrestadoraServicioProducto "
					+ " and prod.cliente.idCliente = :idCliente");
			query.setParameter("idPrestadoraServicioProducto", idPrestadoraServicioProducto);
			query.setParameter("idCliente", idCliente);

			return (ClienteServicio) query.uniqueResult();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerClienteServicioXIdPrestServProducto ", e);
			return null;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioProductoDto> obtenerProductosXIdCliente(Long idCliente) {
		List<PrestadoraServicioProductoDto> listCatProducto =  new ArrayList<PrestadoraServicioProductoDto>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select id_prest_serv_producto ");
			sb.append(" from cliente_servicio ");
			sb.append(" where id_cliente = ?");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public PrestadoraServicioProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PrestadoraServicioProductoDto producto = new PrestadoraServicioProductoDto();
					producto.setIdPrestadoraServicioProducto(rs.getLong("id_prest_serv_producto"));
					listCatProducto.add(producto);
					return producto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerProductosXIdCliente ", e);
			return Collections.emptyList();
		}
	}
	

}
