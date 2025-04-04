package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.facturacion.job.JobPoolFacturacionEjecucion;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = ConstEntities.ENTIDAD_FACTLOGPERIODOS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class PeriodosFacturados extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 6407713877331154135L;
	/*
	 * Valores
	 *
	 ********************************************/
	@Basic
	private Calendar fechaInicioFacturacion;

	@Basic
	private Calendar fechaFinFacturacion;

	@Basic
	private Calendar fechaEjecucion;
	
	@Basic
	private Long idAgrupacion;
	
	@Basic
	private String operacion;
	
	@Basic
	private String uuidInstance;
	
	@Basic
	private Long numLineasPedido;
	
	@Column(precision = 16, scale = 2)
	private BigDecimal monto;
	
	@Basic
	private String codUsuario;
	
	@Basic
	private String nombreUsuario;
	
	/*
	 * Asociaciones
	 *
	 ********************************************/
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<FacturasGeneradas> facturas = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	private Set<PeriodosFacturadosClientes> clientes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosOrgVentas> organizacionesVentas = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosTipologias> tipologias = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosCodDelegacion> codigosDelegacion = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosRemitentes> remitentes = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosCias> companias = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosTiposPeticion> tiposPeticion = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosPruebas> pruebas = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosSectores> sectores = new HashSet<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "periodo")
	@JsonManagedReference
	@ToString.Exclude
	private Set<PeriodosFacturadosCodigosPeticion> codigosPeticion = new HashSet<>();
	

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "periodo", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	@ToString.Exclude
	private ControlPeriodos control;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_JobPoolFacturacionEjecucion", nullable = true)
	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	private JobPoolFacturacionEjecucion jobPoolFacturacionEjecucion;

	/*
	 * EntityMethods
	 *
	 ********************************************/
	public void addFacturas(FacturasGeneradas factura) {
		factura.setPeriodo(this);
		this.facturas.add(factura);
	}
	public void removeFacturas(FacturasGeneradas factura) {
		if(this.facturas != null)
			this.facturas.remove(factura);
	}
	
	public void addClientes(PeriodosFacturadosClientes cliente) {
		cliente.setPeriodo(this);
		this.clientes.add(cliente);
	}
	
	public void addCias(PeriodosFacturadosCias compania) {
		compania.setPeriodo(this);
		this.companias.add(compania);
	}
	
	public void addCodDelegacion(PeriodosFacturadosCodDelegacion codDelegacion) {
		codDelegacion.setPeriodo(this);
		this.codigosDelegacion.add(codDelegacion);
	}
	
	public void addOrgVentas(PeriodosFacturadosOrgVentas orgVentas) {
		orgVentas.setPeriodo(this);
		this.organizacionesVentas.add(orgVentas);
	}
	
	public void addOrgVentas(PeriodosFacturadosPruebas prueba) {
		prueba.setPeriodo(this);
		this.pruebas.add(prueba);
	}
	
	public void addRemitentes(PeriodosFacturadosRemitentes remitente) {
		remitente.setPeriodo(this);
		this.remitentes.add(remitente);
	}
	
	public void addSectores(PeriodosFacturadosSectores sector) {
		sector.setPeriodo(this);
		this.sectores.add(sector);
	}
	
	public void addTipologias(PeriodosFacturadosTipologias tipologia) {
		tipologia.setPeriodo(this);
		this.tipologias.add(tipologia);
	}
	
	public void addTiposPeticion(PeriodosFacturadosTiposPeticion tipoPeticion) {
		tipoPeticion.setPeriodo(this);
		this.tiposPeticion.add(tipoPeticion);
	}
	
	public void addCodigoPeticion(PeriodosFacturadosCodigosPeticion codPeticion) {
		codPeticion.setPeriodo(this);
		this.codigosPeticion.add(codPeticion);
	}
	
	public void addPruebas(PeriodosFacturadosPruebas prueba) {
		prueba.setPeriodo(this);
		this.pruebas.add(prueba);
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PeriodosFacturados other = (PeriodosFacturados) obj;
		return Objects.equals(fechaInicioFacturacion, other.fechaInicioFacturacion)
			&& Objects.equals(fechaFinFacturacion, other.fechaFinFacturacion)
			&& Objects.equals(fechaEjecucion, other.fechaEjecucion)
			&& Objects.equals(facturas, other.facturas)
			&& Objects.equals(idAgrupacion, other.idAgrupacion);
	}
	@Override
	public String toString() {
		return "PeriodosFacturados [id=" + id + "]";
	}

}
