package com.echevarne.sap.cloud.facturacion.model.masterdata;

import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.constants.ConstEntities;
import com.echevarne.sap.cloud.facturacion.model.BasicMasDataEntity;
import com.echevarne.sap.cloud.facturacion.model.parametrizacion.AccionesPermitidas;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

/**
 *
 * @author Hernan Girardi
 * @since 16/06/2020
 */
@Getter
@Setter
@Entity
@Table(name=ConstEntities.ENTIDAD_MASDATAESTADO,
	indexes=@Index(name = "IDX_ByCodeEstado",  columnList="codeEstado", unique=true))
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@SapEntitySet(creatable = true, updatable = true, searchable = false, deletable = false)
@NoArgsConstructor
public class MasDataEstado extends BasicMasDataEntity {

	private static final long serialVersionUID = -6807877846468211362L;
	public static final int ESTADO_PRINCIPAL 	= 1;
	public static final int ESTADO_SECUNDARIO 	= 2;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("" + ESTADO_PRINCIPAL)
	private int tipoEstado = ESTADO_PRINCIPAL;

	@Basic
	private String nombre;

	@Basic
	private String descripcion;

	@Basic
	@NaturalId
	private String codeEstado;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean aplicaPadre;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
    private int critically;

	@Basic
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean estadoTecnico;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "FK_GrupoEstado", nullable = false)
	@BatchSize(size = 50)
	@JsonBackReference
	private MasDataEstadosGrupo estadosGrupo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
	@JsonManagedReference
	@BatchSize(size = 50)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<MasDataCombinacionEstado> combinacion;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estado")
	@JsonManagedReference
	@BatchSize(size = 50)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<AccionesPermitidas> accionesPermitidas;


	public MasDataEstado(String nombre, String descripcion, String code) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codeEstado = code;
	}

	public MasDataEstado(int tipoEstado, String nombre, String descripcion, String code) {
		this.tipoEstado = tipoEstado;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.codeEstado = code;
	}

	@Override
	public boolean onEquals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		MasDataEstado other = (MasDataEstado) obj;
		return  Objects.equals(this.nombre, other.nombre) &&
				Objects.equals(this.tipoEstado, other.tipoEstado) &&
				Objects.equals(this.estadoTecnico, other.estadoTecnico) &&
				Objects.equals(this.descripcion, other.descripcion) &&
				Objects.equals(this.codeEstado, other.codeEstado);
	}
}
