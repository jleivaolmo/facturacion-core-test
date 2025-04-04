package com.echevarne.sap.cloud.facturacion.model.salidas;

public interface IFactura {
    String getCodigoCliente();
    String getOrganizacionVentas();
    String getOficinaVentas();
    String getProvinciaRemitente();
    String getCodigoPeticion();
    String getTipoPeticion();
    String getMuestraRemitida();
    String getTarifa();
    String getPrueba();
    String getGrupoSector();
    String getConceptoFacturacion();
    String getUnidadProductiva();
    String getNoBaremada();
    String getEspecialidadCliente();
    String getCodigoMoneda();
    String getCodigoOperacion();
    String getCodigoReferenciaCliente();
    String getDocumentoUnico();
    String getCodigoPoliza();
}
