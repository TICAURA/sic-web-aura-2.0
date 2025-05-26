package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteServicio;

public interface ClienteServicioDao extends DAO<ClienteServicio,Long>{

	ClienteServicio obtenerClienteServicioXIdPrestServProducto(Long idPrestadoraServicioProducto, Long idCliente);

	List<PrestadoraServicioProductoDto> obtenerProductosXIdCliente(Long idCliente);
	
}
