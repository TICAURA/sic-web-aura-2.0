package mx.com.consolida.crm.dao.interfaz;

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

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegal;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PrestadoraServicioRepresentanteLegalDaoImpl extends GenericDAO<PrestadoraServicioRepresentanteLegal, Long> implements PrestadoraServicioRepresentanteLegalDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioRegistroPatronalDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@Override
	@Transactional(readOnly = true)
	public List<RepresentanteLegalDto> getListRepresentanteLegalByIdPrestadoraServicio(Long idPrestadora) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select psrl.id_prestadora_servicio_representante_legal,  ps.id_prestadora_servicio, ");
			sb.append(" per.id_persona, per.nombre, per.apellido_paterno,  per.apellido_materno, per.rfc, per.curp, psrl.contrasena_certificado ");
			sb.append(" from sin.prestadora_servicio_representante_legal psrl,sin.prestadora_servicio ps, sin.persona per ");
			sb.append(" where per.id_persona = psrl.id_persona ");
			sb.append(" and ps.id_prestadora_servicio = psrl.id_prestadora_servicio ");
			sb.append(" and ps.id_prestadora_servicio = ? ");
			sb.append(" and per.ind_estatus = 1 ");
			sb.append(" and psrl.ind_estatus = 1 ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					RepresentanteLegalDto representante = new RepresentanteLegalDto();
					representante.setIdGenericoRepresentanteLegal(rs.getLong("id_prestadora_servicio_representante_legal"));
					representante.setIdPersona(rs.getLong("id_persona"));
					representante.setNombre(rs.getString("nombre"));
					representante.setApellidoPaterno(rs.getString("apellido_paterno"));
					representante.setApellidoMaterno(rs.getString("apellido_materno"));
					representante.setRfc(rs.getString("rfc"));
					representante.setCurp(rs.getString("curp"));
					representante.setContrasenaCertificado(rs.getString("contrasena_certificado"));
					representante.setPrestadoraServicioDto(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
					
					return representante;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRepresentanteLegalByIdPrestadoraServicio ", e);
			return Collections.emptyList();
		}
	}


}
