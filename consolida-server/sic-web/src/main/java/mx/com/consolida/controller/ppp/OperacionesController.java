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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.ppp.service.interfaz.NominaComplementoBO;

@Controller
@RequestMapping("pppOperaciones")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class OperacionesController  extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperacionesController.class);
	
	@Autowired
	private NominaBO nominaBO;
	
	@Autowired
	private NominaComplementoBO nominaComplementoBO;
	
	@RequestMapping(value = "/nominas/cargaInicialNominaParaAutorizarFinanciamiento")
	@ResponseBody
	public Map<String, Object> cargaInicialNominaParaAutorizarFinanciamiento(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

				dataReturn.put("gridNominasParaAutorizarFinanciamiento", nominaBO.getNominasParaAutorizarFinanciamiento(NominaEstatusEnum.REQUIERE_FINANCIAMIENTO.getId()));

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value="/nominas/rechazarFinanciamientoOperaciones")
	@ResponseBody
	public MensajeDTO rechazarFinanciamientoOperaciones(@RequestBody NominaDto nomina) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoOperaciones, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoOperaciones, no cuenta con motivo de rechazo para el getIdNominaPPP "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }
			 
			 if(!nominaBO.cambioEstatusOtorgarFinanciamientoGenerico(nomina.getIdNominaPPP(), nomina.getMotivoRechazo(), NominaEstatusEnum.RECHAZAR_FINANCIAMIENTO_OPER, getUser().getIdUsuario())) {
				 LOGGER.error("Ocurrio un error en rechazarFinanciamientoOperaciones ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en rechazarFinanciamientoOperaciones ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
		 
		
				
	return mensajeDTO;
	}
	
	@RequestMapping(value="/nominas/autorizarFinanciamientoOperaciones")
	@ResponseBody
	public MensajeDTO autorizarFinanciamientoOperaciones(@RequestBody NominaDto nomina) throws BusinessException {
		 MensajeDTO mensajeDTO = new MensajeDTO();

		 try {
			 
			 if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoOperaciones, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoOperaciones, no cuenta con motivo de rechazo para el getIdNominaPPP "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }
			 
			 if(!nominaBO.cambioEstatusOtorgarFinanciamientoGenerico(nomina.getIdNominaPPP(), nomina.getMotivoRechazo(), NominaEstatusEnum.AUTORIZADO_OPERACIONES, getUser().getIdUsuario())) {
				 LOGGER.error("Ocurrio un error en autorizarFinanciamientoOperaciones ");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			 }
			 
			 
		 }catch (Exception e) {
			 LOGGER.error("Ocurrio un error en autorizarFinanciamientoOperaciones ", e);
			 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
		 
		
				
	return mensajeDTO;
	}

}
