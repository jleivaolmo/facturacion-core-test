package com.echevarne.sap.cloud.facturacion.model.texts;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.echevarne.sap.cloud.facturacion.odata.annotations.JPAExit;
import com.echevarne.sap.cloud.facturacion.odata.annotations.Sap;
import com.echevarne.sap.cloud.facturacion.odata.annotations.SapEntitySet;

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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Table(name = "VT_CECO")
@SapEntitySet(creatable = false, updatable = false, searchable = true)
@JPAExit(allowAll = true, fieldId = "codigoCentro", fieldDescription = "nombreCentro")
public class CentroCosteText implements Serializable {
	
	private static final long serialVersionUID = 6102850467077199806L;
	
	@Id
	@Sap(text="nombreCentro")
	//@Column(columnDefinition="VARCHAR(255)")
	private String codigoCentro;
	
	@Basic
	private String nombreCentro;
}
