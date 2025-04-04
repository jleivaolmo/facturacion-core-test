package com.echevarne.sap.cloud.facturacion.services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

/**
 * Interface for the service{@link SolicitudMuestreoService}.
 * 
 * <p>This is a interface for Services. . .</p>
 *
 * @author Steven Ricardo Mendez Brenes
 * @version 1.0
 * @since 06/24/2019
 * 
 */
public interface SolicitudMuestreoService extends CrudService<SolicitudMuestreo, Long> {

	SolicitudMuestreo create(SolicitudMuestreo newSolicitudMuestreo);
	
	SolicitudMuestreo findSolicitudByCodigoPeticion(String codigoPeticion);

	List<String> findCodigosPeticionesInSolicitud(String codigoPeticion);

	List<PeticionMuestreo> findPeticionesInSolicitud(String codigoPeticion);

	List<SolicitudMuestreo> findSolicitudesMixtasFromDate(Timestamp from);

	Set<PeticionMuestreo> desbloqueaPruebasMutuaPeticionesMixtas(int numDaysBlock);
	
	List<SolicitudMuestreo> findSolicitudesMuestreoFecha(Date from);
	
}
