package mx.com.consolida.controller.cotizador;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatImssDto;
import mx.com.consolida.catalogos.CatIsrDto;
import mx.com.consolida.catalogos.CatPrimaRiesgoDto;
import mx.com.consolida.catalogos.CatSalariosGeneralesDto;
import mx.com.consolida.catalogos.CatSubsidioDto;
import mx.com.consolida.catalogos.CatVacacionesDto;
import mx.com.consolida.dto.CatatologosCotizadorDto;
import mx.com.consolida.dto.ColaboradoresTempDto;
import mx.com.consolida.dto.CotizacionColaboradorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.CotizacionTotalesDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoCotizadorBO;
import mx.com.consolida.service.interfaz.CotizadorBO;
/**
 *
 * @author Abel
 */
@Controller
@RequestMapping("/cotizadorFormulas")
@SessionAttributes(value = {ReferenciaSeguridad.USUARIO_APLICATIVO,
    ReferenciaSeguridad.CLIENTE_TEMP, ReferenciaSeguridad.COTIZADOR, ReferenciaSeguridad.CATS_COTIZADOR, ReferenciaSeguridad.CLIENTETEMPBITACORASOLICITUDES})
public class colaboradorFormulasController {
	@Autowired
	private CotizadorBO cotizadorBO;
    @Autowired
    private CatalogoCotizadorBO catalogoCotizadorBO;
	
	private CotizacionDto cot;
	
	private CotizacionTotalesDto cotTotDto;
	
	private CatSalariosGeneralesDto smg;
	private CatSalariosGeneralesDto smgzf;
	private CatSalariosGeneralesDto smgv;
	private CatSalariosGeneralesDto uma;
	private CatSalariosGeneralesDto isn;

	private CatImssDto cfp;
	private CatImssDto ep;
	private CatImssDto et;
	private CatImssDto gmp;
	private CatImssDto gmt;
	private CatImssDto pdp;
	private CatImssDto pdt;
	private CatImssDto ivp;
	private CatImssDto ivt;
	private CatImssDto pdgp;
	private CatImssDto rp;
	private CatImssDto cvp;
	private CatImssDto cvt;
	private CatImssDto aip;
	private BigDecimal promedioVSM;
	
	private CatPrimaRiesgoDto primaRiesgo;
	
	
	private BigDecimal estudioImss;

	
    
    @RequestMapping(value = "/ejecutarCotizacion")
    @ResponseBody
    public CotizacionTotalesDto ejecutarCotizacion(Model model, @RequestBody List<EmpleadoDTO> empleados, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
        CotizacionTotalesDto totales = new CotizacionTotalesDto();
        if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR)) > 0) {
            CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
            CatatologosCotizadorDto cat = (CatatologosCotizadorDto) model.asMap().get(ReferenciaSeguridad.CATS_COTIZADOR);
            totales = ejecutarCotizacion(cot, empleados, cat, usuarioAplicativo);
        }
        return totales;
    }
    
    @RequestMapping(value = "/obtenerSalarios")
    @ResponseBody
    public Map<String ,Object> obtenerSalariosExcel(Model model, @RequestBody List<EmpleadoDTO> empleados, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
    	Map <String ,Object > datosExcel = new HashMap<String, Object>();
    	if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR))>0) {
	    	CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
	    	CatatologosCotizadorDto cat = (CatatologosCotizadorDto) model.asMap().get(ReferenciaSeguridad.CATS_COTIZADOR);
	    	empleados = obtenerSalarios(empleados, cot, cat);
	    	
	    	List<EmpleadoDTO> listEmpleados = empleados.stream().sorted((o1, o2)->{
	        	   if(o1.getSalarioDiario().compareTo(o2.getSalarioDiario()) == 0) {
		        	   if(o1.getNetoNomina().compareTo(o2.getNetoNomina()) == 0) {
		        		   if(o1.getGravados().compareTo(o2.getGravados()) == 0) {
		        			   if(o1.getExentos().compareTo(o2.getExentos()) == 0) {
		        				   if(o1.getFechaAntiguedad().compareTo(o2.getFechaAntiguedad()) == 0) {
		        					   if(o1.getAsimilados().compareTo(o2.getAsimilados()) == 0) {
		        						   if(o1.getaOtros().compareTo(o2.getaOtros())==0) {
		        							   return o1.getDgPrimaDeRiesgo().compareTo(o2.getDgPrimaDeRiesgo());
		        						   }else {
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
    	return datosExcel;
    	}else {
    		datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout");
    		return datosExcel;
    	}
    }
	
	public void inicializaCatalogos(CatatologosCotizadorDto cat) {
		
		smg = cat.getSmg();
		smgzf = cat.getSmgzf();
		uma = cat.getUma();
		isn = cat.getIsn();
		cfp = cat.getCfp();
		ep = cat.getEp();
		et = cat.getEt();
		gmp = cat.getGmp();
		gmt = cat.getGmt();
		pdp = cat.getPdp();
		pdt = cat.getPdt();
		ivp = cat.getIvp();
		ivt = cat.getIvt();
		pdgp = cat.getPdgp();
		rp = cat.getRp();
		cvp = cat.getCvp();
		cvt = cat.getCvt();
		aip = cat.getAip();
		
		estudioImss = cot.getIdPeriodicidad().getDiasPeriodo();
		smgv = cot.getIdZona().getClave().equals("ZONA-ZF") ? smgzf:smg;
		
		promedioVSM = new BigDecimal(1.14);
	}
	
	public CotizacionTotalesDto obtenerTotalesCotizacion(CotizacionDto cot, UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		cotTotDto = new CotizacionTotalesDto();
		List<CotizacionColaboradorDto> list = cotizadorBO.obtenerColaboradoresByIdCotizacion(cot.getIdCotizacion());
		for (CotizacionColaboradorDto ccDto : list) {
			cotTotDto(ccDto,cot);
		}
		cotizadorBO.guardarcotizacionTotal(cotTotDto);
		return cotTotDto;
	}

	public CotizacionTotalesDto ejecutarCotizacion(CotizacionDto cot, List<EmpleadoDTO> listEmpleados,
			CatatologosCotizadorDto cat, UsuarioAplicativo usuarioAplicativo) throws BusinessException {

		boolean bandera = false;
		int x = 1;
		
		BigDecimal assSDAnterior = null;
		BigDecimal netoAnterior = null;
		BigDecimal gravadosAnterior = null;
		BigDecimal exentosAnterior = null;
		Date fechaAntiguedadAnterior = null;
		BigDecimal asimiladosAnterior = null;
		BigDecimal otrosAnterior = null;
		String primavacacionalAnterior = null;

		CotizacionColaboradorDto ccDto = null;
		CotizacionColaboradorDto ccDtoAnterior = null;
		ColaboradoresTempDto ctDto = null;
		ColaboradoresTempDto ctDtoAnterior = null;

		this.cotTotDto = new CotizacionTotalesDto();
		this.cot = cot;
		cot.setComisionImss(cot.getComisionImss() != null ? cot.getComisionImss() : new BigDecimal(0));
		inicializaCatalogos(cat);

		if (listEmpleados != null && !listEmpleados.isEmpty()) {
			for (EmpleadoDTO empl : listEmpleados) {
				ctDto = new ColaboradoresTempDto();
				ccDto = new CotizacionColaboradorDto();
				if(ccDto.getaNetoTotalAnterior()==null) {
					ccDto.setaNetoTotalAnterior(new BigDecimal(0));
				}
				ccDto.setDgPrimaDeRiesgo(empl.getDgPrimaDeRiesgo() != null ? empl.getDgPrimaDeRiesgo() : "I");
				
				ccDto.setDgFechaDeAntIguedad(empl.getFechaAntiguedad() != null ? empl.getFechaAntiguedad():new Date());
				ccDto.setAssGravados(empl.getGravados());
				ccDto.setAssExentos(empl.getExentos());
				ccDto.setAasAsimialdos(empl.getAsimilados());
				ccDto.setaOtros(empl.getaOtros());
				ccDto.setAssNetoNomina(empl.getNetoNomina());
				ccDto.setDgMontoBrutoMensual(cot.getDgMontoBrutoMensual() == null ? new BigDecimal(0)
						: cot.getDgMontoBrutoMensual());
				primaRiesgo = catalogoCotizadorBO.otenerIsrByPrimaRiegoVar(ccDto.getDgPrimaDeRiesgo());

				ccDto.setDgVSM(cot.getDgVSM() == null ? new BigDecimal(0) : cot.getDgVSM());
				ccDto.setDgporcCotizacionDeseado(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());
				ccDto.setDgporcBeneficioFiscal(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());
				ccDto.setDgporcCotizacionDeseado(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());

				ccDto.setAssSD(empl.getSalarioDiario());
				

				if (bandera && empl.getSalarioDiario().compareTo(assSDAnterior) == 0 && empl.getNetoNomina().compareTo(netoAnterior) == 0 && empl.getGravados().compareTo(gravadosAnterior) == 0 
						&& empl.getExentos().compareTo(exentosAnterior) == 0 && empl.getFechaAntiguedad().compareTo(fechaAntiguedadAnterior) == 0 && empl.getAsimilados().compareTo(asimiladosAnterior) == 0
						&& empl.getaOtros().compareTo(otrosAnterior) == 0 && ccDto.getDgPrimaDeRiesgo().compareTo(primavacacionalAnterior) == 0) {
					
					ctDtoAnterior = cotizadorBO.guardarEmpleado(ctDtoAnterior, usuarioAplicativo);
					ctDto.setIdColaboradorTemp(ctDtoAnterior.getIdColaboradorTemp());

					ccDto = cotizadorBO.obtenerCotizacionColaboradorById(ccDtoAnterior.getIdCotizacionColaborador());
					ccDto.setIdCotizacionColaborador(null);
					ccDto.setIdCotizacion(cot);
					ccDto.setIdColaboradorTemp(ctDto);
					ccDto.setIdUsuarioAlta(usuarioAplicativo.getIdUsuario());
					ccDto.setFechaAlta(new Date());
					ccDto.setDgFechaDeAntIguedad(fechaAntiguedadAnterior != null ? fechaAntiguedadAnterior : new Date());
					ccDto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					ccDtoAnterior = cotizadorBO.guardarcotizacionColaborador(ccDto, usuarioAplicativo);
				} else {

					if (ccDto.getAssSD() == null || ccDto.getAssSD().compareTo(new BigDecimal(0)) == 0) {
						ccDto.setSeguirCalculando(true);
						assSD(empl.getNetoNomina(), null,ccDto);
					}
					ejecutarPrincipal(ccDto);
					ctDto = llenarColaboradoresTempDto(ccDto);
					ctDto.setFechaAlta(new Date());
					ctDto.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
					ctDto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus().longValue());
					ctDto.setIdCotizacion(cot);
					ctDtoAnterior = cotizadorBO.guardarEmpleado(ctDto, usuarioAplicativo);
					ctDto.setIdColaboradorTemp(ctDtoAnterior.getIdColaboradorTemp());

					ejecutar(ccDto);
					ccDto.setIdCotizacion(cot);
					ccDto.setIdColaboradorTemp(ctDto);
					ccDto.setIdUsuarioAlta(usuarioAplicativo.getIdUsuario());
					ccDto.setFechaAlta(new Date());
					ccDto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());

					ccDtoAnterior = cotizadorBO.guardarcotizacionColaborador(ccDto, usuarioAplicativo);

					assSDAnterior = ccDto.getAssSD();
					netoAnterior = ccDto.getAssNetoNomina();
					gravadosAnterior = ccDto.getAssGravados();
					exentosAnterior = ccDto.getAssExentos();
					fechaAntiguedadAnterior = ccDto.getDgFechaDeAntIguedad();
					asimiladosAnterior = ccDto.getAasAsimialdos();
					otrosAnterior = ccDto.getaOtros();
					primavacacionalAnterior = ccDto.getDgPrimaDeRiesgo();
					bandera = true;
				}
			}
		}
		return cotTotDto;
	}
	
	
	
    public void assSD(BigDecimal neto,Integer valorAumento, CotizacionColaboradorDto ccDto) {
    	ccDto.setAssSDAnterior(ccDto.getAssSD());
		if (valorAumento == null) {
			valorAumento = 1000;
			switch (cot.getIdPeriodicidad().getIdTipoPago().intValue()) {
			case 1:
				ccDto.setAssSD(neto.divide(new BigDecimal(7.00), 10, RoundingMode.HALF_UP));
				break;
			case 2:
				ccDto.setAssSD(neto.divide(new BigDecimal(15.00), 10, RoundingMode.HALF_UP));
				break;
			case 3:
				ccDto.setAssSD(neto.divide(new BigDecimal(31.00), 10, RoundingMode.HALF_UP));
				break;
			case 4:
				ccDto.setAssSD(neto.divide(new BigDecimal(14.00), 10, RoundingMode.HALF_UP));
				break;
			case 5:
				ccDto.setAssSD(neto.divide(new BigDecimal(365.00), 10, RoundingMode.HALF_UP));
				break;
			}
		} else {
			if(valorAumento == 0) {
				assSDDecimal(neto,new BigDecimal(0.1),0, ccDto);
				return;
			}else {
				ccDto.setAssSD(ccDto.getAssSD().add(new BigDecimal(valorAumento)));
			}
		}
		ejecutarPrincipal(ccDto);
    	if(neto.compareTo(ccDto.getAssNetoNomina())==1) {
    		assSD(neto,valorAumento,ccDto);
    	} else if(neto.compareTo(ccDto.getAssNetoNomina())==0) {
    		return;
    	}else {
    		if(valorAumento != 0) {
    			ccDto.setAssSD(ccDto.getAssSDAnterior());
    		}
    		switch(valorAumento) {
			case 1000:
				valorAumento = 100;
				break;
			case 100:
				valorAumento = 10;
				break;
			case 10:
				valorAumento = 1;
				break;
			case 1:
				valorAumento = 0;
				break;
			case 0:
				ccDto.setSeguirCalculando(false);
				break;
			}
    		if(ccDto.isSeguirCalculando()) {
    			assSD(neto,valorAumento, ccDto);
    		}else {
    			return;
    		}
    	}
    }
    
    public void assSDDecimal(BigDecimal neto, BigDecimal valorAumento, Integer inicialDecimales,CotizacionColaboradorDto ccDto) {
    	ccDto.setAssSDAnterior(ccDto.getAssSD());
    	String aux = ccDto.getAssSD().toString();
		String[] valor = aux.split("\\.");
		if(inicialDecimales!=null) {
			aux = valor[0]+"."+inicialDecimales.toString();
			ccDto.setAssSD(new BigDecimal(aux));
		}else {
			ccDto.setAssSD(disminuyeDecimal(ccDto.getAssSD(), valorAumento));
		}
		if(valor.length > 1 && valor[1].length()==5) {
			return;
		}
    	ejecutarPrincipal(ccDto);
    	if(neto.compareTo(ccDto.getAssNetoNomina())==1) {
    		assSDDecimal(neto,valorAumento,null,ccDto);
    	} else if(neto.compareTo(ccDto.getAssNetoNomina())==0) {
    		return;
    	}else {
    		if(valorAumento.compareTo(new BigDecimal(0))!=0) {
    			ccDto.setAssSD(ccDto.getAssSDAnterior());
    		}
    		
    		if(valorAumento.compareTo(new BigDecimal(0.1))==0) {
				valorAumento = new BigDecimal("0.01");
    		} else if(valorAumento.compareTo(new BigDecimal("0.01"))==0) {
				valorAumento = new BigDecimal("0.001");
    		} else if(valorAumento.compareTo(new BigDecimal("0.001"))==0) {
				valorAumento = new BigDecimal("0.0001");
    		} else if(valorAumento.compareTo(new BigDecimal("0.0001"))==0) {
				valorAumento = new BigDecimal("0.00001");
    		} else if(valorAumento.compareTo(new BigDecimal("0.00001"))==0) {
    			valorAumento = new BigDecimal("0.000001");
			} else if(valorAumento.compareTo(new BigDecimal("0.000001"))==0) {
				valorAumento = new BigDecimal("0.0000001");
			} else if(valorAumento.compareTo(new BigDecimal("0.0000001"))==0) {
				valorAumento = new BigDecimal("0.00000001");
			} else {
				ccDto.setSeguirCalculando(false);
			}
    		
    		if(ccDto.isSeguirCalculando()) {
    			assSDDecimal(neto,valorAumento,null,ccDto);
    		}else {
    			return;
    		}
    	}
    }
    public void assSDI(CotizacionColaboradorDto ccDto) {
    	BigDecimal variable;
    	if(cot.getIdVacaciones().getDescripcion().equals("Ley")) {
    		Date fechaActual = new Date();
        	int dias=(int) ((fechaActual.getTime()- (ccDto.getDgFechaDeAntIguedad()==null? fechaActual :ccDto.getDgFechaDeAntIguedad()).getTime())/86400000) + 1;
        	BigDecimal valor = new BigDecimal((dias) /365);
        	CatVacacionesDto vacaciones = catalogoCotizadorBO.obtenerVacacionesByAntiguedad(valor);
        	variable = vacaciones.getVacaciones();
    	}else {
    		variable = new BigDecimal(cot.getDiasVacaciones()!=null ?cot.getDiasVacaciones(): 0);
    	}
    	BigDecimal aguinaldo = cot.getAguinaldo()!=null ?cot.getAguinaldo(): new BigDecimal(0);
    	BigDecimal primaVacacional = cot.getPrimaVacacional()!=null ?cot.getPrimaVacacional(): new BigDecimal(0);
    	BigDecimal var1 = (variable.multiply(primaVacacional)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal var2 = var1.add(aguinaldo);
    	BigDecimal var3 = var2.divide(new BigDecimal(365), 10, RoundingMode.HALF_UP);
    	BigDecimal var4 = var3.add(new BigDecimal(1));
    	
    	BigDecimal variable_1 = var4.setScale(4, BigDecimal.ROUND_DOWN);
    	BigDecimal variable_4 = variable_1.multiply(ccDto.getAssSD());
    	variable_4 = redondearDecimales(variable_4.doubleValue(), 2);
    	BigDecimal variable_5 = (new BigDecimal(25).multiply(uma.getValor()));
    	if(variable_4.compareTo(variable_5)==1 ) {
    		ccDto.setAssSDI(variable_5);
    	}else {
    		ccDto.setAssSDI(variable_4);
    	}
    }
    
    public void assSuedo(CotizacionColaboradorDto ccDto) {
    	//En el Excel se encuentran de esta manera (invertidos)
    	if(cot.getIdDias().getClave().equals("DIAS-NAT")) {
    		ccDto.setAssSuedo(ccDto.getAssSD().multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
    	}else {
    		ccDto.setAssSuedo(ccDto.getAssSD().multiply(cot.getIdPeriodicidad().getDiasNaturales()));
    	}
    	ccDto.setAssSuedo(redondearDecimales(ccDto.getAssSuedo().doubleValue(), 2));
    }
    
    public void assISR(CotizacionColaboradorDto ccDto) {
    	BigDecimal variable_1 = ccDto.getAssSuedo().add(ccDto.getAssGravados()==null ? new BigDecimal(0):ccDto.getAssGravados());
    	CatIsrDto isrDto =catalogoCotizadorBO.otenerIsrByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),variable_1);
    	if(isrDto==null || isrDto.getIdCatIsr()==null) {
    		isrDto = new CatIsrDto();
    		isrDto.setCuotaFija(new BigDecimal(0));
    		isrDto.setLimiteInferior(new BigDecimal(0));
    		isrDto.setLimiteSuperior(new BigDecimal(0));
    		isrDto.setPorcentajecatTipoPago(new BigDecimal(0));
    	}
    	BigDecimal variable_2 = variable_1.subtract(isrDto.getLimiteInferior());
    	BigDecimal variable_3;
    	CatSubsidioDto subcidioDto = catalogoCotizadorBO.otenerSubsidioByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getAssSuedo());
    	variable_3 = subcidioDto.getSubsidio();
    	BigDecimal varP = isrDto.getPorcentajecatTipoPago().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal var1 = variable_2.multiply(varP);
    	BigDecimal var2 = var1.add(isrDto.getCuotaFija());
    	ccDto.setAssISR((var2).subtract(variable_3));
    	int valor = ccDto.getAssISR().compareTo(new BigDecimal(1));
    	ccDto.setAssISR((valor == -1 ||valor == 0 ? new BigDecimal(0):ccDto.getAssISR()));
    	ccDto.setAssISR(redondearDecimales(ccDto.getAssISR().doubleValue(), 2));
    	
    }
    
    public void assSubsidio(CotizacionColaboradorDto ccDto) {
    	int valor = ccDto.getAssISR().compareTo(new BigDecimal(1));
    	if(valor == -1 ||valor == 0) {
    		BigDecimal var = ccDto.getAssSuedo().add(ccDto.getAssGravados());
    		CatIsrDto isrDto = catalogoCotizadorBO.otenerIsrByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),var);
    		CatSubsidioDto subcidioDto = catalogoCotizadorBO.otenerSubsidioByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getAssSuedo());
    		if(isrDto==null || isrDto.getIdCatIsr()==null) {
        		isrDto = new CatIsrDto();
        		isrDto.setCuotaFija(new BigDecimal(0));
        		isrDto.setLimiteInferior(new BigDecimal(0));
        		isrDto.setLimiteSuperior(new BigDecimal(0));
        		isrDto.setPorcentajecatTipoPago(new BigDecimal(0));
        	}
    		BigDecimal varP = isrDto.getPorcentajecatTipoPago().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    		BigDecimal var1 = (var.subtract(isrDto.getLimiteInferior())).multiply(varP).add(isrDto.getCuotaFija());
    		ccDto.setAssSubsidio(var1.subtract(subcidioDto.getSubsidio()));
    	}else {
    		ccDto.setAssSubsidio(new BigDecimal(0));
    	}
    	ccDto.setAssSubsidio(ccDto.getAssSubsidio().compareTo(new BigDecimal(0))==-1 ? ccDto.getAssSubsidio().multiply(new BigDecimal(-1)):ccDto.getAssSubsidio());
    	ccDto.setAssSubsidio(redondearDecimales(ccDto.getAssSubsidio().doubleValue(), 2));
    }
    
    
    public void assCoImss(CotizacionColaboradorDto ccDto) {
    	BigDecimal variable_1 = new BigDecimal(0);
    	if(ccDto.getAssSD().compareTo(smgv.getValor())==0) {
    		ccDto.setAssCoImss(variable_1);
    		return;
    	}
    	BigDecimal variable_2;
    	BigDecimal aux = uma.getValor().multiply(new BigDecimal(3));
    	int valid = ccDto.getAssSDI().compareTo(aux);
    	if(valid==1) {
    		BigDecimal restUma = ccDto.getAssSDI().subtract(aux);
    		variable_1 = (restUma).multiply(et.getValor());
    	} 
    	variable_2 = variable_1.multiply(cot.getIdPeriodicidad().getDiasPeriodo());
    	BigDecimal var1 = variable_2.add((ccDto.getAssSDI().multiply(gmt.getValor())).multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
    	
    	BigDecimal var2 = (ccDto.getAssSDI().multiply(pdt.getValor())).multiply(cot.getIdPeriodicidad().getDiasPeriodo());
    	BigDecimal var3 = (ccDto.getAssSDI().multiply(ivt.getValor())).multiply(cot.getIdPeriodicidad().getDiasPeriodo());
    	BigDecimal var4 = (ccDto.getAssSDI().multiply(cvt.getValor())).multiply(cot.getIdPeriodicidad().getDiasPeriodo());
    	ccDto.setAssCoImss((( var1.add(var2)).add(var3)).add(var4)) ;
    	ccDto.setAssCoImss(redondearDecimales(ccDto.getAssCoImss().doubleValue(), 2));
    }
    
    public void assNetoNomina(CotizacionColaboradorDto ccDto) {
    	ccDto.setAssNetoNomina((((ccDto.getAssSuedo().add(ccDto.getAssGravados())).add(ccDto.getAssExentos())).add(ccDto.getAssSubsidio())).subtract((ccDto.getAssISR().add(ccDto.getAssCoImss()))));
    	ccDto.setAssNetoNomina(redondearDecimales(ccDto.getAssNetoNomina().doubleValue(), 2));
    }
    
    public void aasISR(CotizacionColaboradorDto ccDto) {
    	if(ccDto.getAasAsimialdos() != null) {
    		CatIsrDto isrDto =catalogoCotizadorBO.otenerIsrByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getAasAsimialdos());
    		if(isrDto==null || isrDto.getIdCatIsr()==null) {
        		isrDto = new CatIsrDto();
        		isrDto.setCuotaFija(new BigDecimal(0));
        		isrDto.setLimiteInferior(new BigDecimal(0));
        		isrDto.setLimiteSuperior(new BigDecimal(0));
        		isrDto.setPorcentajecatTipoPago(new BigDecimal(0));
        	}
    		BigDecimal op1 = ccDto.getAasAsimialdos().subtract(isrDto.getLimiteInferior());
    		BigDecimal varP = isrDto.getPorcentajecatTipoPago().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    		BigDecimal op2 = op1.multiply(varP);
    		ccDto.setAasISR(op2.add(isrDto.getCuotaFija()));
    	}else {
    		ccDto.setAasISR(new BigDecimal(0));
    	}
    	ccDto.setAasISR(redondearDecimales(ccDto.getAasISR().doubleValue(), 2));
    }
    public void aasNetoAsimilados(CotizacionColaboradorDto ccDto) {
    	ccDto.setAasAsimialdos(ccDto.getAasAsimialdos()==null ? new BigDecimal(0):ccDto.getAasAsimialdos());
    	ccDto.setAasNetoAsimilados(ccDto.getAasAsimialdos().subtract(ccDto.getAasISR()));
    }
    public void aNetoTotal(CotizacionColaboradorDto ccDto) {
    	ccDto.setaOtros(ccDto.getaOtros() == null ? new BigDecimal(0):ccDto.getaOtros());
    	ccDto.setaNetoTotal((ccDto.getAssNetoNomina().add(ccDto.getAasNetoAsimilados())).add(ccDto.getaOtros()));
    }
    public void acfaPercepcionTotal(CotizacionColaboradorDto ccDto) {
    	BigDecimal op1 = ccDto.getAssSuedo().add(ccDto.getAssGravados());
    	BigDecimal op2 = op1.add(ccDto.getAssExentos());
    	BigDecimal op3 = op2.add(ccDto.getAssSubsidio());
    	BigDecimal op4 = op3.add(ccDto.getAasNetoAsimilados());
    	BigDecimal op5 = op4.add(ccDto.getaOtros());
    	ccDto.setAcfaPercepcionTotal(op5.add(ccDto.getAasISR()));
    }
    public void acfaCargaSocial(CotizacionColaboradorDto ccDto) {
    	if(ccDto.getAssSD().compareTo(smgv.getValor())==0) {/*validado contra el excel*/
    		BigDecimal var;
    		BigDecimal umaMult3= uma.getValor().multiply(new BigDecimal(3));
    		if(ccDto.getAssSDI().compareTo(umaMult3)==1) {
    			var = ccDto.getAssSDI().subtract(umaMult3);
    			var = var.multiply(et.getValor());
    		}else {
    			var = new BigDecimal(0);
    		}
    		var = var.multiply(estudioImss);
    		BigDecimal variable1 = (ccDto.getAssSDI().multiply(gmt.getValor())).multiply(estudioImss);
    		BigDecimal variable2 = (ccDto.getAssSDI().multiply(pdt.getValor())).multiply(estudioImss);
    		BigDecimal variable3 = (ccDto.getAssSDI().multiply(ivt.getValor())).multiply(estudioImss);
    		BigDecimal variable4 = (ccDto.getAssSDI().multiply(cvt.getValor())).multiply(estudioImss);
    		BigDecimal variable5;
    		if(ccDto.getAssSDI().compareTo(new BigDecimal(0))==1) {
    			BigDecimal var1 = primaRiesgo.getRiesgoTrabajo().multiply(ccDto.getAssSDI());
    			var1 = (var1.multiply(estudioImss)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    			
    			BigDecimal var2 = (uma.getValor().multiply(cfp.getValor())).multiply(estudioImss);
    			BigDecimal var3;
    			if(ccDto.getAssSDI().compareTo(umaMult3)==1) {
    				var3 = ccDto.getAssSDI().subtract(umaMult3);
    				var3 =  var3.multiply(ep.getValor());
    			}else {
    				var3 = new BigDecimal(0);
    			}
    			var3 = var3.multiply(estudioImss);
    			BigDecimal var4 =(ccDto.getAssSDI().multiply(gmp.getValor()).multiply(estudioImss));
        		BigDecimal var5 = (ccDto.getAssSDI().multiply(pdp.getValor()).multiply(estudioImss));
        		BigDecimal var6 = (ccDto.getAssSDI().multiply(ivp.getValor()).multiply(estudioImss));
        		BigDecimal var7 = (ccDto.getAssSDI().multiply(pdgp.getValor()).multiply(estudioImss));
        		BigDecimal var8 = (ccDto.getAssSDI().multiply(rp.getValor()).multiply(estudioImss));
        		BigDecimal var9 = (ccDto.getAssSDI().multiply(cvp.getValor()).multiply(estudioImss));
        		BigDecimal var10 = (ccDto.getAssSDI().multiply(aip.getValor()).multiply(estudioImss));
        		BigDecimal varP = isn.getValor().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
        		BigDecimal var11 = ccDto.getAssSuedo().multiply(varP);
        		variable5 = var1.add(var2.add(var3.add(var4.add(var5.add(var6.add(var7.add(var8.add(var9.add(var10.add(var11))))))))));
    		} else {
    			variable5 = new BigDecimal(0);
    		}
    		ccDto.setAcfaCargaSocial(variable1.add(variable2.add(variable3.add(variable4.add(variable5)))));
    		
    		
    	}else {/*validado contra el excel*/
    		if(ccDto.getAssSDI().compareTo(new BigDecimal(0))==1) {
    			BigDecimal var1 = ((primaRiesgo.getRiesgoTrabajo().multiply(ccDto.getAssSDI())).multiply(estudioImss)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    			var1 = redondearDecimales(var1.doubleValue(), 8);
    			BigDecimal var2 = (uma.getValor().multiply(cfp.getValor())).multiply(estudioImss);
    			var2 = redondearDecimales(var2.doubleValue(), 2);
    			BigDecimal var3;
    			if(ccDto.getAssSDI().compareTo(uma.getValor().multiply(new BigDecimal(3)))==1) {
    				BigDecimal operacion = uma.getValor().multiply(new BigDecimal(3));
    				var3 = (ccDto.getAssSDI().subtract((operacion)).multiply(ep.getValor()));
    				var3 = var3.multiply(estudioImss);
    			}else {
    				var3 = new BigDecimal(0);
    			}
    			BigDecimal var4 =(ccDto.getAssSDI().multiply(gmp.getValor()).multiply(estudioImss));
        		BigDecimal var5 = (ccDto.getAssSDI().multiply(pdp.getValor()).multiply(estudioImss));
        		BigDecimal var6 = (ccDto.getAssSDI().multiply(ivp.getValor()).multiply(estudioImss));
        		BigDecimal var7 = (ccDto.getAssSDI().multiply(pdgp.getValor()).multiply(estudioImss));
        		BigDecimal var8 = (ccDto.getAssSDI().multiply(rp.getValor()).multiply(estudioImss));
        		BigDecimal var9 = (ccDto.getAssSDI().multiply(cvp.getValor()).multiply(estudioImss));
        		BigDecimal var10 = (ccDto.getAssSDI().multiply(aip.getValor()).multiply(estudioImss));
        		
        		BigDecimal varP = isn.getValor().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
        		BigDecimal var11 = ccDto.getAssSuedo().multiply(varP);
        		ccDto.setAcfaCargaSocial(var1.add(var2.add(var3.add(var4.add(var5.add(var6.add(var7.add(var8.add(var9.add(var10.add(var11)))))))))));
    		} else {
    			ccDto.setAcfaCargaSocial(new BigDecimal(0));
    		}
    	}
    	ccDto.setAcfaCargaSocial(redondearDecimales(ccDto.getAcfaCargaSocial().doubleValue(), 2));
    }
    public void acfaComision(CotizacionColaboradorDto ccDto) {
    	if(cot.getTieneProvedor().equals(1)) {
    		BigDecimal  varp= cot.getFeeActual().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    		if(cot.getIdTipo().getClave().equals("TIP-NOM-NOM")) {
    			ccDto.setAcfaComision(ccDto.getAcfaPercepcionTotal().multiply(varp));
    		}else {
    			ccDto.setAcfaComision((ccDto.getAcfaPercepcionTotal().add(ccDto.getAcfaCargaSocial())).multiply(varp));
    		}
    	}
    	else {
    		ccDto.setAcfaComision(new BigDecimal(0));
    	}
    	ccDto.setAcfaComision(redondearDecimales(ccDto.getAcfaComision().doubleValue(), 2));
    }
    public void acfaSubtotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setAcfaSubtotalFactura((ccDto.getAcfaPercepcionTotal().add(ccDto.getAcfaCargaSocial())).add(ccDto.getAcfaComision()));
    }
    public void acfaIVA(CotizacionColaboradorDto ccDto) {
    	if(cot.getTieneProvedor()==1) {
    		ccDto.setAcfaIVA(ccDto.getAcfaSubtotalFactura().multiply(new BigDecimal(0.16)));
    	} else {
    		ccDto.setAcfaIVA(new BigDecimal(0));
    	}
    }
    public void acfaTotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setAcfaTotalFactura(ccDto.getAcfaSubtotalFactura().add(ccDto.getAcfaIVA()));
    }
    
    public BigDecimal calculaVSM(BigDecimal vsm, Integer valorAumento, CotizacionColaboradorDto ccDto) {
    	BigDecimal aumento = null;
    	if(valorAumento !=null) {
    		aumento = new BigDecimal(valorAumento);
    	}
    	ccDto.setGsssSD(vsm.multiply(smgv.getValor()));
    	//ejecuta calculo de ppp
    	gsssSDI(ccDto);	
    	gsssSuedo(ccDto);
    	gsssExentos(ccDto);
    	gsssISR(ccDto);
    	gsssSubsidio(ccDto);	
    	gsssCoImss(ccDto);	
    	gsssNetoNomina(ccDto);	
    	gsePlanPensionesPrivada(ccDto);
    	BigDecimal val = new BigDecimal(ccDto.getGsePlanPensionesPrivada().doubleValue()/ccDto.getaNetoTotal().doubleValue());//gsePlanPensionesPrivada.divide(aNetoTotal, 10);
    	val = val.multiply(new BigDecimal(100));
    	val = val.setScale(2, BigDecimal.ROUND_HALF_UP);
    	int bandera = ccDto.getDgporcCotizacionDeseado().compareTo(val);
//    	System.out.println(" VSM: "+ vsm );
    	
    	if(bandera==1) {
    		ccDto.setContador(ccDto.getContador()+1);
    		if(vsm.equals(new BigDecimal(1.5))) {
    			return vsm;
    		}
    		boolean seguirCalculando = true;
    		if(valorAumento != 0 && valorAumento != 9) {
    			vsm = vsm.subtract(new BigDecimal(valorAumento));
    		}
    		switch(valorAumento) {
			case 1:
				valorAumento = 0;
				break;
			case 0:
			case 9:
				valorAumento = 9;
				String valor = ".";
				
				for(int i=1;i<=ccDto.getDecimalesOcupar();i++) {
					valor = valor + 0; 
				}
				valor = valor + 1;
				aumento = new BigDecimal(valor);
				vsm = vsm.subtract(aumento);
				ccDto.setDecimalesOcupar(ccDto.getDecimalesOcupar()+1);
//				return null; 
				break;
			}
    		if(seguirCalculando && ccDto.getContador()<2) {
    			if(valorAumento == 0) {
    	    		vsm = vsm.add(new BigDecimal(.1));
    			}
    			vsm = calculaVSM(vsm,valorAumento,ccDto);
    		}
    	} else if(bandera==-1) {
    		ccDto.setContador(0);
    		if(valorAumento == null) {
				vsm = vsm.add(new BigDecimal(.5));
				valorAumento = 1;
	    	}else if(valorAumento == 0){
	    		vsm = vsm.add(new BigDecimal(.1));
	    	}else if(valorAumento == 9) {
	    		String valor = ".";
				for(int i=0;i<ccDto.getDecimalesOcupar();i++) {
					valor = valor + 0;
				}
				valor = valor + 1;
				aumento = new BigDecimal(valor);
				vsm = vsm.add(aumento);
				
	    	} else {
	    		vsm = vsm.add(new BigDecimal(valorAumento));
	    	}
    		vsm = calculaVSM(vsm,valorAumento, ccDto);
    	}else if(bandera==0) {
    		return vsm;
    	}
    	return vsm;
    }
    public BigDecimal calculaVSMByNeto(BigDecimal vsm, Integer valorAumento, CotizacionColaboradorDto ccDto) {
    	BigDecimal aumento = null;
    	if(valorAumento !=null) {
    		aumento = new BigDecimal(valorAumento);
    	}
    	ccDto.setGsssSD(vsm.multiply(smgv.getValor()));
    	//ejecuta calculo de ppp
    	gsssSDI(ccDto);	
    	gsssSuedo(ccDto);
    	gsssExentos(ccDto);
    	gsssISR(ccDto);
    	gsssSubsidio(ccDto);	
    	gsssCoImss(ccDto);	
    	gsssNetoNomina(ccDto);
    	BigDecimal val = ccDto.getGsssNetoNomina();
    	int bandera = val.compareTo(ccDto.getDgMontoBrutoMensual());
//    	System.out.println(" VSM: "+ vsm );
    	
    	if(bandera==1) {
    		ccDto.setContador(ccDto.getContador()+1);
    		if(vsm.equals(new BigDecimal(1.5))) {
    			return vsm;
    		}
    		boolean seguirCalculando = true;
    		if(valorAumento != 0 && valorAumento != 9) {
    			vsm = vsm.subtract(new BigDecimal(valorAumento));
    		}
    		switch(valorAumento) {
			case 1:
				valorAumento = 0;
				break;
			case 0:
			case 9:
				valorAumento = 9;
				String valor = ".";
				
				for(int i=1;i<=ccDto.getDecimalesOcupar();i++) {
					valor = valor + 0; 
				}
				valor = valor + 1;
				aumento = new BigDecimal(valor);
				vsm = vsm.subtract(aumento);
				ccDto.setDecimalesOcupar(ccDto.getDecimalesOcupar()+1);
//				return null; 
				break;
			}
    		if(seguirCalculando && ccDto.getContador()<2) {
    			if(valorAumento == 0) {
    	    		vsm = vsm.add(new BigDecimal(.1));
    			}
    			vsm = calculaVSMByNeto(vsm,valorAumento,ccDto);
    		}
    	} else if(bandera==-1) {
    		ccDto.setContador(0);
    		if(valorAumento == null) {
				vsm = vsm.add(new BigDecimal(.5));
				valorAumento = 1;
	    	}else if(valorAumento == 0){
	    		vsm = vsm.add(new BigDecimal(.1));
	    	}else if(valorAumento == 9) {
	    		String valor = ".";
				for(int i=0;i<ccDto.getDecimalesOcupar();i++) {
					valor = valor + 0;
				}
				valor = valor + 1;
				aumento = new BigDecimal(valor);
				vsm = vsm.add(aumento);
				
	    	} else {
	    		vsm = vsm.add(new BigDecimal(valorAumento));
	    	}
    		vsm = calculaVSMByNeto(vsm,valorAumento,ccDto);
    	}else if(bandera==0) {
    		return vsm;
    	}
    	return vsm;
    }
    
	public void gsssSD(CotizacionColaboradorDto ccDto) {
		ccDto.setDecimalesOcupar(0);
		ccDto.setContador(0);
    	BigDecimal variable_1 = cot.getIdDias().getClave().equals("DIAS-NAT") ? cot.getIdPeriodicidad().getDiasPeriodo() : cot.getIdPeriodicidad().getDiasNaturales();
    	if(ccDto.getDgVSM() !=null && ccDto.getDgVSM().compareTo(new BigDecimal(0))==1) {
    		ccDto.setDgVSM((ccDto.getDgVSM() != null ? ccDto.getDgVSM():new BigDecimal(0)));
    		ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
    	}else { 
    		if(cot.getIdTipoCotizacion().getClave().equals("TIP-COTZ-M")) {
    			int bandera = ccDto.getaNetoTotalAnterior()==null? 1 : ccDto.getaNetoTotalAnterior().compareTo(ccDto.getaNetoTotal());
        		if(bandera == 0) {
        			ccDto.setDgVSM(ccDto.getVsmAnterior());
        			ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
        		} else {
        			ccDto.setDgVSM(new BigDecimal(1.5));
        			ccDto.setDgVSM(calculaVSMByNeto(ccDto.getDgVSM(),null,ccDto));
        			ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
        			ccDto.setVsmAnterior(ccDto.getDgVSM());
        			ccDto.setaNetoTotalAnterior(ccDto.getaNetoTotal());
        		}
        	} else {
        		if(cot.getIdImss().getIdCatGeneral() != 9) {
	        		int bandera = ccDto.getaNetoTotalAnterior()==null? 1 : ccDto.getaNetoTotalAnterior().compareTo(ccDto.getaNetoTotal());
	        		if(bandera == 0) {
	        			ccDto.setDgVSM(ccDto.getVsmAnterior());
	        			ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
	        		} else {
	        			ccDto.setDgVSM(new BigDecimal(1.5));
	        			ccDto.setDgVSM(calculaVSM(ccDto.getDgVSM(),null,ccDto));
	        			ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
	        			if(ccDto.getDgVSM().compareTo(new BigDecimal(1.5))==-1) {
	        				ccDto.setDgVSM(new BigDecimal(1.5));
	        			}
	        			
	        			ccDto.setVsmAnterior(ccDto.getDgVSM());
	        			ccDto.setaNetoTotalAnterior(ccDto.getaNetoTotal());
	        		}
        		} else {
        			ccDto.setDgVSM(new BigDecimal(0));
        			ccDto.setGsssSD(ccDto.getDgVSM().multiply(smgv.getValor()));
        		}
        	}
    	}
    }

	public void gsssSDI(CotizacionColaboradorDto ccDto) {
		BigDecimal valorUMAx25 = (new BigDecimal(25).multiply(uma.getValor()));
		if (cot.getIdPrestaciones().getClave().equals("TIP-PRS-MP")) {
			BigDecimal variable1;
			if (cot.getIdVacaciones().getClave().equals("VAC-Ley")) {
				Date fechaActual = new Date();
				int dias = (int) ((fechaActual.getTime() - (ccDto.getDgFechaDeAntIguedad() == null ? fechaActual : ccDto.getDgFechaDeAntIguedad()).getTime()) / 86400000);
				BigDecimal valor = new BigDecimal((dias + 1) / 365);
				CatVacacionesDto vacaciones = catalogoCotizadorBO.obtenerVacacionesByAntiguedad(valor);
				variable1 = vacaciones.getVacaciones();
			} else {
				variable1 = new BigDecimal(cot.getDiasVacaciones()!=null ? cot.getDiasVacaciones():0);
			}
			BigDecimal primaVacacional = cot.getPrimaVacacional()!=null ?cot.getPrimaVacacional(): new BigDecimal(0);
			BigDecimal aguinaldo = cot.getAguinaldo()!=null ?cot.getAguinaldo(): new BigDecimal(0);
			BigDecimal var1 = (variable1.multiply(primaVacacional)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
			BigDecimal variable_1 = ((((var1).add(aguinaldo))
					.divide(new BigDecimal(365), 10, RoundingMode.HALF_UP)).add(new BigDecimal(1))).setScale(4, BigDecimal.ROUND_DOWN);
			BigDecimal variable_4 = (variable_1.multiply(ccDto.getGsssSD())).setScale(2, BigDecimal.ROUND_UP);

			if (variable_4.compareTo(valorUMAx25) == 1) {
				ccDto.setGsssSDI(valorUMAx25);
			} else {
				ccDto.setGsssSDI(variable_4);
			}
		} else {

			if (ccDto.getGsssSD().multiply(new BigDecimal(1.0452)).compareTo(valorUMAx25) == 1) {
				ccDto.setGsssSDI( valorUMAx25);
			} else {
				ccDto.setGsssSDI(ccDto.getGsssSD().multiply(new BigDecimal(1.0452)));
			}
		}
		ccDto.setGsssSDI( redondearDecimales(ccDto.getGsssSDI().doubleValue(), 2));
	}
    public void gsssSuedo(CotizacionColaboradorDto ccDto) {
    	BigDecimal variable_1 = cot.getIdDias().getClave().equals("DIAS-NAT") 
    			? 
    			cot.getIdPeriodicidad().getDiasPeriodo() : cot.getIdPeriodicidad().getDiasNaturales();
    			ccDto.setGsssSuedo( ccDto.getGsssSD().multiply(variable_1));
    			ccDto.setGsssSuedo(redondearDecimales(ccDto.getGsssSuedo().doubleValue(), 2));
    }
    public void gsssExentos(CotizacionColaboradorDto ccDto) {
    	ccDto.setGsssExentos(new BigDecimal(0));
    }
    public void gsssISR(CotizacionColaboradorDto ccDto) {
    	CatIsrDto isrDto = catalogoCotizadorBO.otenerIsrByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getGsssSuedo());
    	if(isrDto==null || isrDto.getIdCatIsr()==null) {
    		isrDto = new CatIsrDto();
    		isrDto.setCuotaFija(new BigDecimal(0));
    		isrDto.setLimiteInferior(new BigDecimal(0));
    		isrDto.setLimiteSuperior(new BigDecimal(0));
    		isrDto.setPorcentajecatTipoPago(new BigDecimal(0));
    	}
		CatSubsidioDto subcidioDto = catalogoCotizadorBO.otenerSubsidioByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getGsssSuedo());
		BigDecimal varP = isrDto.getPorcentajecatTipoPago().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal var1 = (((ccDto.getGsssSuedo().subtract(isrDto.getLimiteInferior())).multiply(varP)).add(isrDto.getCuotaFija())).subtract(subcidioDto.getSubsidio());
    	if(var1.compareTo(new BigDecimal(0)) == 1) {
    		ccDto.setGsssISR(var1);
    	}else {
    		ccDto.setGsssISR(new BigDecimal(0));
    	}
    	ccDto.setGsssISR(redondearDecimales(ccDto.getGsssISR().doubleValue(), 2));
    }

    public void gsssSubsidio(CotizacionColaboradorDto ccDto) {
    	if(ccDto.getGsssISR().equals(new BigDecimal(0))){
    		CatIsrDto isrDto =catalogoCotizadorBO.otenerIsrByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getGsssSuedo());
    		if(isrDto==null || isrDto.getIdCatIsr()==null) {
        		isrDto = new CatIsrDto();
        		isrDto.setCuotaFija(new BigDecimal(0));
        		isrDto.setLimiteInferior(new BigDecimal(0));
        		isrDto.setLimiteSuperior(new BigDecimal(0));
        		isrDto.setPorcentajecatTipoPago(new BigDecimal(0));
        	}
    		CatSubsidioDto subsidioDto = catalogoCotizadorBO.otenerSubsidioByIdPeriodicidadVar(cot.getIdPeriodicidad().getIdTipoPago().intValue(),ccDto.getGsssSuedo());
    		BigDecimal varP = isrDto.getPorcentajecatTipoPago().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    		BigDecimal var1 =  (ccDto.getGsssSuedo().subtract(isrDto.getLimiteInferior())).multiply(varP);
    		ccDto.setGsssSubsidio(((var1.add(isrDto.getCuotaFija())).subtract(subsidioDto.getSubsidio())));
    		
    		ccDto.setGsssSubsidio(ccDto.getGsssSubsidio().compareTo(new BigDecimal(0))==-1?ccDto.getGsssSubsidio().multiply(new BigDecimal(-1)):ccDto.getGsssSubsidio());

    	}else{ 
    		ccDto.setGsssSubsidio(new BigDecimal(0));
    	}	
    	ccDto.setGsssSubsidio(redondearDecimales(ccDto.getGsssSubsidio().doubleValue(), 2));
    	
    }
    
    public void gsssCoImss(CotizacionColaboradorDto ccDto) {
    	if(ccDto.getGsssSD().compareTo(smgv.getValor()) == 0) {
    		ccDto.setGsssCoImss(new BigDecimal(0));
    	}else {
    		BigDecimal variable = (new BigDecimal(3).multiply(uma.getValor()));
        	BigDecimal variable_1 = (ccDto.getGsssSDI().compareTo(variable)==1 )? (ccDto.getGsssSDI().subtract((variable))).multiply(et.getValor()) : new BigDecimal(0);
        	BigDecimal var1 = (variable_1.multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
        	BigDecimal var2 = (ccDto.getGsssSDI().multiply(gmt.getValor()).multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
        	BigDecimal var3 = (ccDto.getGsssSDI().multiply(pdt.getValor()).multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
        	BigDecimal var4 = (ccDto.getGsssSDI().multiply(ivt.getValor()).multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
        	BigDecimal var5 = (ccDto.getGsssSDI().multiply(cvt.getValor()).multiply(cot.getIdPeriodicidad().getDiasPeriodo()));
        	ccDto.setGsssCoImss((((var1.add(var2)).add(var3)).add(var4)).add(var5));
    	}
    	ccDto.setGsssCoImss(redondearDecimales(ccDto.getGsssCoImss().doubleValue(), 2));
    		
    }
    
    public void gsssNetoNomina(CotizacionColaboradorDto ccDto) {
    	ccDto.setGsssNetoNomina(((ccDto.getGsssSuedo().add(ccDto.getGsssExentos())).add(ccDto.getGsssSubsidio())).subtract((ccDto.getGsssISR().add(ccDto.getGsssCoImss()))));
    	ccDto.setGsssNetoNomina(redondearDecimales(ccDto.getGsssNetoNomina().doubleValue(), 2));
    }
    public void gsePlanPensionesPrivada(CotizacionColaboradorDto ccDto) {
    	BigDecimal var1 = ccDto.getaNetoTotal().subtract(ccDto.getGsssNetoNomina());
    	BigDecimal var2 = ccDto.getGsssISR().add(ccDto.getGsssCoImss());
    	BigDecimal sum1 = ccDto.getAssISR().add(ccDto.getAssCoImss());
    	BigDecimal var3 = sum1.subtract(var2);
//    	BigDecimal varp = dgporcBeneficioFiscal.divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal varp = new BigDecimal(0);
    	BigDecimal var4 = var3.multiply(varp);
    	BigDecimal var5 = var1.add(var4);
    	ccDto.setGsePlanPensionesPrivada(var5.compareTo(new BigDecimal(0))==-1? new BigDecimal(0):var5); 
    	ccDto.setGsePlanPensionesPrivada(redondearDecimales(ccDto.getGsePlanPensionesPrivada().doubleValue(), 2));
    }
    
    public void gsNetoTotal(CotizacionColaboradorDto ccDto) {
    	ccDto.setGsNetoTotal(ccDto.getGsssNetoNomina().add(ccDto.getGsePlanPensionesPrivada()));
    }
    public void gscfmPercepcionTotal(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfmPercepcionTotal((ccDto.getGsssSuedo().add(ccDto.getGsssSubsidio())).add(ccDto.getGsePlanPensionesPrivada()));
    }
    public void gscfmCargaSocial(CotizacionColaboradorDto ccDto) {
    	if(ccDto.getGsssSD().compareTo(smgv.getValor())==0) {/*falta por validar*/
    		BigDecimal var;
    		BigDecimal umaMult3= uma.getValor().multiply(new BigDecimal(3));
    		if(ccDto.getGsssSDI().compareTo(umaMult3)==1) {
    			var = ccDto.getGsssSDI().subtract(umaMult3);
    			var = var.multiply(et.getValor());
    		}else {
    			var = new BigDecimal(0);
    		}
    		var = var.multiply(estudioImss);
    		BigDecimal variable1 = (ccDto.getGsssSDI().multiply(gmt.getValor())).multiply(estudioImss);
    		BigDecimal variable2 = (ccDto.getGsssSDI().multiply(pdt.getValor())).multiply(estudioImss);
    		BigDecimal variable3 = (ccDto.getGsssSDI().multiply(ivt.getValor())).multiply(estudioImss);
    		BigDecimal variable4 = (ccDto.getGsssSDI().multiply(cvt.getValor())).multiply(estudioImss);
    		BigDecimal variable5;
    		if(ccDto.getGsssSDI().compareTo(new BigDecimal(0))==1) {
    			BigDecimal var1 = primaRiesgo.getRiesgoTrabajo().multiply(ccDto.getGsssSDI());
    			var1 = (var1.multiply(estudioImss)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    			
    			BigDecimal var2 = (uma.getValor().multiply(cfp.getValor())).multiply(estudioImss);
    			BigDecimal var3;
    			if(ccDto.getGsssSDI().compareTo(umaMult3)==1) {
    				var3 = ccDto.getGsssSDI().subtract(umaMult3);
    				var3 =  var3.multiply(ep.getValor());
    			}else {
    				var3 = new BigDecimal(0);
    			}
    			var3 = var3.multiply(estudioImss);
    			BigDecimal var4 =(ccDto.getGsssSDI().multiply(gmp.getValor()).multiply(estudioImss));
        		BigDecimal var5 = (ccDto.getGsssSDI().multiply(pdp.getValor()).multiply(estudioImss));
        		BigDecimal var6 = (ccDto.getGsssSDI().multiply(ivp.getValor()).multiply(estudioImss));
        		BigDecimal var7 = (ccDto.getGsssSDI().multiply(pdgp.getValor()).multiply(estudioImss));
        		BigDecimal var8 = (ccDto.getGsssSDI().multiply(rp.getValor()).multiply(estudioImss));
        		BigDecimal var9 = (ccDto.getGsssSDI().multiply(cvp.getValor()).multiply(estudioImss));
        		BigDecimal var10 = (ccDto.getGsssSDI().multiply(aip.getValor()).multiply(estudioImss));
        		BigDecimal varP = isn.getValor().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
        		BigDecimal var11 = ccDto.getGsssSuedo().multiply(varP);
        		variable5 = var1.add(var2.add(var3.add(var4.add(var5.add(var6.add(var7.add(var8.add(var9.add(var10.add(var11))))))))));
    		} else {
    			variable5 = new BigDecimal(0);
    		}
    		ccDto.setGscfmCargaSocial(variable1.add(variable2.add(variable3.add(variable4.add(variable5)))));
    	}else {/*validado contra el excel*/
    		if(ccDto.getGsssSDI().compareTo(new BigDecimal(0))==1) {
    			BigDecimal var1 = ((primaRiesgo.getRiesgoTrabajo().multiply(ccDto.getGsssSDI())).multiply(estudioImss)).divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    			var1 = redondearDecimales(var1.doubleValue(), 8);
    			BigDecimal var2 = (uma.getValor().multiply(cfp.getValor())).multiply(estudioImss);
    			var2 = redondearDecimales(var2.doubleValue(), 2);
    			BigDecimal var3;
    			if(ccDto.getGsssSDI().compareTo(uma.getValor().multiply(new BigDecimal(3)))==1) {
    				BigDecimal operacion = uma.getValor().multiply(new BigDecimal(3));
    				var3 = (ccDto.getGsssSDI().subtract((operacion)).multiply(ep.getValor()));
    				var3 = var3.multiply(estudioImss);
    			}else {
    				var3 = new BigDecimal(0);
    			}
    			BigDecimal var4 =(ccDto.getGsssSDI().multiply(gmp.getValor()).multiply(estudioImss));
        		BigDecimal var5 = (ccDto.getGsssSDI().multiply(pdp.getValor()).multiply(estudioImss));
        		BigDecimal var6 = (ccDto.getGsssSDI().multiply(ivp.getValor()).multiply(estudioImss));
        		BigDecimal var7 = (ccDto.getGsssSDI().multiply(pdgp.getValor()).multiply(estudioImss));
        		BigDecimal var8 = (ccDto.getGsssSDI().multiply(rp.getValor()).multiply(estudioImss));
        		BigDecimal var9 = (ccDto.getGsssSDI().multiply(cvp.getValor()).multiply(estudioImss));
        		BigDecimal var10 = (ccDto.getGsssSDI().multiply(aip.getValor()).multiply(estudioImss));
        		
        		BigDecimal varP = isn.getValor().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
        		BigDecimal var11 = ccDto.getGsssSuedo().multiply(varP);
        		ccDto.setGscfmCargaSocial(var1.add(var2.add(var3.add(var4.add(var5.add(var6.add(var7.add(var8.add(var9.add(var10.add(var11)))))))))));
    		} else {
    			ccDto.setGscfmCargaSocial(new BigDecimal(0));
    		}
    	}
    	ccDto.setGscfmCargaSocial(redondearDecimales(ccDto.getGscfmCargaSocial().doubleValue(), 2));
    }
    public void gscfmComision(CotizacionColaboradorDto ccDto) {
    	BigDecimal porcentajeImss= cot.getComisionImss().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal porcentajePpp= cot.getComisionPpp().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	BigDecimal var1 = ((ccDto.getGsssSuedo().add(ccDto.getGsssExentos())).add(ccDto.getGsssSubsidio())).add(ccDto.getGscfmCargaSocial());
    	
    	BigDecimal resul1 = porcentajeImss.multiply(var1);
    	BigDecimal resul2 = porcentajePpp.multiply(ccDto.getGsePlanPensionesPrivada());
    	ccDto.setGscfmComision((resul1).add(resul2));
    	ccDto.setGscfmComision(redondearDecimales(ccDto.getGscfmComision().doubleValue(), 2));
    }
    public void gscfmSubtotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfmSubtotalFactura((ccDto.getGscfmPercepcionTotal().add(ccDto.getGscfmCargaSocial())).add(ccDto.getGscfmComision()));
    	ccDto.setGscfmSubtotalFactura(redondearDecimales(ccDto.getGscfmSubtotalFactura().doubleValue(), 2));
    }
    
    public void gscfmIVA(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfmIVA(ccDto.getGscfmSubtotalFactura().multiply(new BigDecimal(0.16)));
    	ccDto.setGscfmIVA(redondearDecimales(ccDto.getGscfmIVA().doubleValue(), 2));
    }
    public void gscfmTotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfmTotalFactura(ccDto.getGscfmSubtotalFactura().add(ccDto.getGscfmIVA()));
    	ccDto.setGscfmTotalFactura(redondearDecimales(ccDto.getGscfmTotalFactura().doubleValue(), 2));
    }

    public void gscfpppPercepcionNomina(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppPercepcionNomina(ccDto.getGsssSuedo().add(ccDto.getGsssSubsidio()));
    	ccDto.setGscfpppPercepcionNomina( redondearDecimales(ccDto.getGscfpppPercepcionNomina().doubleValue(), 2));
    }
    
    public void gscfpppCargaSocial(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppCargaSocial(ccDto.getGscfmCargaSocial().compareTo(ccDto.getGscfmCargaSocial())==-1 ? ccDto.getGscfmCargaSocial().multiply(new BigDecimal(-1)):ccDto.getGscfmCargaSocial());
    }
    public void gscfpppCostoNomina(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppCostoNomina(ccDto.getGscfpppPercepcionNomina().add(ccDto.getGscfpppCargaSocial()));
    	ccDto.setGscfpppCostoNomina(redondearDecimales(ccDto.getGscfpppCostoNomina().doubleValue(), 2));
    }
    public void gscfpppPPP (CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppPPP(ccDto.getGsePlanPensionesPrivada());
    }
    public void gscfpppComision(CotizacionColaboradorDto ccDto) {
    	BigDecimal cPpp= cot.getComisionPpp().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP);
    	ccDto.setGscfpppComision(cPpp.multiply(ccDto.getGsePlanPensionesPrivada()));
    	ccDto.setGscfpppComision(redondearDecimales(ccDto.getGscfpppComision().doubleValue(), 2));
    }
    public void gscfpppSubtotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppSubtotalFactura(ccDto.getGscfpppPPP().add(ccDto.getGscfpppComision()));
    	ccDto.setGscfpppSubtotalFactura(redondearDecimales(ccDto.getGscfpppSubtotalFactura().doubleValue(), 2));
    }
    public void gscfpppIVA(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppIVA(ccDto.getGscfpppSubtotalFactura().multiply(new BigDecimal(0.16)));
    	ccDto.setGscfpppIVA(redondearDecimales(ccDto.getGscfpppIVA().doubleValue(), 2));
    }
    public void gscfpppTotalFactura(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppTotalFactura(ccDto.getGscfpppSubtotalFactura().add(ccDto.getGscfpppIVA()));
    	ccDto.setGscfpppTotalFactura(redondearDecimales(ccDto.getGscfpppTotalFactura().doubleValue(), 2));
    }
    public void gscfpppCostoTotal(CotizacionColaboradorDto ccDto) {
    	ccDto.setGscfpppCostoTotal(ccDto.getGscfpppCostoNomina().add(ccDto.getGscfpppSubtotalFactura()));
    }

    
    public void ejecutarPrincipal(CotizacionColaboradorDto ccDto) {
    	assSDI(ccDto);	
    	assSuedo(ccDto);	
    	assISR(ccDto);
    	assSubsidio(ccDto);
    	assCoImss(ccDto);	
    	assNetoNomina(ccDto);
    }
    public List<EmpleadoDTO> obtenerSalarios(List<EmpleadoDTO> listEmpleados,CotizacionDto cot,CatatologosCotizadorDto cat) {
    	this.cot = cot;
		inicializaCatalogos(cat);
		int x=1;
		EmpleadoDTO aux = null;
		BigDecimal  assSDAnterior=null;
		BigDecimal  netoAnterior = null;
		BigDecimal  gravadosAnterior = null;
		BigDecimal  exentosAnterior = null;
		Date  fechaAntiguedadAnterior = null;
		String primavacacionalAnterior = null;
		CotizacionColaboradorDto ccDto = null;
		
		if (listEmpleados != null && !listEmpleados.isEmpty()) {
			for (EmpleadoDTO empl : listEmpleados) {
				ccDto = new CotizacionColaboradorDto();
				ccDto.setDgPrimaDeRiesgo(empl.getDgPrimaDeRiesgo() != null ? empl.getDgPrimaDeRiesgo() : "I");
				ccDto.setDgFechaDeAntIguedad(empl.getFechaAntiguedad() != null ? empl.getFechaAntiguedad():new Date());
				ccDto.setAssGravados(empl.getGravados());
				ccDto.setAssExentos(empl.getExentos());
				ccDto.setAasAsimialdos(empl.getAsimilados());
				ccDto.setaOtros(empl.getaOtros());
				ccDto.setAssNetoNomina(empl.getNetoNomina());
				ccDto.setDgMontoBrutoMensual(cot.getDgMontoBrutoMensual() == null ? new BigDecimal(0)
						: cot.getDgMontoBrutoMensual());
				primaRiesgo = catalogoCotizadorBO.otenerIsrByPrimaRiegoVar(ccDto.getDgPrimaDeRiesgo());

				ccDto.setDgVSM(cot.getDgVSM() == null ? new BigDecimal(0) : cot.getDgVSM());
				ccDto.setDgporcCotizacionDeseado(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());
				ccDto.setDgporcBeneficioFiscal(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());
				ccDto.setDgporcCotizacionDeseado(cot.getDgporcCotizacionDeseado() == null ? new BigDecimal(0)
						: cot.getDgporcCotizacionDeseado());

				ccDto.setAssSD(empl.getSalarioDiario());
				
				if (ccDto.getAssSD() == null || ccDto.getAssSD().compareTo(new BigDecimal(0))==0) {
					ccDto.setSeguirCalculando(true);
					if(aux!=null 
							&& netoAnterior.compareTo(empl.getNetoNomina())==0
							&& ccDto.getAssGravados().compareTo(gravadosAnterior) == 0
							&& ccDto.getAssExentos().compareTo(exentosAnterior) == 0
							&& ccDto.getDgFechaDeAntIguedad().compareTo(fechaAntiguedadAnterior) == 0
							&& ccDto.getDgPrimaDeRiesgo().compareTo(primavacacionalAnterior) == 0
							) {
						ccDto.setAssSD(aux.getSalarioDiario());
						ccDto.setAssNetoNomina(aux.getNetoNomina());
						
					}else {
						assSD(empl.getNetoNomina(), null, ccDto);
						ejecutarPrincipal(ccDto);
						assSDAnterior = new BigDecimal(ccDto.getAssSD().doubleValue());
						netoAnterior = new BigDecimal(ccDto.getAssNetoNomina().doubleValue());
						
						gravadosAnterior = new BigDecimal(ccDto.getAssGravados().doubleValue());
						exentosAnterior = new BigDecimal(ccDto.getAssExentos().doubleValue());
						fechaAntiguedadAnterior = empl.getFechaAntiguedad();
						primavacacionalAnterior = ccDto.getDgPrimaDeRiesgo();
					}
				}else {
					if(aux!=null 
						&& ccDto.getAssSD().compareTo(assSDAnterior) == 0
						&& ccDto.getAssGravados().compareTo(gravadosAnterior) == 0
						&& ccDto.getAssExentos().compareTo(exentosAnterior) == 0
						&& ccDto.getDgFechaDeAntIguedad().compareTo(fechaAntiguedadAnterior) == 0
						&& ccDto.getDgPrimaDeRiesgo().compareTo(primavacacionalAnterior) == 0
						) {
						empl.setNetoNomina(netoAnterior);
						ccDto.setAssNetoNomina(netoAnterior);
					} else {
						ejecutarPrincipal(ccDto);
						assSDAnterior = new BigDecimal(ccDto.getAssSD().doubleValue());
						netoAnterior = new BigDecimal(ccDto.getAssNetoNomina().doubleValue());
						gravadosAnterior = new BigDecimal(ccDto.getAssGravados().doubleValue());
						exentosAnterior = new BigDecimal(ccDto.getAssExentos().doubleValue());
						fechaAntiguedadAnterior = empl.getFechaAntiguedad();

						primavacacionalAnterior = ccDto.getDgPrimaDeRiesgo();
					}
				}
				
				empl.setSalarioDiario(new BigDecimal(ccDto.getAssSD().doubleValue())); 
				empl.setNetoNomina(new BigDecimal(ccDto.getAssNetoNomina().doubleValue()));
				aux = empl;
//				System.out.print("\n recorrido colaboradores excel = "+ x++);
			}
		}
    	return listEmpleados;
    }
    
    
    public void ejecutar(CotizacionColaboradorDto ccDto) {
    	aasISR(ccDto);	
    	aasNetoAsimilados(ccDto);
    	aNetoTotal(ccDto);	
    	acfaPercepcionTotal(ccDto);	
    	acfaCargaSocial(ccDto);	
    	acfaComision(ccDto);	
    	acfaSubtotalFactura(ccDto);	
    	acfaIVA(ccDto);	
    	acfaTotalFactura(ccDto);	
    	gsssSD(ccDto);	
    	gsssSDI(ccDto);	
    	gsssSuedo(ccDto);	
    	gsssExentos(ccDto);
    	gsssISR(ccDto);
    	gsssSubsidio(ccDto);	
    	gsssCoImss(ccDto);	
    	gsssNetoNomina(ccDto);	
    	gsePlanPensionesPrivada(ccDto);	
    	gsNetoTotal(ccDto);	
    	gscfmPercepcionTotal(ccDto);	
    	gscfmCargaSocial(ccDto);	
    	gscfmComision(ccDto);	
    	gscfmSubtotalFactura(ccDto);	
    	gscfmIVA(ccDto);	
    	gscfmTotalFactura(ccDto);	
    	gscfpppPercepcionNomina(ccDto);	
    	gscfpppCargaSocial(ccDto);	
    	gscfpppCostoNomina(ccDto);	
    	gscfpppPPP(ccDto);	
    	gscfpppComision(ccDto);	
    	gscfpppSubtotalFactura(ccDto);	
    	gscfpppIVA(ccDto);	
    	gscfpppTotalFactura(ccDto);	
    	gscfpppCostoTotal(ccDto);
    }
    
    public ColaboradoresTempDto llenarColaboradoresTempDto(CotizacionColaboradorDto ccDto) {
    	ColaboradoresTempDto c = new ColaboradoresTempDto();
    	c.setAsimilados(ccDto.getAasAsimialdos());
    	c.setCoImss(ccDto.getAssCoImss());
    	c.setExentos(ccDto.getAssExentos());
    	c.setFechaAntiguedad(ccDto.getDgFechaDeAntIguedad());
    	c.setGravados(ccDto.getAssGravados());
    	c.setIsr(ccDto.getAssISR());
    	c.setNetoNomina(ccDto.getAssNetoNomina());
    	c.setOtros(ccDto.getaOtros());
    	c.setPrimaDeRiesgo(ccDto.getDgPrimaDeRiesgo());
    	c.setSalarioDiario(ccDto.getAssSD());
    	c.setSalarioDiarioIntegral(ccDto.getAssSDI());
    	c.setSubsidio(ccDto.getAssSubsidio());
    	c.setSueldo(ccDto.getAssSuedo());
		return c;
    }
    
    void cotTotDto(CotizacionColaboradorDto c,CotizacionDto cot){
    	cotTotDto.setTotaldDiferenciaNeto(cotTotDto.getTotaldDiferenciaNeto().add(c.getdDiferenciaNeto()!=null ?c.getdDiferenciaNeto(): new BigDecimal(0))); 
        cotTotDto.setTotaldDiferenciaCostoMixto(cotTotDto.getTotaldDiferenciaCostoMixto().add(c.getdDiferenciaCostoMixto()!=null ?c.getdDiferenciaCostoMixto(): new BigDecimal(0)));
        cotTotDto.setTotaldDiferenciaCostoSoloPPP(cotTotDto.getTotaldDiferenciaCostoSoloPPP().add(c.getdDiferenciaCostoSoloPPP()!=null ?c.getdDiferenciaCostoSoloPPP(): new BigDecimal(0)));
        cotTotDto.setTotalassSuedo(cotTotDto.getTotalassSuedo().add(c.getAssSuedo()!=null ?c.getAssSuedo(): new BigDecimal(0)));
        cotTotDto.setTotalassGravados(cotTotDto.getTotalassGravados().add(c.getAssGravados()!=null ?c.getAssGravados(): new BigDecimal(0)));
        cotTotDto.setTotalassExentos(cotTotDto.getTotalassExentos().add(c.getAssExentos()!=null ?c.getAssExentos(): new BigDecimal(0)));
        cotTotDto.setTotalassSubsidio(cotTotDto.getTotalassSubsidio().add(c.getAssSubsidio()!=null ?c.getAssSubsidio(): new BigDecimal(0)));
        cotTotDto.setTotalassISR(cotTotDto.getTotalassISR().add(c.getAssISR()!=null ?c.getAssISR(): new BigDecimal(0)));
        cotTotDto.setTotalassCoImss(cotTotDto.getTotalassCoImss().add(c.getAssCoImss()!=null ?c.getAssCoImss(): new BigDecimal(0)));
        cotTotDto.setTotalassNetoNomina(cotTotDto.getTotalassNetoNomina().add(c.getAssNetoNomina()!=null ?c.getAssNetoNomina(): new BigDecimal(0)));
        cotTotDto.setTotalaasAsimialdos(cotTotDto.getTotalaasAsimialdos().add(c.getAasAsimialdos()!=null ?c.getAasAsimialdos(): new BigDecimal(0)));
        cotTotDto.setTotalaasISR(cotTotDto.getTotalaasISR().add(c.getAasISR()!=null ?c.getAasISR(): new BigDecimal(0)));
        cotTotDto.setTotalaasNetoAsimilados(cotTotDto.getTotalaasNetoAsimilados().add(c.getAasNetoAsimilados()!=null ?c.getAasNetoAsimilados(): new BigDecimal(0)));
        cotTotDto.setTotalaOtros(cotTotDto.getTotalaOtros().add(c.getaOtros()!=null ?c.getaOtros(): new BigDecimal(0)));
        cotTotDto.setTotalaNetoTotal(cotTotDto.getTotalaNetoTotal().add(c.getaNetoTotal()!=null ?c.getaNetoTotal(): new BigDecimal(0)));
        cotTotDto.setTotalacfaPercepcionTotal(cotTotDto.getTotalacfaPercepcionTotal().add(c.getAcfaPercepcionTotal()!=null ?c.getAcfaPercepcionTotal(): new BigDecimal(0)));
        cotTotDto.setTotalacfaCargaSocial(cotTotDto.getTotalacfaCargaSocial().add(c.getAcfaCargaSocial()!=null ?c.getAcfaCargaSocial(): new BigDecimal(0)));
        cotTotDto.setTotalacfaComision(cotTotDto.getTotalacfaComision().add(c.getAcfaComision()!=null ?c.getAcfaComision(): new BigDecimal(0)));
        cotTotDto.setTotalacfaSubtotalFactura(cotTotDto.getTotalacfaSubtotalFactura().add(c.getAcfaSubtotalFactura()!=null ?c.getAcfaSubtotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalacfaIVA(cotTotDto.getTotalacfaIVA().add(c.getAcfaIVA()!=null ?c.getAcfaIVA(): new BigDecimal(0)));
        cotTotDto.setTotalacfaTotalFactura(cotTotDto.getTotalacfaTotalFactura().add(c.getAcfaTotalFactura()!=null ?c.getAcfaTotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalgsssSuedo(cotTotDto.getTotalgsssSuedo().add(c.getGsssSuedo()!=null ?c.getGsssSuedo(): new BigDecimal(0)));
        cotTotDto.setTotalgsssExentos(cotTotDto.getTotalgsssExentos().add(c.getGsssExentos()!=null ?c.getGsssExentos(): new BigDecimal(0)));
        cotTotDto.setTotalgsssSubsidio(cotTotDto.getTotalgsssSubsidio().add(c.getGsssSubsidio()!=null ?c.getGsssSubsidio(): new BigDecimal(0)));
        cotTotDto.setTotalgsssISR(cotTotDto.getTotalgsssISR().add(c.getGsssISR()!=null ?c.getGsssISR(): new BigDecimal(0)));
        cotTotDto.setTotalgsssCoImss(cotTotDto.getTotalgsssCoImss().add(c.getGsssCoImss()!=null ?c.getGsssCoImss(): new BigDecimal(0)));
        cotTotDto.setTotalgsssNetoNomina(cotTotDto.getTotalgsssNetoNomina().add(c.getGsssNetoNomina()!=null ?c.getGsssNetoNomina(): new BigDecimal(0)));
        cotTotDto.setTotalgsePlanPensionesPrivada(cotTotDto.getTotalgsePlanPensionesPrivada().add(c.getGsePlanPensionesPrivada()!=null ?c.getGsePlanPensionesPrivada(): new BigDecimal(0)));
        cotTotDto.setTotalgsNetoTotal(cotTotDto.getTotalgsNetoTotal().add(c.getGsNetoTotal()!=null ?c.getGsNetoTotal(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmPercepcionTotal(cotTotDto.getTotalgscfmPercepcionTotal().add(c.getGscfmPercepcionTotal()!=null ?c.getGscfmPercepcionTotal(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmCargaSocial(cotTotDto.getTotalgscfmCargaSocial().add(c.getGscfmCargaSocial()!=null ?c.getGscfmCargaSocial(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmComision(cotTotDto.getTotalgscfmComision().add(c.getGscfmComision()!=null ?c.getGscfmComision(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmSubtotalFactura(cotTotDto.getTotalgscfmSubtotalFactura().add(c.getGscfmSubtotalFactura()!=null ?c.getGscfmSubtotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmIVA(cotTotDto.getTotalgscfmIVA().add(c.getGscfmIVA()!=null ?c.getGscfmIVA(): new BigDecimal(0)));
        cotTotDto.setTotalgscfmTotalFactura(cotTotDto.getTotalgscfmTotalFactura().add(c.getGscfmTotalFactura()!=null ?c.getGscfmTotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppPercepcionNomina(cotTotDto.getTotalgscfpppPercepcionNomina().add(c.getGscfpppPercepcionNomina()!=null ?c.getGscfpppPercepcionNomina(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppCargaSocial(cotTotDto.getTotalgscfpppCargaSocial().add(c.getGscfpppCargaSocial()!=null ?c.getGscfpppCargaSocial(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppCostoNomina(cotTotDto.getTotalgscfpppCostoNomina().add(c.getGscfpppCostoNomina()!=null ?c.getGscfpppCostoNomina(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppComision(cotTotDto.getTotalgscfpppComision().add(c.getGscfpppComision()!=null ?c.getGscfpppComision(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppSubtotalFactura(cotTotDto.getTotalgscfpppSubtotalFactura().add(c.getGscfpppSubtotalFactura()!=null ?c.getGscfpppSubtotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppIVA(cotTotDto.getTotalgscfpppIVA().add(c.getGscfpppIVA()!=null ?c.getGscfpppIVA(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppTotalFactura(cotTotDto.getTotalgscfpppTotalFactura().add(c.getGscfpppTotalFactura()!=null ?c.getGscfpppTotalFactura(): new BigDecimal(0)));
        cotTotDto.setTotalgscfpppCostoTotal(cotTotDto.getTotalgscfpppCostoTotal().add(c.getGscfpppCostoTotal()!=null ?c.getGscfpppCostoTotal(): new BigDecimal(0)));
        cotTotDto.setIdUsuarioAlta(1L);
        cotTotDto.setFechaAlta(new Date());
        cotTotDto.setIndEstatus(1L);
        cotTotDto.setIdCotizacion(cot);
    }
    
    public static BigDecimal redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado=(resultado-parteEntera)*Math.pow(10, numeroDecimales);
        resultado=Math.round(resultado);
        resultado=(resultado/Math.pow(10, numeroDecimales))+parteEntera;
        return new BigDecimal(String.valueOf(resultado));
    }
    
    public static BigDecimal disminuyeDecimal(BigDecimal valorInicial, BigDecimal valorAumentoDecimal) {
    	NumberFormat nf = NumberFormat.getInstance();
    	BigDecimal resultado = valorInicial.add(valorAumentoDecimal);
    	String aux = resultado.toString();
    	resultado = new BigDecimal(aux);
    	int x = valorInicial.compareTo(resultado);
    	if(x==0) {
    		try {
    			throw new Exception();
    		}catch(Exception ex) {
    			int y = 100/0;
    		}
    	}
        return resultado;
    }
}
