package mx.com.consolida.service.interfaz;

import java.util.List;

import mx.com.consolida.dto.ClienteTempDto;

public interface ClienteFinalBO {

	void guardar(ClienteTempDto cliente);
	void actualizar(ClienteTempDto cliente);
	public List<ClienteTempDto> obtenerClientes();
	public ClienteTempDto obtenerClienteById(Integer idClienteTemp);
}