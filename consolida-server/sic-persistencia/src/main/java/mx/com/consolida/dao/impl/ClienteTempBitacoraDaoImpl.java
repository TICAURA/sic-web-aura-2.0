package mx.com.consolida.dao.impl;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.interfaz.CatTipoEventoDao;
import mx.com.consolida.dao.interfaz.ClienteTempBitacoraDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.entity.CatTipoEvento;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.ClienteTempBitacora;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;


@Repository
public class ClienteTempBitacoraDaoImpl extends GenericDAO<ClienteTempBitacora, Long> implements ClienteTempBitacoraDao{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Autowired
	private CatTipoEventoDao catTipoEventoDao;
	
	@Autowired
	private ClienteTempDao clienteDao;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteTempBitacoraDaoImpl.class);
	
	@Transactional
	public void guardarBitacora(ClienteTempBitacoraDto bitacora, UsuarioAplicativo usuarioAplicativo) {
		CatTipoEvento catTipoEvento = catTipoEventoDao.read(bitacora.getTipoEvento().getIdCatTipoEvento().longValue());
		ClienteTemp clienteTemp = clienteDao.read(bitacora.getIdClienteTemp());
		ClienteTempBitacora entity = new ClienteTempBitacora();
//		entity = conv().map(bitacora,ClienteTempBitacora.class);
		entity = new ClienteTempBitacora(bitacora);
		entity.setClienteTemp(clienteTemp);
		entity.setCatTipoEvento(catTipoEvento);
		entity.setFechaAlta(new Date());
		entity.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
		entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		entity.setIdClienteTempBitacora(create(entity));		
	}

	
}

