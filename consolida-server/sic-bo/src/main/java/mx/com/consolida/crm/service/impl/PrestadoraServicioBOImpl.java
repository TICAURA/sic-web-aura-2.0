package mx.com.consolida.crm.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.AccionistaDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dao.interfaz.CatGeneralDao;
import mx.com.consolida.crm.dao.interfaz.CelulaPrestadoraServicioDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDomicilioDao;
import mx.com.consolida.crm.dao.interfaz.ClientePersonaContactoDao;
import mx.com.consolida.crm.dao.interfaz.DomicilioDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioAccionistaDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioAccionistaDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioAccionistaDomicilioDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioApoderadoLegalDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioApoderadoLegalDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioClaseFraccionPrimaDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioCuentaBancariaDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioDoctoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioDomicilioDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioEstatusDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioGiroComercialDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioImssDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioObjetoSocialDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioProductoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioRegistroPatronalDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioRepresentanteLegalDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioRepresentanteLegalDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioStpDao;
import mx.com.consolida.crm.dto.CatProductoDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.FichaTecnicaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioActividadDto;
import mx.com.consolida.crm.dto.PrestadoraServicioClaseFraccionPrimaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioCuentaBancariaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDocumentoDto;
import mx.com.consolida.crm.dto.PrestadoraServicioDto;
import mx.com.consolida.crm.dto.PrestadoraServicioImssDto;
import mx.com.consolida.crm.dto.PrestadoraServicioObjetoSocialDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.crm.dto.PrestadoraServicioRegistroPatronalDto;
import mx.com.consolida.crm.dto.PrestadoraServicioStpDto;
import mx.com.consolida.crm.service.interfaz.CelulaBO;
import mx.com.consolida.crm.service.interfaz.PrestadoraServicioBO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.usuario.PersonaDAO;
import mx.com.consolida.dao.usuario.UsuarioDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.CatEstatus;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatSubGiroComercial;
import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaPrestadoraServicio;
import mx.com.consolida.entity.crm.CatEntidadFederativa;
import mx.com.consolida.entity.crm.CatMunicipios;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.crm.ClienteDomicilio;
import mx.com.consolida.entity.crm.ClientePersonaContacto;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionista;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionistaDocumento;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioAccionistaDomicilio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegal;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioApoderadoLegalDocumento;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioClaseFraccionPrima;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioCuentaBancaria;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDocto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDomicilio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioEstatus;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioGiroComercial;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioImss;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioObjetoSocial;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRegistroPatronal;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegal;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioRepresentanteLegalDocumento;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioStp;
import mx.com.consolida.entity.seguridad.Persona;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.CatGeneralEnum;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;
import mx.com.consolida.usuario.UsuarioDTO;

@Service
public class PrestadoraServicioBOImpl implements PrestadoraServicioBO{
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Autowired
	private DomicilioDao domicilioDao;
	
	@Autowired
	private ClienteDomicilioDao clienteDomicilioDao;

	@Autowired
	private ClientePersonaContactoDao clientePersonaContactoDao;
	
	@Autowired
	private PrestadoraServicioDao prestadoraServicioDao;
	
	@Autowired
	private CatalogoBO catBo;
	
	@Autowired
	private CelulaBO celulaBO;
	
	@Autowired
	private PrestadoraServicioDomicilioDao prestadoraServicioDomicilioDao;
	
	@Autowired
	private CelulaPrestadoraServicioDao celulaPrestadoraServicioDao;
	
	@Autowired
	private	PrestadoraServicioCuentaBancariaDao prestadoraServicioCuentaBancariaDao;
	
	@Autowired
	private PrestadoraServicioProductoDao prestadoraServicioProductoDao;
	
	@Autowired
	private PrestadoraServicioGiroComercialDao prestadoraServicioGiroComercialDao;
	
	@Autowired
	private PrestadoraServicioImssDao prestadoraServicioImssDao;
	
	@Autowired
	private PrestadoraServicioRegistroPatronalDao prestadoraServicioRegistroPatronalDao;
	
	@Autowired
	private PrestadoraServicioClaseFraccionPrimaDao prestadoraServicioClaseFraccionPrimaDao;
	
	@Autowired
	private PrestadoraServicioObjetoSocialDao prestadoraServicioObjetoSocialDao;
	
	@Autowired
	private PersonaDAO personaDao;
	
	@Autowired
	private PrestadoraServicioRepresentanteLegalDao prestadoraServicioRepresentanteLegalDao;
	
	@Autowired
	private PrestadoraServicioApoderadoLegalDao prestadoraServicioApoderadoLegalDao;
	
	@Autowired
	private PrestadoraServicioAccionistaDao prestadoraServicioAccionistaDao;
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;
	
	@Autowired
	private PrestadoraServicioAccionistaDomicilioDao prestadoraServicioAccionistaDomicilioDao;
	
	@Autowired
	private PrestadoraServicioDoctoDao prestadoraServicioDoctoDao;
	
	@Autowired
	private PrestadoraServicioRepresentanteLegalDocumentoDao prestadoraServicioRepresentanteLegalDocumentoDao;
	
	@Autowired
	private PrestadoraServicioApoderadoLegalDocumentoDao prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao;
	
	@Autowired
	private PrestadoraServicioAccionistaDocumentoDao prestadoraServicioAccionistaDocumentoDao;
	
	@Autowired
	private PrestadoraServicioEstatusDao prestadoraServicioEstatusDao;
	
	@Autowired
	private CatEstatusDao catEstatusDao;
	
	@Autowired
	private PrestadoraServicioStpDao prestadoraServicioStpDao;
	
	@Autowired
	private CatGeneralDao catGeneralDao;
	
	@Autowired
	private UsuarioDAO usuarioDao;

	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrestadoraServicioBOImpl.class);

	
	@Transactional
	public Boolean cambiarEstatusProspecto(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo) {
		try {
			
			ModelMapper mapper = new ModelMapper();
			
			Cliente cliente = new Cliente();
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
			
			cliente = mapper.map(clienteDto, Cliente.class);
			cliente.setUsuarioAlta(usuario);
			cliente.setFechaAlta(new Date());
			cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			Long idCliente = clienteDao.create(cliente);
			
			Domicilio domicilio = new Domicilio();
			domicilio.setCodigoPostal(clienteDto.getDomicilioDto().getCodigoPostal());
			domicilio.setCalle(clienteDto.getDomicilioDto().getCalle());
			domicilio.setNumeroExterior(clienteDto.getDomicilioDto().getNumeroExterior());
			domicilio.setNumeroInterior(clienteDto.getDomicilioDto().getNumeroInterior());
			domicilio.setColonia(clienteDto.getDomicilioDto().getColonia());
			CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
			catEntidadFederativa.setIdEntidadFederativa(clienteDto.getDomicilioDto().getIdEntidadFederativa());
			domicilio.setCatEntidadFederativa(catEntidadFederativa);
			CatMunicipios catMunicipios = new CatMunicipios();
			catMunicipios.setIdCatMunicipios(clienteDto.getDomicilioDto().getCatMunicipios().getIdCatGeneral());
			domicilio.setCatMunicipios(catMunicipios);			
			domicilio.setUsuarioByUsaurioAlta(usuario);
			domicilio.setFechaAlta(new Date());
			domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			Long idDomicilio = domicilioDao.create(domicilio);
			
			ClienteDomicilio clienteDomicilio = new ClienteDomicilio();
			cliente = new Cliente();
			cliente.setIdCliente(idCliente);
			clienteDomicilio.setCliente(cliente);
			domicilio = new Domicilio();
			domicilio.setIdDomicilio(idDomicilio);
			clienteDomicilio.setDomicilio(domicilio);
			clienteDomicilio.setUsuarioByUsuarioAlta(usuario);
			clienteDomicilio.setFechaAlta(new Date());
			clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			clienteDomicilioDao.create(clienteDomicilio);
			
			ClientePersonaContacto clientePersonaContacto = new ClientePersonaContacto();
			clientePersonaContacto.setNombre(clienteDto.getClientePersonaContacto().getNombre());
			clientePersonaContacto.setApellidoPaterno(clienteDto.getClientePersonaContacto().getApellidoPaterno());
			clientePersonaContacto.setApellidoMaterno(clienteDto.getClientePersonaContacto().getApellidoMaterno());
			clientePersonaContacto.setCorreoElectronico(clienteDto.getClientePersonaContacto().getCorreoElectronico());
			clientePersonaContacto.setTelefono(clienteDto.getClientePersonaContacto().getTelefono());
			cliente = new Cliente();
			cliente.setIdCliente(idCliente);
			clientePersonaContacto.setCliente(cliente);
			clientePersonaContacto.setUsuarioAlta(usuario);
			domicilio.setFechaAlta(new Date());
			clientePersonaContacto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			clientePersonaContactoDao.create(clientePersonaContacto);

			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en cambiarEstatusProspecto ", e);
			return Boolean.FALSE;
		}
		
	}

	@Override
	public List<PrestadoraServicioDto> obtenerPrestadorasServicio(Long idCelula) {
		return prestadoraServicioDao.obtenerPrestadorasServicio(idCelula);
	}

	@Override
	public PrestadoraServicioDto obtenerCatalogosDatosGenerales(PrestadoraServicioDto prestadoraServicioDto) {
		prestadoraServicioDto.setCatCelula(celulaBO.listarTodasLasCelulas());
		return prestadoraServicioDto;
	}

	@Override
	public PrestadoraServicioDto obtenerCatalogosProductos(PrestadoraServicioDto prestadoraServicioDto) {
			prestadoraServicioDto.setCatProductoDto(catBo.obtenerCatProducto());
		return prestadoraServicioDto;
	}

	@Override
	public PrestadoraServicioDto obtenerCatalogosDomicilio(PrestadoraServicioDto prestadoraServicioDto) {
	
		prestadoraServicioDto.setEntidadFederativa(catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		if(prestadoraServicioDto != null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto() != null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio() != null &&
				prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa() != null) {
			prestadoraServicioDto.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa().toString()));
		}
		return prestadoraServicioDto;
	}
	
	@Override
	public PrestadoraServicioDto obtenerCatalogosCuentaBancaria(PrestadoraServicioDto prestadoraServicioDto) {
		
		prestadoraServicioDto.setCatTipoCuenta(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_CUENTA_BANCARIA.getCve()));
		prestadoraServicioDto.setCatBanco(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_BANCOS.getCve()));
		
		return prestadoraServicioDto;
	}
	
	
	@Override
	public PrestadoraServicioDto obtenerCatalogos(PrestadoraServicioDto prestadoraServicioDto) {
		if(prestadoraServicioDto.getIdPrestadoraServicio() != null) {
			prestadoraServicioDto.setCatProductoDto(catBo.obtenerCatProducto());
		}
		
		prestadoraServicioDto.setEntidadFederativa(catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		prestadoraServicioDto.setCatTipoCuenta(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_CUENTA_BANCARIA.getCve()));
		prestadoraServicioDto.setCatBanco(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.BANCO_STP.getCve()));
		prestadoraServicioDto.setCatCelula(celulaBO.listarTodasLasCelulas());
		if(prestadoraServicioDto != null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto() != null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio() != null &&
				prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa() != null) {
			prestadoraServicioDto.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa().toString()));
		}
		return prestadoraServicioDto;
	}

	@Override
	@Transactional
	public PrestadoraServicioDto guardarPrestadoraServicio(PrestadoraServicioDto prestadoraServicioDto,
			UsuarioAplicativo usuarioAplicativo) {
		try {
		Usuario usuario =  new Usuario();
		usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		byte[] archivoLogotipo = null;
		boolean guardaBitacora = true;
		
		PrestadoraServicio entity = new PrestadoraServicio();
		
		//Guarda PrestadoraServicio
		if (prestadoraServicioDto.getIdPrestadoraServicio() != null) {
				entity = prestadoraServicioDao.read(prestadoraServicioDto.getIdPrestadoraServicio());
				guardaBitacora = false;
			if(Boolean.TRUE.equals(prestadoraServicioDto.getEsFondo())) {
				entity.setEsFondo(1);
			}else {
				entity.setEsFondo(0);
			}
			
			if(prestadoraServicioDto.getArchivoLogotipo()!=null && prestadoraServicioDto.getArchivoLogotipo().containsKey("archivo")) {
				String bytesArchivoLogotipo = prestadoraServicioDto.getArchivoLogotipo().get("archivo").toString();
				archivoLogotipo = bytesArchivoLogotipo.getBytes();
				
				entity.setNombreArchivoLogotipo(prestadoraServicioDto.getArchivoLogotipo().get("nombreArchivoLogotipo").toString());
				entity.setLogo(archivoLogotipo);
			}
			
//			entity.setClave(prestadoraServicioDto.getClave());
			entity.setRfc(prestadoraServicioDto.getRfc());
			entity.setNombreCorto(prestadoraServicioDto.getNombreCorto());
			entity.setRazonSocial(prestadoraServicioDto.getRazonSocial());
			entity.setId_consar(prestadoraServicioDto.getIdConsar());
			entity.setCelulaPrestadoraServicio(new CelulaPrestadoraServicio());
			entity.getCelulaPrestadoraServicio().setCelula(new Celula());
			entity.getCelulaPrestadoraServicio().setIdCelulaPrestadoraServicio(prestadoraServicioDto.getIdPrestadoraServicio());
			entity.getCelulaPrestadoraServicio().getCelula().setClave(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getClave());
			entity.getCelulaPrestadoraServicio().getCelula().setIdCelula(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula());
			entity.getCelulaPrestadoraServicio().getCelula().setNombre(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getNombre());
			
			
			entity.setFechaModificacion(new Date());
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioModificacion(usuario);
		}else {
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			entity.setUsuarioAlta(usuario);
			entity.setFechaAlta(new Date());
			
			if(Boolean.TRUE.equals(prestadoraServicioDto.getEsFondo())) {
				entity.setEsFondo(1);
			}else {
				entity.setEsFondo(0);
			}
			
			if(prestadoraServicioDto.getArchivoLogotipo().containsKey("archivo")) {
				String bytesArchivoLogotipo = prestadoraServicioDto.getArchivoLogotipo().get("archivo").toString();
				archivoLogotipo = bytesArchivoLogotipo.getBytes();
				
				entity.setNombreArchivoLogotipo(prestadoraServicioDto.getArchivoLogotipo().get("nombreArchivoLogotipo").toString());
				entity.setLogo(archivoLogotipo);
			}
			
//				entity.setClave(prestadoraServicioDto.getClave());
			entity.setRfc(prestadoraServicioDto.getRfc());
			entity.setNombreCorto(prestadoraServicioDto.getNombreCorto());
			entity.setRazonSocial(prestadoraServicioDto.getRazonSocial());
			entity.setId_consar(prestadoraServicioDto.getIdConsar());
			
			entity.setCelulaPrestadoraServicio(new CelulaPrestadoraServicio());
			entity.getCelulaPrestadoraServicio().setCelula(new Celula());
			entity.getCelulaPrestadoraServicio().setIdCelulaPrestadoraServicio(prestadoraServicioDto.getIdPrestadoraServicio());
			entity.getCelulaPrestadoraServicio().getCelula().setClave(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getClave());
			entity.getCelulaPrestadoraServicio().getCelula().setIdCelula(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula());
			entity.getCelulaPrestadoraServicio().getCelula().setNombre(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getNombre());
		}

		prestadoraServicioDao.createOrUpdate(entity);
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		
		if(guardaBitacora) {
			PrestadoraServicioEstatus prestadoraServicioEstatus = new PrestadoraServicioEstatus();
			prestadoraServicioEstatus.setPrestadoraServicio(new PrestadoraServicio(prestadoraServicioDto.getIdPrestadoraServicio()));
			prestadoraServicioEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.PRESTADORA_SERVICIO_CREADA.getIdEstatus()));
			prestadoraServicioEstatus.setUsuarioAlta(usuario);
			prestadoraServicioEstatus.setFechaAlta(new Date());
			prestadoraServicioEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			prestadoraServicioEstatusDao.create(prestadoraServicioEstatus);
		}
		

		
		//Guarda Celula
		if(entity.getCelulaPrestadoraServicio() != null &&  entity.getCelulaPrestadoraServicio().getCelula() != null
			&&	entity.getCelulaPrestadoraServicio().getCelula().getIdCelula() != null) {
			CelulaPrestadoraServicio celula = new CelulaPrestadoraServicio();
			
			if(entity.getCelulaPrestadoraServicio().getIdCelulaPrestadoraServicio() == null) {
			
			celula.setCelula(entity.getCelulaPrestadoraServicio().getCelula());
			celula.setFechaAlta(new Date());
			celula.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			celula.setUsuarioAlta(usuario);
			celula.setPrestadoraServicio(entity);
			celulaPrestadoraServicioDao.create(celula);
			}else {
				celula = celulaPrestadoraServicioDao.read(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getIdCelulaPrestadoraServicio());
				celula.setCelula(new Celula());
				celula.getCelula().setClave(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getClave());
				celula.getCelula().setIdCelula(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getIdCelula());
				celula.getCelula().setNombre(prestadoraServicioDto.getCelulaPrestadoraServicioDto().getCelula().getNombre());
				celula.setFechaAlta(new Date());
				celula.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				celula.setFechaModificacion(new Date());
				celula.setUsuarioModificacion(usuario);
				celula.setPrestadoraServicio(new PrestadoraServicio());
				celula.getPrestadoraServicio().setIdPrestadoraServicio(prestadoraServicioDto.getIdPrestadoraServicio());
				celulaPrestadoraServicioDao.createOrUpdate(celula);
			}
			
		}
		//Guarda Domicilio
				if(prestadoraServicioDto != null && prestadoraServicioDto.getPrestadoraServicioDomicilioDto() != null && 
						prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio() != null) {
					
				if(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdDomicilio() == null) {
					entity.setPrestadoraServicioDomicilio(new PrestadoraServicioDomicilio());
					entity.getPrestadoraServicioDomicilio().setDomicilio(new Domicilio());
										
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCalle(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCalle());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatEntidadFederativa(new CatEntidadFederativa());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatEntidadFederativa().setIdEntidadFederativa(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatMunicipios(new CatMunicipios());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatMunicipios().setIdCatMunicipios(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCatMunicipios().getIdCatGeneral());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatTipoDomicilio(new CatGeneral());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatTipoDomicilio().setIdCatGeneral(34L);
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCodigoPostal(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setColonia(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getColonia());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setNumeroExterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroExterior());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setNumeroInterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroInterior());
					
					entity.getPrestadoraServicioDomicilio().getDomicilio().setFechaAlta(new Date());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setUsuarioByUsaurioAlta(usuario);
					entity.getPrestadoraServicioDomicilio().getDomicilio().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					
					entity.getPrestadoraServicioDomicilio().setFechaAlta(new Date());
					entity.getPrestadoraServicioDomicilio().setUsuarioByUsuarioAlta(usuario);
					entity.getPrestadoraServicioDomicilio().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					
				}else {
					entity.setPrestadoraServicioDomicilio(prestadoraServicioDomicilioDao.read(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getIdPrestadoraServicioDomicilio()));
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCalle(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCalle());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatEntidadFederativa(new CatEntidadFederativa());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatEntidadFederativa().setIdEntidadFederativa(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getIdEntidadFederativa());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatMunicipios(new CatMunicipios());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatMunicipios().setIdCatMunicipios(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCatMunicipios().getIdCatGeneral());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCatTipoDomicilio(new CatGeneral());
					entity.getPrestadoraServicioDomicilio().getDomicilio().getCatTipoDomicilio().setIdCatGeneral(34L);
					entity.getPrestadoraServicioDomicilio().getDomicilio().setCodigoPostal(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getCodigoPostal());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setColonia(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getColonia());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setNumeroExterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroExterior());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setNumeroInterior(prestadoraServicioDto.getPrestadoraServicioDomicilioDto().getDomicilio().getNumeroInterior());

					
					entity.getPrestadoraServicioDomicilio().getDomicilio().setFechaModificacion(new Date());
					entity.getPrestadoraServicioDomicilio().getDomicilio().setUsuarioByUsuarioModificacion(usuario);
					entity.getPrestadoraServicioDomicilio().getDomicilio().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					
					if(entity.getPrestadoraServicioDomicilio().getDomicilio().getFechaAlta() == null) {
						entity.getPrestadoraServicioDomicilio().getDomicilio().setFechaAlta(new Date());
					}
					if(entity.getPrestadoraServicioDomicilio().getDomicilio().getUsuarioByUsaurioAlta() == null) {
						entity.getPrestadoraServicioDomicilio().getDomicilio().setUsuarioByUsaurioAlta(usuario);
					}
					
					entity.getPrestadoraServicioDomicilio().setFechaModificacion(new Date());
					entity.getPrestadoraServicioDomicilio().setUsuarioByUsuarioModificacion(usuario);
					entity.getPrestadoraServicioDomicilio().setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					if(entity.getPrestadoraServicioDomicilio().getFechaAlta() == null) {
						entity.getPrestadoraServicioDomicilio().setFechaAlta(new Date());
					}
					if(entity.getPrestadoraServicioDomicilio().getUsuarioByUsuarioAlta() == null) {
						entity.getPrestadoraServicioDomicilio().setUsuarioByUsuarioAlta(usuario);
					}
				}
				entity.getPrestadoraServicioDomicilio().setIdPrestadoraServicio(entity);
				domicilioDao.createOrUpdate(entity.getPrestadoraServicioDomicilio().getDomicilio());
				prestadoraServicioDomicilioDao.createOrUpdate(entity.getPrestadoraServicioDomicilio());
				}
				
		
		
//			prestadoraServicioDto = mapper.map(entity, PrestadoraServicioDto.class);
		}catch(Exception e) {
			LOGGER.error("Ocurrio un error en guardarPrestadoraServicio ", e);
			LOGGER.error(e.getMessage());
		}
		return prestadoraServicioDto;
		
	}

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByIdDatosGenerales(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		prestadoraServicioDto.setIdConsar(entity.getId_consar());
//		prestadoraServicioDto.setClave(entity.getClave());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		prestadoraServicioDto.setNombreArchivoLogotipo(entity.getNombreArchivoLogotipo());
		
		
		if(entity.getCelulaPrestadoraServicio() != null) {
			prestadoraServicioDto.setCelulaPrestadoraServicioDto(celulaPrestadoraServicioDao.convertirCelulaPrestadoraServicioADto(entity.getCelulaPrestadoraServicio()));
		}
		
		return prestadoraServicioDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByIdProductos(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		
		
		if(!entity.getPrestadoraServicioProducto().isEmpty()) {
			prestadoraServicioDto.setPrestadoraServicioProducto(prestadoraServicioProductoDao.convertirPrestadoraServicioProductoADto(entity.getPrestadoraServicioProducto()));
		}
		
						
		return prestadoraServicioDto;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByIdFiel(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		prestadoraServicioDto.setPasswordCsd(entity.getPasswordCsd());
		prestadoraServicioDto.setPasswordFiel(entity.getPasswordFiel());
		
		
		if(entity.getCelulaPrestadoraServicio() != null) {
			prestadoraServicioDto.setCelulaPrestadoraServicioDto(celulaPrestadoraServicioDao.convertirCelulaPrestadoraServicioADto(entity.getCelulaPrestadoraServicio()));
		}
		
	    PrestadoraServicioDocumentoDto prestadoraDocumento = new PrestadoraServicioDocumentoDto();
	    if(entity.getPasswordCsd() != null) {
		byte[] decodedBytesCsd = Base64.getDecoder().decode(entity.getPasswordCsd());
		String passCsd = new String(decodedBytesCsd);
	    prestadoraDocumento.setPasswordCsd(passCsd);
	    prestadoraServicioDto.setPasswordCsd(passCsd);
	    }
	    if(entity.getPasswordFiel() != null) {
	    byte[] decodedBytesFiel = Base64.getDecoder().decode(entity.getPasswordFiel());
	    String passFiel = new String(decodedBytesFiel);
	    prestadoraDocumento.setPasswordFiel(passFiel);
	    prestadoraServicioDto.setPasswordFiel(passFiel);
	    }
	    
		prestadoraServicioDto.setPrestadoraServicioDocumento(prestadoraDocumento);
	    
		return prestadoraServicioDto;
	}

	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByIdDomicilio(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		DomicilioComunDto prestadoraServicioDomicilio = new DomicilioComunDto();
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		
		
		
		if(entity.getCelulaPrestadoraServicio() != null) {
			prestadoraServicioDto.setCelulaPrestadoraServicioDto(celulaPrestadoraServicioDao.convertirCelulaPrestadoraServicioADto(entity.getCelulaPrestadoraServicio()));
		}
		
		if(entity.getPrestadoraServicioDomicilio() != null) {
			prestadoraServicioDomicilio = prestadoraServicioDomicilioDao.convertirPrestadoraServicioDomicilioADto(entity.getPrestadoraServicioDomicilio());
			prestadoraServicioDto.setPrestadoraServicioDomicilioDto(prestadoraServicioDomicilio);
		}
		
		
		return prestadoraServicioDto;
	}

	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByIdCuentaBancaria(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		
		
		if(entity.getCelulaPrestadoraServicio() != null) {
			prestadoraServicioDto.setCelulaPrestadoraServicioDto(celulaPrestadoraServicioDao.convertirCelulaPrestadoraServicioADto(entity.getCelulaPrestadoraServicio()));
		}
		
		if(!entity.getPrestadoraServicioCuentaBancaria().isEmpty()) {
			prestadoraServicioDto.setPrestadoraServicioCuentaBancaria(prestadoraServicioCuentaBancariaDao.convertirCuentaPrestadoraServicioADto(entity.getPrestadoraServicioCuentaBancaria()));
		}
		
		return prestadoraServicioDto;
	}


	@Override
	public PrestadoraServicioDto obtenerEntidadFederativaXCP(String codigoPostal) {
		return prestadoraServicioDao.obtenerEntidadFederativaXCP(codigoPostal);
	}

	@Override
	public void guardarDomicilioPrestadoraServicio(DomicilioComunDto prestadoraServicioDomicilioDto,
			UsuarioAplicativo usuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
			PrestadoraServicioDomicilio entity = new PrestadoraServicioDomicilio();
			ModelMapper mapper = new ModelMapper();
			
			PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
			prestadoraServicio = prestadoraServicioDao.read(prestadoraServicioDomicilioDto.getPrestadoraServicio().getIdPrestadoraServicio());
			
			
			if (prestadoraServicioDomicilioDto.getIdPrestadoraServicioDomicilio() != null) {
				entity.setFechaModificacion(new Date());
				entity.setUsuarioByUsuarioModificacion(usuario);
				
				entity = mapper.map(prestadoraServicioDomicilioDto, PrestadoraServicioDomicilio.class);
				
				domicilioDao.crearActualizarDomicilio(entity.getDomicilio());
				
			}else {
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioByUsuarioAlta(usuario);
				entity.setFechaAlta(new Date());
				
				Domicilio entityDomicilio =  new Domicilio();
				entityDomicilio = mapper.map(prestadoraServicioDomicilioDto.getDomicilio(), Domicilio.class);
				entityDomicilio.setCatEntidadFederativa(new CatEntidadFederativa());
				entityDomicilio.getCatEntidadFederativa().setIdEntidadFederativa(prestadoraServicioDomicilioDto.getDomicilio().getIdEntidadFederativa());
				entityDomicilio.setCatTipoDomicilio(new CatGeneral());
				entityDomicilio.getCatTipoDomicilio().setIdCatGeneral(34L);
				entityDomicilio.setIdDomicilio(domicilioDao.crearActualizarDomicilio(entityDomicilio));
				
				PrestadoraServicioDomicilio prestadoraServicioDomicilio =  new PrestadoraServicioDomicilio();
				
				prestadoraServicioDomicilio = mapper.map(prestadoraServicioDomicilioDto, PrestadoraServicioDomicilio.class);
				prestadoraServicioDomicilio.setDomicilio(entityDomicilio);
				prestadoraServicioDomicilio.setIdPrestadoraServicio(prestadoraServicio);
				prestadoraServicioDomicilio.setUsuarioByUsuarioAlta(usuario);
				prestadoraServicioDomicilio.setFechaAlta(new Date());
				prestadoraServicioDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioDomicilioDao.createOrUpdate(prestadoraServicioDomicilio);
				
				
			}

			prestadoraServicioDao.createOrUpdate(prestadoraServicio);
		}catch(Exception e) {
			
		}
		
	}

	@Override
	@Transactional
	public void guardarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo) throws BusinessException {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
			PrestadoraServicioCuentaBancaria entity = new PrestadoraServicioCuentaBancaria();
			PrestadoraServicio prestadoraServicio =new PrestadoraServicio();
			
			prestadoraServicio = prestadoraServicioDao.read(cuenta.getIdPrestadoraServicio());
			
			//Guarda PrestadoraServicioCuenta
			if (cuenta.getIdPrestadoraServicioCuentaBancaria() != null) {
				entity = prestadoraServicioCuentaBancariaDao.read(cuenta.getIdPrestadoraServicioCuentaBancaria());
				
				entity.setCatBanco(new CatGeneral());
				entity.getCatBanco().setIdCatGeneral(cuenta.getCatBanco().getIdCatGeneral());
				
				entity.setCatTipoCuenta(new CatGeneral());
				entity.getCatTipoCuenta().setIdCatGeneral(cuenta.getCatTipoCuenta().getIdCatGeneral());
				
				entity.setClabeInterbancaria(cuenta.getClabeInterbancaria());
				if(cuenta.getEsPrincipal() != null) {
					if(cuenta.getEsPrincipal() == true) {
						entity.setEsPrincipal(1);
					}else {
						entity.setEsPrincipal(0);
					}
				}else {
					entity.setEsPrincipal(0);
				}
				entity.setNumeroCuenta(cuenta.getNumeroCuenta());
				entity.setNumeroReferencia(cuenta.getNumeroReferencia());
				
				entity.setFechaModificacion(new Date());
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioModificacion(usuario);
				if(entity.getFechaAlta() == null) {
					entity.setFechaAlta(new Date());
				}
				if(entity.getUsuarioAlta() == null) {
					entity.setUsuarioAlta(usuario);
				}
			}else {
				entity.setCatBanco(new CatGeneral());
				entity.getCatBanco().setIdCatGeneral(cuenta.getCatBanco().getIdCatGeneral());
				
				entity.setCatTipoCuenta(new CatGeneral());
				entity.getCatTipoCuenta().setIdCatGeneral(cuenta.getCatTipoCuenta().getIdCatGeneral());
				
				entity.setClabeInterbancaria(cuenta.getClabeInterbancaria());
				if(cuenta.getEsPrincipal() != null) {
					if(cuenta.getEsPrincipal() == true) {
						entity.setEsPrincipal(1);
					}else {
						entity.setEsPrincipal(0);
					}
				}else {
					entity.setEsPrincipal(0);
				}
				entity.setNumeroCuenta(cuenta.getNumeroCuenta());
				entity.setNumeroReferencia(cuenta.getNumeroReferencia());
				
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioAlta(usuario);
				entity.setFechaAlta(new Date());
			}
			entity.setPrestadoraServicio(prestadoraServicio);
			prestadoraServicioCuentaBancariaDao.createOrUpdate(entity);
		
			}catch(Exception e) {
				LOGGER.error(e.getMessage());
			}
		
	}

	@Override
	@Transactional
	public void eliminarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
			PrestadoraServicioCuentaBancaria entity = new PrestadoraServicioCuentaBancaria();
			
			//Guarda PrestadoraServicioCuenta
			if (cuenta.getIdPrestadoraServicioCuentaBancaria() != null) {
				entity = prestadoraServicioCuentaBancariaDao.read(cuenta.getIdPrestadoraServicioCuentaBancaria());
				entity.setFechaModificacion(new Date());
				entity.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				entity.setUsuarioModificacion(usuario);
				if(entity.getFechaAlta() == null) {
					entity.setFechaAlta(new Date());
				}
				if(entity.getUsuarioAlta() == null) {
					entity.setUsuarioAlta(usuario);
				}
			}

			prestadoraServicioCuentaBancariaDao.createOrUpdate(entity);
		
			}catch(Exception e) {
				LOGGER.error(e.getMessage());
			}
		
	}

	@Override
	@Transactional
	public void guardarArchivosFiel(PrestadoraServicioDocumentoDto documentos, UsuarioAplicativo usuarioAplicativo) {
//		byte[] archivoFielCer = null;
//		byte[] archivoFielKey = null;
//		byte[] archivoCsdCer = null;
//		byte[] archivoCsdKey = null;
		
		Usuario usuario =  new Usuario();
		usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
		PrestadoraServicio prestadoraServicio =  prestadoraServicioDao.read(documentos.getIdPrestadoraServicio());
		
		if(prestadoraServicio.getIdPrestadoraServicio() != null) {
			if(documentos.getPasswordFiel() != null) {
				prestadoraServicio.setPasswordFiel(documentos.getPasswordFiel());
			}
			
			if(documentos.getPasswordCsd() != null) {
				prestadoraServicio.setPasswordCsd(documentos.getPasswordCsd());
			}
			prestadoraServicio.setFechaModificacion(new Date());
			prestadoraServicio.setUsuarioModificacion(usuario);
			prestadoraServicio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
		}
//		else {
		
//		if(documentos.getArchivoFielCer().containsKey("archivoFielCer")) {
//		String bytesArchivoFielCer = documentos.getArchivoFielCer().get("archivoFielCer").toString();
//		archivoFielCer = bytesArchivoFielCer.getBytes();
//		
//		entity.setNombreArchivoFielCer(documentos.getArchivoFielCer().get("nombreArchivoFielCer").toString());
//		entity.setArchivoFielCer(archivoFielCer);
//		}
//		
//		if(documentos.getArchivoFielKey().containsKey("archivoFielKey")) {
//		String byteArchivoFielKey = documentos.getArchivoFielKey().get("archivoFielKey").toString();
//		archivoFielKey = byteArchivoFielKey.getBytes();
//		
//		entity.setNombreArchivoFielKey(documentos.getArchivoFielKey().get("nombreArchivoFielKey").toString());
//		entity.setArchivoFielKey(archivoFielKey);
//		}
//		
//		if(documentos.getArchivoCsdCer().containsKey("archivoCsdCer")) {
//		String bytesArchivoCsdCer = documentos.getArchivoCsdCer().get("archivoCsdCer").toString();
//		archivoCsdCer = bytesArchivoCsdCer.getBytes();
//		
//		entity.setNombreArchivoCsdCer(documentos.getArchivoCsdCer().get("nombreArchivoCsdCer").toString());
//		entity.setArchivoCsdCer(archivoCsdCer);
//		}
//		
//		if(documentos.getArchivoCsdKey().containsKey("archivoCsdKey")) {
//		String bytesArchivoCsdKey = documentos.getArchivoCsdKey().get("archivoCsdKey").toString();
//		archivoCsdKey = bytesArchivoCsdKey.getBytes();
//		
//		entity.setNombreArchivoCsdKey(documentos.getArchivoCsdKey().get("nombreArchivoCsdKey").toString());
//		entity.setArchivoCsdKey(archivoCsdKey);
//		}
		
//		if(documentos.getPasswordFiel() != null) {
//			entity.setPasswordFiel(documentos.getPasswordFiel());
//		}
//		
//		if(documentos.getPasswordCsd() != null) {
//			entity.setPasswordCsd(documentos.getPasswordCsd());
//		}
//		
//		entity.setFechaAlta(new Date());
//		entity.setUsuarioAlta(usuario);
//		entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//		entity.setPrestadoraServicio(prestadoraServicio);
//		}
		prestadoraServicioDao.createOrUpdate(prestadoraServicio);
	}
	
	@Override
	@Transactional
	public void guardarProducto(PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo) {
		try {
		Usuario usuario =  new Usuario();
		usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
		PrestadoraServicioProducto entity =  new PrestadoraServicioProducto();
		PrestadoraServicio entityPrestadora = new PrestadoraServicio();
		entityPrestadora = prestadoraServicioDao.read(producto.getPrestadoraServicioDto().getIdPrestadoraServicio());
		
		if(producto.getIdPrestadoraServicioProducto() ==null) {
			
			entity.setPrestadoraServicio(entityPrestadora);
			
			entity.setCatProductoSat(new CatGeneral());
			entity.getCatProductoSat().setIdCatGeneral(producto.getIdCatGeneral());
			
			entity.setDescripcionProductoConsolida(producto.getDescripcionProductoConsolida());

			entity.setFechaAlta(new Date());
			entity.setUsuarioAlta(usuario);
			entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			
			prestadoraServicioProductoDao.createOrUpdate(entity);
				
			}else {
			PrestadoraServicioProducto modificacion =	prestadoraServicioProductoDao.read(producto.getIdPrestadoraServicioProducto());
			modificacion.setCatProductoSat(new CatGeneral());
			modificacion.getCatProductoSat().setIdCatGeneral(producto.getIdCatGeneral());
			modificacion.setDescripcionProductoConsolida(producto.getDescripcionProductoConsolida());
			modificacion.setPrestadoraServicio(entityPrestadora);
			modificacion.setFechaModificacion(new Date());
			modificacion.setUsuarioModificacion(usuario);
			modificacion.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			
			prestadoraServicioProductoDao.createOrUpdate(modificacion);
			}
		}catch(Exception e) {
			LOGGER.error("Ocurrio un error en el metodo guardarProducto " + e.getMessage());
		}
			
		
	}
	
	@Override
	@Transactional
	public void eliminarProducto(PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo) {
		try {
		Usuario usuario =  new Usuario();
		usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
		PrestadoraServicioProducto modificacion =	prestadoraServicioProductoDao.read(producto.getIdPrestadoraServicioProducto());
			modificacion.setCatProductoSat(new CatGeneral());
			modificacion.getCatProductoSat().setIdCatGeneral(producto.getIdCatGeneral());
			modificacion.setDescripcionProductoConsolida(producto.getDescripcionProductoConsolida());
			modificacion.setFechaModificacion(new Date());
			modificacion.setUsuarioModificacion(usuario);
			modificacion.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
			
			prestadoraServicioProductoDao.createOrUpdate(modificacion);
			
		}catch(Exception e) {
			LOGGER.error("Ocurrio un error en el metodo guardarProducto " + e.getMessage());
		}
			
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<CatProductoDto> obtenerEstatusCatalogoProductos(List<CatProductoDto> catProductoDto, Long idPrestadoraServicio) {
		List<CatProductoDto> busquedaProducto = new ArrayList<CatProductoDto>();
		busquedaProducto = prestadoraServicioProductoDao.obtenerProductosXIdPrestadora(idPrestadoraServicio);
		
		for(CatProductoDto productoDto : catProductoDto) {
			for(CatProductoDto producto : busquedaProducto) {
				
				if(producto.getIdCatProducto() == productoDto.getIdCatProducto()) {
					productoDto.setIndEstatus(Boolean.TRUE);
				}
			}
		}
		
		return catProductoDto;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioDto> listaPrestdorasFondoYSinFondoByIdCelula(Long idCelula, boolean esFondo) {
		try {
			
			List<PrestadoraServicioDto> lista = prestadoraServicioDao.listaPrestdorasFondoYSinFondoByIdCelula(idCelula, esFondo);
			
			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en listaPrestdorasFondoYSinFondoByIdCelula ", e);
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public MensajeDTO verificarGuardado(PrestadoraServicioCuentaBancariaDto cuenta) {
		MensajeDTO mensaje = new MensajeDTO();
		PrestadoraServicio entity = prestadoraServicioDao.read(cuenta.getIdPrestadoraServicio());
		if(!entity.getPrestadoraServicioCuentaBancaria().isEmpty()) {
			List<PrestadoraServicioCuentaBancariaDto> list = prestadoraServicioCuentaBancariaDao.convertirCuentaPrestadoraServicioADto(entity.getPrestadoraServicioCuentaBancaria());
			Boolean tienePrincipal = false;
			for(PrestadoraServicioCuentaBancariaDto cuentaDto: list) {
				if(cuentaDto.getEsPrincipal() == true) {
					tienePrincipal = true;
				}
			}
			
			if(tienePrincipal == true) {
				mensaje.setCorrecto(false);
				mensaje.setMensajeError("Únicamente se puede marcar una cuenta como principal, favor de verificar");
			}else {
				mensaje.setCorrecto(true);
			}
		}else {
			mensaje.setCorrecto(true);
		}
		return mensaje;
	}

	@Override
	public List<PrestadoraServicioDto> verificarFondo(Long idCelula) {
		return prestadoraServicioDao.verificarFondo(idCelula);
	}

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioActividadDto obtenerDatosByActividad(Long idPrestadoraServicio) {
		
		PrestadoraServicioActividadDto cuenta = new PrestadoraServicioActividadDto();
		
		try {

			PrestadoraServicio entity = new PrestadoraServicio();
					
			entity = prestadoraServicioDao.read(idPrestadoraServicio);
			
			if(!entity.getPrestadoraGiroComercial().isEmpty() ) {
				cuenta.setListPrestadorActividad(prestadoraServicioGiroComercialDao.convertirGiroADto(entity.getPrestadoraGiroComercial()));
			}
			return cuenta;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerDatosByActividad ", e);
			return cuenta;
		}
		
	}

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioActividadDto obtenerCatalogosActividad(PrestadoraServicioActividadDto prestadoraActividad) {
		try {

			prestadoraActividad.setGirosComerciales(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve()));
			if(prestadoraActividad.getIdGiroComercial() != null) {
				prestadoraActividad.setCatSubGiroComercial(obtenerSubgiroXIdGiro(prestadoraActividad.getIdGiroComercial().toString()));
			}
			return prestadoraActividad;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en obtenerDatosByActividad ", e);
			return new PrestadoraServicioActividadDto();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro) {
		return prestadoraServicioGiroComercialDao.obtenerSubgiroXIdGiro(idGiro);
	}

	@Override
	@Transactional
	public void guardarActividad(PrestadoraServicioActividadDto actividad, UsuarioAplicativo usuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());
		
			PrestadoraServicioGiroComercial prestadoraServicio = new PrestadoraServicioGiroComercial();
			PrestadoraServicio prestadora = new PrestadoraServicio();
			
			CatGeneral  catGeneral  = new CatGeneral();
			CatSubGiroComercial  catSubGiroComercial = new CatSubGiroComercial();
			
			prestadora = prestadoraServicioDao.read(actividad.getPrestadoraDto().getIdPrestadoraServicio());
			catGeneral.setIdCatGeneral(actividad.getIdGiroComercial());
			catSubGiroComercial.setIdCatSubGiroComercial(actividad.getIdSubGiroComercial());
			
			if (actividad.getIdPrestadoraGiroComercial() != null) {
				prestadoraServicio = prestadoraServicioGiroComercialDao.read(actividad.getIdPrestadoraGiroComercial());
				prestadoraServicio.setPrestadoraServicio(prestadora);
				prestadoraServicio.setCatGiroComercial(catGeneral);
				prestadoraServicio.setCatSubGiroComercial(catSubGiroComercial);
				prestadoraServicio.setFechaModificacion(new Date());
				prestadoraServicio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicio.setUsuarioModificacion(usuario);
				if(prestadoraServicio.getFechaAlta() == null) {
					prestadoraServicio.setFechaAlta(new Date());
				}
				if(prestadoraServicio.getUsuarioAlta() == null) {
					prestadoraServicio.setUsuarioAlta(usuario);
				}
			}else {
				prestadoraServicio.setPrestadoraServicio(prestadora);
				prestadoraServicio.setCatGiroComercial(catGeneral);
				prestadoraServicio.setCatSubGiroComercial(catSubGiroComercial);
				prestadoraServicio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicio.setUsuarioAlta(usuario);
				prestadoraServicio.setFechaAlta(new Date());
			}

			prestadoraServicioGiroComercialDao.createOrUpdate(prestadoraServicio);
		
			}catch(Exception e) {
				LOGGER.error("Ocurrio un error en guardarActividad ", e);
			}
	}

	@Override
	@Transactional
	public void eliminarActividad(PrestadoraServicioActividadDto actividad, Long idUsuarioAplicativo) {
		
		try {
			
			PrestadoraServicioGiroComercial psa = prestadoraServicioGiroComercialDao.read(actividad.getIdPrestadoraGiroComercial());
			psa.setFechaModificacion(new Date());
			psa.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
			psa.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
			prestadoraServicioGiroComercialDao.update(psa);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarActividad ", e);
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarPrestadoraRegistroPatronal(PrestadoraServicioRegistroPatronalDto registroPatronal, Long idUsuarioAplicativo) {
		try {
			
			PrestadoraServicioRegistroPatronal clienConcepFact = new PrestadoraServicioRegistroPatronal();
			
			if(registroPatronal.getIdPrestadoraServicioRegistroPatronal()!=null && registroPatronal.getEsEliminar() == null) {
				
				clienConcepFact = prestadoraServicioRegistroPatronalDao.read(registroPatronal.getIdPrestadoraServicioRegistroPatronal());
				clienConcepFact.setPrestadoraServicio(prestadoraServicioDao.read(registroPatronal.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				clienConcepFact.setRegistroPatronal(registroPatronal.getRegistroPatronal());
				clienConcepFact.setFechaModificacion(new Date());
				clienConcepFact.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioRegistroPatronalDao.update(clienConcepFact);
				
				return Boolean.TRUE;
				
			}else if(registroPatronal.getEsEliminar()) {
				
				clienConcepFact = prestadoraServicioRegistroPatronalDao.read(registroPatronal.getIdPrestadoraServicioRegistroPatronal());
				clienConcepFact.setPrestadoraServicio(prestadoraServicioDao.read(registroPatronal.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				clienConcepFact.setRegistroPatronal(registroPatronal.getRegistroPatronal());
				clienConcepFact.setFechaModificacion(new Date());
				clienConcepFact.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				
				prestadoraServicioRegistroPatronalDao.update(clienConcepFact);
				
				return Boolean.TRUE;
				
			}else {
				
				clienConcepFact.setPrestadoraServicio(prestadoraServicioDao.read(registroPatronal.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				clienConcepFact.setRegistroPatronal(registroPatronal.getRegistroPatronal());
				clienConcepFact.setFechaAlta(new Date());
				clienConcepFact.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				
				prestadoraServicioRegistroPatronalDao.create(clienConcepFact);

				return Boolean.TRUE;
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarPrestadoraRegistroPatronal ",e );
			return Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioRegistroPatronalDto> getListRegistroPatronalByIdPrestadora(Long idPrestadora) {
		try {

			List<PrestadoraServicioRegistroPatronalDto>  lista = prestadoraServicioRegistroPatronalDao.getListRegistroPatronalByIdPrestadora(idPrestadora);

			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRegistroPatronalByIdPrestadora ",e );
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarPrestadoraClaseFraccionPrima(PrestadoraServicioClaseFraccionPrimaDto prestadoraClaseFraccionPrimaDto, Long idUsuarioAplicativo) {
		
		try {
				PrestadoraServicioClaseFraccionPrima prestadoraServicioClaseFraccionPrima = new PrestadoraServicioClaseFraccionPrima();
			
				if(prestadoraClaseFraccionPrimaDto.getIdPrestadoraServicioClaseFraccionPrima()!=null) {
					
					prestadoraServicioClaseFraccionPrima = prestadoraServicioClaseFraccionPrimaDao.read(prestadoraClaseFraccionPrimaDto.getIdPrestadoraServicioClaseFraccionPrima());
					prestadoraServicioClaseFraccionPrima.setFraccion(prestadoraClaseFraccionPrimaDto.getFraccion());
					prestadoraServicioClaseFraccionPrima.setPrima(prestadoraClaseFraccionPrimaDto.getPrima());
					prestadoraServicioClaseFraccionPrima.setCatClase(new CatGeneral(prestadoraClaseFraccionPrimaDto.getCatClase().getIdCatGeneral()));
//					prestadoraServicioClaseFraccionPrima.setCatRiesgo(new CatGeneral(prestadoraClaseFraccionPrimaDto.getCatRiesgo().getIdCatGeneral()));
					prestadoraServicioClaseFraccionPrima.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
					prestadoraServicioClaseFraccionPrima.setFechaModificacion(new Date());
					prestadoraServicioClaseFraccionPrima.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					prestadoraServicioClaseFraccionPrimaDao.update(prestadoraServicioClaseFraccionPrima);
					
					
				}else {
					
					prestadoraServicioClaseFraccionPrima.setPrestadoraServicio(new PrestadoraServicio(prestadoraClaseFraccionPrimaDto.getPrestadoraServicio().getIdPrestadoraServicio()));
					prestadoraServicioClaseFraccionPrima.setFraccion(prestadoraClaseFraccionPrimaDto.getFraccion());
					prestadoraServicioClaseFraccionPrima.setPrima(prestadoraClaseFraccionPrimaDto.getPrima());
					prestadoraServicioClaseFraccionPrima.setCatClase(new CatGeneral(prestadoraClaseFraccionPrimaDto.getCatClase().getIdCatGeneral()));
//					prestadoraServicioClaseFraccionPrima.setCatRiesgo(new CatGeneral(prestadoraClaseFraccionPrimaDto.getCatRiesgo().getIdCatGeneral()));
					prestadoraServicioClaseFraccionPrima.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					prestadoraServicioClaseFraccionPrima.setFechaAlta(new Date());
					prestadoraServicioClaseFraccionPrima.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					prestadoraServicioClaseFraccionPrimaDao.create(prestadoraServicioClaseFraccionPrima);

				}
			
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarPrestadoraClaseFraccionPrima ",e );
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioClaseFraccionPrimaDto getPresatdorServicioClaseFraccionByIdPrestServicio(Long idPrestadora) {
		try {
			
			return prestadoraServicioClaseFraccionPrimaDao.getPresatdorServicioClaseFraccionByIdPrestServicio(idPrestadora);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPresatdorServicioClaseFraccionByIdPrestServicio ",e );
			return null;
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarPrestadoraImss(PrestadoraServicioImssDto prestadoraServicioImssDto,Long idUsuarioAplicativo) {
		try {
			
				PrestadoraServicioImss prestadoraServicioImss = new PrestadoraServicioImss();
			
				if(prestadoraServicioImssDto.getIdPrestadoraServicioImss()!=null) {
					
					prestadoraServicioImss = prestadoraServicioImssDao.read(prestadoraServicioImssDto.getIdPrestadoraServicioImss());
					prestadoraServicioImss.setPassword(prestadoraServicioImssDto.getPassword());
					prestadoraServicioImss.setUsuario(prestadoraServicioImssDto.getUsuario());
					prestadoraServicioImss.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
					prestadoraServicioImss.setFechaModificacion(new Date());
					prestadoraServicioImss.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					prestadoraServicioImssDao.update(prestadoraServicioImss);
					
					
				}else {
					
					prestadoraServicioImss.setPrestadoraServicio(new PrestadoraServicio(prestadoraServicioImssDto.getPrestadoraServicio().getIdPrestadoraServicio()));
					prestadoraServicioImss.setPassword(prestadoraServicioImssDto.getPassword());
					prestadoraServicioImss.setUsuario(prestadoraServicioImssDto.getUsuario());
					prestadoraServicioImss.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
					prestadoraServicioImss.setFechaAlta(new Date());
					prestadoraServicioImss.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					prestadoraServicioImssDao.create(prestadoraServicioImss);
	
				}
			
			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarPrestadoraClaseFraccionPrima ",e );
			return Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioImssDto getPresatdorServicioImssByIdPrestServicio(Long idPrestadora) {
		try {
			
			return prestadoraServicioImssDao.getPresatdorServicioImssByIdPrestServicio(idPrestadora);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPresatdorServicioImssByIdPrestServicio ",e );
			return null;
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarPrestadoraObjetoSocial(PrestadoraServicioObjetoSocialDto prestadoraServicioObjetoSocialDto, Long idUsuarioAplicativo) {
		try {
			
			PrestadoraServicioObjetoSocial prestadoraServicioObjetoSocial = new PrestadoraServicioObjetoSocial();
					
			if(prestadoraServicioObjetoSocialDto.getIdPrestadoraServicioObjetoSocial()!=null && prestadoraServicioObjetoSocialDto.getEsEliminar() == null) {
				
				prestadoraServicioObjetoSocial = prestadoraServicioObjetoSocialDao.read(prestadoraServicioObjetoSocialDto.getIdPrestadoraServicioObjetoSocial());
				prestadoraServicioObjetoSocial.setDescripcionObjetoSocial(prestadoraServicioObjetoSocialDto.getDescripcion());
				prestadoraServicioObjetoSocial.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				prestadoraServicioObjetoSocial.setFechaModificacion(new Date());
				prestadoraServicioObjetoSocial.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioObjetoSocialDao.update(prestadoraServicioObjetoSocial);
				
				return Boolean.TRUE;
				
			}else if(prestadoraServicioObjetoSocialDto.getEsEliminar()) {
				
				prestadoraServicioObjetoSocial = prestadoraServicioObjetoSocialDao.read(prestadoraServicioObjetoSocialDto.getIdPrestadoraServicioObjetoSocial());
				prestadoraServicioObjetoSocial.setDescripcionObjetoSocial(prestadoraServicioObjetoSocialDto.getDescripcion());
				prestadoraServicioObjetoSocial.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				prestadoraServicioObjetoSocial.setFechaModificacion(new Date());
				prestadoraServicioObjetoSocial.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				prestadoraServicioObjetoSocialDao.update(prestadoraServicioObjetoSocial);
				

				return Boolean.TRUE;
			
			}else {
				
				prestadoraServicioObjetoSocial.setPrestadoraServicio(new PrestadoraServicio(prestadoraServicioObjetoSocialDto.getPrestadoraServicio().getIdPrestadoraServicio()));
				prestadoraServicioObjetoSocial.setDescripcionObjetoSocial(prestadoraServicioObjetoSocialDto.getDescripcion());
				prestadoraServicioObjetoSocial.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				prestadoraServicioObjetoSocial.setFechaAlta(new Date());
				prestadoraServicioObjetoSocial.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioObjetoSocialDao.create(prestadoraServicioObjetoSocial);
				
				return Boolean.TRUE;

			}

	}catch (Exception e) {
		LOGGER.error("Ocurrio un error en guardarActualizarPrestadoraObjetoSocial ",e );
		return Boolean.FALSE;
	}
	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioObjetoSocialDto> getListObjetoSocialByIdPrestadora(Long idPrestadora) {
		try {
			
			List<PrestadoraServicioObjetoSocialDto>  lista = prestadoraServicioObjetoSocialDao.getListObjetoSocialByIdPrestadora(idPrestadora);

			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListObjetoSocialByIdPrestadora ",e );
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public CatGeneralDto obtenerCatProductoSatXCve(String cveSat) {
		CatGeneralDto ctaGeneral =  new CatGeneralDto();
		ctaGeneral = catBo.obtenerCatGeneralByClvMaestroClv(CatMaestroEnum.PRODUCTO_SAT.getCve(), cveSat);
		return ctaGeneral;
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioArchivo(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		
		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		prestadoraServicioDto.setIdConsar(entity.getId_consar());
//		prestadoraServicioDto.setClave(entity.getClave());
		if(entity.getEsFondo() == 1) {
		prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		}else {
		prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIdPrestadoraServicio(entity.getIdPrestadoraServicio());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		prestadoraServicioDto.setNombreArchivoLogotipo(entity.getNombreArchivoLogotipo());
		
		PrestadoraServicioDto archivo = new PrestadoraServicioDto();
		archivo = prestadoraServicioDao.obtenerPrestadoraServicioArchivoLogotipo(idPrestadoraServicio);
		prestadoraServicioDto.setArchivoRecuperadoLogotipo(archivo.getArchivoRecuperadoLogotipo());
		
		return prestadoraServicioDto;
	}

	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPrestadora(Long idPrestadora) {
		return prestadoraServicioDao.listDocumentosPrestadora(idPrestadora); 	
	}

	@Override
	@Transactional
	public Boolean guardarActualizarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioRepresentanteLegal representante = new PrestadoraServicioRepresentanteLegal();
			
			if(representanteLegalDto.getIdGenericoRepresentanteLegal()!=null && representanteLegalDto.getIdPersona()!=null) {
				
				persona = personaDao.read(representanteLegalDto.getIdPersona());
				persona.setNombre(representanteLegalDto.getNombre());
				persona.setApellidoPaterno(representanteLegalDto.getApellidoPaterno());
				if(representanteLegalDto.getApellidoMaterno() == null || "".equals(representanteLegalDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(representanteLegalDto.getApellidoMaterno());
				}
				persona.setRfc(representanteLegalDto.getRfc());
				persona.setCurp(representanteLegalDto.getCurp());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				
				representante = prestadoraServicioRepresentanteLegalDao.read(representanteLegalDto.getIdGenericoRepresentanteLegal());
				representante.setPrestadoraServicio(new PrestadoraServicio(representanteLegalDto.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				representante.setPersona(persona);
				representante.setContrasenaCertificado(representanteLegalDto.getContrasenaCertificado());
				representante.setUsuarioModificacion(new Usuario(idUsuarioALta));
				representante.setFechaModificacion(new Date());
				representante.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioRepresentanteLegalDao.update(representante);
			
			}else {
				
				persona.setNombre(representanteLegalDto.getNombre());
				persona.setApellidoPaterno(representanteLegalDto.getApellidoPaterno());
				if(representanteLegalDto.getApellidoMaterno() == null || "".equals(representanteLegalDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(representanteLegalDto.getApellidoMaterno());
				}
				persona.setRfc(representanteLegalDto.getRfc());
				persona.setCurp(representanteLegalDto.getCurp());
				persona.setUsuarioAlta(idUsuarioALta);
				persona.setFechaAlta(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				Long idPersona = personaDao.create(persona);
				
				representante.setPrestadoraServicio(new PrestadoraServicio(representanteLegalDto.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				representante.setPersona(new Persona(idPersona));
				representante.setContrasenaCertificado(representanteLegalDto.getContrasenaCertificado());
				representante.setUsuarioAlta(new Usuario(idUsuarioALta));
				representante.setFechaAlta(new Date());
				representante.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioRepresentanteLegalDao.create(representante);
			}
			

			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarRepresentanteLegal ",e );
			return  Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional
	public Boolean eliminarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioRepresentanteLegal representante = new PrestadoraServicioRepresentanteLegal();
			
			if(representanteLegalDto.getIdGenericoRepresentanteLegal()!=null && representanteLegalDto.getIdPersona()!=null) {
				
				persona = personaDao.read(representanteLegalDto.getIdPersona());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.INACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				representante = prestadoraServicioRepresentanteLegalDao.read(representanteLegalDto.getIdGenericoRepresentanteLegal());
				representante.setUsuarioModificacion(new Usuario(idUsuarioALta));
				representante.setFechaModificacion(new Date());
				representante.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				prestadoraServicioRepresentanteLegalDao.update(representante);
			
			}else {
				LOGGER.error("Ocurrio un error en eliminarRepresentanteLegal representanteLegalDto.getIdGenericoRepresentanteLegal() && representanteLegalDto.getIdPersona() vienen null");
				return Boolean.FALSE;
			}
			
			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarRepresentanteLegal ", e);
			return  Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional
	public Boolean guardarActualizarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioApoderadoLegal apoderado = new PrestadoraServicioApoderadoLegal();
			
			if(apoderadoLegalDto.getIdGenericoApoderadoLegal()!=null && apoderadoLegalDto.getIdPersona()!=null) {
				
				persona = personaDao.read(apoderadoLegalDto.getIdPersona());
				persona.setNombre(apoderadoLegalDto.getNombre());
				persona.setApellidoPaterno(apoderadoLegalDto.getApellidoPaterno());
				if(apoderadoLegalDto.getApellidoMaterno() == null || "".equals(apoderadoLegalDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(apoderadoLegalDto.getApellidoMaterno());
				}
				persona.setRfc(apoderadoLegalDto.getRfc());
				persona.setCurp(apoderadoLegalDto.getCurp());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				
				apoderado = prestadoraServicioApoderadoLegalDao.read(apoderadoLegalDto.getIdGenericoApoderadoLegal());
				apoderado.setPrestadoraServicio(new PrestadoraServicio(apoderadoLegalDto.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				apoderado.setPersona(persona);
				apoderado.setPoderNotarial(apoderadoLegalDto.getPoderNotarial());
				apoderado.setCatFacultad(new CatGeneral(apoderadoLegalDto.getCatFacultadDto().getIdCatGeneral()));
				apoderado.setUsuarioModificacion(new Usuario(idUsuarioALta));
				apoderado.setFechaModificacion(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioApoderadoLegalDao.update(apoderado);
			
			}else {
				
				persona.setNombre(apoderadoLegalDto.getNombre());
				persona.setApellidoPaterno(apoderadoLegalDto.getApellidoPaterno());
				if(apoderadoLegalDto.getApellidoMaterno() == null || "".equals(apoderadoLegalDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(apoderadoLegalDto.getApellidoMaterno());
				}
				persona.setRfc(apoderadoLegalDto.getRfc());
				persona.setCurp(apoderadoLegalDto.getCurp());
				persona.setUsuarioAlta(idUsuarioALta);
				persona.setFechaAlta(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				Long idPersona = personaDao.create(persona);
				
				apoderado.setPrestadoraServicio(new PrestadoraServicio(apoderadoLegalDto.getPrestadoraServicioDto().getIdPrestadoraServicio()));
				apoderado.setPersona(new Persona(idPersona));
				apoderado.setPoderNotarial(apoderadoLegalDto.getPoderNotarial());
				apoderado.setCatFacultad(new CatGeneral(apoderadoLegalDto.getCatFacultadDto().getIdCatGeneral()));
				apoderado.setUsuarioAlta(new Usuario(idUsuarioALta));
				apoderado.setFechaAlta(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioApoderadoLegalDao.create(apoderado);
			}
			

			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarApoderadoLegal ", e);
			return  Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional
	public Boolean eliminarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioApoderadoLegal apoderado = new PrestadoraServicioApoderadoLegal();
			
			if(apoderadoLegalDto.getIdGenericoApoderadoLegal()!=null && apoderadoLegalDto.getIdPersona()!=null) {
				
				persona = personaDao.read(apoderadoLegalDto.getIdPersona());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.INACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				apoderado = prestadoraServicioApoderadoLegalDao.read(apoderadoLegalDto.getIdGenericoApoderadoLegal());
				apoderado.setUsuarioModificacion(new Usuario(idUsuarioALta));
				apoderado.setFechaModificacion(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				prestadoraServicioApoderadoLegalDao.update(apoderado);
			
			}else {
				LOGGER.error("Ocurrio un error en eliminarApoderadoLegal apoderadoLegalDto.getIdGenericoApoderadoLegal() && apoderadoLegalDto.getIdPersona() vienen null");
				return Boolean.FALSE;
			}
			
			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarApoderadoLegal ", e);
			return  Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RepresentanteLegalDto> getListRepresentanteLegalByIdPrestadoraServicio(Long idPrestadora) {
		try {

			return prestadoraServicioRepresentanteLegalDao.getListRepresentanteLegalByIdPrestadoraServicio(idPrestadora);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRepresentanteLegalByIdPrestadoraServicio ", e);
			return  Collections.emptyList();
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ApoderadoLegalDto> getListApoderadoLegalByIdPrestadoraServicio(Long idPrestadora) {
		try {

			return prestadoraServicioApoderadoLegalDao.getListApoderadoLegalByIdPrestadoraServicio(idPrestadora);
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListApoderadoLegalByIdPrestadoraServicio ", e);
			return  Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarAccionistas(AccionistaDto accionistaDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioAccionista accionista = new PrestadoraServicioAccionista();
			
			if(accionistaDto.getIdGenericoAccionista()!=null && accionistaDto.getIdPersona()!=null) {
				
				persona = personaDao.read(accionistaDto.getIdPersona());
				persona.setNombre(accionistaDto.getNombre());
				persona.setApellidoPaterno(accionistaDto.getApellidoPaterno());
				if(accionistaDto.getApellidoMaterno() == null || "".equals(accionistaDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(accionistaDto.getApellidoMaterno());
				}
				persona.setRfc(accionistaDto.getRfc());
				persona.setFechaNacimiento(accionistaDto.getFechaNacimiento());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				
				accionista = prestadoraServicioAccionistaDao.read(accionistaDto.getIdGenericoAccionista());
				accionista.setPrestadoraServicio(new PrestadoraServicio(accionistaDto.getPrestadoraServicio().getIdPrestadoraServicio()));
				accionista.setPersona(persona);
				accionista.setPorcentajeAccion(accionistaDto.getPorcentajeAccion());
				accionista.setUsuarioModificacion(new Usuario(idUsuarioALta));
				accionista.setFechaModificacion(new Date());
				accionista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioAccionistaDao.update(accionista);
				
				
				Domicilio domicilio =  new Domicilio();
				if(accionistaDto.getDomicilioDto()!=null &&
						(accionistaDto.getDomicilioDto().getIdDomicilio() !=null && accionistaDto.getDomicilioDto().getIdDomicilio() >= 1)) {
					domicilio = domicilioDao.read(accionistaDto.getDomicilioDto().getIdDomicilio());
					domicilio.setCodigoPostal(accionistaDto.getDomicilioDto().getCodigoPostal());
					domicilio.setCalle(accionistaDto.getDomicilioDto().getCalle());
					domicilio.setNumeroExterior(accionistaDto.getDomicilioDto().getNumeroExterior());
					domicilio.setNumeroInterior(accionistaDto.getDomicilioDto().getNumeroInterior());
					domicilio.setColonia(accionistaDto.getDomicilioDto().getColonia());
					CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
					catEntidadFederativa.setIdEntidadFederativa(accionistaDto.getDomicilioDto().getIdEntidadFederativa());
					domicilio.setCatEntidadFederativa(catEntidadFederativa);
					CatMunicipios catMunicipios = new CatMunicipios();
					catMunicipios.setIdCatMunicipios(accionistaDto.getDomicilioDto().getCatMunicipios().getIdCatGeneral());
					domicilio.setCatMunicipios(catMunicipios);			
					domicilio.setUsuarioByUsuarioModificacion(new Usuario(idUsuarioALta));
					domicilio.setFechaModificacion(new Date());
					domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					domicilioDao.createOrUpdate(domicilio);
					
				}else {
					
					domicilio.setCodigoPostal(accionistaDto.getDomicilioDto().getCodigoPostal());
					domicilio.setCalle(accionistaDto.getDomicilioDto().getCalle());
					domicilio.setNumeroExterior(accionistaDto.getDomicilioDto().getNumeroExterior());
					domicilio.setNumeroInterior(accionistaDto.getDomicilioDto().getNumeroInterior());
					domicilio.setColonia(accionistaDto.getDomicilioDto().getColonia());
					CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
					catEntidadFederativa.setIdEntidadFederativa(accionistaDto.getDomicilioDto().getIdEntidadFederativa());
					domicilio.setCatEntidadFederativa(catEntidadFederativa);
					CatMunicipios catMunicipios = new CatMunicipios();
					catMunicipios.setIdCatMunicipios(accionistaDto.getDomicilioDto().getCatMunicipios().getIdCatGeneral());
					domicilio.setCatMunicipios(catMunicipios);			
					domicilio.setUsuarioByUsaurioAlta(new Usuario(idUsuarioALta));
					domicilio.setFechaAlta(new Date());
					domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					Long idDomicilio = domicilioDao.create(domicilio);
					
					PrestadoraServicioAccionistaDomicilio accionistaDomicilio = new PrestadoraServicioAccionistaDomicilio();
					accionistaDomicilio.setPrestadoraServicioAccionista(accionista);
					accionistaDomicilio.setDomicilio(new Domicilio(idDomicilio));
					accionistaDomicilio.setFechaAlta(new Date());
					accionistaDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					accionistaDomicilio.setUsuarioAlta(new Usuario(idUsuarioALta));
					prestadoraServicioAccionistaDomicilioDao.create(accionistaDomicilio);
					
				}
						


			}else {
				
				persona.setNombre(accionistaDto.getNombre());
				persona.setApellidoPaterno(accionistaDto.getApellidoPaterno());
				if(accionistaDto.getApellidoMaterno() == null || "".equals(accionistaDto.getApellidoMaterno().trim())) {
					persona.setApellidoMaterno(null);
				}else {
					persona.setApellidoMaterno(accionistaDto.getApellidoMaterno());
				}
				persona.setRfc(accionistaDto.getRfc());
				
				
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String format = sf.format(accionistaDto.getFechaNacimiento());
				Date parse = sf.parse(format);
				persona.setFechaNacimiento(parse);
				persona.setUsuarioAlta(idUsuarioALta);
				persona.setFechaAlta(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				Long idPersona = personaDao.create(persona);
				
				accionista.setPrestadoraServicio(new PrestadoraServicio(accionistaDto.getPrestadoraServicio().getIdPrestadoraServicio()));
				accionista.setPersona(new Persona(idPersona));
				accionista.setPorcentajeAccion(accionistaDto.getPorcentajeAccion());
				accionista.setUsuarioAlta(new Usuario(idUsuarioALta));
				accionista.setFechaAlta(new Date());
				accionista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioAccionistaDao.create(accionista);
				
				if(accionistaDto.getDomicilioDto()!=null && accionistaDto.getDomicilioDto().getCodigoPostal() !=null) {
					Domicilio domicilio = new Domicilio();
					domicilio.setCodigoPostal(accionistaDto.getDomicilioDto().getCodigoPostal());
					domicilio.setCalle(accionistaDto.getDomicilioDto().getCalle());
					domicilio.setNumeroExterior(accionistaDto.getDomicilioDto().getNumeroExterior());
					domicilio.setNumeroInterior(accionistaDto.getDomicilioDto().getNumeroInterior());
					domicilio.setColonia(accionistaDto.getDomicilioDto().getColonia());
					CatEntidadFederativa catEntidadFederativa = new CatEntidadFederativa();
					catEntidadFederativa.setIdEntidadFederativa(accionistaDto.getDomicilioDto().getIdEntidadFederativa());
					domicilio.setCatEntidadFederativa(catEntidadFederativa);
					CatMunicipios catMunicipios = new CatMunicipios();
					catMunicipios.setIdCatMunicipios(accionistaDto.getDomicilioDto().getCatMunicipios().getIdCatGeneral());
					domicilio.setCatMunicipios(catMunicipios);			
					domicilio.setUsuarioByUsaurioAlta(new Usuario(idUsuarioALta));
					domicilio.setFechaAlta(new Date());
					domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					Long idDomicilio = domicilioDao.create(domicilio);
					
					PrestadoraServicioAccionistaDomicilio accionistaDomicilio = new PrestadoraServicioAccionistaDomicilio();
					accionistaDomicilio.setPrestadoraServicioAccionista(accionista);
					accionistaDomicilio.setDomicilio(new Domicilio(idDomicilio));
					accionistaDomicilio.setFechaAlta(new Date());
					accionistaDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					accionistaDomicilio.setUsuarioAlta(new Usuario(idUsuarioALta));
					prestadoraServicioAccionistaDomicilioDao.create(accionistaDomicilio);
				}

			}
			

			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarAccionistas ", e);
			return  Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional
	public Boolean eliminarAccionista(AccionistaDto accionistaDto, Long idUsuarioALta) {
		try {
			
			Persona persona = new Persona();
			PrestadoraServicioAccionista accionista = new PrestadoraServicioAccionista();
			
			if(accionistaDto.getIdGenericoAccionista()!=null && accionistaDto.getIdPersona()!=null) {
				
				persona = personaDao.read(accionistaDto.getIdPersona());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.INACTIVO.getIdEstatus()));
				personaDao.update(persona);
				
				accionista = prestadoraServicioAccionistaDao.read(accionistaDto.getIdGenericoAccionista());
				accionista.setUsuarioModificacion(new Usuario(idUsuarioALta));
				accionista.setFechaModificacion(new Date());
				accionista.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				prestadoraServicioAccionistaDao.update(accionista);
			
			}else {
				LOGGER.error("Ocurrio un error en eliminarAccionista accionistaDto.getIdGenericoAccionista() && apoderadoLegalDto.getIdPersona() vienen null");
				return Boolean.FALSE;
			}
			
			
			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarAccionista ", e);
			return  Boolean.FALSE;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<AccionistaDto> getListAccionistasByIdPrestadoraServicio(Long idPrestadora) {
		try {
			
			List<AccionistaDto> lista = prestadoraServicioAccionistaDao.getListAccionistaByIdPrestadoraServicio(idPrestadora);

			return lista;
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListAccionistasByIdPrestadoraServicio ", e);
			return  Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean eliminarPrestadora(Long idPrestadora, Long idUsuarioAplicativo) {
		try {
			PrestadoraServicio prestadora = new PrestadoraServicio();
			
			if(idPrestadora!=null) {
				prestadora = prestadoraServicioDao.read(idPrestadora);
				prestadora.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				prestadora.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				prestadora.setFechaModificacion(new Date());
				prestadoraServicioDao.update(prestadora);
				
				PrestadoraServicioEstatus prestadoraServicioEstatus = new PrestadoraServicioEstatus();
				prestadoraServicioEstatus.setPrestadoraServicio(prestadora);
				prestadoraServicioEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.PRESTADORA_SERVICIO_ELIMINADA.getIdEstatus()));
				prestadoraServicioEstatus.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				prestadoraServicioEstatus.setFechaAlta(new Date());
				prestadoraServicioEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				prestadoraServicioEstatusDao.create(prestadoraServicioEstatus);
				
				CelulaPrestadoraServicio celulaPrestadora = celulaPrestadoraServicioDao.getCelulaPrestByIdPrestadora(idPrestadora);
				celulaPrestadora.setFechaModificacion(new Date());
				celulaPrestadora.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				celulaPrestadora.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				celulaPrestadoraServicioDao.update(celulaPrestadora);
				
				return Boolean.TRUE;
			}else {
				LOGGER.error("Ocurrio un error en eliminarPrestadora, idPrestadora viene null");
				return Boolean.FALSE;
			}
			
			
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en eliminarPrestadora ", e);
			return Boolean.FALSE;
		}

	}
	
	@Transactional
	public void guardarDocumentosPrestadora(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		Map<String,String> contextos = new HashMap<String,String>();
		
		contextos.put("1",String.valueOf(documento.getIdPrestadora()));
		contextos.put("2", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
	    
		
	    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
	    
	    PrestadoraServicioDocto prestadora = new PrestadoraServicioDocto();
	    
	    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
	    
	    if(documento.getIdPrestadoraServicioDoctumentos() != null && documento.getIdPrestadoraServicioDoctumentos()!=0L) {
	    	prestadora = prestadoraServicioDoctoDao.read(documento.getIdPrestadoraServicioDoctumentos());
	    }
	    
	    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
	    prestadora.setDefinicionDocumento(definicionDocumento);
	    prestadora.setIdCMS(idCMS);
	    prestadora.setIdPrestadoraServicioDocumento(documento.getIdPrestadoraServicioDoctumentos());
	    prestadora.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	    prestadora.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
	    
	    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
	    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());
	    prestadora.setPrestadoraServicio(prestadoraServicio);
	    
	    
	    if(documento.getIdPrestadoraServicioDoctumentos() != null && documento.getIdPrestadoraServicioDoctumentos()!=0L) {
	    	Usuario usuarioModificacion = new Usuario();
		    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
	    	prestadora.setUsuarioModificacion(usuarioModificacion);
	    	prestadora.setFechaModificacion(new Date());
	    	prestadoraServicioDoctoDao.update(prestadora);
	    	
	    }else {
	    	
	    	Usuario usuarioalta = new Usuario();
		    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
	    	prestadora.setUsuarioAlta(usuarioalta);
	    	prestadora.setFechaAlta(new Date());
	    	
	    	prestadoraServicioDoctoDao.create(prestadora);
	    }

	}

	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPrestadoraRepresentante(Long idPrestadora) {
		return prestadoraServicioRepresentanteLegalDocumentoDao.listDocumentosRepresentantePrestadora(idPrestadora);
	}
	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(Long idPrestadora, Long idPrestadoraServRepLeg) {
		return prestadoraServicioRepresentanteLegalDocumentoDao.listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(idPrestadora, idPrestadoraServRepLeg);
	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPrestadoraApoderado(Long idPrestadora, Long idPrestadoraServApodLeg) {
		return prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao.listDocumentosApoderadoPrestadora(idPrestadora, idPrestadoraServApodLeg);
	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPrestadoraAccionista(Long idPrestadora, Long idPrestadoraServaccionista) {
		return prestadoraServicioAccionistaDocumentoDao.listDocumentosApoderadoPrestadora(idPrestadora, idPrestadoraServaccionista);
	}

	@Transactional
	public void guardarDocumentosPrestadoraApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		Map<String,String> contextos = new HashMap<String,String>();
		
		
		contextos.put("1","apoderadoLegal");
		contextos.put("2",String.valueOf(documento.getIdPrestadora()));
		contextos.put("3",String.valueOf(documento.getIdPrestadoraServicioApoderadoLegal()));
		contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
		
	    
	    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
	    
	    PrestadoraServicioApoderadoLegalDocumento apoderadoDocumento = new PrestadoraServicioApoderadoLegalDocumento();
	    PrestadoraServicioApoderadoLegal apoderado = new PrestadoraServicioApoderadoLegal();
	    
	    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
	    
	    if(documento.getIdPrestadoraServicioApoderadoLegalDoctumento() != null && documento.getIdPrestadoraServicioApoderadoLegalDoctumento()!=0L) {
	    	apoderadoDocumento = prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao.read(documento.getIdPrestadoraServicioApoderadoLegalDoctumento());
	    }
	    
	    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
	    apoderadoDocumento.setDefinicionDocumento(definicionDocumento);
	    apoderadoDocumento.setIdCMS(idCMS);
	    apoderadoDocumento.setIdPrestadoraServicioApoderadoLegalDocumento(documento.getIdPrestadoraServicioApoderadoLegalDoctumento());
	    apoderadoDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	    apoderadoDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
	    
	    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
	    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());
	    apoderado.setPrestadoraServicio(prestadoraServicio);
	    apoderado.setIdPrestadoraServicioApoderadoLegal(documento.getIdPrestadoraServicioApoderadoLegal());
	    
	    apoderadoDocumento.setPrestadoraServicioApoderadoLegal(apoderado);
	    
	    if(documento.getIdPrestadoraServicioApoderadoLegalDoctumento() != null && documento.getIdPrestadoraServicioApoderadoLegalDoctumento()!=0L) {
	    	Usuario usuarioModificacion = new Usuario();
		    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    apoderadoDocumento.setUsuarioModificacion(usuarioModificacion);
		    apoderadoDocumento.setFechaModificacion(new Date());
	    	prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao.update(apoderadoDocumento);
	    	
	    }else {
	    	
	    	Usuario usuarioalta = new Usuario();
		    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    apoderadoDocumento.setFechaAlta(new Date());
		    apoderadoDocumento.setUsuarioAlta(usuarioalta);
	    	
	    	prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao.create(apoderadoDocumento);
	    }
	}
	
	@Transactional
	public void eliminarDocumentosPrestadoraApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
	    
	    if(documento.getIdPrestadoraServicioApoderadoLegalDoctumento() != null && documento.getIdPrestadoraServicioApoderadoLegalDoctumento()!=0L) {	    	
	    	prestadoraServicioApoderadoLegalDocumentoDaoDocumentoDao.delete(documento.getIdPrestadoraServicioApoderadoLegalDoctumento());
	    	
	    }
	}
	
	@Transactional
	public void guardarDocumentosPrestadoraRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		try {
			
			Map<String,String> contextos = new HashMap<String,String>();
			
			
			contextos.put("1","representanteLegal");
			contextos.put("2",String.valueOf(documento.getIdPrestadora()));
			contextos.put("3",String.valueOf(documento.getIdPrestadoraServicioRepresentanteLegal()));
			contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
			
		    
		    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
		    
		    PrestadoraServicioRepresentanteLegalDocumento representanteDocumento = new PrestadoraServicioRepresentanteLegalDocumento();
		    PrestadoraServicioRepresentanteLegal representante = new PrestadoraServicioRepresentanteLegal();
		    
		    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
		    
		    if(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento() != null && documento.getIdPrestadoraServicioRepresentanteLegalDoctumento()!=0L) {
		    	representanteDocumento = prestadoraServicioRepresentanteLegalDocumentoDao.read(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento());
		    }
		    
		    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		    representanteDocumento.setDefinicionDocumento(definicionDocumento);
		    representanteDocumento.setIdCMS(idCMS);
		    representanteDocumento.setIdPrestadoraServicioRepresentanteLegalDocumento(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento());
		    representanteDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		    representanteDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
		    
		    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
		    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());
		    representante.setPrestadoraServicio(prestadoraServicio);
		    representante.setIdPrestadoraServicioRepresentanteLegal(documento.getIdPrestadoraServicioRepresentanteLegal());
		    
		    representanteDocumento.setPrestadoraServicioRepresentanteLegal(representante);
		    
		    if(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento() != null && documento.getIdPrestadoraServicioRepresentanteLegalDoctumento()!=0L) {
		    	Usuario usuarioModificacion = new Usuario();
			    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setUsuarioModificacion(usuarioModificacion);
			    representanteDocumento.setFechaModificacion(new Date());
			    prestadoraServicioRepresentanteLegalDocumentoDao.update(representanteDocumento);
		    	
		    }else {
		    	
		    	Usuario usuarioalta = new Usuario();
			    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setFechaAlta(new Date());
			    representanteDocumento.setUsuarioAlta(usuarioalta);
		    	
			    prestadoraServicioRepresentanteLegalDocumentoDao.create(representanteDocumento);
		    }
			
		}catch (IOException io) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante ", io);
		}
		
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante ", e);
		}
		

	}
	
	@Transactional
	public void eliminarDocumentosPrestadoraRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
	    
	    if(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento() != null && documento.getIdPrestadoraServicioRepresentanteLegalDoctumento()!=0L) {	    	
	    	prestadoraServicioRepresentanteLegalDocumentoDao.delete(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento());
	    	
	    }
	}
	
	@Transactional
	public void guardarDocumentosPrestadoraAccionista(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		Map<String,String> contextos = new HashMap<String,String>();
		
		
		contextos.put("1","accionista");
		contextos.put("2",String.valueOf(documento.getIdPrestadora()));
		contextos.put("3",String.valueOf(documento.getIdPrestadoraServicioAccionista()));
		contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
		
	    
	    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
	    
	    PrestadoraServicioAccionistaDocumento accionistaDocumento = new PrestadoraServicioAccionistaDocumento();
	    PrestadoraServicioAccionista accionista = new PrestadoraServicioAccionista();
	    
	    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
	    
	    if(documento.getIdPrestadoraServicioAccionistaDoctumento()!= null && documento.getIdPrestadoraServicioAccionistaDoctumento()!=0L) {
	    	accionistaDocumento = prestadoraServicioAccionistaDocumentoDao.read(documento.getIdPrestadoraServicioAccionistaDoctumento());
	    }
	    
	    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
	    accionistaDocumento.setDefinicionDocumento(definicionDocumento);
	    	    
	    accionistaDocumento.setIdCMS(idCMS);
	    accionistaDocumento.setIdPrestadoraServicioAccionistaDocumento(documento.getIdPrestadoraServicioAccionistaDoctumento());
	    accionistaDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	    accionistaDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
	    
	    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
	    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());
	    accionista.setPrestadoraServicio(prestadoraServicio);
	    accionista.setIdPrestadoraServicioAccionista(documento.getIdPrestadoraServicioAccionista());
	    
	    accionistaDocumento.setPrestadoraServicioAccionista(accionista);
	    
	    if(documento.getIdPrestadoraServicioAccionistaDoctumento() != null && documento.getIdPrestadoraServicioAccionistaDoctumento()!=0L) {
	    	Usuario usuarioModificacion = new Usuario();
		    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    accionistaDocumento.setUsuarioModificacion(usuarioModificacion);
		    accionistaDocumento.setFechaModificacion(new Date());
		    prestadoraServicioAccionistaDocumentoDao.update(accionistaDocumento);
	    	
	    }else {
	    	
	    	Usuario usuarioalta = new Usuario();
		    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    accionistaDocumento.setFechaAlta(new Date());
		    accionistaDocumento.setUsuarioAlta(usuarioalta);
		    accionistaDocumento.setFechaAlta(new Date());
	    	
		    prestadoraServicioAccionistaDocumentoDao.create(accionistaDocumento);
	    }
	}
	
	@Transactional
	public void eliminarDocumentosAccionista(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
	    
	    if(documento.getIdPrestadoraServicioAccionistaDoctumento() != null && documento.getIdPrestadoraServicioAccionistaDoctumento()!=0L) {	    	
	    	prestadoraServicioAccionistaDocumentoDao.delete(documento.getIdPrestadoraServicioAccionistaDoctumento());
	    	
	    }
	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosPrestadoraDefinicionDocto(String listaDefinicionDocumento, Long idPrestadora) {
		return prestadoraServicioDao.listDocumentosPrestadoraDefinicionDocto(listaDefinicionDocumento, idPrestadora); 	
	}
	
	@Override
	@Transactional
	public void eliminarDocumentos(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) {
		prestadoraServicioDoctoDao.delete(documento.getIdPrestadoraServicioDoctumentos());
	}
	
	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listPrestadoraDocumentosRepresentanteCerKey(Long idPrestadoraServRepLeg, Long idPrestadora) {
		return prestadoraServicioRepresentanteLegalDocumentoDao.listPrestadoraDocumentosRepresentanteCerKey(idPrestadoraServRepLeg, idPrestadora); 	
	}
	
	@Override
	@Transactional
	public void guardarDocumentosPrestadoraRepresentanteCerKey(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		
		try {
			
			Map<String,String> contextos = new HashMap<String,String>();
			
			
			contextos.put("1","representanteLegal");
			contextos.put("2",String.valueOf(documento.getIdPrestadora()));
			contextos.put("3",String.valueOf(documento.getIdPrestadoraServicioRepresentanteLegal()));
			contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));
			
		    
		    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);
		    
		    PrestadoraServicioRepresentanteLegalDocumento representanteDocumento = new PrestadoraServicioRepresentanteLegalDocumento();
		    PrestadoraServicioRepresentanteLegal representante = new PrestadoraServicioRepresentanteLegal();
		    
		    DefinicionDocumento definicionDocumento= new DefinicionDocumento();
		    
		    if(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento() != null && documento.getIdPrestadoraServicioRepresentanteLegalDoctumento()!=0L) {
		    	representanteDocumento = prestadoraServicioRepresentanteLegalDocumentoDao.read(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento());
		    }
		    
		    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		    representanteDocumento.setDefinicionDocumento(definicionDocumento);
		    representanteDocumento.setFechaAlta(new Date());
		    representanteDocumento.setFechaModificacion(new Date());
		    representanteDocumento.setIdCMS(idCMS);
		    representanteDocumento.setIdPrestadoraServicioRepresentanteLegalDocumento(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento());
		    representanteDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		    representanteDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());
		    
		    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
		    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());
		    representante.setPrestadoraServicio(prestadoraServicio);
		    representante.setIdPrestadoraServicioRepresentanteLegal(documento.getIdPrestadoraServicioRepresentanteLegal());
		    
		    representanteDocumento.setPrestadoraServicioRepresentanteLegal(representante);
		    
		    if(documento.getIdPrestadoraServicioRepresentanteLegalDoctumento() != null && documento.getIdPrestadoraServicioRepresentanteLegalDoctumento()!=0L) {
		    	Usuario usuarioModificacion = new Usuario();
			    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setUsuarioModificacion(usuarioModificacion);
			    representanteDocumento.setFechaModificacion(new Date());
			    prestadoraServicioRepresentanteLegalDocumentoDao.update(representanteDocumento);
		    	
		    }else {
		    	
		    	Usuario usuarioalta = new Usuario();
			    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setFechaAlta(new Date());
			    representanteDocumento.setUsuarioAlta(usuarioalta);
		    	
			    prestadoraServicioRepresentanteLegalDocumentoDao.create(representanteDocumento);
		    }
			
		}catch (IOException io) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante ", io);
		}
		
		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosPrestadoraRepresentante ", e);
		}
	}
	
	@Transactional(readOnly=false)
	public FichaTecnicaDto fichaTecnica(Long idPrestadora) throws Exception {
		FichaTecnicaDto fichaTecnica = prestadoraServicioDao.fichaTecnica(idPrestadora);
		return fichaTecnica;
	}

	@Transactional(readOnly=false)
	public List<PrestadoraServicioDto> listPrestadoraServicioByIdCelulaAndIdCliente(Long idCelula, Long idCliente, boolean esFondo)  throws Exception{
		return prestadoraServicioDao.listPrestadoraServicioByIdCelulaAndIdCliente(idCelula, idCliente,esFondo );
	}
	
	@Override
	@Transactional(readOnly = true)
	public PrestadoraServicioDto obtenerPrestadoraServicioByStp(Long idPrestadoraServicio) {
		PrestadoraServicio entity = new PrestadoraServicio();
		PrestadoraServicioDto prestadoraServicioDto  = new PrestadoraServicioDto();
		List<PrestadoraServicioStpDto> prestadoraServicioStpDto = new ArrayList<PrestadoraServicioStpDto>();

		List<PrestadoraServicioDto> listPrestServDto = new ArrayList<>();

		entity = prestadoraServicioDao.read(idPrestadoraServicio);

		prestadoraServicioDto.setRfc(entity.getRfc());
		prestadoraServicioDto.setNombreCorto(entity.getNombreCorto());
		prestadoraServicioDto.setRazonSocial(entity.getRazonSocial());
		if (entity.getEsFondo() == 1) {
			prestadoraServicioDto.setEsFondo(Boolean.TRUE);
		} else {
			prestadoraServicioDto.setEsFondo(Boolean.FALSE);
		}
		prestadoraServicioDto.setFechaAlta(entity.getFechaAlta());
		prestadoraServicioDto.setUsuarioAlta(new UsuarioDTO());
		prestadoraServicioDto.getUsuarioAlta().setIdUsuario(entity.getUsuarioAlta().getIdUsuario());
		prestadoraServicioDto.setIndEstatus(entity.getIndEstatus());
		List<CatGeneralDto> catTipoDispersor = catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_DISPERSOR.getCve());
		prestadoraServicioDto.setCatTipoDispersor(catTipoDispersor);

		if (entity.getPrestadoraServicioStp() != null) {
			prestadoraServicioStpDto = prestadoraServicioStpDao
					.convertirPrestadoraServicioStpADto(entity.getPrestadoraServicioStp());
			prestadoraServicioDto.setListprestadoraServicioStpDto(prestadoraServicioStpDto);
		}
		

		return prestadoraServicioDto;
	}

	@Override
	@Transactional
	public void guardarDatosStp(PrestadoraServicioStpDto prestadoraServicioStp, Long idPrestadoraServicio, Long idUsuario) {
		PrestadoraServicio entity = new PrestadoraServicio();
		PrestadoraServicioStp prestadoraServicioEntity = new PrestadoraServicioStp();
		CatGeneral tipoDispersor = new CatGeneral();
		entity = prestadoraServicioDao.read(idPrestadoraServicio);
		tipoDispersor =catGeneralDao.read(prestadoraServicioStp.getIdTipoDispersor());
		Usuario usuario =new Usuario();
		usuario = usuarioDao.read(idUsuario);
		
				
		if(prestadoraServicioStp.getIdPrestadoraServicioStp() != null) {
			prestadoraServicioEntity = prestadoraServicioStpDao.read(prestadoraServicioStp.getIdPrestadoraServicioStp());
			prestadoraServicioEntity.setFechaModificacion(new Date());
			prestadoraServicioEntity.setUsuarioModificacion(usuario);
		}else {
			prestadoraServicioEntity.setPrestadoraServicio(entity);
		    prestadoraServicioEntity.setFechaAlta(new Date());
			prestadoraServicioEntity.setUsuarioAlta(usuario);
			prestadoraServicioEntity.getUsuarioAlta().setIdUsuario(idUsuario);
		}
				
		prestadoraServicioEntity.setClabeCuentaOrdenante(prestadoraServicioStp.getClabeCuentaOrdenante());
		prestadoraServicioEntity.setNombreCentroCostos(prestadoraServicioStp.getNombreCentroCostos());
		prestadoraServicioEntity.setIdTipoDispersor(tipoDispersor);
		
		if(prestadoraServicioStp.getIdTipoDispersor().equals(3101l)) {
			prestadoraServicioEntity.setApiKey(prestadoraServicioStp.getApiKey());
			prestadoraServicioEntity.setClientSecret(prestadoraServicioStp.getClientSecret());
			prestadoraServicioEntity.setClientId(prestadoraServicioStp.getClientId());
			prestadoraServicioEntity.setIdCliente(prestadoraServicioStp.getIdCliente());
			prestadoraServicioEntity.setIdCuentaAhorro(prestadoraServicioStp.getIdCuentaAhorro());
			prestadoraServicioEntity.setPassword(prestadoraServicioStp.getPassword());
			prestadoraServicioEntity.setUserName(prestadoraServicioStp.getUserName());
		}	
		if (prestadoraServicioStp.getActivo()) {
			prestadoraServicioEntity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			prestadoraServicioStpDao.actualizarPrestadora(idPrestadoraServicio);
		}else {
			prestadoraServicioEntity.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
		}
		prestadoraServicioStpDao.createOrUpdate(prestadoraServicioEntity);
		
		
	}

	
	
}
