package mx.com.consolida.crm.dto;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import mx.com.consolida.dto.DocumentoRegistroDto;
import mx.com.consolida.usuario.UsuarioDTO;


public class PrestadoraServicioDocumentoDto  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long idPrestadoraServicioDocumento;
	
	private Long idPrestadoraServicio;
	private DocumentoRegistroDto documentoRegistro;
	private Long idContentFile;
	private String rutaDocumento;
	private Date fechaAlta;
	private UsuarioDTO usuarioAlta;
	private Date fechaModificacion;
	private Long indEstatus;
	private String passwordFiel;
	private String passwordCsd;
	private Long idDefinicionDocumento;
	
	private Map <String ,Object > archivoFielCer;
	private String archivoRecuperadoFielCer;
	
	private Map <String ,Object > archivoFielKey;
	private String archivoRecuperadoFielKey;
	
	private Map <String ,Object > archivoCsdCer;
	private String archivoRecuperadoCsdCer;
	
	private Map <String ,Object > archivoCsdKey;
	private String archivoRecuperadoCsdKey;
	
	private String nombreArchivoFielCer;
	private String nombreArchivoFielKey;
	private String nombreArchivoCsdCer;
	private String nombreArchivoCsdKey;
	
	private Long idCMS;
	private File fileFielKey;
	private File fileFielCer;
	private File fileCsdKey;
	private File fileCsdCer;
	
	public Long getIdPrestadoraServicioDocumento() {
		return idPrestadoraServicioDocumento;
	}
	public void setIdPrestadoraServicioDocumento(Long idPrestadoraServicioDocumento) {
		this.idPrestadoraServicioDocumento = idPrestadoraServicioDocumento;
	}
	
	public DocumentoRegistroDto getDocumentoRegistro() {
		return documentoRegistro;
	}
	public void setDocumentoRegistro(DocumentoRegistroDto documentoRegistro) {
		this.documentoRegistro = documentoRegistro;
	}
	public Long getIdContentFile() {
		return idContentFile;
	}
	public void setIdContentFile(Long idContentFile) {
		this.idContentFile = idContentFile;
	}
	public String getRutaDocumento() {
		return rutaDocumento;
	}
	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Long getIndEstatus() {
		return indEstatus;
	}
	public void setIndEstatus(Long indEstatus) {
		this.indEstatus = indEstatus;
	}
	public String getPasswordFiel() {
		return passwordFiel;
	}
	public void setPasswordFiel(String passwordFiel) {
		this.passwordFiel = passwordFiel;
	}
	public String getPasswordCsd() {
		return passwordCsd;
	}
	public void setPasswordCsd(String passwordCsd) {
		this.passwordCsd = passwordCsd;
	}
	public Map<String, Object> getArchivoFielCer() {
		return archivoFielCer;
	}
	public void setArchivoFielCer(Map<String, Object> archivoFielCer) {
		this.archivoFielCer = archivoFielCer;
	}
	public String getArchivoRecuperadoFielCer() {
		return archivoRecuperadoFielCer;
	}
	public void setArchivoRecuperadoFielCer(String archivoRecuperadoFielCer) {
		this.archivoRecuperadoFielCer = archivoRecuperadoFielCer;
	}
	public Map<String, Object> getArchivoFielKey() {
		return archivoFielKey;
	}
	public void setArchivoFielKey(Map<String, Object> archivoFielKey) {
		this.archivoFielKey = archivoFielKey;
	}
	public String getArchivoRecuperadoFielKey() {
		return archivoRecuperadoFielKey;
	}
	public void setArchivoRecuperadoFielKey(String archivoRecuperadoFielKey) {
		this.archivoRecuperadoFielKey = archivoRecuperadoFielKey;
	}
	public Map<String, Object> getArchivoCsdCer() {
		return archivoCsdCer;
	}
	public void setArchivoCsdCer(Map<String, Object> archivoCsdCer) {
		this.archivoCsdCer = archivoCsdCer;
	}
	public String getArchivoRecuperadoCsdCer() {
		return archivoRecuperadoCsdCer;
	}
	public void setArchivoRecuperadoCsdCer(String archivoRecuperadoCsdCer) {
		this.archivoRecuperadoCsdCer = archivoRecuperadoCsdCer;
	}
	public Map<String, Object> getArchivoCsdKey() {
		return archivoCsdKey;
	}
	public void setArchivoCsdKey(Map<String, Object> archivoCsdKey) {
		this.archivoCsdKey = archivoCsdKey;
	}
	public String getArchivoRecuperadoCsdKey() {
		return archivoRecuperadoCsdKey;
	}
	public void setArchivoRecuperadoCsdKey(String archivoRecuperadoCsdKey) {
		this.archivoRecuperadoCsdKey = archivoRecuperadoCsdKey;
	}
	public String getNombreArchivoFielCer() {
		return nombreArchivoFielCer;
	}
	public void setNombreArchivoFielCer(String nombreArchivoFielCer) {
		this.nombreArchivoFielCer = nombreArchivoFielCer;
	}
	public String getNombreArchivoFielKey() {
		return nombreArchivoFielKey;
	}
	public void setNombreArchivoFielKey(String nombreArchivoFielKey) {
		this.nombreArchivoFielKey = nombreArchivoFielKey;
	}
	public String getNombreArchivoCsdCer() {
		return nombreArchivoCsdCer;
	}
	public void setNombreArchivoCsdCer(String nombreArchivoCsdCer) {
		this.nombreArchivoCsdCer = nombreArchivoCsdCer;
	}
	public String getNombreArchivoCsdKey() {
		return nombreArchivoCsdKey;
	}
	public void setNombreArchivoCsdKey(String nombreArchivoCsdKey) {
		this.nombreArchivoCsdKey = nombreArchivoCsdKey;
	}
	public Long getIdPrestadoraServicio() {
		return idPrestadoraServicio;
	}
	public void setIdPrestadoraServicio(Long idPrestadoraServicio) {
		this.idPrestadoraServicio = idPrestadoraServicio;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public UsuarioDTO getUsuarioAlta() {
		return usuarioAlta;
	}
	public void setUsuarioAlta(UsuarioDTO usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}
	public Long getIdCMS() {
		return idCMS;
	}
	public void setIdCMS(Long idCMS) {
		this.idCMS = idCMS;
	}
	public File getFileFielKey() {
		return fileFielKey;
	}
	public void setFileFielKey(File fileFielKey) {
		this.fileFielKey = fileFielKey;
	}
	public File getFileFielCer() {
		return fileFielCer;
	}
	public void setFileFielCer(File fileFielCer) {
		this.fileFielCer = fileFielCer;
	}
	public File getFileCsdKey() {
		return fileCsdKey;
	}
	public void setFileCsdKey(File fileCsdKey) {
		this.fileCsdKey = fileCsdKey;
	}
	public File getFileCsdCer() {
		return fileCsdCer;
	}
	public void setFileCsdCer(File fileCsdCer) {
		this.fileCsdCer = fileCsdCer;
	}
	public Long getIdDefinicionDocumento() {
		return idDefinicionDocumento;
	}
	public void setIdDefinicionDocumento(Long idDefinicionDocumento) {
		this.idDefinicionDocumento = idDefinicionDocumento;
	}
	
	
	
}
