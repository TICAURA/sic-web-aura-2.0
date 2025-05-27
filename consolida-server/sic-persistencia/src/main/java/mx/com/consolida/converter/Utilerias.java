package mx.com.consolida.converter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * Clase de utilerías de apoyo al sistema.
 *
 * @author Abel
 */
public class Utilerias {
    
    private static final Logger LOGGER = Logger.getLogger(Utilerias.class);
    
    private static final String ACTIVO = "Activo";
    private static final String INACTIVO = "Inactivo";
    public static final Locale LOCALE_MX = new Locale("es", "MX");
    
    private Utilerias() {
        //DO NOTHING
    }
    
    public static String getConcatenar(String obj1, String obj2) {
        try {
            StringBuilder sBuilder = new StringBuilder();
            sBuilder.append(obj1);
            sBuilder.append(obj2);
            return sBuilder.toString();
        } catch (Exception e) {
            LOGGER.error(e);
            return null;
        }
    }

    /**
     * Convierte una cadena con el formato dd/MM/yyyy a una fecha.
     *
     * @param psFecha la cadena que contiene la fecha formateada.
     * @return el objeto java.util.Date definido por la cadena.
     */
    public static Date convierteStringToDate(String psFecha) {
        Date dtFecha = null;
        String format="dd/MM/yyyy";
        DateFormat dfFormatoFecha = new SimpleDateFormat(format);
        try {
            if (psFecha != null) {
                dtFecha = dfFormatoFecha.parse(psFecha);
            }
        } catch (ParseException ex) {
            LOGGER.error(ex);
        }
        return dtFecha;
    }
    
    public static String convierteDate(Date psFecha) {
    	String fecha=null;
    	
    	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy") ;
    	String formatted = dateFormat.format(psFecha);
    	fecha=formatted.substring(0,3) + getMes(formatted.substring(3,5)) + formatted.substring(5,10);
        return fecha;
    }
    
    
    public static String getMes(String mes){

        switch (mes) {
          case "01":
            return "ENE.";
          case "02":
            return "FEB.";
          case "03":
            return "MAR.";
          case "04":
            return "ABR.";
          case "05":
            return "MAY.";
          case "06":
            return "JUN.";
          case "7":
            return "JUL.";
          case "08":
            return "AGOS.";
          case "09":
            return "SEP.";
          case "10":
            return "OCT.";
          case "11":
            return "NOV.";
          case "12":
            return "DIC.";
          default:
            break;
        }
        return mes;
      }
    
    /**
     * 
     * @param pdFecha
     * @param formato
     * @return
     * @throws ParseException
     */
   
    public static String convirteStringToString(String pdFecha, String formato) throws ParseException {
    SimpleDateFormat forma = new SimpleDateFormat("dd/MM/yyyy"); 
		Date fecha = forma.parse(pdFecha);
    SimpleDateFormat sdfFormatoFecha = new SimpleDateFormat(formato);
    return (fecha != null) ? sdfFormatoFecha.format(fecha) : "";
}
    /**
     * Aumenta o disminuye años a una fecha
     * @param fecha fecha a la que se le aumentaran años
     * @param anios años que se le aumentaran a la fecha
     * @return  regresa la fecha ya con los años aumnetados
     */
    public static Date fechaAnios(Date fecha, Integer anios) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        if (anios != null) {
            calendar.add(Calendar.YEAR, anios);
        }
        return calendar.getTime();
    }
    /**
     * obtener el año de una fecha
     * @param fecha fecha de la cual se obtendra el año
     * @return  regresa el año de uma fecha
     */
    public static Integer obtenAnios(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * obtener el Mes de una fecha
     * @param fecha fecha de la cual se obtendra el mes
     * @return  regresa el mes que trae la fecha
     */
    public static Integer obtenMes(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH)+1;
    }
    
    public static String obtenMes(Integer fecha) {
        Calendar fechaCalendar = Calendar.getInstance();
        fechaCalendar.setTime(new Date());
        fechaCalendar.set(Calendar.MONTH, fecha-1);
        String mes = fechaCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "ES"));
        return mes.substring(0,1).toUpperCase().concat(mes.substring(1));
    }
    public static String obtenMesFromDate(Date fecha) {
        Calendar fechaCalendar = Calendar.getInstance();
        fechaCalendar.setTime(fecha);
        String mes = fechaCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, new Locale("es", "ES"));
        return mes.substring(0,1).toUpperCase().concat(mes.substring(1));
    }
    
    public static String primerosMeses(Integer fecha) {
        switch (fecha){
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            default:
                return "";
        }
    }
    public static String mitadMeses(Integer fecha) {
        switch (fecha){
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            default:
                return "";
        }
        
    }
    public static String ultimosMeses(Integer fecha) {
        switch (fecha){
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                return "";
        }
    }
    /**
     * obtener dia de de una fecha
     * @param fecha fecha de la cual se obtendra el día
     * @return  regresa el dia que trae la fecha
     */
    public static Integer obtenDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * obtener ultimo dia de de una fecha
     * @param fecha fecha de la cual se obtendra el día
     * @return  regresa el ultimo dia que trae la fecha
     */
    public static Integer obtenUltimoDia(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Convierte una cadena que contiene una fecha con el formato dado a fecha.
     *
     * @param formato el formato de fecha que será convertido.
     * @param psFecha la cadena que contiene la fecha formateada.
     * @return el objeto java.util.Date definido por la cadena.
     */
    public static Date convierteStringToDate(String formato, String psFecha) {
        Date dtFecha = null;
        DateFormat dfFormatoFecha = new SimpleDateFormat(formato);
        try {
            if (psFecha != null) {
                dtFecha = dfFormatoFecha.parse(psFecha);
            }
        } catch (ParseException ex) {
            LOGGER.error(ex);
        }
        return dtFecha;
    }

    /**
     * Convierte una fecha a una cadena con el formato dd/MM/yyyy.
     *
     * @param pdFecha el objeto java.util.Date que representa la fecha a formatear.
     * @return la cadena con la fecha formateada.
     */
    public static String convirteDateToString(Date pdFecha) {
        String format="dd/MM/yyyy";
        return convirteDateToString(pdFecha, format);
    }

    /**
     * Convierte una fecha a una cadena con el formato dd/MM/yyyy.
     *
     * @param pdFecha el objeto java.util.Date que representa la fecha a formatear.
     * @param formato el formato de fecha deseado.
     * @return la cadena con la fecha formateada.
     */
    public static String convirteDateToString(Date pdFecha, String formato) {
        SimpleDateFormat sdfFormatoFecha = new SimpleDateFormat(formato);
        return (pdFecha != null) ? sdfFormatoFecha.format(pdFecha) : "";
    }

    /**
     * Convierte un objeto java.math.BigInteger a una cadena.
     *
     * @param pbiValor el valor que será convertido.
     * @return Activo si el valor es 1; Inactivo si el valor es 0.
     */
    public static String qconvierteBigIntToStringCat(BigInteger pbiValor) {
        String sValor = "";
        String activo=ACTIVO;
        String inactivo=INACTIVO;
        try {
            if (pbiValor != null) {
                sValor = pbiValor.intValue() == 1 ? activo : inactivo;
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return sValor;
    }

    /**
     * Convierte una cadena a un valor de tipo java.math.BigInteger.
     *
     * @param pbiValor el valor que será convertido.
     * @return 1 si el valor es 1; 0 si el valor es 0.
     */
    public static BigInteger qconvierteStringCAtToBigInt(String pbiValor) {
        BigInteger sValor = null;
        String v1="1";
        String v0="0";
        try {
            if (pbiValor != null) {
                if (pbiValor.equals(v1)) {
                    sValor = new BigInteger(v1);
                } else {
                    sValor = new BigInteger(v0);
                }
            } else {
                sValor = new BigInteger(v0);
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return sValor;
    }

    /**
     * Convierte una cadena que define una baja a un objeto de tipo java.math.BigInteger.
     *
     * @param pbiValor la cadena que determina la baja.
     * @return 1 si la cadena es "baja"; 0 en caso contrario.
     */
    public static BigInteger convBajaStringToInt(String pbiValor) {
        BigInteger sValor = null;
        String baja="baja";
        String v1="1";
        String v0="0";
        try {
            if (pbiValor.equals(baja)) {
                sValor = new BigInteger(v1);
            } else {
                sValor = new BigInteger(v0);
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return sValor;
    }

    /**
     * Convierte el valor de un objeto de tipo java.math.BigInteger que define
     * un status a una cadena.
     *
     * @param pbiValor el valor a convertir.
     * @return Activo si el valor es 1; Inactivo en caso contrario.
     */
    public static String convertEstatus(BigInteger pbiValor) {
        String aux = String.valueOf(pbiValor);
        String sValor = "";
        String activo=ACTIVO;
        String inactivo=INACTIVO;
        String v1="1";
        try {
            if (aux.equals(v1)) {
                sValor = activo;
            } else {
                sValor = inactivo;
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
        return sValor;
    }

    /**
     * Convierte a cadena el valor de una status definido por un opbjeto java.lang.Boolean.
     *
     * @param pbiValor el valor que será convertido.
     * @return Activo si el valor es true; Inactivo en caso contrario.
     */
    public static String convertEstatus(Boolean pbiValor) {
        String sValor;
        String activo=ACTIVO;
        String inactivo=INACTIVO;
        if (pbiValor) {
            sValor = activo;
        } else {
            sValor = inactivo;
        }
        return sValor;
    }

    /**
     * LEE EL INPUT Y VACIA LOS DATOS AL OUPUT, PARA OBTENER LOS DATOS COMO
     * ARREGLO DE BYTES.
     *
     * @param datosEncInput
     *
     * @return El arreglo de bytes
     *
     * @throws IOException Senial de que una I/O exception ha ocurrido.
     */
    public static byte[] getBytes(InputStream datosEncInput) throws IOException {
        BufferedOutputStream dest;
        ByteArrayOutputStream bout;
        int bufferSize = 8192;
        int count;
        byte[] data = new byte[bufferSize];
        bout = new ByteArrayOutputStream();
        dest = new BufferedOutputStream(bout, bufferSize);
        while ((count = datosEncInput.read(data, 0, bufferSize)) != -1) {
            dest.write(data, 0, count);
        }
        dest.flush();
        dest.close();
        return bout.toByteArray();
    }
    public static String removeAccents(String text) {
        return text == null ? null
                : Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    } 
    
    public static String cambiarCaracteres(String cadena,String datoViejo,String datoNuevo) {
        String iniciales="";
        if (cadena != null && !cadena.isEmpty()) {
            iniciales = cadena.replaceAll(datoViejo, datoNuevo);
        }
        return iniciales;
    }
    
    public static boolean isMethodValid(Field field) {
        if (null == field || null == field.getName()){
            return true;
        }
        if("serialVersionUID".equals(field.getName())){
            return false;
        } else{
            return !field.getName().contains("_persistence");
        }
    }
    
    public static boolean validarConsecutivo(Integer inicio, List<Integer> listaNumeros, boolean cons) {
        Integer nextNumero = inicio;
        for(int i = 0; i < listaNumeros.size(); i++) {
            if(cons && listaNumeros.get(i) - nextNumero == 1) {
                nextNumero = listaNumeros.get(i);
            } else if (!cons && (nextNumero.equals(listaNumeros.get(i)) || listaNumeros.get(i) - nextNumero == 1)){
                nextNumero = listaNumeros.get(i);
            }else {
                return false;
            }
        }
        return true;
    }   

    public static String getNombreArchivoFormato(String name) {
        String numeroAleatorio = Integer.toString((new Random()).nextInt(Integer.MAX_VALUE));
        return numeroAleatorio + "_"+ name;
    }
    /**
     * Aumenta o disminuye días a una fecha
     * @param fecha fecha a la que se le aumentaran dias
     * @param dias dias que se le aumentaran a la fecha
     * @return  regresa la fecha ya con los dias aumnetados
     */
    public static Date fechaDias(Date fecha, Integer dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        if (dias != null) {
            calendar.add(Calendar.DAY_OF_YEAR, dias);
        }
        return calendar.getTime();
    }
    /**
     * obtener dia de la semana
     * @param fecha fecha de la cual se obtendra el día
     * @return  regresa el dia de la semana que trae la fecha
     */
    public static Integer obtenDiaDeSemana(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public static Integer obtenHora(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static String getValueStringWithLikePartSQL(Object parametro) {
        return "%" + String.valueOf(parametro) + "%";
    }
    
    /**
     * Convierte una fecha a una cadena con formato dd/MMM/yyyy. El mes no se presenta en numérico
     *
     * @param pdFecha el objeto java.util.Date que representa la fecha a formatear.
     * @return la cadena con la fecha formateada.
     */
    public static String convirteDateToStringMesEnLetra(Date pdFecha) {
    	SimpleDateFormat format2 = new SimpleDateFormat("dd/MMM/yyyy", LOCALE_MX);
        return format2.format(pdFecha).toUpperCase();
    }
    

}
