package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.PeticionesCliente;
import com.echevarne.sap.cloud.facturacion.model.actualizacion.ProcesoActualizacion;
import com.echevarne.sap.cloud.facturacion.util.DateUtils;
import lombok.var;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Repository for the Model {@link PeticionesClienteRep}
 * @author Germán Laso
 * @since 22/09/2020
 */

@Repository("peticionesClienteRep")
public interface PeticionesClienteRep extends JpaRepository<PeticionesCliente,Long>, JpaSpecificationExecutor<PeticionesCliente> {

	public static final String SQLANY = "%";
	public static final String ANY = "*";
	public static final Integer ANYINT = 0;

	static Predicate filterByParamsPredicate(ProcesoActualizacion procesoActualizacion, Root<PeticionesCliente> pc, CriteriaBuilder cb) {
		var codigosCliente = procesoActualizacion.getListaClientes();
		var orgsVentas = procesoActualizacion.getListaOrgVentas();
		var areasVentas = procesoActualizacion.getListaAreasVentas();
		var cargosPeticion = procesoActualizacion.getListaCargosPeticion();
		var codsAlerta = procesoActualizacion.getListaCodsAlerta();
		var codsDivisa = procesoActualizacion.getListaCodsDivisa();
		var codsHistClinica = procesoActualizacion.getListaCodsHistClinica();
		var codsCanal = procesoActualizacion.getListaCodigosCanal();
		var codsEstado = procesoActualizacion.getListaCodigosEstado();
		var codsGrupoEstado = procesoActualizacion.getListaCodigosGrupoEstado();
		var codsPeticion = procesoActualizacion.getListaCodigosPeticion();
		var codsSector = procesoActualizacion.getListaCodigosSector();
		var codsPaciente = procesoActualizacion.getListaCodigosPaciente();
		var codsPetLims = procesoActualizacion.getListaCodPeticionLims();
		var codsRefExt = procesoActualizacion.getListaCodRefExterno();
		var codsTarifa = procesoActualizacion.getListaCodTarifa();
		var codsUsrExt = procesoActualizacion.getListaCodUsrExterno();
		var companias = procesoActualizacion.getListaCompanias();
		var conceptosFact = procesoActualizacion.getListaConceptosFact();
		var esMixta = procesoActualizacion.getEsMixta();
		var esMuestraRemitida = procesoActualizacion.getEsMuestraRemitida();
		var esPrivada = procesoActualizacion.getEsPrivada();
		var fechaRecMuestraIni = DateUtils.toIniDate(procesoActualizacion.getFechaRecepcionMuestraIni());
		var fechaRecMuestraFin = DateUtils.toFinalDate(procesoActualizacion.getFechaRecepcionMuestraFin());
		var fechaTomaMuestraIni = DateUtils.toIniDate(procesoActualizacion.getFechaTomaMuestraIni());
		var fechaTomaMuestraFin = DateUtils.toFinalDate(procesoActualizacion.getFechaTomaMuestraFin());
		var fechaActDesde = DateUtils.toIniDate(procesoActualizacion.getFechaActualizacionDesde());
		var fechaActHasta = DateUtils.toFinalDate(procesoActualizacion.getFechaActualizacionHasta());
		var fechaCreacionDesde = DateUtils.toIniDate(procesoActualizacion.getFechaCreacionDesde());
		var fechaCreacionHasta = DateUtils.toFinalDate(procesoActualizacion.getFechaCreacionHasta());
		var fechaPeticionDesde = DateUtils.toIniDate(procesoActualizacion.getFechaPeticionDesde());
		var fechaPeticionHasta = DateUtils.toFinalDate(procesoActualizacion.getFechaPeticionHasta());
		var fechaFacturaDesde = DateUtils.toIniDate(procesoActualizacion.getFechaFacturaDesde());
		var fechaFacturaHasta = DateUtils.toFinalDate(procesoActualizacion.getFechaFacturaHasta());
		var motivosEstado = procesoActualizacion.getListaMotivosEstado();
		var numerosContrato = procesoActualizacion.getListaNumerosContrato();
		var oficinasVentas = procesoActualizacion.getListaOficinasVentas();
		var provsRemitente = procesoActualizacion.getListaProvRemitente();
		var refsCliente = procesoActualizacion.getListaRefCliente();
		var remitentes = procesoActualizacion.getListaRemitentes();
		var tiposContrato = procesoActualizacion.getListaTiposContrato();
		var tiposCotizacion = procesoActualizacion.getListaTiposCotizacion();
		var tiposPeticion = procesoActualizacion.getListaTiposPeticion();
		var materiales = procesoActualizacion.getListaMaterial();
		var facturas = procesoActualizacion.getListaFacturas();
		var lotes = procesoActualizacion.getListaLotes();
		var lugarMuestreo = procesoActualizacion.getListaLugarMuestreo();
		var nombreAnimal = procesoActualizacion.getListaNombreAnimal();
		var codigoPropietario = procesoActualizacion.getListaCodigoPropietario();
		var nombreDescr = procesoActualizacion.getListaNombreDescr();
		var pedidoVenta = procesoActualizacion.getListaPedidoVenta();
		var prefactura = procesoActualizacion.getListaPrefactura();
		
		var predicate = predicateFilter(codigosCliente, orgsVentas, areasVentas, cargosPeticion, codsAlerta, codsDivisa,
				codsHistClinica, codsCanal, codsEstado, codsGrupoEstado, codsPeticion, codsSector, codsPaciente,
				codsPetLims, codsRefExt, codsTarifa, codsUsrExt, companias, conceptosFact, esMixta, esMuestraRemitida,
				esPrivada, fechaRecMuestraIni, fechaRecMuestraFin, fechaTomaMuestraIni, fechaTomaMuestraFin,
				fechaActDesde, fechaActHasta, fechaCreacionDesde, fechaCreacionHasta, fechaPeticionDesde,
				fechaPeticionHasta, fechaFacturaDesde, fechaFacturaHasta, motivosEstado, numerosContrato, oficinasVentas,
				provsRemitente, refsCliente, remitentes, tiposContrato, tiposCotizacion, tiposPeticion, materiales, facturas, 
				lotes, lugarMuestreo, nombreAnimal, codigoPropietario, nombreDescr, pedidoVenta, prefactura, pc, cb);
		return predicate;
	}

	static Predicate predicateFilter(Set<String> codigosCliente,
			Set<String> orgsVentas, Set<String> areasVentas,
			Set<String> cargosPeticion, Set<String> codsAlerta,
			Set<String> codsDivisa, Set<String> codsHistClinica,
			Set<String> codsCanal, Set<String> codsEstado,
			Set<String> codsGrupoEstado, Set<String> codsPeticion,
			Set<String> codsSector, Set<String> codsPaciente,
			Set<String> codsPetLims, Set<String> codsRefExt,
			Set<String> codsTarifa, Set<String> codsUsrExt,
			Set<String> companias, Set<String> conceptosFact,
			Boolean esMixta, Boolean esMuestraRemitida, Boolean esPrivada,
			Calendar fechaRecMuestraIni, Calendar fechaRecMuestraFin,
			Calendar fechaTomaMuestraIni, Calendar fechaTomaMuestraFin,
			Calendar fechaActDesde, Calendar fechaActHasta, Calendar fechaCreacionDesde,
			Calendar fechaCreacionHasta, Calendar fechaPeticionDesde,
			Calendar fechaPeticionHasta, Calendar fechaFacturaDesde,
			Calendar fechaFacturaHasta, Set<String> motivosEstado,
			Set<String> numerosContrato, Set<String> oficinasVentas,
			Set<String> provsRemitente, Set<String> refsCliente,
			Set<String> remitentes, Set<String> tiposContrato,
			Set<String> tiposCotizacion, Set<Integer> tiposPeticion,
			Set<String> materiales, Set<String> facturas, 
			Set<String> lotes, Set<String> lugarMuestreo, 
			Set<String> nombreAnimal, Set<String> codigoPropietario, 
			Set<String> nombreDescr, Set<String> pedidoVenta, Set<String> prefactura, 
			Root<PeticionesCliente> pc, CriteriaBuilder cb) {
		return cb.and(
				filtroPorCampo("codigoCliente",codigosCliente, pc, cb),
				filtroPorCampo("organizacionVentas",orgsVentas, pc, cb),
				filtroPorCampo("codigoDelegacion",oficinasVentas, pc, cb),
				filtroPorCampo("areaVentas",areasVentas, pc, cb),
				filtroPorCampo("cargoPeticion",cargosPeticion, pc, cb),
				filtroPorCampo("codAlerta",codsAlerta, pc, cb),
				filtroPorCampo("codDivisa",codsDivisa, pc, cb),
				filtroPorCampo("codHistClinica",codsHistClinica, pc, cb),
				filtroPorCampo("codCanal",codsCanal, pc, cb),
				filtroPorCampo("codigoEstado",codsEstado, pc, cb),
				filtroPorCampo("codigoGrupoEstado",codsGrupoEstado, pc, cb),
				filtroPorCampo("codigoPeticion",codsPeticion, pc, cb),
				filtroPorCampo("codSector",codsSector, pc, cb),
				filtroPorCampo("codPaciente",codsPaciente, pc, cb),
				filtroPorCampo("codPetLims",codsPetLims, pc, cb),
				filtroPorCampo("codRefExt",codsRefExt, pc, cb),
				filtroPorCampo("codTarifa",codsTarifa, pc, cb),
				filtroPorCampo("codUsrExt",codsUsrExt, pc, cb),
				filtroPorCampo("compania",companias, pc, cb),
				filtroPorCampo("conceptoFact",conceptosFact, pc, cb),
				filtroPorCampo("motivoEstado",motivosEstado, pc, cb),
				filtroPorCampo("numeroContrato",numerosContrato, pc, cb),
				filtroPorCampo("provRemitente",provsRemitente, pc, cb),
				filtroPorCampo("refCliente",refsCliente, pc, cb),
				filtroPorCampo("remitente",remitentes, pc, cb),
				filtroPorCampo("tipoContrato",tiposContrato, pc, cb),
				filtroPorCampo("tipoCotizacion",tiposCotizacion, pc, cb),
				filtroPorCampoInt("tipoPeticion",tiposPeticion, pc, cb),
				filtroPorCampo("material",materiales, pc, cb),
				filtroPorCampo("factura",facturas, pc, cb),
				filtroPorCampo("lote",lotes, pc, cb),
				filtroPorCampo("lugarMuestreo",lugarMuestreo, pc, cb),
				filtroPorCampo("nombreAnimal",nombreAnimal, pc, cb),
				filtroPorCampo("codigoPropietario",codigoPropietario, pc, cb),
				filtroPorCampo("nombreDescr",nombreDescr, pc, cb),
				filtroPorCampo("pedidoVenta",pedidoVenta, pc, cb),
				filtroPorCampo("prefactura",prefactura, pc, cb),
				
				(esMixta!=null? cb.equal(pc.get("esMixta"), esMixta) : cb.and()),
				(esMuestraRemitida!=null? cb.equal(pc.get("esMuestraRemitida"), esMuestraRemitida) : cb.and()),
				(esPrivada!=null? cb.equal(pc.get("esPrivada"), esPrivada) : cb.and()),
				
				filtroFechas("fechaRecMuestra", fechaRecMuestraIni, fechaRecMuestraFin, pc, cb),
				filtroFechas("fechaTomaMuestra", fechaTomaMuestraIni, fechaTomaMuestraFin, pc, cb),
				filtroFechas("fechaActualizacion", fechaActDesde, fechaActHasta, pc, cb),
				filtroFechas("fechaPeticion", fechaPeticionDesde, fechaPeticionHasta, pc, cb),
				filtroFechas("fechaCreacion", fechaCreacionDesde, fechaCreacionHasta, pc, cb),
				filtroFechas("fechaFactura", fechaFacturaDesde, fechaFacturaHasta, pc, cb));
	}

	static Predicate filtroFechas(String nombre, Calendar fechaIni, Calendar fechaFin, Root<PeticionesCliente> pc, CriteriaBuilder cb) {
		return Objects.nonNull(fechaIni) && Objects.nonNull(fechaFin)
				? cb.between(pc.get(nombre), fechaIni, fechaFin)
				: cb.and();
	}

	static Predicate filtroPorCampo(String nombre, Set<String> lista, Root<PeticionesCliente> pc, CriteriaBuilder cb) {
		Predicate response = null;
		if (lista.isEmpty() || lista.contains(ANY)) {
			response = cb.and();
		} else if (lista.size()==1 && lista.stream().findFirst().get().contains(ANY)){
			String filterValue = lista.stream().findFirst().get();
			String sqlFilterValue = filterValue.replace(ANY, SQLANY);
			response = cb.like(pc.<String>get(nombre), sqlFilterValue);
		} else {
			response = pc.get(nombre).in(lista);
		}
		return response;
	}
	
	static Predicate filtroPorCampoInt(String nombre, Set<Integer> lista, Root<PeticionesCliente> pc, CriteriaBuilder cb) {
		Predicate response = null;
		if (lista.isEmpty() || lista.contains(ANYINT)) {
			response = cb.and();
		} else {
			response = pc.get(nombre).in(lista);
		}
		return response;
	}

	//David Bolet: Se añade un default method para devolver la query como stream usando specifications
	default Stream<PeticionesCliente> streamAll(EntityManager em, Specification<PeticionesCliente> spec) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<PeticionesCliente> query = cb.createQuery(PeticionesCliente.class);
		Root<PeticionesCliente> root = query.from(PeticionesCliente.class);
		query.where(spec.toPredicate(root, query, cb));
		return em.createQuery(query).getResultStream();
	}

	List<PeticionesCliente> findAllByCodigoCliente(String codigoCliente);

	Integer countByCodigoCliente(String codigoCliente);

	@Query("from PeticionesCliente pc where pc.codigoPeticion in :listaCodigos")
	List<PeticionesCliente> findAllByListaCodigos(@Param("listaCodigos") List<String> listaCodigos);
}
