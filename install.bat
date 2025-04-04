mvn clean package -Dmaven.test.skip=true
copy target\facturacion-core-test.jar ..\facturacion-recepcion-test\libs
pause