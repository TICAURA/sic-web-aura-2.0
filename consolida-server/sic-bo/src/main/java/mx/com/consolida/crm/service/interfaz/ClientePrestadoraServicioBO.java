package mx.com.consolida.crm.service.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClientePrestadoraServicioDto;

public interface ClientePrestadoraServicioBO {

	List<ClientePrestadoraServicioDto> listaClientesPrestadoras(Long idCliente);
	
	Long getidFondoByIdCliente(Long idCliente);
	
}
