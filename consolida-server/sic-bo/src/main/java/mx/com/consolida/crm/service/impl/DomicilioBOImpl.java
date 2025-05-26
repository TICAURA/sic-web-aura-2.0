package mx.com.consolida.crm.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.consolida.crm.dao.interfaz.DomicilioDao;
import mx.com.consolida.crm.dto.DomicilioDto;
import mx.com.consolida.crm.service.interfaz.DomicilioBO;
import mx.com.consolida.entity.crm.CatEntidadFederativa;
import mx.com.consolida.entity.crm.CatMunicipios;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.CatEstatusEnum;

@Service
public class DomicilioBOImpl implements DomicilioBO{
	@Autowired
	private DomicilioDao domicilioDao;
	
	
	@Transactional
	public Long guardarDomicilio(DomicilioDto domicilioDto , Long idUsuarioAutenticado) {
		
		
		Domicilio domicilio = new Domicilio();
		
		if(domicilioDto.getIdDomicilio() != null) {
			domicilio = domicilioDao.read(domicilioDto.getIdDomicilio());
		}
		
		domicilio.setCodigoPostal(domicilioDto.getCodigoPostal());
		domicilio.setCalle(domicilioDto.getCalle());
		domicilio.setNumeroExterior(domicilioDto.getNumeroExterior()!=null ? domicilioDto.getNumeroExterior() : null);
		domicilio.setNumeroInterior(domicilioDto.getNumeroInterior()!=null ? domicilioDto.getNumeroInterior() : null);
		domicilio.setColonia(domicilioDto.getColonia());
		CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
		catEntidadFederativa.setIdEntidadFederativa(domicilioDto.getCatEntidadFederativa().getIdCatGeneral());
		domicilio.setCatEntidadFederativa(catEntidadFederativa);
		CatMunicipios catMunicipios = new CatMunicipios();
		catMunicipios.setIdCatMunicipios(domicilioDto.getCatMunicipios().getIdCatGeneral());
		domicilio.setCatMunicipios(catMunicipios);		
		Usuario usuarioAutenticado = new Usuario();
		usuarioAutenticado.setIdUsuario(idUsuarioAutenticado);
		
		if(domicilioDto.getIdDomicilio() == null) {
			
			domicilio.setUsuarioByUsaurioAlta(usuarioAutenticado);
			domicilio.setFechaAlta(new Date());
			
		}else {
			domicilio.setUsuarioByUsuarioModificacion(usuarioAutenticado);
			domicilio.setFechaModificacion(new Date());
		}
		
		domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		Long idDomicilio = domicilioDao.create(domicilio);
		
		domicilioDao.createOrUpdate(domicilio);
		
		return domicilio.getIdDomicilio();
	}
	
}
