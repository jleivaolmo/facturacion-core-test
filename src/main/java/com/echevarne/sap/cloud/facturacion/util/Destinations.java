package com.echevarne.sap.cloud.facturacion.util;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.echevarne.sap.cloud.facturacion.config.ContextProvider;
import com.echevarne.sap.cloud.facturacion.exception.DestinationPropertyNotFoundException;
import com.echevarne.sap.cloud.facturacion.soap.DestinationMessageSender;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpClientAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Component("destinations")
public class Destinations {
	public static String INSTANCE_ROUTING_HEADER_NAME="X-Cf-App-Instance";
	public static int NORMAL_SERVICE_TIMEOUT_MILLISECONDS = 60 *60*1000;
	public static int QUICK_SERVICE_TIMEOUT_MILLISECONDS = 500;
	public static int MAX_INSTANCES = 8;

    public enum Enum {
        S4DESTINATION("S4HC"),
        TRANSFORMACION("MsTransformacion"),
        MODELADO("MsModelado"),
        RECEPCION("MsRecepcion"),
        INTERLOCUTORES("MsInterlocutores"),
        AGRUPACION("MsAgrupacion"),
        SALIDA("MsSalidas"),
        TRAK("TRAK"),
        COBROS("MsCobros"),
        LIQUIDACION("MsLiquidacion"),
        RESPUESTAS_S4("MsRespuestasS4"),
        CPI_SENDDOCUMENTS("CPI_SENDDOCUMENTS"),
        BATCH("MsBatch");

        String value;
        RestTemplate normalTemplate;
        RestTemplate wStringConverterTemplate;        
        RestTemplate quickTemplate;
        Map<Integer, Long> routingHistory = new HashMap<>();
        String app_guid=null;

        public String getValue() {
            return this.value;
        }

        Enum(String destinationName) {
            this.value = destinationName;
            this.normalTemplate = null;
            this.wStringConverterTemplate = null;
        }
    }

    @Autowired
    @Qualifier("restTemplateFactory")
    private ObjectProvider<RestTemplate> restTemplateFactory;


    @SuppressWarnings("unchecked")
    public <T> T getS4Destination() {
        Object d = ContextProvider.getBean("S4Destination");
        return (T) d;
    }

    public RestTemplate createRestTemplate(Destinations.Enum destination) {
        if (destination.normalTemplate != null) return destination.normalTemplate;
        final RestTemplate restTemplate = restTemplateFactory.getObject(destination, NORMAL_SERVICE_TIMEOUT_MILLISECONDS);
        destination.normalTemplate = restTemplate;
        return restTemplate;
    }

    public RestTemplate createQuickRestTemplate(Destinations.Enum destination) {
        if (destination.quickTemplate != null) return destination.quickTemplate;
        final RestTemplate restTemplate = restTemplateFactory.getObject(destination, QUICK_SERVICE_TIMEOUT_MILLISECONDS);
        destination.quickTemplate = restTemplate;
        if (destination.value.startsWith("Ms")) { //para microservicios, almacenamos su guid
        	destination.app_guid = getAppGuid(restTemplate); 
        }
        return restTemplate;
    }
    
    public RestTemplate createRestTemplateWithStringConverter(Destinations.Enum destination) {
        if (destination.wStringConverterTemplate != null) return destination.wStringConverterTemplate;
        final RestTemplate restTemplate = restTemplateFactory.getObject(destination, NORMAL_SERVICE_TIMEOUT_MILLISECONDS);
        //restTemplate.setInterceptors(Collections.singletonList(new TestInterceptor()));
        restTemplate.setMessageConverters(Collections.singletonList(new StringHttpMessageConverter()));
        destination.wStringConverterTemplate = restTemplate;
        return restTemplate;
    }

    public HttpClient getHttpClient(String destinationName) {
        final HttpDestination httpDestination = DestinationAccessor.getDestination(destinationName).asHttp();
        return HttpClientAccessor.getHttpClient(httpDestination);
    }

    public DestinationMessageSender getDestinationMessageSender(String destinationName) {
        Destination destination = DestinationAccessor.getDestination(destinationName);

        String url = destination.get("URL", String.class)
                .getOrElseThrow(() -> new DestinationPropertyNotFoundException(destinationName + " Destination URL not found"));
        String user = destination.get("User", String.class)
                .getOrElseThrow(() -> new DestinationPropertyNotFoundException(destinationName + " Destination User not found"));
        String password = destination.get("Password", String.class)
                .getOrElseThrow(() -> new DestinationPropertyNotFoundException(destinationName + " Destination Password not found"));

        DestinationMessageSender messageSender = new DestinationMessageSender();
        messageSender.setDestinationName(destinationName);
        messageSender.setDestinationURL(url);
        messageSender.setCredentials(new UsernamePasswordCredentials(user, password));
        return messageSender;
    }

    public Map<Integer, String> getInstanceGuidForEachReplicaParallel(Destinations.Enum destination) {
        RestTemplate rt = createQuickRestTemplate(destination);
        if (destination.app_guid == null) {
            log.error("Error retrieving app-guid for " + destination.value);
            return null;
        }

        Flux<Integer> indicesFlux = Flux.range(0, MAX_INSTANCES);

        Flux<AbstractMap.SimpleEntry<Integer, String>> resultFlux = indicesFlux.parallel()
                .runOn(Schedulers.boundedElastic()) // Use an appropriate scheduler
                .flatMap(replicaIndex -> Mono.fromCallable(() -> {
                    try {
                        HttpHeaders headers = new HttpHeaders();
                        headers.set(INSTANCE_ROUTING_HEADER_NAME, destination.app_guid + ":" + replicaIndex);
                        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
                        ResponseEntity<String> response = rt.exchange(
                                "/app/instance-guid",
                                HttpMethod.GET,
                                httpEntity,
                                String.class
                        );
                        // Extract instance GUID from the response
                        String instanceGuid = response.getBody();
                        log.debug("index=" + replicaIndex + " instance-guid=" + instanceGuid);
                        return new AbstractMap.SimpleEntry<>(replicaIndex, instanceGuid);
                    } catch (RestClientResponseException ex) {
                        log.error("Error retrieving instance GUID for replica index " + replicaIndex + ": " + ex.getMessage());
                        return null;
                    } catch (ResourceAccessException ex) {
                        log.warn("Could not get a response: " + ex.getCause().getMessage());
                        return null;
                    } catch (Exception ex) {
                        log.error("Error retrieving instance GUID for replica index " + replicaIndex + ": " + ex.getMessage());
                        return null;
                    }
                }))
                .sequential(); // Convert back to sequential Flux

        List<AbstractMap.SimpleEntry<Integer, String>> results = resultFlux.collectList().block();
        // Convert the list of results to a map
        Map<Integer, String> instanceGuidMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : results) {
            if (entry != null) {
                instanceGuidMap.put(entry.getKey(), entry.getValue());
            }
        }

        return instanceGuidMap;
    }
    
//    public Map<Integer, String> getInstanceGuidForEachReplica(Destinations.Enum destination) {
//        long start = System.currentTimeMillis();
//        RestTemplate rt = createQuickRestTemplate(destination);
//        
//        if (destination.app_guid == null) {
//            log.error("Error retrieving app-guid for " + destination.value);
//            return null;
//        }
//
//        int replicaIndex = 0;
//        Map<Integer, String> instanceGuidMap = new HashMap<>();
//
//        while (replicaIndex < MAX_INSTANCES) {
//            try {
//                HttpHeaders headers = new HttpHeaders();
//                headers.set(INSTANCE_ROUTING_HEADER_NAME, destination.app_guid + ":" + replicaIndex);
//                HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//
//                ResponseEntity<String> response = rt.exchange(
//                        "/app/instance-guid",
//                        HttpMethod.GET,
//                        httpEntity,
//                        String.class
//                );
//
//                // Extract instance GUID from the response
//                String instanceGuid = response.getBody();
//                log.debug("index=" + replicaIndex + " instance-guid=" + instanceGuid);
//                instanceGuidMap.put(replicaIndex, instanceGuid);
//                replicaIndex++;
//            } catch (RestClientResponseException ex) {
//                // No more instances
//                if (HttpStatus.BAD_REQUEST.getReasonPhrase().equals(ex.getStatusText()))
//                    break;
//            } catch (ResourceAccessException ex) {
//                // Timeout
//                replicaIndex++;
//            } catch (Exception ex) { // Unexpected, exit returning null
//                log.error("Error retrieving instance GUID for replica index " + replicaIndex + ": " + ex.getMessage());
//                return null;
//            }
//        }
//
//        log.debug("Time elapsed: " + (System.currentTimeMillis() - start) / 1000.0 + " seconds");
//        return instanceGuidMap;
//    }    
    
    public String getHeaderValueForLessMemoryLoadedInstance(Destinations.Enum destination) {
    	
    	long start=System.currentTimeMillis();
        RestTemplate rt = createQuickRestTemplate(destination);
        
        if (destination.app_guid == null) {
        	log.error("Error retrieving app-guid for "+ destination.value);
            return null;
        }

        int replicaIndex = 0;
        int minMemIndex = -1;
        long minMemoryUsage = Long.MAX_VALUE;
        boolean isInHistory = false;
        while (replicaIndex < MAX_INSTANCES) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set(INSTANCE_ROUTING_HEADER_NAME, destination.app_guid + ":" + replicaIndex);
                HttpEntity<?> httpEntity = new HttpEntity<>(headers);

                ResponseEntity<String> response = rt.exchange(
                		"/app/used-mem",
                        HttpMethod.GET,
                        httpEntity,
                        String.class
                );

                // Check if the current replica has lower memory usage
                long currentMemoryUsage = Long.parseLong(response.getBody());                   
                long forecastMemoryUsage = predictFutureUsageBasedOnRecentAssignment(replicaIndex,destination.routingHistory);
                log.debug("index=" + replicaIndex + " mem actual=" + currentMemoryUsage + " mem anticipada=" + forecastMemoryUsage);
                currentMemoryUsage += forecastMemoryUsage;          
                if (currentMemoryUsage < minMemoryUsage) {
                    minMemoryUsage = currentMemoryUsage;
                    minMemIndex = replicaIndex;
                    isInHistory=forecastMemoryUsage>0;
                }
                replicaIndex++;
            } catch (RestClientResponseException ex) {
            	// no hay más instancias
                if (HttpStatus.BAD_REQUEST.getReasonPhrase().equals(ex.getStatusText())) 
                break;
            } catch (ResourceAccessException ex) {
                // timeout
            	replicaIndex++;
            } catch (Exception ex) { // Unexpected, exit returning null
            	log.error("Error trying to find "+ destination.value + " memory consumption:" + ex.getMessage());
            	return null;
            }
        }
        log.debug("tiempo transcurrido=" + (System.currentTimeMillis()-start)/1000.0);
        if (minMemIndex == -1) return null;
        // Si vuelve a ser el mínimo aún teniendo en cuenta las posibles compensaciones de todos, podemos
        // eliminar todas las compensaciones
        if (isInHistory) destination.routingHistory.clear();      
        destination.routingHistory.put(minMemIndex, System.currentTimeMillis());
        return destination.app_guid + ":" + minMemIndex;
    }
    
    private String getAppGuid(RestTemplate rt) {
        try {
            return rt.getForObject("/app/guid", String.class);
        } catch (Exception ex) {
            return null;
        }
    }

    public String getHeaderValueForLessMemoryLoadedInstanceParallel(Destinations.Enum destination) {
        RestTemplate rt = createQuickRestTemplate(destination);

        if (destination.app_guid == null) {
            log.error("Error retrieving app-guid for " + destination.value);
            return null;
        }

        Flux<Integer> indicesFlux = Flux.range(0, MAX_INSTANCES);

        long start = System.currentTimeMillis();
        Flux<ResultWrapper> resultFlux = indicesFlux.parallel()
                .runOn(Schedulers.boundedElastic()) // Use an appropriate scheduler
                .flatMap(replicaIndex -> Mono.fromCallable(() -> {
                    try {
                        HttpHeaders headers = new HttpHeaders();
                        headers.set(INSTANCE_ROUTING_HEADER_NAME, destination.app_guid + ":" + replicaIndex);
                        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
                        ResponseEntity<String> response = rt.exchange(
                                "/app/used-mem",
                                HttpMethod.GET,
                                httpEntity,
                                String.class
                        );
                        long currentMemoryUsage = Long.parseLong(response.getBody());
                        log.debug("index=" + replicaIndex + " mem actual=" + currentMemoryUsage);                        
                        long forecastMemoryUsage = predictFutureUsageBasedOnRecentAssignment(replicaIndex,destination.routingHistory);
                        log.debug("index=" + replicaIndex + " mem actual=" + currentMemoryUsage + " mem anticipada=" + forecastMemoryUsage);
                        return new ResultWrapper(replicaIndex, currentMemoryUsage+forecastMemoryUsage, forecastMemoryUsage>0);
                    } catch (RestClientResponseException ex) {
                    	// no hay más instancias si recibimos "Bad Request"
                        if (!HttpStatus.BAD_REQUEST.getReasonPhrase().equals(ex.getStatusText())) 
                        	log.error("Error 1 trying to find " + destination.value + " memory consumption: " + ex.getMessage());
                        return null;
                    } catch (ResourceAccessException ex) {
                        // timeout
                    	log.warn("Could not get a response: " + ex.getCause().getMessage());
                    	return null;
                    } catch (Exception ex) {
                        log.error("Error 2 trying to find " + destination.value + " memory consumption: " + ex.getMessage());
                        return null;
                    }
                }))
                .sequential(); // Convert back to sequential Flux

        List<ResultWrapper> results = resultFlux.collectList().block();

        // Find the instance with the lowest memory usage
        ResultWrapper minResult = results.stream()
                .filter(Objects::nonNull)
                .min(Comparator.comparingLong(ResultWrapper::getMemoryUsage))
                .orElse(null);

        log.debug("tiempo transcurrido=" + (System.currentTimeMillis() - start) / 1000.0);
        if (minResult == null) return null;
        // Si vuelve a ser el mínimo aún teniendo en cuenta las posibles correcciones de todos, podemos
        // eliminarlas todas
        if (minResult.isInHistory()) destination.routingHistory.clear();
        destination.routingHistory.put(minResult.replicaIndex, System.currentTimeMillis());        
        return destination.app_guid + ":" + minResult.replicaIndex;
    }

    @SuppressWarnings("rawtypes")
	private long predictFutureUsageBasedOnRecentAssignment(int replicaIndex, Map routingEvents) {
    	if (!routingEvents.containsKey(replicaIndex)) return 0L;
    	long lastRoutingEventTimestamp = (Long)routingEvents.get(replicaIndex);
    	long currentTime = System.currentTimeMillis();
    	// si hace más de 15 minutos de la última asignación a esta instancia, 
    	// el aumento en el uso de memoria ya ha debido ocurrir
    	// devolvemos cero y eliminamos del histórico esa asignación
    	if (currentTime>lastRoutingEventTimestamp + (15 * 60 * 1000))  {
    		routingEvents.remove(replicaIndex);
    		return 0L;
        }
    	// si hace entre 0 y 5 minutos de la última asignación, probablemente el uso de memoria todavía crecerá en 1,5Gb
    	if (currentTime< lastRoutingEventTimestamp + (5 * 60 * 1000) ) return 1500000000; 
    	// si hace entre 5 y 10 minutos de la última asignación, el uso de memoria todavía podría crecer en 1Gb
    	if (currentTime< lastRoutingEventTimestamp + (10 * 60 * 1000) ) return 1000000000; 
    	// si hace entre 10 y 15 minutos, quizá ya haya aumentado pero al sumar un valor evitamos que se asigne repetidamente al mismo
    	return 500000000;
    }
    
    class ResultWrapper {
        public long getMemoryUsage() {
			return this.memoryUsage;
		}
        public boolean isInHistory() {
			return this.inHistory;
		}        

		final int replicaIndex;
        final long memoryUsage;
        final boolean inHistory;

        public ResultWrapper(int replicaIndex, long memoryUsage, boolean inHistory) {
            this.replicaIndex = replicaIndex;
            this.memoryUsage = memoryUsage;
            this.inHistory = inHistory;
        }
    }

    @Slf4j
    public static class TestInterceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().forEach((x, y) -> log.error("Header {} value {} ", x, y));
            return execution.execute(request, body);
        }
    }
}

