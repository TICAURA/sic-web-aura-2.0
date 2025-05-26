package mx.com.consolida.controller.cotizador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.TotalesClienteTempDto;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.ClienteTempBO;

@Controller
@RequestMapping("seguimiento")
@SessionAttributes(value={ReferenciaSeguridad.USUARIO_APLICATIVO, ReferenciaSeguridad.CLIENTE_TEMP })
public class SeguimientoProspectosController {

	@Autowired
	private ClienteTempBO clienteBO;
	
	@RequestMapping(value = "/obtenerContadoresTotales")
	@ResponseBody
	public TotalesClienteTempDto obtenerContadoresTotales(Model model) {
		TotalesClienteTempDto clienteTempDto = new TotalesClienteTempDto();
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		clienteTempDto = clienteBO.obtenerContadoresTotales(usuarioAplicativo);
		return clienteTempDto;
	}
	
	@RequestMapping(value = "/busquedaContadorXEstatus")
	@ResponseBody
	public List<ClienteTempDto> busquedaContadorXEstatus(@RequestBody Long idEstatus, Model model) {
		List<ClienteTempDto> clienteTempDto = new ArrayList<ClienteTempDto>(); 
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		clienteTempDto = clienteBO.obtenerContadorSeguimientoXEstatus(idEstatus, usuarioAplicativo);
		
		return clienteTempDto;
		}
	
	
	@RequestMapping(value = "/obtenerRegistrosContadorPrincipal")
	@ResponseBody
	public List<ClienteTempDto> obtenerRegistrosContadorPrincipal(Model model) {
		List<ClienteTempDto> clienteTempDto = new ArrayList<ClienteTempDto>();
		UsuarioAplicativo usuarioAplicativo = new UsuarioAplicativo();
		if(model.containsAttribute(ReferenciaSeguridad.USUARIO_APLICATIVO)) {
			usuarioAplicativo =  (UsuarioAplicativo) model.asMap().get(ReferenciaSeguridad.USUARIO_APLICATIVO);
		}
		clienteTempDto = clienteBO.obtenerRegistrosContadorPrincipal(usuarioAplicativo);
		
		return clienteTempDto;
		}
		
}