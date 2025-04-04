package com.echevarne.sap.cloud.facturacion.model;

import java.util.Calendar;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.DisplayFormatEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;

import lombok.Data;

@Entity
@Data
@Table(name = "T_ReglaFacturaPorFecha")
@SapEntitySet(searchable = true)
public class ReglaFacturaPorFecha extends ValidityBasicEntity {

	private static final long serialVersionUID = 347052884259267009L;

	@Column(columnDefinition="NVARCHAR(4) default '*'")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="organizacionText/nombreOrganizacion")
    private String organizacionVentas;

	@Column(columnDefinition="NVARCHAR(10) default '*'")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard,  text="clienteText/nombreCliente")
    private String codigoCliente;

	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed,  text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    private int tipoPeticion;

	@Column(columnDefinition="NVARCHAR(10) default '*'")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoEmpresa", LocalDataProperty = "interlocutorEmpresa") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreEmpresa")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="empresaText/nombreEmpresa")
    private String interlocutorEmpresa;

	@Column(columnDefinition="NVARCHAR(10) default '*'")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "interlocutorCompania") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="companiaText/nombreCompania")
    private String interlocutorCompania;

	@Column(columnDefinition="NVARCHAR(10) default '*'")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Interlocutor comercial", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "interlocutorRemitente") },
					ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente")
					})
			})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="remitenteText/nombreRemitente")
    private String interlocutorRemitente;

	@Basic
	@Column(name = "fecha_peticion", nullable = false)
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaPeticion;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		ReglaFacturaPorFecha other = (ReglaFacturaPorFecha) obj;
		return
				Objects.equals(organizacionVentas, other.organizacionVentas) &&
				Objects.equals(codigoCliente, other.codigoCliente) &&
				tipoPeticion == other.tipoPeticion &&
				Objects.equals(interlocutorCompania, other.interlocutorCompania) &&
				Objects.equals(interlocutorEmpresa, other.interlocutorEmpresa) &&
				Objects.equals(interlocutorRemitente, other.interlocutorRemitente) &&
				Objects.equals(fechaPeticion, other.fechaPeticion);
	}
}
