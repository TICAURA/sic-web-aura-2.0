package mx.com.consolida.crm.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClienteProductoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteProducto;

@Repository
public class ClienteProductoGralDaoImpl extends GenericDAO<ClienteProducto, Long> implements  ClienteProductoGralDao {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteProductoGralDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ClienteProductoGralDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteProductoDto> getProductosByidCliente(Long idCliente) {
try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select  p.id_producto, p.descripcion, p.clave,ifnull( cp.id_cliente_producto,0) id_producto_cliente   from ");
			sb.append(" (select id_cat_general id_producto, clave, descripcion  from cat_general cg2  "); 
			sb.append(" where cg2.id_cat_maestro in(30, 57) and ind_estatus =1) p  "); 
			sb.append(" left join   "); 
			sb.append(" ( select id_cliente_producto, id_producto  "); 
			sb.append(" from   cliente_producto cp join cat_general cg  on cp.id_producto =cg.id_cat_general "); 
			sb.append(" where id_cliente =?  and cp.ind_estatus='1') "); 
			sb.append(" cp on p.id_producto= cp.cp.id_producto ; ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					ClienteProductoDto producto = new ClienteProductoDto();
					producto.setClave(rs.getString("clave"));
					producto.setDescripcion(rs.getString("descripcion"));
					producto.setIdCliente(idCliente);
					producto.setIdProducto(rs.getLong("id_producto"));
					producto.setIdClienteProducto(rs.getLong("id_producto_cliente"));
					producto.setEstatus(rs.getLong("id_producto_cliente")==0? Boolean.FALSE: Boolean.TRUE);
					
					return producto;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getProductosByidCliente", e);
			return Collections.emptyList();
		}
	}

}
