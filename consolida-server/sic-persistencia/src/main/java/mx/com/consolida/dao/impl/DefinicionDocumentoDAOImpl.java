package mx.com.consolida.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dao.admin.DefinicionDocumentoDAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.entity.DefinicionDocumento;

@Repository
public class DefinicionDocumentoDAOImpl  extends GenericDAO<DefinicionDocumento, Long> implements DefinicionDocumentoDAO{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DefinicionDocumentoDAOImpl.class);
	
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CanalVentaDto> listarTodasCanalesVenta() {

		try {
			// Se buscan las celulas registradas para los administradores de la celula
			StringBuilder sb = new StringBuilder();

			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					CanalVentaDto canalVenta = new CanalVentaDto();


					return canalVenta;

				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al en listarTodasCelularRegistradas ", e);
			return Collections.emptyList();
		}

	}
	
	
}
