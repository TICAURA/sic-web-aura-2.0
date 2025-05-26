package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.entity.crm.ClienteServicio;

public interface ClienteProductoDao extends mx.com.consolida.dao.DAO<ClienteServicio, Long>{

	ClienteServicio obtenerClienteProductoXIdCatProducto(Long idCatProducto, Long idCliente);

	List<CatProductoDto> obtenerProductosXIdCliente(Long idCliente);


}
