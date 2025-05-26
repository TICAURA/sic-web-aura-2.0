package mx.com.consolida.crm.service.interfaz;

import java.io.IOException;
import java.util.List;

import mx.com.consolida.catalogos.DocumentoCSMDto;
import mx.com.consolida.converter.TechnicalException;
import mx.com.consolida.crm.dto.ColaboradorDto;
import mx.com.consolida.crm.dto.FormulaFacturaDto;
import mx.com.consolida.crm.dto.NominaClienteDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadDto;
import mx.com.consolida.crm.dto.NominaPeriodicidadFechasDto;
import mx.com.consolida.entity.crm.NominaPeriodicidadFechas;
import mx.com.consolida.generico.UsuarioAplicativo;
import mx.com.consolida.ppp.dto.ValidaCreacionNominaDto;

public interface NominaClienteBO {
	
	List<NominaClienteDto> listaNominaCliente(Long idCliente);
	List<NominaClienteDto> listaNominaCliente(Long idCliente , Long idUsuarioNominista);
	public NominaClienteDto nominaClientebyId(Long idNominaCliente);
	public ColaboradorDto guardarColaborador(ColaboradorDto dto, UsuarioAplicativo us) throws TechnicalException;
	public ColaboradorDto editarColaborador(ColaboradorDto dto, UsuarioAplicativo us);
	public ColaboradorDto obtenerColaboradorById(Long idColaborador);
	public List<ColaboradorDto> obtenercolaboradoresByidNomina(Long idNomina);
	
	public void calcularPeriodo(NominaPeriodicidadDto dto, UsuarioAplicativo us);
	public NominaPeriodicidadDto obtenerPeriodo(Long idNomina);
	public List<NominaPeriodicidadFechasDto> obtenerPeriodosFechas(Long idNomina);
	public void guardarDocumentoColaborador(DocumentoCSMDto documento, UsuarioAplicativo usuarioAplicativo) throws IOException;
	public void eliminarDocumentoColaborador(Long idColaboradorDocumento);
	public FormulaFacturaDto formulaFactura( Long idNominaCliente);
	public ValidaCreacionNominaDto validaSecciones(Long idCliente , Long idNominaCliente);
}
