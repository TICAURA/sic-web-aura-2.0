package mx.com.consolida.reportes;

import com.google.gson.Gson;
public class JSONParser {
	private static Gson gson = new Gson();

	   /**
	    * Convierte un objeto a JSON
	    * 
	    * @param objeto
	    * @return
	    */
	   public static String convertirObjetoAJSON(Object objeto) {
	      return gson.toJson(objeto);
	   }

	   /**
	    * Convierte un JSON a un objeto
	    * 
	    * @param json
	    * @param clase
	    * @return
	    */
	   public static <T> T convertirJSONAObjeto(String json, Class<T> clase) {
	      return gson.fromJson(json, clase);
	   }

	   public static Gson getGson() {
	      return gson;
	   }
}
