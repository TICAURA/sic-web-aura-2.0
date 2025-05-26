package mx.com.consolida.crm.service.interfaz;

import java.io.IOException;
import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dto.CatProductoDto;
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
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.generico.BusinessException;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteSeccionesBO {

	DomicilioComunDto obtenerEntidadFederativaXCP(String codigoPostal);

	void guardarDomicilioPrestadoraServicio(DomicilioComunDto prestadoraServicioDomicilioDto,
			UsuarioAplicativo usuarioAplicativo);

	void guardarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo) throws BusinessException;

	void eliminarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo);

	DomicilioComunDto obtenerCatalogosDomicilio(DomicilioComunDto clienteDomicilioDto);

	DomicilioComunDto obtenerDatosDomicilioByCliente(ClienteDto clienteDto);

	void guardarDomicilioCliente(DomicilioComunDto domicilioDto, Long idUsuarioAplicativo);

	void guardarClienteMedioContacto(ClienteMedioContactoDto cliente, Long idUsuarioAplicativo);
	
	Boolean guardaActualizaDatosgenerales(ClienteDto cliente, Long idUsuarioAplicativo);
	
	Boolean guardarActualizarClienteConceptoFacturacion(ClienteConceptoFacaturacionDto clienteConcepfact, Long idUsuarioAplicativo);
	
	List<ClienteConceptoFacaturacionDto> getListtConceptosFacturacionByCliente(Long idCliente);
	
	Boolean guardaDatosCelula(ClienteDto clienteDto, Long idUsuarioAplicativo);
	
	Long guardaActualizaNominaCliente(NominaClienteDto nominaClienteDto, Long idUsuarioAplicativo);
	
	Boolean eliminarNominaCliente(Long idNominaCliente, Long idUsuarioAplicativo);

	ClienteCuentasBancariasDto obtenerDatosCuentaBancariaByCliente(ClienteDto clienteDto);

	ClienteCuentasBancariasDto obtenerCatalogosCuentaBancaria(ClienteCuentasBancariasDto cuenta);

	void guardarCuentaBancaria(ClienteCuentaBancariaDto cuenta, UsuarioAplicativo usuarioAplicativo);

	void eliminarCuentaBancaria(ClienteCuentaBancariaDto cuenta, Long idUsuarioAplicativo);

	ClienteActividadDto obtenerCatalogosActividad(ClienteActividadDto actividad);

	List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro);

	void guardarActividad(ClienteActividadDto actividad, UsuarioAplicativo usuarioAplicativo);

	ClienteActividadDto obtenerDatosByActividad(ClienteDto clienteDto);

//	ClienteServicioDto obtenerCatalogosProductos(ClienteServicioDto producto);

	Boolean guardarProducto(ClienteServicioDto clienteServicioDto, Long idUsuarioAplicativo);

//	List<CatProductoDto> obtenerEstatusCatalogoProductos(List<CatProductoDto> catProductoDto, Long idCliente);

	void eliminarActividad(ClienteActividadDto actividad);

	MensajeDTO verificarGuardado(ClienteCuentaBancariaDto cuenta);
	
	NoministaDto getClienteNoministaByidClienteIdNominista(Long idCliente);
	
	Boolean eliminarClientePrestadora(Long idClientePrestadoraServicio, Long idUsuarioAplicativo);
	
	void guardarDomicilioComercial(DomicilioComunDto domicilioDto, Long idUsuarioAplicativo);
	
	 Boolean existeCelulaEnCliente(Long idCliente, Long idCelula);
	 
	 List<PrestadoraServicioProductoDto> obtenerEstatusPrestServProductos(List<PrestadoraServicioProductoDto> catPrestServProductoDto, Long idCliente);
	 
	 List<PrestadoraServicioProductoDto> listaPrestadoraServicioProductosByIdCelula(Long idCelula);

	void guardarComision(ClienteComisionHonorarioDto comision, Long idUsuario);

	List<ClienteComisionHonorarioDto> obtenerNominaComisionesXIdNomina(NominaClienteDto nomina);

	void eliminarComision(Long idNominaClienteComision, Long idUsuario);
	
	void guardarHonorario(ClienteComisionHonorarioDto comision, Long idUsuario);

	void eliminarHonorario(Long idNominaClienteHonorario, Long idUsuario);

	List<ClienteComisionHonorarioDto> obtenerNominaHonorariosXIdNomina(NominaClienteDto nomina);
	 
	 List<RepresentanteLegalDto> getListRepresentanteLegalByIdCliente(Long idCliente);
	 
	 Boolean guardarActualizarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta);
	 
	 Boolean eliminarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta);
	 
	 List<ApoderadoLegalDto> getListApoderadoLegalByIdCliente(Long idCliente);
	 
	 Boolean guardarActualizarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta);
	 
	 Boolean eliminarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta);
	 
	 List<DocumentoCSMDto> listDocumentosClienteRepresentante(Long idCliente, Long idClienteServRepLeg);
	 
	 List<DocumentoCSMDto> listDocumentosClienteApoderado(Long idCliente, Long idClienteApodLeg);
	 
	 void guardarDocumentosClienteApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 void guardarDocumentosClienteRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 void eliminarDocumentosApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 void eliminarDocumentosRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)throws IOException;
	 
	 List<DocumentoCSMDto> listDocumentosCliente(Long idCliente);
	 
	 void guardarDocumentosCliente(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 void eliminarDocumentosCliente(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 List<DocumentoCSMDto> listDocumentosCerKeyRepresentanteCliente(Long idCliente, Long idClienteServRepLeg);
	 
	 void guardarDocumentosClienteRepresentanteCerKey(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	 
	 Boolean existeCelulaCliente(Long idCliente);
	 
	 CelulaDto getCelulaBy(Long idCliente);
	 
	 String getPrefijoStp(Long idCliente);
	 
	 Boolean guardarPrefijoStp(ClienteDto clienteDto, UsuarioAplicativo usuarioAplicativo);

}

