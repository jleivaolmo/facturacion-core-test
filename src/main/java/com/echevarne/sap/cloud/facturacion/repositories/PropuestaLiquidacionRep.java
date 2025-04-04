package com.echevarne.sap.cloud.facturacion.repositories;

import java.util.Calendar;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.echevarne.sap.cloud.facturacion.model.liquidaciones.PropuestaLiquidacion;

@Repository("propuestaLiquidacionRep")
public interface PropuestaLiquidacionRep extends JpaRepository<PropuestaLiquidacion, Long> {

	@Query("SELECT pl FROM PropuestaLiquidacion pl WHERE "
			+ "pl.proveedor IN (?1,'*') and pl.grupoProveedor IN (?2,'*') and pl.tipoLiquidacion = ?3 and pl.sociedad IN (?4,'*') and "
			+ " ((fechaInicioPeriodo <= ?5 and fechaFinPeriodo >= ?5) "
			+ " or (fechaInicioPeriodo <= ?6 and fechaFinPeriodo >= ?6)) and fechaFinalizacion is null ")
	Set<PropuestaLiquidacion> findByParamsProvGr(String proveedor, String grupoProveedor, Integer tipoLiquidacion, String sociedad, Calendar calFechaIni,
			Calendar calFechaFin);

	@Query("SELECT pl FROM PropuestaLiquidacion pl WHERE pl.proveedor IN (?1,'*') and pl.tipoLiquidacion = ?2 and pl.sociedad IN (?3,'*') and "
			+ " ((fechaInicioPeriodo <= ?4 and fechaFinPeriodo >= ?4) or (fechaInicioPeriodo <= ?5 and fechaFinPeriodo >= ?5)) and fechaFinalizacion is null ")
	Set<PropuestaLiquidacion> findByParamsProv(String proveedor, Integer tipoLiquidacion, String sociedad, Calendar calFechaIni, Calendar calFechaFin);

	@Query("SELECT pl FROM PropuestaLiquidacion pl WHERE pl.grupoProveedor IN (?1,'*') and pl.tipoLiquidacion = ?2 and pl.sociedad IN (?3,'*') and "
			+ " ((fechaInicioPeriodo <= ?4 and fechaFinPeriodo >= ?4) or (fechaInicioPeriodo <= ?5 and fechaFinPeriodo >= ?5)) and fechaFinalizacion is null ")
	Set<PropuestaLiquidacion> findByParamsGr(String grupoProveedor, Integer tipoLiquidacion, String sociedad, Calendar calFechaIni, Calendar calFechaFin);

	@Query("SELECT pl FROM PropuestaLiquidacion pl WHERE pl.tipoLiquidacion = ?1 and pl.sociedad IN (?2,'*') and "
			+ " ((fechaInicioPeriodo <= ?3 and fechaFinPeriodo >= ?3) or (fechaInicioPeriodo <= ?4 and fechaFinPeriodo >= ?4)) and fechaFinalizacion is null ")
	Set<PropuestaLiquidacion> findByParams(Integer tipoLiquidacion, String sociedad, Calendar calFechaIni, Calendar calFechaFin);

	@Query("SELECT pl FROM PropuestaLiquidacion pl WHERE pl.uuidInstance = ?1 and fechaFinalizacion is null ")
	Set<PropuestaLiquidacion> findByUUIDInProcess(String uuid);

}
