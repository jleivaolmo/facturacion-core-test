package com.echevarne.sap.cloud.facturacion.model.determinaciones;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.model.texts.EmpresasText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
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

import lombok.Getter;
import lombok.Setter;

/**
 * Class for the Entity {@link BloqueoCortesia}.
 */
@Getter
@Setter
@Entity
@Table(name = ConstEntities.ENTIDAD_BLOQUEOCORTESIA, indexes = {
		@Index(name = "IDX_bloqueoCortesia", columnList = "organizacionVentas,cliente,oficinaVentas,tipoPeticion,empresa,compania,remitente,validez_desde", unique = true)
	})
@Cacheable(false)
@SapEntitySet(searchable = false)
public class BloqueoCortesia extends BasicEntity {

	private static final long serialVersionUID = -8032984327881774386L;

	@Priorizable
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	@Sortable(order=FieldSortEnum.Asc, priority=2)
	private String organizacionVentas;

	@Priorizable
	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "cliente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente")
	@Sortable(order=FieldSortEnum.Asc, priority=1)
	private String cliente;

	@Priorizable
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "oficinaVentasText/nombreOficina")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "oficinaVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sortable(order=FieldSortEnum.Asc, priority=3)
	private String oficinaVentas;

	@Priorizable
	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
	@Sortable(order=FieldSortEnum.Asc, priority=4)
	private int tipoPeticion;

	@Priorizable
	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Empresas, Label = "Empresa", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoEmpresa", LocalDataProperty = "empresa") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreEmpresa") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "empresaText/nombreEmpresa")
	@Sortable(order=FieldSortEnum.Asc, priority=7)
	private String empresa;

	@Priorizable
	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "compania") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania")
	@Sortable(order=FieldSortEnum.Asc, priority=5)
	private String compania;

	@Priorizable
	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "remitente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteText/nombreRemitente")
	@Sortable(order=FieldSortEnum.Asc, priority=6)
	private String remitente;

	@Basic
	@Column(name = "validez_desde", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date, filterable = true)
	@Sortable(order=FieldSortEnum.Asc, priority=8)
	private Calendar validezDesde;

	@Basic
	@Column(name = "validez_hasta", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date, filterable = true)
	@Sortable(order=FieldSortEnum.Asc, priority=9)
	private Calendar validezHasta;
	/*
     * Associations Texts
     *
     ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="organizacionVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OrganizacionVentaText organizacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="cliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="oficinaVentas", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaVentasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="empresa", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EmpresasText empresaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="compania", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="remitente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="tipoPeticion", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		BloqueoCortesia other = (BloqueoCortesia) obj;
		return  Objects.equals(this.organizacionVentas, other.organizacionVentas) &&
				Objects.equals(this.cliente, other.cliente) &&
				Objects.equals(this.oficinaVentas, other.oficinaVentas) &&
				Objects.equals(this.tipoPeticion, other.tipoPeticion) &&
				Objects.equals(this.empresa, other.empresa) &&
				Objects.equals(this.compania, other.compania) &&
				Objects.equals(this.remitente, other.remitente);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public OficinaVentaText getOficinaVentasText() {
		return EntityUtil.getOrNull(() -> this.oficinaVentasText, OficinaVentaText::getNombreOficina);
	}

	public EmpresasText getEmpresaText() {
		return EntityUtil.getOrNull(() -> this.empresaText, EmpresasText::getNombreEmpresa);
	}

	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}

	public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}
}
