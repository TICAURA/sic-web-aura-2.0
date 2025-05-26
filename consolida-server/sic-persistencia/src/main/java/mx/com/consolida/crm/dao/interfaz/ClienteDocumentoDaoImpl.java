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
import mx.com.consolida.entity.crm.ClienteDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class ClienteDocumentoDaoImpl extends GenericDAO<ClienteDocumento, Long> implements ClienteDocumentoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteDocumentoDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<DocumentoCSMDto> listDocumentosCliente(Long idCliente) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" select dd.id_definicion_documento , dd.clave_documento  , dd.nombre_documento ,   ");
		sb.append(" psd.id_cliente_documento, psd.id_cliente  , psd.id_CMS , psd.nombre_archivo    ");
		sb.append(" from definicion_documento dd  ");
		sb.append(" left join (select psdi.* from cliente_documento psdi where psdi.id_cliente = ? ) psd  ");
		sb.append(" on dd.id_definicion_documento = psd.id_definicion_documento  ");
		sb.append(" where dd.id_definicion_documento  in (30,31,32,33,34,35,36,37,38) and dd.ind_estatus = 1 ");
		
		// 1,2,3,4,5,6,7,8,9,10 son todas las definiciones que tiene la prestadora de servicios
		
		
		return jdbcTemplate.query(sb.toString(), new Object[] {idCliente}, new RowMapper() {
	          public DocumentoCSMDto mapRow(ResultSet rs, int rowNum) throws SQLException {
	          
	          DocumentoCSMDto documento = new DocumentoCSMDto();
	          documento.setIdCliente(idCliente);;
	          documento.setIdCMS(rs.getLong("id_CMS"));
	          DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();
	          
	          definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
	          definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
	          definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
	          documento.setDefinicion(definicionDocumento);
	          	 
	          documento.setNombreArchivo(rs.getString("nombre_archivo"));
	          documento.setIdClienteDocumento(rs.getLong("id_cliente_documento"));
	        	  
	          return documento;
		   }
		   });
		
	}

}
