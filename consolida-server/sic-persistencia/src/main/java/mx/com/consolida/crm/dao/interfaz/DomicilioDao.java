package mx.com.consolida.crm.dao.interfaz;

import mx.com.consolida.dao.DAO;
import mx.com.consolida.entity.crm.Domicilio;

public interface DomicilioDao extends DAO<Domicilio,Long>{

	Long crearActualizarDomicilio(Domicilio entityDomicilio);

}
