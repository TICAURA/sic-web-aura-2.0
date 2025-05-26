package mx.com.consolida.crm.service.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteProductoDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteProductoBo {
	
	List<ClienteProductoDto> getProductosByIdCliente(Long idCliente);
	
	
	
	void guardarProducto(ClienteProductoDto producto,UsuarioAplicativo usuarioAplicativo);
	
}
