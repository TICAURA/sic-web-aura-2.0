/**
 * 
 */
package mx.com.consolida.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class FormatoFecha {

	public static final String FORMATO_FECHA_MMMMM_YYYY = "MMMMM 'del' yyyy";
	public static final String FORMATO_FECHA_DD_MMMMM_YYYY = "dd MMMMM yyyy";
	public static final String FORMATO_FECHA_DD_DE_MMMMM_DE_YYYY = "dd 'de' MMMMM 'de' yyyy";
	public static final String FORMATO_FECHA_DD_DE_MMMMM = "dd 'de' MMMMM 'de'";
	public static final String FORMATO_FECHA_HOY = "'México, Ciudad de México a' dd 'de' MMMMM 'de' yyyy";
	public static final String FORMATO_FECHA_HOY_INGLES = "'Mexico City', MMMMM dd',' yyyy";
	public static final String FORMATO_FECHA_INGLES = "MMMM dd',' yyyy";
	public static final String FORMATO_FECHA_CAREXT = "dd 'DEL MES DE ' MMMMM 'DEL' yyyy";
	public static final String FORMATO_FECHA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMATO_FECHA_DD_MM_YY = "dd/MM/yy";
	public static final String FORMATO_FECHA_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String FORMATO_FECHA_DD_MMM_YYYY = "dd/MMM/yyyy";
	public static final String FORMATO_FECHA_ESPANA_DD_MMM_YYYY = "dd 'DÍAS DEL MES DE ' MMMMM 'DE' yyyy";
	public static final String FORMATO_FECHA_DD_MMMMM_YYYY_2 = "dd/MMMMM/yyyy";
	public static final String FORMATO_FECHA_DD_MMM_YYYY_2 = "ddMMMyyyy";
	public static final String FORMATO_FECHA_DD_MMM_YYYY_HH_MM_SS = "dd/MMM/yyyy HH:mm:ss";
	public static final String FORMATO_FECHA_DD_MMM_YYYY_HH_MM = "dd/MMM/yyyy HH:mm";
	public static final Locale LOCALE_ES_MX = new Locale("es", "MX");
	public static final Locale LOCALE_EN_US = new Locale("en", "US");
	public static final Locale LOCALE_ES_ES = new Locale("es", "ES");
	public static final String FORMATO_FECHA_MM_MMM_DD_YYYY_HH_MM = "MM-MMM/dd/yyyy HH:mm";
	public static final String FORMATO_FECHA_DD_MM_YY_HH_MM_SS_S = "dd/MM/yy HH:mm:ss.S";
	public static final String FORMATO_FECHA_YYYY_MM_DD_HH_MM_SS_Z = "yyyy-MM-dd HH:mm:ss z";
	public static final String TIME_ZONE_UTC = "UTC";
	public static final String FORMATO_FECHA_MMMM_YYYY = "'Ciudad de México a' dd 'de' MMMMM 'de' yyyy";
	public static final String FORMATO_FECHA_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static int getDiferenciaDias(Date fecha1, Date fecha2) {

		return Math.abs((int) ((fecha2.getTime() - fecha1.getTime()) / 86400000));
	}

	public static final int getAnio(Date date) {

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(date);

		return fecha.get(Calendar.YEAR);
	}

	public static final int getDiasMes(Date date) {

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(date);

		return fecha.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final String formatoMesAnio(Date fecha) {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA_MMMMM_YYYY, LOCALE_ES_MX);

		return sdf.format(fecha);
	}

	public static final String formatoFecha(Date fecha, String formato, Locale localidad) {

		SimpleDateFormat sdf = new SimpleDateFormat(formato, localidad);

		return sdf.format(fecha);
	}

	public static final Date formatoFechaDate(Date fecha, String timeZone) {

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		calendar.setTime(fecha);

		return calendar.getTime();
	}

	public static final Date formatoFecha(String cadena) {

		if (cadena == null) {

			return null;

		} else {

			DateFormat format = new SimpleDateFormat(FORMATO_FECHA_DD_MM_YY_HH_MM_SS_S);

			try {

				return format.parse(cadena);

			} catch (ParseException e) {

				return null;
			}
		}
	}

	public static final Date formatoFecha(String cadena, String formato, Locale localidad) {

		if (cadena == null) {

			return null;

		} else {

			DateFormat format = new SimpleDateFormat(formato, localidad);

			try {

				return format.parse(cadena);

			} catch (ParseException e) {

				return null;
			}
		}
	}

	public static final Date getFechaActual() {

		Calendar fechaActual = Calendar.getInstance();
		return fechaActual.getTime();
	}

	public static final Date getFechaActualSinHora() {

		Calendar fechaActual = Calendar.getInstance();
		return FormatoFecha.setHoraACero(fechaActual).getTime();
	}

	public static final Date getFechaSinHora(Date date) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return FormatoFecha.setHoraACero(calendar).getTime();
	}

	public static final Date getFechaHoraFija(Date date, TimeFix timeFix) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(Calendar.MILLISECOND, timeFix.getMillisecond());
		cal.set(Calendar.SECOND, timeFix.getSecond());
		cal.set(Calendar.MINUTE, timeFix.getMinute());
		cal.set(Calendar.HOUR_OF_DAY, timeFix.getHourOfDay());

		return cal.getTime();
	}

	public static final Calendar setHoraACero(Calendar fecha) {

		fecha.set(Calendar.HOUR_OF_DAY, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		return fecha;
	}

	public static final XMLGregorianCalendar getXMLGregorianCalendar(Date fecha) throws DatatypeConfigurationException {

		GregorianCalendar gregory = new GregorianCalendar();
		gregory.setTime(fecha);

		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
	}

	public static final String formatoFechaEstandar(final Date fecha) {

		return formatoFecha(fecha, FORMATO_FECHA_DD_MMM_YYYY, LOCALE_ES_MX);
	}

	public static final String formatoFechaMMJF(final Date fecha) {
		return formatoFecha(fecha, FORMATO_FECHA_ESPANA_DD_MMM_YYYY, LOCALE_ES_MX);
	}

	public static final String formatoFechaFirma(final Date date) {

		return formatoFecha(date, FORMATO_FECHA_DD_MMM_YYYY_2, LOCALE_ES_MX);
	}

	public static final String formatoFechaCAR(final Date date) {

		return formatoFecha(date, FORMATO_FECHA_DD_DE_MMMMM_DE_YYYY, LOCALE_ES_MX);
	}

	public static final String formatoFechaIngles(final Date date) {

		return formatoFecha(date, FORMATO_FECHA_INGLES, LOCALE_EN_US);
	}

	public static final String formatoFechaCompleto(final Date date) {

		return formatoFecha(date, FORMATO_FECHA_HOY, LOCALE_ES_MX);
	}

	public static final Date calculaFechaMasMeses(Date fechaInicial, int mesesASumar) {

		Date fechaCalculada = null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicial);
		calendar.add(Calendar.MONTH, mesesASumar);

		fechaCalculada = calendar.getTime();

		return fechaCalculada;
	}

	public static int calcularDiferenciaDeMeses(Date fechaInicio, Date fechaFin) {

		Calendar inicio = new GregorianCalendar();
		Calendar fin = new GregorianCalendar();

		inicio.setTime(fechaInicio);
		fin.setTime(fechaFin);

		int anioInicio = inicio.get(Calendar.YEAR);
		int anioFin = fin.get(Calendar.YEAR);

		int mesInicio = inicio.get(Calendar.MONTH) + 1;
		int mesFin = fin.get(Calendar.MONTH) + 1;

		int diaFin = fin.get(Calendar.DAY_OF_MONTH);

		if (diaFin > 01)
			mesFin++;

		return (anioFin - anioInicio) * 12 + (mesFin - mesInicio);
	}

	public static int calcularEdad(Date fechaNacimiento) {

		Calendar calendarNacimiento = Calendar.getInstance();
		calendarNacimiento.setTime(fechaNacimiento);

		Calendar fechaActual = Calendar.getInstance();

		// Cálculo de las diferencias.
		int years = fechaActual.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR);
		int months = fechaActual.get(Calendar.MONTH) - calendarNacimiento.get(Calendar.MONTH);
		int days = fechaActual.get(Calendar.DAY_OF_MONTH) - calendarNacimiento.get(Calendar.DAY_OF_MONTH);

		// Hay que comprobar si el día de su cumpleaños es posterior
		// a la fecha actual, para restar 1 a la diferencia de años,
		// pues aún no ha sido su cumpleaños.

		if (months < 0 // Aún no es el mes de su cumpleaños
				|| (months == 0 && days < 0)) { // o es el mes pero no ha
												// llegado el día.

			years--;
		}

		return years;
	}

	/**
	 * Suma n dias a una fecha
	 * 
	 * @param fechaInicial
	 * @param diasASumar
	 * @return diferencia
	 */
	public static Date calcularFechaMasDias(Date fechaInicial, int diasASumar) {

		Date fechaCalculada = null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicial);
		calendar.add(Calendar.DATE, diasASumar);

		fechaCalculada = calendar.getTime();

		return fechaCalculada;
	}

	public static final Date calculaFechaMasAnios(Date fechaInicial, int aniosASumar) {

		Date fechaCalculada = null;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaInicial);
		calendar.add(Calendar.YEAR, aniosASumar);

		fechaCalculada = calendar.getTime();

		return fechaCalculada;
	}

	public static final Date getFechaPrimerDiaMes(Date date) {

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(date);

		int ultimoDiaMes = fecha.getActualMinimum(Calendar.DAY_OF_MONTH);

		Calendar fechaSalida = Calendar.getInstance();

		fechaSalida.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), ultimoDiaMes);

		return fechaSalida.getTime();
	}

	public static final Date getFechaUltimoDiaMes(Date date) {

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(date);

		int ultimoDiaMes = fecha.getActualMaximum(Calendar.DAY_OF_MONTH);

		Calendar fechaSalida = Calendar.getInstance();

		fechaSalida.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), ultimoDiaMes);

		return fechaSalida.getTime();
	}

	public static final Date getFechaDeMilisegundos(Long timeInMillis) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMillis);
		return calendar.getTime();
	}

	public static final boolean validaEntreFechas(Date fecha, Date fechaInicio, Date fechaFin) {

		boolean salida = false;

		if ((fecha.equals(fechaInicio) || fecha.after(fechaInicio))
				&& (fecha.equals(fechaFin) || fecha.before(fechaFin))) {

			salida = true;
		}
		return salida;
	}
}