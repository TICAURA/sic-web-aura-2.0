package mx.com.consolida.controller.user;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import mx.com.consolida.EmpleadoDTO;
import mx.com.consolida.catalogos.DocumentoDTO;
import mx.com.consolida.generico.ReferenciaSeguridad;


@RestController
@RequestMapping(value = "/archivoNomina")
@SessionAttributes(value={ReferenciaSeguridad.COTIZADOR,ReferenciaSeguridad.CATS_COTIZADOR })
public class ExcelPoiHelperNomina {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelPoiHelperNomina.class);

	@Value("${urlReportes}")
	private String urlReportes;




	private static List<String> nombreColumnas = Arrays.asList("NOMBRE", "APELLIDO_PATERNO", "APELLIDO_MATERNO", "RFC", "MONTO_PPP", "CLABE_INTERBANCARIA_O_TARJETA", "CURP", "NUMERO_SEGURO_SOCIAL", "INSTITUCION_CONTRAPARTE", "CORREO_ELECTRONICO");

    @SuppressWarnings("resource")
   	public Map <String ,Object >  readJExcel(ByteArrayInputStream bytesArray, String FILE_NAME) {
    	Map <String ,Object > datosExcel = new HashMap<String, Object>();
       	List<String> headerData = new ArrayList<String>();
       	List<EmpleadoDTO> rowsData = new ArrayList<EmpleadoDTO>();
       	datosExcel.put("header", headerData);


           try {
               Workbook workbook = null;
               XSSFWorkbook  workbooks = null;
               Sheet datatypeSheet = null;
               List<EmpleadoDTO> listEmpleados = new ArrayList<EmpleadoDTO>();
               if(FILE_NAME != null && (FILE_NAME.contains(".xlsx") || FILE_NAME.contains(".XLSX"))) {
            	   workbooks = new XSSFWorkbook(bytesArray);
            	   datatypeSheet = workbooks.getSheetAt(0);
               }else  {
               	workbook = new HSSFWorkbook(bytesArray);
               	 datatypeSheet = workbook.getSheetAt(0);
               }
               Iterator<Row> iterator = datatypeSheet.iterator();
               int i = 0;
               Double totalMontoPPP = 0.0;
               while (iterator.hasNext()) {
               	Row ro = iterator.next();
               	if(0 == i) {
                       Iterator<Cell> cellIterator = ro.iterator();
               		 while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            if(nombreColumnas.contains(currentCell.toString())) {
                            	headerData.add(currentCell.getStringCellValue());
                            }else {
                            	datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout");
                        	    return datosExcel;
                            }
                        }
               	}else {
               		EmpleadoDTO rowData = new EmpleadoDTO();
               		int contador = 1;
                       for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                            Cell ce = ro.getCell(j);
                           if(ce!=null) {
		   	                      if(j == 0){
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo NOMBRE, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setNombre(ce.getStringCellValue());
		   	                      }else if(j == 1){
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo APELLIDO_PATERNO, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setApellidoPaterno(ce.getStringCellValue());
		   	                      }else if(j == 2){
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo APELLIDO_MATERNO, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setApellidoMaterno(ce.getStringCellValue());
		   	                      }else if(j == 3){
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo RFC, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setRfc(ce.getStringCellValue());
		   	                      }else if(j == 4){
		   	                    	if (ce.getCellTypeEnum() != CellType.NUMERIC) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo MONTO_PPP, debe ser de tipo Moneda, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setMontoPPP(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
		   	                    	 if(rowData.getMontoPPP() != null) {
		  		   	                   totalMontoPPP = totalMontoPPP +rowData.getMontoPPP().doubleValue();
		  		   	                      }
		   	                      }else if(j == 5) {
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo CLABE_INTERBANCARIA_O_TARJETA, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setClabe(ce.getStringCellValue());
		   	                      }else if(j == 6) {
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo CURP, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setCurp(ce.getStringCellValue());
		   	                      }else if(j == 7) {
		   	                    	 if (ce.getCellTypeEnum() != CellType.STRING) {
		   	                       rowData.setEstatus(0);
                           		   rowData.setDescError("Error, el tipo de celda del campo NUMERO_SEGURO_SOCIAL, debe ser de tipo General y/o Texto, favor de verificar");
                           		   rowsData.add(rowData);
                           		   datosExcel.put("contentRows", rowsData);
                           		   	return datosExcel;
		   	                      }
		   	                    	  rowData.setNss(String.valueOf(ce.getStringCellValue()));
		   	                      }else if(j == 8) {
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo INSTITUCION_CONTRAPARTE, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setInstitucionContraparte(ce.getStringCellValue());
		   	                      }else if(j == 9) {
		   	                    	if (ce.getCellTypeEnum() != CellType.STRING) {
				   	                       rowData.setEstatus(0);
		                           		   rowData.setDescError("Error, el tipo de celda del campo CORREO_ELECTRONICO, debe ser de tipo General y/o Texto, favor de verificar");
		                           		   rowsData.add(rowData);
		                           		   datosExcel.put("contentRows", rowsData);
		                           		   	return datosExcel;
				   	                      }
		   	                    	  rowData.setCorreoElectronico(ce.getStringCellValue());
		   	                      }
                           }
                     }
                       if(rowData.getNombre() != null || rowData.getApellidoPaterno() != null || rowData.getRfc() != null || rowData.getClabe() != null
                    		   || rowData.getMontoPPP().intValue() >=1 || rowData.getCurp() != null || rowData.getNss() != null || rowData.getInstitucionContraparte() != null) {

                    	   Iterator<Row> iterator2 = datatypeSheet.iterator();
                		   while (iterator2.hasNext()) {
                              	Row ro2 = iterator2.next();
                              	EmpleadoDTO rowData2 = new EmpleadoDTO();
                              	for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                                    Cell ce2 = ro2.getCell(j);
                                   if(j == 0){
                                	   if(ce2 != null) {
                                    rowData2.setNombre(ce2.getStringCellValue());
                                	   }
		   	                      }else if(j == 1){
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setApellidoPaterno(ce2.getStringCellValue());
		   	                    	}
		   	                      }else if(j == 2){
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setApellidoMaterno(ce2.getStringCellValue());
		   	                    	}
		   	                      }else if(j == 3){
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setRfc(ce2.getStringCellValue());
		   	                    	}
		   	                      }else if(j == 4){
//		   	                    	  rowData2.setMontoPPP(new BigDecimal(String.valueOf(ce2.getNumericCellValue())));
		   	                      }else if(j == 5) {
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setClabe(ce2.getStringCellValue());
		   	                    	}
		   	                      }else if(j == 6) {
		   	                    	  if(ce2 != null) {
		   	                    	  rowData2.setCurp(ce2.getStringCellValue());
		   	                    	  }
		   	                      }else if(j == 7) {
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setNss(String.valueOf(ce2.getStringCellValue()));
		   	                    	}
		   	                      }else if(j == 8) {
		   	                    	if(ce2 != null) {
		   	                    	  rowData2.setInstitucionContraparte(ce2.getStringCellValue());
		   	                    	}
		   	                      }else if(j == 9) {
		   	                    	  if(ce2 != null) {
		   	                    	  rowData2.setCorreoElectronico(ce2.getStringCellValue());
		   	                    	  }
                              	}

                		   }
                              	listEmpleados.add(rowData2);
                		   }

                    	   for(int x=0; x < listEmpleados.size() ; x++) {
                    		   if(listEmpleados.get(x).getNombre() == rowData.getNombre() && listEmpleados.get(x).getApellidoPaterno() == rowData.getApellidoPaterno()
                    				   && listEmpleados.get(x).getRfc() == rowData.getRfc() && listEmpleados.get(x).getCurp() == rowData.getCurp()) {
                    			   listEmpleados.remove(listEmpleados.get(x));
                    		   }
                    	   }

                    	   if(rowData.getRfc() == null || rowData.getRfc().length() != 13) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el formato del RFC");
                    	   }else {
                    		  for(EmpleadoDTO empleado : listEmpleados) {
                    			  if(empleado.getRfc() == rowData.getRfc()) {
                    			   rowData.setEstatus(0);
                           		   rowData.setDescError("Error, el campo RFC se encuentra repetido, favor de verificar");
                    			  }
                    		  }
                    	   }

                    	   if(rowData.getNombre() == null) {
                    		   rowData.setEstatus(0);
                    		   if(rowData.getDescError() != null) {
                    			   rowData.setDescError(rowData.getDescError() + " , Nombre ");
                    		   }else {
                    			   rowData.setDescError("Error en el Nombre ");
                    		   }
                    	   }
                    	   if(rowData.getApellidoPaterno() == null) {
                    		   rowData.setEstatus(0);
                    		   if(rowData.getDescError() != null) {
                    			   rowData.setDescError(rowData.getDescError() + " , Apellido Paterno ");
                    		   }else {
                    			   rowData.setDescError("Error en el  Apellido Paterno ");
                    		   }
                    	   }


                    	   if(rowData.getMontoPPP() != null && rowData.getMontoPPP().intValue() < 1) {
                    		   rowData.setEstatus(0);
                    		   if(rowData.getDescError() != null) {
                    			   rowData.setDescError(rowData.getDescError() + " , Monto ");
                    		   }else {
                    			   rowData.setDescError("Error en el Monto debe ser mayor a 1  peso");
                    		   }

                    	   }else if(rowData.getMontoPPP() == null) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el campo Monto_PPP, el valor no puede ser vacío ");
                    	   }

                    	   if(rowData.getClabe() == null || rowData.getClabe().length() < 16 || rowData.getClabe().length() > 18 ) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el formato de la Clabe interbancaria o número de tarjeta");
                    	   }else {
                    		   for(EmpleadoDTO empleado : listEmpleados) {
                     			  if(empleado.getClabe() == rowData.getClabe()) {
                     			   rowData.setEstatus(0);
                            		   rowData.setDescError("Error, el campo Clabe ó número de tarjeta se encuentra repetido, favor de verificar");
                     			  }
                     		  }
                    	   }

                    	   if(rowData.getCurp() == null || rowData.getCurp().length() != 18) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el formato del CURP");
                    	   }else {
                    		   for(EmpleadoDTO empleado : listEmpleados) {
                     			  if(empleado.getCurp() == rowData.getCurp()) {
                     			   rowData.setEstatus(0);
                            		   rowData.setDescError("Error, el campo Curp se encuentra repetido, favor de verificar");
                     			  }
                     		  }
                    	   }

                    	   if(rowData.getNss() == null || rowData.getNss().length() != 11) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el formato del número de seguro social");
                    	   }else {
                    		   for(EmpleadoDTO empleado : listEmpleados) {
                     			  if(empleado.getNss() == rowData.getNss()) {
                     			   rowData.setEstatus(0);
                            		   rowData.setDescError("Error, el campo número de seguro social se encuentra repetido, favor de verificar");
                     			  }
                     		  }
                    	   }

                    	   if(rowData.getInstitucionContraparte() == null || rowData.getInstitucionContraparte().length() != 3) {
                    		   rowData.setEstatus(0);
                    		   rowData.setDescError("Error en el formato de la Institución contraparte");
                    	   }


                    	   if(rowData.getDescError()== null) {
                    		   rowData.setEstatus(1);
                        	   rowData.setDescError("OK");
                    	   }

                    	   rowData.setDescEstatus("Cargado layout");

                    	   rowData.setTotalMontoPPP(totalMontoPPP);
                       	rowsData.add(rowData);
                       }
               	}

                   i +=1;
               }

           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
           if(rowsData.size() > 0) {
        	   datosExcel.put("contentRows", rowsData);
           }else {
        	    datosExcel.put("errorCargalayout", "EL layout no contiene colboradores para registrar");
        	    return datosExcel;
           }

           return datosExcel;
       }


    @RequestMapping(value="/createFile")
	@ResponseBody
    public Map <String ,Object > createFile(@RequestBody DocumentoDTO documento, Model model) throws IOException {
    	System.out.print("\nArchivo creado correctamente a las :" + new Date());
    	Map <String ,Object > datosExcel = new HashMap<String, Object>();
    	try {
    		String archivoString="";
	    	byte[] bytes = new byte[documento.getArchivo().getBytes().length];
	    	if(documento != null && (documento.getNombreArchivo().contains(".xlsx") || documento.getNombreArchivo().contains(".XLSX"))) {
		    	 archivoString = documento.getArchivo().replace("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,", "");
		    	}else {
		    	 archivoString = documento.getArchivo().replace("data:application/vnd.ms-excel;base64,", "");
		    	}
	        bytes = Base64.getDecoder().decode(archivoString);
	        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
	        datosExcel = readJExcel(byteArrayInputStream,documento.getNombreArchivo());


    	} catch (Exception e) {
        	datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout.<br> <br>Favor de verificar el tipo de dato para cada celda");
        	LOGGER.error("Error en la carga", e);
		}

        return datosExcel;
    }
    public void borrarDocumento(String destino) {
    	File archivo = new File(destino);
    	if(archivo.delete()) {
    		System.out.print("\nArchivo eliminado correctamente a las :" + new Date());
    	}else {
    		archivo.deleteOnExit();
    	}
    }
}
