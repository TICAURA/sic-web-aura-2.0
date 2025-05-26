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
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.CatFolioDto;
import mx.com.consolida.ppp.dto.CatSerieDto;
import mx.com.consolida.ppp.service.interfaz.SerieFolioBO;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class SerieFolioController  extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SerieFolioController.class);
	
	@Autowired
	private SerieFolioBO serieBO;
	
	@Autowired
	private CatalogoDao catalogoDao;
	
	
	@RequestMapping(value = "/serieFolio/cargaInicialSerie")
	@ResponseBody
	public Map<String, Object> cargaInicialSerieFolio(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {

				dataReturn.put("comboCelula", catalogoDao.obtenerCatCelula());
				dataReturn.put("comboTipoComprobante", catalogoDao.obtenerCatGeneralNominaByClvMaestro(CatMaestroEnum.CAT_TIPO_COMPROBANTE.getCve()));
				dataReturn.put("gridSeries", serieBO.listaCatSerie());
//				dataReturn.put("gridFolios", serieBO.listaCatFolio());
//				dataReturn.put("listaCatSerie", serieBO.listaCatSerie());
			
				return dataReturn;
			
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialSerieFolio ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/serieFolio/guardarSerie")
	@ResponseBody
	public MensajeDTO guardaSerie(@RequestBody CatSerieDto catSerieDto,  Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(catSerieDto == null) {
				LOGGER.error("Ocurrio un error en guardaSerie,  catSerieDto viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar");
				return mensajeDTO;
			}
			
			if (catSerieDto == null
					|| catSerieDto.getCatCelula()== null 
					|| (catSerieDto.getCatCelula()!= null && catSerieDto.getCatCelula().getIdCatGeneral() == null)
					
					|| catSerieDto.getCatTipoComprobante()== null 
					|| (catSerieDto.getCatTipoComprobante()!= null && catSerieDto.getCatTipoComprobante().getIdCatGeneral() == null)
					
					|| catSerieDto.getNombreSerie() == null) {
				throw new BusinessException("", "");
			}

			if(!serieBO.guardarActualizarSerie(catSerieDto, getUser().getIdUsuario())){
				LOGGER.error("Ocurrio un error en guardaSerie - guardarActualizarSerie");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar en Series");
				return mensajeDTO;
				
			}
			
			return mensajeDTO;
			
		}catch (BusinessException be){
			
			if (catSerieDto == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar y seleccionar alguna opcion");
				return mensajeDTO;
			
			}else if (catSerieDto.getCatTipoComprobante()== null 
					|| (catSerieDto.getCatTipoComprobante()!= null && catSerieDto.getCatTipoComprobante().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar 'Tipo de comprobante'");
				return mensajeDTO;
			
			}else if (catSerieDto.getCatCelula()== null 
					|| (catSerieDto.getCatCelula()!= null && catSerieDto.getCatCelula().getIdCatGeneral() == null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar 'Celula'");
				return mensajeDTO;
			
			}else if (catSerieDto.getNombreSerie() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'Nombre de la serie'");
				return mensajeDTO;
			}
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaSerie ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		
		return mensajeDTO;
	}

	@RequestMapping(value = "/serieFolio/eliminarSerie")
	@ResponseBody
	public MensajeDTO eliminarSerie(@RequestBody Long idCatSerie,  Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(idCatSerie == null) {
				LOGGER.error("Ocurrio un error en eliminarSerie,  idCatSerie viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar");
				return mensajeDTO;
			}
			


			if(!serieBO.eliminarSerie(idCatSerie, getUser().getIdUsuario())){
				LOGGER.error("Ocurrio un error en eliminarSerie - eliminarSerie");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al eliminar");
				return mensajeDTO;
				
			}
			
			return mensajeDTO;
			
		}catch (BusinessException be){
			
			if (idCatSerie == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
				return mensajeDTO;
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarSerie ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		
		return mensajeDTO;
	}
	
	@RequestMapping(value = "/serieFolio/guardarFolio")
	@ResponseBody
	public MensajeDTO guardarFolio(@RequestBody CatFolioDto catFolioDto,  Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(catFolioDto == null) {
				LOGGER.error("Ocurrio un error en guardarFolio,  catFolioDto viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar");
				return mensajeDTO;
			}
			
			if (catFolioDto.getCatSerieDto() == null || 
					(catFolioDto.getCatSerieDto() !=null && catFolioDto.getCatSerieDto().getIdCatSerie()==null)
					|| catFolioDto.getNumeroFolio() == null) {
				throw new BusinessException("", "");
			}

			if(!serieBO.guardarActualizarFolio(catFolioDto, getUser().getIdUsuario())){
				LOGGER.error("Ocurrio un error en guardarFolio - guardarActualizarSerie");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar en Folios");
				return mensajeDTO;
				
			}
			
			return mensajeDTO;
			
		}catch (BusinessException be){
			
			if (catFolioDto.getCatSerieDto() == null || 
					(catFolioDto.getCatSerieDto() !=null && catFolioDto.getCatSerieDto().getIdCatSerie()==null)) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe seleccionar 'serie'");
				return mensajeDTO;
			}
			
			if (catFolioDto.getNumeroFolio() == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Debe ingresar 'N\u00famero de folio'");
				return mensajeDTO;
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarFolio ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		
		return mensajeDTO;
	}
	
	@RequestMapping(value = "/serieFolio/eliminarFolio")
	@ResponseBody
	public MensajeDTO eliminarFolio(@RequestBody Long idCatFolio,  Model model) throws BusinessException {
		
		MensajeDTO mensajeDTO = new MensajeDTO();
		
		try {
			
			if(idCatFolio == null) {
				LOGGER.error("Ocurrio un error en eliminarFolio,  idCatFolio viene null");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al guardar");
				return mensajeDTO;
			}
			


			if(!serieBO.eliminarFolio(idCatFolio, getUser().getIdUsuario())){
				LOGGER.error("Ocurrio un error en eliminarFolio - eliminarFolio");
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error al eliminar");
				return mensajeDTO;
				
			}
			
			return mensajeDTO;
			
		}catch (BusinessException be){
			
			if (idCatFolio == null) {
				mensajeDTO.setCorrecto(false);
				mensajeDTO.setMensajeError("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
				return mensajeDTO;
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarFolio ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde");
		}
		
		return mensajeDTO;
	}

}
