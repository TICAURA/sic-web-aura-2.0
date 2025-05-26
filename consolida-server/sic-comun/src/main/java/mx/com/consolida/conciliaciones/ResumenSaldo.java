package mx.com.consolida.conciliaciones;

public class ResumenSaldo {
	long id;
	String concepto;
	Double montoFondo;
	Double montoOtroIngreso;
	
		
	public ResumenSaldo() {
		
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public Double getMontoFondo() {
		return montoFondo;
	}
	public void setMontoFondo(Double montoFondo) {
		this.montoFondo = montoFondo;
	}
	public Double getMontoOtroIngreso() {
		return montoOtroIngreso;
	}
	public void setMontoOtroIngreso(Double montoOtroIngreso) {
		this.montoOtroIngreso = montoOtroIngreso;
	}

	
	

}
