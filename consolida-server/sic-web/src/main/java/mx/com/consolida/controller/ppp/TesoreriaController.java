package mx.com.consolida.controller.ppp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.ppp.service.interfaz.NominaComplementoBO;

@Controller
@RequestMapping("pppTesoreria")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class TesoreriaController  extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TesoreriaController.class);
	
	@Autowired
	private NominaBO nominaBO;
	
	@Autowired
	private NominaComplementoBO nominaComplementoBO;
	
	@RequestMapping(value = "/nominas/cargaInicialTesoreriaNominaParaAutorizarFinanciamiento")
	@ResponseBody
	public Map<String, Object> cargaInicialTesoreriaNominaParaAutorizarFinanciamiento(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

				dataReturn.put("gridNominasParaAutorizarFinanciamiento", nominaBO.getNominasParaAutorizarFinanciamiento(NominaEstatusEnum.AUTORIZADO_OPERACIONES.getId()));

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value="/nominas/rechazarFinanciamientoTesoreria")
	@ResponseBody
	public MensajeDTO rechazarFinanciamientoTesoreria(@RequestBody NominaDto nomina) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoTesoreria, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoTesoreria, no cuenta con motivo de rechazo para el idClienteTmp "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }
			 
			 if(!nominaBO.cambioEstatusOtorgarFinanciamientoGenerico(nomina.getIdNominaPPP(), nomina.getMotivoRechazo(), NominaEstatusEnum.RECHAZAR_FINANCIAMIENTO_FINANZAS, getUser().getIdUsuario())) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoTesoreria ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en rechazarFinanciamientoTesoreria ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
		 
		
				
	return mensajeDTO;
	}
	
	@RequestMapping(value="/nominas/autorizarFinanciamientoTesoreria")
	@ResponseBody
	public MensajeDTO autorizarFinanciamientoTesoreria(@RequestBody NominaDto nomina) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoTesoreria, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoTesoreria, no cuenta con motivo de rechazo para el getIdNominaPPP "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }
			 
			 if(!nominaBO.cambioEstatusOtorgarFinanciamientoGenerico(nomina.getIdNominaPPP(), nomina.getMotivoRechazo(), NominaEstatusEnum.AUTORIZADO_FINANZAS, getUser().getIdUsuario())) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoTesoreria ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en autorizarFinanciamientoTesoreria ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}	
	return mensajeDTO;
	}

}
