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
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.Colaborador;
import mx.com.consolida.entity.crm.ColaboradorDocumento;
import mx.com.documento.DefinicionDocumentoDto;

@Repository
public class ColaboradorDocumentosDaoImpl extends GenericDAO<ColaboradorDocumento, Long> implements ColaboradorDocumentosDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ColaboradorDocumentosDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List<DocumentoCSMDto> obtenerDocumentos(Long idColaborador) {
		StringBuilder sql = new StringBuilder();
		sql.append("select dd.id_definicion_documento,dd.nombre_documento,dd.clave_documento,dd.url_base,  \r\n" + 
				"		 psd.id_colaborador_documento,psd.id_colaborador,psd.id_CMS,psd.nombre_archivo   \r\n" + 
				"		 from definicion_documento dd \r\n" + 
				"		 left join (\r\n" + 
				"		 select cd.* from\r\n" + 
				"		 colaborador_documento cd where cd.id_colaborador = ").append(idColaborador)
		.append("					) psd \r\n" + 
				"		 on dd.id_definicion_documento = psd.id_definicion_documento \r\n" + 
				"		 where dd.id_definicion_documento  in (44,43,49,45,50,48,47,51,46,52,53)");
		List<DocumentoCSMDto> list = jdbcTemplate.query(sql.toString(), new Object[] {}, new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				DocumentoCSMDto documento = new DocumentoCSMDto();
				documento.setIdCMS(rs.getLong("id_CMS"));
				DefinicionDocumentoDto definicionDocumento = new DefinicionDocumentoDto();

				documento.setIdColaboradorDocumento(rs.getLong("id_colaborador_documento"));
				documento.setIdColaborador(rs.getLong("id_colaborador"));
				definicionDocumento.setIdDefinicionDocumento(rs.getLong("id_definicion_documento"));
				definicionDocumento.setClaveDocumento(rs.getString("clave_documento"));
				definicionDocumento.setNombreDocumento(rs.getString("nombre_documento"));
				definicionDocumento.setUrlBase(rs.getString("url_base"));
				documento.setDefinicion(definicionDocumento);

				documento.setNombreArchivo(rs.getString("nombre_archivo"));

				return documento;

			}
		});
		return list;
	}
	
	public void eliminarDocumento(Long idColaboradorDocumento) {
		String sql = "delete from colaborador_documento where id_colaborador_documento = "+idColaboradorDocumento;
		jdbcTemplate.execute(sql);
	}
}
