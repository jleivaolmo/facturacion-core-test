package com.echevarne.sap.cloud.facturacion.model.trazabilidad;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.masterdata.MasDataMotivosRectificacion;
import com.echevarne.sap.cloud.facturacion.model.solicitudagrupada.SolicitudesAgrupadas;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Hernan Girardi
 * @since 28/04/2020
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(
		name = "T_TrazabilidadSolicitudAgr",
		indexes = {
			@Index(name = "TrazabilidadSolicitudAgr_bySalesOrder", columnList = "salesOrder", unique = false),
			@Index(name = "TrazabilidadSolicitudAgr_byBillingDocument", columnList = "billingDocument", unique = false),
			@Index(name = "TrazabilidadSolicitudAgr_unique", columnList = "fk_SolicitudAgrupada,fk_TrazabilidadSolicitud", unique = true),
		}
)
@SapEntitySet(creatable = false, updatable = false, searchable = true, deletable = false)
public class TrazabilidadSolicitudAgrupado extends BasicEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = -8558335025737320932L;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_SolicitudAgrupada")
	@JsonIgnore
	private SolicitudesAgrupadas solicitudAgr;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrazabilidadSolicitud", nullable = false)
	@JsonIgnore
	private TrazabilidadSolicitud trazabilidad;

	@Basic
	private String salesOrder;

	@Basic
	private String billingDocument;

	@Basic
	private String accountingDocument;

	@Basic
	private String cancelBillingDoc;

	@Basic
	private Date fechaFactura;
	
	//Se usa el tipo primitivo boolean para que se inicialice a false por defecto
	@Basic
	private boolean isRectificacion;
	
	@Basic
	private Date fechaVencimiento;
	
	@Basic
	private String formaPago;

	@OneToMany(mappedBy = "trazabilidad", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<TrazabilidadSolAgrDocumentosSAP> trzDocumentosSAP = new HashSet<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_MotivoRectificacion", nullable = true)
	@JsonIgnore
	private MasDataMotivosRectificacion motivoRectificacion;

	public TrazabilidadSolicitudAgrupado(SolicitudesAgrupadas solicitudAgrupada, TrazabilidadSolicitud ts) {
		this.solicitudAgr = solicitudAgrupada;
		this.trazabilidad = ts;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		TrazabilidadSolicitudAgrupado other = (TrazabilidadSolicitudAgrupado) obj;
		return Objects.equals(solicitudAgr, other.solicitudAgr)
			&& Objects.equals(trazabilidad, other.trazabilidad);
	}

	public void addTrzDocumentosSAP(TrazabilidadSolAgrDocumentosSAP trzDocumentoSAP) {
		this.trzDocumentosSAP.add(trzDocumentoSAP);
	}
}
