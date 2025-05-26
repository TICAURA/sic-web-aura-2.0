package mx.com.consolida.ppp.dto;

public enum FacturadoresENUM {
	SW(9945L,	"SW","SW"),
	FACTURADOR(9946L,"FACTURADOR","FACTURADOR.COM"),
	FACT_INT(9947L,"FACT_INT","FACTURA INTELIGENTE");
	
	private Long id;
	private String clave;
	private String desc;
	
	private FacturadoresENUM(Long id, String clave, String desc) {
		this.id = id;
		this.clave = clave;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}

	public String getClave() {
		return clave;
	}

	public String getDesc() {
		return desc;
	}
}
