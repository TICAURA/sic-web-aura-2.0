package mx.com.consolida.util;

public enum TimeFix {

   CONV_PRIMER_PERIODO_AP(0, 0, 0, 8), CONV_PRIMER_PERIODO_CR(0, 0, 0, 18), CONV_RECEPCION_PROPUESTA_AP(0, 0, 0,
            18), CONV_RECEPCION_PROPUESTA_CR(0, 0, 0, 18), CONV_EVALUACION_PROPUESTA_AP(0, 0, 0,
                     18), CONV_EVALUACION_PROPUESTA_CR(0, 0, 0, 18), CONV_FORMALIZACION_AP(0, 0, 0,
                              18), CONV_FORMALIZACION_CR(0, 0, 0, 18), CONV_PUBLICACION_RESULTADO(0, 0, 0, 18);

   private Integer millisecond;
   private Integer second;
   private Integer minute;
   private Integer hourOfDay;

   private TimeFix(Integer millisecond, Integer second, Integer minute, Integer hourOfDay) {
      this.millisecond = millisecond;
      this.second = second;
      this.minute = minute;
      this.hourOfDay = hourOfDay;
   }

   public Integer getMillisecond() {
      return millisecond;
   }

   public Integer getSecond() {
      return second;
   }

   public Integer getMinute() {
      return minute;
   }

   public Integer getHourOfDay() {
      return hourOfDay;
   }

}
