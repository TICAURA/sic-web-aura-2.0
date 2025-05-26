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

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.comunes.dto.AccionistaDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionista;

@Repository
public class PrestadoraServicioAccionistaDaoImpl extends GenericDAO<PrestadoraServicioAccionista, Long> implements PrestadoraServicioAccionistaDao{
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioAccionistaDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<AccionistaDto> getListAccionistaByIdPrestadoraServicio(Long idPrestadora) {
		
		try {
			
			StringBuilder sb = new StringBuilder();			
			sb.append(" SELECT psal.id_prestadora_servicio_accionista,  ps.id_prestadora_servicio, psal.porcentaje_accion,   "); 
			sb.append(" per.id_persona, per.nombre, per.apellido_paterno,  per.apellido_materno, per.rfc,  per.fecha_nacimiento,   ");
			sb.append(" d.id_domicilio, d.id_entidad_federativa, d.id_municipio, d.calle, d.numero_exterior, d.numero_interior, d.colonia, d.codigo_postal   "); 
			sb.append(" FROM sin.prestadora_servicio ps  ");
			sb.append(" LEFT JOIN sin.prestadora_servicio_accionista psal ON  ");
			sb.append(" ps.id_prestadora_servicio = psal.id_prestadora_servicio  ");
			sb.append(" LEFT JOIN sin.persona per ON  ");
			sb.append(" per.id_persona = psal.id_persona  ");
			sb.append(" LEFT JOIN sin.prestadora_servicio_accionista_domicilio psad ON  ");
			sb.append(" psal.id_prestadora_servicio_accionista = psad.id_prestadora_servicio_accionista  ");
			sb.append(" LEFT JOIN sin.domicilio d ON  ");
			sb.append(" d.id_domicilio = psad.id_domicilio  ");
			sb.append(" WHERE ps.id_prestadora_servicio = ?  ");
			sb.append(" and psal.ind_estatus = 1  ");
			sb.append(" and per.ind_estatus = 1;  ");
			
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					AccionistaDto accionista = new AccionistaDto();
					accionista.setIdGenericoAccionista(rs.getLong("id_prestadora_servicio_accionista"));
					accionista.setIdPersona(rs.getLong("id_persona"));
					accionista.setNombre(rs.getString("nombre"));
					accionista.setApellidoPaterno(rs.getString("apellido_paterno"));
					accionista.setApellidoMaterno(rs.getString("apellido_materno"));
					accionista.setRfc(rs.getString("rfc"));
					accionista.setFechaNacimiento(rs.getDate("fecha_nacimiento"));
					accionista.setPorcentajeAccion(rs.getBigDecimal("porcentaje_accion"));
					accionista.setPrestadoraServicio(new PrestadoraServicioDto(rs.getLong("id_prestadora_servicio")));
					
				
					accionista.setDomicilioDto(new DomicilioDto());
					accionista.getDomicilioDto().setIdDomicilio(rs.getLong("id_domicilio"));
					accionista.getDomicilioDto().setIdEntidadFederativa(rs.getLong("id_entidad_federativa"));
					accionista.getDomicilioDto().setCatMunicipios(new CatGeneralDto());
					accionista.getDomicilioDto().getCatMunicipios().setIdCatGeneral(rs.getLong("id_municipio"));
					accionista.getDomicilioDto().setCalle(rs.getString("calle"));
					accionista.getDomicilioDto().setNumeroExterior(rs.getString("numero_exterior"));
					accionista.getDomicilioDto().setNumeroInterior(rs.getString("numero_interior"));
					accionista.getDomicilioDto().setCodigoPostal(rs.getString("codigo_postal"));
					accionista.getDomicilioDto().setColonia(rs.getString("colonia"));
					

					
					
					return accionista;	
				}
			});
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListApoderadoLegalByIdPrestadoraServicio ", e);
			return Collections.emptyList();
		}
	}

}
