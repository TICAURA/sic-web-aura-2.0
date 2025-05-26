package mx.com.consolida.controller.finanzas;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.conciliaciones.CargaOrdenesPagoDto;
import mx.com.consolida.conciliaciones.DetalleConciliacionesCCDto;
import mx.com.consolida.conciliaciones.OrdenPagoDto;
import mx.com.consolida.conciliaciones.TotalesConciliacionesDto;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.conciliacion.CargaOrdenPagoBO;
import mx.com.consolida.service.conciliacion.OrdenPagoBO;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.consolida.util.FormatoFecha;

@Controller
@RequestMapping("conciliacion")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class ConciliacionController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConciliacionController.class);
	
	@Autowired
	CargaOrdenPagoBO cargaOrdenPagoBo;
	
	@Autowired
	OrdenPagoBO ordenPagoBo;
	@Autowired
	private CelulaBO celulaBO;
	

	FormatoFecha  fechaformato;
	
	private static List<String> nombreColumnas = Arrays.asList("#","ID","Folio Orden","Institución","Concepto del Pago",
			"Beneficiario",	"Cta Beneficiario","Contraparte",	"Monto","Rastreo","Ordenante","Cta Ordenante","Fecha Operación",
			"Folio Orden CEP","Nombre Cliente CEP","Fecha Captura","Fecha Liquidación","Tiempo Liquidación","Causa Devolución"
);
	
	@RequestMapping(value = "/pagos/cargaInicialConsolidado")
	@ResponseBody
	public Map<String, Object> cargaInicialConsolidado(@RequestBody  String  anioMesDia,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		String fechaFin=null;
		String fechaInicio=null;
		try {
		
			
		/*	if (anioMesDia == null || anioMesDia=="" || anioMesDia.length()==0 ) {
				Date fecha = new Date();
				String pattern = "yyyyMM";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);		
				anioMesDia = simpleDateFormat.format(new Date());
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				Calendar calendar = Calendar.getInstance(); 			
				calendar.set(Calendar.DAY_OF_MONTH,1);
				fechaFin=fechaformato.formatoFechaEstandar(fecha);
					//	formatter.format(fecha);
			    fechaInicio = fechaformato.formatoFechaEstandar(calendar.getTime());
			    		//formatter.format(calendar.getTime());
			
		}*/
			
			
			//BigDecimal costoPromedio=  cargaOrdenPagoBo.getCostoPromedio();
		/*	OrdenPagoDto orden = new OrdenPagoDto();
			
			orden.setFechaInicioS(fechaInicio);
			orden.setFechaFinS(fechaFin);
			orden.setAnioMesDia(anioMesDia);*/
			//orden.setCostoPromedio(Double.valueOf(costoPromedio.toString()));
		/*   TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotales(orden);
			
		
		
			Double ingresoBruto =totales.getTotalDepositos() -totales.getTotalDisponible();
				
			dataReturn.put("depositos",totales.getTotalDepositos());	
			dataReturn.put("disponible",totales.getTotalDisponible() );
			dataReturn.put("ingresoBruto", ingresoBruto);
			dataReturn.put("utilidad",ingresoBruto*0.18 );
			dataReturn.put("gasto", ingresoBruto*0.18 );
			dataReturn.put("comisiones", ingresoBruto*.6);
			dataReturn.put("socios", ingresoBruto*.04);
			dataReturn.put("orden", orden);*/
			return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/pagos/cargaInicialOrdenes")
	@ResponseBody
	public Map<String, Object> cargaInicialOrdenes(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
					
			dataReturn.put( "listaCargasOrdenes" , cargaOrdenPagoBo.getCargaOrdenesPago());
			dataReturn.put("ordenesPagos", cargaOrdenPagoBo.getOrdenesPago());
						
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/pagos/cargaInicialDetalle")
	@ResponseBody
	public Map<String, Object> cargaInicialDetalle(@RequestBody String anioMes, Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
			
			Date fecha = new Date();
			if ( anioMes ==null || anioMes=="" || anioMes.length()==0 ) {
			String pattern = "yyyyMM";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		     anioMes = simpleDateFormat.format(new Date());
			}
			
			//BigDecimal costoPromedio = cargaOrdenPagoBo.getCostoPromedio();		
			OrdenPagoDto orden = new OrdenPagoDto();
		//	orden.setCostoPromedio(Double.valueOf(costoPromedio.toString()));
			
			orden.setAnioMesDia(anioMes);
			// TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotales(orden);
			     
			
//			dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(anioMes));
//			dataReturn.put("totalEgresos",cargaOrdenPagoBo.getTotalEgresos(anioMes));
//			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(anioMes));
//			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(anioMes));
//			dataReturn.put("detalle",cargaOrdenPagoBo.getDetalleIngresos(anioMes));
			
			dataReturn.put("totalIngresos", 0);
			dataReturn.put("totalEgresos",0);
			dataReturn.put("totalClientesIngresos", 0);
			dataReturn.put("totalClientesEgresos", 0);
			dataReturn.put("detalle",0);
					
			return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);	
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/pagos/getCalculoCliente")
	@ResponseBody
	public Map<String, Object> getCalculoCliente(@RequestBody  OrdenPagoDto orden, Model model) throws BusinessException {
		
       Map<String, Object> dataReturn = new HashMap<>();
       Long fechaInicio = null;
		Long fechaFin = null;
		String fechaInicios = null;
		String fechaFins = null;
		try {
			
			if(orden.getFechaInicio() != null && orden.getFechaFin() != null) {
				Format formatter = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				fechaInicio = df.parse(formatter.format(orden.getFechaInicio() )).getTime();
				fechaFin = df.parse(formatter.format(orden.getFechaFin())).getTime();
				
				if (fechaFin < fechaInicio) {
					throw new BusinessException("", "");
				} else if (fechaInicio > fechaFin) {
					throw new BusinessException("", "");
				}
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			
		    fechaInicios =fechaformato.formatoFechaEstandar(orden.getFechaInicio());
		    		//formatter.format(orden.getFechaInicio());
		    fechaFins = fechaformato.formatoFechaEstandar(orden.getFechaFin());
		    		//formatter.format(orden.getFechaFin());
							
          BigDecimal costoPromedio=cargaOrdenPagoBo.getCostoPromedio();
			
					
			
			orden.setCostoPromedio(Double.valueOf(costoPromedio.toString()));
			orden.setFechaInicioS(fechaInicios);	
			orden.setFechaFinS(fechaFins);
			
		    TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotalesDate(orden);
			
			Double ingresoBruto =totales.getTotalDepositos() -totales.getTotalDisponible();
				
			dataReturn.put("depositos",totales.getTotalDepositos());	
		dataReturn.put("disponible",totales.getTotalDisponible() );
			dataReturn.put("ingresoBruto", ingresoBruto);
			dataReturn.put("utilidad",ingresoBruto*0.18 );
			dataReturn.put("gasto", ingresoBruto*0.18 );
			dataReturn.put("comisiones", ingresoBruto*.6);
			dataReturn.put("socio", ingresoBruto*.04);
			dataReturn.put("orden", orden);
				
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCalculoCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
		//detalle
		/*Map<String, Object> dataReturn = new HashMap<>();
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getAnioMesDia() == null || orden.getAnioMesDia()=="" ) {
				Date fecha = new Date();
				String pattern = "yyyyMM";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				orden.setAnioMesDia(simpleDateFormat.format(new Date()));
				
			}
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		    fechaInicio = formatter.format(orden.getFechaInicio());
		    fechaFin = formatter.format(orden.getFechaFin());
		
			orden.setFechaInicioS(fechaInicio);
			orden.setFechaFinS(fechaFin);
			BigDecimal costoPromedio=cargaOrdenPagoBo.getCostoPromedio();
			
					
			
			orden.setCostoPromedio(Double.valueOf(costoPromedio.toString()));
		    TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotales(orden);
			
			Double ingresoBruto =totales.getTotalDepositos() -totales.getTotalDisponible();
				
			dataReturn.put("depositos",totales.getTotalDepositos());	
		dataReturn.put("disponible",totales.getTotalDisponible() );
			dataReturn.put("ingresoBruto", ingresoBruto);
			dataReturn.put("utilidad",ingresoBruto*0.18 );
			dataReturn.put("gasto", ingresoBruto*0.18 );
			dataReturn.put("comisiones", ingresoBruto*.6);
			dataReturn.put("socio", ingresoBruto*.04);
			dataReturn.put("orden", orden);
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getCalculoCliente ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
		return dataReturn;*/
	}
	
	
	@RequestMapping(value = "/pagos/getCarga")
	@ResponseBody
	public Map<String, Object> getCarga(@RequestBody CargaOrdenesPagoDto carga,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
						Date fecha = new Date();
						String pattern = "MMyyyy";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
					    String mesAnio = simpleDateFormat.format(new Date());			
					    
					    
		
			dataReturn.put("contentRows", cargaOrdenPagoBo.getOrdenesPagoByIdCarga(carga.getIdCargaOrdenPago()));
		  
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/pagos/getDetalleTotales")
	@ResponseBody
	public Map<String, Object> getDetalleTotales(@RequestBody OrdenPagoDto orden,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getFechaInicio() != null || orden.getFechaFin()!=null ) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
			    fechaInicio = fechaformato.formatoFechaEstandar(orden.getFechaInicio());
			    		//formatter.format(orden.getFechaInicio());
			    fechaFin = fechaformato.formatoFechaEstandar(orden.getFechaFin());
			
				orden.setFechaInicioS(fechaInicio);
				orden.setFechaFinS(fechaFin);		
			}
			
				/*	Date fecha = new Date();
						String pattern = "yyyyMM";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						
						if (anioMes==null || "".equals(anioMes)) {
							anioMes = simpleDateFormat.format(new Date());
						}*/
						
					    					
			dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(orden));
			dataReturn.put("totalEgresos", cargaOrdenPagoBo.getTotalEgresos(orden));
			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(orden));
			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(orden));
			dataReturn.put("orden", orden);
		//	dataReturn.put("detalleIngresos", cargaOrdenPagoBo.getDetalleIngresos(orden));

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/pagos/getDetalleIngresos")
	@ResponseBody
	public Map<String, Object> getDetalleIngresos(@RequestBody OrdenPagoDto orden,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getFechaInicio() != null || orden.getFechaFin()!=null ) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
			    fechaInicio =  fechaformato.formatoFechaEstandar(orden.getFechaInicio());
				
			    		//formatter.format(orden.getFechaInicio());
			    fechaFin = fechaformato.formatoFechaEstandar(orden.getFechaFin());
			
				orden.setFechaInicioS(fechaInicio);
				orden.setFechaFinS(fechaFin);		
			}
			
				/*	Date fecha = new Date();
						String pattern = "yyyyMM";
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
						
						if (anioMes==null || "".equals(anioMes)) {
							anioMes = simpleDateFormat.format(new Date());
						}*/
						
					    					
			/*dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(orden));
			dataReturn.put("totalEgresos", cargaOrdenPagoBo.getTotalEgresos(orden));
			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(orden));
			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(orden));*/
			dataReturn.put("detalleIngresos", cargaOrdenPagoBo.getDetalleIngresos(orden));
			dataReturn.put("orden", orden);

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/pagos/calcularCosolidado")
	@ResponseBody
	public Map<String, Object> calcularCosolidado(@RequestBody OrdenPagoDto orden, Model model) throws BusinessException {
		
	
		Map<String, Object> dataReturn = new HashMap<>();
		Long fechaInicio = null;
		Long fechaFin = null;
		String fechaInicios = null;
		String fechaFins = null;
		try {
			//rango de fechas
			if(orden.getFechaInicio() != null && orden.getFechaFin() != null) {
				Format formatter = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				fechaInicio = df.parse(formatter.format(orden.getFechaInicio() )).getTime();
				fechaFin = df.parse(formatter.format(orden.getFechaFin())).getTime();
				
				if (fechaFin < fechaInicio) {
					throw new BusinessException("", "");
				} else if (fechaInicio > fechaFin) {
					throw new BusinessException("", "");
				}
			}
		

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		    fechaInicios = fechaformato.formatoFechaEstandar(orden.getFechaInicio());
		    		//formatter.format(orden.getFechaInicio());
		    fechaFins = fechaformato.formatoFechaEstandar(orden.getFechaFin());
		
	
			
			BigDecimal costoPromedio=cargaOrdenPagoBo.getCostoPromedio();
	
			orden.setFechaInicioS("Total del periodo " +fechaInicios + " al " );	
			orden.setFechaFinS(fechaFins);
		//	orden.setAnioMesDia(anioMesDia);
		//	orden.setCostoPromedio(Double.valueOf(costoPromedio.toString()));
		    TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotalesDate(orden);
		//    List<DetalleConciliacionesCCDto> listCC= cargaOrdenPagoBo.getlistDetalleCC(orden)  ; 
		
		
			Double ingresoBruto =totales.getTotalDepositos() -totales.getTotalDisponible();
				
			dataReturn.put("depositos",totales.getTotalDepositos());	
			dataReturn.put("disponible",totales.getTotalDisponible() );
			dataReturn.put("ingresoBruto", ingresoBruto);
			dataReturn.put("utilidad",ingresoBruto*0.18 );
			dataReturn.put("gasto", ingresoBruto*0.18 );
			dataReturn.put("comisiones", ingresoBruto*.6);
			dataReturn.put("socios", ingresoBruto*.04);
			dataReturn.put("listCC", cargaOrdenPagoBo.getlistDetalleCC(orden) );
			dataReturn.put("orden", orden);
				return dataReturn;
		
		}
		
		
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
		
	}
	
	
	
	@RequestMapping(value = "/pagos/getDetalleClientesIngresos")
	@ResponseBody
	public Map<String, Object> getDetalleClienteIngresos(@RequestBody OrdenPagoDto orden,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getFechaInicio() != null || orden.getFechaFin()!=null ) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
			    fechaInicio =fechaformato.formatoFechaEstandar(orden.getFechaInicio()); 
			    		//formatter.format(orden.getFechaInicio());
			    fechaFin =fechaformato.formatoFechaEstandar(orden.getFechaFin());
			
				orden.setFechaInicioS(fechaInicio);
				orden.setFechaFinS(fechaFin);		
	
		/*	if (anioMes == null || anioMes=="" || anioMes.length()==0  ) {
				Date fecha = new Date();
				String pattern = "yyyyMM";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				anioMes = simpleDateFormat.format(new Date());*/
				
			}
						
			    					
		/*	dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(anioMes));
			dataReturn.put("totalEgresos", cargaOrdenPagoBo.getTotalEgresos(anioMes));
			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(anioMes));
			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(anioMes));*/
			dataReturn.put("detalleIngresos", cargaOrdenPagoBo.getDetalleClienteIngresos(orden));
			dataReturn.put("orden", orden);
		  
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/pagos/getDetalleClientesEgresos")
	@ResponseBody
	public Map<String, Object> getDetalleClienteEgresos(@RequestBody OrdenPagoDto orden,Model model) throws BusinessException {

		Map<String, Object> dataReturn = new HashMap<>();
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getFechaInicio() != null || orden.getFechaFin()!=null ) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
			    fechaInicio =fechaformato.formatoFechaEstandar(orden.getFechaInicio());
				
			    		//formatter.format(orden.getFechaInicio());
			    fechaFin = fechaformato.formatoFechaEstandar(orden.getFechaFin());
			
				orden.setFechaInicioS(fechaInicio);
				orden.setFechaFinS(fechaFin);	
			}
						
			    					
			/*dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(anioMes));
			dataReturn.put("totalEgresos", cargaOrdenPagoBo.getTotalEgresos(anioMes));
			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(anioMes));
			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(anioMes));*/
			dataReturn.put("detalleEgresos", cargaOrdenPagoBo.getDetalleClienteEgresos(orden));
			dataReturn.put("orden", orden);
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/pagos/getDetalleEgresos")
	@ResponseBody
	public Map<String, Object> getDetalleEgresos(@RequestBody OrdenPagoDto orden,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		String fechaInicio=null;
		String fechaFin =null;
		try {
			
			if (orden.getFechaInicio() != null || orden.getFechaFin()!=null ) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				
			    fechaInicio = fechaformato.formatoFechaEstandar(orden.getFechaInicio());
			    	//	formatter.format(orden.getFechaInicio());
			    fechaFin = fechaformato.formatoFechaEstandar(orden.getFechaFin());
			
				orden.setFechaInicioS(fechaInicio);
				orden.setFechaFinS(fechaFin);	
			}
					
			/*dataReturn.put("totalIngresos", cargaOrdenPagoBo.getTotalIngresos(orden));
			dataReturn.put("totalEgresos", cargaOrdenPagoBo.getTotalEgresos(orden));
			dataReturn.put("totalClientesIngresos", cargaOrdenPagoBo.getClienteIngresos(orden));
			dataReturn.put("totalClientesEgresos", cargaOrdenPagoBo.getClienteEgresos(orden));*/
			dataReturn.put("detalleEgresos", cargaOrdenPagoBo.getDetalleEgresos(orden));
			dataReturn.put("orden", orden);
				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialNomina ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	
	@RequestMapping(value = "/pagos/guardarOrdenes")
	@ResponseBody
	public void guardarOrdenesPagos(@RequestBody CargaOrdenesPagoDto ordenesPagosDto) throws BusinessException {
		try {
			ordenesPagosDto.setIdUsuarioAlta(getUser().getIdUsuario());
			cargaOrdenPagoBo.guardaOrdenesPagos(ordenesPagosDto);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardar ordenes de pagos ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo m&aacute;s tarde.");
		}
	}
	
	
	@RequestMapping(value = "/pagos/createFile")
	@ResponseBody
	public Map<String, Object> createFile(@RequestBody DocumentoDTO documento, Model model) throws IOException {
		System.out.print("\nArchivo creado correctamente a las :" + new Date());
		Map<String, Object> datosExcel = new HashMap<String, Object>();
	
		try {
			String archivoString = "";
			byte[] bytes = new byte[documento.getArchivo().getBytes().length];
			if (documento != null && (documento.getNombreArchivo().contains(".xlsx")
					|| documento.getNombreArchivo().contains(".XLSX"))) {
				// archivoString =
				// documento.getArchivo().replace("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,",
				// "");
				archivoString = documento.getArchivo().substring(78, documento.getArchivo().length());
			} else {
				archivoString = documento.getArchivo().replace("data:applicaettion/vnd.ms-excel;base64,", "");
			}

			// bytes = n

			Base64 base64 = new Base64();
			bytes = base64.decode(archivoString);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			datosExcel = readJExcel(byteArrayInputStream, documento.getNombreArchivo());
			if (!datosExcel.containsKey("errorCargalayout")) {
				datosExcel.put("errorCargaLayout", "OK");
				}
			

//    	}catch(IOException e) {
//        	datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout.<br> <br>Favor de verificar el tipo de dato para cada celda");
//        	LOGGER.error("Error en la carga", e);
		} catch (Exception e) {
			datosExcel.put("errorCargaLayout",
					"El archivo no pudo ser le&iacute;do, verifica la estructura y el formato");
			LOGGER.error("Error en la carga", e);
		}

		return datosExcel;
	}
	
	public Map<String, Object> readJExcel(ByteArrayInputStream bytesArray, String FILE_NAME) {
		Map<String, Object> datosExcel = new HashMap<String, Object>();
		List<String> headerData = new ArrayList<String>();
		List<OrdenPagoDto> rowsData = new ArrayList<OrdenPagoDto>();
		datosExcel.put("header", headerData);
		Boolean layoutPuesto = Boolean.FALSE;
		Boolean errorLayout = false;
		

		LOGGER.info(
				"===================================================================================================");
		LOGGER.info(
				"=============================Validando archivo ordenes=======================================");
		try {
			Workbook workbook = null;
			XSSFWorkbook workbooks = null;
			Sheet datatypeSheet = null;
			
			if (FILE_NAME != null && (FILE_NAME.contains(".xlsx") || FILE_NAME.contains(".XLSX"))) {
				workbooks = new XSSFWorkbook(bytesArray);
				datatypeSheet = workbooks.getSheetAt(0);
			} else {
				workbook = new HSSFWorkbook(bytesArray);
				datatypeSheet = workbook.getSheetAt(0);
			}
			Iterator<Row> iterator = datatypeSheet.iterator();
			int i = 0;
			Double totalMonto = 0.0;
			Double totalMontoCorrecto = 0.0;
			Double totalMontoDevuelto =0.0;
			Double totalMontoIncorrecto=0.0;
			int totalRegistros= 0;
			int totalRegDevueltos=0;
			int totalRegCorrectos= 0;
			int totalRegIncorrectos = 0;
			
			
			while (iterator.hasNext()) {
				Row ro = iterator.next();
				if (0== i) {
				}else if (1==i) {
					Iterator<Cell> cellIterator = ro.iterator();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (nombreColumnas.contains(currentCell.toString())) {
							headerData.add(currentCell.getStringCellValue());
							if("PUESTO".contentEquals(currentCell.toString())){
								layoutPuesto= Boolean.TRUE;
							}
						} else {
							datosExcel.put("errorCargaLayout",
									"El contenido del archivo excel no corresponde al layout.<br> <br>Favor de verificar el tipo de dato para cada celda");
							return datosExcel;
						}
					}
					
					
				} else {
					String fechaliquidacion = "";
					OrdenPagoDto rowData = new OrdenPagoDto();
					for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
						Cell ce = ro.getCell(j);
						System.out.println(" celda: " + j );
						if (ce != null) {
						
							}  if (j == 1) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {			
									rowData.setEstatus(0);
									rowData.setDescError("Error, el tipo de celda del campo ID, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
							
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setIdOrdenOrigen( Long.valueOf(ce.getStringCellValue()));

								}else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									Long valor =(long) ce.getNumericCellValue();
									
									rowData.setIdOrdenOrigen((long)ce.getNumericCellValue());

								}
								
								if (ordenPagoBo.existeOrdenPagoById(rowData.getIdOrdenOrigen())){
									rowData.setEstatus(0);
									rowData.setDescError("Error, el , ID de la orden ya existe, favor de verificar");

								}
								
								
							} else if (j == 2) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Folio Orden, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setFolioOrden( Long.parseLong( ce.getStringCellValue()));

								}else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									rowData.setFolioOrden( (long) ce.getNumericCellValue());

								}
								
								
								
							} else if (j == 3) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Concepto del Pago, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setInstitucion(ce.getStringCellValue());

								}
							

						} else if (j == 4) {
							
							if (ce.getCellTypeEnum() == CellType.BLANK) {
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, el tipo de celda del campo Institucion , debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

							} else if (ce.getCellTypeEnum()== CellType.STRING) {
								rowData.setConceptoPago(ce.getStringCellValue());
								
							}else if (ce.getCellTypeEnum() != CellType.STRING) {
							
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, el tipo de celda del campo Institucion, debe ser de tipo General y/o Texto, favor de verificar");

							}
						

					}else if (j == 5) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Beneficiario, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setBeneficiario(ce.getStringCellValue());

								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setBeneficiario(ce.getStringCellValue());
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Beneficiario, debe ser de tipo General y/o Texto, favor de verificar");

								}
								
					} else if (j == 6) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Cta Beneficiario, debe ser de tipo Moneda y mayor a 0, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setCtaBeneficiario(ce.getStringCellValue());
						
								}
								
								if (!(rowData.getCtaBeneficiario().length() == 18 )) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error en el formato de la Cta Beneficiario");
								} 
							} else if (j == 7) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Contraparte, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setContraparte(ce.getStringCellValue());
								
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Contraparte, debe ser de tipo General y/o Texto, favor de verificar");
							

								}
								

							
							} else if (j == 8) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Monto, debe ser de tipo Moneda y mayor a 0, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									rowData.setMonto(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
				
;
								} else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									rowData.setMonto(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Monto, debe ser de tipo Moneda, favor de verificar");

								}
							
								if (rowData.getMonto().equals(BigDecimal.ZERO)) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo monto, debe ser de tipo Moneda y ser mayor a Cero, favor de verificar");

								}
							
							} else if (j == 9) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Rastreo, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								}else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setRastreo(ce.getStringCellValue());
						
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Rastreo, debe ser de tipo General y/o Texto, favor de verificar");

								}
								
								

							} else if (j == 10) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Ordenante, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");

								}else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setOrdenante(ce.getStringCellValue());
								
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Ordenante, debe ser de tipo General y/o Texto, favor de verificar");
								}
								
							}else if (j == 11) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Cta Ordenante, debe ser de tipo Moneda y mayor a 0, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setCtaOrdenante(ce.getStringCellValue());
								}else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Cta Ordenante, debe ser de tipo General y/o Texto, favor de verificar");

								}
					
								if ( rowData.getCtaOrdenante()==null ||  !(rowData.getCtaOrdenante().length() == 18 )) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Cta Ordenante, debe ser de tipo General y/o Texto y de 18 posiciones, favor de verificar\")");
								} 
							}else if (j == 12) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto, favor de verificar");

								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									try {
									String fecha =  ce.getStringCellValue().substring(6, 10) +"/" + ce.getStringCellValue().substring(3, 5)+"/"+ ce.getStringCellValue().substring(0, 2);
									
									rowData.setFechaOperacion(new Date(fecha));	
									}catch(Exception e) {
										rowData.setEstatus(0);
										rowData.setDescError(
												"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto con formato dd/MM/yyyy, favor de verificar");

									}

								}else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									
									Double prueba = ce.getNumericCellValue();
									System.out.print("fechaOperacion:" + prueba);
									
									
									
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto y formato dd/MM/yyyy, favor de verificar");
								
								}	else   {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto y formato dd/MM/yyyy, favor de verificar");
							
								}
									
							
							}else if (j == 13) {
								
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									try {
									rowData.setFolioOrdenCep(Integer.valueOf(ce.getStringCellValue()));
									}catch(Exception e) {
										
									};
								}
										
							
							}else if (j == 14) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setNombreClienteCep(ce.getStringCellValue());	
								}
									
							
							}else if (j == 15) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto, favor de verificar");
							
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
																
									try {
										String fecha =  ce.getStringCellValue().substring(6, 10) +"/" + ce.getStringCellValue().substring(3, 5)+"/"+ ce.getStringCellValue().substring(0, 2);
										rowData.setFechaCaptura(new Date(fecha));
								   }catch(Exception e) {
											rowData.setEstatus(0);
											rowData.setDescError(
													"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto y con formato dd/MM/yyyy, favor de verificar");
											
										}

								} else if (ce.getCellTypeEnum() == CellType.NUMERIC) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto y con formato dd/MM/yyyy, favor de verificar");
							
								}else  {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Operacion, debe ser de tipo General y/o Texto y con formato dd/MM/yyyy, favor de verificar");
							
								}
										
							
							}else if (j == 16) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha liquidacion, debe ser de tipo General y/o Texto, favor de verificar");
									
									
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									
									try {
										String fecha =  ce.getStringCellValue().substring(6, 10) +"/" + ce.getStringCellValue().substring(3, 5)+"/"+ ce.getStringCellValue().substring(0, 2);
										 fechaliquidacion =  ce.getStringCellValue().substring(6, 10) +"/" + ce.getStringCellValue().substring(3, 5)+"/"+ ce.getStringCellValue().substring(0, 2);
										rowData.setFechaLiquidacion(new Date(fechaliquidacion));	
										System.out.print(" fechaliquidacion " + rowData.getFechaLiquidacion()  );
										}catch(Exception e) {
											rowData.setEstatus(0);
											rowData.setDescError(
													"Error, el tipo de celda del campo Fecha Liquidacion , debe ser de tipo General y/o Texto y con el formato dd/MM/yyyy, favor de verificar");
										
										}
					

								}else if (ce.getCellTypeEnum() == CellType.NUMERIC) {

									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Liquidacion, debe ser de tipo General y/o Texto y con el formato dd/MM/yyyy, favor de verificar");
								
								}else  {
									rowData.setFechaLiquidacion(ce.getDateCellValue());	
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Fecha Liquidacion, debe ser de tipo General y/o Texto y con el formato dd/MM/yyyy, favor de verificar");
									
								}
									
							
							}else if (j == 17) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo Tiempo liquidacion Operacion, debe ser de tipo General y/o Texto y con el formato HH:mm:ss.mmm, favor de verificar");
								
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
								
									try {
										
										String s = ce.getStringCellValue().substring(0, 8);
										fechaliquidacion=fechaliquidacion + " " + s;
										
										rowData.setFechaLiquidacion(new Date(fechaliquidacion));
										
										long ms= 0l;
										Time t ;
										SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
										
										ms = sdf.parse(s).getTime();
										 t = new Time(ms);
											rowData.setTiempoLiquidacion(t);	
									
									} catch ( Exception e) {
										rowData.setEstatus(0);
										rowData.setDescError(
												"Error, el tipo de celda del campo Tiempo liquidacion Operacion, debe ser de tipo General y/o Texto y con el formato HH:mm:ss.mmm, favor de verificar");
									
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									

								}
								
//								try {
//									String s = ce.getStringCellValue().substring(0, 8);
//									fechaliquidacion=fechaliquidacion + " " + s;
//									
//									rowData.setFechaLiquidacion(new Date(fechaliquidacion));
//									
//									long ms= 0l;
//									Time t ;
//									SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//								//	ms = sdf.parse(ce.getStringCellValue().replace(",", ".")).getTime();
//									ms = sdf.parse(fechaliquidacion).getTime();
//									 t = new Time(ms);
//										rowData.setTiempoLiquidacion(t);	
//								} catch (Exception e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
									
							
							}else if (j == 18) {
								
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									
								} else if (ce.getCellTypeEnum() == CellType.STRING) {
									rowData.setCausaDevolucion(ce.getStringCellValue());
									if (ce.getStringCellValue() != null && ce.getStringCellValue().length()>1) {
										rowData.setEstatus(0);
										rowData.setDescError("Error: ,"+ce.getStringCellValue()+", favor de verificar");
										totalRegDevueltos++;
										totalMontoDevuelto +=rowData.getMonto()!=null? Double.valueOf(rowData.getMonto().toString()): 0.0;
									}
								}
										
							
							}
						

					}

					if (rowData.getDescError()==null || rowData.getDescError()=="") {
						rowData.setEstatus(1);
						rowData.setDescError("OK");
						totalMontoCorrecto+=rowData.getMonto()!=null? Double.valueOf(rowData.getMonto().toString()): 0.0;
						totalRegCorrectos++ ;
					
						errorLayout = true;
					}else if("".equals(rowData.getCausaDevolucion())&& rowData.getDescError().length()>1) {
						
						totalRegIncorrectos++;
						totalMontoIncorrecto+=rowData.getMonto()!=null? Double.valueOf(rowData.getMonto().toString()): 0.0;
						
					}
					if (rowData.getMonto()!=null ) {
						totalMonto+= Double.valueOf(rowData.getMonto().toString());
					}
					totalRegistros++;
					
					rowsData.add(rowData);
					System.out.println(rowsData.size() + ": " + rowData.getOrdenante());
					
					System.out.println(rowsData.size() + ": " + rowData.getContraparte());

				}

				i += 1;
				System.out.print(" renglon:" + i );
			}

			System.out.println(
					"=============================Fin validación archivo pagos=======================================");
			System.out.println(
					"===================================================================================================");
			datosExcel.put("errorLayout", errorLayout);
			datosExcel.put("layoutPuesto", layoutPuesto);
			datosExcel.put("reg", totalRegistros);
			datosExcel.put("totalMonto", totalMonto);
			datosExcel.put("regDevueltos", totalRegDevueltos);
			datosExcel.put("totalMontoDevuelto", totalMontoDevuelto);
			datosExcel.put("regCorrectos", totalRegCorrectos);
			datosExcel.put("totalMontoCorrecto", totalMontoCorrecto);
			datosExcel.put("regIncorrectos", totalRegIncorrectos);
			datosExcel.put("totalMontoIncorrecto", totalMontoIncorrecto);
			
			
			if (errorLayout) {
				datosExcel.put("errorLayout", true);
			}else {
				datosExcel.put("errorLayout", false);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (rowsData.size() > 0) {
			datosExcel.put("contentRows", rowsData);
		} else {
			datosExcel.put("errorCargaLayout", "EL layout no contiene  dep&oacute;sitos para registrar");
			return datosExcel;
		}

		return datosExcel;
	}

	private String validaCeldaString(Cell cell) {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		}
		return null;

	}
	
	
	@RequestMapping(value = "/pagos/cargaInicialDispersion")
	@ResponseBody
	public Map<String, Object> cargaInicialDispersion(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {					   
			    	dataReturn.put("catCelula", celulaBO.listarTodasLasCelulas());
			 

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialReporetes ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}

	@RequestMapping(value = "/pagos/consultarParaDispersion")
	@ResponseBody
	public Map<String, Object> consultarParaDispersion(@RequestBody OrdenPagoDto orden, Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		Long fechaInicio = null;
		Long fechaFin = null;
		String fechaInicios = null;
		String fechaFins = null;
		try {
			//rango de fechas
			if(orden.getFechaInicio() != null && orden.getFechaFin() != null) {
				Format formatter = new SimpleDateFormat("MM-dd-yyyy");
				DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
				fechaInicio = df.parse(formatter.format(orden.getFechaInicio() )).getTime();
				fechaFin = df.parse(formatter.format(orden.getFechaFin())).getTime();
				
				if (fechaFin < fechaInicio) {
					throw new BusinessException("", "");
				} else if (fechaInicio > fechaFin) {
					throw new BusinessException("", "");
				}
			}
		

			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		
		    fechaInicios = fechaformato.formatoFechaEstandar(orden.getFechaInicio());
		    		//formatter.format(orden.getFechaInicio());
		    fechaFins = fechaformato.formatoFechaEstandar(orden.getFechaFin());
		
	
		
			orden.setFechaInicioS("Total del periodo " +fechaInicios + " al " );	
			orden.setFechaFinS(fechaFins);
		    TotalesConciliacionesDto totales= cargaOrdenPagoBo.getTotalesDate(orden);

		  //  dataReturn.put("listCC", cargaOrdenPagoBo.getlistDetalleCC(orden) );
			dataReturn.put("listCC", cargaOrdenPagoBo.getlistDispersionIB(orden) );
			dataReturn.put("orden", orden);
				return dataReturn;
		
		}
		
		
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en consultarParaDispersion ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
		
	}
	
	@RequestMapping(value = "/pagos/dispersar")
	@ResponseBody
	public Map<String, Object> dispersar(@RequestBody OrdenPagoDto orden, Model model) throws BusinessException {
		
 		Map<String, Object> dataReturn = new HashMap<>();
		
		try {					   
			    	dataReturn.put("catCelula", celulaBO.listarTodasLasCelulas());
			 

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialReporetes ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	

}
