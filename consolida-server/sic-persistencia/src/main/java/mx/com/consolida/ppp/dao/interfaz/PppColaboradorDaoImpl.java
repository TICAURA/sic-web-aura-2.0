package mx.com.consolida.ppp.dao.interfaz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppColaborador;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;

@Repository
public class PppColaboradorDaoImpl extends GenericDAO<PppColaborador, Long> implements PppColaboradorDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppColaboradorDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public void eliminarColaboradores(Long idPppNomina) {
		Session session = sessionFactory.getCurrentSession();
		
		Query queryClabe = session
				.createQuery("delete from PppColaboradorClabeInterbancaria pcci where pcci.pppColaborador.idPppColaborador in "
						+ " ((select pc.idPppColaborador from PppColaborador pc where pc.pppNomina.idPppNomina = :idPppNomina))");
		queryClabe.setParameter("idPppNomina", idPppNomina);
		queryClabe.executeUpdate();
		 
		Query queryEstatus = session
					.createQuery("delete from PppColaboradorEstatus pce where pce.pppColaborador.idPppColaborador in "
							+ " ((select pc.idPppColaborador from PppColaborador pc where pc.pppNomina.idPppNomina = :idPppNomina))");
		queryEstatus.setParameter("idPppNomina", idPppNomina);
		queryEstatus.executeUpdate();
			 
		Query queryColaborador = session
						.createQuery("delete from PppColaborador pc where pc.pppNomina.idPppNomina = :idPppNomina");
		queryColaborador.setParameter("idPppNomina", idPppNomina);
		queryColaborador.executeUpdate();
		
	}
	

	@Override
	@Transactional(readOnly = true )
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EmpleadoDTO> obtenercolaboradoresParaDispersionStpByidNomina(Long idPppNomina) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select col.id_ppp_colaborador, col.id_ppp_nomina, col.rfc, ");
			sb.append(" cec.id_cat_estatus_colaborador, cec.clave,cec.descripcion  ");
			sb.append(" from sin.ppp_colaborador col ");
			sb.append(" inner JOIN sin.ppp_colaborador_estatus ce on ce.id_ppp_colaborador = col.id_ppp_colaborador ");
			sb.append(" left join sin.cat_estatus_colaborador cec on cec.id_cat_estatus_colaborador = ce.id_cat_estatus_colaborador ");
			sb.append(" where cec.id_cat_estatus_colaborador = ? ");
			sb.append(" and col.id_ppp_nomina = ?  ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA.getId(), idPppNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					EmpleadoDTO colaborador =  new EmpleadoDTO();
					colaborador.setIdPppColaborador (rs.getLong("id_ppp_colaborador"));
					colaborador.setIdNomina(rs.getLong("id_ppp_nomina"));
					colaborador.setRfc(rs.getString("rfc"));
					CatGeneralDto estatusColaborador = new CatGeneralDto();
					estatusColaborador.setIdCatGeneral(rs.getLong("id_cat_estatus_colaborador"));
					estatusColaborador.setClave(rs.getString("clave"));
					estatusColaborador.setDescripcion(rs.getString("descripcion"));
					colaborador.setEstatusColaborador(estatusColaborador);

					return colaborador;
				}
 
			});

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenercolaboradoresParaDispersionStpByidNomina ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional(readOnly = true)
	public Long totalColaboradoresByIdPppNomina(Long idPppNomina, Long idCatEstatusColaborador, Long idCatEstatusColaborador2, Long idCatEstatusColaborador3) {
			try {
				
				StringBuilder sb = new StringBuilder();					
				sb.append(" select count(pcol.id_ppp_colaborador) as total "); 
				sb.append(" from sin.ppp_colaborador pcol, sin.ppp_colaborador_estatus pce "); 
				sb.append(" where pcol.id_ppp_nomina = ? "); 
				sb.append(" and pce.id_ppp_colaborador = pcol.id_ppp_colaborador "); 
				sb.append(" and pce.id_ppp_colaborador_estatus = (select max(id_ppp_colaborador_estatus)  "); 
				sb.append(" 									from sin.ppp_colaborador_estatus  "); 
				
				if(idCatEstatusColaborador2 !=null && idCatEstatusColaborador3 !=null) {
					sb.append("                                 where id_cat_estatus_colaborador in (?, ?, ?)  "); 
				}else {
					sb.append("                                 where id_cat_estatus_colaborador in (?)  "); 
				}
				
				sb.append("                                     and id_ppp_colaborador = pcol.id_ppp_colaborador "); 
				sb.append("                                     and ind_estatus = 1) "); 
				sb.append(" and pcol.ind_estatus = 1 "); 
				sb.append(" and pce.ind_estatus = 1 ");

				
				Object[] objeto = null;
				if(idCatEstatusColaborador2 !=null) {
					objeto = new Object[] {idPppNomina, idCatEstatusColaborador, idCatEstatusColaborador2, idCatEstatusColaborador3};
				}else {
					 objeto = new Object[] {idPppNomina, idCatEstatusColaborador};
				}
				

				List<Long> total = jdbcTemplate.query(sb.toString(),objeto, new RowMapper() {
					public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
						Long id = rs.getLong("total");
						
						return id;	
					}
				});
				
				if (total != null && !total.isEmpty()) {
					return total.get(0);
				} else {
					return null;
				}

				
			}catch (Exception e) {
				LOGGER.error("Ocurrio un error en totalColaboradoresByIdPppNomina ", e);
			}
			return null;
	}

	
	@Override
	public void eliminarColaborador(Long idPppColaborador) {
		Session session = sessionFactory.getCurrentSession();
		
		Query queryClabe = session
				.createQuery("delete from PppColaboradorClabeInterbancaria pcci where pcci.pppColaborador.idPppColaborador in "
						+ " (:idPppColaborador)");
		queryClabe.setParameter("idPppColaborador", idPppColaborador);
		queryClabe.executeUpdate();
		 
		Query queryEstatus = session
					.createQuery("delete from PppColaboradorEstatus pce where pce.pppColaborador.idPppColaborador in "
							+ " (:idPppColaborador)");
		queryEstatus.setParameter("idPppColaborador", idPppColaborador);
		queryEstatus.executeUpdate();
			 
		Query queryColaborador = session
						.createQuery("delete from PppColaborador pc where pc.idPppColaborador = :idPppColaborador");
		queryColaborador.setParameter("idPppColaborador", idPppColaborador);
		queryColaborador.executeUpdate();
		
	}

}
