package mx.com.consolida.crm.dao.interfaz;

import org.springframework.stereotype.Repository;

import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteEstatus;

@Repository
public class ClienteEstatusDaoImpl extends GenericDAO<ClienteEstatus, Long> implements ClienteEstatusDao{

}
