package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.ClienteCuentaBancariaDto;
import mx.com.consolida.entity.crm.ClienteCuentaBancaria;

public interface ClienteCuentaBancariaDao extends mx.com.consolida.dao.DAO<ClienteCuentaBancaria, Long>{

	List<ClienteCuentaBancariaDto> convertirCuentaADto(List<ClienteCuentaBancaria> clienteCuentaBancaria);


}
