package mx.com.consolida.crm.dao.interfaz;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClienteMedioContactoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteMedioContacto;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class ClienteMedioContactoDaoImpl extends GenericDAO<ClienteMedioContacto, Long> implements ClienteMedioContactoDao{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteMedioContactoDaoImpl.class);
	
	@Override
	public ClienteMedioContactoDto convertirMedioContactoADto(ClienteMedioContacto clienteMedioContacto) {
		ClienteMedioContactoDto cliente = new ClienteMedioContactoDto();
		
		cliente.setApellidoMaterno(clienteMedioContacto.getApellidoMaterno());
		cliente.setApellidoPaterno(clienteMedioContacto.getApellidoPaterno());
		cliente.setCliente(new ClienteDto());
		cliente.getCliente().setIdCliente(clienteMedioContacto.getCliente().getIdCliente());
		cliente.setCorreo(clienteMedioContacto.getCorreo());
		cliente.setFechaAlta(clienteMedioContacto.getFechaAlta());
		cliente.setFechaModificacion(clienteMedioContacto.getFechaModificacion());
		cliente.setIdClienteMedioContacto(clienteMedioContacto.getIdClienteMedioContacto());
		cliente.setIndEstatus(clienteMedioContacto.getIndEstatus());
		cliente.setNombre(clienteMedioContacto.getNombre());
		cliente.setTelefono(clienteMedioContacto.getTelefono());
		cliente.setUsuarioAlta(new UsuarioDTO());
		cliente.getUsuarioAlta().setIdUsuario(clienteMedioContacto.getUsuarioAlta().getIdUsuario());
		if(clienteMedioContacto.getUsuarioModificacion() != null && clienteMedioContacto.getUsuarioModificacion().getIdUsuario() != null) {
			cliente.setUsuarioModificacion(new UsuarioDTO());
			cliente.getUsuarioModificacion().setIdUsuario(clienteMedioContacto.getUsuarioModificacion().getIdUsuario());
		}
		
		if(clienteMedioContacto.getFechaNacimiento() != null) {
			cliente.setFechaNacimiento(clienteMedioContacto.getFechaNacimiento());
		}
		
		if(clienteMedioContacto.getEsCeo() != null) {
			cliente.setEsCeo(clienteMedioContacto.getEsCeo());
		}

		return cliente;
	}
	

}
