package mx.com.consolida.controller.cotizador;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.dto.CanalVentaDto;
import mx.com.consolida.dto.CatatologosCotizadorDto;
import mx.com.consolida.dto.ClienteTempBitacoraDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CostoAdicionalDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.CotizacionTotalesDto;
import mx.com.consolida.dto.RentabilidadDto;
import mx.com.consolida.dto.ResultadoDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatCotizadorEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.reportes.JSONParser;
import mx.com.consolida.service.admin.CanalVentaBO;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.interfaz.CatalogoCotizadorBO;
import mx.com.consolida.service.interfaz.ClienteTempBO;
import mx.com.consolida.service.interfaz.CotizadorBO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@Controller
@RequestMapping("/cotizador")
@SessionAttributes(value = {ReferenciaSeguridad.USUARIO_APLICATIVO,
    ReferenciaSeguridad.CLIENTE_TEMP, ReferenciaSeguridad.COTIZADOR, ReferenciaSeguridad.CATS_COTIZADOR, ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES})
public class CotizacionController {
	private static Logger LOGGER = LoggerFactory.getLogger(CotizacionController.class);
	
	@Value("${urlReportes}")
	private String urlReportes;
	
    @Autowired
    private CotizadorBO cotizadorBO;

    @Autowired
    private CatalogoBO catBo;

    @Autowired
    private CatalogoCotizadorBO catalogoCotizadorBO;

    @Autowired
    private colaboradorFormulasController cotizacionFormulas;
    @Autowired
	private ClienteTempBO nuevoClienteBO;
	@Autowired
	private CanalVentaBO canalVentaBo;

    @RequestMapping(value = "/version")
    @ResponseBody
    public String version() {
        return "1.0";
    }

    @RequestMapping(value = "/generarReporteCotizacion")
    @ResponseBody
    public HashMap<String, Object> generarReporteCotizacion(Model model) throws BusinessException {
        CotizacionTotalesDto totales = new CotizacionTotalesDto();
        
        HashMap<String, Object> documento = new HashMap<String, Object>();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR)) > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
            ClienteTempDto cliente = nuevoClienteBO.obtenerClienteById(cot.getIdCliente().getIdClienteTemp());
            CanalVentaDto canalVenta = canalVentaBo.obtenerCanalVentaByIdCanalVenta(cliente.getIdCanalVenta());
            inicializarValoresFaltantes(cot);
            totales = cotizadorBO.obtenerTotalByIdCotizacion(cot.getIdCotizacion());
            totales.setResultado(new ResultadoDto());
            File file;
            if (cot.getIdImss().getClave().equals("IMMS-CL")) {
            	totales.getTotalacfaIVA();
            }
            if (cot.getIdImss().getIdCatGeneral() == 9 || cot.getIdImss().getIdCatGeneral() == 53) {
                llenarTotalesPPP(totales,cot);
                file = new File(urlReportes + "/cotizacionPPP.jrxml");
            } else {
                llenarTotales(totales,cot);
                file = new File(urlReportes + "/cotizacionMixta.jrxml");
            }
            if(cot.getIdTipoSolCotizacion().getClave().equals(catBo.obtenerCatGeneralByClv("TIP_COT_GEN").getClave())) {
            	realizarRentabilidad(totales, cot);
            }else {
            	totales.setRentabilidadImss(new RentabilidadDto());
            	totales.setRentabilidadPPP(new RentabilidadDto());
            	totales.setRentabilidadTotal(new RentabilidadDto());
            }
            try {
            	JasperReport reporte = JasperCompileManager.compileReport(new FileInputStream(file));
            	Map<String, Object> datos = new HashMap<>();
            	if(cot.getIdTipoSolCotizacion().getClave().equals("TIP_COT_BAS")){
            		datos.put("encabezado", "PRE COTIZACI\u00d3N");
            	}else if(cot.getIdTipoSolCotizacion().getClave().equals("TIP_COT_GEN")) {
            		datos.put("encabezado", "COTIZACI\u00d3N");
            	}
            	
            	datos.put("cotizador", cot);
            	datos.put("total", totales);
            	datos.put("cliente", cliente);
            	datos.put("canalVenta", canalVenta);
            	final StringBuilder json = new StringBuilder(JSONParser.convertirObjetoAJSON(datos));
	            ByteArrayInputStream jsonInput;
	            jsonInput = new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
	            final JsonDataSource dataSource = new JsonDataSource(jsonInput);
	            Map<String, Object> params = new HashMap<String, Object>();
	            params.put("SUBREPORT_DIR", urlReportes);
	            final JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, params, dataSource);
	            Base64 base64 = new Base64();
	    		String encodedFile =  base64.encodeToString(JasperExportManager.exportReportToPdf(jasperPrint));
	            documento.put("documento", encodedFile);
            } catch (Exception e) {
    			LOGGER.error("Error consulta de reporte plantilla",e);
    		}
        }
        return documento;
    }
    
    @RequestMapping(value = "/guardarValoresCotizacion")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void cotizar(@RequestBody CotizacionDto cotizacion, Model model) throws BusinessException {
    	Long idClienteTempBitacoraSolicitudes = null;
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		
		if (cotizacion.getIdVacaciones().getIdCatGeneral() == 1L) {
			cotizacion.setAguinaldo(new BigDecimal(15));
			cotizacion.setPrimaVacacional(new BigDecimal(25));
		}
		
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES)) {
			idClienteTempBitacoraSolicitudes = ((Long) model.asMap().get(ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES));
		}
    	
        if (cotizacion.getIdCotizacion() != null) {
//			edita
			if(cotizacion.getIdTipoSolCotizacion().getClave().equals("TIP_COT_BAS")) {
				cotizacion.setIdTipoSolCotizacion(catBo.obtenerCatGeneralByClv("TIP_COT_BAS"));
			}else {
				cotizacion.setIdTipoSolCotizacion(catBo.obtenerCatGeneralByClv("TIP_COT_GEN"));
			}
			
			cotizadorBO.editarCotizacion(cotizacion, usuarioAplicativo);
			
        } else {
//				GUARDA
            cotizacion.setFechaAlta(new Date());
            cotizacion.setIdCliente(new ClienteTempDto((Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP)));
            cotizacion.setIdTipoSolCotizacion(catBo.obtenerCatGeneralByClv("TIP_COT_GEN"));
            cotizacion = cotizadorBO.guardarCotizacion(cotizacion,usuarioAplicativo, idClienteTempBitacoraSolicitudes);
            model.addAttribute(ReferenciaSeguridad.COTIZADOR, cotizacion.getIdCotizacion());
        }
    }

    @RequestMapping(value = "/guardarValoresPreCotizacion")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void guardarValoresPreCotizacion(@RequestBody CotizacionDto cotizacion, Model model) throws BusinessException {
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if (cotizacion.getIdVacaciones().getIdCatGeneral() == 1L) {
			cotizacion.setAguinaldo(new BigDecimal(15));
			cotizacion.setPrimaVacacional(new BigDecimal(25));
		}
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
        if (cotizacion.getIdCotizacion() != null) {
//				EDITAR
            cotizacion.setIdTipoSolCotizacion(catBo.obtenerCatGeneralByClv("TIP_COT_BAS"));
            cotizadorBO.editarCotizacion(cotizacion,usuarioAplicativo);
            model.addAttribute(ReferenciaSeguridad.COTIZADOR, cotizacion.getIdCotizacion());
        } else {
//				GUARDA
            cotizacion.setIdCliente(new ClienteTempDto((Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP)));
            cotizacion.setIdTipoSolCotizacion(catBo.obtenerCatGeneralByClv("TIP_COT_BAS"));
            cotizacion = cotizadorBO.guardarCotizacion(cotizacion,usuarioAplicativo, null);
            model.addAttribute(ReferenciaSeguridad.COTIZADOR, cotizacion.getIdCotizacion());
        }
    }

    @RequestMapping(value = "/cargaInicial")
    @ResponseBody
    public CotizacionDto cargaInicial(Model model) throws BusinessException {
    	try {
    		
            if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue() > 0) {
                CotizacionDto cotizacion = cotizadorBO.obtenerCotizacionById(((Number)   model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue());
                if(cotizacion.getCostoAdicional()!=null && cotizacion.getCostoAdicional().getIdCostoAdicional()!=null) {
                	 return cotizacion;
                }else {
                	cotizacion.setCostoAdicional(new CostoAdicionalDto());
                }
                return cotizacion;
            } else {
                CotizacionDto cotizacion = new CotizacionDto();
                cotizacion.setCostoAdicional(new CostoAdicionalDto());
                return cotizacion;
            }
    	}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicial ",e );
			return new CotizacionDto();
		}

    }

    @RequestMapping(value = "/cargarColaboradores")
    @ResponseBody
    public Map<String, Object> cargarColaboradores(Model model) throws BusinessException {
        Map<String, Object> datosExcel = new HashMap<String, Object>();
        List<EmpleadoDTO> rowsData = new ArrayList<EmpleadoDTO>();
        datosExcel.put("contentRows", rowsData);
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue() > 0) {
            List<ColaboradoresTempDto> cotizacion = cotizadorBO.obtenercotizacionesColaboradoresByIdCot(((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue());
            for (ColaboradoresTempDto colab : cotizacion) {
                EmpleadoDTO emp = new EmpleadoDTO();
                emp.setSalarioDiario(colab.getSalarioDiario());
                emp.setGravados(colab.getGravados());
                emp.setExentos(colab.getExentos());
                emp.setFechaAntiguedad(colab.getFechaAntiguedad()!=null ?colab.getFechaAntiguedad() : new Date());
                emp.setNetoNomina(colab.getNetoNomina());
                emp.setAsimilados(colab.getAsimilados());
                emp.setaOtros(colab.getOtros());
                emp.setDgPrimaDeRiesgo(colab.getPrimaDeRiesgo());
                rowsData.add(emp);
            }
            
            List<EmpleadoDTO> listEmpleados = rowsData.stream().sorted((o1, o2)-> {
         	   if(o1.getSalarioDiario().compareTo(o2.getSalarioDiario()) == 0) {
 	        	   if(o1.getNetoNomina().compareTo(o2.getNetoNomina()) == 0) {
 	        		   if(o1.getGravados().compareTo(o2.getGravados()) == 0) {
 	        			   if(o1.getExentos().compareTo(o2.getExentos()) == 0) {
 	        				   if(o1.getFechaAntiguedad().compareTo(o2.getFechaAntiguedad()) == 0) {
 	        					   if(o1.getAsimilados().compareTo(o2.getAsimilados()) == 0) {
 	        						   if(o1.getaOtros().compareTo(o2.getaOtros())==0) {
 	        							   return o1.getDgPrimaDeRiesgo().compareTo(o2.getDgPrimaDeRiesgo());
 	        						   } else {
 	        							   return o1.getaOtros().compareTo(o2.getaOtros());
 	        						   }
 	        					   }else {
 	        						   return o1.getAsimilados().compareTo(o2.getAsimilados());
 	        					   }
 	        				   } else {
 	        					   return o1.getFechaAntiguedad().compareTo(o2.getFechaAntiguedad());
 	        				   }
 	        			   }else {
 	        				   return o1.getExentos().compareTo(o2.getExentos());
 	        			   }
 	        		   }else {
 	        			   return o1.getGravados().compareTo(o2.getGravados());
 	        		   }
 	        	   }else {
 	        		   return o1.getNetoNomina().compareTo(o2.getNetoNomina());  
 	        	   }
         	   } else {
         		   return o1.getSalarioDiario().compareTo(o2.getSalarioDiario());
         	   }
         		   
         	   }).collect(Collectors.toList());
            
            datosExcel.put("contentRows", listEmpleados);
        }
        return datosExcel;
    }

    @RequestMapping(value = "/cargartotales")
    @ResponseBody
    public CotizacionTotalesDto cargartotales(Model model) throws BusinessException {
        CotizacionTotalesDto totales = new CotizacionTotalesDto();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue() > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById(((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue());

            totales = cotizadorBO.obtenerTotalByIdCotizacion(cot.getIdCotizacion());
            totales.setResultado(new ResultadoDto());
            if (cot.getIdImss().getIdCatGeneral() == 9 || cot.getIdImss().getIdCatGeneral() == 53) {
                llenarTotalesPPP(totales,cot);
            } else {
                llenarTotales(totales,cot);
            }
            if(cot.getIdTipoSolCotizacion().getClave().equals(catBo.obtenerCatGeneralByClv("TIP_COT_GEN").getClave())) {
            	realizarRentabilidad(totales,cot);
            }
        }
        return totales;
    }


    @RequestMapping(value = "/limpiarEjecutarCotizacion")
    @ResponseBody
    public void limpiarEjecutarCotizacion(Model model) {
    	 if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR)) > 0) {
             CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
             cotizadorBO.eliminadoLogicoEmpleados(cot.getIdCotizacion());
             cotizadorBO.eliminadoLogicocotizacionColaborador(cot.getIdCotizacion());
             cotizadorBO.eliminadoLogicoCotizacionTotal(cot.getIdCotizacion());
    	 }
    }
    @SuppressWarnings("unused")
    @RequestMapping(value = "/ejecutarCotizacion")
    @ResponseBody
    public CotizacionTotalesDto ejecutarCotizacion(Model model, @RequestBody List<EmpleadoDTO> empleados, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
        CotizacionTotalesDto totales = new CotizacionTotalesDto();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR)) > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
            CatatologosCotizadorDto cat = (CatatologosCotizadorDto) model.asMap().get(ReferenciaSeguridad.CATS_COTIZADOR);
            totales = cotizacionFormulas.ejecutarCotizacion(cot, empleados, cat, usuarioAplicativo);
           
        }
        return totales;
    }
    
    @SuppressWarnings("unused")
    @RequestMapping(value = "/obtenerTotalesCotizacion")
    @ResponseBody
    public CotizacionTotalesDto obtenerTotalesCotizacion(Model model, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
        CotizacionTotalesDto totales = new CotizacionTotalesDto();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR)) > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
            totales =  cotizacionFormulas.obtenerTotalesCotizacion(cot, usuarioAplicativo);
            totales.setResultado(new ResultadoDto());
            if (cot.getIdImss().getIdCatGeneral() == 9 || cot.getIdImss().getIdCatGeneral() == 53) {
                llenarTotalesPPP(totales,cot);
            } else {
                llenarTotales(totales,cot);
            }
            if(cot.getIdTipoSolCotizacion().getClave().equals(catBo.obtenerCatGeneralByClv("TIP_COT_GEN").getClave())) {
            	realizarRentabilidad(totales,cot);
            }
        }
        return totales;
    }
    

    @RequestMapping(value = "/seleccionCotizacion")
    @ResponseStatus(HttpStatus.OK)
//	@ResponseBody
    public void seleccionCotizacion(@RequestBody Long idCotizacion, Model model) {
        model.addAttribute(ReferenciaSeguridad.COTIZADOR, idCotizacion);
//		return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/seleccionCotizacionVerAutorizador")
	@ResponseBody
    public Map<String, Object> seleccionCotizacionVerAutorizador(@RequestBody Long idClienteTemp, Model model) {
    	Map<String, Object> regresar = new HashMap<String, Object>();
    	CotizacionDto cot = new CotizacionDto();
    	try {
    		cot = cotizadorBO.obtenercotizacionAutorizar(idClienteTemp); 
    	} catch (Exception e) {
    		regresar.put("mensaje", "Error");
		}
    	if(cot!=null && cot.getIdCotizacion()!=null) {
    		model.addAttribute(ReferenciaSeguridad.COTIZADOR, cot.getIdCotizacion());
    		regresar.put("mensaje", "Exito");
    	}else {
    		regresar.put("mensaje", "Error");
    	}
        return regresar; 
    }
    
        
    @RequestMapping(value = "/nuevaCotizacion/agregarColaborador")
    @ResponseBody
    public Map<String, Object> agregarColaborador(@RequestBody EmpleadoDTO colaborador, Model model) {
        Map<String, Object> datosExcel = new HashMap<String, Object>();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Number) model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue() > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById(((Number)   model.asMap().get(ReferenciaSeguridad.COTIZADOR)).longValue());
            CatatologosCotizadorDto cat = (CatatologosCotizadorDto) model.asMap().get(ReferenciaSeguridad.CATS_COTIZADOR);
            List<EmpleadoDTO> rowsData = new ArrayList<EmpleadoDTO>();

            datosExcel.put("contentRows", rowsData);

            if (colaborador.getColaboradoresGuardados() != null) {
                rowsData.addAll(colaborador.getColaboradoresGuardados());
            } else {
                List<String> headerData = new ArrayList<String>();                
                headerData.add("Salario diario");
                headerData.add("GRAVADOS");
                headerData.add("EXENTOS");
                headerData.add("FECHA DE ANTIGUEDAD");
                headerData.add("NETO NOMINA");
                headerData.add("ASIMILADOS");
                headerData.add("OTROS");
                headerData.add("PRIMA DE RIESGO");
            }

            EmpleadoDTO rowData = new EmpleadoDTO();
            rowData.setSalarioDiario(new BigDecimal(String.valueOf(colaborador.getSalarioDiario())));
            rowData.setGravados(colaborador.getGravados() != null ? new BigDecimal(String.valueOf(colaborador.getGravados())) : new BigDecimal(0));
            rowData.setExentos(colaborador.getExentos() != null ? new BigDecimal(String.valueOf(colaborador.getExentos())) : new BigDecimal(0));
            rowData.setFechaAntiguedad(colaborador.getFechaAntiguedad());
            rowData.setNetoNomina(colaborador.getNetoNomina() != null ? new BigDecimal(String.valueOf(colaborador.getNetoNomina())) : new BigDecimal(0));
            rowData.setAsimilados(colaborador.getAsimilados() != null ? new BigDecimal(String.valueOf(colaborador.getAsimilados())) : new BigDecimal(0));
            rowData.setaOtros(colaborador.getaOtros() != null ? new BigDecimal(String.valueOf(colaborador.getaOtros())) : new BigDecimal(0));
            rowData.setDgPrimaDeRiesgo(colaborador.getDgPrimaDeRiesgo());
            rowsData.add(rowData);
            rowsData = cotizacionFormulas.obtenerSalarios(rowsData, cot, cat);
            
            
        }
        return datosExcel;
    }

    @RequestMapping(value = "/guardarBitacora")
    @ResponseBody
    public void guardarClienteNuevo( @RequestBody ClienteTempBitacoraDto bitacora,  Model model) throws BusinessException {
    	
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		Long idUsuarioTemp;
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		if (model.containsAttribute(ReferenciaSeguridad.CLIENTE_TEMP)) {
			idUsuarioTemp = (Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP);
			bitacora.setIdClienteTemp(idUsuarioTemp);
		}
    	
        cotizadorBO.guardarBitacora(bitacora,usuarioAplicativo);
    }

    @RequestMapping(value = "/guardarSolicitudCotizacion")
    @ResponseBody
    public void guardarSolicitudCotizacion(@RequestBody ClienteTempBitacoraSolicitudesDto solicitarCotizacion, UsuarioAplicativo usuarioAplicativoBorrar, Model model) throws BusinessException {
        try {
        	UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
    		Long idUsuarioTemp;
    		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
    			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
    		} else {
    			if(usuarioAplicativoBorrar != null && usuarioAplicativoBorrar.getIdUsuario() != null) {
    				usuarioAplicativo = usuarioAplicativoBorrar;
    			}
    		}
    		if (model.containsAttribute(ReferenciaSeguridad.CLIENTE_TEMP)) {
    			idUsuarioTemp = (Long) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP);
    			solicitarCotizacion.setIdClienteTemp(idUsuarioTemp);
    		}
            if (solicitarCotizacion.getObservacion() == null) {
                throw new BusinessException("", "");
            }

            cotizadorBO.guardarSolicitudCotizacion(solicitarCotizacion, usuarioAplicativo);
        } catch (BusinessException be) {
            throw new BusinessException(be.getCodigo(), be.getMessage());
        }
    }

    @RequestMapping(value = "/solicitarAutorizacionCotizacion")
    @ResponseBody
    public void solicitarAutorizacionCotizacion(@RequestBody Long idCotizacion) throws BusinessException {
    	CotizacionDto cotizacion = cotizadorBO.obtenerCotizacionById(idCotizacion);
        try {

            if (cotizacion.getIdCotizacion() == null) {
                throw new BusinessException("", "");
            }

            cotizadorBO.solicitarAutorizacionCotizacion(cotizacion);
        } catch (BusinessException be) {
            throw new BusinessException(be.getCodigo(), be.getMessage());
        }
    }

    public void llenarCatalogosCotizador(Model model) {
    	
        CatatologosCotizadorDto cat = new CatatologosCotizadorDto();
        cat.setSmg(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.SMG.getCve()));
        cat.setSmgzf(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.SMGZF.getCve()));
        cat.setUma(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.UMA.getCve()));
        cat.setIsn(catalogoCotizadorBO.obtenerSalarioGeneralByClave(CatCotizadorEnum.ISN.getCve()));
        cat.setCfp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CFP.getCve()));
        cat.setEp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.EP.getCve()));
        cat.setEt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.ET.getCve()));
        cat.setGmp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.GMP.getCve()));
        cat.setGmt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.GMT.getCve()));
        cat.setPdp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDP.getCve()));
        cat.setPdt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDT.getCve()));
        cat.setIvp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.IVP.getCve()));
        cat.setIvt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.IVT.getCve()));
        cat.setPdgp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.PDGP.getCve()));
        cat.setRp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.RP.getCve()));
        cat.setCvp(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CVP.getCve()));
        cat.setCvt(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.CVT.getCve()));
        cat.setAip(catalogoCotizadorBO.otenerImssByClave(CatCotizadorEnum.AIP.getCve()));
        model.addAttribute(ReferenciaSeguridad.CATS_COTIZADOR, cat);
    }

    public void llenarTotalesPPP(CotizacionTotalesDto totales, CotizacionDto cot) {
        totales.getResultado().setNetoImmsActual(redondearDecimales(totales.getTotalassNetoNomina().doubleValue(),2));
        totales.getResultado().setNetoImmsNomCliente(redondearDecimales(totales.getTotalgsssNetoNomina().doubleValue(),2));
        totales.getResultado().setNetoImmsPPP(0);
        totales.getResultado().setNetoImmsTotal(redondearDecimales(totales.getResultado().getNetoImmsNomCliente() - totales.getResultado().getNetoImmsPPP(),2));
        totales.getResultado().setNetoImmsCAP(redondearDecimales(totales.getResultado().getNetoImmsActual() - totales.getResultado().getNetoImmsTotal(),2));
        
        totales.getResultado().setNetoEstrategiaActual(redondearDecimales(totales.getTotalaasNetoAsimilados().doubleValue() + totales.getTotalaOtros().doubleValue(),2));
        totales.getResultado().setNetoEstrategiaNomCliente(0);
        totales.getResultado().setNetoEstrategiaPPP(redondearDecimales(totales.getTotalgsePlanPensionesPrivada().doubleValue(),2));
        totales.getResultado().setNetoEstrategiaTotal(redondearDecimales(totales.getResultado().getNetoEstrategiaNomCliente() + totales.getResultado().getNetoEstrategiaPPP(),2));
        totales.getResultado().setNetoEstrategiaCAP(redondearDecimales(totales.getResultado().getNetoEstrategiaActual() - totales.getResultado().getNetoEstrategiaTotal(),2));
        
        totales.getResultado().setTotalDispColActual(redondearDecimales(totales.getResultado().getNetoImmsActual() + totales.getResultado().getNetoEstrategiaActual(),2));
        totales.getResultado().setTotalDispColNomCliente(redondearDecimales(totales.getResultado().getNetoImmsNomCliente() + totales.getResultado().getNetoEstrategiaNomCliente(),2));
        totales.getResultado().setTotalDispColPPP(redondearDecimales(totales.getResultado().getNetoImmsPPP() + totales.getResultado().getNetoEstrategiaPPP(),2));
        totales.getResultado().setTotalDispColTotal(redondearDecimales(totales.getResultado().getNetoImmsTotal() + totales.getResultado().getNetoEstrategiaTotal(),2));
        totales.getResultado().setTotalDispColCAP(redondearDecimales(totales.getResultado().getTotalDispColActual() - totales.getResultado().getTotalDispColTotal(),2));
        
        totales.getResultado().setTotalDeduccionActual(redondearDecimales(totales.getTotalaasISR().doubleValue() + totales.getTotalassISR().doubleValue() + totales.getTotalassCoImss().doubleValue(),2));
        totales.getResultado().setTotalDeduccionNomCliente(redondearDecimales(totales.getTotalgsssISR().doubleValue() + totales.getTotalgsssCoImss().doubleValue(),2));
        totales.getResultado().setTotalDeduccionPPP(0);
        totales.getResultado().setTotalDeduccionTotal(redondearDecimales(totales.getResultado().getTotalDeduccionNomCliente() + totales.getResultado().getTotalDeduccionPPP(),2));
        totales.getResultado().setTotalDeduccionCAP(redondearDecimales(totales.getResultado().getTotalDeduccionActual() - totales.getResultado().getTotalDeduccionTotal(),2));
        
        totales.getResultado().setTotalPercepcionesActual(redondearDecimales(totales.getResultado().getTotalDispColActual() + totales.getResultado().getTotalDeduccionActual(),2));
        totales.getResultado().setTotalPercepcionesNomCliente(redondearDecimales(totales.getResultado().getTotalDispColNomCliente() + totales.getResultado().getTotalDeduccionNomCliente(),2));
        totales.getResultado().setTotalPercepcionesPPP(redondearDecimales(totales.getResultado().getTotalDispColPPP() + totales.getResultado().getTotalDeduccionPPP(),2));
        totales.getResultado().setTotalPercepcionesTotal(redondearDecimales(totales.getResultado().getTotalDispColTotal() + totales.getResultado().getTotalDeduccionTotal(),2));
        totales.getResultado().setTotalPercepcionesCAP(redondearDecimales(totales.getResultado().getTotalPercepcionesActual() - totales.getResultado().getTotalPercepcionesTotal(),2));
        
        totales.getResultado().setCargaSocialActual(redondearDecimales(totales.getTotalacfaCargaSocial().doubleValue(),2));
        totales.getResultado().setCargaSocialNomCliente(redondearDecimales(totales.getTotalgscfpppCargaSocial().doubleValue(),2));
        totales.getResultado().setCargaSocialPPP(0);
        totales.getResultado().setCargaSocialTotal(redondearDecimales(totales.getResultado().getCargaSocialNomCliente() + totales.getResultado().getCargaSocialPPP(),2));
        totales.getResultado().setCargaSocialCAP(redondearDecimales(totales.getResultado().getCargaSocialActual() - totales.getResultado().getCargaSocialTotal(),2));
        
        totales.getResultado().setSubtotalNominaActual(redondearDecimales(totales.getResultado().getTotalPercepcionesActual() + totales.getResultado().getCargaSocialActual(),2));
        totales.getResultado().setSubtotalNominaNomCliente(redondearDecimales(totales.getResultado().getTotalPercepcionesNomCliente() + totales.getResultado().getCargaSocialNomCliente(),2));
        totales.getResultado().setSubtotalNominaPPP(redondearDecimales(totales.getResultado().getTotalPercepcionesPPP() + totales.getResultado().getCargaSocialPPP(),2));
        totales.getResultado().setSubtotalNominaTotal(redondearDecimales(totales.getResultado().getTotalPercepcionesTotal() + totales.getResultado().getCargaSocialTotal(),2));
        totales.getResultado().setSubtotalNominaCAP(redondearDecimales(totales.getResultado().getSubtotalNominaActual()- totales.getResultado().getSubtotalNominaTotal(),2));
        
        totales.getResultado().setComisionActual(redondearDecimales(totales.getTotalacfaComision().doubleValue(),2));
        totales.getResultado().setComisionNomCliente(0);
        totales.getResultado().setComisionPPP(redondearDecimales(totales.getTotalgscfpppComision().doubleValue(),2));
        totales.getResultado().setComisionTotal(redondearDecimales(totales.getResultado().getComisionNomCliente() + totales.getResultado().getComisionPPP(),2));
        totales.getResultado().setComisionCAP(redondearDecimales(totales.getResultado().getComisionActual() - totales.getResultado().getComisionTotal(),2));
        
        totales.getResultado().setCostoTotalActual(redondearDecimales(totales.getResultado().getSubtotalNominaActual() + totales.getResultado().getComisionActual(),2));
        totales.getResultado().setCostoTotalNomCliente(redondearDecimales(totales.getResultado().getSubtotalNominaNomCliente() + totales.getResultado().getComisionNomCliente(),2));
        totales.getResultado().setCostoTotalPPP(redondearDecimales(totales.getResultado().getSubtotalNominaPPP() + totales.getResultado().getComisionPPP(),2));
        totales.getResultado().setCostoTotalTotal(redondearDecimales(totales.getResultado().getComisionTotal() + totales.getResultado().getSubtotalNominaTotal(),2));
        totales.getResultado().setCostoTotalCAP(redondearDecimales(totales.getResultado().getCostoTotalActual() - totales.getResultado().getCostoTotalTotal(),2));
        
//        totales.getResultado().setIvaActual(redondearDecimales(totales.getTotalgscfmSubtotalFactura().doubleValue(),2));
        totales.getResultado().setIvaActual(redondearDecimales(totales.getTotalacfaIVA().doubleValue(),2));
        totales.getResultado().setIvaNomCliente(0);
        totales.getResultado().setIvaPPP(redondearDecimales(totales.getResultado().getCostoTotalPPP() * 0.16,2));
        totales.getResultado().setIvaTotal(redondearDecimales(totales.getResultado().getIvaNomCliente() + totales.getResultado().getIvaPPP(),2));
        totales.getResultado().setIvaCAP(redondearDecimales(totales.getResultado().getIvaActual() - totales.getResultado().getIvaTotal(),2));
        
        totales.getResultado().setIvaTrasladoActual(redondearDecimales(totales.getResultado().getIvaActual(),2));
        totales.getResultado().setIvaTrasladoNomCliente(redondearDecimales(totales.getResultado().getIvaNomCliente(),2));
        totales.getResultado().setIvaTrasladoPPP(redondearDecimales(totales.getResultado().getIvaPPP(),2));
        totales.getResultado().setIvaTrasladoTotal(redondearDecimales(totales.getResultado().getIvaTotal(),2));
        totales.getResultado().setIvaTrasladoCAP(redondearDecimales(totales.getResultado().getIvaTrasladoActual() - totales.getResultado().getIvaTrasladoTotal(),2));
        
        
        totales.getResultado().setTotalFlujoActual(redondearDecimales(totales.getResultado().getCostoTotalActual() + totales.getResultado().getIvaTrasladoActual(),2));
        totales.getResultado().setTotalFlujoNomCliente(redondearDecimales(totales.getResultado().getCostoTotalNomCliente() + totales.getResultado().getIvaTrasladoNomCliente(),2));
        totales.getResultado().setTotalFlujoPPP(redondearDecimales(totales.getResultado().getCostoTotalPPP() + totales.getResultado().getIvaTrasladoPPP(),2));
        totales.getResultado().setTotalFlujoTotal(redondearDecimales(totales.getResultado().getCostoTotalTotal() + totales.getResultado().getIvaTrasladoTotal(),2));
        totales.getResultado().setTotalFlujoCAP(redondearDecimales(totales.getResultado().getTotalFlujoTotal() - totales.getResultado().getTotalFlujoActual(),2));
        totales.getResultado().setDiferenciaActualCostoTotal(redondearDecimales(totales.getResultado().getCostoTotalTotal() - totales.getResultado().getCostoTotalActual(),2));
        totales.getResultado().setDiferenciaActualFlujoTotal(redondearDecimales(totales.getResultado().getTotalFlujoActual() - totales.getResultado().getTotalFlujoTotal(),2));
    }
    
    public void llenarTotales(CotizacionTotalesDto totales, CotizacionDto cot) {
        totales.getResultado().setNetoImmsActual(redondearDecimales(totales.getTotalassNetoNomina().doubleValue(),2));
        totales.getResultado().setNetoImmsPPP(redondearDecimales(totales.getTotalgsssNetoNomina().doubleValue(),2));
        totales.getResultado().setNetoImmsCAP(redondearDecimales(totales.getResultado().getNetoImmsActual() - totales.getResultado().getNetoImmsPPP(),2));
        
        totales.getResultado().setNetoEstrategiaActual(redondearDecimales(totales.getTotalaasNetoAsimilados().doubleValue() + totales.getTotalaOtros().doubleValue(),2));
        totales.getResultado().setNetoEstrategiaPPP(redondearDecimales(totales.getTotalgsePlanPensionesPrivada().doubleValue(),2));
        totales.getResultado().setNetoEstrategiaCAP(redondearDecimales(totales.getResultado().getNetoEstrategiaActual() - totales.getResultado().getNetoEstrategiaPPP(),2));
        
        totales.getResultado().setTotalDispColActual(redondearDecimales(totales.getResultado().getNetoImmsActual() + totales.getResultado().getNetoEstrategiaActual(),2));
        totales.getResultado().setTotalDispColPPP(redondearDecimales(totales.getResultado().getNetoImmsPPP() + totales.getResultado().getNetoEstrategiaPPP(),2));
        totales.getResultado().setTotalDispColCAP(redondearDecimales(totales.getResultado().getTotalDispColActual() - totales.getResultado().getTotalDispColPPP(),2));
        
        totales.getResultado().setTotalDeduccionActual(redondearDecimales(totales.getTotalaasISR().doubleValue() + totales.getTotalassISR().doubleValue() + totales.getTotalassCoImss().doubleValue(),2));
        totales.getResultado().setTotalDeduccionPPP(redondearDecimales(totales.getTotalgsssISR().doubleValue() + totales.getTotalgsssCoImss().doubleValue(),2));
        totales.getResultado().setTotalDeduccionCAP(redondearDecimales(totales.getResultado().getTotalDeduccionActual() - totales.getResultado().getTotalDeduccionPPP(),2));
        
        totales.getResultado().setTotalPercepcionesActual(redondearDecimales(totales.getResultado().getTotalDispColActual() + totales.getResultado().getTotalDeduccionActual(),2));
        totales.getResultado().setTotalPercepcionesPPP(redondearDecimales(totales.getResultado().getTotalDispColPPP() + totales.getResultado().getTotalDeduccionPPP(),2));
        totales.getResultado().setTotalPercepcionesCAP(redondearDecimales(totales.getResultado().getTotalPercepcionesActual() - totales.getResultado().getTotalPercepcionesPPP(),2));
        
        totales.getResultado().setCargaSocialActual(redondearDecimales(totales.getTotalacfaCargaSocial().doubleValue(),2));
        totales.getResultado().setCargaSocialPPP(redondearDecimales(totales.getTotalgscfmCargaSocial().doubleValue(),2));
        totales.getResultado().setCargaSocialCAP(redondearDecimales(totales.getResultado().getCargaSocialActual() - totales.getResultado().getCargaSocialPPP(),2));
        
        totales.getResultado().setSubtotalNominaActual(redondearDecimales(totales.getResultado().getTotalPercepcionesActual() + totales.getResultado().getCargaSocialActual(),2));
        totales.getResultado().setSubtotalNominaPPP(redondearDecimales(totales.getResultado().getTotalPercepcionesPPP() + totales.getResultado().getCargaSocialPPP(),2));
        totales.getResultado().setSubtotalNominaCAP(redondearDecimales(totales.getResultado().getSubtotalNominaActual() - totales.getResultado().getSubtotalNominaPPP(),2));
        
        totales.getResultado().setComisionActual(redondearDecimales(totales.getTotalacfaComision().doubleValue(),2));
        totales.getResultado().setComisionPPP(redondearDecimales(totales.getTotalgscfmComision().doubleValue(),2));
        totales.getResultado().setComisionCAP(redondearDecimales(totales.getResultado().getComisionActual() - totales.getResultado().getComisionPPP(),2));
        
        totales.getResultado().setCostoTotalActual(redondearDecimales(totales.getResultado().getSubtotalNominaActual() + totales.getResultado().getComisionActual(),2));
        totales.getResultado().setCostoTotalPPP(redondearDecimales(totales.getResultado().getSubtotalNominaPPP() + totales.getResultado().getComisionPPP(),2));
        totales.getResultado().setCostoTotalCAP(redondearDecimales(totales.getResultado().getCostoTotalActual() - totales.getResultado().getCostoTotalPPP(),2));
        
//        totales.getResultado().setIvaActual(redondearDecimales(totales.getTotalgscfmSubtotalFactura().doubleValue(),2));
        totales.getResultado().setIvaActual(redondearDecimales(totales.getTotalacfaIVA().doubleValue(),2));
        totales.getResultado().setIvaPPP(redondearDecimales(totales.getResultado().getCostoTotalPPP() * 0.16,2));
        totales.getResultado().setIvaCAP(redondearDecimales(totales.getResultado().getIvaActual() - totales.getResultado().getIvaPPP(),2));
        
        totales.getResultado().setIvaTrasladoActual(redondearDecimales(totales.getResultado().getIvaActual(),2));
        totales.getResultado().setIvaTrasladoPPP(redondearDecimales(totales.getResultado().getIvaPPP(),2));
        totales.getResultado().setIvaTrasladoCAP(redondearDecimales(totales.getResultado().getIvaTrasladoActual() - totales.getResultado().getIvaTrasladoPPP() ,2));
        
        totales.getResultado().setTotalFlujoActual(redondearDecimales(totales.getResultado().getCostoTotalActual() + totales.getResultado().getIvaTrasladoActual(),2));
        totales.getResultado().setTotalFlujoPPP(redondearDecimales(totales.getResultado().getCostoTotalPPP() + totales.getResultado().getIvaTrasladoPPP(),2));
        totales.getResultado().setTotalFlujoCAP(redondearDecimales(totales.getResultado().getTotalFlujoActual() - totales.getResultado().getTotalFlujoPPP(),2));
        
        totales.getResultado().setDiferenciaActualCostoPPP(redondearDecimales(totales.getResultado().getCostoTotalActual() - totales.getResultado().getCostoTotalPPP(),2));
        totales.getResultado().setDiferenciaActualCostoCAP(redondearDecimales(totales.getResultado().getCostoTotalActual() - totales.getResultado().getCostoTotalPPP(),2));
        
        totales.getResultado().setDiferenciaActualFlujoPPP(redondearDecimales(totales.getResultado().getTotalFlujoActual() - totales.getResultado().getTotalFlujoPPP(),2));
        totales.getResultado().setDiferenciaActualFlujoCAP(redondearDecimales(totales.getResultado().getTotalFlujoActual() - totales.getResultado().getTotalFlujoPPP(),2));
    }
    
    public void realizarRentabilidad(CotizacionTotalesDto totales, CotizacionDto cot) {
    	RentabilidadDto rentabilidadImss = new RentabilidadDto();
    	RentabilidadDto rentabilidadPPP = new RentabilidadDto();
    	RentabilidadDto rentabilidadTotal = new RentabilidadDto();
    	
    	realizarRentabilidadImss(rentabilidadImss, totales, cot);
    	realizarRentabilidadPPP(rentabilidadPPP, totales, cot);
    	realizarRentabilidadTotal(rentabilidadTotal, rentabilidadImss, rentabilidadPPP);
    	
    	
    	totales.setRentabilidadImss(rentabilidadImss);
    	totales.setRentabilidadPPP(rentabilidadPPP);
    	totales.setRentabilidadTotal(rentabilidadTotal);

    }
    public void realizarRentabilidadImss(RentabilidadDto rent, CotizacionTotalesDto totales, CotizacionDto cot) {
    	double porcentajePromotorIMSS = cot.getCostoAdicional().getIdPorcentajePromotorImss().getValor().doubleValue() / 100;////rehacer nuevo campo
    	double porcentajeEstrategia = cot.getCostoAdicional().getIdPorcentajeCostoEstrategia().getValor().doubleValue() / 100;
    	double porcentajeIndirectos = cot.getCostoAdicional().getIdPorcentajeCostosIndirectos().getValor().doubleValue() / 100;

    	rent.setPeriodicidad(cot.getIdPeriodicidad().getDescripcionTipoPago());
    	rent.setTipoProducto(cot.getIdImss());
    	rent.setNumColaboradores(cot.getNumColaboradores());
    	
    	rent.setDispersion(redondearDecimales(totales.getResultado().getTotalPercepcionesPPP() - totales.getResultado().getNetoEstrategiaPPP(),2));
    	rent.setCargaSocial(redondearDecimales(totales.getResultado().getCargaSocialPPP(),2));
    	
    	double comisionImss = cot.getComisionImss()==null ? 0:cot.getComisionImss().doubleValue();
    	comisionImss = comisionImss/100; 
    	rent.setComisionServicios(redondearDecimales(comisionImss * (rent.getDispersion()+rent.getCargaSocial()),2));
    	rent.setSubtotalFactura(redondearDecimales(rent.getComisionServicios() + rent.getCargaSocial() + rent.getDispersion(),2));
    	rent.setIva(redondearDecimales(rent.getSubtotalFactura() * 0.16,2));
    	rent.setTotalFactura(redondearDecimales(rent.getSubtotalFactura() + rent.getIva(),2));
    	///////////La parte de arriba esta validad
    	rent.setComisionEstimadaPromotor(redondearDecimales(porcentajePromotorIMSS * rent.getComisionServicios(),2));
//    	rent.setPircing(redondearDecimales(rent.getComisionServicios() + rent.getComisionEstimadaPromotor(),2));
    	rent.setPircing(redondearDecimales(rent.getComisionServicios() - rent.getComisionEstimadaPromotor(), 2));
    	rent.setSgmm(0);
    	rent.setTimbrado(redondearDecimales(cot.getCostoAdicional().getIdValorTimbre().getValor().doubleValue()* rent.getNumColaboradores(),2));
    	rent.setSpei(redondearDecimales(cot.getCostoAdicional().getIdValoSpei().getValor().doubleValue()* rent.getNumColaboradores(),2));
    	rent.setImplant(0);
    	
    	rent.setCostoEstrategia(0);
    	
    	rent.setCostosDirectos(redondearDecimales(rent.getTimbrado() + rent.getSpei() + rent.getImplant(),2));
    	
    	rent.setCostosIndirectos(redondearDecimales(porcentajeIndirectos * rent.getSubtotalFactura(),2));
    	rent.setUtilidades(redondearDecimales(rent.getPircing()- rent.getCostosDirectos() - rent.getCostosIndirectos(),2));
    	if(rent.getSubtotalFactura()!=0) {
    		double utilidad = rent.getUtilidades() / rent.getSubtotalFactura();
    		rent.setRentabilidad(redondearDecimales(utilidad*100,2));
    	} else {
    		rent.setRentabilidad(0);
    	}
    }
    
    public void realizarRentabilidadPPP(RentabilidadDto rent, CotizacionTotalesDto totales, CotizacionDto cot) {
    	double porcentajePromotor = cot.getCostoAdicional().getIdPorcentajePromotor().getValor().doubleValue() / 100;
    	double porcentajeEstrategia = cot.getCostoAdicional().getIdPorcentajeCostoEstrategia().getValor().doubleValue() / 100;
    	double porcentajeIndirectos = cot.getCostoAdicional().getIdPorcentajeCostosIndirectos().getValor().doubleValue() / 100;
    	double montoSGMM = cot.getCostoAdicional().getMontoSgmm()!=null ? cot.getCostoAdicional().getMontoSgmm().doubleValue(): 0;
    	double montoImplant = cot.getCostoAdicional().getMontoImplant()!=null ?cot.getCostoAdicional().getMontoImplant().doubleValue():0;
    	
    	double promotorSgmm = montoSGMM * (cot.getCostoAdicional().getPorcentajePromotorSgmm().doubleValue()/100);
    	double clienteSgmm = montoSGMM * (cot.getCostoAdicional().getPorcentajeClienteSgmm().doubleValue()/100);
    	double corportivoSgmm = montoSGMM * (cot.getCostoAdicional().getPorcentajeCorporativoSgmm().doubleValue()/100);
    	
    	double promotorImplant = montoImplant * (cot.getCostoAdicional().getPorcentajePromotorImplant().doubleValue()/100);
    	double clienteImplant = montoImplant * (cot.getCostoAdicional().getPorcentajeClienteImplant().doubleValue()/100);
    	double corportivoImplant = montoImplant * (cot.getCostoAdicional().getPorcentajeCorporativoImplant().doubleValue()/100);
    	
    	rent.setPeriodicidad(cot.getIdPeriodicidad().getDescripcionTipoPago());
    	rent.setTipoProducto(cot.getIdImss());
    	rent.setNumColaboradores(cot.getNumColaboradores()); 
    	
    	rent.setDispersion(redondearDecimales(totales.getTotalgsePlanPensionesPrivada().doubleValue(),2));
    	rent.setCargaSocial(0);
    	double comisionPPP = cot.getComisionPpp()==null ? 0:cot.getComisionPpp().doubleValue();
    	comisionPPP = comisionPPP / 100;
    	rent.setComisionServicios(redondearDecimales(comisionPPP * rent.getDispersion(),2));
    	rent.setCostoAdicionalesCliente(redondearDecimales(clienteSgmm + clienteImplant,2));/***************************/
    	
    	rent.setSubtotalFactura(redondearDecimales(rent.getComisionServicios()+ rent.getDispersion() + rent.getCostoAdicionalesCliente(),2));
    	rent.setIva(redondearDecimales(rent.getSubtotalFactura() * 0.16,2));
    	rent.setTotalFactura(redondearDecimales(rent.getSubtotalFactura() + rent.getIva(),2));
    	rent.setPircing(redondearDecimales(rent.getTotalFactura() * porcentajePromotor,2));
    	rent.setCostoAdicionalesPromotor(redondearDecimales(promotorSgmm + promotorImplant,2));/***************************/
    	rent.setComisionEstimadaPromotor(redondearDecimales(rent.getComisionServicios() + rent.getIva() - rent.getPircing() ,2));
    	
    	rent.setSgmm(redondearDecimales(corportivoSgmm,2));
    	rent.setTimbrado(redondearDecimales(cot.getCostoAdicional().getIdValorTimbre().getValor().doubleValue()* rent.getNumColaboradores(),2));
    	rent.setSpei(redondearDecimales(cot.getCostoAdicional().getIdValoSpei().getValor().doubleValue()* rent.getNumColaboradores(),2));
    	rent.setCostoEstrategia(redondearDecimales(porcentajeEstrategia * rent.getDispersion(),2));
    	rent.setImplant(redondearDecimales(corportivoImplant,2));
    	
    	
    	rent.setCostosDirectos(redondearDecimales(rent.getSgmm() + rent.getTimbrado() + rent.getSpei() + rent.getCostoEstrategia() + rent.getImplant(),2));
    	
    	rent.setCostosIndirectos(redondearDecimales(porcentajeIndirectos * rent.getTotalFactura(),2));
    	rent.setUtilidades(redondearDecimales(rent.getPircing() - rent.getCostosDirectos() - rent.getCostosIndirectos(),2));
    	if(rent.getSubtotalFactura()!=0) {
    		double utilidad = rent.getUtilidades() / rent.getSubtotalFactura();
    		rent.setRentabilidad(redondearDecimales(utilidad*100,2));
    	}else {
    		rent.setRentabilidad(0);
    	}
    }
    public void realizarRentabilidadTotal(RentabilidadDto rent, RentabilidadDto renMixta, RentabilidadDto renPPP) {
    	rent.setPeriodicidad(renMixta.getPeriodicidad());
    	rent.setTipoProducto(renMixta.getTipoProducto());
    	rent.setNumColaboradores(renMixta.getNumColaboradores());
    	rent.setDispersion(redondearDecimales(renMixta.getDispersion() + renPPP.getDispersion(),2));
    	rent.setCargaSocial(redondearDecimales(renMixta.getCargaSocial() + renPPP.getCargaSocial(),2));
    	rent.setComisionServicios(redondearDecimales(renMixta.getComisionServicios() + renPPP.getComisionServicios(),2));
    	rent.setSubtotalFactura(redondearDecimales(renMixta.getSubtotalFactura() + renPPP.getSubtotalFactura(),2));
    	rent.setIva(redondearDecimales(renMixta.getIva() + renPPP.getIva(),2));
    	rent.setTotalFactura(redondearDecimales(renMixta.getTotalFactura() + renPPP.getTotalFactura(),2));
    	
    	rent.setComisionEstimadaPromotor(redondearDecimales(renMixta.getComisionEstimadaPromotor() + renPPP.getComisionEstimadaPromotor(),2));
    	rent.setPircing(redondearDecimales(renMixta.getPircing() + renPPP.getPircing(),2));
    	
    	rent.setSgmm(renMixta.getSgmm() + renPPP.getSgmm());
    	rent.setTimbrado(redondearDecimales(renMixta.getTimbrado() + renPPP.getTimbrado(),2));
    	rent.setSpei(redondearDecimales(renMixta.getSpei() + renPPP.getSpei(),2));
    	rent.setCostoEstrategia(renMixta.getCostoEstrategia() + renPPP.getCostoEstrategia());
    	rent.setImplant(redondearDecimales(renMixta.getImplant() + renPPP.getImplant(),2));
    	
    	rent.setCostosDirectos(redondearDecimales(renMixta.getCostosDirectos() + renPPP.getCostosDirectos(),2));
    	
    	rent.setCostosIndirectos(redondearDecimales(renMixta.getCostosIndirectos() + renPPP.getCostosIndirectos(),2));
    	rent.setUtilidades(redondearDecimales(renMixta.getUtilidades() + renPPP.getUtilidades(),2));
    	rent.setRentabilidad(redondearDecimales(renMixta.getRentabilidad() + renPPP.getRentabilidad(),2));
    }
    
    
    
    
    public static double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return resultado;
    }
    
    public void inicializarValoresFaltantes(CotizacionDto cot) {
    	cot.setAguinaldo(cot.getAguinaldo()==null?new BigDecimal(0):cot.getAguinaldo());
    	cot.setComisionImss(cot.getComisionImss()==null?new BigDecimal(0):cot.getComisionImss());
    	cot.setComisionPpp(cot.getComisionPpp()==null?new BigDecimal(0):cot.getComisionPpp());
    	cot.setDgMontoBrutoMensual(cot.getDgMontoBrutoMensual()==null?new BigDecimal(0):cot.getDgMontoBrutoMensual());
    	cot.setDgVSM(cot.getDgVSM()==null?new BigDecimal(0):cot.getDgVSM());
    	cot.setDgporcCotizacionDeseado(cot.getDgporcCotizacionDeseado()==null?new BigDecimal(0):cot.getDgporcCotizacionDeseado());
    	cot.setDiasVacaciones(cot.getDiasVacaciones()==null?0:cot.getDiasVacaciones());
    	cot.setFeeActual(cot.getFeeActual()==null?new BigDecimal(0):cot.getFeeActual());
    	cot.setPrimaVacacional(cot.getPrimaVacacional()==null? new BigDecimal(0):cot.getPrimaVacacional());
    }
}