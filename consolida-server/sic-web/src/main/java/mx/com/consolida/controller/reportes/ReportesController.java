package mx.com.consolida.controller.reportes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.CatReportesENUM;
import mx.com.consolida.controller.base.BaseController;
import mx.com.consolida.converter.Utilerias;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.ReportesBO;
import mx.com.consolida.dto.admin.RolDTO;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.service.usuario.UsuarioBO;
import mx.com.consolida.usuario.UsuarioDTO;
import mx.com.consolida.util.ConstantesComunes;
import mx.com.reporte.dto.ReporteDTO;

@Controller
@RequestMapping("reportes")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO})
public class ReportesController  extends BaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportesController.class);
	
	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private UsuarioBO usuarioBO;
	
	@Autowired
	private CelulaBO celulaBO;
	
	@Autowired
	private ReportesBO reportesBO;
	
	@RequestMapping(value = "/cargaInicial")
	@ResponseBody
	public Map<String, Object> cargaInicialReporetes(Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
			
			    UsuarioDTO userAut = usuarioBO.usuarioAutenticacion(getUser().getUsuario());
			    
			    if(userAut == null) {
			    	LOGGER.error("Ocurrio un error en cargaInicialReporetes objeto UsuarioDTO es null ");
					throw new BusinessException ("Ocurrio un error en el sistema");
			    }

						    
			    dataReturn.put("usuario", usuario(userAut));
			    if(userAut.getCelula().getIdCelula()!=0l) {
			      	dataReturn.put("catCelula", celulaBO.consultarCelulaPorUsuario(userAut.getIdUsuario()));

			    }else {
					   
			    	dataReturn.put("catCelula", celulaBO.listarTodasLasCelulas());
			   }
				dataReturn.put("catReportes", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.REPORTES.getCve()));
				dataReturn.put("catTipoPeriodo", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_PERIODO.getCve()));
				dataReturn.put("catListaProductos", catBo.obtenerCatGeneralListaProductos());

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargaInicialReporetes ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	@RequestMapping(value = "/getReporte")
	@ResponseBody
	public Map<String, Object> getReporte(@RequestBody ReporteDTO reporte,Model model) throws BusinessException {
		
		Map<String, Object> dataReturn = new HashMap<>();
		
		try {
				
				List<String> listaCentro = new ArrayList<>();
				
				List<CelulaDto> listaCentroCostos = reporte.getListaCentroCostos();
				
				for(CelulaDto lista : listaCentroCostos) {
					listaCentro.add(String.valueOf(lista.getIdCelula()));
				}
				
				String listString = listaCentro.toString();
				listString = listString.substring(1, listString.length()-1); 
			
				if(CatReportesENUM.REPORTE_ESTIMADOS.getClave().equals(reporte.getCatReporte().getClave())) {
					
					reporte.setFechaInicio(Utilerias.convierteStringToDate("01/"+reporte.getMes()+"/"+reporte.getAnio()));
					reporte = getPeriodos(reporte);
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteEstimados(reporte.getFechaInicio(), reporte.getFechaFin(), listString, reporte.getCatTipoPeriodo().getClave()));
					
				}else if(CatReportesENUM.REPORTE_OPERACIONES.getClave().equals(reporte.getCatReporte().getClave())) {
					
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteOperaciones(reporte.getFechaInicio(), reporte.getFechaFin(), listString));
					
				}else if(CatReportesENUM.REPORTE_VARIACIONES.getClave().equals(reporte.getCatReporte().getClave())) {
					
					reporte.setFechaInicio(Utilerias.convierteStringToDate("01/"+reporte.getMes()+"/"+reporte.getAnio()));
					reporte = getPeriodos(reporte);
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteVariaciones(reporte.getFechaInicio(), reporte.getFechaFin(), listString, reporte.getCatTipoPeriodo().getClave()));
					
				}else if(CatReportesENUM.COLABORADORES_FALTANTES_EN_CRM.getClave().equals(reporte.getCatReporte().getClave())) {

					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteColabFaltCrm(listString));
					
				}else if (CatReportesENUM.FACTURACION_MES.getClave().equals(reporte.getCatReporte().getClave())) {
					String periodo = reporte.getMes() + reporte.getAnio();
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteFacturacionMensual(listString, periodo));
				}else if (CatReportesENUM.FACTURACION.getClave().equals(reporte.getCatReporte().getClave())) {
					String periodo = reporte.getMes() + reporte.getAnio();
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteFacturacion(reporte.getFechaInicio(), reporte.getFechaFin(), listString));
				}else if (CatReportesENUM.DISPERSION.getClave().equals(reporte.getCatReporte().getClave())) {
					String periodo = reporte.getMes() + reporte.getAnio();
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteDispersion(reporte.getFechaInicio(), reporte.getFechaFin(), listString));
				}else if (CatReportesENUM.COLABORADORES.getClave().equals(reporte.getCatReporte().getClave())) {
					String periodo = reporte.getMes() + reporte.getAnio();
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteColaboradores(listString));
				}else if (CatReportesENUM.CLIENTES.getClave().equals(reporte.getCatReporte().getClave())) {
					String periodo = reporte.getMes() + reporte.getAnio();
					dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteClientes(listString));
				}
				   else if(CatReportesENUM.REPORTE_CONSAR.getClave().equals(reporte.getCatReporte().getClave())) {
						
						dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteConsar(reporte.getFechaInicio(), reporte.getFechaFin(), listString));
						
					}
				   else if(CatReportesENUM.REPORTE_TESO_OPERA.getClave().equals(reporte.getCatReporte().getClave())) {
						
						dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteTesoOpera(reporte.getFechaInicio(), reporte.getFechaFin(), listString));
						
					}
				   else if(CatReportesENUM.REPORTE_PROD.getClave().equals(reporte.getCatReporte().getClave())) {
						
						dataReturn.put(ConstantesComunes.REPORTE, reportesBO.reporteProductos(reporte.getCatListaProductos().getClave(), listString));
						
					}
			
				dataReturn.put("catReportes", catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.REPORTES.getCve()));

				return dataReturn;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getReporte ", e);
			throw new BusinessException ("Ocurrio un error en el sistema");
		}
	}
	
	private UsuarioDTO usuario(UsuarioDTO userAut) {
	    UsuarioDTO usuario = new UsuarioDTO();
	    usuario.setIdUsuarioCelula(usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario())!=null ? usuarioBO.getIdCelulaByIdUsuario(getUser().getIdUsuario()) : null);
	    RolDTO rol = new RolDTO();
	    rol.setNombre(userAut.getUsuarioRols().get(0).getRol().getNombre());
	    usuario.setRol(rol);
	    
	    return usuario;
	}
	
	private ReporteDTO getPeriodos(ReporteDTO reporte) {
		
		Calendar cal = null;
		Date fechaFin = null;
		
		if("PRIM_QUIN".equals(reporte.getCatTipoPeriodo().getClave())) {
			
			cal = Calendar.getInstance(); 
			cal.setTime(reporte.getFechaInicio());
			cal.set(Calendar.DATE, 15);
	        fechaFin = cal.getTime();
	        reporte.setFechaFin(fechaFin);
	        
		}else if("SEGUN_QUIN".equals(reporte.getCatTipoPeriodo().getClave())){

			// fecha inicio
			cal = Calendar.getInstance(); 
			cal.setTime(reporte.getFechaInicio());
			cal.set(Calendar.DATE, 16);
	        Date fechaInicio = cal.getTime();
	        reporte.setFechaInicio(fechaInicio);
			
			// fecha fin
			cal = Calendar.getInstance();
		    cal.setTime(fechaInicio);
		    int dia = cal.getActualMaximum(Calendar.DATE);
			
		    cal = Calendar.getInstance();
		    cal.setTime(reporte.getFechaInicio());
		    cal.set(Calendar.DATE, dia);
	        fechaFin = cal.getTime();
	        reporte.setFechaFin(fechaFin);
		}
		
		return reporte;
	}

}
