copy /Y target\facturacion-core-test-1.0.0-SNAPSHOT.jar ..\facturacion-recepcion-test\libs\facturacion-core-test.jar
pause
mvn install:install-file -Dfile=..\facturacion-recepcion-test\libs\facturacion-core-test.jar -DgroupId=com.echevarne.sap.cloud.facturacion.core -DartifactId=facturacion-core-test -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar
pause