package mx.com.consolida.crm.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.PrestadoraServicioCuentaBancariaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioCuentaBancaria;

@Repository
public class PrestadoraServicioCuentaBancariaDaoImpl extends GenericDAO<PrestadoraServicioCuentaBancaria, Long> implements PrestadoraServicioCuentaBancariaDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioCuentaBancariaDaoImpl.class);


	@Override
	public List<PrestadoraServicioCuentaBancariaDto> convertirCuentaPrestadoraServicioADto(
			List<PrestadoraServicioCuentaBancaria> prestadoraServicioCuentaBancaria) {

		List<PrestadoraServicioCuentaBancariaDto> listPrestadoraServicioCuenta = new ArrayList<PrestadoraServicioCuentaBancariaDto>();
		
		for(PrestadoraServicioCuentaBancaria cuenta: prestadoraServicioCuentaBancaria) {
			PrestadoraServicioCuentaBancariaDto cuentaDto = new PrestadoraServicioCuentaBancariaDto();
			if(cuenta.getIndEstatus() != 0) {
			cuentaDto.setCatBanco(new CatGeneralDto());
			cuentaDto.setCatTipoCuenta(new CatGeneralDto());
			
			cuentaDto.getCatBanco().setIdCatGeneral(cuenta.getCatBanco().getIdCatGeneral());
			cuentaDto.getCatBanco().setClave(cuenta.getCatBanco().getClave());
			cuentaDto.getCatBanco().setDescripcion(cuenta.getCatBanco().getDescripcion());
			
			cuentaDto.getCatTipoCuenta().setIdCatGeneral(cuenta.getCatTipoCuenta().getIdCatGeneral());
			cuentaDto.getCatTipoCuenta().setClave(cuenta.getCatTipoCuenta().getClave());
			cuentaDto.getCatTipoCuenta().setDescripcion(cuenta.getCatTipoCuenta().getDescripcion());
			
			cuentaDto.setClabeInterbancaria(cuenta.getClabeInterbancaria());
			if(cuenta.getEsPrincipal() == 1) {
				cuentaDto.setEsPrincipal(Boolean.TRUE);
			}else {
				cuentaDto.setEsPrincipal(Boolean.FALSE);
			}
			cuentaDto.setIdPrestadoraServicioCuentaBancaria(cuenta.getIdPrestadoraServicioCuentaBancaria());
			cuentaDto.setNumeroCuenta(cuenta.getNumeroCuenta());
			cuentaDto.setNumeroReferencia(cuenta.getNumeroReferencia());
			
			cuentaDto.setIdPrestadoraServicio(cuenta.getPrestadoraServicio().getIdPrestadoraServicio());
			
			listPrestadoraServicioCuenta.add(cuentaDto);
			
			}
			
		}
		
		return listPrestadoraServicioCuenta;
	}


	

}