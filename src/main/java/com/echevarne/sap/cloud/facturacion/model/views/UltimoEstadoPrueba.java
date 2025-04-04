package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.proxy.HibernateProxy;

import com.echevarne.sap.cloud.facturacion.model.texts.EstadosFacturacionGrupoText;
import com.echevarne.sap.cloud.facturacion.model.texts.EstadosFacturacionText;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.FilterRestrictionsEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.PropertyValueListEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.enums.ValueListEntitiesEnum;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueList;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameter;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterDisplayOnly;
import com.echevarne.sap.cloud.facturacion.odata.annotations.sap.common.ValueListParameterInOut;
import com.echevarne.sap.cloud.facturacion.persistence.EntityUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_PRUE_ULTESTADO")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@SapEntitySet(creatable = false, updatable = false, searchable = true)
public class UltimoEstadoPrueba implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5752210674004586466L;

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
	@ValueList(CollectionPath = ValueListEntitiesEnum.Estados, Label = "Estado de la prueba", Parameters = {
			@ValueListParameter(
					ValueListParameterInOut = {
						@ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "codigoEstado")
					},
					ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado")
					}
			)
	})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text="estadoText/nombreEstado", valueList=PropertyValueListEnum.Fixed)
	private int codigoEstado;

	@Basic
	@ValueList(CollectionPath = ValueListEntitiesEnum.GrupoEstados, Label = "Estado de facturación", Parameters = {
			@ValueListParameter(
					ValueListParameterInOut = {
						@ValueListParameterInOut(ValueListProperty = "codigoEstado", LocalDataProperty = "codigoGrupoEstado")
					},
					ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nombreEstado")
					}
			)
	})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, text="estadoFactText/nombreEstado", valueList=PropertyValueListEnum.Fixed)
	private int codigoGrupoEstado;

	@Basic
	@Sap(filterable = false)
	private Timestamp fechaEstado;

	/*
	 * Motivos de estados
	 *
	 *******************************************/
	@OneToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "uuid", referencedColumnName = "uuid_parent", insertable = false, updatable = false)
	private MotivosPruebaEstado motivo;

	/*
	 * UI Fields
	 *
	 *******************************************/
	@Basic
	private int critically;

	/*
     * Associations Texts
     *
     ********************************************/
    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoEstado", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EstadosFacturacionText estadoText;

    @ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name="codigoGrupoEstado", insertable=false, updatable=false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EstadosFacturacionGrupoText estadoFactText;


	public EstadosFacturacionText getEstadoText() {
		return EntityUtil.getOrNull(() -> this.estadoText, EstadosFacturacionText::getNombreEstado);
	}

	public EstadosFacturacionGrupoText getEstadoFactText() {
		return EntityUtil.getOrNull(() -> this.estadoFactText, EstadosFacturacionGrupoText::getNombreEstado);
	}
	
	/**
	 * Ver
	 * https://jpa-buddy.com/blog/hopefully-the-final-article-about-equals-and-hashcode-for-jpa-entities-with-db-generated-ids/
	 */
	public final boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		Class<?> oEffectiveClass = o instanceof HibernateProxy
				? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
				: o.getClass();
		Class<?> thisEffectiveClass = this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
				: this.getClass();
		if (thisEffectiveClass != oEffectiveClass)
			return false;
		UltimoEstadoPrueba uep = (UltimoEstadoPrueba) o;
		return getUuid() != null && Objects.equals(getUuid(), uep.getUuid());
	}

	public final int hashCode() {
		return this instanceof HibernateProxy
				? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
				: getClass().hashCode();
	}
}
