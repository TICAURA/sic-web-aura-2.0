package mx.com.consolida.ppp.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.ppp.dto.CatFolioDto;
import mx.com.consolida.ppp.dto.HistoricoNominaDto;

@Repository
public class PppNominaEstatusDaoImpl extends GenericDAO<PppNominaEstatus, Long> implements PppNominaEstatusDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppNominaEstatusDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	public List<PppNominaEstatus> getPppNominaEstatusActivo(Long idNomina) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select estatus from PppNominaEstatus estatus where estatus.indEstatus = 1 and estatus.pppNomina.idPppNomina = :idNomina");
			query.setParameter("idNomina", idNomina);

			return (List<PppNominaEstatus>) query.list();
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPppNominaEstatusActivo ", e);
			return null;
		}

	}
	

		
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HistoricoNominaDto> getHistoricoByIdPppNomina(Long idPppNomina) {
		try {
			
			StringBuilder sb = new StringBuilder();						
			sb.append(" select pppne.id_ppp_nomina_estatus, pppne.id_ppp_nomina, pppne.observacion, pppne.fecha_alta, ");
			sb.append(" cen.descripcion as desc_cat_estatus_nomina, per.nombre, per.apellido_paterno, per.apellido_materno  ");
			sb.append(" from sin.ppp_nomina_estatus pppne, sin.cat_estatus_nomina cen, sin.persona per, ");
			sb.append(" sin.usuario usu ");
			sb.append(" where cen.id_cat_estatus_nomina = pppne.id_cat_estatus_nomina ");
			sb.append(" and pppne.usuario_alta = usu.id_usuario ");
			sb.append(" and per.id_persona = usu.id_persona ");
			sb.append(" and pppne.id_ppp_nomina = ? ");
			sb.append(" order by pppne.id_ppp_nomina_estatus ");

			return jdbcTemplate.query(sb.toString(), new Object[] {idPppNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					HistoricoNominaDto historico = new HistoricoNominaDto();
					historico.setIdNominaPpp(rs.getLong("id_ppp_nomina"));
					if(rs.getString("apellido_materno")!=null) {
						historico.setNombreUsuarioMovimiento(rs.getString("nombre")+" "+rs.getString("apellido_paterno")+" "+rs.getString("apellido_materno"));
					}else {
						historico.setNombreUsuarioMovimiento(rs.getString("nombre")+" "+rs.getString("apellido_paterno"));
					}
					
					historico.setFechaMovimiento(rs.getDate("fecha_alta"));
					historico.setFechaMovimientoFormato(Utilerias.convirteDateToStringMesEnLetra(rs.getDate("fecha_alta")));
					historico.setObservacion(rs.getString("observacion"));
					historico.setDescEstatusNomina(rs.getString("desc_cat_estatus_nomina"));
					return historico;
				}
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getHistoricoByIdPppNomina ", e);
			return Collections.emptyList();
		}
	}

	@Transactional
	@Override
	public List<CatGeneralDto> obtenerCatEstatusNomina() {
		try {
			String sql = "select id_cat_estatus_nomina as idCatGeneral, clave, descripcion, ind_estatus as indEstatus "
					+ " from sin.cat_estatus_nomina where id_cat_estatus_nomina in (7, 13, 17, 18, 19, 20, 21, 22, 23, 25, 24)";
			@SuppressWarnings("unchecked")
			List<CatGeneralDto> data = (List<CatGeneralDto>) (List<?>) callNativeQuery(sql, "obtenerGeneral", null);
			return data;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatEstatusInicialCotizaciones ", e);
			return Collections.emptyList();
		}
	}
}
