package mx.com.consolida.crm.dao.interfaz;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.crm.dto.ClienteCuentaBancariaDto;
import mx.com.consolida.dao.GenericDAO;
import mx.com.consolida.entity.crm.ClienteCuentaBancaria;

@Repository
public class ClienteCuentaBancariaDaoImpl extends GenericDAO<ClienteCuentaBancaria, Long> implements ClienteCuentaBancariaDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(ClienteCuentaBancariaDaoImpl.class);


	@Override
	public List<ClienteCuentaBancariaDto> convertirCuentaADto(List<ClienteCuentaBancaria> clienteCuentaBancaria) {
		List<ClienteCuentaBancariaDto> listClienteCuenta = new ArrayList<ClienteCuentaBancariaDto>();
		
		for(ClienteCuentaBancaria cuenta: clienteCuentaBancaria) {
			ClienteCuentaBancariaDto cuentaDto = new ClienteCuentaBancariaDto();
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
			
			cuentaDto.setIdClienteCuentaBancaria(cuenta.getIdClienteCuentaBancaria());
			cuentaDto.setNumeroCuenta(cuenta.getNumeroCuenta());
			cuentaDto.setNumeroReferencia(cuenta.getNumeroReferencia());
			
			cuentaDto.setIdCliente(cuenta.getCliente().getIdCliente());
			
			listClienteCuenta.add(cuentaDto);
			
			}
			
		}
		
		return listClienteCuenta;
	}


	
}