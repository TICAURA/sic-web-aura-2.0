package mx.com.consolida.ppp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.crm.dao.interfaz.ClienteDao;
import mx.com.consolida.crm.service.impl.DocumentoServiceBO;
import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.entity.ppp.CatEstatusColaborador;
import mx.com.consolida.entity.ppp.CatEstatusNomina;
import mx.com.consolida.entity.ppp.PppColaborador;
import mx.com.consolida.entity.ppp.PppColaboradorEstatus;
import mx.com.consolida.entity.ppp.PppColaboradorStp;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.entity.ppp.PppNominaComplementaria;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.entity.ppp.PppNominaFactura;
import mx.com.consolida.entity.ppp.PppNominaFacturaDocumento;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatEstatusColaboradorEnum;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dao.interfaz.CatEstatusColaboradorDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorEstatusDao;
import mx.com.consolida.ppp.dao.interfaz.PppColaboradorStpDao;
import mx.com.consolida.ppp.dao.interfaz.PppFacturaDocumentoDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaComplementariaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaDocumentoDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaEstatusDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaFacturaDocumentoDao;
import mx.com.consolida.ppp.dto.HistoricoNominaDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.dto.NominaEstatusDto;
import mx.com.consolida.ppp.dto.NominasDTO;
import mx.com.consolida.ppp.service.interfaz.ColaboradorPPPBO;
import mx.com.consolida.ppp.service.interfaz.NominaBO;
import mx.com.consolida.ppp.service.interfaz.NominaComplementoBO;
import mx.com.consolida.ppp.service.interfaz.NominaFacturaBO;
import mx.com.facturacion.factura.SeguimientoNominaDto;
import mx.com.consolida.converter.Utilerias;

@Service
public class NominaBOImpl implements NominaBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaBOImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PppNominaDao nomimnaDao;
	
	@Autowired
	private PppNominaEstatusDao nominaEstatusDao;
	
	@Autowired
	private PppColaboradorDao pppColaboradorDao;
	
	@Autowired
	private PppNominaDocumentoDao pppNominaDocumentoDao;
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;
	
	@Autowired
	private PppNominaEstatusDao pppNominaEstatusDao;

	@Autowired
	private PppColaboradorEstatusDao pppColaboradorEstatusDao;
	
	@Autowired
	private	CatEstatusColaboradorDao catEstatusColaboradorDao;
	
	@Autowired
	private PppNominaFacturaDocumentoDao pppNominaFacturaDocumentoDao;
	
	@Autowired
	private PppNominaFacturaDao pppNominaFacturaDao;
	
	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private PppColaboradorStpDao pppColaboradorStpDao;
	
	@Autowired
	private PppNominaComplementariaDao pppNominaComplementariaDao;
	
	@Autowired
	private NominaComplementoBO pppNominaDatosComple;
	
	@Autowired
	private ColaboradorPPPBO colaboradorPppBo;
	
	@Autowired
	private PppFacturaDocumentoDao pppFacturaDocumentoDao;


	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaClientesNomina(Long idRol, Long idUsuarioAplicativo) {
		try {
			List<NominaDto> lista = nomimnaDao.listaClientesNomina(idRol, idUsuarioAplicativo);
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaClientesNominaByCelula(Long idCelula) {
		try {
			List<NominaDto> lista = nomimnaDao.listaClientesNominaByCelula(idCelula);
	
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaClientesNomina ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardarNomina(NominaDto nominaDto, Long idUsuarioAplicativo) {
		try {
			
			
			if(nominaDto!=null) {
				
				PppNomina nomina = new PppNomina();
				PppNominaEstatus nominaEstatus = new PppNominaEstatus();
				if (nominaDto.getIdNomina() != null) {

					nomina = nomimnaDao.read(nominaDto.getIdNomina());
					nomina.setNominaCliente(new NominaCliente(nominaDto.getNominaClienteDto().getIdNominaCliente()));
					nomina.setFechaInicioNomina(nominaDto.getFechaInicioNomina());
					nomina.setFechaFinNomina(nominaDto.getFechaFinNomina());
					nomina.setClave(nominaDto.getClaveNomina());
					nomina.setFechaModificacion(new Date());
					nomina.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
					nomina.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
					nomimnaDao.update(nomina);


				} else {

					nomina.setNominaCliente(new NominaCliente(nominaDto.getNominaClienteDto().getIdNominaCliente()));
					nomina.setFechaInicioNomina(nominaDto.getFechaInicioNomina());
					nomina.setFechaFinNomina(nominaDto.getFechaFinNomina());
					if(nominaDto.getNominaClienteDto().getCatProductoNomina().getIdCatGeneral()==9958) {
					nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "IRLAB-"));//IRLAB
					
				}else if (nominaDto.getNominaClienteDto().getCatProductoNomina().getIdCatGeneral()==9950) {
						nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "MXT-")); 
				}else {
					nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "PPP-"));  //PPP
				}
					nomina.setConsecutivo(nomimnaDao.maxConsecutivoByIdNomina(nominaDto.getNominaClienteDto().getIdNominaCliente())!=null ? nomimnaDao.maxConsecutivoByIdNomina(nominaDto.getNominaClienteDto().getIdNominaCliente()) + 1 : 1);
					nomina.setFechaAlta(new Date());
					nomina.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					nomina.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
					//La periodicidad es obligatoria
					nomina.setIdPeriodicidad(nominaDto.getNominaClienteDto().getPeriodicidadNomina().getIdCatGeneral());
					Long idNomina = nomimnaDao.create(nomina);
					nominaEstatus.setCatEstatusNomina(new CatEstatusNomina(new Long(NominaEstatusEnum.BORRADOR.getId())));				
					nominaEstatus.setPppNomina(new PppNomina(idNomina));
					nominaEstatus.setFechaAlta(new Date());
					nominaEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					nominaEstatus.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
					nominaEstatusDao.create(nominaEstatus);				
				}
		
				return Boolean.TRUE;
			}else {
				LOGGER.error("Ocurrio un error en guardarNomina, objeto nominaDto viene null");
				
				return Boolean.FALSE;
			}
			

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarNomina ", e);
			return Boolean.FALSE;
		}
	}
	
	/*Guarda Nomina Complementaria*/
	@Override
	@Transactional
	public Long guardarNominaComplementaria(NominaDto nominaDto, Long idUsuarioAplicativo) {
		long a=0l;
		
		try {	
		
              if(nominaDto!=null) {			
            		PppNomina nomina = new PppNomina();
				if (nominaDto.getIdNomina() != null) {

					nomina = nomimnaDao.read(nominaDto.getIdNomina());
					nomina.setNominaCliente(new NominaCliente(nominaDto.getNominaClienteDto().getIdNominaCliente()));
					nomina.setFechaInicioNomina(nominaDto.getFechaInicioNomina());
					nomina.setFechaFinNomina(nominaDto.getFechaFinNomina());
					nomina.setClave(nominaDto.getClaveNomina());
					nomina.setFechaModificacion(new Date());
					nomina.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
					nomina.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
					nomimnaDao.update(nomina);
   
                     a=nominaDto.getIdNomina();
				} else {
				PppNomina nominaPadre = new PppNomina();
				PppNominaEstatus nominaEstatus = new PppNominaEstatus();
				PppNominaComplementaria nominaComplementaria=new PppNominaComplementaria();
			
					nomina.setNominaCliente(new NominaCliente(nominaDto.getNominaClienteDto().getIdNominaCliente()));
					nomina.setFechaInicioNomina(nominaDto.getSinformatofechaInicioNomina());
					nomina.setFechaFinNomina(nominaDto.getSinformatofechaFinNomina());
					if(nominaDto.getNominaClienteDto().getCatProductoNomina().getIdCatGeneral()==9958) {
						nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "IRLAB-"));//IRLAB
					}else if(nominaDto.getNominaClienteDto().getCatProductoNomina().getIdCatGeneral()==9950) {
						nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "MXT-"));//MXP 
					}else	{
						nomina.setClave(generaClaveNomina(nominaDto.getNominaClienteDto().getIdNominaCliente(), "PPP-"));  //PPP
					}
					nomina.setConsecutivo(nomimnaDao.maxConsecutivoByIdNomina(nominaDto.getNominaClienteDto().getIdNominaCliente())!=null ? nomimnaDao.maxConsecutivoByIdNomina(nominaDto.getNominaClienteDto().getIdNominaCliente()) + 1 : 1);
					nomina.setFechaAlta(new Date());
					nomina.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					nomina.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
					//La periodicidad es obligatoria
					nomina.setIdPeriodicidad(nominaDto.getPeriodicidadNomina().getIdCatGeneral());
					Long idNomina = nomimnaDao.create(nomina);
					
					nominaEstatus.setCatEstatusNomina(new CatEstatusNomina(new Long(NominaEstatusEnum.COMPLEMENTO.getId())));
					nominaEstatus.setPppNomina(new PppNomina(idNomina));
					nominaEstatus.setFechaAlta(new Date());
					nominaEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					nominaEstatus.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
					nominaEstatusDao.create(nominaEstatus);
					
					//creacion de la nomina complementaria
					nominaPadre = nomimnaDao.read(nominaDto.getIdNominaPPPPadre());
					nominaComplementaria.setPppNominaPadre(new PppNomina(nominaPadre.getIdPppNomina()));
					nominaComplementaria.setPppNomina(new PppNomina(idNomina));
					nominaComplementaria.setFechaAlta(new Date());
					nominaComplementaria.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					nominaComplementaria.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
					pppNominaComplementariaDao.create(nominaComplementaria);
				
				a=nominaComplementaria.getPppNomina().getIdPppNomina();
				}

				return a;
				
				//return Boolean.TRUE;
			}else {
				LOGGER.error("Ocurrio un error en guardarNomina, objeto nominaDto viene null");
				
				//return Boolean.FALSE;
				return a;
			}
			

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarNomina ", e);
			//return Boolean.FALSE;
			return a;
		}
	}
	
	private String generaClaveNomina(Long idNominaClient, String inicial) throws BusinessException {
		try {
			
			
			String clave = "";
			
			
			if(nomimnaDao.maxConsecutivoByIdNomina(idNominaClient)!=null) {
				
				if(nomimnaDao.existeNomina(inicial+idNominaClient.toString()+"-")) {
					Integer maximo = nomimnaDao.maxConsecutivoByIdNomina(idNominaClient) + 1;
					clave = inicial + idNominaClient.toString() + "-" +String.format("%06d", maximo) ;
				}else {
					clave = inicial + idNominaClient.toString() + "-" +String.format("%06d", 1) ;
				}
				
			}else {
				int str = 1;
				clave = inicial + idNominaClient.toString() + "-" +String.format("%06d", str);
			}
			return clave;
		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en generaClaveNomina ", be);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en generaClaveNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominaEnProcesoByIdCliente(Long idCliente , Long idNominista ) {
		try {
			List<NominaDto> lista  = nomimnaDao.listaNominaEnProcesoByIdCliente(idCliente,  idNominista);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaEnProcesoByIdCliente ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominaAcomplementar(Long idCliente , Long idNominista ) {
		try {
			List<NominaDto> lista  = nomimnaDao.listaCierreNomina(idCliente,  idNominista);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaCierreNomina ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public NominaDto getDatosNominaByIdNomina(Long idNomina, Boolean complementaria) throws BusinessException {
		try {
			
			
			NominaDto nom=nomimnaDao.getDatosNominaByIdNomina(idNomina);
			NominaDto nominaPadre=null;
		if (nom.getCatEstatusNomina().getIdCatGeneral()==24 || complementaria || nom.getEsNominaComplementaria()) {
				nominaPadre= nomimnaDao.getNominaPadre(idNomina);
				nom.setEsNominaComplementaria(true);
				nom.setNominaComplementoDto(pppNominaDatosComple.getDatosNomComplByIdNomCompl(nominaPadre.getClaveNomina()));			
				nom.setMontoTotalPpp(getTotalFacturadoXNomina(nominaPadre.getIdNominaPPPPadre()));
				nom.setMontoAComplementarPpp(getTotalNoDispersadoXNomina(nominaPadre.getIdNominaPPPPadre()));
				nom.setListColaboradores(colaboradorPppBo.cargaInicialColaboradoresNominasPadre(nominaPadre.getIdNominaPPPPadre()));
			
			}
			return nom;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional
	public NominaDto getDatosNominaByIdNominaComplementaria(NominaDto nominaDTO, long idUsuario) throws BusinessException {
		try {
			NominaDto nominaComp=new NominaDto();
			Long idNominaProceso=null;
			/*Verificar si hay nominas en proceso*/
			idNominaProceso=this.getNominasComplementariasEnBorrador(nominaDTO.getIdNomina());
			if(idNominaProceso!=null) {
				nominaComp=this.getDatosNominaByIdNomina(idNominaProceso,true);
				nominaComp.setMontoTotalPpp(nominaDTO.getNominaClienteDto().getMontoTotalColaboradores());
				nominaComp.setMontoAComplementarPpp(nominaDTO.getNominaClienteDto().getMontoNoDispersado());
				nominaComp.setEsNominaComplementaria(true);
				nominaComp.setNominaComplementoDto(pppNominaDatosComple.getDatosNomComplByIdNomCompl(nominaDTO.getClaveNomina()));
				nominaComp.setIdNominaPPPPadre(nominaDTO.getIdNomina());
				nominaComp.setSinformatofechaInicioNomina(nominaDTO.getFechaInicioNomina());
				nominaComp.setSinformatofechaFinNomina(nominaDTO.getFechaFinNomina());
				
			}else {
			nominaComp.setFechaInicioNomina(nominaDTO.getFechaInicioNomina());
			nominaComp.setFechaFinNomina(nominaDTO.getFechaFinNomina());
			
			nominaComp.setMontoTotalPpp(nominaDTO.getNominaClienteDto().getMontoTotalColaboradores());
			nominaComp.setMontoAComplementarPpp(nominaDTO.getNominaClienteDto().getMontoNoDispersado());
			nominaComp.setEsNominaComplementaria(true);
			nominaComp.setNominaComplementoDto(pppNominaDatosComple.getDatosNomComplByIdNomCompl(nominaDTO.getClaveNomina()));
			
			nominaComp.setNominaClienteDto(nominaDTO.getNominaClienteDto());
			nominaComp.setPeriodicidadNomina(nominaDTO.getPeriodicidadNomina());
			nominaComp.setIdNominaPPPPadre(nominaDTO.getIdNomina());
			nominaComp.setSinformatofechaInicioNomina(nominaDTO.getFechaInicioNomina());
			nominaComp.setSinformatofechaFinNomina(nominaDTO.getFechaFinNomina());
			
			}
			return nominaComp;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNominaByIdNomina ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional
	public NominaDto guardaNominaByIdNominaComplementaria(NominaDto nominaDTO, long idUsuario) throws BusinessException {
		try {
		
			Long idComplementaria=0l;
			NominaDto nominaComp=new NominaDto();
				idComplementaria=this.guardarNominaComplementaria(nominaDTO, idUsuario);		
		
			if (idComplementaria!=0l) {
	    	nominaComp =nomimnaDao.getDatosNominaByIdNomina(idComplementaria);
			nominaComp.setFechaInicioNomina(nominaDTO.getFechaInicioNomina());
			nominaComp.setFechaInicioNomina(nominaDTO.getFechaFinNomina());
			nominaComp.setMontoTotalPpp(nominaDTO.getNominaClienteDto().getSubtotalFacturado());
			nominaComp.setMontoAComplementarPpp(nominaDTO.getNominaClienteDto().getMontoNoDispersado());
			nominaComp.setEsNominaComplementaria(true);
			nominaComp.setNominaComplementoDto(nominaDTO.getNominaComplementoDto());
		
			}
			return nominaComp;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaNominaByIdNominaComplementaria ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public NominaDto getIdNominaByComplementaria(Long idNomina) throws BusinessException {
		try {
		//se obtiene el id de la nomina complementaria
			return nomimnaDao.getNominaComplemenaria(idNomina);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getIdNominaByComplementaria ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> getNominasParaAutorizarFinanciamiento(int idCatEstatusNomina) {
		try {
			List<NominaDto> lista = nomimnaDao.getNominasParaAutorizarFinanciamiento(idCatEstatusNomina);
			return lista;

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasParaAutorizarFinanciamiento ", e);
			return Collections.emptyList();
		}
	}
	
	
	@Override
	@Transactional
	public Boolean cambioEstatusOtorgarFinanciamientoGenerico(Long idNominaPPP, String observacion , NominaEstatusEnum estatus , Long idUsuarioAplicativo) {
		
		try {

			//Consulta los estatus activos y los apaga  , inserta el nuevo estatus
			List<PppNominaEstatus> estatusActivos = nominaEstatusDao.getPppNominaEstatusActivo(idNominaPPP);
			
			for (PppNominaEstatus pppNominaEstatus : estatusActivos) {
				pppNominaEstatus.setIndEstatus(IndEstatusEnum.INACTIVO.getEstatus());
				nominaEstatusDao.update(pppNominaEstatus);
			}
			
			PppNominaEstatus nominaEstatus = new PppNominaEstatus();
			nominaEstatus.setCatEstatusNomina(new CatEstatusNomina(new Long(estatus.getId())));
			nominaEstatus.setPppNomina(new PppNomina(idNominaPPP));
			nominaEstatus.setFechaAlta(new Date());
			nominaEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
			nominaEstatus.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
			if(observacion!=null & !"".equals(observacion)) {
				nominaEstatus.setObservacion(observacion);
			}else {
				nominaEstatus.setObservacion(null);
			}

			nominaEstatusDao.create(nominaEstatus);
		
			
			
			
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en rechazarFinanciamiento ", e);
			return Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<HistoricoNominaDto> getHistoricoByIdPppNomina(Long idPppNomina) {
		try {
			
			return nominaEstatusDao.getHistoricoByIdPppNomina(idPppNomina);

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en getHistoricoByIdPppNomina ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominasFinanzasByCelula(Long idCelula, Long idCatEstatusNomina) {
		try {
			List<NominaDto> lista  = nomimnaDao.listaNominasFinanzasByCelula(idCelula, idCatEstatusNomina);
			lista=this.listaColor(lista);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominasFinanzasByCelula ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public void guardarCveOrdenPagoColaborador(List<EmpleadoDTO> colaboradores, Long idUsuario) {
		try {
			for(EmpleadoDTO colaborador: colaboradores) {
			
			PppColaborador pppColaborador = new PppColaborador();
			
			pppColaborador = pppColaboradorDao.read(colaborador.getIdPppColaborador());
			
			PppColaboradorStp pppColaboradorStp = new PppColaboradorStp();
			pppColaboradorStp.setFechaAlta(new Date());
			pppColaboradorStp.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			pppColaboradorStp.setUsuarioAlta(new Usuario());
			pppColaboradorStp.getUsuarioAlta().setIdUsuario(idUsuario);
			pppColaboradorStp.setPppColaborador(pppColaborador);
			pppColaboradorStp.setCveOrdenPago(colaborador.getCveOrdenPago());
			pppColaboradorStpDao.create(pppColaboradorStp);
			
			//no cambiar a los bloqueados
			if(!colaborador.getDescEstatus().equals("BLOQUEADO")) {
			if(colaborador.getCveOrdenPago() != null) {
				CatEstatusColaborador estatusColaboradorPago = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.ORDEN_PAGO_CREADA.getId());
				PppColaboradorEstatus colaboradorEstatusOrden = new PppColaboradorEstatus();
				colaboradorEstatusOrden.setPppColaborador(pppColaborador);
				colaboradorEstatusOrden.setFechaAlta(new Date());
				colaboradorEstatusOrden.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				colaboradorEstatusOrden.setUsuarioAlta(new Usuario());
				colaboradorEstatusOrden.getUsuarioAlta().setIdUsuario(idUsuario);
				
				colaboradorEstatusOrden.setCatEstatusColaborador(estatusColaboradorPago);
				pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatusOrden);
			}
				}
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
		
	}

	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPppNomina(Long idPppNominaFactura) {
		return pppNominaDocumentoDao.listDocumentosPppNomina(idPppNominaFactura);
	}

	@Transactional
	public void guardarDocumentosPppNominaFactura(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		Map<String,String> contextos = new HashMap<String,String>();
		contextos.put("1","nomina");
		contextos.put("2",String.valueOf(documento.getIdPppNomina()));
		contextos.put("3","nominaFactura");
		contextos.put("4",String.valueOf(documento.getIdPppNominaFactura()));
		contextos.put("5", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
	    		
	    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
	    
	    PppNominaFacturaDocumento pppNominaFacturaDocumento = new PppNominaFacturaDocumento();

	    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
	    
	    if(documento.getIdPppNominaFacturaDocumento() != null && documento.getIdPppNominaFacturaDocumento()!=0L) {
	    	pppNominaFacturaDocumento = pppNominaFacturaDocumentoDao.read(documento.getIdPppNominaFacturaDocumento());
	    }
	    
	    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
	    pppNominaFacturaDocumento.setDefinicionDocumento(definicionDocumento);
	    pppNominaFacturaDocumento.setIdCMS(idCMS);
	    pppNominaFacturaDocumento.setIdPppNominaFacturaDocumento(documento.getIdPppNominaFacturaDocumento());
	    pppNominaFacturaDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	    pppNominaFacturaDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
	    
	    PppNominaFactura pppNominaFactura = new PppNominaFactura();
	    pppNominaFactura.setIdPppNominaFactura(documento.getIdPppNominaFactura());
	    pppNominaFacturaDocumento.setPppNominaFactura(pppNominaFactura);
	    
	    
	    if(documento.getIdPppNominaFacturaDocumento() != null && documento.getIdPppNominaFacturaDocumento()!=0L) {
	    	Usuario usuarioModificacion = new Usuario();
		    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    pppNominaFacturaDocumento.setUsuarioModificacion(usuarioModificacion);
		    pppNominaFacturaDocumento.setFechaModificacion(new Date());
		    pppNominaFacturaDocumentoDao.update(pppNominaFacturaDocumento);
	    	
	    }else {
	    	
	    	Usuario usuarioalta = new Usuario();
		    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    pppNominaFacturaDocumento.setUsuarioAlta(usuarioalta);
		    pppNominaFacturaDocumento.setFechaAlta(new Date());
	    	
		    pppNominaFacturaDocumentoDao.create(pppNominaFacturaDocumento);
	    }
	}
	
	@Transactional
	public void eliminarDocumentosPppNomina(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException {
		if(documento.getIdPppNominaFacturaDocumento() != null && documento.getIdPppNominaFacturaDocumento()!=0L) {	    	
			pppNominaFacturaDocumentoDao.delete(documento.getIdPppNominaFacturaDocumento());
	    }
		
	}
	
	
	public void actualizarPPPNominaEstatus(Long idNominaPPP, String observacion , NominaEstatusEnum estatus , Long idUsuarioAplicativo) {
		//Consulta los estatus activos y los apaga  , inserta el nuevo estatus
		List<PppNominaEstatus> estatusActivos = pppNominaEstatusDao.getPppNominaEstatusActivo(idNominaPPP);
		
		for (PppNominaEstatus pppNominaEstatus : estatusActivos) {
			pppNominaEstatus.setIndEstatus(IndEstatusEnum.INACTIVO.getEstatus());
			pppNominaEstatusDao.update(pppNominaEstatus);
		}
		
		PppNominaEstatus nominaEstatus = new PppNominaEstatus();
		nominaEstatus.setCatEstatusNomina(new CatEstatusNomina(new Long(estatus.getId())));
		nominaEstatus.setPppNomina(new PppNomina(idNominaPPP));
		nominaEstatus.setFechaAlta(new Date());
		nominaEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
		nominaEstatus.setObservacion(observacion);
		nominaEstatus.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		
		pppNominaEstatusDao.create(nominaEstatus);
	}

	@Override
	@Transactional
	public Boolean cambiaEstatusNomina(Long idNomina, String observacion, NominaEstatusEnum nomEstaEnum, Long idUsuarioAplicativo) {
		try {



			if(idNomina != null) {
				
				if(observacion != null) {
					actualizarPPPNominaEstatus(idNomina,observacion, nomEstaEnum, idUsuarioAplicativo);
				}else {
					actualizarPPPNominaEstatus(idNomina,null, nomEstaEnum, idUsuarioAplicativo);
				}
		
				
				
			}else {
				LOGGER.error("Ocurrio un error en cambiaEstatusNomina, idNomina es null ");
				return Boolean.FALSE;
			}

				
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiaEstatusNomina ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean dispersionStpColaborador(Long idPppNomina, Long idUsuarioAplicativo) {
		
		try {
			
			if(idPppNomina != null) {
				
				List<EmpleadoDTO> colaboradores = pppColaboradorDao.obtenercolaboradoresParaDispersionStpByidNomina(idPppNomina);
				
				if(colaboradores!=null && !colaboradores.isEmpty()) {
					
					CatEstatusColaborador estatusColaboradorPago = null;
					
					for(EmpleadoDTO lista : colaboradores) {
						
						estatusColaboradorPago = catEstatusColaboradorDao.read(CatEstatusColaboradorEnum.DISPERSADO.getId());
						PppColaboradorEstatus colaboradorEstatusOrden = new PppColaboradorEstatus();
						colaboradorEstatusOrden.setPppColaborador(pppColaboradorDao.read(lista.getIdPppColaborador()));
						colaboradorEstatusOrden.setFechaAlta(new Date());
						colaboradorEstatusOrden.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
						colaboradorEstatusOrden.setUsuarioAlta(new Usuario());
						colaboradorEstatusOrden.getUsuarioAlta().setIdUsuario(idUsuarioAplicativo);
						
						colaboradorEstatusOrden.setCatEstatusColaborador(estatusColaboradorPago);
						pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatusOrden);

					}
				}
				
				 
				return Boolean.TRUE;
			}else {
				LOGGER.error("Ocurrio un error en dispersionStpColaborador, idPppNomina viene null ");
				return Boolean.FALSE;
			}

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en dispersionStpColaborador ", e);
			return Boolean.FALSE;
		}
		
	}
	
	
	@Override
	@Transactional
	public void crearEstatusColaboradorSTP(EmpleadoDTO colaborador, Long idUsuario, Long idEstatusColaborador) {
		try {
			
			PppColaboradorStp pppColaboradorStp = new PppColaboradorStp();
			pppColaboradorStp = pppColaboradorStpDao.read(colaborador.getIdPpppColaboradorStp());
			
			PppColaborador pppColaborador = new PppColaborador();
			pppColaborador = pppColaboradorDao.read(colaborador.getIdPppColaborador());
			
			if(colaborador.getIdStp() != null) {
				pppColaboradorStp.setIdStp(colaborador.getIdStp());
			}
			
			if(colaborador.getDescripcionErrorStp() != null) {
				pppColaboradorStp.setDescripcionErrorStp(colaborador.getDescripcionErrorStp());
			}
			
			pppColaboradorStpDao.update(pppColaboradorStp);
			
			CatEstatusColaborador estatusColaboradorPago = catEstatusColaboradorDao.read(idEstatusColaborador);
			PppColaboradorEstatus colaboradorEstatusOrden = new PppColaboradorEstatus();
			colaboradorEstatusOrden.setPppColaborador(pppColaborador);
			colaboradorEstatusOrden.setFechaAlta(new Date());
			colaboradorEstatusOrden.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			colaboradorEstatusOrden.setUsuarioAlta(new Usuario());
			colaboradorEstatusOrden.getUsuarioAlta().setIdUsuario(idUsuario);
			colaboradorEstatusOrden.setObservacion(colaborador.getDescError());
				
			colaboradorEstatusOrden.setCatEstatusColaborador(estatusColaboradorPago);
			pppColaboradorEstatusDao.createOrUpdate(colaboradorEstatusOrden);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
		}
		
	}

	@Override
	public List<CatGeneralDto> obtenerCatEstatusNomina() {
		try {
			return nominaEstatusDao.obtenerCatEstatusNomina();
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatEstatusNomina ", e);
			return Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominaDto> listaNominasSeguimiento(SeguimientoNominaDto seguimientoNomina) {
		try {
			List<NominaDto> lista  = nomimnaDao.listaNominasSeguimiento(seguimientoNomina);
			lista=this.listaColor(lista);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominasSeguimiento ", e);
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	@Override
	public Double getTotalNoDispersadoXNomina(Long idNomina) {
		Double total=null;
		try {
		
			StringBuilder sb = new StringBuilder();					
	
			sb.append("	SELECT IFNULL(SUM(ppl.monto_ppp),0) - IFNULL((SELECT SUM(pp.monto_ppp) as totalNoDispersado  FROM sin.ppp_colaborador PP ");  
			sb.append(" INNER JOIN ppp_colaborador_estatus CO ON PP.ID_PPP_COLABORADOR=CO.ID_PPP_COLABORADOR  ");
			sb.append(" WHERE PP.id_ppp_nomina IN (SELECT ID_PPP_NOMINA FROM ppp_nomina_complementaria WHERE ID_PPP_NOMINA_PADRE =? AND IND_ESTATUS=1 )  ");
			sb.append(" AND CO.id_cat_estatus_colaborador <>4 AND  CO.id_cat_estatus_colaborador <>9 AND  CO.id_cat_estatus_colaborador <>13 AND  CO.id_cat_estatus_colaborador <>5 ");
			sb.append(" and   PP.ind_estatus=1  and   co.ind_estatus=1 and CO.id_ppp_colaborador_estatus = (SELECT MAX(B.id_ppp_colaborador_estatus) FROM ppp_colaborador_estatus B ");
			sb.append(" where B.ID_PPP_COLABORADOR=PP.ID_PPP_COLABORADOR) ),0) as total ");
			sb.append("  FROM ppp_colaborador ppl  ");
			sb.append(" INNER JOIN ppp_colaborador_estatus coe ON ppl.ID_PPP_COLABORADOR=coe.ID_PPP_COLABORADOR ");
			sb.append(" and   ppl.ind_estatus=1  and   coe.ind_estatus=1 and COE.id_ppp_colaborador_estatus ");
			sb.append(" =(select MAX(id_ppp_colaborador_estatus) from ppp_colaborador_estatus where ID_PPP_COLABORADOR=coe.ID_PPP_COLABORADOR and  ind_estatus=1 ) ");
			sb.append("	WHERE ppl.id_ppp_nomina =? AND coe.id_cat_estatus_colaborador in (4, 9, 13, 5) ");

	
			total=jdbcTemplate.queryForObject(sb.toString(), new Object[]{idNomina, idNomina}, Double.class);
		
					return total;	
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getTotalNoDispersadoXNomina ", e);
		}
		return total;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	@Override
	public Double getTotalFacturadoXNomina(Long idNomina) {
		Double total=null;
		try {
		
			StringBuilder sb = new StringBuilder();					
			sb.append(" SELECT sum(monto_ppp) FROM ppp_colaborador where id_ppp_nomina=? "); 
		total=jdbcTemplate.queryForObject(sb.toString(), new Object[]{idNomina}, Double.class);
					return total;	
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getTotalFacturadoXNomina ", e);
		}
		return total;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	@Override
	public Long getNominasComplementariasEnBorrador(Long idNomina) {
		Long idNominaBorrador=null;
		try {
		
			StringBuilder sb = new StringBuilder();							
			sb.append("SELECT  ID_PPP_NOMINA FROM ppp_nomina_estatus est WHERE est.id_ppp_nomina =( "); 
			sb.append("SELECT ID_PPP_NOMINA FROM ppp_nomina_complementaria comp WHERE comp.ID_PPP_NOMINA_PADRE =? AND comp.IND_ESTATUS=1  "); 
			sb.append("AND comp.fecha_alta =(select max(fecha_alta) FROM ppp_nomina_complementaria where id_ppp_nomina_padre=?))  "); 
			sb.append("AND est.id_cat_estatus_nomina=24 and est.fecha_alta=(select max(fecha_alta) from ppp_nomina_estatus where  "); 
			sb.append("id_ppp_nomina =(SELECT ID_PPP_NOMINA FROM ppp_nomina_complementaria comp WHERE comp.ID_PPP_NOMINA_PADRE =? AND comp.IND_ESTATUS=1  ");  
			sb.append("AND comp.fecha_alta =(select max(fecha_alta) FROM ppp_nomina_complementaria where id_ppp_nomina_padre=?)))  "); 
			
			
			
			idNominaBorrador=jdbcTemplate.queryForObject(sb.toString(), new Object[]{idNomina, idNomina, idNomina, idNomina}, Long.class);
					return idNominaBorrador;	
		
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominasComplementariasEnBorrador ", e);
		}
		return idNominaBorrador;
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public NominaDto getNominaPadre(Long idNomina) throws BusinessException {
		try {
		
			return nomimnaDao.getNominaPadre(idNomina);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getNominaPadre ", e);
			throw new BusinessException("Ocurrio un error en el sistema. Favor de intentarlo mas tarde.");
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public String  getNominaEstatusById(Long idNomina)  {
		
		List<PppNominaEstatus> listEstatus= pppNominaEstatusDao.getPppNominaEstatusActivo(idNomina);
		
		if (listEstatus!=null) {
			return listEstatus.get(0).getCatEstatusNomina().getClave();
		}else {
			return null;
			
		}
	}
	
	
	public List<NominaDto> listaColor (List<NominaDto> listaNominas){
		List<NominaDto> listaNominaColor=listaNominas;
        for(int i=0; i<listaNominaColor.size(); i++){

        if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 7 || listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 17    ){
        	listaNominaColor.get(i).setColorNomina("circuloamarillo.jpg");
        		
        }else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 1 || listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 21 || listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 22) {
        	listaNominaColor.get(i).setColorNomina("circuloGris.jpg");
        }
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 25) {
        	listaNominaColor.get(i).setColorNomina("circuloNaranja.jpg");
        }
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 18) {
        	listaNominaColor.get(i).setColorNomina("circuloVerde.jpg");
        }
        //stp_errores
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 19) {
        List<EmpleadoDTO> listColaboradores=colaboradorPppBo.cargaColaboradoresRespuestaStp(listaNominaColor.get(i).getIdNomina());
        
        for(int j=0; j<listColaboradores.size(); j++){
        	
        	if(listColaboradores.get(j).getEstado()=="3" && listColaboradores.get(j).getCausaDevolucion() =="Insuficiencia de liquidez del participante emisor" ){
        		listaNominaColor.get(i).setColorNomina("circuloAzul.jpg");	
        	}else {
        		listaNominaColor.get(i).setColorNomina("circuloMorado.jpg");
        	}
        }
    
        }
        
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 24) {
        	listaNominaColor.get(i).setColorNomina("circuloRosa.jpg");
        }
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 20) {
        	listaNominaColor.get(i).setColorNomina("circulorojo.png");
        }
        else if(listaNominas.get(i).getCatEstatusNomina().getIdCatGeneral() == 23) {
        	listaNominaColor.get(i).setColorNomina("palomita.jpg");
        }
        else{
        	listaNominaColor.get(i).setColorNomina("circuloCafe.png");
       
         }
         }
        return listaNominaColor;
		
	}
	
	
	@Override
	public Long getIdNominaFacturaPadre(Long idNominaHija) {
		return nomimnaDao.getIdNominaFacturaPadre(idNominaHija);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NominasDTO> listaNominaAFacturar(String listCliente , Long idNominaCliente ) {
		try {
			List<NominasDTO> lista  = nomimnaDao.listaNominaAFacturar(listCliente,  idNominaCliente);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaNominaAFacturar ", e);
			return Collections.emptyList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPppFactura(Long idPppFactura){
		return pppFacturaDocumentoDao.listDocumentosPppFactura(idPppFactura);
	}
	
	public String obtenerFormatoFecha(Date fecha) {
		String fechaformato = Utilerias.convierteDate(fecha);
		return fechaformato;
	}




}
