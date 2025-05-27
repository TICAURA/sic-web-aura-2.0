package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClientePrestadoraServicioDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClientePrestadoraServicio;

public interface ClientePrestadoraServicioDao extends DAO<ClientePrestadoraServicio,Long>{
	
	List<ClientePrestadoraServicioDto> listaClientesPrestadoras(Long idCliente);

	Long getidFondoByIdCliente(Long idCliente);
	
	Long getidFondoPrestadoraByIdCliente(Long idCliente) ;
	
}
