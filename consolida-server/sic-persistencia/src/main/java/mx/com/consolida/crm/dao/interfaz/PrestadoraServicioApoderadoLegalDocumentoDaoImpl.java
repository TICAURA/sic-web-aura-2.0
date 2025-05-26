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
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegalDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PrestadoraServicioApoderadoLegalDocumentoDaoImpl extends GenericDAO<PrestadoraServicioApoderadoLegalDocumento, Long> implements PrestadoraServicioApoderadoLegalDocumentoDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioApoderadoLegalDocumentoDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosApoderadoPrestadora(Long idPrestadora, Long idPrestadoraServApodLeg) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_prestadora_servicio_apoderado_legal_documento, psd.id_prestadora_servicio_apoderado_legal  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psrld.* ");
		sb.append(" 			from sin.prestadora_servicio_apoderado_legal psrl, prestadora_servicio_apoderado_legal_documento psrld ");
		sb.append(" 			where psrld.id_prestadora_servicio_apoderado_legal = psrl.id_prestadora_servicio_apoderado_legal ");
		sb.append(" 			and psrl.id_prestadora_servicio_apoderado_legal = ? ) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (22,23,24,25,72) and dd.ind_estatus = 1");
				
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServApodLeg}, new RowMapper() {
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
	          documento.setIdPrestadoraServicioApoderadoLegalDoctumento(rs.getLong("id_prestadora_servicio_apoderado_legal_documento"));
	          documento.setIdPrestadoraServicioApoderadoLegal(rs.getLong("id_prestadora_servicio_apoderado_legal"));
	        	  
	          return documento;
		   }
		   });
	}

}
