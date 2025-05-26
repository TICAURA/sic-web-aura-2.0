package mx.com.consolida.crm.dao.interfaz;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.CelulaPrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.celula.CelulaPrestadoraServicio;

@Repository
public class CelulaPrestadoraServicioDaoImpl extends GenericDAO<CelulaPrestadoraServicio, Long> implements CelulaPrestadoraServicioDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(CelulaPrestadoraServicioDaoImpl.class);


	@Override
	public CelulaPrestadoraServicioDto convertirCelulaPrestadoraServicioADto(
			CelulaPrestadoraServicio entity) {
		CelulaPrestadoraServicioDto celula = new CelulaPrestadoraServicioDto();
		
		celula.setCelula(new CelulaDto());
		celula.setPrestadoraServicio(new PrestadoraServicioDto());
		
		celula.setIdCelulaPrestadoraServicio(entity.getIdCelulaPrestadoraServicio());
		
		celula.getCelula().setIdCelula(entity.getCelula().getIdCelula());
		celula.getCelula().setClave(entity.getCelula().getClave());
		celula.getCelula().setNombre(entity.getCelula().getNombre());
		
		return celula;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional(readOnly = true)
	public CelulaPrestadoraServicio getCelulaPrestByIdPrestadora(Long idPrestadora) {
		
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select cp from CelulaPrestadoraServicio cp where cp.prestadoraServicio.idPrestadoraServicio = :idPrestadora and cp.indEstatus = 1");
			query.setParameter("idPrestadora", idPrestadora);
			
			if((CelulaPrestadoraServicio) query.uniqueResult()!=null) {				
				return (CelulaPrestadoraServicio) query.uniqueResult();
			}else {
				return null;
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCelulaPrestByIdPrestadora ", e);
			return null;
		}		
	}

}
