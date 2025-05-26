package mx.com.consolida.converter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Convierte Entidades a DTOS y viceversa para que pueda convertirlo de manera adecuado es requerido que el dto se llame igual que la entidad mas las siglas DTO
 * Ejemplo: 
 *      Entidad = Prueba
 *      DTO = PruebaDTO
 *  private final Converter<ApColaboradorEO, ColaboradorDTO> converterUsuario = new Converter<ApColaboradorEO, ColaboradorDTO>(ApColaboradorEO.class, ColaboradorDTO.class);
 */
public class Converter<E,D> {
    
    final Class<E> entityClass;
    final Class<D> dtoClass;

    public Converter(Class<E> entityClass, Class<D> dtoClass) {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }
     
     public E obtenerEntity(D dto) throws TechnicalException{
         try{
            E entity = (E)this.entityClass.newInstance();    
            Builder builderG = new Builder<E,D>();

            BuilderObjects boBuilder = new BuilderObjects(entity, dto);
            boBuilder = builderG.convertDTOtoEO(boBuilder);
            return (E)boBuilder.getEntity();
             
//         } catch (InstantiationException | IllegalAccessException ex) {
         } catch (Exception ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException("no se pudo obtener el DTO");
        }
     }
     
     public D obtenerDTO(E entity) throws TechnicalException {
        try {
            D dto = (D) this.dtoClass.newInstance();
            Builder builderG = new Builder<E, D>();
            BuilderObjects boBuilder = new BuilderObjects(entity, dto);
            boBuilder = builderG.convertEOtoDTO(boBuilder);
            return (D) boBuilder.getDto();

//        } catch (InstantiationException | IllegalAccessException ex) {
        } catch (Exception ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            throw new TechnicalException("no se pudo obtener el DTO");
        }
    }
}
