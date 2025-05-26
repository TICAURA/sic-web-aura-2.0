package mx.com.consolida.controller.tools;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.com.consolida.controller.base.BaseController;


/**
 * Clase para encriptado y desencriptado con algoritmo AES. 
 * Se realiza esta clase para encriptado de cadenas de dispersión stp
 * Cadena original para generar firma stp
 * y json enviado para la operación de dispersión 
 * 
 * @author Noé Briones Pérez
 *
 */

@Controller
@RequestMapping("ppp")
public class AESSymetricCrypto extends BaseController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AESSymetricCrypto.class);
	
	@Value("${stpKey}")
	private String stpKey;

	private KeyGenerator keyGenerator;
	private Key key;
	
	/*
	 * Método para inicializar la llave cuando se llama desde el front
	 */
	@RequestMapping(value = "/cryptInit")
	@ResponseBody
	public void main() throws Exception {
		keyGenerator =  KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		key = new SecretKeySpec(stpKey.getBytes(), 0, 16, "AES");
	}
	
	/*
	 * Método para inicializar la llave desde otro controller
	 */
	
	public void  cryptInit(String stpKey) throws Exception {
		keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		key = new SecretKeySpec(stpKey.getBytes(), 0, 16, "AES");
	}

	/*
	 * Método para encriptar cadenas 
	 */
	@RequestMapping(value = "/encrypt")
	@ResponseBody
	public String encryp(String texto) throws Exception {
		String textoEncriptado = "";

		// Se obtiene un cifrador AES
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		// Se inicializa para encriptación y se encripta el texto,
		// que debemos pasar como bytes.
		aes.init(Cipher.ENCRYPT_MODE, key);
		byte[] encriptado = aes.doFinal(texto.getBytes());

		// Se convierte de byte[] a string base64
		Base64 base64 = new Base64();
		textoEncriptado = base64.encodeAsString(encriptado);
		
		return textoEncriptado;
	}
	
	/*
	 * Método para desencriptar
	 */

	@RequestMapping(value = "/decrypt")
	@ResponseBody
	public String decrypt(@RequestBody String texto) throws Exception {
		
		//se verifica  que la lleve este creado, si no es así se inicializa
		if (key ==null) 
			main();
		
		String textoDesencriptado = "";
		Base64 base64 = new Base64();
		
		// Se convierte de string base64 a byte[]
		byte[] encript = base64.decode(texto);

		// Se obtiene un cifrador AES
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

		// Se inicializa para desencriptacion ,
		// que debemos pasar como bytes.
		aes.init(Cipher.DECRYPT_MODE, key);
		byte[] desencriptado = aes.doFinal(encript);
		textoDesencriptado =new String(desencriptado) ;
		
		
		if (textoDesencriptado.substring(0, 1).equals("|")) {
			return formatObject(textoDesencriptado);
		} else {
			// se retorno objeto json
			return textoDesencriptado;
		}
		
	}
	
	/*
	 * Quita pipe | los reemplaza por ":", y  da formato jsonArray
	 */
	
	private String formatObject(String text	) {
		
		StringBuilder builder = new StringBuilder();
		String elemArray[] = text.split("|");
		builder.append("[");
		
		for (String str : elemArray) {
			if (str.equals("|")) {
				builder.append("\":\",");
			} else {
				builder.append("\"" + str + "\",");
			}
		}

		// quita  la última ",", para dar formato correcto al jsonArray
		String str = builder.substring(0, builder.length() - 1);

		return str + "]";
	
		
	}
}