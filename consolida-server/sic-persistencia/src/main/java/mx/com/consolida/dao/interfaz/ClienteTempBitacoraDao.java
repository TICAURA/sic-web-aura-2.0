package mx.com.consolida.dao.interfaz;

import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.entity.ClienteTempBitacora;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteTempBitacoraDao extends mx.com.consolida.dao.DAO<ClienteTempBitacora,Long>{

	void guardarBitacora(ClienteTempBitacoraDto bitacora, UsuarioAplicativo usuarioAplicativo);

}
