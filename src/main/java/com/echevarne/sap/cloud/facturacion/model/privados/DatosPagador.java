package com.echevarne.sap.cloud.facturacion.model.privados;

import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.echevarne.sap.cloud.facturacion.model.BasicEntity;
import com.echevarne.sap.cloud.facturacion.model.trazabilidad.TrazabilidadSolicitud;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.SemanticsEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
@Table(name = "T_DatosPagador", indexes = {
		@Index(name = "DatosPagador_byDocumento", columnList = "tipoDocumentoPagador,numeroDocumentoPagador", unique = false),
		@Index(name = "DatosPagador_byNombre", columnList = "nombrePagador,primApellidoPagador", unique = false),
		@Index(name = "DatosPagador_byEmail", columnList = "emailPagador", unique = false),
		@Index(name = "DatosPagador_byTelefono", columnList = "telefono1Pagador,telefono2Pagador", unique = false)})
@SapEntitySet(creatable = true, updatable = true, searchable = true, deletable = true)
public class DatosPagador extends BasicEntity {

	private static final long serialVersionUID = 8105141872789758428L;

	@Column(length = 35)
	@Sap(semantics = SemanticsEnum.NAME)
	private String nombrePagador;

	@Column(length = 35)
	@Sap(semantics = SemanticsEnum.FAMILYNAME)
	private String primApellidoPagador;

	@Column(length = 35)
	private String segApellidoPagador;

	@Basic
	private String numeroDocumentoPagador;

	@Basic
	private String tipoDocumentoPagador;

	@Column(length = 60)
	@Sap(semantics = SemanticsEnum.STREET)
	private String direccionPagador;

	@Column(length = 35)
	@Sap(semantics = SemanticsEnum.CITY)
	private String ciudadPagador;

	@Basic
	@Sap(semantics = SemanticsEnum.POBOX)
	private String codigoPostalPagador;

	@Basic
	@Sap(semantics = SemanticsEnum.REGION)
	private String provinciaPagador;

	@Basic
	@Sap(semantics = SemanticsEnum.TEL)
	private String telefono1Pagador;

	@Basic
	@Sap(semantics = SemanticsEnum.TEL)
	private String telefono2Pagador;

	@Basic
	@Sap(semantics = SemanticsEnum.EMAIL)
	private String emailPagador;

	@Basic
	@Sap(semantics = SemanticsEnum.COUNTRY)
	private String codigoPaisPagador;

	@Basic
	private String codigoIdiomaPagador;

	@Basic
	@ColumnDefault("false")
	private boolean esEmpresa;

	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "fk_TrazabilidadSolicitud", nullable = false)
	@JsonIgnore
	private TrazabilidadSolicitud trzSolicitud;

	@Override
	public boolean onEquals(Object obj) {
		return equals(obj);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DatosPagador other = (DatosPagador) obj;
		return  Objects.equals(this.nombrePagador, other.nombrePagador) &&
				Objects.equals(this.primApellidoPagador, other.primApellidoPagador) &&
				Objects.equals(this.segApellidoPagador, other.segApellidoPagador) &&
				Objects.equals(this.numeroDocumentoPagador, other.numeroDocumentoPagador) &&
				Objects.equals(this.tipoDocumentoPagador, other.tipoDocumentoPagador) &&
				Objects.equals(this.direccionPagador, other.direccionPagador) &&
				Objects.equals(this.ciudadPagador, other.ciudadPagador) &&
				Objects.equals(this.codigoPostalPagador, other.codigoPostalPagador) &&
				Objects.equals(this.provinciaPagador, other.provinciaPagador) &&
				Objects.equals(this.telefono1Pagador, other.telefono1Pagador) &&
				Objects.equals(this.telefono2Pagador, other.telefono2Pagador) &&
				Objects.equals(this.emailPagador, other.emailPagador) &&
				Objects.equals(this.codigoPaisPagador, other.codigoPaisPagador) &&
				Objects.equals(this.codigoIdiomaPagador, other.codigoIdiomaPagador) &&
				Objects.equals(this.esEmpresa, other.esEmpresa);
	}

	public void update(DatosPagador other) {
		this.nombrePagador = other.nombrePagador;
		this.primApellidoPagador = other.primApellidoPagador;
		this.segApellidoPagador = other.segApellidoPagador;
		this.numeroDocumentoPagador = other.numeroDocumentoPagador;
		this.tipoDocumentoPagador = other.tipoDocumentoPagador;
		this.direccionPagador = other.direccionPagador;
		this.ciudadPagador = other.ciudadPagador;
		this.codigoPostalPagador = other.codigoPostalPagador;
		this.provinciaPagador = other.provinciaPagador;
		this.telefono1Pagador = other.telefono1Pagador;
		this.telefono2Pagador = other.telefono2Pagador;
		this.emailPagador = other.emailPagador;
		this.codigoPaisPagador = other.codigoPaisPagador;
		this.codigoIdiomaPagador = other.codigoIdiomaPagador;
		this.esEmpresa = other.esEmpresa;
	}

	public String getCleanNombrePagador() {
		String nombreCompletoPagador = "";

		if (!Strings.isNullOrEmpty(this.getNombrePagador()))
			nombreCompletoPagador = this.getNombrePagador();
		if (!Strings.isNullOrEmpty(this.getPrimApellidoPagador()))
			nombreCompletoPagador = nombreCompletoPagador.concat(StringUtils.SPACE + this.getPrimApellidoPagador());
		if (!Strings.isNullOrEmpty(this.getSegApellidoPagador()))
			nombreCompletoPagador = nombreCompletoPagador.concat(StringUtils.SPACE + this.getSegApellidoPagador());

		return StringUtils.substring(nombreCompletoPagador, 0, 50);
	}

}
