package mx.com.consolida.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.ClientePrestadoraServicioDao;
import mx.com.consolida.crm.dto.ClientePrestadoraServicioDto;
import mx.com.consolida.crm.service.interfaz.ClientePrestadoraServicioBO;
import mx.com.consolida.entity.crm.Cliente;

@Service
public class ClientePrestadoraServicioBOimpl  implements ClientePrestadoraServicioBO{
	
	@Autowired
	private ClientePrestadoraServicioDao clientePrestadoraServicioDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientePrestadoraServicioBOimpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<ClientePrestadoraServicioDto> listaClientesPrestadoras(Long idCliente) {
		try {
			
			return clientePrestadoraServicioDao.listaClientesPrestadoras(idCliente);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesPrestadoras ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Long getidFondoByIdCliente(Long idCliente) {
		try {
			
			return clientePrestadoraServicioDao.getidFondoByIdCliente(idCliente);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getidFondoByIdCliente ", e);
			return null;
		}
	}

}
