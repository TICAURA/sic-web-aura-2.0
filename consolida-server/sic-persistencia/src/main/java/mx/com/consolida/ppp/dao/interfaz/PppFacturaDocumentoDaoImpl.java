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

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.ppp.PppFacturaDocumento;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.entity.ppp.PppNominaFacturaDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class PppFacturaDocumentoDaoImpl extends GenericDAO<PppFacturaDocumento, Long> implements PppFacturaDocumentoDao{
	
	private static Logger LOGGER = LoggerFactory.getLogger(PppFacturaDocumentoDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public PppFacturaDocumento getUltimoDocComprobantePagoByIdCms(Long idCms) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select documento from PppFacturaDocumento documento where documento.indEstatus = 1 and documento.definicionDocumento.idDefinicionDocumento = 65 and documento.idCMS = :idCms");
			query.setParameter("idCms", idCms);

			PppFacturaDocumento documento = (PppFacturaDocumento) query.uniqueResult();
			
			return documento;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getUltimoDocByIdCms ", e);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Long> getidPppFacturaDocumento(Long idPppFactura) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select documento.idPppFacturaDocumento from PppFacturaDocumento documento where documento.indEstatus = 1 and documento.pppFactura.idPppFactura = :idPppFactura");
			query.setParameter("idPppFactura", idPppFactura);

			List<Long>  lista = query.list();
			
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getidPppFacturaDocumento ", e);
			return null;
		}
	}

	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPppFactura(Long idPppNominaFactura) {
		
		try {
			
			StringBuilder sb = new StringBuilder();
			sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,  dd.tipo_documentos, ");
			sb.append("  psd.id_ppp_factura_documento, psd.id_ppp_factura  , psd.id_CMS , psd.nombre_archivo     "); 
			sb.append(" from definicion_documento dd   "); 
			sb.append(" left join (select psdi.* from ppp_factura_documento psdi where psdi.id_ppp_factura = ? ) psd   "); 
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
		          documento.setIdPppNominaFacturaDocumento(rs.getLong("id_ppp_factura_documento"));
		        	  
		          return documento;
			   }
			   });
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listDocumentosPppFactura ", e);
			return Collections.emptyList();
		}
				
	}
	

}
