package mx.com.consolida.controller.ppp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.ClienteBO;
import mx.com.consolida.crm.service.interfaz.ClienteSeccionesBO;
import mx.com.consolida.crm.service.interfaz.NominaClienteBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatGenericoDTO;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.ppp.dto.FacturaMsDTO;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.FacturaBO;
import mx.com.consolida.ppp.service.interfaz.MultifacturaBO;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.ppp.service.interfaz.SerieFolioBO;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.documento.DocumentoMSDto;
import mx.com.facturacion.factura.CatMunicipioDTO;
import mx.com.facturacion.factura.ClienteDTO;
import mx.com.facturacion.factura.ConceptoDTO;
import mx.com.facturacion.factura.ConceptoFacturaDTO;
import mx.com.facturacion.factura.DomicilioDTO;
import mx.com.facturacion.factura.EmpresaDTO;
import mx.com.facturacion.factura.FacturaDTO;
import mx.com.facturacion.factura.ImpuestoDTO;
import mx.com.facturacion.factura.ImpuestoFacturaDTO;
import mx.com.facturacion.factura.Numero_Letras;

@Controller
@RequestMapping("ppp")
@SessionAttributes(value = { ReferenciaSeguridad.USUARIO_APLICATIVO })
public class MultifacturaController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MultifacturaController.class);

	@Autowired
	private SerieFolioBO serieBO;

	@Autowired
	private CatalogoDao catalogoDao;
	
	@Autowired
	private CatalogoBO catBo;

	@Autowired
	private NominaBO nominaBO;

	@Autowired
	private NominaClienteBO nominaClienteBO;

	@Autowired
	private FacturaBO facturaBO;

	@Autowired
	private PrestadoraServicioBO prestadoraServicioBO;

	@Autowired
	private ClienteBO clienteBO;

	@Autowired
	private ClienteSeccionesBO clienteSeccionesBO;
	
	@Autowired
	private  MultifacturaBO multifacturaBO;
	
	@Autowired
	private CelulaBO celulaBO;

	@Value("${urlZuul}")
	private String urlZuulServer;

	//@Value("${servicioFactura}")
	//private String servicioFactura;
	
	//@Value("${servicioFacturaNomina}")
	//private String servicioFacturaNomina;

	@Autowired
	private UsuarioBO usuarioBO;

	@RequestMapping(value = "/multifactura/cargaInicialMultifactura")
	@ResponseBody
	public Map<String, Object> cargaInicialMultifactura(Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {
			Long idCelula = usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario());
			String nombreRol = getUser().getRol().getNombre();

		/*lista celulas*/
				   
		    	dataReturn.put("catCelula", celulaBO.listarTodasLasCelulas());
		   
		    	FacturaDTO facturaDTO= new FacturaDTO();
		    	dataReturn.put("facturaDTO", facturaDTO);
			
			dataReturn.put("comboPac", catBo.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(CatMaestroEnum.PROVEEDOR_TIMBRADO.getCve()));

			dataReturn.put("rol", getUser().getRol().getIdRol());
			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialMultifactura ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/multifactura/cargaInicialMultifacturaClientes")
	@ResponseBody
	public Map<String, Object> cargaInicialMultifacturaClientes(@RequestBody Long idCelula) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();

		try {
		
				dataReturn.put("gridNominaCliente", nominaBO.listaClientesNominaByCelula(idCelula));

			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialMultifacturaClientes ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/multifactura/detalleNominasClienteByIdClient")
	@ResponseBody
	public List<NominaClienteDto> detalleNominasClienteByIdCliente(@RequestBody Long idCliente) throws BusinessException {
		
		try {
			    List<NominaClienteDto> nominas = null;
			 
				String nombreRol = getUser().getRol().getNombre();
				
				if(nombreRol != null && RolUsuarioENUM.NOMINISTA.getClave().equalsIgnoreCase(nombreRol)) {
					//Si es nominista se manda el usuario nominista
					nominas = nominaClienteBO.listaNominaClient(idCliente, getUser().getIdUsuario());
				}else {
					//Si es nominista se manda el usuario nominista
					nominas = nominaClienteBO.listaNominaClient(idCliente, null);
				}
					 
					
			    if(nominas!=null && !nominas.isEmpty()) {
			    	return nominas;
			    }else {
			    	return Collections.emptyList();
			    }

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasClienteByIdCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
//si
	@RequestMapping(value = "/multifactura/listaNominasFacturar")
	@ResponseBody
	public Map<String, Object> listaNominasFacturar(@RequestBody NominaDto  nominaCliente  , Model model) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();
		try {
			String nombreRol = getUser().getRol().getNombre();
			
			String listClientesPatino =facturaBO.obtenerClientesPatino(nominaCliente.getClienteDto().getIdCliente(), nominaCliente.getRfcPrestadora());
	        if(listClientesPatino!=null) {
	        	dataReturn.put("gridNominasProceso", nominaBO.listaNominaAFacturar(listClientesPatino, 0l));
	        }
	           else { 
	        	   dataReturn.put("gridNominasProceso", nominaBO.listaNominaAFacturar(null, nominaCliente.getClienteDto().getIdCliente() ));
		        
			
			} 
			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominasFacturar ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/multifactura/listaFacturasDisponibles")
	@ResponseBody
	public Map<String, Object> listaFacturasDisponibles(@RequestBody NominaDto  nominaCliente , Model model) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();
		try {

			String listClientesPatino =facturaBO.obtenerClientesPatino(nominaCliente.getClienteDto().getIdCliente(), nominaCliente.getRfcPrestadora());
	        if(listClientesPatino!=null) {
	        	dataReturn.put("gridFacturaDisponible",
						facturaBO.obtenerFacturasDisponibles(listClientesPatino, 0l));
	        }    else { 
			dataReturn.put("gridFacturaDisponible",
						facturaBO.obtenerFacturasDisponibles(null, nominaCliente.getClienteDto().getIdCliente()));
	        }
			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaFacturasDisponibles ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/multifactura/listaFacturasGeneradas")
	@ResponseBody
	public Map<String, Object> listaFacturasGeneradas(@RequestBody Long idCliente, Model model) throws BusinessException {
		Map<String, Object> dataReturn = new HashMap<>();
		try {
	             dataReturn.put("gridFacturaGeneradas",
						facturaBO.obtenerFacturasGeneradas(idCliente));
			
			return dataReturn;
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaEnProcesoByIdCliente ", e);
			throw new BusinessException("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/multifactura/cargaNominasVinculadas")
	@ResponseBody
	public List<NominaDto> cargaNominasVinculadas(@RequestBody Long idFactura) throws BusinessException {
		try {
			List<NominaDto> lista = new ArrayList<NominaDto>();

			lista = multifacturaBO.listaNominaVinculadasFactura(idFactura);
		return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaNominasVinculadas ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		}
	
	
	@RequestMapping(value = "/multifactura/getFacturacionByIdNominaCliente")
	@ResponseBody
	public FacturaDTO cargaInicialFacturacion(@RequestBody Long idNominaCliente) throws BusinessException {
		try {
			//Obtener el id de la nomina padre
			FacturaDTO facturaDTO=null;
			
				
			 facturaDTO = facturaBO.obtenerFacturaByIdNominaCliente(idNominaCliente);
			
			if(facturaDTO != null && facturaDTO.getIdPPPNominaFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdPPPNominaFactura()));
			}
			
			return facturaDTO;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException ("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/guardarConceptoMulti", method = RequestMethod.POST)
	@ResponseBody
	public FacturaDTO guardarConceptoMul(@RequestBody FacturaDTO facturaDTO) throws BusinessException {
		FacturaDTO facturaDto = null;
		try {
			if (facturaDTO == null) {
				LOGGER.error("Ocurrio un error al guardar la factura, facturaDTO viene null ");
				return facturaDto;
			}
		
		 facturaDto=facturaBO.guardarFactura(facturaDTO, getUser().getIdUsuario());
		return facturaDto;
			
	} catch (Exception e) {

		LOGGER.error("Ocurrio un error en guardarConceptoMul ", e);
		throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
}
	}
	
	
	@RequestMapping(value = "/nominas/vinculaFacturaNomina", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> vinculaFacturaNomina(@RequestBody FacturaDTO facturaDTO) throws BusinessException {
		try {
		//FacturaDTO facturaDto = null;
		facturaBO.vincularFacturaNomina(facturaDTO, getUser().getIdUsuario());
		
		return new ResponseEntity<String>(HttpStatus.OK);
		}
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarConceptoMul ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		

	}
	
	@RequestMapping(value = "/multifactura/agregarConceptoPlus")
	@ResponseBody

	public ResponseEntity<String> agregarConceptoPlus(@RequestBody FacturaDTO facturaDTO) throws BusinessException {
		try {
		
		facturaBO.guardarConceptoFacturaPlus(facturaDTO, getUser().getIdUsuario());
		
		return new ResponseEntity<String>(HttpStatus.OK);
	
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en agregarConceptoPlus ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
		
	}
	
	

	@RequestMapping(value = "/nominas/eliminarConceptoMul")
	@ResponseBody
	public ResponseEntity<String> eliminarConceptoMulti(@RequestBody ConceptoFacturaDTO conceptoFactura) {

		facturaBO.eliminarConceptoFactura(conceptoFactura);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@RequestMapping(value = "/multifactura/getFacturacionByNominaCliente")
	@ResponseBody
	public FacturaDTO getFacturacionByNominaCliente(@RequestBody Long idNominaClientePPP) throws BusinessException {
		try {
			FacturaDTO facturaDTO = null;

			facturaDTO = facturaBO.obtenerFacturaByIdNominaCliente(idNominaClientePPP);

			if (facturaDTO != null && facturaDTO.getIdFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPFactura(facturaDTO.getIdFactura()));
			}

			return facturaDTO;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacionNominaCliente ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	//si
	@RequestMapping(value = "/multifactura/getFactura")
	@ResponseBody
	public FacturaDTO getFactura(@RequestBody Long idFactura) throws BusinessException {
		try {
			FacturaDTO facturaDTO = null;

			facturaDTO = facturaBO.obtenerFactura(idFactura);
		//	facturaDTO = facturaBO.obtenerFacturaByIdNomina(idFactura);

			if (facturaDTO != null && facturaDTO.getIdFactura() != null) {
				facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(idFactura));
			//	facturaDTO.setConceptos(facturaBO.obtenerConceptosFacturaByIdPPPFactura(idFactura));
			//	facturaDTO.setConceptosPlus(facturaBO.obtenerConceptosPlusFacturaByIdPPPFactura(idFactura));
				
			}

			return facturaDTO;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacionNominaCliente ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	

	@RequestMapping(value = "/nominas/obtenerDocumentosPppFactura", method = RequestMethod.POST)
	@ResponseBody
	public List<DocumentoCSMDto> obtenerDocumentosPppFactura(@RequestBody Long idPppFactura) throws BusinessException {
		try {
			if (idPppFactura == null) {
				LOGGER.error("Ocurrio un error en obtenerDocumentosPppFactura, idPppFactura viene null ");
				return new ArrayList<DocumentoCSMDto>();
			}
		//	return nominaBO.listDocumentosPppFactura(idPppFactura);
			return nominaBO.listDocumentosPppNomina(idPppFactura);
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerDocumentosPppNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}

	@RequestMapping(value = "/nominas/preFacturaMul")
	@ResponseBody
	public DocumentoMSDto preFacturaMul(@RequestBody FacturaDTO factura) throws BusinessException {
		DocumentoMSDto documento = new DocumentoMSDto();
		try {
			RestTemplate restTemplate = new RestTemplate();
			//FacturaDTO facturaDTO = facturaBO.obtenerFacturaByIdNominaCliente(factura.getIdFactura());
		//	FacturaDTO facturaDTO = facturaBO.obtenerFacturaByIdFactura(factura.getIdFactura());
			FacturaDTO facturaDTO = facturaBO.obtenerFactura(factura.getIdFactura());
			facturaDTO.setTipoCambio("1");
			if (facturaDTO != null && facturaDTO.getIdFactura() != null) {
				List<ConceptoDTO> listConcepto = null;
				//listConcepto =facturaBO.obtenerConceptosFacturaByIdPPPFactura(facturaDTO.getIdFactura());
				//listConcepto.addAll(facturaBO.obtenerConceptosPlusFacturaByIdPPPFactura(facturaDTO.getIdFactura()));
				listConcepto =facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdFactura());
				
				facturaDTO.setConceptos(listConcepto);

			List<ImpuestoFacturaDTO> impuestosFactura = new ArrayList<>();
		
			BigDecimal total=new BigDecimal(0);
				for (ConceptoDTO conceptoDTO : facturaDTO.getConceptos()) {
					if (conceptoDTO.getImpuestos() != null && !conceptoDTO.getImpuestos().isEmpty()) {
						List<ImpuestoDTO> impuestoDTOs = conceptoDTO.getImpuestos();
						for (ImpuestoDTO impuestoDTO : impuestoDTOs) {
							
						 total=	total.add(impuestoDTO.getTotalImpuesto());


							String impuestosDescripcion = impuestoDTO.getTipo().getDescripcion() + " "
									+ impuestoDTO.getTotalImpuesto().setScale(2, BigDecimal.ROUND_FLOOR);

							conceptoDTO.setImpuestosDescripcion(impuestosDescripcion);
						}
					}
			 
				}
				ImpuestoFacturaDTO impuesto = new ImpuestoFacturaDTO(1,"IVA Trasladado 16%", total);
				impuestosFactura.add(impuesto);
		
			
				facturaDTO.setImpuestosFactura(impuestosFactura);
			}

			// PRESTADORA (EMPRESA) DATOS GENERALES Y DOMICILIO
			PrestadoraServicioDto prestadoraServicioDto = null;

			if (facturaDTO.getIdPrestadoraServicio() != null && facturaDTO.getIdPrestadoraServicio() > 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(
						new PrestadoraServicioDto(), facturaDTO.getIdPrestadoraServicio());
			} else {
			
			}

			// Emisor
			facturaDTO.setEmpresa(datosEmisor(prestadoraServicioDto));
			facturaDTO.setDomicilioEmpresa(domicilioEmisor(prestadoraServicioDto));

			// CLIENTE DATOS GENERALES Y DIRECCION
			ClienteDto cliente = null;
			if (facturaDTO.getIdCliente() != null && facturaDTO.getIdCliente() > 0) {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(facturaDTO.getIdCliente());
			} else {
				//cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(clienteDto.getIdCliente());
			}

			DomicilioComunDto domicilio = null;

			if (facturaDTO.getIdCliente() != null && facturaDTO.getIdCliente() > 0) {
				domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(facturaDTO.getIdCliente()));
			} else {
			//	domicilio = clienteSeccionesBO.obtenerDatosDomicilioByCliente(new ClienteDto(clienteDto.getIdCliente()));
			}

			// Cliente
			facturaDTO.setCliente(datosCliente(cliente));
			facturaDTO.setDomicilioCliente(domicilioCliente(domicilio));

			// Monto en pesos
			facturaDTO.setMontoLetra(Numero_Letras.cantidadConLetra(facturaDTO.getTotales().getTotal().toString()));
			//String ruta=urlZuulServer + servicioFactura + "/vistaPreviafactura";
			String base64= restTemplate.postForObject(urlZuulServer + "/api/factura/vistaPreviafactura", facturaDTO, String.class);

		//	String base64 = restTemplate.postForObject(urlZuulServer + servicioFactura + "/vistaPreviafactura",facturaDTO, String.class);
			documento.setDocumentoBase64(base64);
			documento.setMimeType("data:application/pdf;base64,");
	//		documento.setDocumentoBase64("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9MdIuMCqFTNI2cukb3qZZgAKo+9SvUwDiu9CUk8KsGlpBZMmmcmSrEiOW2Kag1VvEOpEVxi8/LIg118SrH7HJY+spLv/Wn2LEPOO+jKlf9g/B9rc4esmX6X/rl5dH4VdEJJ6wS3ggOo4DB+U0fxA8A8l+g8nIiC+8F24hcZTND0lY7dE3jJC0RP5B0I+tPxqx/8Ag0vugAplbmRzdHJlYW0KZW5kb2JqCjEgMCBvYmoKPDwvVGFicy9TL0dyb3VwPDwvUy9UcmFuc3BhcmVuY3kvVHlwZS9Hcm91cC9DUy9EZXZpY2VSR0I+Pi9Db250ZW50cyA1IDAgUi9UeXBlL1BhZ2UvUmVzb3VyY2VzPDwvQ29sb3JTcGFjZTw8L0NTL0RldmljZVJHQj4+L1Byb2NTZXQgWy9QREYgL1RleHQgL0ltYWdlQiAvSW1hZ2VDIC9JbWFnZUldL0ZvbnQ8PC9GMSAyIDAgUj4+L1hPYmplY3Q8PC9pbWcxIDQgMCBSL2ltZzAgMyAwIFI+Pj4+L1BhcmVudCA2IDAgUi9NZWRpYUJveFswIDAgNjEyIDc5Ml0+PgplbmRvYmoKNyAwIG9iagpbMSAwIFIvWFlaIDAgODAyIDBdCmVuZG9iagoyIDAgb2JqCjw8L1N1YnR5cGUvVHlwZTEvVHlwZS9Gb250L0Jhc2VGb250L0hlbHZldGljYS9FbmNvZGluZy9XaW5BbnNpRW5jb2Rpbmc+PgplbmRvYmoKNiAwIG9iago8PC9LaWRzWzEgMCBSXS9UeXBlL1BhZ2VzL0NvdW50IDEvSVRYVCgyLjEuNyk+PgplbmRvYmoKOCAwIG9iago8PC9OYW1lc1soSlJfUEFHRV9BTkNIT1JfMF8xKSA3IDAgUl0+PgplbmRvYmoKOSAwIG9iago8PC9EZXN0cyA4IDAgUj4+CmVuZG9iagoxMCAwIG9iago8PC9OYW1lcyA5IDAgUi9UeXBlL0NhdGFsb2cvUGFnZXMgNiAwIFIvVmlld2VyUHJlZmVyZW5jZXM8PC9QcmludFNjYWxpbmcvQXBwRGVmYXVsdD4+Pj4KZW5kb2JqCjExIDAgb2JqCjw8L01vZERhdGUoRDoyMDIyMDMyNDE1MjQwNC0wNicwMCcpL0NyZWF0b3IoSmFzcGVyUmVwb3J0cyBMaWJyYXJ5IHZlcnNpb24gNi4yLjApL0NyZWF0aW9uRGF0ZShEOjIwMjIwMzI0MTUyNDA0LTA2JzAwJykvUHJvZHVjZXIoaVRleHQgMi4xLjcgYnkgMVQzWFQpPj4KZW5kb2JqCnhyZWYKMCAxMgowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwNDc4NjkgMDAwMDAgbiAKMDAwMDA0ODE3OSAwMDAwMCBuIAowMDAwMDAwMDE1IDAwMDAwIG4gCjAwMDAwMjg2MTUgMDAwMDAgbiAKMDAwMDA0NTI5NCAwMDAwMCBuIAowMDAwMDQ4MjY3IDAwMDAwIG4gCjAwMDAwNDgxNDQgMDAwMDAgbiAKMDAwMDA0ODMzMCAwMDAwMCBuIAowMDAwMDQ4Mzg0IDAwMDAwIG4gCjAwMDAwNDg0MTYgMDAwMDAgbiAKMDAwMDA0ODUyMCAwMDAwMCBuIAp0cmFpbGVyCjw8L0luZm8gMTEgMCBSL0lEIFs8N2Y3M2U3NTU0MGMwMjBmMjcxNzYyZTJlMzZmYzU5NjE+PDE2ZDYzZDdmOWUzOTlhYjM1ZWYzNTU2ODViMGNkZDQ1Pl0vUm9vdCAxMCAwIFIvU2l6ZSAxMj4+CnN0YXJ0eHJlZgo0ODY4OAolJUVPRgo=");

			return documento;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@RequestMapping(value = "/multifactura/generarFacturaMulti")

	@ResponseBody
	public FacturaMsDTO generarFacturaMulti(@RequestBody FacturaDTO factura) throws BusinessException {
		try {
			RestTemplate restTemplate = new RestTemplate();
			//FacturaDTO facturaDTO = facturaBO.obtenerFacturaByIdFactura(factura.getIdFactura());
			FacturaDTO facturaDTO = facturaBO.obtenerFactura(factura.getIdFactura());
  			facturaDTO.setIdUsuarioAplicativo(getUser().getIdUsuario());
			 facturaDTO.getIdPrestadoraServicio();
		//	facturaDTO.setIdPrestadoraServicio(nominaDto.getPrestadoraServicioDto().getIdPrestadoraServicio());
			facturaDTO.setTipoCambio("1");
			if (facturaDTO != null && facturaDTO.getIdFactura() != null) {
				List<ConceptoDTO> listConcepto = null;
				//listConcepto =facturaBO.obtenerConceptosFacturaByIdPPPFactura(facturaDTO.getIdFactura());
				//listConcepto.addAll(facturaBO.obtenerConceptosPlusFacturaByIdPPPFactura(facturaDTO.getIdFactura()));
				listConcepto =facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdFactura());
				
				facturaDTO.setConceptos(listConcepto);
				//facturaDTO.setConceptos(	facturaBO.obtenerConceptosFacturaByIdPPPNominaFactura(facturaDTO.getIdPPPNominaFactura()));
				List<ImpuestoFacturaDTO> impuestosFactura = new ArrayList<>();

				BigDecimal total=new BigDecimal(0);
				for (ConceptoDTO conceptoDTO : facturaDTO.getConceptos()) {
					if (conceptoDTO.getImpuestos() != null && !conceptoDTO.getImpuestos().isEmpty()) {
						List<ImpuestoDTO> impuestoDTOs = conceptoDTO.getImpuestos();
						for (ImpuestoDTO impuestoDTO : impuestoDTOs) {
							 total=	total.add(impuestoDTO.getTotalImpuesto());

								String impuestosDescripcion = impuestoDTO.getTipo().getDescripcion() + " "
										+ impuestoDTO.getTotalImpuesto().setScale(2, BigDecimal.ROUND_FLOOR);

								conceptoDTO.setImpuestosDescripcion(impuestosDescripcion);
						}
					}
				}
				ImpuestoFacturaDTO impuesto = new ImpuestoFacturaDTO(1,"IVA Trasladado 16%", total);
				impuestosFactura.add(impuesto);
		

				facturaDTO.setImpuestosFactura(impuestosFactura);
			}

			// PRESTADORA (EMPRESA) DATOS GENERALES Y DOMICILIO 
			PrestadoraServicioDto prestadoraServicioDto = null;

			if (facturaDTO.getIdPrestadoraServicio() != null && facturaDTO.getIdPrestadoraServicio() > 0) {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(
						new PrestadoraServicioDto(), facturaDTO.getIdPrestadoraServicio());
			} else {
				prestadoraServicioDto = prestadoraServicioBO.obtenerPrestadoraServicioByIdDomicilio(
						new PrestadoraServicioDto(), facturaDTO.getIdPrestadoraServicio());
			}

			// Emisor 
			facturaDTO.setEmpresa(datosEmisor(prestadoraServicioDto));
			facturaDTO.setDomicilioEmpresa(domicilioEmisor(prestadoraServicioDto));

			// CLIENTE DATOS GENERALES Y DIRECCION 
		     ClienteDto cliente = null;
			if (facturaDTO.getIdCliente() != null && facturaDTO.getIdCliente() > 0) {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(facturaDTO.getIdCliente());
			} else {
				cliente = clienteBO.getDatosGeneralesClienteBiIdCliente(facturaDTO.getIdCliente());
			}

			DomicilioComunDto domicilio = null;

			if (facturaDTO.getIdCliente() != null && facturaDTO.getIdCliente() > 0) {
				domicilio = clienteSeccionesBO
						.obtenerDatosDomicilioByCliente(new ClienteDto(facturaDTO.getIdCliente()));
			} else {
				domicilio = clienteSeccionesBO
						.obtenerDatosDomicilioByCliente(new ClienteDto(facturaDTO.getIdCliente()));
			}

			// Cliente 
			facturaDTO.setCliente(datosCliente(cliente));
			facturaDTO.setDomicilioCliente(domicilioCliente(domicilio));
			facturaDTO.getCliente().setUsoCFDI(facturaDTO.getUsoCFDI());

			// Monto en pesos
			facturaDTO.setMontoLetra(Numero_Letras.cantidadConLetra(facturaDTO.getTotales().getTotal().toString()));

			facturaDTO.setIsMultifactura(1);
			// PAC timbrado seleccionado
			facturaDTO.setPacTimbrado(factura.getPacTimbrado());
		//	String ruta=urlZuulServer + servicioFactura + "/generarFactura";
	    //FacturaMsDTO facturaMsDTO = restTemplate.postForObject(urlZuulServer +  "api/factura/generarFactura", facturaDTO, FacturaMsDTO.class);
			FacturaMsDTO facturaMsDTO= restTemplate.postForObject(urlZuulServer +"/api/factura/generarFactura40", facturaDTO, FacturaMsDTO.class);

		//	FacturaMsDTO facturaMsDTO = restTemplate.postForObject(urlZuulServer + servicioFactura + "/generarFactura", facturaDTO, FacturaMsDTO.class);
		

			return facturaMsDTO;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialFacturacion Mu", e);
			new FacturaMsDTO("500", "Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");

		}
	}

	private DomicilioDTO domicilioCliente(DomicilioComunDto domicilio) {
		DomicilioDTO domicilioCliente = new DomicilioDTO();
		domicilioCliente.setIdDomicilio(domicilio.getDomicilio().getIdDomicilio().intValue());
		domicilioCliente.setCodigoPostal(domicilio.getDomicilio().getCodigoPostal());
		domicilioCliente.setCalle(domicilio.getDomicilio().getCalle());
		domicilioCliente.setNumExterior(domicilio.getDomicilio().getNumeroExterior());
		domicilioCliente.setNumInterior(domicilio.getDomicilio().getNumeroInterior());
		domicilioCliente.setColonia(domicilio.getDomicilio().getColonia());

		CatMunicipioDTO catMunicipioCliente = new CatMunicipioDTO();
		catMunicipioCliente.setIdCatMunicipio(domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral() != null
				? domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral().intValue()
				: null);
		catMunicipioCliente.setClave(domicilio.getDomicilio().getCatMunicipios().getClave() != null
				? domicilio.getDomicilio().getCatMunicipios().getClave()
				: null);
		catMunicipioCliente.setMunicipio(domicilio.getDomicilio().getCatMunicipios().getDescripcion() != null
				? domicilio.getDomicilio().getCatMunicipios().getDescripcion().toUpperCase()
				: null);
		domicilioCliente.setCatMunicipio(catMunicipioCliente);

		CatGeneralDto catEntidadFed = domicilio.getDomicilio().getCatEntidadFederativa();

		CatGenericoDTO catEstadoPaisCliente = new CatGenericoDTO();
		catEstadoPaisCliente
				.setId(catEntidadFed.getIdCatGeneral() != null ? catEntidadFed.getIdCatGeneral().intValue() : null);
		catEstadoPaisCliente.setClave(catEntidadFed.getClave() != null ? catEntidadFed.getClave() : null);
		catEstadoPaisCliente.setDescripcion(
				catEntidadFed.getDescripcion() != null ? catEntidadFed.getDescripcion().toUpperCase() : null);
		domicilioCliente.setCatEstadoPais(catEstadoPaisCliente);

		CatGenericoDTO catPaisCliente = new CatGenericoDTO();
		catPaisCliente.setDescripcion("M\u00c9XICO".toUpperCase());
		domicilioCliente.setCatPais(catPaisCliente);
		return domicilioCliente;
	}

	private ClienteDTO datosCliente(ClienteDto cliente) {
		ClienteDTO clienteFactura = new ClienteDTO();
		if (cliente.getRazonSocial() != null) {
			clienteFactura.setRazonSocial(cliente.getRazonSocial());
		} else {
			clienteFactura.setRazonSocial(cliente.getApellidoMaterno() != null
					? cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno()
					: cliente.getNombre() + " " + cliente.getApellidoPaterno());
		}

		clienteFactura.setRfc(cliente.getRfc());
		clienteFactura.setRegimenFiscal(cliente.getCatRegimenFiscal());
		
		return clienteFactura;
	}

	private EmpresaDTO datosEmisor(PrestadoraServicioDto prestadoraServicioDto) {
		EmpresaDTO empresaDto = new EmpresaDTO();
		empresaDto.setIdEmpresa(prestadoraServicioDto.getIdPrestadoraServicio().intValue());
		empresaDto.setNombre(prestadoraServicioDto.getNombreCorto());
		empresaDto.setRazonSocial(prestadoraServicioDto.getRazonSocial());
		empresaDto.setRfc(prestadoraServicioDto.getRfc());

		return empresaDto;
	}

	public DomicilioDTO domicilioEmisor(PrestadoraServicioDto prestadoraServicioDto) {

		DomicilioDTO domicilioEmpresa = new DomicilioDTO();
		domicilioEmpresa.setCalle(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCalle());
		domicilioEmpresa.setNumExterior(
				prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroExterior());
		domicilioEmpresa.setNumInterior(
				prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroInterior());
		domicilioEmpresa
				.setColonia(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getColonia());
		domicilioEmpresa.setCodigoPostal(
				prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal());

		CatGeneralDto catEntidadFed = prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio()
				.getCatEntidadFederativa();
		CatGenericoDTO catEstadoPais = new CatGenericoDTO();
		catEstadoPais
				.setId(catEntidadFed.getIdCatGeneral() != null ? catEntidadFed.getIdCatGeneral().intValue() : null);
		catEstadoPais.setClave(catEntidadFed.getClave() != null ? catEntidadFed.getClave() : null);
		catEstadoPais.setDescripcion(
				catEntidadFed.getDescripcion() != null ? catEntidadFed.getDescripcion().toUpperCase() : null);
		domicilioEmpresa.setCatEstadoPais(catEstadoPais);

		CatGeneralDto catMunicipios = prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio()
				.getCatMunicipios();
		CatMunicipioDTO catMunicipio = new CatMunicipioDTO();
		catMunicipio.setIdCatMunicipio(
				catMunicipios.getIdCatGeneral() != null ? catMunicipios.getIdCatGeneral().intValue() : null);
		catMunicipio.setClave(catMunicipios.getClave() != null ? catMunicipios.getClave() : null);
		catMunicipio.setMunicipio(
				catMunicipios.getDescripcion() != null ? catMunicipios.getDescripcion().toUpperCase() : null);
		domicilioEmpresa.setCatMunicipio(catMunicipio);

		CatGenericoDTO catPais = new CatGenericoDTO();
		catPais.setDescripcion("M\u00c9XICO".toUpperCase());
		domicilioEmpresa.setCatPais(catPais);

		return domicilioEmpresa;
	}

}