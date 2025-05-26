package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.entity.crm.Cliente;

public interface ClienteFinalDao extends mx.com.consolida.dao.DAO<Cliente,Integer>{
	public List<ClienteTempDto> obtenerClientes();
	public ClienteTempDto obtenerClienteById(Integer idCliente);
	public List<CotizacionDto> obtenerCotizaciones(Integer idCliente);
}
