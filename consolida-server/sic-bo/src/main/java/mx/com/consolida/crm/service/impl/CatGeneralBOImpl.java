package mx.com.consolida.crm.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.CatGeneralDao;
import mx.com.consolida.crm.service.interfaz.CatGeneralBO;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatMaestro;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.UsuarioAplicativo;

@Service
public class CatGeneralBOImpl implements CatGeneralBO{
	
	@Autowired
	private CatGeneralDao catGeneralDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatGeneralBOImpl.class);

	@Override
	@Transactional
	public Boolean guardarCatGeneral(Long idCatMaestro, String clave, String descripcion, UsuarioAplicativo usuarioAplicativo) {
		
		try {
			
			CatGeneral catGeneral = new CatGeneral();
			catGeneral.setCatMaestro(new CatMaestro(idCatMaestro));
			catGeneral.setClave(clave);
			catGeneral.setDescripcion(descripcion);
			catGeneral.setIdUsuarioAlta(usuarioAplicativo.getIdUsuario());
			catGeneral.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			catGeneralDao.create(catGeneral);
			
			return Boolean.TRUE;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarCatGeneral ", e);
			return Boolean.FALSE;
		}
	}

}
