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
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.facturacion.factura.SeguimientoNominaDto;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class SeguimientoNominaController extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FinanzasController.class);
	
	@Autowired
	private NominaBO nominaBO;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	@RequestMapping(value = "/seguimientoNomina/cargaInicial")
	@ResponseBody
	public Map<String, Object> cargaInicialNomina(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasFinanzasByCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()), 7L));
				dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/seguimientoNomina/buscarNominas")
	@ResponseBody
	public Map<String, Object> buscarNominas(@RequestBody SeguimientoNominaDto seguimientoNomina) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
				seguimientoNomina.setIdCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()));
				dataReturn.put("gridNominasParaDispersion", nominaBO.listaNominasSeguimiento(seguimientoNomina));
				dataReturn.put("catEstatusNomina", nominaBO.obtenerCatEstatusNomina());
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en buscarNominas ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
		


}
