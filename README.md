# sic-web-aura-2.0


NOE

Requisitos previos
Git
MySQL 8.0.23
Dump de la base de datos
Jetty 9.4.36.v20210114 o superior (https://www.eclipse.org/jetty/download.php)
Java 11 (Recomiendo usar Sdkman https://sdkman.io/install)
Preparacion del ambiente 🔧
Tener descargado el proyecto
Estar en la rama correspondiente que estará trabajando
Tener la base de datos desplegada y corriendo
Tener el plugin de Jetty Eclipse 5.0.0 y descargar Jetty de la pagina oficial (https://www.eclipse.org/jetty/download.php)
Una vez que se tenga el plugin de Jetty instalado lo configuramos
Nos dirigimos a la parte de RUN-CONFIGURATIONS
Elegimos el plugin de Jetty Web-app damos doble click
En la pestaña de WEB APP checamos que el puerto sea 8080 y que el contextpath sea /sic-web si no cambiarlos
Nos movemos a la pestaña de ARGUMENTS y ponemos tanto en Program como VM este perfil: -Dspring.profiles.active=local
Pasamos a la pestaña de OPTIONS en la parte de Jetty: damos en la parte USE JETTY AT PATH y damos en el boton de EXTERNAL y seleccionamos la ruta donde descargamos la ultima version de jetty quedando asi el path: "C:\Users\DEV-PC\Downloads\jetty-distribution-9.4.36.v20210114". en la parte inferior de esta opcion aparece la version del jetty que se descargo.
En la misma pestaña de OPTIONS nos vamos a la parte de JETTY FEATURES y solo deben de estar seleccionadas ENABLE JMX SUPPORT y ENABLE JETTY LAUNCH INFO
Damos APPLY y con estos queda configurado el plugin de Jetty
Despliegue 📦
Antes que cualquier cosa correr en terminal o cmd el comando en la ruta sic-web-aura\consolida-server
mvn clean install
Para que se descarguen las todas las librerías del proyecto y no se tenga algún problema al desplegarlo debe aparecer lo siguiente al correrlo:

[INFO] sic-server ......................................... SUCCESS [  0.291 s]
[INFO] sic-comun .......................................... SUCCESS [  4.028 s]
[INFO] sic-persistencia ................................... SUCCESS [  4.943 s]
[INFO] sic-bo ............................................. SUCCESS [  1.788 s]
[INFO] sic-web Maven Webapp ............................... SUCCESS [ 19.395 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
Indicando que se descargo todo correctamente en caso contrario correr nuevamente el comando

Abrir el archivo bo-cfg-jetty.xml que se encuentra en la ruta sic-web-aura\consolida-server\sic-web\src\main\resources Cambiar lo siguiente:
<prop key="url.ms.base">http://traefik</prop>
<prop key="url.factura.service">/api/factura</prop>
<prop key="url.nomina.service">/api/facturanomina</prop>
<prop key="url.documento.service">/api/documento</prop>
Por:

<prop key="url.ms.base">http://host.docker.internal</prop>
<prop key="url.factura.service">:9080</prop>
<prop key="url.nomina.service">:8096</prop>
<prop key="url.documento.service">:8086</prop>
Abrir el archivo datos-spring-cfg-jetty.xml que se encuentra en la ruta sic-web-aura\consolida-server\sic-web\src\main\resources Cambiar el usuario/contraseña para acceder a la base de datos
<property name="username" value="root" />
<property name="password" value="root" />
Con estos cambios se puede desplegar el proyecto sin olvidar que el perfil este local:
-Dspring.profiles.active=local 
Para comprobar que se levanto correctamente el ambiente entrar al siguiente link: http://localhost:8080/sic-web/

NOTA:
Para que el proyecto funcione de manera correcta se deben tener levantado el proyecto de Microservicios
