package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.SUF_MIXTA_MUTUA;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.SUF_MIXTA_PRIVADO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;

import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;

import com.echevarne.sap.cloud.facturacion.repositories.PeticionMuestreoRep;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudMuestreoRepository;

import com.echevarne.sap.cloud.facturacion.services.PeticionMuestreoItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolicitudMuestreoService;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import com.echevarne.sap.cloud.facturacion.util.MessagesUtils;
import com.echevarne.sap.cloud.facturacion.util.PerfilesUtils;

import lombok.var;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for the service{@link SolicitudMuestreoServiceImpl}.
 */
@Slf4j
@Service("solicitudMuestreoSrv")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SolicitudMuestreoServiceImpl extends CrudServiceImpl<SolicitudMuestreo, Long>
		implements SolicitudMuestreoService {

	private final SolicitudMuestreoRepository solicitudMuestreoRepository;
	private final PeticionMuestreoRep peticionMuestreoRep;
	private final PeticionMuestreoItemsService peticionMuestreoItemsService;
	
	@Autowired
	public SolicitudMuestreoServiceImpl(SolicitudMuestreoRepository solicitudMuestreoRepository,
			PeticionMuestreoRep peticionMuestreoRep, 
			PeticionMuestreoItemsService peticionMuestreoItemsService) {
		super(solicitudMuestreoRepository);
		this.solicitudMuestreoRepository = solicitudMuestreoRepository;
		this.peticionMuestreoItemsService = peticionMuestreoItemsService;
		this.peticionMuestreoRep = peticionMuestreoRep;
	}

	/**
	 * Main process of {@link SolicitudMuestreo} for mapping, controlling, creating
	 * and transform into {@link SolicitudIndividual}
	 */
	@Override
	@Transactional
	public SolicitudMuestreo create(SolicitudMuestreo newSolicitudMuestreo) {
		return createSolicitudMuestreo(newSolicitudMuestreo);
	}

	

	/**
	 * Creamos la solicitud con todas sus peticiones
	 */
	private SolicitudMuestreo createSolicitudMuestreo(SolicitudMuestreo newSolicitudMuestreo) {
		Boolean nuevaMixta;
		String codigoPeticion = newSolicitudMuestreo.getCodigoPeticion();
		Set<BasicMessagesEntity> messages = new HashSet<>();
		MessagesUtils.addMessagesCache(newSolicitudMuestreo.getCodigoPeticion(),messages);
		try {
			
			nuevaMixta = checkPeticionMixta(newSolicitudMuestreo);
			SolicitudMuestreo existingSolicitudMuestreo = findSolicitudByCodigoPeticion(codigoPeticion);
			if (Objects.nonNull(existingSolicitudMuestreo)) {
				nuevaMixta = false;
				newSolicitudMuestreo.setId(existingSolicitudMuestreo.getId());
				solicitudMuestreoRepository.save(newSolicitudMuestreo); //AndFlush
				
			} else {
				
				existingSolicitudMuestreo = solicitudMuestreoRepository.saveAndFlush(newSolicitudMuestreo); //AndFlush
				
			}
			
			return existingSolicitudMuestreo;
		} catch (Exception e) {
			throw exception(e);
		}
	}
	
	

	private boolean peticionEnProcesoFacturacion(SolicitudMuestreo existingSolicitudMuestreo) {
		boolean response = false;
		
		return response;
	}

	

	// Chequeamos las pruebas incluidas en las peticiones de la solicitud
	private Boolean checkPeticionMixta(SolicitudMuestreo solicitud) {
		for (PeticionMuestreo peticion : solicitud.getPeticion()) {
			Map<Boolean, Set<PeticionMuestreoItems>> clasificacionPruebas = peticion.getPruebas().stream()
					.collect(Collectors.groupingBy(PeticionMuestreoItems::getEsPrivado, Collectors.toSet()));
			if (clasificacionPruebas.size() > 1) {
				PeticionMuestreo nueva = (PeticionMuestreo) peticion.copyWithoutId();
				log.debug("hay {} pruebas normales y {} pruebas de privados", clasificacionPruebas.get(false).size(),
						clasificacionPruebas.get(true).size());
				for (PeticionMuestreoItems prueba : clasificacionPruebas.get(true)) {
					peticion.getPruebas().remove(prueba);
					prueba.setPeticion(nueva);
				}
				nueva.setEsPrivado(true);
				nueva.setCodigoPeticion(nueva.getCodigoPeticion() + SUF_MIXTA_PRIVADO);
				nueva.setPruebas(clasificacionPruebas.get(true));
				solicitud.getPeticion().add(nueva);
				peticion.setCodigoPeticion(peticion.getCodigoPeticion() + SUF_MIXTA_MUTUA);
				solicitud.setEsMixta(true);
				return true;
			}
		}
		return false;
	}

	

	

	

	

	

	

	/**
	 *
	 * Obtenemos la petición si existe en el sistema
	 */
	public SolicitudMuestreo findSolicitudByCodigoPeticion(String codigoPeticion) {
		return solicitudMuestreoRepository.findByCodigoPeticion(codigoPeticion);
	}

	/**
	 *
	 * Obtenemos la petición si existe en el sistema
	 */
	public PeticionMuestreo findByCodigoPeticion(String codigoPeticion) {
		return peticionMuestreoRep.findByCodigoPeticion(codigoPeticion);
	}

	public List<String> findCodigosPeticionesInSolicitud(String codigoPeticion) {
		return solicitudMuestreoRepository.findCodigosPeticionesInSolicitud(codigoPeticion);
	}

	public List<SolicitudMuestreo> findSolicitudesMixtasFromDate(Timestamp from) {
		return solicitudMuestreoRepository.findSolicitudesMixtasFromDate(from);
	}

	@Override
	public List<PeticionMuestreo> findPeticionesInSolicitud(String codigoPeticion) {
		return solicitudMuestreoRepository.findPeticionesInSolicitud(codigoPeticion);
	}
	
	

	@Override
	public SolicitudMuestreo createAndFlush(SolicitudMuestreo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SolicitudMuestreo entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateAll(Collection<SolicitudMuestreo> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SolicitudMuestreo updateAndFlush(SolicitudMuestreo entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(SolicitudMuestreo entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAll(Collection<SolicitudMuestreo> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAndFlush(SolicitudMuestreo entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<SolicitudMuestreo> findById(Long entityId) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<SolicitudMuestreo> findAllById(List<Long> entityIds) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
