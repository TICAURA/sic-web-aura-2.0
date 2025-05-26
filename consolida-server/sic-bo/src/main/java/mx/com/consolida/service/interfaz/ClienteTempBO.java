package mx.com.consolida.service.interfaz; 

import java.util.List;

import mx.com.consolida.comunes.dto.CatGrupoDto;
import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.ObservacionAutorizadorDto;
import mx.com.consolida.dto.TotalesClienteTempDto;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteTempBO {

	void guardar(ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo);
	public List<ClienteTempDto> obtenerClientes(UsuarioAplicativo usuarioAplicativo);
	public ClienteTempDto obtenerClienteById(Long idClienteTemp);
	void guardarNuevoGrupo(CatGrupoDto grupo);
	List<ClienteTempDto> obtenerBitacoraSolicitudesCotizacion();
	List<ClienteTempDto> obtenerBitacoraCotizacion(Long idTipoSolCotizacion);
	
	List<ClienteTempDto> obtenerBitacoraSolicitudesAutorizador();
	void guardarObservacionAutorizador(ObservacionAutorizadorDto cotizacionDto, UsuarioAplicativo usuarioAplicativo);
	void autorizarCotizacion(Long idCotizacion);
	List<ClienteTempDto> obtenerContadorSeguimientoXEstatus(Long idEstatus, UsuarioAplicativo usuarioAplicativo);
	TotalesClienteTempDto obtenerContadoresTotales(UsuarioAplicativo usuarioAplicativo);
	List<ClienteTempDto> obtenerRegistrosContadorPrincipal(UsuarioAplicativo usuarioAplicativo);
	List<ClienteTempBitacoraSolicitudesDto> obtenerArchivoBitacoraSolicitudes(Long integer);
	ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdClienteTemp(Long idClienteTemp);
	ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdCotizacion(Long idCotizacion);
	void eliminarProspecto(ClienteTempDto cliente, UsuarioAplicativo usuarioAplicativo);
	ClienteTempDto obtenerEntidadFederativaXCP(String codigoPostal);
	List<ClienteTempDto> obtenerCotizacionesPorEstatus(Long idEstatus);
	List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro);
}