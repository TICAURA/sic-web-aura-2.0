package mx.com.consolida.dao.interfaz;

import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.entity.ClienteTempBitacoraSolicitudes;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteTempBitacoraSolicitudesDao extends mx.com.consolida.dao.DAO<ClienteTempBitacoraSolicitudes, Long>{

	void guardarSolicitudCotizacion(ClienteTempBitacoraSolicitudesDto solicitarCotizacion, UsuarioAplicativo usuarioAplicativo);

	ClienteTempBitacoraSolicitudes obtenerClienteXIdCotizacion(Long idCotizacion);

	ClienteTempBitacoraSolicitudes obtenerClienteXIdCliente(Long idClienteTemp);


}
