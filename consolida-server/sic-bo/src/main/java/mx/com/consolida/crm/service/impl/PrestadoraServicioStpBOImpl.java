package mx.com.consolida.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioStpDao;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioStp;


@Service
public class PrestadoraServicioStpBOImpl implements PrestadoraServicioStpBO {
	@Autowired
	private PrestadoraServicioStpDao prestadoraServicioStpDao;

	public PrestadoraServicioStpBOImpl()  {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	@Override
	public PrestadoraServicioStpDto getPrestadoraServicioByIdStp(Long idPrestadoraServicio) {
		PrestadoraServicioStpDto pStpDto = new PrestadoraServicioStpDto();
		PrestadoraServicioStp p = prestadoraServicioStpDao.read(idPrestadoraServicio);
		pStpDto.setActivo(p.getIndEstatus()==1l? Boolean.TRUE: Boolean.FALSE);
		pStpDto.setApiKey(p.getApiKey());
		pStpDto.setClabeCuentaOrdenante(p.getClabeCuentaOrdenante());
		pStpDto.setClientId(p.getClientId());
		pStpDto.setIdCliente(p.getIdCliente());
		pStpDto.setClientSecret(p.getClientSecret());
		pStpDto.setDescripcionTipoDispersor(p.getIdTipoDispersor().getDescripcion());
		pStpDto.setIdCuentaAhorro(p.getIdCuentaAhorro());
		pStpDto.setIdPrestadoraServicioStp(p.getIdPrestadoraServicioStp());
		pStpDto.setPassword(p.getPassword());
		pStpDto.setUserName(p.getUserName());
		
		return pStpDto;
	}
	

}
