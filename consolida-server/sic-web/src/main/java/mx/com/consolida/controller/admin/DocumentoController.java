package mx.com.consolida.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.com.consolida.crm.service.impl.DocumentoServiceBO;
import mx.com.documento.DocumentoMSDto;

@RestController
@RequestMapping(value="/documento")	
public class DocumentoController {
	
	@Autowired
	private DocumentoServiceBO documentoServiceBO;
	
	@RequestMapping(value = "/documentoByIdCMS/{idCMS}")
	@ResponseBody
	public DocumentoMSDto documentoMSDto(@PathVariable Long idCMS) {
		return documentoServiceBO.obtenerDocumentoMSDto(idCMS);
	}
}
