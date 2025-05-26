package mx.com.consolida.converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author Abel
 */
 @Retention(RetentionPolicy.RUNTIME)
 @Target({ElementType.FIELD})
public abstract @interface BuilderField {

   /**
    * Recibe como valor el nombre del Field del Bean destino.
    * @return String
    */
  public abstract String mappedField() default "";
  /**
    * Recibe como valor el nombre de la Propiedad del Bean Destino.
    * @return String
    */
  public abstract String mappedProperty() default "";
  /**
    * Recibe como valor el nombre de la Propiedad del Bean Origen.
    * @return String
    */
  public abstract String property() default "";
  /**
    * No se realiza la conversion del Field si encuentra en true.
    * @return boolean
    */
  public abstract boolean load() default true;

}
