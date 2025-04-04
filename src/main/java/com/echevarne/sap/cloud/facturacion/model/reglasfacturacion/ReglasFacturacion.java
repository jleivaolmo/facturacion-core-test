package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.ValidityBasicEntity;
import com.echevarne.sap.cloud.facturacion.model.adds.AgrReglaFacturacionAdds;
import com.echevarne.sap.cloud.facturacion.model.divisores.SplitedBy;
import com.echevarne.sap.cloud.facturacion.model.divisores.Splittable;
import com.echevarne.sap.cloud.facturacion.model.matchs.Matcheable;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.MuestraRemitidaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.processor.ODataExits;
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "T_ReglaFacturacion")
@Cacheable(false)
@SapEntitySet(creatable = true, updatable = true, searchable = false)
public class ReglasFacturacion extends BasicEntity implements Splittable, ODataExits {

	private static final long serialVersionUID = -1218719891125029390L;

	@Basic
	@Column(name = "validez_desde", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezDesde = ValidityBasicEntity.getDefaultFechaDesde();

	@Basic
	@Column(name = "validez_hasta", nullable = false)
    @Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validezHasta = ValidityBasicEntity.getDefaultFechaHasta();

	@Basic
	@ColumnDefault("false")
	private int prioridad;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String organizacionVentas;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente")
	private String codigoCliente;

	@Matcheable
	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreOficina")
	private String delegacionEmisora;

	@Basic
	private String descripcionRegla;

	@Basic
	private String textoFactura;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "organizacionVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionEmisora", referencedColumnName = "codigoOficina", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "muestraRemitida", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MuestraRemitidaText muestraRemitidaText;

	/*
	 * Multiples values
	 *
	 ********************************************/
	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactDelegacion> codigoDelegacion = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactRemitentes> interlocutorRemitente = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactCompanias> interlocutorCompania = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactProvRemitente> provinciaRemitente = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactTipoPeticion> tipoPeticion = new HashSet<>();

	@Matcheable
	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed) // ,
																										// text="muestraRemitidaText/nombreMuestraRemitida")
	@ValueList(CollectionPath = ValueListEntitiesEnum.MuestraRemitida, Label = "Muestra remitida", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "muestraRemitida", LocalDataProperty = "muestraRemitida") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMuestraRemitida") }) })
	private String muestraRemitida;

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactTarifa> tarifa = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactPrueba> codigoPrueba = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactConceptoFact> conceptoFacturacion = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactUnidadProd> unidadProductiva = new HashSet<>();

	@Basic
	@ColumnDefault("false")
	private boolean noBaremada;

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactEspecialidadCliente> especialidadCliente = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactOperacion> codigoOperacion = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactReferencia> codigoReferenciaCliente = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactDocumentoUnico> documentoUnico = new HashSet<>();

	@OneToMany(mappedBy = "regla", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	@BatchSize(size = 100)
	private Set<RegFactPolizas> codigoPoliza = new HashSet<>();

	/*
	 * Valores adicionales no parametrizables
	 *
	 ********************************************/

	@Basic
	private String codigoMoneda;

	@Basic
	private String codigoPeticion;

	@Basic
	private String codigoBaremo;

	/*
	 * Configuraciones extras
	 *
	 ********************************************/

	@Basic
	@ColumnDefault("false")
	private boolean envioPrefactura;

	/*
	 * Splitters
	 *
	 ********************************************/
	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoPoliza", code = "cpo")
	private boolean facturaPorPoliza;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "tipoPeticion", code = "tp")
	private boolean facturaPorTipoPeticion;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoOperacion", code = "co")
	private boolean facturaPorOperacion;

	// @Basic
	// @SplitedBy(field = "sectorVentas", code = "sv")
	// private boolean facturaPorSector;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "sectorPrueba", code = "sp")
	private boolean facturaPorGrupoSector;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "interlocutorEmpresa", code = "ie")
	private boolean facturaPorEmpresa;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "interlocutorCompania", code = "ic")
	private boolean facturaPorCompania;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "interlocutorRemitente", code = "ir")
	private boolean facturaPorRemitente;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "unidadProductiva", code = "up")
	private boolean facturaPorUnidadProductiva;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoPrueba", code = "cp")
	private boolean facturaPorPrueba;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "documentoUnico", code = "du")
	private boolean facturaPorDocumentoUnico;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "servicioConcertado", code = "sc")
	private boolean facturaPorServicioConcertado;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoReferenciaCliente", code = "crc")
	private boolean facturaPorReferenciaCliente;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoPeticion", code = "fpp")
	private boolean facturaPorPeticion;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoMoneda", code = "fpm")
	private boolean facturaPorMoneda;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoBaremo", code = "fpb")
	private boolean facturaPorBaremo;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "conceptoFacturacion", code = "fpc")
	private boolean facturaPorConcepto;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "codigoDelegacion", code = "cd")
	private boolean facturaPorDelegacion;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "provinciaRemitente", code = "pr")
	private boolean facturaPorProvRemitente;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "muestraRemitida", code = "mr")
	private boolean facturaPorMuestraRemitida;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "tarifa", code = "tar")
	private boolean facturaPorTarifa;

	@Basic
	@ColumnDefault("false")
	@SplitedBy(field = "especialidadCliente", code = "ec")
	private boolean facturaPorEspecialidadCliente;

	@Basic
	@ColumnDefault("true")
	private boolean incluyePoliza = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeTipoPeticion = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeOperacion = true;

	// @Basic
	// private boolean incluyeSector = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeEmpresa = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeCompania = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeRemitente = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeUnidadProductiva = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyePrueba = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeDocumentoUnico = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeServicioConcertado = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeReferenciaCliente = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyePeticion = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeMoneda = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeBaremo = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeConcepto = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeDelegacion = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeProvRemitente = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeMuestraRemitida = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeTarifa = true;

	@Basic
	@ColumnDefault("true")
	private boolean incluyeEspecialidadCliente = true;

	/*
	 * Associations
	 *
	 ********************************************/
	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private AgrReglaFacturacionAdds adicional;

	@PrePersist
	public void prePersist() {
		if (StringUtils.equalsAnyOrNull(this.codigoCliente, StringUtils.EMPTY))
			this.codigoCliente = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.organizacionVentas, StringUtils.EMPTY))
			this.organizacionVentas = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.delegacionEmisora, StringUtils.EMPTY))
			this.delegacionEmisora = StringUtils.ANY;
		if (this.muestraRemitida == null)
			this.muestraRemitida = "";
	}

	@PreUpdate
	public void preUpdate() {
		if (this.muestraRemitida == null)
			this.muestraRemitida = "";
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ReglasFacturacion other = (ReglasFacturacion) obj;
		return prioridad == other.prioridad && Objects.equals(codigoCliente, other.codigoCliente)
				&& Objects.equals(codigoDelegacion, other.codigoDelegacion)
				&& Objects.equals(descripcionRegla, other.descripcionRegla)
				&& Objects.equals(textoFactura, other.textoFactura)
				&& Objects.equals(codigoOperacion, other.codigoOperacion)
				&& Objects.equals(codigoPoliza, other.codigoPoliza) && Objects.equals(codigoPrueba, other.codigoPrueba)
				&& Objects.equals(codigoReferenciaCliente, other.codigoReferenciaCliente)
				&& Objects.equals(delegacionEmisora, other.delegacionEmisora)
				&& Objects.equals(documentoUnico, other.documentoUnico)
				&& Objects.equals(interlocutorCompania, other.interlocutorCompania)
				// && Objects.equals(interlocutorEmpresa, other.interlocutorEmpresa)
				&& Objects.equals(interlocutorRemitente, other.interlocutorRemitente)
				&& Objects.equals(provinciaRemitente, other.provinciaRemitente)
				&& Objects.equals(organizacionVentas, other.organizacionVentas)
				// && Objects.equals(sectorVentas, other.sectorVentas)
				// && Objects.equals(servicioConcertado, other.servicioConcertado)
				&& Objects.equals(codigoMoneda, other.codigoMoneda)
				&& Objects.equals(codigoPeticion, other.codigoPeticion)
				&& Objects.equals(codigoBaremo, other.codigoBaremo) && Objects.equals(tipoPeticion, other.tipoPeticion)
				&& Objects.equals(unidadProductiva, other.unidadProductiva);
	}

	@Override
	public ReglasFacturacion postBatch() {
		if (this.codigoDelegacion.size() == 0)
			this.codigoDelegacion.add(new RegFactDelegacion(this));
		if (this.codigoOperacion.size() == 0)
			this.codigoOperacion.add(new RegFactOperacion(this));
		if (this.codigoPoliza.size() == 0)
			this.codigoPoliza.add(new RegFactPolizas(this));
		if (this.codigoPrueba.size() == 0)
			this.codigoPrueba.add(new RegFactPrueba(this));
		if (this.tipoPeticion.size() == 0)
			this.tipoPeticion.add(new RegFactTipoPeticion(this));
		if (this.codigoReferenciaCliente.size() == 0)
			this.codigoReferenciaCliente.add(new RegFactReferencia(this));
		if (this.documentoUnico.size() == 0)
			this.documentoUnico.add(new RegFactDocumentoUnico(this));
		if (this.interlocutorCompania.size() == 0)
			this.interlocutorCompania.add(new RegFactCompanias(this));
		// if(this.interlocutorEmpresa.size() == 0)
		// this.interlocutorEmpresa.add(new RegFactEmpresas(this));
		if (this.interlocutorRemitente.size() == 0)
			this.interlocutorRemitente.add(new RegFactRemitentes(this));
		if (this.provinciaRemitente.size() == 0)
			this.provinciaRemitente.add(new RegFactProvRemitente(this));
		// if(this.sectorVentas.size() == 0)
		// this.sectorVentas.add(new RegFactSector(this));
		// if(this.servicioConcertado.size() == 0)
		// this.servicioConcertado.add(new RegFactServicioConc(this));
		if (this.unidadProductiva.size() == 0)
			this.unidadProductiva.add(new RegFactUnidadProd(this));
		if (this.codigoReferenciaCliente.size() == 0)
			this.codigoReferenciaCliente.add(new RegFactReferencia(this));
		if (this.conceptoFacturacion.size() == 0)
			this.conceptoFacturacion.add(new RegFactConceptoFact(this));
		if (this.especialidadCliente.size() == 0)
			this.especialidadCliente.add(new RegFactEspecialidadCliente(this));
		if (this.tarifa.size() == 0)
			this.tarifa.add(new RegFactTarifa(this));
		return this;
	}

	@Override
	public BasicEntity createEntity(BasicEntity entity) {
		return null;
	}

	@Override
	public BasicEntity updateEntity(BasicEntity entity) {
		return null;
	}

	@Override
	public BasicEntity getTargetEntity() {
		return null;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public OficinaVentaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
	}

	public MuestraRemitidaText getMuestraRemitidaText() {
		return EntityUtil.getOrNull(() -> this.muestraRemitidaText, MuestraRemitidaText::getNombreMuestraRemitida);
	}
}
