package mx.com.consolida.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mx.com.consolida.converter.Convertir;


@SuppressWarnings("unchecked")
public abstract class GenericDAO<T, K extends Serializable> extends Convertir implements DAO<T, K> {

   @Autowired
   protected SessionFactory sessionFactory;


   protected final Class<T> entityType;

   {
      final ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
      final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
      entityType = (Class<T>) actualTypeArguments[0];
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public K create(final T newInstance) {
      try{
         final Session session = sessionFactory.getCurrentSession();

         final K pk = (K) session.save(newInstance);

         session.flush();

         return pk;

      }catch (Exception ex){
         throw new RuntimeException("Error: ", ex);
      }
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void createOrUpdate(final T newInstance) {

      final Session session = sessionFactory.getCurrentSession();

      session.saveOrUpdate(newInstance);
      session.flush();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public K merge(final T newInstance) {

      final Session session = sessionFactory.getCurrentSession();

      final K pk = (K) session.merge(newInstance);

      session.flush();

      return pk;
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public T read(final K id) {
      return (T) sessionFactory.getCurrentSession().get(entityType, id);
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public List<T> findAll() {
	    try
	    {
	        return sessionFactory.getCurrentSession().createCriteria(entityType).list();
	    } catch (Exception e) {
	        return new ArrayList<T>();
	    }
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void update(final T transientObject) {
      final Session session = sessionFactory.getCurrentSession();
      session.update(transientObject);
      session.flush();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void updateWithNoFlush(final T transientObject) {
      final Session session = sessionFactory.getCurrentSession();
      session.update(transientObject);
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void delete(final K persistentObjectId) {
      final Session session = sessionFactory.getCurrentSession();
      session.delete(read(persistentObjectId));
      session.flush();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void flush() {
      sessionFactory.getCurrentSession().flush();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void close() {
      sessionFactory.getCurrentSession().close();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void open() {
      sessionFactory.getCurrentSession();
   }

   @Transactional(propagation = Propagation.MANDATORY)
   public void flushAndClear() {
	   sessionFactory.getCurrentSession().flush();
       sessionFactory.getCurrentSession().clear();

   }

   public List<T> callNativeQuery(String cadenaQuery, String cadenaMapeo, Map<String, Object> map) {
	   //EntityManager em = emf.createEntityManager();
      final Session session = sessionFactory.getCurrentSession();
      Query q = session.createNativeQuery(cadenaQuery,cadenaMapeo);
       //Query q = em.createNativeQuery(cadenaQuery, cadenaMapeo);
//       if (map != null) {
//           map.keySet().forEach(s -> q.setParameter(s, map.get(s)));
//       }
       List<T> list = q.getResultList();
      //session.clear();
      //session.close();
      session.flush();
       return list;
   }

   public <F> List<F> castList(List<?> srcList, Class<F> clas) {
      List<F> list = new ArrayList<F>();
      for (Object obj : srcList) {
         if (obj != null && clas.isAssignableFrom(obj.getClass())) {
            list.add(clas.cast(obj));
         }
      }
      return list;
   }

public ModelMapper conv() {
	ModelMapper modelMapper = new ModelMapper();
	modelMapper.getConfiguration().setAmbiguityIgnored(true);
	return modelMapper;
}


}
