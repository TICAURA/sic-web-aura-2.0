package mx.com.consolida.crm.service.interfaz;

import java.io.IOException;
import java.util.List;
import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.comunes.dto.AccionistaDto;
import mx.com.consolida.comunes.dto.ApoderadoLegalDto;
import mx.com.consolida.comunes.dto.RepresentanteLegalDto;
import mx.com.consolida.crm.dto.CatProductoDto;
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
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.generico.MensajeDTO;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface PrestadoraServicioBO {

	List<PrestadoraServicioDto> obtenerPrestadorasServicio(Long idCelula);

	PrestadoraServicioDto obtenerCatalogos(PrestadoraServicioDto prestadoraServicioDto);

	PrestadoraServicioDto guardarPrestadoraServicio(PrestadoraServicioDto prestadoraServicioDto, UsuarioAplicativo usuarioAplicativo);

	PrestadoraServicioDto obtenerEntidadFederativaXCP(String codigoPostal);

	void guardarDomicilioPrestadoraServicio(DomicilioComunDto prestadoraServicioDomicilioDto,
			UsuarioAplicativo usuarioAplicativo);

	void guardarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo);

	void eliminarCuentaPrestadoraServicio(PrestadoraServicioCuentaBancariaDto cuenta,
			UsuarioAplicativo usuarioAplicativo);

	void guardarArchivosFiel(PrestadoraServicioDocumentoDto documentos, UsuarioAplicativo usuarioAplicativo);

	void guardarProducto(PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo);

	List<CatProductoDto> obtenerEstatusCatalogoProductos(List<CatProductoDto> catProductoDto,
			Long idPrestadoraServicio);

	PrestadoraServicioDto obtenerCatalogosDatosGenerales(PrestadoraServicioDto prestadoraServicioDto);

	PrestadoraServicioDto obtenerPrestadoraServicioByIdDatosGenerales(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	PrestadoraServicioDto obtenerPrestadoraServicioByIdProductos(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	PrestadoraServicioDto obtenerCatalogosProductos(PrestadoraServicioDto prestadoraServicioDto);

	PrestadoraServicioDto obtenerPrestadoraServicioByIdDomicilio(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	PrestadoraServicioDto obtenerPrestadoraServicioByIdCuentaBancaria(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	PrestadoraServicioDto obtenerCatalogosDomicilio(PrestadoraServicioDto prestadoraServicioDto);

	PrestadoraServicioDto obtenerCatalogosCuentaBancaria(PrestadoraServicioDto prestadoraServicioDto);

	PrestadoraServicioDto obtenerPrestadoraServicioByIdFiel(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	List<PrestadoraServicioDto> listaPrestdorasFondoYSinFondoByIdCelula(Long idCelula, boolean esFondo);

	MensajeDTO verificarGuardado(PrestadoraServicioCuentaBancariaDto cuenta);

	List<PrestadoraServicioDto> verificarFondo(Long idCelula);
	
	PrestadoraServicioActividadDto obtenerDatosByActividad(Long idPrestadoraServicio);
	
	PrestadoraServicioActividadDto obtenerCatalogosActividad(PrestadoraServicioActividadDto prestadoraActividad);
	
	List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro);
	
	void guardarActividad(PrestadoraServicioActividadDto actividad, UsuarioAplicativo usuarioAplicativo);
	
	void eliminarActividad(PrestadoraServicioActividadDto actividad, Long idUsuarioAplicativo);
	
	Boolean guardarActualizarPrestadoraRegistroPatronal(PrestadoraServicioRegistroPatronalDto registroPatronal, Long idUsuarioAplicativo);

	List<PrestadoraServicioRegistroPatronalDto> getListRegistroPatronalByIdPrestadora(Long idPrestadora);
	
	Boolean guardarActualizarPrestadoraClaseFraccionPrima(PrestadoraServicioClaseFraccionPrimaDto prestadoraClaseFraccionPrimaDto, Long idUsuarioAplicativo);
	
	PrestadoraServicioClaseFraccionPrimaDto getPresatdorServicioClaseFraccionByIdPrestServicio(Long idPrestadora);
	
	Boolean guardarActualizarPrestadoraImss(PrestadoraServicioImssDto prestadoraServicioImssDto, Long idUsuarioAplicativo);
	
	PrestadoraServicioImssDto getPresatdorServicioImssByIdPrestServicio(Long idPrestadora);
	
	Boolean guardarActualizarPrestadoraObjetoSocial(PrestadoraServicioObjetoSocialDto prestadoraServicioObjetoSocial, Long idUsuarioAplicativo);
	
	List<PrestadoraServicioObjetoSocialDto> getListObjetoSocialByIdPrestadora(Long idPrestadora);

	CatGeneralDto obtenerCatProductoSatXCve(String cveSat);

	void eliminarProducto(PrestadoraServicioProductoDto producto, UsuarioAplicativo usuarioAplicativo);

	PrestadoraServicioDto obtenerPrestadoraServicioArchivo(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);
	
	List<DocumentoCSMDto> listDocumentosPrestadora(Long idPrestadora);

	Boolean guardarActualizarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta);
	
	Boolean eliminarRepresentanteLegal(RepresentanteLegalDto representanteLegalDto, Long idUsuarioALta);
	
	Boolean guardarActualizarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta);
	
	Boolean eliminarApoderadoLegal(ApoderadoLegalDto apoderadoLegalDto, Long idUsuarioALta);
	
	List<RepresentanteLegalDto> getListRepresentanteLegalByIdPrestadoraServicio(Long idPrestadora);
	
	List<ApoderadoLegalDto> getListApoderadoLegalByIdPrestadoraServicio(Long idPrestadora);
	
	Boolean guardarActualizarAccionistas(AccionistaDto accionistaDto, Long idUsuarioALta);
	
	Boolean eliminarAccionista(AccionistaDto accionistaDto, Long idUsuarioALta);
	
	List<AccionistaDto> getListAccionistasByIdPrestadoraServicio(Long idPrestadora);
	
	Boolean eliminarPrestadora(Long idPrestadora, Long idUsuarioAplicativo);
	
	void guardarDocumentosPrestadora(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException ;
	
	List<DocumentoCSMDto> listDocumentosPrestadoraRepresentante(Long idPrestadora);
	
	List<DocumentoCSMDto> listDocumentosPrestadoraApoderado(Long idPrestadora, Long idPrestadoraServApodLeg);
	
	List<DocumentoCSMDto> listDocumentosPrestadoraAccionista(Long idPrestadora, Long idPrestadoraServaccionista);
	
	void guardarDocumentosPrestadoraApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException ;
	
	void eliminarDocumentosPrestadoraApoderado(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	
	void guardarDocumentosPrestadoraRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	
	void eliminarDocumentosPrestadoraRepresentante(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;

	void guardarDocumentosPrestadoraAccionista(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	
	void eliminarDocumentosAccionista(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;

	List<DocumentoCSMDto> listDocumentosPrestadoraDefinicionDocto(String listaDefinicionDocumento,
			Long idPrestadoraServicio);
	
	void eliminarDocumentos(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo);

	void guardarDocumentosPrestadoraRepresentanteCerKey(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo)
			throws IOException;

	List<DocumentoCSMDto> listPrestadoraDocumentosRepresentanteCerKey(Long idPrestadoraServRepLeg,
			Long idPrestadoraServicio);
	
	List<DocumentoCSMDto> listDocumentosRepresentantePrestadoraByIdPrestServRepLeg(Long idPrestadora, Long idPrestadoraServRepLeg);
	
	public FichaTecnicaDto fichaTecnica(Long idPrestadora) throws Exception;
	
	List<PrestadoraServicioDto> listPrestadoraServicioByIdCelulaAndIdCliente(Long idCelula, Long idCliente, boolean esFondo)   throws Exception;

	PrestadoraServicioDto obtenerPrestadoraServicioByStp(PrestadoraServicioDto prestadoraServicioDto,
			Long idPrestadoraServicio);

	void guardarDatosStp(PrestadoraServicioStpDto prestadoraServicioStp, Long idPrestadoraServicio, Long idUsuario);
}