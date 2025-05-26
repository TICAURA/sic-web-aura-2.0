package mx.com.consolida.catalogos;

import java.io.Serializable;

import mx.com.documento.DefinicionDocumentoDto;

public class DocumentoCSMDto implements Serializable{

	//Prestadora
	private Long idPrestadora;
	private Long idPrestadoraServicioDoctumentos;
	
	// APODERADO LEGAL
	private Long idPrestadoraServicioApoderadoLegalDoctumento;
	private Long idPrestadoraServicioApoderadoLegal;

	// REPRESENTANTE LEGAL
	private Long idPrestadoraServicioRepresentanteLegalDoctumento;
	private Long idPrestadoraServicioRepresentanteLegal;
	
	// ACCIONISTA
	private Long idPrestadoraServicioAccionistaDoctumento;
	private Long idPrestadoraServicioAccionista;
	
	//Cliente
	private Long idCliente;
	private Long idClienteDocumento;
	
	//Colaboradores
	private Long idColaborador;
	private Long idColaboradorDocumento;
	
	//Representante legal
	private Long idClienteRepresentanteLegal;
	private Long idClienteRepresentanteLegalDocumento;
	
	//Representante legal
	private Long idClienteApoderadoLegal;
	private Long idClienteApoderadoLegalDocumento;
	
	//PPP nomina
	private Long idPppNomina;
	private Long idPppNominaFactura;
	private Long idPppNominaDocumento;
	private Long idPppNominaFacturaDocumento;

	
	private DefinicionDocumentoDto definicion;
	private Long idCMS;
	private String nombreArchivo;
	private String usuarioAlta;
	
	private DocumentoDTO documentoNuevo;

	public Long getIdPrestadora() {
		return idPrestadora;
	}

	public void setIdPrestadora(Long idPrestadora) {
		this.idPrestadora = idPrestadora;
	}

	public Long getIdPrestadoraServicioDoctumentos() {
		return idPrestadoraServicioDoctumentos;
	}

	public void setIdPrestadoraServicioDoctumentos(Long idPrestadoraServicioDoctumentos) {
		this.idPrestadoraServicioDoctumentos = idPrestadoraServicioDoctumentos;
	}

	public Long getIdPrestadoraServicioApoderadoLegalDoctumento() {
		return idPrestadoraServicioApoderadoLegalDoctumento;
	}

	public void setIdPrestadoraServicioApoderadoLegalDoctumento(Long idPrestadoraServicioApoderadoLegalDoctumento) {
		this.idPrestadoraServicioApoderadoLegalDoctumento = idPrestadoraServicioApoderadoLegalDoctumento;
	}

	public Long getIdPrestadoraServicioApoderadoLegal() {
		return idPrestadoraServicioApoderadoLegal;
	}

	public void setIdPrestadoraServicioApoderadoLegal(Long idPrestadoraServicioApoderadoLegal) {
		this.idPrestadoraServicioApoderadoLegal = idPrestadoraServicioApoderadoLegal;
	}

	public Long getIdPrestadoraServicioRepresentanteLegalDoctumento() {
		return idPrestadoraServicioRepresentanteLegalDoctumento;
	}

	public void setIdPrestadoraServicioRepresentanteLegalDoctumento(Long idPrestadoraServicioRepresentanteLegalDoctumento) {
		this.idPrestadoraServicioRepresentanteLegalDoctumento = idPrestadoraServicioRepresentanteLegalDoctumento;
	}

	public Long getIdPrestadoraServicioRepresentanteLegal() {
		return idPrestadoraServicioRepresentanteLegal;
	}

	public void setIdPrestadoraServicioRepresentanteLegal(Long idPrestadoraServicioRepresentanteLegal) {
		this.idPrestadoraServicioRepresentanteLegal = idPrestadoraServicioRepresentanteLegal;
	}

	public Long getIdPrestadoraServicioAccionistaDoctumento() {
		return idPrestadoraServicioAccionistaDoctumento;
	}

	public void setIdPrestadoraServicioAccionistaDoctumento(Long idPrestadoraServicioAccionistaDoctumento) {
		this.idPrestadoraServicioAccionistaDoctumento = idPrestadoraServicioAccionistaDoctumento;
	}

	public Long getIdPrestadoraServicioAccionista() {
		return idPrestadoraServicioAccionista;
	}

	public void setIdPrestadoraServicioAccionista(Long idPrestadoraServicioAccionista) {
		this.idPrestadoraServicioAccionista = idPrestadoraServicioAccionista;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}

	public Long getIdClienteDocumento() {
		return idClienteDocumento;
	}

	public void setIdClienteDocumento(Long idClienteDocumento) {
		this.idClienteDocumento = idClienteDocumento;
	}

	public Long getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(Long idColaborador) {
		this.idColaborador = idColaborador;
	}

	public Long getIdColaboradorDocumento() {
		return idColaboradorDocumento;
	}

	public void setIdColaboradorDocumento(Long idColaboradorDocumento) {
		this.idColaboradorDocumento = idColaboradorDocumento;
	}

	public Long getIdClienteRepresentanteLegal() {
		return idClienteRepresentanteLegal;
	}

	public void setIdClienteRepresentanteLegal(Long idClienteRepresentanteLegal) {
		this.idClienteRepresentanteLegal = idClienteRepresentanteLegal;
	}

	public Long getIdClienteRepresentanteLegalDocumento() {
		return idClienteRepresentanteLegalDocumento;
	}

	public void setIdClienteRepresentanteLegalDocumento(Long idClienteRepresentanteLegalDocumento) {
		this.idClienteRepresentanteLegalDocumento = idClienteRepresentanteLegalDocumento;
	}

	public Long getIdClienteApoderadoLegal() {
		return idClienteApoderadoLegal;
	}

	public void setIdClienteApoderadoLegal(Long idClienteApoderadoLegal) {
		this.idClienteApoderadoLegal = idClienteApoderadoLegal;
	}

	public Long getIdClienteApoderadoLegalDocumento() {
		return idClienteApoderadoLegalDocumento;
	}

	public void setIdClienteApoderadoLegalDocumento(Long idClienteApoderadoLegalDocumento) {
		this.idClienteApoderadoLegalDocumento = idClienteApoderadoLegalDocumento;
	}

	public DefinicionDocumentoDto getDefinicion() {
		return definicion;
	}

	public void setDefinicion(DefinicionDocumentoDto definicion) {
		this.definicion = definicion;
	}

	public Long getIdCMS() {
		return idCMS;
	}

	public void setIdCMS(Long idCMS) {
		this.idCMS = idCMS;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public DocumentoDTO getDocumentoNuevo() {
		return documentoNuevo;
	}

	public void setDocumentoNuevo(DocumentoDTO documentoNuevo) {
		this.documentoNuevo = documentoNuevo;
	}

	public Long getIdPppNomina() {
		return idPppNomina;
	}

	public void setIdPppNomina(Long idPppNomina) {
		this.idPppNomina = idPppNomina;
	}

	public Long getIdPppNominaDocumento() {
		return idPppNominaDocumento;
	}

	public void setIdPppNominaDocumento(Long idPppNominaDocumento) {
		this.idPppNominaDocumento = idPppNominaDocumento;
	}

	public Long getIdPppNominaFacturaDocumento() {
		return idPppNominaFacturaDocumento;
	}

	public void setIdPppNominaFacturaDocumento(Long idPppNominaFacturaDocumento) {
		this.idPppNominaFacturaDocumento = idPppNominaFacturaDocumento;
	}

	public Long getIdPppNominaFactura() {
		return idPppNominaFactura;
	}

	public void setIdPppNominaFactura(Long idPppNominaFactura) {
		this.idPppNominaFactura = idPppNominaFactura;
	}
	
	
	
}
