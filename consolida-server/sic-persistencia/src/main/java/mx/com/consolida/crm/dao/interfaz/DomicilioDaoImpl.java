package mx.com.consolida.crm.dao.interfaz;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.Domicilio;

@Repository
public class DomicilioDaoImpl extends GenericDAO<Domicilio, Long> implements DomicilioDao{

	@Override
	@Transactional
	public Long crearActualizarDomicilio(Domicilio entityDomicilio) {
		 Long idDomicilio = create(entityDomicilio).longValue();
		return idDomicilio;
	}

}
