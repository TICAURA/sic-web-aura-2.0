package mx.com.consolida.crm.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteActividadDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.crm.ClienteGiroComercial;

@Repository
public class ClienteGiroComercialDaoImpl extends GenericDAO<ClienteGiroComercial, Long> implements ClienteGiroComercialDao{

	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteGiroComercialDaoImpl.class);

	@Override
	public List<ClienteActividadDto> convertirGiroADto(List<ClienteGiroComercial> clienteGiroComercial) {
		List<ClienteActividadDto> actividad = new ArrayList<ClienteActividadDto>();
		
		for(ClienteGiroComercial cliente: clienteGiroComercial) {
			ClienteActividadDto clienteActividad = new ClienteActividadDto();
			
			clienteActividad.setIdClienteGiroComercial(cliente.getIdClienteGiroComercial());
			clienteActividad.setCatGiroComercial(new CatGeneralDto());
			clienteActividad.getCatGiroComercial().setIdCatGeneral(cliente.getCatGiroComercial().getIdCatGeneral());
			clienteActividad.getCatGiroComercial().setDescripcion(cliente.getCatGiroComercial().getDescripcion());
			clienteActividad.setSubgiroComercial(new CatSubGiroComercialDto());
			clienteActividad.getSubgiroComercial().setIdCatSubGiroComercial(cliente.getCatSubGiroComercial().getIdCatSubGiroComercial());
			clienteActividad.getSubgiroComercial().setDescripcion(cliente.getCatSubGiroComercial().getDescripcion());
			
			actividad.add(clienteActividad);
		}
		return actividad;
	}
	
}
