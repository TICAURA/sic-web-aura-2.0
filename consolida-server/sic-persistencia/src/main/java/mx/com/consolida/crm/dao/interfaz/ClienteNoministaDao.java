package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.ClienteNominista;

public interface ClienteNoministaDao extends DAO<ClienteNominista,Long>{
	
	List<NoministaDto> listaNoministasByCliente(Long idCliente);
	ClienteNominista existeNomistaEnCliente(Long idCliente, Long idNominista);
	List<ClienteNominista> getListNomistaClienteByCliente(Long idCliente);

}
