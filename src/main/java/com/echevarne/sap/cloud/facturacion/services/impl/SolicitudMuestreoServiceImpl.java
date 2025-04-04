package com.echevarne.sap.cloud.facturacion.services.impl;

import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.SUF_MIXTA_MUTUA;
import static com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion.SUF_MIXTA_PRIVADO;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.echevarne.sap.cloud.facturacion.gestionestados.Bloqueada;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.BasicMessagesEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreoItems;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.SolicitudMuestreo;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.Trazabilidad;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadAlbaran;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadClasificacion;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.model.views.ItemsEnProcesoFacturacion;
import com.echevarne.sap.cloud.facturacion.repositories.ItemsEnProcesoFacturacionRep;
import com.echevarne.sap.cloud.facturacion.repositories.PeticionMuestreoRep;
import com.echevarne.sap.cloud.facturacion.repositories.SolicitudMuestreoRepository;
import com.echevarne.sap.cloud.facturacion.services.BloqueoTecnicoService;
import com.echevarne.sap.cloud.facturacion.services.GestionCambioService;
import com.echevarne.sap.cloud.facturacion.services.MasDataEstadoService;
import com.echevarne.sap.cloud.facturacion.services.PeticionMuestreoItemsService;
import com.echevarne.sap.cloud.facturacion.services.ProcesadorEstadosService;
import com.echevarne.sap.cloud.facturacion.services.ProcessControlService;
import com.echevarne.sap.cloud.facturacion.services.SolIndItemsService;
import com.echevarne.sap.cloud.facturacion.services.SolicitudMuestreoService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadClasificacionService;
import com.echevarne.sap.cloud.facturacion.services.TrazabilidadService;
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
	private final ProcesadorEstadosService procesadorEstadosService;
	private final GestionCambioService gestionCambiosSrv;
	private final ProcessControlService processControlSrv;
	private final PeticionMuestreoItemsService peticionMuestreoItemsService;
	private final MasDataEstadoService masDataEstadoService;
	private final TrazabilidadClasificacionService clasificacionSrv;
	private final TrazabilidadService trazabilidadSrv;
    private final SolIndItemsService solIndItemsService;
    private final ItemsEnProcesoFacturacionRep itemsEnProcesoFacturacionRep;
    
    @Autowired
	private BloqueoTecnicoService bloqueoTecnicoSrv;
   
	@Autowired
	public SolicitudMuestreoServiceImpl(SolicitudMuestreoRepository solicitudMuestreoRepository,
			PeticionMuestreoRep peticionMuestreoRep, ProcesadorEstadosService procesadorEstadosService,
			GestionCambioService gestionCambiosSrv, ProcessControlService processControlSrv,
			PeticionMuestreoItemsService peticionMuestreoItemsService, MasDataEstadoService masDataEstadoService,
            TrazabilidadClasificacionService clasificacionSrv, TrazabilidadService trazbilidadService,
            SolIndItemsService solIndItemsService, ItemsEnProcesoFacturacionRep itemsEnProcesoFacturacionRep) {
		super(solicitudMuestreoRepository);
		this.solicitudMuestreoRepository = solicitudMuestreoRepository;
		this.procesadorEstadosService = procesadorEstadosService;
		this.gestionCambiosSrv = gestionCambiosSrv;
		this.processControlSrv = processControlSrv;
		this.peticionMuestreoItemsService = peticionMuestreoItemsService;
		this.peticionMuestreoRep = peticionMuestreoRep;
		this.masDataEstadoService = masDataEstadoService;
        this.clasificacionSrv = clasificacionSrv;
		this.trazabilidadSrv = trazbilidadService;
        this.solIndItemsService = solIndItemsService;
        this.itemsEnProcesoFacturacionRep = itemsEnProcesoFacturacionRep;
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
	 *
	 * Establecemos la trazabilidad inicial para solicitud y pruebas
	 */
	public void setTrazabilidad(SolicitudMuestreo solicitud) {
		if (solicitud.getTrazabilidad() == null) {
			TrazabilidadAlbaran trazaAlb = new TrazabilidadAlbaran();
			trazaAlb.setSolicitudRec(solicitud);
			solicitud.setTrazabilidad(trazaAlb);
		}
		solicitud.getPeticion().forEach(this::setTrazabilidad);
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
			MessagesUtils.addSuccess(messages, "0100", codigoPeticion, null, null, null);
			nuevaMixta = checkPeticionMixta(newSolicitudMuestreo);
			SolicitudMuestreo existingSolicitudMuestreo = findSolicitudByCodigoPeticion(codigoPeticion);
			if (Objects.nonNull(existingSolicitudMuestreo)) {
				revisaBloqueoTecnico(existingSolicitudMuestreo.getPeticion());
				checkMixtasExistentes(newSolicitudMuestreo, existingSolicitudMuestreo);
				nuevaMixta = false;
				newSolicitudMuestreo.setId(existingSolicitudMuestreo.getId());
				checkPerfiles(newSolicitudMuestreo);
				existingSolicitudMuestreo = gestionCambiosSrv.process(newSolicitudMuestreo, existingSolicitudMuestreo);
				setTrazabilidad(existingSolicitudMuestreo);
				removePeticionesSinPruebas(existingSolicitudMuestreo);
				solicitudMuestreoRepository.save(existingSolicitudMuestreo); //AndFlush
				MessagesUtils.addSuccess(messages, "0102", codigoPeticion, null, null, null);
			} else {
				setTrazabilidad(newSolicitudMuestreo);
				checkPerfiles(newSolicitudMuestreo);
				existingSolicitudMuestreo = solicitudMuestreoRepository.saveAndFlush(newSolicitudMuestreo); //AndFlush
				MessagesUtils.addSuccess(messages, "0101", codigoPeticion, null, null, null);
			}
			var peticionEnProceso = peticionEnProcesoFacturacion(existingSolicitudMuestreo);
			if (!peticionEnProceso) {
				setEstados(existingSolicitudMuestreo, messages);
				if (nuevaMixta && checkCondicionTiempo(existingSolicitudMuestreo)) {
					existingSolicitudMuestreo.getPruebasMutua().stream().forEach(x -> procesadorEstadosService.bloquearPrueba(x));
				} else {
					checkSigueSiendoMixta(existingSolicitudMuestreo, messages);
				}
			}
			return existingSolicitudMuestreo;
		} catch (Exception e) {
			throw exception(e);
		}
	}
	
	private void removePeticionesSinPruebas(SolicitudMuestreo existingSolicitudMuestreo) {
		List<PeticionMuestreo> peticionesABorrar = new ArrayList<>();
		existingSolicitudMuestreo.getPeticion().forEach(p -> {
			if (p.getPruebas().size() == 0) {
				peticionesABorrar.add(p);
			}
		});
		existingSolicitudMuestreo.getPeticion().removeAll(peticionesABorrar);
		peticionesABorrar.forEach(p -> {
			this.peticionMuestreoRep.delete(p);
		});
	}

	private void revisaBloqueoTecnico(Set<PeticionMuestreo> setPetMuestreo) {
		setPetMuestreo.stream().filter(p -> p.getTrazabilidad() != null).forEach(pt -> {
			if (bloqueoTecnicoSrv.tieneBloqueoTecnico(pt.getTrazabilidad())) {
				String msg = "No se puede realizar la actualización de la petición " + pt.getCodigoPeticion() + " porque tiene un bloqueoTecnico activo";
				log.error(msg);
				throw new RuntimeException(msg);
			} else {
				log.info("La petición " + pt.getCodigoPeticion() + " no tiene bloqueoTecnico");
			}
		});
	}

	private boolean peticionEnProcesoFacturacion(SolicitudMuestreo existingSolicitudMuestreo) {
		boolean response = false;
		// Si alguna de sus peticiones se encuentra en un proceso de facturación, se interrumpe el set de los estados
		for (var peticion : existingSolicitudMuestreo.getPeticion()) {
			if (!peticion.isEsPrivado()) {
				List<ItemsEnProcesoFacturacion> procesos = itemsEnProcesoFacturacionRep.findByCodigoPeticion(peticion.getCodigoPeticion());
				if (procesos != null && procesos.size() > 0) {
					log.error("La petición " + peticion.getCodigoPeticion() + " se encuentra en un proceso de facturación");
					response = true;
					break;
				}
			}
		}
		return response;
	}

	public void checkPerfiles(SolicitudMuestreo solicitudMuestreo) {
		if (PerfilesUtils.countPerfiles(solicitudMuestreo)>0) {
			PerfilesUtils.getPerfiles(solicitudMuestreo).forEach(PerfilesUtils::setValidacionPerfil);
		}
	}

	private boolean checkCondicionTiempo(SolicitudMuestreo existingSolicitudMuestreo) {
		if (existingSolicitudMuestreo.getPeticionesMutua() == null ||
				existingSolicitudMuestreo.getPeticionesMutua().isEmpty() ||
				existingSolicitudMuestreo.getPeticionesMutua().get(0).getFechas() == null ||
				existingSolicitudMuestreo.getPeticionesMutua().get(0).getFechas().getFechaPeticion() == null ) {
			log.error("No se pudo checkear la condicion de tiempo");
			return false;
		}
		return DateUtils.addDays(existingSolicitudMuestreo.getPeticionesMutua().get(0).getFechas().getFechaPeticion(),20).after(DateUtils.today());
	}

	// Chequeeamos los casos relacionados con solicitudes mixtas
	private void checkMixtasExistentes(SolicitudMuestreo newSolicitudMuestreo,
			SolicitudMuestreo existingSolicitudMuestreo) {

		// Caso solicitud mixta pasa a no mixta
		if (existingSolicitudMuestreo.isEsMixta() && !newSolicitudMuestreo.isEsMixta()) {
			if (newSolicitudMuestreo.getPeticionesMutua().size() > 0) { // Caso en que queden solo pruebas de mutua
				newSolicitudMuestreo.getPeticionesMutua().get(0)
						.setCodigoPeticion(newSolicitudMuestreo.getCodigoPeticion() + SUF_MIXTA_MUTUA);

				// trasferir las pruebas de privados ya que la petición es solo de mutua
				PeticionMuestreo newPeticionMutua = newSolicitudMuestreo.getPeticionesMutua().get(0);
				PeticionMuestreo oldPeticionPrivada = existingSolicitudMuestreo.getPeticionesPrivado().get(0);
				newSolicitudMuestreo.getPeticion().add(oldPeticionPrivada);

				transferItemsIfApply(oldPeticionPrivada, newPeticionMutua);

			} else if (newSolicitudMuestreo.getPeticionesPrivado().size() > 0) { // Caso en que queden solo pruebas de mutua
				newSolicitudMuestreo.getPeticionesPrivado().get(0)
						.setCodigoPeticion(newSolicitudMuestreo.getCodigoPeticion() + SUF_MIXTA_PRIVADO);

				// trasferir las pruebas de mutua ya que la petición es solo privada
				PeticionMuestreo newPeticionPrivada = newSolicitudMuestreo.getPeticionesPrivado().get(0);
				PeticionMuestreo oldPeticionMutua = existingSolicitudMuestreo.getPeticionesMutua().get(0);
				newSolicitudMuestreo.getPeticion().add(oldPeticionMutua);

				transferItemsIfApply(oldPeticionMutua, newPeticionPrivada);
				
			}

		// Caso solicitud no mixta pasa a mixta
		} else if (!existingSolicitudMuestreo.isEsMixta() && newSolicitudMuestreo.isEsMixta()) {
			if (existingSolicitudMuestreo.getPeticionesMutua().size() > 0) { // Caso en el que una solicitud de mutua pasa a mixta
				if (!existingSolicitudMuestreo.getCodigoPeticion().endsWith(SUF_MIXTA_MUTUA)) {
					newSolicitudMuestreo.getPeticionesMutua().get(0)
							.setCodigoPeticion(existingSolicitudMuestreo.getCodigoPeticion() + SUF_MIXTA_MUTUA);
					existingSolicitudMuestreo.getPeticionesMutua().get(0)
							.setCodigoPeticion(existingSolicitudMuestreo.getCodigoPeticion() + SUF_MIXTA_MUTUA);
				}
				PeticionMuestreo newPeticionPrivada = newSolicitudMuestreo.getPeticionesPrivado().get(0);
				PeticionMuestreo oldPeticionMutua = existingSolicitudMuestreo.getPeticionesMutua().get(0);

				transferItemsIfApply(oldPeticionMutua, newPeticionPrivada);

			} else { // Caso en el que una solicitud de privado pasa a mixta
				if (existingSolicitudMuestreo.getPeticionesPrivado().size() > 0) {
					newSolicitudMuestreo.getPeticionesPrivado().get(0)
							.setCodigoPeticion(existingSolicitudMuestreo.getCodigoPeticion()+ SUF_MIXTA_PRIVADO);
					existingSolicitudMuestreo.getPeticionesPrivado().get(0)
							.setCodigoPeticion(existingSolicitudMuestreo.getCodigoPeticion()+ SUF_MIXTA_PRIVADO);
				}
				PeticionMuestreo newPeticionMutua = newSolicitudMuestreo.getPeticionesMutua().get(0);
				PeticionMuestreo oldPeticionPrivada = existingSolicitudMuestreo.getPeticionesPrivado().get(0);

				transferItemsIfApply(oldPeticionPrivada, newPeticionMutua);
			}

		// Caso solicitud sigue siendo mixta, chequeamos si alguna prueba cambió de petición
		} else if (existingSolicitudMuestreo.isEsMixta() && newSolicitudMuestreo.isEsMixta()) {

			PeticionMuestreo newPeticionPrivada = newSolicitudMuestreo.getPeticionesPrivado().get(0);
			PeticionMuestreo oldPeticionMutua = existingSolicitudMuestreo.getPeticionesMutua().get(0);

			transferItemsIfApply(oldPeticionMutua, newPeticionPrivada);

			PeticionMuestreo newPeticionMutua = newSolicitudMuestreo.getPeticionesMutua().get(0);
			PeticionMuestreo oldPeticionPrivada = existingSolicitudMuestreo.getPeticionesPrivado().get(0);

			transferItemsIfApply(oldPeticionPrivada, newPeticionMutua);
		}
	}

	private void transferItemsIfApply(PeticionMuestreo existingPeticion, PeticionMuestreo newPeticion) {

		List<PeticionMuestreoItems> pruebasExistentes = new ArrayList<PeticionMuestreoItems>();
		existingPeticion.getPruebas().stream().forEach(p -> pruebasExistentes.add(p));

		List<PeticionMuestreoItems> pruebasNuevas = new ArrayList<PeticionMuestreoItems>();
		newPeticion.getPruebas().stream().forEach(p -> pruebasNuevas.add(p));

        SolicitudIndividual solicitudExistente = existingPeticion.getTrazabilidad().getSolicitudInd();

		List<Integer> idsNuevos = pruebasNuevas.stream().map(PeticionMuestreoItems::getIdItem).collect(Collectors.toList());
		List<PeticionMuestreoItems> pruebasABorrar = new ArrayList<PeticionMuestreoItems>();
		List<SolIndItems> itemsABorrar = new ArrayList<SolIndItems>();
		pruebasExistentes.stream().forEach(pe -> {
			if (idsNuevos.contains(pe.getIdItem())) {
				Optional<PeticionMuestreoItems> optPruebaNueva = pruebasNuevas.stream().filter(pn -> pn.getIdItem() == pe.getIdItem()).findFirst();
				if (optPruebaNueva.isPresent()) {
					Trazabilidad existingTrazabilidad = pe.getTrazabilidad();
					pe.setInactive(true);
					pruebasABorrar.add(pe);
					newPeticion.getPruebas().add(optPruebaNueva.get());

                    SolIndItems solIndItem = existingTrazabilidad.getItemInd();
                    if (solicitudExistente != null && solIndItem != null) {
                    	solIndItem.setInactive(true);
                        itemsABorrar.add(solIndItem);
                        TrazabilidadClasificacion trazabilidadClasificacion = existingTrazabilidad.getTrzClasificacion();
                        existingTrazabilidad.setTrzClasificacion(null);
                        if (trazabilidadClasificacion != null) {
                            clasificacionSrv.remove(trazabilidadClasificacion);
                        }
                        trazabilidadSrv.update(existingTrazabilidad);    
                    }
				}
			}
		});
        solIndItemsService.removeAll(itemsABorrar);
		peticionMuestreoItemsService.removeAll(pruebasABorrar);
		existingPeticion.getPruebas().removeAll(pruebasABorrar);
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
				reajusteNumItemPruebas(clasificacionPruebas);
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

	private void reajusteNumItemPruebas(Map<Boolean, Set<PeticionMuestreoItems>> clasificacionPruebas) {
		HashMap<Integer,Integer> numItemsCambiados = new HashMap<>();
		var listMutua = new ArrayList<>(clasificacionPruebas.get(false));
		listMutua.sort(Comparator.comparing(PeticionMuestreoItems::getIdItem));
		int idItem = 1;
		for (var i : listMutua) {
			idItem = ajusteItems(numItemsCambiados, idItem, i);
		}
		var listPrivados = new ArrayList<>(clasificacionPruebas.get(true));
		listPrivados.sort(Comparator.comparing(PeticionMuestreoItems::getIdItem));
		for (var i : listPrivados) {
			idItem = ajusteItems(numItemsCambiados, idItem, i);
		}
	}

	private int ajusteItems(HashMap<Integer, Integer> numItemsCambiados, int idItem, PeticionMuestreoItems i) {
		numItemsCambiados.put(i.getIdItem(), idItem);
		i.setIdItem(idItem);
		if (i.getIdParent()>0) {
			i.setIdParent(numItemsCambiados.get(i.getIdParent()));
		}
		idItem++;
		return idItem;
	}

	private void checkSigueSiendoMixta(SolicitudMuestreo solicitud, Set<BasicMessagesEntity> messages) {
		if (solicitud.getPeticion().size() > 1) {
			if (solicitud.getPeticion().stream().filter(x -> !x.getPruebas().isEmpty()).count() <= 1L) {
				solicitud.getPruebasMutua().forEach(x -> procesadorEstadosService.desbloquearPrueba(x, messages));
			}
			solicitud.setEsMixta(true);
		} else {
			if (solicitud.isEsMixta()) {
				log.info("La solicitud " + solicitud.getCodigoPeticionLims() + " deja de ser mixta");
			}
			solicitud.setEsMixta(false);
		}
		solicitudMuestreoRepository.saveAndFlush(solicitud);
	}

	@Override
	@Transactional
	public Set<PeticionMuestreo> desbloqueaPruebasMutuaPeticionesMixtas(int numDaysBlock) {
		Set<PeticionMuestreo> modificadas = new HashSet<>();
		Set<BasicMessagesEntity> messages = new HashSet<>();
		log.info("Ejecutando Cron de PeticionesMixtas ");
		ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.of("UTC"));
		Timestamp numDays = Timestamp.from(zonedDateTime.minus(numDaysBlock, ChronoUnit.DAYS).toInstant());
		MasDataEstado bloqueada = masDataEstadoService.findByCodeEstado(Bloqueada.CODIGO);
		List<PeticionMuestreoItems> pruebasDesbloquear = peticionMuestreoItemsService.findPruebasMixtasPorEstadoYFecha(numDays, bloqueada);
		log.info("Se encontraron {} pruebas de peticiones mixtas anteriores a {} días ", pruebasDesbloquear.size(), numDays);
		for (PeticionMuestreoItems prueba : pruebasDesbloquear) {
			if (procesadorEstadosService.desbloquearPrueba(prueba, messages)) {
				modificadas.add(prueba.getPeticion());
				log.debug("Prueba {} de la peticion {} desbloqueada con éxito", prueba.getId(), prueba.getPeticion().getCodigoPeticion());
			} else {
				log.debug("No se pudo desbloquear la prueba {} de la peticion {}", prueba.getId(), prueba.getPeticion().getCodigoPeticion());
			}
		}
		modificadas.forEach(x -> procesadorEstadosService.desbloquearPeticion(x,messages));
		return modificadas;
	}

	@Transactional
	protected int desbloquearPruebasPeticion(PeticionMuestreo peticionMuestreo, Set<BasicMessagesEntity> messages) {
		if (peticionMuestreo.isEsPrivado()) return 0;
		try {
			Set<PeticionMuestreoItems> bloqueadas = peticionMuestreo.getPruebas().stream().filter(p -> checkIsBloqueada(p)).collect(Collectors.toSet());
			if (bloqueadas.size()>0) {
				log.info(String.format("La peticion %s tiene %d pruebas bloqueadas", peticionMuestreo.getCodigoPeticion(), bloqueadas.size()));
				bloqueadas.stream().filter(x -> x.getTrazabilidad().getEstadoAnterior().isPresent()).forEach(x -> procesadorEstadosService.desbloquearPrueba(x, messages));
			}
			return bloqueadas.size();
		} catch (Exception e) {
			return 0;
		}
	}

	private boolean checkIsBloqueada(PeticionMuestreoItems peticionMuestreoItems) {
		return Bloqueada.CODIGO.equals(
				EstadosUtils.getEstadoActual(peticionMuestreoItems.getTrazabilidad()).getCodeEstado());
	}

	/**
	 *
	 * Establecemos la trazabilidad inicial para peticion y pruebas
	 */
	private void setTrazabilidad(PeticionMuestreo peticion) {
		// Cabecera
		if (peticion.getTrazabilidad() == null) {
			TrazabilidadSolicitud trazaSol = new TrazabilidadSolicitud();
			trazaSol.setPeticionRec(peticion);
			peticion.setTrazabilidad(trazaSol);
		}
		// Posiciones
		peticion.getPruebas().forEach(prueba -> {
			if (prueba.getTrazabilidad() == null) {
				Trazabilidad itemTrazabilidad = new Trazabilidad();
				itemTrazabilidad.setItemRec(prueba);
				prueba.setTrazabilidad(itemTrazabilidad);
			}
		});
	}

	public void setEstados(SolicitudMuestreo sm, Set<BasicMessagesEntity> messages) {
		procesadorEstadosService.procesarEstados(sm, messages);
		/*sm.getPeticion().stream().forEach(peticion -> {
			processControlSrv.saveSolicitudRecepcionada(peticion.getTrazabilidad(), messages);
		});*/

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
	public List<SolicitudMuestreo> findSolicitudesMuestreoFecha(Date from) {
		return solicitudMuestreoRepository.findSolicitudesMuestreoFecha(from);
	}
}
