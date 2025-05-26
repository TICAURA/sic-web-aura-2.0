package mx.com.consolida.converter;

import mx.com.consolida.catalogos.*;
import mx.com.consolida.dto.*;
import mx.com.consolida.entity.*;

/**
 * @author Abel
 */
public class Convertir {

	private final Converter<ClienteTemp, ClienteTempDto> convierteClienteTemp;
	private final Converter<Cotizacion, CotizacionDto> convierteCotizacion;
	private final Converter<CatGeneral, CatGeneralDto> convierteCatGeneral;
	private final Converter<CatTipoPago, CatTipoPagoDto> convierteCatTipoPago;
	private final Converter<ColaboradoresTemp, ColaboradoresTempDto> convierteColaboradoresTemp;
	private final Converter<CotizacionColaborador, CotizacionColaboradorDto> convierteCotizacionColaborador;
	private final Converter<MedioContactoTemp, MedioContactoDto> convierteMedioContactoTemp;
	private final Converter<CotizacionTotales, CotizacionTotalesDto> convierteCotizacionTotalesDto;

    public Convertir() {
    	this.convierteClienteTemp = new Converter<ClienteTemp, ClienteTempDto>(ClienteTemp.class, ClienteTempDto.class);
    	this.convierteCotizacion = new Converter<Cotizacion, CotizacionDto>(Cotizacion.class, CotizacionDto.class);
    	this.convierteCatGeneral = new Converter<CatGeneral, CatGeneralDto>(CatGeneral.class, CatGeneralDto.class);
    	this.convierteCatTipoPago = new Converter<CatTipoPago, CatTipoPagoDto>(CatTipoPago.class, CatTipoPagoDto.class);
    	this.convierteColaboradoresTemp = new Converter<ColaboradoresTemp, ColaboradoresTempDto>(ColaboradoresTemp.class, ColaboradoresTempDto.class);
    	this.convierteCotizacionColaborador = new Converter<CotizacionColaborador, CotizacionColaboradorDto>(CotizacionColaborador.class, CotizacionColaboradorDto.class);
    	this.convierteMedioContactoTemp = new Converter<MedioContactoTemp, MedioContactoDto>(MedioContactoTemp.class, MedioContactoDto.class);
    	this.convierteCotizacionTotalesDto = new Converter<CotizacionTotales, CotizacionTotalesDto>(CotizacionTotales.class, CotizacionTotalesDto.class);
    }

    public CotizacionTotalesDto obtenerDTO(CotizacionTotales enty) throws TechnicalException {
        return convierteCotizacionTotalesDto.obtenerDTO(enty);
    }
    public CotizacionTotales obtenerEntity(CotizacionTotalesDto dto) throws TechnicalException {
        return convierteCotizacionTotalesDto.obtenerEntity(dto);
    }
    
    public MedioContactoDto obtenerDTO(MedioContactoTemp enty) throws TechnicalException {
        return convierteMedioContactoTemp.obtenerDTO(enty);
    }
    public MedioContactoTemp obtenerEntity(MedioContactoDto dto) throws TechnicalException {
        return convierteMedioContactoTemp.obtenerEntity(dto);
    }
    
    public CotizacionColaboradorDto obtenerDTO(CotizacionColaborador enty) throws TechnicalException {
        return convierteCotizacionColaborador.obtenerDTO(enty);
    }
    public CotizacionColaborador obtenerEntity(CotizacionColaboradorDto dto) throws TechnicalException {
        return convierteCotizacionColaborador.obtenerEntity(dto);
    }
    
    public ColaboradoresTempDto obtenerDTO(ColaboradoresTemp enty) throws TechnicalException {
        return convierteColaboradoresTemp.obtenerDTO(enty);
    }
    public ColaboradoresTemp obtenerEntity(ColaboradoresTempDto dto) throws TechnicalException {
        return convierteColaboradoresTemp.obtenerEntity(dto);
    }
    
    public CatTipoPagoDto obtenerDTO(CatTipoPago enty) throws TechnicalException {
        return convierteCatTipoPago.obtenerDTO(enty);
    }
    public CatTipoPago obtenerEntity(CatTipoPagoDto dto) throws TechnicalException {
        return convierteCatTipoPago.obtenerEntity(dto);
    }
    
    public CatGeneralDto obtenerDTO(CatGeneral enty) throws TechnicalException {
        return convierteCatGeneral.obtenerDTO(enty);
    }
    public CatGeneral obtenerEntity(CatGeneralDto dto) throws TechnicalException {
        return convierteCatGeneral.obtenerEntity(dto);
    }
    
    public ClienteTempDto obtenerDTO(ClienteTemp enty) throws TechnicalException {
        return convierteClienteTemp.obtenerDTO(enty);
    }
    public ClienteTemp obtenerEntity(ClienteTempDto dto) throws TechnicalException {
        return convierteClienteTemp.obtenerEntity(dto);
    }
    
    public CotizacionDto obtenerDTO(Cotizacion enty) throws TechnicalException {
        return convierteCotizacion.obtenerDTO(enty);
    }
    public Cotizacion obtenerEntity(CotizacionDto dto) throws TechnicalException {
        return convierteCotizacion.obtenerEntity(dto);
    }

    
}