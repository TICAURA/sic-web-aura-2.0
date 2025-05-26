
package mx.com.consolida.generico;

/**Excepción genérica de base de datos
 * @author Oscar Dorantes
 *
 */
public class DAOException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String descripcion;
	private String descTipoError;
	private String descFuncionalidad;
	private String descCapaOrigen;
	private String causa;
	private String resolucion;
	
	
	public DAOException(String clave, String descripcion){
		this.codigo=clave;
		this.descripcion=descripcion;
	}
	public DAOException(Exception e, String clave, String descripcion){
		super(e);
		this.codigo=clave;
		this.descripcion=descripcion;
	}
	

	public DAOException(Exception e, Respuestas errorBd) {
		super(e);
		this.codigo=errorBd.getClave();
		this.descripcion=errorBd.getDescripcion();
	}
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
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
		return "La DAOException es:  [codigo=" + codigo + ", descripcion="
				+ descripcion + ", descTipoError=" + descTipoError
				+ ", descFuncionalidad=" + descFuncionalidad
				+ ", descCapaOrigen=" + descCapaOrigen + ", causa=" + causa
				+ ", resolucion=" + resolucion + "]";
	}
	
}
