package mx.com.consolida.dao;

import java.io.Serializable;
import java.util.List;

public interface DAO<T, PK extends Serializable> {

   PK create(final T newInstance);

   PK merge(final T newInstance);

   T read(final PK id);
   
   public List<T> findAll() ;

   void update(final T transientObject);

   void updateWithNoFlush(final T transientObject);

   void delete(final PK persistentObjectId);

   void createOrUpdate(final T newInstance);

   void flush();
   
   void close();
   
   void open();
   
   void flushAndClear();

   /**
    * Castea los contenidos de una lista cualquiera a una clase especificada
    * 
    * @param srcList
    *           La lista
    * @param clas
    *           La clase a la que se quiere castear
    * @return
    */
   <F> List<F> castList(List<?> srcList, Class<F> clas);

}
