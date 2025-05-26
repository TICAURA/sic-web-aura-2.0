package mx.com.consolida.crm.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.crm.dao.interfaz.CelulaClienteDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDao;
import mx.com.consolida.crm.dao.interfaz.ClienteEstatusDao;
import mx.com.consolida.crm.dao.interfaz.ClientePrestadoraServicioDao;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.service.interfaz.ClienteBO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dao.interfaz.ClienteTempEstatusDao;
import mx.com.consolida.entity.CatEstatus;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatGrupo;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.ClienteTempEstatus;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.crm.ClienteEstatus;
import mx.com.consolida.entity.crm.ClienteNominista;
import mx.com.consolida.entity.crm.ClientePrestadoraServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.TipoPersonaEnum;
import mx.com.consolida.generico.UsuarioAplicativo;

@Service
public class ClienteBOImpl implements ClienteBO{
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private ClienteEstatusDao clienteEstatusDao;
	
	@Autowired
	private CatEstatusDao catEstatusDao;
	
	@Autowired
	private ClienteTempEstatusDao clienteTempEstatusDao;
	
	@Autowired
	private ClienteTempDao clienteTempDao;
	
	@Autowired
	private CelulaClienteDao celulaClienteDao;
	
	@Autowired
	private ClientePrestadoraServicioDao clientePrestadoraServicioDao;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteBOImpl.class);
	
	public static final Long FISICA = 21L;
	public static final Long MORAL = 22L;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteDto> listarProspectosAutorizar() {
		
		try {
			
			List<ClienteDto> listaProspectos = clienteDao.listarProspectosAutorizar();
			return listaProspectos;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listarProspectosAutorizar ", e);
			return Collections.emptyList();
		}

	}

	@Override
	@Transactional
	public ClienteDto cambiarEstatusProspecto(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo) {
		try {

			
				Cliente cliente = new Cliente();
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());			
				
				CatGeneral catTipoPersona = new CatGeneral();
				catTipoPersona.setIdCatGeneral(clienteDto.getCatTipoPersona().getIdCatGeneral());
				cliente.setCatTipoPersona(catTipoPersona);
				
				ClienteTemp clienteTemp = new ClienteTemp();
				clienteTemp.setIdClienteTemp(clienteDto.getIdClienteTemp());
				cliente.setClienteTemp(clienteTemp);
				
				CatGrupo catGrupo = new CatGrupo();
				catGrupo.setIdCatGrupo(clienteDto.getCatGrupo().getIdCatGrupo());
				cliente.setCatGrupo(catGrupo);
				
				cliente.setRfc(clienteDto.getRfc()!=null ? clienteDto.getRfc() : null);
				cliente.setRazonSocial(clienteDto.getRazonSocial());
				cliente.setNombre(clienteDto.getNombre());
				cliente.setApellidoPaterno(clienteDto.getApellidoPaterno());
				cliente.setApellidoMaterno(clienteDto.getApellidoMaterno());
				cliente.setNombreComercial(clienteDto.getNombreComercial());
				cliente.setUsuarioAlta(usuario);
				cliente.setFechaAlta(new Date());
				cliente.setEsActivo(Boolean.TRUE);
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				
//				cliente.setIdCatGiroComercial(clienteDto.getCatSubGiroComercialDto().getIdGiroComercial());
//				cliente.setIdSubCatGiroComercial(clienteDto.getCatSubGiroComercialDto().getIdCatSubGiroComercial());
				
				Long idCliente = clienteDao.create(cliente);
				cliente.setIdCliente(idCliente);
				
//				ClientePersonaContacto clientePersonaContacto = new ClientePersonaContacto();
//				clientePersonaContacto.setNombre(clienteDto.getClientePersonaContacto().getNombre());
//				clientePersonaContacto.setApellidoPaterno(clienteDto.getClientePersonaContacto().getApellidoPaterno());
//				clientePersonaContacto.setApellidoMaterno(clienteDto.getClientePersonaContacto().getApellidoMaterno());
//				clientePersonaContacto.setCorreoElectronico(clienteDto.getClientePersonaContacto().getCorreoElectronico());
//				clientePersonaContacto.setTelefono(clienteDto.getClientePersonaContacto().getTelefono());
//				clientePersonaContacto.setCliente(cliente);
//				clientePersonaContacto.setUsuarioAlta(usuario);
//				clientePersonaContacto.setFechaAlta(new Date());
//				clientePersonaContacto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//				clientePersonaContactoDao.create(clientePersonaContacto);
				
//				Domicilio domicilio = new Domicilio();
//				domicilio.setCodigoPostal(clienteDto.getDomicilioDto().getCodigoPostal());
//				domicilio.setCalle(clienteDto.getDomicilioDto().getCalle());
//				domicilio.setNumeroExterior(clienteDto.getDomicilioDto().getNumeroExterior()!=null ? clienteDto.getDomicilioDto().getNumeroExterior() : null);
//				domicilio.setNumeroInterior(clienteDto.getDomicilioDto().getNumeroInterior()!=null ? clienteDto.getDomicilioDto().getNumeroInterior() : null);
//				domicilio.setColonia(clienteDto.getDomicilioDto().getColonia());
//				CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
//				catEntidadFederativa.setIdEntidadFederativa(clienteDto.getDomicilioDto().getIdEntidadFederativa());
//				domicilio.setCatEntidadFederativa(catEntidadFederativa);
//				CatMunicipios catMunicipios = new CatMunicipios();
//				catMunicipios.setIdCatMunicipios(clienteDto.getDomicilioDto().getCatMunicipios().getIdCatGeneral());
//				domicilio.setCatMunicipios(catMunicipios);			
//				domicilio.setUsuarioByUsaurioAlta(usuario);
//				domicilio.setFechaAlta(new Date());
//				domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//				Long idDomicilio = domicilioDao.create(domicilio);
//				
//				ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
//				clienteDomicilio.setCliente(new Cliente(idCliente));
//				clienteDomicilio.setDomicilio(new Domicilio(idDomicilio));
//				clienteDomicilio.setUsuarioByUsuarioAlta(usuario);
//				clienteDomicilio.setFechaAlta(new Date());
//				clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//				clienteDomicilioDao.create(clienteDomicilio);
				
				
				ClienteEstatus clienteEstatus = new ClienteEstatus();
				cliente.setIdCliente(idCliente);
				clienteEstatus.setCliente(cliente);
				clienteEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.PROSPECTO_AUTORIZADO_MESA_CONTROL.getIdEstatus()));
				clienteEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				Usuario usuarioAlta = new Usuario();
				usuarioAlta.setIdUsuario(usuarioAplicativo.getIdUsuario());
				clienteEstatus.setUsuarioAlta(usuarioAlta);
				clienteEstatus.setFechaAlta(new Date());
				clienteEstatusDao.create(clienteEstatus);

				clienteDto.setIdCliente(idCliente);
				return clienteDto;

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiarEstatusProspecto ", e);
			return new ClienteDto();
		}
		
	}
	
	@Override
	@Transactional
	public Boolean declinarProspecto(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo) {
try {
			
		    if(clienteDto!=null && clienteDto.getIdClienteTemp()!=null) {
		    	
//		    	clienteTempEstatusDao.apagarEstatusLogicoByIdCliente(clienteDto.getIdClienteTemp());
		    	
		    	CatEstatus  catEstatus =  catEstatusDao.read(CatEstatusEnum.PROSPECTO_DECLINADO_MSA_CTRL.getIdEstatus());
		    	
				//Se cambia estatus a cliente_temp
				ClienteTempEstatus estatus= new ClienteTempEstatus();
				estatus.setCatEstatus(catEstatus);
				estatus.setClienteTemp(clienteTempDao.read(clienteDto.getIdClienteTemp()));
				estatus.setFechaAlta(new Date());
				estatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				estatus.setUsuarioAlta(usuarioAplicativo.getIdUsuario());
//				clienteTempEstatusDao.create(estatus);
				clienteTempEstatusDao.agregarClienteTempEstatus(estatus);
				
				return Boolean.TRUE;
		    	
		    }else {
		    	LOGGER.error("Ocurrio un error en declinarProspecto, clienteDto no cuenta con IdClienteTemp ");
		    	return Boolean.FALSE;
		    }
			

			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en declinarProspecto ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Long registrarActualizarCliente(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo) {
		try {
			
			
			Cliente cliente = new Cliente();
			ClienteTemp clienteTemp = new ClienteTemp();
			ClienteEstatus clienteEstatus = new ClienteEstatus();
			CelulaCliente celulaCliente = new CelulaCliente();
			ClientePrestadoraServicio clientePrestadora = new ClientePrestadoraServicio();
			Long idCliente = 0L;
			
			if(clienteDto.getIdCliente()!=null) {
				cliente.setIdCliente(clienteDto.getIdCliente());
			}

			if(clienteDto.getIdClienteTemp()!=null) {
				clienteTemp.setIdClienteTemp(clienteDto.getIdClienteTemp());
				cliente.setClienteTemp(clienteTemp);
			}
			
			if(clienteDto.getUsuarioAlta()!=null && clienteDto.getUsuarioAlta().getIdUsuario()!=null) {
				Usuario user = new Usuario();
				user.setIdUsuario(clienteDto.getUsuarioAlta().getIdUsuario());
				cliente.setUsuarioAlta(user);
			}

			
			if(TipoPersonaEnum.FISICA.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
				cliente.setRazonSocial(null);
				cliente.setApellidoPaterno(clienteDto.getApellidoPaterno()!=null ? clienteDto.getApellidoPaterno() : null);
				cliente.setApellidoMaterno(clienteDto.getApellidoMaterno()!=null ?clienteDto.getApellidoMaterno()  : null );
				cliente.setNombre(clienteDto.getNombre()!=null ?clienteDto.getNombre()  : null);
			}else if(TipoPersonaEnum.MORAL.getId_tipoPersona().equals(clienteDto.getCatTipoPersona().getIdCatGeneral())) {
				cliente.setRazonSocial(clienteDto.getRazonSocial()!=null ? clienteDto.getRazonSocial() : null);
				cliente.setApellidoPaterno(null);
				cliente.setApellidoMaterno(null );
				cliente.setNombre(null);
			}
			
			cliente.setRfc(clienteDto.getRfc());
//			cliente.setNombreComercial(clienteDto.getNombreComercial()!=null ? clienteDto.getNombreComercial()  : null);
			cliente.setFechaConstitucionEmpresa(clienteDto.getFechaConstitucionEmpresa()!=null ? clienteDto.getFechaConstitucionEmpresa()   : null);
			cliente.setCveRegistroPatronal(clienteDto.getCveRegistroPatronal()!=null ? clienteDto.getCveRegistroPatronal()  : null);
			cliente.setFechaAlta(clienteDto.getFechaAlta()!=null ? clienteDto.getFechaAlta() : null);
			cliente.setActividadEconomicaFinal(clienteDto.getActividadEconomicaFinal()!=null ? clienteDto.getActividadEconomicaFinal() : null);
			cliente.setEsActivo(true);
			if(clienteDto.getPrefijoSTP() != null) {
				cliente.setPrefijoSTP(clienteDto.getPrefijoSTP());
			}
			
			CatGrupo catGrupo = new CatGrupo();
			catGrupo.setIdCatGrupo(clienteDto.getCatGrupo().getIdCatGrupo()!=null ? clienteDto.getCatGrupo().getIdCatGrupo()  : null);
			cliente.setCatGrupo(catGrupo);
			
			CatGeneral catTipoPersona = new CatGeneral();
			catTipoPersona.setIdCatGeneral(clienteDto.getCatTipoPersona().getIdCatGeneral()!=null ? clienteDto.getCatTipoPersona().getIdCatGeneral()  : null);
			cliente.setCatTipoPersona(catTipoPersona);
			
			CatGeneral catCategoria = new CatGeneral();
			catCategoria.setIdCatGeneral(clienteDto.getCatCategoria().getIdCatGeneral()!=null ? clienteDto.getCatCategoria().getIdCatGeneral()  : null);
			cliente.setCatCategoria(catCategoria);
			
			CatGeneral catRegimenFiscal = new CatGeneral();
			catRegimenFiscal.setIdCatGeneral(clienteDto.getCatRegimenFiscal().getIdCatGeneral()!=null ? clienteDto.getCatRegimenFiscal().getIdCatGeneral()  : null);
			cliente.setCatRegimenFiscal(catRegimenFiscal);
			
			
//			CatGeneral catTipoPago = new CatGeneral();
//			catTipoPago.setIdCatGeneral(clienteDto.getCatTipoPago().getIdCatGeneral());
//			cliente.setCatTipoPago(catTipoPago);
			
//			cliente.setIdCatGiroComercial(clienteDto.getCatGiroComercialDto().getIdCatGeneral());
//			cliente.setIdSubCatGiroComercial(clienteDto.getCatSubGiroComercialDto().getIdCatSubGiroComercial());
			
			Usuario usuarioAlta = new Usuario();
			usuarioAlta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			

			if(cliente.getIdCliente()!=null) {
				
				// ACTUALIZA EN CLIENTE
				cliente.setUsuarioModificacion(usuarioAlta);
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				cliente.setFechaModificacion(new Date());
				clienteDao.update(cliente);
				
				idCliente = cliente.getIdCliente();
				
				// ACTUALIZA O REGISTRA EN CELULA CLIENTE
				if(clienteDto.getIdCelulaCliente()!=null) {
					celulaCliente = celulaClienteDao.read(clienteDto.getIdCelulaCliente());
					celulaCliente.setUsuarioModificacion(usuarioAlta);
					celulaCliente.setFechaModificacion(new Date());
					celulaClienteDao.update(celulaCliente);
				}else {
					celulaCliente.setCelula(new Celula(clienteDto.getCelula().getIdCelula()));
					celulaCliente.setCliente(cliente);
					celulaCliente.setUsuarioAlta(usuarioAlta);
					celulaCliente.setFechaAlta(new Date());
					celulaCliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					celulaClienteDao.create(celulaCliente);
				}

				// ACTUALIZA EN CELULA CLIENT
//				List<ClienteNominista> listNominista = clienteNoministaDao.getListNomistaClienteByCliente(cliente.getIdCliente());
//				
//				if(listNominista!=null && !listNominista.isEmpty()) {
//					clienteNominista = listNominista.get(0);
//					clienteNominista.setUsuarioModificacion(usuarioAlta);
//					clienteNominista.setFechaModificacion(new Date());
//					clienteNominista.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
//					clienteNoministaDao.update(clienteNominista);
//					
//					clienteNominista = new ClienteNominista();
//					clienteNominista.setUsuarioNominista(new Usuario(clienteDto.getNoministaDto().getIdNominista()));
//					clienteNominista.setCliente(cliente);
//					clienteNominista.setUsuarioAlta(usuarioAlta);
//					clienteNominista.setFechaAlta(new Date());
//					clienteNominista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//					clienteNoministaDao.create(clienteNominista);
//					
//				} else {
//					
//					// REGISTRA EN CLIENTE NOMINISTA
//					clienteNominista.setUsuarioNominista(new Usuario(clienteDto.getNoministaDto().getIdNominista()));
//					clienteNominista.setCliente(cliente);
//					clienteNominista.setUsuarioAlta(usuarioAlta);
//					clienteNominista.setFechaAlta(new Date());
//					clienteNominista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//					clienteNoministaDao.create(clienteNominista);
//				}
				
				// ACTUALIZA EN CLIENTE ESTATUS
				cliente.setIdCliente(clienteDto.getIdCliente()!=null ? clienteDto.getIdCliente() : cliente.getIdCliente());
				clienteEstatus.setCliente(cliente);
				clienteEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.PROSPECTO_ACTUALIZADO_POR_MESA_CONTROL.getIdEstatus()));
				clienteEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteEstatus.setUsuarioAlta(usuarioAlta);
				clienteEstatus.setFechaAlta(new Date());
				
				clienteEstatusDao.create(clienteEstatus);
				
			}else {
				
				// REGISTRA EN CLIENTE
				cliente.setUsuarioAlta(usuarioAlta);
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				cliente.setFechaAlta(new Date());
				idCliente = clienteDao.create(cliente);
				cliente.setIdCliente(idCliente);
				
				// REGISTRA EN CELULA CLIENTE
//				celulaCliente.setCelula(new Celula(clienteDto.getCelula().getIdCelula()));
//				celulaCliente.setCliente(cliente);
//				celulaCliente.setUsuarioAlta(usuarioAlta);
//				celulaCliente.setFechaAlta(new Date());
//				celulaCliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//				celulaClienteDao.create(celulaCliente);
				
				
				// REGISTRA EN CLIENTE NOMINISTA
//				clienteNominista.setUsuarioNominista(new Usuario(clienteDto.getNoministaDto().getIdNominista()));
//				clienteNominista.setCliente(cliente);
//				clienteNominista.setUsuarioAlta(usuarioAlta);
//				clienteNominista.setFechaAlta(new Date());
//				clienteNominista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//				clienteNoministaDao.create(clienteNominista);
				
				// REGISTRA EN CLIENTE ESTATUS
				cliente.setIdCliente(idCliente);
				clienteEstatus.setCliente(cliente);
				clienteEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.PROSPECTO_CREADO_POR_MESA_CONTROL.getIdEstatus()));
				clienteEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteEstatus.setUsuarioAlta(usuarioAlta);
				clienteEstatus.setFechaAlta(new Date());
				clienteEstatusDao.create(clienteEstatus);
				
				celulaCliente.setCelula(new Celula(clienteDto.getCelula().getIdCelula()));
				celulaCliente.setCliente(cliente);
				celulaCliente.setUsuarioAlta(usuarioAlta);
				celulaCliente.setFechaAlta(new Date());
				celulaCliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				celulaClienteDao.create(celulaCliente);
				
				clientePrestadora.setCliente(cliente);
				clientePrestadora.setPrestadoraServicio(null);
				clientePrestadora.setPrestadoraServicioFondo(new PrestadoraServicio(clienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio()));
				clientePrestadora.setUsuarioAlta(usuarioAlta);
				clientePrestadora.setFechaAlta(new Date());
				clientePrestadora.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clientePrestadoraServicioDao.create(clientePrestadora);

			}			

		
			return idCliente;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en registrarActualizarCliente ", e);
			return 0L;
		}
		
	}
		
	@Override
	@Transactional(readOnly = true)
	public List<ClienteDto> listaProspectosAutorizadosByMesaControl(Long idCelula) {
		
		try {
			
			List<ClienteDto> listaProspectos = clienteDao.listaProspectosAutorizadosByMesaControl(idCelula);
			return listaProspectos;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaProspectosAutorizadosByMesaControl ", e);
			return Collections.emptyList();
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Long getIdCLienteByRfc(String rfc) {
		try {

			if(clienteDao.getIdClienteByRfc(rfc)!=null) {
				Long id = clienteDao.getIdClienteByRfc(rfc);
				return id;
			}else {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaProspectosAutorizadosByMesaControl ", e);
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Boolean existeClienteEnCelula(Long idCelula, String rfc) {
		try {

			CelulaCliente cc = clienteDao.getCelulaByIdCelulaRfc(idCelula, rfc);
			
			if(cc != null && cc.getIdCelulaCliente()!=null) {
				return true;
			}
			
			return false;
			
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en existeClienteEncelula ", e);
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteDto getDatosGeneralesClienteBiIdCliente(Long idCliente) {
		try {

			if(idCliente!=null) {
				return clienteDao.getDatosGeneralesClienteBiIdCliente(idCliente);
			}else {
				LOGGER.error("Ocurrio un error en getDatosGeneralesClienteBiIdCliente, idCliente viene null ");
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en getDatosGeneralesClienteBiIdCliente ", e);
			return null;
		}
	}

	@Override
	@Transactional
	public Boolean eliminarCliente(Long idCliente, Long idUsuarioAplicativo) {
		
		try {
			Cliente cliente = new Cliente();
			
			if(idCliente!=null) {
				cliente = clienteDao.read(idCliente);
				cliente.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				cliente.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				cliente.setFechaModificacion(new Date());
				clienteDao.update(cliente);
				
				CelulaCliente celulaCliente = celulaClienteDao.getAllCelulaByIdCliente(idCliente);
				celulaCliente.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				celulaCliente.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				celulaCliente.setFechaModificacion(new Date());
				celulaClienteDao.update(celulaCliente);
				
				
				return Boolean.TRUE;
			}else {
				LOGGER.error("Ocurrio un error en eliminarCliente, idCliente viene null");
				return Boolean.FALSE;
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarCliente ", e);
			return Boolean.FALSE;
		}
		
		
	}
}
