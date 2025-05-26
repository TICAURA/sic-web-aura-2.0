package mx.com.consolida.generico;

public enum CatCotizadorEnum {
	
	SMG( 1,"SMG","SMG"),                     
	SMGZF( 2,"SMGZF","SMGZF"),                                
	SMGV( 3,"SMGV","SMGV"),               
	UMA( 4,"UMA","UMA"),                                
	ISN( 5,"ISN","ISN"),  
	
        CFP(1,"CFP","Cuota Fija"),
        EP (2,"EP","Excedente"),
        ET (3,"ET","Excedente "),
        GMP(4,"GMP","Gastos medicos"),
        GMT(5,"GMT","Gastos medicos"),
        PDP(6,"PDP","Prest Dinero"),
        PDT(7,"PDF","Prest Dinero"),
        IVP(8,"IVP","Inv y Vida"),
        IVT(9,"IVT","Inv y Vida"),
        PDGP(10,"PDGP","Prest Dinero"),
        RP (11,"RP","Retiro"),
        CVP(12,"CVP","Cesantia y Vejez"),
        CVT(13,"CVT","Cesantia y Vejez"),
        AIP(14,"AIP","Aportación INFONAVIT");

	
	private int id;
	private String cve;
	private String desc;
	
	private CatCotizadorEnum(int id, String cve, String desc) {
		this.id = id;
		this.cve = cve;
		this.desc = desc;
	}

	public final int getId() {
		return id;
	}

	public final String getCve() {
		return cve;
	}

	public final String getDesc() {
		return desc;
	}
	
}
