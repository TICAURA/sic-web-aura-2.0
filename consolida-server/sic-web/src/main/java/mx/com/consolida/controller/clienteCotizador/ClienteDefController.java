package mx.com.consolida.controller.clienteCotizador;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.interfaz.ClienteFinalBO;

@Controller
@RequestMapping("clientes")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CLIENTE_TEMP })
public class ClienteDefController {

	@Autowired
	ClienteFinalBO nuevoClienteBO;
	
	@RequestMapping(value = "/cargaInicial")
	@ResponseBody
	public List<ClienteTempDto> cargaInicial() {
		List<ClienteTempDto> clienteTempDto = new ArrayList<ClienteTempDto>();
		clienteTempDto = nuevoClienteBO.obtenerClientes();
		return clienteTempDto;
	}
	
	@RequestMapping(value = "/verCotizaciones")
	public void verCotizaciones(@RequestBody Long idCliente, Model model) {
		model.addAttribute(ReferenciaSeguridad.CLIENTE_TEMP, idCliente);
	}
	
	
	@RequestMapping(value = "/cargaInicialCliente")
	@ResponseBody
	public ClienteTempDto cargaInicial(Model model) {
		if(model.containsAttribute(ReferenciaSeguridad.CLIENTE_TEMP)) {
			Integer idCliente = (Integer) model.asMap().get(ReferenciaSeguridad.CLIENTE_TEMP);
			ClienteTempDto clienteTempDto = new ClienteTempDto();
			clienteTempDto = nuevoClienteBO.obtenerClienteById(idCliente);
			return clienteTempDto;
		}else {
			return new ClienteTempDto();
		}
	}
	
	@RequestMapping(value="/guardar")
	@ResponseBody
	public void guardarClienteNuevo(@RequestBody ClienteTempDto cliente) throws BusinessException {
			try {
				
			if(cliente.getIdTipoPersona().getIdCatGeneral() == null) {
				throw new BusinessException("500","Ingresa");
			}
			
			if(cliente.getRfc() == null) {
				throw new BusinessException("","");
			}
			
			if(cliente.getIdMedioContacto().getCorreoElectronico() == null) {
				throw new BusinessException("","");
			}
				nuevoClienteBO.guardar(cliente);
			}catch(BusinessException be){
				throw new BusinessException(be.getCodigo(), be.getMessage());
	  }	
	}
	
	@RequestMapping(value="/consulta-detalle", method = RequestMethod.GET)
	@ResponseBody
	public ClienteTempDto detalleCliente(@RequestParam int idCliente) throws BusinessException {
		ClienteTempDto clienteTempDto = new ClienteTempDto();
		
			clienteTempDto = nuevoClienteBO.obtenerClienteById(idCliente);
			
			return clienteTempDto;

	}
	
	@RequestMapping(value="/actualiza")
	@ResponseBody
	public void actualizaClienteNuevo(@RequestBody ClienteTempDto cliente) throws BusinessException {
			try {
				
			if(cliente.getIdTipoPersona().getIdCatGeneral() == null) {
				throw new BusinessException("","");
			}
			
			if(cliente.getRfc() == null) {
				throw new BusinessException("","");
			}
			
			if(cliente.getIdMedioContacto().getCorreoElectronico() == null) {
				throw new BusinessException("","");
			}
				nuevoClienteBO.actualizar(cliente);
			}catch(BusinessException be){
				throw new BusinessException(be.getCodigo(), be.getMessage());
	  }	
	}
	
}