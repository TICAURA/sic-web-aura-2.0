package mx.com.consolida.crm.dao.interfaz;

import java.util.List;

import mx.com.consolida.crm.dto.PrestadoraServicioCuentaBancariaDto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioCuentaBancaria;

public interface PrestadoraServicioCuentaBancariaDao extends mx.com.consolida.dao.DAO<PrestadoraServicioCuentaBancaria, Long>{

	List<PrestadoraServicioCuentaBancariaDto> convertirCuentaPrestadoraServicioADto(
			List<PrestadoraServicioCuentaBancaria> prestadoraServicioCuenta);

}
