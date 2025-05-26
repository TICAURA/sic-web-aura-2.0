package mx.com.consolida.crm.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.ClienteNoministaDao;
import mx.com.consolida.crm.dto.ClienteNoministaDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.crm.service.interfaz.ClienteNoministaBO;
import mx.com.consolida.entity.crm.ClienteNominista;

@Service
public class ClienteNoministaBOImpl implements ClienteNoministaBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteNoministaBOImpl.class);
	
	@Autowired
	private ClienteNoministaDao clienteNoministaDao;

	@Override
	@Transactional(readOnly = true)
	public List<NoministaDto> lsitaNoministasByCLiente(Long idCliente) {
		
		try {
			List<NoministaDto> listaNoministas = clienteNoministaDao.listaNoministasByCliente(idCliente);
			return listaNoministas;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en lsitaNoministasByCLiente ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteNoministaDto getClienteNoministaByidClienteIdNominista(Long idCliente, Long idNominista) {
		try {
			
			ClienteNoministaDto clienteNoministaDto = new ClienteNoministaDto();
			
			ClienteNominista clienteNominista = clienteNoministaDao.existeNomistaEnCliente(idCliente, idNominista);
			if(clienteNominista!=null && clienteNominista.getIdClienteNominista()!=null) {
				clienteNoministaDto.setIdClienteNominista(clienteNominista.getIdClienteNominista());
				clienteNoministaDto.setIdCliente(clienteNominista.getCliente().getIdCliente());
				clienteNoministaDto.setIdUsuarioNominista(clienteNominista.getUsuarioNominista().getIdUsuario());
				return clienteNoministaDto;
				
			}else {
				LOGGER.info("EetClienteNoministaByidClienteIdNominista el objeto ClienteNominista viene null");
				return new ClienteNoministaDto();
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getClienteNoministaByidClienteIdNominista ", e);
			return new ClienteNoministaDto();
		}
	}

}
