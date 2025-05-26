package mx.com.facturacion.factura;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DatosCertificadoDTO implements Serializable {

	private String numeroCertificado;
	private String certificado64;

	public String getNumeroCertificado() {
		return numeroCertificado;
	}

	public void setNumeroCertificado(String numeroCertificado) {
		this.numeroCertificado = numeroCertificado;
	}

	public String getCertificado64() {
		return certificado64;
	}

	public void setCertificado64(String certificado64) {
		this.certificado64 = certificado64;
	}

}
