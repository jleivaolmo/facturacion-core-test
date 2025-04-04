package com.echevarne.sap.cloud.facturacion.model.views;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.echevarne.sap.cloud.facturacion.model.texts.CompaniasText;
import com.echevarne.sap.cloud.facturacion.model.texts.EmpresasText;
import com.echevarne.sap.cloud.facturacion.model.texts.PacientesText;
import com.echevarne.sap.cloud.facturacion.model.texts.ProfesionalesText;
import com.echevarne.sap.cloud.facturacion.model.texts.RemitentesText;
import com.echevarne.sap.cloud.facturacion.model.texts.VisitadoresText;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "V_INTER_SOLI_TODOS")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.NONE)
@SapEntitySet(creatable = false, updatable = false, searchable = false)
public class InterlocutoresSolicitud implements Serializable{

	private static final long serialVersionUID = 1746344874919338986L;

	/*
	 * Clave
	 *
	 *******************************************/
    @Id
	@Basic
	private Long uuid;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Remitentes, Label = "Remitente", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoRemitente", LocalDataProperty = "codigoRemitente") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreRemitente") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "remitenteText/nombreRemitente", filterable = true)
	private String codigoRemitente;

	@Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Companias, Label = "Compañia", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoCompania", LocalDataProperty = "codigoCompania") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreCompania") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "companiaText/nombreCompania", filterable = true)
	private String codigoCompania;

    @Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Pacientes, Label = "Paciente", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoPaciente") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre")}),
		@ValueListParameter(
			ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "z_nifCliente")})})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "pacienteText/nombre", filterable = true)
	private String codigoPaciente;

	@Basic
	@Sap(filterable = false)
	private String nifPaciente;

    @Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Empresas, Label = "Empresa", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigoEmpresa", LocalDataProperty = "codigoEmpresa") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombreEmpresa") }) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "empresaText/nombreEmpresa", filterable = false)
	private String codigoEmpresa;

    @Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Profesionales, Label = "Profesional", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoProfesional") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }),
		@ValueListParameter(
			ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nifCliente")})})
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "profesionalText/nombre", filterable = false)
	private String codigoProfesional;

    @Column(length = 10)
	@ValueList(CollectionPath = ValueListEntitiesEnum.Visitadores, Label = "Visitador", Parameters = {
		@ValueListParameter(ValueListParameterInOut = {
				@ValueListParameterInOut(ValueListProperty = "codigo", LocalDataProperty = "codigoVisitador") }, ValueListParameterDisplayOnly = {
						@ValueListParameterDisplayOnly(ValueListProperty = "nombre") }),
		@ValueListParameter(
				ValueListParameterDisplayOnly = {
							@ValueListParameterDisplayOnly(ValueListProperty = "nifCliente")}) })
	@Sap(filterRestriction = FilterRestrictionsEnum.Multiple, valueList = PropertyValueListEnum.Standard, text = "visitadorText/nombre", filterable = false)
	private String codigoVisitador;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoEmpresa", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private EmpresasText empresaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoVisitador", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private VisitadoresText visitadorText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoPaciente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private PacientesText pacienteText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoProfesional", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private ProfesionalesText profesionalText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoCompania", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private CompaniasText companiaText;

	@ManyToOne(fetch = FetchType.LAZY)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@JoinColumn(name = "codigoRemitente", insertable = false, updatable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private RemitentesText remitenteText;

	public EmpresasText getEmpresaText() {
		return EntityUtil.getOrNull(() -> this.empresaText, EmpresasText::getNombreEmpresa);
	}

	public VisitadoresText getVisitadorText() {
		return EntityUtil.getOrNull(() -> this.visitadorText, VisitadoresText::getNombre);
	}

	public PacientesText getPacienteText() {
		return EntityUtil.getOrNull(() -> this.pacienteText, PacientesText::getNombre);
	}

	public ProfesionalesText getProfesionalText() {
		return EntityUtil.getOrNull(() -> this.profesionalText, ProfesionalesText::getNombre);
	}

	public CompaniasText getCompaniaText() {
		return EntityUtil.getOrNull(() -> this.companiaText, CompaniasText::getNombreCompania);
	}

	public RemitentesText getRemitenteText() {
		return EntityUtil.getOrNull(() -> this.remitenteText, RemitentesText::getNombreRemitente);
	}

}
