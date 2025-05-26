package mx.com.consolida.ppp.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.CatFolio;
import mx.com.consolida.ppp.dto.CatFolioDto;
import mx.com.consolida.ppp.dto.CatSerieDto;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class CatFolioDaoImpl extends GenericDAO<CatFolio, Long> implements CatFolioDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatFolioDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CatFolioDto> listaCatFolio() {
		try {
			
			StringBuilder sb = new StringBuilder();						
			sb.append(" select cf.id_cat_folio, cf.numero_folio ");
			sb.append(" , cs.id_cat_serie, cs.clave, cs.color ,cs.descripcion ");
			sb.append(" from sin.cat_folio cf, sin.cat_serie cs "); 
			sb.append(" where  cs.id_cat_serie = cf.id_cat_serie ");
			sb.append(" and cf.ind_estatus = 1 ");

			return jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatFolioDto catFolioDto = new CatFolioDto();
					catFolioDto.setIdCatFolio(rs.getLong("id_cat_serie"));
					catFolioDto.setNumeroFolio(rs.getString("numero_folio"));
					return catFolioDto;	
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCatFolio ", e);
			return Collections.emptyList();
		}
	}

}
