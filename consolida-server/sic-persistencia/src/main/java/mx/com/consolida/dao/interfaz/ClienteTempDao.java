package mx.com.consolida.dao.interfaz;

import java.util.List;

import mx.com.consolida.dto.CatSubGiroComercialDto;
import mx.com.consolida.dto.ClienteTempBitacoraSolicitudesDto;
import mx.com.consolida.dto.ClienteTempDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.dto.TotalesClienteTempDto;
import mx.com.consolida.entity.ClienteTemp;
import mx.com.consolida.generico.UsuarioAplicativo;

public interface ClienteTempDao extends mx.com.consolida.dao.DAO<ClienteTemp,Long>{
	public List<ClienteTempDto> obtenerClientes(UsuarioAplicativo usuarioAplicativo);
	public ClienteTempDto obtenerClienteById(Long idClienteTemp);
	public List<CotizacionDto> obtenerCotizaciones(Long idClienteTemp, Long idTipoSolCotizacion);
	public List<ClienteTempDto> obtenerBitacoraSolicitudesCotizacion();
	public List<ClienteTempDto> obtenerBitacoraCotizacion(Long idTipoSolCotizacion);
	public List<ClienteTempDto> obtenerBitacoraSolicitudesAutorizador();
	public String obtenerNombreEstadoXCveEstado(String estado);
	public String obtenerNombreAlcaldiaXIdMunicipio(Long alcaldia);
	public String obtenerNombreGiroComercialXId(Long idGiroComercial);
	public List<ClienteTempDto> obtenerContadorSeguimientoXEstatus(Long idEstatus,UsuarioAplicativo usuarioAplicativo);
	public TotalesClienteTempDto obtenerContadoresTotales(UsuarioAplicativo usuarioAplicativo);
	public List<ClienteTempDto> obtenerRegistrosContadorPrincipal(UsuarioAplicativo usuarioAplicativo);
	List<ClienteTempBitacoraSolicitudesDto> obtenerArchivoBitacoraSolicitudes(Long idClienteTempBitacoraSolicitudes);
	ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdClienteTemp(Long idClienteTemp);
	ClienteTempBitacoraSolicitudesDto obtenerBitacoraSolicitudesXIdCotizacion(Long idCotizacion);
	public ClienteTempDto obtenerEntidadFederativaXCP(String codigoPostal);
	List<ClienteTempDto> obtenerBitacoraCotizacionByIdEstatus(Long idEstatus);
	List<CatSubGiroComercialDto> obtenerSubgiroXIdGiro(String idGiro);
}
