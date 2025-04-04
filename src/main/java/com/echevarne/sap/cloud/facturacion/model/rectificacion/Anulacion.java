package com.echevarne.sap.cloud.facturacion.model.rectificacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.CanalDistribucionText;
import com.echevarne.sap.cloud.facturacion.model.texts.ClasificacionText;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.model.texts.ConceptosFacturacionText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.PacientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.ProfesionalesText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorPruebaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.VisitadoresText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sortable;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FieldSortEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.odata.jpa.annotation.GroupByField;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@Table(name = "V_ANULACION")
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class Anulacion implements Serializable {

	private static final long serialVersionUID = -8217845035460323262L;

	@Basic
	private String peticion;

	@Basic
	private String prueba;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Cliente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente", requiredInFilter = true)
	private String codigoCliente;

	@Id
	@Basic
	@Sap(requiredInFilter = true)
	private String codigoFactura;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.Interval)
	private Calendar fechaFactura;

	@Basic
	private BigDecimal importeTotal;
	
	@Basic
	private BigDecimal importePrueba;

	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String organizacionVentas;

	@Column(length = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4DistributionChannel, Label = "Canal de distribución", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoCanal", LocalDataProperty = "canalDistribucion") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCanal") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "canalDistribucionText/nombreCanal")
	private String canalDistribucion;

	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Column(length = 2)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorText/nombreSector")
	private String sectorVentas;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionEmisoraText/nombreOficina")
	private String delegacionEmisora;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "codigoRemitente") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteText/nombreRemitente", filterable = true)
	@GroupByField(fieldName = "codigoRemitente")
	private String codigoRemitente;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Pacientes, Label = "Paciente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoPaciente") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "pacienteText/nombre", filterable = true)
	private String codigoPaciente;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ConceptosFacturacion, Label = "Concepto facturación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoConcepto") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "conceptoText/nombreMaterial")
	private String codigoConcepto;

	@Basic
	private String baremo;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Visitadores, Label = "Visitador", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoVisitador") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "visitadorText/nombre", filterable = true)
	private String codigoVisitador;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Profesionales, Label = "Profesional", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoProfesional") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }),
			@ValueListParameter(ValueListParameterDisplayOnly = { @ValueListParameterDisplayOnly(ValueListProperty = "nifCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "profesionalText/nombre", filterable = true)
	private String codigoProfesional;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Clasificacion, Label = "Clasificación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoClasificacion") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "clasificacionText/nombre", filterable = true)
	private String codigoClasificacion;

	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "compania") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania")
	@Sortable(order = FieldSortEnum.Asc, priority = 5)
	private String compania;

	@ValueList(CollectionPath = ValueListEntitiesEnum.SectoresPrueba, Label = "Sector de ventas prueba", Parameters = {
			@ValueListParameter(ValueListParameterInOut = { @ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorPrueba") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorPruebaText/nombreSector", filterable = true)
	private String sectorPrueba;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "organizacionVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "canalDistribucion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CanalDistribucionText canalDistribucionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sectorVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionEmisora", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionEmisoraText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoRemitente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoPaciente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PacientesText pacienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoConcepto", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConceptosFacturacionText conceptoText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoVisitador", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private VisitadoresText visitadorText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoProfesional", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProfesionalesText profesionalText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoClasificacion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClasificacionText clasificacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "compania", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sectorPrueba", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorPruebaText sectorPruebaText;

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public CanalDistribucionText getCanalDistribucionText() {
		return EntityUtil.getOrNull(() -> this.canalDistribucionText, CanalDistribucionText::getNombreCanal);
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public OficinaVentaText getDelegacionEmisoraText() {
		return EntityUtil.getOrNull(() -> this.delegacionEmisoraText, OficinaVentaText::getNombreOficina);
	}

	public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}

	public PacientesText getPacienteText() {
		return EntityUtil.getOrNull(() -> this.pacienteText, PacientesText::getNombre);
	}

	public ConceptosFacturacionText getConceptoText() {
		return EntityUtil.getOrNull(() -> this.conceptoText, ConceptosFacturacionText::getNombreMaterial);
	}

	public VisitadoresText getVisitadorText() {
		return EntityUtil.getOrNull(() -> this.visitadorText, VisitadoresText::getNombre);
	}

	public ProfesionalesText getProfesionalText() {
		return EntityUtil.getOrNull(() -> this.profesionalText, ProfesionalesText::getNombre);
	}

	public ClasificacionText getClasificacionText() {
		return EntityUtil.getOrNull(() -> this.clasificacionText, ClasificacionText::getNombre);
	}

	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}

	public SectorPruebaText getSectorPruebaText() {
		return EntityUtil.getOrNull(() -> this.sectorPruebaText, SectorPruebaText::getNombreSector);
	}
}
