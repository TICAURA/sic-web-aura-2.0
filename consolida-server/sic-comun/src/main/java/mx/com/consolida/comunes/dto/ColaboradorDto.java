package mx.com.consolida.comunes.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import mx.com.consolida.catalogos.CatGeneralDto;
import mx.com.consolida.dto.CatEntidadFederativaDto;
import mx.com.consolida.generico.CatGenericoDTO;

public class ColaboradorDto implements Serializable{
	
	// DATOS GENERALES
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreCompleto;
	private CatGeneralDto catNacionalidad;
	private Date fechaNacimiento;
	private String rfc;
	private String curp;
	private CatGeneralDto catEstadoCivil;
	private CatGeneralDto catGenero;
	private String celular;
	private String telefono;
	private String correoElectronico;
	
	// DOMICILIO
	private CatGeneralDto catPais;
	private CatEntidadFederativaDto catEntidadFederativaDto;
	private String calle;
	private String colonia;
	private String codigoPostal;
	
	// DATOS NOMINA
	private BigDecimal salarioDiario;
	private BigDecimal diario;
	private Date fechaAltaFirmaContrato;
	private Date fechaAntiguedad;
	private String imss;
	private CatGeneralDto catNivelEstudios;
	private String profesion;
	private String tipoSangre;
	private CatGeneralDto catFormaPago;
	private CatGeneralDto catBancoOperador;
	private String controlBanco;
	private String cuentaClabeDeposito_1;
	private boolean esCuentaPrincipalDeposito_1;
	private String cuentaClabeDeposito_2;
	private boolean esCuentaPrincipalDeposito_2;
	
//	private String 
//	private String
//	private String
//	private String
	
	

}
