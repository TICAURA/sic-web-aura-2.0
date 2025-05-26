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
import mx.com.consolida.entity.crm.CatFormulas;
import mx.com.consolida.entity.crm.NominaClienteComision;
import mx.com.consolida.entity.crm.NominaClienteHonorario;
import mx.com.consolida.generico.CatEstatusEnum;

@Repository
public class NominaClienteHonorarioDaoImpl extends GenericDAO<NominaClienteHonorario, Long> implements NominaClienteHonorarioDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaClienteHonorarioDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	public List<ClienteComisionHonorarioDto> convertirNominaClienteHonorarioADto(
			List<NominaClienteHonorario> nominaClienteHonorarios) {
		List<ClienteComisionHonorarioDto> listHonorarios =  new ArrayList<ClienteComisionHonorarioDto>();
		
		for(NominaClienteHonorario entity: nominaClienteHonorarios) {
			if(entity.getIndEstatus().equals(CatEstatusEnum.ACTIVO.getIdEstatus())) {
				ClienteComisionHonorarioDto honorario = new ClienteComisionHonorarioDto();
				honorario.setIdNominaClienteHonorario(entity.getIdNominaClienteHonorario());
				
				if(entity.getCatFormulaPpp() != null && entity.getCatFormulaPpp().getIdCatFormulas() != null) {
				honorario.setFormulaPPP(new CatGeneralDto());
				honorario.getFormulaPPP().setIdCatGeneral(entity.getCatFormulaPpp().getIdCatFormulas());
				honorario.getFormulaPPP().setClave(entity.getCatFormulaPpp().getClave());
				honorario.getFormulaPPP().setDescripcion(entity.getCatFormulaPpp().getDescripcion());
				}
				
				if(entity.getCatFormulaFactura() !=null && entity.getCatFormulaFactura().getIdCatFormulas() != null) {
					honorario.setFormulaFactura(new CatGeneralDto());
					honorario.getFormulaFactura().setIdCatGeneral(entity.getCatFormulaFactura().getIdCatFormulas());
					honorario.getFormulaFactura().setClave(entity.getCatFormulaFactura().getClave());
					honorario.getFormulaFactura().setDescripcion(entity.getCatFormulaFactura().getDescripcion());
				}
				
				if(entity.getCatTipoIva() !=null && entity.getCatTipoIva().getIdCatFormulas() != null) {
					honorario.setFormulaTipoIva(new CatGeneralDto());
					honorario.getFormulaTipoIva().setIdCatGeneral(entity.getCatTipoIva().getIdCatFormulas());
					honorario.getFormulaTipoIva().setClave(entity.getCatTipoIva().getClave());
					honorario.getFormulaTipoIva().setDescripcion(entity.getCatTipoIva().getDescripcion());
				}
				
				honorario.setHonorarioPPP(entity.getHonorarioPpp());
				honorario.setIvaComercial(entity.getIvaComercial());
				
				honorario.setHonorarioPPPSs(entity.getHonorarioPppSs());
				honorario.setHonorarioPPPMixto(entity.getHonorarioPppMixto());
				
				if(entity.getCatFormulaFacturaSS() != null && entity.getCatFormulaFacturaSS().getIdCatFormulas() != null) {
				honorario.setFormulaPPPSs(new CatGeneralDto());
				honorario.getFormulaPPPSs().setIdCatGeneral(entity.getCatFormulaFacturaSS().getIdCatFormulas());
				honorario.getFormulaPPPSs().setDescripcion(entity.getCatFormulaFacturaSS().getDescripcion());
				}
				
				if(entity.getCatFormulaFacturaMixto() != null && entity.getCatFormulaFacturaMixto().getIdCatFormulas() != null) {
				honorario.setFormulaPPPMixto(new CatGeneralDto());
				honorario.getFormulaPPPMixto().setIdCatGeneral(entity.getCatFormulaFacturaMixto().getIdCatFormulas());
				honorario.getFormulaPPPMixto().setDescripcion(entity.getCatFormulaFacturaMixto().getDescripcion());
				}
				
				if(entity.getCatFormulaFacturaMaquila() != null && entity.getCatFormulaFacturaMaquila().getIdCatFormulas() != null) {
				honorario.setFormulaPPPMaquila(new CatGeneralDto());
				honorario.getFormulaPPPMaquila().setIdCatGeneral(entity.getCatFormulaFacturaMaquila().getIdCatFormulas());
				honorario.getFormulaPPPMaquila().setDescripcion(entity.getCatFormulaFacturaMaquila().getDescripcion());
				}
				
				honorario.setNominaCliente(new NominaClienteDto());
				honorario.getNominaCliente().setIdNominaCliente(entity.getNominaCliente().getIdNominaCliente());
				honorario.getNominaCliente().setClaveNomina(entity.getNominaCliente().getClaveNomina());
				honorario.getNominaCliente().setNombreNomina(entity.getNominaCliente().getNombreNomina());
				listHonorarios.add(honorario);
				
			}
			}
		
		return listHonorarios;
		}
	
	

}
