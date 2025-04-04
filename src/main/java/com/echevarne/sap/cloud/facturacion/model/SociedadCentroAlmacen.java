package com.echevarne.sap.cloud.facturacion.model;

import java.io.Serializable;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.NaturalId;

import com.echevarne.sap.cloud.facturacion.model.texts.AlmacenText;
import com.echevarne.sap.cloud.facturacion.model.texts.CentroText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterIn;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor()
@EqualsAndHashCode(callSuper = false)
@Table(name = "T_SociedadCentroAlmacen", indexes = {
		@Index(name = "IDX_SociedadCentroAlmacen", columnList = "sociedad", unique = true) })
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class SociedadCentroAlmacen extends BasicEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 227629954920973831L;

	@Column(length = 4)
	@NaturalId
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "sociedad") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String sociedad;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Centros, Label = "Nombre del centro", Parameters = {
            @ValueListParameter(ValueListParameterInOut = {
                    @ValueListParameterInOut(ValueListProperty = "codigoCentro", LocalDataProperty = "centro") }, ValueListParameterDisplayOnly = {
                    		@ValueListParameterDisplayOnly(ValueListProperty = "nombreCentro") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, text = "centroText/nombreCentro")
	private String centro;

	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Almacenes, Label = "Nombre del alamcen", Parameters = {
			@ValueListParameter(
					ValueListParameterIn = {
						@ValueListParameterIn(ValueListProperty = "codigoCentro", LocalDataProperty = "centro")
					},
					ValueListParameterInOut = {
						@ValueListParameterInOut(ValueListProperty = "codigoAlmacen", LocalDataProperty = "almacen")
					},
					ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreAlmacen")
					})
			})
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed, text = "almacenText/nombreAlmacen")
	private String almacen;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sociedad", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "centro", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CentroText centroText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumns(value = {
			@JoinColumn(name = "centro", referencedColumnName = "codigoCentro", insertable = false, updatable = false, nullable = true),
			@JoinColumn(name = "almacen", referencedColumnName = "codigoAlmacen", insertable = false, updatable = false, nullable = true)
	}, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private AlmacenText almacenText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		SociedadCentroAlmacen other = (SociedadCentroAlmacen) obj;
		if (almacen == null) {
			if (other.almacen != null)
				return false;
		} else if (!almacen.equals(other.almacen))
			return false;
		if (centro == null) {
			if (other.centro != null)
				return false;
		} else if (!centro.equals(other.centro))
			return false;
		if (sociedad == null) {
			if (other.sociedad != null)
				return false;
		} else if (!sociedad.equals(other.sociedad))
			return false;
		return true;
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public CentroText getCentroText() {
		return EntityUtil.getOrNull(() -> this.centroText, CentroText::getNombreCentro);
	}

	public AlmacenText getAlmacenText() {
		return EntityUtil.getOrNull(() -> this.almacenText, AlmacenText::getNombreAlmacen);
	}
}
