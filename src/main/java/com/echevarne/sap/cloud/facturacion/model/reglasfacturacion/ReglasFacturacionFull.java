package com.echevarne.sap.cloud.facturacion.model.reglasfacturacion;

import java.util.Calendar;
import javax.persistence.*;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import com.echevarne.sap.cloud.facturacion.model.texts.BooleanText;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.MuestraRemitidaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.RegionText;
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
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterOut;
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
@Table(name = "V_REGLAFACT_FULL")
@SapEntitySet(creatable = false, updatable = false, searchable = false, deletable = false)
public class ReglasFacturacionFull {

    @Id
    @Basic
    private Long id;

    @Basic
    private String descripcionRegla;

    @Basic
    private int prioridad;

    @Basic
    private int critically;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.EstadoEntidad, Label = "Estado de la regla", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
            @ValueListParameterInOut(ValueListProperty = "estado", LocalDataProperty = "estado") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)
    private String estado;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    private Calendar validezDesde;

    @Basic
    @Sap(displayFormat = DisplayFormatEnum.Date, filterRestriction = FilterRestrictionsEnum.None)
    private Calendar validezHasta;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="clienteText/nombreCliente")
    private String codigoCliente;

    @Basic
    @ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text="organizacionText/nombreOrganizacion")
    private String organizacionVentas;

    @Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                        @ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "envioPrefactura") },
                        ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private boolean envioPrefactura;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionEmisoraText/nombreOficina")
	private String delegacionEmisora;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación", Parameters = {
        @ValueListParameter(ValueListParameterInOut = {
                @ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "codigoDelegacion") }, ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionText/nombreOficina")
    private String codigoDelegacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeDelegacion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeDelegacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorDelegacion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorDelegacion;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Interlocutor comercial", Parameters = {
        @ValueListParameter(ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "interlocutorRemitente") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente")
                })
        })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text="remitenteText/nombreRemitente")
    private String interlocutorRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeRemitente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorRemitente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorRemitente;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Interlocutor comercial", Parameters = {
        @ValueListParameter(ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "codigoCompania", LocalDataProperty = "interlocutorCompania") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania")
                })
        })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text="companiaText/nombreCompania")
    private String interlocutorCompania;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeCompania") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeCompania;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorCompania") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorCompania;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Regiones, Label = "Provincia del remitente", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRegion", LocalDataProperty = "provinciaRemitente") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreRegion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "regionRemitenteText/nombreRegion")
	private String provinciaRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeProvRemitente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeProvRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorProvRemitente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorProvRemitente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorPeticion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorPeticion;

    @Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed)//, text="tipoPeticionText/nombreTipoPeticion")
    @ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = {
        @ValueListParameter(ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
    private String tipoPeticion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeTipoPeticion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeTipoPeticion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorTipoPeticion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorTipoPeticion;

    @Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed) //, text="muestraRemitidaText/nombreMuestraRemitida")
	@ValueList(CollectionPath = ValueListEntitiesEnum.MuestraRemitida, Label = "Muestra remitida", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "muestraRemitida", LocalDataProperty = "muestraRemitida") },
			ValueListParameterDisplayOnly = {
				@ValueListParameterDisplayOnly(ValueListProperty = "nombreMuestraRemitida") }) })
    private String muestraRemitida;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeMuestraRemitida") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeMuestraRemitida;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorMuestraRemitida") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorMuestraRemitida;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Tarifas, Label = "Tarifa", Parameters = {
		@ValueListParameter(ValueListParameterOut = {
				@ValueListParameterOut(ValueListProperty = "codigoTarifa", LocalDataProperty = "tarifa") },
				ValueListParameterDisplayOnly = {
				@ValueListParameterDisplayOnly(ValueListProperty = "nombreTarifa")
		})
	})
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text="tarifaText/nombreTarifa")
    private String tarifa;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeTarifa") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeTarifa;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorTarifa") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorTarifa;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Perfil / Prueba", Parameters = {
        @ValueListParameter(ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoPrueba") },
                ValueListParameterDisplayOnly = {
                @ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text="pruebaText/nombreMaterial")
    private String codigoPrueba;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyePrueba") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyePrueba;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorPrueba") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorPrueba;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ConceptosFacturacion, Label = "Concepto facturacion", Parameters = {
        @ValueListParameter(ValueListParameterOut = {
                @ValueListParameterOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "conceptoFacturacion") }, ValueListParameterDisplayOnly = {
                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
    @Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text = "conceptoText/nombreMaterial")
    private String conceptoFacturacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeConcepto") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeConcepto;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorConcepto") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorConcepto;

    @Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4UnidadesProductivas, Label = "Unidades productivas", Parameters = {
		@ValueListParameter(ValueListParameterOut = {
				@ValueListParameterOut(ValueListProperty = "nombre", LocalDataProperty = "unidadProductiva") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard)//, text = "unidadesProductivasText/nombre")
    private String unidadProductiva;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeUnidadProductiva") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeUnidadProductiva;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorUnidadProductiva") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorUnidadProductiva;

    @Basic
    private boolean noBaremada;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorBaremo") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorBaremo;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String especialidadCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeEspecialidadCliente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeEspecialidadCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorEspecialidadCliente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorEspecialidadCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String codigoOperacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeOperacion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeOperacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorOperacion") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorOperacion;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String codigoReferenciaCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeReferenciaCliente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeReferenciaCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorReferenciaCliente") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorReferenciaCliente;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String documentoUnico;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyeDocumentoUnico") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyeDocumentoUnico;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorDocumentoUnico") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorDocumentoUnico;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.None)
    private String codigoPoliza;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombreIncluir")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombreIncluir", LocalDataProperty = "incluyePoliza") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombreIncluir") }) })
    private String incluyePoliza;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorPoliza") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorPoliza;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorMoneda") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorMoneda;

    @Basic
    @Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Fixed)//, text="booleanText/nombre")
    @ValueList(CollectionPath = ValueListEntitiesEnum.Boolean, Label = "Sí o No", Parameters = {
                        @ValueListParameter(ValueListParameterInOut = {
                                        @ValueListParameterInOut(ValueListProperty = "nombre", LocalDataProperty = "facturaPorGrupoSector") },
                                        ValueListParameterDisplayOnly = {
                                        @ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
    private String facturaPorGrupoSector;
    
    @Basic
	private String textoFactura;

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
	@JoinColumn(name="codigoCliente", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionEmisora", referencedColumnName = "codigoOficina", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionEmisoraText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDelegacion", referencedColumnName = "codigoOficina", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="muestraRemitida", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MuestraRemitidaText muestraRemitidaText;

    @ManyToOne(fetch = FetchType.LAZY)
    @LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="envioPrefactura", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private BooleanText booleanText;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "provinciaRemitente", referencedColumnName = "codigoRegion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RegionText regionRemitenteText;

    public OrganizacionVentaText getOrganizacionText() {
        return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
    }

    public ClientesText getClienteText() {
        return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
    }

    public OficinaVentaText getDelegacionEmisoraText() {
		return EntityUtil.getOrNull(() -> this.delegacionEmisoraText, OficinaVentaText::getNombreOficina);
	}

	public OficinaVentaText getDelegacionText() {
		return EntityUtil.getOrNull(() -> this.delegacionText, OficinaVentaText::getNombreOficina);
	}

    public MuestraRemitidaText getMuestraRemitidaText() {
        return EntityUtil.getOrNull(() -> this.muestraRemitidaText, MuestraRemitidaText::getNombreMuestraRemitida);
    }

    public BooleanText getBooleanText() {
        return EntityUtil.getOrNull(() -> this.booleanText, BooleanText::getNombre);
    }
    
    public RegionText getRegionRemitenteText() {
		return EntityUtil.getOrNull(() -> this.regionRemitenteText, RegionText::getNombreRegion);
	}
}
