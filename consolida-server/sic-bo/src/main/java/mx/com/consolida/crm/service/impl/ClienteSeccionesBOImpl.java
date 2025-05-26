package mx.com.consolida.crm.service.impl;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dao.interfaz.CelulaClienteDao;
import mx.com.consolida.crm.dao.interfaz.ClienteApoderadoLegalDao;
import mx.com.consolida.crm.dao.interfaz.ClienteApoderadoLegalDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.ClienteConceptoFacaturacionDao;
import mx.com.consolida.crm.dao.interfaz.ClienteCuentaBancariaDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.ClienteDomicilioDao;
import mx.com.consolida.crm.dao.interfaz.ClienteEstatusDao;
import mx.com.consolida.crm.dao.interfaz.ClienteGiroComercialDao;
import mx.com.consolida.crm.dao.interfaz.ClienteMedioContactoDao;
import mx.com.consolida.crm.dao.interfaz.ClienteNoministaDao;
import mx.com.consolida.crm.dao.interfaz.ClientePrestadoraServicioDao;
import mx.com.consolida.crm.dao.interfaz.ClienteRepresentanteLegalDao;
import mx.com.consolida.crm.dao.interfaz.ClienteRepresentanteLegalDocumentoDao;
import mx.com.consolida.crm.dao.interfaz.ClienteServicioDao;
import mx.com.consolida.crm.dao.interfaz.DomicilioDao;
import mx.com.consolida.crm.dao.interfaz.NominaClienteComisionDao;
import mx.com.consolida.crm.dao.interfaz.NominaClienteDao;
import mx.com.consolida.crm.dao.interfaz.NominaClienteHonorarioDao;
import mx.com.consolida.crm.dao.interfaz.PrestadoraServicioProductoDao;
import mx.com.consolida.crm.dto.CelulaDto;
import mx.com.consolida.crm.dto.ClienteActividadDto;
import mx.com.consolida.crm.dto.ClienteComisionHonorarioDto;
import mx.com.consolida.crm.dto.ClienteConceptoFacaturacionDto;
import mx.com.consolida.crm.dto.ClienteCuentaBancariaDto;
import mx.com.consolida.crm.dto.ClienteCuentasBancariasDto;
import mx.com.consolida.crm.dto.ClienteDto;
import mx.com.consolida.crm.dto.ClienteMedioContactoDto;
import mx.com.consolida.crm.dto.ClienteServicioDto;
import mx.com.consolida.crm.dto.DomicilioComunDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.NoministaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioCuentaBancariaDto;
import mx.com.consolida.crm.dto.PrestadoraServicioProductoDto;
import mx.com.consolida.crm.service.interfaz.ClienteSeccionesBO;
import mx.com.consolida.dao.interfaz.CatEstatusDao;
import mx.com.consolida.dao.interfaz.ClienteTempDao;
import mx.com.consolida.dao.usuario.PersonaDAO;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.entity.CatGeneral;
import mx.com.consolida.entity.CatGrupo;
import mx.com.consolida.entity.CatSubGiroComercial;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.entity.DefinicionDocumento;
import mx.com.consolida.entity.celula.Celula;
import mx.com.consolida.entity.celula.CelulaCliente;
import mx.com.consolida.entity.crm.CatEntidadFederativa;
import mx.com.consolida.entity.crm.CatFormulas;
import mx.com.consolida.entity.crm.CatMunicipios;
import mx.com.consolida.entity.crm.Cliente;
import mx.com.consolida.entity.crm.ClienteApoderadoLegal;
import mx.com.consolida.entity.crm.ClienteApoderadoLegalDocumento;
import mx.com.consolida.entity.crm.ClienteConceptoFacturacion;
import mx.com.consolida.entity.crm.ClienteCuentaBancaria;
import mx.com.consolida.entity.crm.ClienteDocumento;
import mx.com.consolida.entity.crm.ClienteDomicilio;
import mx.com.consolida.entity.crm.ClienteEstatus;
import mx.com.consolida.entity.crm.ClienteGiroComercial;
import mx.com.consolida.entity.crm.ClienteMedioContacto;
import mx.com.consolida.entity.crm.ClienteNominista;
import mx.com.consolida.entity.crm.ClientePrestadoraServicio;
import mx.com.consolida.entity.crm.ClienteRepresentanteLegal;
import mx.com.consolida.entity.crm.ClienteRepresentanteLegalDocumento;
import mx.com.consolida.entity.crm.ClienteServicio;
import mx.com.consolida.entity.crm.Domicilio;
import mx.com.consolida.entity.crm.NominaCliente;
import mx.com.consolida.entity.crm.NominaClienteComision;
import mx.com.consolida.entity.crm.NominaClienteHonorario;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioCuentaBancaria;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioDomicilio;
import mx.com.consolida.entity.prestadoraservicio.PrestadoraServicioProducto;
import mx.com.consolida.entity.seguridad.Persona;
import mx.com.consolida.entity.seguridad.Usuario;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.CatEstatusEnum;
import mx.com.consolida.generico.CatMaestroEnum;
import mx.com.consolida.generico.IndEstatusEnum;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.TipoPersonaEnum;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.service.interfaz.CatalogoBO;

@Service
public class ClienteSeccionesBOImpl implements ClienteSeccionesBO{


	@Autowired
	private DomicilioDao domicilioDao;

	@Autowired
	private ClienteDao clienteDao;

	@Autowired
	private CatalogoBO catBo;

	@Autowired
	private ClienteDomicilioDao clienteDomicilioDao;

	@Autowired
	private ClienteMedioContactoDao clienteMedioContactoDao;

	@Autowired
	private ClienteConceptoFacaturacionDao clienConceptoFacaturacionDao;

	@Autowired
	private CelulaClienteDao celulaClienteDao;

	@Autowired
	private ClienteNoministaDao clienteNoministaDao;

	@Autowired
	private NominaClienteDao nominaClienteDao;

	@Autowired
	private ClienteCuentaBancariaDao clienteCuentaBancariaDao;

	@Autowired
	private ClienteTempDao clienteTempDao;

	@Autowired
	private ClienteGiroComercialDao clienteGiroComercialDao;

	@Autowired
	private ClientePrestadoraServicioDao clientePrestadoraServicioDao;

	@Autowired
	private CatEstatusDao catEstatusDao;

	@Autowired
	private ClienteEstatusDao clienteEstatusDao;

	@Autowired
	private ClienteServicioDao clienteServicioDao;

	@Autowired
	private PrestadoraServicioProductoDao prestadoraServicioProductoDao;

	@Autowired
	private ClienteRepresentanteLegalDao clienteRepresentanteLegalDao;

	@Autowired
	private ClienteApoderadoLegalDao clienteApoderadoLegalDao;

	@Autowired
	private PersonaDAO personaDao;

	@Autowired
	private	NominaClienteComisionDao nominaClienteComisionDao;

	@Autowired
	private	NominaClienteHonorarioDao nominaClienteHonorarioDao;

	@Autowired
	private ClienteRepresentanteLegalDocumentoDao clienteRepresentanteLegalDocumentoDao;

	@Autowired
	private ClienteApoderadoLegalDocumentoDao clienteApoderadoLegalDocumentoDao;

	@Autowired
	private DocumentoServiceBO documentoServiceBO;

	@Autowired
	private ClienteDocumentoDao clienteDocumentoDao;


	private static final Logger LOGGER = LoggerFactory.getLogger(ClienteSeccionesBOImpl.class);

	@Override
	public DomicilioComunDto obtenerEntidadFederativaXCP(String codigoPostal) {
		return clienteDao.obtenerEntidadFederativaXCP(codigoPostal);
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
//			prestadoraServicio = prestadoraServicioDao.read(prestadoraServicioDomicilioDto.getPrestadoraServicio().getIdPrestadoraServicio());


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
//				prestadoraServicioDomicilioDao.createOrUpdate(prestadoraServicioDomicilio);


			}

//			prestadoraServicioDao.createOrUpdate(prestadoraServicio);
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
			ModelMapper mapper = new ModelMapper();

			entity = mapper.map(cuenta, PrestadoraServicioCuentaBancaria.class);
			//Guarda PrestadoraServicioCuenta
			if (cuenta.getIdPrestadoraServicioCuentaBancaria() != null) {
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
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioAlta(usuario);
				entity.setFechaAlta(new Date());
			}

//			prestadoraServicioCuentaBancariaDao.createOrUpdate(entity);

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
			ModelMapper mapper = new ModelMapper();

			entity = mapper.map(cuenta, PrestadoraServicioCuentaBancaria.class);
			//Guarda PrestadoraServicioCuenta
			if (cuenta.getIdPrestadoraServicioCuentaBancaria() != null) {
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

//			prestadoraServicioCuentaBancariaDao.createOrUpdate(entity);

			}catch(Exception e) {
				LOGGER.error(e.getMessage());
			}

	}

	@Override
	public DomicilioComunDto obtenerCatalogosDomicilio(DomicilioComunDto clienteDomicilioDto) {
		clienteDomicilioDto.setEntidadFederativa(catBo.obtenerCatEntidadFederativaByClvMaestro(CatMaestroEnum.ENTIDAD_FEDERATIVA.getCve()));
		if(clienteDomicilioDto != null && clienteDomicilioDto.getDomicilio() != null &&	clienteDomicilioDto.getDomicilio().getIdEntidadFederativa() != null) {
			clienteDomicilioDto.setMunicipios(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), clienteDomicilioDto.getDomicilio().getIdEntidadFederativa().toString()));
		}
		if(clienteDomicilioDto != null && clienteDomicilioDto.getDomicilioComercial() != null &&	clienteDomicilioDto.getDomicilioComercial().getIdEntidadFederativa() != null) {
			clienteDomicilioDto.setMunicipiosDomicilioComercial(catBo.obtenerCatMunicipioByClvMaestroByEntidadFed(CatMaestroEnum.MUNICIPIOS.getCve(), clienteDomicilioDto.getDomicilioComercial().getIdEntidadFederativa().toString()));
		}
		return clienteDomicilioDto;
	}

	@Override
	@Transactional(readOnly = true)
	public DomicilioComunDto obtenerDatosDomicilioByCliente(ClienteDto clienteDto) throws BusinessException {

		try {

			Cliente cliente = new Cliente();

			cliente = clienteDao.read(clienteDto.getIdCliente());
			DomicilioComunDto bucarDatosDomicilio = null;
			DomicilioComunDto domicilioComunDto = new DomicilioComunDto();

			if(cliente!=null) {
				if(cliente.getClienteDomicilio()!=null && !cliente.getClienteDomicilio().isEmpty()) {

					for(ClienteDomicilio clienteDomicilio : cliente.getClienteDomicilio()) {
						bucarDatosDomicilio = new DomicilioComunDto();
						bucarDatosDomicilio = clienteDomicilioDao.getDatosDomicilioByIdClienteDomicilio(clienteDomicilio.getIdClienteDomicilio());

						if(bucarDatosDomicilio!= null && bucarDatosDomicilio.getDomicilio()!=null) {
							if( bucarDatosDomicilio.getDomicilio().getCatTipoDomicilio()!=null && bucarDatosDomicilio.getDomicilio().getCatTipoDomicilio().getIdCatGeneral().equals(34L)) {
								domicilioComunDto.setDomicilio(bucarDatosDomicilio.getDomicilio());
							}

							if( bucarDatosDomicilio.getDomicilio().getCatTipoDomicilio()!=null && bucarDatosDomicilio.getDomicilio().getCatTipoDomicilio().getIdCatGeneral().equals(35L)) {
								domicilioComunDto.setDomicilioComercial(bucarDatosDomicilio.getDomicilio());
							}
						}
					}

				}

			}else {
				LOGGER.error("Ocurrio un error en obtenerDatosDomicilioByCliente, entity cliente viene nula al momento de realizar el read ");
				throw new BusinessException("Ocurrio un error en el sistema, Favor de intentarlo mas tarde.");
			}

			if(!cliente.getClienteMedioContacto().isEmpty()) {
				for(ClienteMedioContacto contacto: cliente.getClienteMedioContacto()) {
					if(contacto.getFechaNacimiento() != null) {
						domicilioComunDto.setClienteMedioContactoCEODto(clienteMedioContactoDao.convertirMedioContactoADto(contacto));
					}else {
						domicilioComunDto.setClienteMedioContactoDto(clienteMedioContactoDao.convertirMedioContactoADto(contacto));
					}

			}
			}

			return domicilioComunDto;

		}catch (BusinessException be) {
			LOGGER.error("Ocurrio un error en obtenerDatosDomicilioByCliente ", be);
			throw new BusinessException("Ocurrio un error en el sistema, Favor de intentarlo mas tarde.");
		}catch(Exception e) {
			LOGGER.error("Ocurrio un error en obtenerDatosDomicilioByCliente ", e);
			throw new BusinessException("Ocurrio un error en el sistema, Favor de intentarlo mas tarde.");
		}



	}

	@Override
	@Transactional
	public void guardarDomicilioCliente(DomicilioComunDto domicilioDto, Long idUsuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(idUsuarioAplicativo);



			Domicilio domicilio = new Domicilio();

			if(domicilioDto.getDomicilio()!=null && domicilioDto.getDomicilio().getIdDomicilio()!=null) {

				if(domicilioDto.getDomicilio().getIdDomicilio()!=null) {

					domicilio = domicilioDao.read(domicilioDto.getDomicilio().getIdDomicilio());

					domicilio.setCodigoPostal(domicilioDto.getDomicilio().getCodigoPostal());
					domicilio.setCalle(domicilioDto.getDomicilio().getCalle());
					domicilio.setColonia(domicilioDto.getDomicilio().getColonia());
					domicilio.setNumeroExterior(domicilioDto.getDomicilio().getNumeroExterior()!=null ? domicilioDto.getDomicilio().getNumeroExterior() : null);
					domicilio.setNumeroInterior(domicilioDto.getDomicilio().getNumeroInterior()!=null ? domicilioDto.getDomicilio().getNumeroInterior() : null);
					domicilio.setCatMunicipios(new CatMunicipios(domicilioDto.getDomicilio().getCatMunicipios().getIdCatGeneral()));

					domicilio.setCatEntidadFederativa(new CatEntidadFederativa());
					domicilio.getCatEntidadFederativa().setIdEntidadFederativa(domicilioDto.getDomicilio().getIdEntidadFederativa());
					domicilio.setCatTipoDomicilio(new CatGeneral(34L));
					domicilio.setFechaModificacion(new Date());
					domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					domicilio.setUsuarioByUsuarioModificacion(usuario);
					domicilioDao.update(domicilio);

					ClienteDomicilio clienteDomicilio = clienteDomicilioDao.read(clienteDomicilioDao.getIdClienteDomicilioByIdDomicilio(domicilioDto.getDomicilio().getIdDomicilio()));
					clienteDomicilio.setDomicilio(domicilio);
					clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));
					clienteDomicilio.setUsuarioByUsuarioModificacion(usuario);
					clienteDomicilio.setFechaModificacion(new Date());
					clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					clienteDomicilioDao.update(clienteDomicilio);

				}

			}else if(domicilioDto.getDomicilio()!=null && domicilioDto.getDomicilio().getCodigoPostal()!=null) {

				domicilio =  new Domicilio();
				domicilio.setCodigoPostal(domicilioDto.getDomicilio().getCodigoPostal());
				domicilio.setCalle(domicilioDto.getDomicilio().getCalle());
				domicilio.setColonia(domicilioDto.getDomicilio().getColonia());
				domicilio.setNumeroExterior(domicilioDto.getDomicilio().getNumeroExterior()!=null ? domicilioDto.getDomicilio().getNumeroExterior() : null);
				domicilio.setNumeroInterior(domicilioDto.getDomicilio().getNumeroInterior()!=null ? domicilioDto.getDomicilio().getNumeroInterior() : null);
				domicilio.setCatMunicipios(new CatMunicipios(domicilioDto.getDomicilio().getCatMunicipios().getIdCatGeneral()));

				domicilio.setCatEntidadFederativa(new CatEntidadFederativa());
				domicilio.getCatEntidadFederativa().setIdEntidadFederativa(domicilioDto.getDomicilio().getIdEntidadFederativa());
				domicilio.setCatTipoDomicilio(new CatGeneral(34L));
				domicilio.setFechaAlta(new Date());
				domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				domicilio.setUsuarioByUsaurioAlta(usuario);

				domicilio.setIdDomicilio(domicilioDao.crearActualizarDomicilio(domicilio));

				ClienteDomicilio clienteDomicilio =  new ClienteDomicilio();
				clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));

				clienteDomicilio.setDomicilio(domicilio);
				clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));

				clienteDomicilio.setUsuarioByUsuarioAlta(usuario);
				clienteDomicilio.setFechaAlta(new Date());
				clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteDomicilioDao.create(clienteDomicilio);

			}else {
				LOGGER.error("Ocurrio un error en guardarDomicilioCliente domicilio fiscal vienen nulos");
			}

		}catch(Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void guardarDomicilioComercial(DomicilioComunDto domicilioDto, Long idUsuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(idUsuarioAplicativo);



			Domicilio domicilio = new Domicilio();

			if(domicilioDto.getDomicilioComercial()!=null && domicilioDto.getDomicilioComercial().getIdDomicilio()!=null) {

				domicilio = domicilioDao.read(domicilioDto.getDomicilioComercial().getIdDomicilio());

				domicilio.setCodigoPostal(domicilioDto.getDomicilioComercial().getCodigoPostal());
				domicilio.setCalle(domicilioDto.getDomicilioComercial().getCalle());
				domicilio.setColonia(domicilioDto.getDomicilioComercial().getColonia());
				domicilio.setNumeroExterior(domicilioDto.getDomicilioComercial().getNumeroExterior()!=null ? domicilioDto.getDomicilioComercial().getNumeroExterior() : null);
				domicilio.setNumeroInterior(domicilioDto.getDomicilioComercial().getNumeroInterior()!=null ? domicilioDto.getDomicilioComercial().getNumeroInterior() : null);
				domicilio.setCatMunicipios(new CatMunicipios(domicilioDto.getDomicilioComercial().getCatMunicipios().getIdCatGeneral()));

				domicilio.setCatEntidadFederativa(new CatEntidadFederativa());
				domicilio.getCatEntidadFederativa().setIdEntidadFederativa(domicilioDto.getDomicilioComercial().getIdEntidadFederativa());
				domicilio.setCatTipoDomicilio(new CatGeneral(35L));
				domicilio.setFechaModificacion(new Date());
				domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				domicilio.setUsuarioByUsuarioModificacion(usuario);
				domicilioDao.update(domicilio);

				ClienteDomicilio clienteDomicilio = clienteDomicilioDao.read(clienteDomicilioDao.getIdClienteDomicilioByIdDomicilio(domicilioDto.getDomicilioComercial().getIdDomicilio()));
				clienteDomicilio.setDomicilio(domicilio);
				clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));
				clienteDomicilio.setUsuarioByUsuarioModificacion(usuario);
				clienteDomicilio.setFechaModificacion(new Date());
				clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteDomicilioDao.update(clienteDomicilio);


			}else if(domicilioDto.getDomicilioComercial()!=null && domicilioDto.getDomicilioComercial().getCodigoPostal()!=null) {

				domicilio =  new Domicilio();
				domicilio.setCodigoPostal(domicilioDto.getDomicilioComercial().getCodigoPostal());
				domicilio.setCalle(domicilioDto.getDomicilioComercial().getCalle());
				domicilio.setColonia(domicilioDto.getDomicilioComercial().getColonia());
				domicilio.setNumeroExterior(domicilioDto.getDomicilioComercial().getNumeroExterior()!=null ? domicilioDto.getDomicilioComercial().getNumeroExterior() : null);
				domicilio.setNumeroInterior(domicilioDto.getDomicilioComercial().getNumeroInterior()!=null ? domicilioDto.getDomicilioComercial().getNumeroInterior() : null);
				domicilio.setCatMunicipios(new CatMunicipios(domicilioDto.getDomicilioComercial().getCatMunicipios().getIdCatGeneral()));

				domicilio.setCatEntidadFederativa(new CatEntidadFederativa());
				domicilio.getCatEntidadFederativa().setIdEntidadFederativa(domicilioDto.getDomicilioComercial().getIdEntidadFederativa());
				domicilio.setCatTipoDomicilio(new CatGeneral(35L));
				domicilio.setFechaAlta(new Date());
				domicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				domicilio.setUsuarioByUsaurioAlta(usuario);
				domicilio.setIdDomicilio(domicilioDao.crearActualizarDomicilio(domicilio));

				ClienteDomicilio clienteDomicilio =  new ClienteDomicilio();
				clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));

				clienteDomicilio.setDomicilio(domicilio);
				clienteDomicilio.setCliente(new Cliente(domicilioDto.getCliente().getIdCliente()));

				clienteDomicilio.setUsuarioByUsuarioAlta(usuario);
				clienteDomicilio.setFechaAlta(new Date());
				clienteDomicilio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteDomicilioDao.create(clienteDomicilio);
			}else {
				LOGGER.error("Ocurrio un error en guardarDomicilioCliente domicilio comercial vienen nulos");
			}

		}catch(Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	@Transactional
	public void guardarClienteMedioContacto(ClienteMedioContactoDto clienteMedioContactoDto, Long idUsuarioAplicativo) {

			try {
				Usuario usuario =  new Usuario();
				usuario.setIdUsuario(idUsuarioAplicativo);

				ClienteMedioContacto clienteMedioContacto = new ClienteMedioContacto();
				Cliente clienteEntity = new Cliente();

				clienteEntity = clienteDao.read(clienteMedioContactoDto.getCliente().getIdCliente());
				clienteMedioContactoDto.setCliente(new ClienteDto());

				if(clienteMedioContactoDto.getIdClienteMedioContacto() != null) {
					clienteMedioContacto = clienteMedioContactoDao.read(clienteMedioContactoDto.getIdClienteMedioContacto());
					clienteMedioContacto.setApellidoPaterno(clienteMedioContactoDto.getApellidoPaterno());
					if(clienteMedioContactoDto.getApellidoMaterno() != null && clienteMedioContactoDto.getApellidoMaterno() != "") {
						clienteMedioContacto.setApellidoMaterno(clienteMedioContactoDto.getApellidoMaterno());
					}
					clienteMedioContacto.setCorreo(clienteMedioContactoDto.getCorreo());
					clienteMedioContacto.setNombre(clienteMedioContactoDto.getNombre());
					clienteMedioContacto.setTelefono(clienteMedioContactoDto.getTelefono());
					clienteMedioContacto.setCliente(clienteEntity);
					clienteMedioContacto.setFechaModificacion(new Date());
					clienteMedioContacto.setUsuarioModificacion(usuario);
					clienteMedioContacto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					if(clienteMedioContactoDto.getFechaNacimiento() != null) {
						clienteMedioContacto.setFechaNacimiento(clienteMedioContactoDto.getFechaNacimiento());
						clienteMedioContacto.setEsCeo(1);
					}

					clienteMedioContactoDao.update(clienteMedioContacto);

				}else {

					clienteMedioContacto.setCliente(clienteEntity);
					clienteMedioContacto.setNombre(clienteMedioContactoDto.getNombre());
					clienteMedioContacto.setApellidoPaterno(clienteMedioContactoDto.getApellidoPaterno());
					clienteMedioContacto.setApellidoMaterno(clienteMedioContactoDto.getApellidoMaterno()!=null ? clienteMedioContactoDto.getApellidoMaterno() : null);
					clienteMedioContacto.setCorreo(clienteMedioContactoDto.getCorreo());
					clienteMedioContacto.setTelefono(clienteMedioContactoDto.getTelefono());
					clienteMedioContacto.setFechaAlta(new Date());
					clienteMedioContacto.setUsuarioAlta(usuario);
					clienteMedioContacto.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					if(clienteMedioContactoDto.getFechaNacimiento() != null) {
						clienteMedioContacto.setFechaNacimiento(clienteMedioContactoDto.getFechaNacimiento());
						clienteMedioContacto.setEsCeo(1);
					}
					clienteMedioContactoDao.create(clienteMedioContacto);
				}

			}catch(Exception e) {
				LOGGER.error(e.getMessage());
			}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarClienteConceptoFacturacion(ClienteConceptoFacaturacionDto clienteConcepfact, Long idUsuarioAplicativo) {
		try {

			ClienteConceptoFacturacion clienConcepFact = new ClienteConceptoFacturacion();

			if(clienteConcepfact.getIdConceptoFacturacionCliente()!=null && clienteConcepfact.getEsEliminar() == null) {

				clienConcepFact = clienConceptoFacaturacionDao.read(clienteConcepfact.getIdConceptoFacturacionCliente());
				clienConcepFact.setCliente(clienteDao.read(clienteConcepfact.getClienteDto().getIdCliente()));
				clienConcepFact.setConceptoFacturacion(clienteConcepfact.getDescConceptoFacturacion());
				clienConcepFact.setFechaAlta(new Date());
				clienConcepFact.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienConceptoFacaturacionDao.update(clienConcepFact);

				return Boolean.TRUE;

			}else if(clienteConcepfact.getEsEliminar()) {

				clienConcepFact = clienConceptoFacaturacionDao.read(clienteConcepfact.getIdConceptoFacturacionCliente());
				clienConcepFact.setCliente(clienteDao.read(clienteConcepfact.getClienteDto().getIdCliente()));
				clienConcepFact.setConceptoFacturacion(clienteConcepfact.getDescConceptoFacturacion());
				clienConcepFact.setFechaModificacion(new Date());
				clienConcepFact.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());

				clienConceptoFacaturacionDao.update(clienConcepFact);

				return Boolean.TRUE;

			}else {

				clienConcepFact.setCliente(clienteDao.read(clienteConcepfact.getClienteDto().getIdCliente()));
				clienConcepFact.setConceptoFacturacion(clienteConcepfact.getDescConceptoFacturacion());
				clienConcepFact.setFechaAlta(new Date());
				clienConcepFact.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				clienConcepFact.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());

				clienConceptoFacaturacionDao.create(clienConcepFact);

				return Boolean.TRUE;
			}


		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarClienteConceptoFacturacion ",e );
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteConceptoFacaturacionDto> getListtConceptosFacturacionByCliente(Long idCliente) {
		try {

			List<ClienteConceptoFacaturacionDto>  lista = clienConceptoFacaturacionDao.getListConceptoFactruByCliente(idCliente);

			return lista;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarClienteConceptoFacturacion ",e );
			return Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardaActualizaDatosgenerales(ClienteDto clienteDto, Long idUsuarioAplicativo) {
		try {


			Cliente cliente = new Cliente();
			ClienteTemp clienteTemp = new ClienteTemp();
//			CelulaCliente celulaCliente = new CelulaCliente();
//			ClienteNominista clienteNominista = new ClienteNominista();
			Boolean esActivo;

			if(clienteDto.getIdCliente()!=null) {

				cliente = clienteDao.read(clienteDto.getIdCliente());
				esActivo = cliente.getEsActivo();


				cliente.setIdCliente(clienteDto.getIdCliente());
			}else {
				LOGGER.error("Ocurrio un error en guardaActualizaDatosgenerales, no cuenta con getIdCliente() ");
				return Boolean.FALSE;
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
			cliente.setNombreComercial(clienteDto.getNombreComercial()!=null ? clienteDto.getNombreComercial()  : null);
			cliente.setFechaConstitucionEmpresa(clienteDto.getFechaConstitucionEmpresa()!=null ? clienteDto.getFechaConstitucionEmpresa()   : null);
			cliente.setCveRegistroPatronal(clienteDto.getCveRegistroPatronal()!=null ? clienteDto.getCveRegistroPatronal()  : null);
			cliente.setFechaAlta(clienteDto.getFechaAlta()!=null ? clienteDto.getFechaAlta() : null);
			cliente.setEsActivo(clienteDto.getEsActivo());
			cliente.setActividadEconomicaFinal(clienteDto.getActividadEconomicaFinal());

			CatGrupo catGrupo = new CatGrupo();
			catGrupo.setIdCatGrupo(clienteDto.getCatGrupo().getIdCatGrupo()!=null ? clienteDto.getCatGrupo().getIdCatGrupo()  : null);
			cliente.setCatGrupo(catGrupo);

			CatGeneral catTipoPersona = new CatGeneral();
			catTipoPersona.setIdCatGeneral(clienteDto.getCatTipoPersona().getIdCatGeneral()!=null ? clienteDto.getCatTipoPersona().getIdCatGeneral()  : null);
			cliente.setCatTipoPersona(catTipoPersona);

			CatGeneral catRegimenFiscal = new CatGeneral();
			catRegimenFiscal.setIdCatGeneral(clienteDto.getCatRegimenFiscal().getIdCatGeneral()!=null ? clienteDto.getCatRegimenFiscal().getIdCatGeneral()  : null);
			cliente.setCatRegimenFiscal(catRegimenFiscal);

			CatGeneral catCategoria = new CatGeneral();
			catCategoria.setIdCatGeneral(clienteDto.getCatCategoria().getIdCatGeneral()!=null ? clienteDto.getCatCategoria().getIdCatGeneral()  : null);
			cliente.setCatCategoria(catCategoria);


			Usuario usuarioAlta = new Usuario();
			usuarioAlta.setIdUsuario(idUsuarioAplicativo);


			if(cliente.getIdCliente()!=null) {

				// ACTUALIZA EN CLIENTE
				cliente.setUsuarioModificacion(usuarioAlta);
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				cliente.setFechaModificacion(new Date());
				clienteDao.update(cliente);


				ClienteEstatus clienteEstatus = new ClienteEstatus();
				if(clienteDto.getEsActivo() != esActivo) {
					if(!clienteDto.getEsActivo()) {
						clienteEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.CLIENTE_DESACTIVADO.getIdEstatus()));
					}else if(clienteDto.getEsActivo()) {
						clienteEstatus.setCatEstatus(catEstatusDao.read(CatEstatusEnum.CLIENTE_ACTIVO.getIdEstatus()));
					}

					cliente.setIdCliente(cliente.getIdCliente());
					clienteEstatus.setCliente(cliente);
					clienteEstatus.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					clienteEstatus.setUsuarioAlta(usuarioAlta);
					clienteEstatus.setFechaAlta(new Date());

					clienteEstatusDao.create(clienteEstatus);

				}
			}	else {
				LOGGER.error("Ocurrio un error en guardaActualizaDatosgenerales, no cuenta idCliente ");
				return Boolean.FALSE;
			}


			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaActualizaDatosgenerales ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean guardaDatosCelula(ClienteDto clienteDto, Long idUsuarioAplicativo) {
		try {


			Cliente cliente = new Cliente();
			CelulaCliente celulaCliente = new CelulaCliente();
			ClientePrestadoraServicio clientePrestadora = new ClientePrestadoraServicio();

			if(clienteDto.getIdCliente()!=null) {

				cliente = clienteDao.read(clienteDto.getIdCliente());

			}else {
				LOGGER.error("Ocurrio un error en guardaDatosCelula, no cuenta con getIdCliente() ");
				return Boolean.FALSE;
			}


			Usuario usuarioAlta = new Usuario();
			usuarioAlta.setIdUsuario(idUsuarioAplicativo);


			if(cliente!=null && cliente.getIdCliente()!=null) {

				// ACTUALIZA EN CLIENTE
				cliente.setUsuarioModificacion(usuarioAlta);
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				cliente.setFechaModificacion(new Date());
				clienteDao.update(cliente);

				// ACTUALIZA O REGISTRA EN CELULA CLIENTE
				if(clienteDto.getIdCelulaCliente()!=null) {
					celulaCliente = celulaClienteDao.read(clienteDto.getIdCelulaCliente());
					celulaCliente.setCelula(new Celula(celulaCliente.getCelula().getIdCelula()));
					celulaCliente.setUsuarioModificacion(usuarioAlta);
					celulaCliente.setFechaModificacion(new Date());
					celulaClienteDao.update(celulaCliente);

					clientePrestadora.setCliente(cliente);
					if(clienteDto.getPrestadoraServicio()!=null && clienteDto.getPrestadoraServicio().getIdPrestadoraServicio()!=null) {
						clientePrestadora.setPrestadoraServicio(new PrestadoraServicio(clienteDto.getPrestadoraServicio().getIdPrestadoraServicio()));
					}else {
						clientePrestadora.setPrestadoraServicio(null);
					}
					clientePrestadora.setPrestadoraServicioFondo(new PrestadoraServicio(clienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio()));
					clientePrestadora.setUsuarioAlta(usuarioAlta);
					clientePrestadora.setFechaAlta(new Date());
					clientePrestadora.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					clientePrestadoraServicioDao.create(clientePrestadora);


				}else {

					if(!existeCelulaCliente(cliente.getIdCliente())) {

						if(clienteDto.getCelula()!=null && clienteDto.getCelula().getIdCelula()!=null) {
							celulaCliente.setCelula(new Celula( clienteDto.getCelula().getIdCelula()));
						}else {
							CelulaDto celula = getCelulaBy(clienteDto.getIdCliente());
							celulaCliente.setCelula(new Celula(celula.getIdCelula()));
						}

						celulaCliente.setCliente(cliente);
						celulaCliente.setUsuarioAlta(usuarioAlta);
						celulaCliente.setFechaAlta(new Date());
						celulaCliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
						celulaClienteDao.create(celulaCliente);
					}


					clientePrestadora.setCliente(cliente);
					if(clienteDto.getPrestadoraServicio()!=null && clienteDto.getPrestadoraServicio().getIdPrestadoraServicio()!=null) {
						clientePrestadora.setPrestadoraServicio(new PrestadoraServicio(clienteDto.getPrestadoraServicio().getIdPrestadoraServicio()));
					}else {
						clientePrestadora.setPrestadoraServicio(null);
					}
					clientePrestadora.setPrestadoraServicioFondo(new PrestadoraServicio(clienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio()));
					clientePrestadora.setUsuarioAlta(usuarioAlta);
					clientePrestadora.setFechaAlta(new Date());
					clientePrestadora.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
					clientePrestadoraServicioDao.create(clientePrestadora);
				}



//				// ACTUALIZA EN CELULA CLIENT
//				List<ClienteNominista> listNominista = clienteNoministaDao.getListNomistaClienteByCliente(cliente.getIdCliente());
//
//				if(listNominista!=null && !listNominista.isEmpty()) {
//
//					// ACTUALIZA EN CLIENTE NOMINISTA
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
//				}else {
//					// REGISTRA EN CLIENTE NOMINISTA
//					clienteNominista.setUsuarioNominista(new Usuario(clienteDto.getNoministaDto().getIdNominista()));
//					clienteNominista.setCliente(cliente);
//					clienteNominista.setUsuarioAlta(usuarioAlta);
//					clienteNominista.setFechaAlta(new Date());
//					clienteNominista.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
//					clienteNoministaDao.create(clienteNominista);
//				}


			}	else {
				LOGGER.error("Ocurrio un error en guardaDatosCelula, no cuenta idCliente ");
				return Boolean.FALSE;
			}


			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaDatosCelula ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean eliminarClientePrestadora(Long idClientePrestadoraServicio, Long idUsuarioAplicativo) {
		try {


			ClientePrestadoraServicio clientePrestadora = new ClientePrestadoraServicio();

			if(idClientePrestadoraServicio!=null) {

				clientePrestadora = clientePrestadoraServicioDao.read(idClientePrestadoraServicio);
				clientePrestadora.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				clientePrestadora.setFechaModificacion(new Date());
				clientePrestadora.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				clientePrestadoraServicioDao.update(clientePrestadora);

				return Boolean.TRUE;

			}else {
				LOGGER.error("Ocurrio un error en guardaActualizaNominaCliente, idNominaCliente viene null  ");
				return Boolean.FALSE;
			}



		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaActualizaNominaCliente ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Long guardaActualizaNominaCliente(NominaClienteDto nominaClienteDto, Long idUsuarioAplicativo) {
		try {


			NominaCliente nominaCliente = new NominaCliente();
			Usuario usuario = new Usuario();

			if(nominaClienteDto.getIdNominaCliente()!=null) {

				nominaCliente = nominaClienteDao.read(nominaClienteDto.getIdNominaCliente());
				nominaCliente.setNombreNomina(nominaClienteDto.getNombreNomina());
				nominaCliente.setClaveNomina(nominaClienteDto.getClaveNomina());
				CatGeneral catProductoNomina = new CatGeneral();
				catProductoNomina.setIdCatGeneral(nominaClienteDto.getCatProductoNomina().getIdCatGeneral());
				nominaCliente.setCatProductoNomina(catProductoNomina);

				if(nominaClienteDto.getPrestadoraServicioFondo() !=null && nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() !=null) {

					nominaCliente.setPrestadoraServicio(new PrestadoraServicio(nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio()));

				}else if(nominaClienteDto.getPrestadoraServicio() !=null && nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio() !=null){

					nominaCliente.setPrestadoraServicio(new PrestadoraServicio(nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio()));
				}else{

					nominaCliente.setPrestadoraServicio(null);

				}

				usuario.setIdUsuario(idUsuarioAplicativo);
				nominaCliente.setUsuarioModificacion(usuario);
				nominaCliente.setFechaModificacion(new Date());
				nominaCliente.setUsuarioNominista(new Usuario());


				if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304) {
					nominaCliente.setRequiereFactura(nominaClienteDto.getRequiereFactura());
				}else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
						|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
						|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950){
					nominaCliente.setRequiereFactura(1L);
				}


				nominaCliente.getUsuarioNominista().setIdUsuario(nominaClienteDto.getUsuarioNominista().getIdNominista());
				nominaClienteDao.update(nominaCliente);

			}else {

				nominaCliente.setCliente(new Cliente(nominaClienteDto.getClienteDto().getIdCliente()));
				nominaCliente.setNombreNomina(nominaClienteDto.getNombreNomina());
				nominaCliente.setClaveNomina(nominaClienteDto.getClaveNomina());
				CatGeneral catProductoNomina = new CatGeneral();
				catProductoNomina.setIdCatGeneral(nominaClienteDto.getCatProductoNomina().getIdCatGeneral());
				nominaCliente.setCatProductoNomina(catProductoNomina);


				if(nominaClienteDto.getPrestadoraServicioFondo() !=null && nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio() !=null) {

					nominaCliente.setPrestadoraServicio(new PrestadoraServicio(nominaClienteDto.getPrestadoraServicioFondo().getIdPrestadoraServicio()));

				}else if(nominaClienteDto.getPrestadoraServicio() !=null && nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio() !=null){

					nominaCliente.setPrestadoraServicio(new PrestadoraServicio(nominaClienteDto.getPrestadoraServicio().getIdPrestadoraServicio()));
				}else{

					nominaCliente.setPrestadoraServicio(null);

				}

				usuario.setIdUsuario(idUsuarioAplicativo);
				nominaCliente.setUsuarioAlta(usuario);
				nominaCliente.setFechaAlta(new Date());
				nominaCliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				nominaCliente.setUsuarioNominista(new Usuario());

				if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 304) {
					nominaCliente.setRequiereFactura(Long.valueOf(nominaClienteDto.getRequiereFactura()));
				}else if(nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 306
						|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9949
						|| nominaClienteDto.getCatProductoNomina().getIdCatGeneral() == 9950){
					nominaCliente.setRequiereFactura(1L);
				}

				nominaCliente.getUsuarioNominista().setIdUsuario(nominaClienteDto.getUsuarioNominista().getIdNominista());
				nominaCliente.setIdNominaCliente(nominaClienteDao.create(nominaCliente));

			}


			return nominaCliente.getIdNominaCliente();
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaActualizaNominaCliente ", e);
			return null;
		}
	}

	@Override
	@Transactional
	public Boolean eliminarNominaCliente(Long idNominaCliente, Long idUsuarioAplicativo) {
		try {


			NominaCliente nominaCliente = new NominaCliente();

			if(idNominaCliente!=null) {

				nominaCliente = nominaClienteDao.read(idNominaCliente);
				nominaCliente.setUsuarioModificacion(new Usuario(idUsuarioAplicativo));
				nominaCliente.setFechaModificacion(new Date());
				nominaCliente.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				nominaClienteDao.update(nominaCliente);

				return Boolean.TRUE;

			}else {
				LOGGER.error("Ocurrio un error en guardaActualizaNominaCliente, idNominaCliente viene null  ");
				return Boolean.FALSE;
			}



		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardaActualizaNominaCliente ", e);
			return Boolean.FALSE;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteCuentasBancariasDto obtenerDatosCuentaBancariaByCliente(ClienteDto clienteDto) {
		Cliente entity = new Cliente();
		ClienteCuentasBancariasDto cuenta = new ClienteCuentasBancariasDto();

		entity = clienteDao.read(clienteDto.getIdCliente());

		if(!entity.getClienteCuentaBancaria().isEmpty()) {
			cuenta.setClienteCuentasBancarias(clienteCuentaBancariaDao.convertirCuentaADto(entity.getClienteCuentaBancaria()));
		}

		return cuenta;
	}

	@Override
	public ClienteCuentasBancariasDto obtenerCatalogosCuentaBancaria(ClienteCuentasBancariasDto cuenta) {
		cuenta.setCatTipoCuenta(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.TIPO_CUENTA_BANCARIA.getCve()));
		cuenta.setCatBanco(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.CAT_BANCOS.getCve()));
		return cuenta;
	}

	@Override
	@Transactional
	public void guardarCuentaBancaria(ClienteCuentaBancariaDto cuentaBancariaDto, UsuarioAplicativo usuarioAplicativo) {
		try {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());

			ClienteCuentaBancaria clienteCuentaBancaria = new ClienteCuentaBancaria();

			if (cuentaBancariaDto.getIdClienteCuentaBancaria() != null) {

				clienteCuentaBancaria = clienteCuentaBancariaDao.read(cuentaBancariaDto.getIdClienteCuentaBancaria());
				clienteCuentaBancaria.setCatBanco(new CatGeneral(cuentaBancariaDto.getCatBanco().getIdCatGeneral()));
				clienteCuentaBancaria.setCatTipoCuenta(new CatGeneral(cuentaBancariaDto.getCatTipoCuenta().getIdCatGeneral()));
				clienteCuentaBancaria.setNumeroCuenta(cuentaBancariaDto.getNumeroCuenta());
				clienteCuentaBancaria.setClabeInterbancaria(cuentaBancariaDto.getClabeInterbancaria());
				if(cuentaBancariaDto.getEsPrincipal() != null) {
				clienteCuentaBancaria.setEsPrincipal(cuentaBancariaDto.getEsPrincipal() == true ? 1 : 0);
				}else {
					clienteCuentaBancaria.setEsPrincipal(0);
				}
				clienteCuentaBancaria.setNumeroReferencia(cuentaBancariaDto.getNumeroReferencia());
				clienteCuentaBancaria.setFechaModificacion(new Date());
				clienteCuentaBancaria.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteCuentaBancaria.setUsuarioModificacion(usuario);

				clienteCuentaBancariaDao.update(clienteCuentaBancaria);
			} else {

				clienteCuentaBancaria.setCliente(new Cliente(cuentaBancariaDto.getIdCliente()));
				clienteCuentaBancaria.setCatBanco(new CatGeneral(cuentaBancariaDto.getCatBanco().getIdCatGeneral()));
				clienteCuentaBancaria.setCatTipoCuenta(new CatGeneral(cuentaBancariaDto.getCatTipoCuenta().getIdCatGeneral()));
				clienteCuentaBancaria.setNumeroCuenta(cuentaBancariaDto.getNumeroCuenta());
				clienteCuentaBancaria.setClabeInterbancaria(cuentaBancariaDto.getClabeInterbancaria());
				if(cuentaBancariaDto.getEsPrincipal() != null) {
					clienteCuentaBancaria.setEsPrincipal(cuentaBancariaDto.getEsPrincipal() == true ? 1 : 0);
				}else {
					clienteCuentaBancaria.setEsPrincipal(0);
				}
				clienteCuentaBancaria.setNumeroReferencia(cuentaBancariaDto.getNumeroReferencia());
				clienteCuentaBancaria.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteCuentaBancaria.setUsuarioAlta(usuario);
				clienteCuentaBancaria.setFechaAlta(new Date());

				clienteCuentaBancariaDao.create(clienteCuentaBancaria);
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarCuentaBancaria ", e.getMessage());
		}

	}

	@Override
	@Transactional
	public void eliminarCuentaBancaria(ClienteCuentaBancariaDto cuentaBancariaDto, Long idUsuarioAplicativo){
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(idUsuarioAplicativo);

			ClienteCuentaBancaria clienteCuentaBancaria = new ClienteCuentaBancaria();

			if (cuentaBancariaDto.getIdClienteCuentaBancaria() != null) {

				clienteCuentaBancaria = clienteCuentaBancariaDao.read(cuentaBancariaDto.getIdClienteCuentaBancaria());
				clienteCuentaBancaria.setFechaModificacion(new Date());
				clienteCuentaBancaria.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				clienteCuentaBancaria.setUsuarioModificacion(usuario);

				clienteCuentaBancariaDao.update(clienteCuentaBancaria);
			} else {

				LOGGER.error("Ocurrio un error en eliminarCuentaBancaria, no viene setado el id en cuentaBancariaDto.getIdClienteCuentaBancaria() ");
			}

			}catch(Exception e) {
				LOGGER.error("Ocurrio un error en eliminarCuentaBancaria ", e.getMessage());
			}

	}

	@Override
	public ClienteActividadDto obtenerCatalogosActividad(ClienteActividadDto actividad) {
		actividad.setGirosComerciales(catBo.obtenerCatGeneralByClvMaestro(CatMaestroEnum.GIRO_COMERCIAL.getCve()));
		if(actividad.getIdGiroComercial() != null) {
			actividad.setCatSubGiroComercial(obtenerSubgiroXIdGiro(actividad.getIdGiroComercial().toString()));
		}
		return actividad;
	}

	@Override
	public List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro) {
		return clienteTempDao.obtenerSubgiroXIdGiro(idGiro);
	}

	@Override
	@Transactional
	public void guardarActividad(ClienteActividadDto actividad, UsuarioAplicativo usuarioAplicativo) {
		try {
			Usuario usuario =  new Usuario();
			usuario.setIdUsuario(usuarioAplicativo.getIdUsuario());

			ClienteGiroComercial entity = new ClienteGiroComercial();
			Cliente cliente = new Cliente();

			CatGeneral  catGeneral  = new CatGeneral();
			CatSubGiroComercial  catSubGiroComercial = new CatSubGiroComercial();

			cliente = clienteDao.read(actividad.getCliente().getIdCliente());
			catGeneral.setIdCatGeneral(actividad.getIdGiroComercial());
			catSubGiroComercial.setIdCatSubGiroComercial(actividad.getIdSubGiroComercial());

			if (actividad.getIdClienteGiroComercial() != null) {
				entity = clienteGiroComercialDao.read(actividad.getIdClienteGiroComercial());
				entity.setCliente(cliente);
				entity.setCatGiroComercial(catGeneral);
				entity.setCatSubGiroComercial(catSubGiroComercial);
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
				entity.setCliente(cliente);
				entity.setCatGiroComercial(catGeneral);
				entity.setCatSubGiroComercial(catSubGiroComercial);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				entity.setUsuarioAlta(usuario);
				entity.setFechaAlta(new Date());
			}

			clienteGiroComercialDao.createOrUpdate(entity);

			}catch(Exception e) {
				LOGGER.error(e.getMessage());
			}
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteActividadDto obtenerDatosByActividad(ClienteDto clienteDto) {

		Cliente entity = new Cliente();
		ClienteActividadDto cuenta = new ClienteActividadDto();

		entity = clienteDao.read(clienteDto.getIdCliente());

		if(!entity.getClienteGiroComercial().isEmpty() ) {
			cuenta.setListClienteActividad(clienteGiroComercialDao.convertirGiroADto(entity.getClienteGiroComercial()));
		}
		return cuenta;

	}


//	@Override
//	public ClienteServicioDto obtenerCatalogosProductos(ClienteServicioDto producto) {
//		producto.setCatProductoDto(catBo.obtenerCatProducto());
//		return producto;
//	}

	@Override
	@Transactional
	public Boolean guardarProducto(ClienteServicioDto clienteServicioDto, Long idUsuarioAplicativo) {


		try {

			PrestadoraServicioProductoDto prestadoraProducto =  clienteServicioDto.getPrestadoraServicioProducto();
			ClienteServicio clienteServicio = new ClienteServicio();

			if(prestadoraProducto.getIndEstatus().equals(Boolean.TRUE)) {

				clienteServicio.setCliente(clienteDao.read(clienteServicioDto.getCliente().getIdCliente()));
				clienteServicio.setPrestadoraServicioProducto(new PrestadoraServicioProducto(clienteServicioDto.getPrestadoraServicioProducto().getIdPrestadoraServicioProducto()));
				clienteServicio.setUsuarioAlta(new Usuario(idUsuarioAplicativo));
				clienteServicio.setFechaAlta(new Date());
				clienteServicio.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteServicioDao.createOrUpdate(clienteServicio);

				return Boolean.TRUE;

			}else {
				ClienteServicio id = clienteServicioDao.obtenerClienteServicioXIdPrestServProducto(prestadoraProducto.getIdPrestadoraServicioProducto(), clienteServicioDto.getCliente().getIdCliente());
				clienteServicioDao.delete(id.getIdClienteServicio());
			}

			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en ", e);
			return Boolean.FALSE;
		}

	}

//	@Override
//	@Transactional(readOnly = true)
//	public List<CatProductoDto> obtenerEstatusCatalogoProductos(List<CatProductoDto> catProductoDto, Long idCliente) {
//		List<CatProductoDto> busquedaProducto = new ArrayList<CatProductoDto>();
//		busquedaProducto = clienteProductoDao.obtenerProductosXIdCliente(idCliente);
//
//		for(CatProductoDto productoDto : catProductoDto) {
//			for(CatProductoDto producto : busquedaProducto) {
//
//				if(producto.getIdCatProducto() == productoDto.getIdCatProducto()) {
//					productoDto.setIndEstatus(Boolean.TRUE);
//				}
//			}
//		}
//
//		return catProductoDto;
//	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioProductoDto> obtenerEstatusPrestServProductos(List<PrestadoraServicioProductoDto> catPrestServProductoDto, Long idCliente) {
		List<PrestadoraServicioProductoDto> busquedaProducto = new ArrayList<PrestadoraServicioProductoDto>();

		busquedaProducto = clienteServicioDao.obtenerProductosXIdCliente(idCliente);

		for(PrestadoraServicioProductoDto productoDto : catPrestServProductoDto) {
			for(PrestadoraServicioProductoDto producto : busquedaProducto) {

				if(producto.getIdPrestadoraServicioProducto() == productoDto.getIdPrestadoraServicioProducto()) {
					productoDto.setIndEstatus(Boolean.TRUE);
				}
			}
		}

		return catPrestServProductoDto;
	}

	@Override
	@Transactional
	public void eliminarActividad(ClienteActividadDto actividad) {
//		clienteGiroComercialDao.read(actividad.getIdClienteGiroComercial());
		clienteGiroComercialDao.delete(actividad.getIdClienteGiroComercial());

	}


	@Override
	@Transactional(readOnly = true)
	public MensajeDTO verificarGuardado(ClienteCuentaBancariaDto cuenta) {
		MensajeDTO mensaje = new MensajeDTO();
		Cliente entity = new Cliente();

		entity = clienteDao.read(cuenta.getIdCliente());

		if(!entity.getClienteCuentaBancaria().isEmpty()) {
			List<ClienteCuentaBancariaDto> list = clienteCuentaBancariaDao.convertirCuentaADto(entity.getClienteCuentaBancaria());
			Boolean tienePrincipal = false;
			for(ClienteCuentaBancariaDto cuentaDto: list) {
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
	@Transactional(readOnly = true)
	public NoministaDto getClienteNoministaByidClienteIdNominista(Long idCliente) {
		try {

			NoministaDto noministaDto = new NoministaDto();

			ClienteNominista clienteNominista = clienteNoministaDao.existeNomistaEnCliente(idCliente, null);
			if(clienteNominista!=null && clienteNominista.getIdClienteNominista()!=null) {

				noministaDto.setIdNominista(clienteNominista.getUsuarioNominista().getIdUsuario());
				return noministaDto;

			}else {
				LOGGER.info("EetClienteNoministaByidClienteIdNominista el objeto ClienteNominista viene null");
				return new NoministaDto();
			}

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getClienteNoministaByidClienteIdNominista ", e);
			return new NoministaDto();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeCelulaEnCliente(Long idCliente, Long idCelula) {

		try {

			CelulaCliente celCli = celulaClienteDao.getCelulaByIdCliente(idCliente);


			if(celCli!=null &&  celCli.getIdCelulaCliente()!=null) {

				if(idCelula.equals(celCli.getCelula().getIdCelula())) {
					return Boolean.FALSE;
				}else {
					return Boolean.TRUE;
				}
			}else {
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("OCurrio un error en existeCelulaEnCliente ", e);
			return Boolean.FALSE;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Boolean existeCelulaCliente(Long idCliente) {

		try {

			CelulaCliente celCli = celulaClienteDao.getCelulaByIdCliente(idCliente);


			if(celCli!=null &&  celCli.getIdCelulaCliente()!=null) {

				return Boolean.TRUE;

			}else {
				return Boolean.FALSE;
			}

		}catch (Exception e) {
			LOGGER.error("OCurrio un error en existeCelulaEnCliente ", e);
			return Boolean.FALSE;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public CelulaDto getCelulaBy(Long idCliente) {

		try {

			CelulaCliente celCli = celulaClienteDao.getCelulaByIdCliente(idCliente);

			CelulaDto celula = new CelulaDto();
			if(celCli.getCelula()!=null && celCli.getCelula().getIdCelula()!=null) {
				celula.setIdCelula(celCli.getCelula().getIdCelula());
			}


			return celula;

		}catch (Exception e) {
			LOGGER.error("OCurrio un error en existeCelulaEnCliente ", e);
			return null;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<PrestadoraServicioProductoDto> listaPrestadoraServicioProductosByIdCelula(Long idCelula) {

		try {

			return prestadoraServicioProductoDao.listaPrestadoraServicioProductosByIdCelula(idCelula);

		}catch (Exception e) {
			LOGGER.error("OCurrio un error en listaPrestadoraServicioProductosByIdCelula ", e);
			return Collections.emptyList();
		}

	}

	@Override
	@Transactional(readOnly = true)
	public List<RepresentanteLegalDto> getListRepresentanteLegalByIdCliente(Long idCliente) {
		try {

			return clienteRepresentanteLegalDao.getListRepresentanteLegalByIdCliente(idCliente);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListRepresentanteLegalByIdCliente ", e);
			return  Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta) {
		try {

			Persona persona = new Persona();
			ClienteRepresentanteLegal representante = new ClienteRepresentanteLegal();

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


				representante = clienteRepresentanteLegalDao.read(representanteLegalDto.getIdGenericoRepresentanteLegal());
				representante.setCliente(new Cliente(representanteLegalDto.getClienteDto().getIdCliente()));
				representante.setPersona(persona);
				representante.setContrasenaCertificado(representanteLegalDto.getContrasenaCertificado());
				representante.setUsuarioModificacion(new Usuario(idUsuarioALta));
				representante.setFechaModificacion(new Date());
				representante.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteRepresentanteLegalDao.update(representante);

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

				representante.setCliente(new Cliente(representanteLegalDto.getClienteDto().getIdCliente()));
				representante.setPersona(new Persona(idPersona));
				representante.setContrasenaCertificado(representanteLegalDto.getContrasenaCertificado());
				representante.setUsuarioAlta(new Usuario(idUsuarioALta));
				representante.setFechaAlta(new Date());
				representante.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteRepresentanteLegalDao.create(representante);
			}



			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarRepresentanteLegal ",e );
			return  Boolean.FALSE;
		}
	}

	@Transactional
	public void guardarComision(ClienteComisionHonorarioDto comision, Long idUsuario) {

		try {
			NominaClienteComision entity = new NominaClienteComision();
			NominaCliente nomina = new NominaCliente();
			if(comision.getIdNominaClienteComision() == null) {
				nomina = nominaClienteDao.read(comision.getNominaCliente().getIdNominaCliente());
				entity.setNominaCliente(nomina);
				entity.setCanalVenta(new CatGeneral());
				entity.getCanalVenta().setIdCatGeneral(comision.getCanalVenta().getIdCatGeneral());

				if(comision.getFormulaComision() != null && comision.getFormulaComision().getIdCatGeneral() != null) {
				entity.setCatFormulaComision(new CatFormulas());
				entity.getCatFormulaComision().setIdCatFormulas(comision.getFormulaComision().getIdCatGeneral());
				}

				if(comision.getFormulaPricing() != null &&comision.getFormulaPricing().getIdCatGeneral() != null) {
				entity.setCatFormulaPricing(new CatFormulas());
				entity.getCatFormulaPricing().setIdCatFormulas(comision.getFormulaPricing().getIdCatGeneral());
				}
				entity.setComisionista(new Usuario());
				entity.getComisionista().setIdUsuario(comision.getUsuario().getIdUsuario());


				entity.setComision(comision.getComision());
				entity.setPricing(comision.getComisionPricing());

				entity.setEsquema(new CatGeneral());
				entity.getEsquema().setIdCatGeneral(comision.getEsquema().getIdCatGeneral());

				if(comision.getFechaInicioPago() != null) {
					entity.setFechaInicioPago(comision.getFechaInicioPago());
				}

				if(comision.getFechaFinPago() != null) {
					entity.setFechaFinalPago(comision.getFechaFinPago());
				}

				if(comision.getPorcentajeComision() != null && comision.getPorcentajeComision().getIdCatGeneral() != null) {
					entity.setPorcentajeComision(new CatGeneral());
					entity.getPorcentajeComision().setIdCatGeneral(comision.getPorcentajeComision().getIdCatGeneral());
				}

				entity.setFechaAlta(new Date());
				entity.setUsuarioAlta(new Usuario());
				entity.getUsuarioAlta().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());

			}else {
				entity = nominaClienteComisionDao.read(comision.getIdNominaClienteComision());
				entity.setCanalVenta(new CatGeneral());
				entity.getCanalVenta().setIdCatGeneral(comision.getCanalVenta().getIdCatGeneral());

				if(comision.getFormulaComision() != null && comision.getFormulaComision().getIdCatGeneral() != null) {
				entity.setCatFormulaComision(new CatFormulas());
				entity.getCatFormulaComision().setIdCatFormulas(comision.getFormulaComision().getIdCatGeneral());
				}

				if(comision.getFormulaPricing() != null &&comision.getFormulaPricing().getIdCatGeneral() != null) {
				entity.setCatFormulaPricing(new CatFormulas());
				entity.getCatFormulaPricing().setIdCatFormulas(comision.getFormulaPricing().getIdCatGeneral());
				}
				entity.setComisionista(new Usuario());
				entity.getComisionista().setIdUsuario(comision.getUsuario().getIdUsuario());


				entity.setComision(comision.getComision());
				entity.setPricing(comision.getComisionPricing());

				entity.setEsquema(new CatGeneral());
				entity.getEsquema().setIdCatGeneral(comision.getEsquema().getIdCatGeneral());

				if(comision.getFechaInicioPago() != null) {
					entity.setFechaInicioPago(comision.getFechaInicioPago());
				}

				if(comision.getFechaFinPago() != null) {
					entity.setFechaFinalPago(comision.getFechaFinPago());
				}

				if(comision.getPorcentajeComision() != null && comision.getPorcentajeComision().getIdCatGeneral() != null) {
					entity.setPorcentajeComision(new CatGeneral());
					entity.getPorcentajeComision().setIdCatGeneral(comision.getPorcentajeComision().getIdCatGeneral());
				}
				entity.setFechaModificacion(new Date());
				entity.setUsuarioModificacion(new Usuario());
				entity.getUsuarioModificacion().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			}
			nominaClienteComisionDao.createOrUpdate(entity);
		}catch (Exception e) {
			LOGGER.error("OCurrio un error en listaPrestadoraServicioProductosByIdCelula ", e);
		}
	}


	@Override
	@Transactional
	public void guardarHonorario(ClienteComisionHonorarioDto honorario, Long idUsuario) {

		try {
			NominaClienteHonorario entity = new NominaClienteHonorario();
			NominaCliente nomina = new NominaCliente();
			if(honorario.getIdNominaClienteHonorario() == null) {
				nomina = nominaClienteDao.read(honorario.getNominaCliente().getIdNominaCliente());
				entity.setNominaCliente(nomina);

				if(honorario.getFormulaPPP() != null && honorario.getFormulaPPP().getIdCatGeneral() != null) {
				entity.setCatFormulaPpp(new CatFormulas());
				entity.getCatFormulaPpp().setIdCatFormulas(honorario.getFormulaPPP().getIdCatGeneral());
				}

				if(honorario.getFormulaFactura()!=null && honorario.getFormulaFactura().getIdCatGeneral() != null) {
				entity.setCatFormulaFactura(new CatFormulas());
				entity.getCatFormulaFactura().setIdCatFormulas(honorario.getFormulaFactura().getIdCatGeneral());
				}

				if(honorario.getFormulaTipoIva() !=null && honorario.getFormulaTipoIva().getIdCatGeneral() != null) {
					entity.setCatTipoIva(new CatFormulas());
					entity.getCatTipoIva().setIdCatFormulas(honorario.getFormulaTipoIva().getIdCatGeneral());
					}

				entity.setHonorarioPpp(honorario.getHonorarioPPP());
				entity.setIvaComercial(honorario.getIvaComercial() != null ? honorario.getIvaComercial() : null);

				if(honorario.getHonorarioPPPSs() != null) {
				entity.setHonorarioPppSs(honorario.getHonorarioPPPSs());
				}

				if(honorario.getHonorarioPPPMixto() != null) {
				entity.setHonorarioPppMixto(honorario.getHonorarioPPPMixto());
				}

				if(honorario.getFormulaPPPSs() != null && honorario.getFormulaPPPSs().getIdCatGeneral() != null) {
				entity.setCatFormulaFacturaSS(new CatFormulas());
				entity.getCatFormulaFacturaSS().setIdCatFormulas(honorario.getFormulaPPPSs().getIdCatGeneral());
				}

				if(honorario.getFormulaPPPMixto() != null && honorario.getFormulaPPPMixto().getIdCatGeneral() != null) {
					entity.setCatFormulaFacturaMixto(new CatFormulas());
					entity.getCatFormulaFacturaMixto().setIdCatFormulas(honorario.getFormulaPPPMixto().getIdCatGeneral());
					}

				if(honorario.getFormulaPPPMaquila() != null && honorario.getFormulaPPPMaquila().getIdCatGeneral() != null) {
					entity.setCatFormulaFacturaMaquila(new CatFormulas());
					entity.getCatFormulaFacturaMaquila().setIdCatFormulas(honorario.getFormulaPPPMaquila().getIdCatGeneral());
					}

				entity.setFechaAlta(new Date());
				entity.setUsuarioAlta(new Usuario());
				entity.getUsuarioAlta().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());

			}else {
				entity = nominaClienteHonorarioDao.read(honorario.getIdNominaClienteHonorario());

				if(honorario.getFormulaPPP() != null && honorario.getFormulaPPP().getIdCatGeneral() != null) {
				entity.setCatFormulaPpp(new CatFormulas());
				entity.getCatFormulaPpp().setIdCatFormulas(honorario.getFormulaPPP().getIdCatGeneral());
				}

				if(honorario.getFormulaFactura()!=null && honorario.getFormulaFactura().getIdCatGeneral() != null) {
				entity.setCatFormulaFactura(new CatFormulas());
				entity.getCatFormulaFactura().setIdCatFormulas(honorario.getFormulaFactura().getIdCatGeneral());
				}

				if(honorario.getFormulaTipoIva() !=null && honorario.getFormulaTipoIva().getIdCatGeneral() != null) {
					entity.setCatTipoIva(new CatFormulas());
					entity.getCatTipoIva().setIdCatFormulas(honorario.getFormulaTipoIva().getIdCatGeneral());
					}

				entity.setHonorarioPpp(honorario.getHonorarioPPP());
				entity.setIvaComercial(honorario.getIvaComercial() != null ? honorario.getIvaComercial() : null);

				if(honorario.getHonorarioPPPSs() != null) {
					entity.setHonorarioPppSs(honorario.getHonorarioPPPSs());
					}

					if(honorario.getHonorarioPPPMixto() != null) {
					entity.setHonorarioPppMixto(honorario.getHonorarioPPPMixto());
					}

					if(honorario.getFormulaPPPSs() != null && honorario.getFormulaPPPSs().getIdCatGeneral() != null) {
					entity.setCatFormulaFacturaSS(new CatFormulas());
					entity.getCatFormulaFacturaSS().setIdCatFormulas(honorario.getFormulaPPPSs().getIdCatGeneral());
					}

					if(honorario.getFormulaPPPMixto() != null && honorario.getFormulaPPPMixto().getIdCatGeneral() != null) {
						entity.setCatFormulaFacturaMixto(new CatFormulas());
						entity.getCatFormulaFacturaMixto().setIdCatFormulas(honorario.getFormulaPPPMixto().getIdCatGeneral());
						}

					if(honorario.getFormulaPPPMaquila() != null && honorario.getFormulaPPPMaquila().getIdCatGeneral() != null) {
						entity.setCatFormulaFacturaMaquila(new CatFormulas());
						entity.getCatFormulaFacturaMaquila().setIdCatFormulas(honorario.getFormulaPPPMaquila().getIdCatGeneral());
						}

				entity.setUsuarioModificacion(new Usuario());
				entity.getUsuarioModificacion().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
			}
			nominaClienteHonorarioDao.createOrUpdate(entity);
		}catch (Exception e) {
			LOGGER.error("OCurrio un error en guardarHonorario ", e);
		}
	}


	@Override
	@Transactional
	public void eliminarHonorario(Long idNominaClienteHonorario, Long idUsuario) {

		try {
			NominaClienteHonorario entity = new NominaClienteHonorario();

				entity = nominaClienteHonorarioDao.read(idNominaClienteHonorario);
				entity.setFechaModificacion(new Date());
				entity.setUsuarioModificacion(new Usuario());
				entity.getUsuarioModificacion().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());

				nominaClienteHonorarioDao.createOrUpdate(entity);
		}catch (Exception e) {
			LOGGER.error("OCurrio un error en listaPrestadoraServicioProductosByIdCelula ", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClienteComisionHonorarioDto> obtenerNominaHonorariosXIdNomina(NominaClienteDto nominaDto) {
		NominaCliente nomina = new NominaCliente();
		nomina = nominaClienteDao.read(nominaDto.getIdNominaCliente());
		List<ClienteComisionHonorarioDto> honorarios = new ArrayList<ClienteComisionHonorarioDto>();
		if(!nomina.getNominaClienteHonorarios().isEmpty()) {
			honorarios = nominaClienteHonorarioDao.convertirNominaClienteHonorarioADto(nomina.getNominaClienteHonorarios());
		}

		return honorarios;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ApoderadoLegalDto> getListApoderadoLegalByIdCliente(Long idCliente) {
		try {

			return clienteApoderadoLegalDao.getListApoderadoLegalByIdCliente(idCliente);

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getListApoderadoLegalByIdCliente ", e);
			return  Collections.emptyList();
		}
	}

	@Override
	@Transactional
	public Boolean guardarActualizarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta) {
		try {

			Persona persona = new Persona();
			ClienteApoderadoLegal apoderado = new ClienteApoderadoLegal();

			if(apoderadoLegalDto.getIdGenericoApoderadoLegal()!=null && apoderadoLegalDto.getIdPersona()!=null) {

				persona = personaDao.read(apoderadoLegalDto.getIdPersona());
				persona.setNombre(apoderadoLegalDto.getNombre());
				persona.setApellidoPaterno(apoderadoLegalDto.getApellidoPaterno());
				persona.setApellidoMaterno(apoderadoLegalDto.getApellidoMaterno()!=null ? apoderadoLegalDto.getApellidoMaterno() : null);
				persona.setRfc(apoderadoLegalDto.getRfc());
				persona.setCurp(apoderadoLegalDto.getCurp());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				personaDao.update(persona);


				apoderado = clienteApoderadoLegalDao.read(apoderadoLegalDto.getIdGenericoApoderadoLegal());
				apoderado.setCliente(new Cliente(apoderadoLegalDto.getClienteDto().getIdCliente()));
				apoderado.setPersona(persona);
				apoderado.setPoderNotarial(apoderadoLegalDto.getPoderNotarial());
				apoderado.setCatFacultad(new CatGeneral(apoderadoLegalDto.getCatFacultadDto().getIdCatGeneral()));
				apoderado.setUsuarioModificacion(new Usuario(idUsuarioALta));
				apoderado.setFechaModificacion(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteApoderadoLegalDao.update(apoderado);

			}else {

				persona.setNombre(apoderadoLegalDto.getNombre());
				persona.setApellidoPaterno(apoderadoLegalDto.getApellidoPaterno());
				persona.setApellidoMaterno(apoderadoLegalDto.getApellidoMaterno()!=null ? apoderadoLegalDto.getApellidoMaterno() : null);
				persona.setRfc(apoderadoLegalDto.getRfc());
				persona.setCurp(apoderadoLegalDto.getCurp());
				persona.setUsuarioAlta(idUsuarioALta);
				persona.setFechaAlta(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.ACTIVO.getIdEstatus()));
				Long idPersona = personaDao.create(persona);

				apoderado.setCliente(new Cliente(apoderadoLegalDto.getClienteDto().getIdCliente()));
				apoderado.setPersona(new Persona(idPersona));
				apoderado.setPoderNotarial(apoderadoLegalDto.getPoderNotarial());
				apoderado.setCatFacultad(new CatGeneral(apoderadoLegalDto.getCatFacultadDto().getIdCatGeneral()));
				apoderado.setUsuarioAlta(new Usuario(idUsuarioALta));
				apoderado.setFechaAlta(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				clienteApoderadoLegalDao.create(apoderado);
			}



			return  Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarActualizarApoderadoLegal ", e);
			return  Boolean.FALSE;
		}
	}

	@Override
	@Transactional
	public Boolean eliminarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta) {

		try {

			Persona persona = new Persona();
			ClienteRepresentanteLegal representante = new ClienteRepresentanteLegal();

			if(representanteLegalDto.getIdGenericoRepresentanteLegal()!=null && representanteLegalDto.getIdPersona()!=null) {

				persona = personaDao.read(representanteLegalDto.getIdPersona());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.INACTIVO.getIdEstatus()));
				personaDao.update(persona);

				representante = clienteRepresentanteLegalDao.read(representanteLegalDto.getIdGenericoRepresentanteLegal());
				representante.setUsuarioModificacion(new Usuario(idUsuarioALta));
				representante.setFechaModificacion(new Date());
				representante.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				clienteRepresentanteLegalDao.update(representante);

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
	public Boolean eliminarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta) {
	try {

			Persona persona = new Persona();
			ClienteApoderadoLegal apoderado = new ClienteApoderadoLegal();

			if(apoderadoLegalDto.getIdGenericoApoderadoLegal()!=null && apoderadoLegalDto.getIdPersona()!=null) {

				persona = personaDao.read(apoderadoLegalDto.getIdPersona());
				persona.setUsuarioModificacion(idUsuarioALta);
				persona.setFechaModificacion(new Date());
				persona.setIndEstatus(String.valueOf(CatEstatusEnum.INACTIVO.getIdEstatus()));
				personaDao.update(persona);

				apoderado = clienteApoderadoLegalDao.read(apoderadoLegalDto.getIdGenericoApoderadoLegal());
				apoderado.setUsuarioModificacion(new Usuario(idUsuarioALta));
				apoderado.setFechaModificacion(new Date());
				apoderado.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());
				clienteApoderadoLegalDao.update(apoderado);

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
	@Transactional
	public List<ClienteComisionHonorarioDto> obtenerNominaComisionesXIdNomina(NominaClienteDto nominaDto) {
		NominaCliente nomina = new NominaCliente();
		List<ClienteComisionHonorarioDto> comisiones= new ArrayList<ClienteComisionHonorarioDto>();
		nomina = nominaClienteDao.read(nominaDto.getIdNominaCliente());

		if(!nomina.getNominaClienteComision().isEmpty()) {
			comisiones = nominaClienteComisionDao.convertirNominaClienteComisionADto(nomina.getNominaClienteComision());
		}

		return comisiones;

	}

	@Override
	@Transactional
	public void eliminarComision(Long idNominaClienteComision, Long idUsuario) {

		try {
			NominaClienteComision entity = new NominaClienteComision();

				entity = nominaClienteComisionDao.read(idNominaClienteComision);
				entity.setFechaModificacion(new Date());
				entity.setUsuarioModificacion(new Usuario());
				entity.getUsuarioModificacion().setIdUsuario(idUsuario);
				entity.setIndEstatus(CatEstatusEnum.INACTIVO.getIdEstatus());

			nominaClienteComisionDao.createOrUpdate(entity);
		}catch (Exception e) {
			LOGGER.error("OCurrio un error en eliminarComision ", e);
		}
	}


	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosClienteRepresentante(Long idCliente, Long idClienteServRepLeg) {
		return clienteRepresentanteLegalDocumentoDao.listDocumentosRepresentanteCliente(idCliente, idClienteServRepLeg);
	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosClienteApoderado(Long idCliente, Long idClienteApodLeg) {
		return clienteApoderadoLegalDocumentoDao.listDocumentosApoderadoCliente(idCliente, idClienteApodLeg);
	}

	@Transactional
	public void guardarDocumentosClienteRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {

		try {

			Map<String,String> contextos = new HashMap<String,String>();


			contextos.put("1","representanteLegal");
			contextos.put("2",String.valueOf(documento.getIdCliente()));
			contextos.put("3",String.valueOf(documento.getIdClienteRepresentanteLegal()));
			contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));


		    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);

		    ClienteRepresentanteLegalDocumento representanteDocumento = new ClienteRepresentanteLegalDocumento();
		    ClienteRepresentanteLegal representante = new ClienteRepresentanteLegal();

		    DefinicionDocumento definicionDocumento= new DefinicionDocumento();

		    if(documento.getIdClienteRepresentanteLegalDocumento() != null && documento.getIdClienteRepresentanteLegalDocumento()!=0L) {
		    	representanteDocumento = clienteRepresentanteLegalDocumentoDao.read(documento.getIdClienteRepresentanteLegalDocumento());
		    }

		    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		    representanteDocumento.setDefinicionDocumento(definicionDocumento);
		    representanteDocumento.setIdCMS(idCMS);
		    representanteDocumento.setIdClienteRepresentanteLegalDocumento(documento.getIdClienteRepresentanteLegalDocumento());
		    representanteDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		    representanteDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());

		    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
		    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());

		    Cliente cliente = new Cliente();
		    cliente.setIdCliente(documento.getIdCliente());
		    representante.setCliente(cliente);
		    representante.setIdClienteRepresentanteLegal(documento.getIdClienteRepresentanteLegal());

		    representanteDocumento.setClienteRepresentanteLegal(representante);

		    if(documento.getIdClienteRepresentanteLegalDocumento() != null && documento.getIdClienteRepresentanteLegalDocumento()!=0L) {
		    	Usuario usuarioModificacion = new Usuario();
			    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setUsuarioModificacion(usuarioModificacion);
			    representanteDocumento.setFechaModificacion(new Date());
			    clienteRepresentanteLegalDocumentoDao.update(representanteDocumento);

		    }else {

		    	Usuario usuarioalta = new Usuario();
			    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setFechaAlta(new Date());
			    representanteDocumento.setUsuarioAlta(usuarioalta);

			    clienteRepresentanteLegalDocumentoDao.create(representanteDocumento);
		    }

		}catch (IOException io) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentante ", io);
		}

		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentante ", e);
		}
	}

	@Transactional
	public void guardarDocumentosClienteApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {

		try {

			Map<String,String> contextos = new HashMap<String,String>();


			contextos.put("1","apoderadoLegal");
			contextos.put("2",String.valueOf(documento.getIdCliente()));
			contextos.put("3",String.valueOf(documento.getIdClienteApoderadoLegal()));
			contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));


		    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);

		    ClienteApoderadoLegalDocumento representanteDocumento = new ClienteApoderadoLegalDocumento();
		    ClienteApoderadoLegal representante = new ClienteApoderadoLegal();

		    DefinicionDocumento definicionDocumento= new DefinicionDocumento();

		    if(documento.getIdClienteApoderadoLegalDocumento() != null && documento.getIdClienteApoderadoLegalDocumento()!=0L) {
		    	representanteDocumento = clienteApoderadoLegalDocumentoDao.read(documento.getIdClienteApoderadoLegalDocumento());
		    }

		    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		    representanteDocumento.setDefinicionDocumento(definicionDocumento);
		    representanteDocumento.setIdCMS(idCMS);
		    representanteDocumento.setIdClienteApoderadoLegalDocumento(documento.getIdClienteApoderadoLegalDocumento());
		    representanteDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		    representanteDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());

		    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
		    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());

		    Cliente cliente = new Cliente();
		    cliente.setIdCliente(documento.getIdCliente());
		    representante.setCliente(cliente);
		    representante.setIdClienteApoderadoLegal(documento.getIdClienteApoderadoLegal());

		    representanteDocumento.setClienteApoderadoLegal(representante);

		    if(documento.getIdClienteApoderadoLegalDocumento() != null && documento.getIdClienteApoderadoLegalDocumento()!=0L) {
		    	Usuario usuarioModificacion = new Usuario();
			    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setUsuarioModificacion(usuarioModificacion);
			    representanteDocumento.setFechaModificacion(new Date());
			    clienteApoderadoLegalDocumentoDao.update(representanteDocumento);

		    }else {

		    	Usuario usuarioalta = new Usuario();
			    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setFechaAlta(new Date());
			    representanteDocumento.setUsuarioAlta(usuarioalta);

			    clienteApoderadoLegalDocumentoDao.create(representanteDocumento);
		    }

		}catch (IOException io) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteApoderado ", io);
		}

		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteApoderado ", e);
		}

	}

	@Transactional
	public void eliminarDocumentosApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException {
		if(documento.getIdClienteApoderadoLegalDocumento() != null && documento.getIdClienteApoderadoLegalDocumento()!=0L) {
			clienteApoderadoLegalDocumentoDao.delete(documento.getIdClienteApoderadoLegalDocumento());
	    }
	}

	@Transactional
	public void eliminarDocumentosRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException {
		if(documento.getIdClienteRepresentanteLegalDocumento() != null && documento.getIdClienteRepresentanteLegalDocumento()!=0L) {
			clienteRepresentanteLegalDocumentoDao.delete(documento.getIdClienteRepresentanteLegalDocumento());
	    }
	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosCliente(Long idCliente) {
		return clienteDocumentoDao.listDocumentosCliente(idCliente);
	}

	@Transactional
	public void guardarDocumentosCliente(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {

		Map<String,String> contextos = new HashMap<String,String>();

		contextos.put("1",String.valueOf(documento.getIdCliente()));
		contextos.put("2", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));


	    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);

	    ClienteDocumento clienteDocu = new ClienteDocumento();

	    DefinicionDocumento definicionDocumento= new DefinicionDocumento();

	    if(documento.getIdClienteDocumento() != null && documento.getIdClienteDocumento()!=0L) {
	    	clienteDocu = clienteDocumentoDao.read(documento.getIdClienteDocumento());
	    }

	    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
	    clienteDocu.setDefinicionDocumento(definicionDocumento);
	    clienteDocu.setIdCMS(idCMS);
	    clienteDocu.setIdClienteDocumento(documento.getIdClienteDocumento());
	    clienteDocu.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
	    clienteDocu.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());

	    Cliente cliente = new Cliente();
	    cliente.setIdCliente(documento.getIdCliente());
	    clienteDocu.setCliente(cliente);


	    if(documento.getIdClienteDocumento() != null && documento.getIdClienteDocumento()!=0L) {
	    	Usuario usuarioModificacion = new Usuario();
		    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    clienteDocu.setUsuarioModificacion(usuarioModificacion);
		    clienteDocu.setFechaModificacion(new Date());
	    	clienteDocumentoDao.update(clienteDocu);

	    }else {

	    	Usuario usuarioalta = new Usuario();
		    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
		    clienteDocu.setUsuarioAlta(usuarioalta);
		    clienteDocu.setFechaAlta(new Date());

	    	clienteDocumentoDao.create(clienteDocu);
	    }
	}

	@Transactional
	public void eliminarDocumentosCliente(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException {
		if(documento.getIdClienteDocumento() != null && documento.getIdClienteDocumento()!=0L) {
			clienteDocumentoDao.delete(documento.getIdClienteDocumento());
	    }

	}

	@Transactional(readOnly = true)
	public List<DocumentoCSMDto> listDocumentosCerKeyRepresentanteCliente(Long idCliente, Long idClienteServRepLeg) {
		return clienteRepresentanteLegalDocumentoDao.listDocumentosCerKeyRepresentanteCliente(idCliente, idClienteServRepLeg);
	}

	@Transactional
	public void guardarDocumentosClienteRepresentanteCerKey(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException {
		try {

			Map<String,String> contextos = new HashMap<String,String>();


			contextos.put("1","representanteLegal");
			contextos.put("2",String.valueOf(documento.getIdCliente()));
			contextos.put("3",String.valueOf(documento.getIdClienteRepresentanteLegal()));
			contextos.put("4", String.valueOf(documento.getDefinicion().getIdDefinicionDocumento()));


		    Long idCMS = documentoServiceBO.guardarDocumentoCMS(documento, contextos);

		    ClienteRepresentanteLegalDocumento representanteDocumento = new ClienteRepresentanteLegalDocumento();
		    ClienteRepresentanteLegal representante = new ClienteRepresentanteLegal();

		    DefinicionDocumento definicionDocumento= new DefinicionDocumento();

		    if(documento.getIdClienteRepresentanteLegalDocumento() != null && documento.getIdClienteRepresentanteLegalDocumento()!=0L) {
		    	representanteDocumento = clienteRepresentanteLegalDocumentoDao.read(documento.getIdClienteRepresentanteLegalDocumento());
		    }

		    definicionDocumento.setIdDefinicionDocumento(documento.getDefinicion().getIdDefinicionDocumento());
		    representanteDocumento.setDefinicionDocumento(definicionDocumento);
		    representanteDocumento.setIdCMS(idCMS);
		    representanteDocumento.setIdClienteRepresentanteLegalDocumento(documento.getIdClienteRepresentanteLegalDocumento());
		    representanteDocumento.setIndEstatus(IndEstatusEnum.ACTIVO.getEstatus());
		    representanteDocumento.setNombreArchivo(documento.getDocumentoNuevo().getNombreArchivo());

		    PrestadoraServicio prestadoraServicio = new PrestadoraServicio();
		    prestadoraServicio.setIdPrestadoraServicio(documento.getIdPrestadora());

		    Cliente cliente = new Cliente();
		    cliente.setIdCliente(documento.getIdCliente());
		    representante.setCliente(cliente);
		    representante.setIdClienteRepresentanteLegal(documento.getIdClienteRepresentanteLegal());

		    representanteDocumento.setClienteRepresentanteLegal(representante);

		    if(documento.getIdClienteRepresentanteLegalDocumento() != null && documento.getIdClienteRepresentanteLegalDocumento()!=0L) {
		    	Usuario usuarioModificacion = new Usuario();
			    usuarioModificacion.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setUsuarioModificacion(usuarioModificacion);
			    representanteDocumento.setFechaModificacion(new Date());
			    clienteRepresentanteLegalDocumentoDao.update(representanteDocumento);

		    }else {

		    	Usuario usuarioalta = new Usuario();
			    usuarioalta.setIdUsuario(usuarioAplicativo.getIdUsuario());
			    representanteDocumento.setFechaAlta(new Date());
			    representanteDocumento.setUsuarioAlta(usuarioalta);

			    clienteRepresentanteLegalDocumentoDao.create(representanteDocumento);
		    }

		}catch (IOException io) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentanteCerKey ", io);
		}

		catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarDocumentosClienteRepresentanteCerKey ", e);
		}

	}

	@Override
	@Transactional
	public Boolean guardarPrefijoStp(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo) {
		try {

			if(clienteDto == null) {
				LOGGER.error("Ocurrio un error en guardarPrefijoStp, clienteDto es null");
				return Boolean.FALSE;
			}

			if(clienteDto != null) {
				if( clienteDto.getIdCliente() == null || (clienteDto.getPrefijoSTP() == null || "".equals(clienteDto.getPrefijoSTP().trim()))) {
					LOGGER.error("Ocurrio un error en guardarPrefijoStp, clienteDto.getIdCliente() o clienteDto.getPrefijoSTP() es null ");
					return Boolean.FALSE;
				}
			}

			Cliente cliente = clienteDao.read(clienteDto.getIdCliente());

			if(cliente!=null) {

				cliente.setPrefijoSTP(clienteDto.getPrefijoSTP());
				cliente.setUsuarioModificacion(new Usuario(usuarioAplicativo.getIdUsuario()));
				cliente.setIndEstatus(CatEstatusEnum.ACTIVO.getIdEstatus());
				cliente.setFechaModificacion(new Date());
				clienteDao.update(cliente);

			}else {
				LOGGER.error("Ocurrio un error en guardarPrefijoStp, entity cliente es null ");
				return Boolean.FALSE;
			}

			return Boolean.TRUE;
		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en guardarPrefijoStp ", e);
			return Boolean.FALSE;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public String getPrefijoStp(Long idCliente) {
		try {

			return clienteDao.read(idCliente).getPrefijoSTP();

		}catch (Exception e) {
			LOGGER.error("Ocurrio un error en getPrefijoStp ", e);
			throw new BusinessException("Ocurrio un error en el sistema, Favor de intentarlo mas tarde.");		}
	}
}
