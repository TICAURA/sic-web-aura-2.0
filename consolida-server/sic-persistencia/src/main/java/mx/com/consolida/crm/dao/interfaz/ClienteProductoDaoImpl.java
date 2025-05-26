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

import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteServicio;

@Repository
public class ClienteProductoDaoImpl extends GenericDAO<ClienteServicio, Long> implements ClienteProductoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteProductoDaoImpl.class);


	@Override
	@Transactional(readOnly = true)
	public ClienteServicio obtenerClienteProductoXIdCatProducto(Long idCatProducto, Long idCliente) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select prod from ClienteProducto prod where prod.catProducto.idCatProducto = :idCatProducto "
					+ " and prod.cliente.idCliente = :idCliente");
			query.setParameter("idCatProducto", idCatProducto);
			query.setParameter("idCliente", idCliente);

			return (ClienteServicio) query.uniqueResult();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeNomistaEnCliente", e);
			return null;
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly = true)
	public List<CatProductoDto> obtenerProductosXIdCliente(Long idCliente) {
		List<CatProductoDto> listCatProducto =  new ArrayList<CatProductoDto>();
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select id_producto ");
			sb.append(" from cliente_producto");
			sb.append(" where id_cliente = ?");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public CatProductoDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatProductoDto producto = new CatProductoDto();
					producto.setIdCatProducto(rs.getLong("id_producto"));
					
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
