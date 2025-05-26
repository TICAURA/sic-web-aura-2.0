package mx.com.consolida.ppp.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.CatSerie;
import mx.com.consolida.ppp.dto.CatSerieDto;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class CatSerieDaoImpl extends GenericDAO<CatSerie, Long> implements CatSerieDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatSerieDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CatSerieDto> listaCatSerie() {
		try {
			
			StringBuilder sb = new StringBuilder();						
			sb.append(" select cs.id_cat_serie,cs.id_tipo_comprobante, cg.descripcion as desc_tipo_comprobante, cs.nombre_serie,cs.folio_inicial,  cs.usuario_alta ");
			sb.append(" , cel.id_celula, cel.nombre as desc_celula,cs.fecha_inicio_vigencia, cs.fecha_fin_vigencia, cs.ind_estatus, cs.id_estatus ");
			sb.append(" from sin.cat_serie cs, sin.cat_maestro cm, sin.cat_general cg, sin.celula cel   ");
			sb.append(" where cg.id_cat_maestro = cm.id_cat_maestro ");
			sb.append(" and cg.id_cat_general = cs.id_tipo_comprobante ");
			sb.append(" and cel.id_celula = cs.id_celula and cs.ind_estatus= '1'");
			sb.append("  order by cel.id_celula, cs.id_tipo_comprobante ");

			
			List<CatSerieDto> list = jdbcTemplate.query(sb.toString(), new Object[] {}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					CatSerieDto catSerieDto = new CatSerieDto();
					catSerieDto.setIdCatSerie(rs.getLong("id_cat_serie"));
					catSerieDto.setCatTipoComprobante(new CatGeneralDto(rs.getLong("id_tipo_comprobante"), rs.getString("desc_tipo_comprobante")));
					catSerieDto.setCatCelula(new CatGeneralDto(rs.getLong("id_celula"), rs.getString("desc_celula")));
					catSerieDto.setFolioInicial(rs.getLong("folio_inicial"));
					catSerieDto.setNombreSerie(rs.getString("nombre_serie"));
					catSerieDto.setUsuarioAlta(new UsuarioDTO(rs.getLong("usuario_alta")));
					catSerieDto.setFechaInicioVigencia(rs.getDate("fecha_inicio_vigencia"));
					catSerieDto.setFechaFinVigencia(rs.getDate("fecha_fin_vigencia"));
					catSerieDto.setFechaInicioVigenciaFormato(rs.getDate("fecha_inicio_vigencia")==null?"":
						Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_inicio_vigencia")));
					catSerieDto.setFechaFinVigenciaFormato(rs.getDate("fecha_fin_vigencia")==null?"":
						Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_fin_vigencia")));
					catSerieDto.setIndEstatus(rs.getString("ind_estatus"));
					catSerieDto.setIdEstatusSerie(rs.getLong("id_estatus"));
					return catSerieDto;	
				}
			});
			
			return list;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarProspectosAutorizar ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public Long maxConsecutivo() {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select max(serie.folioInicial) from CatSerie serie ");
			
			if((Long) query.uniqueResult()!=null) {				
				return (Long) query.uniqueResult();
			}else {
				return (long) 0;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en maxConsecutivoByIdNomina ", e);
			return null;
		}		
	}

}
