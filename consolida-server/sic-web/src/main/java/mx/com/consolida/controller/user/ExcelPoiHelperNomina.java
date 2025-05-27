package mx.com.consolida.controller.user;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping(value = "/archivoNomina")
@SessionAttributes(value = { ReferenciaSeguridad.COTIZADOR, ReferenciaSeguridad.CATS_COTIZADOR })
public class ExcelPoiHelperNomina {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelPoiHelperNomina.class);

	@Value("${urlReportes}")
	private String urlReportes;

	private static List<String> nombreColumnas = Arrays.asList("NOMBRE", "APELLIDO_PATERNO", "APELLIDO_MATERNO", "RFC",
			"MONTO_PPP", "CLABE_INTERBANCARIA_O_TARJETA", "CURP", "NUMERO_SEGURO_SOCIAL", "INSTITUCION_CONTRAPARTE",
			"CORREO_ELECTRONICO","CODIGO_POSTAL","DOMICILIO","PUESTO", "NUMERO_AFILIACION");

	@SuppressWarnings("resource")
	public Map<String, Object> readJExcel(ByteArrayInputStream bytesArray, String FILE_NAME, Long esSindicato) {
		Map<String, Object> datosExcel = new HashMap<String, Object>();
		List<String> headerData = new ArrayList<String>();
		List<EmpleadoDTO> rowsData = new ArrayList<EmpleadoDTO>();
		datosExcel.put("header", headerData);
		Boolean layoutPuesto = Boolean.FALSE;
		Boolean layoutNoAfiliacion = Boolean.FALSE;
		List<String> listRfc = new ArrayList<>();
		List<String> listCurp = new ArrayList<>();
		List<String> listClabe = new ArrayList<>();
		List<String> listNss = new ArrayList<>();
		List<String> listNumeroAfiliacion = new ArrayList<>();
		LOGGER.info(
				"===================================================================================================");
		LOGGER.info(
				"=============================Validando archivo colaboradores=======================================");
		try {
			Workbook workbook = null;
			XSSFWorkbook workbooks = null;
			Sheet datatypeSheet = null;
			List<EmpleadoDTO> listEmpleados = new ArrayList<EmpleadoDTO>();
			if (FILE_NAME != null && (FILE_NAME.contains(".xlsx") || FILE_NAME.contains(".XLSX"))) {
				workbooks = new XSSFWorkbook(bytesArray);
				datatypeSheet = workbooks.getSheetAt(0);
			} else {
				workbook = new HSSFWorkbook(bytesArray);
				datatypeSheet = workbook.getSheetAt(0);
			}
			Iterator<Row> iterator = datatypeSheet.iterator();
			int i = 0;
			Double totalMontoPPP = 0.0;
			while (iterator.hasNext()) {
				Row ro = iterator.next();
				if (0 == i) {
					Iterator<Cell> cellIterator = ro.iterator();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (nombreColumnas.contains(currentCell.toString())) {
							headerData.add(currentCell.getStringCellValue());
							if("PUESTO".contentEquals(currentCell.toString())){
								layoutPuesto= Boolean.TRUE;
							}
							if("NUMERO_AFILIACION".contentEquals(currentCell.toString())){
								layoutNoAfiliacion= Boolean.TRUE;
							}
							
						} else {
							datosExcel.put("errorCargalayout",
									"El contenido del archivo excel no corresponde al layout");
							return datosExcel;
						}
					}
					
					
				} else {
					EmpleadoDTO rowData = new EmpleadoDTO();
					
					for (int j = ro.getFirstCellNum(); j <= ro.getLastCellNum(); j++) {
						Cell ce = ro.getCell(j);
						if (ce != null) {
							if (j == 0) {
								if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo NOMBRE, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo NOMBRE, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setNombre(ce.getStringCellValue().toUpperCase());
							} else if (j == 1) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo APELLIDO_PATERNO, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo APELLIDO_PATERNO, No debe ser NULL o de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setApellidoPaterno(ce.getStringCellValue().toUpperCase());
							} else if (j == 2) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setApellidoMaterno("");
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo APELLIDO_MATERNO, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else {
									rowData.setApellidoMaterno(ce.getStringCellValue().toUpperCase());
								}
							} else if (j == 3) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo RFC, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo RFC, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}

								rowData.setRfc(ce.getStringCellValue().toUpperCase());

								if (rowData.getRfc().length() != 13) {
									rowData.setEstatus(0);
									rowData.setDescError("Error en el formato del RFC");
								} else if (listRfc.contains(rowData.getRfc())) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el campo RFC se encuentra repetido, favor de verificar");
								} else {
									listRfc.add(rowData.getRfc());
								}
							} else if (j == 4) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo MONTO_PPP, debe ser de tipo Moneda y mayor a 0, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.NUMERIC) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo MONTO_PPP, debe ser de tipo Moneda, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setMontoPPP(new BigDecimal(String.valueOf(ce.getNumericCellValue())));
								if (rowData.getMontoPPP().equals(BigDecimal.ZERO)) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo MONTO_PPP, debe ser de tipo Moneda y ser mayor a Cero, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								totalMontoPPP = totalMontoPPP + rowData.getMontoPPP().doubleValue();
							} else if (j == 5) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo MONTO_PPP, debe ser de tipo Moneda y mayor a 0, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CLABE_INTERBANCARIA_O_TARJETA, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setClabe(ce.getStringCellValue());

								if (!(rowData.getClabe().length() == 18 || rowData.getClabe().length()==16)) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error en el formato de la Clabe interbancaria o número de tarjeta");
								} else if (listClabe.contains(rowData.getClabe())) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el campo Clabe ó número de tarjeta se encuentra repetido, favor de verificar");
								} else {
									listClabe.add(rowData.getClabe());
								}
							} else if (j == 6) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CURP, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CURP, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setCurp(ce.getStringCellValue().toUpperCase());

								if (rowData.getCurp().length() != 18) {
									rowData.setEstatus(0);
									rowData.setDescError("Error en el formato del CURP");
								} else if (listCurp.contains(rowData.getCurp())) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el campo Curp se encuentra repetido, favor de verificar");
								} else {
									listCurp.add(rowData.getCurp());
								}
							} else if (j == 7) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo NUMERO_SEGURO_SOCIAL, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo NUMERO_SEGURO_SOCIAL, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setNss(String.valueOf(ce.getStringCellValue()));
								if (rowData.getNss().length() != 11) {
									rowData.setEstatus(0);
									rowData.setDescError("Error en el formato del número de seguro social");
								} else if (listNss.contains(rowData.getNss())) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el campo número de seguro social se encuentra repetido, favor de verificar");
								} else {
									listNss.add(rowData.getNss());
								}

							} else if (j == 8) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo INSTITUCION_CONTRAPARTE, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo INSTITUCION_CONTRAPARTE, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setInstitucionContraparte(ce.getStringCellValue());
								if (rowData.getInstitucionContraparte().length() != 3) {
									rowData.setEstatus(0);
									rowData.setDescError("Error en el formato de la Institución contraparte");
								}

							} else if (j == 9) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CORREO_ELECTRONICO, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CORREO_ELECTRONICO, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setCorreoElectronico(ce.getStringCellValue().trim());
							}else if (j == 10) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CODIGO_POSTAL, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo CODIGO_POSTAL, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setCodigoPostal(ce.getStringCellValue());
							}else if (j == 11) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo DOMICILIO, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo DOMICILIO, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setDomicilio(ce.getStringCellValue());
							}
							/*else if (j == 12) {
								if (ce.getCellTypeEnum() == CellType.BLANK) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo PUESTO, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								} else if (ce.getCellTypeEnum() != CellType.STRING) {
									rowData.setEstatus(0);
									rowData.setDescError(
											"Error, el tipo de celda del campo PUESTO, debe ser de tipo General y/o Texto, favor de verificar");
									rowsData.add(rowData);
									datosExcel.put("contentRows", rowsData);
									return datosExcel;
								}
								rowData.setPuesto(ce.getStringCellValue());
							
						}*/
							
							
						else if (j == 12) {
							if (ce.getCellTypeEnum() == CellType.BLANK) {
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, el tipo de celda del campo NUMERO_AFILIACION, debe ser de tipo General y/o Texto y no venir Nulo, favor de verificar");
								rowsData.add(rowData);
								datosExcel.put("contentRows", rowsData);
								return datosExcel;
							} else if (ce.getCellTypeEnum() != CellType.STRING) {
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, el tipo de celda del campo NUMERO_AFILIACION, debe ser de tipo General y/o Texto, favor de verificar");
								rowsData.add(rowData);
								datosExcel.put("contentRows", rowsData);
								return datosExcel;
							}
							rowData.setNumeroAfiliacion(ce.getStringCellValue().toUpperCase());

							if (rowData.getNumeroAfiliacion().length() != 10) {
								rowData.setEstatus(0);
								rowData.setDescError("Error en el formato del NUMERO_AFILIACION");
							} else if (listNumeroAfiliacion.contains(rowData.getNumeroAfiliacion())) {
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, el campo NUMERO_AFILIACION se encuentra repetido, favor de verificar");
							} else {
								
								listCurp.add(rowData.getCurp());
							}
						} 
						
							
							//fin esSindicato
						
						}
					}

					if(esSindicato==1) {
						if (ro.getLastCellNum()==12) {
							Cell ce = ro.getCell(12);
							if (ce != null) {
							if (ce.getCellTypeEnum() == CellType.BLANK) {
								rowData.setEstatus(0);
								rowData.setDescError(
										"Error, se necesita el campo NUMERO_AFILIACION para este tipo de nomina");
									rowsData.add(rowData);
								datosExcel.put("contentRows", rowsData);
								return datosExcel;
							}
						}else {
							rowData.setEstatus(0);
							rowData.setDescError(
									"Error, se necesita el campo NUMERO_AFILIACION para este tipo de nomina");
							rowsData.add(rowData);
							datosExcel.put("contentRows", rowsData);
							return datosExcel;
						}
					}
					}
					if(esSindicato==0) {
						Cell ce = ro.getCell(12);
						if (ce != null) {
					
							rowData.setEstatus(0);
							rowData.setDescError(
									"Error, Hay campos de más en el archivo");
								rowsData.add(rowData);
							datosExcel.put("contentRows", rowsData);
							return datosExcel;
						
					}
				
						
					}

					if (rowData.getDescError() == null) {
						rowData.setEstatus(1);
						rowData.setDescError("OK");
					}
					rowsData.add(rowData);
					System.out.println(rowsData.size() + ": " + rowData.getRfc());

				}

				i += 1;
			}

			System.out.println(
					"=============================Fin validación archivo colaboradores=======================================");
			System.out.println(
					"===================================================================================================");
			datosExcel.put("layoutPuesto", layoutPuesto);
			datosExcel.put("layoutNoAfiliacion", layoutNoAfiliacion);
			datosExcel.put("totalMonto", totalMontoPPP);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (rowsData.size() > 0) {
			datosExcel.put("contentRows", rowsData);
		} else {
			datosExcel.put("errorCargalayout", "EL layout no contiene colboradores para registrar");
			return datosExcel;
		}

		return datosExcel;
	}

	private String validaCeldaString(Cell cell) {
		if (cell == null) {
			return null;
		}

		if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		}
		return null;

	}

	@RequestMapping(value = "/createFile")
	@ResponseBody
	public Map<String, Object> createFile(@RequestBody DocumentoDTO documento, Model model) throws IOException {
		System.out.print("\nArchivo creado correctamente a las :" + new Date());
		Map<String, Object> datosExcel = new HashMap<String, Object>();
	
		try {
			String archivoString = "";
			byte[] bytes = new byte[documento.getArchivo().getBytes().length];
			if (documento != null && (documento.getNombreArchivo().contains(".xlsx")
					|| documento.getNombreArchivo().contains(".XLSX"))) {
				// archivoString =
				// documento.getArchivo().replace("data:application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;base64,",
				// "");
				archivoString = documento.getArchivo().substring(78, documento.getArchivo().length());
			} else {
				archivoString = documento.getArchivo().replace("data:applicaettion/vnd.ms-excel;base64,", "");
			}

			// bytes = new sun.misc.BASE64Decoder().decodeBuffer(archivoString);

			Base64 base64 = new Base64();
			bytes = base64.decode(archivoString);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			datosExcel = readJExcel(byteArrayInputStream, documento.getNombreArchivo(), documento.getEsSindicato());

//    	}catch(IOException e) {
//        	datosExcel.put("errorCargalayout", "El contenido del archivo excel no corresponde al layout.<br> <br>Favor de verificar el tipo de dato para cada celda");
//        	LOGGER.error("Error en la carga", e);
		} catch (Exception e) {
			datosExcel.put("errorCargalayout",
					"El contenido del archivo excel no corresponde al layout.<br> <br>Favor de verificar el tipo de dato para cada celda");
			LOGGER.error("Error en la carga", e);
		}

		return datosExcel;
	}

	public void borrarDocumento(String destino) {
		File archivo = new File(destino);
		if (archivo.delete()) {
			System.out.print("\nArchivo eliminado correctamente a las :" + new Date());
		} else {
			archivo.deleteOnExit();
		}
	}
}
