package mx.com.consolida.generico;


public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4708080743493057841L;
	
	private String codigo;
	private String descripcion;
	private String mensajeAdicionalExcepcion;	
	private String descTipoError;
	private String descFuncionalidad;
	private String descCapaOrigen;
	private String causa;
	private String resolucion;
	
	public BusinessException(Exception e) {
		super(e);
	}
	public BusinessException(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public BusinessException(String codigo, String descripcion,
			String mensajeAdicionalExcepcion, String descTipoError,
			String descFuncionalidad, String descCapaOrigen, String causa,
			String resolucion) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.mensajeAdicionalExcepcion = mensajeAdicionalExcepcion;
		this.descTipoError = descTipoError;
		this.descFuncionalidad = descFuncionalidad;
		this.descCapaOrigen = descCapaOrigen;
		this.causa = causa;
		this.resolucion = resolucion;
	}
	public BusinessException(DAOException e){
		super(e);
		this.setCausa(e.getCausa());
		this.setCodigo(e.getCodigo());
		this.setDescCapaOrigen(e.getDescCapaOrigen());
		this.setDescFuncionalidad(e.getDescFuncionalidad());
		this.setDescripcion(e.getDescripcion());
		this.setDescTipoError(e.getDescTipoError());
		this.setResolucion(e.getResolucion());
	}
	public BusinessException(Exception e, String clave, String descripcion){
		super(e);
		this.codigo=clave;
		this.descripcion=descripcion;
	}
	
	public BusinessException(Respuestas respuesta){
		this.codigo=respuesta.getClave();
		this.descripcion=respuesta.getDescripcion();
	}

	public BusinessException(Exception causa, Respuestas respuesta){
		super(causa);
		this.codigo=respuesta.getClave();
		this.descripcion=respuesta.getDescripcion();
	}
	
	public BusinessException(String clave, String descripcion){
		this.codigo=clave;
		this.descripcion=descripcion;
	}

	/**
	 * @return the clave
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setCodigo(String clave) {
		this.codigo = clave;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the parametros
	 */
	public String getMensajeAdicionalExcepcion() {
		return mensajeAdicionalExcepcion;
	}

	/**
	 * @param parametros the parametros to set
	 */
	public void setMensajeAdicionalExcepcion(String parametros) {
		this.mensajeAdicionalExcepcion = parametros;
	}
	public String getDescTipoError() {
		return descTipoError;
	}
	public void setDescTipoError(String descTipoError) {
		this.descTipoError = descTipoError;
	}
	public String getDescFuncionalidad() {
		return descFuncionalidad;
	}
	public void setDescFuncionalidad(String descFuncionalidad) {
		this.descFuncionalidad = descFuncionalidad;
	}
	public String getDescCapaOrigen() {
		return descCapaOrigen;
	}
	public void setDescCapaOrigen(String descCapaOrigen) {
		this.descCapaOrigen = descCapaOrigen;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	@Override
	public String toString() {
		return "La excepción es: [codigo=" + codigo + ", descripcion="
				+ descripcion + ", mensajeAdicionalExcepcion="
				+ mensajeAdicionalExcepcion + ", descTipoError="
				+ descTipoError + ", descFuncionalidad=" + descFuncionalidad
				+ ", descCapaOrigen=" + descCapaOrigen + ", causa=" + causa
				+ ", resolucion=" + resolucion + "]";
	}
	
}
