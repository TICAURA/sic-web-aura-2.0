package mx.com.consolida.generico;

public enum Respuestas {
	ALTA_FONDOS_NOMBRE_REPETIDO("El nombre del fondo está repetido","-1"),
	COTIZACION_NO_SELECCIONADA("No selecciono una cotizacion","402");
	
	
	 private String descripcion;
	    private String codigo;
	    private String parametros;
	      
	    private Respuestas(String descripcion, String clave) {
	        this.codigo = clave;
	        this.descripcion=descripcion;
	    }
	    private Respuestas(String descripcion, String clave,String parametros) {
	        this.codigo = clave;
	        this.descripcion=descripcion+parametros;
	        this.setParametros(parametros);
	    }

	    public String getClave() {
	        return codigo;
	    }

		/**
		 * @return the nombre
		 */
		public String getDescripcion(){
			return descripcion;
		}
		
		public static Respuestas getExcepcionPorClave(String clave){
			for(int i=0;i<Respuestas.values().length;i++){
				if(Respuestas.values()[i].getClave().equals(clave)){
					return Respuestas.values()[i];
				}
			}
			return null;
		}
		/**
		 * @return the parametros
		 */
		public String getParametros() {
			return parametros;
		}
		/**
		 * @param parametros the parametros to set
		 */
		public void setParametros(String parametros) {
			this.parametros = parametros;
		}
}
