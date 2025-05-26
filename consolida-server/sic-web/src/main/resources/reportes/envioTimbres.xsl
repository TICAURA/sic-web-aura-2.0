<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <title>SIC</title>
                <style>
                    body{
                        color: #333333 /*737373*/; 
                        font-family:    Georgia, Times New Roman, Times, serif;
                        font-size: 16px; 
                        text-align: justify;
                    }
                    .contenedor{
                        padding-top: 20px;
                        width:70% !important;
                    }
                    hr {
                        background: black none repeat scroll 0 0;
                        height: 5px;
                    }
                </style>
            </head>
            <body>
                <div align="center">
                    <div class="contenedor" >
                        <p style="font-size: 18px;">SIC</p>
                        <hr/>
                        <span style="font-weight: bold; font-size: 28px;">Notificación</span>
                        <hr/>
                        <div align="left">
						
                            <div>
							<br/>
                                Adjunto encontrará Comprobante Fiscal Digital, por la percepción recibida por la empresa <span style="font-weight: bold;"><xsl:value-of select = "data/fondoEmpresa" /></span>
<br/><br/>
						Saludos cordiales
<br/><br/>
						FAVOR DE NO RESPONDER A ESTE CORREO
                            </div>
                            <div>
                            </div>
                        </div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>