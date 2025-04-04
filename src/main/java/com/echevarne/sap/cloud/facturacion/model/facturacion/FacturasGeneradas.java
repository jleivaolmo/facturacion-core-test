package com.echevarne.sap.cloud.facturacion.model.facturacion;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.contratos.ContratoCapitativo;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataSubTipologiaFacturacion;
import com.echevarne.sap.cloud.facturacion.model.reglasfacturacion.ReglasFacturacion;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Table(
	name = ConstEntities.ENTIDAD_FACTLOGGENERADAS,
	indexes = {
		@Index(name = "IDX_byIdAgrupacion", columnList = "idAgrupacion", unique = false),
	}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = false, includeFieldNames = false)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class FacturasGeneradas extends BasicEntity {

	/**
	*
	*/
	private static final long serialVersionUID = -1966049391401089854L;

	/*
	 * Campos
	 *
	 ********************************************/
	@Basic
	private String UUID;
	
	@Basic
	private String agrupacionKey;

	@Basic
	private String divisorFactura;

	@Basic
	private Long idAgrupacion;

	@Basic
	@Column(unique = true)
	private String salesOrder;

	@Basic
	private String billingDocument;

	@Basic
	private String accountingDocument;

	@Basic
	private boolean esPrefactura = false;

	@Basic
	private int critically;

	@Basic
	private Date fechaFactura;

	@Basic
	private Long idPrefactura;
	
	@Basic
	private Date fechaVencimiento;
	
	@Basic
	private String formaPago;

	/*
	 * Asociaciones
	 *
	 ********************************************/
	@OneToMany(cascade = CascadeType.ALL, mappedBy="factura")
	@JsonManagedReference
	@ToString.Exclude
	private Set<LogFacturacion> messages;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_PeriodoFacturado", nullable = false)
	@JsonBackReference
	@ToString.Exclude
	private PeriodosFacturados periodo;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ReglaFacturacion", nullable = true)
	@JsonIgnore
	@ToString.Exclude
	private ReglasFacturacion regla;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_ContratoCapitativo", nullable = true)
	@JsonIgnore
	@ToString.Exclude
	private ContratoCapitativo contrato;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SubTipologiaFacturacion", nullable = false)
	@JsonIgnore
	@ToString.Exclude
	private MasDataSubTipologiaFacturacion subTipologia;

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		FacturasGeneradas other = (FacturasGeneradas) obj;
		return Objects.equals(accountingDocument, other.accountingDocument) && Objects.equals(agrupacionKey, other.agrupacionKey) && Objects.equals(billingDocument, other.billingDocument)
				&& Objects.equals(contrato, other.contrato) && critically == other.critically && Objects.equals(divisorFactura, other.divisorFactura) && esPrefactura == other.esPrefactura
				&& Objects.equals(fechaFactura, other.fechaFactura) && Objects.equals(fechaVencimiento, other.fechaVencimiento) && Objects.equals(formaPago, other.formaPago)
				&& Objects.equals(idAgrupacion, other.idAgrupacion) && Objects.equals(idPrefactura, other.idPrefactura) && Objects.equals(messages, other.messages)
				&& Objects.equals(periodo, other.periodo) && Objects.equals(regla, other.regla) && Objects.equals(salesOrder, other.salesOrder) && Objects.equals(subTipologia, other.subTipologia);
	}
}
