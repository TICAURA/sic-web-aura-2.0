package mx.com.consolida.crm.controller.celula;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.usuario.UsuarioDTO;

@Controller
@RequestMapping("admin")
@SessionAttributes(value={ReferenciaSeguridad.RFC_CELULA})
public class CelulaController extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CelulaController.class);
	
	@Autowired
	private CelulaBO celulaBO;

	
	@RequestMapping(value = "/celula",method = RequestMethod.GET)
	@ResponseBody
	public List<CelulaDto> celulaCargaInicial() throws BusinessException {
		
		try {
			List<CelulaDto> celula = celulaBO.listarTodasLasCelulas();

			return celula;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en celulaCargaInicial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	
	
	
	
	@RequestMapping(value = "/celula/administrador",method = RequestMethod.POST)
	@ResponseBody
	public List<CelulaDto> guardarAdministrador(@RequestBody CelulaDto celulaDTO) throws BusinessException {
		
		try {
			celulaBO.guardarAdministrador(celulaDTO,getUser().getIdUsuario());
			
			List<CelulaDto> celula = celulaBO.listarTodasLasCelulas();

			return celula;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en celulaCargaInicial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value="/celula" ,method = RequestMethod.POST)
	@ResponseBody
	public List<CelulaDto> guardarCelula(@RequestBody CelulaDto celulaDto) throws BusinessException {
		try {


		celulaBO.guardarCelula(celulaDto, getUser().getIdUsuario());
		List<CelulaDto> celula = celulaBO.listarTodasLasCelulas();
		
		return celula;
		}catch (BusinessException be) {
		       LOGGER.error("Error al guardar la celula. CelulaController.guardarCelula:: ",be.getMessage(), be);
		       throw be;
	    } catch (Exception e) {
	       LOGGER.error("Error al guardar la celula. CelulaController.guardarCelula:: ",e);
	       	throw new BusinessException(e.getCause().getMessage());
	    }
	}
	
	
	@RequestMapping(value = "/celulaAdministrador",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> celulaUsuarioActual() throws BusinessException {
		Map<String, Object> response = new HashMap<String, Object>();
		
		try {
			CelulaDto celula = celulaBO.consultarDatosCelula(RolUsuarioENUM.ADMINISTRADOR_CELULA.getId(),getUser().getIdUsuario());
			List<UsuarioDTO> usuariosCelula =   celulaBO.consultarUsuariosCelula(celula.getIdCelula());
							   
			
			response.put("celula", celula);				
			response.put("usuariosCelula", usuariosCelula);	
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en celulaCargaInicial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
			
		return response;
	}
	
	
	
	@RequestMapping(value = "/celula/integranteCelula",method = RequestMethod.POST)
	@ResponseBody
	public List<UsuarioDTO> guardarIntegranteCelula(@RequestBody CelulaDto celulaDTO) throws BusinessException {
		
		try {
			celulaBO.guardarIntegranteCelula(celulaDTO,getUser().getIdUsuario());
			
			List<UsuarioDTO> usuariosCelula =   celulaBO.consultarUsuariosCelula(celulaDTO.getIdCelula());

			return usuariosCelula;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en celulaCargaInicial ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
}
