package mx.com.consolida.ppp.service.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.crm.service.impl.DocumentoServiceBO;
import mx.com.consolida.dto.DefinicionDocumentoENUM;
import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.ppp.CatEstatusNomina;
import mx.com.consolida.entity.ppp.PppNomina;
import mx.com.consolida.entity.ppp.PppNominaComplemento;
import mx.com.consolida.entity.ppp.PppNominaEstatus;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegal;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegalDocumento;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.NominaEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dao.interfaz.PppNominaComplementoDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaDao;
import mx.com.consolida.ppp.dao.interfaz.PppNominaEstatusDao;
import mx.com.consolida.ppp.dto.NominaComplementoDto;
import mx.com.consolida.ppp.dto.NominaDto;
import mx.com.consolida.ppp.service.interfaz.NominaComplementoBO;
import mx.com.documento.DefinicionDocumentoDto;

@Service
public class NominaComplementoBOImpl implements NominaComplementoBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NominaComplementoBOImpl.class);

	@Autowired
	private PppNominaComplementoDao nominaComplementoDao;
	
	@Autowired
	private PppNominaEstatusDao pppNominaEstatusDao;
	
	@Autowired
	private PppNominaDao pppNominaDao;
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;

	@Override
	@Transactional
	public Boolean guargarNominaComplemento(NominaComplementoDto nominaComplementoDto, Long idUsuarioAplicativo) {
		
		Map<String,String> contextos = new HashMap<String,String>();
		DocumentoCSMDto documento = new DocumentoCSMDto();
		Long idCMS = null;
		
		try {
			
			PppNominaComplemento nominaComplemento = new PppNominaComplemento();
			
			if (nominaComplementoDto.getIdNominaComplemento() != null) {
	
				nominaComplemento = nominaComplementoDao.read(nominaComplementoDto.getIdNominaComplemento());
				nominaComplemento.setFechaFacturacion(nominaComplementoDto.getFechaFacturacion());
				nominaComplemento.setFechaDispersion(nominaComplementoDto.getFechaDispersion());
				nominaComplemento.setFechaTimbrado(nominaComplementoDto.getFechaTimbrado());
				nominaComplemento.setRequiereFianciamiento(nominaComplementoDto.getRequiereFianciamiento());
				nominaComplemento.setMontoFinanciamiento(nominaComplementoDto.getMontoFinanciamiento()!=null ? nominaComplementoDto.getMontoFinanciamiento() : null);
				nominaComplemento.setObservaciones(nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null);
				nominaComplemento.setFechaModificacion(new Date());
				nominaComplemento.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				nominaComplemento.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
				
				if(nominaComplementoDto.getRequiereFianciamiento()) {
					
					if(nominaComplementoDto.getDocumentoNuevo().getMimeType()!=null) {
							contextos.put("1","nomina");
							contextos.put("2",String.valueOf(nominaComplementoDto.getNominaDto().getIdNomina()));
							contextos.put("3", String.valueOf(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento()));
							
							documento.setDocumentoNuevo(nominaComplementoDto.getDocumentoNuevo());
							documento.setDefinicion(new DefinicionDocumentoDto(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento()));
						    idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
						    nominaComplemento.setIdDefinicionDocumento(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento());
							nominaComplemento.setIdCMS(idCMS);
							nominaComplemento.setNombreArchivo(nominaComplementoDto.getDocumentoNuevo().getNombreArchivo());
					}

				}else {
					nominaComplemento.setIdDefinicionDocumento(null);
					nominaComplemento.setIdCMS(null);
					nominaComplemento.setNombreArchivo(null);
				}
				
				nominaComplementoDao.update(nominaComplemento);	
				
				if(nominaComplementoDto.getRequiereFianciamiento() != null && nominaComplementoDto.getRequiereFianciamiento()) {
					
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.REQUIERE_FINANCIAMIENTO,
									idUsuarioAplicativo);
				}else {
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.BORRADOR,
									idUsuarioAplicativo);
				}
	
			} else {
				
				if(nominaComplementoDto.getRequiereFianciamiento()) {
					contextos.put("1","nomina");
					contextos.put("2",String.valueOf(nominaComplementoDto.getNominaDto().getIdNomina()));
					contextos.put("3", String.valueOf(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento()));
					
					documento.setDocumentoNuevo(nominaComplementoDto.getDocumentoNuevo());
					documento.setDefinicion(new DefinicionDocumentoDto(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento()));
				    idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
				    nominaComplemento.setIdDefinicionDocumento(DefinicionDocumentoENUM.PPP_DOCUMENTO_SUSTENTO_FINANCIAMIENTO.getIdDefinicionDocumento());
					nominaComplemento.setIdCMS(idCMS);
					nominaComplemento.setNombreArchivo(nominaComplementoDto.getDocumentoNuevo().getNombreArchivo());
					
				}else {
					nominaComplemento.setIdDefinicionDocumento(null);
					nominaComplemento.setIdCMS(null);
					nominaComplemento.setNombreArchivo(null);
				}

				nominaComplemento.setPppNomina(new PppNomina(nominaComplementoDto.getNominaDto().getIdNomina()));
				nominaComplemento.setFechaFacturacion(nominaComplementoDto.getFechaFacturacion());
				nominaComplemento.setFechaDispersion(nominaComplementoDto.getFechaDispersion());
				nominaComplemento.setFechaTimbrado(nominaComplementoDto.getFechaTimbrado());
				nominaComplemento.setRequiereFianciamiento(nominaComplementoDto.getRequiereFianciamiento());
				nominaComplemento.setMontoFinanciamiento(nominaComplementoDto.getMontoFinanciamiento()!=null ? nominaComplementoDto.getMontoFinanciamiento() : null);
				nominaComplemento.setObservaciones(nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null);
				nominaComplemento.setFechaAlta(new Date());
				nominaComplemento.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				nominaComplemento.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
				nominaComplementoDao.create(nominaComplemento);				
							
				if(nominaComplementoDto.getRequiereFianciamiento() != null && nominaComplementoDto.getRequiereFianciamiento()) {
					
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.REQUIERE_FINANCIAMIENTO,
									idUsuarioAplicativo);
				}
				if(nominaComplementoDto.getNominaDto().getCatEstatusNomina().getIdCatGeneral() == 24) {
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.CTA_CONCILIADA,
									idUsuarioAplicativo);
					
				}
				//ejemplo con Layner
				if(nominaComplementoDto.getNominaDto().getClienteDto().getEstimbreSindicato()==1) {
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.CTA_CONCILIADA,
									idUsuarioAplicativo);
				}
				
				/*if(nominaComplementoDto.getNominaDto().getNecesitaFactura()== false) {
					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.COL_CARGADOS,
									idUsuarioAplicativo);
					
				}*/
				
			}
	
			
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guargarNominaComplemento ", e);
			return Boolean.FALSE;
		}
	}

	private void actualizarPPPNominaEstatus(Long idNominaPPP, String observacion , NominaEstatusEnum estatus , Long idUsuarioAplicativo) {
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
	@Transactional(readOnly = true)
	public NominaComplementoDto getDatosNomComplByIdNomCompl(String claveNomina) {
		try {
			
			return nominaComplementoDao.getDatosNomComplByIdNomCompl(claveNomina);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosNomComplByIdNomCompl ", e);
			return null;
		}
	}
	
	@Override
	@Transactional
	public Boolean autorizaFinanciamientoOperaciones(NominaComplementoDto nominaComplementoDto, Long idUsuarioAplicativo) {
		try {

			
			if (nominaComplementoDto.getIdNominaComplemento() != null) {

				if(nominaComplementoDto.getRequiereFianciamiento() != null && nominaComplementoDto.getRequiereFianciamiento()) {

					actualizarPPPNominaEstatus(nominaComplementoDto.getNominaDto().getIdNomina(),
							nominaComplementoDto.getObservaciones()!=null ? nominaComplementoDto.getObservaciones() : null,
									NominaEstatusEnum.AUTORIZADO_OPERACIONES,
									idUsuarioAplicativo);
				}
	
			} else {
	
				LOGGER.error("Ocurrio un error en guargarNominaComplemento, nominaComplementoDto.getIdNominaComplemento() es null ");
				return Boolean.FALSE;
			}
	
			
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guargarNominaComplemento ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public NominaDto getNominaDtoByClave(String claveNomina) {
		try {
			
			return pppNominaDao.getNominaDtoByClave(claveNomina);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cargarDatosDespuesDeGuardarComplemento ", e);
			return new NominaDto();
		}
	}

}
