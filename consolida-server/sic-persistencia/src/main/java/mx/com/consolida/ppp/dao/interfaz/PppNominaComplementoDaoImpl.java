package mx.com.consolida.ppp.dao.interfaz;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNominaComplemento;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;

@Repository
public class PppNominaComplementoDaoImpl  extends GenericDAO<PppNominaComplemento, Long> implements PppNominaComplementoDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PppNominaComplementoDaoImpl.class);
	
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NominaComplementoDto getDatosNomComplByIdNomCompl(String claveNomina) {
		try {
			
			
			StringBuilder sb = new StringBuilder();					
			sb.append(" SELECT  compl.id_ppp_nomina_complemento, compl.id_ppp_nomina, compl.fecha_facturacion, "); 
			sb.append(" compl.fecha_timbrado, compl.fecha_dispersion, compl.requiere_financiamiento, compl.monto_financiamiento, compl.observaciones, "); 
			sb.append(" compl.id_definicion_documento, compl.id_cms, compl.nombre_archivo, "); 
			sb.append(" pppn.id_ppp_nomina, pppn.clave, pppn.fecha_inicio_nomina, pppn.fecha_fin_nomina "); 
			sb.append(" FROM sin.ppp_nomina_complemento compl, sin.ppp_nomina pppn "); 
			sb.append(" where pppn.id_ppp_nomina = compl.id_ppp_nomina  "); 
			sb.append(" and compl.ind_estatus = 1 "); 
			sb.append(" and pppn.clave = ? ");

			
			List<NominaComplementoDto> nomina = jdbcTemplate.query(sb.toString(), new Object[] {claveNomina}, new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					NominaComplementoDto nominaComplementoDto = new NominaComplementoDto();
					nominaComplementoDto.setIdNominaComplemento(rs.getLong("id_ppp_nomina_complemento"));
					nominaComplementoDto.setIdCMS(rs.getLong("id_cms") !=0  ? rs.getLong("id_cms") : null);
					nominaComplementoDto.setNombreArchivo(rs.getString("nombre_archivo")!=null ? rs.getString("nombre_archivo") : null );
					
					DocumentoDTO documento = new DocumentoDTO();
					documento.setNombreArchivo(rs.getString("nombre_archivo")!=null ? rs.getString("nombre_archivo") : null );
					nominaComplementoDto.setDocumentoNuevo(documento);		
					
					NominaDto nominaDto = new NominaDto();
					nominaDto.setIdNomina(rs.getLong("id_ppp_nomina"));
					nominaDto.setClaveNomina(rs.getString("clave"));
					nominaDto.setFechaInicioNomina(rs.getDate("fecha_inicio_nomina"));
					nominaDto.setFechaFinNomina(rs.getDate("fecha_fin_nomina"));
					nominaComplementoDto.setNominaDto(nominaDto);
					
					
					nominaComplementoDto.setNominaDto(new NominaDto(rs.getLong("id_ppp_nomina")));
					nominaComplementoDto.setFechaFacturacion(rs.getDate("fecha_facturacion"));
					nominaComplementoDto.setFechaTimbrado(rs.getDate("fecha_timbrado"));
					nominaComplementoDto.setFechaDispersion(rs.getDate("fecha_dispersion"));
					nominaComplementoDto.setRequiereFianciamiento(rs.getBoolean("requiere_financiamiento"));
					nominaComplementoDto.setMontoFinanciamiento(rs.getBigDecimal("monto_financiamiento") !=null ? rs.getBigDecimal("monto_financiamiento") : null);
					nominaComplementoDto.setObservaciones(rs.getString("observaciones")!=null ? rs.getString("observaciones") : null);
					
					return nominaComplementoDto;						
				}
			});

		
			if (nomina != null && !nomina.isEmpty()) {
				return nomina.get(0);
			}else {
				return new NominaComplementoDto();
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNomComplByIdNomCompl ", e);
			return new NominaComplementoDto();
		}
	}

}
