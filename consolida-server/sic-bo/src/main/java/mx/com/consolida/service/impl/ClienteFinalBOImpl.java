package mx.com.consolida.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.dao.interfaz.ClienteFinalDao;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.service.interfaz.ClienteFinalBO;


@Service
public class ClienteFinalBOImpl implements ClienteFinalBO {

	@Autowired
	private ClienteFinalDao clienteDao;
	
	@Transactional
	public void guardar(ClienteTempDto cliente) {
		Cliente entity = new Cliente();
		ModelMapper mapper = new ModelMapper();
//		if(cliente.getNombre() != null) {
//			String nombreCompleto = cliente.getNombre() + " "+ cliente.getApellidoPaterno()==null ? "":cliente.getApellidoPaterno() + " "+ cliente.getApellidoMaterno()==null ? "":cliente.getApellidoMaterno();
//			Cliente.setRazonSocial(nombreCompleto);
//		}else {
		entity = mapper.map(cliente, Cliente.class);
//		entity.setIdEstatus(1L);
		
		Usuario usuarioAlta = new Usuario();
		usuarioAlta.setIdUsuario(1L);
		entity.setUsuarioAlta(usuarioAlta);
		entity.setFechaAlta(new Date());
		clienteDao.create(entity);
		
	}
	
	@Transactional
	public void actualizar(ClienteTempDto cliente) {
		Cliente entity = new Cliente();
		ModelMapper mapper = new ModelMapper();
		entity = mapper.map(cliente, Cliente.class);
//		entity.setIdEstatus(1L);
		Usuario usuarioAlta = new Usuario();
		usuarioAlta.setIdUsuario(1L);
		entity.setUsuarioAlta(usuarioAlta);
		entity.setFechaAlta(new Date());
		clienteDao.update(entity);
		
	}
	
	
	
	public List<ClienteTempDto> obtenerClientes() {
		return clienteDao.obtenerClientes();
	}
	
	public ClienteTempDto obtenerClienteById(Integer idClienteTemp) {
		ClienteTempDto cliente = new ClienteTempDto();
		cliente = clienteDao.obtenerClienteById(idClienteTemp);
		cliente.setCotizaciones(clienteDao.obtenerCotizaciones(idClienteTemp));
		return cliente;
	}
	
}
