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
import org.springframework.web.bind.annotation.RequestBody;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegalDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PrestadoraServicioRepresentanteLegalDocumentoDaoImpl extends GenericDAO<PrestadoraServicioRepresentanteLegalDocumento, Long> implements PrestadoraServicioRepresentanteLegalDocumentoDao{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioDoctoDaoImpl.class);
	
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosRepresentantePrestadora(Long idPrestadora) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_prestadora_servicio_representante_legal_documento, psd.id_prestadora_servicio_representante_legal  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psrld.* ");
		sb.append(" 			from sin.prestadora_servicio_representante_legal psrl, prestadora_servicio_representante_legal_documento psrld ");
		sb.append(" 			where psrld.id_prestadora_servicio_representante_legal = psrl.id_prestadora_servicio_representante_legal ");
		sb.append(" 			and psrl.id_prestadora_servicio = ?) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (18,19,20,21) ");
				
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadora}, new RowMapper() {
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
	          documento.setIdPrestadoraServicioRepresentanteLegalDoctumento(rs.getLong("id_prestadora_servicio_representante_legal_documento"));
	          documento.setIdPrestadoraServicioRepresentanteLegal(rs.getLong("id_prestadora_servicio_representante_legal"));
	        	  
	          return documento;
		   }
		   });
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(Long idPrestadora, Long idPrestadoraServRepLeg) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_prestadora_servicio_representante_legal_documento, psd.id_prestadora_servicio_representante_legal  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psrld.* ");
		sb.append(" 			from sin.prestadora_servicio_representante_legal psrl, prestadora_servicio_representante_legal_documento psrld ");
		sb.append(" 			where psrld.id_prestadora_servicio_representante_legal = psrl.id_prestadora_servicio_representante_legal ");
		sb.append(" 			and psrl.id_prestadora_servicio_representante_legal = ?) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (18,19,20,21) ");
				
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServRepLeg}, new RowMapper() {
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
	          documento.setIdPrestadoraServicioRepresentanteLegalDoctumento(rs.getLong("id_prestadora_servicio_representante_legal_documento"));
	          documento.setIdPrestadoraServicioRepresentanteLegal(rs.getLong("id_prestadora_servicio_representante_legal"));
	        	  
	          return documento;
		   }
		   });
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoCSMDto> listPrestadoraDocumentosRepresentanteCerKey(Long idPrestadoraServRepLeg,
			Long idPrestadora) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_prestadora_servicio_representante_legal_documento, psd.id_prestadora_servicio_representante_legal  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psrld.* ");
		sb.append(" 			from sin.prestadora_servicio_representante_legal psrl, prestadora_servicio_representante_legal_documento psrld ");
		sb.append(" 			where psrld.id_prestadora_servicio_representante_legal = psrl.id_prestadora_servicio_representante_legal ");
		sb.append(" 			and psrl.id_prestadora_servicio_representante_legal = ?) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (58, 59)");
				
		return jdbcTemplate.query(sb.toString(), new Object[] {idPrestadoraServRepLeg}, new RowMapper() {
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
	          documento.setIdPrestadoraServicioRepresentanteLegalDoctumento(rs.getLong("id_prestadora_servicio_representante_legal_documento"));
	          documento.setIdPrestadoraServicioRepresentanteLegal(rs.getLong("id_prestadora_servicio_representante_legal"));
	        	  
	          return documento;
		   }
		   });
	}
	
}
