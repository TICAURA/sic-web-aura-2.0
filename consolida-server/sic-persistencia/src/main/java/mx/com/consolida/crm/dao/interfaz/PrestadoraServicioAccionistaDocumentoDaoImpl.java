package mx.com.consolida.crm.dao.interfaz;

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

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionistaDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PrestadoraServicioAccionistaDocumentoDaoImpl extends GenericDAO<PrestadoraServicioAccionistaDocumento, Long> implements PrestadoraServicioAccionistaDocumentoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioAccionistaDocumentoDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosApoderadoPrestadora(Long idPrestadora, Long idPrestadoraServaccionista) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_prestadora_servicio_accionista_documento, psd.id_prestadora_servicio_accionista  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psrld.* ");
		sb.append(" 			from sin.prestadora_servicio_accionista psrl, prestadora_servicio_accionista_documento psrld ");
		sb.append(" 			where psrld.id_prestadora_servicio_accionista = psrl.id_prestadora_servicio_accionista ");
		sb.append(" 			and psrl.id_prestadora_servicio_accionista = ? ) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (15,16,17)");
				
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServaccionista}, new RowMapper() {
	          public DocumentoCSMDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	          
	          DocumentoCSMDto documento = new DocumentoCSMDto();
	          documento.setIdPrestadora(idPrestadora);
	          documento.setIdCMS(rs.getLong("id_CMS"));
	          DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();
	          
	          definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
	          definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
	          definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
	          documento.setDefinicion(definicionDocumento);
	          	 
	          documento.setNombreArchivo(rs.getString("nombre_archivo"));
	          documento.setIdPrestadoraServicioAccionistaDoctumento(rs.getLong("id_prestadora_servicio_accionista_documento"));
	          documento.setIdPrestadoraServicioAccionista(rs.getLong("id_prestadora_servicio_accionista"));
	        	  
	          return documento;
		   }
		   });
	}

}
