package com.echevarne.sap.cloud.facturacion.model.liquidaciones;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_ReglaLiquidacionRemitentes", indexes = {
		@Index(name = "IDX_ReglaLiquidacionRemitentes", columnList = "descripcion, prioridad, validoDesde, validoHasta, "
				+ "esMuestraRemitida, codigoOrganizacion", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ReglaLiquidacionRemitentes extends BasicEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6366210496543085952L;

	@Basic
	private String descripcion;

	@Basic
	private int prioridad;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validoDesde;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validoHasta;

	@NonNull
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "codigoOrganizacion") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion", filterable = true)
	private String codigoOrganizacion;

	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Single)
	private boolean esMuestraRemitida;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false)
	private BigDecimal importeLiquidacion1;

	@Column(precision = 16, scale = 2)
	@Sap(filterable = false)
	private BigDecimal importeLiquidacion2;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa", filterable = false)
	private BigDecimal importeLiquidacion3;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa", filterable = false)
	private BigDecimal importeLiquidacion4;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa", filterable = false)
	private BigDecimal importeLiquidacion5;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String codigoDivisa;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesCliente> clientes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesOficinaVentas> oficinasVentas = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesCodRemitente> remitentes = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesUndProductiva> undsProductivas = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesConceptoFact> conceptosFact = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesTipoPet> tiposPet = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesCompania> companias = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesPrueba> pruebas = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "reglaLiquidacion", orphanRemoval = true)
	@JsonManagedReference
	private Set<ReglaLiquidacionRemitentesDelProductiva> delsProductiva = new HashSet<>();

	/*
	 * Associations Texts
	 *
	 ********************************************/

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoOrganizacion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		ReglaLiquidacionRemitentes other = (ReglaLiquidacionRemitentes) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(codigoOrganizacion, other.codigoOrganizacion)
				&& esMuestraRemitida == other.esMuestraRemitida && Objects.equals(importeLiquidacion1, other.importeLiquidacion1)
				&& Objects.equals(importeLiquidacion2, other.importeLiquidacion2) && Objects.equals(importeLiquidacion3, other.importeLiquidacion3)
				&& Objects.equals(importeLiquidacion4, other.importeLiquidacion4) && Objects.equals(importeLiquidacion5, other.importeLiquidacion5)
				&& prioridad == other.prioridad && Objects.equals(validoDesde, other.validoDesde) && Objects.equals(validoHasta, other.validoHasta);
	}

}
