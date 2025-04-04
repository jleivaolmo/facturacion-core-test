package com.echevarne.sap.cloud.facturacion.model.contratos;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;
import org.hibernate.annotations.*;
import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.constants.ConstFacturacion;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.adds.ContratoCapitativoAdds;
import com.echevarne.sap.cloud.facturacion.model.priority.Priorizable;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolIndPartner;
import com.echevarne.sap.cloud.facturacion.model.solicitudindividual.SolicitudIndividual;
import com.echevarne.sap.cloud.facturacion.model.solicitudrecibida.PeticionMuestreo;
import com.echevarne.sap.cloud.facturacion.model.texts.CanalDistribucionText;
import com.echevarne.sap.cloud.facturacion.model.texts.ClientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.DivisasText;
import com.echevarne.sap.cloud.facturacion.model.texts.OficinaVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.OrganizacionVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.PaisesText;
import com.echevarne.sap.cloud.facturacion.model.texts.RegionText;
import com.echevarne.sap.cloud.facturacion.model.texts.SectorVentaText;
import com.echevarne.sap.cloud.facturacion.model.texts.TarifasText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoContratoText;
import com.echevarne.sap.cloud.facturacion.model.texts.TipoPeticionText;
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
import com.echevarne.sap.cloud.facturacion.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.var;

/**
 * Class for the Entity {@link ContratoCapitativo}.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Cacheable(false)
@Table(name = ConstEntities.ENTIDAD_CONTRATOCAPITATIVO, indexes = { @Index(name = "IDX_byTipo", columnList = "tipoContrato", unique = false),
		@Index(name = "IDX_byCliente", columnList = "codigoCliente", unique = false),
		@Index(name = "IDX_byValidez", columnList = "validoDesde,validoHasta", unique = false),
		@Index(name = "IDX_byClienteOrgValidez", columnList = "codigoCliente,organizacionVentas,validoDesde,validoHasta", unique = false) })
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = true)
public class ContratoCapitativo extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 8082827092842935413L;

	@Column(length = 200)
	private String descripcionContrato;

	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Fixed, text = "tipoContratoText/nombreTipoContrato")
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoContrato, Label = "Tipo de contrato", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "tipoContrato", LocalDataProperty = "tipoContrato") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoContrato") }) })
	@Column(length = 3)
	private String tipoContrato;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Customers, Label = "Clientes", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoCliente", LocalDataProperty = "codigoCliente") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreCliente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "clienteText/nombreCliente")
	private String codigoCliente;

	@Priorizable(priority = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOrganization, Label = "Organización de ventas", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoOrganizacion", LocalDataProperty = "organizacionVentas") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreOrganizacion") }) })
	@Column(length = 4)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "organizacionText/nombreOrganizacion")
	private String organizacionVentas;

	@Column(length = 2)
	@Priorizable(priority = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4DistributionChannel, Label = "Canal de distribución", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoCanal", LocalDataProperty = "canalDistribucion") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreCanal") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "canalDistribucionText/nombreCanal")
	private String canalDistribucion;

	@Priorizable(priority = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Division, Label = "Sector de ventas", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoSector", LocalDataProperty = "sectorVentas") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreSector") }) })
	@Column(length = 2)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "sectorText/nombreSector")
	private String sectorVentas;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validoDesde;

	@Basic
	@Sap(displayFormat = DisplayFormatEnum.Date)
	private Calendar validoHasta;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.TipoPeticion, Label = "Tipo de petición", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "tipoPeticion", LocalDataProperty = "tipoPeticion") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTipoPeticion") }) })
	@Priorizable(priority = 2)
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "tipoPeticionText/nombreTipoPeticion")
	private int tipoPeticion;

	@Priorizable(priority = 2)
	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4SalesOffice, Label = "Oficina de ventas", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoOficina", LocalDataProperty = "delegacionEmisora") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreOficina") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "delegacionEmisoraText/nombreOficina")
	private String delegacionEmisora;

	@Priorizable(priority = 2)
	@Column(length = 4)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Regiones, Label = "Provincia de la delegación", Parameters = {
			@ValueListParameter(ValueListParameterInOut = {
					@ValueListParameterInOut(ValueListProperty = "codigoRegion", LocalDataProperty = "provinciaDelegacion") }, ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreRegion") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "regionDelegacionText/nombreRegion")
	private String provinciaDelegacion;

	@Column(length = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Tarifas, Label = "Tarifa", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoTarifa", LocalDataProperty = "codigoTarifa") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreTarifa") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "tarifasText/nombreTarifa")
	private String codigoTarifa;

	@Column(length = 2)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Paises, Label = "Pais", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoPais", LocalDataProperty = "codigoPais") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombrePais") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "paisesText/nombrePais")
	private String codigoPais;

	@Column(length = 40)
	@Priorizable(priority = 2)
	private String codigoReferenciaCliente;

	@Column(length = 4, nullable = false)
	@ColumnDefault("false")
	private int periodicidad;

	@Column(precision = 10)
	@ColumnDefault("false")
	private int maximoPeticion;

	@Basic
	private boolean facturaUnica;

	@Basic
	private boolean envioPrefactura;

	@Column(precision = 16, scale = 2)
	@Sap(unit = "monedaContrato")
	private BigDecimal importeContrato;

	@Column(precision = 5, scale = 2)
	@Sap(unit = "monedaContrato")
	private BigDecimal importe2Peticion;

	@Column(precision = 5, scale = 2)
	@Sap(unit = "monedaContrato")
	private BigDecimal importe3Peticion;

	@Column(precision = 5, scale = 2)
	@Sap(unit = "monedaContrato")
	private BigDecimal importe4Peticion;

	@Column(precision = 5, scale = 2)
	@Sap(unit = "monedaContrato")
	private BigDecimal importe5Peticion;

	@Column(length = 4)
	@ColumnDefault("false")
	private int dias;

	@Basic
	@Column(length = 5)
	private int prioridad;

	@Column(length = 5)
	@ValueList(CollectionPath = ValueListEntitiesEnum.S4Divisas, Label = "Moneda del contrato", Parameters = { @ValueListParameter(ValueListParameterInOut = {
			@ValueListParameterInOut(ValueListProperty = "codigoDivisa", LocalDataProperty = "monedaContrato") }, ValueListParameterDisplayOnly = {
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisa"),
					@ValueListParameterDisplayOnly(ValueListProperty = "nombreDivisaCorto") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "divisasText/nombreDivisa", semantics = SemanticsEnum.CURRENCY_CODE)
	private String monedaContrato;

	@Basic
	private String textoFactura;

	@PrePersist
	public void prePersist() {
		if (StringUtils.equalsAnyOrNull(this.codigoCliente, StringUtils.EMPTY))
			this.codigoCliente = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.canalDistribucion, StringUtils.EMPTY))
			this.canalDistribucion = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.codigoTarifa, StringUtils.EMPTY))
			this.codigoTarifa = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.delegacionEmisora, StringUtils.EMPTY))
			this.delegacionEmisora = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.codigoReferenciaCliente, StringUtils.EMPTY))
			this.codigoReferenciaCliente = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.delegacionEmisora, StringUtils.EMPTY))
			this.delegacionEmisora = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.monedaContrato, StringUtils.EMPTY))
			this.monedaContrato = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.organizacionVentas, StringUtils.EMPTY))
			this.organizacionVentas = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.provinciaDelegacion, StringUtils.EMPTY))
			this.provinciaDelegacion = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.sectorVentas, StringUtils.EMPTY))
			this.sectorVentas = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.codigoReferenciaCliente, StringUtils.EMPTY))
			this.codigoReferenciaCliente = StringUtils.ANY;
		if (StringUtils.equalsAnyOrNull(this.codigoPais, StringUtils.EMPTY))
			this.codigoPais = ConstFacturacion.IDIOMA_DEFAULT;
	}

	/*
	 * Associations
	 *
	 ********************************************/

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contrato")
	@JsonManagedReference
	private Set<ContCapitativoInterlocutores> interlocutores = new HashSet<>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contrato")
	@JsonManagedReference
	private Set<ContCapitativoPolizas> polizas = new HashSet<>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contrato")
	@JsonManagedReference
	private Set<ContCapitativoOficinaVenta> oficinasVentaMultiple = new HashSet<>();

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "contrato")
	@JsonManagedReference
	private Set<ContCapitativoProvRemitente> provRemitenteMultiple = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "id", insertable = false, updatable = false, nullable = true)
	private ContratoCapitativoAdds adicional;

	/*
	 * Associations Texts
	 *
	 ********************************************/
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "contrato", fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonManagedReference
	private ContratoCapitativoText text;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "delegacionEmisora", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private OficinaVentaText delegacionEmisoraText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "sectorVentas", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private SectorVentaText sectorText;

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
	@JoinColumn(name = "codigoCliente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ClientesText clienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipoPeticion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoPeticionText tipoPeticionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "tipoContrato", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TipoContratoText tipoContratoText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "provinciaDelegacion", referencedColumnName = "codigoRegion", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RegionText regionDelegacionText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoTarifa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private TarifasText tarifasText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoPais", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PaisesText paisesText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "monedaContrato", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private DivisasText divisasText;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		ContratoCapitativo other = (ContratoCapitativo) obj;
		return facturaUnica == other.facturaUnica && maximoPeticion == other.maximoPeticion && periodicidad == other.periodicidad
				&& tipoPeticion == other.tipoPeticion && envioPrefactura == other.envioPrefactura && dias == other.dias && prioridad == other.prioridad
				&& Objects.equals(canalDistribucion, other.canalDistribucion) && Objects.equals(codigoCliente, other.codigoCliente)
				&& Objects.equals(codigoPais, other.codigoPais) && Objects.equals(codigoReferenciaCliente, other.codigoReferenciaCliente)
				&& Objects.equals(codigoTarifa, other.codigoTarifa) && Objects.equals(delegacionEmisora, other.delegacionEmisora)
				&& Objects.equals(importeContrato, other.importeContrato) && Objects.equals(importe2Peticion, other.importe2Peticion)
				&& Objects.equals(importe3Peticion, other.importe3Peticion) && Objects.equals(importe4Peticion, other.importe4Peticion)
				&& Objects.equals(importe5Peticion, other.importe5Peticion) && Objects.equals(monedaContrato, other.monedaContrato)
				&& Objects.equals(organizacionVentas, other.organizacionVentas) && Objects.equals(provinciaDelegacion, other.provinciaDelegacion)
				&& Objects.equals(sectorVentas, other.sectorVentas) && Objects.equals(tipoContrato, other.tipoContrato)
				&& Objects.equals(validoDesde, other.validoDesde) && Objects.equals(validoHasta, other.validoHasta);
	}

	public Optional<ContCapitativoInterlocutores> getInterlocutorWithPrefix(String prefix) {
		return interlocutores.stream().filter(inter -> inter.getCodigoInterlocutor().startsWith(prefix)).findFirst();
	}

	public Optional<ContCapitativoInterlocutores> getInterlocutorInContract(String role, String codigoInterlocutor) {
		return interlocutores.stream()
				.filter(inter -> (inter.getInterlocutoresText().getRolInterlocutor().equals(role) && inter.getCodigoInterlocutor().equals(codigoInterlocutor)))
				.findFirst();
	}

	/**
	 * @param si       SolicitudIndividual
	 * @param peticion PeticionMuestreo
	 * @return prioridad
	 */
	public Integer getPriority(SolicitudIndividual si, PeticionMuestreo peticion) {
		return this.prioridad;
	}

	public boolean applies(SolicitudIndividual solicitudIndividual, PeticionMuestreo peticion) {
		if (this.sectorVentas != null) {
			if (!StringUtils.equalsAnyOrValue(this.sectorVentas, solicitudIndividual.getOrganizationDivision()))
				return false;
		}
		if (this.tipoPeticion >=0) {
			if (!StringUtils.equalsAnyOrValue(this.tipoPeticion, peticion.getTipoPeticion()))
				return false;
		}
		if (this.provinciaDelegacion != null) {
			if (!StringUtils.equalsAnyOrValue(this.provinciaDelegacion, solicitudIndividual.getProvinciaDelegacion()))
				return false;
		}
		if (this.codigoReferenciaCliente != null) {
			if (!StringUtils.equalsAnyOrValue(this.codigoReferenciaCliente, peticion.getCodigoReferenciaCliente()))
				return false;
		}
		if (this.canalDistribucion != null) {
			if (!StringUtils.equalsAnyOrValue(this.canalDistribucion, solicitudIndividual.getDistributionChannel()))
				return false;
		}
		
		if (!checkApplyList(solicitudIndividual))
			return false;
		
		if (this.polizas.size() > 0) {
			if (!this.polizas.stream().anyMatch(pol -> StringUtils.equalsAnyOrValue(pol.getCodigoPoliza(), (solicitudIndividual.getCodigoPoliza()))))
				return false;
		}
		if (this.oficinasVentaMultiple.size() > 0) {
			if (!this.oficinasVentaMultiple.stream()
					.anyMatch(ov -> StringUtils.equalsAnyOrValue(ov.getCodigoOficinaVenta(), (solicitudIndividual.getSalesOffice()))))
				return false;
		}
		if (this.provRemitenteMultiple.size() > 0) {
			var optPmi = peticion.interlocutorByRol("ZR");
			if (optPmi.isPresent()) {
				if (!this.provRemitenteMultiple.stream()
						.anyMatch(pr -> StringUtils.equalsAnyOrValue(pr.getProvinciaRemitente(), (optPmi.get().getProvinciaInterlocutor()))))
					return false;
			}
		}
		return true;
	}

	private boolean checkApplyList(SolicitudIndividual solicitudIndividual) {
		String[] prefix = { "REM", "CIA", "PR", "PA" };
		List<String> listPrefix = Arrays.asList(prefix);
		Enumeration<String> enumPrefix = Collections.enumeration(listPrefix);
		while (enumPrefix.hasMoreElements()) {
			String pf = enumPrefix.nextElement();
			var partners = this.interlocutores.stream().filter(i -> i.getCodigoInterlocutor() != null && i.getCodigoInterlocutor().startsWith(pf))
					.map(ContCapitativoInterlocutores::getCodigoInterlocutor).collect(Collectors.toList());
			if (partners.size() > 0) {
				var partnersSol = solicitudIndividual.getPartners().stream().filter(i -> i.getCustomer() != null && i.getCustomer().startsWith(pf))
						.map(SolIndPartner::getCustomer).collect(Collectors.toList());
				if (!partners.containsAll(partnersSol)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean matchInterlocutor(List<String> partners, String customer) {
		return partners.stream().anyMatch(inter -> StringUtils.equalsAnyOrValue(inter, customer));
	}

	public OficinaVentaText getDelegacionEmisoraText() {
		return EntityUtil.getOrNull(() -> this.delegacionEmisoraText, OficinaVentaText::getNombreOficina);
	}

	public SectorVentaText getSectorText() {
		return EntityUtil.getOrNull(() -> this.sectorText, SectorVentaText::getNombreSector);
	}

	public OrganizacionVentaText getOrganizacionText() {
		return EntityUtil.getOrNull(() -> this.organizacionText, OrganizacionVentaText::getNombreOrganizacion);
	}

	public CanalDistribucionText getCanalDistribucionText() {
		return EntityUtil.getOrNull(() -> this.canalDistribucionText, CanalDistribucionText::getNombreCanal);
	}

	public ClientesText getClienteText() {
		return EntityUtil.getOrNull(() -> this.clienteText, ClientesText::getNombreCliente);
	}

	public TipoPeticionText getTipoPeticionText() {
		return EntityUtil.getOrNull(() -> this.tipoPeticionText, TipoPeticionText::getNombreTipoPeticion);
	}

	public TipoContratoText getTipoContratoText() {
		return EntityUtil.getOrNull(() -> this.tipoContratoText, TipoContratoText::getNombreTipoContrato);
	}

	public RegionText getRegionDelegacionText() {
		return EntityUtil.getOrNull(() -> this.regionDelegacionText, RegionText::getNombreRegion);
	}

	public TarifasText getTarifasText() {
		return EntityUtil.getOrNull(() -> this.tarifasText, TarifasText::getNombreTarifa);
	}

	public PaisesText getPaisesText() {
		return EntityUtil.getOrNull(() -> this.paisesText, PaisesText::getNombrePais);
	}

	public DivisasText getDivisasText() {
		return EntityUtil.getOrNull(() -> this.divisasText, DivisasText::getNombreDivisa);
	}
}
