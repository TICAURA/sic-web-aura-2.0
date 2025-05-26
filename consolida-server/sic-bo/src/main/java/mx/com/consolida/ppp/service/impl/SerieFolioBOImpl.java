package mx.com.consolida.ppp.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.ppp.CatFolio;
import mx.com.consolida.entity.ppp.CatSerie;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.IndEstatusSeriesEnum;
import mx.com.consolida.ppp.dao.interfaz.CatFolioDao;
import mx.com.consolida.ppp.dao.interfaz.CatSerieDao;
import mx.com.consolida.ppp.dto.CatFolioDto;
import mx.com.consolida.ppp.dto.CatSerieDto;
import mx.com.consolida.ppp.service.interfaz.SerieFolioBO;

@Service
public class SerieFolioBOImpl implements SerieFolioBO{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SerieFolioBOImpl.class);
	
	@Autowired
	private CatSerieDao catSerieDao;
	
	@Autowired
	private CatFolioDao catFolioDao;

	@Override
	@Transactional
	public Boolean guardarActualizarSerie(CatSerieDto catSerieDto, Long idUsuarioAplicativo) {
		try {
			
			if(catSerieDto ==null 
					|| (catSerieDto!=null 
							&& catSerieDto.getNombreSerie() == null )
					|| catSerieDto.getCatTipoComprobante()== null 
							|| (catSerieDto.getCatTipoComprobante()!= null && catSerieDto.getCatTipoComprobante().getIdCatGeneral() == null)
					|| catSerieDto.getCatCelula()== null 
							|| (catSerieDto.getCatCelula()!= null && catSerieDto.getCatCelula().getIdCatGeneral() == null)) {
				LOGGER.error("Ocurrio un error en guardarSerie, favor de revisar catSerieDto, alguna variable viene null");
				return Boolean.FALSE;
			}

			
			CatSerie catSerie = new CatSerie();
			if(catSerieDto.getIdCatSerie()!=null) {
				
				catSerie = catSerieDao.read(catSerieDto.getIdCatSerie());
				catSerie.setCatCelula(new CatGeneral(catSerieDto.getCatCelula().getIdCatGeneral()));
				catSerie.setCatTipoComprobante(new CatGeneral(catSerieDto.getCatTipoComprobante().getIdCatGeneral()));
				catSerie.setNombreSerie(catSerieDto.getNombreSerie());
				//catSerie.setFolioInicial( 1L );
				catSerie.setFechaModificacion(new Date());
				catSerie.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				
				catSerie.setFechaIncioVigencia(catSerieDto.getFechaInicioVigencia());
				catSerie.setFechaFinVigencia(catSerieDto.getFechaFinVigencia());
				
				
				catSerieDao.update(catSerie);
				
			}else {
				
				catSerie.setCatTipoComprobante(new CatGeneral(catSerieDto.getCatTipoComprobante().getIdCatGeneral()));
				catSerie.setCatCelula(new CatGeneral(catSerieDto.getCatCelula().getIdCatGeneral()));
				catSerie.setNombreSerie(catSerieDto.getNombreSerie());
				catSerie.setFolioInicial( 1L );
				catSerie.setFechaAlta(new Date());
				catSerie.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				catSerie.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
				catSerie.setFechaIncioVigencia(catSerieDto.getFechaInicioVigencia());
				catSerie.setFechaFinVigencia(catSerieDto.getFechaFinVigencia());
				catSerie.setIdEstatusSerie(IndEstatusSeriesEnum.POR_INICIAR.getEstatus());
				
				catSerieDao.create(catSerie);
				
			}
			

			
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarSerie ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatSerieDto> listaCatSerie() {
		try {
			
			return catSerieDao.listaCatSerie();

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en SerieFolioBOImpl ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean eliminarSerie(Long idCatSerie, Long idUsuarioAplicativo) {
		try {
			
			if(idCatSerie ==null ) {
				LOGGER.error("Ocurrio un error en eliminatSerie, idCatSerie viene null");
				return Boolean.FALSE;
			}
			
			CatSerie catSerie = new CatSerie();
			catSerie = catSerieDao.read(idCatSerie);
			catSerie.setFechaModificacion(new Date());
			catSerie.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
			catSerie.setIndEstatus(Long.valueOf(IndEstatusEnum.INACTIVO.getEstatus()));
			catSerieDao.update(catSerie);

			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminatSerie ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarFolio(CatFolioDto catFolioDto, Long idUsuarioAplicativo) {
		try {
			
			if(catFolioDto ==null 
					|| catFolioDto.getCatSerieDto() == null
					|| (catFolioDto.getCatSerieDto()!=null 
							&& catFolioDto.getCatSerieDto().getIdCatSerie() == null) 
					|| catFolioDto.getNumeroFolio() == null) {
				LOGGER.error("Ocurrio un error en guardarActualizarFolio, favor de revisar catFolioDto, alguna variable viene null");
				return Boolean.FALSE;
			}
			
			CatFolio catFolio = new CatFolio();
			if(catFolioDto.getIdCatFolio()!=null) {
				
				catFolio = catFolioDao.read(catFolioDto.getIdCatFolio());
				catFolio.setNumeroFolio(catFolioDto.getNumeroFolio());
				catFolio.setCatSerie(new CatSerie(catFolioDto.getCatSerieDto().getIdCatSerie()));
				catFolio.setFechaModificacion(new Date());
				catFolio.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				catFolio.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
				catFolioDao.update(catFolio);
				
			}else {
				
				catFolio.setNumeroFolio(catFolioDto.getNumeroFolio());
				catFolio.setCatSerie(new CatSerie(catFolioDto.getCatSerieDto().getIdCatSerie()));
				catFolio.setFechaAlta(new Date());
				catFolio.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				catFolio.setIndEstatus(Long.valueOf(IndEstatusEnum.ACTIVO.getEstatus()));
				catFolioDao.create(catFolio);
				
			}

			
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarFolio ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatFolioDto> listaCatFolio() {
		try {
			
			return catFolioDao.listaCatFolio();

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en SerieFolioBOImpl ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean eliminarFolio(Long idCatFolio, Long idUsuarioAplicativo) {
		
		try {
			
			if(idCatFolio ==null ) {
				LOGGER.error("Ocurrio un error en eliminarFolio, idCatFolio viene null");
				return Boolean.FALSE;
			}
			
			CatFolio catFolio = new CatFolio();
			catFolio = catFolioDao.read(idCatFolio);
			catFolio.setFechaModificacion(new Date());
			catFolio.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
			catFolio.setIndEstatus(Long.valueOf(IndEstatusEnum.INACTIVO.getEstatus()));
			catFolioDao.update(catFolio);

			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarFolio ", e);
			return Boolean.FALSE;
		}
	}

}
