package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteProducto;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteProductoDto;

public interface ClienteProductoGralDao extends DAO<ClienteProducto,Long> {
	
	List<ClienteProductoDto> getProductosByidCliente(Long idcliente);

}
