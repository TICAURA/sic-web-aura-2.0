package mx.com.consolida.dao.interfaz;

import mx.com.consolida.entity.ClienteTempEstatus;

public interface ClienteTempEstatusDao extends mx.com.consolida.dao.DAO<ClienteTempEstatus,Long>{

	void apagarEstatusLogicoByIdCliente(Long idClienteTemp);
	Long agregarClienteTempEstatus(ClienteTempEstatus cl);
	
}
