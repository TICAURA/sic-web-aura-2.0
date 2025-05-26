package mx.com.consolida.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.CatTipoPagoDto;
import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.dao.interfaz.CatGrupoDao;
import mx.com.consolida.dao.interfaz.CatValorDefaultDao;
import mx.com.consolida.dao.interfaz.CatalogoDao;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.CatValorDefaultDto;
import mx.com.consolida.entity.CatGrupo;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;

@Service
public class CatalogoBOImpl implements CatalogoBO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatalogoBOImpl.class);

	@Autowired
	private CatalogoDao catDao;
	
	@Autowired
	private CatValorDefaultDao catDaoDefault;
	
	@Autowired
	private CatGrupoDao catGrupoDao;
	
	@Transactional
	public List<CatGeneralDto> obtenerCatGeneralByClvMaestro(String clv){
		return catDao.obtenerCatGeneralByClvMaestro(clv);
	}
	@Transactional
	public CatGeneralDto obtenerCatGeneralByClv(String clv){
		return catDao.obtenerCatGeneralByClv(clv);
	}
	public List<CatTipoPagoDto> otenerTipoPagoActivo(){
		return catDao.otenerTipoPagoActivo();
	}
	
	@Transactional
	public List<CatGrupoDto> obtenerCatalogoGrupo() {
		List<CatGrupo> listCatGrupo =catGrupoDao.findAll();
		List<CatGrupoDto> listCatGrupoDto =  new ArrayList<CatGrupoDto>();
		for(CatGrupo grupo : listCatGrupo) {
			CatGrupoDto grupoDto =  new CatGrupoDto();
			
			grupoDto.setIdCatGrupo(grupo.getIdCatGrupo());
			grupoDto.setCveGrupo(grupo.getCveGrupo());
			grupoDto.setDescripcionRazonSocial(grupo.getDescripcionRazonSocial());
			grupoDto.setRfc(grupo.getRfc());
			
			listCatGrupoDto.add(grupoDto);
		}
		
		
		return listCatGrupoDto;
	}
	
	@Transactional
	public CatValorDefaultDto obtenerCatValorDefaultById(Long id) {
		return catDaoDefault.obtenerCatValorDefaultById(id);
	}
	@Transactional
	public CatValorDefaultDto obtenerCatValorDefaultByIdPrincipal(Long id) {	
		return catDaoDefault.obtenerCatValorDefaultByIdPrincipal(id);
	}
	
	@Transactional
	public List<CatValorDefaultDto> obtenerListCatValorDefaultById(Long id,Long indEstatus) {
		return catDaoDefault.obtenerListCatValorDefaultById(id,indEstatus);
	}
	@Transactional
	public CatValorDefaultDto guardarCatValorDefault(CatValorDefaultDto dto, UsuarioAplicativo us) {
		return catDaoDefault.guardarCatValorDefault(dto,us);
	}
	
	@Transactional
	public List<CatGeneralDto> obtenerCatEntidadFederativaByClvMaestro(String clv){
		return catDao.obtenerCatEntidadFederativaByClvMaestro(clv);
	}
	
	@Transactional
	public List<CatGeneralDto> obtenerCatMunicipioByClvMaestroByEntidadFed(String maestro, String cveEntidad){
		return catDao.obtenerCatMunicipioByClvMaestroByEntidadFed(maestro, cveEntidad);
	}
	
	@Transactional
	public List<CatGeneralDto> obtenerCatMunicipioByClvMaestro(String clv){
		return catDao.obtenerCatMunicipioByClvMaestro(clv);
	}
	
	@Transactional
	public List<CatGeneralDto> obtenerCatMunicipioByEntidadFedByCveMun(String cveEntidad, String cveMunicipio){
		return catDao.obtenerCatMunicipioByEntidadFedByCveMun(cveEntidad, cveMunicipio);
	}
	@Override
	public List<CatGeneralDto> obtenerCatEstatusInicialCotizaciones() {
		return catDao.obtenerCatEstatusInicialCotizaciones();
	}
	@Override
	public List<CatProductoDto> obtenerCatProducto() {
		return catDao.obtenerCatProducto();
	}
	@Override
	@Transactional(readOnly = true)
	public List<CatSubGiroComercialDto> obtenerCatSubgiroXIdGiro(Long idGiro) {
		try {
			
			List<CatSubGiroComercialDto> lista = catDao.obtenerCatSubgiroComercialXIdGiro(idGiro);
			
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerSubgiroXIdGiro ",e);
			return Collections.emptyList();
		}
	}
	@Override
	public CatGeneralDto obtenerCatGeneralByClvMaestroClv(String cve, String cveSat) {
		return catDao.obtenerCatGeneralByClvMaestroClv(cve, cveSat);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CatGeneralDto> catFormulasByIdTipoFormula(int id) {
		try {
			List<CatGeneralDto> lista = catDao.catFormulasByIdTipoFormula(new Long(id));
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerSubgiroXIdGiro ",e);
			return Collections.emptyList();
		}
	}
	@Override
	@Transactional(readOnly = true)
	public List<CatGeneralDto> obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(String clv) {
		try {
			List<CatGeneralDto> lista = catDao.obtenerCatGeneralByClvMaestroOrderByIdCatGeneral(clv);
			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerCatGeneralByClvMaestroOrderByIdCatGeneral ",e);
			return Collections.emptyList();
		}
	}
	
}
