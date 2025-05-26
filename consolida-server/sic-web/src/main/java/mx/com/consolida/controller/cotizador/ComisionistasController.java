package mx.com.consolida.controller.cotizador;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.ComisionistaDto;
import mx.com.consolida.dto.OficinaDto;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.admin.CanalVentaBO;
import mx.com.consolida.service.admin.ComisionistaBO;
import mx.com.consolida.service.interfaz.CatalogoBO;

@RestController
@RequestMapping("/comisiones")
//@SessionAttributes(value={ ReferenciaSeguridad.CANAL_VENTA, ReferenciaSeguridad.OFICINA_CANAL_VENTA})
public class ComisionistasController extends  BaseController {
	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private CanalVentaBO canalVentaBO;
	
	@Autowired
	private ComisionistaBO comisionistaBO;
	
	@RequestMapping(value = "/canalesVenta", method = RequestMethod.GET)
	public List<ComisionistaDto> canalesVenta(Model model){
		
		model.addAttribute(ReferenciaSeguridad.CANAL_VENTA,0L);
		
		return comisionistaBO.listarTodosComisionistas();
			
	}
	
	@RequestMapping(value = "/catalogoCanalesVenta", method = RequestMethod.GET)
	public  Map<String , Object> catalogoCanalesVenta(){
		
		Map<String, Object> catalogosIniciales = new HashedMap();
		
		catalogosIniciales.put("oficinas", canalVentaBO.obtenerOficinas());
		
		return catalogosIniciales; 
	}
	/**
	 * 
	 * @param comisionista
	 * @return
	 */
	@RequestMapping(value = "/comisionista", method = RequestMethod.GET)
	public Map<String , Object> comisionista(Model model){
		Map<String, Object> catalogosIniciales = new HashedMap();
		
		//catalogosIniciales.put("canalesVenta", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CANAL_VENTA.getCve()));
		catalogosIniciales.put("entidadFederativa" ,catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		catalogosIniciales.put("bancos" ,catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_BANCOS.getCve()));
		catalogosIniciales.put("conceptoFDI" ,catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_CONCEPTOSFDI.getCve()));
		catalogosIniciales.put("tipoPago" ,catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_TIPO_PAGO.getCve()));
		catalogosIniciales.put("subtipoPago" ,catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_SUBTIPOPAGO.getCve()));
		
		Object canalVentaEditar = model.asMap().get(ReferenciaSeguridad.CANAL_VENTA);
		if(canalVentaEditar != null && ((Long)canalVentaEditar) > 0) {
			
			catalogosIniciales.put("canalVentaEditar",canalVentaBO.obtenerCanalVentaByIdCanalVenta((Long)canalVentaEditar));
		}
		
		return catalogosIniciales;
	}
	
	
	@RequestMapping(value = "/editarComisionista", method = RequestMethod.GET)
	public Map<String , Object> oficinaCanalVenta(Model model){
		Map<String, Object> catalogosIniciales = new HashedMap();
		
		catalogosIniciales.put("entidadFederativa" ,catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		catalogosIniciales.put("bancos" ,catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_BANCOS.getCve()));
		
		Object oficinaCanalVentaEditar = model.asMap().get(ReferenciaSeguridad.OFICINA_CANAL_VENTA);
		if(oficinaCanalVentaEditar != null && ((Long)oficinaCanalVentaEditar) > 0) {
			//TODO Construir el metodo para recuperar la oficina del canal de venta
			catalogosIniciales.put("oficinaEditar",canalVentaBO.oficinaByIdOficina((Long)oficinaCanalVentaEditar));
		}
		
		return catalogosIniciales;
	}
	
	

	
	@RequestMapping(value = "/oficinaCanalVenta", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> guardarCanalVenta(@RequestBody OficinaDto oficina){
		
		oficina.getDomicilio();
		oficina.getCuentaBancaria();
		
		canalVentaBO.guardarOficinaCanalVenta(oficina, getUser().getIdUsuario());
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/canalVenta", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> guardarCanalVenta(@RequestBody CanalVentaDto canalVenta){
		
		canalVentaBO.guardarCanalVenta(canalVenta, getUser().getIdUsuario());
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/editarCanalVenta", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editar(@RequestBody Long idCanalVenta , Model model){
		

		model.addAttribute(ReferenciaSeguridad.CANAL_VENTA,idCanalVenta);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "/editarOficinaCanalVenta", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> editarOficina(@RequestBody Long idOficinaCanalVenta , Model model){
		

		model.addAttribute(ReferenciaSeguridad.OFICINA_CANAL_VENTA,idOficinaCanalVenta);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/asignarOficinaCanalVenta", method = RequestMethod.POST)
	@ResponseBody
	public List<CanalVentaDto> asignarOficinaCanalVenta(@RequestBody CanalVentaDto canalVentaDto){
		
		canalVentaDto.getIdCanalVenta();
		canalVentaDto.getOficina();

		canalVentaBO.asignacionOficinaCanalVenta(canalVentaDto, getUser().getIdUsuario());
		
		return canalVentaBO.listarTodasCanalesVenta();
	}
	
	
	
	
	

	
}
