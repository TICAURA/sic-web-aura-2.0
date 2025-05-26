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
                        width:50% !important;
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
                        <p style="font-size: 18px;">SIC Prueba 1</p>
                        <hr/>
                        <span style="font-weight: bold; font-size: 28px;">Notificación de recuperación de contraseña </span>
                        <hr/>
                        <div align="left">
						aviso de rivacidad
						aviso de rivacidad
						aviso de rivacidad
						aviso de rivacidad
						aviso de rivacidad
						aviso de rivacidad
						aviso de rivacidadaviso de rivacidad
						aviso de rivacidad
						aviso de rivacidad
                            <div>
                                <span style="font-weight: bold;">Usuario:</span>
                                <br/>
                                <xsl:value-of select = "data/usuario" /><br/>
                            </div>
                            <div>
                                <span style="font-weight: bold;">Contraseña:</span>
                                <br/>
                                <xsl:value-of select = "data/contrasena" /><br/>
								<xsl:value-of select = "data/pruebaMike"/>
                            </div>
                        </div>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
