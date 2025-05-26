package mx.com.consolida.controller.ppp;

import java.util.HashMap;
import java.util.List;
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

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.service.usuario.UsuarioBO;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class FinanzasController extends BaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(FinanzasController.class);

	@Autowired
	private NominaBO nominaBO;

	@Autowired
	private UsuarioBO usuarioBO;

	@RequestMapping(value = "/finanzas/cargaInicialFinanzas")
	@ResponseBody
	public Map<String, Object> cargaInicialNomina(Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {
               if(getUser().getRol().getIdRol()== 15) {
            		dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasFinanzasByCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()), 25L));
               }else {
				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasFinanzasByCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()), 7L));
               }
               dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/finanzas/cargaInicialFinanzasXEstatus")
	@ResponseBody
	public Map<String, Object> cargaInicialNominaXEstatus(@RequestBody String idCatEstatusNomina) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {

				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasFinanzasByCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()), new Long(idCatEstatusNomina)));
				dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}


	@RequestMapping(value = "/finanzas/guardarCveOrdenPagoColaborador")
	@ResponseBody
	public void guardarCveOrdenPagoColaborador(@RequestBody List<EmpleadoDTO> colaboradores) throws BusinessException {
		try {

			if(!colaboradores.isEmpty()) {
				nominaBO.guardarCveOrdenPagoColaborador(colaboradores, getUser().getIdUsuario());
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarColaboradores ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/finanzas/dispersionStpColaborador")
	@ResponseBody
	public Boolean dispersionStpColaborador(@RequestBody Long idPppNomina) throws BusinessException {
		try {

			if(idPppNomina!=null) {
				if(nominaBO.dispersionStpColaborador(idPppNomina, getUser().getIdUsuario())) {

					if(!nominaBO.cambiaEstatusNomina(idPppNomina, null, NominaEstatusEnum.DISPERSADA, getUser().getIdUsuario())) {
						LOGGER.error("Ocurrio un error en dispersionStpColaborador, el cambio de estatus de nomina fallo ");
						return Boolean.FALSE;
					}

					return Boolean.TRUE;

				}else {
					LOGGER.error("Ocurrio un error en dispersionStpColaborador, cambiar el estatus del colaborador a dispersado");
					return Boolean.FALSE;

				}

			}else {
				LOGGER.error("Ocurrio un error en dispersionStpColaborador, idPppNomina es null ");
				return Boolean.FALSE;
			}


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en dispersionStpColaborador ", e);
			return Boolean.FALSE;
		}
	}


	@RequestMapping(value="/finanzas/cambiaEstatusNominaRechazarDispersion", method = RequestMethod.POST)
	@ResponseBody
	public MensajeDTO cambiaEstatusNominaRechazarDispersion(@RequestBody NominaDto nomina, Model model) throws BusinessException {

		MensajeDTO mensajeDTO = new MensajeDTO();

		try {

			if(nomina.getIdNominaPPP() == null) {
				 LOGGER.error("Ocurrio un error en cambiaEstatusNominaRechazarDispersion, no tiene getIdNominaPPP");
				 mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
				 return mensajeDTO;
			 }else if(nomina.getMotivoRechazo() == null || "".equals(nomina.getMotivoRechazo().trim())) {
				 LOGGER.error("Ocurrio un error en cambiaEstatusNominaRechazarDispersion, no cuenta con motivo de rechazo para el idClienteTmp "+ nomina.getIdNominaPPP());
				 mensajeDTO.setMensajeError("Favor de ingresar el motivo de rechazo");
				 return mensajeDTO;
			 }


			if(!nominaBO.cambiaEstatusNomina(nomina.getIdNominaPPP() , nomina.getMotivoRechazo(), NominaEstatusEnum.RECHAZAR_DISPERSION_STP, getUser().getIdUsuario())) {
				LOGGER.error("Ocurrio un error en cambiaEstatusNominaRechazarDispersion, el cambio de estatus de nomina fallo ");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
			}


			return mensajeDTO;

		}catch (BusinessException be) {

			LOGGER.error("Ocurrio un error en cambiaEstatusNominaRechazarDispersion ", be);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNominaRechazarDispersion ", e);
			throw new BusinessException ("Ocurrio un error al realizar la operaci\u00f3n. Favor de intentarlo mas tarde");
		}
	}
}
