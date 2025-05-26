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
import mx.com.consolida.crm.dto.ClienteComisionHonorarioDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.entity.crm.NominaClienteComision;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.usuario.PersonaDto;
import mx.com.consolida.usuario.UsuarioDTO;

@Repository
public class NominaClienteComisionDaoImpl extends GenericDAO<NominaClienteComision, Long> implements NominaClienteComisionDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaClienteComisionDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public List<ClienteComisionHonorarioDto> convertirNominaClienteComisionADto(List<NominaClienteComision> list) {
		List<ClienteComisionHonorarioDto> listComisiones =  new ArrayList<ClienteComisionHonorarioDto>();
		
		for(NominaClienteComision entity: list) {
			if(entity.getIndEstatus().equals(CatEstatusEnum.ACTIVO.getIdEstatus())) {
			ClienteComisionHonorarioDto comision =  new ClienteComisionHonorarioDto();
			String nombreCompleto = null;
		comision.setCanalVenta(new CatGeneralDto());
		comision.getCanalVenta().setIdCatGeneral(entity.getCanalVenta().getIdCatGeneral());
		comision.getCanalVenta().setDescripcion(entity.getCanalVenta().getDescripcion());
		comision.setUsuario(new UsuarioDTO());
		comision.getUsuario().setIdUsuario(entity.getComisionista().getIdUsuario());
		comision.getUsuario().setPersona(new PersonaDto());
		comision.getUsuario().getPersona().setIdPersona(entity.getComisionista().getPersona().getIdPersona());
		comision.getUsuario().getPersona().setNombre(entity.getComisionista().getPersona().getNombre());
		comision.getUsuario().getPersona().setApellidoPaterno(entity.getComisionista().getPersona().getApellidoPaterno());
		comision.getUsuario().getPersona().setApellidoMaterno(entity.getComisionista().getPersona().getApellidoMaterno());
		
		if(entity.getComisionista().getPersona().getNombre() !=null) {
			nombreCompleto = entity.getComisionista().getPersona().getNombre();
		}
		if(entity.getComisionista().getPersona().getApellidoPaterno() !=null) {
			nombreCompleto = nombreCompleto + " " + entity.getComisionista().getPersona().getApellidoPaterno();
		}else {
			nombreCompleto = nombreCompleto + " ";
		}
		if(entity.getComisionista().getPersona().getApellidoMaterno() !=null) {
			nombreCompleto = nombreCompleto + " " + entity.getComisionista().getPersona().getApellidoMaterno();
		}
		if(entity.getFechaInicioPago() != null) {
			comision.setFechaInicioPago(entity.getFechaInicioPago());
		}
		if(entity.getFechaFinalPago() != null) {
			comision.setFechaFinPago(entity.getFechaFinalPago());
		}
		if(entity.getPorcentajeComision() != null) {
			comision.setPorcentajeComision(new CatGeneralDto());
			comision.getPorcentajeComision().setIdCatGeneral(entity.getPorcentajeComision().getIdCatGeneral());
			comision.getPorcentajeComision().setClave(entity.getPorcentajeComision().getClave());
			comision.getPorcentajeComision().setDescripcion(entity.getPorcentajeComision().getDescripcion());
		}
		comision.getUsuario().setNombreCompleto(nombreCompleto);
		comision.setEsquema(new CatGeneralDto());
		comision.getEsquema().setIdCatGeneral(entity.getEsquema().getIdCatGeneral());
		comision.getEsquema().setDescripcion(entity.getEsquema().getDescripcion());
		
		if(entity.getComision() != null) {
			comision.setComision(entity.getComision());
		}
		
		if(entity.getPricing() != null) {
		comision.setComisionPricing(entity.getPricing());
		}
		
		if(entity.getCatFormulaComision() != null) {
			comision.setFormulaComision(new CatGeneralDto());
			comision.getFormulaComision().setIdCatGeneral(entity.getCatFormulaComision().getIdCatFormulas());
			comision.getFormulaComision().setDescripcion(entity.getCatFormulaComision().getDescripcion());
		}
		
		if(entity.getCatFormulaPricing() != null) {
			comision.setFormulaPricing(new CatGeneralDto());
			comision.getFormulaPricing().setIdCatGeneral(entity.getCatFormulaPricing().getIdCatFormulas());
			comision.getFormulaPricing().setDescripcion(entity.getCatFormulaPricing().getDescripcion());	
		}
		
		comision.setIdNominaClienteComision(entity.getIdNominaClienteComision());
		comision.setNominaCliente(new NominaClienteDto());
		comision.getNominaCliente().setIdNominaCliente(entity.getNominaCliente().getIdNominaCliente());
		comision.getNominaCliente().setClaveNomina(entity.getNominaCliente().getClaveNomina());
		comision.getNominaCliente().setNombreNomina(entity.getNominaCliente().getNombreNomina());
		
		listComisiones.add(comision);
			}
		}
		return listComisiones;
	}
	
	

}
