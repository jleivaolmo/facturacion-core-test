package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.gestionestados.Facturada;
import com.echevarne.sap.cloud.facturacion.gestionestados.util.EstadosUtils;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesUsuario;
import com.echevarne.sap.cloud.facturacion.model.texts.CentroText;
import com.echevarne.sap.cloud.facturacion.model.texts.ConceptosFacturacionText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.MaterialesVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorPruebaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPosicionText;
import com.echevarne.sap.cloud.facturacion.model.texts.UnidadesProductivasText;
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
import com.echevarne.sap.cloud.facturacion.util.SolicitudUtils;

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
@Table(name = "V_SOLI_PRUEBA")
@Cacheable(false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class Prueba implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8033588475553975915L;

	/*
	 * Clave
	 *
	 *******************************************/

	@Id
	@Basic
	private Long uuid;

	/*
	 * Campos
	 *
	 *******************************************/

	@Basic
	private Long uuid_parent;

	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "oficinaText/nombreOficina")
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Delegación productiva", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionProductiva") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	private String delegacionProductiva;

	@Basic
	@Sap(text = "centroText/nombreCentro")
	private String codigoCentro;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.MaterialesVenta, Label = "Perfil / Prueba", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoPrueba") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "pruebaText/nombreMaterial")
	private String codigoPrueba;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.ConceptosFacturacion, Label = "Concepto de facturación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoMaterial", LocalDataProperty = "codigoConcepto") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreMaterial") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "conceptoText/nombreMaterial")
	private String codigoConcepto;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaPrecio;

	@Basic
	private String motivoBloqueo;

	@Basic
	private String motivoRechazo;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa")
	private BigDecimal valorPosicion;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa")
	private BigDecimal valorNeto;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "codigoDivisa")
	private BigDecimal valorImpuestos;

	@Column(precision = 16, scale = 2)
	private BigDecimal valorPuntos;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "codigoDivisa") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String codigoDivisa;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoPosicion, Label = "Tipo de posicion", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "tipoPosicion", LocalDataProperty = "tipoPosicion") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPosicion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Single, valueList = PropertyValueListEnum.Standard, text = "tipoPosicionText/nombreTipoPosicion")
	private String tipoPosicion;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar fechaValidacion;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4UnidadesProductivas, Label = "Unidades productivas", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "unidadProductiva") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "unidadesProductivasText/nombre")
	private String unidadProductiva;

	@Basic
	private boolean muestraRemitida;

	@Basic
	private boolean hasAlertas;

	@Basic
	private String pictureUrl;

	@Basic
	@Column(precision = 34, scale = 3)
	@Sap(unit = "unidad")
	private BigDecimal cantidad;

	@Basic
	private String unidad;

	@Basic
	private boolean esPrivada;

	@Basic
	private String reglaFacturacion;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.SectoresPrueba, Label = "Sector de ventas prueba", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sector") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorPruebaText/nombreSector", filterable = true)
	private String sector;

	@Basic
	@Sap(filterable = true)
	private String codigoBaremo;

	@Basic
	private String autorizacion;

	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Single)
	private boolean pruebaAutorizada;

	@Basic
	@Sap(filterRestriction = FilterRestrictionsEnum.Single)
	private boolean faltaMuestra;

	@Basic
	private String codigoSnomed;

	@Basic
	private String lote;

	@Basic
	private String tipoMuestra;

	@Basic
	private String codigoPeticion;

	@Basic
	private String documentoUnico;

	@Basic
	private String codigoOperacion;

	@Basic
	private String cargoFacturacion;

	@Basic
    @Sap(filterable = true, filterRestriction = FilterRestrictionsEnum.None)
    private String codigoPeticionLims;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.Estados, Label = "Estado de la prueba", Parameters = {
		@ValueListParameter( ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "codigoEstado")}, ValueListParameterDisplayOnly = {
				@ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado")}) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text="nombreEstado", valueList=PropertyValueListEnum.Fixed)
	private int codigoEstado;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.GrupoEstados, Label = "Estado de facturación", Parameters = {
		@ValueListParameter( ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "codigoGrupoEstado")}, ValueListParameterDisplayOnly = {
				@ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado")}) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text="nombreGrupoEstado", valueList=PropertyValueListEnum.Fixed)
	private int codigoGrupoEstado;

	@Basic
	@Sap(filterable = false)
	private String nombreEstado;

    @Basic
	@Sap(filterable = false)
	private int criticallyEstado;

    @Basic
	@Sap(filterable = false)
	private String nombreGrupoEstado;

	@Basic
	@Sap(filterable = false)
	private String idTipologia;

    @Basic
	@Sap(filterable = false)
	private String nombreTipologia;

	@Basic
	@Sap(filterable = false)
	private String motivoEstado;

    @Basic
	@Sap(filterable = false)
	private boolean estadoAutomatico;

	@Basic
	@Sap(filterable = false)
	private String contrato;

	/*
	 * UI Fields
	 *
	 *******************************************/
	@Basic
	private int critically;

	@Basic
	private boolean modificada;

	/*
	 * UI Control fields
	 *
	 *******************************************/
	@Basic
	@Getter(AccessLevel.NONE)
	private boolean validForModify;

	@Basic
	@Getter(AccessLevel.NONE)
	private boolean validForEdit;

	@Basic
	@Getter(AccessLevel.NONE)
	private boolean validForDelete;

	@Basic
	@Getter(AccessLevel.NONE)
	private boolean validForExclude;

	@Basic
	@Getter(AccessLevel.NONE)
	private boolean validForInclude;

	//La validación de las operaciones de precio se ha de hacer en backend, no en la app. Se ha de poner todo esto a TRUE
	
	public boolean isValidForModify() {
		return true;
	}

	public boolean isValidForEdit() {
		return true;
	}

	public boolean isValidForDelete() {
		return true;
	}

	public boolean isValidForExclude() {
		return EstadosUtils.getAccionPermitida(AccionesUsuario.ExcluirPrueba, this.getCodigoEstado(), EstadosUtils.NIVEL_POSICION)
				&& !Facturada.CODIGO.equals(this.estadoSolicitud.getEstadoFactText().getShortEstado());
	}

	public boolean isValidForInclude() {
		return EstadosUtils.getAccionPermitida(AccionesUsuario.IncluirPrueba, this.getCodigoEstado(), EstadosUtils.NIVEL_POSICION)
				&& !Facturada.CODIGO.equals(this.estadoSolicitud.getEstadoFactText().getShortEstado()) && tipoPosicion.equals(ConstFacturacion.TIPO_POSICION_EXCLUIDA)
                && !this.isEstadoAutomatico();
	}

	/*
	 * Associations
	 *
	 *******************************************/

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
	private Set<FlujoProcesoPrueba> flujo;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
	private Set<AlertasPruebas> alertas;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "uuid", referencedColumnName = "uuid", insertable = false, updatable = false)
	private Set<CondicionesPrecio> condiciones;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "uuid_parent", insertable = false, updatable = false)
	private UltimoEstadoSolicitud estadoSolicitud;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionProductiva", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText oficinaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoPrueba", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private MaterialesVentaText pruebaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoConcepto", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ConceptosFacturacionText conceptoText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoDivisa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipoPosicion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPosicionText tipoPosicionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "unidadProductiva", insertable = false, updatable = false, nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private UnidadesProductivasText unidadesProductivasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sector", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorPruebaText sectorPruebaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCentro", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CentroText centroText;

	public OficinaVentaText getOficinaText() {
		return EntityUtil.getOrNull(() -> this.oficinaText, OficinaVentaText::getNombreOficina);
	}

	public MaterialesVentaText getPruebaText() {
		return EntityUtil.getOrNull(() -> this.pruebaText, MaterialesVentaText::getNombreMaterial);
	}

	public ConceptosFacturacionText getConceptoText() {
		return EntityUtil.getOrNull(() -> this.conceptoText, ConceptosFacturacionText::getNombreMaterial);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}

	public TipoPosicionText getTipoPosicionText() {
		return EntityUtil.getOrNull(() -> this.tipoPosicionText, TipoPosicionText::getNombreTipoPosicion);
	}

	public UnidadesProductivasText getUnidadesProductivasText() {
		return EntityUtil.getOrNull(() -> this.unidadesProductivasText, UnidadesProductivasText::getNombre);
	}

	public SectorPruebaText getSectorPruebaText() {
		return EntityUtil.getOrNull(() -> this.sectorPruebaText, SectorPruebaText::getNombreSector);
	}

	public CentroText getCentroText() {
		return EntityUtil.getOrNull(() -> this.centroText, CentroText::getNombreCentro);
	}
}
