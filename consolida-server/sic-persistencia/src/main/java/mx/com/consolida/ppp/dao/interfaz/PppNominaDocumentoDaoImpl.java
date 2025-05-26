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

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppNominaDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PppNominaDocumentoDaoImpl  extends GenericDAO<PppNominaDocumento, Long> implements PppNominaDocumentoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PppNominaDocumentoDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPppNomina(Long idPppNominaFactura) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,  dd.tipo_documentos, ");
			sb.append("  psd.id_ppp_nomina_factura_documento, psd.id_ppp_nomina_factura  , psd.id_CMS , psd.nombre_archivo     "); 
			sb.append(" from definicion_documento dd   "); 
			sb.append(" left join (select psdi.* from sin.ppp_nomina_factura_documento psdi where psdi.id_ppp_nomina_factura = ? ) psd   "); 
			sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento   "); 
			sb.append(" where dd.id_definicion_documento  in (63, 64, 65) ");
			
			return jdbcTemplate.query(sb.toString(), new Object[] {idPppNominaFactura}, new RowMapper() {
		          public DocumentoCSMDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		          
		          DocumentoCSMDto documento = new DocumentoCSMDto();
		          documento.setIdPppNominaFactura(idPppNominaFactura);
		          documento.setIdCMS(rs.getLong("id_CMS"));
		          DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();
		          
		          definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
		          definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
		          definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
		          definicionDocumento.setTiposDocumentos(rs.getString("tipo_documentos"));
		          documento.setDefinicion(definicionDocumento);
		          	 
		          documento.setNombreArchivo(rs.getString("nombre_archivo"));
		          documento.setIdPppNominaFacturaDocumento(rs.getLong("id_ppp_nomina_factura_documento"));
		        	  
		          return documento;
			   }
			   });
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listDocumentosPppNomina ", e);
			return Collections.emptyList();
		}
				
	}

}
