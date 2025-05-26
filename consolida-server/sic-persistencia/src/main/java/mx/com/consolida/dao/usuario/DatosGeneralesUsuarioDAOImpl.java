package mx.com.consolida.dao.usuario;

import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.seguridad.Persona;

@Repository
public class DatosGeneralesUsuarioDAOImpl extends  GenericDAO<Persona, Long> 
implements DatosGeneralesUsuarioDAO {

}
