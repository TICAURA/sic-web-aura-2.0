package mx.com.consolida.crm.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.RolUsuarioENUM;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.converter.TechnicalException;
import mx.com.consolida.crm.dao.interfaz.BeneficioAdicionalDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDao;
import mx.com.consolida.crm.dao.interfaz.ColaboradorDao;
import mx.com.consolida.crm.dao.interfaz.ColaboradorDocumentosDao;
import mx.com.consolida.crm.dao.interfaz.ColaboradorNominaDao;
import mx.com.consolida.crm.dao.interfaz.NominaClienteDao;
import mx.com.consolida.crm.dao.interfaz.NominaPeriodicidadDao;
import mx.com.consolida.crm.dao.interfaz.NominaPeriodicidadFechasDao;
import mx.com.consolida.crm.dto.BeneficioAdicionalColaboradorDto;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.crm.dto.ColaboradorNominaDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.dto.FormulaFacturaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadFechasDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.NominaClienteBO;
import mx.com.consolida.entity.crm.BeneficioAdicionalColaborador;
import mx.com.consolida.entity.crm.Colaborador;
import mx.com.consolida.entity.crm.ColaboradorDocumento;
import mx.com.consolida.entity.crm.ColaboradorNomina;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.entity.crm.NominaPeriodicidad;
import mx.com.consolida.entity.crm.NominaPeriodicidadFechas;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dto.ValidaCreacionNominaDto;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class NominaClienteBOImpl implements NominaClienteBO{
	
	@Autowired
	private NominaClienteDao nominaClienteDao;
	
	@Autowired
	private ColaboradorDao colaboradorDao;
	
	@Autowired
	private ColaboradorDocumentosDao colaboradorDocumentosDao;
	
	@Autowired
	private ColaboradorNominaDao colaboradorNominaDao;
	
	@Autowired
	private NominaPeriodicidadDao nominaPeriodicidadDao;
	@Autowired
	private NominaPeriodicidadFechasDao nominaPeriodicidadFechasDao;
	
	@Autowired
	private CelulaBO celulaBO;
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private CatalogoBO catalogoBO;
	
	@Autowired
	private BeneficioAdicionalDao beneficioAdicionalDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaClienteBOImpl.class);

	@Override
	@Transactional(readOnly = true)
	public List<NominaClienteDto> listaNominaCliente(Long idCliente) {
		try {
			List<NominaClienteDto> lista  = nominaClienteDao.listaNominaCliente(idCliente);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaClienteDto> listaNominaCliente(Long idCliente , Long idNominista) {
		try {
			List<NominaClienteDto> lista  = nominaClienteDao.listaNominaCliente(idCliente, idNominista);
			
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaCliente ", e);
			return Collections.emptyList();
		}
	}
	
	public NominaClienteDto nominaClientebyId(Long idNominaCliente) {
		return nominaClienteDao.nominaClientebyId(idNominaCliente);
	}
	@Transactional
	public ColaboradorDto guardarColaborador(ColaboradorDto dto, UsuarioAplicativo us) throws TechnicalException {
		ColaboradorDto resultado= new ColaboradorDto();
			resultado = colaboradorDao.guardarColaborador(dto,us);
			ColaboradorNomina cn = new ColaboradorNomina();
			cn.setCorreoColaborador(resultado.getCorreoElectronico());
			cn.setIdColaborador(new Colaborador(resultado.getIdColaborador()));
			cn.setIdNomina(new NominaCliente(dto.getIdNomina()));
			if(resultado.getEsClabe1Principal()==1) {
				cn.setNumeroCuenta(resultado.getCuentaClabe1());
			}else {
				cn.setNumeroCuenta(resultado.getCuentaClabe2());
			}
			cn.setIndEstatus(1L);
			Long id = colaboradorNominaDao.guardarColaboradorNomina(cn, us);
			
			for(BeneficioAdicionalColaboradorDto ben : dto.getListBeneficioAdicColab()) {
				BeneficioAdicionalColaborador enty = new  BeneficioAdicionalColaborador();
				enty.setIdColaborador(cn.getIdColaborador().getIdColaborador());
				enty.setIdBeneficioAdicional(ben.getGeneral().getIdCatGeneral());
				enty.setIndEstatus(1L);
				beneficioAdicionalDao.guardarBeneficioAdicional(enty);
			}
		return resultado;
	}
	
	@Transactional
	public ColaboradorDto editarColaborador(ColaboradorDto dto, UsuarioAplicativo us) {
		ColaboradorDto resultado= new ColaboradorDto();
		try {
			resultado = colaboradorDao.editarColaborador(dto,us);
			ColaboradorNominaDto dtoCn = colaboradorNominaDao.obtenerColaboradorNomina(dto.getIdNomina(), dto.getIdColaborador());
			ColaboradorNomina cn = new ColaboradorNomina();
			cn.setIdColaboradorNomina(dtoCn.getIdColaboradorNomina());
			cn.setCorreoColaborador(resultado.getCorreoElectronico());
			cn.setIdColaborador(new Colaborador(resultado.getIdColaborador()));
			cn.setIdNomina(new NominaCliente(dto.getIdNomina()));
			if(resultado.getEsClabe1Principal()==1) {
				cn.setNumeroCuenta(resultado.getCuentaClabe1());
			}else {
				cn.setNumeroCuenta(resultado.getCuentaClabe2());
			}
			cn.setIndEstatus(1L);
			colaboradorNominaDao.editarColaboradorNomina(cn, us);
			
			beneficioAdicionalDao.borrarBeneficioAdicionalByColab(dto.getIdColaborador());
			for(BeneficioAdicionalColaboradorDto ben : dto.getListBeneficioAdicColab()) {
				BeneficioAdicionalColaborador enty = new  BeneficioAdicionalColaborador();
				enty.setIdColaborador(dto.getIdColaborador());
				enty.setIdBeneficioAdicional(ben.getGeneral().getIdCatGeneral());
				enty.setIndEstatus(1L);
				beneficioAdicionalDao.guardarBeneficioAdicional(enty);
			}
			
		} catch(Exception ex) {
			LOGGER.error(ex.getMessage());
		}
		return resultado;
	}
	
	public ColaboradorDto obtenerColaboradorById(Long idColaborador) {
		ColaboradorDto dto = colaboradorDao.obtenerColaboradorById(idColaborador);
		dto.setDocumentos(colaboradorDocumentosDao.obtenerDocumentos(idColaborador));
		if(dto.getCodigoPostal() != null) {
			DomicilioComunDto domicilio= clienteDao.obtenerEntidadFederativaXCP(dto.getCodigoPostal());
			dto.setCatMunicipios(domicilio.getDomicilio().getCatMunicipios());
			dto.setMunicipios(catalogoBO.obtenerCatMunicipioByEntidadFedByCveMun(domicilio.getDomicilio().getIdEntidadFederativa().toString(), 
					domicilio.getDomicilio().getCatMunicipios().getIdCatGeneral().toString()));
		}
		dto.setListBeneficioAdicColab(beneficioAdicionalDao.obtenerBeneficioAdicionalByColab(idColaborador));
		return dto;
	}
	public List<ColaboradorDto> obtenercolaboradoresByidNomina(Long idNomina){
		return colaboradorDao.obtenercolaboradoresByidNomina(idNomina);
	}
	
	@Transactional
	public void calcularPeriodo(NominaPeriodicidadDto dto, UsuarioAplicativo us) {
			if(dto.getIdNominaPeriodicidad() == null) {
				NominaPeriodicidad entity = new NominaPeriodicidad(dto);
				entity.setIdNomina(dto.getIdNomina());
				entity.setFechaAlta(new Date());
				entity.setUsuarioAlta(us.getIdUsuario());
				
				entity = nominaPeriodicidadDao.guardarNominaPeriodicidad(entity,us);
				dto.setIdNominaPeriodicidad(entity.getIdNominaPeriodicidad());
			}else {
				NominaPeriodicidad entity = new NominaPeriodicidad(dto);
				entity.setFechaModificacion(new Date());
				entity.setUsuarioModificacion(us.getIdUsuario());
				
				entity = nominaPeriodicidadDao.editarNominaPeriodicidad(entity,us);
				dto.setIdNominaPeriodicidad(entity.getIdNominaPeriodicidad());
			}
		
		
		nominaPeriodicidadFechasDao.eliminarNominaPeriodicidad(dto.getIdNominaPeriodicidad());
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(dto.getFechaInicio());
		Calendar fechaFin = Calendar.getInstance();
		fechaFin.setTime(dto.getFechaInicio());
		Calendar fechaFinPeriodo = Calendar.getInstance();
		fechaFinPeriodo.setTime(dto.getFechaInicio());
		Calendar fechaInicioPeriodo = Calendar.getInstance();
		fechaInicioPeriodo.setTime(dto.getFechaInicio());
		fechaFin.add(Calendar.YEAR, 3);
		if(dto.getIdCatPeriodicidad()==1L) {//semanal
			for (int i = 0; i < 200; i++) {
				if(fechaInicio.compareTo(fechaFin)==1) {
					return;
				}else {
					fechaFinPeriodo.add(Calendar.DAY_OF_YEAR, 7);
					NominaPeriodicidadFechas ef = new NominaPeriodicidadFechas();
					ef.setFechaAlta(new Date());
					ef.setFechaFinPeriodo(fechaFinPeriodo.getTime());
					Calendar fechaAux = Calendar.getInstance();
					fechaAux.setTime(fechaInicioPeriodo.getTime());
					fechaAux.add(Calendar.DAY_OF_MONTH, 1);
					ef.setFechaInicioPeriodo(fechaAux.getTime());
					ef.setFechaTentativaPago(fechaFinPeriodo.getTime());
					ef.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef.setIndEstatus("1");
					ef.setUsuarioAlta(us.getIdUsuario());
					
					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef, us);
					fechaInicioPeriodo.add(Calendar.DAY_OF_YEAR, 7);
				}
			}
		}else if(dto.getIdCatPeriodicidad()==2L) {//catorcenal
			for (int i = 0; i < 200; i++) {
				if(fechaInicio.compareTo(fechaFin)==1) {
					return;
				}else {
					fechaFinPeriodo.add(Calendar.DAY_OF_YEAR, 14);
					NominaPeriodicidadFechas ef = new NominaPeriodicidadFechas();
					ef.setFechaAlta(new Date());
					ef.setFechaFinPeriodo(fechaFinPeriodo.getTime());
					Calendar fechaAux = Calendar.getInstance();
					fechaAux.setTime(fechaInicioPeriodo.getTime());
					fechaAux.add(Calendar.DAY_OF_MONTH, 1);
					ef.setFechaInicioPeriodo(fechaAux.getTime());
					ef.setFechaTentativaPago(fechaFinPeriodo.getTime());
					ef.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef.setIndEstatus("1");
					ef.setUsuarioAlta(us.getIdUsuario());

					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef, us);
					fechaInicioPeriodo.add(Calendar.DAY_OF_YEAR, 14);
				}
			}
		}else if (dto.getIdCatPeriodicidad()==3L) {//quincenal 
			Integer intDiaPrimerPeriodo = fechaSegundoPeriodo(fechaInicioPeriodo);
			Integer intDiaFinSegundoPeriodo = fechaPrimerPeriodo(intDiaPrimerPeriodo);
			fechaInicioPeriodo.set(Calendar.DAY_OF_MONTH, intDiaPrimerPeriodo);
			fechaFinPeriodo.set(Calendar.DAY_OF_MONTH, intDiaFinSegundoPeriodo);
			
			for (int i = 0; i < 45; i++) {
				if(fechaInicio.compareTo(fechaFin)==1) {
					return;
				}else {
					/*********** 1er Peiodo Quincenal******************/
					NominaPeriodicidadFechas ef = new NominaPeriodicidadFechas();
					ef.setFechaAlta(new Date());
					ef.setFechaFinPeriodo(fechaFinPeriodo.getTime());
					ef.setFechaInicioPeriodo(fechaInicioPeriodo.getTime());
					int diaSemana = fechaFinPeriodo.get(Calendar.DAY_OF_WEEK);
					
					if(diaSemana == Calendar.SUNDAY) {//Domingo
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -2);
						ef.setFechaTentativaPago(aux.getTime());
					} else if(diaSemana == Calendar.SATURDAY) {//Sabado
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -1);
						ef.setFechaTentativaPago(aux.getTime());
					} else {
						ef.setFechaTentativaPago(ef.getFechaFinPeriodo());
					}
					ef.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef.setIndEstatus("1");
					ef.setUsuarioAlta(us.getIdUsuario());
					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef, us);
					/*********************** 2do periodo quincenal ********************************/
					NominaPeriodicidadFechas ef1 = new NominaPeriodicidadFechas();
					ef1.setFechaAlta(new Date());
					Calendar auxFechaFin1 = Calendar.getInstance();
					auxFechaFin1.setTime(fechaInicioPeriodo.getTime());
					if(auxFechaFin1.get(Calendar.DAY_OF_MONTH)==1) {
						auxFechaFin1.add(Calendar.MONTH, 1);
					}
					auxFechaFin1.add(Calendar.DAY_OF_YEAR, -1);
					ef1.setFechaFinPeriodo(auxFechaFin1.getTime());
					Calendar fechaAux = Calendar.getInstance();
					fechaAux.setTime(fechaFinPeriodo.getTime());
					fechaAux.add(Calendar.DAY_OF_MONTH, 1);
					ef1.setFechaInicioPeriodo(fechaAux.getTime());
					int diaSemana1 = fechaFinPeriodo.get(Calendar.DAY_OF_WEEK);
					if(diaSemana1 == Calendar.SUNDAY) {//Domingo
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -2);
						ef1.setFechaTentativaPago(aux.getTime());
					} else if(diaSemana1 == Calendar.SATURDAY) {//Sabado
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -1);
						ef1.setFechaTentativaPago(aux.getTime());
					}else {
						ef1.setFechaTentativaPago(ef1.getFechaFinPeriodo());
					}
					ef1.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef1.setIndEstatus("1");
					ef1.setUsuarioAlta(us.getIdUsuario());
					
					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef1, us);
					fechaInicioPeriodo.add(Calendar.MONTH, 1);
					fechaFinPeriodo.add(Calendar.MONTH, 1); 
				}
			}
		}else if (dto.getIdCatPeriodicidad()==4L) {//mensual
			for (int i = 0; i < 200; i++) {
				int x = fechaInicioPeriodo.getTime().compareTo(fechaFin.getTime());
				if(x==1) {
					return;
				}else {
					fechaFinPeriodo.add(Calendar.MONTH, 1);
					NominaPeriodicidadFechas ef = new NominaPeriodicidadFechas();
					ef.setFechaAlta(new Date());
					ef.setFechaFinPeriodo(fechaFinPeriodo.getTime());
					Calendar fechaAux = Calendar.getInstance();
					fechaAux.setTime(fechaInicioPeriodo.getTime());
					fechaAux.add(Calendar.DAY_OF_MONTH, 1);
					ef.setFechaInicioPeriodo(fechaAux.getTime());
					int diaSemana = fechaFinPeriodo.get(Calendar.DAY_OF_WEEK);
					if(diaSemana == Calendar.SUNDAY) {//Domingo
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -2);
						ef.setFechaTentativaPago(aux.getTime());
					} else if(diaSemana == Calendar.SATURDAY) {//Sabado
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -1);
						ef.setFechaTentativaPago(aux.getTime());
					}else {
						ef.setFechaTentativaPago(ef.getFechaFinPeriodo());
					}
					ef.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef.setIndEstatus("1");
					ef.setUsuarioAlta(us.getIdUsuario());
					
					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef, us);
					fechaInicioPeriodo.add(Calendar.MONTH, 1);
				}
			}
		}else {//Anual
			for (int i = 0; i < 200; i++) {
				if(fechaInicioPeriodo.compareTo(fechaFin)==1) {
					return;
				}else {
					fechaFinPeriodo.add(Calendar.YEAR, 1);
					NominaPeriodicidadFechas ef = new NominaPeriodicidadFechas();
					ef.setFechaAlta(new Date());
					ef.setFechaFinPeriodo(fechaFinPeriodo.getTime());
					Calendar fechaAux = Calendar.getInstance();
					fechaAux.setTime(fechaInicioPeriodo.getTime());
					fechaAux.add(Calendar.DAY_OF_MONTH, 1);
					ef.setFechaInicioPeriodo(fechaAux.getTime());
					
					int diaSemana = fechaFinPeriodo.get(Calendar.DAY_OF_WEEK);
					if(diaSemana == Calendar.SUNDAY) {//Domingo
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -2);
						ef.setFechaTentativaPago(aux.getTime());
					} else if(diaSemana == Calendar.SATURDAY) {//Sabado
						Calendar aux = Calendar.getInstance();
						aux.setTime(ef.getFechaFinPeriodo());
						aux.add(Calendar.DAY_OF_WEEK, -1);
						ef.setFechaTentativaPago(aux.getTime());
					}else {
						ef.setFechaTentativaPago(ef.getFechaFinPeriodo());
					}
					ef.setIdNominaPeriodicidad(dto.getIdNominaPeriodicidad());
					ef.setIndEstatus("1");
					ef.setUsuarioAlta(us.getIdUsuario());
					
					nominaPeriodicidadFechasDao.guardarNominaPeriodicidad(ef, us);
					fechaInicioPeriodo.add(Calendar.YEAR, 1);
				}
			}
		}
		
	}
	public Integer fechaSegundoPeriodo(Calendar c) {
		int dia = c.get(Calendar.DAY_OF_MONTH);
		Integer aux = dia;
		if(dia == 15) {
			aux = 1;
		} else if(dia == 16) {
			aux = 2;
		} else if(dia == 17) {
			aux = 3;
		} else if(dia == 18) {
			aux = 4;
		} else if(dia == 19) {
			aux = 5;
		} else if(dia == 20) {
			aux = 6;
		} else if(dia == 21) {
			aux = 7;
		} else if(dia == 22) {
			aux = 8;
		} else if(dia == 23) {
			aux = 9;
		} else if(dia == 24) {
			aux = 10;
		} else if(dia == 25) {
			aux = 11;
		} else if(dia == 26) {
			aux = 12;
		} else if(dia == 27) {
			aux = 13;
		} else if(dia == 28) {
			aux = 14;
		} else if(dia == 29) {
			aux = 1;
		} else if(dia == 30) {
			aux = 1;
		} else if(dia == 31) {
			aux = 1;
		} else if(dia == 32) {
			aux = 1;
		} 
		return aux;
	}
	public Integer  fechaPrimerPeriodo (int dia) {
		Integer aux = null;
		if(dia == 1) {
			aux = 15;
		} else if(dia == 2) {
			aux = 16;
		} else if(dia == 3) {
			aux = 17;
		} else if(dia == 4) {
			aux = 18;
		} else if(dia == 5) {
			aux = 19;
		} else if(dia == 6) {
			aux = 20;
		} else if(dia == 7) {
			aux = 21;
		} else if(dia == 8) {
			aux = 22;
		} else if(dia == 9) {
			aux = 23;
		} else if(dia == 10) {
			aux = 24;
		} else if(dia == 11) {
			aux = 25;
		} else if(dia == 12) {
			aux = 26;
		} else if(dia == 13) {
			aux = 27;
		} else if(dia == 14) {
			aux = 28;
		} 
		return aux;
	}
	
	@Transactional(readOnly = false)
	public NominaPeriodicidadDto obtenerPeriodo(Long idNomina) {
		return nominaPeriodicidadDao.obtenerNominaPeriodicidad(idNomina);
	}
	
	@Transactional(readOnly = false)
	public List<NominaPeriodicidadFechasDto> obtenerPeriodosFechas(Long idNomina) {
		return nominaPeriodicidadFechasDao.obtenerPeriodosFechas(idNomina);
	}
	
	@Transactional
	public void guardarDocumentoColaborador(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException{
		Map<String,String> contextos = new HashMap<String,String>();
		contextos.put("1","colaborador");
		contextos.put("2",String.valueOf(documento.getIdColaborador()));
		contextos.put("3","datosGenerales");
		contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
		
		Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
		
		ColaboradorDocumento doc = new ColaboradorDocumento();
		doc.setFechaAlta(new Date());
		doc.setIdColaborador(documento.getIdColaborador());
		doc.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		doc.setIdCms(idCMS);
		doc.setIndEstatus(1L);
		doc.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
		doc.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
		colaboradorDocumentosDao.create(doc);
	}
	
	@Transactional
	public void eliminarDocumentoColaborador(Long idColaboradorDocumento) {
		colaboradorDocumentosDao.eliminarDocumento(idColaboradorDocumento);
	}
	
	
	@Transactional(readOnly = true)
	public FormulaFacturaDto formulaFactura( Long idNominaCliente) {
		return nominaClienteDao.formulaFactura(idNominaCliente); 
	}


	@Transactional(readOnly = true)
	public ValidaCreacionNominaDto validaSecciones(Long idCliente, Long idNominaCliente) {
		return nominaClienteDao.validaSecciones(idCliente, idNominaCliente);
	}
}
