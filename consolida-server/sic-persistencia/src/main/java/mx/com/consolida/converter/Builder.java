package mx.com.consolida.converter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
/**
 * @author Abel
 */
public class Builder<E,D> {

    private static final Logger LOGGER = Logger.getLogger(Builder.class);
    private static final Integer NIVELMAXIMO = 2;
    private static final Integer NIVELMAXIMOLISTA = 1;
    private Integer nivel=1;
    private boolean lista;
    private Integer nivelLista = 1;
    
    public BuilderObjects convertEOtoDTO(BuilderObjects<E, D> builderBO) {
        try {
            if (builderBO.getState().equals(BuilderObjects.STATE.INIT)) {
                copiarValores(builderBO.getEntity(), builderBO.getDto());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return builderBO;
    }

    public BuilderObjects convertDTOtoEO(BuilderObjects<E, D> builderBO) {
        try {
            if (builderBO.getState().equals(BuilderObjects.STATE.INIT)) {
                copiarValores(builderBO.getDto(), builderBO.getEntity());
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return builderBO;
    }   
    
    private void copiarValores(Object a, Object b){
        Class<?> clazzA = a.getClass();
        Class<?> clazzB = b.getClass();
        Field[] fieldsA = clazzA.getDeclaredFields();
        for (Field fieldA : fieldsA) {
            try {
                fieldA.setAccessible(true);
                if (!Utilerias.isMethodValid(fieldA)) {
                    continue;
                }
                Object valor;
                Field fieldB;
                BuilderField property = null;
                if (fieldA.isAnnotationPresent(BuilderField.class)) {
                    property = fieldA.getAnnotation(BuilderField.class);
                    if (!property.load()) {
                        continue;
                    }
                }

                if (property != null && property.mappedField()!=null ) {
                    fieldB = clazzB.getDeclaredField(property.mappedField());
                } else {
                    fieldB = clazzB.getDeclaredField(fieldA.getName());
                }

                if (fieldB == null) {
                    continue;
                } else {
                    fieldB.setAccessible(true);
                }

                if (property != null && !property.property().isEmpty()) {
                    valor = getValue(a, property.property());
                } else {
                    valor = getValue(a, fieldA.getName());
                }
                
                if (isEqualsField(fieldB, fieldA)) {
                    setValue(b, fieldB.getName(), valor);
                } else if (isEqualObject(fieldB, fieldA) && !lista ) {
                    if (NIVELMAXIMO >= nivel) {
                        nivel = nivel+1;
                        String[] arre = fieldA.getType().getName().split("\\.");
                        String dato = arre[arre.length-1];
                        if("List".equals(dato)){
                            converterListNivelDTO(fieldB, valor, b);
                        }else{
                            converterNivelDTO(fieldB, valor,b);
                        }
                        nivel = nivel-1;
                    }else{
                        setValue(b, fieldB.getName(), null);
                    }
                } else if(lista && NIVELMAXIMOLISTA.equals(nivelLista)){
                    nivelLista = nivelLista+1;
                    String[] arre = fieldA.getType().getName().split("\\.");
                    String dato = arre[arre.length-1];
                    if (!"List".equals(dato)) {
                        converterNivelDTO(fieldB, valor, b);
                    }
                    nivelLista = nivelLista-1;
                }else {
                    setValue(b, fieldB.getName(), null);
                }
//            } catch (NoSuchFieldException | SecurityException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException ex) {
            } catch (Exception ex) {
//                LOGGER.info(ex.getMessage(), ex);
            }
        }
    }
    public void converterNivelEntity(Field fieldDest, Object objOrig,Object objDest) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object d = fieldDest.getType().newInstance();
        copiarValores(objOrig, d);
        PropertyUtils.setProperty(objDest, fieldDest.getName(), d);
    }
    
    public void converterNivelDTO(Field fieldDest, Object objOrig,Object objDest) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        Object d = fieldDest.getType().newInstance();
        if (objOrig != null) {
            copiarValores(objOrig, d);
            PropertyUtils.setProperty(objDest, fieldDest.getName(), d);
        }
    }
    public void converterListNivelDTO(Field fieldDest, Object objOrig,Object objDest) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        List<Object> list = (List<Object>) objOrig;
        if (list != null && !list.isEmpty()) {
            lista = true;
            Type friendsGenericType = fieldDest.getGenericType();
            ParameterizedType friendsParameterizedType = (ParameterizedType) friendsGenericType;
            friendsParameterizedType.getActualTypeArguments();
            Type[] friendsType = friendsParameterizedType.getActualTypeArguments();
            Class obj1 = (Class) friendsType[0];
            List<Object> listaDestino = new ArrayList<Object>();
            for (Object object : list) {
                Object obj = obj1.newInstance();
                copiarValores(object, obj);
                listaDestino.add(obj);
            }
            PropertyUtils.setProperty(objDest, fieldDest.getName(), listaDestino);
        }
        lista = false;
    }
    
    public static boolean isEqualObject(Field fieldOrig, Field fieldDest){
        String[] arreNombreOrig = fieldOrig.getType().getName().split("\\.");
        String[] arreNomnreDest = fieldDest.getType().getName().split("\\.");
        String nombreOrig = arreNombreOrig[arreNombreOrig.length-1].replace("Dto", "");
        String nombreDest = arreNomnreDest[arreNomnreDest.length-1].replace("Dto", "");
        return nombreOrig.equals(nombreDest);
    }
    
    public static boolean isEqualsField(Field fieldOrig, Field fieldDest) {
        String[] arre = fieldDest.getType().getName().split("\\.");
        String dato = arre[arre.length-1];
        return fieldOrig.getType().equals(fieldDest.getType()) && !"List".equals(dato);
    }

    public static Object getValue(Object bean, String sname) {
        Object value = null;
        try {
            value = PropertyUtils.getProperty(bean, sname);
//        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage(),ex);
        } 
        return value;
    }

    public static void setValue(Object bean, String sname, Object value) {
        try {
            PropertyUtils.setProperty(bean, sname, value);
//        }  catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage(), ex);
        }
    }
}

