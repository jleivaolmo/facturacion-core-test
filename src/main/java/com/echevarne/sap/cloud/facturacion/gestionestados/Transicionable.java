package com.echevarne.sap.cloud.facturacion.gestionestados;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataEstado;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosEstado;
import com.fasterxml.jackson.annotation.JsonBackReference;

public interface Transicionable<T> {

	boolean transicionar(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual);
	
	boolean transicionarV2(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual);

	Map<MasDataMotivosEstado, String[]> obtieneMotivo(Procesable procesadorEstado, MasDataEstado estadoOrigen, boolean manual);
	
	T obtieneTrazabilidad();

	Optional<List<String>> obtieneDestinos();
	
	Set<String> obtieneAlertas();

	@JsonBackReference
	<T> List<T> obtieneHijos();

	@JsonBackReference
	Transicionable<?> obtienePadre();

	String obtieneNivelEntity();

	boolean contieneValidada();
}
