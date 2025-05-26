package mx.com.consolida.service.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import mx.com.consolida.crm.dto.CuentaBancariaDto;
import mx.com.consolida.crm.service.interfaz.DomicilioBO;
import mx.com.consolida.dao.admin.ComisionistaCuentaBancariaDAO;
import mx.com.consolida.dao.admin.ComisionistaDAO;
import mx.com.consolida.dto.ComisionistaDto;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.Comisionista;
import mx.com.consolida.entity.ComisionistaCuentaBancaria;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.IndEstatusEnum;

@Service
public class ComisionistaBOImpl implements ComisionistaBO {

	@Autowired
	private ComisionistaDAO comisionistaDAO;	

	@Autowired
	private DomicilioBO domicilioBO;
	
	@Autowired
	private ComisionistaCuentaBancariaDAO comisionistaCuentaBancariaDAO;
	

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	
	@Override
	@Transactional
	public void guardarComisionista(ComisionistaDto comisionistaDTO, Long idUsuarioAutenticado) {
		
	
		Long idDomicilio = domicilioBO.guardarDomicilio(comisionistaDTO.getDomicilio(), idUsuarioAutenticado);
		Usuario usuarioAlta = new Usuario();
		usuarioAlta.setIdUsuario(idUsuarioAutenticado);
			 
		 Comisionista comision = new Comisionista();
		 if(comisionistaDTO.getIdComisionista() != null && comisionistaDTO.getIdComisionista()>0) {
			 comision = comisionistaDAO.read(comisionistaDTO.getIdComisionista());
		 }
		 
		 Domicilio domicilio = new Domicilio();
		 domicilio.setIdDomicilio(idDomicilio);
		 comision.setIdDomicilio(domicilio);

		 
		 CatGeneral tipoCanalVenta = new CatGeneral();
		 tipoCanalVenta.setIdCatGeneral(comisionistaDTO.getTipoCanalVenta().getIdCatGeneral());
		 comision.setIdTipoCanalVenta(tipoCanalVenta);
		 Usuario usuario = new Usuario();
		
		 
		 comision.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	
		 comision.setRazonSocial(comisionistaDTO.getRazonSocial());
		 
		 
		 if(comisionistaDTO.getIdComisionista() != null && comisionistaDTO.getIdComisionista()>0) {
			 comision.setUsuarioModificacion(usuarioAlta);
			 comision.setFechaModificaicon(new Date());
		 }else {
			 comision.setUsuarioAlta(usuarioAlta);
			 comision.setFechaAlta(new Date());
		 }
		 
		 comisionistaDAO.createOrUpdate(comision);
		 	 
		CuentaBancariaDto cuentaBancariaDto = comisionistaDTO.getCuentaBancaria();
			
		ComisionistaCuentaBancaria  comiCuentaBancaria= new ComisionistaCuentaBancaria();
		
		
		if(cuentaBancariaDto.getIdComisionistaCuentaBancaria()!= null && cuentaBancariaDto.getIdComisionistaCuentaBancaria() > 0) {
			comiCuentaBancaria = comisionistaCuentaBancariaDAO.read(cuentaBancariaDto.getIdComisionistaCuentaBancaria());
		}
		
		CatGeneral banco = new CatGeneral();
		banco.setIdCatGeneral(cuentaBancariaDto.getCatBanco().getIdCatGeneral());

		comiCuentaBancaria.setBanco(banco);
		comiCuentaBancaria.setNumeroCuenta(cuentaBancariaDto.getNumeroCuenta());
		comiCuentaBancaria.setClabeInterbancaria(cuentaBancariaDto.getClabeInterbancaria());
		comiCuentaBancaria.setComisionista(comision);
		// 55 Recepcion de depositos
		CatGeneral catTipoCuenta  = new CatGeneral();
		catTipoCuenta.setIdCatGeneral(55L);
		comiCuentaBancaria.setTipoCuenta(catTipoCuenta);
		comiCuentaBancaria.setIndEstatus(1L);
		if(cuentaBancariaDto.getIdCanalVentaCuentaBancaria() != null && cuentaBancariaDto.getIdCanalVentaCuentaBancaria() > 0) {
			
			comiCuentaBancaria.setUsuarioModificacion(usuarioAlta);
			comiCuentaBancaria.setFechaModificacion(new Date());
			
		}else {
			comiCuentaBancaria.setUsuarioAlta(usuarioAlta);
			comiCuentaBancaria.setFechaAlta(new Date());
			
		}
		
		comisionistaCuentaBancariaDAO.createOrUpdate(comiCuentaBancaria);
		
		 Long idCanalCuentaBancaria = comiCuentaBancaria.getIdComisionistaCuentaBancaria();
		
	}
	
	
	@Transactional
	public List<ComisionistaDto> listarTodosComisionistas() {
		return comisionistaDAO.listarAllComisionistas();

	}

	
	@Transactional
	public ComisionistaDto obtenerComisionistaByIdComisionista(Long idComisionista) {
		return comisionistaDAO.obtenerComisionistaByIdComisionista(idComisionista);
	}

	@Override
	public Long asignacionClienteComisiones(ComisionistaDto comisionistaDto, Long idUsuarioAutenticado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
