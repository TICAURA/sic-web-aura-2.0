package mx.com.consolida.crm.dao.interfaz;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.crm.dto.PrestadoraServicioDocumentoDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDocumento;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class PrestadoraServicioDocumentoDaoImpl extends GenericDAO<PrestadoraServicioDocumento, Long> implements PrestadoraServicioDocumentoDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioDocumentoDaoImpl.class);


	@Override
	public PrestadoraServicioDocumentoDto convertirPrestadoraServicioDocumentoADto(
			PrestadoraServicioDocumento entity) {
		
		PrestadoraServicioDocumentoDto prestadoraServicioDocumentoDto= new PrestadoraServicioDocumentoDto();
		
		prestadoraServicioDocumentoDto.setIdPrestadoraServicioDocumento(entity.getIdPrestadoraServicioDocumento());
		prestadoraServicioDocumentoDto.setPasswordCsd(entity.getPasswordCsd());
		prestadoraServicioDocumentoDto.setPasswordFiel(entity.getPasswordFiel());
		prestadoraServicioDocumentoDto.setIdPrestadoraServicio(entity.getPrestadoraServicio().getIdPrestadoraServicio());
		prestadoraServicioDocumentoDto.setNombreArchivoFielCer(entity.getNombreArchivoFielCer());
		prestadoraServicioDocumentoDto.setNombreArchivoFielKey(entity.getNombreArchivoFielKey());
		prestadoraServicioDocumentoDto.setNombreArchivoCsdCer(entity.getNombreArchivoCsdCer());
		prestadoraServicioDocumentoDto.setNombreArchivoCsdKey(entity.getNombreArchivoCsdKey());
		prestadoraServicioDocumentoDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDocumentoDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDocumentoDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		
		return prestadoraServicioDocumentoDto;
	}


}