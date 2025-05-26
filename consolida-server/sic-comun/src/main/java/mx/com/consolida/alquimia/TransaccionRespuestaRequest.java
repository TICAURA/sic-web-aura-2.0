package mx.com.consolida.alquimia;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


public class TransaccionRespuestaRequest {
	
	private String id;
	private String empresa;
	private String claveRastreo;
	private String monto;
	private String estado;
	private String causaDevolucion;
    private String tsLiquidacion;
	
	
	public TransaccionRespuestaRequest() {
			// TODO Auto-generated constructor stub
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getEmpresa() {
		return empresa;
	}


	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}


	public String getClaveRastreo() {
		return claveRastreo;
	}


	public void setClaveRastreo(String claveRastreo) {
		this.claveRastreo = claveRastreo;
	}


	public String getMonto() {
		return monto;
	}


	public void setMonto(String monto) {
		this.monto = monto;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getCausaDevolucion() {
		return causaDevolucion;
	}


	public void setCausaDevolucion(String causaDevolucion) {
		this.causaDevolucion = causaDevolucion;
	}


	public String getTsLiquidacion() {
		return tsLiquidacion;
	}


	public void setTsLiquidacion(String tsLiquidacion) {
		this.tsLiquidacion = tsLiquidacion;
	}


	
		
	
	 

}
