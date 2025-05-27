package mx.com.consolida.controller.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.dto.CatatologosCotizadorDto;
import mx.com.consolida.dto.CotizacionDto;
import mx.com.consolida.generico.ReferenciaSeguridad;
import mx.com.consolida.service.interfaz.CotizadorBO;
import org.apache.commons.codec.binary.Base64;


@RestController
@RequestMapping(value = "/archivo")
@SessionAttributes(value={ReferenciaSeguridad.COTIZADOR,ReferenciaSeguridad.CATS_COTIZADOR })
public class ExcelPoiHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelPoiHelper.class);
	
	@Value("${urlReportes}")
	private String urlReportes;
	@Autowired
	private CotizadorBO cotizadorBO;
	
	private static List<String> nombreColumnas = Arrays.asList("SALARIO_DIARIO", "GRAVADOS", "EXENTOS", "FECHA_DE_ANTIGUEDAD","NETO_NOMINA","ASIMILADOS","OTROS","PRIMA_DE_RIESGO","MONTO_PPP");

    @SuppressWarnings("resource")
   	public Map <String ,Object >  readJExcel(ByteArrayInputStream bytesArray,String FILE_NAME, CotizacionDto cot,CatatologosCotizadorDto cat) {
    	System.out.println("\n ingresa a readJExcel");
    	Map <String ,Object > datosExcel = new HashMap<String, Object>();
       	List<String> headerData = new ArrayList<String>();
       	List<EmpleadoDTO> rowsData = new ArrayList<EmpleadoDTO>();
       	datosExcel.put("header", headerData);
       	System.out.println("\n inicia el proceso");
 	 
       	Workbook workbook = null;
       	XSSFWorkbook  workbooks = null;
       	Sheet datatypeSheet =null;
       	
       	
       	
        if(FILE_NAME != null && (FILE_NAME.contains(".xlsx") || FILE_NAME.contains(".XLSX"))) {
          	try {
          		workbooks = new XSSFWorkbook(bytesArray);
          		datatypeSheet= workbooks.getSheetAt(0);
  			} catch (Exception e) {
  				LOGGER.error("Ocurrio un error 2", e);
  			}
          } else  {
          	try {
  				workbook = new HSSFWorkbook(bytesArray);
  				datatypeSheet= workbook.getSheetAt(0);
  			} catch (IOException e) {
  				// TODO Auto-generated catch block
  				LOGGER.error("Ocurrio un error 3", e);
  			}
          }


           try {               
               Iterator<Row> iterator = datatypeSheet.iterator();
               int i = 0;
               while (iterator.hasNext()) {
               	Row ro = iterator.next();
               	if(0 == i) {
                       Iterator<Cell> cellIterator = ro.iterator();
               		 while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            if(nombreColumnas.contains(currentCell.toString())) {
                            	headerData.add(currentCell.getStringCellValue());
                            }else { 
                            	 LOGGER.error("Error controlado, El contenido del archivo excel no corresponde al layout para el servicio readJExcel");
                            	datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout");
                        	    return datosExcel;
                            }
                        }
               	}else {
               		try {
               			EmpleadoDTO rowData = new EmpleadoDTO();
                       for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                            Cell ce = ro.getCell(j);
                            	if(ce!=null) {
  		   	                      if(j == 0){
  		   	                    	  BigDecimal sd = new BigDecimal(String.valueOf(ce.getNumericCellValue()));
  		   	                    	  rowData.setSalarioDiario(sd);
  		   	                      }else if(j == 1){
  		   	                    	  rowData.setGravados(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }else if(j == 2){
  		   	                    	  rowData.setExentos(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }else if(j == 3) {
  		   	                    	  rowData.setFechaAntiguedad(ce.getDateCellValue());
  		   	                      }else if(j == 4) {
  		   	                    	  rowData.setNetoNomina(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }else if(j == 5) {
  		   	                    	  rowData.setAsimilados(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }else if(j == 6) {
  		   	                    	  rowData.setaOtros(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }else if(j == 7) {
  		   	                    	  rowData.setDgPrimaDeRiesgo(ce.getStringCellValue());
  		   	                      }else if (j == 8) {
  		   	                    	rowData.setSoloPpp(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
  		   	                      }
                             }
                     }         
                       if(cot.getIdImss().getIdCatGeneral() != 9L) {
							if ((rowData.getSalarioDiario() != null && rowData.getSalarioDiario().compareTo(new BigDecimal(0)) == 1)
									|| (rowData.getNetoNomina() != null && rowData.getNetoNomina().compareTo(new BigDecimal(0)) == 1)) {
								
								rowData.setSalarioDiario(rowData.getSalarioDiario() != null ? rowData.getSalarioDiario() : new BigDecimal(0));
								if(rowData.getSalarioDiario() != null && rowData.getSalarioDiario().compareTo(new BigDecimal(0)) == 1) {
									rowData.setNetoNomina(new BigDecimal(0));
								} else {
									rowData.setNetoNomina( rowData.getNetoNomina() != null ? rowData.getNetoNomina() : new BigDecimal(0));
								}
								rowData.setGravados( rowData.getGravados() != null ? rowData.getGravados() : new BigDecimal(0));
								rowData.setExentos(rowData.getExentos() != null ? rowData.getExentos() : new BigDecimal(0));
								rowData.setFechaAntiguedad( rowData.getFechaAntiguedad() != null ? rowData.getFechaAntiguedad() : new Date());
								rowData.setAsimilados( rowData.getAsimilados() != null ? rowData.getAsimilados() : new BigDecimal(0));
								rowData.setaOtros(rowData.getaOtros() != null ? rowData.getaOtros() : new BigDecimal(0));
								rowData.setGravados( rowData.getGravados() != null ? rowData.getGravados() : new BigDecimal(0));
								rowData.setDgPrimaDeRiesgo( rowData.getDgPrimaDeRiesgo() != null ? rowData.getDgPrimaDeRiesgo() : "I");
								rowsData.add(rowData);
							}
                       }else if (cot.getIdImss().getIdCatGeneral() == 9L && rowData.getSoloPpp() != null && rowData.getSoloPpp().compareTo(new BigDecimal(0)) == 1) {
                    	   rowData.setSalarioDiario(new BigDecimal(0));
                    	   rowData.setNetoNomina(new BigDecimal(0));
                    	   rowData.setGravados(new BigDecimal(0));
							rowData.setExentos( new BigDecimal(0));
							rowData.setFechaAntiguedad(new Date());
							rowData.setAsimilados(new BigDecimal(0));
							rowData.setaOtros(new BigDecimal(0));
							rowData.setGravados(new BigDecimal(0));
							rowData.setDgPrimaDeRiesgo("I");
							
                    	   rowData.setaOtros(rowData.getSoloPpp());
                    	   rowsData.add(rowData);
                       }
               	 }catch(Exception ex) {
               		LOGGER.error("Ocurrio un error en readJExcel, El archivo contiene registros con datos erroneos", ex);
                 	ex.printStackTrace();
                 	datosExcel.put("errorCargalayout", "El archivo contiene registros con datos erróneos");
                 }
               	}
                   i +=1;
               }
           } catch (Exception e) {
        	   LOGGER.error("Ocurrio un error en readJExcel ", e);
               e.printStackTrace();
           }
           List<EmpleadoDTO> listEmpleados = rowsData.stream().sorted((o1, o2)->{
        	   if(o1.getSalarioDiario().compareTo(o2.getSalarioDiario()) == 0) {
	        	   if(o1.getNetoNomina().compareTo(o2.getNetoNomina()) == 0) {
	        		   if(o1.getGravados().compareTo(o2.getGravados()) == 0) {
	        			   if(o1.getExentos().compareTo(o2.getExentos()) == 0) {
	        				   if(o1.getFechaAntiguedad().compareTo(o2.getFechaAntiguedad()) == 0) {
	        					   if(o1.getAsimilados().compareTo(o2.getAsimilados()) == 0) {
	        						   if(o1.getaOtros().compareTo(o2.getaOtros())==0) {
	        							   return o1.getDgPrimaDeRiesgo().compareTo(o2.getDgPrimaDeRiesgo());
	        						   }else {
	        							   return o1.getaOtros().compareTo(o2.getaOtros());
	        						   }
	        					   }else {
	        						   return o1.getAsimilados().compareTo(o2.getAsimilados());
	        					   }
	        				   } else {
	        					   return o1.getFechaAntiguedad().compareTo(o2.getFechaAntiguedad());
	        				   }
	        			   }else {
	        				   return o1.getExentos().compareTo(o2.getExentos());
	        			   }
	        		   }else {
	        			   return o1.getGravados().compareTo(o2.getGravados());
	        		   }
	        	   }else {
	        		   return o1.getNetoNomina().compareTo(o2.getNetoNomina());  
	        	   }
        	   } else {
        		   return o1.getSalarioDiario().compareTo(o2.getSalarioDiario());
        	   }
        		   
        	   }).collect(Collectors.toList());
           rowsData = listEmpleados;
           
           if(rowsData.size() > 0) {
        	   datosExcel.put("contentRows", rowsData);
           }else {
        	   if(cot.getIdImss().getIdCatGeneral() == 9L) {
        		   datosExcel.put("errorCargalayout", "EL layout no contiene colaboradores para registrar, recuerda que para el producto PPP es obligatorio el campo MONTO_PPP \n");
        	   }else {
        		   datosExcel.put("errorCargalayout", "EL layout no contiene colaboradores para registrar, recuerda que para el producto 'PPP + IMSS' o 'PPP + IMSS Cliente' es obligatorio el campo SALARIO_DIARIO o  NETO_NOMINA \n");
        	   }
        	    
        	    return datosExcel;
           }
           
           return datosExcel;
       }
    

	@SuppressWarnings("restriction")
	@RequestMapping(value="/xlsFile", method=RequestMethod.POST)
	@ResponseBody
    public Map <String ,Object > createFile(@RequestBody DocumentoDTO documento, Model model, HttpServletResponse response) throws IOException {
    	Map <String ,Object > datosExcel = new HashMap<String, Object>();
    	if (model.asMap().containsKey(ReferenciaSeguridad.COTIZADOR) && ((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR))>0) {
			CotizacionDto cot = cotizadorBO.obtenerCotizacionById((Long) model.asMap().get(ReferenciaSeguridad.COTIZADOR));
			CatatologosCotizadorDto cat = (CatatologosCotizadorDto) model.asMap().get(ReferenciaSeguridad.CATS_COTIZADOR);
    	try {
    		String archivoString="";
	    	byte[] bytes = new byte[documento.getArchivo().getBytes().length];
	    	if(documento != null && (documento.getNombreArchivo().contains(".xlsx") || documento.getNombreArchivo().contains(".XLSX"))) {
		    	 archivoString = documento.getArchivo().replace("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,", "");
		    	}else {
		    	 archivoString = documento.getArchivo().replace("data:application/vnd.ms-excel;base64,", "");
		    	}
	      //  bytes = new sun.misc.BASE64Decoder().decodeBuffer(archivoString);
	        Base64 base64 = new Base64();
	    	bytes =   base64.decode(archivoString);
	        
	        
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	        
	        datosExcel = readJExcel(byteArrayInputStream,documento.getNombreArchivo(),cot,cat);
	        System.out.print("\nArchivo creado correctamente a las :" + new Date());
        }	/*catch(IOException e) {
        	 LOGGER.error("Ocurrio un error en createFile ", e);
        		}*/ 
    	catch (Exception e) {
        	 LOGGER.error("Ocurrio un error en createFile ", e);
        	e.printStackTrace();
		}
        return datosExcel;
    	}else {
    		return datosExcel;
    	}
    }

    @RequestMapping (value="/descargarLayoutCot", method=RequestMethod.POST )
	@ResponseBody
    public HashMap<String, Object> descargarLayoutCot() throws IOException {
    	HashMap<String, Object> documento = new HashMap<String, Object>();
		String workingDir = System.getProperty("user.dir");
    	String filePath= workingDir+"/src/main/resources/cotizador/LayoutCotizadorDetallado.xlsx";
    	    File file = new File(filePath);
    	    
    	    byte[] fileArray = new byte[(int) file.length()];
    	    Base64 base64 = new Base64();
    		InputStream inputStream;

    		String encodedFile = "";
    		try {
    			inputStream = new FileInputStream(file);
    			inputStream.read(fileArray);
    			encodedFile = base64.encodeToString(fileArray);
    		} catch (Exception e) {
    			// Manejar Error
    		}
    	    
    	    documento.put("documento", encodedFile);
    		return documento;
    }
    
 
}



