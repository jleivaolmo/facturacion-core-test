mvn clean package -Dmaven.test.skip=true
copy /Y target\facturacion-core-test*.jar ..\facturacion-recepcion-test\libs\facturacion-core-test.jar
mvn install:install-file -Dfile=..\facturacion-recepcion-test\libs\facturacion-core-test.jar -DgroupId=com.echevarne.sap.cloud.facturacion.core -DartifactId=facturacion-core-test -Dversion=1.0.0-SNAPSHOT -Dpackaging=jar