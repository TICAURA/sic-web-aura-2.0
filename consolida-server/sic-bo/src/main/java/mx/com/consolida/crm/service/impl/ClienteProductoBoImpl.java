package mx.com.consolida.crm.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.ClienteProductoGralDao;
import mx.com.consolida.crm.dto.ClienteProductoDto;
import mx.com.consolida.crm.service.interfaz.ClienteProductoBo;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.crm.ClienteProducto;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.UsuarioAplicativo;

@Service
public class  ClienteProductoBoImpl implements ClienteProductoBo {
	
	@Autowired
	private ClienteProductoGralDao productoDao;

	@Override
	public List<ClienteProductoDto> getProductosByIdCliente(Long idCliente) {
		return  productoDao.getProductosByidCliente(idCliente);
	}

	
	@Override
	@Transactional
	public void guardarProducto(ClienteProductoDto producto, UsuarioAplicativo usuarioAplicativo) {
		ClienteProducto prodcto = new ClienteProducto();
		Usuario usuario = new Usuario();
		usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		Cliente cliente = new Cliente();
		cliente.setIdCliente(producto.getIdCliente());
		prodcto.setCliente(cliente);
		CatGeneral prod = new CatGeneral();
		prod.setIdCatGeneral(producto.getIdProducto());
		prodcto.setProducto(prod);	
		prodcto.setUsuarioAlta(usuario);
		prodcto.setIndEstatus("1");
		prodcto.setFechaAlta(new Date());
		if (producto.getIdClienteProducto()==0 ) {
		
		
		productoDao.create(prodcto);
	
	}else {
		productoDao.delete(producto.getIdClienteProducto());
	}
	
	}

}
